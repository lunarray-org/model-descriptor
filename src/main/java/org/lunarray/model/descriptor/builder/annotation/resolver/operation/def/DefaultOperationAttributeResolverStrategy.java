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

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.model.annotations.Ignore;
import org.lunarray.model.descriptor.model.annotations.MemberName;
import org.lunarray.model.descriptor.model.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default attribute resolver strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultOperationAttributeResolverStrategy
		implements OperationAttributeResolverStrategy {

	/** Validation message. */
	private static final String COMMAND_NULL = "Operation may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOperationAttributeResolverStrategy.class);

	/** Default constructor. */
	public DefaultOperationAttributeResolverStrategy() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public String getName(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationAttributeResolverStrategy.COMMAND_NULL);
		String result = null;
		if (operation.isAnnotationPresent(MemberName.class)) {
			final Iterator<MemberName> candidates = operation.getAnnotation(MemberName.class).iterator();
			while (CheckUtil.isNull(result) && candidates.hasNext()) {
				result = candidates.next().value();
			}
		}
		if (CheckUtil.isNull(result)) {
			result = operation.getName();
		}
		DefaultOperationAttributeResolverStrategy.LOGGER.debug("Resolved name for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getReferenceRelation(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationAttributeResolverStrategy.COMMAND_NULL);
		Class<?> result = null;
		if (operation.isAnnotationPresent(Reference.class)) {
			final Iterator<Reference> candidates = operation.getAnnotation(Reference.class).iterator();
			while (CheckUtil.isNull(result) && candidates.hasNext()) {
				result = candidates.next().value();
			}
		}
		DefaultOperationAttributeResolverStrategy.LOGGER.debug("Resolved reference relation for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isIgnore(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationAttributeResolverStrategy.COMMAND_NULL);
		final boolean result = operation.isAnnotationPresent(Ignore.class);
		DefaultOperationAttributeResolverStrategy.LOGGER.debug("Resolved is ignore for operation {}: {}", operation, result);
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isReference(final DescribedOperation operation) {
		Validate.notNull(operation, DefaultOperationAttributeResolverStrategy.COMMAND_NULL);
		final boolean result = operation.isAnnotationPresent(Reference.class);
		DefaultOperationAttributeResolverStrategy.LOGGER.debug("Resolved is reference for operation {}: {}", operation, result);
		return result;
	}
}
