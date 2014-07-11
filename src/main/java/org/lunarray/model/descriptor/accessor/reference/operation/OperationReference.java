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
package org.lunarray.model.descriptor.accessor.reference.operation;

import java.io.Serializable;

import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.operation.Operation;

/**
 * Describes a operation reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The type of the containing entity.
 */
public interface OperationReference<C>
		extends Serializable {

	/**
	 * Executes a operation.
	 * 
	 * @param parentType
	 *            The containing entity.
	 * @param parameters
	 *            The parameters.
	 * @return The operation result.
	 * @throws ValueAccessException
	 *             Thrown if the operation could not be accessed..
	 */
	Object execute(C parentType, Object[] parameters) throws ValueAccessException;

	/**
	 * Get referenced operation.
	 * 
	 * @return The referenced operation.
	 */
	Operation getReferencedOperation();
}
