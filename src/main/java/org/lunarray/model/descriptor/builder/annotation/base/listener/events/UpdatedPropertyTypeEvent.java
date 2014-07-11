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
package org.lunarray.model.descriptor.builder.annotation.base.listener.events;

import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptorBuilder;

/**
 * An event propagated when a property entity type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public final class UpdatedPropertyTypeEvent<P, E, B extends AnnotationBuilderContext> {

	/** The builder. */
	private final transient AnnotationPropertyDescriptorBuilder<P, E, B> builder;
	/** The property. */
	private final transient DescribedProperty<P> property;

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param property
	 *            The property.
	 */
	public UpdatedPropertyTypeEvent(final AnnotationPropertyDescriptorBuilder<P, E, B> builder, final DescribedProperty<P> property) {
		this.builder = builder;
		this.property = property;
	}

	/**
	 * Gets the value for the builder field.
	 * 
	 * @return The value for the builder field.
	 */
	public AnnotationPropertyDescriptorBuilder<P, E, B> getBuilder() {
		return this.builder;
	}

	/**
	 * Gets the value for the property field.
	 * 
	 * @return The value for the property field.
	 */
	public DescribedProperty<P> getProperty() {
		return this.property;
	}
}
