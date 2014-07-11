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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.lunarray.model.descriptor.model.member.Cardinality;

/**
 * Tests the model builder utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see ModelBuilderUtil
 */
public class ModelBuilderUtilTest {

	/**
	 * Test the cardinality resolver.
	 * 
	 * @see ModelBuilderUtil#resolveCardinality(Class)
	 */
	@Test
	public void testMultipleCardinality() {
		Assert.assertEquals(Cardinality.MULTIPLE, ModelBuilderUtil.resolveCardinality(Collection.class));
		Assert.assertEquals(Cardinality.MULTIPLE, ModelBuilderUtil.resolveCardinality(Set.class));
		Assert.assertEquals(Cardinality.MULTIPLE, ModelBuilderUtil.resolveCardinality(List.class));
	}

	/**
	 * Test the cardinality resolver.
	 * 
	 * @see ModelBuilderUtil#resolveCardinality(Class)
	 */
	@Test
	public void testNoneCardinality() {
		Assert.assertEquals(Cardinality.NONE, ModelBuilderUtil.resolveCardinality(Void.TYPE));
		Assert.assertEquals(Cardinality.NONE, ModelBuilderUtil.resolveCardinality(Void.class));
	}

	/**
	 * Test the cardinality resolver.
	 * 
	 * @see ModelBuilderUtil#resolveCardinality(Class)
	 */
	@Test
	public void testNullableCardinality() {
		Assert.assertEquals(Cardinality.NULLABLE, ModelBuilderUtil.resolveCardinality(Integer.class));
		Assert.assertEquals(Cardinality.NULLABLE, ModelBuilderUtil.resolveCardinality(Long.class));
		Assert.assertEquals(Cardinality.NULLABLE, ModelBuilderUtil.resolveCardinality(String.class));
		Assert.assertEquals(Cardinality.NULLABLE, ModelBuilderUtil.resolveCardinality(Object.class));
		Assert.assertEquals(Cardinality.NULLABLE, ModelBuilderUtil.resolveCardinality(Character.class));
	}

	/**
	 * Test the cardinality resolver.
	 * 
	 * @see ModelBuilderUtil#resolveCardinality(Class)
	 */
	@Test
	public void testSingleCardinality() {
		Assert.assertEquals(Cardinality.SINGLE, ModelBuilderUtil.resolveCardinality(Integer.TYPE));
		Assert.assertEquals(Cardinality.SINGLE, ModelBuilderUtil.resolveCardinality(Long.TYPE));
		Assert.assertEquals(Cardinality.SINGLE, ModelBuilderUtil.resolveCardinality(Character.TYPE));
	}
}
