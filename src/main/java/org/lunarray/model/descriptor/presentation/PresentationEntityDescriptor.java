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
package org.lunarray.model.descriptor.presentation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;

/**
 * Describes a presentation entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public interface PresentationEntityDescriptor<E>
		extends EntityDescriptor<E>, Described {

	/**
	 * Gets the property that represents the name of this entity.
	 * 
	 * @return The name property.
	 */
	PropertyDescriptor<?, E> getNameProperty();

	/**
	 * Gets the ordered list of properties.
	 * 
	 * @return The properties.
	 */
	List<MemberDescriptor<E>> getOrderedMembers();

	/**
	 * Gets the ordered list of properties.
	 * 
	 * @return The properties.
	 */
	List<OperationDescriptor<E>> getOrderedOperations();

	/**
	 * Gets the ordered list of properties.
	 * 
	 * @return The properties.
	 */
	List<PropertyDescriptor<?, E>> getOrderedProperties();

	/**
	 * Gets the resource bundle.
	 * 
	 * @return The resource bundle.
	 */
	ResourceBundle getResourceBundle();

	/**
	 * Gets the resource bundle for a given locale.
	 * 
	 * @param locale
	 *            The given locale.
	 * @return The resources bundle, or null.
	 */
	ResourceBundle getResourceBundle(Locale locale);

	/**
	 * Indicates whether this entity should be visible.
	 * 
	 * @return True if and only if it should be visible.
	 */
	boolean isVisible();
}
