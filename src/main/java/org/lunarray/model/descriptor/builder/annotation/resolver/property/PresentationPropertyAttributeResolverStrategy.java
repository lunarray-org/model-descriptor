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
package org.lunarray.model.descriptor.builder.annotation.resolver.property;

import java.util.List;

import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.BooleanInherit;

/**
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PresentationPropertyAttributeResolverStrategy {

	/**
	 * Resolve the format.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The format.
	 */
	String format(DescribedProperty<?> property);

	/**
	 * Resolve the format.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The format.
	 */
	String format(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Tests if the property is immutable.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if the property is immutable or not.
	 */
	BooleanInherit immutable(DescribedProperty<?> property);

	/**
	 * Tests if the property is immutable.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier
	 * @return True if the property is immutable or not.
	 */
	BooleanInherit immutable(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Tests if the property should be rendered in line.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if the property should be rendered in line.
	 */
	BooleanInherit inLine(DescribedProperty<?> property);

	/**
	 * Tests if the property should be rendered in line.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier
	 * @return True if the property should be rendered in line.
	 */
	BooleanInherit inLine(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Gets the label key for the property.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The label key.
	 */
	String labelKey(DescribedProperty<?> property);

	/**
	 * Gets the label key for the property.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The label key.
	 */
	String labelKey(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Indicates the property is a name.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if the property is a name.
	 */
	BooleanInherit name(DescribedProperty<?> property);

	/**
	 * Indicates the property is a name.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return True if the property is a name.
	 */
	BooleanInherit name(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Gets the property order.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The order.
	 */
	int order(DescribedProperty<?> property);

	/**
	 * Gets the property order.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The order.
	 */
	int order(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Gets the properties qualifiers.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The qualifiers.
	 */
	List<Class<?>> qualifiers(DescribedProperty<?> property);

	/**
	 * Gets the render type.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The render type.
	 */
	RenderType render(DescribedProperty<?> property);

	/**
	 * Gets the render type.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The render type.
	 */
	RenderType render(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Tests if the property is visible.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if the property is visible.
	 */
	BooleanInherit required(DescribedProperty<?> property);

	/**
	 * Tests if the property is visible.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return True if the property is visible.
	 */
	BooleanInherit required(DescribedProperty<?> property, Class<?> qualifier);

	/**
	 * Tests if the property is visible.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if the property is visible.
	 */
	BooleanInherit visible(DescribedProperty<?> property);

	/**
	 * Tests if the property is visible.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return True if the property is visible.
	 */
	BooleanInherit visible(DescribedProperty<?> property, Class<?> qualifier);
}
