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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.DescribedDetail;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * The described details.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public abstract class AbstractDescribedDetail
		implements DescribedDetail {

	/** Serial id. */
	private static final long serialVersionUID = -4573816245482815768L;
	/** The description key. */
	private String descriptionKey;
	/** The resource bundle. */
	private String resourceBundle;
	/** Visible indication. */
	private boolean visible;

	/**
	 * Constructs the detail.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected AbstractDescribedDetail(final AbstractDescribedDetailBuilder builder) {
		Validate.notNull(builder, "Builder may not be null.");
		this.descriptionKey = builder.getDescriptionKey();
		this.resourceBundle = builder.getResourceBundle();
		this.visible = builder.isVisible();
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	public final void describedToString(final StringBuilder builder) {
		builder.append("\tVisible: ").append(this.visible);
		builder.append("\n\tResource Bundle: ").append(this.resourceBundle);
		builder.append("\n\tDescription Key: ").append(this.descriptionKey);
		builder.append("\n");
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescriptionKey() {
		return this.descriptionKey;
	}

	/** {@inheritDoc} */
	@Override
	public final ResourceBundle getResourceBundle() {
		ResourceBundle bundle = null;
		final String bundleName = this.getResourceBundleName();
		if (!CheckUtil.isNull(bundleName) && !StringUtil.isEmptyString(bundleName)) {
			bundle = ResourceBundle.getBundle(bundleName);
		}
		return bundle;
	}

	/** {@inheritDoc} */
	@Override
	public final ResourceBundle getResourceBundle(final Locale locale) {
		ResourceBundle bundle = null;
		final String bundleName = this.getResourceBundleName();
		if (!CheckUtil.isNull(bundleName) && !StringUtil.isEmptyString(bundleName)) {
			bundle = ResourceBundle.getBundle(bundleName, locale);
		}
		return bundle;
	}

	/** {@inheritDoc} */
	@Override
	public final String getResourceBundleName() {
		return this.resourceBundle;
	}

	/**
	 * Gets the value for the visible field.
	 * 
	 * @return The value for the visible field.
	 */
	public final boolean isVisible() {
		return this.visible;
	}

	/**
	 * Sets a new value for the descriptionKey field.
	 * 
	 * @param descriptionKey
	 *            The new value for the descriptionKey field.
	 */
	public final void setDescriptionKey(final String descriptionKey) {
		this.descriptionKey = descriptionKey;
	}

	/**
	 * Sets a new value for the resourceBundle field.
	 * 
	 * @param resourceBundle
	 *            The new value for the resourceBundle field.
	 */
	public final void setResourceBundle(final String resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	/**
	 * Sets a new value for the visible field.
	 * 
	 * @param visible
	 *            The new value for the visible field.
	 */
	public final void setVisible(final boolean visible) {
		this.visible = visible;
	}
}
