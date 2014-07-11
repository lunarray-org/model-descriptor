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

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that sets the name.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder context type.
 */
public final class SetNameListener<E, K extends Serializable, B extends AnnotationBuilderContext>
		extends AbstractUpdateEntityTypeListener<E, K, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SetNameListener.class);

	/** Default constructor. */
	public SetNameListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedEntityTypeEvent<E, K, B> event) throws EventException {
		SetNameListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationEntityDescriptorBuilder<E, K, B> builder = event.getBuilder();
		builder.name(builder.getBuilderContext().getEntityName(event.getEntity()));
	}
}
