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
package org.lunarray.model.descriptor.builder.annotation.resolver.property.def;

import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.model.annotations.Alias;
import org.lunarray.model.descriptor.model.annotations.Embedded;
import org.lunarray.model.descriptor.model.annotations.Ignore;
import org.lunarray.model.descriptor.model.annotations.Key;
import org.lunarray.model.descriptor.model.annotations.MemberName;
import org.lunarray.model.descriptor.model.annotations.Reference;
import org.lunarray.model.descriptor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default attribute resolver strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultPropertyAttributeResolverStrategy
		implements PropertyAttributeResolverStrategy {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyAttributeResolverStrategy.class);
	/** Validation message. */
	private static final String PROPERTY_NULL = "Property may not be null.";

	/** Default constructor. */
	public DefaultPropertyAttributeResolverStrategy() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public String getAlias(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		String alias = null;
		if (property.isAnnotationPresent(Alias.class)) {
			final Iterator<Alias> candidates = property.getAnnotation(Alias.class).iterator();
			while (StringUtil.isEmptyString(alias) && candidates.hasNext()) {
				alias = candidates.next().value();
			}
		}
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved alias for property {}: {}", property, alias);
		return alias;
	}

	/** {@inheritDoc} */
	@Override
	public String getName(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		String result = null;
		if (property.isAnnotationPresent(MemberName.class)) {
			final Iterator<MemberName> candidates = property.getAnnotation(MemberName.class).iterator();
			while (CheckUtil.isNull(result) && candidates.hasNext()) {
				result = candidates.next().value();
			}
		}
		if (CheckUtil.isNull(result)) {
			result = property.getName();
		}
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved name for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getReferenceRelation(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		Class<?> result = null;
		if (property.isAnnotationPresent(Reference.class)) {
			final Iterator<Reference> candidates = property.getAnnotation(Reference.class).iterator();
			while (CheckUtil.isNull(result) && candidates.hasNext()) {
				result = candidates.next().value();
			}
		}
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved reference relation for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAlias(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		final boolean result = property.isAnnotationPresent(Alias.class);
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved is alias for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmbedded(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		final boolean result = property.isAnnotationPresent(Embedded.class);
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved is embedded for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isIgnore(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		final boolean result = property.isAnnotationPresent(Ignore.class);
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved is ignore for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isKey(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		boolean identifier = false;
		identifier |= property.isAnnotationPresent(Key.class);
		identifier |= (Enum.class.isAssignableFrom(property.getEntityType().getEntityType()) && "name".equals(property.getName()));
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved is key for property {}: {}", property, identifier);
		return identifier;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isReference(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyAttributeResolverStrategy.PROPERTY_NULL);
		final boolean result = property.isAnnotationPresent(Reference.class);
		DefaultPropertyAttributeResolverStrategy.LOGGER.debug("Resolved is reference for property {}: {}", property, result);
		return result;
	}
}
