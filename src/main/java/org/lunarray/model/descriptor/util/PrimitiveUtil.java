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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * A utility to handle primitives.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum PrimitiveUtil {
	/** The util instance. */
	INSTANCE;

	/**
	 * Gets the object type associated with the primitive type.
	 * 
	 * @param primitiveType
	 *            The primitive type. May not be null.
	 * @return The object type or null.
	 */
	public static Class<?> getObjectType(final Class<?> primitiveType) {
		Validate.notNull(primitiveType, "May not be null.");
		return INSTANCE.innerGetObjectType(primitiveType);
	}

	/** Map describing the relation from primitives to classes. */
	private Map<Class<?>, Class<?>> primitivesToClasses;

	/**
	 * Default constructor.
	 */
	private PrimitiveUtil() {
		this.primitivesToClasses = new HashMap<Class<?>, Class<?>>();
		this.primitivesToClasses.put(Byte.TYPE, Byte.class);
		this.primitivesToClasses.put(Integer.TYPE, Integer.class);
		this.primitivesToClasses.put(Double.TYPE, Double.class);
		this.primitivesToClasses.put(Float.TYPE, Float.class);
		this.primitivesToClasses.put(Long.TYPE, Long.class);
		this.primitivesToClasses.put(Short.TYPE, Short.class);
		this.primitivesToClasses.put(Character.TYPE, Character.class);
		this.primitivesToClasses.put(Boolean.TYPE, Boolean.class);
	}

	/**
	 * Gets the object type associated with the primitive type.
	 * 
	 * @param primitiveType
	 *            The primitive type.
	 * @return The object type or null.
	 */
	private Class<?> innerGetObjectType(final Class<?> primitiveType) {
		Class<?> result = null;
		if (this.primitivesToClasses.containsKey(primitiveType)) {
			result = this.primitivesToClasses.get(primitiveType);
		}
		return result;
	}
}
