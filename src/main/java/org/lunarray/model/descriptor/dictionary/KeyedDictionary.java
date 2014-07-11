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

import java.util.Collection;

import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.extension.Extension;

/**
 * Describes a dictionary with a restricting key.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface KeyedDictionary
		extends Extension {

	/**
	 * Gets all entities associated with a given dictionary key.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param dictionaryKey
	 *            The dictionary key
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @return The collection of entities for the given key.
	 */
	<E> Collection<E> lookupAll(String dictionaryKey, EntityDescriptor<E> entityDescriptor);
}
