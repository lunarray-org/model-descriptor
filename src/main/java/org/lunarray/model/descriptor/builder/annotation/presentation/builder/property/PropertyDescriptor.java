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
import org.lunarray.model.descriptor.builder.annotation.base.builders.property.AbstractPropertyDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualPropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RelationPresentationDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * A property descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class PropertyDescriptor<P, E>
		extends AbstractPropertyDescriptor<P, E>
		implements PresQualPropertyDescriptor<P, E> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = 7045674822808877610L;
	/** The entity name. */
	private String entityName;
	/** The property detail. */
	private PropertyDetail propertyDetail;

	/**
	 * Constructs the descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 * @param entityName
	 *            The entity name.
	 */
	protected PropertyDescriptor(final PropertyBuilder<P, E> builder, final String entityName) {
		super(builder);
		this.propertyDetail = builder.getDetail();
		this.entityName = entityName;
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, PropertyDescriptor.ADAPTER_TYPE_NULL);
		A adapted = null;
		if (RelationPresentationDescriptor.class.equals(adapterType)) {
			if (this.isRelation()) {
				adapted = adapterType.cast(this);
			}
		} else {
			adapted = this.propertyAdapt(adapterType);
		}
		return adapted;
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, PropertyDescriptor.ADAPTER_TYPE_NULL);
		boolean result;
		if (RelationPresentationDescriptor.class.equals(adapterType)) {
			result = this.isRelation();
		} else {
			result = this.propertyAdaptable(adapterType);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return DescribedUtil.getDescription(this.propertyDetail, this.getDescriptionKey());
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription(final Locale locale) {
		return DescribedUtil.getDescription(this.propertyDetail, this.getDescriptionKey(), locale);
	}

	/** {@inheritDoc} */
	@Override
	public String getDescriptionKey() {
		String description;
		if (StringUtil.isEmptyString(this.propertyDetail.getDescriptionKey())) {
			description = String.format("%s.%s", this.entityName, this.getName());
		} else {
			description = this.propertyDetail.getDescriptionKey();
		}
		return description;
	}

	/**
	 * Gets the value for the entityName field.
	 * 
	 * @return The value for the entityName field.
	 */
	public String getEntityName() {
		return this.entityName;
	}

	/** {@inheritDoc} */
	@Override
	public String getFormat() {
		return this.propertyDetail.getFormat();
	}

	/** {@inheritDoc} */
	@Override
	public int getOrder() {
		return this.propertyDetail.getOrder();
	}

	/**
	 * Gets the value for the propertyDetail field.
	 * 
	 * @return The value for the propertyDetail field.
	 */
	public PropertyDetail getPropertyDetail() {
		return this.propertyDetail;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType getRenderType() {
		return this.propertyDetail.getRenderType();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInLineIndication() {
		return this.propertyDetail.isInLineIndication();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isName() {
		return this.propertyDetail.isName();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isRequiredIndication() {
		return this.propertyDetail.isRequiredIndication();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisible() {
		return this.propertyDetail.isVisible();
	}

	/**
	 * Sets a new value for the entityName field.
	 * 
	 * @param entityName
	 *            The new value for the entityName field.
	 */
	public void setEntityName(final String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Sets a new value for the propertyDetail field.
	 * 
	 * @param propertyDetail
	 *            The new value for the propertyDetail field.
	 */
	public void setPropertyDetail(final PropertyDetail propertyDetail) {
		this.propertyDetail = propertyDetail;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationPropertyDescriptor[\n");
		this.propertyToString(builder);
		builder.append(this.propertyDetail.toString());
		builder.append("]");
		return builder.toString();
	}
}
