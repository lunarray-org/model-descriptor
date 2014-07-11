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

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry object factory instance strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class ObjectFactoryInstanceStrategy<E>
		implements CreationalStrategy<E> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectFactoryInstanceStrategy.class);
	/** Serial id. */
	private static final long serialVersionUID = 8445320133469818417L;
	/** Object factory reference. */
	private ExtensionRef<ObjectFactory> objectFactory;
	/** The entity type. */
	private Class<? extends E> type;

	/**
	 * Default constructor.
	 * 
	 * @param objectFactory
	 *            The object factory. May not be null.
	 * @param type
	 *            The entity type. May not be null.
	 */
	protected ObjectFactoryInstanceStrategy(final ExtensionRef<ObjectFactory> objectFactory, final Class<? extends E> type) {
		Validate.notNull(objectFactory, "Object factory may not be null.");
		Validate.notNull(type, "Type may not be null.");
		this.objectFactory = objectFactory;
		this.type = type;
	}

	/** {@inheritDoc} */
	@Override
	public E getInstance() throws CreationException {
		ObjectFactoryInstanceStrategy.LOGGER.debug("Getting instance from object factory {} with type {}", this.objectFactory, this.type);
		try {
			return this.objectFactory.get().getInstance(this.type);
		} catch (final InstanceException e) {
			throw new CreationException("Could not get instance.", e);
		}
	}

	/**
	 * Gets the value for the objectFactory field.
	 * 
	 * @return The value for the objectFactory field.
	 */
	public ExtensionRef<ObjectFactory> getObjectFactory() {
		return this.objectFactory;
	}

	/**
	 * Gets the value for the type field.
	 * 
	 * @return The value for the type field.
	 */
	public Class<? extends E> getType() {
		return this.type;
	}

	/**
	 * Sets a new value for the objectFactory field.
	 * 
	 * @param objectFactory
	 *            The new value for the objectFactory field.
	 */
	public void setObjectFactory(final ExtensionRef<ObjectFactory> objectFactory) {
		this.objectFactory = objectFactory;
	}

	/**
	 * Sets a new value for the type field.
	 * 
	 * @param type
	 *            The new value for the type field.
	 */
	public void setType(final Class<? extends E> type) {
		this.type = type;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ObjectFactoryCreationalStrategy [");
		builder.append("\n\tInstance Target: ").append(this.type.getName());
		builder.append("\n\tObject Factory: ").append(this.objectFactory);
		builder.append("\n]");
		return builder.toString();
	}
}
