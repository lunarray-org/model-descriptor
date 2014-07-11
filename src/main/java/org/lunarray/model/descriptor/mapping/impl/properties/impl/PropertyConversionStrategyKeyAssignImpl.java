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
package org.lunarray.model.descriptor.mapping.impl.properties.impl;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.converter.ConverterTool;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.mapping.ModelConversionTool;
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.mapping.impl.properties.exceptions.ConversionException;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A property conversion strategy for assigning a key.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The source model super type.
 * @param <T>
 *            The target model super type.
 * @param <E>
 *            The source entity type.
 * @param <F>
 *            The target entity type.
 * @param <P>
 *            The source property type.
 * @param <Q>
 *            The target property type.
 */
public final class PropertyConversionStrategyKeyAssignImpl<S, T, E, F, P extends S, Q extends Serializable>
		extends AbstractPropertyConversionStrategyImpl<S, T, E, F, P, Q> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConversionStrategyKeyAssignImpl.class);
	/** Serial id. */
	private static final long serialVersionUID = 689325609234636990L;

	/**
	 * Create the strategy.
	 * 
	 * @param source
	 *            The source property. May not be null.
	 * @param target
	 *            The target property. May not be null.
	 * @param sourceModel
	 *            The source model. May not be null.
	 * @param targetModel
	 *            The target model. May not be null.
	 * @return The conversion strategy.
	 * @param <S>
	 *            The source type.
	 * @param <T>
	 *            The target model type.
	 * @param <P>
	 *            The source property.
	 * @param <E>
	 *            The source entity.
	 * @param <Q>
	 *            The target property.
	 * @param <F>
	 *            The target entity.
	 */
	public static <S, T, E extends S, F, P, Q> PropertyConversionStrategy<E, F> create(final PropertyDescriptor<P, E> source,
			final PropertyDescriptor<Q, F> target, final Model<S> sourceModel, final Model<T> targetModel) {
		Validate.notNull(sourceModel, "Source model may not be null.");
		Validate.notNull(targetModel, "Target model may not be null.");
		@SuppressWarnings("unchecked")
		final PropertyDescriptor<S, E> sourceProperty = (PropertyDescriptor<S, E>) source;
		@SuppressWarnings("unchecked")
		final PropertyDescriptor<Serializable, F> targetProperty = (PropertyDescriptor<Serializable, F>) target;
		return new PropertyConversionStrategyKeyAssignImpl<S, T, E, F, S, Serializable>(sourceProperty, targetProperty, sourceModel,
				targetModel);
	}

	/**
	 * Constructs the strategy.
	 * 
	 * @param sourceProperty
	 *            The source property.
	 * @param targetProperty
	 *            The target property.
	 * @param sourceModel
	 *            The source model.
	 * @param targetModel
	 *            The target model.
	 */
	private PropertyConversionStrategyKeyAssignImpl(final PropertyDescriptor<P, E> sourceProperty,
			final PropertyDescriptor<Q, F> targetProperty, final Model<S> sourceModel, final Model<T> targetModel) {
		super(sourceProperty, targetProperty, targetModel, sourceModel);
	}

	/** {@inheritDoc} */
	@Override
	public void apply(final E source, final F target, final ModelConversionTool conversionTool) throws ConversionException {
		try {
			final PropertyDescriptor<P, E> sourcePropertyDescriptor = this.getSourceProperty();
			final PropertyDescriptor<Q, F> targetPropertyDescriptor = this.getTargetProperty();
			PropertyConversionStrategyKeyAssignImpl.LOGGER.debug("Converting from property {} to {} for entity {} to {}",
					sourcePropertyDescriptor, targetPropertyDescriptor, source, target);
			final P value = this.getSourceProperty().getValue(source);
			final EntityDescriptor<P> sourceDescriptor = this.getSourceModel().getEntity(this.getSourceProperty().getPropertyType());
			@SuppressWarnings("unchecked")
			final KeyedEntityDescriptor<P, Q> keyedTarget = sourceDescriptor.adapt(KeyedEntityDescriptor.class);
			final PropertyDescriptor<Q, P> keyProperty = keyedTarget.getKeyProperty();
			Q keyValue = keyProperty.getValue(value);
			if (!this.getTargetProperty().isAssignable(keyValue)) {
				final ConverterTool sourceConverter = this.getSourceModel().getExtension(ConverterTool.class);
				final ConverterTool targetConverter = this.getTargetModel().getExtension(ConverterTool.class);
				if (CheckUtil.isNull(sourceConverter) || CheckUtil.isNull(targetConverter)) {
					throw new ConversionException("Could not find converter.");
				} else {
					final String stringValue = sourceConverter.convertToString(keyProperty.getPropertyType(), keyValue);
					keyValue = targetConverter.convertToInstance(this.getTargetProperty().getPropertyType(), stringValue);
				}
			}
			this.getTargetProperty().setValue(target, keyValue);
		} catch (final ValueAccessException e) {
			throw new ConversionException(e);
		} catch (final ConverterException e) {
			throw new ConversionException(e);
		}
	}
}
