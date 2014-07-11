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
package org.lunarray.model.descriptor.accessor.reference.operation.impl;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.operation.Operation;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.accessor.reference.property.PropertyValueReference;

/**
 * The operation reference over a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The containing type.
 * @param <T>
 *            The intermediate target type.
 */
public final class PropertyOperationReference<C, T>
		implements OperationReference<C> {

	/** The serial id. */
	private static final long serialVersionUID = -968088056098742475L;
	/** The operation reference. */
	private OperationReference<T> operationReference;
	/** The property value reference. */
	private PropertyValueReference<T, C> propertyValueReference;

	/**
	 * Default constructor.
	 * 
	 * @param operationReference
	 *            The operation reference. May not be null.
	 * @param propertyValueReference
	 *            The property value reference. May not be null.
	 */
	public PropertyOperationReference(final OperationReference<T> operationReference,
			final PropertyValueReference<T, C> propertyValueReference) {
		Validate.notNull(operationReference, "Operation reference may not be null.");
		Validate.notNull(propertyValueReference, "Property reference may not be null.");
		this.operationReference = operationReference;
		this.propertyValueReference = propertyValueReference;
	}

	/** {@inheritDoc} */
	@Override
	public Object execute(final C parentType, final Object[] parameters) throws ValueAccessException {
		Validate.notNull(parentType, "Parent value may not be null for embedded operation.");
		return this.operationReference.execute(this.propertyValueReference.getValue(parentType), parameters);
	}

	/**
	 * Gets the value for the operationReference field.
	 * 
	 * @return The value for the operationReference field.
	 */
	public OperationReference<T> getOperationReference() {
		return this.operationReference;
	}

	/**
	 * Gets the value for the propertyValueReference field.
	 * 
	 * @return The value for the propertyValueReference field.
	 */
	public PropertyValueReference<T, C> getPropertyValueReference() {
		return this.propertyValueReference;
	}

	/** {@inheritDoc} */
	@Override
	public Operation getReferencedOperation() {
		return this.operationReference.getReferencedOperation();
	}

	/**
	 * Sets a new value for the operationReference field.
	 * 
	 * @param operationReference
	 *            The new value for the operationReference field.
	 */
	public void setOperationReference(final DirectOperationReference<T> operationReference) {
		this.operationReference = operationReference;
	}

	/**
	 * Sets a new value for the propertyValueReference field.
	 * 
	 * @param propertyValueReference
	 *            The new value for the propertyValueReference field.
	 */
	public void setPropertyValueReference(final PropertyValueReference<T, C> propertyValueReference) {
		this.propertyValueReference = propertyValueReference;
	}
}
