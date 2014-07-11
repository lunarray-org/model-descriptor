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

import java.math.BigInteger;
import java.util.Calendar;

import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;

public class SampleEntity18
		implements ModelMarker {

	private SampleEntity17 test01;

	private boolean test02;

	private BigInteger test03;

	private String test04;

	@PresentationHint(format = "ddmmyyyy")
	private Calendar test05;

	private SampleEnum01 test06;

	private SampleEntity01 test07;

	@PresentationHint(render = RenderType.RADIO)
	private SampleEnum01 test08;

	public SampleEntity17 getTest01() {
		return this.test01;
	}

	public BigInteger getTest03() {
		return this.test03;
	}

	public String getTest04() {
		return this.test04;
	}

	public Calendar getTest05() {
		return this.test05;
	}

	public SampleEnum01 getTest06() {
		return this.test06;
	}

	public SampleEntity01 getTest07() {
		return this.test07;
	}

	public SampleEnum01 getTest08() {
		return this.test08;
	}

	public boolean isTest02() {
		return this.test02;
	}

	public void setTest01(final SampleEntity17 test01) {
		this.test01 = test01;
	}

	public void setTest02(final boolean test02) {
		this.test02 = test02;
	}

	public void setTest03(final BigInteger test03) {
		this.test03 = test03;
	}

	public void setTest04(final String test04) {
		this.test04 = test04;
	}

	public void setTest05(final Calendar test05) {
		this.test05 = test05;
	}

	public void setTest06(final SampleEnum01 test06) {
		this.test06 = test06;
	}

	public void setTest07(final SampleEntity01 test07) {
		this.test07 = test07;
	}

	public void setTest08(final SampleEnum01 test08) {
		this.test08 = test08;
	}
}
