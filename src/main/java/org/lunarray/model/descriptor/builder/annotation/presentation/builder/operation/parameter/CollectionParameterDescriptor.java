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
import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractCollectionParameterDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualCollectionParameterDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Describes a collection parameter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The parameter type.
 */
public final class CollectionParameterDescriptor<C, P extends Collection<C>>
		extends AbstractCollectionParameterDescriptor<C, P>
		implements PresQualCollectionParameterDescriptor<C, P> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -2248993058399788115L;
	/** The entity name. */
	private String entityName;
	/** The operation name. */
	private String operationName;
	/** The parameter detail. */
	private ParameterDetail parameterDetail;

	/**
	 * Constructs the parameter.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 * @param entityName
	 *            The entity name.
	 * @param operationName
	 *            The operation name.
	 */
	protected CollectionParameterDescriptor(final CollectionParameterBuilder<C, P> builder, final String entityName,
			final String operationName) {
		super(builder);
		this.parameterDetail = builder.getDetail();
		this.entityName = entityName;
		this.operationName = operationName;
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
	public String getDescription() {
		return DescribedUtil.getDescriptor(this.parameterDetail.getDescriptionKey(), this.parameterDetail.getResourceBundle(),
				this.getDescriptionKey());
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription(final Locale locale) {
		return DescribedUtil.getDescriptor(this.parameterDetail.getDescriptionKey(), this.parameterDetail.getResourceBundle(locale),
				this.getDescriptionKey());
	}

	/** {@inheritDoc} */
	@Override
	public String getDescriptionKey() {
		String key;
		if (StringUtil.isEmptyString(this.parameterDetail.getDescriptionKey())) {
			key = String.format("%s.%s.%s", this.entityName, this.operationName, this.getIndex());
		} else {
			key = this.parameterDetail.getDescriptionKey();
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
		return this.parameterDetail.getFormat();
	}

	/**
	 * Gets the value for the operationName field.
	 * 
	 * @return The value for the operationName field.
	 */
	public String getOperationName() {
		return this.operationName;
	}

	/** {@inheritDoc} */
	@Override
	public int getOrder() {
		return this.parameterDetail.getOrder();
	}

	/**
	 * Gets the value for the parameterDetail field.
	 * 
	 * @return The value for the parameterDetail field.
	 */
	public ParameterDetail getParameterDetail() {
		return this.parameterDetail;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType getRenderType() {
		return this.parameterDetail.getRenderType();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isInLineIndication() {
		return this.parameterDetail.isInLineIndication();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isRequiredIndication() {
		return this.parameterDetail.isRequiredIndication();
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
	 * Sets a new value for the operationName field.
	 * 
	 * @param operationName
	 *            The new value for the operationName field.
	 */
	public void setOperationName(final String operationName) {
		this.operationName = operationName;
	}

	/**
	 * Sets a new value for the parameterDetail field.
	 * 
	 * @param parameterDetail
	 *            The new value for the parameterDetail field.
	 */
	public void setParameterDetail(final ParameterDetail parameterDetail) {
		this.parameterDetail = parameterDetail;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationCollectionParameterDescriptor[\n");
		this.collectionParameterToString(builder);
		builder.append(this.parameterDetail.toString());
		builder.append("]");
		return builder.toString();
	}
}
