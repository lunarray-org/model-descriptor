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
package org.lunarray.model.descriptor.builder.annotation.presentation.property;

import java.util.Collection;

import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationCollectionPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;

/**
 * A property descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <C>
 *            The collection type.
 */
public interface PresQualCollectionPropertyDescriptorBuilder<C, P extends Collection<C>, E>
		extends PresQualPropertyDescriptorBuilder<P, E>, AnnotationCollectionPropertyDescriptorBuilder<C, P, E, PresQualBuilderContext> {

}
