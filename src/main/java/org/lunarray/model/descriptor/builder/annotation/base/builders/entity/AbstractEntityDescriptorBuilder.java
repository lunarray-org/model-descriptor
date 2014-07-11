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
package org.lunarray.model.descriptor.builder.annotation.base.builders.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationCollectionPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptor;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.AbstractBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.entity.EntityExtensionProcessorListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.entity.ProcessCreational;
import org.lunarray.model.descriptor.builder.annotation.base.listener.entity.ProcessOperationListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.entity.ProcessPropertyListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.entity.SetNameListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildEntityEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildPropertyEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.PreBuildEntityEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            Entity type.
 * @param <K>
 *            Key type.
 * @param <B>
 *            Builder context type.
 */
public abstract class AbstractEntityDescriptorBuilder<E, K extends Serializable, B extends AnnotationBuilderContext>
		extends AbstractBuilder<B>
		implements AnnotationEntityDescriptorBuilder<E, K, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityDescriptorBuilder.class);
	/** The property aliases. */
	private final transient Map<String, String> aliases;
	/** Creational strategies. */
	private final transient List<CreationalStrategy<E>> creationalStrategies;
	/** Entity type. */
	private transient DescribedEntity<E> entityType;
	/** Extensions. */
	private final transient Map<Class<?>, EntityExtension<E>> extensions;
	/** Key property. */
	private transient PropertyDescriptor<K, E> keyProperty;
	/** Members. */
	private final transient Map<String, MemberDescriptor<E>> members;
	/** Entity name. */
	private transient String nameBuilder;
	/** Operation builders. */
	private final transient List<AnnotationOperationDescriptorBuilder<E, B>> operationBuilders;
	/** Operations. */
	private final transient Map<String, OperationDescriptor<E>> operations;
	/** Properties. */
	private transient Map<String, PropertyDescriptor<?, E>> properties;
	/** Property builders. */
	private final transient List<AnnotationPropertyDescriptorBuilder<?, E, B>> propertyBuilders;
	/** The relation type. */
	private transient RelationType relationTypeBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	public AbstractEntityDescriptorBuilder(final B builderContext) {
		super(builderContext);
		this.properties = new HashMap<String, PropertyDescriptor<?, E>>();
		this.propertyBuilders = new LinkedList<AnnotationPropertyDescriptorBuilder<?, E, B>>();
		this.extensions = new HashMap<Class<?>, EntityExtension<E>>();
		this.aliases = new HashMap<String, String>();
		this.operations = new HashMap<String, OperationDescriptor<E>>();
		this.members = new HashMap<String, MemberDescriptor<E>>();
		this.operationBuilders = new LinkedList<AnnotationOperationDescriptorBuilder<E, B>>();
		this.creationalStrategies = new LinkedList<CreationalStrategy<E>>();
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new SetNameListener<E, K, B>(), this);
		bus.addListener(new ProcessPropertyListener<E, K, B>(), this);
		bus.addListener(new MemberBuilderBuild(), this);
		bus.addListener(new ProcessOperationListener<E, K, B>(), this);
		bus.addListener(new EntityExtensionProcessorListener<E, K, B>(), this);
		bus.addListener(new ProcessCreational<E, K, B, E, Serializable>(), this);
	}

	/** {@inheritDoc} */
	@Override
	public final void addAlias(final DescribedProperty<?> alias, final String aliasFor) {
		if (alias.getName().equals(aliasFor)) {
			throw new IllegalArgumentException(String.format("Property '%s' cannot be an alias for itself.", aliasFor));
		}
		this.aliases.put(alias.getName(), aliasFor);
	}

	/** {@inheritDoc} */
	@Override
	public final <C, P extends Collection<C>> AnnotationCollectionPropertyDescriptorBuilder<C, P, E, B> addCollectionProperty() {
		final AnnotationCollectionPropertyDescriptorBuilder<C, P, E, B> builder = this.createCollectionPropertyBuilder();
		builder.entityType(this.entityType);
		this.getBuilderContext().getBus().addListener(new KeyBuildListener<P>(), builder);
		this.propertyBuilders.add(builder);
		return builder;
	}

	@Override
	public final AnnotationOperationDescriptorBuilder<E, B> addOperation() {
		final AnnotationOperationDescriptorBuilder<E, B> builder = this.createOperationBuilder();
		builder.entityType(this.getEntityType());
		this.operationBuilders.add(builder);
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<?, E, B> addProperty() {
		final AnnotationPropertyDescriptorBuilder<?, E, B> builder = this.createPropertyBuilder();
		builder.entityType(this.getEntityType());
		this.getBuilderContext().getBus().addListener(new KeyBuildListener<Object>(), builder);
		this.propertyBuilders.add(builder);
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	public final EntityDescriptor<E> build() {
		final Pattern pattern = Pattern.compile(this.getBuilderContext().getConfiguration().nameRegexp());
		final Matcher matcher = pattern.matcher(this.nameBuilder);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("Invalid name '%s'.", this.nameBuilder));
		}
		final B ctx = this.getBuilderContext();
		final Bus bus = ctx.getBus();
		try {
			bus.handleEvent(new PreBuildEntityEvent<E, K, B>(this), this);
		} catch (final EventException e) {
			AbstractEntityDescriptorBuilder.LOGGER.warn("Could not process pre build event.", e);
		}
		final EntityDescriptor<E> descriptor = this.createEntityDescriptor();
		try {
			bus.handleEvent(new BuildEntityEvent<E>(descriptor), this);
		} catch (final EventException e) {
			AbstractEntityDescriptorBuilder.LOGGER.warn("Could not process build event.", e);
		}
		return descriptor;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationEntityDescriptorBuilder<E, K, B> entity(final DescribedEntity<E> entityType) {
		this.entityType = entityType;
		try {
			final B ctx = this.getBuilderContext();
			ctx.getBus().handleEvent(new UpdatedEntityTypeEvent<E, K, B>(this, entityType), this);
		} catch (final EventException e) {
			AbstractEntityDescriptorBuilder.LOGGER.warn("Could not process entity update event.", e);
		}
		return this;
	}

	/**
	 * Gets the value for the aliases field.
	 * 
	 * @return The value for the aliases field.
	 */
	public final Map<String, String> getAliases() {
		return this.aliases;
	}

	/**
	 * Gets the value for the creationalStrategies field.
	 * 
	 * @return The value for the creationalStrategies field.
	 */
	public final List<CreationalStrategy<E>> getCreationalStrategies() {
		return this.creationalStrategies;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedEntity<E> getEntityType() {
		return this.entityType;
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return this.nameBuilder;
	}

	/**
	 * Gets the relation type.
	 * 
	 * @return The relation type.
	 */
	public final RelationType getRelationType() {
		return this.relationTypeBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationEntityDescriptorBuilder<E, K, B> name(final String name) {
		this.nameBuilder = name;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final void registerCreationalStrategy(final CreationalStrategy<E> strategy) {
		this.creationalStrategies.add(strategy);
	}

	/** {@inheritDoc} */
	@Override
	public final void registerExtension(final Class<? extends EntityExtension<E>> extensionInt, final EntityExtension<E> extension) {
		this.extensions.put(extensionInt, extension);
	}

	/**
	 * Updates the relation type.
	 * 
	 * @param relationType
	 *            The relation type.
	 * @return The builder.
	 */
	public final AnnotationEntityDescriptorBuilder<E, K, B> relationType(final RelationType relationType) {
		this.relationTypeBuilder = relationType;
		return this;
	}

	/**
	 * Creates a collection property builder.
	 * 
	 * @param <C>
	 *            The collection type.
	 * @param <P>
	 *            The property type.
	 * @return The collection property builder.
	 */
	protected abstract <C, P extends Collection<C>> AnnotationCollectionPropertyDescriptorBuilder<C, P, E, B> createCollectionPropertyBuilder();

	/**
	 * Creates the entity descriptor.
	 * 
	 * @return The descriptor.
	 */
	protected abstract EntityDescriptor<E> createEntityDescriptor();

	/**
	 * Creates a operation builder.
	 * 
	 * @return The operation builder.
	 */
	protected abstract AnnotationOperationDescriptorBuilder<E, B> createOperationBuilder();

	/**
	 * Creates a property builder.
	 * 
	 * @param <P>
	 *            The property type.
	 * @return The property builder.
	 */
	protected abstract <P> AnnotationPropertyDescriptorBuilder<P, E, B> createPropertyBuilder();

	/**
	 * Gets the extensions.
	 * 
	 * @return The extensions.
	 */
	protected final Map<Class<?>, EntityExtension<E>> getExtensions() {
		return this.extensions;
	}

	/**
	 * Gets the key property.
	 * 
	 * @return The key property.
	 */
	protected final PropertyDescriptor<K, E> getKeyProperty() {
		return this.keyProperty;
	}

	/**
	 * Gets the value for the members field.
	 * 
	 * @return The value for the members field.
	 */
	protected final Map<String, MemberDescriptor<E>> getMembers() {
		return this.members;
	}

	/**
	 * Gets the value for the operations field.
	 * 
	 * @return The value for the operations field.
	 */
	protected final Map<String, OperationDescriptor<E>> getOperations() {
		return this.operations;
	}

	/**
	 * Gets the properties.
	 * 
	 * @return The properties.
	 */
	protected final Map<String, PropertyDescriptor<?, E>> getProperties() {
		return this.properties;
	}

	/**
	 * Key listener.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 * @param <P>
	 *            The property type.
	 */
	public final class KeyBuildListener<P>
			implements Listener<BuildPropertyEvent<P, E>> {

		/** Default constructor. */
		public KeyBuildListener() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@SuppressWarnings("unchecked")
		@Override
		public void handleEvent(final BuildPropertyEvent<P, E> event) throws EventException {
			AbstractEntityDescriptorBuilder.LOGGER.debug("Handling build event {}", event);
			final AnnotationPropertyDescriptor<?, E> property = event.getProperty();
			if (property.isKeyProperty()) {
				AbstractEntityDescriptorBuilder.this.keyProperty = (PropertyDescriptor<K, E>) property;
			}
		}
	}

	/**
	 * Member builder.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	public final class MemberBuilderBuild
			implements Listener<PreBuildEntityEvent<E, K, B>> {

		/** Default constructor. */
		public MemberBuilderBuild() {
			// Default constructor.
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final PreBuildEntityEvent<E, K, B> event) throws EventException {
			AbstractEntityDescriptorBuilder.LOGGER.debug("Handling pre build event {}", event);
			AbstractEntityDescriptorBuilder.this.properties = new HashMap<String, PropertyDescriptor<?, E>>();
			for (final AnnotationPropertyDescriptorBuilder<?, E, B> builder : AbstractEntityDescriptorBuilder.this.propertyBuilders) {
				final PropertyDescriptor<?, E> property = builder.build();
				final String name = property.getName();
				AbstractEntityDescriptorBuilder.this.properties.put(name, property);
				AbstractEntityDescriptorBuilder.this.members.put(name, property);
			}
			for (final AnnotationOperationDescriptorBuilder<E, B> builder : AbstractEntityDescriptorBuilder.this.operationBuilders) {
				final OperationDescriptor<E> operation = builder.build();
				final String name = operation.getName();
				AbstractEntityDescriptorBuilder.this.operations.put(name, operation);
				AbstractEntityDescriptorBuilder.this.members.put(name, operation);
			}
		}
	}
}
