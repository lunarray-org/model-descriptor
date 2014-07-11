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
package org.lunarray.model.descriptor.builder.annotation.util;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.ParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.model.relation.RelationType;

/**
 * Utility for some generic property builder processing.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum ParameterBuilderUtil {
	/** Instance. */
	INSTANCE;

	/**
	 * Updates the parameter relation.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param builder
	 *            The builder.
	 * @param type
	 *            The desired type.
	 * @param parameter
	 *            The parameter.
	 */
	public static <P, E> void updateRelation(final AnnotationParameterDescriptorBuilder<P, ? extends AnnotationBuilderContext> builder,
			final DescribedEntity<?> type, final DescribedParameter<P> parameter) {
		Validate.notNull(builder, "Builder may not be null.");
		Validate.notNull(type, "Entity may not be null.");
		Validate.notNull(parameter, "Parameter may not be null.");
		// Resolve relation.
		String relationName = null;
		final AnnotationBuilderContext ctx = builder.getBuilderContext();
		RelationType relationType = null;
		if (ctx.containsEntityName(type)) {
			relationName = ctx.getEntityName(type);
			relationType = RelationType.CONCRETE;
		}
		final ParameterAttributeResolverStrategy resolver = ctx.getParameterAttributeResolverStrategy();
		if (resolver.isReference(parameter)) {
			final Class<?> refType = resolver.getReferenceRelation(parameter);
			final DescribedEntity<?> described = ctx.getEntityResolverStrategy().resolveEntity(refType);
			if (ctx.containsEntityName(described)) {
				relationName = ctx.getEntityName(described);
				relationType = RelationType.REFERENCE;
			}
		}
		builder.relatedName(relationName);
		if (RelationType.CONCRETE == relationType) {
			builder.related();
		} else if (RelationType.REFERENCE == relationType) {
			builder.relatedReference();
		} else {
			builder.unrelated();
		}
	}
}
