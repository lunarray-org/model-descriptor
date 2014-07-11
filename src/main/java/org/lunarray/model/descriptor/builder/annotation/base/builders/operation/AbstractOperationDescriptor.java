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
package org.lunarray.model.descriptor.builder.annotation.base.builders.operation;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Represents a property descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type descriptor.
 * @param <E>
 *            The entity type.
 */
public abstract class AbstractOperationDescriptor<R, E>
		implements OperationDescriptor<E> {

	/** Serial id. */
	private static final long serialVersionUID = 7258047746504646235L;
	/** The entity type. */
	private Class<E> entityType;
	/** The property extensions. */
	private Map<Class<?>, OperationExtension<E>> extensions;
	/** The name. */
	private String name;
	/** Commancd reference. */
	private OperationReference<E> operationReference;
	/** The parameters. */
	private List<ParameterDescriptor<?>> parameters;
	/** The result type. */
	private ResultDescriptor<R> resultDescriptor;

	/**
	 * Constructs the property descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected AbstractOperationDescriptor(final AbstractOperationDescriptorBuilder<E, ?> builder) {
		Validate.notNull(builder, "Builder may not be null.");
		this.name = builder.getName();
		this.extensions = builder.getExtensions();
		this.entityType = builder.getEntityType().getEntityType();
		this.operationReference = builder.getOperationReference();
		this.resultDescriptor = builder.getResultDescriptor();
		this.parameters = builder.getParameters();
	}

	/** {@inheritDoc} */
	@Override
	public final <X extends OperationExtension<E>> X extension(final Class<X> extensionClazz) {
		X extension = null;
		if (this.extensions.containsKey(extensionClazz)) {
			extension = extensionClazz.cast(this.extensions.get(extensionClazz));
		}
		return extension;
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
	public final Map<Class<?>, OperationExtension<E>> getExtensions() {
		return this.extensions;
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return this.name;
	}

	/**
	 * Gets the value for the operationReference field.
	 * 
	 * @return The value for the operationReference field.
	 */
	@Override
	public final OperationReference<E> getOperationReference() {
		return this.operationReference;
	}

	/** {@inheritDoc} */
	@Override
	public final ParameterDescriptor<?> getParameter(final int index) {
		ParameterDescriptor<?> result = null;
		if (CheckUtil.checkBounds(index, this.parameters)) {
			result = this.parameters.get(index);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Exactly the check we are doing.
	@Override
	public final <P> ParameterDescriptor<P> getParameter(final int index, final Class<P> parameterType) {
		ParameterDescriptor<P> result = null;
		if (CheckUtil.checkBounds(index, this.parameters)) {
			final ParameterDescriptor<?> tmp = this.parameters.get(index);
			if (tmp.getType().equals(parameterType)) {
				result = (ParameterDescriptor<P>) tmp;
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final int getParameterCount() {
		return this.parameters.size();
	}

	/** {@inheritDoc} */
	@Override
	public final List<ParameterDescriptor<?>> getParameters() {
		return this.parameters;
	}

	/**
	 * Gets the value for the resultDescriptor field.
	 * 
	 * @return The value for the resultDescriptor field.
	 */
	@Override
	public final ResultDescriptor<R> getResultDescriptor() {
		return this.resultDescriptor;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// This is exactly what we check.
	@Override
	public final <T> ResultDescriptor<T> getResultDescriptor(final Class<T> resultType) {
		ResultDescriptor<T> result = null;
		if (this.resultDescriptor.getResultType().equals(resultType)) {
			result = (ResultDescriptor<T>) this.resultDescriptor;
		}
		return result;
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
	public final void setExtensions(final Map<Class<?>, OperationExtension<E>> extensions) {
		this.extensions = extensions;
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
	 * Sets a new value for the operationReference field.
	 * 
	 * @param operationReference
	 *            The new value for the operationReference field.
	 */
	public final void setOperationReference(final OperationReference<E> operationReference) {
		this.operationReference = operationReference;
	}

	/**
	 * Sets a new value for the parameters field.
	 * 
	 * @param parameters
	 *            The new value for the parameters field.
	 */
	public final void setParameters(final List<ParameterDescriptor<?>> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Sets a new value for the resultDescriptor field.
	 * 
	 * @param resultDescriptor
	 *            The new value for the resultDescriptor field.
	 */
	public final void setResultDescriptor(final ResultDescriptor<R> resultDescriptor) {
		this.resultDescriptor = resultDescriptor;
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
	protected final <A> A operationAdapt(final Class<A> clazz) {
		A value = null;
		if (clazz.isInstance(this)) {
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
	protected final boolean operationAdaptable(final Class<?> clazz) {
		return clazz.isInstance(this);
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void operationToString(final StringBuilder builder) {
		builder.append("\tName: ").append(this.name);
		builder.append("\n\tResult Type: ").append(this.resultDescriptor.toString());
		builder.append("\n\tParameter Count: ").append(this.parameters.size());
		builder.append("\n\tExtensions: {\n\t\t");
		StringUtil.commaSeparated(this.extensions.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n");
	}
}
