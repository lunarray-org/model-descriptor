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

import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;

/**
 * String utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum StringUtil {

	/** Instance. */
	INSTANCE;

	/** Comma string. */
	public static final String DOUBLE_TAB_NEWLINE_COMMA = ", \n\t\t";

	/**
	 * Comma separates an iterators content.
	 * 
	 * @param iteratable
	 *            The iteratable. May not be null.
	 * @param comma
	 *            The comma.
	 * @param appender
	 *            The appender. May not be null.
	 */
	public static void commaSeparated(final Iterable<?> iteratable, final String comma, final StringBuilder appender) {
		Validate.notNull(iteratable, "Iteratable may not be null.");
		Validate.notNull(appender, "Appender may not be null.");
		final Iterator<?> iterator = iteratable.iterator();
		if (iterator.hasNext()) {
			appender.append(iterator.next());
		}
		while (iterator.hasNext()) {
			appender.append(comma).append(iterator.next());
		}
	}

	/**
	 * Tests if a string is empty.
	 * 
	 * @param testString
	 *            The string to test.
	 * @return True if the string is null or empty.
	 */
	public static boolean isEmptyString(final String testString) {
		return CheckUtil.isNull(testString) || "".equals(testString);
	}
}
