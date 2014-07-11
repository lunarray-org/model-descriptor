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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.operation.parameter.collection;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationParameterCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresentationParameterBuilderUtil;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.PresentationParameterAttributeResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parameter processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <P>
 *            The parameter type.
 */
public final class CollectionParameterProcessor<C, P extends Collection<C>>
		implements Listener<UpdatedPresentationParameterCollectionTypeEvent<C, P>> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectionParameterProcessor.class);

	/** Default constructor. */
	public CollectionParameterProcessor() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedPresentationParameterCollectionTypeEvent<C, P> event) throws EventException {
		CollectionParameterProcessor.LOGGER.debug("Processing event {}", event);
		Validate.notNull(event, "Event may not be null.");
		this.process(event.getPresentationBuilder(), event.getParameter());
	}

	/**
	 * Process parameter.
	 * 
	 * @param descriptorBuilder
	 *            The builder.
	 * @param parameter
	 *            The parameter.
	 */
	private void process(final PresQualParameterDescriptorBuilder<P> descriptorBuilder, final DescribedParameter<P> parameter) {
		final PresentationParameterAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationParameterAttributeResolverStrategy();
		PresentationParameterBuilderUtil.process(descriptorBuilder, descriptorBuilder.getDetailBuilder(), null);
		for (final Class<?> qualifier : resolver.qualifiers(parameter)) {
			PresentationParameterBuilderUtil.process(descriptorBuilder, descriptorBuilder.getQualifierBuilder(qualifier), qualifier);
		}
	}
}
