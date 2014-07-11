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
package org.lunarray.model.descriptor.model;

import java.io.Serializable;
import java.util.List;

import org.lunarray.model.descriptor.adapter.Adaptable;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;

/**
 * Describes a model.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The super type of the entities in the model.
 */
public interface Model<S>
		extends Adaptable, Serializable {

	/**
	 * Gets a list of all entities.
	 * 
	 * @return The list of all entities.
	 */
	List<EntityDescriptor<? extends S>> getEntities();

	/**
	 * Gets an entity by type.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param entityType
	 *            The entity type.
	 * @return The entity.
	 */
	<E extends S> EntityDescriptor<E> getEntity(Class<E> entityType);

	/**
	 * Gets an entity by name.
	 * 
	 * @param entityKey
	 *            The entity key.
	 * @return The entity.
	 */
	EntityDescriptor<?> getEntity(String entityKey);

	/**
	 * Gets an entity by name.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param entityKey
	 *            The entity key.
	 * @param entityType
	 *            The entity type.
	 * @return The entity.
	 */
	<E extends S> EntityDescriptor<E> getEntity(String entityKey, Class<E> entityType);

	/**
	 * Gets an extension.
	 * 
	 * @param <X>
	 *            The extension type.
	 * @param extensionType
	 *            The extension type.
	 * @return The extension, or null.
	 */
	<X extends Extension> X getExtension(Class<X> extensionType);

	/**
	 * Gets the extension container.
	 * 
	 * @return The extension container.
	 */
	ExtensionContainer getExtensionContainer();

	/**
	 * Gets an extension reference.
	 * 
	 * @param <X>
	 *            The extension type.
	 * @param extensionType
	 *            The extension type.
	 * @return The extension reference, or null.
	 */
	<X extends Extension> ExtensionRef<X> getExtensionRef(Class<X> extensionType);
}
