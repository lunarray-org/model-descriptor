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
package org.lunarray.model.descriptor.builder.annotation.base.build.context;

import java.util.List;
import java.util.Map;

import org.lunarray.common.event.Bus;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.reference.ReferenceBuilder;
import org.lunarray.model.descriptor.builder.Configuration;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.EntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.EntityResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.ParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.ParameterResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyResolverStrategy;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;

/**
 * The annotation builder context.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface AnnotationBuilderContext {

	/**
	 * Registers extensions.
	 * 
	 * @param extensions
	 *            The extensions.
	 */
	void addExtension(final Extension... extensions);

	/**
	 * Registers extensions.
	 * 
	 * @param extensions
	 *            The extensions.
	 */
	void addExtension(final ExtensionRef<?>... extensions);

	/**
	 * Adds a processor.
	 * 
	 * @param postProcessor
	 *            The postprocessor.
	 */
	void addProcessor(final ModelProcessor<?> postProcessor);

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 */
	void attributeEntityResolverStrategy(final EntityAttributeResolverStrategy resolverStrategy);

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 */
	void attributeOperationResolverStrategy(final OperationAttributeResolverStrategy resolverStrategy);

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 */
	void attributeParameterResolverStrategy(final ParameterAttributeResolverStrategy resolverStrategy);

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 */
	void attributePropertyResolverStrategy(final PropertyAttributeResolverStrategy resolverStrategy);

	/**
	 * Cache the entity name.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @param name
	 *            The name.
	 */
	void cacheEntityName(final Class<?> entityType, final String name);

	/**
	 * Sets the configuration.
	 * 
	 * @param configuration
	 *            The configuration.
	 */
	void configuration(Configuration configuration);

	/**
	 * Tests if the entity is named.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @return True if and only if it is named.
	 */
	boolean containsEntityName(final DescribedEntity<?> entityType);

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 */
	void entityResolverStrategy(final EntityResolverStrategy resolverStrategy);

	/**
	 * Gets the accessor context.
	 * 
	 * @param <A>
	 *            The accessor to type.
	 * @return The accessor context.
	 */
	<A> ReferenceBuilder<Object, A> getAccessorContext();

	/**
	 * Gets the value for the bus field.
	 * 
	 * @return The value for the bus field.
	 */
	Bus getBus();

	/**
	 * Gets the configuration.
	 * 
	 * @return The configuration.
	 */
	Configuration getConfiguration();

	/**
	 * Gets the attribute resolver strategy.
	 * 
	 * @return The attribute resolver strategy.
	 */
	EntityAttributeResolverStrategy getEntityAttributeResolverStrategy();

	/**
	 * Gets the entity name.
	 * 
	 * @param entityType
	 *            The entity type.
	 * @return The entity name.
	 */
	String getEntityName(final DescribedEntity<?> entityType);

	/**
	 * Gets the entity resolver strategy.
	 * 
	 * @return The entity resolver strategy.
	 */
	EntityResolverStrategy getEntityResolverStrategy();

	/**
	 * Gets the extension container.
	 * 
	 * @return The extension container.
	 */
	ExtensionContainer getExtensionContainer();

	/**
	 * Gets the extensions references.
	 * 
	 * @return The extension references.
	 */
	Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> getExtensionRef();

	/**
	 * Gets the attribute resolver strategy.
	 * 
	 * @return The attribute resolver strategy.
	 */
	OperationAttributeResolverStrategy getOperationAttributeResolverStrategy();

	/**
	 * Gets the operation resolver strategy.
	 * 
	 * @return The operation resolver strategy.
	 */
	OperationResolverStrategy getOperationResolverStrategy();

	/**
	 * Gets the attribute resolver strategy.
	 * 
	 * @return The attribute resolver strategy.
	 */
	ParameterAttributeResolverStrategy getParameterAttributeResolverStrategy();

	/**
	 * Gets the resolver strategy.
	 * 
	 * @return The resolver strategy.
	 */
	ParameterResolverStrategy getParameterResolverStrategy();

	/**
	 * Gets the post processors.
	 * 
	 * @return The post processors.
	 */
	List<ModelProcessor<?>> getPostProcessors();

	/**
	 * Gets the attribute resolver strategy.
	 * 
	 * @return The attribute resolver strategy.
	 */
	PropertyAttributeResolverStrategy getPropertyAttributeResolverStrategy();

	/**
	 * Gets the attribute resolver strategy.
	 * 
	 * @return The attribute resolver strategy.
	 */
	PropertyResolverStrategy getPropertyResolverStrategy();

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 */
	void operationResolverStrategy(final OperationResolverStrategy resolverStrategy);

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param resolverStrategy
	 *            The resolver strategy.
	 */
	void parameterResolverStrategy(final ParameterResolverStrategy resolverStrategy);

	/**
	 * Modifies the resolver strategy.
	 * 
	 * @param propertyresolverStrategy
	 *            The resolver strategy.
	 */
	void propertyResolverStrategy(final PropertyResolverStrategy propertyresolverStrategy);

	/**
	 * Registers a resource.
	 * 
	 * @param <S>
	 *            The entity super type.
	 * @param resource
	 *            The resource.
	 * @throws ResourceException
	 *             Thrown if the resources could not be resolved.
	 */
	<S> void registeredResources(final Resource<Class<? extends S>> resource) throws ResourceException;

	/**
	 * Sets the accessor context.
	 * 
	 * @param accessorContext
	 *            The accessor context.
	 */
	void setAccessorContext(final ReferenceBuilder<Object, ?> accessorContext);
}
