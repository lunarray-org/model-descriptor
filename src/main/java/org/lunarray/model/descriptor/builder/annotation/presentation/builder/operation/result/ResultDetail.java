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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.result;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * The property detail.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ResultDetail
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 7537204626261803745L;
	/** The format. */
	private String format;
	/** The in line indication. */
	private boolean inLineIndication;
	/** The render type. */
	private RenderType renderType;

	/**
	 * Constructs the detail.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected ResultDetail(final ResultDetailBuilderImpl builder) {
		Validate.notNull(builder, "Builder may not be null.");
		this.renderType = builder.getRenderType();
		this.inLineIndication = builder.isInLineIndication();
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
	 * Gets the value for the renderType field.
	 * 
	 * @return The value for the renderType field.
	 */
	public RenderType getRenderType() {
		return this.renderType;
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
	 * Sets a new value for the format field.
	 * 
	 * @param format
	 *            The new value for the format field.
	 */
	public void setFormat(final String format) {
		this.format = format;
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
	 * Sets a new value for the renderType field.
	 * 
	 * @param renderType
	 *            The new value for the renderType field.
	 */
	public void setRenderType(final RenderType renderType) {
		this.renderType = renderType;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("\n\tIn Line Indication: ").append(this.inLineIndication);
		builder.append("\n\tRender Type: ").append(this.renderType);
		builder.append("\n");
		return builder.toString();
	}
}
