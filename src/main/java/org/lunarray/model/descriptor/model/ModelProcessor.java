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

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.lunarray.model.descriptor.model.property.PropertyExtension;

/**
 * A model processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The entity super type.
 */
public interface ModelProcessor<S> {

	/**
	 * Processes an entity type.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param entityType
	 *            The entity type.
	 * @param extensionContainer
	 *            The extension container associated with the model.
	 * @return Optionally returns an extension.
	 */
	<E extends S> EntityExtension<E> process(DescribedEntity<E> entityType, ExtensionContainer extensionContainer);

	/**
	 * Processes a operation type.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param entityType
	 *            The entity type.
	 * @param extensionContainer
	 *            The extension container associated with the model.
	 * @return Optionally returns an extension.
	 */
	<E extends S> OperationExtension<E> process(DescribedOperation entityType, ExtensionContainer extensionContainer);

	/**
	 * Processes a property.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param <P>
	 *            The property type.
	 * @param property
	 *            The property.
	 * @param extensionContainer
	 *            The extension container associated with the model.
	 * @return Optionally returns an extension.
	 */
	<E extends S, P> PropertyExtension<P, E> process(DescribedProperty<P> property, ExtensionContainer extensionContainer);

	/**
	 * Process the fact that the model built this.
	 * 
	 * @param extensionContainer
	 *            The extension container associated with the model.
	 * 
	 * @return Possibly an extension.
	 */
	Extension process(ExtensionContainer extensionContainer);
}
