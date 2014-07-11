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

import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;

/**
 * An event propagated when a parameter type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 * @param <B>
 *            The builder type.
 */
public abstract class AbstractParameterEvent<P, B extends AnnotationBuilderContext> {

	/** The builder. */
	private final transient AnnotationParameterDescriptorBuilder<P, B> builder;
	/** The parameter. */
	private final transient DescribedParameter<P> parameter;

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param parameter
	 *            The parameter
	 */
	public AbstractParameterEvent(final AnnotationParameterDescriptorBuilder<P, B> builder, final DescribedParameter<P> parameter) {
		this.builder = builder;
		this.parameter = parameter;
	}

	/**
	 * Gets the value for the builder field.
	 * 
	 * @return The value for the builder field.
	 */
	public final AnnotationParameterDescriptorBuilder<P, B> getBuilder() {
		return this.builder;
	}

	/**
	 * Gets the value for the parameter field.
	 * 
	 * @return The value for the parameter field.
	 */
	public final DescribedParameter<P> getParameter() {
		return this.parameter;
	}
}
