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

public class DecoratedSampleEntity09 extends SampleEntity09 {

	private int getCounter = 0;
	private int setCounter = 0;

	public int getGetCounter() {
		return this.getCounter;
	}

	@Override
	public SampleEntity07 getSampleEntity07() {
		this.getCounter++;
		return super.getSampleEntity07();
	}

	public int getSetCounter() {
		return this.setCounter;
	}

	public void init(final SampleEntity07 entity07) {
		super.setSampleEntity07(entity07);
	}

	@Override
	public void setSampleEntity07(final SampleEntity07 sampleEntity07) {
		this.setCounter++;
		super.setSampleEntity07(sampleEntity07);
	}
}
