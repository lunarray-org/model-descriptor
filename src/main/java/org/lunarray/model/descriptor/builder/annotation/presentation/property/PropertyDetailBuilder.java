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
package org.lunarray.model.descriptor.builder.annotation.presentation.property;

import org.lunarray.model.descriptor.builder.annotation.presentation.DescribedDetailBuilder;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * Property detail builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PropertyDetailBuilder
		extends DescribedDetailBuilder {

	/**
	 * Sets a new value for the format field.
	 * 
	 * @param format
	 *            The new value for the format field.
	 * @return The builder.
	 */
	PropertyDetailBuilder format(String format);

	/**
	 * Indicates the property is immutable.
	 * 
	 * @return The builder.
	 */
	PropertyDetailBuilder immutable();

	/**
	 * Indicates in line.
	 * 
	 * @return The builder.
	 */
	PropertyDetailBuilder inLine();

	/**
	 * Indicates a name property.
	 * 
	 * @return The builder.
	 */
	PropertyDetailBuilder name();

	/**
	 * Sets the order.
	 * 
	 * @param order
	 *            The order.
	 * @return The builder.
	 */
	PropertyDetailBuilder order(final int order);

	/**
	 * Sets the render type.
	 * 
	 * @param renderType
	 *            The render type.
	 * @return The builder.
	 */
	PropertyDetailBuilder renderType(final RenderType renderType);

	/**
	 * Indicates the property is required.
	 * 
	 * @return The builder.
	 */
	PropertyDetailBuilder required();
}
