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
package org.lunarray.model.descriptor.builder.annotation.resolver.parameter;

import java.util.List;

import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;

/**
 * A resolver of parameters.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface ParameterResolverStrategy {

	/**
	 * Resolves parameters for a given operation.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @return The parameters.
	 */
	List<DescribedParameter<?>> resolveParameters(DescribedOperation operation);
}
