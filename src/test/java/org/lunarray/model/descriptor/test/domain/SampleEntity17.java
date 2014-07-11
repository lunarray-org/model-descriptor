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

@EntityPresentationHint(resourceBundle = "org.lunarray.test.Labels")
public class SampleEntity17
		implements ModelMarker {

	@PresentationHint(required = BooleanInherit.INHERIT, immutable = BooleanInherit.INHERIT, inLine = BooleanInherit.INHERIT)
	private SampleEntity18 test1;

	@PresentationHint(required = BooleanInherit.INHERIT, immutable = BooleanInherit.INHERIT, inLine = BooleanInherit.INHERIT)
	private int test10;

	@PresentationHint(required = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE, inLine = BooleanInherit.TRUE)
	private int test11;

	@PresentationHint(required = BooleanInherit.FALSE, immutable = BooleanInherit.FALSE, inLine = BooleanInherit.FALSE)
	private int test12;

	@PresentationHint(required = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE, inLine = BooleanInherit.TRUE)
	private SampleEntity18 test2;

	@PresentationHint(required = BooleanInherit.FALSE, immutable = BooleanInherit.FALSE, inLine = BooleanInherit.FALSE)
	private SampleEntity18 test3;

	@PresentationHint(required = BooleanInherit.INHERIT, immutable = BooleanInherit.INHERIT, inLine = BooleanInherit.INHERIT)
	private SampleEntity18 test4;

	@PresentationHint(required = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE, inLine = BooleanInherit.TRUE)
	private SampleEntity18 test5;

	@PresentationHint(required = BooleanInherit.FALSE, immutable = BooleanInherit.FALSE, inLine = BooleanInherit.FALSE)
	private SampleEntity18 test6;

	@PresentationHint(required = BooleanInherit.INHERIT, immutable = BooleanInherit.INHERIT, inLine = BooleanInherit.INHERIT)
	private final SampleEntity18 test7 = new SampleEntity18();

	@PresentationHint(required = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE, inLine = BooleanInherit.TRUE)
	private final SampleEntity18 test8 = new SampleEntity18();

	@PresentationHint(required = BooleanInherit.FALSE, immutable = BooleanInherit.FALSE, inLine = BooleanInherit.FALSE)
	private final SampleEntity18 test9 = new SampleEntity18();

	public SampleEntity18 getTest1() {
		return this.test1;
	}

	public int getTest10() {
		return this.test10;
	}

	public int getTest11() {
		return this.test11;
	}

	public int getTest12() {
		return this.test12;
	}

	public SampleEntity18 getTest2() {
		return this.test2;
	}

	public SampleEntity18 getTest3() {
		return this.test3;
	}

	public SampleEntity18 getTest4() {
		return this.test4;
	}

	public SampleEntity18 getTest5() {
		return this.test5;
	}

	public SampleEntity18 getTest6() {
		return this.test6;
	}

	public SampleEntity18 getTest7() {
		return this.test7;
	}

	public SampleEntity18 getTest8() {
		return this.test8;
	}

	public SampleEntity18 getTest9() {
		return this.test9;
	}

	public void setTest1(final SampleEntity18 test1) {
		this.test1 = test1;
	}

	public void setTest10(final int test10) {
		this.test10 = test10;
	}

	public void setTest11(final int test11) {
		this.test11 = test11;
	}

	public void setTest12(final int test12) {
		this.test12 = test12;
	}

	public void setTest2(final SampleEntity18 test2) {
		this.test2 = test2;
	}

	public void setTest3(final SampleEntity18 test3) {
		this.test3 = test3;
	}

}
