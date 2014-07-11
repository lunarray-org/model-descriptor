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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.entity;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.PreBuildEntityEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.PresQualEntityDescriptorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public final class EntityPreBuildListener<E, K extends Serializable>
		extends AbstractEntityUpdateListener<E>
		implements Listener<PreBuildEntityEvent<E, K, PresQualBuilderContext>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityPreBuildListener.class);

	/** Default constructor. */
	public EntityPreBuildListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final PreBuildEntityEvent<E, K, PresQualBuilderContext> event) throws EventException {
		EntityPreBuildListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationEntityDescriptorBuilder<E, K, PresQualBuilderContext> entity = event.getEntity();
		this.setDescriptor((PresQualEntityDescriptorBuilder<E, ?>) entity);
		this.processNewQualifiers();
	}
}
