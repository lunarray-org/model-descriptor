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
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.AbstractBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildParameterEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedParameterEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.parameter.SetParameterListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.parameter.SetRelationListener;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes a parameter descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 * @param <B>
 *            The context type.
 */
public abstract class AbstractParameterDescriptorBuilder<P, B extends AnnotationBuilderContext>
		extends AbstractBuilder<B>
		implements AnnotationParameterDescriptorBuilder<P, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractParameterDescriptorBuilder.class);
	/** The property cardinality. */
	private transient Cardinality cardinalityBuilder;
	/** The index. */
	private transient int indexBuilder;
	/** The descriptor. */
	private transient DescribedParameter<P> parameterBuilder;
	/** Indicates the property is related. */
	private transient RelationType relatedBuilder;
	/** The name of the related entity. */
	private transient String relatedNameBuilder;
	/** The parameter type. */
	private transient Class<P> typeBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	public AbstractParameterDescriptorBuilder(final B builderContext) {
		super(builderContext);
		this.indexBuilder = -1;
		final Bus bus = builderContext.getBus();
		bus.addListener(new SetParameterListener<P, B>(), this);
		bus.addListener(new SetRelationListener<P, B>(), this);
	}

	/** {@inheritDoc} */
	@Override
	public final ParameterDescriptor<P> build() {
		final ParameterDescriptor<P> parameterDescriptor = this.createParameterDescriptor();
		try {
			final B ctx = this.getBuilderContext();
			ctx.getBus().handleEvent(new BuildParameterEvent<P>(parameterDescriptor), this);
		} catch (final EventException e) {
			AbstractParameterDescriptorBuilder.LOGGER.warn("Could not process build event.", e);
		}
		return parameterDescriptor;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationParameterDescriptorBuilder<P, B> cardinality(final Cardinality cardinality) {
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
	public final int getIndex() {
		return this.indexBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedParameter<P> getParameter() {
		return this.parameterBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<P> getParameterType() {
		return this.typeBuilder;
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
	public final AnnotationParameterDescriptorBuilder<P, B> index(final int index) {
		this.indexBuilder = index;
		return this;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Coerse.
	@Override
	public final AnnotationParameterDescriptorBuilder<P, B> parameter(final DescribedParameter<?> parameter) {
		this.parameterBuilder = (DescribedParameter<P>) parameter;
		try {
			final B ctx = this.getBuilderContext();
			ctx.getBus().handleEvent(new UpdatedParameterEvent<P, B>(this, this.parameterBuilder), this);
		} catch (final EventException e) {
			AbstractParameterDescriptorBuilder.LOGGER.warn("Could not process parameter event.", e);
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationParameterDescriptorBuilder<P, B> related() {
		this.relatedBuilder = RelationType.CONCRETE;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationParameterDescriptorBuilder<P, B> relatedName(final String relatedName) {
		this.relatedNameBuilder = relatedName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationParameterDescriptorBuilder<P, B> relatedReference() {
		this.relatedBuilder = RelationType.REFERENCE;
		return this;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Coerse.
	@Override
	public final AnnotationParameterDescriptorBuilder<P, B> type(final Class<?> type) {
		this.typeBuilder = (Class<P>) type;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationParameterDescriptorBuilder<P, B> unrelated() {
		this.relatedBuilder = null;
		return this;
	}

	/**
	 * Create a parameter descriptor.
	 * 
	 * @return A parameter descriptor.
	 */
	protected abstract ParameterDescriptor<P> createParameterDescriptor();

	/**
	 * Gets the related name.
	 * 
	 * @return The related name.
	 */
	protected final String getRelatedName() {
		return this.relatedNameBuilder;
	}
}
