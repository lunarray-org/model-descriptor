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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.serialization.ConstructorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A creational strategy for constructors.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <F>
 *            The impl type.
 */
public final class ConstructorStrategy<E, F extends E>
		implements CreationalStrategy<E> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConstructorStrategy.class);
	/** The serial id. */
	private static final long serialVersionUID = 2038197749894963257L;
	/** The constructor. */
	private final transient Constructor<F> constructor;

	/**
	 * Default constructor.
	 * 
	 * @param constructor
	 *            The constructor. May not be null.
	 */
	public ConstructorStrategy(final Constructor<F> constructor) {
		Validate.notNull(constructor, "Constructor may not be null.");
		this.constructor = constructor;
	}

	/**
	 * Gets the value for the constructor field.
	 * 
	 * @return The value for the constructor field.
	 */
	public Constructor<F> getConstructor() {
		return this.constructor;
	}

	/** {@inheritDoc} */
	@Override
	public E getInstance() throws CreationException {
		ConstructorStrategy.LOGGER.debug("Getting instance with constructor {}", this.constructor);
		try {
			return this.constructor.newInstance();
		} catch (final InstantiationException e) {
			throw new CreationException("Could create instance.", e);
		} catch (final IllegalAccessException e) {
			throw new CreationException("Could not access.", e);
		} catch (final InvocationTargetException e) {
			throw new CreationException("Could not invoke.", e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ConstructorCreationalStrategy [");
		builder.append("\n\tConstructor: ").append(this.constructor.getName());
		builder.append("\n]");
		return builder.toString();
	}

	/**
	 * Write the replacement.
	 * 
	 * @return The serialization proxy.
	 */
	private Object writeReplace() {
		return new StrategyProxy<E, F>(this.constructor);
	}

	/**
	 * Serialization proxy.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <E>
	 *            The entity type.
	 */
	protected static final class StrategyProxy<E, F extends E>
			implements Serializable {

		/** Serial id. */
		private static final long serialVersionUID = -2307202755089826915L;
		/** The constructor. */
		private ConstructorInfo<F> constructor;

		/**
		 * Default constructor.
		 * 
		 * @param constructor
		 *            The constructor.
		 */
		public StrategyProxy(final Constructor<F> constructor) {
			this.constructor = new ConstructorInfo<F>(constructor);
		}

		/**
		 * Gets the value for the constructor field.
		 * 
		 * @return The value for the constructor field.
		 */
		public ConstructorInfo<F> getConstructor() {
			return this.constructor;
		}

		/**
		 * Sets a new value for the constructor field.
		 * 
		 * @param constructor
		 *            The new value for the constructor field.
		 */
		public void setConstructor(final ConstructorInfo<F> constructor) {
			this.constructor = constructor;
		}

		/**
		 * Gets the instance for this property.
		 * 
		 * @return The property.
		 */
		private Object readResolve() {
			return new ConstructorStrategy<E, F>(this.constructor.resolveMethod());
		}
	}
}
