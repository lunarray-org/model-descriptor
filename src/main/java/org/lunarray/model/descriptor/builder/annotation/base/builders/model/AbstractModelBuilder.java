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
package org.lunarray.model.descriptor.builder.annotation.base.builders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.builder.Configuration;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.model.AnnotationModelBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.AbstractBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.BuildEntityEvent;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.ResourceAddedEvent;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A model builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The entity super type.
 * @param <M>
 *            The model type.
 * @param <B>
 *            The builder context type.
 */
public abstract class AbstractModelBuilder<S, M extends AbstractModel<S>, B extends AnnotationBuilderContext>
		extends AbstractBuilder<B>
		implements AnnotationModelBuilder<S, M, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractModelBuilder.class);
	/** Entity builders. */
	private final transient List<AnnotationEntityDescriptorBuilder<? extends S, ?, B>> entityBuilders;
	/** Entities by name. */
	private final transient Map<String, EntityDescriptor<? extends S>> entityByName;
	/** Entities by type. */
	private final transient Map<Class<?>, EntityDescriptor<? extends S>> entityByType;

	/**
	 * Constructs the builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	protected AbstractModelBuilder(final B builderContext) {
		super(builderContext);
		builderContext.getBus().addListener(new CachedListener());
		this.entityBuilders = new LinkedList<AnnotationEntityDescriptorBuilder<? extends S, ?, B>>();
		this.entityByName = new HashMap<String, EntityDescriptor<? extends S>>();
		this.entityByType = new HashMap<Class<?>, EntityDescriptor<? extends S>>();
	}

	/** {@inheritDoc} */
	@Override
	public final <E extends S> AnnotationEntityDescriptorBuilder<E, ?, B> addEntity() {
		final AnnotationEntityDescriptorBuilder<E, ?, B> builder = this.createEntityBuilder();
		this.entityBuilders.add(builder);
		return builder;
	}

	/** {@inheritDoc} */
	@Override
	public final M build() {
		this.buildEntities();
		return this.createModel();
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationModelBuilder<S, M, B> configuration(final Configuration configuration) {
		this.getBuilderContext().configuration(configuration);
		return this;
	}

	/**
	 * Adds post processors.
	 * 
	 * @param postProcessors
	 *            The post processors to add.
	 * @return The builder.
	 */
	@Override
	public final AnnotationModelBuilder<S, M, B> postProcessors(final ModelProcessor<S>... postProcessors) {
		final B ctx = this.getBuilderContext();
		for (final ModelProcessor<S> processor : postProcessors) {
			ctx.addProcessor(processor);
			final Extension ext = processor.process(ctx.getExtensionContainer());
			if (!CheckUtil.isNull(ext)) {
				ctx.addExtension(ext);
			}
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final AnnotationModelBuilder<S, M, B> resource(final Resource<Class<? extends S>> resource) throws ResourceException {
		final B ctx = this.getBuilderContext();
		ctx.registeredResources(resource);
		try {
			ctx.getBus().handleEvent(new ResourceAddedEvent<S, B>(resource, this));
		} catch (final EventException e) {
			AbstractModelBuilder.LOGGER.warn("Could not process resource event.", e);
		}
		return this;
	}

	/**
	 * Build entities.
	 */
	private void buildEntities() {
		for (final AnnotationEntityDescriptorBuilder<? extends S, ?, B> entityBuilder : this.entityBuilders) {
			entityBuilder.build();
		}
	}

	/**
	 * Create entity builder.
	 * 
	 * @param <E>
	 *            The entity type.
	 * @return An entity builder.
	 */
	protected abstract <E extends S> AnnotationEntityDescriptorBuilder<E, ?, B> createEntityBuilder();

	/**
	 * Creates a model.
	 * 
	 * @return The model.
	 */
	protected abstract M createModel();

	/**
	 * Gets entities by name.
	 * 
	 * @return The entities.
	 */
	protected final Map<String, EntityDescriptor<? extends S>> getEntitiesByName() {
		return this.entityByName;
	}

	/**
	 * Gets entities by type.
	 * 
	 * @return The entities.
	 */
	protected final Map<Class<?>, EntityDescriptor<? extends S>> getEntitiesByType() {
		return this.entityByType;
	}

	/**
	 * Gets extensions.
	 * 
	 * @return The extensions.
	 */
	protected final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> getExtensionRef() {
		return this.getBuilderContext().getExtensionRef();
	}

	/**
	 * A cache listener.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class CachedListener
			implements Listener<BuildEntityEvent<S>> {

		/** Default constructor. */
		public CachedListener() {
			// Default constructor
		}

		/** {@inheritDoc} */
		@Override
		public void handleEvent(final BuildEntityEvent<S> event) throws EventException {
			AbstractModelBuilder.LOGGER.debug("Handling build event {}", event);
			final EntityDescriptor<S> entity = event.getEntity();
			AbstractModelBuilder.this.entityByName.put(entity.getName(), entity);
			AbstractModelBuilder.this.entityByType.put(entity.getEntityType(), entity);
		}
	}
}
