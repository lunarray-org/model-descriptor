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

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.DescribedDetail;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Utility for properties.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum DescribedUtil {

	/** Instance. */
	INSTANCE;

	/**
	 * Gets the descriptor.
	 * 
	 * @param describedDetail
	 *            The property details.
	 * @param defaultValue
	 *            The default value.
	 * @return The descriptor
	 */
	public static String getDescription(final DescribedDetail describedDetail, final String defaultValue) {
		return DescribedUtil.getDescriptor(describedDetail.getDescriptionKey(), describedDetail.getResourceBundle(), defaultValue);
	}

	/**
	 * Gets the descriptor.
	 * 
	 * @param detailDetail
	 *            The property details.
	 * @param locale
	 *            The locale.
	 * @param defaultValue
	 *            The default value.
	 * @return The descriptor
	 */
	public static String getDescription(final DescribedDetail detailDetail, final String defaultValue, final Locale locale) {
		return DescribedUtil.getDescriptor(detailDetail.getDescriptionKey(), detailDetail.getResourceBundle(locale), defaultValue);
	}

	/**
	 * Gets the descriptor.
	 * 
	 * @param propertyDetail
	 *            The detail.
	 * @param bundle
	 *            The bundle.
	 * @param defaultValue
	 *            The default value.
	 * @return The descriptor.
	 */
	public static String getDescriptor(final String propertyDetail, final ResourceBundle bundle, final String defaultValue) {
		String description;
		if (CheckUtil.isNull(bundle) || !bundle.containsKey(propertyDetail)) {
			if (StringUtil.isEmptyString(propertyDetail)) {
				description = defaultValue;
			} else {
				description = propertyDetail;
			}
		} else {
			description = bundle.getString(propertyDetail);
		}
		return description;
	}
}
