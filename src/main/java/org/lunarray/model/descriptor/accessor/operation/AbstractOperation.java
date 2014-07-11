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

import java.lang.reflect.Method;

/**
 * Describes a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public abstract class AbstractOperation
		implements Operation {
	/**
	 * Creates a builder.
	 * 
	 * @return The builder.
	 */
	public static OperationBuilder createBuilder() {
		return new OperationBuilder();
	}
	/** The name. */
	private transient String name;

	/** The operation. */
	private transient Method operation;

	/**
	 * /** Default constructor.
	 */
	public AbstractOperation() {
		// Default constructor.
	}

	/**
	 * Default constructor.
	 * 
	 * @param operation
	 *            The prototype operation.
	 */
	protected AbstractOperation(final Operation operation) {
		this.name = operation.getName();
		this.operation = operation.getOperation();
	}

	/**
	 * Constructs the property.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected AbstractOperation(final OperationBuilder builder) {
		this.name = builder.getNameBuilder();
		this.operation = builder.getOperation();
	}

	/**
	 * Gets the value for the name field.
	 * 
	 * @return The value for the name field.
	 */
	@Override
	public final String getName() {
		return this.name;
	}

	/**
	 * Gets the value for the operation field.
	 * 
	 * @return The value for the operation field.
	 */
	@Override
	public final Method getOperation() {
		return this.operation;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<?> getResultType() {
		return this.operation.getReturnType();
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets a new value for the operation field.
	 * 
	 * @param operation
	 *            The new value for the operation field.
	 */
	public final void setOperation(final Method operation) {
		this.operation = operation;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Operation[\n\tName: ").append(this.name);
		builder.append("\n\tResult Type: ").append(this.operation.getReturnType());
		builder.append("\n]");
		return builder.toString();
	}
}
