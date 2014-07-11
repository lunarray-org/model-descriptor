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
package org.lunarray.model.descriptor.converter.def;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.common.generics.GenericsUtil;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.ConverterTool;
import org.lunarray.model.descriptor.converter.def.converters.AtomicBooleanConverter;
import org.lunarray.model.descriptor.converter.def.converters.AtomicIntegerConverter;
import org.lunarray.model.descriptor.converter.def.converters.AtomicLongConverter;
import org.lunarray.model.descriptor.converter.def.converters.BigDecimalConverter;
import org.lunarray.model.descriptor.converter.def.converters.BigIntegerConverter;
import org.lunarray.model.descriptor.converter.def.converters.BooleanConverter;
import org.lunarray.model.descriptor.converter.def.converters.ByteConverter;
import org.lunarray.model.descriptor.converter.def.converters.CalendarConverter;
import org.lunarray.model.descriptor.converter.def.converters.CharacterConverter;
import org.lunarray.model.descriptor.converter.def.converters.ClazzConverter;
import org.lunarray.model.descriptor.converter.def.converters.CompositeNameConverter;
import org.lunarray.model.descriptor.converter.def.converters.CurrencyConverter;
import org.lunarray.model.descriptor.converter.def.converters.DOMConverter;
import org.lunarray.model.descriptor.converter.def.converters.DateConverter;
import org.lunarray.model.descriptor.converter.def.converters.DoubleConverter;
import org.lunarray.model.descriptor.converter.def.converters.FloatConverter;
import org.lunarray.model.descriptor.converter.def.converters.IdempotentConverter;
import org.lunarray.model.descriptor.converter.def.converters.InetAddressConverter;
import org.lunarray.model.descriptor.converter.def.converters.InetSocketAddressConverter;
import org.lunarray.model.descriptor.converter.def.converters.IntegerConverter;
import org.lunarray.model.descriptor.converter.def.converters.LdapNameConverter;
import org.lunarray.model.descriptor.converter.def.converters.LinkRefConverter;
import org.lunarray.model.descriptor.converter.def.converters.LongConverter;
import org.lunarray.model.descriptor.converter.def.converters.NetworkInterfaceConverter;
import org.lunarray.model.descriptor.converter.def.converters.ShortConverter;
import org.lunarray.model.descriptor.converter.def.converters.SqlDateConverter;
import org.lunarray.model.descriptor.converter.def.converters.SqlTimeConverter;
import org.lunarray.model.descriptor.converter.def.converters.SqlTimestampConverter;
import org.lunarray.model.descriptor.converter.def.converters.URIConverter;
import org.lunarray.model.descriptor.converter.def.converters.URLConverter;
import org.lunarray.model.descriptor.converter.def.converters.UUIDConverter;
import org.lunarray.model.descriptor.converter.def.converters.XMLDurationConverter;
import org.lunarray.model.descriptor.converter.def.converters.XMLGregorianCalendarConverter;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract converter tool with some default converters.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public abstract class AbstractDefaultConverterTool
		implements ConverterTool {

	/** Validation message. */
	private static final String CONVERTER_NULL = "Converter may not be null.";
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDefaultConverterTool.class);
	/** The map of converters. */
	private final transient Map<Class<?>, Converter<?>> converters;

	/**
	 * Default constructor.
	 */
	public AbstractDefaultConverterTool() {
		this.converters = new HashMap<Class<?>, Converter<?>>();
		this.populateDefaultConverters();
	}

	/**
	 * Adds a converter.
	 * 
	 * @param type
	 *            The converter type. May not be null.
	 * @param converter
	 *            The converter. May not be null.
	 * @param <T>
	 *            The convertable type.
	 */
	public final <T> void addConverter(final Class<T> type, final Converter<T> converter) {
		Validate.notNull(type, "Converter type may not be null.");
		Validate.notNull(converter, AbstractDefaultConverterTool.CONVERTER_NULL);
		this.converters.put(type, converter);
	}

	/**
	 * Adds a converter.
	 * 
	 * @param converter
	 *            The converter.
	 */
	public final void addConverter(final Converter<?> converter) {
		Validate.notNull(converter, AbstractDefaultConverterTool.CONVERTER_NULL);
		final Type converterType = GenericsUtil.getEntityGenericType(converter.getClass(), 0, Converter.class);
		this.converters.put(GenericsUtil.guessClazz(converterType), converter);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> T convertToInstance(final Class<T> type, final String stringValue) throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to instance type {} with value: {}", type, stringValue);
		return this.resolve(type).convertToInstance(stringValue);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> T convertToInstance(final Class<T> type, final String stringValue, final Locale locale) throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to instance type {} with locale {} and value: {}", type, locale, stringValue);
		return this.resolve(type).convertToInstance(stringValue, locale);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> T convertToInstance(final Class<T> type, final String stringValue, final Locale locale, final String format)
			throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to instance type {} with locale {}, format '{}' and value: {}", type, locale,
				format, stringValue);
		return this.resolve(type).convertToInstance(stringValue, locale, format);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> T convertToInstance(final Class<T> type, final String stringValue, final String format) throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to instance type {} with format '{}' and value: {}", type, format,
				stringValue);
		return this.resolve(type).convertToInstance(stringValue, format);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> String convertToString(final Class<T> type, final T instance) throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to string with value type {} with value: {}", type, instance);
		return this.resolve(type).convertToString(instance);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> String convertToString(final Class<T> type, final T instance, final Locale locale) throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to string with value type {} with locale {} and value: {}", type, locale,
				instance);
		return this.resolve(type).convertToString(instance, locale);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> String convertToString(final Class<T> type, final T instance, final Locale locale, final String format)
			throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to string with value type {} with locale {}, format '{}' and value: {}",
				type, locale, format, instance);
		return this.resolve(type).convertToString(instance, locale, format);
	}

	/** {@inheritDoc} */
	@Override
	public final <T> String convertToString(final Class<T> type, final T instance, final String format) throws ConverterException {
		AbstractDefaultConverterTool.LOGGER.debug("Converting to string with value type {} with format '{}' and value: {}", type, format,
				instance);
		return this.resolve(type).convertToString(instance, format);
	}

	/**
	 * Add all default converters.
	 */
	private void populateDefaultConverters() {
		this.populateSimpleConverters();
		// Concurrent.
		this.addConverter(new AtomicBooleanConverter());
		this.addConverter(new AtomicIntegerConverter());
		this.addConverter(new AtomicLongConverter());
		// Class.
		this.addConverter(new ClazzConverter());
		// Naming.
		this.addConverter(new CompositeNameConverter());
		this.addConverter(new LdapNameConverter());
		this.addConverter(new LinkRefConverter());
		// Currency.
		this.addConverter(new CurrencyConverter());
		// XML DOM
		this.addConverter(new DOMConverter());
		// XML JAXB types.
		this.addConverter(new XMLDurationConverter());
		this.addConverter(new XMLGregorianCalendarConverter());
		// Network.
		this.addConverter(new InetAddressConverter());
		this.addConverter(new InetSocketAddressConverter());
		this.addConverter(new NetworkInterfaceConverter());
		// Resource types.
		this.addConverter(new URIConverter());
		this.addConverter(new URLConverter());
		this.addConverter(new UUIDConverter());
	}

	/**
	 * Populates the simple converters.
	 */
	private void populateSimpleConverters() {
		// Primitives.
		this.converters.put(Integer.TYPE, new IntegerConverter());
		this.converters.put(Double.TYPE, new DoubleConverter());
		this.converters.put(Float.TYPE, new FloatConverter());
		this.converters.put(Long.TYPE, new LongConverter());
		this.converters.put(Byte.TYPE, new ByteConverter());
		this.converters.put(Short.TYPE, new ShortConverter());
		this.converters.put(Character.TYPE, new CharacterConverter());
		this.converters.put(Boolean.TYPE, new BooleanConverter());
		// Date types.
		this.addConverter(new CalendarConverter());
		this.addConverter(new DateConverter());
		// SQL Date types.
		this.addConverter(new SqlDateConverter());
		this.addConverter(new SqlTimeConverter());
		this.addConverter(new SqlTimestampConverter());
		// String.
		this.addConverter(new IdempotentConverter());
		// Numbers.
		this.addConverter(new IntegerConverter());
		this.addConverter(new DoubleConverter());
		this.addConverter(new FloatConverter());
		this.addConverter(new LongConverter());
		this.addConverter(new ByteConverter());
		this.addConverter(new ShortConverter());
		// Character.
		this.addConverter(new CharacterConverter());
		// Math types.
		this.addConverter(new BigDecimalConverter());
		this.addConverter(new BigIntegerConverter());
		// Boolean.
		this.addConverter(new BooleanConverter());
	}

	/**
	 * Resolves a converter.
	 * 
	 * @param <T>
	 *            The converter type.
	 * @param type
	 *            The converter type. May not be null.
	 * @return The converter of the given type, or null.
	 * @throws ConverterException
	 *             Thrown if no compatible converter could be found.
	 */
	@SuppressWarnings("unchecked")
	// We filled it this way.
	protected final <T> Converter<T> resolve(final Class<T> type) throws ConverterException {
		Validate.notNull(type, "Type may not be null.");
		if (this.converters.containsKey(type)) {
			final Converter<T> result = (Converter<T>) this.converters.get(type);
			AbstractDefaultConverterTool.LOGGER.debug("Resolved converter {} for type {}", result, type);
			return result;
		} else {
			throw new ConverterException(String.format("Could not find converter for '%s'.", type));
		}
	}
}
