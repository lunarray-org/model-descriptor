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
package org.lunarray.model.descriptor.registry;

import java.util.Set;

import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;

/**
 * Describes a registry.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <N>
 *            The name type of registry beans.
 */
public interface Registry<N>
		extends Extension {

	/**
	 * Looks up a bean with a given type.
	 * 
	 * @param <B>
	 *            The bean type.
	 * @param beanType
	 *            The bean type. May not be null.
	 * @return The bean.
	 * @throws RegistryException
	 *             Thrown if the bean could not be accessed.
	 */
	<B> B lookup(Class<B> beanType) throws RegistryException;

	/**
	 * Looks up a bean with a given type and name.
	 * 
	 * @param <B>
	 *            The bean type.
	 * @param beanType
	 *            The bean type. Has to be set if beanName is not set.
	 * @param beanName
	 *            The bean name. Has to be set if beanType is not set.
	 * @return The bean.
	 * @throws RegistryException
	 *             Thrown if the bean could not be accessed.
	 */
	<B> B lookup(Class<B> beanType, N beanName) throws RegistryException;

	/**
	 * Looks up a bean with a given name.
	 * 
	 * @param beanName
	 *            The bean name. May not be null.
	 * @return The bean.
	 * @throws RegistryException
	 *             Thrown if the bean could not be accessed.
	 */
	Object lookup(N beanName) throws RegistryException;

	/**
	 * Gets all names and instances associated with a given bean.
	 * 
	 * @param <B>
	 *            The bean type.
	 * @param beanType
	 *            The bean type. May not be null.
	 * @return The set of bean names.
	 * @throws RegistryException
	 *             Thrown if the bean could not be accessed.
	 */
	<B> Set<N> lookupAll(Class<B> beanType) throws RegistryException;
}
