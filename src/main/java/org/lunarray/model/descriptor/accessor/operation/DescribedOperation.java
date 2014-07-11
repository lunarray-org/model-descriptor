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
package org.lunarray.model.descriptor.accessor.operation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.generics.Member;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;

/**
 * Describes a operation.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DescribedOperation
		extends AbstractOperation
		implements Member {

	/** Validation message. */
	private static final String ANNOTATION_TYPE_NULL = "Annotation type may not be null.";
	/** Operation annotations. */
	private final transient Map<Class<? extends Annotation>, List<Annotation>> annotations;
	/** The entity. */
	private final transient DescribedEntity<?> entityType;
	/** The matching methods. */
	private final transient List<Method> matches;

	/**
	 * Constructs the operation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected DescribedOperation(final OperationBuilder builder) {
		super(builder);
		this.annotations = builder.getAnnotationsBuilder();
		this.entityType = builder.getEntityTypeBuilder();
		this.matches = builder.getMatches();
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
		Validate.notNull(annotationType, DescribedOperation.ANNOTATION_TYPE_NULL);
		List<A> annotation = null;
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

	/** {@inheritDoc} */
	@Override
	public Class<?> getDeclaringType() {
		return this.getOperation().getDeclaringClass();
	}

	/**
	 * Gets the value for the entityType field.
	 * 
	 * @return The value for the entityType field.
	 */
	public DescribedEntity<?> getEntityType() {
		return this.entityType;
	}

	/**
	 * Gets the generic return type.
	 * 
	 * @return The generic return type.
	 */
	public Type getGenericReturnType() {
		return this.getOperation().getGenericReturnType();
	}

	/** {@inheritDoc} */
	@Override
	public Type getGenericType() {
		return this.getGenericReturnType();
	}

	/**
	 * Gets the value for the matches field.
	 * 
	 * @return The value for the matches field.
	 */
	public List<Method> getMatches() {
		return this.matches;
	}

	/**
	 * Tests if the operation has a given annotation.
	 * 
	 * @param annotationType
	 *            The annotation.
	 * @return True if and only if the operation has a given annotation.
	 */
	public boolean isAnnotationPresent(final Class<? extends Annotation> annotationType) {
		Validate.notNull(annotationType, DescribedOperation.ANNOTATION_TYPE_NULL);
		return this.annotations.containsKey(annotationType);
	}
}
