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
package org.lunarray.model.descriptor.accessor.operation;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.lunarray.model.descriptor.serialization.MethodInfo;

/**
 * Defines a serializable operation.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class PersistentOperation
		extends AbstractOperation
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = -5097022538261011697L;
	/**
	 * Creates a builder.
	 * 
	 * @return The builder.
	 */
	public static OperationBuilder createBuilder() {
		return new OperationBuilder();
	}

	/**
	 * Creates a operation from a proxy.
	 * 
	 * @param proxy
	 *            The proxy.
	 * @return The operation.
	 */
	private static PersistentOperation createOperation(final OperationProxy proxy) {
		return new PersistentOperation(proxy);
	}

	/** The entity. */
	private Class<?> entityType;

	/**
	 * Default constructor.
	 */
	public PersistentOperation() {
		super();
	}

	/**
	 * Default constructor.
	 * 
	 * @param proxy
	 *            The serialization proxy.
	 */
	private PersistentOperation(final OperationProxy proxy) {
		super(proxy);
		this.entityType = proxy.getEntityType();
	}

	/**
	 * Constructs the operation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected PersistentOperation(final OperationBuilder builder) {
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
		return new OperationProxy(this);
	}

	/**
	 * The serialization proxy for the operation.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	public static final class OperationProxy
			implements Serializable, Operation {
		/** Serial id. */
		private static final long serialVersionUID = 1144797571573842270L;
		/** The entity. */
		private Class<?> entityType;
		/** The name. */
		private String name;
		/** The operation. */
		private MethodInfo operationInfo;

		/**
		 * Default constructor.
		 * 
		 * @param operation
		 *            The operation to serialize.
		 */
		public OperationProxy(final PersistentOperation operation) {
			this.operationInfo = new MethodInfo(operation.getOperation());
			this.entityType = operation.getEntityType();
			this.name = operation.getName();
		}

		/**
		 * Gets the value for the entityType field.
		 * 
		 * @return The value for the entityType field.
		 */
		public Class<?> getEntityType() {
			return this.entityType;
		}

		/** {@inheritDoc} */
		@Override
		public String getName() {
			return this.name;
		}

		/** {@inheritDoc} */
		@Override
		public Method getOperation() {
			return this.operationInfo.resolveMethod();
		}

		/**
		 * Gets the value for the operationInfo field.
		 * 
		 * @return The value for the operationInfo field.
		 */
		public MethodInfo getOperationInfo() {
			return this.operationInfo;
		}

		/** {@inheritDoc} */
		@Override
		public Class<?> getResultType() {
			return this.operationInfo.resolveMethod().getReturnType();
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
		 * Sets a new value for the name field.
		 * 
		 * @param name
		 *            The new value for the name field.
		 */
		public void setName(final String name) {
			this.name = name;
		}

		/**
		 * Sets a new value for the operationInfo field.
		 * 
		 * @param operationInfo
		 *            The new value for the operationInfo field.
		 */
		public void setOperationInfo(final MethodInfo operationInfo) {
			this.operationInfo = operationInfo;
		}

		/**
		 * Gets the instance for this operation.
		 * 
		 * @return The property.
		 */
		private Object readResolve() {
			return PersistentOperation.createOperation(this);
		}
	}
}
