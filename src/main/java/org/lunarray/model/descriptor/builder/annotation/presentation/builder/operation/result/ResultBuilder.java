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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildOperationEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.PresQualOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualResultTypeDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualResultTypeDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.ResultDetailBuilder;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A result descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 */
public final class ResultBuilder<R, E>
		extends AbstractResultDescriptorBuilder<R, E, PresQualBuilderContext>
		implements PresQualResultTypeDescriptorBuilder<R, E> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ResultBuilder.class);

	/**
	 * Creates the builder.
	 * 
	 * @param <R>
	 *            The result type.
	 * @param <E>
	 *            The entity type.
	 * @param parent
	 *            The parent.
	 * @param operation
	 *            The operation.
	 * @param builderContext
	 *            The builder.
	 * @return The builder.
	 */
	public static <R, E> ResultBuilder<R, E> create(final PresQualOperationDescriptorBuilder<E> parent, final DescribedOperation operation,
			final PresQualBuilderContext builderContext) {
		Validate.notNull(parent, "Parent may not be null.");
		Validate.notNull(operation, "Operation may not be null.");
		Validate.notNull(builderContext, "Context may not be null.");
		return new ResultBuilder<R, E>(parent, operation, builderContext);
	}

	/** The cached detail. */
	private transient ResultDetail cachedDetail;
	/** Caches qualifier values. */
	private final transient Map<Class<?>, ResultReference<R>> cachedQualifierValues;
	/** Cached descriptor. */
	private transient PresQualResultTypeDescriptor<R> cachedValue;
	/** Detail builder. */
	private final transient ResultDetailBuilderImpl detailBuilder;
	/** Qualifier builders. */
	private final transient Map<Class<?>, ResultDetailBuilderImpl> qualifierDetailBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param parent
	 *            The parent.
	 * @param operation
	 *            The operation.
	 * @param builderContext
	 *            The builder.
	 */
	private ResultBuilder(final PresQualOperationDescriptorBuilder<E> parent, final DescribedOperation operation,
			final PresQualBuilderContext builderContext) {
		super(operation, builderContext);
		this.detailBuilder = ResultDetailBuilderImpl.create();
		this.qualifierDetailBuilder = new HashMap<Class<?>, ResultDetailBuilderImpl>();
		this.cachedQualifierValues = new HashMap<Class<?>, ResultReference<R>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListenerBefore(new CacheBuilder(), parent);
	}

	/**
	 * Gets the detail builder.
	 * 
	 * @return The builder.
	 */
	public ResultDetailBuilderImpl detail() {
		return this.detailBuilder;
	}

	/**
	 * Gets a detail builder.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The builder.
	 */
	public ResultDetailBuilderImpl detail(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, ResultDetailBuilderImpl.create());
		}
		return this.qualifierDetailBuilder.get(qualifier);
	}

	/**
	 * Gets cached values.
	 * 
	 * @return The values.
	 */
	public Map<Class<?>, ResultReference<R>> getCachedQualifierValues() {
		return this.cachedQualifierValues;
	}

	/** {@inheritDoc} */
	@Override
	public ResultDetailBuilderImpl getDetailBuilder() {
		return this.detailBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public ResultDetailBuilder getDetailBuilder(final Class<?> qualifier) {
		ResultDetailBuilder result;
		if (this.qualifierDetailBuilder.containsKey(qualifier)) {
			result = this.qualifierDetailBuilder.get(qualifier);
		} else {
			result = this.getDetailBuilder();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public ResultDetailBuilderImpl getQualifierBuilder(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, ResultDetailBuilderImpl.create());
		}
		return this.qualifierDetailBuilder.get(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public PresQualResultTypeDescriptor<R> getResultDescriptor() {
		if (CheckUtil.isNull(this.cachedValue)) {
			this.cachedValue = this.createResultDescriptor();
		}
		return this.cachedValue;
	}

	/** {@inheritDoc} */
	@Override
	public ResultDescriptor<R> getResultDescriptor(final Class<?> qualifier) {
		ResultDescriptor<R> result = this.cachedValue;
		if (this.cachedQualifierValues.containsKey(qualifier)) {
			result = this.cachedQualifierValues.get(qualifier);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	protected PresQualResultTypeDescriptor<R> createResultDescriptor() {
		this.cachedValue = new ResultDescriptorImpl<R>(this);
		return this.cachedValue;
	}

	/**
	 * Gets detail.
	 * 
	 * @return The detail.
	 */
	protected ResultDetail getDetail() {
		if (CheckUtil.isNull(this.cachedDetail)) {
			this.cachedDetail = this.detailBuilder.build();
		}
		return this.cachedDetail;
	}

	/**
	 * Gets detail.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The detail.
	 */
	protected ResultDetail getDetail(final Class<?> qualifier) {
		ResultDetail result;
		if (this.qualifierDetailBuilder.containsKey(qualifier)) {
			result = this.qualifierDetailBuilder.get(qualifier).build();
		} else {
			result = this.getDetail();
		}
		return result;
	}

	/**
	 * A cache builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class CacheBuilder
			implements Listener<BuildOperationEvent<E>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final BuildOperationEvent<E> event) throws EventException {
			ResultBuilder.LOGGER.debug("Handling event {}", event);
			for (final Class<?> key : ResultBuilder.this.qualifierDetailBuilder.keySet()) {
				ResultBuilder.this.cachedQualifierValues.put(key, this.createResult(key));
			}
		}

		/**
		 * Create a property reference.
		 * 
		 * @param key
		 *            The key.
		 * @return The property reference.
		 */
		private ResultReference<R> createResult(final Class<?> key) {
			return new ResultReference<R>(ResultBuilder.this.getResultDescriptor(), ResultBuilder.this.getDetail(key), key);
		}
	}
}
