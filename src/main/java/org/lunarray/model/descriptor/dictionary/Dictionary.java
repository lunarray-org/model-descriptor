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

import java.io.Serializable;
import java.util.Collection;

import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.model.extension.Extension;

/**
 * The dictionary interface.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface Dictionary
		extends Extension {

	/**
	 * Gets all entities of a given type.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param entityDescriptor
	 *            Entity descriptor. May not be null.
	 * @return The collection of entities.
	 * @throws DictionaryException
	 *             Thrown if the entities could not be looked up.
	 */
	<E> Collection<E> lookup(EntityDescriptor<E> entityDescriptor) throws DictionaryException;

	/**
	 * Look up an entity with a given key.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param <K>
	 *            The key type.
	 * @param entityDescriptor
	 *            The entity descriptor. May not be null.
	 * @param key
	 *            The key. May not be null.
	 * @return The entity.
	 * @throws DictionaryException
	 *             Thrown if the entity could not be looked up.
	 */
	<E, K extends Serializable> E lookup(KeyedEntityDescriptor<E, K> entityDescriptor, K key) throws DictionaryException;
}
