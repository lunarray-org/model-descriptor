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

import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;

/**
 * Idempotent converter for {@link String}.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class IdempotentConverter
		implements Converter<String> {

	/**
	 * Default constructor.
	 */
	public IdempotentConverter() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public String convertToInstance(final String stringValue) throws ConverterException {
		return stringValue;
	}

	/** {@inheritDoc} */
	@Override
	public String convertToInstance(final String stringValue, final Locale locale) throws ConverterException {
		return stringValue;
	}

	/** {@inheritDoc} */
	@Override
	public String convertToInstance(final String stringValue, final Locale locale, final String format) throws ConverterException {
		return stringValue;
	}

	/** {@inheritDoc} */
	@Override
	public String convertToInstance(final String stringValue, final String format) throws ConverterException {
		return stringValue;
	}

	/** {@inheritDoc} */
	@Override
	public String convertToString(final String instance) throws ConverterException {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public String convertToString(final String instance, final Locale locale) throws ConverterException {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public String convertToString(final String instance, final Locale locale, final String format) throws ConverterException {
		return instance;
	}

	/** {@inheritDoc} */
	@Override
	public String convertToString(final String instance, final String format) throws ConverterException {
		return instance;
	}
}
