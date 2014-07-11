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
package org.lunarray.model.descriptor.builder.annotation.resolver.property.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.property.AbstractProperty;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.accessor.property.PropertyBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.matchers.PropertyMatcher;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.AbstractPropertyResolver;
import org.lunarray.model.descriptor.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Property resolver using fields.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class FieldPropertyResolver
		extends AbstractPropertyResolver<FieldPropertyResolver> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FieldPropertyResolver.class);

	/**
	 * Default constructor.
	 */
	public FieldPropertyResolver() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public List<DescribedProperty<?>> resolveProperties(final DescribedEntity<?> entityType) {
		FieldPropertyResolver.LOGGER.debug("Resolving properties for {}", entityType);
		Validate.notNull(entityType, "Entity may not be null.");
		final Class<?> type = entityType.getEntityType();
		final List<DescribedProperty<?>> properties = new LinkedList<DescribedProperty<?>>();
		final List<Method> methods = new LinkedList<Method>();
		ReflectionUtil.getMethods(methods, type, false, true);
		final List<Field> fields = new LinkedList<Field>();
		ReflectionUtil.getFields(fields, type);
		for (final Field field : fields) {
			if (this.getFieldMatcherResolver().matches(field)) {
				properties.add(this.processProperty(entityType, methods, field));
			}
		}
		FieldPropertyResolver.LOGGER.debug("Resolved properties {} for entity {}", properties, entityType);
		return properties;
	}

	/**
	 * Processes a property.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param methods
	 *            The method list.
	 * @param field
	 *            The property field.
	 * @param <P>
	 *            The property type.
	 * @return The property.
	 */
	private <P> DescribedProperty<P> processProperty(final DescribedEntity<?> entityType, final List<Method> methods, final Field field) {
		final PropertyBuilder<P> builder = AbstractProperty.createBuilder();
		for (final Annotation a : field.getAnnotations()) {
			builder.addAnnotation(a);
		}
		final PropertyMatcher<Field> matcher = this.getFieldMatcherResolver();
		@SuppressWarnings("unchecked")
		// About as sure as we can get.
		final Class<P> type = (Class<P>) matcher.extractType(field);
		final String name = matcher.extractName(field);
		builder.raw(field).genericType(matcher.extractGenericType(field));
		builder.name(name).type(type).entityType(entityType);
		this.resolveAccessor(methods, builder, type, name);
		this.resolveMutator(methods, builder, type, name);
		return builder.buildDescribed();
	}

	/**
	 * Resolves the accessor.
	 * 
	 * @param methods
	 *            Candidate methods.
	 * @param builder
	 *            The builder.
	 * @param type
	 *            The property type.
	 * @param name
	 *            The property name.
	 * @param <P>
	 *            The property type.
	 */
	private <P> void resolveAccessor(final List<Method> methods, final PropertyBuilder<P> builder, final Class<P> type, final String name) {
		Method accessor = null;
		for (final Method method : methods) {
			if (this.getAccessorMatcherResolver().matches(method, name, type)) {
				for (final Annotation a : method.getAnnotations()) {
					builder.addAnnotation(a);
				}
				accessor = method;
			}
		}
		if (!CheckUtil.isNull(accessor)) {
			builder.accessor(accessor);
		}
	}

	/**
	 * Resolves the mutator.
	 * 
	 * @param methods
	 *            Candidate methods.
	 * @param builder
	 *            The builder.
	 * @param type
	 *            The property type.
	 * @param name
	 *            The property name.
	 * @param <P>
	 *            The property type.
	 */
	private <P> void resolveMutator(final List<Method> methods, final PropertyBuilder<P> builder, final Class<P> type, final String name) {
		Method mutator = null;
		for (final Method method : methods) {
			if (this.getMutatorMatcherResolver().matches(method, name, type)) {
				for (final Annotation a : method.getAnnotations()) {
					builder.addAnnotation(a);
				}
				mutator = method;
			}
		}
		if (CheckUtil.isNull(mutator)) {
			builder.addModifier(Modifier.FINAL);
		} else {
			builder.mutator(mutator);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected FieldPropertyResolver getResolver() {
		return this;
	}
}
