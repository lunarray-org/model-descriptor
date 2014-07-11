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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.dictionary.PaginatedDictionary;
import org.lunarray.model.descriptor.dictionary.composite.PaginatedEntityDictionary;
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
 * A composite registry based paginated dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <N>
 *            The bean name type.
 */
public final class CompositePaginatedRegistryDictionary<N>
		implements PaginatedDictionary, RegistryAware<N> {

	/** Validation message. */
	private static final String ENTITY_DESCRIPTOR_NULL = "Entity descriptor may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CompositePaginatedRegistryDictionary.class);
	/** The dictionaries. */
	private transient Map<String, N> dictionaries;
	/** The registry. */
	private transient Registry<N> registry;

	/** Default constructor. */
	public CompositePaginatedRegistryDictionary() {
		// Default constructor.
	}

	/**
	 * Initializes the dictionaries.
	 */
	public void init() {
		Validate.notNull(this.registry, "Registry may not be null.");
		this.dictionaries = new HashMap<String, N>();
		try {
			final Set<N> registryDictionaries = this.registry.lookupAll(PaginatedEntityDictionary.class);
			for (final N dictionary : registryDictionaries) {
				CompositePaginatedRegistryDictionary.LOGGER.debug("Found dictionary {}", dictionary);
				this.dictionaries.put(this.registry.lookup(PaginatedEntityDictionary.class, dictionary).getEntityName(), dictionary);
			}
		} catch (final RegistryException e) {
			CompositePaginatedRegistryDictionary.LOGGER.warn("Unable to get bean.", e);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Cannot be specified.
	@Override
	public <E> Collection<E> lookup(final EntityDescriptor<E> entityDescriptor) throws DictionaryException {
		Validate.notNull(entityDescriptor, CompositePaginatedRegistryDictionary.ENTITY_DESCRIPTOR_NULL);
		final String name = entityDescriptor.getName();
		if (this.dictionaries.containsKey(name)) {
			try {
				CompositePaginatedRegistryDictionary.LOGGER.debug("Looking up all for {}", entityDescriptor);
				return this.registry.lookup(PaginatedEntityDictionary.class, this.dictionaries.get(name)).lookup();
			} catch (final RegistryException e) {
				throw new UnknownEntityException(entityDescriptor, e);
			}
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Cannot be specified.
	@Override
	public <E, K extends Serializable> E lookup(final KeyedEntityDescriptor<E, K> entityDescriptor, final K key) throws DictionaryException {
		Validate.notNull(entityDescriptor, CompositePaginatedRegistryDictionary.ENTITY_DESCRIPTOR_NULL);
		final String name = entityDescriptor.getName();
		if (this.dictionaries.containsKey(name)) {
			Object obj;
			try {
				CompositePaginatedRegistryDictionary.LOGGER.debug("Looking up {} with key {}", entityDescriptor, key);
				obj = this.registry.lookup(PaginatedEntityDictionary.class, this.dictionaries.get(name)).lookup(key);
				return entityDescriptor.getEntityType().cast(obj);
			} catch (final RegistryException e) {
				throw new UnknownEntityException(entityDescriptor, e);
			}
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Cannot be specified.
	@Override
	public <E> List<E> lookupPaginated(final EntityDescriptor<E> entityDescriptor, final int row, final int count)
			throws DictionaryException {
		Validate.notNull(entityDescriptor, CompositePaginatedRegistryDictionary.ENTITY_DESCRIPTOR_NULL);
		Validate.isTrue(CheckUtil.checkPositive(count), "Count must be positive.");
		Validate.isTrue(CheckUtil.checkPositive(row), "Row must be positive.");
		final String name = entityDescriptor.getName();
		if (this.dictionaries.containsKey(name)) {
			try {
				CompositePaginatedRegistryDictionary.LOGGER.debug("Looking up {} from row {}, count {}", entityDescriptor, row, count);
				return this.registry.lookup(PaginatedEntityDictionary.class, this.dictionaries.get(name)).lookup(row, count);
			} catch (final RegistryException e) {
				throw new UnknownEntityException(entityDescriptor, e);
			}
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <E> int lookupTotals(final EntityDescriptor<E> entityDescriptor) throws DictionaryException {
		Validate.notNull(entityDescriptor, CompositePaginatedRegistryDictionary.ENTITY_DESCRIPTOR_NULL);
		final String name = entityDescriptor.getName();
		if (this.dictionaries.containsKey(name)) {
			try {
				CompositePaginatedRegistryDictionary.LOGGER.debug("Counting total for {}", entityDescriptor);
				return this.registry.lookup(PaginatedEntityDictionary.class, this.dictionaries.get(name)).total();
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
