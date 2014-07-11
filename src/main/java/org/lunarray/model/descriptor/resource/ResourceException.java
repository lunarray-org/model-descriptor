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
package org.lunarray.model.descriptor.resource;

/**
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class ResourceException
		extends Exception {

	/** Serial id. */
	private static final long serialVersionUID = -4984190031459274957L;

	/**
	 * Default constructor.
	 * 
	 * @param exception
	 *            The original cause.
	 */
	public ResourceException(final Exception exception) {
		super(exception);
	}

	/**
	 * Default constructor.
	 * 
	 * @param message
	 *            The message.
	 */
	public ResourceException(final String message) {
		super(message);
	}

	/**
	 * Default constructor.
	 * 
	 * @param message
	 *            The message.
	 * @param exception
	 *            The original cause.
	 */
	public ResourceException(final String message, final Exception exception) {
		super(message, exception);
	}
}
