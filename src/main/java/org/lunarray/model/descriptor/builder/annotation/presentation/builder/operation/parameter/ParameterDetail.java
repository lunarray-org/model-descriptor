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

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * The parameter detail.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ParameterDetail
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 2302660852958285317L;
	/** The description key. */
	private String descriptionKey;
	/** The format. */
	private String format;
	/** The in line indication. */
	private boolean inLineIndication;
	/** The order. */
	private int order;
	/** The render type. */
	private RenderType renderType;
	/** Parameter cannot be empty. */
	private boolean requiredIndication;
	/** The resource bundle. */
	private String resourceBundle;

	/**
	 * Constructs the detail.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected ParameterDetail(final ParameterDetailBuilderImpl builder) {
		Validate.notNull(builder, "Builder may not be null.");
		this.renderType = builder.getRenderType();
		this.inLineIndication = builder.isInLineIndication();
		this.requiredIndication = builder.isRequiredIndication();
		this.order = builder.getOrder();
		this.format = builder.getFormat();
		this.descriptionKey = builder.getDescriptionKey();
		this.resourceBundle = builder.getResourceBundle();
	}

	/**
	 * The description key.
	 * 
	 * @return The description key.
	 */
	public String getDescriptionKey() {
		return this.descriptionKey;
	}

	/**
	 * Gets the value for the format field.
	 * 
	 * @return The value for the format field.
	 */
	public String getFormat() {
		return this.format;
	}

	/**
	 * Gets the value for the order field.
	 * 
	 * @return The value for the order field.
	 */
	public int getOrder() {
		return this.order;
	}

	/**
	 * Gets the value for the renderType field.
	 * 
	 * @return The value for the renderType field.
	 */
	public RenderType getRenderType() {
		return this.renderType;
	}

	/**
	 * Gets the resource bundle.
	 * 
	 * @return The resource bundle.
	 */
	public ResourceBundle getResourceBundle() {
		ResourceBundle bundle = null;
		final String bundleName = this.getResourceBundleName();
		if (!CheckUtil.isNull(bundleName) && !StringUtil.isEmptyString(bundleName)) {
			bundle = ResourceBundle.getBundle(bundleName);
		}
		return bundle;
	}

	/**
	 * Gets the resource bundle.
	 * 
	 * @param locale
	 *            The locale.
	 * @return The resource bundle.
	 */
	public ResourceBundle getResourceBundle(final Locale locale) {
		ResourceBundle bundle = null;
		final String bundleName = this.getResourceBundleName();
		if (!CheckUtil.isNull(bundleName) && !StringUtil.isEmptyString(bundleName)) {
			bundle = ResourceBundle.getBundle(bundleName, locale);
		}
		return bundle;
	}

	/**
	 * Gets the resource bundle name.
	 * 
	 * @return The resource bundle name.
	 */
	public String getResourceBundleName() {
		return this.resourceBundle;
	}

	/**
	 * Gets the value for the inLineIndication field.
	 * 
	 * @return The value for the inLineIndication field.
	 */
	public boolean isInLineIndication() {
		return this.inLineIndication;
	}

	/**
	 * Gets the value for the requiredIndication field.
	 * 
	 * @return The value for the requiredIndication field.
	 */
	public boolean isRequiredIndication() {
		return this.requiredIndication;
	}

	/**
	 * Sets a new value for the descriptionKey field.
	 * 
	 * @param descriptionKey
	 *            The new value for the descriptionKey field.
	 */
	public void setDescriptionKey(final String descriptionKey) {
		this.descriptionKey = descriptionKey;
	}

	/**
	 * Sets a new value for the format field.
	 * 
	 * @param format
	 *            The new value for the format field.
	 */
	public void setFormat(final String format) {
		this.format = format;
	}

	/**
	 * Sets a new value for the inLineIndication field.
	 * 
	 * @param inLineIndication
	 *            The new value for the inLineIndication field.
	 */
	public void setInLineIndication(final boolean inLineIndication) {
		this.inLineIndication = inLineIndication;
	}

	/**
	 * Sets a new value for the order field.
	 * 
	 * @param order
	 *            The new value for the order field.
	 */
	public void setOrder(final int order) {
		this.order = order;
	}

	/**
	 * Sets a new value for the renderType field.
	 * 
	 * @param renderType
	 *            The new value for the renderType field.
	 */
	public void setRenderType(final RenderType renderType) {
		this.renderType = renderType;
	}

	/**
	 * Sets a new value for the requiredIndication field.
	 * 
	 * @param requiredIndication
	 *            The new value for the requiredIndication field.
	 */
	public void setRequiredIndication(final boolean requiredIndication) {
		this.requiredIndication = requiredIndication;
	}

	/**
	 * Sets a new value for the resourceBundle field.
	 * 
	 * @param resourceBundle
	 *            The new value for the resourceBundle field.
	 */
	public void setResourceBundle(final String resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("\n\tIn Line Indication: ").append(this.inLineIndication);
		builder.append("\n\tOrder: ").append(this.order);
		builder.append("\n\tRender Type: ").append(this.renderType);
		builder.append("\n\tIs Required: ").append(this.requiredIndication);
		builder.append("\n\tResource Bundle: ").append(this.resourceBundle);
		builder.append("\n\tDescription Key: ").append(this.descriptionKey);
		builder.append("\n\tFormat: ").append(this.format);
		builder.append("\n");
		return builder.toString();
	}
}
