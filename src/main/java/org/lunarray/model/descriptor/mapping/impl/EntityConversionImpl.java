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
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.mapping.ModelConversionTool;
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.mapping.impl.properties.exceptions.ConversionException;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;

/**
 * An entity conversion implementation.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The source entity.
 * @param <F>
 *            The target entity.
 */
public final class EntityConversionImpl<E, F>
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = -3563375437007591459L;
	/** The strategies. */
	private List<PropertyConversionStrategy<E, F>> propertyStrategies;
	/** The source entity. */
	private EntityDescriptor<E> sourceEntity;
	/** The target entity. */
	private EntityDescriptor<F> targetEntity;

	/**
	 * Default constructor.
	 * 
	 * @param propertyStrategies
	 *            The property strategies. May not be null.
	 * @param sourceEntity
	 *            The source entity type. May not be null.
	 * @param targetEntity
	 *            The target entity type. May not be null.
	 */
	public EntityConversionImpl(final List<PropertyConversionStrategy<E, F>> propertyStrategies, final EntityDescriptor<E> sourceEntity,
			final EntityDescriptor<F> targetEntity) {
		Validate.notNull(targetEntity, "Target may not be null.");
		Validate.notNull(sourceEntity, "Source may not be null.");
		Validate.notNull(propertyStrategies, "Strategies may not be null");
		this.propertyStrategies = propertyStrategies;
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
	}

	/**
	 * Converts from source to target.
	 * 
	 * @param source
	 *            The source.
	 * @param conversionTool
	 *            The conversion tool.
	 * @return The target.
	 * @throws ConversionException
	 *             Thrown if the conversion failed.
	 */
	public F convert(final E source, final ModelConversionTool conversionTool) throws ConversionException {
		F instance;
		try {
			instance = this.targetEntity.createEntity();
		} catch (final CreationException e) {
			throw new ConversionException(e);
		}
		for (final PropertyConversionStrategy<E, F> conversion : this.propertyStrategies) {
			conversion.apply(source, instance, conversionTool);
		}
		return instance;
	}

	/**
	 * Gets the value for the propertyStrategies field.
	 * 
	 * @return The value for the propertyStrategies field.
	 */
	public List<PropertyConversionStrategy<E, F>> getPropertyStrategies() {
		return this.propertyStrategies;
	}

	/**
	 * Gets the value for the sourceEntity field.
	 * 
	 * @return The value for the sourceEntity field.
	 */
	public EntityDescriptor<E> getSourceEntity() {
		return this.sourceEntity;
	}

	/**
	 * Gets the value for the targetEntity field.
	 * 
	 * @return The value for the targetEntity field.
	 */
	public EntityDescriptor<F> getTargetEntity() {
		return this.targetEntity;
	}

	/**
	 * Sets a new value for the propertyStrategies field.
	 * 
	 * @param propertyStrategies
	 *            The new value for the propertyStrategies field.
	 */
	public void setPropertyStrategies(final List<PropertyConversionStrategy<E, F>> propertyStrategies) {
		this.propertyStrategies = propertyStrategies;
	}

	/**
	 * Sets a new value for the sourceEntity field.
	 * 
	 * @param sourceEntity
	 *            The new value for the sourceEntity field.
	 */
	public void setSourceEntity(final EntityDescriptor<E> sourceEntity) {
		this.sourceEntity = sourceEntity;
	}

	/**
	 * Sets a new value for the targetEntity field.
	 * 
	 * @param targetEntity
	 *            The new value for the targetEntity field.
	 */
	public void setTargetEntity(final EntityDescriptor<F> targetEntity) {
		this.targetEntity = targetEntity;
	}
}
