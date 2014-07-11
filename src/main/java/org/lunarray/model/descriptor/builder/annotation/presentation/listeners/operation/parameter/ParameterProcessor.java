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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.operation.parameter;

import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationParameterEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.ParameterDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresentationParameterBuilderUtil;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.PresentationParameterAttributeResolverStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The general property processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 */
public final class ParameterProcessor<P>
		implements Listener<UpdatedPresentationParameterEvent<P>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterProcessor.class);

	/** Default constructor. */
	public ParameterProcessor() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedPresentationParameterEvent<P> event) throws EventException {
		ParameterProcessor.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		this.process(event.getParameterBuilder());
	}

	/**
	 * Process.
	 * 
	 * @param descriptorBuilder
	 *            The builder.
	 */
	private void process(final PresQualParameterDescriptorBuilder<P> descriptorBuilder) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationParameterAttributeResolverStrategy resolver = ctx.getPresentationParameterAttributeResolverStrategy();

		descriptorBuilder.getDetailBuilder().renderType(PresentationParameterBuilderUtil.guessRenderType(descriptorBuilder));
		PresentationParameterBuilderUtil.process(descriptorBuilder, descriptorBuilder.getDetailBuilder(), null);
		final DescribedEntity<?> entityType = descriptorBuilder.getEntityType();
		final Set<Class<?>> qualifiers = ctx.getQualifiers(entityType);
		for (final Class<?> qualifier : resolver.qualifiers(descriptorBuilder.getParameter())) {
			final ParameterDetailBuilder detailBuilder = descriptorBuilder.getQualifierBuilder(qualifier);
			PresentationParameterBuilderUtil.process(descriptorBuilder, detailBuilder, qualifier);
			ctx.putQualifier(entityType, qualifier);
			qualifiers.add(qualifier);
		}
		for (final Class<?> qualifier : qualifiers) {
			final ParameterDetailBuilder qualifierDetail = descriptorBuilder.getQualifierBuilder(qualifier);
			PresentationParameterBuilderUtil.process(descriptorBuilder, qualifierDetail, qualifier);
		}
	}
}
