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
import org.lunarray.model.descriptor.model.extension.Extension;

/**
 * Tool for converters.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface ConverterTool
		extends Extension {

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert to.
	 * @param stringValue
	 *            The string value.
	 * @return The instance.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	<T> T convertToInstance(Class<T> type, String stringValue) throws ConverterException;

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert to.
	 * @param stringValue
	 *            The string value.
	 * @param locale
	 *            The locale.
	 * @return The instance.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	<T> T convertToInstance(Class<T> type, String stringValue, Locale locale) throws ConverterException;

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert to.
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
	<T> T convertToInstance(Class<T> type, String stringValue, Locale locale, String format) throws ConverterException;

	/**
	 * Convert a given string value to an instance.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert to.
	 * @param stringValue
	 *            The string value.
	 * @param format
	 *            The format.
	 * @return The instance.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	<T> T convertToInstance(Class<T> type, String stringValue, String format) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert from.
	 * @param instance
	 *            The instance.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	<T> String convertToString(Class<T> type, T instance) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert from.
	 * @param instance
	 *            The instance.
	 * @param locale
	 *            The locale.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	<T> String convertToString(Class<T> type, T instance, Locale locale) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert from.
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
	<T> String convertToString(Class<T> type, T instance, Locale locale, String format) throws ConverterException;

	/**
	 * Convert a given instance to a string value.
	 * 
	 * @param <T>
	 *            The instance type.
	 * @param type
	 *            The type to convert from.
	 * @param instance
	 *            The instance.
	 * @param format
	 *            The format.
	 * @return The string value.
	 * @throws ConverterException
	 *             Thrown if the value could not be converted.
	 */
	<T> String convertToString(Class<T> type, T instance, String format) throws ConverterException;
}
