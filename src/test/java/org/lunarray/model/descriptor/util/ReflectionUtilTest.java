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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.lunarray.model.descriptor.test.Entity01;
import org.lunarray.model.descriptor.test.SampleEnum;

/**
 * Tests the reflection utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see ReflectionUtil
 */
public class ReflectionUtilTest {

	/**
	 * Tests resolve.
	 * 
	 * @see ReflectionUtil#getFields(java.util.List, Class)
	 */
	@Test
	public void resolveFieldsEntity() throws Exception {
		final List<Field> result = new LinkedList<Field>();
		ReflectionUtil.getFields(result, Entity01.class);
		Assert.assertEquals(4, result.size());
		final String[] fields = { "embedded", "entityList", "someList", "value" };
		for (final String fieldName : fields) {
			Assert.assertTrue(result.contains(Entity01.class.getDeclaredField(fieldName)));
		}
	}

	/**
	 * Tests resolve.
	 * 
	 * @see ReflectionUtil#getFields(java.util.List, Class)
	 */
	@Test
	public void resolveFieldsEnum() throws Exception {
		final List<Field> result = new LinkedList<Field>();
		ReflectionUtil.getFields(result, SampleEnum.class);
		Assert.assertEquals(2, result.size());
		final String[] fields1 = { "someField" };
		for (final String fieldName : fields1) {
			Assert.assertTrue(result.contains(SampleEnum.class.getDeclaredField(fieldName)));
		}
		final String[] fields2 = { "name" };
		for (final String fieldName : fields2) {
			Assert.assertTrue(result.contains(Enum.class.getDeclaredField(fieldName)));
		}
	}

	/**
	 * Tests resolve.
	 * 
	 * @see ReflectionUtil#getMethods(java.util.List, Class)
	 */
	@Test
	public void resolveMethodsEntityNoStatic() throws Exception {
		final List<Method> result = new LinkedList<Method>();
		ReflectionUtil.getMethods(result, Entity01.class, false, true);
		Assert.assertEquals(6, result.size());
		final Set<String> methodNames = new HashSet<String>();
		for (final Method m : result) {
			methodNames.add(m.getName());
		}
		final String[] methods = { "getValue", "setValue", "getEmbedded", "getEntityList", "setEmbedded", "getSomeList" };
		for (final String methodName : methods) {
			Assert.assertTrue(methodNames.contains(methodName));
		}
	}

	/**
	 * Tests resolve.
	 * 
	 * @see ReflectionUtil#getMethods(java.util.List, Class)
	 */
	@Test
	public void resolveMethodsEntityStatic() throws Exception {
		final List<Method> result = new LinkedList<Method>();
		ReflectionUtil.getMethods(result, Entity01.class, true, true);
		Assert.assertEquals(2, result.size());
		final Set<String> methodNames = new HashSet<String>();
		for (final Method m : result) {
			methodNames.add(m.getName());
		}
		final String[] methods = { "getTestStatic", "setTestStatic" };
		for (final String methodName : methods) {
			Assert.assertTrue(methodNames.contains(methodName));
		}
	}

	/**
	 * Tests resolve.
	 * 
	 * @see ReflectionUtil#getMethods(java.util.List, Class)
	 */
	@Test
	public void resolveMethodsEnum() throws Exception {
		final List<Method> result = new LinkedList<Method>();
		ReflectionUtil.getMethods(result, SampleEnum.class, false, true);
		Assert.assertEquals(1, result.size());
		final Set<String> methodNames = new HashSet<String>();
		for (final Method m : result) {
			methodNames.add(m.getName());
		}
		final String[] methods = { "getSomeField" };
		for (final String methodName : methods) {
			Assert.assertTrue(methodNames.contains(methodName));
		}
	}
}
