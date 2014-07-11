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

@EntityPresentationHint(descriptionKey = "entity13", visible = BooleanInherit.TRUE)
public class SampleEntity13
		implements ModelMarker {

	@PresentationHint(labelKey = "field.label")
	private String descriptionField;

	@PresentationHint(name = BooleanInherit.TRUE)
	private String name;

	public String getDescriptionField() {
		return this.descriptionField;
	}

	public String getName() {
		return this.name;
	}

	public void setDescriptionField(final String descriptionField) {
		this.descriptionField = descriptionField;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
