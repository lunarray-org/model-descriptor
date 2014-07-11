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
package org.lunarray.model.descriptor.builder.annotation.base.listener.operation.parameter.collection;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedParameterCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.util.ParameterBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Updates the collection relation.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 * @param <C>
 *            The collection type.
 * @param <B>
 *            The builder context type.
 */
public final class SetCollectionRelationListener<C, P extends Collection<C>, B extends AnnotationBuilderContext>
		implements Listener<UpdatedParameterCollectionTypeEvent<C, P, B>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SetCollectionRelationListener.class);

	/** Default constructor. */
	public SetCollectionRelationListener() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedParameterCollectionTypeEvent<C, P, B> event) throws EventException {
		SetCollectionRelationListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final AnnotationCollectionParameterDescriptorBuilder<C, P, B> builder = event.getCollectionBuilder();
		final DescribedParameter<P> parameter = builder.getParameter();
		final DescribedEntity<?> collectionEntity = builder.getBuilderContext().getEntityResolverStrategy()
				.resolveEntity(builder.getCollectionType());
		ParameterBuilderUtil.updateRelation(builder, collectionEntity, parameter);
	}
}
