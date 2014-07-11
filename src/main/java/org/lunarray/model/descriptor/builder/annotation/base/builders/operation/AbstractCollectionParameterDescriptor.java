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
package org.lunarray.model.descriptor.builder.annotation.base.builders.operation;

import java.util.Collection;

import org.lunarray.model.descriptor.model.operation.parameters.CollectionParameterDescriptor;

/**
 * Describes a collection property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The property type.
 * @param <P>
 *            The collection type.
 */
public abstract class AbstractCollectionParameterDescriptor<C, P extends Collection<C>>
		extends AbstractParameterDescriptor<P>
		implements CollectionParameterDescriptor<C, P> {

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
	protected AbstractCollectionParameterDescriptor(final AbstractCollectionParameterDescriptorBuilder<C, P, ?> builder) {
		super(builder);
		this.collectionType = builder.getCollectionType();
	}

	/** {@inheritDoc} */
	@Override
	public final Class<C> getCollectionType() {
		return this.collectionType;
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

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void collectionParameterToString(final StringBuilder builder) {
		this.parameterToString(builder);
		builder.append("\tCollection Type: ").append(this.collectionType);
		builder.append("\n");
	}
}
