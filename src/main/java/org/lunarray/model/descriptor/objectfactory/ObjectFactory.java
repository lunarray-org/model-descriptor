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
package org.lunarray.model.descriptor.objectfactory;

import org.lunarray.model.descriptor.model.extension.Extension;

/**
 * Describes an object factory extension.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface ObjectFactory
		extends Extension {

	/**
	 * Gets a given instance.
	 * 
	 * @param <O>
	 *            The type.
	 * @param type
	 *            The instance type.
	 * @return An instance of the given type.
	 * @throws InstanceException
	 *             Thrown if the instantiation was unsuccessful.
	 */
	<O> O getInstance(Class<O> type) throws InstanceException;
}
