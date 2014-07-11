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

import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.BooleanInherit;

/**
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PresentationParameterAttributeResolverStrategy {

	/**
	 * Resolve the format.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @return The format.
	 */
	String format(DescribedParameter<?> parameter);

	/**
	 * Resolve the format.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The format.
	 */
	String format(DescribedParameter<?> parameter, Class<?> qualifier);

	/**
	 * Tests if the parameter should be rendered in line.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @return True if the parameter should be rendered in line.
	 */
	BooleanInherit inLine(DescribedParameter<?> parameter);

	/**
	 * Tests if the parameter should be rendered in line.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @param qualifier
	 *            The qualifier
	 * @return True if the parameter should be rendered in line.
	 */
	BooleanInherit inLine(DescribedParameter<?> parameter, Class<?> qualifier);

	/**
	 * Gets the label key for the parameter.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @return The label key.
	 */
	String labelKey(DescribedParameter<?> parameter);

	/**
	 * Gets the label key for the parameter.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The label key.
	 */
	String labelKey(DescribedParameter<?> parameter, Class<?> qualifier);

	/**
	 * Gets the parameter order.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @return The order.
	 */
	int order(DescribedParameter<?> parameter);

	/**
	 * Gets the parameter order.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The order.
	 */
	int order(DescribedParameter<?> parameter, Class<?> qualifier);

	/**
	 * Gets the properties qualifiers.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @return The qualifiers.
	 */
	List<Class<?>> qualifiers(DescribedParameter<?> parameter);

	/**
	 * Gets the render type.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @return The render type.
	 */
	RenderType render(DescribedParameter<?> parameter);

	/**
	 * Gets the render type.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The render type.
	 */
	RenderType render(DescribedParameter<?> parameter, Class<?> qualifier);

	/**
	 * Tests if the parameter is visible.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @return True if the parameter is visible.
	 */
	BooleanInherit required(DescribedParameter<?> parameter);

	/**
	 * Tests if the parameter is visible.
	 * 
	 * @param parameter
	 *            The parameter. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return True if the parameter is visible.
	 */
	BooleanInherit required(DescribedParameter<?> parameter, Class<?> qualifier);
}
