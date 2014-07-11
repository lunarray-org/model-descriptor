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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation;

import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.AbstractDescribedDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.OperationDetailBuilder;

/**
 * The property detail builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class OperationDetailBuilderImpl
		extends AbstractDescribedDetailBuilder
		implements OperationDetailBuilder {

	/**
	 * Creates a new builder.
	 * 
	 * @return A new builder.
	 */
	public static OperationDetailBuilderImpl create() {
		return new OperationDetailBuilderImpl();
	}
	/** The description key. */
	private transient String buttonKeyBuilder;

	/** The order. */
	private transient int orderBuilder;

	/**
	 * Default constructor.
	 */
	private OperationDetailBuilderImpl() {
		super();
	}

	/**
	 * Builds the detail.
	 * 
	 * @return The detail.
	 */
	public OperationDetail build() {
		return new OperationDetail(this);
	}

	/**
	 * Sets a new value for the buttonKey field.
	 * 
	 * @param buttonKey
	 *            The new value for the buttonKey field.
	 * @return The button key.
	 */
	@Override
	public OperationDetailBuilderImpl buttonKey(final String buttonKey) {
		this.buttonKeyBuilder = buttonKey;
		return this;
	}

	/**
	 * Gets the value for the buttonKey field.
	 * 
	 * @return The value for the buttonKey field.
	 */
	public String getButtonKey() {
		return this.buttonKeyBuilder;
	}

	/**
	 * Gets the value for the orderBuilder field.
	 * 
	 * @return The value for the orderBuilder field.
	 */
	public int getOrder() {
		return this.orderBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public OperationDetailBuilderImpl order(final int orderBuilder) {
		this.orderBuilder = orderBuilder;
		return this;
	}
}
