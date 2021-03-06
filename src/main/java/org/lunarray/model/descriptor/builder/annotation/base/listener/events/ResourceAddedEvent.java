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

import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.model.AnnotationModelBuilder;
import org.lunarray.model.descriptor.resource.Resource;

/**
 * A resource added event.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <B>
 *            The builder context.
 * @param <S>
 *            The model super type.
 */
public final class ResourceAddedEvent<S, B extends AnnotationBuilderContext> {
	/** The builder. */
	private final transient AnnotationModelBuilder<S, ?, B> builder;
	/** The resource. */
	private final transient Resource<Class<? extends S>> resource;

	/**
	 * Default constructor.
	 * 
	 * @param resource
	 *            The resource.
	 * @param builder
	 *            The builder.
	 */
	public ResourceAddedEvent(final Resource<Class<? extends S>> resource, final AnnotationModelBuilder<S, ?, B> builder) {
		this.resource = resource;
		this.builder = builder;
	}

	/**
	 * Gets the value for the builder field.
	 * 
	 * @return The value for the builder field.
	 */
	public AnnotationModelBuilder<S, ?, B> getBuilder() {
		return this.builder;
	}

	/**
	 * Gets the value for the resource field.
	 * 
	 * @return The value for the resource field.
	 */
	public Resource<Class<? extends S>> getResource() {
		return this.resource;
	}
}
