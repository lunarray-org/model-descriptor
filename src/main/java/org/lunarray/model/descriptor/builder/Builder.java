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
package org.lunarray.model.descriptor.builder;

import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;

/**
 * Describes a builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The resource type.
 * @param <S>
 *            The entity super type.
 * @param <M>
 *            The model type.
 * @param <B>
 *            The builder type.
 */

public interface Builder<R, S, M extends Model<S>, B extends Builder<R, S, M, B>> {

	/**
	 * Builds the model.
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
	B configuration(Configuration configuration);

	/**
	 * Add extensions.
	 * 
	 * @param extensions
	 *            The extensions to add.
	 * @return The builder.
	 */
	B extensions(Extension... extensions);

	/**
	 * Add post processors.
	 * 
	 * @param postProcessors
	 *            The postprocessors to add.
	 * @return The builder.
	 */
	B postProcessors(ModelProcessor<S>... postProcessors);

	/**
	 * Sets the resource.
	 * 
	 * @param resource
	 *            The resource.
	 * @return The builder.
	 * @throws ResourceException
	 *             Thrown if the resources could not be resolved.
	 */
	B resources(Resource<R> resource) throws ResourceException;
}
