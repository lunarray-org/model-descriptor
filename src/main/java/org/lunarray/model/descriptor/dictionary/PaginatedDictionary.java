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
package org.lunarray.model.descriptor.dictionary;

import java.util.List;

import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;

/**
 * Describes a paginated dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PaginatedDictionary
		extends Dictionary {

	/**
	 * Look up a paginated entity set.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param entityDescriptor
	 *            The entity descriptor. May not be null.
	 * @param row
	 *            The start row. Must be 0 or a positive value.
	 * @param count
	 *            The count. Must be a positive value.
	 * @return The entities from row untill row + count.
	 * @throws DictionaryException
	 *             Thrown if the lookup could not be executed.
	 */
	<E> List<E> lookupPaginated(EntityDescriptor<E> entityDescriptor, int row, int count) throws DictionaryException;

	/**
	 * Lookup the total amount of entities.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param entityDescriptor
	 *            The entity descriptor. May not be null.
	 * @return The total count.
	 * @throws DictionaryException
	 *             Thrown if the totals could not be found.
	 */
	<E> int lookupTotals(EntityDescriptor<E> entityDescriptor) throws DictionaryException;
}
