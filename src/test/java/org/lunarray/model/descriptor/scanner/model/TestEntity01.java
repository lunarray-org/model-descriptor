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
package org.lunarray.model.descriptor.scanner.model;

import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation04;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation08;
import org.lunarray.model.descriptor.scanner.annotations.TestAnnotation01;
import org.lunarray.model.descriptor.test.Entity02;
import org.lunarray.model.descriptor.test.Entity03;

/**
 * Test entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
@TestAnnotation01
@EntityAnnotation04("test")
@EntityAnnotation08(annotationValues = { @TestAnnotation01, @TestAnnotation01 }, byteValues = { 0x04, 0x05 }, charValues = { 'a', 'c' }, clazzValues = {
		Entity02.class, Entity03.class }, doubleValues = { 6.00, 7.00 }, enumValues = { TestEnum01.VALUE_01, TestEnum01.VALUE_02 }, floatValues = {
		8.00f, 9.00f }, intValues = { 10, 11 }, longValues = { 12, 13 }, shortValues = { 14, 15 }, stringValues = { "test01", "test02" })
public class TestEntity01 {

	@EntityAnnotation04("test01")
	private String someField;

	/**
	 * Gets the value for the someField field.
	 * 
	 * @return The value for the someField field.
	 */
	public String getSomeField() {
		return this.someField;
	}

	/**
	 * Sets a new value for the someField field.
	 * 
	 * @param someField
	 *            The new value for the someField field.
	 */
	public void setSomeField(final String someField) {
		this.someField = someField;
	}
}
