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
package org.lunarray.model.descriptor.util;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.model.member.Cardinality;

/**
 * A general utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum ModelBuilderUtil {

	/** Instance. */
	INSTANCE;

	/**
	 * Resolves a type's cardinality.
	 * 
	 * @param type
	 *            The type. May not be null.
	 * @return Single if the type is a primitive, multiple if the type is a
	 *         collection, else nullable.
	 */
	public static Cardinality resolveCardinality(final Class<?> type) {
		Validate.notNull(type, "Type may not be null.");
		// Get cardinality
		final Cardinality cardinality;
		if (Void.class.equals(type) || Void.TYPE.equals(type)) {
			cardinality = Cardinality.NONE;
		} else if (type.isPrimitive()) {
			cardinality = Cardinality.SINGLE;
		} else if (Collection.class.isAssignableFrom(type)) {
			cardinality = Cardinality.MULTIPLE;
		} else {
			cardinality = Cardinality.NULLABLE;
		}
		return cardinality;
	}
}
