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
package org.lunarray.model.descriptor.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.model.extension.HardExtensionRef;
import org.lunarray.model.descriptor.registry.NamedRegistryExtensionRef;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.RegistryExtensionRef;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility for extension processing.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum ExtensionUtil {
	/** The instance. */
	INSTANCE;

	/** Validation message. */
	private static final String EXTENSION_MAP_NULL = "Extension map may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionUtil.class);

	/**
	 * Load extensions to a map.
	 * 
	 * @param extensions
	 *            The original extension list. May not be null.
	 * @param extensionRefs
	 *            The extension references. May not be null.
	 * @return The map of extensions.
	 */
	public static Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> loadExtensions(
			final Iterable<? extends ExtensionRef<?>> extensionRefs, final Iterable<? extends Extension> extensions) {
		final Map<Class<? extends Extension>, ExtensionRef<?>> result = new HashMap<Class<? extends Extension>, ExtensionRef<?>>();
		ExtensionUtil.updateExtensions(result, extensionRefs, extensions);
		return result;
	}

	/**
	 * Process the registry.
	 * 
	 * @param extensionMap
	 *            The extension map. May not be null.
	 * @param registryRef
	 *            The registry. May not be null.
	 * @param <N>
	 *            The registry name type.
	 */
	@SuppressWarnings("unchecked")
	public static <N> void processRegistry(final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> extensionMap,
			final ExtensionRef<Registry<N>> registryRef) {
		Validate.notNull(extensionMap, ExtensionUtil.EXTENSION_MAP_NULL);
		Validate.notNull(registryRef, "Registry may not be null.");
		try {
			final Registry<N> registry = registryRef.get();
			final Set<N> extensions = registry.lookupAll(Extension.class);
			for (final N name : extensions) {
				final Extension extension = registry.lookup(Extension.class, name);
				final List<Class<?>> extensionInterfaces = ExtensionUtil.getAllInterfaces(extension.getClass());
				for (final Class<?> extensionInt : extensionInterfaces) {
					if (Extension.class.isAssignableFrom(extensionInt) && !Registry.class.isAssignableFrom(extensionInt)) {
						extensionMap.put((Class<? extends Extension>) extensionInt,
								ExtensionUtil.createRegistryRef(registryRef, name, extensionInt));
					}
				}
			}
		} catch (final RegistryException e) {
			ExtensionUtil.LOGGER.warn("Could not get bean.", e);
		}
	}

	/**
	 * Load extensions to a map.
	 * 
	 * @param extensionMap
	 *            An existing map. May not be null.
	 * @param extensions
	 *            The original extension list. May not be null.
	 * @param extensionRefs
	 *            The extension references. May not be null.
	 */
	public static void updateExtensions(final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> extensionMap,
			final Iterable<? extends ExtensionRef<?>> extensionRefs, final Iterable<? extends Extension> extensions) {
		Validate.notNull(extensionMap, ExtensionUtil.EXTENSION_MAP_NULL);
		ExtensionUtil.extractReferences(extensionMap, extensionRefs);
		ExtensionUtil.extractExtensions(extensionMap, extensions);
	}

	/**
	 * Creates a hard reference.
	 * 
	 * @param extension
	 *            The extension.
	 * @return The reference.
	 */
	private static ExtensionRef<Extension> createHardReference(final Extension extension) {
		return new HardExtensionRef<Extension>(extension);
	}

	/**
	 * Create registry reference.
	 * 
	 * @param registry
	 *            The registry.
	 * @param name
	 *            The name.
	 * @param extensionInt
	 *            The extension interface.
	 * @return The reference.
	 * @param <N>
	 *            The name type.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <N> RegistryExtensionRef createRegistryRef(final ExtensionRef<Registry<N>> registry, final N name,
			final Class<?> extensionInt) {
		return new RegistryExtensionRef(new NamedRegistryExtensionRef(extensionInt, null, name), registry);
	}

	/**
	 * Extract extensions.
	 * 
	 * @param extensionMap
	 *            The extensionmap.
	 * @param extensions
	 *            The extensions.
	 */
	@SuppressWarnings("unchecked")
	private static void extractExtensions(final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> extensionMap,
			final Iterable<? extends Extension> extensions) {
		if (!CheckUtil.isNull(extensions)) {
			for (final Extension extension : extensions) {
				final List<Class<?>> extensionInterfaces = ExtensionUtil.getAllInterfaces(extension.getClass());
				final ExtensionRef<?> extensionRef = ExtensionUtil.createHardReference(extension);
				for (final Class<?> extensionInt : extensionInterfaces) {
					if (Extension.class.isAssignableFrom(extensionInt)) {
						extensionMap.put((Class<? extends Extension>) extensionInt, extensionRef);
					}
				}
				if (extension instanceof Registry) {
					ExtensionUtil.processRegistry(extensionMap, (ExtensionRef<Registry<Serializable>>) extensionRef);
				}
			}
		}
	}

	/**
	 * Extract the references.
	 * 
	 * @param extensionMap
	 *            The extension map.
	 * @param extensionRefs
	 *            The extension references.
	 */
	@SuppressWarnings("unchecked")
	private static void extractReferences(final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> extensionMap,
			final Iterable<? extends ExtensionRef<?>> extensionRefs) {
		if (!CheckUtil.isNull(extensionRefs)) {
			for (final ExtensionRef<?> extension : extensionRefs) {
				final List<Class<?>> extensionInterfaces = ExtensionUtil.getAllInterfaces(extension.get().getClass());
				for (final Class<?> extensionInt : extensionInterfaces) {
					if (Extension.class.isAssignableFrom(extensionInt)) {
						extensionMap.put((Class<? extends Extension>) extensionInt, extension);
					}
				}
				if (extension.get() instanceof Registry) {
					ExtensionUtil.processRegistry(extensionMap, (ExtensionRef<Registry<Serializable>>) extension);
				}
			}
		}
	}

	/**
	 * Gets all interfaces.
	 * 
	 * @param extension
	 *            The extension.
	 * @return The interfaces.
	 */
	private static List<Class<?>> getAllInterfaces(final Class<?> extension) {
		final List<Class<?>> result = new LinkedList<Class<?>>();
		ExtensionUtil.getAllInterfaces(extension, result);
		return result;
	}

	/**
	 * Gets all interfaces.
	 * 
	 * @param extension
	 *            The extension.
	 * @param interfaces
	 *            The interfaces.
	 */
	private static void getAllInterfaces(final Class<?> extension, final List<Class<?>> interfaces) {
		for (final Class<?> inter : extension.getInterfaces()) {
			interfaces.add(inter);
		}
		final Class<?> superClass = extension.getSuperclass();
		if (!CheckUtil.isNull(superClass)) {
			ExtensionUtil.getAllInterfaces(superClass, interfaces);
		}
	}
}
