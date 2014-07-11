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
package org.lunarray.model.descriptor.builder.annotation.resolver.operation.def;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.AbstractOperation;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.operation.OperationBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationResolverStrategy;
import org.lunarray.model.descriptor.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default operation resolver strategy. Simply excludes a set of given prefixes.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultOperationResolverStrategy
		implements OperationResolverStrategy {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOperationResolverStrategy.class);
	/** Whether or not to exclude methods from object. */
	private transient boolean excludeObjectBuilder;
	/** Set of excluding prefixes. */
	private final transient Set<String> prefixes;

	/**
	 * Default constructor.
	 */
	public DefaultOperationResolverStrategy() {
		this.prefixes = new HashSet<String>();
		this.excludeObjectBuilder = true;
	}

	/**
	 * Adds a prefix to exclude.
	 * 
	 * @param prefix
	 *            The prefix.
	 * @return The strategy.
	 */
	public DefaultOperationResolverStrategy addExcludePrefix(final String prefix) {
		this.prefixes.add(prefix);
		return this;
	}

	/**
	 * Sets a new value for the excludeObject field.
	 * 
	 * @param excludeObject
	 *            The new value for the excludeObject field.
	 * @return The builder.
	 */
	public DefaultOperationResolverStrategy excludeObject(final boolean excludeObject) {
		this.excludeObjectBuilder = excludeObject;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public List<DescribedOperation> resolveOperations(final DescribedEntity<?> entityType) {
		DefaultOperationResolverStrategy.LOGGER.debug("Resolving operations for entity {}", entityType);
		Validate.notNull(entityType, "Entity may not be null.");
		final List<DescribedOperation> operations = new LinkedList<DescribedOperation>();
		final List<Method> methods = new LinkedList<Method>();
		ReflectionUtil.getMethods(methods, entityType.getEntityType(), false, this.excludeObjectBuilder);
		final Set<String> names = new HashSet<String>();
		final Set<Method> process = new HashSet<Method>(methods);
		while (!process.isEmpty()) {
			final Method method = process.iterator().next();
			process.remove(method);
			boolean matches = true;
			final String originalName = method.getName();
			for (final String prefix : this.prefixes) {
				if (originalName.startsWith(prefix)) {
					matches = false;
				}
			}
			// Exclude hashcode and equals.
			if (matches && ("hashCode".equals(originalName) || "equals".equals(originalName))) {
				matches = false;
			}
			if (matches) {
				operations.add(this.processOperation(entityType, names, process, method, originalName));
			}
		}
		DefaultOperationResolverStrategy.LOGGER.debug("Resolved operations {} for entity {}", operations, entityType);
		return operations;
	}

	/**
	 * Tests if the methods match. With match we mean, names equal and
	 * parameters equal.
	 * 
	 * @param method1
	 *            Method 1.
	 * @param method2
	 *            Method 2.
	 * @return If both methods match one another.
	 */
	private boolean match(final Method method1, final Method method2) {
		boolean matches = true;
		if (!method1.getName().equals(method2.getName())) {
			matches = false;
		}
		final Class<?>[] params1 = method1.getParameterTypes();
		final Class<?>[] params2 = method2.getParameterTypes();
		if (params1.length != params2.length) {
			matches = false;
		}
		if (matches) {
			for (int i = 0; i < params1.length; i = i + 1) {
				if (!params1[i].isAssignableFrom(params2[i])) {
					matches = false;
				}
			}
		}
		return matches;
	}

	/**
	 * Process the operation.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param names
	 *            The names set.
	 * @param process
	 *            The process collection.
	 * @param method
	 *            The method.
	 * @param originalName
	 *            The original name
	 * @return The operation.
	 */
	private DescribedOperation processOperation(final DescribedEntity<?> entityType, final Set<String> names, final Set<Method> process,
			final Method method, final String originalName) {
		int counter = 1;
		String name = originalName;
		while (names.contains(name)) {
			name = originalName + Integer.toString(counter);
			counter = counter + 1;
		}
		final OperationBuilder builder = AbstractOperation.createBuilder();
		builder.name(name).entity(entityType).operation(method);
		for (final Annotation a : method.getAnnotations()) {
			builder.addAnnotation(a);
		}
		final List<Method> matches = new LinkedList<Method>();
		for (final Method others : process) {
			if (this.match(method, others)) {
				matches.add(others);
				builder.addMatch(others);
			}
		}
		process.removeAll(matches);
		for (final Method others : matches) {
			for (final Annotation a : others.getAnnotations()) {
				builder.addAnnotation(a);
			}
		}
		return builder.buildDescribed();
	}
}
