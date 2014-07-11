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
package org.lunarray.model.descriptor.builder.annotation.presentation;

import java.util.Set;

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.PresentationEntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.PresentationOperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.PresentationParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PresentationPropertyAttributeResolverStrategy;

/**
 * The presentation qualifier builder context.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PresQualBuilderContext
		extends AnnotationBuilderContext {

	/**
	 * Gets the bundle for the given entity.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @return The bundle, or null.
	 */
	String getBundle(final DescribedEntity<?> entityType);

	/**
	 * Gets the presentation attribute resolver.
	 * 
	 * @return The attribute resolver strategy.
	 */
	PresentationEntityAttributeResolverStrategy getPresentationEntityAttributeResolverStrategy();

	/**
	 * Gets the presentation attribute resolver.
	 * 
	 * @return The attribute resolver strategy.
	 */
	PresentationOperationAttributeResolverStrategy getPresentationOperationAttributeResolverStrategy();

	/**
	 * Gets the presentation attribute resolver.
	 * 
	 * @return The attribute resolver strategy.
	 */
	PresentationParameterAttributeResolverStrategy getPresentationParameterAttributeResolverStrategy();

	/**
	 * Gets the presentation attribute resolver.
	 * 
	 * @return The attribute resolver strategy.
	 */
	PresentationPropertyAttributeResolverStrategy getPresentationPropertyAttributeResolverStrategy();

	/**
	 * Gets the qualifier bundle.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param qualifier
	 *            The qualifier.
	 * @return The bundle, non-qualifier bundle or null.
	 */
	String getQualifierBundle(final DescribedEntity<?> entityType, final Class<?> qualifier);

	/**
	 * Gets all qualifiers.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @return The qualifiers.
	 */
	Set<Class<?>> getQualifiers(final DescribedEntity<?> entityType);

	/**
	 * Updates the presentation attribute resolver.
	 * 
	 * @param attributeResolverStrategy
	 *            The attribute resolver.
	 * @return The context.
	 */
	PresQualBuilderContext presentationEntityAttributeResolverStrategy(
			final PresentationEntityAttributeResolverStrategy attributeResolverStrategy);

	/**
	 * Updates the presentation attribute resolver.
	 * 
	 * @param attributeResolverStrategy
	 *            The attribute resolver.
	 * @return The context.
	 */
	PresQualBuilderContext presentationOperationAttributeResolverStrategy(
			final PresentationOperationAttributeResolverStrategy attributeResolverStrategy);

	/**
	 * Updates the presentation attribute resolver.
	 * 
	 * @param attributeResolverStrategy
	 *            The attribute resolver.
	 * @return The context.
	 */
	PresQualBuilderContext presentationPropertyAttributeResolverStrategy(
			final PresentationPropertyAttributeResolverStrategy attributeResolverStrategy);

	/**
	 * Puts a bundle.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param qualifier
	 *            The qualifier.
	 * @param bundle
	 *            The bundle.
	 */
	void putBundle(final DescribedEntity<?> entityType, final Class<?> qualifier, final String bundle);

	/**
	 * Puts a bundle.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param bundle
	 *            The bundle.
	 */
	void putBundle(final DescribedEntity<?> entityType, final String bundle);

	/**
	 * Puts a qualifier.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param qualifier
	 *            The qualifier.
	 */
	void putQualifier(final DescribedEntity<?> entityType, final Class<?> qualifier);
}
