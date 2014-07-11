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

import java.lang.reflect.Method;

/**
 * The static factory method strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <F>
 *            Type coersion type.
 */
public final class FactoryMethodStrategy<E, F extends E>
		extends AbstractStaticMethodStrategy<E, F> {

	/** Serial id. */
	private static final long serialVersionUID = -1411497421902518387L;

	/**
	 * Default constructor.
	 * 
	 * @param factoryMethod
	 *            The factory method. May not be null.
	 * @param type
	 *            The type. May not be null.
	 */
	protected FactoryMethodStrategy(final Method factoryMethod, final Class<F> type) {
		super(factoryMethod, type);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("FactoryMethodCreationalStrategy [");
		this.abstractToString(builder);
		builder.append("\n]");
		return builder.toString();
	}

	/**
	 * Write the replacement.
	 * 
	 * @return The serialization proxy.
	 */
	private Object writeReplace() {
		return new StrategyProxy<E, F>(this.getFactoryMethod(), this.getType());
	}

	/**
	 * Serialization proxy.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <E>
	 *            The entity type.
	 * @param <F>
	 *            Type coersion type.
	 */
	protected static class StrategyProxy<E, F extends E>
			extends AbstractProxy<E, F> {

		/** Serial id. */
		private static final long serialVersionUID = 8720289088302160445L;

		/**
		 * Default constructor.
		 * 
		 * @param factoryMethod
		 *            The factory method.
		 * @param type
		 *            The type.
		 */
		protected StrategyProxy(final Method factoryMethod, final Class<F> type) {
			super(factoryMethod, type);
		}

		/**
		 * Gets the instance for this property.
		 * 
		 * @return The property.
		 */
		private Object readResolve() {
			return new FactoryMethodStrategy<E, F>(this.getFactoryMethod().resolveMethod(), this.getType());
		}
	}
}
