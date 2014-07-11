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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;

/**
 * Describes a parameter.
 * 
 * @param <P>
 *            The parameter type.
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ParameterBuilder<P> {
	/** The annotations. */
	private transient Map<Class<? extends Annotation>, List<Annotation>> annotationsBuilder;
	/** The entity type. */
	private transient Class<?> entityTypeBuilder;
	/** The parameter index. */
	private transient Type genericTypeBuilder;
	/** The index. */
	private transient int indexBuilder = -1;
	/** The operation. */
	private transient DescribedOperation operationBuilder;
	/** The parameter type. */
	private transient Class<P> parameterTypeBuilder;

	/**
	 * The
	 * 
	 * /** Default constructor.
	 */
	protected ParameterBuilder() {
		// Default constructor.
		this.annotationsBuilder = new HashMap<Class<? extends Annotation>, List<Annotation>>();
	}

	/**
	 * Adds an annotation.
	 * 
	 * @param annotation
	 *            The annotation.
	 * @return The builder.
	 */
	public ParameterBuilder<P> addAnnotation(final Annotation annotation) {
		final Class<? extends Annotation> type = annotation.annotationType();
		if (!this.annotationsBuilder.containsKey(type)) {
			this.annotationsBuilder.put(type, new LinkedList<Annotation>());
		}
		this.annotationsBuilder.get(type).add(annotation);
		return this;
	}

	/**
	 * Build the parameter.
	 * 
	 * @return The parameter.
	 */
	public PersistentParameter<P> build() {
		this.validate();
		return new PersistentParameter<P>(this);
	}

	/**
	 * Build the parameter.
	 * 
	 * @return The parameter.
	 */
	public DescribedParameter<P> buildDescribed() {
		this.validate();
		return new DescribedParameter<P>(this);
	}

	/**
	 * Fills with an existing parameter.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @return The builder.
	 */
	public ParameterBuilder<P> describedParameter(final DescribedParameter<P> parameter) {
		this.annotationsBuilder = parameter.getAnnotations();
		this.genericTypeBuilder = parameter.getGenericType();
		this.indexBuilder = parameter.getIndex();
		this.parameterTypeBuilder = parameter.getType();
		return this;
	}

	/**
	 * Sets a new value for the entityType field.
	 * 
	 * @param entityTypeBuilder
	 *            The new value for the entityType field.
	 * @return The builder.
	 */
	public ParameterBuilder<P> entityType(final Class<?> entityTypeBuilder) {
		this.entityTypeBuilder = entityTypeBuilder;
		return this;
	}

	/**
	 * Sets a new value for the genericType field.
	 * 
	 * @param genericType
	 *            The new value for the genericType field.
	 * @return The builder.
	 */
	public ParameterBuilder<P> genericType(final Type genericType) {
		this.genericTypeBuilder = genericType;
		return this;
	}

	/**
	 * Sets a new value for the index field.
	 * 
	 * @param index
	 *            The new value for the index field. Must be set and positive
	 * @return The builder.
	 */
	public ParameterBuilder<P> index(final int index) {
		this.indexBuilder = index;
		return this;
	}

	/**
	 * Sets a new value for the operation field.
	 * 
	 * @param operation
	 *            The new value for the operation field.
	 * @return The builder.
	 */
	public ParameterBuilder<P> operation(final DescribedOperation operation) {
		this.operationBuilder = operation;
		return this;
	}

	/**
	 * Sets a new value for the parameterType field.
	 * 
	 * @param parameterType
	 *            The new value for the parameterType field. Must be set.
	 * @return The builder.
	 */
	public ParameterBuilder<P> parameterType(final Class<P> parameterType) {
		this.parameterTypeBuilder = parameterType;
		return this;
	}

	/**
	 * Validate all required fields are set.
	 */
	private void validate() {
		Validate.isTrue(CheckUtil.checkPositive(this.indexBuilder), "Index must be positive.");
		Validate.notNull(this.parameterTypeBuilder, "Parameter type must be set.");
		Validate.notNull(this.operationBuilder, "Operation must be set.");
		Validate.notNull(this.genericTypeBuilder, "Generic type must be set.");
	}

	/**
	 * Gets the value for the annotationsBuilder field.
	 * 
	 * @return The value for the annotationsBuilder field.
	 */
	protected Map<Class<? extends Annotation>, List<Annotation>> getAnnotations() {
		return this.annotationsBuilder;
	}

	/**
	 * Gets the value for the entityType field.
	 * 
	 * @return The value for the entityType field.
	 */
	protected Class<?> getEntityType() {
		return this.entityTypeBuilder;
	}

	/**
	 * Gets the value for the genericType field.
	 * 
	 * @return The value for the genericType field.
	 */
	protected Type getGenericType() {
		return this.genericTypeBuilder;
	}

	/**
	 * Gets the value for the index field.
	 * 
	 * @return The value for the index field.
	 */
	protected int getIndex() {
		return this.indexBuilder;
	}

	/**
	 * Gets the value for the operation field.
	 * 
	 * @return The value for the operation field.
	 */
	protected DescribedOperation getOperation() {
		return this.operationBuilder;
	}

	/**
	 * Gets the value for the parameterType field.
	 * 
	 * @return The value for the parameterType field.
	 */
	protected Class<P> getParameterType() {
		return this.parameterTypeBuilder;
	}
}
