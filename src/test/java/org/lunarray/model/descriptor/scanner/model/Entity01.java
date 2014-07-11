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
package org.lunarray.model.descriptor.scanner.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.lunarray.model.descriptor.scanner.annotation.Describes;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation04;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation05;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation06;

/**
 * Test entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
@Describes({ EntityAnnotation04.class, EntityAnnotation05.class, EntityAnnotation06.class })
public class Entity01
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 3420282695126566342L;
	private List<String> value;

	public Entity01() {
		this.value = new LinkedList<String>();
	}

	public List<String> getValue() {
		return this.value;
	}

	public void setValue(final List<String> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Entity01" + this.value.toString();
	}
}
