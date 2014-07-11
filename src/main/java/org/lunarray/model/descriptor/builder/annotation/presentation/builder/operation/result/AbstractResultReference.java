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
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.reference.AbstractReference;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualResultTypeDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierSelected;

/**
 * Common property reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 */
public abstract class AbstractResultReference<R>
		extends AbstractReference
		implements PresQualResultTypeDescriptor<R> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -744842093381723665L;
	/** The delegate. */
	private PresQualResultTypeDescriptor<R> delegate;
	/** The detail. */
	private ResultDetail resultTypeDetail;

	/**
	 * Constructs the refence.
	 * 
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param resultTypeDetail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public AbstractResultReference(final PresQualResultTypeDescriptor<R> delegate, final ResultDetail resultTypeDetail,
			final Class<?> qualifier) {
		super(qualifier);
		Validate.notNull(resultTypeDetail, "Detail may not be null.");
		Validate.notNull(delegate, "Delegate may not be null.");
		this.delegate = delegate;
		this.resultTypeDetail = resultTypeDetail;
	}

	/** {@inheritDoc} */
	@Override
	public final <A> A adapt(final Class<A> clazz) {
		Validate.notNull(clazz, AbstractResultReference.ADAPTER_TYPE_NULL);
		A adaptee = null;
		if (QualifierSelected.class.equals(clazz)) {
			adaptee = clazz.cast(this);
		} else if (this.delegate.adaptable(clazz)) {
			if (clazz.isInstance(this)) {
				adaptee = clazz.cast(this);
			} else {
				adaptee = this.delegate.adapt(clazz);
			}
		}
		return adaptee;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, AbstractResultReference.ADAPTER_TYPE_NULL);
		boolean result = true;
		if (!QualifierSelected.class.equals(adapterType)) {
			result = this.delegate.adaptable(adapterType);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final Cardinality getCardinality() {
		return this.delegate.getCardinality();
	}

	/**
	 * Gets the value for the delegate field.
	 * 
	 * @return The value for the delegate field.
	 */
	public final PresQualResultTypeDescriptor<R> getDelegate() {
		return this.delegate;
	}

	/** {@inheritDoc} */
	@Override
	public final String getFormat() {
		return this.resultTypeDetail.getFormat();
	}

	/** {@inheritDoc} */
	@Override
	public final String getRelatedName() {
		return this.delegate.getRelatedName();
	}

	@Override
	public final RelationType getRelationType() {
		return this.delegate.getRelationType();
	}

	/** {@inheritDoc} */
	@Override
	public final RenderType getRenderType() {
		return this.resultTypeDetail.getRenderType();
	}

	/** {@inheritDoc} */
	@Override
	public final Class<R> getResultType() {
		return this.delegate.getResultType();
	}

	/**
	 * Gets the value for the resultTypeDetail field.
	 * 
	 * @return The value for the resultTypeDetail field.
	 */
	public final ResultDetail getResultTypeDetail() {
		return this.resultTypeDetail;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isInLineIndication() {
		return this.resultTypeDetail.isInLineIndication();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isRelation() {
		return this.delegate.isRelation();
	}

	/**
	 * Sets a new value for the delegate field.
	 * 
	 * @param delegate
	 *            The new value for the delegate field.
	 */
	public final void setDelegate(final PresQualResultTypeDescriptor<R> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Sets a new value for the resultTypeDetail field.
	 * 
	 * @param resultTypeDetail
	 *            The new value for the resultTypeDetail field.
	 */
	public final void setResultTypeDetail(final ResultDetail resultTypeDetail) {
		this.resultTypeDetail = resultTypeDetail;
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void resultTypeToString(final StringBuilder builder) {
		this.referenceToString(builder);
		builder.append(this.resultTypeDetail.toString());
		builder.append("\n\tCardinality: ").append(this.delegate.getCardinality());
		builder.append("\n\tIs Related: ").append(this.delegate.isRelation());
		if (this.isRelation()) {
			builder.append("\n\tRelated Name: ").append(this.delegate.getRelatedName());
		}
		builder.append("\n");
	}
}
