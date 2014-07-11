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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.property;

import java.util.Collection;
import java.util.Iterator;

import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualCollectionPropertyDescriptor;

/**
 * A collection property reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class CollectionPropertyReference<C, P extends Collection<C>, E>
		extends AbstractPropertyReference<P, E>
		implements PresQualCollectionPropertyDescriptor<C, P, E> {

	/** Serial id. */
	private static final long serialVersionUID = 2147568589718828414L;
	/** The property descriptor. */
	private PresQualCollectionPropertyDescriptor<C, P, E> propertyDescriptor;

	/**
	 * Constructs the reference.
	 * 
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param propertyDetail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public CollectionPropertyReference(final PresQualCollectionPropertyDescriptor<C, P, E> delegate, final PropertyDetail propertyDetail,
			final Class<?> qualifier) {
		super(delegate, propertyDetail, qualifier);
		this.propertyDescriptor = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAllValues(final E entity, final Collection<? extends C> collection) throws ValueAccessException {
		return this.propertyDescriptor.addAllValues(entity, collection);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addValue(final E entity, final C value) throws ValueAccessException {
		return this.propertyDescriptor.addValue(entity, value);
	}

	/** {@inheritDoc} */
	@Override
	public void clearValues(final E entity) throws ValueAccessException {
		this.propertyDescriptor.clearValues(entity);
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsAllValues(final E entity, final Collection<?> collection) throws ValueAccessException {
		return this.propertyDescriptor.containsAllValues(entity, collection);
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsValue(final E entity, final C value) throws ValueAccessException {
		return this.propertyDescriptor.containsValue(entity, value);
	}

	/** {@inheritDoc} */
	@Override
	public Class<C> getCollectionType() {
		return this.propertyDescriptor.getCollectionType();
	}

	/**
	 * Gets the value for the propertyDescriptor field.
	 * 
	 * @return The value for the propertyDescriptor field.
	 */
	public PresQualCollectionPropertyDescriptor<C, P, E> getPropertyDescriptor() {
		return this.propertyDescriptor;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isValueEmpty(final E entity) throws ValueAccessException {
		return this.propertyDescriptor.isValueEmpty(entity);
	}

	/** {@inheritDoc} */
	@Override
	public Iterable<C> iteratable(final E entity) throws ValueAccessException {
		return this.propertyDescriptor.iteratable(entity);
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<C> iterator(final E entity) throws ValueAccessException {
		return this.propertyDescriptor.iterator(entity);
	}

	/** {@inheritDoc} */
	@Override
	public boolean removeAllValues(final E entity, final Collection<?> collection) throws ValueAccessException {
		return this.propertyDescriptor.removeAllValues(entity, collection);
	}

	/** {@inheritDoc} */
	@Override
	public boolean removeValue(final E entity, final C value) throws ValueAccessException {
		return this.propertyDescriptor.removeValue(entity, value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean retainAllValues(final E entity, final Collection<?> collection) throws ValueAccessException {
		return this.propertyDescriptor.retainAllValues(entity, collection);
	}

	/**
	 * Sets a new value for the propertyDescriptor field.
	 * 
	 * @param propertyDescriptor
	 *            The new value for the propertyDescriptor field.
	 */
	public void setPropertyDescriptor(final PresQualCollectionPropertyDescriptor<C, P, E> propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationCollectionPropertyReference[\n");
		super.propertyToString(builder);
		builder.append("]");
		return builder.toString();
	}

	/** {@inheritDoc} */
	@Override
	public int valueSize(final E entity) throws ValueAccessException {
		return this.propertyDescriptor.valueSize(entity);
	}
}
