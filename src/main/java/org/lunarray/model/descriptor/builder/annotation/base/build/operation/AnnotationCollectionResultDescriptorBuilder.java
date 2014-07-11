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
package org.lunarray.model.descriptor.builder.annotation.base.build.operation;

import java.util.Collection;

import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;

/**
 * Describes an annotation builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 * @param <C>
 *            The collection type.
 * @param <B>
 *            The builder type.
 */
public interface AnnotationCollectionResultDescriptorBuilder<C, R extends Collection<C>, E, B extends AnnotationBuilderContext>
		extends AnnotationResultDescriptorBuilder<R, E, B> {

	/**
	 * Sets the collection type.
	 * 
	 * @param collectionType
	 *            The type.
	 * @return The builder.
	 */
	AnnotationCollectionResultDescriptorBuilder<C, R, E, B> collectionType(final Class<?> collectionType);

	/**
	 * Gets the collection type.
	 * 
	 * @return The collection type.
	 */
	Class<C> getCollectionType();
}
