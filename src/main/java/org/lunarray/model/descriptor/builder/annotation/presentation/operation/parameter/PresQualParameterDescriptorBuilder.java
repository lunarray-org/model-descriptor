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
package org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter;

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.presentation.PresentationParameterDescriptor;

/**
 * A property descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 */
public interface PresQualParameterDescriptorBuilder<P>
		extends AnnotationParameterDescriptorBuilder<P, PresQualBuilderContext> {

	/**
	 * Gets the detail builder.
	 * 
	 * @return The builder.
	 */
	ParameterDetailBuilder getDetailBuilder();

	/**
	 * Gets the detail.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The detail.
	 */
	ParameterDetailBuilder getDetailBuilder(final Class<?> qualifier);

	/**
	 * Gets the entity type.
	 * 
	 * @return The entity type.
	 */
	DescribedEntity<?> getEntityType();

	/**
	 * Gets the descriptor.
	 * 
	 * @return The descriptor.
	 */
	PresentationParameterDescriptor<P> getParameterDescriptor();

	/**
	 * Gets the descriptor.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The descriptor.
	 */
	PresentationParameterDescriptor<P> getParameterDescriptor(Class<?> qualifier);

	/**
	 * Add builder.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The builder.
	 */
	ParameterDetailBuilder getQualifierBuilder(final Class<?> qualifier);

}
