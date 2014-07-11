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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;

/**
 * Describes a operation.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class OperationBuilder {
	/** The annotations. */
	private transient Map<Class<? extends Annotation>, List<Annotation>> annotationsBuilder;
	/** The entity. */
	private transient DescribedEntity<?> entityTypeBuilder;
	/** The matches. */
	private final transient List<Method> matches;
	/** The name. */
	private transient String nameBuilder;
	/** The operation. */
	private transient Method operationMethod;

	/**
	 * Default constructor.
	 */
	protected OperationBuilder() {
		// Default constructor.
		this.annotationsBuilder = new HashMap<Class<? extends Annotation>, List<Annotation>>();
		this.matches = new LinkedList<Method>();
	}

	/**
	 * Adds an annotation.
	 * 
	 * @param annotation
	 *            The annotation. May not be null.
	 * @return The builder.
	 */
	public OperationBuilder addAnnotation(final Annotation annotation) {
		Validate.notNull(annotation, "Annotation may not be null.");
		final Class<? extends Annotation> type = annotation.annotationType();
		if (!this.annotationsBuilder.containsKey(type)) {
			this.annotationsBuilder.put(type, new LinkedList<Annotation>());
		}
		this.annotationsBuilder.get(type).add(annotation);
		return this;
	}

	/**
	 * Adds a matching method.
	 * 
	 * @param method
	 *            The method.
	 * @return The builder.
	 */
	public OperationBuilder addMatch(final Method method) {
		this.matches.add(method);
		return this;
	}

	/**
	 * Build the operation.
	 * 
	 * @return The operation.
	 */
	public PersistentOperation build() {
		this.validate();
		return new PersistentOperation(this);
	}

	/**
	 * Build the operation.
	 * 
	 * @return The operation.
	 */
	public DescribedOperation buildDescribed() {
		this.validate();
		return new DescribedOperation(this);
	}

	/**
	 * Fills with an existing operation.
	 * 
	 * @param operation
	 *            The operation.
	 * @return The builder.
	 */
	public OperationBuilder describedOperation(final DescribedOperation operation) {
		this.annotationsBuilder = operation.getAnnotations();
		this.entityTypeBuilder = operation.getEntityType();
		this.operationMethod = operation.getOperation();
		this.nameBuilder = operation.getName();
		return this;
	}

	/**
	 * Sets a new value for the entityTypeBuilder field.
	 * 
	 * @param entityTypeBuilder
	 *            The new value for the entityTypeBuilder field. Must be set.
	 * @return The builder.
	 */
	public OperationBuilder entity(final DescribedEntity<?> entityTypeBuilder) {
		this.entityTypeBuilder = entityTypeBuilder;
		return this;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field. Must be set.
	 * @return The builder.
	 */
	public OperationBuilder name(final String name) {
		this.nameBuilder = name;
		return this;
	}

	/**
	 * Sets a new value for the operationBuilder field.
	 * 
	 * @param operationBuilder
	 *            The new value for the operationBuilder field. Must be set.
	 * @return The builder.
	 */
	public OperationBuilder operation(final Method operationBuilder) {
		this.operationMethod = operationBuilder;
		return this;
	}

	/**
	 * Validates all required fields are set.
	 */
	private void validate() {
		Validate.notNull(this.operationMethod, "Operation method must be set.");
		Validate.notNull(this.nameBuilder, "Name");
		Validate.notNull(this.entityTypeBuilder, "Entity type must be set.");
	}

	/**
	 * Gets the value for the annotationsBuilder field.
	 * 
	 * @return The value for the annotationsBuilder field.
	 */
	protected Map<Class<? extends Annotation>, List<Annotation>> getAnnotationsBuilder() {
		return this.annotationsBuilder;
	}

	/**
	 * Gets the value for the entityTypeBuilder field.
	 * 
	 * @return The value for the entityTypeBuilder field.
	 */
	protected DescribedEntity<?> getEntityTypeBuilder() {
		return this.entityTypeBuilder;
	}

	/**
	 * Gets the value for the matches field.
	 * 
	 * @return The value for the matches field.
	 */
	protected List<Method> getMatches() {
		return this.matches;
	}

	/**
	 * Gets the value for the nameBuilder field.
	 * 
	 * @return The value for the nameBuilder field.
	 */
	protected String getNameBuilder() {
		return this.nameBuilder;
	}

	/**
	 * Gets the value for the operationBuilder field.
	 * 
	 * @return The value for the operationBuilder field.
	 */
	protected Method getOperation() {
		return this.operationMethod;
	}
}
