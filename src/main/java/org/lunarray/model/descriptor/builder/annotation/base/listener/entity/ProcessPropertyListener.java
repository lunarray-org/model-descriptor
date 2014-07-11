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
package org.lunarray.model.descriptor.builder.annotation.base.listener.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.property.AbstractProperty;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.accessor.reference.ReferenceBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationCollectionPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.util.ModelBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that processes properties.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder context type.
 */
public final class ProcessPropertyListener<E, K extends Serializable, B extends AnnotationBuilderContext>
		extends AbstractUpdateEntityTypeListener<E, K, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessPropertyListener.class);

	/** Default constructor. */
	public ProcessPropertyListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedEntityTypeEvent<E, K, B> event) throws EventException {
		ProcessPropertyListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		// Pass properties.
		final AnnotationEntityDescriptorBuilder<E, K, B> builder = event.getBuilder();
		builder.getBuilderContext().setAccessorContext(new ReferenceBuilder<Object, E>(builder.getBuilderContext().getConfiguration()));
		this.processProperties(builder, event.getEntity());
	}

	/**
	 * Process a collection property.
	 * 
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @return The collection property descriptor.
	 */
	private AnnotationCollectionPropertyDescriptorBuilder<Object, Collection<Object>, E, B> processCollectionProperty(
			final AnnotationEntityDescriptorBuilder<E, K, B> entityDescriptor) {
		entityDescriptor.getBuilderContext().getAccessorContext();
		return entityDescriptor.addCollectionProperty();
	}

	/**
	 * Process properties.
	 * 
	 * @param builder
	 *            The builder.
	 * @param entityType
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	/* Deduced type. */
	private void processProperties(final AnnotationEntityDescriptorBuilder<E, K, B> builder, final DescribedEntity<E> entityType) {
		final B builderContext = builder.getBuilderContext();
		final List<DescribedProperty<?>> properties = builderContext.getPropertyResolverStrategy().resolveProperties(entityType);
		final ReferenceBuilder<Object, E> ctx = builderContext.getAccessorContext();
		final PropertyAttributeResolverStrategy attributeStrategy = builderContext.getPropertyAttributeResolverStrategy();
		for (final DescribedProperty<?> property : properties) {
			if (attributeStrategy.isIgnore(property)) {
				// Ignore.
				ProcessPropertyListener.LOGGER.info("Ignored property '{}'.", property.getName());
			} else if (attributeStrategy.isAlias(property)) {
				// Process Alias.
				builder.addAlias(property, attributeStrategy.getAlias(property));
			} else {
				// Process regular property.
				ctx.pushProperty(AbstractProperty.createBuilder().describedProperty(property), attributeStrategy.getName(property));
				if (attributeStrategy.isEmbedded(property)) {
					final DescribedEntity<E> entity = (DescribedEntity<E>) builderContext.getEntityResolverStrategy().resolveEntity(
							property.getType());
					this.processProperties(builder, entity);
				}
				// Get Type
				final Class<?> propertyType = property.getType();
				// Get cardinality
				final Cardinality cardinality = ModelBuilderUtil.resolveCardinality(propertyType);
				AnnotationPropertyDescriptorBuilder<?, E, B> propertyBuilder;
				if (Cardinality.MULTIPLE == cardinality) {
					propertyBuilder = this.processCollectionProperty(builder);
				} else {
					propertyBuilder = this.processProperty(builder);
				}
				// Set known values.
				propertyBuilder.cardinality(cardinality).property(property).type(propertyType);
				// Set immutable.
				if (!ctx.getCurrentReference().isMutable()) {
					propertyBuilder.immutable();
				}
				// Set accessor values.
				propertyBuilder.valueReference(ctx.getCurrentReference()).name(ctx.resolveName());
				ctx.popProperty();
			}
		}
	}

	/**
	 * Process a property.
	 * 
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @return The property descriptor.
	 */
	private AnnotationPropertyDescriptorBuilder<?, E, B> processProperty(final AnnotationEntityDescriptorBuilder<E, K, B> entityDescriptor) {
		entityDescriptor.getBuilderContext().getAccessorContext();
		return entityDescriptor.addProperty();
	}
}
