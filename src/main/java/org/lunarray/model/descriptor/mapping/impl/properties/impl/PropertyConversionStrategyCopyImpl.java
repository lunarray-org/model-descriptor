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

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.converter.ConverterTool;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.mapping.ModelConversionTool;
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.mapping.impl.properties.exceptions.ConversionException;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The property conversion strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The source model.
 * @param <T>
 *            The target model.
 * @param <E>
 *            The source entity.
 * @param <F>
 *            The target entity.
 * @param <P>
 *            The property.
 */
public final class PropertyConversionStrategyCopyImpl<S, T, E, F, P>
		extends AbstractPropertyConversionStrategyImpl<S, T, E, F, P, P> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConversionStrategyCopyImpl.class);
	/** Serial id. */
	private static final long serialVersionUID = 4476629092424343541L;

	/**
	 * Creates the strategy.
	 * 
	 * @param source
	 *            The source property. May not be null.
	 * @param target
	 *            The target property. May not be null.
	 * @param targetModel
	 *            The target model. May not be null.
	 * @param sourceModel
	 *            The source model. May not be null.
	 * @return The strategy.
	 * @param <S>
	 *            The source model type.
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
	public static <S, T, E, F, P, Q> PropertyConversionStrategy<E, F> create(final PropertyDescriptor<P, E> source,
			final PropertyDescriptor<Q, F> target, final Model<T> targetModel, final Model<S> sourceModel) {
		Validate.notNull(sourceModel, "Source model may not be null.");
		Validate.notNull(targetModel, "Target model may not be null.");
		final PropertyDescriptor<P, E> sourceProperty = source;
		@SuppressWarnings("unchecked")
		final PropertyDescriptor<P, F> targetProperty = (PropertyDescriptor<P, F>) target;
		return new PropertyConversionStrategyCopyImpl<S, T, E, F, P>(sourceProperty, targetProperty, targetModel, sourceModel);
	}

	/**
	 * Creates the strategy.
	 * 
	 * @param sourceProperty
	 *            The source property.
	 * @param targetProperty
	 *            The target property.
	 * @param targetModel
	 *            The target model.
	 * @param sourceModel
	 *            The source model.
	 */
	private PropertyConversionStrategyCopyImpl(final PropertyDescriptor<P, E> sourceProperty,
			final PropertyDescriptor<P, F> targetProperty, final Model<T> targetModel, final Model<S> sourceModel) {
		super(sourceProperty, targetProperty, targetModel, sourceModel);
	}

	/** {@inheritDoc} */
	@Override
	public void apply(final E source, final F target, final ModelConversionTool conversionTool) throws ConversionException {
		try {
			final PropertyDescriptor<P, E> sourcePropertyDescriptor = this.getSourceProperty();
			final PropertyDescriptor<P, F> targetPropertyDescriptor = this.getTargetProperty();
			PropertyConversionStrategyCopyImpl.LOGGER.debug("Converting from property {} to {} for entity {} to {}",
					sourcePropertyDescriptor, targetPropertyDescriptor, source, target);
			P value = sourcePropertyDescriptor.getValue(source);
			if (!targetPropertyDescriptor.isAssignable(value)) {
				final ConverterTool sourceConverter = this.getSourceModel().getExtension(ConverterTool.class);
				final ConverterTool targetConverter = this.getTargetModel().getExtension(ConverterTool.class);
				if (CheckUtil.isNull(sourceConverter) || CheckUtil.isNull(targetConverter)) {
					throw new ConversionException("Could not find converter.");
				} else {
					final String stringValue = sourceConverter.convertToString(sourcePropertyDescriptor.getPropertyType(), value);
					value = targetConverter.convertToInstance(targetPropertyDescriptor.getPropertyType(), stringValue);
				}
			}
			targetPropertyDescriptor.setValue(target, value);
		} catch (final ValueAccessException e) {
			throw new ConversionException(e);
		} catch (final ConverterException e) {
			throw new ConversionException(e);
		}
	}
}
