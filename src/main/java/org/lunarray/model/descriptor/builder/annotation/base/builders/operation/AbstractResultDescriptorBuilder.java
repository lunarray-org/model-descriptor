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

import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.AbstractBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedResultTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.result.SetRelationListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.result.SetResultListener;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes a result type descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The context type.
 */
public abstract class AbstractResultDescriptorBuilder<R, E, B extends AnnotationBuilderContext>
		extends AbstractBuilder<B>
		implements AnnotationResultDescriptorBuilder<R, E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResultDescriptorBuilder.class);
	/** The property cardinality. */
	private transient Cardinality cardinalityBuilder;
	/** The operation. */
	private final transient DescribedOperation operation;
	/** Indicates the property is related. */
	private transient RelationType relatedBuilder;
	/** The name of the related entity. */
	private transient String relatedNameBuilder;
	/** The property type. */
	private transient Class<R> resultType;

	/**
	 * Constructs the builder.
	 * 
	 * @param operation
	 *            The operation.
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	public AbstractResultDescriptorBuilder(final DescribedOperation operation, final B builderContext) {
		super(builderContext);
		this.operation = operation;
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListenerBefore(new SetRelationListener<R, E, B>(), this);
		bus.addListenerBefore(new SetResultListener<R, E, B>(), this);
	}

	/** {@inheritDoc} */
	@Override
	public final ResultDescriptor<R> build() {
		return this.createResultDescriptor();
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationResultDescriptorBuilder<R, E, B> cardinality(final Cardinality cardinality) {
		this.cardinalityBuilder = cardinality;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final Cardinality getCardinality() {
		return this.cardinalityBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedOperation getOperation() {
		return this.operation;
	}

	/**
	 * Tests if this property is related.
	 * 
	 * @return True if and only if this property is related.
	 */
	@Override
	public final RelationType getRelationType() {
		return this.relatedBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<R> getResultType() {
		return this.resultType;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationResultDescriptorBuilder<R, E, B> related() {
		this.relatedBuilder = RelationType.CONCRETE;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationResultDescriptorBuilder<R, E, B> relatedName(final String relatedName) {
		this.relatedNameBuilder = relatedName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationResultDescriptorBuilder<R, E, B> relatedReference() {
		this.relatedBuilder = RelationType.REFERENCE;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	/* Deduced value. */
	public final AnnotationResultDescriptorBuilder<R, E, B> type(final Class<?> type) {
		this.resultType = (Class<R>) type;
		try {
			this.getBuilderContext().getBus().handleEvent(new UpdatedResultTypeEvent<R, E, B>(this, this.operation), this);
		} catch (final EventException e) {
			AbstractResultDescriptorBuilder.LOGGER.warn("Could not process update operation event.", e);
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationResultDescriptorBuilder<R, E, B> unrelated() {
		this.relatedBuilder = null;
		return this;
	}

	/**
	 * Create a result descriptor.
	 * 
	 * @return A result descriptor.
	 */
	protected abstract ResultDescriptor<R> createResultDescriptor();

	/**
	 * Gets the related name.
	 * 
	 * @return The related name.
	 */
	protected final String getRelatedName() {
		return this.relatedNameBuilder;
	}
}
