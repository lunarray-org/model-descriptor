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

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test The string utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see StringUtil
 */
public class StringUtilTest {

	/**
	 * Test empty string.
	 * 
	 * @see StringUtil#isEmptyString(String)
	 */
	@Test
	public void isEmptyEmpty() {
		Assert.assertTrue(StringUtil.isEmptyString(""));
	}

	/**
	 * Test empty string.
	 * 
	 * @see StringUtil#isEmptyString(String)
	 */
	@Test
	public void isEmptyNotEmpty() {
		Assert.assertTrue(!StringUtil.isEmptyString("test"));
	}

	/**
	 * Test empty string.
	 * 
	 * @see StringUtil#isEmptyString(String)
	 */
	@Test
	public void isEmptyNull() {
		Assert.assertTrue(StringUtil.isEmptyString(null));
	}

	/**
	 * Test string concatenation.
	 * 
	 * @see StringUtil#commaSeparated(Iterable, String, StringBuilder)
	 */
	@Test
	public void testConcatEmpty() {
		final List<String> arg = new LinkedList<String>();
		final String separator = ",";
		final StringBuilder builder = new StringBuilder();
		StringUtil.commaSeparated(arg, separator, builder);
		Assert.assertEquals("", builder.toString());
	}

	/**
	 * Test string concatenation.
	 * 
	 * @see StringUtil#commaSeparated(Iterable, String, StringBuilder)
	 */
	@Test
	public void testConcatMultipleValue() {
		final List<String> arg = new LinkedList<String>();
		arg.add("test01");
		arg.add("test02");
		final String separator = ",";
		final StringBuilder builder = new StringBuilder();
		StringUtil.commaSeparated(arg, separator, builder);
		Assert.assertEquals("test01,test02", builder.toString());
	}

	/**
	 * Test string concatenation.
	 * 
	 * @see StringUtil#commaSeparated(Iterable, String, StringBuilder)
	 */
	@Test
	public void testConcatSingleValue() {
		final List<String> arg = new LinkedList<String>();
		arg.add("test01");
		final String separator = ",";
		final StringBuilder builder = new StringBuilder();
		StringUtil.commaSeparated(arg, separator, builder);
		Assert.assertEquals("test01", builder.toString());
	}
}
