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
package org.lunarray.model.descriptor.dictionary.enumeration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A dictionary for enums.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class EnumDictionary
		implements Dictionary {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EnumDictionary.class);
	/** The delegate dictionary for non-enums. */
	private final transient Dictionary delegate;

	/**
	 * Constructs the dictionary.
	 * 
	 * @param delegate
	 *            The delegate.
	 */
	public EnumDictionary(final Dictionary delegate) {
		this.delegate = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public <E> Collection<E> lookup(final EntityDescriptor<E> entityDescriptor) throws DictionaryException {
		final Class<E> entityType = entityDescriptor.getEntityType();
		Collection<E> result = null;
		if (Enum.class.isAssignableFrom(entityType)) {
			EnumDictionary.LOGGER.debug("Looking up all for {}", entityDescriptor);
			final E[] values = this.getValues(entityType);
			if (!CheckUtil.isNull(values)) {
				final List<E> valueList = new ArrayList<E>(values.length);
				for (final E t : values) {
					valueList.add(t);
				}
				result = valueList;
			}
		} else {
			if (!CheckUtil.isNull(this.delegate)) {
				result = this.delegate.lookup(entityDescriptor);
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public <E, K extends Serializable> E lookup(final KeyedEntityDescriptor<E, K> entityDescriptor, final K identifier)
			throws DictionaryException {
		Validate.notNull(entityDescriptor, "Descriptor may not be null.");
		final Class<E> entityType = entityDescriptor.getEntityType();
		E result = null;
		if (Enum.class.isAssignableFrom(entityType)) {
			EnumDictionary.LOGGER.debug("Lookup up {} for key {}", entityDescriptor, identifier);
			final E[] values = this.getValues(entityType);
			if (!CheckUtil.isNull(values)) {
				Validate.notNull(identifier, "Identifier may not be null.");
				result = this.getEnumValue(entityDescriptor, identifier, values);
			}
		} else {
			if (!CheckUtil.isNull(this.delegate)) {
				result = this.delegate.lookup(entityDescriptor, identifier);
			}
		}
		return result;
	}

	/**
	 * Creates an exception.
	 * 
	 * @param cause
	 *            The original cause.
	 * @param entityType
	 *            The entity type.
	 * @return An exception.
	 */
	private DictionaryException createException(final Exception cause, final Class<?> entityType) {
		return new DictionaryException(String.format("Could not get values for '%s'.", entityType), cause);
	}

	/**
	 * Gets the proper enum value.
	 * 
	 * @param <K>
	 *            The enum key.
	 * @param <E>
	 *            The enum type.
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @param identifier
	 *            The enum identifier.
	 * @param values
	 *            The enum values.
	 * @return The enum value matching the identifier.
	 * @throws DictionaryException
	 *             Thrown if the values could not be accessed.
	 */
	private <K extends Serializable, E> E getEnumValue(final KeyedEntityDescriptor<E, K> entityDescriptor, final K identifier,
			final E[] values) throws DictionaryException {
		E value = null;
		for (final E type : values) {
			try {
				if (entityDescriptor.getKeyProperty().getValue(type).equals(identifier)) {
					value = type;
				}
			} catch (final ValueAccessException e) {
				throw new DictionaryException(String.format("Could not get value for entity '%s'.", entityDescriptor.getName()), e);
			}
		}
		return value;
	}

	/**
	 * Gets the enum values.
	 * 
	 * @param <E>
	 *            The enum type.
	 * @param entityType
	 *            The entity type.
	 * @return An erray of enum values.
	 * @throws DictionaryException
	 *             Thrown if the values could not be accessed.
	 */
	@SuppressWarnings("unchecked")
	/* Deduced value, we know it's an enum. */
	private <E> E[] getValues(final Class<E> entityType) throws DictionaryException {
		Method method;
		try {
			method = entityType.getMethod("values");
		} catch (final NoSuchMethodException e) {
			throw this.createException(e, entityType);
		}
		try {
			return (E[]) method.invoke(null);
		} catch (final IllegalArgumentException e) {
			throw this.createException(e, entityType);
		} catch (final IllegalAccessException e) {
			throw this.createException(e, entityType);
		} catch (final InvocationTargetException e) {
			throw this.createException(e, entityType);
		}
	}
}
