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
package org.lunarray.model.descriptor.accessor.operation.parameter;

import java.io.Serializable;

/**
 * Describes a parameter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter
 */
public final class PersistentParameter<P>
		extends AbstractParameter<P>
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 1230530330986170497L;

	/**
	 * Creates a builder.
	 * 
	 * @param <P>
	 *            The parameter type.
	 * @return The builder.
	 */
	public static <P> ParameterBuilder<P> createBuilder() {
		return new ParameterBuilder<P>();
	}

	/**
	 * Default constructor.
	 */
	public PersistentParameter() {
		super();
	}

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected PersistentParameter(final ParameterBuilder<P> builder) {
		super(builder);
	}

	/**
	 * Write the replacement.
	 * 
	 * @return The serialization proxy.
	 */
	private Object writeReplace() {
		return new ParameterProxy<P>(this);
	}

	/**
	 * The parameter proxy.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <P>
	 *            The parameter type.
	 */
	public static final class ParameterProxy<P>
			implements Parameter<P> {

		/** The parameter index. */
		private int index;
		/** The parameter type. */
		private Class<P> type;

		/**
		 * Default constructor.
		 */
		public ParameterProxy() {
			// Default constructor.
		}

		/**
		 * Default constructor.
		 * 
		 * @param parameter
		 *            The parameter.
		 */
		public ParameterProxy(final PersistentParameter<P> parameter) {
			this.index = parameter.getIndex();
			this.type = parameter.getType();
		}

		/**
		 * Gets the value for the index field.
		 * 
		 * @return The value for the index field.
		 */
		@Override
		public int getIndex() {
			return this.index;
		}

		/**
		 * Gets the value for the type field.
		 * 
		 * @return The value for the type field.
		 */
		@Override
		public Class<P> getType() {
			return this.type;
		}

		/**
		 * Sets a new value for the index field.
		 * 
		 * @param index
		 *            The new value for the index field.
		 */
		public void setIndex(final int index) {
			this.index = index;
		}

		/**
		 * Sets a new value for the type field.
		 * 
		 * @param type
		 *            The new value for the type field.
		 */
		public void setType(final Class<P> type) {
			this.type = type;
		}
	}
}
