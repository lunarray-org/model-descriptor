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
package org.lunarray.model.descriptor.builder.annotation.base.builders.context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.Bus;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.reference.ReferenceBuilder;
import org.lunarray.model.descriptor.builder.Configuration;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.resolver.ResolverFactory;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.EntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.EntityResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.def.DefaultEntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.def.DefaultOperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.ParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.ParameterResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.def.DefaultParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.def.DefaultPropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.model.extension.impl.ExtensionContainerImpl;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.resource.NamedResource;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;
import org.lunarray.model.descriptor.util.ExtensionUtil;

/**
 * Builder context.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public abstract class AbstractBuilderContext
		implements AnnotationBuilderContext {

	/** Accessor context. */
	private transient ReferenceBuilder<Object, ?> accessorContext;
	/** Attribute resolver strategy. */
	private transient EntityAttributeResolverStrategy attributeEntity;
	/** Attribute resolver strategy. */
	private transient OperationAttributeResolverStrategy attributeOperation;
	/** Attribute resolver strategy. */
	private transient ParameterAttributeResolverStrategy attributeParameter;
	/** Attribute resolver strategy. */
	private transient PropertyAttributeResolverStrategy attributeProperty;
	/** An event bus. */
	private final transient Bus bus;
	/** The configuration. */
	private transient Configuration configurationContext;
	/** Entity name cache. */
	private final transient Map<Class<?>, String> entityNameCache;
	/** A entity resolver. */
	private transient EntityResolverStrategy entityResolverContext;
	/** Extension references. */
	private final transient Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> extensionRef;
	/** A operation resolver. */
	private transient OperationResolverStrategy operationResolverContext;
	/** A operation resolver. */
	private transient ParameterResolverStrategy parameterResolverContext;
	/** Post processors. */
	private final transient List<ModelProcessor<?>> processors;
	/** A property resolver. */
	private transient PropertyResolverStrategy propertyResolverContext;

	/**
	 * Builds the context.
	 * 
	 */
	public AbstractBuilderContext() {
		this.extensionRef = new HashMap<Class<? extends Extension>, ExtensionRef<? extends Extension>>();
		this.processors = new LinkedList<ModelProcessor<?>>();
		this.entityNameCache = new HashMap<Class<?>, String>();
		this.bus = new Bus();
	}

	/** {@inheritDoc} */
	@Override
	public final void addExtension(final Extension... extensions) {
		ExtensionUtil.updateExtensions(this.extensionRef, null, Arrays.asList(extensions));
	}

	/** {@inheritDoc} */
	@Override
	public final void addExtension(final ExtensionRef<?>... extensions) {
		ExtensionUtil.updateExtensions(this.extensionRef, Arrays.asList(extensions), null);
	}

	/** {@inheritDoc} */
	@Override
	public final void addProcessor(final ModelProcessor<?> postProcessor) {
		this.processors.add(postProcessor);
	}

	/** {@inheritDoc} */
	@Override
	public final void attributeEntityResolverStrategy(final EntityAttributeResolverStrategy resolverStrategy) {
		this.attributeEntity = resolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final void attributeOperationResolverStrategy(final OperationAttributeResolverStrategy resolverStrategy) {
		this.attributeOperation = resolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final void attributeParameterResolverStrategy(final ParameterAttributeResolverStrategy resolverStrategy) {
		this.attributeParameter = resolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final void attributePropertyResolverStrategy(final PropertyAttributeResolverStrategy resolverStrategy) {
		this.attributeProperty = resolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final void cacheEntityName(final Class<?> entityType, final String name) {
		this.entityNameCache.put(entityType, name);
	}

	/** {@inheritDoc} */
	@Override
	public final void configuration(final Configuration configuration) {
		this.configurationContext = configuration;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean containsEntityName(final DescribedEntity<?> entityType) {
		return this.entityNameCache.containsKey(entityType.getEntityType());
	}

	/** {@inheritDoc} */
	@Override
	public final void entityResolverStrategy(final EntityResolverStrategy resolverStrategy) {
		this.entityResolverContext = resolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	/* Deduced type. */
	public final <A> ReferenceBuilder<Object, A> getAccessorContext() {
		return (ReferenceBuilder<Object, A>) this.accessorContext;
	}

	/** {@inheritDoc} */
	@Override
	public final Bus getBus() {
		return this.bus;
	}

	/** {@inheritDoc} */
	@Override
	public final Configuration getConfiguration() {
		return this.configurationContext;
	}

	/** {@inheritDoc} */
	@Override
	public final EntityAttributeResolverStrategy getEntityAttributeResolverStrategy() {
		return this.attributeEntity;
	}

	/** {@inheritDoc} */
	@Override
	public final String getEntityName(final DescribedEntity<?> entityType) {
		return this.entityNameCache.get(entityType.getEntityType());
	}

	/** {@inheritDoc} */
	@Override
	public final EntityResolverStrategy getEntityResolverStrategy() {
		return this.entityResolverContext;
	}

	/** {@inheritDoc} */
	@Override
	public final ExtensionContainer getExtensionContainer() {
		return new ExtensionContainerImpl(this.extensionRef);
	}

	/** {@inheritDoc} */
	@Override
	public final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> getExtensionRef() {
		return this.extensionRef;
	}

	/** {@inheritDoc} */
	@Override
	public final OperationAttributeResolverStrategy getOperationAttributeResolverStrategy() {
		return this.attributeOperation;
	}

	/** {@inheritDoc} */
	@Override
	public final OperationResolverStrategy getOperationResolverStrategy() {
		return this.operationResolverContext;
	}

	/** {@inheritDoc} */
	@Override
	public final ParameterAttributeResolverStrategy getParameterAttributeResolverStrategy() {
		return this.attributeParameter;
	}

	/** {@inheritDoc} */
	@Override
	public final ParameterResolverStrategy getParameterResolverStrategy() {
		return this.parameterResolverContext;
	}

	/** {@inheritDoc} */
	@Override
	public final List<ModelProcessor<?>> getPostProcessors() {
		return this.processors;
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyAttributeResolverStrategy getPropertyAttributeResolverStrategy() {
		return this.attributeProperty;
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyResolverStrategy getPropertyResolverStrategy() {
		return this.propertyResolverContext;
	}

	/** {@inheritDoc} */
	@Override
	public final void operationResolverStrategy(final OperationResolverStrategy resolverStrategy) {
		this.operationResolverContext = resolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final void parameterResolverStrategy(final ParameterResolverStrategy resolverStrategy) {
		this.parameterResolverContext = resolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final void propertyResolverStrategy(final PropertyResolverStrategy propertyresolverStrategy) {
		this.propertyResolverContext = propertyresolverStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public final <S> void registeredResources(final Resource<Class<? extends S>> resource) throws ResourceException {
		Validate.notNull(resource, "Resource may not be null.");
		this.createAttributeResolvers(resource);
		this.createNodeResolvers();
		if (CheckUtil.isNull(this.configurationContext)) {
			this.configurationContext = DefaultConfiguration.createDefault();
		}
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	// Can't be more sure.
	public final void setAccessorContext(final ReferenceBuilder<Object, ?> accessorContext) {
		this.accessorContext = accessorContext;
		if (this.extensionRef.containsKey(ObjectFactory.class)) {
			accessorContext.objectFactoryRef((ExtensionRef<ObjectFactory>) this.extensionRef.get(ObjectFactory.class));
		}
	}

	/**
	 * Create attribute resolvers.
	 * 
	 * @param resource
	 *            The resource.
	 * @param <S>
	 *            The super type.
	 */
	private <S> void createAttributeResolvers(final Resource<Class<? extends S>> resource) {
		if (CheckUtil.isNull(this.attributeEntity)) {
			final DefaultEntityAttributeResolverStrategy<S> attributeEntityResolver = ResolverFactory.defaultEntityAttributeResolver();
			if (resource instanceof NamedResource) {
				final NamedResource<Class<? extends S>> namedResource = (NamedResource<Class<? extends S>>) resource;
				attributeEntityResolver.entityNames(namedResource.resourceNames());
			}
			this.attributeEntityResolverStrategy(attributeEntityResolver);
		}
		if (CheckUtil.isNull(this.attributeProperty)) {
			final DefaultPropertyAttributeResolverStrategy attributePropertyResolver = ResolverFactory.defaultPropertyAttributeResolver();
			this.attributePropertyResolverStrategy(attributePropertyResolver);
		}
		if (CheckUtil.isNull(this.attributeOperation)) {
			final DefaultOperationAttributeResolverStrategy attributeOperationResolver = ResolverFactory.defaultOperationAttributeResolver();
			this.attributeOperationResolverStrategy(attributeOperationResolver);
		}
		if (CheckUtil.isNull(this.attributeParameter)) {
			final DefaultParameterAttributeResolverStrategy attributeResolverStrategy = ResolverFactory.defaultParameterAttributeResolver();
			this.attributeParameterResolverStrategy(attributeResolverStrategy);
		}
	}

	/**
	 * Create node resolvers.
	 */
	private void createNodeResolvers() {
		if (CheckUtil.isNull(this.propertyResolverContext)) {
			this.propertyResolverStrategy(ResolverFactory.fieldPropertyResolver());
		}
		if (CheckUtil.isNull(this.entityResolverContext)) {
			this.entityResolverStrategy(ResolverFactory.defaultEntityResolver());
		}
		if (CheckUtil.isNull(this.operationResolverContext)) {
			this.operationResolverStrategy(ResolverFactory.defaultOperationResolver());
		}
		if (CheckUtil.isNull(this.parameterResolverContext)) {
			this.parameterResolverStrategy(ResolverFactory.defaultParameterResolver());
		}
	}
}
