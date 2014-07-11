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

import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.ResultDetailBuilder;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * The result type detail builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ResultDetailBuilderImpl
		implements ResultDetailBuilder {

	/**
	 * Creates a new builder.
	 * 
	 * @return A new builder.
	 */
	public static ResultDetailBuilderImpl create() {
		return new ResultDetailBuilderImpl();
	}
	/** The format. */
	private transient String formatBuilder;
	/** The in line indication. */
	private transient boolean inLineIndication;

	/** The render type. */
	private transient RenderType renderTypeBuilder;

	/**
	 * Default constructor.
	 */
	private ResultDetailBuilderImpl() {
		super();
		this.inLineIndication = false;
		this.renderTypeBuilder = RenderType.TEXT;
	}

	/**
	 * Builds the detail.
	 * 
	 * @return The detail.
	 */
	public ResultDetail build() {
		return new ResultDetail(this);
	}

	/** {@inheritDoc} */
	@Override
	public ResultDetailBuilderImpl format(final String format) {
		this.formatBuilder = format;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResultDetailBuilderImpl inLine() {
		this.inLineIndication = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ResultDetailBuilderImpl renderType(final RenderType renderType) {
		this.renderTypeBuilder = renderType;
		return this;
	}

	/**
	 * Gets the value for the format field.
	 * 
	 * @return The value for the format field.
	 */
	protected String getFormat() {
		return this.formatBuilder;
	}

	/**
	 * Gets the value for the renderType field.
	 * 
	 * @return The value for the renderType field.
	 */
	protected RenderType getRenderType() {
		return this.renderTypeBuilder;
	}

	/**
	 * Gets the value for the inLineIndication field.
	 * 
	 * @return The value for the inLineIndication field.
	 */
	protected boolean isInLineIndication() {
		return this.inLineIndication;
	}
}
