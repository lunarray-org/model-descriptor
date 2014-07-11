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
package org.lunarray.model.descriptor.qualifier;

import java.util.Set;

import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;

/**
 * Describes a qualifier enabled entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public interface QualifierEntityDescriptor<E>
		extends EntityDescriptor<E> {

	/**
	 * Gets all the operations marked with a given qualifier.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The operations.
	 */
	Set<OperationDescriptor<E>> getOperations(Class<?> qualifier);

	/**
	 * Gets all the properties marked with a given qualifier.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The properties.
	 */
	Set<PropertyDescriptor<?, E>> getProperties(Class<?> qualifier);

	/**
	 * Gets an adapted entity that is marked with a given qualifier.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The entity.
	 */
	QualifierEntityDescriptor<E> getQualifierEntity(Class<?> qualifier);

	/**
	 * Gets a operation by name marked with a given qualifier.
	 * 
	 * @param name
	 *            The operation name.
	 * @param qualifier
	 *            The qualifier.
	 * @return The operation.
	 */
	OperationDescriptor<E> getQualifierOperation(String name, Class<?> qualifier);

	/**
	 * Gets a property by name marked with a given qualifier.
	 * 
	 * @param name
	 *            The property name.
	 * @param qualifier
	 *            The qualifier.
	 * @return The property.
	 */
	PropertyDescriptor<?, E> getQualifierProperty(String name, Class<?> qualifier);

	/**
	 * Gets a property by name and type marked with a given qualifier.
	 * 
	 * @param name
	 *            The property name.
	 * @param qualifier
	 *            The qualifier.
	 * @param propertyType
	 *            The property type.
	 * @return The property.
	 * @param <P>
	 *            The property type.
	 */
	<P> PropertyDescriptor<P, E> getQualifierProperty(String name, Class<?> qualifier, Class<P> propertyType);

	/**
	 * Gets all (known) qualifiers.
	 * 
	 * @return A set of qualifiers.
	 */
	Set<Class<?>> getQualifiers();
}
