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

import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.presentation.annotations.EntityPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.EntityQualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.EntityQualifierPresentationHints;
import org.lunarray.model.descriptor.presentation.annotations.PresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHint;
import org.lunarray.model.descriptor.presentation.annotations.QualifierPresentationHints;
import org.lunarray.model.descriptor.util.BooleanInherit;

@EntityPresentationHint(descriptionKey = "entity15", resourceBundle = "org.lunarray.test.Labels", visible = BooleanInherit.FALSE)
@EntityQualifierPresentationHints({
		@EntityQualifierPresentationHint(name = Qualifier01.class, hint = @EntityPresentationHint()),
		@EntityQualifierPresentationHint(name = Qualifier02.class, hint = @EntityPresentationHint(descriptionKey = "entity20", visible = BooleanInherit.TRUE, resourceBundle = "org.lunarray.test.Labels2")),
		@EntityQualifierPresentationHint(name = Qualifier03.class, hint = @EntityPresentationHint(descriptionKey = "entity20", resourceBundle = "org.lunarray.test.Labels2")) })
public class SampleEntity20
		implements ModelMarker {

	@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier02.class, hint =

	@PresentationHint(inLine = BooleanInherit.TRUE, labelKey = "field1.key", name = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE,

	render = RenderType.TEXT_AREA, order = 5, required = BooleanInherit.TRUE, visible = BooleanInherit.FALSE)) })
	private String value01;

	@PresentationHint(inLine = BooleanInherit.TRUE, labelKey = "field2.key", name = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE,

	render = RenderType.TEXT_AREA, order = 6, required = BooleanInherit.TRUE, visible = BooleanInherit.FALSE, format = "test4")
	private String value02;

	@PresentationHint(inLine = BooleanInherit.TRUE, labelKey = "field3.key", name = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE,

	render = RenderType.TEXT_AREA, order = 7, required = BooleanInherit.TRUE, visible = BooleanInherit.FALSE, format = "test")
	@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier02.class, hint =

	@PresentationHint(inLine = BooleanInherit.TRUE, labelKey = "field4.key", name = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE,

	render = RenderType.TEXT_AREA, order = 8, required = BooleanInherit.TRUE, visible = BooleanInherit.FALSE, format = "test2")) })
	private String value03;

	@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier02.class, hint =

	@PresentationHint(inLine = BooleanInherit.FALSE, labelKey = "field5.key", name = BooleanInherit.TRUE, immutable = BooleanInherit.TRUE,

	render = RenderType.TEXT_AREA, order = 9, required = BooleanInherit.TRUE, visible = BooleanInherit.FALSE)) })
	private final String value04 = "";

	@QualifierPresentationHints({ @QualifierPresentationHint(name = Qualifier02.class, hint = @PresentationHint(labelKey = "field6.key")),
			@QualifierPresentationHint(name = Qualifier03.class, hint = @PresentationHint()) })
	private String value05;

	public String getValue01() {
		return this.value01;
	}

	public String getValue02() {
		return this.value02;
	}

	public String getValue03() {
		return this.value03;
	}

	public String getValue04() {
		return this.value04;
	}

	public String getValue05() {
		return this.value05;
	}

	public void setValue01(final String value01) {
		this.value01 = value01;
	}

	public void setValue02(final String value02) {
		this.value02 = value02;
	}

	public void setValue03(final String value03) {
		this.value03 = value03;
	}

	public void setValue05(final String value05) {
		this.value05 = value05;
	}
}
