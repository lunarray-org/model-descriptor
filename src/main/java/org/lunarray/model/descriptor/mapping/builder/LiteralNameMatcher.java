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
package org.lunarray.model.descriptor.mapping.builder;

import org.apache.commons.lang.Validate;

/**
 * Name matcher that tests on equality.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class LiteralNameMatcher
		implements NameMatcher {

	/** Validation message. */
	private static final String SOURCE_NAME_NULL = "Source name may not be null.";
	/** Obey case sensitivity. */
	private boolean caseSensitive;

	/**
	 * Default constructor.
	 */
	public LiteralNameMatcher() {
		this.caseSensitive = true;
	}

	/**
	 * Gets the value for the caseSensitive field.
	 * 
	 * @return The value for the caseSensitive field.
	 */
	public boolean isCaseSensitive() {
		return this.caseSensitive;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEntityMatch(final String sourceName, final String targetName) {
		Validate.notNull(sourceName, LiteralNameMatcher.SOURCE_NAME_NULL);
		boolean result;
		if (this.caseSensitive) {
			result = sourceName.equals(targetName);
		} else {
			result = sourceName.equalsIgnoreCase(targetName);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPropertyMatch(final String sourceName, final String targetName) {
		Validate.notNull(sourceName, LiteralNameMatcher.SOURCE_NAME_NULL);
		boolean result;
		if (this.caseSensitive) {
			result = sourceName.equals(targetName);
		} else {
			result = sourceName.equalsIgnoreCase(targetName);
		}
		return result;
	}

	/**
	 * Sets a new value for the caseSensitive field.
	 * 
	 * @param caseSensitive
	 *            The new value for the caseSensitive field.
	 */
	public void setCaseSensitive(final boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
}
