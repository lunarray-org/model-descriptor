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
package org.lunarray.model.descriptor.builder.annotation.presentation;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A described detail.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface DescribedDetail
		extends Serializable {

	/**
	 * Gets the value for the descriptionKey field.
	 * 
	 * @return The value for the descriptionKey field.
	 */
	String getDescriptionKey();

	/**
	 * Gets the resource bundle.
	 * 
	 * @return The bundle.
	 */
	ResourceBundle getResourceBundle();

	/**
	 * Gets the resource bundle.
	 * 
	 * @param locale
	 *            The locale.
	 * @return The bundle.
	 */
	ResourceBundle getResourceBundle(final Locale locale);

	/**
	 * Gets the value for the resourceBundle field.
	 * 
	 * @return The value for the resourceBundle field.
	 */
	String getResourceBundleName();
}
