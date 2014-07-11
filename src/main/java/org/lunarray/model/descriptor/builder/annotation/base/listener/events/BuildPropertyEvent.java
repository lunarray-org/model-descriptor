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

import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptor;

/**
 * Propagated if an entity has been built.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class BuildPropertyEvent<P, E> {

	/** The entity. */
	private final transient AnnotationPropertyDescriptor<P, E> property;

	/**
	 * Default constructor.
	 * 
	 * @param property
	 *            The property.
	 */
	public BuildPropertyEvent(final AnnotationPropertyDescriptor<P, E> property) {
		this.property = property;
	}

	/**
	 * Gets the value for the property field.
	 * 
	 * @return The value for the property field.
	 */
	public AnnotationPropertyDescriptor<P, E> getProperty() {
		return this.property;
	}
}
