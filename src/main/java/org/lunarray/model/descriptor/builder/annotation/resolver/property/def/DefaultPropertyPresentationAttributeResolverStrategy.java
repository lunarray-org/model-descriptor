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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PresentationPropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default property attribute resolver.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultPropertyPresentationAttributeResolverStrategy
		implements PresentationPropertyAttributeResolverStrategy {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyPresentationAttributeResolverStrategy.class);
	/** Validation message. */
	private static final String PROPERTY_NULL = "Property may not be null.";

	/**
	 * Default constructor.
	 */
	public DefaultPropertyPresentationAttributeResolverStrategy() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public String format(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().format();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved format for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String format(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().format();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved format for property {} with qualifier {}: {}",
				property, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit immutable(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().immutable();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is immutable for property {}: {}", property, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit immutable(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().immutable();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is immutable for property {} with qualifier {}: {}",
				property, qualifier, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit inLine(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().inLine();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is in line for property {}: {}", property, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit inLine(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().inLine();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is in line for property {} with qualifier {}: {}",
				property, qualifier, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().labelKey();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved label key for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().labelKey();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved label key for property {} with qualifier {}: {}",
				property, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit name(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().name();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is name for property {}: {}", property, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit name(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().name();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is name for property {} with qualifier {}: {}",
				property, qualifier, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public int order(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		int result = Integer.MIN_VALUE;
		while ((result == Integer.MIN_VALUE) && hint.hasNext()) {
			result = hint.next().order();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved order for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public int order(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		int result = Integer.MIN_VALUE;
		while ((result == Integer.MIN_VALUE) && hint.hasNext()) {
			result = hint.next().order();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved order for property {} with qualifier {}: {}", property,
				qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public List<Class<?>> qualifiers(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final List<Class<?>> result = new LinkedList<Class<?>>();
		if (property.isAnnotationPresent(QualifierPresentationHints.class)) {
			final List<QualifierPresentationHints> hintsList = property.getAnnotation(QualifierPresentationHints.class);
			for (final QualifierPresentationHints hints : hintsList) {
				for (final QualifierPresentationHint hint : hints.value()) {
					result.add(hint.name());
				}
			}
		}
		if (property.isAnnotationPresent(QualifierPresentationHint.class)) {
			final List<QualifierPresentationHint> hintList = property.getAnnotation(QualifierPresentationHint.class);
			for (final QualifierPresentationHint hint : hintList) {
				result.add(hint.name());
			}
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved qualifiers for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType render(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		RenderType result = RenderType.DEFAULT;
		while ((result == RenderType.DEFAULT) && hint.hasNext()) {
			result = hint.next().render();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved render type for property {}: {}", property, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType render(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		RenderType result = RenderType.DEFAULT;
		while ((result == RenderType.DEFAULT) && hint.hasNext()) {
			result = hint.next().render();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved render type for property {} with qualifier {}: {}",
				property, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit required(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().required();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is required for property {}: {}", property, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit required(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().required();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is required for property {} with qualifier {}: {}",
				property, qualifier, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit visible(final DescribedProperty<?> property) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().visible();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is visible for property {}: {}", property, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit visible(final DescribedProperty<?> property, final Class<?> qualifier) {
		Validate.notNull(property, DefaultPropertyPresentationAttributeResolverStrategy.PROPERTY_NULL);
		final Iterator<PresentationHint> hint = this.getHint(property, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().visible();
		}
		DefaultPropertyPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is visible for property {} with qualifier, : {}",
				property, bool);
		return bool;
	}

	/**
	 * Gets the hint for the property.
	 * 
	 * @param property
	 *            The property.
	 * @return The hint.
	 */
	private List<PresentationHint> getHint(final DescribedProperty<?> property) {
		List<PresentationHint> result = new LinkedList<PresentationHint>();
		if (property.isAnnotationPresent(PresentationHint.class)) {
			result = property.getAnnotation(PresentationHint.class);
		}
		return result;
	}

	/**
	 * Gets the hint for the property and qualifier.
	 * 
	 * @param property
	 *            The property.
	 * @param qualifier
	 *            The qualifier.
	 * @return The hint.
	 */
	private List<PresentationHint> getHint(final DescribedProperty<?> property, final Class<?> qualifier) {
		final List<PresentationHint> result = new LinkedList<PresentationHint>();
		if (property.isAnnotationPresent(QualifierPresentationHints.class)) {
			final List<QualifierPresentationHints> hintsList = property.getAnnotation(QualifierPresentationHints.class);
			for (final QualifierPresentationHints hints : hintsList) {
				for (final QualifierPresentationHint hint : hints.value()) {
					if (qualifier.equals(hint.name())) {
						result.add(hint.hint());
					}
				}
			}
		}
		if (property.isAnnotationPresent(QualifierPresentationHint.class)) {
			final List<QualifierPresentationHint> hintList = property.getAnnotation(QualifierPresentationHint.class);
			for (final QualifierPresentationHint hint : hintList) {
				if (qualifier.equals(hint.name())) {
					result.add(hint.hint());
				}
			}
		}
		return result;
	}
}
