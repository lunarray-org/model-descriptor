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
package org.lunarray.model.descriptor.builder.annotation.resolver.property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.resolver.matchers.PropertyMatcher;

/**
 * Property resolver using accessors.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The property resolver type.
 */
public abstract class AbstractPropertyResolver<R extends AbstractPropertyResolver<R>>
		implements PropertyResolverStrategy {

	/** Validation message. */
	private static final String MATCHER_NULL = "Matcher may not be null.";
	/** The accessor matcher. */
	private transient PropertyMatcher<Method> accessorMatcherResolver;
	/** The field matcher. */
	private transient PropertyMatcher<Field> fieldMatcherResolver;
	/** The mutator matcher. */
	private transient PropertyMatcher<Method> mutatorMatcherResolver;

	/**
	 * Default constructor.
	 */
	public AbstractPropertyResolver() {
		this.accessorMatcherResolver = null;
		this.fieldMatcherResolver = null;
		this.mutatorMatcherResolver = null;
	}

	/**
	 * Sets a new value for the accessorMatcher field.
	 * 
	 * @param accessorMatcher
	 *            The new value for the accessorMatcher field.
	 * @return The resolver.
	 */
	public final R accessorMatcher(final PropertyMatcher<Method> accessorMatcher) {
		Validate.notNull(accessorMatcher, AbstractPropertyResolver.MATCHER_NULL);
		this.accessorMatcherResolver = accessorMatcher;
		return this.getResolver();
	}

	/**
	 * Sets a new value for the fieldMatcher field.
	 * 
	 * @param fieldMatcher
	 *            The new value for the fieldMatcher field.
	 * @return The resolver.
	 */
	public final R fieldMatcher(final PropertyMatcher<Field> fieldMatcher) {
		Validate.notNull(fieldMatcher, AbstractPropertyResolver.MATCHER_NULL);
		this.fieldMatcherResolver = fieldMatcher;
		return this.getResolver();
	}

	/**
	 * Gets the value for the accessorMatcherResolver field.
	 * 
	 * @return The value for the accessorMatcherResolver field.
	 */
	public final PropertyMatcher<Method> getAccessorMatcherResolver() {
		return this.accessorMatcherResolver;
	}

	/**
	 * Gets the value for the fieldMatcherResolver field.
	 * 
	 * @return The value for the fieldMatcherResolver field.
	 */
	public final PropertyMatcher<Field> getFieldMatcherResolver() {
		return this.fieldMatcherResolver;
	}

	/**
	 * Gets the value for the mutatorMatcherResolver field.
	 * 
	 * @return The value for the mutatorMatcherResolver field.
	 */
	public final PropertyMatcher<Method> getMutatorMatcherResolver() {
		return this.mutatorMatcherResolver;
	}

	/**
	 * Sets a new value for the mutatorMatcher field.
	 * 
	 * @param mutatorMatcher
	 *            The new value for the mutatorMatcher field.
	 * @return The resolver.
	 */
	public final R mutatorMatcher(final PropertyMatcher<Method> mutatorMatcher) {
		Validate.notNull(mutatorMatcher, AbstractPropertyResolver.MATCHER_NULL);
		this.mutatorMatcherResolver = mutatorMatcher;
		return this.getResolver();
	}

	/**
	 * Gets the resolver.
	 * 
	 * @return The resolver.
	 */
	protected abstract R getResolver();
}
