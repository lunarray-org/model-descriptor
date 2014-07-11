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
package org.lunarray.model.descriptor.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.lunarray.model.descriptor.adapter.Adaptable;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;

/**
 * Describes an entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 */
public interface EntityDescriptor<E>
		extends Adaptable, Serializable {

	/**
	 * Create an instance of this entity.
	 * 
	 * @return The entity.
	 * @throws CreationException
	 *             Thrown if the instance could not be created.
	 */
	E createEntity() throws CreationException;

	/**
	 * Gets a collection of creational strategies.
	 * 
	 * @return The creational strategies.
	 */
	Collection<CreationalStrategy<E>> creationalStrategies();

	/**
	 * Gets an extension.
	 * 
	 * @param <X>
	 *            The extension type.
	 * @param extensionType
	 *            The extension type.
	 * @return The extension.
	 */
	<X extends EntityExtension<E>> X extension(Class<X> extensionType);

	/**
	 * Gets the entity type.
	 * 
	 * @return The entity type.
	 */
	Class<E> getEntityType();

	/**
	 * Gets a member by name.
	 * 
	 * @param name
	 *            The name of the member.
	 * @return The member, or null.
	 */
	MemberDescriptor<E> getMember(String name);

	/**
	 * Gets the entities members.
	 * 
	 * @return The members.
	 */
	Set<MemberDescriptor<E>> getMembers();

	/**
	 * Gets the entity name.
	 * 
	 * @return The entity name.
	 */
	String getName();

	/**
	 * Gets a operation by name.
	 * 
	 * @param name
	 *            The name of the operation.
	 * @return The operation, or null.
	 */
	OperationDescriptor<E> getOperation(String name);

	/**
	 * Gets the entities operations.
	 * 
	 * @return The operations.
	 */
	Set<OperationDescriptor<E>> getOperations();

	/**
	 * Gets the entities properties.
	 * 
	 * @return The properties.
	 */
	Set<PropertyDescriptor<?, E>> getProperties();

	/**
	 * Gets a property by name.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return The property, or null.
	 */
	PropertyDescriptor<?, E> getProperty(String name);

	/**
	 * Gets a property by name and test it's type.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param name
	 *            The property name.
	 * @param propertyType
	 *            The property type.
	 * @return The property.
	 */
	<P> PropertyDescriptor<P, E> getProperty(String name, Class<P> propertyType);
}
