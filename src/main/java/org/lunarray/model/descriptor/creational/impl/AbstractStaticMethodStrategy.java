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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.serialization.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abtract static methad strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <F>
 *            Type coersion type.
 */
public abstract class AbstractStaticMethodStrategy<E, F extends E>
		implements CreationalStrategy<E> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStaticMethodStrategy.class);
	/** The serial id. */
	private static final long serialVersionUID = 8905043005380232831L;
	/** The factory. method. */
	private final transient Method factoryMethod;
	/** Target type. */
	private final transient Class<F> type;

	/**
	 * Default constructor.
	 * 
	 * @param factoryMethod
	 *            The factory method. May not be null.
	 * @param type
	 *            The type. May not be null.
	 */
	protected AbstractStaticMethodStrategy(final Method factoryMethod, final Class<F> type) {
		Validate.notNull(factoryMethod, "Factory method may not be null.");
		Validate.notNull(type, "Type may not be null.");
		this.factoryMethod = factoryMethod;
		this.type = type;
	}

	/**
	 * Gets the value for the factoryMethod field.
	 * 
	 * @return The value for the factoryMethod field.
	 */
	public final Method getFactoryMethod() {
		return this.factoryMethod;
	}

	/** {@inheritDoc} */
	@Override
	public final E getInstance() throws CreationException {
		AbstractStaticMethodStrategy.LOGGER.debug("Creating instance with factory method {} to type {}", this.factoryMethod, this.type);
		try {
			return this.type.cast(this.factoryMethod.invoke(null));
		} catch (final IllegalAccessException e) {
			throw new CreationException("Could not access.", e);
		} catch (final InvocationTargetException e) {
			throw new CreationException("Could not invoke.", e);
		}
	}

	/**
	 * Gets the value for the type field.
	 * 
	 * @return The value for the type field.
	 */
	public final Class<F> getType() {
		return this.type;
	}

	/**
	 * Implements abstract to string part.
	 * 
	 * @param builder
	 *            The string builder
	 */
	protected final void abstractToString(final StringBuilder builder) {
		builder.append("\n\tFactory Method: ").append(this.factoryMethod.toGenericString());
		builder.append("\n\tInstance target: ").append(this.type.getName());
	}

	/**
	 * The abstract proxy.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <E>
	 *            The entity.
	 * @param <F>
	 *            The factory.
	 */
	protected abstract static class AbstractProxy<E, F>
			implements Serializable {
		/** The serial id. */
		private static final long serialVersionUID = -6194501403048960974L;
		/** The factory. method. */
		private MethodInfo factoryMethod;
		/** Target type. */
		private Class<F> type;

		/**
		 * Default constructor.
		 * 
		 * @param factoryMethod
		 *            The factory method.
		 * @param type
		 *            The type.
		 */
		protected AbstractProxy(final Method factoryMethod, final Class<F> type) {
			this.factoryMethod = new MethodInfo(factoryMethod);
			this.type = type;
		}

		/**
		 * Gets the value for the factoryMethod field.
		 * 
		 * @return The value for the factoryMethod field.
		 */
		public final MethodInfo getFactoryMethod() {
			return this.factoryMethod;
		}

		/**
		 * Gets the value for the type field.
		 * 
		 * @return The value for the type field.
		 */
		public final Class<F> getType() {
			return this.type;
		}

		/**
		 * Sets a new value for the factoryMethod field.
		 * 
		 * @param factoryMethod
		 *            The new value for the factoryMethod field.
		 */
		public final void setFactoryMethod(final MethodInfo factoryMethod) {
			this.factoryMethod = factoryMethod;
		}

		/**
		 * Sets a new value for the type field.
		 * 
		 * @param type
		 *            The new value for the type field.
		 */
		public final void setType(final Class<F> type) {
			this.type = type;
		}
	}
}
