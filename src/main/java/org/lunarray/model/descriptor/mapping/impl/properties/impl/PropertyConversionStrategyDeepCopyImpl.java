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

import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.mapping.ModelConversionTool;
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.mapping.impl.properties.exceptions.ConversionException;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The property conversion strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The source entity.
 * @param <F>
 *            The target entity.
 * @param <P>
 *            The source property.
 * @param <Q>
 *            The target property.
 */
public final class PropertyConversionStrategyDeepCopyImpl<E, F, P, Q>
		extends AbstractPropertyConversionStrategyImpl<Object, Object, E, F, P, Q> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyConversionStrategyDeepCopyImpl.class);
	/** Serial id. */
	private static final long serialVersionUID = 4476629092424343541L;

	/**
	 * Creates the strategy.
	 * 
	 * @param source
	 *            The source property. May not be null.
	 * @param target
	 *            The target property. May not be null.
	 * @return The strategy.
	 * @param <P>
	 *            The source property.
	 * @param <E>
	 *            The source entity.
	 * @param <Q>
	 *            The target property.
	 * @param <F>
	 *            The target entity.
	 */
	public static <E, F, P, Q> PropertyConversionStrategy<E, F> create(final PropertyDescriptor<P, E> source,
			final PropertyDescriptor<Q, F> target) {
		final PropertyDescriptor<P, E> sourceProperty = source;
		final PropertyDescriptor<Q, F> targetProperty = target;
		return new PropertyConversionStrategyDeepCopyImpl<E, F, P, Q>(sourceProperty, targetProperty);
	}

	/**
	 * Creates the strategy.
	 * 
	 * @param sourceProperty
	 *            The source property.
	 * @param targetProperty
	 *            The target property.
	 */
	private PropertyConversionStrategyDeepCopyImpl(final PropertyDescriptor<P, E> sourceProperty,
			final PropertyDescriptor<Q, F> targetProperty) {
		super(sourceProperty, targetProperty, null, null);
	}

	/** {@inheritDoc} */
	@Override
	public void apply(final E source, final F target, final ModelConversionTool conversionTool) throws ConversionException {
		try {
			final PropertyDescriptor<P, E> sourcePropertyDescriptor = this.getSourceProperty();
			final PropertyDescriptor<Q, F> targetPropertyDescriptor = this.getTargetProperty();
			PropertyConversionStrategyDeepCopyImpl.LOGGER.debug("Converting from property {} to {} for entity {} to {}",
					sourcePropertyDescriptor, targetPropertyDescriptor, source, target);
			final P value = sourcePropertyDescriptor.getValue(source);
			final Q entityValue = conversionTool.convert(value, targetPropertyDescriptor.getPropertyType());
			targetPropertyDescriptor.setValue(target, entityValue);
		} catch (final ValueAccessException e) {
			throw new ConversionException(e);
		}
	}
}
