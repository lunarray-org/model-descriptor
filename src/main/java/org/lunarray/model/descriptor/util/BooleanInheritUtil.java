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
package org.lunarray.model.descriptor.util;

/**
 * Utility for boolean inheriting.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum BooleanInheritUtil {

	/** Instance. */
	INSTANCE;

	/**
	 * Resolve a value.
	 * 
	 * @param value
	 *            The current value.
	 * @param parent
	 *            The inherited value.
	 * @param defaultValue
	 *            The default value.
	 * @return Resolves the value, that is: if (value=Inherit) { if
	 *         (parent=Inherit) return default else parent } else value.
	 */
	public static boolean resolve(final BooleanInherit value, final BooleanInherit parent, final boolean defaultValue) {
		boolean result;
		if (BooleanInherit.TRUE == value) {
			result = true;
		} else if (BooleanInherit.FALSE == value) {
			result = false;
		} else {
			if (BooleanInherit.TRUE == parent) {
				result = true;
			} else if (BooleanInherit.FALSE == parent) {
				result = false;
			} else {
				result = defaultValue;
			}
		}
		return result;
	}
}
