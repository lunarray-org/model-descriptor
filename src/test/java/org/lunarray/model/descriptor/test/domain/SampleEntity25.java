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

import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;

public class SampleEntity25 {

	@PresentationHint(order = 3)
	public void someMethod(
			@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier02.class, hint = @PresentationHint(render = RenderType.CHECKBOX, format = "%%")) }) final int arg0,
			final Object arg1) {
		// No content
	}

	@PresentationHint(order = 1)
	public void someMethod2(final SampleEntity25 arg0) {
		// No content
	}

	@QualifierPresentationHints(value = { @QualifierPresentationHint(name = Qualifier01.class, hint = @PresentationHint(order = 0)) })
	@PresentationHint(order = 2)
	public void someMethod3(final int arg0, final Object arg1, final SampleEntity25 arg2) {
		// No content
	}

	public void someMethod4(final List<Object> arg0, final List<SampleEntity25> arg1) {
		// No content
	}
}
