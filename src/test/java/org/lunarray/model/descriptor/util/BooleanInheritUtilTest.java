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
 * Tests the boolean with inheritance options.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see BooleanInheritUtil
 */
public class BooleanInheritUtilTest {

	/**
	 * Tests value resolution.
	 * 
	 * @see BooleanInheritUtil#resolve(BooleanInherit, BooleanInherit, boolean)
	 */
	@Test
	public void testDefault() {
		Assert.assertTrue(BooleanInheritUtil.resolve(BooleanInherit.INHERIT, BooleanInherit.INHERIT, true));
		Assert.assertTrue(!BooleanInheritUtil.resolve(BooleanInherit.INHERIT, BooleanInherit.INHERIT, false));
	}

	/**
	 * Tests value resolution.
	 * 
	 * @see BooleanInheritUtil#resolve(BooleanInherit, BooleanInherit, boolean)
	 */
	@Test
	public void testParent() {
		Assert.assertTrue(BooleanInheritUtil.resolve(BooleanInherit.INHERIT, BooleanInherit.TRUE, false));
		Assert.assertTrue(!BooleanInheritUtil.resolve(BooleanInherit.INHERIT, BooleanInherit.FALSE, true));
	}

	/**
	 * Tests value resolution.
	 * 
	 * @see BooleanInheritUtil#resolve(BooleanInherit, BooleanInherit, boolean)
	 */
	@Test
	public void testValue() {
		Assert.assertTrue(BooleanInheritUtil.resolve(BooleanInherit.TRUE, BooleanInherit.FALSE, false));
		Assert.assertTrue(!BooleanInheritUtil.resolve(BooleanInherit.FALSE, BooleanInherit.TRUE, true));
	}
}
