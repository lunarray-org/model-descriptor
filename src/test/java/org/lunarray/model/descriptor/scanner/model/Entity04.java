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

import org.lunarray.model.descriptor.scanner.annotation.Describes;
import org.lunarray.model.descriptor.scanner.annotations.TestAnnotation01;

/**
 * Test entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
@Describes(TestAnnotation01.class)
public class Entity04
		implements Serializable {

	/** The serial id. */
	private static final long serialVersionUID = 1172429900477012280L;

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Entity04";
	}
}
