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
package org.lunarray.model.descriptor.converter;

import java.util.Locale;

import org.lunarray.model.descriptor.converter.exceptions.ConverterException;

/**
 * Describes a converter.
 * 
 * Converters are cached and should be thread safe.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <T>
 *            The type the converter is dedicated to.
 */
public interface Converter<T> {

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param stringValue
	 *            The string value.
	 * @return The instance.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	T convertToInstance(String stringValue) throws ConverterException;

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param stringValue
	 *            The string value.
	 * @param locale
	 *            The locale.
	 * @return The instance.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	T convertToInstance(String stringValue, Locale locale) throws ConverterException;

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param stringValue
	 *            The string value.
	 * @param locale
	 *            The locale.
	 * @param format
	 *            The format.
	 * @return The instance.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	T convertToInstance(String stringValue, Locale locale, String format) throws ConverterException;

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param stringValue
	 *            The string value.
	 * @param format
	 *            The format.
	 * @return The instance.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	T convertToInstance(String stringValue, String format) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param instance
	 *            The instance.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	String convertToString(T instance) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param instance
	 *            The instance.
	 * @param locale
	 *            The locale.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	String convertToString(T instance, Locale locale) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param instance
	 *            The instance.
	 * @param locale
	 *            The locale.
	 * @param format
	 *            The format.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	String convertToString(T instance, Locale locale, String format) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param instance
	 *            The instance.
	 * @param format
	 *            The format.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	String convertToString(T instance, String format) throws ConverterException;
}
