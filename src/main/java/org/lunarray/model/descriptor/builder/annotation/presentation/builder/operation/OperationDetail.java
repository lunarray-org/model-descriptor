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

import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.AbstractDescribedDetail;

/**
 * The operation detail.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class OperationDetail
		extends AbstractDescribedDetail {

	/** Serial id. */
	private static final long serialVersionUID = 2302660852958285317L;
	/** The description key. */
	private String buttonKey;
	/** The order. */
	private int order;

	/**
	 * Constructs the detail.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected OperationDetail(final OperationDetailBuilderImpl builder) {
		super(builder);
		this.order = builder.getOrder();
	}

	/**
	 * Gets the value for the buttonKey field.
	 * 
	 * @return The value for the buttonKey field.
	 */
	public String getButtonKey() {
		return this.buttonKey;
	}

	/**
	 * Gets the value for the order field.
	 * 
	 * @return The value for the order field.
	 */
	public int getOrder() {
		return this.order;
	}

	/**
	 * Sets a new value for the buttonKey field.
	 * 
	 * @param buttonKey
	 *            The new value for the buttonKey field.
	 */
	public void setButtonKey(final String buttonKey) {
		this.buttonKey = buttonKey;
	}

	/**
	 * Sets a new value for the order field.
	 * 
	 * @param order
	 *            The new value for the order field.
	 */
	public void setOrder(final int order) {
		this.order = order;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		this.describedToString(builder);
		builder.append("\tButton Key: ").append(this.buttonKey);
		builder.append("\n\tOrder: ").append(this.order);
		builder.append("\n");
		return builder.toString();
	}
}
