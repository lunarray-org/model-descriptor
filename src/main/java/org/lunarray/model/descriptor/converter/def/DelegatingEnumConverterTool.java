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

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.converter.ConverterTool;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A converter tool that delegates.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DelegatingEnumConverterTool
		implements ConverterTool {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DelegatingEnumConverterTool.class);
	/** Validation message. */
	private static final String TYPE_NULL = "Type may not be null.";
	/** The delegate converter. */
	private final transient ConverterTool converterTool;

	/**
	 * Default constructor.
	 * 
	 * @param converterTool
	 *            The converter delegate.
	 */
	public DelegatingEnumConverterTool(final ConverterTool converterTool) {
		this.converterTool = converterTool;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> T convertToInstance(final Class<T> type, final String stringValue) throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		T result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to instance type {} with value: {}", type, stringValue);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			if (!StringUtil.isEmptyString(stringValue)) {
				result = (T) Enum.valueOf(enumType, stringValue);
			}
		} else {
			result = this.converterTool.convertToInstance(type, stringValue);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> T convertToInstance(final Class<T> type, final String stringValue, final Locale locale) throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		T result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to instance type {} with locale {} and value: {}", type, locale,
					stringValue);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			if (!StringUtil.isEmptyString(stringValue)) {
				result = (T) Enum.valueOf(enumType, stringValue);
			}
		} else {
			result = this.converterTool.convertToInstance(type, stringValue, locale);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> T convertToInstance(final Class<T> type, final String stringValue, final Locale locale, final String format)
			throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		T result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to instance type {} with locale {}, format {} and value: {}", type,
					locale, format, stringValue);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			if (!StringUtil.isEmptyString(stringValue)) {
				result = (T) Enum.valueOf(enumType, stringValue);
			}
		} else {
			result = this.converterTool.convertToInstance(type, stringValue, locale, format);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> T convertToInstance(final Class<T> type, final String stringValue, final String format) throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		T result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to instance type {} with format {} and value: {}", type, stringValue);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			if (!StringUtil.isEmptyString(stringValue)) {
				result = (T) Enum.valueOf(enumType, stringValue);
			}
		} else {
			result = this.converterTool.convertToInstance(type, stringValue, format);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> String convertToString(final Class<T> type, final T instance) throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		String result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to string with type {} with value: {}", type, instance);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			final Enum<?> enumInstance = enumType.cast(instance);
			if (!CheckUtil.isNull(enumInstance)) {
				result = enumInstance.name();
			}
		} else {
			result = this.converterTool.convertToString(type, instance);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> String convertToString(final Class<T> type, final T instance, final Locale locale) throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		String result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to string with type {} with locale {} and value: {}", type, locale,
					instance);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			final Enum<?> enumInstance = enumType.cast(instance);
			if (!CheckUtil.isNull(enumInstance)) {
				result = enumInstance.name();
			}
		} else {
			result = this.converterTool.convertToString(type, instance, locale);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> String convertToString(final Class<T> type, final T instance, final Locale locale, final String format)
			throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		String result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to string with type {} with locale {}, format {} and value: {}", type,
					locale, format, instance);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			final Enum<?> enumInstance = enumType.cast(instance);
			if (!CheckUtil.isNull(enumInstance)) {
				result = enumInstance.name();
			}
		} else {
			result = this.converterTool.convertToString(type, instance, locale, format);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're checking after the fact.
	@Override
	public <T> String convertToString(final Class<T> type, final T instance, final String format) throws ConverterException {
		Validate.notNull(type, DelegatingEnumConverterTool.TYPE_NULL);
		String result = null;
		if (type.isEnum()) {
			DelegatingEnumConverterTool.LOGGER.debug("Converting to string with type {} with format {} and value: {}", type, format,
					instance);
			@SuppressWarnings("rawtypes")
			// No other way?
			final Class<? extends Enum> enumType = (Class<? extends Enum>) type;
			final Enum<?> enumInstance = enumType.cast(instance);
			if (!CheckUtil.isNull(enumInstance)) {
				result = enumInstance.name();
			}
		} else {
			result = this.converterTool.convertToString(type, instance, format);
		}
		return result;
	}
}
