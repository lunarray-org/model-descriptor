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
package org.lunarray.model.descriptor.builder.annotation.simple.entity;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.entity.AbstractEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.simple.operation.OperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.simple.property.CollectionPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.simple.property.PropertyDescriptorBuilder;

/**
 * An entity descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public final class EntityDescriptorBuilder<E, K extends Serializable>
		extends AbstractEntityDescriptorBuilder<E, K, AnnotationBuilderContext> {

	/**
	 * Creates a builder.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param <K>
	 *            The key type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <E, K extends Serializable> EntityDescriptorBuilder<E, K> create(final AnnotationBuilderContext builderContext) {
		Validate.notNull(builderContext, "Builder context may not be null.");
		return new EntityDescriptorBuilder<E, K>(builderContext);
	}

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	private EntityDescriptorBuilder(final AnnotationBuilderContext builderContext) {
		super(builderContext);
	}

	/** {@inheritDoc} */
	@Override
	protected <C, P extends Collection<C>> CollectionPropertyDescriptorBuilder<C, P, E> createCollectionPropertyBuilder() {
		return CollectionPropertyDescriptorBuilder.create(this.getBuilderContext());
	}

	/** {@inheritDoc} */
	@Override
	protected EntityDescriptor<E, K> createEntityDescriptor() {
		return new EntityDescriptor<E, K>(this);
	}

	@Override
	protected AnnotationOperationDescriptorBuilder<E, AnnotationBuilderContext> createOperationBuilder() {
		return OperationDescriptorBuilder.create(this.getBuilderContext());
	}

	/** {@inheritDoc} */
	@Override
	protected <P> PropertyDescriptorBuilder<P, E> createPropertyBuilder() {
		return PropertyDescriptorBuilder.create(this.getBuilderContext());
	}
}
