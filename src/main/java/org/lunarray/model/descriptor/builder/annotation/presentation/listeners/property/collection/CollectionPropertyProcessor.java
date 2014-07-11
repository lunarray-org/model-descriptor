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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.property.collection;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationPropertyCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresentationPropertyBuilderUtil;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.PresentationEntityAttributeResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Property processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class CollectionPropertyProcessor<C, P extends Collection<C>, E>
		implements Listener<UpdatedPresentationPropertyCollectionTypeEvent<C, P, E>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionPropertyProcessor.class);

	/** Default constructor. */
	public CollectionPropertyProcessor() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedPresentationPropertyCollectionTypeEvent<C, P, E> event) throws EventException {
		CollectionPropertyProcessor.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		this.process(event.getPresentationBuilder(), event.getProperty());
	}

	/**
	 * Process property.
	 * 
	 * @param descriptorBuilder
	 *            The builder.
	 * @param property
	 *            The property.
	 */
	private void process(final PresQualPropertyDescriptorBuilder<P, E> descriptorBuilder, final DescribedProperty<P> property) {
		final PresentationEntityAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationEntityAttributeResolverStrategy();
		PresentationPropertyBuilderUtil.process(descriptorBuilder, descriptorBuilder.getDetailBuilder(), null);
		@SuppressWarnings("unchecked")
		final DescribedEntity<E> entity = (DescribedEntity<E>) property.getEntityType();
		for (final Class<?> qualifier : resolver.qualifiers(entity)) {
			PresentationPropertyBuilderUtil.process(descriptorBuilder, descriptorBuilder.getQualifierBuilder(qualifier), qualifier);
		}
	}
}
