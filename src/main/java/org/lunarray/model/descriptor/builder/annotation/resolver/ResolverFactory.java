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
package org.lunarray.model.descriptor.builder.annotation.resolver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

import org.lunarray.model.descriptor.builder.annotation.resolver.entity.def.DefaultEntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.def.DefaultEntityPresentationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.def.DefaultEntityResolver;
import org.lunarray.model.descriptor.builder.annotation.resolver.matchers.AccessorMatcher;
import org.lunarray.model.descriptor.builder.annotation.resolver.matchers.FieldMatcher;
import org.lunarray.model.descriptor.builder.annotation.resolver.matchers.MutatorMatcher;
import org.lunarray.model.descriptor.builder.annotation.resolver.matchers.PropertyMatcher;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.def.DefaultOperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.def.DefaultOperationPresentationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.def.DefaultOperationResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.def.DefaultParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.def.DefaultParameterPresentationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.def.DefaultParameterResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.accessor.AccessorPropertyResolver;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.def.DefaultPropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.def.DefaultPropertyPresentationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.field.FieldPropertyResolver;

/**
 * A factory for resolvers.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum ResolverFactory {

	/** Instance. */
	INSTANCE;

	/** The default is prefix. */
	private static final String ACCESSOR_1_PREFIX = "is";
	/** The default get prefix. */
	private static final String ACCESSOR_2_PREFIX = "get";
	/** The default set prefix. */
	private static final String MUTATOR_PREFIX = "set";

	/**
	 * Constructs an accessor property resolver.
	 * 
	 * @return The property resolver.
	 */
	public static AccessorPropertyResolver accessorPropertyResolver() {
		return new AccessorPropertyResolver().accessorMatcher(ResolverFactory.defaultAccessorMatcher())
				.fieldMatcher(ResolverFactory.defaultFieldMatcher()).mutatorMatcher(ResolverFactory.defaultMutatorMatcher());
	}

	/**
	 * Constructs an accessor property resolver.
	 * 
	 * @param locale
	 *            The locale.
	 * @return The property resolver.
	 */
	public static AccessorPropertyResolver accessorPropertyResolver(final Locale locale) {
		return new AccessorPropertyResolver().accessorMatcher(ResolverFactory.defaultAccessorMatcher(locale))
				.fieldMatcher(ResolverFactory.defaultFieldMatcher(locale)).mutatorMatcher(ResolverFactory.defaultMutatorMatcher(locale));
	}

	/**
	 * Gets the default accessor matcher.
	 * 
	 * @return A default accessor matcher.
	 */
	public static PropertyMatcher<Method> defaultAccessorMatcher() {
		return new AccessorMatcher().addPrefix(ResolverFactory.ACCESSOR_1_PREFIX).addPrefix(ResolverFactory.ACCESSOR_2_PREFIX);
	}

	/**
	 * Gets the default accessor matcher.
	 * 
	 * @param locale
	 *            The locale.
	 * @return A default accessor matcher.
	 */
	public static PropertyMatcher<Method> defaultAccessorMatcher(final Locale locale) {
		return new AccessorMatcher().locale(locale).addPrefix(ResolverFactory.ACCESSOR_1_PREFIX)
				.addPrefix(ResolverFactory.ACCESSOR_2_PREFIX);
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 * @param <S>
	 *            The super type of the model.
	 */
	public static <S> DefaultEntityAttributeResolverStrategy<S> defaultEntityAttributeResolver() {
		return new DefaultEntityAttributeResolverStrategy<S>();
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 */
	public static DefaultEntityPresentationAttributeResolverStrategy defaultEntityPresentationAttributeResolver() {
		return new DefaultEntityPresentationAttributeResolverStrategy();
	}

	/**
	 * Constructs an default entity resolver.
	 * 
	 * @return The entity resolver.
	 */
	public static DefaultEntityResolver defaultEntityResolver() {
		return new DefaultEntityResolver();
	}

	/**
	 * Gets a default field matcher.
	 * 
	 * @return A default field matcher.
	 */
	public static PropertyMatcher<Field> defaultFieldMatcher() {
		return new FieldMatcher();
	}

	/**
	 * Gets a default field matcher.
	 * 
	 * @param locale
	 *            The locale.
	 * @return A default field matcher.
	 */
	public static PropertyMatcher<Field> defaultFieldMatcher(final Locale locale) {
		return new FieldMatcher().locale(locale);
	}

	/**
	 * Gets a default mutator matcher.
	 * 
	 * @return A default mutator matcher.
	 */
	public static PropertyMatcher<Method> defaultMutatorMatcher() {
		return new MutatorMatcher().addPrefix(ResolverFactory.MUTATOR_PREFIX);
	}

	/**
	 * Gets a default mutator matcher.
	 * 
	 * @param locale
	 *            The locale.
	 * @return A default mutator matcher.
	 */
	public static PropertyMatcher<Method> defaultMutatorMatcher(final Locale locale) {
		return new MutatorMatcher().addPrefix(ResolverFactory.MUTATOR_PREFIX).locale(locale);
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 */
	public static DefaultOperationAttributeResolverStrategy defaultOperationAttributeResolver() {
		return new DefaultOperationAttributeResolverStrategy();
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 */
	public static DefaultOperationPresentationAttributeResolverStrategy defaultOperationPresentationAttributeResolver() {
		return new DefaultOperationPresentationAttributeResolverStrategy();
	}

	/**
	 * Constructs an default operation resolver.
	 * 
	 * @return The operation resolver.
	 */
	public static DefaultOperationResolverStrategy defaultOperationResolver() {
		return new DefaultOperationResolverStrategy().addExcludePrefix(ResolverFactory.ACCESSOR_1_PREFIX)
				.addExcludePrefix(ResolverFactory.ACCESSOR_2_PREFIX).addExcludePrefix(ResolverFactory.MUTATOR_PREFIX);
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 */
	public static DefaultParameterAttributeResolverStrategy defaultParameterAttributeResolver() {
		return new DefaultParameterAttributeResolverStrategy();
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 */
	public static DefaultParameterPresentationAttributeResolverStrategy defaultParameterPresentationAttributeResolver() {
		return new DefaultParameterPresentationAttributeResolverStrategy();
	}

	/**
	 * Constructs an default parameter resolver.
	 * 
	 * @return The parameter resolver.
	 */
	public static DefaultParameterResolverStrategy defaultParameterResolver() {
		return new DefaultParameterResolverStrategy();
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 */
	public static DefaultPropertyAttributeResolverStrategy defaultPropertyAttributeResolver() {
		return new DefaultPropertyAttributeResolverStrategy();
	}

	/**
	 * Constructs an default attribute resolver.
	 * 
	 * @return The attribute resolver.
	 */
	public static DefaultPropertyPresentationAttributeResolverStrategy defaultPropertyPresentationAttributeResolver() {
		return new DefaultPropertyPresentationAttributeResolverStrategy();
	}

	/**
	 * Constructs an field property resolver.
	 * 
	 * @return The property resolver.
	 */
	public static FieldPropertyResolver fieldPropertyResolver() {
		final FieldPropertyResolver resolver = new FieldPropertyResolver();
		resolver.accessorMatcher(ResolverFactory.defaultAccessorMatcher());
		resolver.fieldMatcher(ResolverFactory.defaultFieldMatcher());
		resolver.mutatorMatcher(ResolverFactory.defaultMutatorMatcher());
		return resolver;
	}

	/**
	 * Constructs an field property resolver.
	 * 
	 * @param locale
	 *            The locale.
	 * @return The property resolver.
	 */
	public static FieldPropertyResolver fieldPropertyResolver(final Locale locale) {
		return new FieldPropertyResolver().accessorMatcher(ResolverFactory.defaultAccessorMatcher(locale))
				.fieldMatcher(ResolverFactory.defaultFieldMatcher(locale)).mutatorMatcher(ResolverFactory.defaultMutatorMatcher(locale));
	}
}
