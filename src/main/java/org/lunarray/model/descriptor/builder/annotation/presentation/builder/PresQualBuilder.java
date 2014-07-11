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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder;

import org.lunarray.model.descriptor.builder.annotation.base.build.model.AnnotationModelBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.builder.AbstractAnnotationBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.context.BuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;

/**
 * The builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The entity super type.
 */
public final class PresQualBuilder<S>
		extends AbstractAnnotationBuilder<S, ModelImpl<S>, PresQualBuilderContext, PresQualBuilder<S>> {

	/**
	 * Creates the builder.
	 * 
	 * @param <S>
	 *            Entity super type.
	 * @return The builder.
	 */
	public static <S> PresQualBuilder<S> createBuilder() {
		return new PresQualBuilder<S>(BuilderContext.create());
	}

	/**
	 * Default constructor.
	 * 
	 * @param builderContext
	 *            The builder context.
	 */
	private PresQualBuilder(final PresQualBuilderContext builderContext) {
		super(builderContext);
	}

	/** {@inheritDoc} */
	@Override
	protected AnnotationModelBuilder<S, ModelImpl<S>, PresQualBuilderContext> createModelBuilder() {
		return ModelBuilder.create(this.getBuilderContext());
	}

	/** {@inheritDoc} */
	@Override
	protected PresQualBuilder<S> getBuilder() {
		return this;
	}
}
