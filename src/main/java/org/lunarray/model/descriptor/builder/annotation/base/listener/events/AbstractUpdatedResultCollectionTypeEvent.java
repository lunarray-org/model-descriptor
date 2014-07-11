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

import java.util.Collection;

import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionResultDescriptorBuilder;

/**
 * An event propagated when a result type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 * @param <C>
 *            The collection type.
 * @param <B>
 *            The builder context type.
 */
public abstract class AbstractUpdatedResultCollectionTypeEvent<C, R extends Collection<C>, E, B extends AnnotationBuilderContext> {

	/** The builder. */
	private final transient AnnotationCollectionResultDescriptorBuilder<C, R, E, B> builder;
	/** The property. */
	private final transient DescribedOperation operation;

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param operation
	 *            The operation.
	 */
	protected AbstractUpdatedResultCollectionTypeEvent(final AnnotationCollectionResultDescriptorBuilder<C, R, E, B> builder,
			final DescribedOperation operation) {
		this.builder = builder;
		this.operation = operation;
	}

	/**
	 * Gets the value for the builder field.
	 * 
	 * @return The value for the builder field.
	 */
	public final AnnotationCollectionResultDescriptorBuilder<C, R, E, B> getBuilder() {
		return this.builder;
	}

	/**
	 * Gets the value for the operation field.
	 * 
	 * @return The value for the operation field.
	 */
	public final DescribedOperation getOperation() {
		return this.operation;
	}
}
