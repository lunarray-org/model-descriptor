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
import java.util.Collection;

import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;

/**
 * Describes an entity dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public interface EntityDictionary<E, K extends Serializable> {

	/**
	 * Gets the entity name this dictionary is for.
	 * 
	 * @return The entity name.
	 */
	String getEntityName();

	/**
	 * Look up the entities.
	 * 
	 * @return The entities.
	 * @throws DictionaryException
	 *             If the entities could not be found.
	 */
	Collection<E> lookup() throws DictionaryException;

	/**
	 * Look up an entity by key.
	 * 
	 * @param key
	 *            The key.
	 * @return The entity.
	 * @throws DictionaryException
	 *             Thrown if the entity could not be found.
	 */
	E lookup(K key) throws DictionaryException;
}
