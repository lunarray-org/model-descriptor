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
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedCollectionResultTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedOperationReferenceEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedResultCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.AbstractOperationListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.result.collection.SetCollectionRelationListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.operation.result.collection.SetCollectionTypeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A collection property builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The collection type.
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 * @param <B>
 *            The builder context type.
 */
public abstract class AbstractCollectionResultDescriptorBuilder<C, R extends Collection<C>, E, B extends AnnotationBuilderContext>
		extends AbstractResultDescriptorBuilder<R, E, B>
		implements AnnotationCollectionResultDescriptorBuilder<C, R, E, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCollectionResultDescriptorBuilder.class);

	/** The collection type. */
	private transient Class<C> collectionTypeBuilder;

	/**
	 * Builds the builder.
	 * 
	 * @param operation
	 *            The operation.
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	public AbstractCollectionResultDescriptorBuilder(final DescribedOperation operation, final B builderContext) {
		super(operation, builderContext);
		final Bus bus = this.getBuilderContext().getBus();
		bus.addListener(new Delegate(), this);
		bus.addListener(new SetCollectionTypeListener<C, R, E, B>(), this);
		bus.addListener(new SetCollectionRelationListener<C, R, E, B>(), this);
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	/* Deduced value. */
	public final AnnotationCollectionResultDescriptorBuilder<C, R, E, B> collectionType(final Class<?> collectionType) {
		this.collectionTypeBuilder = (Class<C>) collectionType;
		try {
			final B ctx = this.getBuilderContext();
			ctx.getBus().handleEvent(new UpdatedResultCollectionTypeEvent<C, R, E, B>(this, this.getOperation()), this);
		} catch (final EventException e) {
			AbstractCollectionResultDescriptorBuilder.LOGGER.warn("Could not process event.", e);
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

	/**
	 * The result type update.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	public final class Delegate
			extends AbstractOperationListener<E, B> {
		/** Default constructor. */
		public Delegate() {
			super();
			// Default constructor.
		}

		@Override
		/** {@inheritDoc} */
		public void handleEvent(final UpdatedOperationReferenceEvent<E, B> event) throws EventException {
			final Bus bus = AbstractCollectionResultDescriptorBuilder.this.getBuilderContext().getBus();
			bus.handleEvent(new UpdatedCollectionResultTypeEvent<C, R, E, B>(AbstractCollectionResultDescriptorBuilder.this,
					AbstractCollectionResultDescriptorBuilder.this.getOperation()), AbstractCollectionResultDescriptorBuilder.this);
		}
	}
}
