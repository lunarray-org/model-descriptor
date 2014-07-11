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
package org.lunarray.model.descriptor.serialization;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.lunarray.common.check.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains information about a field.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class FieldInfo
		implements Serializable {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FieldInfo.class);
	/** Serial id. */
	private static final long serialVersionUID = -3044477893395582320L;
	/** The containing type. */
	private Class<?> containingType;
	/** The field name. */
	private String name;

	/**
	 * Default constructor.
	 * 
	 * @param field
	 *            The field.
	 */
	public FieldInfo(final Field field) {
		if (!CheckUtil.isNull(field)) {
			this.containingType = field.getDeclaringClass();
			this.name = field.getName();
		}
	}

	/**
	 * Gets the value for the containingType field.
	 * 
	 * @return The value for the containingType field.
	 */
	public Class<?> getContainingType() {
		return this.containingType;
	}

	/**
	 * Gets the value for the name field.
	 * 
	 * @return The value for the name field.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Resolves the field.
	 * 
	 * @return The field.
	 */
	public Field resolveField() {
		Field result = null;
		if (!CheckUtil.isNull(this.containingType) && !CheckUtil.isNull(this.name)) {
			try {
				result = this.containingType.getDeclaredField(this.name);
			} catch (final SecurityException e) {
				FieldInfo.LOGGER.warn("Could not access field.", e);
			} catch (final NoSuchFieldException e) {
				FieldInfo.LOGGER.warn("Could not find field.", e);
			}
		}
		return result;
	}

	/**
	 * Sets a new value for the containingType field.
	 * 
	 * @param containingType
	 *            The new value for the containingType field.
	 */
	public void setContainingType(final Class<?> containingType) {
		this.containingType = containingType;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
