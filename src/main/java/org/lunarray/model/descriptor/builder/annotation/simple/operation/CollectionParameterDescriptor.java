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
package org.lunarray.model.descriptor.builder.annotation.simple.operation;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractCollectionParameterDescriptor;

/**
 * A collection property descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The property type.
 */
public final class CollectionParameterDescriptor<C, P extends Collection<C>>
		extends AbstractCollectionParameterDescriptor<C, P> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = 6757052821287009150L;

	/**
	 * Constructs the property descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected CollectionParameterDescriptor(final CollectionParameterDescriptorBuilder<C, P> builder) {
		super(builder);
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, CollectionParameterDescriptor.ADAPTER_TYPE_NULL);
		return this.parameterAdapt(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, CollectionParameterDescriptor.ADAPTER_TYPE_NULL);
		return this.parameterAdaptable(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SimpleCollectionParameterDescriptor[\n");
		this.collectionParameterToString(builder);
		builder.append("]");
		return builder.toString();
	}
}