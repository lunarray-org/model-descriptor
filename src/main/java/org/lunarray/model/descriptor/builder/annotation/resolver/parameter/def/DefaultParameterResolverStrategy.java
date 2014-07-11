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
package org.lunarray.model.descriptor.builder.annotation.resolver.parameter.def;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.accessor.operation.parameter.ParameterBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.ParameterResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default parameter resolver strategy. Simply excludes a set of given prefixes.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultParameterResolverStrategy
		implements ParameterResolverStrategy {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultParameterResolverStrategy.class);

	/**
	 * Default constructor.
	 */
	public DefaultParameterResolverStrategy() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public List<DescribedParameter<?>> resolveParameters(final DescribedOperation operation) {
		DefaultParameterResolverStrategy.LOGGER.debug("Resolving parameters for operation {}", operation);
		Validate.notNull(operation, "Operation may not be null.");
		final Method method = operation.getOperation();
		final Class<?>[] parameterTypes = method.getParameterTypes();
		final List<DescribedParameter<?>> parameters = new ArrayList<DescribedParameter<?>>(parameterTypes.length);
		int iterator = 0;
		for (final Class<?> type : parameterTypes) {
			parameters.add(iterator, this.process(iterator, type, method, operation));
			iterator = iterator + 1;
		}
		DefaultParameterResolverStrategy.LOGGER.debug("Resolved parameters {} for operation {}", parameters, operation);
		return parameters;
	}

	/**
	 * Process parameter.
	 * 
	 * @param index
	 *            The parameter index.
	 * @param type
	 *            The parameter type.
	 * @param primary
	 *            The primary declaring method.
	 * @param operation
	 *            The operation.
	 * @return The described parameter.
	 * @param <P>
	 *            The parameter type.
	 */
	private <P> DescribedParameter<P> process(final int index, final Class<P> type, final Method primary, final DescribedOperation operation) {
		final ParameterBuilder<P> builder = DescribedParameter.createBuilder();
		// Base attributes.
		builder.index(index).parameterType(type).entityType(primary.getDeclaringClass());
		// Generic type.
		builder.genericType(primary.getGenericParameterTypes()[index]).operation(operation);
		// Process annotations.
		for (final Annotation annotation : primary.getParameterAnnotations()[index]) {
			builder.addAnnotation(annotation);
		}
		for (final Method m : operation.getMatches()) {
			for (final Annotation annotation : m.getParameterAnnotations()[index]) {
				builder.addAnnotation(annotation);
			}
		}
		return builder.buildDescribed();
	}
}
