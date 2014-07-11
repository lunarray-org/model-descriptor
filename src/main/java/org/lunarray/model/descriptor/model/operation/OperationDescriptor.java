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
package org.lunarray.model.descriptor.model.operation;

import java.util.List;

import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;

/**
 * A operation descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public interface OperationDescriptor<E>
		extends MemberDescriptor<E> {
	/**
	 * Gets an extension.
	 * 
	 * @param <X>
	 *            The extension type.
	 * @param extensionType
	 *            The extension type.
	 * @return The extension.
	 */
	<X extends OperationExtension<E>> X extension(Class<X> extensionType);

	/**
	 * Gets the operation reference.
	 * 
	 * @return The operation reference.
	 */
	OperationReference<E> getOperationReference();

	/**
	 * Gets a specific parameter.
	 * 
	 * @param index
	 *            The index.
	 * @return The specific parameter.
	 */
	ParameterDescriptor<?> getParameter(int index);

	/**
	 * Gets the parameter descriptor.
	 * 
	 * @param index
	 *            The index.
	 * @param parameterType
	 *            The parameter type.
	 * @return The descriptor.
	 * @param <P>
	 *            The parameter type.
	 */
	<P> ParameterDescriptor<P> getParameter(int index, Class<P> parameterType);

	/**
	 * Gets the parameter count.
	 * 
	 * @return The parameter count.
	 */
	int getParameterCount();

	/**
	 * Gets the parameters.
	 * 
	 * @return The parameters.
	 */
	List<ParameterDescriptor<?>> getParameters();

	/**
	 * Gets the result descriptor.
	 * 
	 * @return The result.
	 */
	ResultDescriptor<?> getResultDescriptor();

	/**
	 * Get the result descriptor.
	 * 
	 * @param resultType
	 *            The expected result type.
	 * @return The result type.
	 * @param <R>
	 *            The result type.
	 */
	<R> ResultDescriptor<R> getResultDescriptor(Class<R> resultType);
}
