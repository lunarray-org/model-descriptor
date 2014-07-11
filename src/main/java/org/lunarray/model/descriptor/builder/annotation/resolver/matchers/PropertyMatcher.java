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
import java.lang.reflect.Type;
import java.util.Locale;

/**
 * A matcher interface for the property resolvers.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <M>
 *            The match type.
 */
public interface PropertyMatcher<M extends Member> {

	/**
	 * Adds a prefix.
	 * 
	 * @param prefix
	 *            The prefix.
	 * @return The matcher.
	 */
	PropertyMatcher<M> addPrefix(final String prefix);

	/**
	 * Adds a suffix.
	 * 
	 * @param suffix
	 *            The suffix.
	 * @return The matcher.
	 */
	PropertyMatcher<M> addSuffix(final String suffix);

	/**
	 * Sets a new value for the enforceCamelCase field.
	 * 
	 * @param enforceCamelCase
	 *            The new value for the enforceCamelCase field.
	 * @return The matcher.
	 */
	PropertyMatcher<M> enforceCamelCase(final boolean enforceCamelCase);

	/**
	 * Extracts the members generic type.
	 * 
	 * @param type
	 *            The member. May not be null.
	 * @return The type.
	 */
	Type extractGenericType(M type);

	/**
	 * Extract the members name.
	 * 
	 * @param type
	 *            The member. May not be null.
	 * @return The name.
	 */
	String extractName(M type);

	/**
	 * Extracts the members type.
	 * 
	 * @param type
	 *            The member. May not be null.
	 * @return The type.
	 */
	Class<?> extractType(M type);

	/**
	 * Sets a new value for the locale field.
	 * 
	 * @param locale
	 *            The new value for the locale field.
	 * @return The matcher.
	 */
	PropertyMatcher<M> locale(final Locale locale);

	/**
	 * Tests if the member is a property.
	 * 
	 * @param type
	 *            The member. May not be null.
	 * @return True if the member matches to be a property.
	 */
	boolean matches(M type);

	/**
	 * Tests if a member describes a given property.
	 * 
	 * @param type
	 *            The member. May not be null.
	 * @param name
	 *            The property name. May not be null.
	 * @param otherType
	 *            The property type. May not be null.
	 * @return True if the member matches the property.
	 */
	boolean matches(M type, String name, Class<?> otherType);
}
