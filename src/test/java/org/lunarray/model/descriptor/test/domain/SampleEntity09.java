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

import org.lunarray.model.descriptor.model.annotations.Embedded;
import org.lunarray.model.descriptor.model.annotations.Ignore;

public class SampleEntity09 implements ModelMarker {

	@Embedded
	private SampleEntity07 sampleEntity07;

	@Ignore
	private String testValue;

	public SampleEntity07 getSampleEntity07() {
		return this.sampleEntity07;
	}

	public String getTestValue() {
		return this.testValue;
	}

	public void setSampleEntity07(final SampleEntity07 sampleEntity07) {
		this.sampleEntity07 = sampleEntity07;
	}

	public void setTestValue(final String testValue) {
		this.testValue = testValue;
	}
}
