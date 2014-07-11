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
package org.lunarray.model.descriptor.builder.annotation.base.build.entity;

import java.io.Serializable;
import java.util.Collection;

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.base.build.AnnotationBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationCollectionPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.EntityExtension;

/**
 * Describes an entity description builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder context type.
 */
public interface AnnotationEntityDescriptorBuilder<E, K extends Serializable, B extends AnnotationBuilderContext>
		extends AnnotationBuilder<B> {

	/**
	 * Describes a property.
	 * 
	 * @param alias
	 *            The alias property.
	 * @param aliasFor
	 *            The alias the property is for.
	 */
	void addAlias(DescribedProperty<?> alias, String aliasFor);

	/**
	 * Adds a collection property.
	 * 
	 * @param <C>
	 *            The collection type.
	 * @param <P>
	 *            The property type.
	 * @return A property builder.
	 */
	<C, P extends Collection<C>> AnnotationCollectionPropertyDescriptorBuilder<C, P, E, B> addCollectionProperty();

	/**
	 * Adds a operation.
	 * 
	 * @return A operation builder.
	 */
	AnnotationOperationDescriptorBuilder<E, B> addOperation();

	/**
	 * Adds a property.
	 * 
	 * @return A property builder.
	 */
	AnnotationPropertyDescriptorBuilder<?, E, B> addProperty();

	/**
	 * Build the entity.
	 * 
	 * @return The entity.
	 */
	EntityDescriptor<E> build();

	/**
	 * Updates the entity type.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @return The builder.
	 */
	AnnotationEntityDescriptorBuilder<E, K, B> entity(final DescribedEntity<E> entityType);

	/**
	 * Gets the entity type.
	 * 
	 * @return The entity type.
	 */
	DescribedEntity<E> getEntityType();

	/**
	 * Gets the name.
	 * 
	 * @return The name.
	 */
	String getName();

	/**
	 * Update the name.
	 * 
	 * @param name
	 *            The name.
	 * @return The builder.
	 */
	AnnotationEntityDescriptorBuilder<E, K, B> name(final String name);

	/**
	 * Registers a creational strategy.
	 * 
	 * @param strategy
	 *            The creational strategy.
	 */
	void registerCreationalStrategy(CreationalStrategy<E> strategy);

	/**
	 * Register an extension.
	 * 
	 * @param extensionInt
	 *            The extension interface.
	 * @param extension
	 *            The extension.
	 */
	void registerExtension(final Class<? extends EntityExtension<E>> extensionInt, final EntityExtension<E> extension);
}
