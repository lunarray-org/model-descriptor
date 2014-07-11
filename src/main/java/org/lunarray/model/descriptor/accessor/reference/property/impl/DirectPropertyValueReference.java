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
package org.lunarray.model.descriptor.accessor.reference.property.impl;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.reference.property.PropertyValueReference;

/**
 * An implementation of a property reference through a direct accessor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <C>
 *            The containing entity type.
 */
public final class DirectPropertyValueReference<P, C>
		implements PropertyValueReference<P, C> {

	/** Serial id. */
	private static final long serialVersionUID = -1049805222200154370L;
	/**
	 * The property accessor.
	 */
	private PropertyReference<P, C> propertyAccessor;

	/**
	 * Constructs the property value reference.
	 * 
	 * @param propertyAccessor
	 *            The property accessor. May not be null.
	 */
	public DirectPropertyValueReference(final PropertyReference<P, C> propertyAccessor) {
		Validate.notNull(propertyAccessor, "Property may not be null.");
		this.propertyAccessor = propertyAccessor;
	}

	/**
	 * Gets the value for the propertyAccessor field.
	 * 
	 * @return The value for the propertyAccessor field.
	 */
	public PropertyReference<P, C> getPropertyAccessor() {
		return this.propertyAccessor;
	}

	/** {@inheritDoc} */
	@Override
	public P getValue(final C parentType) throws ValueAccessException {
		return this.propertyAccessor.getValue(parentType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAccessible() {
		return this.propertyAccessor.isAccessible();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMutable() {
		return this.propertyAccessor.isMutable();
	}

	/**
	 * Sets a new value for the propertyAccessor field.
	 * 
	 * @param propertyAccessor
	 *            The new value for the propertyAccessor field.
	 */
	public void setPropertyAccessor(final PropertyReference<P, C> propertyAccessor) {
		this.propertyAccessor = propertyAccessor;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(final C parentType, final P value) throws ValueAccessException {
		this.propertyAccessor.setValue(value, parentType);
	}
}
