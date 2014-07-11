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
package org.lunarray.model.descriptor.test.domain;

import org.lunarray.model.descriptor.model.annotations.EntityName;

@EntityName("entity06")
public class SampleEntity06 implements ModelMarker {

	private String testField;

	private Integer testField2;

	public String getTestField() {
		return this.testField;
	}

	public Integer getTestField2() {
		return this.testField2;
	}

	public void setTestField(final String testField) {
		this.testField = testField;
	}

	public void setTestField2(final Integer testField2) {
		this.testField2 = testField2;
	}
}
