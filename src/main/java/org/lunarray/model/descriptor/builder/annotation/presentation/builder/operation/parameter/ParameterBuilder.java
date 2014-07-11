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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.builders.operation.AbstractParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildParameterEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedParameterEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationParameterEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.operation.parameter.ParameterProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.ParameterDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualParameterDescriptorBuilder;
import org.lunarray.model.descriptor.presentation.PresentationParameterDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A parameter builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 */
public final class ParameterBuilder<P>
		extends AbstractParameterDescriptorBuilder<P, PresQualBuilderContext>
		implements PresQualParameterDescriptorBuilder<P> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterBuilder.class);

	/**
	 * Creates the builder.
	 * 
	 * @param <P>
	 *            The parameter type.
	 * @param builderContext
	 *            The builder. May not be null.
	 * @return The builder.
	 */
	public static <P> ParameterBuilder<P> create(final PresQualBuilderContext builderContext) {
		Validate.notNull(builderContext, "Context may not be null.");
		return new ParameterBuilder<P>(builderContext);
	}

	/** The cached detail. */
	private transient ParameterDetail cachedDetail;
	/** Caches qualifier values. */
	private final transient Map<Class<?>, ParameterReference<P>> cachedQualifierValues;
	/** Cached descriptor. */
	private transient ParameterDescriptor<P> cachedValue;
	/** Detail builder. */
	private final transient ParameterDetailBuilderImpl detailBuilder;
	/** Entity name. */
	private transient String entityNameBuilder;
	/** Operation name. */
	private transient String operationNameBuilder;
	/** Qualifier builders. */
	private final transient Map<Class<?>, ParameterDetailBuilderImpl> qualifierDetailBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder. May not be null.
	 */
	private ParameterBuilder(final PresQualBuilderContext builderContext) {
		super(builderContext);
		this.detailBuilder = ParameterDetailBuilderImpl.create();
		this.qualifierDetailBuilder = new HashMap<Class<?>, ParameterDetailBuilderImpl>();
		this.cachedQualifierValues = new HashMap<Class<?>, ParameterReference<P>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new CacheBuilder(), this);
		bus.addListener(new Delegate(), this);
		bus.addListener(new ParameterProcessor<P>(), this);
	}

	/**
	 * Gets the detail builder.
	 * 
	 * @return The builder.
	 */
	public ParameterDetailBuilderImpl detail() {
		return this.detailBuilder;
	}

	/**
	 * Gets a detail builder.
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
	 * Sets a new value for the entityName field.
	 * 
	 * @param entityName
	 *            The new value for the entityName field.
	 * @return The builder.
	 */
	public PresQualParameterDescriptorBuilder<P> entityName(final String entityName) {
		this.entityNameBuilder = entityName;
		return this;
	}

	/**
	 * Gets cached values.
	 * 
	 * @return The values.
	 */
	public Map<Class<?>, ParameterReference<P>> getCachedQualifierValues() {
		return this.cachedQualifierValues;
	}

	/**
	 * Gets detail.
	 * 
	 * @return The detail.
	 */
	public ParameterDetail getDetail() {
		if (CheckUtil.isNull(this.cachedDetail)) {
			this.cachedDetail = this.detailBuilder.build();
		}
		return this.cachedDetail;
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
			result = this.getDetailBuilder();
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
	public ParameterDescriptor<P> getParameterDescriptor() {
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
	protected ParameterDescriptor<P> createParameterDescriptor() {
		this.cachedValue = new ParameterDescriptor<P>(this, this.entityNameBuilder, this.operationNameBuilder);
		return this.cachedValue;
	}

	/**
	 * Gets detail.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The detail.
	 */
	protected ParameterDetail getDetail(final Class<?> qualifier) {
		ParameterDetail result;
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
			implements Listener<BuildParameterEvent<P>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final BuildParameterEvent<P> event) throws EventException {
			ParameterBuilder.LOGGER.debug("Handling build event {}", event);
			for (final Class<?> key : ParameterBuilder.this.qualifierDetailBuilder.keySet()) {
				ParameterBuilder.this.cachedQualifierValues.put(key, this.createParameter(key));
			}
		}

		/**
		 * Create a parameter reference.
		 * 
		 * @param key
		 *            The key.
		 * @return The parameter reference.
		 */
		private ParameterReference<P> createParameter(final Class<?> key) {
			return new ParameterReference<P>(ParameterBuilder.this.getParameterDescriptor(), ParameterBuilder.this.getDetail(key), key);
		}
	}

	/**
	 * The delegate builder.
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
			ParameterBuilder.LOGGER.debug("Handling update parameter event {}", event);
			ParameterBuilder.this
					.getBuilderContext()
					.getBus()
					.handleEvent(new UpdatedPresentationParameterEvent<P>(ParameterBuilder.this, event.getParameter()),
							ParameterBuilder.this);
		}
	}
}
