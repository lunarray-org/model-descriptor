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
package org.lunarray.model.descriptor.creational.impl;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry resolving factory strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <N>
 *            The registry name type.
 */
public final class RegistryCreationalStrategy<E, N extends Serializable>
		implements CreationalStrategy<E> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistryCreationalStrategy.class);
	/** Serial id. */
	private static final long serialVersionUID = -312006641413152731L;
	/** The key. */
	private N key;
	/** The registry ref. */
	private ExtensionRef<Registry<N>> registry;
	/** The target. */
	private Class<E> target;

	/**
	 * Default constructor.
	 * 
	 * @param target
	 *            The target type. May not be null.
	 * @param registry
	 *            The registry. May not be null.
	 * @param key
	 *            The key.
	 */
	protected RegistryCreationalStrategy(final Class<E> target, final ExtensionRef<Registry<N>> registry, final N key) {
		Validate.notNull(target, "Target may not be null.");
		Validate.notNull(registry, "Registry may not be null.");
		this.target = target;
		this.registry = registry;
		this.key = key;
	}

	/** {@inheritDoc} */
	@Override
	public E getInstance() throws CreationException {
		E result;
		try {
			if (CheckUtil.isNull(this.key)) {
				RegistryCreationalStrategy.LOGGER.debug("Getting instance from registry {} of type {}", this.registry, this.target);
				result = this.registry.get().lookup(this.target);
			} else {
				RegistryCreationalStrategy.LOGGER.debug("Getting instance from registry {} of type {} with key {}", this.registry,
						this.target, this.key);
				result = this.registry.get().lookup(this.target, this.key);
			}
		} catch (final RegistryException e) {
			throw new CreationException("Could not lookup in registry.", e);
		}
		return result;
	}

	/**
	 * Gets the value for the key field.
	 * 
	 * @return The value for the key field.
	 */
	public N getKey() {
		return this.key;
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
	 * Gets the value for the target field.
	 * 
	 * @return The value for the target field.
	 */
	public Class<E> getTarget() {
		return this.target;
	}

	/**
	 * Sets a new value for the key field.
	 * 
	 * @param key
	 *            The new value for the key field.
	 */
	public void setKey(final N key) {
		this.key = key;
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

	/**
	 * Sets a new value for the target field.
	 * 
	 * @param target
	 *            The new value for the target field.
	 */
	public void setTarget(final Class<E> target) {
		this.target = target;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RegistryFactoryCreationalStrategy [");
		builder.append("\n\tTarget: ").append(this.target);
		if (!CheckUtil.isNull(this.key)) {
			builder.append("\n\tKey: ").append(this.key);
		}
		if (!CheckUtil.isNull(this.registry)) {
			builder.append("\n\tRegistry: ").append(this.registry);
		}
		builder.append("\n]");
		return builder.toString();
	}
}
