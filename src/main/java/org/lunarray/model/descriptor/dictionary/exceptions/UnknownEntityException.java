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
package org.lunarray.model.descriptor.dictionary.exceptions;

import org.lunarray.model.descriptor.model.entity.EntityDescriptor;

/**
 * An exception if an entity is unrelated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class UnknownEntityException
		extends DictionaryException {

	/** Serial id. */
	private static final long serialVersionUID = -5725399547248072619L;

	/**
	 * Resolves a message.
	 * 
	 * @param entityDescriptor
	 *            The descriptor
	 * @return The exception message.
	 */
	private static String getMessage(final EntityDescriptor<?> entityDescriptor) {
		return String.format("Entity descriptor '%s' was not related.", entityDescriptor.getName());
	}

	/**
	 * Constructs the exception.
	 * 
	 * @param entityDescriptor
	 *            The entity that was not related.
	 */
	public UnknownEntityException(final EntityDescriptor<?> entityDescriptor) {
		super(UnknownEntityException.getMessage(entityDescriptor));
	}

	/**
	 * Constructs the exception.
	 * 
	 * @param entityDescriptor
	 *            The descriptor.
	 * @param cause
	 *            The cause.
	 */
	public UnknownEntityException(final EntityDescriptor<?> entityDescriptor, final Exception cause) {
		super(UnknownEntityException.getMessage(entityDescriptor), cause);
	}
}
