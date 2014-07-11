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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Describes a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 */
public abstract class AbstractProperty<P>
		implements Property<P> {
	/**
	 * Creates a builder.
	 * 
	 * @param <P>
	 *            The property type.
	 * @return The builder.
	 */
	public static <P> PropertyBuilder<P> createBuilder() {
		return new PropertyBuilder<P>();
	}
	/** The accessor. */
	private transient Method accessor;
	/** The modifiers. */
	private transient int modifiers;
	/** The mutator. */
	private transient Method mutator;
	/** The name. */
	private transient String name;
	/** The field. */
	private transient Field raw;

	/** The property type. */
	private transient Class<P> type;

	/**
	 * Default constructor.
	 */
	public AbstractProperty() {
		// Default constructor.
	}

	/**
	 * Default constructor.
	 * 
	 * @param property
	 *            The prototype property.
	 */
	protected AbstractProperty(final Property<P> property) {
		this.accessor = property.getAccessor();
		this.modifiers = property.getModifiers();
		this.mutator = property.getMutator();
		this.name = property.getName();
		this.raw = property.getRaw();
		this.type = property.getType();
	}

	/**
	 * Constructs the property.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected AbstractProperty(final PropertyBuilder<P> builder) {
		this.raw = builder.getRawBuilder();
		this.accessor = builder.getAccessorBuilder();
		this.type = builder.getTypeBuilder();
		this.mutator = builder.getMutatorBuilder();
		this.name = builder.getNameBuilder();
		this.modifiers = builder.getModifiersBuilder();
	}

	/**
	 * Gets the value for the accessor field.
	 * 
	 * @return The value for the accessor field.
	 */
	@Override
	public final Method getAccessor() {
		return this.accessor;
	}

	/**
	 * Gets the value for the modifiers field.
	 * 
	 * @return The value for the modifiers field.
	 */
	@Override
	public final int getModifiers() {
		return this.modifiers;
	}

	/**
	 * Gets the value for the mutator field.
	 * 
	 * @return The value for the mutator field.
	 */
	@Override
	public final Method getMutator() {
		return this.mutator;
	}

	/**
	 * Gets the value for the name field.
	 * 
	 * @return The value for the name field.
	 */
	@Override
	public final String getName() {
		return this.name;
	}

	/**
	 * Gets the value for the raw field.
	 * 
	 * @return The value for the raw field.
	 */
	@Override
	public final Field getRaw() {
		return this.raw;
	}

	/**
	 * Gets the value for the type field.
	 * 
	 * @return The value for the type field.
	 */
	@Override
	public final Class<P> getType() {
		return this.type;
	}

	/**
	 * Sets a new value for the accessor field.
	 * 
	 * @param accessor
	 *            The new value for the accessor field.
	 */
	public final void setAccessor(final Method accessor) {
		this.accessor = accessor;
	}

	/**
	 * Sets a new value for the modifiers field.
	 * 
	 * @param modifiers
	 *            The new value for the modifiers field.
	 */
	public final void setModifiers(final int modifiers) {
		this.modifiers = modifiers;
	}

	/**
	 * Sets a new value for the mutator field.
	 * 
	 * @param mutator
	 *            The new value for the mutator field.
	 */
	public final void setMutator(final Method mutator) {
		this.mutator = mutator;
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
	 * Sets a new value for the raw field.
	 * 
	 * @param raw
	 *            The new value for the raw field.
	 */
	public final void setRaw(final Field raw) {
		this.raw = raw;
	}

	/**
	 * Sets a new value for the type field.
	 * 
	 * @param type
	 *            The new value for the type field.
	 */
	public final void setType(final Class<P> type) {
		this.type = type;
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Property[\n\tType: ").append(this.type);
		builder.append("\n\tName: ").append(this.name);
		builder.append("\n]");
		return builder.toString();
	}
}
