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
package org.lunarray.model.descriptor.builder.annotation.base.builders.operation;

import java.util.Collection;

import org.lunarray.common.event.Bus;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionParameterDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedParameterCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.parameter.collection.SetCollectionRelationListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.parameter.collection.SetCollectionTypeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A collection property builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The property type.
 * @param <P>
 *            The collection type.
 * @param <B>
 *            The builder context type.
 */
public abstract class AbstractCollectionParameterDescriptorBuilder<C, P extends Collection<C>, B extends AnnotationBuilderContext>
		extends AbstractParameterDescriptorBuilder<P, B>
		implements AnnotationCollectionParameterDescriptorBuilder<C, P, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCollectionParameterDescriptorBuilder.class);
	/** The collection type. */
	private transient Class<C> collectionTypeBuilder;

	/**
	 * Builds the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	public AbstractCollectionParameterDescriptorBuilder(final B builderContext) {
		super(builderContext);
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new SetCollectionRelationListener<C, P, B>(), this);
		bus.addListener(new SetCollectionTypeListener<C, P, B>(), this);
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	/* Deduced value. */
	public final AnnotationCollectionParameterDescriptorBuilder<C, P, B> collectionType(final Class<?> collectionType) {
		this.collectionTypeBuilder = (Class<C>) collectionType;
		try {
			final B ctx = this.getBuilderContext();
			ctx.getBus().handleEvent(new UpdatedParameterCollectionTypeEvent<C, P, B>(this, this.getParameter()), this);
		} catch (final EventException e) {
			AbstractCollectionParameterDescriptorBuilder.LOGGER.warn("Could not process event.", e);
		}
		return this;
	}

	/**
	 * Gets the collection type.
	 * 
	 * @return The collection type.
	 */
	@Override
	public final Class<C> getCollectionType() {
		return this.collectionTypeBuilder;
	}
}
