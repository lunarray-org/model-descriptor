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

import java.io.Serializable;

import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;

/**
 * Propagated if an entity has been built.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder type.
 */
public final class PreBuildEntityEvent<E, K extends Serializable, B extends AnnotationBuilderContext> {

	/** The entity. */
	private final transient AnnotationEntityDescriptorBuilder<E, K, B> entity;

	/**
	 * Default constructor.
	 * 
	 * @param entity
	 *            The entity
	 */
	public PreBuildEntityEvent(final AnnotationEntityDescriptorBuilder<E, K, B> entity) {
		this.entity = entity;
	}

	/**
	 * Gets the value for the entity field.
	 * 
	 * @return The value for the entity field.
	 */
	public AnnotationEntityDescriptorBuilder<E, K, B> getEntity() {
		return this.entity;
	}
}
