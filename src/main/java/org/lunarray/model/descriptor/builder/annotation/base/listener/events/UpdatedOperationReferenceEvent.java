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
package org.lunarray.model.descriptor.builder.annotation.base.listener.events;

import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;

/**
 * An event propagated when a operation type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public final class UpdatedOperationReferenceEvent<E, B extends AnnotationBuilderContext>
		extends AbstractOperationReferenceEvent<E, B> {
	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param operation
	 *            The operation.
	 * @param operationReference
	 *            The value reference.
	 */
	public UpdatedOperationReferenceEvent(final AnnotationOperationDescriptorBuilder<E, B> builder, final DescribedOperation operation,
			final OperationReference<E> operationReference) {
		super(builder, operation, operationReference);
	}
}
