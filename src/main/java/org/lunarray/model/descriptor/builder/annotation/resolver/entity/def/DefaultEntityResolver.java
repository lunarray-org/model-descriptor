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
package org.lunarray.model.descriptor.builder.annotation.resolver.entity.def;

import java.lang.annotation.Annotation;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.entity.EntityBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.EntityResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Property resolver using accessors.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultEntityResolver
		implements EntityResolverStrategy {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEntityResolver.class);
	/** Whether or not to search the type hierarchy. */
	private transient boolean searchHierarchyResolver;

	/**
	 * Default constructor.
	 */
	public DefaultEntityResolver() {
		this.searchHierarchyResolver = true;
	}

	/** {@inheritDoc} */
	@Override
	public DescribedEntity<?> resolveEntity(final Class<?> entityType) {
		DefaultEntityResolver.LOGGER.debug("Resolving entity {}", entityType);
		Validate.notNull(entityType, "Entity type may not be null.");
		@SuppressWarnings("unchecked")
		final EntityBuilder<?> builder = DescribedEntity.createBuilder().entityType((Class<Object>) entityType);
		final List<Class<?>> hierarchy = new LinkedList<Class<?>>();
		if (this.searchHierarchyResolver) {
			final Deque<Class<?>> types = new LinkedList<Class<?>>();
			final Set<Class<?>> processed = new HashSet<Class<?>>();
			types.add(entityType);
			while (!types.isEmpty()) {
				final Class<?> next = types.pop();
				hierarchy.add(next);
				final Class<?> superType = next.getSuperclass();
				if (!CheckUtil.isNull(superType) && !processed.contains(superType)) {
					types.add(superType);
				}
				for (final Class<?> interfaceType : next.getInterfaces()) {
					if (!processed.contains(interfaceType)) {
						types.add(interfaceType);
					}
				}
				processed.add(next);
			}
		} else {
			hierarchy.add(entityType);
		}
		for (final Class<?> type : hierarchy) {
			for (final Annotation a : type.getAnnotations()) {
				builder.addAnnotation(a);
			}
		}
		final DescribedEntity<?> result = builder.build();
		DefaultEntityResolver.LOGGER.debug("Resolved entity {} for type {}", result, entityType);
		return result;
	}

	/**
	 * Sets a new value for the searchHierarchyResolver field.
	 * 
	 * @param searchHierarchyResolver
	 *            The new value for the searchHierarchyResolver field.
	 */
	public void setSearchHierarchyResolver(final boolean searchHierarchyResolver) {
		this.searchHierarchyResolver = searchHierarchyResolver;
	}
}
