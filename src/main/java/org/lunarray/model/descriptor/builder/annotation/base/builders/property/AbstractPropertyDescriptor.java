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
package org.lunarray.model.descriptor.builder.annotation.base.builders.property;

import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.reference.property.PropertyValueReference;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.util.PrimitiveUtil;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Represents a property descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public abstract class AbstractPropertyDescriptor<P, E>
		implements AnnotationPropertyDescriptor<P, E> {

	/** Serial id. */
	private static final long serialVersionUID = 7258047746504646235L;
	/** The fields cardinality. */
	private Cardinality cardinality;
	/** The entity type. */
	private Class<E> entityType;
	/** The property extensions. */
	private Map<Class<?>, PropertyExtension<P, E>> extensions;
	/** Immutable indication. */
	private boolean immutable;
	/** The key. */
	private boolean keyProperty;
	/** The name. */
	private String name;
	/** The type. */
	private Class<P> propertyType;
	/** The related entity name. */
	private String relatedName;
	/** The related property. */
	private RelationType relationType;
	/** Value reference. */
	private PropertyValueReference<P, E> valueReference;

	/**
	 * Constructs the property descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected AbstractPropertyDescriptor(final AbstractPropertyDescriptorBuilder<P, E, ?> builder) {
		Validate.notNull(builder, "Builder may not be null.");
		this.propertyType = builder.getPropertyType();
		this.name = builder.getName();
		this.valueReference = builder.getValueReference();
		this.cardinality = builder.getCardinality();
		this.relatedName = builder.getRelatedName();
		this.relationType = builder.getRelationType();
		this.immutable = builder.isImmutable();
		this.keyProperty = builder.isKey();
		this.extensions = builder.getExtensions();
		this.entityType = builder.getEntityType().getEntityType();
	}

	/** {@inheritDoc} */
	@Override
	public final <X extends PropertyExtension<P, E>> X extension(final Class<X> extensionClazz) {
		X extension = null;
		if (this.extensions.containsKey(extensionClazz)) {
			extension = extensionClazz.cast(this.extensions.get(extensionClazz));
		}
		return extension;
	}

	/** {@inheritDoc} */
	@Override
	public final Cardinality getCardinality() {
		return this.cardinality;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<E> getEntityType() {
		return this.entityType;
	}

	/**
	 * Gets the value for the extensions field.
	 * 
	 * @return The value for the extensions field.
	 */
	public final Map<Class<?>, PropertyExtension<P, E>> getExtensions() {
		return this.extensions;
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<P> getPropertyType() {
		return this.propertyType;
	}

	/** {@inheritDoc} */
	@Override
	public final String getRelatedName() {
		return this.relatedName;
	}

	/** {@inheritDoc} */
	@Override
	public final RelationType getRelationType() {
		return this.relationType;
	}

	/** {@inheritDoc} */
	@Override
	public final P getValue(final E parent) throws ValueAccessException {
		return this.valueReference.getValue(parent);
	}

	/**
	 * Gets the value for the valueReference field.
	 * 
	 * @return The value for the valueReference field.
	 */
	public final PropertyValueReference<P, E> getValueReference() {
		return this.valueReference;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isAssignable(final Object value) {
		boolean result;
		if (CheckUtil.isNull(value)) {
			result = Cardinality.NULLABLE == this.getCardinality();
		} else {
			final Class<?> valueType = value.getClass();
			if (valueType.equals(this.getPropertyType())) {
				result = true;
			} else if (this.getPropertyType().isPrimitive()) {
				result = this.isAssignablePrimitive(valueType);
			} else {
				result = this.getPropertyType().isAssignableFrom(valueType);
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isImmutable() {
		return this.immutable;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isKeyProperty() {
		return this.keyProperty;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isRelation() {
		return !CheckUtil.isNull(this.relationType);
	}

	/**
	 * Sets a new value for the cardinality field.
	 * 
	 * @param cardinality
	 *            The new value for the cardinality field.
	 */
	public final void setCardinality(final Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	/**
	 * Sets a new value for the entityType field.
	 * 
	 * @param entityType
	 *            The new value for the entityType field.
	 */
	public final void setEntityType(final Class<E> entityType) {
		this.entityType = entityType;
	}

	/**
	 * Sets a new value for the extensions field.
	 * 
	 * @param extensions
	 *            The new value for the extensions field.
	 */
	public final void setExtensions(final Map<Class<?>, PropertyExtension<P, E>> extensions) {
		this.extensions = extensions;
	}

	/**
	 * Sets a new value for the immutable field.
	 * 
	 * @param immutable
	 *            The new value for the immutable field.
	 */
	public final void setImmutable(final boolean immutable) {
		this.immutable = immutable;
	}

	/**
	 * Sets a new value for the keyProperty field.
	 * 
	 * @param keyProperty
	 *            The new value for the keyProperty field.
	 */
	public final void setKeyProperty(final boolean keyProperty) {
		this.keyProperty = keyProperty;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets a new value for the propertyType field.
	 * 
	 * @param propertyType
	 *            The new value for the propertyType field.
	 */
	public final void setPropertyType(final Class<P> propertyType) {
		this.propertyType = propertyType;
	}

	/**
	 * Sets a new value for the relatedName field.
	 * 
	 * @param relatedName
	 *            The new value for the relatedName field.
	 */
	public final void setRelatedName(final String relatedName) {
		this.relatedName = relatedName;
	}

	/**
	 * Sets a new value for the relationType field.
	 * 
	 * @param relationType
	 *            The new value for the relationType field.
	 */
	public final void setRelationType(final RelationType relationType) {
		this.relationType = relationType;
	}

	/** {@inheritDoc} */
	@Override
	public final void setValue(final E parent, final P value) throws ValueAccessException {
		this.valueReference.setValue(parent, value);
	}

	/**
	 * Sets a new value for the valueReference field.
	 * 
	 * @param valueReference
	 *            The new value for the valueReference field.
	 */
	public final void setValueReference(final PropertyValueReference<P, E> valueReference) {
		this.valueReference = valueReference;
	}

	/**
	 * Tests if the value type is an assignable primitive type.
	 * 
	 * @param valueType
	 *            The value type.
	 * @return True if and only if it is assignable.
	 */
	private boolean isAssignablePrimitive(final Class<?> valueType) {
		boolean result;
		final Class<?> objectType = PrimitiveUtil.getObjectType(this.getPropertyType());
		if (CheckUtil.isNull(objectType)) {
			result = false;
		} else {
			result = objectType.isAssignableFrom(valueType);
		}
		return result;
	}

	/**
	 * Adapt to a new type.
	 * 
	 * @param <A>
	 *            The new type.
	 * @param clazz
	 *            The new type.
	 * @return The current instance is the new type.
	 */
	protected final <A> A propertyAdapt(final Class<A> clazz) {
		A value = null;
		if (RelationDescriptor.class.equals(clazz)) {
			if (this.isRelation()) {
				value = clazz.cast(this);
			}
		} else if (clazz.isInstance(this)) {
			value = clazz.cast(this);
		}
		return value;
	}

	/**
	 * Tests if adapting to the new type is possible.
	 * 
	 * @param clazz
	 *            The new type.
	 * @return True if and only if the current instance is adaptable.
	 */
	protected final boolean propertyAdaptable(final Class<?> clazz) {
		boolean result = true;
		if (RelationDescriptor.class.equals(clazz)) {
			result = this.isRelation();
		} else {
			result = clazz.isInstance(this);
		}
		return result;
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void propertyToString(final StringBuilder builder) {
		builder.append("\tName: ").append(this.name);
		builder.append("\n\tType: ").append(this.propertyType);
		builder.append("\n\tImmutable: ").append(this.immutable);
		builder.append("\n\tCardinality: ").append(this.cardinality);
		builder.append("\n\tIs Key Property: ").append(this.keyProperty);
		if (this.isRelation()) {
			builder.append("\n\trelation type: ").append(this.relationType);
			builder.append("\n\tRelated Name: ").append(this.relatedName);
		}
		builder.append("\n\tExtensions: {\n\t\t");
		StringUtil.commaSeparated(this.extensions.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n");
	}
}
