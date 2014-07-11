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
package org.lunarray.model.descriptor.builder.annotation.base.listener.entity;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.EventException;
import org.lunarray.common.generics.GenericsUtil;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;
import org.lunarray.model.descriptor.converter.ConverterTool;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.creational.Factory;
import org.lunarray.model.descriptor.creational.annotations.FactoryReference;
import org.lunarray.model.descriptor.creational.annotations.Impl;
import org.lunarray.model.descriptor.creational.impl.CreationalStrategyFactory;
import org.lunarray.model.descriptor.creational.util.CreationalScanUtil;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that processes operations.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder context type.
 * @param <F>
 *            The impl type.
 * @param <N>
 *            The registry key type.
 */
public final class ProcessCreational<E, K extends Serializable, B extends AnnotationBuilderContext, F extends E, N extends Serializable>
		extends AbstractUpdateEntityTypeListener<E, K, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessCreational.class);

	/** Default constructor. */
	public ProcessCreational() {
		super();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(final UpdatedEntityTypeEvent<E, K, B> event) throws EventException {
		ProcessCreational.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		final ProcessingContext ctx = new ProcessingContext();
		ctx.setEntity(event.getEntity());
		ctx.setType(ctx.getEntity().getEntityType());
		ctx.setConstructor(CreationalScanUtil.findConstructor(ctx.getType()));
		ctx.setBuilder(event.getBuilder());

		final AnnotationEntityDescriptorBuilder<E, ?, B> builder = ctx.getBuilder();
		final ExtensionContainer container = builder.getBuilderContext().getExtensionContainer();

		ctx.setConverter(container.getExtension(ConverterTool.class));
		final ExtensionRef<ObjectFactory> factory = container.getExtensionRef(ObjectFactory.class);
		ctx.setFactory(factory);
		final ExtensionRef<Registry<N>> registry = container.getExtensionRef(Registry.class);
		ctx.setRegistry(registry);

		final Class<E> type = ctx.getType();
		if (type.isAnnotationPresent(Impl.class)) {
			final Impl implAnn = type.getAnnotation(Impl.class);
			final Class<?> tentativeImpl = implAnn.value();
			if (type.isAssignableFrom(tentativeImpl)) {
				ctx.setImpl((Class<F>) tentativeImpl);
				ctx.setImplConstructor(CreationalScanUtil.findConstructor(ctx.getImpl()));
			}
		}
		if (!CheckUtil.isNull(registry)) {
			final Registry<N> registryInstance = registry.get();
			final Type registryTypeTmp = GenericsUtil.getEntityGenericType(registryInstance.getClass(), 0, Registry.class);
			if (registryTypeTmp instanceof Class) {
				ctx.setRegistryType((Class<N>) registryTypeTmp);
			}
		}
		this.registerFactoryMethods(ctx);
		if (!this.factories(ctx)) {
			this.handleConstructors(ctx);
		}
	}

	/**
	 * Processing factories.
	 * 
	 * @param ctx
	 *            The processing context.
	 * @return True if the a factory was added, false if not.
	 */
	@SuppressWarnings("unchecked")
	private boolean factories(final ProcessingContext ctx) {
		boolean factories = false;
		for (final FactoryReference reference : ctx.getEntity().getAnnotation(FactoryReference.class)) {
			ctx.setFactoryType(reference.value());
			final String keyTmp = reference.key();
			final ConverterTool converter = ctx.getConverter();
			final Class<N> registryType = ctx.getRegistryType();

			if (!StringUtil.isEmptyString(keyTmp) && !CheckUtil.isNull(registryType)
					&& (String.class.equals(registryType) || !CheckUtil.isNull(converter))) {
				try {
					N result;
					if (CheckUtil.isNull(converter)) {
						result = (N) keyTmp;
					} else {
						result = converter.convertToInstance(registryType, keyTmp);
					}
					ctx.setKey(result);
				} catch (final ConverterException e) {
					ProcessCreational.LOGGER.warn("Could not convert.", e);
				}
			}
			this.factoryMethods(ctx, reference);
			factories = true;
		}
		return factories;
	}

	/**
	 * Factory instance methods.
	 * 
	 * @param reference
	 *            The reference.
	 * @param ctx
	 *            The processing context.
	 * @param <G>
	 *            The factory type.
	 */
	private <G extends Factory> void factoryInstanceMethods(final ProcessingContext ctx, final FactoryReference reference) {
		final AnnotationEntityDescriptorBuilder<E, ?, B> builder = ctx.getBuilder();
		@SuppressWarnings("unchecked")
		final Class<G> factoryType = (Class<G>) ctx.getFactoryType();
		final Class<E> type = ctx.getType();
		final ExtensionRef<ObjectFactory> factory = ctx.getFactory();
		final N key = ctx.getKey();
		final ExtensionRef<Registry<N>> registry = ctx.getRegistry();

		// Factory methods for instances
		for (final Method factoryMethod : CreationalScanUtil.scanForFactoryMethod(factoryType, type, false)) {
			// Object factory variant.
			if (!CheckUtil.isNull(reference) && !CheckUtil.isNull(factory)) {
				final CreationalStrategy<G> factoryStrategy = CreationalStrategyFactory.createObjectFactoryInstanceStrategy(factory,
						factoryType);
				final CreationalStrategy<E> strategy = CreationalStrategyFactory.createFactoryFactoryMethodStrategy(factoryStrategy,
						factoryMethod, type);
				builder.registerCreationalStrategy(strategy);
			}
			// Registry variant.
			if ((!CheckUtil.isNull(reference) || !CheckUtil.isNull(key)) && !CheckUtil.isNull(registry)) {
				final CreationalStrategy<G> factoryStrategy = CreationalStrategyFactory.createRegistryFactoryInstanceStrategy(factoryType,
						registry, key);
				final CreationalStrategy<E> strategy = CreationalStrategyFactory.createFactoryFactoryMethodStrategy(factoryStrategy,
						factoryMethod, type);
				builder.registerCreationalStrategy(strategy);
			}
			// Constructor variant.
			final Constructor<G> constructor = CreationalScanUtil.findConstructor(factoryType);
			if (!CheckUtil.isNull(constructor)) {
				final CreationalStrategy<G> factoryStrategy = CreationalStrategyFactory.createConstructorStrategy(constructor);
				final CreationalStrategy<E> strategy = CreationalStrategyFactory.createFactoryFactoryMethodStrategy(factoryStrategy,
						factoryMethod, type);
				builder.registerCreationalStrategy(strategy);
			}
			// Factory factory methods.
			for (final Method factoryFactoryMethod : CreationalScanUtil.scanForFactoryMethod(factoryType, factoryType, true)) {
				final CreationalStrategy<G> factoryStrategy = CreationalStrategyFactory.createFactoryMethodStrategy(factoryFactoryMethod,
						factoryType);
				final CreationalStrategy<E> strategy = CreationalStrategyFactory.createFactoryFactoryMethodStrategy(factoryStrategy,
						factoryMethod, type);
				builder.registerCreationalStrategy(strategy);
			}
		}
	}

	/**
	 * Factory methods.
	 * 
	 * @param reference
	 *            The reference.
	 * @param ctx
	 *            The processing context.
	 */
	private void factoryMethods(final ProcessingContext ctx, final FactoryReference reference) {
		final AnnotationEntityDescriptorBuilder<E, ?, B> builder = ctx.getBuilder();
		final Class<? extends Factory> factoryType = ctx.getFactoryType();
		final Class<E> type = ctx.getType();
		ctx.getImpl();
		ctx.getKey();

		// Factory factory methods.
		for (final Method factoryMethod : CreationalScanUtil.scanForFactoryMethod(factoryType, type, true)) {
			final CreationalStrategy<E> creationalStrategy = CreationalStrategyFactory.createFactoryMethodStrategy(factoryMethod, type);
			builder.registerCreationalStrategy(creationalStrategy);
		}
		this.factoryInstanceMethods(ctx, reference);
	}

	/**
	 * Handle constructors.
	 * 
	 * @param ctx
	 *            The processing context.
	 */
	@SuppressWarnings("unchecked")
	private void handleConstructors(final ProcessingContext ctx) {
		final AnnotationEntityDescriptorBuilder<E, ?, B> builder = ctx.getBuilder();
		final Constructor<E> constructor = ctx.getConstructor();
		final ExtensionRef<ObjectFactory> factory = ctx.getFactory();
		final Class<F> impl = ctx.getImpl();
		final Constructor<? extends E> implConstructor = ctx.getImplConstructor();
		final Class<E> type = ctx.getType();

		if (!CheckUtil.isNull(factory)) {
			if (CheckUtil.isNull(impl)) {
				final CreationalStrategy<E> strategy = CreationalStrategyFactory.createObjectFactoryInstanceStrategy(factory, type);
				builder.registerCreationalStrategy(strategy);
			} else {
				final CreationalStrategy<E> strategyImpl = (CreationalStrategy<E>) CreationalStrategyFactory
						.createObjectFactoryInstanceStrategy(factory, impl);
				builder.registerCreationalStrategy(strategyImpl);
			}
		}
		if (!CheckUtil.isNull(constructor)) {
			final CreationalStrategy<E> strategy = CreationalStrategyFactory.createConstructorStrategy(constructor);
			builder.registerCreationalStrategy(strategy);
		}
		if (!CheckUtil.isNull(implConstructor)) {
			final CreationalStrategy<E> strategy = (CreationalStrategy<E>) CreationalStrategyFactory
					.createConstructorStrategy(implConstructor);
			builder.registerCreationalStrategy(strategy);
		}
	}

	/**
	 * Register factory methods.
	 * 
	 * @param ctx
	 *            The processing context.
	 */
	private void registerFactoryMethods(final ProcessingContext ctx) {
		final AnnotationEntityDescriptorBuilder<E, ?, B> builder = ctx.getBuilder();
		final Class<F> impl = ctx.getImpl();
		final Class<E> type = ctx.getType();

		// Factory methods on entity.
		for (final Method factoryMethods : CreationalScanUtil.scanForFactoryMethod(type, type, true)) {
			final CreationalStrategy<E> creationalStrategy = CreationalStrategyFactory.createFactoryMethodStrategy(factoryMethods, type);
			builder.registerCreationalStrategy(creationalStrategy);
		}
		// Factory methods on impl referenc.
		if (!CheckUtil.isNull(impl)) {
			for (final Method factoryMethods : CreationalScanUtil.scanForFactoryMethod(impl, type, true)) {
				final CreationalStrategy<E> creationalStrategy = CreationalStrategyFactory
						.createFactoryMethodStrategy(factoryMethods, type);
				builder.registerCreationalStrategy(creationalStrategy);
			}
		}
	}

	/**
	 * A processing context.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	private class ProcessingContext {
		/** The builder. */
		private AnnotationEntityDescriptorBuilder<E, ?, B> builder;
		/** The constructor. */
		private Constructor<E> constructor;
		/** The converter. */
		private ConverterTool converter;
		/** The entity. */
		private DescribedEntity<E> entity;
		/** The factory. */
		private ExtensionRef<ObjectFactory> factory;
		/** The factory type. */
		private Class<? extends Factory> factoryType;
		/** The impl type. */
		private Class<F> impl;
		/** The impl constructor. */
		private Constructor<? extends E> implConstructor;
		/** The key. */
		private N key;
		/** The registry. */
		private ExtensionRef<Registry<N>> registry;
		/** The registry type. */
		private Class<N> registryType;
		/** The entity type. */
		private Class<E> type;

		/**
		 * Default constructor.
		 */
		public ProcessingContext() {
			// Default constructor.
		}

		/**
		 * Gets the value for the builder field.
		 * 
		 * @return The value for the builder field.
		 */
		public AnnotationEntityDescriptorBuilder<E, ?, B> getBuilder() {
			return this.builder;
		}

		/**
		 * Gets the value for the constructor field.
		 * 
		 * @return The value for the constructor field.
		 */
		public Constructor<E> getConstructor() {
			return this.constructor;
		}

		/**
		 * Gets the value for the converter field.
		 * 
		 * @return The value for the converter field.
		 */
		public ConverterTool getConverter() {
			return this.converter;
		}

		/**
		 * Gets the value for the entity field.
		 * 
		 * @return The value for the entity field.
		 */
		public DescribedEntity<E> getEntity() {
			return this.entity;
		}

		/**
		 * Gets the value for the factory field.
		 * 
		 * @return The value for the factory field.
		 */
		public ExtensionRef<ObjectFactory> getFactory() {
			return this.factory;
		}

		/**
		 * Gets the value for the factoryType field.
		 * 
		 * @return The value for the factoryType field.
		 */
		public Class<? extends Factory> getFactoryType() {
			return this.factoryType;
		}

		/**
		 * Gets the value for the impl field.
		 * 
		 * @return The value for the impl field.
		 */
		public Class<F> getImpl() {
			return this.impl;
		}

		/**
		 * Gets the value for the implConstructor field.
		 * 
		 * @return The value for the implConstructor field.
		 */
		public Constructor<? extends E> getImplConstructor() {
			return this.implConstructor;
		}

		/**
		 * Gets the value for the key field.
		 * 
		 * @return The value for the key field.
		 */
		public N getKey() {
			return this.key;
		}

		/**
		 * Gets the value for the registry field.
		 * 
		 * @return The value for the registry field.
		 */
		public ExtensionRef<Registry<N>> getRegistry() {
			return this.registry;
		}

		/**
		 * Gets the value for the registryType field.
		 * 
		 * @return The value for the registryType field.
		 */
		public Class<N> getRegistryType() {
			return this.registryType;
		}

		/**
		 * Gets the value for the type field.
		 * 
		 * @return The value for the type field.
		 */
		public Class<E> getType() {
			return this.type;
		}

		/**
		 * Sets a new value for the builder field.
		 * 
		 * @param builder
		 *            The new value for the builder field.
		 */
		public void setBuilder(final AnnotationEntityDescriptorBuilder<E, ?, B> builder) {
			this.builder = builder;
		}

		/**
		 * Sets a new value for the constructor field.
		 * 
		 * @param constructor
		 *            The new value for the constructor field.
		 */
		public void setConstructor(final Constructor<E> constructor) {
			this.constructor = constructor;
		}

		/**
		 * Sets a new value for the converter field.
		 * 
		 * @param converter
		 *            The new value for the converter field.
		 */
		public void setConverter(final ConverterTool converter) {
			this.converter = converter;
		}

		/**
		 * Sets a new value for the entity field.
		 * 
		 * @param entity
		 *            The new value for the entity field.
		 */
		public void setEntity(final DescribedEntity<E> entity) {
			this.entity = entity;
		}

		/**
		 * Sets a new value for the factory field.
		 * 
		 * @param factory
		 *            The new value for the factory field.
		 */
		public void setFactory(final ExtensionRef<ObjectFactory> factory) {
			this.factory = factory;
		}

		/**
		 * Sets a new value for the factoryType field.
		 * 
		 * @param factoryType
		 *            The new value for the factoryType field.
		 */
		public void setFactoryType(final Class<? extends Factory> factoryType) {
			this.factoryType = factoryType;
		}

		/**
		 * Sets a new value for the impl field.
		 * 
		 * @param impl
		 *            The new value for the impl field.
		 */
		public void setImpl(final Class<F> impl) {
			this.impl = impl;
		}

		/**
		 * Sets a new value for the implConstructor field.
		 * 
		 * @param implConstructor
		 *            The new value for the implConstructor field.
		 */
		public void setImplConstructor(final Constructor<? extends E> implConstructor) {
			this.implConstructor = implConstructor;
		}

		/**
		 * Sets a new value for the key field.
		 * 
		 * @param key
		 *            The new value for the key field.
		 */
		public void setKey(final N key) {
			this.key = key;
		}

		/**
		 * Sets a new value for the registry field.
		 * 
		 * @param registry
		 *            The new value for the registry field.
		 */
		public void setRegistry(final ExtensionRef<Registry<N>> registry) {
			this.registry = registry;
		}

		/**
		 * Sets a new value for the registryType field.
		 * 
		 * @param registryType
		 *            The new value for the registryType field.
		 */
		public void setRegistryType(final Class<N> registryType) {
			this.registryType = registryType;
		}

		/**
		 * Sets a new value for the type field.
		 * 
		 * @param type
		 *            The new value for the type field.
		 */
		public void setType(final Class<E> type) {
			this.type = type;
		}
	}
}
