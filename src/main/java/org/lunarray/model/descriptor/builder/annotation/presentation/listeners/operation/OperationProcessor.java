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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.operation;

import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.OperationDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.PresQualOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.PresentationOperationBuilderUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualResultTypeDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.ResultDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.PresentationOperationAttributeResolverStrategy;

/**
 * The general operation processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class OperationProcessor<E>
		implements Listener<UpdatedPresentationOperationReferenceEvent<E>> {

	/** Default constructor. */
	public OperationProcessor() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedPresentationOperationReferenceEvent<E> event) throws EventException {
		Validate.notNull(event, "Event may not be null.");
		this.process(event.getOperationBuilder());
	}

	/**
	 * Process.
	 * 
	 * @param descriptorBuilder
	 *            The builder.
	 */
	private void process(final PresQualOperationDescriptorBuilder<E> descriptorBuilder) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationOperationAttributeResolverStrategy resolver = ctx.getPresentationOperationAttributeResolverStrategy();
		final PresQualResultTypeDescriptorBuilder<Object, E> resultBuilder = descriptorBuilder.getPresentationResultBuilder();

		PresentationOperationBuilderUtil.process(descriptorBuilder, descriptorBuilder.getDetailBuilder(), resultBuilder.getDetailBuilder(),
				null);
		final DescribedEntity<E> entityType = descriptorBuilder.getEntityType();
		final Set<Class<?>> qualifiers = ctx.getQualifiers(entityType);
		for (final Class<?> qualifier : resolver.qualifiers(descriptorBuilder.getOperation())) {
			final ResultDetailBuilder resultTypeDetailBuilder = resultBuilder.getQualifierBuilder(qualifier);
			final OperationDetailBuilder detailBuilder = descriptorBuilder.getQualifierBuilder(qualifier);
			PresentationOperationBuilderUtil.process(descriptorBuilder, detailBuilder, resultTypeDetailBuilder, qualifier);
			ctx.putQualifier(entityType, qualifier);
			qualifiers.add(qualifier);
		}
		for (final Class<?> qualifier : qualifiers) {
			final ResultDetailBuilder resultTypeDetailBuilder = resultBuilder.getQualifierBuilder(qualifier);
			final OperationDetailBuilder qualifierDetail = descriptorBuilder.getQualifierBuilder(qualifier);
			PresentationOperationBuilderUtil.process(descriptorBuilder, qualifierDetail, resultTypeDetailBuilder, qualifier);
		}
	}
}
