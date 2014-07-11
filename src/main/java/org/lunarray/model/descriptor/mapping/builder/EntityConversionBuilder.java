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
package org.lunarray.model.descriptor.mapping.builder;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.mapping.impl.EntityConversionImpl;
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;

/**
 * The entity conversion builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The source entity.
 * @param <F>
 *            The target entity.
 */
public final class EntityConversionBuilder<E, F> {

	/** The strategies. */
	private final transient List<PropertyConversionStrategy<E, F>> propertyStrategies;
	/** The source entity. */
	private final transient EntityDescriptor<E> sourceEntity;
	/** The target entity. */
	private final transient EntityDescriptor<F> targetEntity;

	/**
	 * Creates the builder.
	 * 
	 * @param sourceEntity
	 *            The source. May not be null.
	 * @param targetEntity
	 *            The target. May not be null.
	 */
	public EntityConversionBuilder(final EntityDescriptor<E> sourceEntity, final EntityDescriptor<F> targetEntity) {
		Validate.notNull(sourceEntity, "Source may not be null.");
		Validate.notNull(targetEntity, "Target may not be null.");
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.propertyStrategies = new LinkedList<PropertyConversionStrategy<E, F>>();
	}

	/**
	 * Adds the property conversion.
	 * 
	 * @param strategy
	 *            The strategy to add.
	 * @return The builder.
	 */
	public EntityConversionBuilder<E, F> addPropertyConversion(final PropertyConversionStrategy<E, F> strategy) {
		if (!CheckUtil.isNull(strategy)) {
			this.propertyStrategies.add(strategy);
		}
		return this;
	}

	/**
	 * Builds the entity converter.
	 * 
	 * @return The entity converter.
	 */
	public EntityConversionImpl<E, F> build() {
		return new EntityConversionImpl<E, F>(this.propertyStrategies, this.sourceEntity, this.targetEntity);
	}
}
