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
package org.lunarray.model.descriptor.accessor.reference.property.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.property.PersistentProperty;
import org.lunarray.model.descriptor.accessor.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Property accessor, enables the getting and setting of a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <O>
 *            The containing type.
 */
public final class PropertyReference<P, O>
		implements Serializable {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReference.class);
	/** Serial id. */
	private static final long serialVersionUID = 5739750458043910642L;

	/**
	 * Created a builder.
	 * 
	 * @param <P>
	 *            The type the builder is exposing.
	 * @return The builder.
	 */
	public static <P> Builder<P> createBuilder() {
		return new Builder<P>();
	}

	/** The property field. */
	private PersistentProperty<P> property;

	/**
	 * Constructs the accessor.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected PropertyReference(final Builder<P> builder) {
		this.property = builder.propertyBuilder;
	}

	/**
	 * Gets the value for the property field.
	 * 
	 * @return The value for the property field.
	 */
	public Property<P> getProperty() {
		return this.property;
	}

	/**
	 * Gets the value for the containing type.
	 * 
	 * @param containingType
	 *            The containing type.
	 * @return The value.
	 * @throws ValueAccessException
	 *             If the value could not be accessed.
	 */
	@SuppressWarnings("unchecked")
	/* Deduced value. */
	public P getValue(final O containingType) throws ValueAccessException {
		PropertyReference.LOGGER.debug("Getting property value for {}", containingType);
		P result;
		if (CheckUtil.isNull(this.property.getAccessor())) {
			try {
				result = AccessController.doPrivileged(new Access(containingType));
			} catch (final PrivilegedActionException e) {
				throw new ValueAccessException(e);
			}
		} else {
			try {
				final Method accessor = this.property.getAccessor();
				PropertyReference.LOGGER.debug("Accessing accessor {} for entity {}", accessor, containingType);
				result = (P) accessor.invoke(containingType);
			} catch (final IllegalArgumentException e) {
				throw new ValueAccessException(e);
			} catch (final IllegalAccessException e) {
				throw new ValueAccessException(e);
			} catch (final InvocationTargetException e) {
				throw new ValueAccessException(e);
			}
		}
		return result;
	}

	/**
	 * Accessor is accessible.
	 * 
	 * @return True if and only if the accessor is not null.
	 */
	public boolean isAccessible() {
		return !CheckUtil.isNull(this.property.getAccessor());
	}

	/**
	 * Accessor is mutable.
	 * 
	 * @return True if and only if the mutator is not null.
	 */
	public boolean isMutable() {
		return !CheckUtil.isNull(this.property.getMutator());
	}

	/**
	 * Sets a new value for the property field.
	 * 
	 * @param property
	 *            The new value for the property field.
	 */
	public void setProperty(final PersistentProperty<P> property) {
		this.property = property;
	}

	/**
	 * Sets the value for the containing type.
	 * 
	 * @param value
	 *            The value to set.
	 * @param containingType
	 *            The containing type.
	 * @throws ValueAccessException
	 *             If the value could not be accessed.
	 */
	public void setValue(final P value, final O containingType) throws ValueAccessException {
		PropertyReference.LOGGER.debug("Setting property value for {}", containingType);
		if (CheckUtil.isNull(this.property.getMutator())) {
			try {
				AccessController.doPrivileged(new Mutate(value, containingType));
			} catch (final PrivilegedActionException e) {
				throw new ValueAccessException(e);
			}
		} else {
			try {
				final Method mutator = this.property.getMutator();
				PropertyReference.LOGGER.debug("Accessing mutator {} for entity {} with value {}", mutator, containingType, value);
				mutator.invoke(containingType, value);
			} catch (final IllegalArgumentException e) {
				throw new ValueAccessException(e);
			} catch (final IllegalAccessException e) {
				throw new ValueAccessException(e);
			} catch (final InvocationTargetException e) {
				throw new ValueAccessException(e);
			}
		}
	}

	/**
	 * Gets the value for the type field.
	 * 
	 * @return The value for the type field.
	 */
	protected Class<P> getType() {
		return this.property.getType();
	}

	/**
	 * A builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <P>
	 *            The property type.
	 */
	public static final class Builder<P> {
		/** The property field. */
		private transient PersistentProperty<P> propertyBuilder;

		/**
		 * Default constructor.
		 */
		protected Builder() {
			// Default constructor.
		}

		/**
		 * Builds the accessor.
		 * 
		 * @param <O>
		 *            The containing type.
		 * @return The accessor.
		 */
		public <O> PropertyReference<P, O> build() {
			Validate.notNull(this.propertyBuilder, "Property must be set.");
			return new PropertyReference<P, O>(this);
		}

		/**
		 * Sets the property field.
		 * 
		 * @param property
		 *            The new value. Must be set.
		 * @return The builder
		 */
		public Builder<P> property(final PersistentProperty<P> property) {
			this.propertyBuilder = property;
			return this;
		}
	}

	/**
	 * The access action, if a value is not publicly accessible.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class Access
			implements PrivilegedExceptionAction<P> {

		/** The containing value. */
		private final transient O containingType;

		/**
		 * Constructs the action.
		 * 
		 * @param containingType
		 *            The containing value.
		 */
		public Access(final O containingType) {
			this.containingType = containingType;
		}

		/** {@inheritDoc} */
		@SuppressWarnings("unchecked")
		/* Deduced value. */
		@Override
		public P run() throws ValueAccessException {
			final Field accessorField = PropertyReference.this.property.getRaw();
			PropertyReference.LOGGER.debug("Accessing field {} for {}", accessorField, this.containingType);
			final boolean accessible = accessorField.isAccessible();
			accessorField.setAccessible(true);
			P value = null;
			try {
				value = (P) accessorField.get(this.containingType);
			} catch (final IllegalArgumentException e) {
				throw new ValueAccessException(e);
			} catch (final IllegalAccessException e) {
				throw new ValueAccessException(e);
			}
			accessorField.setAccessible(accessible);
			return value;
		}
	}

	/**
	 * The mutate action, if the mutator is not publicly accessible.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class Mutate
			implements PrivilegedExceptionAction<Void> {

		/** The containing type. */
		private final transient O containingType;
		/** The new value. */
		private final transient P value;

		/**
		 * Constructs the action.
		 * 
		 * @param value
		 *            The value to set.
		 * @param containingType
		 *            The containing value.
		 */
		public Mutate(final P value, final O containingType) {
			this.value = value;
			this.containingType = containingType;
		}

		/** {@inheritDoc} */
		@Override
		public Void run() throws ValueAccessException {
			final Field accessorField = PropertyReference.this.property.getRaw();
			PropertyReference.LOGGER.debug("Mutating field {} for {}, setting {}", accessorField, this.containingType, this.value);
			final boolean accessible = accessorField.isAccessible();
			accessorField.setAccessible(true);
			try {
				accessorField.set(this.containingType, this.value);
			} catch (final IllegalArgumentException e) {
				throw new ValueAccessException(e);
			} catch (final IllegalAccessException e) {
				throw new ValueAccessException(e);
			}
			accessorField.setAccessible(accessible);
			return null;
		}
	}
}
