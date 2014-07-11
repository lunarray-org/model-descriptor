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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.property;

import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationPropertyValueReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresentationPropertyBuilderUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PropertyDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PresentationPropertyAttributeResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The general property processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class PropertyProcessor<P, E>
		implements Listener<UpdatedPresentationPropertyValueReferenceEvent<P, E>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyProcessor.class);

	/** Default constructor. */
	public PropertyProcessor() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedPresentationPropertyValueReferenceEvent<P, E> event) throws EventException {
		PropertyProcessor.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		this.process(event.getPropertyBuilder());
	}

	/**
	 * Process.
	 * 
	 * @param descriptorBuilder
	 *            The builder.
	 */
	private void process(final PresQualPropertyDescriptorBuilder<P, E> descriptorBuilder) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationPropertyAttributeResolverStrategy resolver = ctx.getPresentationPropertyAttributeResolverStrategy();

		descriptorBuilder.getDetailBuilder().renderType(PresentationPropertyBuilderUtil.guessRenderType(descriptorBuilder));
		PresentationPropertyBuilderUtil.process(descriptorBuilder, descriptorBuilder.getDetailBuilder(), null);
		final DescribedEntity<E> entityType = descriptorBuilder.getEntityType();
		final Set<Class<?>> qualifiers = ctx.getQualifiers(entityType);
		for (final Class<?> qualifier : resolver.qualifiers(descriptorBuilder.getProperty())) {
			final PropertyDetailBuilder detailBuilder = descriptorBuilder.getQualifierBuilder(qualifier);
			PresentationPropertyBuilderUtil.process(descriptorBuilder, detailBuilder, qualifier);
			ctx.putQualifier(entityType, qualifier);
			qualifiers.add(qualifier);
		}
		for (final Class<?> qualifier : qualifiers) {
			final PropertyDetailBuilder qualifierDetail = descriptorBuilder.getQualifierBuilder(qualifier);
			PresentationPropertyBuilderUtil.process(descriptorBuilder, qualifierDetail, qualifier);
		}
	}
}
