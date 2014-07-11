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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events;

import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.AbstractOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.PresQualOperationDescriptorBuilder;

/**
 * An event propagated when a operation entity type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class UpdatedPresentationOperationReferenceEvent<E>
		extends AbstractOperationReferenceEvent<E, PresQualBuilderContext> {

	/** The operation builder. */
	private final transient PresQualOperationDescriptorBuilder<E> operationBuilder;

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
	public UpdatedPresentationOperationReferenceEvent(final PresQualOperationDescriptorBuilder<E> builder, final DescribedOperation operation,
			final OperationReference<E> operationReference) {
		super(builder, operation, operationReference);
		this.operationBuilder = builder;
	}

	/**
	 * Gets the value for the operationBuilder field.
	 * 
	 * @return The value for the operationBuilder field.
	 */
	public PresQualOperationDescriptorBuilder<E> getOperationBuilder() {
		return this.operationBuilder;
	}
}
