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
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.dictionary.exceptions.DictionaryException;
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
 * Property conversion strategy that assigns after a lookup.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The source model type.
 * @param <T>
 *            The target model.
 * @param <E>
 *            The source entity.
 * @param <F>
 *            The target entity.
 * @param <P>
 *            The source property.
 * @param <Q>
 *            The target property.
 */
public final class PropertyConversionStrategyLookupAssignImpl<S, T, E, F extends T, P extends Serializable, Q extends T>
		extends AbstractPropertyConversionStrategyImpl<S, T, E, F, P, Q> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConversionStrategyLookupAssignImpl.class);
	/** Serial id. */
	private static final long serialVersionUID = -7506552168109833919L;

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
	 * @param <P>
	 *            The source property.
	 * @param <E>
	 *            The source entity.
	 * @param <T>
	 *            The target type.
	 * @param <Q>
	 *            The target property.
	 * @param <F>
	 *            The target entity.
	 */
	@SuppressWarnings("unchecked")
	public static <S, T, E, F, P, Q> PropertyConversionStrategy<E, F> create(final PropertyDescriptor<P, E> source,
			final PropertyDescriptor<Q, F> target, final Model<T> targetModel, final Model<S> sourceModel) {
		Validate.notNull(sourceModel, "Source model may not be null.");
		Validate.notNull(targetModel, "Target model may not be null.");
		final PropertyDescriptor<Serializable, E> sourceProperty = (PropertyDescriptor<Serializable, E>) source;
		final PropertyDescriptor<T, T> targetProperty = (PropertyDescriptor<T, T>) target;
		return (PropertyConversionStrategy<E, F>) new PropertyConversionStrategyLookupAssignImpl<S, T, E, T, Serializable, T>(
				sourceProperty, targetProperty, targetModel, sourceModel);
	}

	/**
	 * Constructs the conversion.
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
	private PropertyConversionStrategyLookupAssignImpl(final PropertyDescriptor<P, E> sourceProperty,
			final PropertyDescriptor<Q, F> targetProperty, final Model<T> targetModel, final Model<S> sourceModel) {
		super(sourceProperty, targetProperty, targetModel, sourceModel);
	}

	/** {@inheritDoc} */
	@Override
	public void apply(final E source, final F target, final ModelConversionTool conversionTool) throws ConversionException {
		try {
			final PropertyDescriptor<P, E> sourcePropertyDescriptor = this.getSourceProperty();
			final PropertyDescriptor<Q, F> targetPropertyDescriptor = this.getTargetProperty();
			PropertyConversionStrategyLookupAssignImpl.LOGGER.debug("Converting from property {} to {} for entity {} to {}",
					sourcePropertyDescriptor, targetPropertyDescriptor, source, target);
			final P value = sourcePropertyDescriptor.getValue(source);
			final Dictionary dictionary = this.getTargetModel().getExtension(Dictionary.class);
			final EntityDescriptor<Q> targetDescriptor = this.getTargetModel().getEntity(targetPropertyDescriptor.getPropertyType());
			@SuppressWarnings("unchecked")
			final KeyedEntityDescriptor<Q, P> keyedTarget = targetDescriptor.adapt(KeyedEntityDescriptor.class);
			final PropertyDescriptor<P, Q> targetKeyProperty = keyedTarget.getKeyProperty();
			final Q entityValue = dictionary.lookup(keyedTarget, this.convertValue(value, targetKeyProperty));
			targetPropertyDescriptor.setValue(target, entityValue);
		} catch (final DictionaryException e) {
			throw new ConversionException(e);
		} catch (final ValueAccessException e) {
			throw new ConversionException(e);
		}
	}

	/**
	 * Convert the value.
	 * 
	 * @param value
	 *            The value to convert.
	 * @param targetKeyProperty
	 *            The target property.
	 * @return The converter value.
	 * @throws ConversionException
	 *             Thrown if the conversion fails.
	 */
	private P convertValue(final P value, final PropertyDescriptor<P, Q> targetKeyProperty) throws ConversionException {
		P result = value;
		try {
			if (!targetKeyProperty.isAssignable(result)) {
				final ConverterTool sourceConverter = this.getSourceModel().getExtension(ConverterTool.class);
				final ConverterTool targetConverter = this.getTargetModel().getExtension(ConverterTool.class);
				if (CheckUtil.isNull(sourceConverter) || CheckUtil.isNull(targetConverter)) {
					throw new ConversionException("Could not find converter.");
				} else {
					final String stringValue = sourceConverter.convertToString(this.getSourceProperty().getPropertyType(), result);
					result = targetConverter.convertToInstance(targetKeyProperty.getPropertyType(), stringValue);
				}
			}
		} catch (final ConverterException e) {
			throw new ConversionException(e);
		}
		return result;
	}
}
