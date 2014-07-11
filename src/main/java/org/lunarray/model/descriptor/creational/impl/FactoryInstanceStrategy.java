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
import org.lunarray.model.descriptor.creational.Factory;
import org.lunarray.model.descriptor.serialization.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registry factory instance strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <F>
 *            The factory type.
 */
public final class FactoryInstanceStrategy<E, F extends Factory>
		implements CreationalStrategy<E> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FactoryInstanceStrategy.class);
	/** Serial id. */
	private static final long serialVersionUID = 8445320133469818417L;
	/** The factory. */
	private final transient CreationalStrategy<F> factory;
	/** The factory method. */
	private final transient Method factoryMethod;
	/** The target. */
	private final transient Class<E> target;

	/**
	 * Default constructor.
	 * 
	 * @param factory
	 *            The factory type. May not be null.
	 * @param factoryMethod
	 *            The factory method. May not be null.
	 * @param target
	 *            The target type. May not be null.
	 */
	protected FactoryInstanceStrategy(final CreationalStrategy<F> factory, final Method factoryMethod, final Class<E> target) {
		Validate.notNull(factory, "Factory may not be null.");
		Validate.notNull(target, "Target may not be null.");
		Validate.notNull(factoryMethod, "Factory method may not be null.");
		this.factory = factory;
		this.factoryMethod = factoryMethod;
		this.target = target;
	}

	/**
	 * Gets the value for the factory field.
	 * 
	 * @return The value for the factory field.
	 */
	public CreationalStrategy<F> getFactory() {
		return this.factory;
	}

	/**
	 * Gets the value for the factoryMethod field.
	 * 
	 * @return The value for the factoryMethod field.
	 */
	public Method getFactoryMethod() {
		return this.factoryMethod;
	}

	/** {@inheritDoc} */
	@Override
	public E getInstance() throws CreationException {
		FactoryInstanceStrategy.LOGGER.debug("Getting instance with factory method {} and factory {} to type {}", this.factoryMethod,
				this.factory, this.target);
		try {
			return this.target.cast(this.factoryMethod.invoke(this.factory.getInstance()));
		} catch (final IllegalAccessException e) {
			throw new CreationException("Could not access.", e);
		} catch (final InvocationTargetException e) {
			throw new CreationException("Could not invoke.", e);
		}
	}

	/**
	 * Gets the value for the target field.
	 * 
	 * @return The value for the target field.
	 */
	public Class<E> getTarget() {
		return this.target;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("FactoryInstanceStrategy[");
		builder.append("\n\tFactory: ").append(this.factory);
		builder.append("\n\tFactory Method: ").append(this.factoryMethod.toGenericString());
		builder.append("\n\tInstance target: ").append(this.target.getName());
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Write the replacement.
	 * 
	 * @return The serialization proxy.
	 */
	private Object writeReplace() {
		return new Proxy<E, F>(this.getFactory(), this.getFactoryMethod(), this.getTarget());
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
	protected static final class Proxy<E, F extends Factory>
			implements Serializable {
		/** The serial id. */
		private static final long serialVersionUID = 1652686205348787411L;
		/** The factory. */
		private CreationalStrategy<F> factory;
		/** The factory method. */
		private MethodInfo factoryMethod;
		/** The target. */
		private Class<E> target;

		/**
		 * Default constructor.
		 * 
		 * @param factory
		 *            The factory type.
		 * @param factoryMethod
		 *            The factory method.
		 * @param target
		 *            The target type.
		 */
		public Proxy(final CreationalStrategy<F> factory, final Method factoryMethod, final Class<E> target) {
			this.factory = factory;
			this.factoryMethod = new MethodInfo(factoryMethod);
			this.target = target;
		}

		/**
		 * Gets the value for the factory field.
		 * 
		 * @return The value for the factory field.
		 */
		public CreationalStrategy<F> getFactory() {
			return this.factory;
		}

		/**
		 * Gets the value for the factoryMethod field.
		 * 
		 * @return The value for the factoryMethod field.
		 */
		public MethodInfo getFactoryMethod() {
			return this.factoryMethod;
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
		 * Sets a new value for the factory field.
		 * 
		 * @param factory
		 *            The new value for the factory field.
		 */
		public void setFactory(final CreationalStrategy<F> factory) {
			this.factory = factory;
		}

		/**
		 * Sets a new value for the factoryMethod field.
		 * 
		 * @param factoryMethod
		 *            The new value for the factoryMethod field.
		 */
		public void setFactoryMethod(final MethodInfo factoryMethod) {
			this.factoryMethod = factoryMethod;
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

		/**
		 * Gets the instance for this property.
		 * 
		 * @return The property.
		 */
		private Object readResolve() {
			return new FactoryInstanceStrategy<E, F>(this.factory, this.factoryMethod.resolveMethod(), this.target);
		}
	}
}
