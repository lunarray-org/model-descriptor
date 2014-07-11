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
package org.lunarray.model.descriptor.builder.annotation.base.listener.events;

import org.lunarray.model.descriptor.model.operation.OperationDescriptor;

/**
 * Propagated if an entity has been built.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class BuildOperationEvent<E> {

	/** The entity. */
	private final transient OperationDescriptor<E> operation;

	/**
	 * Default constructor.
	 * 
	 * @param operation
	 *            The operation.
	 */
	public BuildOperationEvent(final OperationDescriptor<E> operation) {
		this.operation = operation;
	}

	/**
	 * Gets the value for the operation field.
	 * 
	 * @return The value for the operation field.
	 */
	public OperationDescriptor<E> getOperation() {
		return this.operation;
	}
}
