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

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.reference.property.PropertyValueReference;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A deferred value reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The current type.
 * @param <C>
 *            The containing entity type.
 * @param <D>
 *            The defered property type
 */
public final class DeferredPropertyValueReference<P, C, D>
		implements PropertyValueReference<P, C> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DeferredPropertyValueReference.class);
	/** Serial id. */
	private static final long serialVersionUID = -3496567607246324031L;

	/**
	 * Creates a builder.
	 * 
	 * @param <P>
	 *            The type.
	 * @param <C>
	 *            The parent.
	 * @param <D>
	 *            The intermediate.
	 * @return A new builder.
	 */
	public static <P, C, D> Builder<P, C, D> createBuilder() {
		return new Builder<P, C, D>();
	}

	/** An optional reference to an object factory. */
	private ExtensionRef<ObjectFactory> objectFactoryReference;

	/** The indirection accessor. */
	private PropertyReference<D, C> propertyAccessor;

	/** The deferred value reference. */
	private PropertyValueReference<P, D> valueReference;

	/**
	 * Constructs the value reference.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected DeferredPropertyValueReference(final Builder<P, C, D> builder) {
		this.valueReference = builder.valueReferenceBuilder;
		this.propertyAccessor = builder.propertyAccessorBuilder;
		this.objectFactoryReference = builder.objectFactoryRefBuilder;
	}

	/**
	 * Gets the value for the objectFactoryReference field.
	 * 
	 * @return The value for the objectFactoryReference field.
	 */
	public ExtensionRef<ObjectFactory> getObjectFactoryReference() {
		return this.objectFactoryReference;
	}

	/**
	 * Gets the value for the propertyAccessor field.
	 * 
	 * @return The value for the propertyAccessor field.
	 */
	public PropertyReference<D, C> getPropertyAccessor() {
		return this.propertyAccessor;
	}

	/**
	 * {@inheritDoc} Additionally, if the current property value is "null",
	 * creates a new current property value. Sets this in the parent and defers.
	 */
	@Override
	public P getValue(final C parentType) throws ValueAccessException {
		return this.valueReference.getValue(this.getParentValue(parentType));
	}

	/**
	 * Gets the value for the valueReference field.
	 * 
	 * @return The value for the valueReference field.
	 */
	public PropertyValueReference<P, D> getValueReference() {
		return this.valueReference;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAccessible() {
		return this.valueReference.isAccessible();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMutable() {
		return this.valueReference.isMutable();
	}

	/**
	 * Sets a new value for the objectFactoryReference field.
	 * 
	 * @param objectFactoryReference
	 *            The new value for the objectFactoryReference field.
	 */
	public void setObjectFactoryReference(final ExtensionRef<ObjectFactory> objectFactoryReference) {
		this.objectFactoryReference = objectFactoryReference;
	}

	/**
	 * Sets a new value for the propertyAccessor field.
	 * 
	 * @param propertyAccessor
	 *            The new value for the propertyAccessor field.
	 */
	public void setPropertyAccessor(final PropertyReference<D, C> propertyAccessor) {
		this.propertyAccessor = propertyAccessor;
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(final C parentType, final P value) throws ValueAccessException {
		this.valueReference.setValue(this.getParentValue(parentType), value);
	}

	/**
	 * Sets a new value for the valueReference field.
	 * 
	 * @param valueReference
	 *            The new value for the valueReference field.
	 */
	public void setValueReference(final PropertyValueReference<P, D> valueReference) {
		this.valueReference = valueReference;
	}

	/**
	 * Gets the parent value.
	 * 
	 * @param parentType
	 *            The parent type.
	 * @return The value.
	 * @throws ValueAccessException
	 *             Thrown if the value could not be accessed.
	 */
	private D getParentValue(final C parentType) throws ValueAccessException {
		final D deferredValue = this.propertyAccessor.getValue(parentType);
		final D trueValue;
		if (CheckUtil.isNull(deferredValue) && !CheckUtil.isNull(this.objectFactoryReference)) {
			D instance = deferredValue;
			try {
				DeferredPropertyValueReference.LOGGER.debug("Creating deferred value for property {} and entity {}", parentType,
						this.propertyAccessor);
				instance = this.objectFactoryReference.get().getInstance(this.propertyAccessor.getType());
				this.propertyAccessor.setValue(instance, parentType);
			} catch (final InstanceException e) {
				DeferredPropertyValueReference.LOGGER.warn("Could not create instance.", e);
			}
			trueValue = instance;
		} else {
			trueValue = deferredValue;
		}
		return trueValue;
	}

	/**
	 * The builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param <D>
	 *            The deferred type.
	 */
	public static final class Builder<P, E, D> {
		/** An optional reference to an object factory. */
		private transient ExtensionRef<ObjectFactory> objectFactoryRefBuilder;
		/** The indirection accessor. */
		private transient PropertyReference<D, E> propertyAccessorBuilder;
		/** The deferred value reference. */
		private transient PropertyValueReference<P, D> valueReferenceBuilder;

		/** Default constructor. */
		protected Builder() {
			// Default constructor.
		}

		/**
		 * Build the reference.
		 * 
		 * @return The reference.
		 */
		public DeferredPropertyValueReference<P, E, D> build() {
			Validate.notNull(this.propertyAccessorBuilder, "Reference must be set.");
			Validate.notNull(this.valueReferenceBuilder, "Parent reference must be set.");
			return new DeferredPropertyValueReference<P, E, D>(this);
		}

		/**
		 * Sets a new value for the objectFactoryReference field.
		 * 
		 * @param objectFactoryReference
		 *            The new value for the objectFactoryReference field.
		 * @return The builder.
		 */
		public Builder<P, E, D> objectFactoryReference(final ExtensionRef<ObjectFactory> objectFactoryReference) {
			this.objectFactoryRefBuilder = objectFactoryReference;
			return this;
		}

		/**
		 * Sets a new value for the fieldAccessor field.
		 * 
		 * @param fieldAccessor
		 *            The new value for the fieldAccessor field. Must be set.
		 * @return The builder.
		 */
		public Builder<P, E, D> propertyAccessor(final PropertyReference<D, E> fieldAccessor) {
			this.propertyAccessorBuilder = fieldAccessor;
			return this;
		}

		/**
		 * Sets a new value for the valueReference field.
		 * 
		 * @param valueReference
		 *            The new value for the valueReference field. Must be set.
		 * @return The builder.
		 */
		public Builder<P, E, D> valueReference(final PropertyValueReference<P, D> valueReference) {
			this.valueReferenceBuilder = valueReference;
			return this;
		}
	}
}
