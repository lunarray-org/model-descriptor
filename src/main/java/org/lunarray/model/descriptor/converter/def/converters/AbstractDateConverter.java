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

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.util.DateFormatUtil;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Converter for some date related type..
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <T>
 *            The date type.
 */
public abstract class AbstractDateConverter<T>
		implements Converter<T> {

	/**
	 * Default constructor.
	 */
	public AbstractDateConverter() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue) throws ConverterException {
		T result = null;
		if (!StringUtil.isEmptyString(stringValue)) {
			try {
				final Date date = DateFormatUtil.parseDateTime(stringValue, null);
				result = this.toResultType(date);
			} catch (final ParseException e) {
				throw new ConverterException(String.format("Could not convert string '%s'.", stringValue), e);
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue, final Locale locale) throws ConverterException {
		T result = null;
		if (!StringUtil.isEmptyString(stringValue)) {
			try {
				final Date date = DateFormatUtil.parseDateTime(stringValue, locale);
				result = this.toResultType(date);
			} catch (final ParseException e) {
				throw new ConverterException(String.format("Could not convert string '%s' for locale.", stringValue), e);
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue, final Locale locale, final String format) throws ConverterException {
		T result = null;
		if (StringUtil.isEmptyString(format)) {
			result = this.convertToInstance(stringValue, locale);
		} else {
			if (!StringUtil.isEmptyString(stringValue)) {
				try {
					final Date date = DateFormatUtil.parse(format, stringValue, locale);
					result = this.toResultType(date);
				} catch (final ParseException e) {
					throw new ConverterException(String.format("Could not convert string '%s' for locale and format %s.", stringValue,
							format), e);
				}
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final T convertToInstance(final String stringValue, final String format) throws ConverterException {
		T result = null;
		if (StringUtil.isEmptyString(format)) {
			result = this.convertToInstance(stringValue);
		} else {
			if (!StringUtil.isEmptyString(stringValue)) {
				try {
					final Date date = DateFormatUtil.parse(format, stringValue, null);
					result = this.toResultType(date);
				} catch (final ParseException e) {
					throw new ConverterException(String.format("Could not convert string '%s' for format %s.", stringValue, format), e);
				}
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance) throws ConverterException {
		String result = null;
		if (!CheckUtil.isNull(instance)) {
			final Date date = this.toDateType(instance);
			result = DateFormatUtil.formatDateTime(date, null);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance, final Locale locale) throws ConverterException {
		String result = null;
		if (!CheckUtil.isNull(instance)) {
			final Date date = this.toDateType(instance);
			result = DateFormatUtil.formatDateTime(date, locale);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance, final Locale locale, final String format) throws ConverterException {
		String result = null;
		if (StringUtil.isEmptyString(format)) {
			result = this.convertToString(instance, locale);
		} else {
			if (!CheckUtil.isNull(instance)) {
				final Date date = this.toDateType(instance);
				result = DateFormatUtil.format(format, date, locale);
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final T instance, final String format) throws ConverterException {
		String result = null;
		if (StringUtil.isEmptyString(format)) {
			result = this.convertToString(instance);
		} else {
			if (!CheckUtil.isNull(instance)) {
				final Date date = this.toDateType(instance);
				result = DateFormatUtil.format(format, date, null);
			}
		}
		return result;
	}

	/**
	 * Converts to a date.
	 * 
	 * @param date
	 *            The result type.
	 * @return The date.
	 */
	protected abstract Date toDateType(T date);

	/**
	 * Converts to the result type.
	 * 
	 * @param date
	 *            The date.
	 * @return The result type.
	 */
	protected abstract T toResultType(Date date);
}
