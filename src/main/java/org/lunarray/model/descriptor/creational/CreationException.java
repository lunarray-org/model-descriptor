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
package org.lunarray.model.descriptor.creational;

/**
 * A create exception.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class CreationException
		extends Exception {

	/** The serial id. */
	private static final long serialVersionUID = -6578599193537997811L;
	/** The exception causes. */
	private final transient Throwable[] causes;

	/**
	 * Creates the exception.
	 * 
	 * @param message
	 *            The message.
	 * @param cause
	 *            The cause(s).
	 */
	public CreationException(final String message, final Throwable... cause) {
		super(message);
		this.causes = cause.clone();
	}

	/**
	 * Gets the value for the causes field.
	 * 
	 * @return The value for the causes field.
	 */
	public final Throwable[] getCauses() {
		return this.causes.clone();
	}
}
