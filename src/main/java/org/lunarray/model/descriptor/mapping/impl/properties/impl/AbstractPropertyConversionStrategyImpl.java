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
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;

/**
 * The property strategy that uses the key to look up the other entity.
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
 *            The source property.
 * @param <Q>
 *            The target property.
 */
public abstract class AbstractPropertyConversionStrategyImpl<S, T, E, F, P, Q>
		implements PropertyConversionStrategy<E, F> {

	/** The serial id. */
	private static final long serialVersionUID = -6255328303343433208L;
	/** The source model. */
	private Model<S> sourceModel;
	/** The source property. */
	private PropertyDescriptor<P, E> sourceProperty;
	/** The target model. */
	private Model<T> targetModel;
	/** The target property. */
	private PropertyDescriptor<Q, F> targetProperty;

	/**
	 * Default constructor.
	 * 
	 * @param sourceProperty
	 *            The source property. May not be null.
	 * @param targetProperty
	 *            The target property. May not be null.
	 * @param targetModel
	 *            The target model. May not be null.
	 * @param sourceModel
	 *            The source model. May not be null.
	 */
	public AbstractPropertyConversionStrategyImpl(final PropertyDescriptor<P, E> sourceProperty,
			final PropertyDescriptor<Q, F> targetProperty, final Model<T> targetModel, final Model<S> sourceModel) {
		Validate.notNull(sourceProperty, "Source property may not be null.");
		Validate.notNull(targetProperty, "Target property may not be null.");
		this.sourceProperty = sourceProperty;
		this.targetProperty = targetProperty;
		this.targetModel = targetModel;
		this.sourceModel = sourceModel;
	}

	/**
	 * Gets the value for the sourceModel field.
	 * 
	 * @return The value for the sourceModel field.
	 */
	public final Model<S> getSourceModel() {
		return this.sourceModel;
	}

	/**
	 * Gets the value for the sourceProperty field.
	 * 
	 * @return The value for the sourceProperty field.
	 */
	public final PropertyDescriptor<P, E> getSourceProperty() {
		return this.sourceProperty;
	}

	/**
	 * Gets the value for the targetModel field.
	 * 
	 * @return The value for the targetModel field.
	 */
	public final Model<T> getTargetModel() {
		return this.targetModel;
	}

	/**
	 * Gets the value for the targetProperty field.
	 * 
	 * @return The value for the targetProperty field.
	 */
	public final PropertyDescriptor<Q, F> getTargetProperty() {
		return this.targetProperty;
	}

	/**
	 * Sets a new value for the sourceModel field.
	 * 
	 * @param sourceModel
	 *            The new value for the sourceModel field.
	 */
	public final void setSourceModel(final Model<S> sourceModel) {
		this.sourceModel = sourceModel;
	}

	/**
	 * Sets a new value for the sourceProperty field.
	 * 
	 * @param sourceProperty
	 *            The new value for the sourceProperty field.
	 */
	public final void setSourceProperty(final PropertyDescriptor<P, E> sourceProperty) {
		this.sourceProperty = sourceProperty;
	}

	/**
	 * Sets a new value for the targetModel field.
	 * 
	 * @param targetModel
	 *            The new value for the targetModel field.
	 */
	public final void setTargetModel(final Model<T> targetModel) {
		this.targetModel = targetModel;
	}

	/**
	 * Sets a new value for the targetProperty field.
	 * 
	 * @param targetProperty
	 *            The new value for the targetProperty field.
	 */
	public final void setTargetProperty(final PropertyDescriptor<Q, F> targetProperty) {
		this.targetProperty = targetProperty;
	}
}
