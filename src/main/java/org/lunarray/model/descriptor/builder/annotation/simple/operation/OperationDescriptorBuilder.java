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

import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractOperationDescriptorBuilder;

/**
 * Property descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class OperationDescriptorBuilder<E>
		extends AbstractOperationDescriptorBuilder<E, AnnotationBuilderContext> {

	/**
	 * Creates a builder.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <E> OperationDescriptorBuilder<E> create(final AnnotationBuilderContext builderContext) {
		return new OperationDescriptorBuilder<E>(builderContext);
	}

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context.
	 */
	private OperationDescriptorBuilder(final AnnotationBuilderContext builderContext) {
		super(builderContext);
	}

	/** {@inheritDoc} */
	@Override
	public OperationDescriptor<Object, E> createOperationDescriptor() {
		return new OperationDescriptor<Object, E>(this);
	}

	/** {@inheritDoc} */
	@Override
	protected <P> AnnotationParameterDescriptorBuilder<P, AnnotationBuilderContext> createBuilder() {
		return ParameterDescriptorBuilder.create(this.getBuilderContext());
	}

	/** {@inheritDoc} */
	@Override
	protected <C, P extends Collection<C>> AnnotationCollectionParameterDescriptorBuilder<C, P, AnnotationBuilderContext> createCollectionBuilder() {
		return CollectionParameterDescriptorBuilder.create(this.getBuilderContext());
	}

	/** {@inheritDoc} */
	@Override
	protected <C, R extends Collection<C>> AnnotationCollectionResultDescriptorBuilder<C, R, E, AnnotationBuilderContext> createCollectionResultDescriptorBuilder() {
		return CollectionResultDescriptorBuilder.create(this.getOperation(), this.getBuilderContext());
	}

	/** {@inheritDoc} */
	@Override
	protected <R> AnnotationResultDescriptorBuilder<R, E, AnnotationBuilderContext> createResultDescriptorBuilder() {
		return ResultDescriptorBuilder.create(this.getOperation(), this.getBuilderContext());
	}
}
