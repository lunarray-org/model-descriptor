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
package org.lunarray.model.descriptor.creational.domain;

import java.util.HashSet;
import java.util.Set;

import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;

/**
 * Stub object factory.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class CreationalEntityRegistry
		implements Registry<String> {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public <BeanType> BeanType lookup(final Class<BeanType> beanType) throws RegistryException {
		if (CreationalEntity13Factory.class.equals(beanType)) {
			return (BeanType) new CreationalEntity13Factory();
		}
		return null;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public <BeanType> BeanType lookup(final Class<BeanType> beanType, final String beanName) throws RegistryException {
		if (CreationalEntity14Factory.class.equals(beanType)) {
			return (BeanType) new CreationalEntity14Factory();
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Object lookup(final String beanName) throws RegistryException {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public <BeanType> Set<String> lookupAll(final Class<BeanType> beanType) throws RegistryException {
		return new HashSet<String>();
	}
}
