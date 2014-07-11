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
package org.lunarray.model.descriptor.converter.def.converters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Converter for numbers.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <T>
 *            The number type.
 */
public abstract class AbstractNumberConverter<T>
		implements Converter<T> {

	/** The error message. */
	private static final String ERROR_MESSAGE = "Could not convert.";

	/**
	 * Default constructor.
	 */
	public AbstractNumberConverter() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue) throws ConverterException {
		return this.parse(stringValue, null, null);
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue, final Locale locale) throws ConverterException {
		return this.parse(stringValue, null, locale);
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue, final Locale locale, final String format) throws ConverterException {
		return this.parse(stringValue, format, locale);
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue, final String format) throws ConverterException {
		return this.parse(stringValue, format, null);
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance) throws ConverterException {
		return this.format(instance, null, null);
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance, final Locale locale) throws ConverterException {
		return this.format(instance, null, locale);
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance, final Locale locale, final String format) throws ConverterException {
		return this.format(instance, format, locale);
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance, final String format) throws ConverterException {
		return this.format(instance, format, null);
	}

	/**
	 * Format a value.
	 * 
	 * @param value
	 *            The value to format.
	 * @param format
	 *            The format, may be null.
	 * @param locale
	 *            The locale, may be null.
	 * @return The formatted value.
	 */
	private String format(final T value, final String format, final Locale locale) {
		String result = null;
		if (!CheckUtil.isNull(value)) {
			result = this.getFormat(format, locale).format(this.toNumberType(value));
		}
		return result;
	}

	/**
	 * Gets the number format.
	 * 
	 * @param format
	 *            The number format.
	 * @param locale
	 *            The locale.
	 * @return The number format.
	 */
	private NumberFormat getFormat(final String format, final Locale locale) {
		NumberFormat result;
		if (CheckUtil.isNull(locale)) {
			result = NumberFormat.getInstance();
		} else {
			result = NumberFormat.getInstance(locale);
		}
		if ((result instanceof DecimalFormat) && !StringUtil.isEmptyString(format)) {
			final DecimalFormat decimalFormat = (DecimalFormat) result;
			if (CheckUtil.isNull(locale)) {
				decimalFormat.applyPattern(format);
			} else {
				decimalFormat.applyLocalizedPattern(format);
			}
		}
		return result;
	}

	/**
	 * Parses a value.
	 * 
	 * @param stringValue
	 *            The value to parse.
	 * @param format
	 *            The format, may be null.
	 * @param locale
	 *            The locale, may be null.
	 * @return The result.
	 * @throws ConverterException
	 *             Thrown if the parsing fails.
	 */
	private T parse(final String stringValue, final String format, final Locale locale) throws ConverterException {
		T result = null;
		if (!StringUtil.isEmptyString(stringValue)) {
			try {
				result = this.toResultType(this.getFormat(format, locale).parse(stringValue));
			} catch (final ParseException e) {
				throw new ConverterException(AbstractNumberConverter.ERROR_MESSAGE, e);
			}
		}
		return result;
	}

	/**
	 * Convert to a number type.
	 * 
	 * @param number
	 *            The number.
	 * @return The number.
	 */
	protected abstract Number toNumberType(T number);

	/**
	 * Converts the number to the result type.
	 * 
	 * @param number
	 *            The number.
	 * @return The result type.
	 */
	protected abstract T toResultType(Number number);
}
