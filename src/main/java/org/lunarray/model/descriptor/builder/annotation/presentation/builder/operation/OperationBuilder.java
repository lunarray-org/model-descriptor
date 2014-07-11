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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildOperationEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.parameter.CollectionParameterBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.parameter.ParameterBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.result.CollectionResultBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.result.ResultBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.operation.OperationProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.OperationDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.PresQualOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualCollectionResultTypeDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualResultTypeDescriptorBuilder;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A operation builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class OperationBuilder<E>
		extends AbstractOperationDescriptorBuilder<E, PresQualBuilderContext>
		implements PresQualOperationDescriptorBuilder<E> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationBuilder.class);

	/**
	 * Creates the builder.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param builderContext
	 *            The builder. May not be null.
	 * @return The builder.
	 */
	public static <E> OperationBuilder<E> create(final PresQualBuilderContext builderContext) {
		Validate.notNull(builderContext, "Context may not be null.");
		return new OperationBuilder<E>(builderContext);
	}

	/** The cached detail. */
	private transient OperationDetail cachedDetail;
	/** Caches qualifier values. */
	private final transient Map<Class<?>, OperationReference<?, E>> cachedQualifierValues;
	/** Cached descriptor. */
	private transient OperationDescriptor<?, E> cachedValue;
	/** Detail builder. */
	private final transient OperationDetailBuilderImpl detailBuilder;
	/** Entity name. */
	private transient String entityNameBuilder;
	/** The parameters. */
	private final transient List<PresQualParameterDescriptorBuilder<?>> parameterBuilders;
	/** Qualifier builders. */
	private final transient Map<Class<?>, OperationDetailBuilderImpl> qualifierDetailBuilder;
	/** The result type builder. */
	private transient PresQualResultTypeDescriptorBuilder<?, E> resultTypeBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder.
	 */
	private OperationBuilder(final PresQualBuilderContext builderContext) {
		super(builderContext);
		this.detailBuilder = OperationDetailBuilderImpl.create();
		this.qualifierDetailBuilder = new HashMap<Class<?>, OperationDetailBuilderImpl>();
		this.cachedQualifierValues = new HashMap<Class<?>, OperationReference<?, E>>();
		this.parameterBuilders = new LinkedList<PresQualParameterDescriptorBuilder<?>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new CacheBuilder(), this);
		bus.addListener(new Delegate(), this);
		bus.addListener(new OperationProcessor<E>(), this);
	}

	/**
	 * Gets the detail builder.
	 * 
	 * @return The builder.
	 */
	public OperationDetailBuilderImpl detail() {
		return this.detailBuilder;
	}

	/**
	 * Gets a detail builder.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The builder.
	 */
	public OperationDetailBuilderImpl detail(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, OperationDetailBuilderImpl.create());
		}
		return this.qualifierDetailBuilder.get(qualifier);
	}

	/**
	 * Sets a new value for the entityName field.
	 * 
	 * @param entityName
	 *            The new value for the entityName field.
	 * @return The builder.
	 */
	public PresQualOperationDescriptorBuilder<E> entityName(final String entityName) {
		this.entityNameBuilder = entityName;
		return this;
	}

	/**
	 * Gets cached values.
	 * 
	 * @return The values.
	 */
	public Map<Class<?>, OperationReference<?, E>> getCachedQualifierValues() {
		return this.cachedQualifierValues;
	}

	/** {@inheritDoc} */
	@Override
	public OperationDetailBuilderImpl getDetailBuilder() {
		return this.detailBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public OperationDetailBuilder getDetailBuilder(final Class<?> qualifier) {
		OperationDetailBuilder result;
		if (this.qualifierDetailBuilder.containsKey(qualifier)) {
			result = this.qualifierDetailBuilder.get(qualifier);
		} else {
			result = this.getDetailBuilder();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public OperationDescriptor<?, E> getOperationDescriptor() {
		return this.cachedValue;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Derived.
	@Override
	public <R> PresQualResultTypeDescriptorBuilder<R, E> getPresentationResultBuilder() {
		return (PresQualResultTypeDescriptorBuilder<R, E>) this.resultTypeBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public OperationDetailBuilderImpl getQualifierBuilder(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, OperationDetailBuilderImpl.create());
		}
		return this.qualifierDetailBuilder.get(qualifier);
	}

	/**
	 * Gets the value for the qualifierDetailBuilder field.
	 * 
	 * @return The value for the qualifierDetailBuilder field.
	 */
	public Map<Class<?>, OperationDetailBuilderImpl> getQualifierDetailBuilder() {
		return this.qualifierDetailBuilder;
	}

	/** {@inheritDoc} */
	@Override
	protected <P> AnnotationParameterDescriptorBuilder<P, PresQualBuilderContext> createBuilder() {
		final ParameterBuilder<P> builder = ParameterBuilder.create(this.getBuilderContext());
		builder.entityName(this.entityNameBuilder);
		if (StringUtil.isEmptyString(this.detailBuilder.getDescriptionKey())) {
			builder.operationName(this.getName());
		} else {
			builder.operationName(this.detailBuilder.getDescriptionKey());
		}
		this.parameterBuilders.add(builder);
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	protected <C, P extends Collection<C>> AnnotationCollectionParameterDescriptorBuilder<C, P, PresQualBuilderContext> createCollectionBuilder() {
		final CollectionParameterBuilder<C, P> builder = CollectionParameterBuilder.createBuilder(this.getBuilderContext());
		builder.entityName(this.entityNameBuilder);
		if (StringUtil.isEmptyString(this.detailBuilder.getDescriptionKey())) {
			builder.operationName(this.getName());
		} else {
			builder.operationName(this.detailBuilder.getDescriptionKey());
		}
		this.parameterBuilders.add(builder);
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	protected <C, R extends Collection<C>> AnnotationCollectionResultDescriptorBuilder<C, R, E, PresQualBuilderContext> createCollectionResultDescriptorBuilder() {
		final PresQualCollectionResultTypeDescriptorBuilder<C, R, E> builder = CollectionResultBuilder.createBuilder(this.getOperation(),
				this.getBuilderContext());
		final PresQualResultTypeDescriptorBuilder<R, E> interimBuilder = builder;
		this.resultTypeBuilder = interimBuilder;
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	protected OperationDescriptor<?, E> createOperationDescriptor() {
		this.cachedValue = new OperationDescriptor<Object, E>(this, this.entityNameBuilder);
		return this.cachedValue;
	}

	/** {@inheritDoc} */
	@Override
	protected <R> PresQualResultTypeDescriptorBuilder<R, E> createResultDescriptorBuilder() {
		final PresQualResultTypeDescriptorBuilder<R, E> builder = ResultBuilder.create(this, this.getOperation(), this.getBuilderContext());
		this.resultTypeBuilder = builder;
		return builder;
	}

	/**
	 * Gets detail.
	 * 
	 * @return The detail.
	 */
	protected OperationDetail getDetail() {
		if (CheckUtil.isNull(this.cachedDetail)) {
			this.cachedDetail = this.detailBuilder.build();
		}
		return this.cachedDetail;
	}

	/**
	 * Gets detail.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The detail.
	 */
	protected OperationDetail getDetail(final Class<?> qualifier) {
		OperationDetail result;
		if (this.qualifierDetailBuilder.containsKey(qualifier)) {
			result = this.qualifierDetailBuilder.get(qualifier).build();
		} else {
			result = this.getDetail();
		}
		return result;
	}

	/**
	 * A cache builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class CacheBuilder
			implements Listener<BuildOperationEvent<E>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final BuildOperationEvent<E> event) throws EventException {
			OperationBuilder.LOGGER.debug("Handling build event {}", event);
			for (final Class<?> key : OperationBuilder.this.qualifierDetailBuilder.keySet()) {
				OperationBuilder.this.cachedQualifierValues.put(key, this.createOperation(key));
			}
		}

		/**
		 * Create a property reference.
		 * 
		 * @param key
		 *            The key.
		 * @return The property reference.
		 * @param <R>
		 *            The result type.
		 */
		@SuppressWarnings("unchecked")
		// Derived type.
		private <R> OperationReference<R, E> createOperation(final Class<?> key) {
			final ResultDescriptor<R> resultDescriptor = (ResultDescriptor<R>) OperationBuilder.this.resultTypeBuilder
					.getResultDescriptor(key);
			final OperationDescriptor<R, E> delegate = (OperationDescriptor<R, E>) OperationBuilder.this.getOperationDescriptor();
			final List<ParameterDescriptor<?>> parameters = new ArrayList<ParameterDescriptor<?>>(
					OperationBuilder.this.parameterBuilders.size());
			for (final PresQualParameterDescriptorBuilder<?> parameter : OperationBuilder.this.parameterBuilders) {
				final ParameterDescriptor<?> built = parameter.build();
				final ParameterDescriptor<?> qualifier = parameter.getParameterDescriptor(key);
				if (CheckUtil.isNull(qualifier)) {
					parameters.add(built);
				} else {
					parameters.add(qualifier);
				}
			}
			return new OperationReference<R, E>(resultDescriptor, delegate, OperationBuilder.this.getDetail(key), key, parameters);
		}
	}

	/**
	 * The delegate builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class Delegate
			implements Listener<UpdatedOperationReferenceEvent<E, PresQualBuilderContext>> {

		/** Default constructor. */
		public Delegate() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final UpdatedOperationReferenceEvent<E, PresQualBuilderContext> event) throws EventException {
			OperationBuilder.LOGGER.debug("Handling update operation event {}", event);
			OperationBuilder.this
					.getBuilderContext()
					.getBus()
					.handleEvent(
							new UpdatedPresentationOperationReferenceEvent<E>(OperationBuilder.this, event.getOperation(),
									event.getOperationReference()), OperationBuilder.this);
		}
	}
}
