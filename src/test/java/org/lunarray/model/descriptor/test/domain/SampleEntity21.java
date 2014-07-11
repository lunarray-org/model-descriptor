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

import java.util.Collection;

public class SampleEntity21
		implements ModelMarker {

	private Collection<SampleEntity18> collection;

	/**
	 * Gets the value for the collection field.
	 * 
	 * @return The value for the collection field.
	 */
	public final Collection<SampleEntity18> getCollection() {
		return this.collection;
	}

	/**
	 * Sets a new value for the collection field.
	 * 
	 * @param collection
	 *            The new value for the collection field.
	 */
	public final void setCollection(final Collection<SampleEntity18> collection) {
		this.collection = collection;
	}
}
