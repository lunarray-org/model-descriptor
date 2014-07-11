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
package org.lunarray.model.descriptor.dictionary.composite.registry;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.dictionary.composite.EntityDictionary;
import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;
import org.lunarray.model.descriptor.dictionary.exceptions.UnknownEntityException;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.RegistryAware;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A composite dictionary that uses a registry to populate itself.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <N>
 *            The name type.
 */
public final class CompositeRegistryDictionary<N>
		implements Dictionary, RegistryAware<N> {

	/** Validation message. */
	private static final String ENTITY_DESCRIPTOR_NULL = "Entity descriptor may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CompositeRegistryDictionary.class);
	/** The map of dictionaries. */
	private transient Map<String, N> dictionaries;
	/** The registry. */
	private transient Registry<N> registry;

	/** Default constructor. */
	public CompositeRegistryDictionary() {
		// Default constructor.
	}

	/**
	 * Populates the dictionaries.
	 */
	public void init() {
		Validate.notNull(this.registry, "Registry may not be null.");
		this.dictionaries = new HashMap<String, N>();
		try {
			final Set<N> registryDictionaries = this.registry.lookupAll(EntityDictionary.class);
			for (final N dictionary : registryDictionaries) {
				CompositeRegistryDictionary.LOGGER.debug("Found dictionary: {}", dictionary);
				this.dictionaries.put(this.registry.lookup(EntityDictionary.class, dictionary).getEntityName(), dictionary);
			}
		} catch (final RegistryException e) {
			CompositeRegistryDictionary.LOGGER.warn("Unable to get bean.", e);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Cannot be specified.
	@Override
	public <E> Collection<E> lookup(final EntityDescriptor<E> entityDescriptor) throws DictionaryException {
		Validate.notNull(entityDescriptor, CompositeRegistryDictionary.ENTITY_DESCRIPTOR_NULL);
		CompositeRegistryDictionary.LOGGER.debug("Lookup up all for {}", entityDescriptor);
		final String name = entityDescriptor.getName();
		if (this.dictionaries.containsKey(name)) {
			try {
				return this.registry.lookup(EntityDictionary.class, this.dictionaries.get(name)).lookup();
			} catch (final RegistryException e) {
				throw new UnknownEntityException(entityDescriptor, e);
			}
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Cannot be further specified.
	@Override
	public <E, K extends Serializable> E lookup(final KeyedEntityDescriptor<E, K> entityDescriptor, final K key) throws DictionaryException {
		Validate.notNull(entityDescriptor, CompositeRegistryDictionary.ENTITY_DESCRIPTOR_NULL);
		CompositeRegistryDictionary.LOGGER.debug("Lookup up all for {} with key {}", entityDescriptor, key);
		final String name = entityDescriptor.getName();
		if (this.dictionaries.containsKey(name)) {
			Object obj;
			try {
				obj = this.registry.lookup(EntityDictionary.class, this.dictionaries.get(name)).lookup(key);
				return entityDescriptor.getEntityType().cast(obj);
			} catch (final RegistryException e) {
				throw new UnknownEntityException(entityDescriptor, e);
			}
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setRegistry(final Registry<N> registry) {
		this.registry = registry;
		this.init();
	}
}
