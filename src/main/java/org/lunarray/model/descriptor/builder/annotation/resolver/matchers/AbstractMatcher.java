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
package org.lunarray.model.descriptor.builder.annotation.resolver.matchers;

import java.lang.reflect.Member;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * An abstract matcher.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <M>
 *            The member type.
 */
public abstract class AbstractMatcher<M extends Member>
		implements PropertyMatcher<M> {

	/** Validation message. */
	private static final String MEMBER_NULL = "Member may not be null.";
	/** Whether or not to enforce camel cases in naming. */
	private transient boolean enforceCamelCaseMatcher = true;
	/** The locale. */
	private transient Locale localeMatcher;
	/** The possible prefixes. */
	private final transient List<String> prefixes;
	/** The possible suffixes. */
	private final transient List<String> suffixes;

	/**
	 * Default constructor.
	 */
	public AbstractMatcher() {
		this.prefixes = new LinkedList<String>();
		this.suffixes = new LinkedList<String>();
		this.localeMatcher = Locale.getDefault();
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyMatcher<M> addPrefix(final String prefix) {
		this.prefixes.add(prefix);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyMatcher<M> addSuffix(final String suffix) {
		this.suffixes.add(suffix);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyMatcher<M> enforceCamelCase(final boolean enforceCamelCase) {
		this.enforceCamelCaseMatcher = enforceCamelCase;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final String extractName(final M type) {
		Validate.notNull(type, AbstractMatcher.MEMBER_NULL);
		String name = type.getName();
		name = this.matchPrefixes(name);
		name = this.matchSuffixes(name);
		if (!StringUtil.isEmptyString(name) && this.enforceCamelCaseMatcher) {
			final StringBuilder nameBuilder = new StringBuilder();
			nameBuilder.append(name.substring(0, 1).toLowerCase(this.localeMatcher));
			nameBuilder.append(name.substring(1));
			name = nameBuilder.toString();
		}
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyMatcher<M> locale(final Locale locale) {
		this.localeMatcher = locale;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean matches(final M type) {
		Validate.notNull(type, AbstractMatcher.MEMBER_NULL);
		boolean result = false;
		final String name = type.getName();
		if (this.prefixes.isEmpty() && this.suffixes.isEmpty()) {
			result = true;
		} else {
			result = true;
			result = this.prefixesMatch(result, name);
			result = this.suffixesMatch(result, name);
		}
		return result && this.matchConditions(type);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean matches(final M type, final String name, final Class<?> otherType) {
		Validate.notNull(type, AbstractMatcher.MEMBER_NULL);
		Validate.notNull(name, "Name may not be null.");
		Validate.notNull(otherType, "Other type may not be null.");
		boolean match = this.matches(type);
		if (match) {
			match &= otherType.equals(this.extractType(type));
			match &= name.equals(this.extractName(type));
		}
		return match;
	}

	/**
	 * Match for prefixes.
	 * 
	 * @param name
	 *            The name.
	 * @return The new name.
	 */
	private String matchPrefixes(final String name) {
		String result = name;
		if (!this.prefixes.isEmpty()) {
			String matchingPrefix = "";
			for (final String prefix : this.prefixes) {
				if (result.startsWith(prefix)) {
					matchingPrefix = prefix;
				}
			}
			if (!StringUtil.isEmptyString(matchingPrefix)) {
				result = result.substring(matchingPrefix.length());
			}
		}
		return result;
	}

	/**
	 * Match for suffixes.
	 * 
	 * @param name
	 *            The name.
	 * @return The new name.
	 */
	private String matchSuffixes(final String name) {
		String result = name;
		if (!this.suffixes.isEmpty()) {
			String matchingSuffix = "";
			for (final String suffix : this.suffixes) {
				if (result.startsWith(suffix)) {
					matchingSuffix = suffix;
				}
			}
			if (!StringUtil.isEmptyString(matchingSuffix)) {
				result = result.substring(0, result.length() - matchingSuffix.length());
			}
		}
		return result;
	}

	/**
	 * Process prefix matches.
	 * 
	 * @param matches
	 *            The result.
	 * @param name
	 *            The member name.
	 * @return True if the prefixes match, false otherwise.
	 */
	private boolean prefixesMatch(final boolean matches, final String name) {
		boolean result = matches;
		if (!this.prefixes.isEmpty()) {
			boolean prefixFound = false;
			for (final String prefix : this.prefixes) {
				if (name.startsWith(prefix)) {
					prefixFound = true;
				}
			}
			result &= prefixFound;
		}
		return result;
	}

	/**
	 * Process suffix matches.
	 * 
	 * @param matches
	 *            The result.
	 * @param name
	 *            The member name.
	 * @return True if the suffixes match, false otherwise.
	 */
	private boolean suffixesMatch(final boolean matches, final String name) {
		boolean result = matches;
		if (!this.suffixes.isEmpty()) {
			boolean suffixFound = false;
			for (final String suffix : this.suffixes) {
				if (name.endsWith(suffix)) {
					suffixFound = true;
				}
			}
			result &= suffixFound;
		}
		return result;
	}

	/**
	 * Tests if the member matches the other conditions.
	 * 
	 * @param memberType
	 *            The member.
	 * @return True if the member matches the given conditions.
	 */
	protected abstract boolean matchConditions(M memberType);
}
