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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.result;

import java.util.Collection;

import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualCollectionResultTypeDescriptor;

/**
 * A collection result reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <R>
 *            The result type.
 */
public final class CollectionResultReference<C, R extends Collection<C>>
		extends AbstractResultReference<R>
		implements PresQualCollectionResultTypeDescriptor<C, R> {

	/** Serial id. */
	private static final long serialVersionUID = 2147568589718828414L;
	/** The result descriptor. */
	private PresQualCollectionResultTypeDescriptor<C, R> resultTypeDescriptor;

	/**
	 * Constructs the reference.
	 * 
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param resultTypeDetail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public CollectionResultReference(final PresQualCollectionResultTypeDescriptor<C, R> delegate, final ResultDetail resultTypeDetail,
			final Class<?> qualifier) {
		super(delegate, resultTypeDetail, qualifier);
		this.resultTypeDescriptor = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public Class<C> getCollectionType() {
		return this.resultTypeDescriptor.getCollectionType();
	}

	/**
	 * Gets the value for the resultTypeDescriptor field.
	 * 
	 * @return The value for the resultTypeDescriptor field.
	 */
	public PresQualCollectionResultTypeDescriptor<C, R> getResultTypeDescriptor() {
		return this.resultTypeDescriptor;
	}

	/**
	 * Sets a new value for the resultTypeDescriptor field.
	 * 
	 * @param resultTypeDescriptor
	 *            The new value for the resultTypeDescriptor field.
	 */
	public void setResultTypeDescriptor(final PresQualCollectionResultTypeDescriptor<C, R> resultTypeDescriptor) {
		this.resultTypeDescriptor = resultTypeDescriptor;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationCollectionResultTypeReference[\n");
		super.referenceToString(builder);
		builder.append("]");
		return builder.toString();
	}
}
