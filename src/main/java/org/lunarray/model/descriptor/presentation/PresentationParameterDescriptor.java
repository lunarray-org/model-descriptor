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
package org.lunarray.model.descriptor.presentation;

import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;

/**
 * Describes a presentation property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 */
public interface PresentationParameterDescriptor<P>
		extends ParameterDescriptor<P>, Described {

	/**
	 * Gets the property format.
	 * 
	 * @return The format.
	 */
	String getFormat();

	/**
	 * Gets the order of the property.
	 * 
	 * @return The order.
	 */
	int getOrder();

	/**
	 * Gets the render type.
	 * 
	 * @return The render type.
	 */
	RenderType getRenderType();

	/**
	 * Indicates this property is required.
	 * 
	 * @return True if and only if this property required.
	 */
	boolean isRequiredIndication();
}
