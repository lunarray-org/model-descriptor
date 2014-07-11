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
package org.lunarray.model.descriptor.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;

/**
 * A operation builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class OperationInvocationBuilder<E> {

	/** The arguments. */
	private transient Object[] arguments;
	/** The descriptor. */
	private final transient OperationDescriptor<E> operationDescriptorBuilder;
	/** The target. */
	private transient E targetBuilder;

	/**
	 * Default constructor.
	 * 
	 * @param operationDescriptor
	 *            The operation. May not be null.
	 */
	public OperationInvocationBuilder(final OperationDescriptor<E> operationDescriptor) {
		Validate.notNull(operationDescriptor, "Operation descriptor may not be null.");
		this.operationDescriptorBuilder = operationDescriptor;
		this.arguments = new Object[operationDescriptor.getParameterCount()];
	}

	/**
	 * Execute.
	 * 
	 * @return The result.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	public Object execute() throws ValueAccessException {
		return this.operationDescriptorBuilder.getOperationReference().execute(this.targetBuilder, this.arguments);
	}

	/**
	 * Execute.
	 * 
	 * @param resultType
	 *            The result type.
	 * @return The result.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 * @param <R>
	 *            The result type.
	 */
	@SuppressWarnings("unchecked")
	public <R> R execute(final Class<R> resultType) throws ValueAccessException {
		R result = null;
		if (this.operationDescriptorBuilder.getResultDescriptor().getResultType().isAssignableFrom(resultType)) {
			result = (R) this.operationDescriptorBuilder.getOperationReference().execute(this.targetBuilder, this.arguments);
		} else {
			throw new ValueAccessException("Invalid result type.");
		}
		return result;
	}

	/**
	 * Gets the value for the operationDescriptor field.
	 * 
	 * @return The value for the operationDescriptor field.
	 */
	public OperationDescriptor<E> getOperationDescriptor() {
		return this.operationDescriptorBuilder;
	}

	/**
	 * Gets the parameters for invocation.
	 * 
	 * @return The invocation parameters.
	 */
	public Map<ParameterDescriptor<?>, ?> getParameters() {
		final Map<ParameterDescriptor<?>, Object> result = new HashMap<ParameterDescriptor<?>, Object>();
		for (final ParameterDescriptor<?> descriptor : this.operationDescriptorBuilder.getParameters()) {
			result.put(descriptor, this.arguments[descriptor.getIndex()]);
		}
		return result;
	}

	/**
	 * Gets the target entity.
	 * 
	 * @return The target entity.
	 */
	public E getTarget() {
		return this.targetBuilder;
	}

	/**
	 * Process parameter.
	 * 
	 * @param parameter
	 *            The parameter number.
	 * @param value
	 *            The parameter value.
	 * @param <P>
	 *            The parameter type.
	 */
	public <P> void parameter(final int parameter, final P value) {
		final ParameterDescriptor<?> parameterDescriptor = this.operationDescriptorBuilder.getParameter(parameter);
		if (parameterDescriptor.isAssignable(value)) {
			this.arguments[parameter] = value;
		} else {
			throw new IllegalArgumentException(String.format("Value '%s' is not assignable to parameter %s of operation '%s'.", value,
					parameter, this.operationDescriptorBuilder));
		}
	}

	/**
	 * Process parameter.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @param value
	 *            The parameter value.
	 * @param <P>
	 *            The parameter type.
	 */
	public <P> void parameter(final ParameterDescriptor<P> parameter, final P value) {
		if ((this.operationDescriptorBuilder.getParameter(parameter.getIndex()) == parameter) && parameter.isAssignable(value)) {
			this.arguments[parameter.getIndex()] = value;
		} else {
			throw new IllegalArgumentException(String.format("Value '%s' is not assignable to %s.", value, parameter));
		}
	}

	/**
	 * Sets a new value for the target field.
	 * 
	 * @param target
	 *            The new value for the target field.
	 */
	public void target(final E target) {
		this.targetBuilder = target;
	}
}
