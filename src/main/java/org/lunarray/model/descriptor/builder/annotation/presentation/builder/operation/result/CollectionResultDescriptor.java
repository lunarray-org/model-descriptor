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

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractCollectionResultDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualCollectionResultTypeDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * Describes a collection property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The property type.
 */
public final class CollectionResultDescriptor<C, P extends Collection<C>>
		extends AbstractCollectionResultDescriptor<C, P>
		implements PresQualCollectionResultTypeDescriptor<C, P> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -2248993058399788115L;
	/** The result detail. */
	private ResultDetail propertyDetail;

	/**
	 * Constructs the property.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected CollectionResultDescriptor(final CollectionResultBuilder<C, P, ?> builder) {
		super(builder);
		this.propertyDetail = builder.getDetail();
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, CollectionResultDescriptor.ADAPTER_TYPE_NULL);
		return this.resultAdapt(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, CollectionResultDescriptor.ADAPTER_TYPE_NULL);
		return this.resultAdaptable(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public String getFormat() {
		return this.propertyDetail.getFormat();
	}

	/**
	 * Gets the value for the propertyDetail field.
	 * 
	 * @return The value for the propertyDetail field.
	 */
	public ResultDetail getPropertyDetail() {
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

	/**
	 * Sets a new value for the propertyDetail field.
	 * 
	 * @param propertyDetail
	 *            The new value for the propertyDetail field.
	 */
	public void setPropertyDetail(final ResultDetail propertyDetail) {
		this.propertyDetail = propertyDetail;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationCollectionResultDescriptor[\n");
		this.collectionResultToString(builder);
		builder.append(this.propertyDetail.toString());
		builder.append("]");
		return builder.toString();
	}

}
