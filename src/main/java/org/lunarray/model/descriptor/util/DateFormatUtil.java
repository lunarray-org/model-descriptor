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
package org.lunarray.model.descriptor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;

/**
 * Date format utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum DateFormatUtil {
	/** Instance. */
	INSTANCE;

	/** Configuration file key for the date format. */
	public static final String DEFAULT_DATE_KEY = "date.format";
	/** Configuration file key for the date time format. */
	public static final String DEFAULT_DATE_TIME_KEY = "date.time.format";
	/** Configuration file key for the time format. */
	public static final String DEFAULT_TIME_KEY = "time.format";
	/** Validation message. */
	private static final String FORMAT_EMPTY = "Format may not be empty.";
	/** Validation message. */
	private static final String VALUE_NULL = "Value may not be null.";

	/**
	 * Formats a date.
	 * 
	 * @param type
	 *            The format type.
	 * @param value
	 *            The date value. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The string formatted date.
	 */
	public static String format(final DateTimeType type, final Date value, final Locale locale) {
		String result;
		if (DateTimeType.DATE_TIME == type) {
			result = DateFormatUtil.formatDateTime(value, locale);
		} else if (DateTimeType.TIME == type) {
			result = DateFormatUtil.formatTime(value, locale);
		} else {
			result = DateFormatUtil.formatDate(value, locale);
		}
		return result;
	}

	/**
	 * Formats a date.
	 * 
	 * @param date
	 *            The date value. May not be null.
	 * @param format
	 *            The format. May not be empty.
	 * @param locale
	 *            The (optional) locale.
	 * @return The string formatted date.
	 */
	public static String format(final String format, final Date date, final Locale locale) {
		Validate.notEmpty(format, DateFormatUtil.FORMAT_EMPTY);
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		SimpleDateFormat dateFormat;
		Locale realLocale;
		if (CheckUtil.isNull(locale)) {
			realLocale = Locale.getDefault();
		} else {
			realLocale = locale;
		}
		dateFormat = new SimpleDateFormat(format, realLocale);
		return dateFormat.format(date);
	}

	/**
	 * Formats a date.
	 * 
	 * @param date
	 *            The date value. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The string formatted date.
	 */
	public static String formatDate(final Date date, final Locale locale) {
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		return INSTANCE.resolveDateFormat(locale).format(date);
	}

	/**
	 * Formats a date time.
	 * 
	 * @param date
	 *            The date value. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The string formatted date.
	 */
	public static String formatDateTime(final Date date, final Locale locale) {
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		return INSTANCE.resolveDateTimeFormat(locale).format(date);
	}

	/**
	 * Formats a time.
	 * 
	 * @param date
	 *            The date value. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The string formatted date.
	 */
	public static String formatTime(final Date date, final Locale locale) {
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		return INSTANCE.resolveTimeFormat(locale).format(date);
	}

	/**
	 * Parse a date time.
	 * 
	 * @param type
	 *            The type.
	 * @param value
	 *            The value. May not be null.
	 * @param locale
	 *            The locale.
	 * @return The date.
	 * @throws ParseException
	 *             If the date could not be parsed.
	 */
	public static Date parse(final DateTimeType type, final String value, final Locale locale) throws ParseException {
		Date result;
		if (DateTimeType.DATE_TIME == type) {
			result = DateFormatUtil.parseDateTime(value, locale);
		} else if (DateTimeType.TIME == type) {
			result = DateFormatUtil.parseTime(value, locale);
		} else {
			result = DateFormatUtil.parseDate(value, locale);
		}
		return result;
	}

	/**
	 * Parse a date.
	 * 
	 * @param format
	 *            The format. May not be empty.
	 * @param date
	 *            The date. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The parsed date.
	 * @throws ParseException
	 *             If the date could not be parsed.
	 */
	public static Date parse(final String format, final String date, final Locale locale) throws ParseException {
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		SimpleDateFormat dateFormat;
		Locale realLocale;
		if (CheckUtil.isNull(locale)) {
			realLocale = Locale.getDefault();
		} else {
			realLocale = locale;
		}
		dateFormat = new SimpleDateFormat(format, realLocale);
		return dateFormat.parse(date);
	}

	/**
	 * Parse a date.
	 * 
	 * @param date
	 *            The date. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The parsed date.
	 * @throws ParseException
	 *             If the date could not be parsed.
	 */
	public static Date parseDate(final String date, final Locale locale) throws ParseException {
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		return DateFormatUtil.parse(INSTANCE.resolveDateFormat(locale), date);
	}

	/**
	 * Parse a date time.
	 * 
	 * @param date
	 *            The date time. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The parsed date time.
	 * @throws ParseException
	 *             If the date could not be parsed.
	 */
	public static Date parseDateTime(final String date, final Locale locale) throws ParseException {
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		return DateFormatUtil.parse(INSTANCE.resolveDateTimeFormat(locale), date);
	}

	/**
	 * Parse a time.
	 * 
	 * @param date
	 *            The time. May not be null.
	 * @param locale
	 *            The (optional) locale.
	 * @return The parsed time.
	 * @throws ParseException
	 *             If the date could not be parsed.
	 */
	public static Date parseTime(final String date, final Locale locale) throws ParseException {
		Validate.notNull(date, DateFormatUtil.VALUE_NULL);
		return DateFormatUtil.parse(INSTANCE.resolveTimeFormat(locale), date);
	}

	/**
	 * Updates the configuration file.
	 * 
	 * @param configuration
	 *            The configuration file location.
	 */
	public static void setConfiguration(final String configuration) {
		INSTANCE.configuration = configuration;
		INSTANCE.reset();
	}

	/**
	 * Parse a date with a given format.
	 * 
	 * @param format
	 *            The format.
	 * @param date
	 *            The parsed date.
	 * @return The parsed date.
	 * @throws ParseException
	 *             If the date could not be parsed.
	 */
	private static Date parse(final DateFormat format, final String date) throws ParseException {
		Date result = null;
		if (!StringUtil.isEmptyString(date)) {
			result = format.parse(date);
		}
		return result;
	}

	/** The configuration file. */
	private String configuration;

	/** The configuration properties. */
	private Properties properties;

	/**
	 * Default constructor.
	 */
	private DateFormatUtil() {
		this.configuration = "/META-INF/date-configuration.properties";
		this.reset();
	}

	/**
	 * Reset the properties.
	 */
	private void reset() {
		final Properties tmpProperties = new Properties();
		ConfigurationUtil.loadProperties(this.configuration, tmpProperties);
		this.properties = tmpProperties;
	}

	/**
	 * Resolves a format.
	 * 
	 * @param key
	 *            The format key.
	 * @param defaultValue
	 *            The format default value.
	 * @param locale
	 *            The locale.
	 * @return The format.
	 */
	private DateFormat resolve(final String key, final DateFormat defaultValue, final Locale locale) {
		Locale realLocale;
		if (CheckUtil.isNull(locale)) {
			realLocale = Locale.getDefault();
		} else {
			realLocale = locale;
		}
		DateFormat format;
		if (this.properties.containsKey(key)) {
			format = new SimpleDateFormat(this.properties.getProperty(key), realLocale);
		} else {
			format = defaultValue;
		}
		return format;
	}

	/**
	 * Resolves the date format.
	 * 
	 * @param locale
	 *            The (optional) locale.
	 * @return The format.
	 */
	private DateFormat resolveDateFormat(final Locale locale) {
		DateFormat defaultValue;
		if (CheckUtil.isNull(locale)) {
			defaultValue = DateFormat.getDateInstance(DateFormat.FULL);
		} else {
			defaultValue = DateFormat.getDateInstance(DateFormat.FULL, locale);
		}
		return this.resolve(DateFormatUtil.DEFAULT_DATE_KEY, defaultValue, locale);
	}

	/**
	 * Resolves the date time format.
	 * 
	 * @param locale
	 *            The (optional) locale.
	 * @return The format.
	 */
	private DateFormat resolveDateTimeFormat(final Locale locale) {
		DateFormat defaultValue;
		if (CheckUtil.isNull(locale)) {
			defaultValue = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		} else {
			defaultValue = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
		}
		return this.resolve(DateFormatUtil.DEFAULT_DATE_TIME_KEY, defaultValue, locale);
	}

	/**
	 * Resolves the time format.
	 * 
	 * @param locale
	 *            The (optional) locale.
	 * @return The format.
	 */
	private DateFormat resolveTimeFormat(final Locale locale) {
		DateFormat defaultValue;
		if (CheckUtil.isNull(locale)) {
			defaultValue = DateFormat.getTimeInstance(DateFormat.FULL);
		} else {
			defaultValue = DateFormat.getTimeInstance(DateFormat.FULL, locale);
		}
		return this.resolve(DateFormatUtil.DEFAULT_TIME_KEY, defaultValue, locale);
	}
}
