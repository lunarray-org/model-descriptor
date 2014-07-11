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
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;

/**
 * The strategy builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The source type.
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
public final class PropertyConversionStrategyBuilder<S, P, E extends S, T, Q, F extends T> {

	/**
	 * Creates the builder.
	 * 
	 * @return The builder.
	 * @param <S>
	 *            The source type.
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
	public static <S, P, E extends S, T, Q, F extends T> PropertyConversionStrategyBuilder<S, P, E, T, Q, F> createBuilder() {
		return new PropertyConversionStrategyBuilder<S, P, E, T, Q, F>();
	}
	/** Prefer a dictionary. */
	private transient boolean preferDictionaryBuilder;
	/** The source model. */
	private transient Model<S> sourceModelBuilder;
	/** The source property. */
	private transient PropertyDescriptor<P, E> sourcePropertyBuilder;
	/** The target model. */
	private transient Model<T> targetModelBuilder;

	/** The target property. */
	private transient PropertyDescriptor<Q, F> targetPropertyBuilder;

	/** The default constructor. */
	private PropertyConversionStrategyBuilder() {
		this.sourcePropertyBuilder = null;
		this.targetPropertyBuilder = null;
	}

	/**
	 * Build the strategy.
	 * 
	 * @return The strategy.
	 */
	public PropertyConversionStrategy<E, F> build() {
		Validate.notNull(this.sourceModelBuilder, "Source model may not be null.");
		Validate.notNull(this.sourcePropertyBuilder, "Source property may not be null.");
		Validate.notNull(this.targetModelBuilder, "Target model may not be null.");
		Validate.notNull(this.targetPropertyBuilder, "Target property may not be null.");
		PropertyConversionStrategy<E, F> result;
		if (this.sourcePropertyBuilder.isRelation()) {
			final RelationDescriptor relatedSource = this.sourcePropertyBuilder.adapt(RelationDescriptor.class);
			final RelationDescriptor relatedTarget = this.targetPropertyBuilder.adapt(RelationDescriptor.class);
			if ((RelationType.REFERENCE == relatedSource.getRelationType()) && (RelationType.REFERENCE == relatedTarget.getRelationType())) {
				result = PropertyConversionStrategyCopyImpl.create(this.sourcePropertyBuilder, this.targetPropertyBuilder,
						this.targetModelBuilder, this.sourceModelBuilder);
			} else {
				result = this.mappingProcessing(relatedSource, relatedTarget);
			}
		} else {
			result = PropertyConversionStrategyCopyImpl.create(this.sourcePropertyBuilder, this.targetPropertyBuilder,
					this.targetModelBuilder, this.sourceModelBuilder);
		}
		return result;
	}

	/**
	 * Sets a new value for the preferDictionary field.
	 * 
	 * @param preferDictionary
	 *            The new value for the preferDictionary field.
	 * @return The builder.
	 */
	public PropertyConversionStrategyBuilder<S, P, E, T, Q, F> preferDictionary(final boolean preferDictionary) {
		this.preferDictionaryBuilder = preferDictionary;
		return this;
	}

	/**
	 * Sets a new value for the sourceModel field.
	 * 
	 * @param sourceModel
	 *            The new value for the sourceModel field. Must be set.
	 * @return The builder.
	 */
	public PropertyConversionStrategyBuilder<S, P, E, T, Q, F> sourceModel(final Model<S> sourceModel) {
		this.sourceModelBuilder = sourceModel;
		return this;
	}

	/**
	 * Sets a new value for the sourceProperty field.
	 * 
	 * @param sourceProperty
	 *            The new value for the sourceProperty field. Must be set.
	 * @return The builder.
	 */
	public PropertyConversionStrategyBuilder<S, P, E, T, Q, F> sourceProperty(final PropertyDescriptor<P, E> sourceProperty) {
		this.sourcePropertyBuilder = sourceProperty;
		return this;
	}

	/**
	 * Sets a new value for the targetModel field.
	 * 
	 * @param targetModel
	 *            The new value for the targetModel field. Must be set.
	 * @return The builder.
	 */
	public PropertyConversionStrategyBuilder<S, P, E, T, Q, F> targetModel(final Model<T> targetModel) {
		this.targetModelBuilder = targetModel;
		return this;
	}

	/**
	 * Sets a new value for the targetProperty field.
	 * 
	 * @param targetProperty
	 *            The new value for the targetProperty field. Must be set.
	 * @return The builder.
	 */
	public PropertyConversionStrategyBuilder<S, P, E, T, Q, F> targetProperty(final PropertyDescriptor<Q, F> targetProperty) {
		this.targetPropertyBuilder = targetProperty;
		return this;
	}

	/**
	 * Process the mapping.
	 * 
	 * @param relatedSource
	 *            The source mapping.
	 * @param relatedTarget
	 *            The target mapping.
	 * @return The mapping.
	 */
	private PropertyConversionStrategy<E, F> mappingProcessing(final RelationDescriptor relatedSource,
			final RelationDescriptor relatedTarget) {
		PropertyConversionStrategy<E, F> result;
		final EntityDescriptor<?> sourceEntity = this.sourceModelBuilder.getEntity(relatedSource.getRelatedName());
		final EntityDescriptor<?> targetEntity = this.targetModelBuilder.getEntity(relatedTarget.getRelatedName());
		if (RelationType.REFERENCE == relatedSource.getRelationType()) {
			result = PropertyConversionStrategyLookupAssignImpl.create(this.sourcePropertyBuilder, this.targetPropertyBuilder,
					this.targetModelBuilder, this.sourceModelBuilder);
		} else if (RelationType.REFERENCE == relatedTarget.getRelationType()) {
			result = PropertyConversionStrategyKeyAssignImpl.create(this.sourcePropertyBuilder, this.targetPropertyBuilder,
					this.sourceModelBuilder, this.targetModelBuilder);
		} else {
			if (this.preferDictionaryBuilder && !CheckUtil.isNull(sourceEntity.adapt(KeyedEntityDescriptor.class))
					&& !CheckUtil.isNull(targetEntity.adapt(KeyedEntityDescriptor.class))) {
				result = PropertyConversionStrategyLookupKeyImpl.create(this.sourcePropertyBuilder, this.targetPropertyBuilder,
						this.sourceModelBuilder, this.targetModelBuilder);
			} else {
				result = PropertyConversionStrategyDeepCopyImpl.create(this.sourcePropertyBuilder, this.targetPropertyBuilder);
			}
		}
		return result;
	}
}
