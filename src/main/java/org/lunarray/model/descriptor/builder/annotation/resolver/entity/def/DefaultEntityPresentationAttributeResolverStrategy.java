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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.PresentationEntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.presentation.annotations.EntityPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.EntityQualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.EntityQualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default property attribute resolver.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultEntityPresentationAttributeResolverStrategy
		implements PresentationEntityAttributeResolverStrategy {

	/** Validation message. */
	private static final String ENTITY_NULL = "Entity may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEntityPresentationAttributeResolverStrategy.class);

	/**
	 * Default constructor.
	 */
	public DefaultEntityPresentationAttributeResolverStrategy() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedEntity<?> entityType) {
		Validate.notNull(entityType, DefaultEntityPresentationAttributeResolverStrategy.ENTITY_NULL);
		final Iterator<EntityPresentationHint> hint = this.getHint(entityType).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().descriptionKey();
		}
		DefaultEntityPresentationAttributeResolverStrategy.LOGGER.debug("Resolved label key for entity {}: {}", entityType, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedEntity<?> entityType, final Class<?> qualifier) {
		Validate.notNull(entityType, DefaultEntityPresentationAttributeResolverStrategy.ENTITY_NULL);
		final Iterator<EntityPresentationHint> hint = this.getHint(entityType, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().descriptionKey();
		}
		DefaultEntityPresentationAttributeResolverStrategy.LOGGER.debug("Resolved laber key for entity {} with qualifier: {}", entityType,
				qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public List<Class<?>> qualifiers(final DescribedEntity<?> entityType) {
		Validate.notNull(entityType, DefaultEntityPresentationAttributeResolverStrategy.ENTITY_NULL);
		final List<Class<?>> qualifiers = new LinkedList<Class<?>>();
		if (entityType.isAnnotationPresent(EntityQualifierPresentationHints.class)) {
			final List<EntityQualifierPresentationHints> hintsList = entityType.getAnnotation(EntityQualifierPresentationHints.class);
			for (final EntityQualifierPresentationHints hints : hintsList) {
				for (final EntityQualifierPresentationHint hint : hints.value()) {
					qualifiers.add(hint.name());
				}
			}
		}
		if (entityType.isAnnotationPresent(EntityQualifierPresentationHint.class)) {
			final List<EntityQualifierPresentationHint> hintList = entityType.getAnnotation(EntityQualifierPresentationHint.class);
			for (final EntityQualifierPresentationHint hint : hintList) {
				qualifiers.add(hint.name());
			}
		}
		DefaultEntityPresentationAttributeResolverStrategy.LOGGER.debug("Resolved qualifiers for entity {}: {}", entityType, qualifiers);
		return qualifiers;
	}

	/** {@inheritDoc} */
	@Override
	public String resourceBundle(final DescribedEntity<?> entityType) {
		Validate.notNull(entityType, DefaultEntityPresentationAttributeResolverStrategy.ENTITY_NULL);
		final Iterator<EntityPresentationHint> hint = this.getHint(entityType).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().resourceBundle();
		}
		DefaultEntityPresentationAttributeResolverStrategy.LOGGER.debug("Resolved resource bundle for entity {}: {}", entityType, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String resourceBundle(final DescribedEntity<?> entityType, final Class<?> qualifier) {
		Validate.notNull(entityType, DefaultEntityPresentationAttributeResolverStrategy.ENTITY_NULL);
		final Iterator<EntityPresentationHint> hint = this.getHint(entityType, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().resourceBundle();
		}
		DefaultEntityPresentationAttributeResolverStrategy.LOGGER.debug("Resolved resource bundle for entity {} with qualifier: {}",
				entityType, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit visible(final DescribedEntity<?> entityType) {
		Validate.notNull(entityType, DefaultEntityPresentationAttributeResolverStrategy.ENTITY_NULL);
		final Iterator<EntityPresentationHint> hint = this.getHint(entityType).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().visible();
		}
		DefaultEntityPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is visible for entity {}: {}", entityType, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit visible(final DescribedEntity<?> entityType, final Class<?> qualifier) {
		Validate.notNull(entityType, DefaultEntityPresentationAttributeResolverStrategy.ENTITY_NULL);
		final Iterator<EntityPresentationHint> hint = this.getHint(entityType, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().visible();
		}
		DefaultEntityPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is visible for entity {} with qualifier {}: {}",
				entityType, qualifier, bool);
		return bool;
	}

	/**
	 * Gets the hint for the entity.
	 * 
	 * @param entity
	 *            The entity
	 * @return The hint.
	 */
	private List<EntityPresentationHint> getHint(final DescribedEntity<?> entity) {
		List<EntityPresentationHint> result = new LinkedList<EntityPresentationHint>();
		if (entity.isAnnotationPresent(EntityPresentationHint.class)) {
			result = entity.getAnnotation(EntityPresentationHint.class);
		}
		return result;
	}

	/**
	 * Gets the hint for the entity and qualifier.
	 * 
	 * @param entity
	 *            The entity
	 * @param qualifier
	 *            The qualifier.
	 * @return The hint.
	 */
	private List<EntityPresentationHint> getHint(final DescribedEntity<?> entity, final Class<?> qualifier) {
		final List<EntityPresentationHint> result = new LinkedList<EntityPresentationHint>();
		if (entity.isAnnotationPresent(EntityQualifierPresentationHints.class)) {
			final List<EntityQualifierPresentationHints> hintsList = entity.getAnnotation(EntityQualifierPresentationHints.class);
			for (final EntityQualifierPresentationHints hints : hintsList) {
				for (final EntityQualifierPresentationHint hint : hints.value()) {
					if (qualifier.equals(hint.name())) {
						result.add(hint.hint());
					}
				}
			}
		}
		if (entity.isAnnotationPresent(EntityQualifierPresentationHint.class)) {
			final List<EntityQualifierPresentationHint> hintList = entity.getAnnotation(EntityQualifierPresentationHint.class);
			for (final EntityQualifierPresentationHint hint : hintList) {
				if (qualifier.equals(hint.name())) {
					result.add(hint.hint());
				}
			}
		}
		return result;
	}
}
