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
package org.lunarray.model.descriptor.builder.annotation.presentation.operation.result;

import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * Result type detail builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface ResultDetailBuilder {

	/**
	 * Sets a new value for the format field.
	 * 
	 * @param format
	 *            The new value for the format field.
	 * @return The builder.
	 */
	ResultDetailBuilder format(String format);

	/**
	 * Indicates in line.
	 * 
	 * @return The builder.
	 */
	ResultDetailBuilder inLine();

	/**
	 * Sets the render type.
	 * 
	 * @param renderType
	 *            The render type.
	 * @return The builder.
	 */
	ResultDetailBuilder renderType(final RenderType renderType);
}
