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
package org.lunarray.model.descriptor.builder.annotation.resolver.operation;

import java.util.List;

import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.BooleanInherit;

/**
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PresentationOperationAttributeResolverStrategy {

	/**
	 * Gets the label key for the operation.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return The label key.
	 */
	String buttonKey(DescribedOperation operation);

	/**
	 * Gets the label key for the operation.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The label key.
	 */
	String buttonKey(DescribedOperation operation, Class<?> qualifier);

	/**
	 * Resolve the format.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return The format.
	 */
	String format(DescribedOperation operation);

	/**
	 * Resolve the format.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The format.
	 */
	String format(DescribedOperation operation, Class<?> qualifier);

	/**
	 * Tests if the operation should be rendered in line.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return True if the operation should be rendered in line.
	 */
	BooleanInherit inLine(DescribedOperation operation);

	/**
	 * Tests if the operation should be rendered in line.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param qualifier
	 *            The qualifier
	 * @return True if the operation should be rendered in line.
	 */
	BooleanInherit inLine(DescribedOperation operation, Class<?> qualifier);

	/**
	 * Gets the label key for the operation.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return The label key.
	 */
	String labelKey(DescribedOperation operation);

	/**
	 * Gets the label key for the operation.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The label key.
	 */
	String labelKey(DescribedOperation operation, Class<?> qualifier);

	/**
	 * Gets the operation order.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return The order.
	 */
	int order(DescribedOperation operation);

	/**
	 * Gets the operation order.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The order.
	 */
	int order(DescribedOperation operation, Class<?> qualifier);

	/**
	 * Gets the operations qualifiers.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return The qualifiers.
	 */
	List<Class<?>> qualifiers(DescribedOperation operation);

	/**
	 * Gets the render type.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return The render type.
	 */
	RenderType render(DescribedOperation operation);

	/**
	 * Gets the render type.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The render type.
	 */
	RenderType render(DescribedOperation operation, Class<?> qualifier);

	/**
	 * Tests if the operation is visible.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return True if the operation is visible.
	 */
	BooleanInherit visible(DescribedOperation operation);

	/**
	 * Tests if the operation is visible.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return True if the operation is visible.
	 */
	BooleanInherit visible(DescribedOperation operation, Class<?> qualifier);
}
