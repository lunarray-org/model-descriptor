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

import java.util.List;

public class SampleEntity08 implements ModelMarker {

	private boolean field;

	private String field2;

	private final int field3;

	private String field4;

	private List<String> field5;

	private SampleEntity07 sampleEntity07;

	public SampleEntity08() {
		this.field3 = 5;
	}

	public String getField2() {
		return this.field2;
	}

	public int getField3() {
		return this.field3;
	}

	public String getField4() {
		return this.field4;
	}

	public List<String> getField5() {
		return this.field5;
	}

	public SampleEntity07 getSampleEntity07() {
		return this.sampleEntity07;
	}

	public boolean isField() {
		return this.field;
	}

	public void setField(final boolean field) {
		this.field = field;
	}

	public void setField2(final String field2) {
		this.field2 = field2;
	}

	public void setField5(final List<String> field5) {
		this.field5 = field5;
	}

	public void setSampleEntity07(final SampleEntity07 sampleEntity07) {
		this.sampleEntity07 = sampleEntity07;
	}
}
