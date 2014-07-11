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
package org.lunarray.model.descriptor.mapping.impl;

import java.io.Serializable;
import java.util.Map;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.mapping.ModelConversionTool;
import org.lunarray.model.descriptor.mapping.impl.properties.exceptions.ConversionException;

/**
 * The model conversion tool.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ModelConversionToolImpl
		implements ModelConversionTool, Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 2447268754927331701L;
	/** The conversions. */
	private Map<ClassPair, EntityConversionImpl<?, ?>> entityConversion;

	/**
	 * Constructs the tool.
	 * 
	 * @param entityConversion
	 *            The conversion map.
	 */
	public ModelConversionToolImpl(final Map<ClassPair, EntityConversionImpl<?, ?>> entityConversion) {
		this.entityConversion = entityConversion;
	}

	/** {@inheritDoc} */
	@Override
	public <E, F> F convert(final E entity, final Class<F> targetType) throws ConversionException {
		F result = null;
		if (!CheckUtil.isNull(entity)) {
			final Class<?> entityType = entity.getClass();
			final ClassPair pair = new ClassPair(targetType, entityType);
			if (this.entityConversion.containsKey(pair)) {
				@SuppressWarnings("unchecked")
				final EntityConversionImpl<E, F> converter = (EntityConversionImpl<E, F>) this.entityConversion.get(pair);
				result = converter.convert(entity, this);
			} else {
				throw new ConversionException(String.format("Could not convert '%s' to '%s'.", entity, targetType));
			}
		}
		return result;
	}

	/**
	 * Gets the value for the entityConversion field.
	 * 
	 * @return The value for the entityConversion field.
	 */
	public Map<ClassPair, EntityConversionImpl<?, ?>> getEntityConversion() {
		return this.entityConversion;
	}

	/**
	 * Sets a new value for the entityConversion field.
	 * 
	 * @param entityConversion
	 *            The new value for the entityConversion field.
	 */
	public void setEntityConversion(final Map<ClassPair, EntityConversionImpl<?, ?>> entityConversion) {
		this.entityConversion = entityConversion;
	}
}
