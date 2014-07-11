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
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;

/**
 * A collection property descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The property type.
 * @param <P>
 *            The collection type.
 */
public final class CollectionParameterDescriptorBuilder<C, P extends Collection<C>>
		extends AbstractCollectionParameterDescriptorBuilder<C, P, AnnotationBuilderContext> {

	/**
	 * Creates a builder.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <C>
	 *            The collection type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <C, P extends Collection<C>> CollectionParameterDescriptorBuilder<C, P> create(
			final AnnotationBuilderContext builderContext) {
		return new CollectionParameterDescriptorBuilder<C, P>(builderContext);
	}

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context.
	 */
	private CollectionParameterDescriptorBuilder(final AnnotationBuilderContext builderContext) {
		super(builderContext);
	}

	/** {@inheritDoc} */
	@Override
	protected ParameterDescriptor<P> createParameterDescriptor() {
		return new CollectionParameterDescriptor<C, P>(this);
	}
}
