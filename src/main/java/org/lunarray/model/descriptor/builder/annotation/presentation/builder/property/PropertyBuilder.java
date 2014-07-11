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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.property;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptor;
import org.lunarray.model.descriptor.builder.annotation.base.builders.property.AbstractPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildPropertyEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedPropertyValueReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationPropertyValueReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.property.PropertyProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PropertyDetailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A property builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class PropertyBuilder<P, E>
		extends AbstractPropertyDescriptorBuilder<P, E, PresQualBuilderContext>
		implements PresQualPropertyDescriptorBuilder<P, E> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyBuilder.class);

	/**
	 * Creates the builder.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param builderContext
	 *            The builder.
	 * @return The builder.
	 */
	public static <P, E> PropertyBuilder<P, E> create(final PresQualBuilderContext builderContext) {
		Validate.notNull(builderContext, "Context may not be null.");
		return new PropertyBuilder<P, E>(builderContext);
	}

	/** The cached detail. */
	private transient PropertyDetail cachedDetail;
	/** Caches qualifier values. */
	private final transient Map<Class<?>, PropertyReference<P, E>> cachedQualifierValues;
	/** Cached descriptor. */
	private transient PropertyDescriptor<P, E> cachedValue;
	/** Detail builder. */
	private final transient PropertyDetailBuilderImpl detailBuilder;
	/** Entity name. */
	private transient String entityNameBuilder;
	/** Qualifier builders. */
	private final transient Map<Class<?>, PropertyDetailBuilderImpl> qualifierDetailBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder. May not be null.
	 */
	private PropertyBuilder(final PresQualBuilderContext builderContext) {
		super(builderContext);
		this.detailBuilder = PropertyDetailBuilderImpl.create();
		this.qualifierDetailBuilder = new HashMap<Class<?>, PropertyDetailBuilderImpl>();
		this.cachedQualifierValues = new HashMap<Class<?>, PropertyReference<P, E>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new CacheBuilder(), this);
		bus.addListener(new Delegate(), this);
		bus.addListener(new PropertyProcessor<P, E>(), this);
	}

	/**
	 * Gets the detail builder.
	 * 
	 * @return The builder.
	 */
	public PropertyDetailBuilderImpl detail() {
		return this.detailBuilder;
	}

	/**
	 * Gets a detail builder.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The builder.
	 */
	public PropertyDetailBuilderImpl detail(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, PropertyDetailBuilderImpl.create());
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
	public PresQualPropertyDescriptorBuilder<P, E> entityName(final String entityName) {
		this.entityNameBuilder = entityName;
		return this;
	}

	/**
	 * Gets cached values.
	 * 
	 * @return The values.
	 */
	public Map<Class<?>, PropertyReference<P, E>> getCachedQualifierValues() {
		return this.cachedQualifierValues;
	}

	/**
	 * Gets detail.
	 * 
	 * @return The detail.
	 */
	public PropertyDetail getDetail() {
		if (CheckUtil.isNull(this.cachedDetail)) {
			this.cachedDetail = this.detailBuilder.build();
		}
		return this.cachedDetail;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl getDetailBuilder() {
		return this.detailBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilder getDetailBuilder(final Class<?> qualifier) {
		PropertyDetailBuilder result;
		if (this.qualifierDetailBuilder.containsKey(qualifier)) {
			result = this.qualifierDetailBuilder.get(qualifier);
		} else {
			result = this.getDetailBuilder();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDescriptor<P, E> getPropertyDescriptor() {
		return this.cachedValue;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDetailBuilderImpl getQualifierBuilder(final Class<?> qualifier) {
		if (!this.qualifierDetailBuilder.containsKey(qualifier)) {
			this.qualifierDetailBuilder.put(qualifier, PropertyDetailBuilderImpl.create());
		}
		return this.qualifierDetailBuilder.get(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	protected AnnotationPropertyDescriptor<P, E> createPropertyDescriptor() {
		this.cachedValue = new PropertyDescriptor<P, E>(this, this.entityNameBuilder);
		return this.cachedValue;
	}

	/**
	 * Gets detail.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The detail.
	 */
	protected PropertyDetail getDetail(final Class<?> qualifier) {
		PropertyDetail result;
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
			implements Listener<BuildPropertyEvent<P, E>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final BuildPropertyEvent<P, E> event) throws EventException {
			PropertyBuilder.LOGGER.debug("Handling build event {}", event);
			for (final Class<?> key : PropertyBuilder.this.qualifierDetailBuilder.keySet()) {
				PropertyBuilder.this.cachedQualifierValues.put(key, this.createProperty(key));
			}
		}

		/**
		 * Create a property reference.
		 * 
		 * @param key
		 *            The key.
		 * @return The property reference.
		 */
		private PropertyReference<P, E> createProperty(final Class<?> key) {
			return new PropertyReference<P, E>(PropertyBuilder.this.getPropertyDescriptor(), PropertyBuilder.this.getDetail(key), key);
		}
	}

	/**
	 * The delegate builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class Delegate
			implements Listener<UpdatedPropertyValueReferenceEvent<P, E, PresQualBuilderContext>> {

		/** Default constructor. */
		public Delegate() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final UpdatedPropertyValueReferenceEvent<P, E, PresQualBuilderContext> event) throws EventException {
			PropertyBuilder.LOGGER.debug("Handling update property value reference event {}", event);
			PropertyBuilder.this
					.getBuilderContext()
					.getBus()
					.handleEvent(
							new UpdatedPresentationPropertyValueReferenceEvent<P, E>(PropertyBuilder.this, event.getProperty(),
									event.getValueReference()), PropertyBuilder.this);
		}
	}
}
