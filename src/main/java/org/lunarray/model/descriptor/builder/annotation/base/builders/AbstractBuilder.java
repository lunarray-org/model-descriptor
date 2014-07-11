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
package org.lunarray.model.descriptor.builder.annotation.base.builders;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.base.build.AnnotationBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;

/**
 * Base abstract builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <B>
 *            The builder context type.
 */
public abstract class AbstractBuilder<B extends AnnotationBuilderContext>
		implements AnnotationBuilder<B> {

	/** The builder context. */
	private final transient B builderContextBuilder;

	/**
	 * Default constructor.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	protected AbstractBuilder(final B builderContext) {
		Validate.notNull(builderContext, "Builder context may not be null.");
		this.builderContextBuilder = builderContext;
	}

	/** {@inheritDoc} */
	@Override
	public final B getBuilderContext() {
		return this.builderContextBuilder;
	}
}
