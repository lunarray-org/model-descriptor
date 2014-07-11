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
package org.lunarray.model.descriptor.accessor.entity;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * Describes an entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class EntityBuilder<E> {
	/** Property annotations. */
	private final transient Map<Class<? extends Annotation>, List<Annotation>> annotationsBuilder;
	/** The entity. */
	private transient Class<E> entityTypeBuilder;

	/**
	 * Constructs the property.
	 */
	protected EntityBuilder() {
		this.annotationsBuilder = new HashMap<Class<? extends Annotation>, List<Annotation>>();
	}

	/**
	 * A new annotation.
	 * 
	 * @param annotation
	 *            Adds an annotation.
	 * @return The builder.
	 */
	public EntityBuilder<E> addAnnotation(final Annotation annotation) {
		final Class<? extends Annotation> type = annotation.annotationType();
		if (!this.annotationsBuilder.containsKey(type)) {
			this.annotationsBuilder.put(type, new LinkedList<Annotation>());
		}
		this.annotationsBuilder.get(type).add(annotation);
		return this;
	}

	/**
	 * Builds the entity.
	 * 
	 * @return The entity.
	 */
	public DescribedEntity<E> build() {
		Validate.notNull(this.entityTypeBuilder, "Entity type must be set.");
		return new DescribedEntity<E>(this);
	}

	/**
	 * Sets a new value for the entityType field.
	 * 
	 * @param entityType
	 *            The new value for the entityType field. Must be set.
	 * @return The builder.
	 */
	public EntityBuilder<E> entityType(final Class<E> entityType) {
		this.entityTypeBuilder = entityType;
		return this;
	}

	/**
	 * Gets the value for the annotations field.
	 * 
	 * @return The value for the annotations field.
	 */
	protected Map<Class<? extends Annotation>, List<Annotation>> getAnnotations() {
		return this.annotationsBuilder;
	}

	/**
	 * Gets the value for the entityType field.
	 * 
	 * @return The value for the entityType field.
	 */
	protected Class<E> getEntityType() {
		return this.entityTypeBuilder;
	}
}
