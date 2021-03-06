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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation;

import java.util.List;

import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;

/**
 * Describes a operation reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 */
public final class OperationReference<R, E>
		extends AbstractOperationReference<R, E> {

	/** Serial id. */
	private static final long serialVersionUID = 7954514827527406393L;

	/**
	 * Constructs the refence.
	 * 
	 * @param reference
	 *            The reference. May not be null.
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param operationDetail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 * @param parameters
	 *            The parameters. May not be null.
	 */
	public OperationReference(final ResultDescriptor<R> reference, final OperationDescriptor<R, E> delegate, final OperationDetail operationDetail,
			final Class<?> qualifier, final List<ParameterDescriptor<?>> parameters) {
		super(reference, delegate, operationDetail, qualifier, parameters);
	}

	/** {@inheritDoc} */
	@Override
	public org.lunarray.model.descriptor.accessor.reference.operation.OperationReference<E> getOperationReference() {
		return this.getDelegate().getOperationReference();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationOperationReference[\n");
		this.operationToString(builder);
		builder.append("]");
		return builder.toString();
	}
}
