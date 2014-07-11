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
package org.lunarray.model.descriptor.model.property;

import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;

/**
 * Describes a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public interface PropertyDescriptor<P, E>
		extends MemberDescriptor<E> {

	/**
	 * Gets an extension.
	 * 
	 * @param <X>
	 *            The extension type.
	 * @param extensionType
	 *            The extension type.
	 * @return The extension.
	 */
	<X extends PropertyExtension<P, E>> X extension(Class<X> extensionType);

	/**
	 * Gets the property cardinality.
	 * 
	 * @return The cardinality.
	 */
	Cardinality getCardinality();

	/**
	 * Gets the property type.
	 * 
	 * @return The property type.
	 */
	Class<P> getPropertyType();

	/**
	 * Gets a value for this property.
	 * 
	 * @param entity
	 *            The entity.
	 * @return The value.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	P getValue(E entity) throws ValueAccessException;

	/**
	 * Tests if the given value is assignable as a property.
	 * 
	 * @param value
	 *            The value.
	 * @return True if and only if the value is assignable.
	 */
	boolean isAssignable(Object value);

	/**
	 * Tests whether the property is immutable.
	 * 
	 * @return True if and only if the property is immutable.
	 */
	boolean isImmutable();

	/**
	 * Tests whether the property is a relation.
	 * 
	 * @return True if the property is relation to another entity, false if not.
	 */
	boolean isRelation();

	/**
	 * Sets a value for the property.
	 * 
	 * @param entity
	 *            The entity.
	 * @param value
	 *            The value to set.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	void setValue(E entity, P value) throws ValueAccessException;
}
