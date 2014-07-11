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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.mapping.ModelConversionTool;
import org.lunarray.model.descriptor.mapping.impl.ClassPair;
import org.lunarray.model.descriptor.mapping.impl.EntityConversionImpl;
import org.lunarray.model.descriptor.mapping.impl.ModelConversionToolImpl;
import org.lunarray.model.descriptor.mapping.impl.properties.PropertyConversionStrategy;
import org.lunarray.model.descriptor.mapping.impl.properties.impl.PropertyConversionStrategyBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;

/**
 * The model conversion strategy.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ModelConversionStrategyBuilder {

	/**
	 * Creates the builder.
	 * 
	 * @return The builder.
	 */
	public static ModelConversionStrategyBuilder create() {
		return new ModelConversionStrategyBuilder();
	}
	/** The conversions. */
	private final transient Map<ClassPair, EntityConversionBuilder<?, ?>> conversionsBuilder;
	/** The models. */
	private final transient List<Model<?>> modelsBuilder;
	/** The name matcher. */
	private transient NameMatcher nameMatcherBuilder;

	/** Whether or not to prefer the dictionary. */
	private transient boolean preferDictionaryBuilder;

	/**
	 * Default constructor.
	 */
	private ModelConversionStrategyBuilder() {
		this.modelsBuilder = new LinkedList<Model<?>>();
		this.conversionsBuilder = new HashMap<ClassPair, EntityConversionBuilder<?, ?>>();
		this.nameMatcherBuilder = new LiteralNameMatcher();
	}

	/**
	 * Add the model.
	 * 
	 * @param model
	 *            The model. May not be null.
	 * @return The entity type.
	 * @param <S>
	 *            The source type.
	 */
	public <S> ModelConversionStrategyBuilder addModel(final Model<S> model) {
		Validate.notNull(model, "Model may not be null");
		for (final Model<?> otherModel : this.modelsBuilder) {
			this.processModel(model, otherModel);
		}
		this.modelsBuilder.add(model);
		return this;
	}

	/**
	 * Builds the tool.
	 * 
	 * @return The tool.
	 */
	public ModelConversionTool build() {
		final Map<ClassPair, EntityConversionImpl<?, ?>> converters = new HashMap<ClassPair, EntityConversionImpl<?, ?>>();
		for (final Map.Entry<ClassPair, EntityConversionBuilder<?, ?>> entry : this.conversionsBuilder.entrySet()) {
			converters.put(entry.getKey(), entry.getValue().build());
		}
		return new ModelConversionToolImpl(converters);
	}

	/**
	 * Sets the entity name matcher.
	 * 
	 * @param nameMatcher
	 *            The name matcher. May not be null.
	 * @return The builder.
	 */
	public ModelConversionStrategyBuilder entityNameMatcher(final NameMatcher nameMatcher) {
		Validate.notNull(nameMatcher, "Name matcher may not be null.");
		this.nameMatcherBuilder = nameMatcher;
		return this;
	}

	/**
	 * Prefer a copy.
	 * 
	 * @return The builder.
	 */
	public ModelConversionStrategyBuilder preferCopy() {
		this.preferDictionaryBuilder = false;
		return this;
	}

	/**
	 * Prefer a dictionary.
	 * 
	 * @return The builder.
	 */
	public ModelConversionStrategyBuilder preferDictionary() {
		this.preferDictionaryBuilder = true;
		return this;
	}

	/**
	 * Process an entity.
	 * 
	 * @param source
	 *            The source entity.
	 * @param target
	 *            The target entity.
	 * @param sourceModel
	 *            The source model.
	 * @param targetModel
	 *            The target model.
	 * @param <S>
	 *            The source type.
	 * @param <E>
	 *            The source entity.
	 * @param <T>
	 *            The target type.
	 * @param <F>
	 *            The target entity.
	 */
	private <T, S, E extends S, F extends T> void processEntity(final EntityDescriptor<E> source, final EntityDescriptor<F> target,
			final Model<S> sourceModel, final Model<T> targetModel) {
		final ClassPair pair = new ClassPair(target.getEntityType(), source.getEntityType());
		final EntityConversionBuilder<E, F> builder = new EntityConversionBuilder<E, F>(source, target);
		for (final PropertyDescriptor<?, E> sourceProperty : source.getProperties()) {
			for (final PropertyDescriptor<?, F> targetProperty : target.getProperties()) {
				final String sourceName = sourceProperty.getName();
				final String targetName = targetProperty.getName();
				if (this.nameMatcherBuilder.isPropertyMatch(sourceName, targetName)) {
					builder.addPropertyConversion(this.processProperty(sourceProperty, targetProperty, sourceModel, targetModel));
				}
			}
		}
		this.conversionsBuilder.put(pair, builder);
	}

	/**
	 * Process the model.
	 * 
	 * @param source
	 *            The source model.
	 * @param target
	 *            The target model.
	 * @param <S>
	 *            The source type.
	 * @param <T>
	 *            The target type.
	 */
	private <T, S> void processModel(final Model<S> source, final Model<T> target) {
		for (final EntityDescriptor<? extends S> descriptor1 : source.getEntities()) {
			for (final EntityDescriptor<? extends T> descriptor2 : target.getEntities()) {
				final String name1 = descriptor1.getName();
				final String name2 = descriptor2.getName();
				if (this.nameMatcherBuilder.isEntityMatch(name1, name2)) {
					this.processEntity(descriptor1, descriptor2, source, target);
				}
				if (this.nameMatcherBuilder.isEntityMatch(name2, name1)) {
					this.processEntity(descriptor2, descriptor1, target, source);
				}
			}
		}
	}

	/**
	 * Process the properties.
	 * 
	 * @param source
	 *            The source property.
	 * @param target
	 *            The target property.
	 * @param sourceModel
	 *            The source model.
	 * @param targetModel
	 *            The target model.
	 * @return The strategy.
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
	private <S, T, E extends S, F extends T, P, Q> PropertyConversionStrategy<E, F> processProperty(final PropertyDescriptor<P, E> source,
			final PropertyDescriptor<Q, F> target, final Model<S> sourceModel, final Model<T> targetModel) {
		final PropertyConversionStrategyBuilder<S, P, E, T, Q, F> builder = PropertyConversionStrategyBuilder
				.<S, P, E, T, Q, F>createBuilder();
		builder.sourceProperty(source).sourceModel(sourceModel).targetProperty(target).targetModel(targetModel);
		builder.preferDictionary(this.preferDictionaryBuilder);
		return builder.build();
	}
}
