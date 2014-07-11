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

import java.util.Collection;

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.builder.annotation.base.build.AnnotationBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationExtension;

/**
 * Describes an annotation builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public interface AnnotationOperationDescriptorBuilder<E, B extends AnnotationBuilderContext>
		extends AnnotationBuilder<B> {

	/**
	 * Adds a collection parameter.
	 * 
	 * @return A collection parameter builder.
	 * @param <P>
	 *            The parameter type.
	 * @param <C>
	 *            The collection type.
	 */
	<C, P extends Collection<C>> AnnotationCollectionParameterDescriptorBuilder<C, P, B> addCollectionParameter();

	/**
	 * Adds a parameter.
	 * 
	 * @return A collection parameter builder.
	 * @param <P>
	 *            The parameter type.
	 */
	<P> AnnotationParameterDescriptorBuilder<P, B> addParameter();

	/**
	 * Build the descriptor.
	 * 
	 * @return The descriptor.
	 */
	OperationDescriptor<E> build();

	/**
	 * Gets the result type descriptor builder.
	 * 
	 * @return The result type descriptor builder.
	 * @param <C>
	 *            The collection type.
	 * @param <R>
	 *            The result type.
	 */
	<C, R extends Collection<C>> AnnotationCollectionResultDescriptorBuilder<C, R, E, B> createCollectionResultTypeBuilder();

	/**
	 * Gets the result type descriptor builder.
	 * 
	 * @return The result type descriptor builder.
	 * @param <R>
	 *            The result type.
	 */
	<R> AnnotationResultDescriptorBuilder<R, E, B> createResultTypeBuilder();

	/**
	 * Updates the entity type.
	 * 
	 * @param entityType
	 *            the entity type.
	 * @return The builder.
	 */
	AnnotationOperationDescriptorBuilder<E, B> entityType(final DescribedEntity<E> entityType);

	/**
	 * Gets the field.
	 * 
	 * @return The field.
	 */
	DescribedOperation getOperation();

	/**
	 * Gets the operation reference.
	 * 
	 * @return The operation reference.
	 */
	OperationReference<E> getOperationReference();

	/**
	 * Gets the result type descriptor builder.
	 * 
	 * @return The result type descriptor builder.
	 * @param <R>
	 *            The result type.
	 */
	<R> AnnotationResultDescriptorBuilder<R, E, B> getResultBuilder();

	/**
	 * Updates the operation name.
	 * 
	 * @param name
	 *            The name.
	 * @return The builder.
	 */
	AnnotationOperationDescriptorBuilder<E, B> name(final String name);

	/**
	 * Sets the operation.
	 * 
	 * @param operation
	 *            The operation.
	 * @return The builder.
	 */
	AnnotationOperationDescriptorBuilder<E, B> operation(final DescribedOperation operation);

	/**
	 * Updates the operation reference.
	 * 
	 * @param valueReference
	 *            The operation reference.
	 * @return The builder.
	 */
	AnnotationOperationDescriptorBuilder<E, B> operationReference(final OperationReference<E> valueReference);

	/**
	 * Register an extension.
	 * 
	 * @param extensionInt
	 *            The extension interface.
	 * @param extension
	 *            The extension.
	 */
	void registerExtension(final Class<? extends OperationExtension<E>> extensionInt, final OperationExtension<E> extension);
}
