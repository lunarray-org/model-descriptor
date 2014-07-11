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
package org.lunarray.model.descriptor.builder.annotation.simple.operation;

import java.util.Collection;

import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractCollectionResultDescriptorBuilder;

/**
 * A collection result descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The result type.
 * @param <P>
 *            The collection type.
 * @param <E>
 *            The entity type.
 */
public final class CollectionResultDescriptorBuilder<C, P extends Collection<C>, E>
		extends AbstractCollectionResultDescriptorBuilder<C, P, E, AnnotationBuilderContext> {

	/**
	 * Creates a builder.
	 * 
	 * @param <P>
	 *            The result type.
	 * @param <E>
	 *            The entity type.
	 * @param <C>
	 *            The collection type.
	 * @param operation
	 *            The operation.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <C, P extends Collection<C>, E> CollectionResultDescriptorBuilder<C, P, E> create(final DescribedOperation operation,
			final AnnotationBuilderContext builderContext) {
		return new CollectionResultDescriptorBuilder<C, P, E>(operation, builderContext);
	}

	/**
	 * Constructs the builder.
	 * 
	 * @param operation
	 *            The operation.
	 * @param builderContext
	 *            The builder context.
	 */
	private CollectionResultDescriptorBuilder(final DescribedOperation operation, final AnnotationBuilderContext builderContext) {
		super(operation, builderContext);
	}

	/** {@inheritDoc} */
	@Override
	protected CollectionResultDescriptor<C, P> createResultDescriptor() {
		return new CollectionResultDescriptor<C, P>(this);
	}
}
