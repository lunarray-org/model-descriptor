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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.parameter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedParameterEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationParameterCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.operation.parameter.ParameterProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.operation.parameter.collection.CollectionParameterProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.ParameterDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualCollectionParameterDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualParameterDescriptorBuilder;
import org.lunarray.model.descriptor.presentation.PresentationParameterDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The collection parameter builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The parameter type.
 */
public final class CollectionParameterBuilder<C, P extends Collection<C>>
		extends AbstractCollectionParameterDescriptorBuilder<C, P, PresQualBuilderContext>
		implements PresQualCollectionParameterDescriptorBuilder<C, P> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionParameterBuilder.class);

	/**
	 * Creates the builder.
	 * 
	 * @param <C>
	 *            The collection type.
	 * @param <P>
	 *            The parameter type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <C, P extends Collection<C>> CollectionParameterBuilder<C, P> createBuilder(final PresQualBuilderContext builderContext) {
		Validate.notNull(builderContext, "Context may not be null.");
		return new CollectionParameterBuilder<C, P>(builderContext);
	}

	/** Cached qualifiers. */
	private final transient Map<Class<?>, CollectionParameterReference<C, P>> cachedQualifierValues;
	/** Cached value. */
	private transient CollectionParameterDescriptor<C, P> cachedValue;
	/** Detail builder. */
	private final transient ParameterDetailBuilderImpl detailBuilder;
	/** Entity name. */
	private transient String entityNameBuilder;
	/** Operation name. */
	private transient String operationNameBuilder;
	/** Detail builder. */
	private final transient Map<Class<?>, ParameterDetailBuilderImpl> qualifierDetailBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	private CollectionParameterBuilder(final PresQualBuilderContext builderContext) {
		super(builderContext);
		this.detailBuilder = ParameterDetailBuilderImpl.create();
		this.qualifierDetailBuilder = new HashMap<Class<?>, ParameterDetailBuilderImpl>();
		this.cachedQualifierValues = new HashMap<Class<?>, CollectionParameterReference<C, P>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new Delegate(), this);
		bus.addListener(new CacheBuilder(), this);
		bus.addListener(new CollectionParameterProcessor<C, P>(), this);
		bus.addListener(new ParameterProcessor<P>(), this);
	}

	/**
	 * The detail builder.
	 * 
	 * @return The builder.
	 */
	public ParameterDetailBuilderImpl detail() {
		return this.detailBuilder;
	}

	/**
	 * The detail builder.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The builder.
	 */
	public ParameterDetailBuilderImpl detail(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, ParameterDetailBuilderImpl.create());
		}
		return this.qualifierDetailBuilder.get(qualifier);
	}

	/**
	 * Sets the entity name.
	 * 
	 * @param entityName
	 *            The entity name.
	 * @return The builder.
	 */
	public CollectionParameterBuilder<C, P> entityName(final String entityName) {
		this.entityNameBuilder = entityName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilderImpl getDetailBuilder() {
		return this.detailBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilder getDetailBuilder(final Class<?> qualifier) {
		ParameterDetailBuilder result;
		if (this.qualifierDetailBuilder.containsKey(qualifier)) {
			result = this.qualifierDetailBuilder.get(qualifier);
		} else {
			result = this.detailBuilder;
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public DescribedEntity<?> getEntityType() {
		return this.getParameter().getOperation().getEntityType();
	}

	/** {@inheritDoc} */
	@Override
	public PresQualCollectionParameterDescriptor<C, P> getParameterDescriptor() {
		return this.cachedValue;
	}

	/** {@inheritDoc} */
	@Override
	public PresentationParameterDescriptor<P> getParameterDescriptor(final Class<?> qualifier) {
		return this.cachedQualifierValues.get(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public ParameterDetailBuilderImpl getQualifierBuilder(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, ParameterDetailBuilderImpl.create());
		}
		return this.qualifierDetailBuilder.get(qualifier);
	}

	/**
	 * Sets a new value for the operationName field.
	 * 
	 * @param operationName
	 *            The new value for the operationName field.
	 * @return The builder.
	 */
	public PresQualParameterDescriptorBuilder<P> operationName(final String operationName) {
		this.operationNameBuilder = operationName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	protected CollectionParameterDescriptor<C, P> createParameterDescriptor() {
		if (CheckUtil.isNull(this.cachedValue)) {
			this.cachedValue = new CollectionParameterDescriptor<C, P>(this, this.entityNameBuilder, this.operationNameBuilder);
		}
		return this.cachedValue;
	}

	/**
	 * Gets the detail.
	 * 
	 * @return The detail.
	 */
	protected ParameterDetail getDetail() {
		return this.detailBuilder.build();
	}

	/**
	 * Gets the detail.
	 * 
	 * @param qualifier
	 *            The qualifier
	 * @return The detail.
	 */
	protected ParameterDetail getDetail(final Class<?> qualifier) {
		ParameterDetail result;
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
			implements Listener<UpdatedPresentationParameterCollectionTypeEvent<C, P>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final UpdatedPresentationParameterCollectionTypeEvent<C, P> event) throws EventException {
			CollectionParameterBuilder.LOGGER.debug("Handling update collection parameter event {}", event);
			for (final Class<?> key : CollectionParameterBuilder.this.qualifierDetailBuilder.keySet()) {
				CollectionParameterBuilder.this.cachedQualifierValues.put(key, this.createParameterReference(key));
			}
		}

		/**
		 * Create a parameter reference.
		 * 
		 * @param key
		 *            The key.
		 * @return The reference.
		 */
		private CollectionParameterReference<C, P> createParameterReference(final Class<?> key) {
			return new CollectionParameterReference<C, P>(CollectionParameterBuilder.this.getParameterDescriptor(),
					CollectionParameterBuilder.this.getDetail(key), key);
		}
	}

	/**
	 * Delegates.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class Delegate
			implements Listener<UpdatedParameterEvent<P, PresQualBuilderContext>> {

		/** Default constructor. */
		public Delegate() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final UpdatedParameterEvent<P, PresQualBuilderContext> event) throws EventException {
			CollectionParameterBuilder.LOGGER.debug("Handling update parameter event {}", event);
			CollectionParameterBuilder.this
					.getBuilderContext()
					.getBus()
					.handleEvent(
							new UpdatedPresentationParameterCollectionTypeEvent<C, P>(CollectionParameterBuilder.this, event.getParameter()),
							CollectionParameterBuilder.this);
		}
	}
}
