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

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.dictionary.PaginatedDictionary;
import org.lunarray.model.descriptor.dictionary.composite.PaginatedEntityDictionary;
import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;
import org.lunarray.model.descriptor.dictionary.exceptions.UnknownEntityException;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple composite paginated dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class CompositePaginatedDictionary
		extends AbstractCompositeDictionary<PaginatedEntityDictionary<?, ?>>
		implements PaginatedDictionary {

	/** Validation message. */
	private static final String ENTITY_DESCRIPTOR_NULL = "Entity descriptor may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CompositePaginatedDictionary.class);

	/**
	 * Constructs the composite dictionary.
	 * 
	 * @param dictionaries
	 *            The dictionaries. May not be null.
	 */
	public CompositePaginatedDictionary(final List<PaginatedEntityDictionary<?, ?>> dictionaries) {
		super(dictionaries);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// Cannot be specified.
	@Override
	public <E> List<E> lookupPaginated(final EntityDescriptor<E> entityDescriptor, final int row, final int count)
			throws DictionaryException {
		CompositePaginatedDictionary.LOGGER.debug("Paginated lookup for entity descriptor {} from row {}, count {}", entityDescriptor, row,
				count);
		Validate.notNull(entityDescriptor, CompositePaginatedDictionary.ENTITY_DESCRIPTOR_NULL);
		Validate.isTrue(CheckUtil.checkPositive(count), "Count must be positive.");
		Validate.isTrue(CheckUtil.checkPositive(row), "Row must be positive.");
		final String name = entityDescriptor.getName();
		final Map<String, PaginatedEntityDictionary<?, ?>> dictionaries = this.getDictionaries();
		if (dictionaries.containsKey(name)) {
			return (List<E>) dictionaries.get(name).lookup(row, count);
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}

	/** {@inheritDoc} */
	@Override
	public <E> int lookupTotals(final EntityDescriptor<E> entityDescriptor) throws DictionaryException {
		CompositePaginatedDictionary.LOGGER.debug("Total lookup for entity descriptor {}", entityDescriptor);
		Validate.notNull(entityDescriptor, CompositePaginatedDictionary.ENTITY_DESCRIPTOR_NULL);
		final String name = entityDescriptor.getName();
		final Map<String, PaginatedEntityDictionary<?, ?>> dictionaries = this.getDictionaries();
		if (dictionaries.containsKey(name)) {
			return dictionaries.get(name).total();
		} else {
			throw new UnknownEntityException(entityDescriptor);
		}
	}
}
