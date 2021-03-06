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
package org.lunarray.model.descriptor.adapter;

/**
 * Indicates the instance is adaptable.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface Adaptable {

	/**
	 * Adapt to a new type.
	 * 
	 * @param <A>
	 *            The new type.
	 * @param adapterType
	 *            The new type. May not be null.
	 * @return The current instance is the new type.
	 */
	<A> A adapt(Class<A> adapterType);

	/**
	 * Tests if adapting is possible to the given type.
	 * 
	 * @param adapterType
	 *            The adapter type. May not be null.
	 * @return True if and only if this node is adaptable to the given
	 *         adapterType.
	 */
	boolean adaptable(Class<?> adapterType);
}
