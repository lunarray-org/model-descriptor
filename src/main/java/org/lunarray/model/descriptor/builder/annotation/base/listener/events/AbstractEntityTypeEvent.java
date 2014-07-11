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

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;

/**
 * An event propagated when an entity type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <K>
 *            The key type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder context type.
 */
public abstract class AbstractEntityTypeEvent<E, K extends Serializable, B extends AnnotationBuilderContext> {

	/** The builder. */
	private final transient AnnotationEntityDescriptorBuilder<E, K, B> builder;
	/** The property. */
	private final transient DescribedEntity<E> entity;

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param entity
	 *            The entity.
	 */
	protected AbstractEntityTypeEvent(final AnnotationEntityDescriptorBuilder<E, K, B> builder, final DescribedEntity<E> entity) {
		this.builder = builder;
		this.entity = entity;
	}

	/**
	 * Gets the value for the builder field.
	 * 
	 * @return The value for the builder field.
	 */
	public final AnnotationEntityDescriptorBuilder<E, K, B> getBuilder() {
		return this.builder;
	}

	/**
	 * Gets the value for the entity field.
	 * 
	 * @return The value for the entity field.
	 */
	public final DescribedEntity<E> getEntity() {
		return this.entity;
	}
}
