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
package org.lunarray.model.descriptor.creational.impl;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.creational.Factory;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.registry.Registry;

/**
 * Creates creational strategies.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum CreationalStrategyFactory {
	/** The instance. */
	INSTANCE;

	/**
	 * Create a constructor strategy.
	 * 
	 * @param constructor
	 *            The constructor.
	 * @return The strategy.
	 * @param <E>
	 *            The entity type.
	 * @param <F>
	 *            The impl type.
	 */
	public static <E, F extends E> CreationalStrategy<E> createConstructorStrategy(final Constructor<F> constructor) {
		return new ConstructorStrategy<E, F>(constructor);
	}

	/**
	 * The factory factory method strategy.
	 * 
	 * @param factory
	 *            The factory.
	 * @param factoryMethod
	 *            The factory factory method.
	 * @param target
	 *            The impl.
	 * @return The strategy.
	 * @param <E>
	 *            The entity type.
	 * @param <F>
	 *            The impl type.
	 */
	public static <E, F extends Factory> CreationalStrategy<E> createFactoryFactoryMethodStrategy(final CreationalStrategy<F> factory,
			final Method factoryMethod, final Class<E> target) {
		return new FactoryInstanceStrategy<E, F>(factory, factoryMethod, target);
	}

	/**
	 * Create a factory method strategy.
	 * 
	 * @param factoryMethod
	 *            The factory method.
	 * @param type
	 *            The type.
	 * @return The strategy.
	 * @param <E>
	 *            The entity type.
	 * @param <F>
	 *            The impl type.
	 */
	public static <E, F extends E> CreationalStrategy<E> createFactoryMethodStrategy(final Method factoryMethod, final Class<F> type) {
		return new FactoryMethodStrategy<E, F>(factoryMethod, type);
	}

	/**
	 * Create object factory strategy.
	 * 
	 * @param objectFactory
	 *            The object factory.
	 * @param type
	 *            The type.
	 * @return The strategy.
	 * @param <E>
	 *            The entity type.
	 * @param <F>
	 *            The impl type.
	 */
	public static <E, F extends E> CreationalStrategy<E> createObjectFactoryInstanceStrategy(
			final ExtensionRef<ObjectFactory> objectFactory, final Class<F> type) {
		return new ObjectFactoryInstanceStrategy<E>(objectFactory, type);
	}

	/**
	 * Create registry factory instance strategy.
	 * 
	 * @param target
	 *            The target type.
	 * @param registry
	 *            The registry reference.
	 * @param key
	 *            The registry key.
	 * @return The strategy.
	 * @param <E>
	 *            The entity type.
	 * @param <N>
	 *            The registry key type.
	 */
	public static <E, N extends Serializable> CreationalStrategy<E> createRegistryFactoryInstanceStrategy(final Class<E> target,
			final ExtensionRef<Registry<N>> registry, final N key) {
		return new RegistryCreationalStrategy<E, N>(target, registry, key);
	}
}
