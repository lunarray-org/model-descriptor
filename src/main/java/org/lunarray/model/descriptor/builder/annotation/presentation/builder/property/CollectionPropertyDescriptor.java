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

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.builders.property.AbstractCollectionPropertyDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualCollectionPropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Describes a collection property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class CollectionPropertyDescriptor<C, P extends Collection<C>, E>
		extends AbstractCollectionPropertyDescriptor<C, P, E>
		implements PresQualCollectionPropertyDescriptor<C, P, E> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -2248993058399788115L;
	/** The entity name. */
	private String entityName;
	/** The property detail. */
	private PropertyDetail propertyDetail;

	/**
	 * Constructs the property.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 * @param entityName
	 *            The entity name.
	 */
	protected CollectionPropertyDescriptor(final CollectionPropertyBuilder<C, P, E> builder, final String entityName) {
		super(builder);
		this.propertyDetail = builder.getDetail();
		this.entityName = entityName;
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, CollectionPropertyDescriptor.ADAPTER_TYPE_NULL);
		return this.propertyAdapt(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, CollectionPropertyDescriptor.ADAPTER_TYPE_NULL);
		return this.propertyAdaptable(adapterType);
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
		String key;
		if (StringUtil.isEmptyString(this.propertyDetail.getDescriptionKey())) {
			key = String.format("%s.%s", this.entityName, this.getName());
		} else {
			key = this.propertyDetail.getDescriptionKey();
		}
		return key;
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
		builder.append("PresentationCollectionPropertyDescriptor[\n");
		this.collectionPropertyToString(builder);
		builder.append(this.propertyDetail.toString());
		builder.append("]");
		return builder.toString();
	}
}
