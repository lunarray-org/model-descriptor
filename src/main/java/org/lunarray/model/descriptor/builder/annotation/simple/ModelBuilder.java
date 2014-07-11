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
package org.lunarray.model.descriptor.builder.annotation.simple;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.builders.model.AbstractModelBuilder;
import org.lunarray.model.descriptor.builder.annotation.simple.entity.EntityDescriptorBuilder;

/**
 * A model builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The model super type.
 */
public final class ModelBuilder<S>
		extends AbstractModelBuilder<S, ModelImpl<S>, AnnotationBuilderContext> {

	/**
	 * Creates a new builder.
	 * 
	 * @param <S>
	 *            The entity super type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The new builder.
	 */
	public static <S> ModelBuilder<S> create(final AnnotationBuilderContext builderContext) {
		Validate.notNull(builderContext, "Builder context must be set.");
		return new ModelBuilder<S>(builderContext);
	}

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context.
	 */
	private ModelBuilder(final AnnotationBuilderContext builderContext) {
		super(builderContext);
	}

	/** {@inheritDoc} */
	@Override
	protected EntityDescriptorBuilder<S, ? extends Serializable> createEntityBuilder() {
		return EntityDescriptorBuilder.create(this.getBuilderContext());
	}

	/** {@inheritDoc} */
	@Override
	protected ModelImpl<S> createModel() {
		return new ModelImpl<S>(this);
	}
}
