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
package org.lunarray.model.descriptor.accessor.reference.operation.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.operation.Operation;
import org.lunarray.model.descriptor.accessor.operation.PersistentOperation;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes a direct operation reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The containing type.
 */
public final class DirectOperationReference<C>
		implements OperationReference<C> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DirectOperationReference.class);
	/** The serial id. */
	private static final long serialVersionUID = 1691666557162531104L;
	/** The operation. */
	private PersistentOperation operation;

	/**
	 * Default constructor.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 */
	public DirectOperationReference(final PersistentOperation operation) {
		Validate.notNull(operation, "Operation may not be null.");
		this.operation = operation;
	}

	/** {@inheritDoc} */
	@Override
	public Object execute(final C parentType, final Object[] parameters) throws ValueAccessException {
		Object[] params = parameters;
		if (CheckUtil.isNull(params)) {
			params = new Object[0];
		}
		Object result;
		try {
			DirectOperationReference.LOGGER.debug("Invoking operation {} on entity {} with parameters {}", this.operation, parentType,
					Arrays.toString(parameters));
			result = this.operation.getOperation().invoke(parentType, params);
		} catch (final IllegalArgumentException e) {
			throw new ValueAccessException(e);
		} catch (final IllegalAccessException e) {
			throw new ValueAccessException(e);
		} catch (final InvocationTargetException e) {
			throw new ValueAccessException(e);
		}
		return result;
	}

	/**
	 * Gets the value for the operation field.
	 * 
	 * @return The value for the operation field.
	 */
	public PersistentOperation getOperation() {
		return this.operation;
	}

	/** {@inheritDoc} */
	@Override
	public Operation getReferencedOperation() {
		return this.operation;
	}

	/**
	 * Sets a new value for the operation field.
	 * 
	 * @param operation
	 *            The new value for the operation field.
	 */
	public void setOperation(final PersistentOperation operation) {
		this.operation = operation;
	}
}
