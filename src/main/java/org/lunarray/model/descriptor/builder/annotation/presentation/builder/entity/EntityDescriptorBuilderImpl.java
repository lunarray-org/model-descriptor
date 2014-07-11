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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.entity.AbstractEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.PreBuildEntityEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.OperationBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.operation.OperationReference;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.property.CollectionPropertyBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.property.CollectionPropertyReference;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.property.PropertyBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.property.PropertyReference;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.EntityDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.PresQualEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.entity.EntityDescriptionProcessor;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.entity.EntityPreBuildListener;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.entity.EntityUpdateListener;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationEntityTypeEvent;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationOperationDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RelationPresentationDescriptor;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An entity builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            entityType
 * @param <K>
 *            keyType
 */
public final class EntityDescriptorBuilderImpl<E, K extends Serializable>
		extends AbstractEntityDescriptorBuilder<E, K, PresQualBuilderContext>
		implements PresQualEntityDescriptorBuilder<E, K> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityDescriptorBuilderImpl.class);

	/**
	 * Creates a builder.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @param <K>
	 *            The key type.
	 * @param builderContext
	 *            The builder context. May not be null.
	 * @return The builder.
	 */
	public static <E, K extends Serializable> EntityDescriptorBuilderImpl<E, K> create(final PresQualBuilderContext builderContext) {
		Validate.notNull(builderContext, "Context may not be null.");
		return new EntityDescriptorBuilderImpl<E, K>(builderContext);
	}

	/** Cached detail. */
	private transient EntityDetail cachedDetail;
	/** Cached ordered members. */
	private transient List<MemberDescriptor<E>> cachedMembersOrdered;
	/** Cached ordered operations. */
	private transient List<PresentationOperationDescriptor<E>> cachedOperationsOrdered;
	/** Cached ordered properties. */
	private transient List<PresentationPropertyDescriptor<?, E>> cachedPropertiesOrdered;
	/** Cached operations ordered qualifier. */
	private final transient Map<Class<?>, List<PresentationOperationDescriptor<E>>> cachedQualifierCommOrdered;
	/** Cached qualifier details. */
	private final transient Map<Class<?>, EntityDetail> cachedQualifierDetails;
	/** Cached qualifier members. */
	private final transient Map<Class<?>, Map<String, MemberDescriptor<E>>> cachedQualifierMember;
	/** Cached members ordered qualifier. */
	private final transient Map<Class<?>, List<MemberDescriptor<E>>> cachedQualifierMemOrdered;
	/** Cached qualifier operations. */
	private final transient Map<Class<?>, Map<String, PresentationOperationDescriptor<E>>> cachedQualifierOperation;
	/** Cached qualifier properties. */
	private final transient Map<Class<?>, Map<String, PresentationPropertyDescriptor<?, E>>> cachedQualifierProperty;
	/** Cached properties ordered qualifier. */
	private final transient Map<Class<?>, List<PresentationPropertyDescriptor<?, E>>> cachedQualifierPropOrdered;
	/** Cached value. */
	private transient EntityDescriptor<E, K> cachedValue;
	/** Property builders. */
	private final transient List<CollectionPropertyBuilder<Object, List<Object>, E>> collectionPropertyBuilders;
	/** Detail. */
	private final transient EntityDetailBuilderImpl detail;
	/** Operation builders. */
	private final transient List<OperationBuilder<E>> operationBuilders;
	/** Property builders. */
	private final transient List<PropertyBuilder<Object, E>> propertyBuilders;
	/** Qualifier details. */
	private final transient Map<Class<?>, EntityDetailBuilderImpl> qualifierDetails;
	/** References. */
	private final transient Map<Class<?>, QualifierEntityDescriptor<E>> references = new HashMap<Class<?>, QualifierEntityDescriptor<E>>();

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context.
	 */
	private EntityDescriptorBuilderImpl(final PresQualBuilderContext builderContext) {
		super(builderContext);
		this.qualifierDetails = new HashMap<Class<?>, EntityDetailBuilderImpl>();
		this.detail = EntityDetailBuilderImpl.create();
		this.propertyBuilders = new LinkedList<PropertyBuilder<Object, E>>();
		this.collectionPropertyBuilders = new LinkedList<CollectionPropertyBuilder<Object, List<Object>, E>>();
		this.cachedQualifierPropOrdered = new HashMap<Class<?>, List<PresentationPropertyDescriptor<?, E>>>();
		this.cachedQualifierProperty = new HashMap<Class<?>, Map<String, PresentationPropertyDescriptor<?, E>>>();
		this.cachedQualifierDetails = new HashMap<Class<?>, EntityDetail>();

		this.operationBuilders = new LinkedList<OperationBuilder<E>>();
		this.cachedQualifierCommOrdered = new HashMap<Class<?>, List<PresentationOperationDescriptor<E>>>();
		this.cachedQualifierOperation = new HashMap<Class<?>, Map<String, PresentationOperationDescriptor<E>>>();

		this.cachedQualifierMemOrdered = new HashMap<Class<?>, List<MemberDescriptor<E>>>();
		this.cachedQualifierMember = new HashMap<Class<?>, Map<String, MemberDescriptor<E>>>();

		final Bus bus = this.getBuilderContext().getBus();
		bus.addListenerBefore(new EntityPreBuildListener<E, K>(), this);
		bus.addListener(new EntityUpdateListener<E, K>(), this);
		bus.addListener(new EntityDescriptionProcessor<E, K>(), this);
		bus.addListenerBefore(new Delegate(), this);
		bus.addListener(new CacheBuilder(), this);
	}

	/** {@inheritDoc} */
	@Override
	public EntityDetailBuilder getDetailBuilder() {
		return this.detail;
	}

	/** {@inheritDoc} */
	@Override
	public EntityDetailBuilder getDetailBuilder(final Class<?> qualifier) {
		if (!this.qualifierDetails.containsKey(qualifier)) {
			this.qualifierDetails.put(qualifier, EntityDetailBuilderImpl.create());
		}
		return this.qualifierDetails.get(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public Map<Class<?>, ? extends EntityDetailBuilder> getQualifierDetails() {
		return this.qualifierDetails;
	}

	/**
	 * Create an entity reference.
	 * 
	 * @param key
	 *            The key.
	 * @return The reference.
	 */
	private EntityReference<E, K> createEntityReference(final Class<?> key) {
		return new EntityReference<E, K>(this, key);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	/* Object is universal. */
	@Override
	protected <C, P extends Collection<C>> CollectionPropertyBuilder<C, P, E> createCollectionPropertyBuilder() {
		final CollectionPropertyBuilder<C, P, E> builder = CollectionPropertyBuilder.createBuilder(this.getBuilderContext());
		builder.entityType(this.getEntityType());
		@SuppressWarnings("rawtypes")
		// Have to make raw.
		final CollectionPropertyBuilder rawBuilder = builder;
		this.collectionPropertyBuilders.add(rawBuilder);
		if (StringUtil.isEmptyString(this.detail.getDescriptionKey())) {
			builder.entityName(this.getName());
		} else {
			builder.entityName(this.detail.getDescriptionKey());
		}
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	protected EntityDescriptor<E, K> createEntityDescriptor() {
		this.cachedValue = new EntityDescriptor<E, K>(this);
		for (final Class<?> key : this.qualifierDetails.keySet()) {
			this.references.put(key, this.createEntityReference(key));
		}
		return this.cachedValue;
	}

	/** {@inheritDoc} */
	@Override
	protected AnnotationOperationDescriptorBuilder<E, PresQualBuilderContext> createOperationBuilder() {
		final OperationBuilder<E> builder = OperationBuilder.create(this.getBuilderContext());
		builder.entityType(this.getEntityType());
		this.operationBuilders.add(builder);
		if (StringUtil.isEmptyString(this.detail.getDescriptionKey())) {
			builder.entityName(this.getName());
		} else {
			builder.entityName(this.detail.getDescriptionKey());
		}
		return builder;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	/* Object is universal. */
	@Override
	protected <P extends Object> PropertyBuilder<P, E> createPropertyBuilder() {
		final PropertyBuilder<P, E> builder = PropertyBuilder.create(this.getBuilderContext());
		builder.entityType(this.getEntityType());
		this.propertyBuilders.add((PropertyBuilder<Object, E>) builder);
		if (StringUtil.isEmptyString(this.detail.getDescriptionKey())) {
			builder.entityName(this.getName());
		} else {
			builder.entityName(this.detail.getDescriptionKey());
		}
		return builder;
	}

	/**
	 * Gets the delegate.
	 * 
	 * @return The delegate.
	 */
	protected EntityDescriptor<E, K> getDelegate() {
		return this.cachedValue;
	}

	/**
	 * Gets the detail.
	 * 
	 * @return The detail.
	 */
	protected EntityDetail getDetail() {
		if (CheckUtil.isNull(this.cachedDetail)) {
			this.cachedDetail = this.detail.build();
		}
		return this.cachedDetail;
	}

	/**
	 * Gets the detail.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The detail.
	 */
	protected EntityDetail getDetail(final Class<?> qualifier) {
		EntityDetail result;
		if (this.qualifierDetails.containsKey(qualifier)) {
			result = this.cachedQualifierDetails.get(qualifier);
		} else {
			result = this.cachedDetail;
		}
		return result;
	}

	/**
	 * Gets the entity references.
	 * 
	 * @return The references.
	 */
	protected Map<Class<?>, QualifierEntityDescriptor<E>> getEntityReferences() {
		return this.references;
	}

	/**
	 * Gets the members.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The members.
	 */
	@SuppressWarnings("unchecked")
	// Derived
	protected Map<String, MemberDescriptor<E>> getMembers(final Class<?> qualifier) {
		@SuppressWarnings("rawtypes")
		Map result = this.getMembers();
		if (this.cachedQualifierMember.containsKey(qualifier)) {
			result = this.cachedQualifierMember.get(qualifier);
		}
		return result;
	}

	/**
	 * Gets the operations.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The operations.
	 */
	@SuppressWarnings("unchecked")
	// Derived
	protected Map<String, PresentationOperationDescriptor<E>> getOperations(final Class<?> qualifier) {
		@SuppressWarnings("rawtypes")
		Map result = this.getOperations();
		if (this.cachedQualifierOperation.containsKey(qualifier)) {
			result = this.cachedQualifierOperation.get(qualifier);
		}
		return result;
	}

	/**
	 * Gets the operations by qualifier.
	 * 
	 * @return The operations.
	 */
	protected Map<Class<?>, Map<String, PresentationOperationDescriptor<E>>> getOperationsByQualifier() {
		return this.cachedQualifierOperation;
	}

	/**
	 * Gets the ordered members.
	 * 
	 * @return The members.
	 */
	protected List<MemberDescriptor<E>> getOrderedMembers() {
		return this.cachedMembersOrdered;
	}

	/**
	 * Gets the ordered members.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The members.
	 */
	@SuppressWarnings("unchecked")
	// Derived.
	protected List<MemberDescriptor<E>> getOrderedMembers(final Class<?> qualifier) {
		@SuppressWarnings("rawtypes")
		List result = this.cachedMembersOrdered;
		if (this.cachedQualifierMemOrdered.containsKey(qualifier)) {
			result = this.cachedQualifierMemOrdered.get(qualifier);
		}
		return result;
	}

	/**
	 * Gets the ordered operations.
	 * 
	 * @return The operations.
	 */
	protected List<PresentationOperationDescriptor<E>> getOrderedOperations() {
		return this.cachedOperationsOrdered;
	}

	/**
	 * Gets the ordered operations.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The operations.
	 */
	protected List<PresentationOperationDescriptor<E>> getOrderedOperations(final Class<?> qualifier) {
		List<PresentationOperationDescriptor<E>> result = this.cachedOperationsOrdered;
		if (this.cachedQualifierCommOrdered.containsKey(qualifier)) {
			result = this.cachedQualifierCommOrdered.get(qualifier);
		}
		return result;
	}

	/**
	 * Gets the ordered properties.
	 * 
	 * @return The properties.
	 */
	protected List<PresentationPropertyDescriptor<?, E>> getOrderedProperties() {
		return this.cachedPropertiesOrdered;
	}

	/**
	 * Gets the ordered properties.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The properties.
	 */
	@SuppressWarnings("unchecked")
	// Derived.
	protected List<PresentationPropertyDescriptor<?, E>> getOrderedProperties(final Class<?> qualifier) {
		@SuppressWarnings("rawtypes")
		List result = this.cachedPropertiesOrdered;
		if (this.cachedQualifierPropOrdered.containsKey(qualifier)) {
			result = this.cachedQualifierPropOrdered.get(qualifier);
		}
		return result;
	}

	/**
	 * Gets the properties.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The properties.
	 */
	@SuppressWarnings("unchecked")
	// Derived.
	protected Map<String, PresentationPropertyDescriptor<?, E>> getProperties(final Class<?> qualifier) {
		@SuppressWarnings("rawtypes")
		Map result = this.getProperties();
		if (this.cachedQualifierProperty.containsKey(qualifier)) {
			result = this.cachedQualifierProperty.get(qualifier);
		}
		return result;
	}

	/**
	 * Gets the properties by qualifier.
	 * 
	 * @return The properties.
	 */
	protected Map<Class<?>, Map<String, PresentationPropertyDescriptor<?, E>>> getPropertiesByQualifier() {
		return this.cachedQualifierProperty;
	}

	/**
	 * Gets the qualifiers.
	 * 
	 * @return The qualifiers.
	 */
	protected Set<Class<?>> getQualifiers() {
		return this.getBuilderContext().getQualifiers(this.getEntityType());
	}

	/**
	 * A cache builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class CacheBuilder
			implements Listener<PreBuildEntityEvent<E, K, PresQualBuilderContext>> {

		/** Default constructor. */
		public CacheBuilder() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final PreBuildEntityEvent<E, K, PresQualBuilderContext> event) throws EventException {
			EntityDescriptorBuilderImpl.LOGGER.debug("Handling event {}", event);
			this.cacheProperties();
			this.cacheOperations();
			this.cacheQualifierProperties();
			this.cacheQualifierOperations();
			final Map<Integer, List<PresentationOperationDescriptor<E>>> operations = new TreeMap<Integer, List<PresentationOperationDescriptor<E>>>();
			this.cacheOrderedOperations(operations);
			final Map<Integer, List<PresentationPropertyDescriptor<?, E>>> properties = new TreeMap<Integer, List<PresentationPropertyDescriptor<?, E>>>();
			this.cacheOrderedProperties(properties);
			EntityDescriptorBuilderImpl.this.cachedPropertiesOrdered = new LinkedList<PresentationPropertyDescriptor<?, E>>();
			for (final List<PresentationPropertyDescriptor<?, E>> orderedProperties : properties.values()) {
				EntityDescriptorBuilderImpl.this.cachedPropertiesOrdered.addAll(orderedProperties);
			}
			EntityDescriptorBuilderImpl.this.cachedOperationsOrdered = new LinkedList<PresentationOperationDescriptor<E>>();
			for (final List<PresentationOperationDescriptor<E>> orderedOperations : operations.values()) {
				EntityDescriptorBuilderImpl.this.cachedOperationsOrdered.addAll(orderedOperations);
			}
			final Map<Integer, List<MemberDescriptor<E>>> members = new TreeMap<Integer, List<MemberDescriptor<E>>>();
			this.cacheOrderedMembers(members);
			EntityDescriptorBuilderImpl.this.cachedMembersOrdered = new LinkedList<MemberDescriptor<E>>();
			for (final List<MemberDescriptor<E>> orderedMembers : members.values()) {
				EntityDescriptorBuilderImpl.this.cachedMembersOrdered.addAll(orderedMembers);
			}
			for (final PropertyBuilder<Object, E> builder : EntityDescriptorBuilderImpl.this.propertyBuilders) {
				if (builder.getDetail().isName()) {
					EntityDescriptorBuilderImpl.this.detail.namePropertyName(builder.getPropertyDescriptor().getName());
				}
			}
			for (final CollectionPropertyBuilder<Object, List<Object>, E> builder : EntityDescriptorBuilderImpl.this.collectionPropertyBuilders) {
				if (builder.getDetail().isName()) {
					EntityDescriptorBuilderImpl.this.detail.namePropertyName(builder.getPropertyDescriptor().getName());
				}
			}
			this.cacheQualifiers();
		}

		/**
		 * Cache operations.
		 */
		private void cacheOperations() {
			for (final OperationBuilder<E> builder : EntityDescriptorBuilderImpl.this.operationBuilders) {
				final Map<Class<?>, OperationReference<?, E>> values = builder.getCachedQualifierValues();
				for (final Map.Entry<Class<?>, OperationReference<?, E>> entry : values.entrySet()) {
					final Class<?> key = entry.getKey();
					this.testAddMap(EntityDescriptorBuilderImpl.this.cachedQualifierOperation, key);
					final OperationReference<?, E> reference = entry.getValue();
					final String name = reference.getName();
					EntityDescriptorBuilderImpl.this.cachedQualifierOperation.get(key).put(name, reference);
					this.testAddMap(EntityDescriptorBuilderImpl.this.cachedQualifierMember, key);
					EntityDescriptorBuilderImpl.this.cachedQualifierMember.get(key).put(name, reference);
				}
			}
		}

		/**
		 * Cache ordered.
		 * 
		 * @param members
		 *            The temporary members.
		 */
		@SuppressWarnings("unchecked")
		// We know the adapting step.
		private void cacheOrderedMembers(final Map<Integer, List<MemberDescriptor<E>>> members) {
			for (final org.lunarray.model.descriptor.model.property.PropertyDescriptor<?, E> value : EntityDescriptorBuilderImpl.this
					.getProperties().values()) {
				/* Adapting a type of this builder, it's always this type. */
				final PresentationPropertyDescriptor<?, E> presentation = value.adapt(PresentationPropertyDescriptor.class);
				final RelationPresentationDescriptor presentationRelated = value.adapt(RelationPresentationDescriptor.class);
				if (presentation.isVisible() || (!CheckUtil.isNull(presentationRelated) && presentationRelated.isInLineIndication())) {
					final Integer order = Integer.valueOf(presentation.getOrder());
					this.testAddEntry(members, order);
					members.get(order).add(presentation);
				}
			}
			for (final org.lunarray.model.descriptor.model.operation.OperationDescriptor<E> value : EntityDescriptorBuilderImpl.this
					.getOperations().values()) {
				/* Adapting a type of this builder, it's always this type. */
				final PresentationOperationDescriptor<E> presentation = value.adapt(PresentationOperationDescriptor.class);
				if (presentation.isVisible()) {
					final Integer order = Integer.valueOf(presentation.getOrder());
					this.testAddEntry(members, order);
					members.get(order).add(presentation);
				}
			}
		}

		/**
		 * Cache ordered.
		 * 
		 * @param operations
		 *            The temporary operations.
		 */
		@SuppressWarnings("unchecked")
		// We know the adapting step.
		private void cacheOrderedOperations(final Map<Integer, List<PresentationOperationDescriptor<E>>> operations) {
			for (final org.lunarray.model.descriptor.model.operation.OperationDescriptor<E> value : EntityDescriptorBuilderImpl.this
					.getOperations().values()) {
				/* Adapting a type of this builder, it's always this type. */
				final PresentationOperationDescriptor<E> presentation = value.adapt(PresentationOperationDescriptor.class);
				if (presentation.isVisible()) {
					final Integer order = Integer.valueOf(presentation.getOrder());
					this.testAddEntry(operations, order);
					operations.get(order).add(presentation);
				}
			}
		}

		/**
		 * Cache ordered.
		 * 
		 * @param properties
		 *            The temporary properties.
		 */
		@SuppressWarnings("unchecked")
		// We know the adapting step.
		private void cacheOrderedProperties(final Map<Integer, List<PresentationPropertyDescriptor<?, E>>> properties) {
			for (final org.lunarray.model.descriptor.model.property.PropertyDescriptor<?, E> value : EntityDescriptorBuilderImpl.this
					.getProperties().values()) {
				/* Adapting a type of this builder, it's always this type. */
				final PresentationPropertyDescriptor<?, E> presentation = value.adapt(PresentationPropertyDescriptor.class);
				final RelationPresentationDescriptor presentationRelated = value.adapt(RelationPresentationDescriptor.class);
				if (presentation.isVisible() || (!CheckUtil.isNull(presentationRelated) && presentationRelated.isInLineIndication())) {
					final Integer order = Integer.valueOf(presentation.getOrder());
					this.testAddEntry(properties, order);
					properties.get(order).add(presentation);
				}
			}
		}

		/**
		 * Cache properties.
		 */
		private void cacheProperties() {
			for (final PropertyBuilder<Object, E> builder : EntityDescriptorBuilderImpl.this.propertyBuilders) {
				final Map<Class<?>, PropertyReference<Object, E>> values = builder.getCachedQualifierValues();
				for (final Map.Entry<Class<?>, PropertyReference<Object, E>> entry : values.entrySet()) {
					final Class<?> key = entry.getKey();
					final PropertyReference<Object, E> value = entry.getValue();
					final String name = value.getName();
					this.testAddMap(EntityDescriptorBuilderImpl.this.cachedQualifierProperty, key);
					EntityDescriptorBuilderImpl.this.cachedQualifierProperty.get(key).put(name, value);
					this.testAddMap(EntityDescriptorBuilderImpl.this.cachedQualifierMember, key);
					EntityDescriptorBuilderImpl.this.cachedQualifierMember.get(key).put(name, value);
				}
			}
			for (final CollectionPropertyBuilder<Object, List<Object>, E> builder : EntityDescriptorBuilderImpl.this.collectionPropertyBuilders) {
				final Map<Class<?>, CollectionPropertyReference<Object, List<Object>, E>> values = builder.getCachedQualifierValues();
				for (final Map.Entry<Class<?>, CollectionPropertyReference<Object, List<Object>, E>> entry : values.entrySet()) {
					final Class<?> key = entry.getKey();
					final CollectionPropertyReference<Object, List<Object>, E> value = entry.getValue();
					final String name = value.getName();
					this.testAddMap(EntityDescriptorBuilderImpl.this.cachedQualifierProperty, key);
					EntityDescriptorBuilderImpl.this.cachedQualifierProperty.get(key).put(name, value);
					this.testAddMap(EntityDescriptorBuilderImpl.this.cachedQualifierMember, key);
					EntityDescriptorBuilderImpl.this.cachedQualifierMember.get(key).put(name, value);
				}
			}
		}

		/**
		 * Cache qualifier descriptor.
		 * 
		 * @param qualifier
		 *            The qualifier.
		 * @return The properties.
		 */
		@SuppressWarnings("unchecked")
		// We know the adapting step.
		private Map<Integer, List<PresentationOperationDescriptor<E>>> cacheQualifierDescriptorOperation(final Class<?> qualifier) {
			final Map<Integer, List<PresentationOperationDescriptor<E>>> addOperations = new TreeMap<Integer, List<PresentationOperationDescriptor<E>>>();
			for (final org.lunarray.model.descriptor.model.operation.OperationDescriptor<E> value : EntityDescriptorBuilderImpl.this
					.getOperations().values()) {
				org.lunarray.model.descriptor.model.operation.OperationDescriptor<E> trueValue;
				final String name = value.getName();
				if (!CheckUtil.isNull(EntityDescriptorBuilderImpl.this.getOperations(qualifier))
						&& EntityDescriptorBuilderImpl.this.getOperations(qualifier).containsKey(name)) {
					trueValue = EntityDescriptorBuilderImpl.this.getOperations(qualifier).get(name);
				} else {
					trueValue = value;
				}
				/* Adapting a type of this builder, it's always this type. */
				final PresentationOperationDescriptor<E> presentation = trueValue.adapt(PresentationOperationDescriptor.class);
				if (presentation.isVisible()) {
					final Integer order = Integer.valueOf(presentation.getOrder());
					this.testAddEntry(addOperations, order);
					addOperations.get(order).add(presentation);
				}
			}
			return addOperations;
		}

		/**
		 * Cache qualifier descriptor.
		 * 
		 * @param qualifier
		 *            The qualifier.
		 * @return The properties.
		 */
		@SuppressWarnings("unchecked")
		// We know the adapting step.
		private Map<Integer, List<PresentationPropertyDescriptor<?, E>>> cacheQualifierDescriptorProperty(final Class<?> qualifier) {
			final Map<Integer, List<PresentationPropertyDescriptor<?, E>>> addProperties = new TreeMap<Integer, List<PresentationPropertyDescriptor<?, E>>>();
			for (final org.lunarray.model.descriptor.model.property.PropertyDescriptor<?, E> value : EntityDescriptorBuilderImpl.this
					.getProperties().values()) {
				org.lunarray.model.descriptor.model.property.PropertyDescriptor<?, E> trueValue;
				final String name = value.getName();
				if (!CheckUtil.isNull(EntityDescriptorBuilderImpl.this.getProperties(qualifier))
						&& EntityDescriptorBuilderImpl.this.getProperties(qualifier).containsKey(name)) {
					trueValue = EntityDescriptorBuilderImpl.this.getProperties(qualifier).get(name);
				} else {
					trueValue = value;
				}
				/* Adapting a type of this builder, it's always this type. */
				final PresentationPropertyDescriptor<?, E> presentation = trueValue.adapt(PresentationPropertyDescriptor.class);
				final RelationPresentationDescriptor presentationRelated = trueValue.adapt(RelationPresentationDescriptor.class);
				if (presentation.isVisible() || (!CheckUtil.isNull(presentationRelated) && presentationRelated.isInLineIndication())) {
					final Integer order = Integer.valueOf(presentation.getOrder());
					this.testAddEntry(addProperties, order);
					addProperties.get(order).add(presentation);
				}
			}
			return addProperties;
		}

		/**
		 * Cache qualifier members.
		 * 
		 * @param qualifier
		 *            The qualifier.
		 * @param addMembers
		 *            The members.
		 */
		private void cacheQualifierMembers(final Class<?> qualifier, final Map<Integer, List<MemberDescriptor<E>>> addMembers) {
			final List<MemberDescriptor<E>> qualifierMembers = new LinkedList<MemberDescriptor<E>>();
			EntityDescriptorBuilderImpl.this.cachedQualifierMemOrdered.put(qualifier, qualifierMembers);
			for (final List<MemberDescriptor<E>> orderedOperations : addMembers.values()) {
				qualifierMembers.addAll(orderedOperations);
			}
		}

		/**
		 * The qualifier operations.
		 */
		@SuppressWarnings("unchecked")
		// We know the adapting step.
		private void cacheQualifierOperations() {
			for (final Map.Entry<Class<?>, Map<String, PresentationOperationDescriptor<E>>> entryType : EntityDescriptorBuilderImpl.this.cachedQualifierOperation
					.entrySet()) {
				final Map<String, PresentationOperationDescriptor<E>> entries = entryType.getValue();
				for (final Map.Entry<String, org.lunarray.model.descriptor.model.operation.OperationDescriptor<E>> entry : EntityDescriptorBuilderImpl.this
						.getOperations().entrySet()) {
					final String key = entry.getKey();

					if (!entries.containsKey(key)) {
						entries.put(key, entry.getValue().adapt(PresentationOperationDescriptor.class));
					}
				}
			}
		}

		/**
		 * Cache qualifier operations.
		 * 
		 * @param qualifier
		 *            The qualifier.
		 * @param addOperations
		 *            The operations.
		 */
		private void cacheQualifierOperations(final Class<?> qualifier,
				final Map<Integer, List<PresentationOperationDescriptor<E>>> addOperations) {
			final List<PresentationOperationDescriptor<E>> qualifierOperations = new LinkedList<PresentationOperationDescriptor<E>>();
			EntityDescriptorBuilderImpl.this.cachedQualifierCommOrdered.put(qualifier, qualifierOperations);
			for (final List<PresentationOperationDescriptor<E>> orderedOperations : addOperations.values()) {
				qualifierOperations.addAll(orderedOperations);
			}
			if (!EntityDescriptorBuilderImpl.this.cachedQualifierDetails.containsKey(qualifier)
					&& EntityDescriptorBuilderImpl.this.qualifierDetails.containsKey(qualifier)) {
				EntityDescriptorBuilderImpl.this.cachedQualifierDetails.put(qualifier, EntityDescriptorBuilderImpl.this.qualifierDetails
						.get(qualifier).build());
			}
		}

		/**
		 * The qualifier properties.
		 */
		@SuppressWarnings("unchecked")
		// We know the adapting step.
		private void cacheQualifierProperties() {
			for (final Map.Entry<Class<?>, Map<String, PresentationPropertyDescriptor<?, E>>> entryType : EntityDescriptorBuilderImpl.this.cachedQualifierProperty
					.entrySet()) {
				final Map<String, PresentationPropertyDescriptor<?, E>> entries = entryType.getValue();
				for (final Map.Entry<String, org.lunarray.model.descriptor.model.property.PropertyDescriptor<?, E>> entry : EntityDescriptorBuilderImpl.this
						.getProperties().entrySet()) {
					final String key = entry.getKey();

					if (!entries.containsKey(key)) {
						entries.put(key, entry.getValue().adapt(PresentationPropertyDescriptor.class));
					}
				}
			}
		}

		/**
		 * Cache qualifier properties.
		 * 
		 * @param qualifier
		 *            The qualifier.
		 * @param addProperties
		 *            The properties.
		 */
		private void cacheQualifierProperties(final Class<?> qualifier,
				final Map<Integer, List<PresentationPropertyDescriptor<?, E>>> addProperties) {
			final List<PresentationPropertyDescriptor<?, E>> qualifierProperties = new LinkedList<PresentationPropertyDescriptor<?, E>>();
			EntityDescriptorBuilderImpl.this.cachedQualifierPropOrdered.put(qualifier, qualifierProperties);
			this.testAddEntry(EntityDescriptorBuilderImpl.this.cachedQualifierMemOrdered, qualifier);
			for (final List<PresentationPropertyDescriptor<?, E>> orderedProperties : addProperties.values()) {
				qualifierProperties.addAll(orderedProperties);
			}
			if (!EntityDescriptorBuilderImpl.this.cachedQualifierDetails.containsKey(qualifier)
					&& EntityDescriptorBuilderImpl.this.qualifierDetails.containsKey(qualifier)) {
				EntityDescriptorBuilderImpl.this.cachedQualifierDetails.put(qualifier, EntityDescriptorBuilderImpl.this.qualifierDetails
						.get(qualifier).build());
			}
		}

		/**
		 * Cache qualifiers.
		 */
		private void cacheQualifiers() {
			for (final Class<?> qualifier : EntityDescriptorBuilderImpl.this.getQualifiers()) {
				final Map<Integer, List<PresentationPropertyDescriptor<?, E>>> addProperties = this
						.cacheQualifierDescriptorProperty(qualifier);
				this.cacheQualifierProperties(qualifier, addProperties);
				final Map<Integer, List<PresentationOperationDescriptor<E>>> addOperations = this
						.cacheQualifierDescriptorOperation(qualifier);
				this.cacheQualifierOperations(qualifier, addOperations);
				final Map<Integer, List<MemberDescriptor<E>>> addMembers = this.createMap();
				for (final Map.Entry<Integer, List<PresentationPropertyDescriptor<?, E>>> entry : addProperties.entrySet()) {
					final Integer key = entry.getKey();
					this.testAddEntry(addMembers, key);
					addMembers.get(key).addAll(entry.getValue());
				}
				for (final Map.Entry<Integer, List<PresentationOperationDescriptor<E>>> entry : addOperations.entrySet()) {
					final Integer key = entry.getKey();
					this.testAddEntry(addMembers, key);
					addMembers.get(key).addAll(entry.getValue());
				}
				this.cacheQualifierMembers(qualifier, addMembers);
			}
		}

		/**
		 * Creates a map.
		 * 
		 * @return A map.
		 * @param <J>
		 *            The key type.
		 * @param <V>
		 *            The value type.
		 */
		private <J, V> Map<J, V> createMap() {
			return new HashMap<J, V>();
		}

		/**
		 * Test if there's an entry, if not, create a list.
		 * 
		 * @param <L>
		 *            The key.
		 * @param <F>
		 *            The list type.
		 * @param map
		 *            The map.
		 * @param key
		 *            The key.
		 */
		private <L, F> void testAddEntry(final Map<L, List<F>> map, final L key) {
			if (!map.containsKey(key)) {
				map.put(key, new LinkedList<F>());
			}
		}

		/**
		 * Test if the element exists and if not, add.
		 * 
		 * @param <G>
		 *            The first key.
		 * @param <L>
		 *            The second key.
		 * @param <F>
		 *            The second element.
		 * @param map
		 *            The map to add the element in.
		 * @param key
		 *            The key.
		 */
		private <G, L, F> void testAddMap(final Map<G, Map<L, F>> map, final G key) {
			if (!map.containsKey(key)) {
				map.put(key, new HashMap<L, F>());
			}
		}
	}

	/**
	 * Delegates.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class Delegate
			implements Listener<UpdatedEntityTypeEvent<E, K, PresQualBuilderContext>> {

		/** Default constructor. */
		public Delegate() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final UpdatedEntityTypeEvent<E, K, PresQualBuilderContext> event) throws EventException {
			EntityDescriptorBuilderImpl.this
					.getBuilderContext()
					.getBus()
					.handleEvent(new UpdatedPresentationEntityTypeEvent<E, K>(EntityDescriptorBuilderImpl.this, event.getEntity()),
							EntityDescriptorBuilderImpl.this);
		}
	}

}
