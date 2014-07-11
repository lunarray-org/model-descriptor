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
package org.lunarray.model.descriptor.builder.annotation.resolver.property.accessor;

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
 * Property resolver using accessors.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class AccessorPropertyResolver
		extends AbstractPropertyResolver<AccessorPropertyResolver> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AccessorPropertyResolver.class);

	/**
	 * Default constructor.
	 */
	public AccessorPropertyResolver() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public List<DescribedProperty<?>> resolveProperties(final DescribedEntity<?> entityType) {
		AccessorPropertyResolver.LOGGER.debug("Resolving properties for {}", entityType);
		Validate.notNull(entityType, "Entity may not be null.");
		final List<DescribedProperty<?>> properties = new LinkedList<DescribedProperty<?>>();
		final Class<?> type = entityType.getEntityType();
		final List<Method> methods = new LinkedList<Method>();
		ReflectionUtil.getMethods(methods, type, false, true);
		final List<Field> fields = new LinkedList<Field>();
		ReflectionUtil.getFields(fields, type);
		for (final Method method : methods) {
			if (this.getAccessorMatcherResolver().matches(method)) {
				properties.add(this.processProperty(entityType, methods, fields, method));
			}
		}
		AccessorPropertyResolver.LOGGER.debug("Resolved properties {} for entity {}", properties, entityType);
		return properties;
	}

	/**
	 * Process the property.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param methods
	 *            The methods.
	 * @param fields
	 *            The fields.
	 * @param method
	 *            The accessor method.
	 * @param <P>
	 *            The property type.
	 * @return The property.
	 */
	private <P> DescribedProperty<P> processProperty(final DescribedEntity<?> entityType, final List<Method> methods,
			final List<Field> fields, final Method method) {
		final PropertyBuilder<P> builder = AbstractProperty.createBuilder();
		for (final Annotation a : method.getAnnotations()) {
			builder.addAnnotation(a);
		}
		final PropertyMatcher<Method> resolver = this.getAccessorMatcherResolver();
		@SuppressWarnings("unchecked")
		// About as sure as we can get.
		final Class<P> type = (Class<P>) resolver.extractType(method);
		builder.accessor(method).genericType(resolver.extractGenericType(method));
		final String name = resolver.extractName(method);
		builder.name(name).type(type).entityType(entityType);
		// Resolve field.
		for (final Field field : fields) {
			if (this.getFieldMatcherResolver().matches(field, name, type)) {
				for (final Annotation a : field.getAnnotations()) {
					builder.addAnnotation(a);
				}
				builder.raw(field);
			}
		}
		// Resolve mutator.
		Method mutator = null;
		for (final Method mutatorCandidate : methods) {
			if (this.getMutatorMatcherResolver().matches(mutatorCandidate, name, type)) {
				for (final Annotation a : mutatorCandidate.getAnnotations()) {
					builder.addAnnotation(a);
				}
				mutator = mutatorCandidate;
			}
		}
		if (CheckUtil.isNull(mutator)) {
			builder.addModifier(Modifier.FINAL);
		} else {
			builder.mutator(mutator);
		}
		return builder.buildDescribed();
	}

	/** {@inheritDoc} */
	@Override
	protected AccessorPropertyResolver getResolver() {
		return this;
	}
}
