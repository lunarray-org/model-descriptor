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
package org.lunarray.model.descriptor.accessor.exceptions;

/**
 * Throw if a value of a property could not be accessed.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ValueAccessException
		extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = 3343883249298223678L;

	/**
	 * Constructs the exception.
	 * 
	 * @param cause
	 *            The original cause.
	 */
	public ValueAccessException(final Exception cause) {
		super(cause);
	}

	/**
	 * Default constructor.
	 * 
	 * @param message
	 *            The message.
	 */
	public ValueAccessException(final String message) {
		super(message);
	}
}
