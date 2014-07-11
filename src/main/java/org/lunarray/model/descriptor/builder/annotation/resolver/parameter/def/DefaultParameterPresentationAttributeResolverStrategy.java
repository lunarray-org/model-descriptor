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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.PresentationParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default parameter attribute resolver.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultParameterPresentationAttributeResolverStrategy
		implements PresentationParameterAttributeResolverStrategy {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultParameterPresentationAttributeResolverStrategy.class);
	/** Validation message. */
	private static final String PARAMETER_NULL = "Parameter may not be null.";

	/**
	 * Default constructor.
	 */
	public DefaultParameterPresentationAttributeResolverStrategy() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public String format(final DescribedParameter<?> parameter) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().format();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved format for parameter {}: {}", parameter, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String format(final DescribedParameter<?> parameter, final Class<?> qualifier) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().format();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved format for parameter {} with qualifier {}: {}",
				parameter, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit inLine(final DescribedParameter<?> parameter) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().inLine();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is inline for parameter {}: {}", parameter, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit inLine(final DescribedParameter<?> parameter, final Class<?> qualifier) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().inLine();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is inline for parameter {} with qualifier {}: {}",
				parameter, qualifier, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedParameter<?> parameter) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().labelKey();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved label key for parameter {}: {}", parameter, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedParameter<?> parameter, final Class<?> qualifier) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().labelKey();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved label key for parameter {} with qualifier {}: {}",
				parameter, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public int order(final DescribedParameter<?> parameter) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter).iterator();
		int result = Integer.MIN_VALUE;
		while ((result == Integer.MIN_VALUE) && hint.hasNext()) {
			result = hint.next().order();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved order for parameter {}: {}", parameter, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public int order(final DescribedParameter<?> parameter, final Class<?> qualifier) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter, qualifier).iterator();
		int result = Integer.MIN_VALUE;
		while ((result == Integer.MIN_VALUE) && hint.hasNext()) {
			result = hint.next().order();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved order for parameter {} with qualifier {}: {}",
				parameter, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public List<Class<?>> qualifiers(final DescribedParameter<?> parameter) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final List<Class<?>> result = new LinkedList<Class<?>>();
		if (parameter.isAnnotationPresent(QualifierPresentationHints.class)) {
			final List<QualifierPresentationHints> hintsList = parameter.getAnnotation(QualifierPresentationHints.class);
			for (final QualifierPresentationHints hints : hintsList) {
				for (final QualifierPresentationHint hint : hints.value()) {
					result.add(hint.name());
				}
			}
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved qualifiers for parameter {}: {}", parameter, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType render(final DescribedParameter<?> parameter) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter).iterator();
		RenderType result = RenderType.DEFAULT;
		while ((result == RenderType.DEFAULT) && hint.hasNext()) {
			result = hint.next().render();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved render type for parameter {}: {}", parameter, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType render(final DescribedParameter<?> parameter, final Class<?> qualifier) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter, qualifier).iterator();
		RenderType result = RenderType.DEFAULT;
		while ((result == RenderType.DEFAULT) && hint.hasNext()) {
			result = hint.next().render();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved render type for parameter {} with qualifier {}: {}",
				parameter, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit required(final DescribedParameter<?> parameter) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().required();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is required for parameter {}: {}", parameter, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit required(final DescribedParameter<?> parameter, final Class<?> qualifier) {
		Validate.notNull(parameter, DefaultParameterPresentationAttributeResolverStrategy.PARAMETER_NULL);
		final Iterator<PresentationHint> hint = this.getHint(parameter, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().required();
		}
		DefaultParameterPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is required for parameter {} with qualifier {}: {}",
				parameter, qualifier, bool);
		return bool;
	}

	/**
	 * Gets the hint for the parameter.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @return The hint.
	 */
	private List<PresentationHint> getHint(final DescribedParameter<?> parameter) {
		List<PresentationHint> result = new LinkedList<PresentationHint>();
		if (parameter.isAnnotationPresent(PresentationHint.class)) {
			result = parameter.getAnnotation(PresentationHint.class);
		}
		return result;
	}

	/**
	 * Gets the hint for the parameter and qualifier.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @param qualifier
	 *            The qualifier.
	 * @return The hint.
	 */
	private List<PresentationHint> getHint(final DescribedParameter<?> parameter, final Class<?> qualifier) {
		final List<PresentationHint> result = new LinkedList<PresentationHint>();
		if (parameter.isAnnotationPresent(QualifierPresentationHints.class)) {
			final List<QualifierPresentationHints> hintsList = parameter.getAnnotation(QualifierPresentationHints.class);
			for (final QualifierPresentationHints hints : hintsList) {
				for (final QualifierPresentationHint hint : hints.value()) {
					if (qualifier.equals(hint.name())) {
						result.add(hint.hint());
					}
				}
			}
		}
		return result;
	}
}
