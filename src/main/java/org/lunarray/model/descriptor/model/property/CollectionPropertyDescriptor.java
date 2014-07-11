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

import java.util.Collection;
import java.util.Iterator;

import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;

/**
 * Describes a collection property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The property type.
 * @param <P>
 *            The collection type.
 * @param <E>
 *            The collection type.
 */
public interface CollectionPropertyDescriptor<C, P extends Collection<C>, E>
		extends PropertyDescriptor<P, E> {

	/**
	 * Adds all values.
	 * 
	 * @see Collection#addAll(Collection)
	 * @param entity
	 *            The entity.
	 * @param collection
	 *            The collection to add.
	 * @return True if the operation modified the collection.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean addAllValues(E entity, Collection<? extends C> collection) throws ValueAccessException;

	/**
	 * Adds a value.
	 * 
	 * @see Collection#add(Object)
	 * @param entity
	 *            The entity.
	 * @param value
	 *            The value.
	 * @return True if the operation modified the collection
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean addValue(E entity, C value) throws ValueAccessException;

	/**
	 * Clear the collection.
	 * 
	 * @see Collection#clear()
	 * @param entity
	 *            The entity.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	void clearValues(E entity) throws ValueAccessException;

	/**
	 * Tests if the collection contains all values.
	 * 
	 * @see Collection#containsAll(Collection)
	 * @param entity
	 *            The entity.
	 * @param collection
	 *            The collection to test with.
	 * @return True if the collection in the entity contains all values.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean containsAllValues(E entity, Collection<?> collection) throws ValueAccessException;

	/**
	 * Tests if the collection contains the value.
	 * 
	 * @see Collection#contains(Object)
	 * @param entity
	 *            The entity.
	 * @param value
	 *            The value to test with.
	 * @return True if the collection in the entity the value.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean containsValue(E entity, C value) throws ValueAccessException;

	/**
	 * Gets the collection type.
	 * 
	 * @return The collection type.
	 */
	Class<C> getCollectionType();

	/**
	 * Tests if the value is empty.
	 * 
	 * @see Collection#isEmpty()
	 * @param entity
	 *            The entity.
	 * @return True if the collection is empty.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean isValueEmpty(E entity) throws ValueAccessException;

	/**
	 * Gets an iterateable value.
	 * 
	 * @see Collection#iterator()
	 * @param entity
	 *            The entity.
	 * @return An iterateable value.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	Iterable<C> iteratable(E entity) throws ValueAccessException;

	/**
	 * Gets an iterator.
	 * 
	 * @see Collection#iterator()
	 * @param entity
	 *            The entity.
	 * @return The iterator.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	Iterator<C> iterator(E entity) throws ValueAccessException;

	/**
	 * Removes all given values.
	 * 
	 * @see Collection#removeAll(Collection)
	 * @param entity
	 *            The entity.
	 * @param collection
	 *            The collection to remove.
	 * @return True if the collection was modified.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean removeAllValues(E entity, Collection<?> collection) throws ValueAccessException;

	/**
	 * Removes a value.
	 * 
	 * @see Collection#remove(Object)
	 * @param entity
	 *            The entity.
	 * @param value
	 *            The value to remove.
	 * @return True if the collection was modified.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean removeValue(E entity, C value) throws ValueAccessException;

	/**
	 * Retains all given values, discards the rest.
	 * 
	 * @see Collection#retainAll(Collection)
	 * @param entity
	 *            The entity.
	 * @param collection
	 *            The collection to retain.
	 * @return True if the collection was modified.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	boolean retainAllValues(E entity, Collection<?> collection) throws ValueAccessException;

	/**
	 * Gets the collection size.
	 * 
	 * @see Collection#size()
	 * @param entity
	 *            The entity.
	 * @return The size.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	int valueSize(E entity) throws ValueAccessException;
}
