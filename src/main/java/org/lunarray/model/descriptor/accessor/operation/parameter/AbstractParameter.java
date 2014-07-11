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

/**
 * Describes an abstract parameter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 */
public abstract class AbstractParameter<P>
		implements Parameter<P> {

	/** The parameter index. */
	private transient int index;
	/** The parameter type. */
	private transient Class<P> type;

	/**
	 * /** Default constructor.
	 */
	public AbstractParameter() {
		// Default constructor.
	}

	/**
	 * Constructs the parameter.
	 * 
	 * @param prototype
	 *            A prototype.
	 */
	protected AbstractParameter(final Parameter<P> prototype) {
		this.index = prototype.getIndex();
		this.type = prototype.getType();
	}

	/**
	 * Constructs the parameter.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected AbstractParameter(final ParameterBuilder<P> builder) {
		this.index = builder.getIndex();
		this.type = builder.getParameterType();
	}

	/** {@inheritDoc} */
	@Override
	public final int getIndex() {
		return this.index;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<P> getType() {
		return this.type;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Parameter[\n\tIndex: ").append(this.index);
		builder.append("\n\tType: ").append(this.type);
		builder.append("\n]");
		return builder.toString();
	}
}
