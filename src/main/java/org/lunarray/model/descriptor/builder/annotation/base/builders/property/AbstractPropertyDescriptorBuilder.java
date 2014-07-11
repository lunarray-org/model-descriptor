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
package org.lunarray.model.descriptor.builder.annotation.base.builders.property;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.accessor.reference.property.PropertyValueReference;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptor;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.AbstractBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildPropertyEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedPropertyTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedPropertyValueReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.property.PropertyProcessorListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.property.SetImmutableListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.property.SetKeyListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.property.SetPropertyListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.property.SetRelationListener;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes a property descriptor builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The context type.
 */
public abstract class AbstractPropertyDescriptorBuilder<P, E, B extends AnnotationBuilderContext>
		extends AbstractBuilder<B>
		implements AnnotationPropertyDescriptorBuilder<P, E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPropertyDescriptorBuilder.class);
	/** The property cardinality. */
	private transient Cardinality cardinalityBuilder;
	/** The entity type. */
	private transient DescribedEntity<E> entityTypeBuilder;
	/** The extensions. */
	private final transient Map<Class<?>, PropertyExtension<P, E>> extensions;
	/** Indicates the property is immutable. */
	private transient boolean immutableBuilder;
	/** Denotes whether the property is a key property. */
	private transient boolean keyBuilder;
	/** The property name. */
	private transient String nameBuilder;
	/** The property. */
	private transient DescribedProperty<P> propertyBuilder;
	/** The property type. */
	private transient Class<P> propertyType;
	/** Indicates the property is related. */
	private transient RelationType relatedBuilder;
	/** The name of the related entity. */
	private transient String relatedNameBuilder;
	/** The value reference. */
	private transient PropertyValueReference<P, E> valueReferenceBuilder;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	public AbstractPropertyDescriptorBuilder(final B builderContext) {
		super(builderContext);
		this.extensions = new HashMap<Class<?>, PropertyExtension<P, E>>();
		this.keyBuilder = false;
		this.immutableBuilder = false;
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new SetImmutableListener<P, E, B>(), this);
		bus.addListener(new PropertyProcessorListener<P, E, B>(), this);
		bus.addListener(new SetPropertyListener<P, E, B>(), this);
		bus.addListener(new SetRelationListener<P, E, B>(), this);
		bus.addListener(new SetKeyListener<P, E, B>(), this);
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptor<P, E> build() {
		final Pattern pattern = Pattern.compile(this.getBuilderContext().getConfiguration().nameRegexp());
		final Matcher matcher = pattern.matcher(this.nameBuilder);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("Invalid name '%s'.", this.nameBuilder));
		}
		final AnnotationPropertyDescriptor<P, E> propertyDescriptor = this.createPropertyDescriptor();
		try {
			final B ctx = this.getBuilderContext();
			ctx.getBus().handleEvent(new BuildPropertyEvent<P, E>(propertyDescriptor), this);
		} catch (final EventException e) {
			AbstractPropertyDescriptorBuilder.LOGGER.warn("Could not process build event.", e);
		}
		return propertyDescriptor;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> cardinality(final Cardinality cardinality) {
		this.cardinalityBuilder = cardinality;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> entityType(final DescribedEntity<E> entityType) {
		this.entityTypeBuilder = entityType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final Cardinality getCardinality() {
		return this.cardinalityBuilder;
	}

	/**
	 * Gets the value for the entityTypeBuilder field.
	 * 
	 * @return The value for the entityTypeBuilder field.
	 */
	public final DescribedEntity<E> getEntityType() {
		return this.entityTypeBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedProperty<P> getProperty() {
		return this.propertyBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<P> getPropertyType() {
		return this.propertyType;
	}

	/**
	 * Tests if this property is related.
	 * 
	 * @return True if and only if this property is related.
	 */
	@Override
	public final RelationType getRelationType() {
		return this.relatedBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyValueReference<P, E> getValueReference() {
		return this.valueReferenceBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> immutable() {
		this.immutableBuilder = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isImmutable() {
		return this.immutableBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> key() {
		this.keyBuilder = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> mutable() {
		this.immutableBuilder = false;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> name(final String name) {
		this.nameBuilder = name;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	// It's a restriction.
	public final AnnotationPropertyDescriptorBuilder<P, E, B> property(final DescribedProperty<?> property) {
		this.propertyBuilder = (DescribedProperty<P>) property;
		try {
			this.getBuilderContext().getBus().handleEvent(new UpdatedPropertyTypeEvent<P, E, B>(this, this.propertyBuilder), this);
		} catch (final EventException e) {
			AbstractPropertyDescriptorBuilder.LOGGER.warn("Could not process update property event.", e);
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final void registerExtension(final Class<? extends PropertyExtension<P, E>> extensionInt, final PropertyExtension<P, E> extension) {
		this.extensions.put(extensionInt, extension);
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> related() {
		this.relatedBuilder = RelationType.CONCRETE;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> relatedName(final String relatedName) {
		this.relatedNameBuilder = relatedName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> relatedReference() {
		this.relatedBuilder = RelationType.REFERENCE;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	/* Deduced value. */
	public final AnnotationPropertyDescriptorBuilder<P, E, B> type(final Class<?> type) {
		this.propertyType = (Class<P>) type;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationPropertyDescriptorBuilder<P, E, B> unrelated() {
		this.relatedBuilder = null;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	/* Deduced value. */
	public final AnnotationPropertyDescriptorBuilder<P, E, B> valueReference(final PropertyValueReference<?, E> valueReference) {
		this.valueReferenceBuilder = (PropertyValueReference<P, E>) valueReference;
		try {
			this.getBuilderContext().getBus()
					.handleEvent(new UpdatedPropertyValueReferenceEvent<P, E, B>(this, this.propertyBuilder, this.valueReferenceBuilder));
		} catch (final EventException e) {
			AbstractPropertyDescriptorBuilder.LOGGER.warn("Could not process value reference update event.", e);
		}
		return this;
	}

	/**
	 * Create a property descriptor.
	 * 
	 * @return A property descriptor.
	 */
	protected abstract AnnotationPropertyDescriptor<P, E> createPropertyDescriptor();

	/**
	 * Get the extensions.
	 * 
	 * @return The extensions.
	 */
	protected final Map<Class<?>, PropertyExtension<P, E>> getExtensions() {
		return this.extensions;
	}

	/**
	 * Gets the name.
	 * 
	 * @return The name.
	 */
	protected final String getName() {
		return this.nameBuilder;
	}

	/**
	 * Gets the related name.
	 * 
	 * @return The related name.
	 */
	protected final String getRelatedName() {
		return this.relatedNameBuilder;
	}

	/**
	 * Indicates this is a key property.
	 * 
	 * @return True if and only if this property is key property.
	 */
	protected final boolean isKey() {
		return this.keyBuilder;
	}
}
