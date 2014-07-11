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

import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.AbstractDescribedDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PropertyDetailBuilder;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * The property detail builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class PropertyDetailBuilderImpl
		extends AbstractDescribedDetailBuilder
		implements PropertyDetailBuilder {

	/**
	 * Creates a new builder.
	 * 
	 * @return A new builder.
	 */
	public static PropertyDetailBuilderImpl create() {
		return new PropertyDetailBuilderImpl();
	}
	/** The format. */
	private transient String formatBuilder;
	/** Indicates immutable. */
	private transient boolean immutableBuilder;
	/** The in line indication. */
	private transient boolean inLineIndication;
	/** The name indication. */
	private transient boolean nameBuilder;
	/** The order. */
	private transient int orderBuilder;
	/** The render type. */
	private transient RenderType renderTypeBuilder;

	/** The required indication. */
	private transient boolean requiredIndication;

	/**
	 * Default constructor.
	 */
	private PropertyDetailBuilderImpl() {
		super();
		this.inLineIndication = false;
		this.nameBuilder = false;
		this.orderBuilder = Integer.MIN_VALUE;
		this.immutableBuilder = false;
		this.renderTypeBuilder = RenderType.TEXT;
		this.requiredIndication = false;
	}

	/**
	 * Builds the detail.
	 * 
	 * @return The detail.
	 */
	public PropertyDetail build() {
		return new PropertyDetail(this);
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl format(final String format) {
		this.formatBuilder = format;
		return this;
	}

	/**
	 * Gets the value for the order field.
	 * 
	 * @return The value for the order field.
	 */
	public int getOrder() {
		return this.orderBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl immutable() {
		this.immutableBuilder = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl inLine() {
		this.inLineIndication = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl name() {
		this.nameBuilder = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl order(final int order) {
		this.orderBuilder = order;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl renderType(final RenderType renderType) {
		this.renderTypeBuilder = renderType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl required() {
		this.requiredIndication = true;
		return this;
	}

	/**
	 * Gets the value for the format field.
	 * 
	 * @return The value for the format field.
	 */
	protected String getFormat() {
		return this.formatBuilder;
	}

	/**
	 * Gets the value for the renderType field.
	 * 
	 * @return The value for the renderType field.
	 */
	protected RenderType getRenderType() {
		return this.renderTypeBuilder;
	}

	/**
	 * Gets the value for the immutable field.
	 * 
	 * @return The value for the immutable field.
	 */
	protected boolean isImmutable() {
		return this.immutableBuilder;
	}

	/**
	 * Gets the value for the inLineIndication field.
	 * 
	 * @return The value for the inLineIndication field.
	 */
	protected boolean isInLineIndication() {
		return this.inLineIndication;
	}

	/**
	 * Gets the value for the name field.
	 * 
	 * @return The value for the name field.
	 */
	protected boolean isName() {
		return this.nameBuilder;
	}

	/**
	 * Gets the value for the requiredIndication field.
	 * 
	 * @return The value for the requiredIndication field.
	 */
	protected boolean isRequiredIndication() {
		return this.requiredIndication;
	}
}
