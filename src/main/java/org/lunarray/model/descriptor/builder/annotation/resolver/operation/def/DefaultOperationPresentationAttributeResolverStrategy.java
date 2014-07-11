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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.PresentationOperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default operation attribute resolver.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultOperationPresentationAttributeResolverStrategy
		implements PresentationOperationAttributeResolverStrategy {
	/** Validation message. */
	private static final String COMMAND_NULL = "Operation may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOperationPresentationAttributeResolverStrategy.class);

	/**
	 * Default constructor.
	 */
	public DefaultOperationPresentationAttributeResolverStrategy() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public String buttonKey(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().buttonKey();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved button key for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String buttonKey(final DescribedOperation operation, final Class<?> qualifier) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().buttonKey();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved button key for operation {} with qualifier {}: {}",
				operation, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String format(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().format();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved format for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String format(final DescribedOperation operation, final Class<?> qualifier) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().format();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved format for operation {} with qualifier {}: {}",
				operation, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit inLine(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().inLine();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is inline for operation {}: {}", operation, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit inLine(final DescribedOperation operation, final Class<?> qualifier) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().inLine();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is inline for operation {} with qualifier {}: {}",
				operation, qualifier, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedOperation operation) {
		final Iterator<PresentationHint> hint = this.getHint(operation).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().labelKey();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved label key for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String labelKey(final DescribedOperation operation, final Class<?> qualifier) {
		final Iterator<PresentationHint> hint = this.getHint(operation, qualifier).iterator();
		String result = "";
		while (("".equals(result)) && hint.hasNext()) {
			result = hint.next().labelKey();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved label key for operation {} with qualifier {}: {}",
				operation, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public int order(final DescribedOperation operation) {
		final Iterator<PresentationHint> hint = this.getHint(operation).iterator();
		int result = Integer.MIN_VALUE;
		while ((result == Integer.MIN_VALUE) && hint.hasNext()) {
			result = hint.next().order();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved order for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public int order(final DescribedOperation operation, final Class<?> qualifier) {
		final Iterator<PresentationHint> hint = this.getHint(operation, qualifier).iterator();
		int result = Integer.MIN_VALUE;
		while ((result == Integer.MIN_VALUE) && hint.hasNext()) {
			result = hint.next().order();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved order for operation {} with qualifier {}: {}",
				operation, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public List<Class<?>> qualifiers(final DescribedOperation operation) {
		final List<Class<?>> result = new LinkedList<Class<?>>();
		if (operation.isAnnotationPresent(QualifierPresentationHints.class)) {
			final List<QualifierPresentationHints> hintsList = operation.getAnnotation(QualifierPresentationHints.class);
			for (final QualifierPresentationHints hints : hintsList) {
				for (final QualifierPresentationHint hint : hints.value()) {
					result.add(hint.name());
				}
			}
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved qualifiers for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType render(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation).iterator();
		RenderType result = RenderType.DEFAULT;
		while ((result == RenderType.DEFAULT) && hint.hasNext()) {
			result = hint.next().render();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved render for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public RenderType render(final DescribedOperation operation, final Class<?> qualifier) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation, qualifier).iterator();
		RenderType result = RenderType.DEFAULT;
		while ((result == RenderType.DEFAULT) && hint.hasNext()) {
			result = hint.next().render();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved render for operation {} with qualifier {}: {}",
				operation, qualifier, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit visible(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().visible();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is visible for operation {}: {}", operation, bool);
		return bool;
	}

	/** {@inheritDoc} */
	@Override
	public BooleanInherit visible(final DescribedOperation operation, final Class<?> qualifier) {
		Validate.notNull(operation, DefaultOperationPresentationAttributeResolverStrategy.COMMAND_NULL);
		final Iterator<PresentationHint> hint = this.getHint(operation, qualifier).iterator();
		BooleanInherit bool = BooleanInherit.INHERIT;
		while ((BooleanInherit.INHERIT == bool) && hint.hasNext()) {
			bool = hint.next().visible();
		}
		DefaultOperationPresentationAttributeResolverStrategy.LOGGER.debug("Resolved is visible for operation {} with qualifier {}: {}",
				operation, qualifier, bool);
		return bool;
	}

	/**
	 * Gets the hint for the operation.
	 * 
	 * @param operation
	 *            The operation.
	 * @return The hint.
	 */
	private List<PresentationHint> getHint(final DescribedOperation operation) {
		List<PresentationHint> result = new LinkedList<PresentationHint>();
		if (operation.isAnnotationPresent(PresentationHint.class)) {
			result = operation.getAnnotation(PresentationHint.class);
		}
		return result;
	}

	/**
	 * Gets the hint for the operation and qualifier.
	 * 
	 * @param operation
	 *            The operation.
	 * @param qualifier
	 *            The qualifier.
	 * @return The hint.
	 */
	private List<PresentationHint> getHint(final DescribedOperation operation, final Class<?> qualifier) {
		final List<PresentationHint> result = new LinkedList<PresentationHint>();
		if (operation.isAnnotationPresent(QualifierPresentationHints.class)) {
			final List<QualifierPresentationHints> hintsList = operation.getAnnotation(QualifierPresentationHints.class);
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
