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
package org.lunarray.model.descriptor.builder.annotation.base.builders.property;

import java.util.Collection;
import java.util.Iterator;

import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.model.property.CollectionPropertyDescriptor;

/**
 * Describes a collection property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The property type.
 * @param <P>
 *            The collection type.
 * @param <E>
 *            The entity type.
 */
public abstract class AbstractCollectionPropertyDescriptor<C, P extends Collection<C>, E>
		extends AbstractPropertyDescriptor<P, E>
		implements CollectionPropertyDescriptor<C, P, E> {

	/** Serial id. */
	private static final long serialVersionUID = -2946495198319629080L;
	/** The collection type. */
	private Class<C> collectionType;

	/**
	 * Constructs the property.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected AbstractCollectionPropertyDescriptor(final AbstractCollectionPropertyDescriptorBuilder<C, P, E, ?> builder) {
		super(builder);
		this.collectionType = builder.getCollectionType();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean addAllValues(final E entity, final Collection<? extends C> collection) throws ValueAccessException {
		return this.getValue(entity).addAll(collection);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean addValue(final E entity, final C value) throws ValueAccessException {
		return this.getValue(entity).add(value);
	}

	/** {@inheritDoc} */
	@Override
	public final void clearValues(final E entity) throws ValueAccessException {
		this.getValue(entity).clear();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean containsAllValues(final E entity, final Collection<?> collection) throws ValueAccessException {
		return this.getValue(entity).containsAll(collection);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean containsValue(final E entity, final C value) throws ValueAccessException {
		return this.getValue(entity).contains(value);
	}

	/** {@inheritDoc} */
	@Override
	public final Class<C> getCollectionType() {
		return this.collectionType;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isValueEmpty(final E entity) throws ValueAccessException {
		return this.getValue(entity).isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public final Iterable<C> iteratable(final E entity) throws ValueAccessException {
		return this.getValue(entity);
	}

	/** {@inheritDoc} */
	@Override
	public final Iterator<C> iterator(final E entity) throws ValueAccessException {
		return this.getValue(entity).iterator();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean removeAllValues(final E entity, final Collection<?> collection) throws ValueAccessException {
		return this.getValue(entity).removeAll(collection);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean removeValue(final E entity, final C value) throws ValueAccessException {
		return this.getValue(entity).remove(value);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean retainAllValues(final E entity, final Collection<?> collection) throws ValueAccessException {
		return this.getValue(entity).retainAll(collection);
	}

	/**
	 * Sets a new value for the collectionType field.
	 * 
	 * @param collectionType
	 *            The new value for the collectionType field.
	 */
	public final void setCollectionType(final Class<C> collectionType) {
		this.collectionType = collectionType;
	}

	/** {@inheritDoc} */
	@Override
	public final int valueSize(final E entity) throws ValueAccessException {
		return this.getValue(entity).size();
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void collectionPropertyToString(final StringBuilder builder) {
		this.propertyToString(builder);
		builder.append("\n\tCollection Type: ").append(this.collectionType);
		builder.append("\n");
	}
}
