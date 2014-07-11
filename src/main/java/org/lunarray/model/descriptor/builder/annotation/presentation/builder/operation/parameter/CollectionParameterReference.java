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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.parameter;

import java.util.Collection;

import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualCollectionParameterDescriptor;

/**
 * A collection parameter reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The parameter type.
 */
public final class CollectionParameterReference<C, P extends Collection<C>>
		extends AbstractParameterReference<P>
		implements PresQualCollectionParameterDescriptor<C, P> {

	/** Serial id. */
	private static final long serialVersionUID = 2147568589718828414L;
	/** The parameter descriptor. */
	private PresQualCollectionParameterDescriptor<C, P> parameterDescriptor;

	/**
	 * Constructs the reference.
	 * 
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param parameterDetail
	 *            The detail.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public CollectionParameterReference(final PresQualCollectionParameterDescriptor<C, P> delegate, final ParameterDetail parameterDetail,
			final Class<?> qualifier) {
		super(delegate, parameterDetail, qualifier);
		this.parameterDescriptor = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public Class<C> getCollectionType() {
		return this.parameterDescriptor.getCollectionType();
	}

	/**
	 * Gets the value for the parameterDescriptor field.
	 * 
	 * @return The value for the parameterDescriptor field.
	 */
	public PresQualCollectionParameterDescriptor<C, P> getParameterDescriptor() {
		return this.parameterDescriptor;
	}

	/**
	 * Sets a new value for the parameterDescriptor field.
	 * 
	 * @param parameterDescriptor
	 *            The new value for the parameterDescriptor field.
	 */
	public void setParameterDescriptor(final PresQualCollectionParameterDescriptor<C, P> parameterDescriptor) {
		this.parameterDescriptor = parameterDescriptor;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationCollectionParameterReference[\n");
		super.parameterToString(builder);
		builder.append("]");
		return builder.toString();
	}
}
