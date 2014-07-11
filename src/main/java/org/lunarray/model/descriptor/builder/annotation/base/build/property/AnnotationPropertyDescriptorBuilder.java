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
package org.lunarray.model.descriptor.builder.annotation.base.build.property;

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.accessor.reference.property.PropertyValueReference;
import org.lunarray.model.descriptor.builder.annotation.base.build.AnnotationBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.lunarray.model.descriptor.model.relation.RelationType;

/**
 * Describes an annotation builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public interface AnnotationPropertyDescriptorBuilder<P, E, B extends AnnotationBuilderContext>
		extends AnnotationBuilder<B> {

	/**
	 * Build the descriptor.
	 * 
	 * @return The descriptor.
	 */
	PropertyDescriptor<P, E> build();

	/**
	 * Sets the cardinality.
	 * 
	 * @param cardinality
	 *            The cardinality.
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> cardinality(final Cardinality cardinality);

	/**
	 * Updates the entity type.
	 * 
	 * @param entityType
	 *            the entity type.
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> entityType(final DescribedEntity<E> entityType);

	/**
	 * Gets the cardinality.
	 * 
	 * @return The cardinality.
	 */
	Cardinality getCardinality();

	/**
	 * Gets the field.
	 * 
	 * @return The field.
	 */
	DescribedProperty<P> getProperty();

	/**
	 * Gets the property type.
	 * 
	 * @return The property type.
	 */
	Class<P> getPropertyType();

	/**
	 * Gets the relation type.
	 * 
	 * @return The type of relation or null for unrelated.
	 */
	RelationType getRelationType();

	/**
	 * Gets the value reference.
	 * 
	 * @return The value reference.
	 */
	PropertyValueReference<P, E> getValueReference();

	/**
	 * Makes this property immutable.
	 * 
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> immutable();

	/**
	 * Tests if this property is immutable.
	 * 
	 * @return True if and only if the property is immutable.
	 */
	boolean isImmutable();

	/**
	 * Makes this a key property.
	 * 
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> key();

	/**
	 * Makes the property writable.
	 * 
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> mutable();

	/**
	 * Updates the property name.
	 * 
	 * @param name
	 *            The name.
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> name(final String name);

	/**
	 * Sets the property.
	 * 
	 * @param property
	 *            The property.
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> property(final DescribedProperty<?> property);

	/**
	 * Register an extension.
	 * 
	 * @param extensionInt
	 *            The extension interface.
	 * @param extension
	 *            The extension.
	 */
	void registerExtension(final Class<? extends PropertyExtension<P, E>> extensionInt, final PropertyExtension<P, E> extension);

	/**
	 * Makes this property related to a concrete type.
	 * 
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> related();

	/**
	 * Provides the related entity name.
	 * 
	 * @param relatedName
	 *            The related entity name.
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> relatedName(final String relatedName);

	/**
	 * Makes this property related to a reference.
	 * 
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> relatedReference();

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            The type.
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> type(final Class<?> type);

	/**
	 * Make property unrelated.
	 * 
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> unrelated();

	/**
	 * Updates the value reference.
	 * 
	 * @param valueReference
	 *            The value reference.
	 * @return The builder.
	 */
	AnnotationPropertyDescriptorBuilder<P, E, B> valueReference(final PropertyValueReference<?, E> valueReference);
}
