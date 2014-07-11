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
package org.lunarray.model.descriptor.builder.annotation.resolver.entity;

import java.util.List;

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.util.BooleanInherit;

/**
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PresentationEntityAttributeResolverStrategy {

	/**
	 * Gets the label key for the entity.
	 * 
	 * @param entityType
	 *            The entity type. May not be null.
	 * @return The label key.
	 */
	String labelKey(DescribedEntity<?> entityType);

	/**
	 * Gets the label key for the entity.
	 * 
	 * @param entityType
	 *            The entity type. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The label key.
	 */
	String labelKey(DescribedEntity<?> entityType, final Class<?> qualifier);

	/**
	 * Gets the entities qualifiers.
	 * 
	 * @param entityType
	 *            The entity type. May not be null.
	 * @return The qualifiers.
	 */
	List<Class<?>> qualifiers(DescribedEntity<?> entityType);

	/**
	 * Gets the resource bundle.
	 * 
	 * @param entityType
	 *            The entity. May not be null.
	 * @return The resource bundle.
	 */
	String resourceBundle(DescribedEntity<?> entityType);

	/**
	 * Gets the resource bundle.
	 * 
	 * @param entityType
	 *            The entity. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return The resource bundle.
	 */
	String resourceBundle(DescribedEntity<?> entityType, Class<?> qualifier);

	/**
	 * Tests if the entity is visible.
	 * 
	 * @param entityType
	 *            The entity type. May not be null.
	 * @return True if the entity is visible.
	 */
	BooleanInherit visible(DescribedEntity<?> entityType);

	/**
	 * Tests if the entity is visible.
	 * 
	 * @param entityType
	 *            The entity type. May not be null.
	 * @param qualifier
	 *            The qualifier.
	 * @return True if the entity is visible.
	 */
	BooleanInherit visible(DescribedEntity<?> entityType, Class<?> qualifier);
}
