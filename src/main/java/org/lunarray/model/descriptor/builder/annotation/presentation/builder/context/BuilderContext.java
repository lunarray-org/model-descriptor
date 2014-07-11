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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.builders.context.AbstractBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.resolver.ResolverFactory;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.PresentationEntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.PresentationOperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.PresentationParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PresentationPropertyAttributeResolverStrategy;

/**
 * The builder context.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class BuilderContext
		extends AbstractBuilderContext
		implements PresQualBuilderContext {

	/**
	 * Creates a builder context.
	 * 
	 * @return The builder context.
	 */
	public static BuilderContext create() {
		return new BuilderContext();
	}
	/** The bundles. */
	private final transient Map<Class<?>, String> bundles;
	/** The entity qualifiers. */
	private final transient Map<Class<?>, Set<Class<?>>> entityQualifiers;
	/** The qualifier bundles. */
	private final transient Map<Class<?>, Map<Class<?>, String>> qualifierBundles;
	/** The presentation attribute resolver. */
	private transient PresentationEntityAttributeResolverStrategy resolverEntityStrategy;
	/** The presentation attribute resolver. */
	private transient PresentationOperationAttributeResolverStrategy resolverOperationStrategy;
	/** The presentation attribute resolver. */
	private transient PresentationParameterAttributeResolverStrategy resolverParameterStrategy;

	/** The presentation attribute resolver. */
	private transient PresentationPropertyAttributeResolverStrategy resolverPropertyStrategy;

	/**
	 * Constructs the context.
	 */
	private BuilderContext() {
		super();
		this.bundles = new HashMap<Class<?>, String>();
		this.qualifierBundles = new HashMap<Class<?>, Map<Class<?>, String>>();
		this.entityQualifiers = new HashMap<Class<?>, Set<Class<?>>>();
	}

	/** {@inheritDoc} */
	@Override
	public String getBundle(final DescribedEntity<?> described) {
		String bundle = null;
		final Class<?> entityType = described.getEntityType();
		if (this.bundles.containsKey(entityType)) {
			bundle = this.bundles.get(entityType);
		}
		return bundle;
	}

	/** {@inheritDoc} */
	@Override
	public PresentationEntityAttributeResolverStrategy getPresentationEntityAttributeResolverStrategy() {
		if (CheckUtil.isNull(this.resolverEntityStrategy)) {
			this.resolverEntityStrategy = ResolverFactory.defaultEntityPresentationAttributeResolver();
		}
		return this.resolverEntityStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public PresentationOperationAttributeResolverStrategy getPresentationOperationAttributeResolverStrategy() {
		if (CheckUtil.isNull(this.resolverOperationStrategy)) {
			this.resolverOperationStrategy = ResolverFactory.defaultOperationPresentationAttributeResolver();
		}
		return this.resolverOperationStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public PresentationParameterAttributeResolverStrategy getPresentationParameterAttributeResolverStrategy() {
		if (CheckUtil.isNull(this.resolverParameterStrategy)) {
			this.resolverParameterStrategy = ResolverFactory.defaultParameterPresentationAttributeResolver();
		}
		return this.resolverParameterStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public PresentationPropertyAttributeResolverStrategy getPresentationPropertyAttributeResolverStrategy() {
		if (CheckUtil.isNull(this.resolverPropertyStrategy)) {
			this.resolverPropertyStrategy = ResolverFactory.defaultPropertyPresentationAttributeResolver();
		}
		return this.resolverPropertyStrategy;
	}

	/** {@inheritDoc} */
	@Override
	public String getQualifierBundle(final DescribedEntity<?> described, final Class<?> qualifier) {
		final Class<?> entityType = described.getEntityType();
		String bundle = null;
		if (this.qualifierBundles.containsKey(entityType) && this.qualifierBundles.get(entityType).containsKey(qualifier)) {
			bundle = this.qualifierBundles.get(entityType).get(qualifier);
		} else if (this.bundles.containsKey(entityType)) {
			bundle = this.bundles.get(entityType);
		}
		return bundle;
	}

	/** {@inheritDoc} */
	@Override
	public Set<Class<?>> getQualifiers(final DescribedEntity<?> described) {
		Set<Class<?>> result;
		final Class<?> entityType = described.getEntityType();
		if (this.entityQualifiers.containsKey(entityType)) {
			result = new HashSet<Class<?>>(this.entityQualifiers.get(entityType));
		} else {
			result = new HashSet<Class<?>>();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public BuilderContext presentationEntityAttributeResolverStrategy(
			final PresentationEntityAttributeResolverStrategy attributeResolverStrategy) {
		this.resolverEntityStrategy = attributeResolverStrategy;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public BuilderContext presentationOperationAttributeResolverStrategy(
			final PresentationOperationAttributeResolverStrategy attributeResolverStrategy) {
		this.resolverOperationStrategy = attributeResolverStrategy;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public BuilderContext presentationPropertyAttributeResolverStrategy(
			final PresentationPropertyAttributeResolverStrategy attributeResolverStrategy) {
		this.resolverPropertyStrategy = attributeResolverStrategy;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public void putBundle(final DescribedEntity<?> described, final Class<?> qualifier, final String bundle) {
		final Class<?> entityType = described.getEntityType();
		if (!CheckUtil.isNull(bundle)) {
			if (!this.qualifierBundles.containsKey(entityType)) {
				this.qualifierBundles.put(entityType, new HashMap<Class<?>, String>());
			}
			this.qualifierBundles.get(entityType).put(qualifier, bundle);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putBundle(final DescribedEntity<?> described, final String bundle) {
		final Class<?> entityType = described.getEntityType();
		if (!CheckUtil.isNull(bundle)) {
			this.bundles.put(entityType, bundle);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void putQualifier(final DescribedEntity<?> described, final Class<?> qualifier) {
		final Class<?> entityType = described.getEntityType();
		if (!this.entityQualifiers.containsKey(entityType)) {
			this.entityQualifiers.put(entityType, new HashSet<Class<?>>());
		}
		this.entityQualifiers.get(entityType).add(qualifier);
	}
}
