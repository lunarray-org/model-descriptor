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

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.AbstractOperationListener;
import org.lunarray.model.descriptor.builder.annotation.util.ResultTypeBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Updates the relation.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder type.
 */
public final class SetRelationListener<R, E, B extends AnnotationBuilderContext>
		extends AbstractOperationListener<E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SetRelationListener.class);

	/** Default constructor. */
	public SetRelationListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedOperationReferenceEvent<E, B> event) throws EventException {
		SetRelationListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationOperationDescriptorBuilder<E, B> operationBuilder = event.getBuilder();
		final AnnotationResultDescriptorBuilder<R, E, B> builder = operationBuilder.getResultBuilder();
		final DescribedOperation operation = operationBuilder.getOperation();
		@SuppressWarnings("unchecked")
		final DescribedEntity<R> entity = (DescribedEntity<R>) builder.getBuilderContext().getEntityResolverStrategy()
				.resolveEntity(operation.getResultType());
		ResultTypeBuilderUtil.updateRelation(builder, entity, operation);
	}
}
