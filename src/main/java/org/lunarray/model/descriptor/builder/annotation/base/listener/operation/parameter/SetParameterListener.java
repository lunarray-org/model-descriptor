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
package org.lunarray.model.descriptor.builder.annotation.base.listener.operation.parameter;

import java.util.Deque;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.common.generics.GenericsUtil;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedParameterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update parameter type.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 * @param <B>
 *            The builder type.
 */
public final class SetParameterListener<P, B extends AnnotationBuilderContext>
		implements Listener<UpdatedParameterEvent<P, B>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SetParameterListener.class);

	/** Default constructor. */
	public SetParameterListener() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedParameterEvent<P, B> event) throws EventException {
		SetParameterListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationParameterDescriptorBuilder<P, B> builder = event.getBuilder();
		final Deque<DescribedParameter<?>> params = new LinkedList<DescribedParameter<?>>();
		params.add(event.getParameter());
		final java.lang.reflect.Type type = GenericsUtil.getRealType(params);
		if (type instanceof Class) {
			builder.type((Class<?>) type);
		} else {
			builder.type(Object.class);
		}
	}
}
