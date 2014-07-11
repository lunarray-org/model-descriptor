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
package org.lunarray.model.descriptor.validator;

import java.util.Collection;
import java.util.Locale;

import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;

/**
 * Describes a validator.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface PropertyValidator
		extends Extension {

	/**
	 * Validates the entity.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param <P>
	 *            The property type.
	 * @param propertyDescriptor
	 *            The property descriptor. May not be null.
	 * @param entity
	 *            The entity. May not be null.
	 * @return A collection of constraint violations.
	 */
	<P, E> Collection<PropertyViolation<E, P>> validateProperty(PropertyDescriptor<P, E> propertyDescriptor, E entity);

	/**
	 * Validates the entity.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param <P>
	 *            The property type.
	 * @param propertyDescriptor
	 *            The entity descriptor. May not be null.
	 * @param entity
	 *            The entity. May not be null.
	 * @param locale
	 *            The locale for the messages.
	 * @return A collection of constraint violations.
	 */
	<P, E> Collection<PropertyViolation<E, P>> validateProperty(PropertyDescriptor<P, E> propertyDescriptor, E entity, Locale locale);
}
