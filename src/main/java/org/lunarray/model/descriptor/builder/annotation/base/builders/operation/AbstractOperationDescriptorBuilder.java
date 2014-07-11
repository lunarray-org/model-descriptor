/* 
 * Model Tools.
 * Copyright (C) 2013 Pal Hargitai (pal@lunarray.org)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lunarray.model.descriptor.builder.annotation.base.builders.operation;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.AbstractBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildOperationEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.OperationProcessorListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.ProcessParameterListener;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes a operation descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The context type.
 */
public abstract class AbstractOperationDescriptorBuilder<E, B extends AnnotationBuilderContext>
		extends AbstractBuilder<B>
		implements AnnotationOperationDescriptorBuilder<E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOperationDescriptorBuilder.class);
	/** The entity type. */
	private transient DescribedEntity<E> entityTypeBuilder;
	/** The extensions. */
	private final transient Map<Class<?>, OperationExtension<E>> extensions;
	/** The operation name. */
	private transient String nameBuilder;
	/** The operation. */
	private transient DescribedOperation operationBuilder;
	/** The operation reference. */
	private transient OperationReference<E> operationReferenceBuilder;
	/** The parameters. */
	private transient List<ParameterDescriptor<?>> parameterDescriptors;
	/** The parameter builders. */
	private final transient List<AnnotationParameterDescriptorBuilder<?, B>> parameters;
	/** The result type builder. */
	private transient AnnotationResultDescriptorBuilder<?, E, B> resultBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	public AbstractOperationDescriptorBuilder(final B builderContext) {
		super(builderContext);
		this.extensions = new HashMap<Class<?>, OperationExtension<E>>();
		this.parameters = new LinkedList<AnnotationParameterDescriptorBuilder<?, B>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new OperationProcessorListener<E, B>(), this);
		bus.addListener(new ProcessParameterListener<E, AnnotationBuilderContext>(), this);
	}

	/** {@inheritDoc} */
	@Override
	public final <C, P extends Collection<C>> AnnotationCollectionParameterDescriptorBuilder<C, P, B> addCollectionParameter() {
		final AnnotationCollectionParameterDescriptorBuilder<C, P, B> builder = this.createCollectionBuilder();
		this.parameters.add(builder);
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	public final <P> AnnotationParameterDescriptorBuilder<P, B> addParameter() {
		final AnnotationParameterDescriptorBuilder<P, B> builder = this.createBuilder();
		this.parameters.add(builder);
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	public final OperationDescriptor<E> build() {
		final Pattern pattern = Pattern.compile(this.getBuilderContext().getConfiguration().nameRegexp());
		final Matcher matcher = pattern.matcher(this.nameBuilder);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("Invalid name '%s'.", this.nameBuilder));
		}
		final OperationDescriptor<E> operationDescriptor = this.createOperationDescriptor();
		try {
			final B ctx = this.getBuilderContext();
			final Bus bus = ctx.getBus();
			final BuildOperationEvent<E> event = new BuildOperationEvent<E>(operationDescriptor);
			bus.handleEvent(event, this.resultBuilder);
			bus.handleEvent(event, this);
		} catch (final EventException e) {
			AbstractOperationDescriptorBuilder.LOGGER.warn("Could not process build event.", e);
		}
		return operationDescriptor;
	}

	/** {@inheritDoc} */
	@Override
	public final <C, R extends Collection<C>> AnnotationCollectionResultDescriptorBuilder<C, R, E, B> createCollectionResultTypeBuilder() {
		final AnnotationCollectionResultDescriptorBuilder<C, R, E, B> collectionBuilder = this.createCollectionResultDescriptorBuilder();
		this.resultBuilder = collectionBuilder;
		return collectionBuilder;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Derived type.
	@Override
	public final AnnotationResultDescriptorBuilder<?, E, B> createResultTypeBuilder() {
		this.resultBuilder = this.createResultDescriptorBuilder();
		return this.resultBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationOperationDescriptorBuilder<E, B> entityType(final DescribedEntity<E> entityType) {
		this.entityTypeBuilder = entityType;
		return this;
	}

	/**
	 * Gets the value for the entityTypeBuilder field.
	 * 
	 * @return The value for the entityTypeBuilder field.
	 */
	public final DescribedEntity<E> getEntityType() {
		return this.entityTypeBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedOperation getOperation() {
		return this.operationBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final OperationReference<E> getOperationReference() {
		return this.operationReferenceBuilder;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Derived type.
	@Override
	public final AnnotationResultDescriptorBuilder<?, E, B> getResultBuilder() {
		return this.resultBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationOperationDescriptorBuilder<E, B> name(final String name) {
		this.nameBuilder = name;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationOperationDescriptorBuilder<E, B> operation(final DescribedOperation operation) {
		this.operationBuilder = operation;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationOperationDescriptorBuilder<E, B> operationReference(final OperationReference<E> operationReference) {
		this.operationReferenceBuilder = operationReference;
		try {
			final Bus bus = this.getBuilderContext().getBus();
			final UpdatedOperationReferenceEvent<E, B> event = new UpdatedOperationReferenceEvent<E, B>(this, this.operationBuilder,
					this.operationReferenceBuilder);
			bus.handleEvent(event, this.resultBuilder);
			bus.handleEvent(event, this);
		} catch (final EventException e) {
			AbstractOperationDescriptorBuilder.LOGGER.warn("Could not process operation reference update event.", e);
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final void registerExtension(final Class<? extends OperationExtension<E>> extensionInt, final OperationExtension<E> extension) {
		this.extensions.put(extensionInt, extension);
	}

	/**
	 * Creates a parameter builder.
	 * 
	 * @return The builder.
	 * @param <P>
	 *            The parameter type.
	 */
	protected abstract <P> AnnotationParameterDescriptorBuilder<P, B> createBuilder();

	/**
	 * Creates a collection parameter builder.
	 * 
	 * @return The builder.
	 * @param <P>
	 *            The parameter type.
	 * @param <C>
	 *            The collection type.
	 */
	protected abstract <C, P extends Collection<C>> AnnotationCollectionParameterDescriptorBuilder<C, P, B> createCollectionBuilder();

	/**
	 * Creates the result type descriptor.
	 * 
	 * @return The result descriptor.
	 * @param <C>
	 *            The collection type.
	 * @param <R>
	 *            The rutern type.
	 */
	protected abstract <C, R extends Collection<C>> AnnotationCollectionResultDescriptorBuilder<C, R, E, B> createCollectionResultDescriptorBuilder();

	/**
	 * Create a operation descriptor.
	 * 
	 * @return A operation descriptor.
	 */
	protected abstract OperationDescriptor<E> createOperationDescriptor();

	/**
	 * Creates the result type descriptor.
	 * 
	 * @return The result descriptor.
	 * @param <R>
	 *            The rutern type.
	 */
	protected abstract <R> AnnotationResultDescriptorBuilder<R, E, B> createResultDescriptorBuilder();

	/**
	 * Get the extensions.
	 * 
	 * @return The extensions.
	 */
	protected final Map<Class<?>, OperationExtension<E>> getExtensions() {
		return this.extensions;
	}

	/**
	 * Gets the name.
	 * 
	 * @return The name.
	 */
	protected final String getName() {
		return this.nameBuilder;
	}

	/**
	 * Gets the parameter.
	 * 
	 * @return The parameters.
	 */
	protected final List<ParameterDescriptor<?>> getParameters() {
		if (CheckUtil.isNull(this.parameterDescriptors)) {
			this.parameterDescriptors = new LinkedList<ParameterDescriptor<?>>();
			for (final AnnotationParameterDescriptorBuilder<?, B> parameter : this.parameters) {
				this.parameterDescriptors.add(parameter.build());
			}
		}
		return this.parameterDescriptors;
	}

	/**
	 * Gets the result descriptor.
	 * 
	 * @return The result descriptor.
	 * @param <R>
	 *            The result type.
	 */
	protected final <R> ResultDescriptor<R> getResultDescriptor() {
		@SuppressWarnings("unchecked")
		// Derived type.
		final AnnotationResultDescriptorBuilder<R, E, B> builder = (AnnotationResultDescriptorBuilder<R, E, B>) this.resultBuilder;
		ResultDescriptor<R> result = null;
		if (!CheckUtil.isNull(builder)) {
			result = builder.build();
		}
		return result;
	}
}
