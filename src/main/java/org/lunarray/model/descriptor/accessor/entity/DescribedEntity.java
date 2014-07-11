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
public final class DescribedEntity<E> {
	/** Validation message. */
	private static final String ANNOTATION_TYPE_NULL = "Annotation type may not be null.";
	/**
	 * Creates a builder.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @return The builder.
	 */
	public static <E> EntityBuilder<E> createBuilder() {
		return new EntityBuilder<E>();
	}
	/** Property annotations. */
	private final transient Map<Class<? extends Annotation>, List<Annotation>> annotations;

	/** The entity. */
	private final transient Class<E> entityType;

	/**
	 * Constructs the property.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected DescribedEntity(final EntityBuilder<E> builder) {
		this.annotations = builder.getAnnotations();
		this.entityType = builder.getEntityType();
	}

	/**
	 * Gets an annotation.
	 * 
	 * @param <A>
	 *            The annotation type.
	 * @param annotationType
	 *            The annotation type.
	 * @return The annotation, or null.
	 */
	@SuppressWarnings("unchecked")
	public <A extends Annotation> List<A> getAnnotation(final Class<A> annotationType) {
		Validate.notNull(annotationType, DescribedEntity.ANNOTATION_TYPE_NULL);
		List<A> annotation = new LinkedList<A>();
		if (this.annotations.containsKey(annotationType)) {
			annotation = (List<A>) this.annotations.get(annotationType);
		}
		return annotation;
	}

	/**
	 * Gets the value for the annotations field.
	 * 
	 * @return The value for the annotations field.
	 */
	public Map<Class<? extends Annotation>, List<Annotation>> getAnnotations() {
		return this.annotations;
	}

	/**
	 * Gets the value for the entityType field.
	 * 
	 * @return The value for the entityType field.
	 */
	public Class<E> getEntityType() {
		return this.entityType;
	}

	/**
	 * Tests if the property has a given annotation.
	 * 
	 * @param annotationType
	 *            The annotation. May not be null.
	 * @return True if and only if the property has a given annotation.
	 */
	public boolean isAnnotationPresent(final Class<? extends Annotation> annotationType) {
		Validate.notNull(annotationType, DescribedEntity.ANNOTATION_TYPE_NULL);
		return this.annotations.containsKey(annotationType);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Entity[\n\tType: ").append(this.entityType);
		builder.append("\n]");
		return builder.toString();
	}
}
