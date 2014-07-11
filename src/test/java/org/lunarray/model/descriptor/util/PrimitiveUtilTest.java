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

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the primitive util.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see PrimitiveUtil
 */
public class PrimitiveUtilTest {

	/**
	 * Test the primitive util.
	 * 
	 * @see PrimitiveUtil#getObjectType(Class)
	 */
	@Test
	public void testNonPrimitive() {
		Assert.assertNull(PrimitiveUtil.getObjectType(String.class));
	}

	/**
	 * Test the primitive util.
	 * 
	 * @see PrimitiveUtil#getObjectType(Class)
	 */
	@Test
	public void testNonPrimitiveBoxable() {
		Assert.assertNull(PrimitiveUtil.getObjectType(Integer.class));
	}

	/**
	 * Test the primitive util.
	 * 
	 * @see PrimitiveUtil#getObjectType(Class)
	 */
	@Test
	public void testPrimitive() {
		Assert.assertNotNull(PrimitiveUtil.getObjectType(Integer.TYPE));
	}
}
