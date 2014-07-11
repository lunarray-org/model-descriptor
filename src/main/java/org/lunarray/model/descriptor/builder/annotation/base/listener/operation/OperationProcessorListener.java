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
package org.lunarray.model.descriptor.builder.annotation.base.listener.operation;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedOperationReferenceEvent;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Operation processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public final class OperationProcessorListener<E, B extends AnnotationBuilderContext>
		extends AbstractOperationListener<E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationProcessorListener.class);

	/** Default constructor. */
	public OperationProcessorListener() {
		// Default constructor.
		super();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(final UpdatedOperationReferenceEvent<E, B> event) throws EventException {
		OperationProcessorListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationOperationDescriptorBuilder<E, B> builder = event.getBuilder();
		final B ctx = builder.getBuilderContext();
		final DescribedOperation operation = event.getOperation();
		for (final ModelProcessor<?> processor : ctx.getPostProcessors()) {
			final ModelProcessor<E> modelProcessor = (ModelProcessor<E>) processor;
			final OperationExtension<E> extension = modelProcessor.process(operation, ctx.getExtensionContainer());
			if (!CheckUtil.isNull(extension)) {
				for (final Class<?> extensionInt : extension.getClass().getInterfaces()) {
					if (OperationExtension.class.isAssignableFrom(extensionInt)) {
						builder.registerExtension((Class<? extends OperationExtension<E>>) extensionInt, extension);
					}
				}
			}
		}
	}
}
