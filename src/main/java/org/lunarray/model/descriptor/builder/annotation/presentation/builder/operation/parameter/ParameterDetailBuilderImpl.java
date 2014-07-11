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

import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.ParameterDetailBuilder;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * The property detail builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ParameterDetailBuilderImpl
		implements ParameterDetailBuilder {

	/**
	 * Creates a new builder.
	 * 
	 * @return A new builder.
	 */
	public static ParameterDetailBuilderImpl create() {
		return new ParameterDetailBuilderImpl();
	}
	/** The description key. */
	private transient String descriptionKeyBuilder;
	/** The format. */
	private transient String formatBuilder;
	/** The in line indication. */
	private transient boolean inLineIndication;
	/** The order. */
	private transient int orderBuilder;
	/** The render type. */
	private transient RenderType renderTypeBuilder;
	/** The required indication. */
	private transient boolean requiredIndication;

	/** The resource bundle. */
	private transient String resourceBundleBuilder;

	/**
	 * Default constructor.
	 */
	private ParameterDetailBuilderImpl() {
		super();
		this.inLineIndication = false;
		this.orderBuilder = Integer.MIN_VALUE;
		this.renderTypeBuilder = RenderType.TEXT;
		this.requiredIndication = false;
		this.descriptionKeyBuilder = "";
	}

	/**
	 * Builds the detail.
	 * 
	 * @return The detail.
	 */
	public ParameterDetail build() {
		return new ParameterDetail(this);
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilder descriptionKey(final String descriptionKey) {
		this.descriptionKeyBuilder = descriptionKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilderImpl format(final String format) {
		this.formatBuilder = format;
		return this;
	}

	/**
	 * Gets the value for the descriptionKey field.
	 * 
	 * @return The value for the descriptionKey field.
	 */
	public String getDescriptionKey() {
		return this.descriptionKeyBuilder;
	}

	/**
	 * Gets the value for the order field.
	 * 
	 * @return The value for the order field.
	 */
	public int getOrder() {
		return this.orderBuilder;
	}

	/**
	 * Gets the value for the resourceBundle field.
	 * 
	 * @return The value for the resourceBundle field.
	 */
	public String getResourceBundle() {
		return this.resourceBundleBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilderImpl inLine() {
		this.inLineIndication = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilderImpl order(final int order) {
		this.orderBuilder = order;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilderImpl renderType(final RenderType renderType) {
		this.renderTypeBuilder = renderType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilderImpl required() {
		this.requiredIndication = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilder resourceBundle(final String resourceBundle) {
		this.resourceBundleBuilder = resourceBundle;
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
	 * Gets the value for the inLineIndication field.
	 * 
	 * @return The value for the inLineIndication field.
	 */
	protected boolean isInLineIndication() {
		return this.inLineIndication;
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
