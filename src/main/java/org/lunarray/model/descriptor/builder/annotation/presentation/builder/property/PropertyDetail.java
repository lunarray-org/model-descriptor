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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.property;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.AbstractDescribedDetail;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * The property detail.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class PropertyDetail
		extends AbstractDescribedDetail {

	/** Serial id. */
	private static final long serialVersionUID = 2302660852958285317L;
	/** The format. */
	private String format;
	/** Property is immutable. */
	private boolean immutable;
	/** The in line indication. */
	private boolean inLineIndication;
	/** Indicates the property is a name. */
	private boolean name;
	/** The order. */
	private int order;
	/** The render type. */
	private RenderType renderType;
	/** Property cannot be empty. */
	private boolean requiredIndication;

	/**
	 * Constructs the detail.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected PropertyDetail(final PropertyDetailBuilderImpl builder) {
		super(builder);
		Validate.notNull(builder, "Builder may not be null.");
		this.renderType = builder.getRenderType();
		this.inLineIndication = builder.isInLineIndication();
		this.name = builder.isName();
		this.requiredIndication = builder.isRequiredIndication();
		this.order = builder.getOrder();
		this.immutable = builder.isImmutable();
		this.format = builder.getFormat();
	}

	/**
	 * Gets the value for the format field.
	 * 
	 * @return The value for the format field.
	 */
	public String getFormat() {
		return this.format;
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
	 * Gets the value for the renderType field.
	 * 
	 * @return The value for the renderType field.
	 */
	public RenderType getRenderType() {
		return this.renderType;
	}

	/**
	 * Gets the value for the immutable field.
	 * 
	 * @return The value for the immutable field.
	 */
	public boolean isImmutable() {
		return this.immutable;
	}

	/**
	 * Gets the value for the inLineIndication field.
	 * 
	 * @return The value for the inLineIndication field.
	 */
	public boolean isInLineIndication() {
		return this.inLineIndication;
	}

	/**
	 * Gets the value for the name field.
	 * 
	 * @return The value for the name field.
	 */
	public boolean isName() {
		return this.name;
	}

	/**
	 * Gets the value for the requiredIndication field.
	 * 
	 * @return The value for the requiredIndication field.
	 */
	public boolean isRequiredIndication() {
		return this.requiredIndication;
	}

	/**
	 * Sets a new value for the format field.
	 * 
	 * @param format
	 *            The new value for the format field.
	 */
	public void setFormat(final String format) {
		this.format = format;
	}

	/**
	 * Sets a new value for the immutable field.
	 * 
	 * @param immutable
	 *            The new value for the immutable field.
	 */
	public void setImmutable(final boolean immutable) {
		this.immutable = immutable;
	}

	/**
	 * Sets a new value for the inLineIndication field.
	 * 
	 * @param inLineIndication
	 *            The new value for the inLineIndication field.
	 */
	public void setInLineIndication(final boolean inLineIndication) {
		this.inLineIndication = inLineIndication;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 */
	public void setName(final boolean name) {
		this.name = name;
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

	/**
	 * Sets a new value for the renderType field.
	 * 
	 * @param renderType
	 *            The new value for the renderType field.
	 */
	public void setRenderType(final RenderType renderType) {
		this.renderType = renderType;
	}

	/**
	 * Sets a new value for the requiredIndication field.
	 * 
	 * @param requiredIndication
	 *            The new value for the requiredIndication field.
	 */
	public void setRequiredIndication(final boolean requiredIndication) {
		this.requiredIndication = requiredIndication;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		this.describedToString(builder);
		builder.append("\tImmutable: ").append(this.immutable);
		builder.append("\n\tIn Line Indication: ").append(this.inLineIndication);
		builder.append("\n\tIs Name Property: ").append(this.name);
		builder.append("\n\tOrder: ").append(this.order);
		builder.append("\n\tRender Type: ").append(this.renderType);
		builder.append("\n\tIs Required: ").append(this.requiredIndication);
		builder.append("\n");
		return builder.toString();
	}
}
