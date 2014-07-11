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

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.reference.AbstractReference;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualParameterDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierSelected;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Common parameter reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 */
public abstract class AbstractParameterReference<P>
		extends AbstractReference
		implements PresQualParameterDescriptor<P> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -744842093381723665L;
	/** The delegate. */
	private PresQualParameterDescriptor<P> delegate;
	/** The detail. */
	private ParameterDetail parameterDetail;

	/**
	 * Constructs the refence.
	 * 
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param parameterDetail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public AbstractParameterReference(final PresQualParameterDescriptor<P> delegate, final ParameterDetail parameterDetail,
			final Class<?> qualifier) {
		super(qualifier);
		Validate.notNull(parameterDetail, "Detail may not be null.");
		Validate.notNull(delegate, "Delegate may not be null.");
		this.delegate = delegate;
		this.parameterDetail = parameterDetail;
	}

	/** {@inheritDoc} */
	@Override
	public final <A> A adapt(final Class<A> clazz) {
		Validate.notNull(clazz, AbstractParameterReference.ADAPTER_TYPE_NULL);
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
		Validate.notNull(adapterType, AbstractParameterReference.ADAPTER_TYPE_NULL);
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
	public final PresQualParameterDescriptor<P> getDelegate() {
		return this.delegate;
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription() {
		return DescribedUtil.getDescriptor(this.parameterDetail.getDescriptionKey(), this.parameterDetail.getResourceBundle(),
				this.delegate.getDescription());
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription(final Locale locale) {
		return DescribedUtil.getDescriptor(this.parameterDetail.getDescriptionKey(), this.parameterDetail.getResourceBundle(locale),
				this.delegate.getDescription());
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescriptionKey() {
		String result = this.parameterDetail.getDescriptionKey();
		if (StringUtil.isEmptyString(result)) {
			result = this.delegate.getDescriptionKey();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final String getFormat() {
		return this.parameterDetail.getFormat();
	}

	/** {@inheritDoc} */
	@Override
	public final int getIndex() {
		return this.delegate.getIndex();
	}

	/** {@inheritDoc} */
	@Override
	public final int getOrder() {
		int result;
		if (Integer.MIN_VALUE == this.parameterDetail.getOrder()) {
			result = this.delegate.getOrder();
		} else {
			result = this.parameterDetail.getOrder();
		}
		return result;
	}

	/**
	 * Gets the value for the parameterDetail field.
	 * 
	 * @return The value for the parameterDetail field.
	 */
	public final ParameterDetail getParameterDetail() {
		return this.parameterDetail;
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
		return this.parameterDetail.getRenderType();
	}

	/** {@inheritDoc} */
	@Override
	public final Class<P> getType() {
		return this.delegate.getType();
	}

	@Override
	public final boolean isAssignable(final Object value) {
		return this.delegate.isAssignable(value);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isInLineIndication() {
		return this.parameterDetail.isInLineIndication();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isRelation() {
		return this.delegate.isRelation();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isRequiredIndication() {
		return this.parameterDetail.isRequiredIndication();
	}

	/**
	 * Sets a new value for the delegate field.
	 * 
	 * @param delegate
	 *            The new value for the delegate field.
	 */
	public final void setDelegate(final PresQualParameterDescriptor<P> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Sets a new value for the parameterDetail field.
	 * 
	 * @param parameterDetail
	 *            The new value for the parameterDetail field.
	 */
	public final void setParameterDetail(final ParameterDetail parameterDetail) {
		this.parameterDetail = parameterDetail;
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void parameterToString(final StringBuilder builder) {
		this.referenceToString(builder);
		builder.append(this.parameterDetail.toString());
		builder.append("\n\tType: ").append(this.delegate.getType());
		builder.append("\n\tCardinality: ").append(this.delegate.getCardinality());
		builder.append("\n\tIs Related: ").append(this.delegate.isRelation());
		if (this.isRelation()) {
			builder.append("\n\tRelated Name: ").append(this.delegate.getRelatedName());
		}
		builder.append("\n");
	}
}
