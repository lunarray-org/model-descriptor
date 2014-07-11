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

import org.lunarray.model.descriptor.accessor.property.DescribedProperty;

/**
 * Defines an attribute resolver.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PropertyAttributeResolverStrategy {

	/**
	 * Gets the property alias.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The property alias, or null.
	 */
	String getAlias(DescribedProperty<?> property);

	/**
	 * Gets the name.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The name of the property.
	 */
	String getName(DescribedProperty<?> property);

	/**
	 * Gets the reference relation entity.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return The reference relation entity.
	 */
	Class<?> getReferenceRelation(DescribedProperty<?> property);

	/**
	 * Tests if the property is an alias for another property.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if and only if the property is an alias for another
	 *         property.
	 */
	boolean isAlias(DescribedProperty<?> property);

	/**
	 * Tests if the property is an embedded property.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if and only if the property is embedded.
	 */
	boolean isEmbedded(DescribedProperty<?> property);

	/**
	 * Tests whether the property can be ignored.
	 * 
	 * @param property
	 *            The property to test. May not be null.
	 * @return True if and only if the property can be ignored.
	 */
	boolean isIgnore(DescribedProperty<?> property);

	/**
	 * Tests if the property is a key property.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if and only if the property is a key.
	 */
	boolean isKey(DescribedProperty<?> property);

	/**
	 * Tests if the property is a reference.
	 * 
	 * @param property
	 *            The property. May not be null.
	 * @return True if and only if the property is a reference.
	 */
	boolean isReference(DescribedProperty<?> property);
}
