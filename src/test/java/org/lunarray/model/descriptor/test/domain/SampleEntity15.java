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

import org.lunarray.model.descriptor.presentation.annotations.EntityPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.util.BooleanInherit;

@EntityPresentationHint(descriptionKey = "entity15", resourceBundle = "org.lunarray.test.Labels")
public class SampleEntity15
		implements ModelMarker {

	@PresentationHint(order = 3, visible = BooleanInherit.TRUE, name = BooleanInherit.INHERIT)
	private String field1;

	@PresentationHint(order = 2, visible = BooleanInherit.FALSE, name = BooleanInherit.TRUE)
	private String field2;

	@PresentationHint(order = 1, visible = BooleanInherit.INHERIT, labelKey = "test.label", name = BooleanInherit.FALSE)
	private String field3;

	@PresentationHint(order = 4, visible = BooleanInherit.TRUE)
	private String field4;

	@PresentationHint(order = 5, visible = BooleanInherit.FALSE)
	private String field5;

	@PresentationHint(order = 6, visible = BooleanInherit.INHERIT)
	private String field6;

	public String get() {
		return this.field6;
	}

	public String get4() {
		return this.field4;
	}

	public String get5() {
		return this.field5;
	}

	public String getField1() {
		return this.field1;
	}

	public String getField2() {
		return this.field2;
	}

	public String getField3() {
		return this.field3;
	}

	public void setField1(final String field1) {
		this.field1 = field1;
	}

	public void setField2(final String field2) {
		this.field2 = field2;
	}

	public void setField3(final String field3) {
		this.field3 = field3;
	}

	public void setField4(final String field4) {
		this.field4 = field4;
	}

	public void setField5(final String field5) {
		this.field5 = field5;
	}

	public void setField6(final String field6) {
		this.field6 = field6;
	}
}
