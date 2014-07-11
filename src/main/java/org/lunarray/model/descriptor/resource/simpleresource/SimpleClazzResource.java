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
package org.lunarray.model.descriptor.resource.simpleresource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.resource.Resource;

/**
 * A simple collection based resource.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The marker type.
 */
public final class SimpleClazzResource<S>
		implements Resource<Class<? extends S>> {

	/** The collection of classes. */
	private final transient Set<Class<? extends S>> resources;

	/**
	 * Constructs the resource.
	 */
	public SimpleClazzResource() {
		this.resources = new HashSet<Class<? extends S>>();
	}

	/**
	 * Constructs the resource with a given array of resources.
	 * 
	 * @param resources
	 *            The resources.
	 */
	public SimpleClazzResource(final Class<? extends S>... resources) {
		this();
		this.addClazzes(resources);
	}

	/**
	 * Constructs the resource with a given collection of resources.
	 * 
	 * @param resources
	 *            The resources.
	 */
	public SimpleClazzResource(final Collection<Class<? extends S>> resources) {
		this();
		this.addClazzes(resources);
	}

	/**
	 * Adds a resource.
	 * 
	 * @param resource
	 *            The resource to add.
	 * @return This resource.
	 */
	public SimpleClazzResource<S> addClazz(final Class<? extends S> resource) {
		this.resources.add(resource);
		return this;
	}

	/**
	 * Adds an array of resources.
	 * 
	 * @param resources
	 *            The resources to add.
	 * @return This resource.
	 */
	public SimpleClazzResource<S> addClazzes(final Class<? extends S>... resources) {
		for (final Class<? extends S> clazz : resources) {
			this.resources.add(clazz);
		}
		return this;
	}

	/**
	 * Adds a collection of resources.
	 * 
	 * @param resources
	 *            The resources to add. May not be null.
	 * @return This resource.
	 */
	public SimpleClazzResource<S> addClazzes(final Collection<Class<? extends S>> resources) {
		Validate.notNull(resources, "Resources may not be null.");
		this.resources.addAll(resources);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Class<? extends S>> getResources() {
		return new ArrayList<Class<? extends S>>(this.resources);
	}
}
