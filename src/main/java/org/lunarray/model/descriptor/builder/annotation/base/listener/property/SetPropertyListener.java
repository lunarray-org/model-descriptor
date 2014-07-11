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
package org.lunarray.model.descriptor.builder.annotation.base.listener.property;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.common.generics.GenericsUtil;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedPropertyTypeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update property type.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public final class SetPropertyListener<P, E, B extends AnnotationBuilderContext>
		implements Listener<UpdatedPropertyTypeEvent<P, E, B>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SetPropertyListener.class);

	/** Default constructor. */
	public SetPropertyListener() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedPropertyTypeEvent<P, E, B> event) throws EventException {
		SetPropertyListener.LOGGER.debug("Handle event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationPropertyDescriptorBuilder<P, E, B> builder = event.getBuilder();
		final java.lang.reflect.Type type = GenericsUtil.getRealType(builder.getBuilderContext().getAccessorContext()
				.getDescribedProperties());
		if (type instanceof Class) {
			builder.type((Class<?>) type);
		} else {
			builder.type(Object.class);
		}
	}
}
