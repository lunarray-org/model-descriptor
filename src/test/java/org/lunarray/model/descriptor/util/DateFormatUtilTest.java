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

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the date format util.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see DateFormatUtil
 */
public class DateFormatUtilTest {

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testFormatLocale() {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		final Date date = new Date(1000000000000l);
		DateFormatUtil.format(DateTimeType.DATE, date, Locale.GERMAN);
		DateFormatUtil.format(DateTimeType.DATE_TIME, date, Locale.GERMAN);
		DateFormatUtil.format(DateTimeType.TIME, date, Locale.GERMAN);
	}

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testFormatLocaleCustomFormat() {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		final Date date = new Date(1000000000000l);
		DateFormatUtil.format("yyyy-MM-dd", date, Locale.GERMAN);
		DateFormatUtil.format("EEEE, MMMMM d, yyyy H:mm:ss a z", date, Locale.GERMAN);
		DateFormatUtil.format(DateTimeType.TIME, date, Locale.GERMAN);
	}

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testFormatNoLocale() {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		final Date date = new Date(1000000000000l);
		Assert.assertEquals("2001-09-09", DateFormatUtil.format(DateTimeType.DATE, date, null));
		Assert.assertEquals("Sunday, September 9, 2001 3:46:40 AM CEST", DateFormatUtil.format(DateTimeType.DATE_TIME, date, null));
		Assert.assertEquals("3:46:40 AM CEST", DateFormatUtil.format(DateTimeType.TIME, date, null));
	}

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testFormatNoLocaleCustomFormat() {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		final Date date = new Date(1000000000000l);
		Assert.assertEquals("2001-09-09", DateFormatUtil.format("yyyy-MM-dd", date, null));
		Assert.assertEquals("Sunday, September 9, 2001 3:46:40 AM CEST",
				DateFormatUtil.format("EEEE, MMMMM d, yyyy H:mm:ss a z", date, null));
		Assert.assertEquals("3:46:40 AM CEST", DateFormatUtil.format(DateTimeType.TIME, date, null));
	}

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testParseLocale() throws Exception {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		DateFormatUtil.parse(DateTimeType.DATE, "2001-09-09", Locale.GERMAN);
	}

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testParseLocaleCustomFormat() throws Exception {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		DateFormatUtil.parse("yyyy-MM-dd", "2001-09-09", Locale.GERMAN);
	}

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testParseNoLocale() throws Exception {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		Assert.assertEquals(new Date(999986400000l), DateFormatUtil.parse(DateTimeType.DATE, "2001-09-09", null));
		Assert.assertEquals(new Date(1000000000000l),
				DateFormatUtil.parse(DateTimeType.DATE_TIME, "Sunday, September 9, 2001 3:46:40 AM CEST", null));
		Assert.assertEquals(new Date(6400000l), DateFormatUtil.parse(DateTimeType.TIME, "3:46:40 AM CEST", null));
	}

	/**
	 * Test format.
	 * 
	 * @see DateFormatUtil#format(DateTimeType, java.util.Date,
	 *      java.util.Locale)
	 */
	@Test
	public void testParseNoLocaleCustomFormat() throws Exception {
		DateFormatUtil.setConfiguration("/META-INF/dateconfiguration.properties");
		Assert.assertEquals(new Date(999986400000l), DateFormatUtil.parse("yyyy-MM-dd", "2001-09-09", null));
		Assert.assertEquals(new Date(1000000000000l),
				DateFormatUtil.parse("EEEE, MMMMM d, yyyy H:mm:ss a z", "Sunday, September 9, 2001 3:46:40 AM CEST", null));
		Assert.assertEquals(new Date(6400000l), DateFormatUtil.parse(DateTimeType.TIME, "3:46:40 AM CEST", null));
	}
}
