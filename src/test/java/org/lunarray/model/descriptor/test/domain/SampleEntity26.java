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

import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;

public class SampleEntity26 {

	@QualifierPresentationHints(value = { @QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(visible = BooleanInherit.FALSE, inLine = BooleanInherit.TRUE)) })
	@PresentationHint(order = 3)
	public SampleEntity25 someMethod() {
		return null;
	}

	@PresentationHint(order = 1)
	public int someMethod2() {
		return -1;
	}

	@QualifierPresentationHints(value = { @QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(order = 0, visible = BooleanInherit.TRUE, format = "test")) })
	@PresentationHint(order = 2, inLine = BooleanInherit.TRUE, format = "test3")
	public SampleEntity25 someMethod3() {
		return null;
	}

	@PresentationHint(visible = BooleanInherit.FALSE)
	public void someMethod4() {
		// No content
	}

	@QualifierPresentationHints(value = { @QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(visible = BooleanInherit.FALSE)) })
	@PresentationHint(order = 3)
	public List<SampleEntity25> someMethod5() {
		return null;
	}

	@PresentationHint(order = 1, format = "test2")
	public Integer someMethod6() {
		return null;
	}

	@QualifierPresentationHints(value = { @QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(order = 0, visible = BooleanInherit.TRUE)) })
	@PresentationHint(order = 2)
	public List<SampleEntity25> someMethod7() {
		return null;
	}

	@PresentationHint(visible = BooleanInherit.FALSE)
	public void someMethod8() {
		// No content
	}
}
