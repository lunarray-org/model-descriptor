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
package org.lunarray.model.descriptor.accessor.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;

/**
 * Describes a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 */
public final class PropertyBuilder<P> {
	/** The accessor. */
	private transient Method accessorBuilder;
	/** The annotations. */
	private transient Map<Class<? extends Annotation>, List<Annotation>> annotationsBuilder;
	/** The entity. */
	private transient DescribedEntity<?> entityTypeBuilder;
	/** The generic type. */
	private transient Type genericTypeBuilder;
	/** The modifiers. */
	private transient int modifiersBuilder;
	/** The mutator. */
	private transient Method mutatorBuilder;
	/** The name. */
	private transient String nameBuilder;
	/** The field. */
	private transient Field rawBuilder;
	/** The property type. */
	private transient Class<P> typeBuilder;

	/**
	 * Default constructor.
	 */
	protected PropertyBuilder() {
		// Default constructor.
		this.annotationsBuilder = new HashMap<Class<? extends Annotation>, List<Annotation>>();
	}

	/**
	 * Sets a new value for the accessor field.
	 * 
	 * @param accessor
	 *            The new value for the accessor field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> accessor(final Method accessor) {
		this.accessorBuilder = accessor;
		return this;
	}

	/**
	 * Adds an annotation.
	 * 
	 * @param annotation
	 *            The annotation. May not be null.
	 * @return The builder.
	 */
	public PropertyBuilder<P> addAnnotation(final Annotation annotation) {
		Validate.notNull(annotation, "Annotation may not be null.");
		final Class<? extends Annotation> type = annotation.annotationType();
		if (!this.annotationsBuilder.containsKey(type)) {
			this.annotationsBuilder.put(type, new LinkedList<Annotation>());
		}
		this.annotationsBuilder.get(type).add(annotation);
		return this;
	}

	/**
	 * Sets a new value for the modifiers field.
	 * 
	 * @param modifiers
	 *            The new value for the modifiers field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> addModifier(final int modifiers) {
		this.modifiersBuilder |= modifiers;
		return this;
	}

	/**
	 * Build the property.
	 * 
	 * @return The property.
	 */
	public PersistentProperty<P> build() {
		this.validate();
		return new PersistentProperty<P>(this);
	}

	/**
	 * Build the property.
	 * 
	 * @return The property.
	 */
	public DescribedProperty<P> buildDescribed() {
		this.validate();
		return new DescribedProperty<P>(this);
	}

	/**
	 * Fills with an existing property.
	 * 
	 * @param property
	 *            The property.
	 * @return The builder.
	 */
	@SuppressWarnings("unchecked")
	public PropertyBuilder<P> describedProperty(final DescribedProperty<?> property) {
		this.accessorBuilder = property.getAccessor();
		this.annotationsBuilder = property.getAnnotations();
		this.entityTypeBuilder = property.getEntityType();
		this.genericTypeBuilder = property.getGenericType();
		this.modifiersBuilder = property.getModifiers();
		this.mutatorBuilder = property.getMutator();
		this.nameBuilder = property.getName();
		this.rawBuilder = property.getRaw();
		this.typeBuilder = (Class<P>) property.getType();
		return this;
	}

	/**
	 * Sets a new value for the entityType field.
	 * 
	 * @param entityType
	 *            The new value for the entityType field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> entityType(final DescribedEntity<?> entityType) {
		this.entityTypeBuilder = entityType;
		return this;
	}

	/**
	 * Sets a new value for the genericType field.
	 * 
	 * @param genericType
	 *            The new value for the genericType field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> genericType(final Type genericType) {
		this.genericTypeBuilder = genericType;
		return this;
	}

	/**
	 * Sets a new value for the modifiers field.
	 * 
	 * @param modifiers
	 *            The new value for the modifiers field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> modifiers(final int modifiers) {
		this.modifiersBuilder = modifiers;
		return this;
	}

	/**
	 * Sets a new value for the mutator field.
	 * 
	 * @param mutator
	 *            The new value for the mutator field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> mutator(final Method mutator) {
		this.mutatorBuilder = mutator;
		return this;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> name(final String name) {
		this.nameBuilder = name;
		return this;
	}

	/**
	 * Sets a new value for the field field.
	 * 
	 * @param raw
	 *            The new value for the field field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> raw(final Field raw) {
		this.rawBuilder = raw;
		this.modifiers(raw.getModifiers());
		@SuppressWarnings("unchecked")
		// We know this to be always true.
		final Class<P> rawType = (Class<P>) raw.getType();
		this.type(rawType);
		return this;
	}

	/**
	 * Sets a new value for the propertyType field.
	 * 
	 * @param type
	 *            The new value for the propertyType field.
	 * @return The builder.
	 */
	public PropertyBuilder<P> type(final Class<P> type) {
		this.typeBuilder = type;
		return this;
	}

	/**
	 * Validate that all required fields are set.
	 */
	private void validate() {
		Validate.notNull(this.typeBuilder, "Type must be set.");
		Validate.notNull(this.nameBuilder, "Name must be set.");
		Validate.notNull(this.genericTypeBuilder, "Generic type must be set.");
	}

	/**
	 * Gets the value for the accessorBuilder field.
	 * 
	 * @return The value for the accessorBuilder field.
	 */
	protected Method getAccessorBuilder() {
		return this.accessorBuilder;
	}

	/**
	 * Gets the value for the annotationsBuilder field.
	 * 
	 * @return The value for the annotationsBuilder field.
	 */
	protected Map<Class<? extends Annotation>, List<Annotation>> getAnnotationsBuilder() {
		return this.annotationsBuilder;
	}

	/**
	 * Gets the value for the entityTypeBuilder field.
	 * 
	 * @return The value for the entityTypeBuilder field.
	 */
	protected DescribedEntity<?> getEntityTypeBuilder() {
		return this.entityTypeBuilder;
	}

	/**
	 * Gets the value for the genericTypeBuilder field.
	 * 
	 * @return The value for the genericTypeBuilder field.
	 */
	protected Type getGenericTypeBuilder() {
		return this.genericTypeBuilder;
	}

	/**
	 * Gets the value for the modifiersBuilder field.
	 * 
	 * @return The value for the modifiersBuilder field.
	 */
	protected int getModifiersBuilder() {
		return this.modifiersBuilder;
	}

	/**
	 * Gets the value for the mutatorBuilder field.
	 * 
	 * @return The value for the mutatorBuilder field.
	 */
	protected Method getMutatorBuilder() {
		return this.mutatorBuilder;
	}

	/**
	 * Gets the value for the nameBuilder field.
	 * 
	 * @return The value for the nameBuilder field.
	 */
	protected String getNameBuilder() {
		return this.nameBuilder;
	}

	/**
	 * Gets the value for the rawBuilder field.
	 * 
	 * @return The value for the rawBuilder field.
	 */
	protected Field getRawBuilder() {
		return this.rawBuilder;
	}

	/**
	 * Gets the value for the typeBuilder field.
	 * 
	 * @return The value for the typeBuilder field.
	 */
	protected Class<P> getTypeBuilder() {
		return this.typeBuilder;
	}
}
