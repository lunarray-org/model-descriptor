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
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The entity extension processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder context.
 */
public final class EntityExtensionProcessorListener<E, K extends Serializable, B extends AnnotationBuilderContext>
		extends AbstractUpdateEntityTypeListener<E, K, B> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityExtensionProcessorListener.class);

	/** Default constructor. */
	public EntityExtensionProcessorListener() {
		super();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(final UpdatedEntityTypeEvent<E, K, B> event) throws EventException {
		EntityExtensionProcessorListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final B ctx = event.getBuilder().getBuilderContext();
		for (final ModelProcessor<?> processor : ctx.getPostProcessors()) {
			final ModelProcessor<E> modelProcessor = (ModelProcessor<E>) processor;
			final EntityExtension<E> extension = modelProcessor.process(event.getEntity(), ctx.getExtensionContainer());
			if (!CheckUtil.isNull(extension)) {
				for (final Class<?> extensionInt : extension.getClass().getInterfaces()) {
					if (EntityExtension.class.isAssignableFrom(extensionInt)) {
						event.getBuilder().registerExtension((Class<? extends EntityExtension<E>>) extensionInt, extension);
					}
				}
			}
		}
	}
}
