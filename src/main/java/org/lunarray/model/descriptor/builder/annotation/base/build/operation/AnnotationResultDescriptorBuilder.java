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
package org.lunarray.model.descriptor.builder.annotation.base.build.operation;

import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.build.AnnotationBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;

/**
 * Describes an annotation builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public interface AnnotationResultDescriptorBuilder<R, E, B extends AnnotationBuilderContext>
		extends AnnotationBuilder<B> {

	/**
	 * Build the descriptor.
	 * 
	 * @return The descriptor.
	 */
	ResultDescriptor<R> build();

	/**
	 * Sets the cardinality.
	 * 
	 * @param cardinality
	 *            The cardinality.
	 * @return The builder.
	 */
	AnnotationResultDescriptorBuilder<R, E, B> cardinality(final Cardinality cardinality);

	/**
	 * Gets the cardinality.
	 * 
	 * @return The cardinality.
	 */
	Cardinality getCardinality();

	/**
	 * Gets the operation.
	 * 
	 * @return The operation.
	 */
	DescribedOperation getOperation();

	/**
	 * Gets the relation type.
	 * 
	 * @return The type of relation or null for unrelated.
	 */
	RelationType getRelationType();

	/**
	 * Gets the result type.
	 * 
	 * @return The result type.
	 */
	Class<R> getResultType();

	/**
	 * Makes this result related to a concrete type.
	 * 
	 * @return The builder.
	 */
	AnnotationResultDescriptorBuilder<R, E, B> related();

	/**
	 * Provides the related entity name.
	 * 
	 * @param relatedName
	 *            The related entity name.
	 * @return The builder.
	 */
	AnnotationResultDescriptorBuilder<R, E, B> relatedName(final String relatedName);

	/**
	 * Makes this result related to a reference.
	 * 
	 * @return The builder.
	 */
	AnnotationResultDescriptorBuilder<R, E, B> relatedReference();

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            The type.
	 * @return The builder.
	 */
	AnnotationResultDescriptorBuilder<R, E, B> type(final Class<?> type);

	/**
	 * Make result unrelated.
	 * 
	 * @return The builder.
	 */
	AnnotationResultDescriptorBuilder<R, E, B> unrelated();
}
