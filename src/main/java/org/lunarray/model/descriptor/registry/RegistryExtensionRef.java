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

import java.io.Serializable;

import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A weak extension reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <X>
 *            The extension type.
 * @param <N>
 *            The registry bean name type.
 */
public final class RegistryExtensionRef<X extends Extension, N extends Serializable>
		implements ExtensionRef<X> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistryExtensionRef.class);
	/** Serial id. */
	private static final long serialVersionUID = 1L;
	/** The extension reference. */
	private NamedRegistryExtensionRef<X, N> reference;
	/** The registry. */
	private ExtensionRef<Registry<N>> registry;

	/**
	 * Constructs the reference.
	 * 
	 * @param reference
	 *            The extension reference.
	 * @param registry
	 *            The registry.
	 */
	public RegistryExtensionRef(final NamedRegistryExtensionRef<X, N> reference, final ExtensionRef<Registry<N>> registry) {
		this.reference = reference;
		this.registry = registry;
	}

	/** {@inheritDoc} */
	@Override
	public X get() {
		X result = null;
		try {
			result = this.reference.lookup(this.registry.get());
		} catch (final RegistryException e) {
			RegistryExtensionRef.LOGGER.warn("Unable to get bean.", e);
		}
		return result;
	}

	/**
	 * Gets the value for the reference field.
	 * 
	 * @return The value for the reference field.
	 */
	public NamedRegistryExtensionRef<X, N> getReference() {
		return this.reference;
	}

	/**
	 * Gets the value for the registry field.
	 * 
	 * @return The value for the registry field.
	 */
	public ExtensionRef<Registry<N>> getRegistry() {
		return this.registry;
	}

	/**
	 * Sets a new value for the reference field.
	 * 
	 * @param reference
	 *            The new value for the reference field.
	 */
	public void setReference(final NamedRegistryExtensionRef<X, N> reference) {
		this.reference = reference;
	}

	/**
	 * Sets a new value for the registry field.
	 * 
	 * @param registry
	 *            The new value for the registry field.
	 */
	public void setRegistry(final ExtensionRef<Registry<N>> registry) {
		this.registry = registry;
	}
}
