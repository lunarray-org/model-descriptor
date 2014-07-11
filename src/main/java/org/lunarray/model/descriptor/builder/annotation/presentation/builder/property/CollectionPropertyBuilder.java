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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.builder.annotation.base.builders.property.AbstractCollectionPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildPropertyEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedPropertyValueReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationPropertyCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.property.PropertyProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.property.collection.CollectionPropertyProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualCollectionPropertyDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualCollectionPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PropertyDetailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The collection property builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class CollectionPropertyBuilder<C, P extends Collection<C>, E>
		extends AbstractCollectionPropertyDescriptorBuilder<C, P, E, PresQualBuilderContext>
		implements PresQualCollectionPropertyDescriptorBuilder<C, P, E> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionPropertyBuilder.class);

	/**
	 * Creates the builder.
	 * 
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
	public static <C, P extends Collection<C>, E> CollectionPropertyBuilder<C, P, E> createBuilder(
			final PresQualBuilderContext builderContext) {
		Validate.notNull(builderContext, "Context may not be null.");
		return new CollectionPropertyBuilder<C, P, E>(builderContext);
	}

	/** Cached qualifiers. */
	private final transient Map<Class<?>, CollectionPropertyReference<C, P, E>> cachedQualifierValues;
	/** Cached value. */
	private transient CollectionPropertyDescriptor<C, P, E> cachedValue;
	/** Detail builder. */
	private final transient PropertyDetailBuilderImpl detailBuilder;
	/** Entity name. */
	private transient String entityNameBuilder;
	/** Detail builder. */
	private final transient Map<Class<?>, PropertyDetailBuilderImpl> qualifierDetailBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context.
	 */
	private CollectionPropertyBuilder(final PresQualBuilderContext builderContext) {
		super(builderContext);
		this.detailBuilder = PropertyDetailBuilderImpl.create();
		this.qualifierDetailBuilder = new HashMap<Class<?>, PropertyDetailBuilderImpl>();
		this.cachedQualifierValues = new HashMap<Class<?>, CollectionPropertyReference<C, P, E>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new Delegate(), this);
		bus.addListener(new CacheBuilder(), this);
		bus.addListener(new CollectionPropertyProcessor<C, P, E>(), this);
		bus.addListener(new PropertyProcessor<P, E>(), this);
	}

	/**
	 * The detail builder.
	 * 
	 * @return The builder.
	 */
	public PropertyDetailBuilderImpl detail() {
		return this.detailBuilder;
	}

	/**
	 * The detail builder.
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
	 * Sets the entity name.
	 * 
	 * @param entityName
	 *            The entity name.
	 * @return The builder.
	 */
	public CollectionPropertyBuilder<C, P, E> entityName(final String entityName) {
		this.entityNameBuilder = entityName;
		return this;
	}

	/**
	 * Gets the value for the cachedQualifierValues field.
	 * 
	 * @return The value for the cachedQualifierValues field.
	 */
	public Map<Class<?>, CollectionPropertyReference<C, P, E>> getCachedQualifierValues() {
		return this.cachedQualifierValues;
	}

	/**
	 * Gets the detail.
	 * 
	 * @return The detail.
	 */
	public PropertyDetail getDetail() {
		return this.detailBuilder.build();
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
			result = this.detailBuilder;
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public PresQualCollectionPropertyDescriptor<C, P, E> getPropertyDescriptor() {
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
	protected CollectionPropertyDescriptor<C, P, E> createPropertyDescriptor() {
		if (CheckUtil.isNull(this.cachedValue)) {
			this.cachedValue = new CollectionPropertyDescriptor<C, P, E>(this, this.entityNameBuilder);
		}
		return this.cachedValue;
	}

	/**
	 * Gets the detail.
	 * 
	 * @param qualifier
	 *            The qualifier
	 * @return The detail.
	 */
	protected PropertyDetail getDetail(final Class<?> qualifier) {
		PropertyDetail result;
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
			implements Listener<BuildPropertyEvent<P, E>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final BuildPropertyEvent<P, E> event) throws EventException {
			CollectionPropertyBuilder.LOGGER.debug("Handling build event {}", event);
			for (final Class<?> key : CollectionPropertyBuilder.this.qualifierDetailBuilder.keySet()) {
				CollectionPropertyBuilder.this.cachedQualifierValues.put(key, this.createPropertyReference(key));
			}
		}

		/**
		 * Create a property reference.
		 * 
		 * @param key
		 *            The key.
		 * @return The reference.
		 */
		private CollectionPropertyReference<C, P, E> createPropertyReference(final Class<?> key) {
			return new CollectionPropertyReference<C, P, E>(CollectionPropertyBuilder.this.getPropertyDescriptor(),
					CollectionPropertyBuilder.this.getDetail(key), key);
		}
	}

	/**
	 * Delegates.
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
			CollectionPropertyBuilder.LOGGER.debug("Handling update property value reference event {}", event);
			CollectionPropertyBuilder.this
					.getBuilderContext()
					.getBus()
					.handleEvent(
							new UpdatedPresentationPropertyCollectionTypeEvent<C, P, E>(CollectionPropertyBuilder.this, event.getProperty()),
							CollectionPropertyBuilder.this);
		}
	}
}
