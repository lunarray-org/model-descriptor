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

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedOperationReferenceEvent;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.util.ModelBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that processes properties.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder context type.
 */
public final class ProcessParameterListener<E, B extends AnnotationBuilderContext>
		extends AbstractOperationListener<E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessParameterListener.class);

	/** Default constructor. */
	public ProcessParameterListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedOperationReferenceEvent<E, B> event) throws EventException {
		ProcessParameterListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		this.processParameters(event.getBuilder());
	}

	/**
	 * Process a collection parameter.
	 * 
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @return The collection parameter descriptor.
	 */
	private AnnotationCollectionParameterDescriptorBuilder<Object, Collection<Object>, B> processCollectionParameter(
			final AnnotationOperationDescriptorBuilder<E, B> entityDescriptor) {
		return entityDescriptor.addCollectionParameter();
	}

	/**
	 * Process a parameter.
	 * 
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @return The parameter descriptor.
	 */
	private AnnotationParameterDescriptorBuilder<?, B> processParameter(final AnnotationOperationDescriptorBuilder<E, B> entityDescriptor) {
		return entityDescriptor.addParameter();
	}

	/**
	 * Process parameters.
	 * 
	 * @param builder
	 *            The builder.
	 */
	private void processParameters(final AnnotationOperationDescriptorBuilder<E, B> builder) {
		final B builderContext = builder.getBuilderContext();
		final List<DescribedParameter<?>> parameters = builderContext.getParameterResolverStrategy().resolveParameters(
				builder.getOperation());
		for (final DescribedParameter<?> parameter : parameters) {
			// Get Type
			final Class<?> parameterType = parameter.getType();
			// Get cardinality
			final Cardinality cardinality = ModelBuilderUtil.resolveCardinality(parameterType);
			AnnotationParameterDescriptorBuilder<?, B> parameterBuilder;
			if (Cardinality.MULTIPLE == cardinality) {
				parameterBuilder = this.processCollectionParameter(builder);
			} else {
				parameterBuilder = this.processParameter(builder);
			}
			// Set known values.
			parameterBuilder.cardinality(cardinality).index(parameter.getIndex()).parameter(parameter);
		}
	}
}
