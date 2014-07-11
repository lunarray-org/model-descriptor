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
package org.lunarray.model.descriptor.objectfactory.registry;

import java.io.Serializable;

import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An object factory that gets instances by delegating it to a registry.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class RegistryObjectFactory
		implements ObjectFactory, Serializable {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistryObjectFactory.class);
	/** Serial id. */
	private static final long serialVersionUID = -6775095907764403855L;
	/** The registry. */
	private transient ExtensionRef<Registry<?>> registry;

	/**
	 * Default constructor.
	 */
	public RegistryObjectFactory() {
		this.registry = null;
	}

	/** {@inheritDoc} */
	@Override
	public <O> O getInstance(final Class<O> type) throws InstanceException {
		RegistryObjectFactory.LOGGER.debug("Getting instance for type {}.", type);
		try {
			return this.registry.get().lookup(type);
		} catch (final RegistryException e) {
			throw new InstanceException(String.format("Could not find bean with type '%s'.", type), e);
		}
	}

	/**
	 * Sets the registry.
	 * 
	 * @param registry
	 *            The registry.
	 */
	public void setRegistry(final ExtensionRef<Registry<?>> registry) {
		this.registry = registry;
	}
}
