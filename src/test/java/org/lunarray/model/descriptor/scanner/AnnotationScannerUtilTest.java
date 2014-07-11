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
package org.lunarray.model.descriptor.scanner;

import java.lang.annotation.Annotation;

import org.junit.Assert;
import org.junit.Test;
import org.lunarray.model.descriptor.scanner.annotations.TestAnnotation01;
import org.lunarray.model.descriptor.scanner.annotations.TestAnnotation02;
import org.lunarray.model.descriptor.scanner.annotations.TestAnnotation03;
import org.lunarray.model.descriptor.scanner.model.TestEntity01;
import org.lunarray.model.descriptor.scanner.model.TestEntity02;

/**
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class AnnotationScannerUtilTest {

	/**
	 * Transitive annotation scanning.
	 * 
	 * @see AnnotationScannerUtil#getMarked(Class, Class, boolean)
	 */
	@Test
	public void testScan() {
		Assert.assertEquals(1, AnnotationScannerUtil.getMarked(TestAnnotation01.class, TestEntity01.class, false).size());
		Assert.assertEquals(0, AnnotationScannerUtil.getMarked(TestAnnotation02.class, TestEntity01.class, false).size());
		Assert.assertEquals(0, AnnotationScannerUtil.getMarked(TestAnnotation03.class, TestEntity01.class, false).size());
		Assert.assertEquals(0, AnnotationScannerUtil.getMarked(TestAnnotation01.class, TestEntity02.class, false).size());
		Assert.assertEquals(0, AnnotationScannerUtil.getMarked(TestAnnotation02.class, TestEntity02.class, false).size());
		Assert.assertEquals(1, AnnotationScannerUtil.getMarked(TestAnnotation03.class, TestEntity02.class, false).size());
	}

	/**
	 * Transitive annotation scanning.
	 * 
	 * @see AnnotationScannerUtil#getMarked(Class,
	 *      java.lang.annotation.Annotation, boolean)
	 */
	@Test
	public void testScanInstance() {
		Assert.assertEquals(1, AnnotationScannerUtil.getMarked(TestAnnotation01.class, new TestAnnotation01Impl(), false).size());
	}

	/**
	 * Transitive annotation scanning.
	 * 
	 * @see AnnotationScannerUtil#getMarked(Class, Class, boolean)
	 */
	@Test
	public void testScanTransitive() {
		Assert.assertEquals(3, AnnotationScannerUtil.getMarked(TestAnnotation01.class, TestEntity01.class, true).size());
		Assert.assertEquals(3, AnnotationScannerUtil.getMarked(TestAnnotation02.class, TestEntity01.class, true).size());
		Assert.assertEquals(3, AnnotationScannerUtil.getMarked(TestAnnotation03.class, TestEntity01.class, true).size());
		Assert.assertEquals(3, AnnotationScannerUtil.getMarked(TestAnnotation01.class, TestEntity02.class, true).size());
		Assert.assertEquals(3, AnnotationScannerUtil.getMarked(TestAnnotation02.class, TestEntity02.class, true).size());
		Assert.assertEquals(3, AnnotationScannerUtil.getMarked(TestAnnotation03.class, TestEntity02.class, true).size());
	}

	/**
	 * Transitive annotation scanning.
	 * 
	 * @see AnnotationScannerUtil#getMarked(Class,
	 *      java.lang.annotation.Annotation, boolean)
	 */
	@Test
	public void testScanTransitiveInstance() {
		Assert.assertEquals(3, AnnotationScannerUtil.getMarked(TestAnnotation01.class, new TestAnnotation01Impl(), true).size());
	}

	/**
	 * Test annotation implementation.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	@SuppressWarnings("all")
	public class TestAnnotation01Impl
			implements TestAnnotation01 {
		/** {@inheritDoc} */
		@Override
		public Class<? extends Annotation> annotationType() {
			return TestAnnotation01.class;
		}
	}
}
