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
package org.lunarray.model.descriptor.builder.annotation.simple.property;

import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptor;
import org.lunarray.model.descriptor.builder.annotation.base.builders.property.AbstractPropertyDescriptorBuilder;

/**
 * Property descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class PropertyDescriptorBuilder<P, E>
		extends AbstractPropertyDescriptorBuilder<P, E, AnnotationBuilderContext> {

	/**
	 * Creates a builder.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <P, E> PropertyDescriptorBuilder<P, E> create(final AnnotationBuilderContext builderContext) {
		return new PropertyDescriptorBuilder<P, E>(builderContext);
	}

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context.
	 */
	private PropertyDescriptorBuilder(final AnnotationBuilderContext builderContext) {
		super(builderContext);
	}

	/** {@inheritDoc} */
	@Override
	public AnnotationPropertyDescriptor<P, E> createPropertyDescriptor() {
		return new PropertyDescriptor<P, E>(this);
	}
}
