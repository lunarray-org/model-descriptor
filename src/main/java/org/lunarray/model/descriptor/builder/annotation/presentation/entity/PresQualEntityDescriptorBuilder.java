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
package org.lunarray.model.descriptor.builder.annotation.presentation.entity;

import java.io.Serializable;
import java.util.Map;

import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;

/**
 * A presentation qualifier entity descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            Entity type.
 * @param <K>
 *            Key type.
 */
public interface PresQualEntityDescriptorBuilder<E, K extends Serializable>
		extends AnnotationEntityDescriptorBuilder<E, K, PresQualBuilderContext> {

	/**
	 * Gets the detail builder.
	 * 
	 * @return The builder.
	 */
	EntityDetailBuilder getDetailBuilder();

	/**
	 * Creates a detail builder.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return A detail builder.
	 */
	EntityDetailBuilder getDetailBuilder(Class<?> qualifier);

	/**
	 * Gets the qualifier details.
	 * 
	 * @return The details.
	 */
	Map<Class<?>, ? extends EntityDetailBuilder> getQualifierDetails();
}
