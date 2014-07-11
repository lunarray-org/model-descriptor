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
package org.lunarray.model.descriptor.mapping.impl.properties.exceptions;

/**
 * An exception if the conversion fails.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class ConversionException
		extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = -9017284382674535305L;

	/**
	 * Constructs the exception.
	 * 
	 * @param cause
	 *            The original cause.
	 */
	public ConversionException(final Exception cause) {
		super(cause);
	}

	/**
	 * Constructs the exception.
	 * 
	 * @param message
	 *            The message.
	 */
	public ConversionException(final String message) {
		super(message);
	}

	/**
	 * Constructs the exception.
	 * 
	 * @param message
	 *            The message.
	 * @param cause
	 *            The original cause.
	 */
	public ConversionException(final String message, final Exception cause) {
		super(message, cause);
	}
}
