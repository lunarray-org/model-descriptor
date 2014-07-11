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

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractResultDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualResultTypeDescriptor;
import org.lunarray.model.descriptor.presentation.RelationPresentationDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * A result type descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 */
public final class ResultDescriptorImpl<R>
		extends AbstractResultDescriptor<R>
		implements PresQualResultTypeDescriptor<R> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = 7045674822808877610L;
	/** The property detail. */
	private ResultDetail resultTypeDetail;

	/**
	 * Constructs the descriptor.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected ResultDescriptorImpl(final ResultBuilder<R, ?> builder) {
		super(builder);
		this.resultTypeDetail = builder.getDetail();
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, ResultDescriptorImpl.ADAPTER_TYPE_NULL);
		A adapted = null;
		if (RelationPresentationDescriptor.class.equals(adapterType)) {
			if (this.isRelation()) {
				adapted = adapterType.cast(this);
			}
		} else {
			adapted = this.resultAdapt(adapterType);
		}
		return adapted;
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, ResultDescriptorImpl.ADAPTER_TYPE_NULL);
		boolean result;
		if (RelationPresentationDescriptor.class.equals(adapterType)) {
			result = this.isRelation();
		} else {
			result = this.resultAdaptable(adapterType);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String getFormat() {
		return this.resultTypeDetail.getFormat();
	}

	/** {@inheritDoc} */
	@Override
	public RenderType getRenderType() {
		return this.resultTypeDetail.getRenderType();
	}

	/**
	 * Gets the value for the resultTypeDetail field.
	 * 
	 * @return The value for the resultTypeDetail field.
	 */
	public ResultDetail getResultTypeDetail() {
		return this.resultTypeDetail;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInLineIndication() {
		return this.resultTypeDetail.isInLineIndication();
	}

	/**
	 * Sets a new value for the resultTypeDetail field.
	 * 
	 * @param resultTypeDetail
	 *            The new value for the resultTypeDetail field.
	 */
	public void setResultTypeDetail(final ResultDetail resultTypeDetail) {
		this.resultTypeDetail = resultTypeDetail;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationResultDescriptor[\n");
		this.resultToString(builder);
		builder.append(this.resultTypeDetail.toString());
		builder.append("]");
		return builder.toString();
	}
}
