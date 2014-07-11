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
package org.lunarray.model.descriptor.dictionary.composite;

import java.io.Serializable;
import java.util.List;

import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;

/**
 * Represents a paginated entity dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public interface PaginatedEntityDictionary<E, K extends Serializable>
		extends EntityDictionary<E, K> {

	/**
	 * Look up entities from row to row + count.
	 * 
	 * @param row
	 *            The start row.
	 * @param count
	 *            The row count.
	 * @return The entities.
	 * @throws DictionaryException
	 *             Thrown if the entities could not be looked up.
	 */
	List<E> lookup(int row, int count) throws DictionaryException;

	/**
	 * Gets the total amount of entities in the dictionary.
	 * 
	 * @return The total amount.
	 * @throws DictionaryException
	 *             Thrown if the entity could not be looked up.
	 */
	int total() throws DictionaryException;
}
