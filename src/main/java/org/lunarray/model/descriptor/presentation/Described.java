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
package org.lunarray.model.descriptor.presentation;

import java.util.Locale;

/**
 * Indicates something is described.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface Described {

	/**
	 * Gets the description string.
	 * 
	 * @return The description string.
	 */
	String getDescription();

	/**
	 * Gets the description string for a given locale.
	 * 
	 * @param locale
	 *            The locale.
	 * @return The string in a given locale.
	 */
	String getDescription(Locale locale);

	/**
	 * Gets the description key.
	 * 
	 * @return The description key.
	 */
	String getDescriptionKey();
}
