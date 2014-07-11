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
package org.lunarray.model.descriptor.builder.annotation.base.listener.operation.result;

import java.util.Deque;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.generics.GenericsUtil;
import org.lunarray.common.generics.Member;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.AbstractOperationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update property type.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public final class SetResultListener<R, E, B extends AnnotationBuilderContext>
		extends AbstractOperationListener<E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SetResultListener.class);

	/** Default constructor. */
	public SetResultListener() {
		// Default constructor.
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedOperationReferenceEvent<E, B> event) throws EventException {
		SetResultListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationOperationDescriptorBuilder<E, B> builder = event.getBuilder();
		final Deque<Member> members = new LinkedList<Member>();
		members.add(builder.getOperation());
		members.addAll(builder.getBuilderContext().getAccessorContext().getDescribedProperties());
		final java.lang.reflect.Type type = GenericsUtil.getRealType(members);
		final AnnotationResultDescriptorBuilder<R, E, B> resultDescriptor = builder.getResultBuilder();
		if (type instanceof Class) {
			resultDescriptor.type((Class<?>) type);
		} else {
			final Class<?> guessedClazz = GenericsUtil.guessClazz(type);
			resultDescriptor.type(guessedClazz);
		}

	}
}
