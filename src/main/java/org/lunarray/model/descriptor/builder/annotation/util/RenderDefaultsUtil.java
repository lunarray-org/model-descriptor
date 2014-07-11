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
package org.lunarray.model.descriptor.builder.annotation.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.presentation.RenderType;

/**
 * Render defaults utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum RenderDefaultsUtil {

	/** Singleton instance. */
	INSTANCE;

	/** The map of defaults. */
	private final Map<Class<?>, RenderType> renderDefaults;

	/**
	 * Fills the defaults.
	 */
	private RenderDefaultsUtil() {
		this.renderDefaults = new HashMap<Class<?>, RenderType>();
		this.renderDefaults.put(Calendar.class, RenderType.DATE_PICKER);
		this.renderDefaults.put(Date.class, RenderType.DATE_PICKER);
		this.renderDefaults.put(java.sql.Date.class, RenderType.DATE_PICKER);
		this.renderDefaults.put(String.class, RenderType.TEXT);
		this.renderDefaults.put(Integer.class, RenderType.TEXT);
		this.renderDefaults.put(Double.class, RenderType.TEXT);
		this.renderDefaults.put(Float.class, RenderType.TEXT);
		this.renderDefaults.put(Long.class, RenderType.TEXT);
		this.renderDefaults.put(Byte.class, RenderType.TEXT);
		this.renderDefaults.put(Short.class, RenderType.TEXT);
		this.renderDefaults.put(Character.class, RenderType.TEXT);
		this.renderDefaults.put(Integer.TYPE, RenderType.TEXT);
		this.renderDefaults.put(Double.TYPE, RenderType.TEXT);
		this.renderDefaults.put(Float.TYPE, RenderType.TEXT);
		this.renderDefaults.put(Long.TYPE, RenderType.TEXT);
		this.renderDefaults.put(Byte.TYPE, RenderType.TEXT);
		this.renderDefaults.put(Short.TYPE, RenderType.TEXT);
		this.renderDefaults.put(Character.TYPE, RenderType.TEXT);
		this.renderDefaults.put(BigDecimal.class, RenderType.TEXT);
		this.renderDefaults.put(BigInteger.class, RenderType.TEXT);
		this.renderDefaults.put(Boolean.class, RenderType.CHECKBOX);
		this.renderDefaults.put(Boolean.TYPE, RenderType.CHECKBOX);
	}

	/**
	 * Resolve the render type for a given type, or return defaultValue.
	 * 
	 * @param type
	 *            The type. May not be null.
	 * @param defaultValue
	 *            The default value.
	 * @return The render type.
	 */
	public RenderType resolve(final Class<?> type, final RenderType defaultValue) {
		Validate.notNull(type, "Type may not be null.");
		RenderType result;
		if (this.renderDefaults.containsKey(type)) {
			result = this.renderDefaults.get(type);
		} else {
			result = defaultValue;
		}
		return result;
	}
}
