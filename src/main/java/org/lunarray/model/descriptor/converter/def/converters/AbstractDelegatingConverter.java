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

import java.util.Locale;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Default delegating converter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <C>
 *            The converter type.
 */
public abstract class AbstractDelegatingConverter<C>
		implements Converter<C> {

	/**
	 * Default constructor.
	 */
	public AbstractDelegatingConverter() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public final C convertToInstance(final String stringValue) throws ConverterException {
		C result = null;
		if (!StringUtil.isEmptyString(stringValue)) {
			result = this.toInstance(stringValue);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final C convertToInstance(final String stringValue, final Locale locale) throws ConverterException {
		return this.convertToInstance(stringValue);
	}

	/** {@inheritDoc} */
	@Override
	public final C convertToInstance(final String stringValue, final Locale locale, final String format) throws ConverterException {
		return this.convertToInstance(stringValue);
	}

	/** {@inheritDoc} */
	@Override
	public final C convertToInstance(final String stringValue, final String format) throws ConverterException {
		return this.convertToInstance(stringValue);
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final C instance) throws ConverterException {
		String result = null;
		if (!CheckUtil.isNull(instance)) {
			result = this.toString(instance);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final C instance, final Locale locale) throws ConverterException {
		return this.convertToString(instance);
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final C instance, final Locale locale, final String format) throws ConverterException {
		return this.convertToString(instance);
	}

	/** {@inheritDoc} */
	@Override
	public final String convertToString(final C instance, final String format) throws ConverterException {
		return this.convertToString(instance);
	}

	/**
	 * Converts a non null and non empty string to an instance.
	 * 
	 * @param stringValue
	 *            The string value.
	 * @return The converted value.
	 * @throws ConverterException
	 *             Thrown if conversion is unsuccessful.
	 */
	protected abstract C toInstance(String stringValue) throws ConverterException;

	/**
	 * Converts a non-null instance to a string.
	 * 
	 * @param instance
	 *            The instance to convert, not null.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if conversion is unsuccessful.
	 */
	protected abstract String toString(C instance) throws ConverterException;
}
