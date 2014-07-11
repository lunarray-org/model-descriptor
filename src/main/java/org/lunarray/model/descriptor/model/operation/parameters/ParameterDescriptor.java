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
package org.lunarray.model.descriptor.model.operation.parameters;

import java.io.Serializable;

import org.lunarray.model.descriptor.adapter.Adaptable;
import org.lunarray.model.descriptor.model.member.Cardinality;

/**
 * Describes a parameter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The type.
 */
public interface ParameterDescriptor<P>
		extends Adaptable, Serializable {

	/**
	 * Gets the cardinality.
	 * 
	 * @return The cardinality.
	 */
	Cardinality getCardinality();

	/**
	 * Gets the parameter index.
	 * 
	 * @return The parameter index.
	 */
	int getIndex();

	/**
	 * Gets the parameter type.
	 * 
	 * @return The parameter type.
	 */
	Class<P> getType();

	/**
	 * Tests if the given value is assignable as a parameter.
	 * 
	 * @param value
	 *            The value.
	 * @return True if and only if the value is assignable.
	 */
	boolean isAssignable(Object value);

	/**
	 * Tests whether the property is relation.
	 * 
	 * @return True if the property is relation to another entity, false if not.
	 */
	boolean isRelation();

}
