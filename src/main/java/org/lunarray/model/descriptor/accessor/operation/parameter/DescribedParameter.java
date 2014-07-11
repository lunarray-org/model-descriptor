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
package org.lunarray.model.descriptor.accessor.operation.parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.generics.Member;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;

/**
 * Describes a parameter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter
 */
public final class DescribedParameter<P>
		extends AbstractParameter<P>
		implements Member {

	/** Validation message. */
	private static final String ANNOTATION_TYPE_NULL = "Annotation type may not be null.";
	/**
	 * Creates a builder.
	 * 
	 * @return A builder.
	 * @param <P>
	 *            The parameter type.
	 */
	public static <P> ParameterBuilder<P> createBuilder() {
		return new ParameterBuilder<P>();
	}
	/** The annotations. */
	private final transient Map<Class<? extends Annotation>, List<Annotation>> annotations;
	/** The entity type. */
	private final transient Class<?> entityType;
	/** The generic type. */
	private final transient Type genericType;

	/** The operation. */
	private final transient DescribedOperation operation;

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 */
	public DescribedParameter(final ParameterBuilder<P> builder) {
		super(builder);
		this.genericType = builder.getGenericType();
		this.annotations = builder.getAnnotations();
		this.entityType = builder.getEntityType();
		this.operation = builder.getOperation();
	}

	/**
	 * Gets an annotation.
	 * 
	 * @param <A>
	 *            The annotation type.
	 * @param annotationType
	 *            The annotation type. May not be null.
	 * @return The annotation, or null.
	 */
	@SuppressWarnings("unchecked")
	public <A extends Annotation> List<A> getAnnotation(final Class<A> annotationType) {
		Validate.notNull(annotationType, DescribedParameter.ANNOTATION_TYPE_NULL);
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
		return this.entityType;
	}

	/**
	 * Gets the value for the genericType field.
	 * 
	 * @return The value for the genericType field.
	 */
	@Override
	public Type getGenericType() {
		return this.genericType;
	}

	/**
	 * Gets the value for the operation field.
	 * 
	 * @return The value for the operation field.
	 */
	public DescribedOperation getOperation() {
		return this.operation;
	}

	/**
	 * Tests if the operation has a given annotation.
	 * 
	 * @param annotationType
	 *            The annotation. May not be null.
	 * @return True if and only if the operation has a given annotation.
	 */
	public boolean isAnnotationPresent(final Class<? extends Annotation> annotationType) {
		Validate.notNull(annotationType, DescribedParameter.ANNOTATION_TYPE_NULL);
		return this.annotations.containsKey(annotationType);
	}
}
