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
package org.lunarray.model.descriptor.builder.annotation.base.build.model;

import org.lunarray.model.descriptor.builder.Configuration;
import org.lunarray.model.descriptor.builder.annotation.base.build.AnnotationBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;

/**
 * Describes an annotation model builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The entity super type.
 * @param <M>
 *            The model type.
 * @param <B>
 *            The builder context type.
 */
public interface AnnotationModelBuilder<S, M extends Model<S>, B extends AnnotationBuilderContext>
		extends AnnotationBuilder<B> {

	/**
	 * Adds an entity.
	 * 
	 * @param <E>
	 *            Entity type.
	 * @return The builder.
	 */
	<E extends S> AnnotationEntityDescriptorBuilder<E, ?, B> addEntity();

	/**
	 * Build the model.
	 * 
	 * @return The model.
	 */
	M build();

	/**
	 * Sets the configuration.
	 * 
	 * @param configuration
	 *            The configuration.
	 * @return The builder.
	 */
	AnnotationModelBuilder<S, M, B> configuration(Configuration configuration);

	/**
	 * Adds post processors.
	 * 
	 * @param postProcessors
	 *            The post processors to add.
	 * @return The builder.
	 */
	AnnotationModelBuilder<S, M, B> postProcessors(final ModelProcessor<S>... postProcessors);

	/**
	 * Sets the resource.
	 * 
	 * @param resource
	 *            The resources.
	 * @return The builder.
	 * @throws ResourceException
	 *             Thrown if the resource could not be resolved.
	 */
	AnnotationModelBuilder<S, M, B> resource(final Resource<Class<? extends S>> resource) throws ResourceException;
}
