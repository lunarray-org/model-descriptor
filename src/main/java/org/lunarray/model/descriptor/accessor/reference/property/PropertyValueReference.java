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
package org.lunarray.model.descriptor.accessor.reference.property;

import java.io.Serializable;

import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;

/**
 * Describes a value reference for a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <C>
 *            The type of the containing entity.
 */
public interface PropertyValueReference<P, C>
		extends Serializable {

	/**
	 * Gets a value.
	 * 
	 * @param parentType
	 *            The containing entity.
	 * @return The value of the property.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	P getValue(C parentType) throws ValueAccessException;

	/**
	 * Tests whether the property is accessible.
	 * 
	 * @return True if and only if the property is accessible.
	 */
	boolean isAccessible();

	/**
	 * Tests whether the property is mutable.
	 * 
	 * @return True if and only if the property is mutable.
	 */
	boolean isMutable();

	/**
	 * Sets a value.
	 * 
	 * @param parentType
	 *            The containing entity.
	 * @param value
	 *            The new value of the property.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	void setValue(C parentType, P value) throws ValueAccessException;
}
