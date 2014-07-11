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

@EntityPresentationHint(resourceBundle = "org.lunarray.test.Labels")
public class SampleEntity16 implements ModelMarker {

	@PresentationHint()
	private String field1;

	@PresentationHint()
	private String field2;

	@PresentationHint()
	private final String field3 = "field3";

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
}
