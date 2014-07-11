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
package org.lunarray.model.descriptor.dictionary.composite.simple;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.dictionary.composite.EntityDictionary;
import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;
import org.lunarray.model.descriptor.dictionary.exceptions.UnknownEntityException;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple composite dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <D>
 *            The dictionary type.
 */
public abstract class AbstractCompositeDictionary<D extends EntityDictionary<?, ? extends Serializable>>
		implements Dictionary {

	/** Validation message. */
	private static final String ENTITY_DESCRIPTOR_NULL = "Entity descriptor may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCompositeDictionary.class);
	/** The dictionaries. */
	private final transient Map<String, D> dictionaries;

	/**
	 * Constructs the dictionary.
	 * 
	 * @param dictionaries
	 *            The partial dictionaries. May not be null.
	 */
	public AbstractCompositeDictionary(final List<D> dictionaries) {
		Validate.notNull(dictionaries, "Dictionaries may not be null.");
		this.dictionaries = new HashMap<String, D>();
		for (final D dictionary : dictionaries) {
			this.dictionaries.put(dictionary.getEntityName(), dictionary);
		}
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Cannot be specified.
	@Override
	public final <E> Collection<E> lookup(final EntityDescriptor<E> entityDescriptor) throws DictionaryException {
		Validate.notNull(entityDescriptor, AbstractCompositeDictionary.ENTITY_DESCRIPTOR_NULL);
		final String name = entityDescriptor.getName();
		AbstractCompositeDictionary.LOGGER.debug("Lookup up all for {}", entityDescriptor);
		if (this.dictionaries.containsKey(name)) {
			return (Collection<E>) this.dictionaries.get(name).lookup();
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/** {@inheritDoc} */
	@Override
	public final <E, K extends Serializable> E lookup(final KeyedEntityDescriptor<E, K> entityDescriptor, final K key)
			throws DictionaryException {
		Validate.notNull(entityDescriptor, AbstractCompositeDictionary.ENTITY_DESCRIPTOR_NULL);
		final String name = entityDescriptor.getName();
		AbstractCompositeDictionary.LOGGER.debug("Looking up {} for key {}", entityDescriptor, key);
		if (this.dictionaries.containsKey(name)) {
			@SuppressWarnings("unchecked")
			final EntityDictionary<E, K> dictionary = (EntityDictionary<E, K>) this.getDictionaries().get(name);
			return entityDescriptor.getEntityType().cast(dictionary.lookup(key));
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/**
	 * Gets the value for the dictionaries field.
	 * 
	 * @return The value for the dictionaries field.
	 */
	protected final Map<String, D> getDictionaries() {
		return this.dictionaries;
	}
}
