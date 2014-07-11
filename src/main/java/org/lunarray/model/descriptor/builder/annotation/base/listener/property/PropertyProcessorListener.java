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
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.property.AnnotationPropertyDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedPropertyTypeEvent;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Property processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public final class PropertyProcessorListener<P, E, B extends AnnotationBuilderContext>
		extends AbstractPropertyProcessorListener<P, E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyProcessorListener.class);

	/** Default constructor. */
	public PropertyProcessorListener() {
		super();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(final UpdatedPropertyTypeEvent<P, E, B> event) throws EventException {
		PropertyProcessorListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationPropertyDescriptorBuilder<P, E, B> builder = event.getBuilder();
		final B ctx = builder.getBuilderContext();
		final DescribedProperty<P> property = event.getProperty();
		for (final ModelProcessor<?> processor : ctx.getPostProcessors()) {
			final ModelProcessor<E> modelProcessor = (ModelProcessor<E>) processor;
			final PropertyExtension<P, E> extension = modelProcessor.process(property, ctx.getExtensionContainer());
			if (!CheckUtil.isNull(extension)) {
				for (final Class<?> extensionInt : extension.getClass().getInterfaces()) {
					if (PropertyExtension.class.isAssignableFrom(extensionInt)) {
						builder.registerExtension((Class<? extends PropertyExtension<P, E>>) extensionInt, extension);
					}
				}
			}
		}
	}
}
