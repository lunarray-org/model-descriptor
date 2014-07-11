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

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractOperationDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.presentation.PresentationOperationDescriptor;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * A operation descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 */
public final class OperationDescriptor<R, E>
		extends AbstractOperationDescriptor<R, E>
		implements PresentationOperationDescriptor<E> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = 7045674822808877610L;
	/** The entity name. */
	private String entityName;
	/** The operation detail. */
	private OperationDetail operationDetail;
	/** The separator. */
	private char separator;

	/**
	 * Constructs the descriptor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param entityName
	 *            The entity name.
	 */
	protected OperationDescriptor(final OperationBuilder<E> builder, final String entityName) {
		super(builder);
		this.operationDetail = builder.getDetail();
		this.entityName = entityName;
		this.separator = builder.getBuilderContext().getConfiguration().embeddedIndicator();
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, OperationDescriptor.ADAPTER_TYPE_NULL);
		return this.operationAdapt(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, OperationDescriptor.ADAPTER_TYPE_NULL);
		return this.operationAdaptable(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public String getButton() {
		return DescribedUtil.getDescriptor(this.operationDetail.getDescriptionKey(), this.operationDetail.getResourceBundle(),
				this.getButtonKey());
	}

	/** {@inheritDoc} */
	@Override
	public String getButton(final Locale locale) {
		return DescribedUtil.getDescriptor(this.operationDetail.getDescriptionKey(), this.operationDetail.getResourceBundle(locale),
				this.getButtonKey());
	}

	/** {@inheritDoc} */
	@Override
	public String getButtonKey() {
		String description;
		if (StringUtil.isEmptyString(this.operationDetail.getDescriptionKey())) {
			description = new StringBuilder().append(this.entityName).append(this.separator).append(this.getName()).toString();
		} else {
			description = this.operationDetail.getDescriptionKey();
		}
		return description;
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return DescribedUtil.getDescription(this.operationDetail, this.getDescriptionKey());
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription(final Locale locale) {
		return DescribedUtil.getDescription(this.operationDetail, this.getDescriptionKey(), locale);
	}

	/** {@inheritDoc} */
	@Override
	public String getDescriptionKey() {
		return this.getButtonKey();
	}

	/**
	 * Gets the value for the entityName field.
	 * 
	 * @return The value for the entityName field.
	 */
	public String getEntityName() {
		return this.entityName;
	}

	/**
	 * Gets the value for the operationDetail field.
	 * 
	 * @return The value for the operationDetail field.
	 */
	public OperationDetail getOperationDetail() {
		return this.operationDetail;
	}

	/** {@inheritDoc} */
	@Override
	public int getOrder() {
		return this.operationDetail.getOrder();
	}

	/**
	 * Gets the value for the separator field.
	 * 
	 * @return The value for the separator field.
	 */
	public char getSeparator() {
		return this.separator;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisible() {
		return this.operationDetail.isVisible();
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
	 * Sets a new value for the operationDetail field.
	 * 
	 * @param operationDetail
	 *            The new value for the operationDetail field.
	 */
	public void setOperationDetail(final OperationDetail operationDetail) {
		this.operationDetail = operationDetail;
	}

	/**
	 * Sets a new value for the separator field.
	 * 
	 * @param separator
	 *            The new value for the separator field.
	 */
	public void setSeparator(final char separator) {
		this.separator = separator;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationOperationDescriptor[\n");
		this.operationToString(builder);
		builder.append(this.operationDetail.toString());
		builder.append("]");
		return builder.toString();
	}
}
