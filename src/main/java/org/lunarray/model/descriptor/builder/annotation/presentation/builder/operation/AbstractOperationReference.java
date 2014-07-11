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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.reference.AbstractReference;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationOperationDescriptor;
import org.lunarray.model.descriptor.qualifier.QualifierSelected;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Common operation reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 */
public abstract class AbstractOperationReference<R, E>
		extends AbstractReference
		implements PresentationOperationDescriptor<E> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -744842093381723665L;
	/** The delegate. */
	private OperationDescriptor<R, E> delegate;
	/** The detail. */
	private OperationDetail detail;
	/** The parameters. */
	private List<ParameterDescriptor<?>> parameters;
	/** The result type reference. */
	private ResultDescriptor<R> resultType;

	/**
	 * Constructs the refence.
	 * 
	 * @param reference
	 *            The result reference. May not be null.
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param detail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 * @param parameters
	 *            The parameters. May not be null.
	 */
	public AbstractOperationReference(final ResultDescriptor<R> reference, final OperationDescriptor<R, E> delegate,
			final OperationDetail detail, final Class<?> qualifier, final List<ParameterDescriptor<?>> parameters) {
		super(qualifier);
		Validate.notNull(delegate, "Delegate may not be null.");
		Validate.notNull(detail, "Detail may not be null.");
		Validate.notNull(reference, "Result may not be null.");
		Validate.notNull(parameters, "Parameters may not be null.");
		this.delegate = delegate;
		this.detail = detail;
		this.resultType = reference;
		this.parameters = parameters;
	}

	/** {@inheritDoc} */
	@Override
	public final <A> A adapt(final Class<A> clazz) {
		Validate.notNull(clazz, AbstractOperationReference.ADAPTER_TYPE_NULL);
		A adaptee = null;
		if (QualifierSelected.class.equals(clazz)) {
			adaptee = clazz.cast(this);
		} else if (this.delegate.adaptable(clazz)) {
			if (clazz.isInstance(this)) {
				adaptee = clazz.cast(this);
			} else {
				adaptee = this.delegate.adapt(clazz);
			}
		}
		return adaptee;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, AbstractOperationReference.ADAPTER_TYPE_NULL);
		boolean result = true;
		if (!QualifierSelected.class.equals(adapterType)) {
			result = this.delegate.adaptable(adapterType);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final <X extends OperationExtension<E>> X extension(final Class<X> extensionType) {
		return this.delegate.extension(extensionType);
	}

	/** {@inheritDoc} */
	@Override
	public final String getButton() {
		return DescribedUtil.getDescriptor(this.detail.getDescriptionKey(), this.detail.getResourceBundle(), this.getButtonKey());
	}

	/** {@inheritDoc} */
	@Override
	public final String getButton(final Locale locale) {
		return DescribedUtil.getDescriptor(this.detail.getDescriptionKey(), this.detail.getResourceBundle(locale), this.getButtonKey());
	}

	/** {@inheritDoc} */
	@Override
	public final String getButtonKey() {
		String description;
		if (StringUtil.isEmptyString(this.detail.getDescriptionKey())) {
			description = String.format("%s.%s", this.delegate.getEntityName(), this.getName());
		} else {
			description = this.detail.getDescriptionKey();
		}
		return description;
	}

	/**
	 * Gets the value for the delegate field.
	 * 
	 * @return The value for the delegate field.
	 */
	public final OperationDescriptor<R, E> getDelegate() {
		return this.delegate;
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription() {
		return DescribedUtil.getDescription(this.detail, this.delegate.getDescription());
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescription(final Locale locale) {
		return DescribedUtil.getDescription(this.detail, this.delegate.getDescription(locale), locale);
	}

	/** {@inheritDoc} */
	@Override
	public final String getDescriptionKey() {
		return this.detail.getDescriptionKey();
	}

	/**
	 * Gets the value for the detail field.
	 * 
	 * @return The value for the detail field.
	 */
	public final OperationDetail getDetail() {
		return this.detail;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<E> getEntityType() {
		return this.delegate.getEntityType();
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return this.delegate.getName();
	}

	/** {@inheritDoc} */
	@Override
	public final int getOrder() {
		return this.detail.getOrder();
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
	// We check this.
	@Override
	public final <P> ParameterDescriptor<P> getParameter(final int index, final Class<P> parameterType) {
		ParameterDescriptor<P> result = null;
		if (CheckUtil.checkBounds(index, this.parameters)) {
			final ParameterDescriptor<?> candidate = this.parameters.get(index);
			if (parameterType.equals(candidate.getType())) {
				result = (ParameterDescriptor<P>) candidate;
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final int getParameterCount() {
		return this.delegate.getParameterCount();
	}

	/** {@inheritDoc} */
	@Override
	public final List<ParameterDescriptor<?>> getParameters() {
		return this.parameters;
	}

	/** {@inheritDoc} */
	@Override
	public final ResultDescriptor<?> getResultDescriptor() {
		return this.resultType;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Derived.
	@Override
	public final <T> ResultDescriptor<T> getResultDescriptor(final Class<T> resultType) {
		ResultDescriptor<T> result = null;
		if (this.resultType.getResultType().equals(resultType)) {
			result = (ResultDescriptor<T>) this.resultType;
		}
		return result;
	}

	/**
	 * Gets the value for the resultType field.
	 * 
	 * @return The value for the resultType field.
	 */
	public final ResultDescriptor<R> getResultType() {
		return this.resultType;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isVisible() {
		return this.detail.isVisible();
	}

	/**
	 * Sets a new value for the delegate field.
	 * 
	 * @param delegate
	 *            The new value for the delegate field.
	 */
	public final void setDelegate(final OperationDescriptor<R, E> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Sets a new value for the detail field.
	 * 
	 * @param detail
	 *            The new value for the detail field.
	 */
	public final void setDetail(final OperationDetail detail) {
		this.detail = detail;
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
	 * Sets a new value for the resultType field.
	 * 
	 * @param resultType
	 *            The new value for the resultType field.
	 */
	public final void setResultType(final ResultDescriptor<R> resultType) {
		this.resultType = resultType;
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void operationToString(final StringBuilder builder) {
		this.referenceToString(builder);
		builder.append("\n\tName: ").append(this.delegate.getName());
		builder.append("\n");
	}
}
