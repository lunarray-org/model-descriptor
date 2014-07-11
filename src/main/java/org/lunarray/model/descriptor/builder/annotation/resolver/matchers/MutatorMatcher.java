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

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.lang.Validate;

/**
 * A matcher for mutators.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class MutatorMatcher
		extends AbstractMatcher<Method> {
	/** Validation message. */
	private static final String METHOD_NULL = "Method may not be null.";

	/**
	 * Default constructor.
	 */
	public MutatorMatcher() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public Type extractGenericType(final Method type) {
		Validate.notNull(type, MutatorMatcher.METHOD_NULL);
		Type result = null;
		if (this.matchGenericConditions(type)) {
			result = type.getGenericParameterTypes()[0];
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> extractType(final Method type) {
		Validate.notNull(type, MutatorMatcher.METHOD_NULL);
		Class<?> result = null;
		if (this.matchConditions(type)) {
			result = type.getParameterTypes()[0];
		}
		return result;
	}

	/**
	 * Has a generic parameter.
	 * 
	 * @param memberType
	 *            The member.
	 * @return True if there is a generic parameter.
	 */
	private boolean matchGenericConditions(final Method memberType) {
		Validate.notNull(memberType, MutatorMatcher.METHOD_NULL);
		return memberType.getGenericParameterTypes().length == 1;
	}

	/** {@inheritDoc} */
	@Override
	protected boolean matchConditions(final Method memberType) {
		Validate.notNull(memberType, MutatorMatcher.METHOD_NULL);
		return memberType.getParameterTypes().length == 1;
	}
}
