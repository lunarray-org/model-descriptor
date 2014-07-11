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

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.reference.AbstractReference;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualPropertyDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierSelected;

/**
 * Common property reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public abstract class AbstractPropertyReference<P, E>
		extends AbstractReference
		implements PresQualPropertyDescriptor<P, E> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -744842093381723665L;
	/** The delegate. */
	private PresQualPropertyDescriptor<P, E> delegate;
	/** The detail. */
	private PropertyDetail propertyDetail;

	/**
	 * Constructs the refence.
	 * 
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param propertyDetail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public AbstractPropertyReference(final PresQualPropertyDescriptor<P, E> delegate, final PropertyDetail propertyDetail,
			final Class<?> qualifier) {
		super(qualifier);
		Validate.notNull(delegate, "Delegate may not be null.");
		Validate.notNull(propertyDetail, "Detail may not be null.");
		this.delegate = delegate;
		this.propertyDetail = propertyDetail;
	}

	/** {@inheritDoc} */
	@Override
	public final <A> A adapt(final Class<A> clazz) {
		Validate.notNull(clazz, AbstractPropertyReference.ADAPTER_TYPE_NULL);
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
		Validate.notNull(adapterType, AbstractPropertyReference.ADAPTER_TYPE_NULL);
		boolean result = true;
		if (!QualifierSelected.class.equals(adapterType)) {
			result = this.delegate.adaptable(adapterType);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final <X extends PropertyExtension<P, E>> X extension(final Class<X> extensionClazz) {
		return this.delegate.extension(extensionClazz);
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
	public final PresQualPropertyDescriptor<P, E> getDelegate() {
		return this.delegate;
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription() {
		return DescribedUtil.getDescription(this.propertyDetail, this.delegate.getDescription());
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription(final Locale locale) {
		return DescribedUtil.getDescription(this.propertyDetail, this.delegate.getDescription(), locale);
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescriptionKey() {
		return this.propertyDetail.getDescriptionKey();
	}

	/** {@inheritDoc} */
	@Override
	public final Class<E> getEntityType() {
		return this.delegate.getEntityType();
	}

	/** {@inheritDoc} */
	@Override
	public final String getFormat() {
		return this.propertyDetail.getFormat();
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return this.delegate.getName();
	}

	/** {@inheritDoc} */
	@Override
	public final int getOrder() {
		int result;
		if (Integer.MIN_VALUE == this.propertyDetail.getOrder()) {
			result = this.delegate.getOrder();
		} else {
			result = this.propertyDetail.getOrder();
		}
		return result;
	}

	/**
	 * Gets the value for the propertyDetail field.
	 * 
	 * @return The value for the propertyDetail field.
	 */
	public final PropertyDetail getPropertyDetail() {
		return this.propertyDetail;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<P> getPropertyType() {
		return this.delegate.getPropertyType();
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
		return this.propertyDetail.getRenderType();
	}

	/** {@inheritDoc} */
	@Override
	public final P getValue(final E parent) throws ValueAccessException {
		return this.delegate.getValue(parent);
	}

	@Override
	public final boolean isAssignable(final Object value) {
		return this.delegate.isAssignable(value);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isImmutable() {
		return this.propertyDetail.isImmutable();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isInLineIndication() {
		return this.propertyDetail.isInLineIndication();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isName() {
		return this.propertyDetail.isName();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isRelation() {
		return this.delegate.isRelation();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isRequiredIndication() {
		return this.propertyDetail.isRequiredIndication();
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isVisible() {
		return this.propertyDetail.isVisible();
	}

	/**
	 * Sets a new value for the delegate field.
	 * 
	 * @param delegate
	 *            The new value for the delegate field.
	 */
	public final void setDelegate(final PresQualPropertyDescriptor<P, E> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Sets a new value for the propertyDetail field.
	 * 
	 * @param propertyDetail
	 *            The new value for the propertyDetail field.
	 */
	public final void setPropertyDetail(final PropertyDetail propertyDetail) {
		this.propertyDetail = propertyDetail;
	}

	/** {@inheritDoc} */
	@Override
	public final void setValue(final E parent, final P value) throws ValueAccessException {
		this.delegate.setValue(parent, value);
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void propertyToString(final StringBuilder builder) {
		this.referenceToString(builder);
		builder.append(this.propertyDetail.toString());
		builder.append("\n\tName: ").append(this.delegate.getName());
		builder.append("\n\tType: ").append(this.delegate.getPropertyType());
		builder.append("\n\tImmutable: ").append(this.delegate.isImmutable());
		builder.append("\n\tCardinality: ").append(this.delegate.getCardinality());
		builder.append("\n\tIs Related: ").append(this.delegate.isRelation());
		if (this.isRelation()) {
			builder.append("\n\tRelated Name: ").append(this.delegate.getRelatedName());
		}
		builder.append("\n");
	}
}
