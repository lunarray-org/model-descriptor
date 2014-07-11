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
package org.lunarray.model.descriptor.builder.annotation.base.listener.model;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.ResourceAddedEvent;
import org.lunarray.model.descriptor.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache entity names.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The model super type.
 * @param <B>
 *            The builder context.
 */
public final class EntityNameCacheListener<S, B extends AnnotationBuilderContext>
		implements Listener<ResourceAddedEvent<S, B>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityNameCacheListener.class);

	/** Default constructor. */
	public EntityNameCacheListener() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final ResourceAddedEvent<S, B> event) throws EventException {
		EntityNameCacheListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		try {
			for (final Class<?> type : event.getResource().getResources()) {
				final B ctx = event.getBuilder().getBuilderContext();
				final DescribedEntity<?> entity = ctx.getEntityResolverStrategy().resolveEntity(type);
				ctx.cacheEntityName(type, ctx.getEntityAttributeResolverStrategy().getName(entity));
			}
		} catch (final ResourceException e) {
			throw new EventException(e);
		}
	}
}
