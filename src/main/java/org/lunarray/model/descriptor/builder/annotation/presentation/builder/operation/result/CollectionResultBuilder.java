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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractCollectionResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationPropertyCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualCollectionResultTypeDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualCollectionResultTypeDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.ResultDetailBuilder;
import org.lunarray.model.descriptor.presentation.PresentationResultDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The collection result builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 */
public final class CollectionResultBuilder<C, R extends Collection<C>, E>
		extends AbstractCollectionResultDescriptorBuilder<C, R, E, PresQualBuilderContext>
		implements PresQualCollectionResultTypeDescriptorBuilder<C, R, E> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionResultBuilder.class);

	/**
	 * Creates the builder.
	 * 
	 * @param operation
	 *            The operation. May not be null.
	 * @param <C>
	 *            The collection type.
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <C, P extends Collection<C>, E> CollectionResultBuilder<C, P, E> createBuilder(final DescribedOperation operation,
			final PresQualBuilderContext builderContext) {
		Validate.notNull(operation, "Operation may not be null.");
		Validate.notNull(builderContext, "Context may not be null.");
		return new CollectionResultBuilder<C, P, E>(operation, builderContext);
	}

	/** Cached qualifiers. */
	private final transient Map<Class<?>, CollectionResultReference<C, R>> cachedQualifierValues;
	/** Cached value. */
	private transient CollectionResultDescriptor<C, R> cachedValue;
	/** Detail builder. */
	private final transient ResultDetailBuilderImpl detailBuilder;
	/** Detail builder. */
	private final transient Map<Class<?>, ResultDetailBuilderImpl> qualifierDetailBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param operation
	 *            The operation descriptor.
	 * @param builderContext
	 *            The builder context.
	 */
	private CollectionResultBuilder(final DescribedOperation operation, final PresQualBuilderContext builderContext) {
		super(operation, builderContext);
		this.detailBuilder = ResultDetailBuilderImpl.create();
		this.qualifierDetailBuilder = new HashMap<Class<?>, ResultDetailBuilderImpl>();
		this.cachedQualifierValues = new HashMap<Class<?>, CollectionResultReference<C, R>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new CacheBuilder(), this);
	}

	/**
	 * The detail builder.
	 * 
	 * @return The builder.
	 */
	public ResultDetailBuilderImpl detail() {
		return this.detailBuilder;
	}

	/**
	 * The detail builder.
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
			result = this.detailBuilder;
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
	public PresentationResultDescriptor<R> getResultDescriptor() {
		if (CheckUtil.isNull(this.cachedValue)) {
			this.cachedValue = this.createResultDescriptor();
		}
		return this.cachedValue;
	}

	/** {@inheritDoc} */
	@Override
	public PresQualCollectionResultTypeDescriptor<C, R> getResultDescriptor(final Class<?> qualifier) {
		PresQualCollectionResultTypeDescriptor<C, R> descriptor = this.cachedValue;
		if (this.cachedQualifierValues.containsKey(qualifier)) {
			descriptor = this.cachedQualifierValues.get(qualifier);
		}
		return descriptor;
	}

	/** {@inheritDoc} */
	@Override
	protected CollectionResultDescriptor<C, R> createResultDescriptor() {
		if (CheckUtil.isNull(this.cachedValue)) {
			this.cachedValue = new CollectionResultDescriptor<C, R>(this);
		}
		return this.cachedValue;
	}

	/**
	 * Gets the detail.
	 * 
	 * @return The detail.
	 */
	protected ResultDetail getDetail() {
		return this.detailBuilder.build();
	}

	/**
	 * Gets the detail.
	 * 
	 * @param qualifier
	 *            The qualifier
	 * @return The detail.
	 */
	protected ResultDetail getDetail(final Class<?> qualifier) {
		ResultDetail result;
		if (this.qualifierDetailBuilder.containsKey(qualifier)) {
			result = this.qualifierDetailBuilder.get(qualifier).build();
		} else {
			result = this.detailBuilder.build();
		}
		return result;
	}

	/**
	 * A cache builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class CacheBuilder
			implements Listener<UpdatedPresentationPropertyCollectionTypeEvent<C, R, E>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final UpdatedPresentationPropertyCollectionTypeEvent<C, R, E> event) throws EventException {
			CollectionResultBuilder.LOGGER.debug("Handling event {}", event);
			for (final Class<?> key : CollectionResultBuilder.this.qualifierDetailBuilder.keySet()) {
				CollectionResultBuilder.this.cachedQualifierValues.put(key, this.createPropertyReference(key));
			}
		}

		/**
		 * Create a property reference.
		 * 
		 * @param key
		 *            The key.
		 * @return The reference.
		 */
		private CollectionResultReference<C, R> createPropertyReference(final Class<?> key) {
			return new CollectionResultReference<C, R>(CollectionResultBuilder.this.getResultDescriptor(key),
					CollectionResultBuilder.this.getDetail(key), key);
		}
	}
}
