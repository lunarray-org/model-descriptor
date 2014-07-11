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

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.lunarray.model.descriptor.serialization.FieldInfo;
import org.lunarray.model.descriptor.serialization.MethodInfo;

/**
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 */
public final class PersistentProperty<P>
		extends AbstractProperty<P>
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = -5097022538261011697L;
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

	/**
	 * Creates a property from a proxy.
	 * 
	 * @param proxy
	 *            The proxy.
	 * @return The property.
	 * @param <P>
	 *            The property type.
	 */
	private static <P> PersistentProperty<P> createProperty(final PropertyProxy<P> proxy) {
		return new PersistentProperty<P>(proxy);
	}

	/** The entity. */
	private transient Class<?> entityType;

	/**
	 * Default constructor.
	 */
	public PersistentProperty() {
		super();
	}

	/**
	 * Default constructor.
	 * 
	 * @param proxy
	 *            The serialization proxy.
	 */
	private PersistentProperty(final PropertyProxy<P> proxy) {
		super(proxy);
		this.entityType = proxy.getEntityType();
	}

	/**
	 * Constructs the property.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected PersistentProperty(final PropertyBuilder<P> builder) {
		super(builder);
		this.entityType = builder.getEntityTypeBuilder().getEntityType();
	}

	/**
	 * Gets the value for the entityType field.
	 * 
	 * @return The value for the entityType field.
	 */
	public Class<?> getEntityType() {
		return this.entityType;
	}

	/**
	 * Sets a new value for the entityType field.
	 * 
	 * @param entityType
	 *            The new value for the entityType field.
	 */
	public void setEntityType(final Class<?> entityType) {
		this.entityType = entityType;
	}

	/**
	 * Write the replacement.
	 * 
	 * @return The serialization proxy.
	 */
	private Object writeReplace() {
		return new PropertyProxy<P>(this);
	}

	/**
	 * The serialization proxy for the Property.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <P>
	 *            The property type.
	 */
	public static final class PropertyProxy<P>
			implements Serializable, Property<P> {
		/** Serial id. */
		private static final long serialVersionUID = 1144797571573842270L;
		/** The accessor. */
		private MethodInfo accessorInfo;
		/** The entity. */
		private Class<?> entityType;
		/** The modifiers. */
		private int modifiers;
		/** The mutator. */
		private MethodInfo mutatorInfo;
		/** The name. */
		private String name;
		/** The field. */
		private FieldInfo rawInfo;
		/** The property type. */
		private Class<P> type;

		/**
		 * Default constructor.
		 * 
		 * @param property
		 *            The property to serialize.
		 */
		public PropertyProxy(final PersistentProperty<P> property) {
			this.accessorInfo = new MethodInfo(property.getAccessor());
			this.entityType = property.entityType;
			this.modifiers = property.getModifiers();
			this.mutatorInfo = new MethodInfo(property.getMutator());
			this.name = property.getName();
			this.rawInfo = new FieldInfo(property.getRaw());
			this.type = property.getType();
		}

		/** {@inheritDoc} */
		@Override
		public Method getAccessor() {
			return this.accessorInfo.resolveMethod();
		}

		/**
		 * Gets the value for the accessorInfo field.
		 * 
		 * @return The value for the accessorInfo field.
		 */
		public MethodInfo getAccessorInfo() {
			return this.accessorInfo;
		}

		/**
		 * Gets the value for the entityType field.
		 * 
		 * @return The value for the entityType field.
		 */
		public Class<?> getEntityType() {
			return this.entityType;
		}

		/**
		 * Gets the value for the modifiers field.
		 * 
		 * @return The value for the modifiers field.
		 */
		@Override
		public int getModifiers() {
			return this.modifiers;
		}

		/** {@inheritDoc} */
		@Override
		public Method getMutator() {
			return this.mutatorInfo.resolveMethod();
		}

		/**
		 * Gets the value for the mutatorInfo field.
		 * 
		 * @return The value for the mutatorInfo field.
		 */
		public MethodInfo getMutatorInfo() {
			return this.mutatorInfo;
		}

		/**
		 * Gets the value for the name field.
		 * 
		 * @return The value for the name field.
		 */
		@Override
		public String getName() {
			return this.name;
		}

		/** {@inheritDoc} */
		@Override
		public Field getRaw() {
			return this.rawInfo.resolveField();
		}

		/**
		 * Gets the value for the rawInfo field.
		 * 
		 * @return The value for the rawInfo field.
		 */
		public FieldInfo getRawInfo() {
			return this.rawInfo;
		}

		/**
		 * Gets the value for the type field.
		 * 
		 * @return The value for the type field.
		 */
		@Override
		public Class<P> getType() {
			return this.type;
		}

		/**
		 * Sets a new value for the accessor field.
		 * 
		 * @param accessor
		 *            The new value for the accessor field.
		 */
		public void setAccessor(final MethodInfo accessor) {
			this.accessorInfo = accessor;
		}

		/**
		 * Sets a new value for the accessorInfo field.
		 * 
		 * @param accessorInfo
		 *            The new value for the accessorInfo field.
		 */
		public void setAccessorInfo(final MethodInfo accessorInfo) {
			this.accessorInfo = accessorInfo;
		}

		/**
		 * Sets a new value for the entityType field.
		 * 
		 * @param entityType
		 *            The new value for the entityType field.
		 */
		public void setEntityType(final Class<?> entityType) {
			this.entityType = entityType;
		}

		/**
		 * Sets a new value for the modifiers field.
		 * 
		 * @param modifiers
		 *            The new value for the modifiers field.
		 */
		public void setModifiers(final int modifiers) {
			this.modifiers = modifiers;
		}

		/**
		 * Sets a new value for the mutatorInfo field.
		 * 
		 * @param mutatorInfo
		 *            The new value for the mutatorInfo field.
		 */
		public void setMutatorInfo(final MethodInfo mutatorInfo) {
			this.mutatorInfo = mutatorInfo;
		}

		/**
		 * Sets a new value for the name field.
		 * 
		 * @param name
		 *            The new value for the name field.
		 */
		public void setName(final String name) {
			this.name = name;
		}

		/**
		 * Sets a new value for the rawInfo field.
		 * 
		 * @param rawInfo
		 *            The new value for the rawInfo field.
		 */
		public void setRawInfo(final FieldInfo rawInfo) {
			this.rawInfo = rawInfo;
		}

		/**
		 * Sets a new value for the type field.
		 * 
		 * @param type
		 *            The new value for the type field.
		 */
		public void setType(final Class<P> type) {
			this.type = type;
		}

		/**
		 * Gets the instance for this property.
		 * 
		 * @return The property.
		 */
		private Object readResolve() {
			return PersistentProperty.createProperty(this);
		}
	}
}
