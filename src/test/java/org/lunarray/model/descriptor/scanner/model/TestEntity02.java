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
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation05;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation06;
import org.lunarray.model.descriptor.scanner.annotations.TestAnnotation03;

/**
 * Test entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
@TestAnnotation03
@EntityAnnotation05({ "testValue1", "testValue2" })
@EntityAnnotation06(value = { 6, 8, 13 }, innerAnnotation = @EntityAnnotation04("InnerTestValue01"), innerAnnotations = {
		@EntityAnnotation05("TestCollection01"), @EntityAnnotation05("TestCollection02"), @EntityAnnotation05("TestCollection03") })
public class TestEntity02 {

	@EntityAnnotation05({ "testValue3", "testValue4" })
	@EntityAnnotation06(value = { 20 }, innerAnnotation = @EntityAnnotation04("InnerTestValue04"), innerAnnotations = {
			@EntityAnnotation05("TestCollection06"), @EntityAnnotation05("TestCollection07"), @EntityAnnotation05("TestCollection08") })
	private String testProperty;

	/**
	 * Gets the value for the testProperty field.
	 * 
	 * @return The value for the testProperty field.
	 */
	public String getTestProperty() {
		return this.testProperty;
	}

	/**
	 * Sets a new value for the testProperty field.
	 * 
	 * @param testProperty
	 *            The new value for the testProperty field.
	 */
	public void setTestProperty(final String testProperty) {
		this.testProperty = testProperty;
	}
}
