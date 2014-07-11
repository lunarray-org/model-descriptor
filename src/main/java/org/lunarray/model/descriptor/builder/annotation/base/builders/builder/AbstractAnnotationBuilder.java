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
package org.lunarray.model.descriptor.builder.annotation.base.builders.builder;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.builder.Configuration;
import org.lunarray.model.descriptor.builder.ExtensionReferenceBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.model.AnnotationModelBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.AbstractBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.builders.model.AbstractModel;
import org.lunarray.model.descriptor.builder.annotation.base.listener.model.EntityNameCacheListener;
import org.lunarray.model.descriptor.builder.annotation.base.listener.model.ProcessResourceListener;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.EntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyResolverStrategy;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;

/**
 * 
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The entity super type.
 * @param <M>
 *            The model type.
 * @param <C>
 *            The builder context type.
 * @param <B>
 *            The builder type.
 */

public abstract class AbstractAnnotationBuilder<S, M extends AbstractModel<S>, C extends AnnotationBuilderContext, B extends ExtensionReferenceBuilder<Class<? extends S>, S, M, B>>
		extends AbstractBuilder<C>
		implements ExtensionReferenceBuilder<Class<? extends S>, S, M, B> {

	/** The model builder. */
	private final transient AnnotationModelBuilder<S, M, C> modelBuilder;

	/**
	 * Constructs the annotation builder.
	 * 
	 * @param builderContext
	 *            The builder context. May not be null.
	 */
	protected AbstractAnnotationBuilder(final C builderContext) {
		super(builderContext);
		this.modelBuilder = this.createModelBuilder();
		this.getBuilderContext().getBus().addListener(new EntityNameCacheListener<S, C>());
		this.getBuilderContext().getBus().addListener(new ProcessResourceListener<S, C>());
	}

	/**
	 * Update the attribute resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 * @return The builder.
	 */
	public final B attributeEntityResolver(final EntityAttributeResolverStrategy resolverStrategy) {
		this.getBuilderContext().attributeEntityResolverStrategy(resolverStrategy);
		return this.getBuilder();
	}

	/**
	 * Update the attribute resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 * @return The builder.
	 */
	public final B attributeOperationResolver(final OperationAttributeResolverStrategy resolverStrategy) {
		this.getBuilderContext().attributeOperationResolverStrategy(resolverStrategy);
		return this.getBuilder();
	}

	/**
	 * Update the attribute resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 * @return The builder.
	 */
	public final B attributePropertyResolver(final PropertyAttributeResolverStrategy resolverStrategy) {
		this.getBuilderContext().attributePropertyResolverStrategy(resolverStrategy);
		return this.getBuilder();
	}

	/** {@inheritDoc} */
	@Override
	public final M build() {
		return this.modelBuilder.build();
	}

	/** {@inheritDoc} */
	@Override
	public final B configuration(final Configuration configuration) {
		this.modelBuilder.configuration(configuration);
		return this.getBuilder();
	}

	/** {@inheritDoc} */
	@Override
	public final B extensions(final Extension... extensions) {
		if (!CheckUtil.isNull(extensions)) {
			this.getBuilderContext().addExtension(extensions.clone());
		}
		return this.getBuilder();
	}

	/** {@inheritDoc} */
	@Override
	public final B extensions(final ExtensionRef<?>... extensions) {
		if (!CheckUtil.isNull(extensions)) {
			this.getBuilderContext().addExtension(extensions.clone());
		}
		return this.getBuilder();
	}

	/** {@inheritDoc} */
	@Override
	public final B postProcessors(final ModelProcessor<S>... postProcessors) {
		if (!CheckUtil.isNull(postProcessors)) {
			this.modelBuilder.postProcessors(postProcessors);
		}
		return this.getBuilder();
	}

	/**
	 * Update the property resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 * @return The builder
	 */
	public final B propertyResolver(final PropertyResolverStrategy resolverStrategy) {
		this.getBuilderContext().propertyResolverStrategy(resolverStrategy);
		return this.getBuilder();
	}

	/** {@inheritDoc} */
	@Override
	public final B resources(final Resource<Class<? extends S>> resource) throws ResourceException {
		this.modelBuilder.resource(resource);
		return this.getBuilder();
	}

	/**
	 * Create a model builder.
	 * 
	 * @return The builder.
	 */
	protected abstract AnnotationModelBuilder<S, M, C> createModelBuilder();

	/**
	 * Gets the builder.
	 * 
	 * @return The builder.
	 */
	protected abstract B getBuilder();
}
