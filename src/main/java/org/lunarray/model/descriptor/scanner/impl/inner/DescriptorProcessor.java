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
package org.lunarray.model.descriptor.scanner.impl.inner;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.lunarray.model.descriptor.scanner.annotation.Aggregates;
import org.lunarray.model.descriptor.scanner.annotation.Describes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The descriptor processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DescriptorProcessor
		implements ModelProcessor<Serializable> {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DescriptorProcessor.class);
	/** The annotation entity mapping. */
	private Map<Class<? extends Annotation>, List<Class<? extends Serializable>>> annotationEntityMapping;

	/**
	 * Default constructor.
	 */
	public DescriptorProcessor() {
		this.annotationEntityMapping = new HashMap<Class<? extends Annotation>, List<Class<? extends Serializable>>>();
	}

	/**
	 * Gets the annotation mapping.
	 * 
	 * @return The annotation mapping.
	 */
	public Map<Class<? extends Annotation>, List<Class<? extends Serializable>>> getAnnotationEntityMapping() {
		return this.annotationEntityMapping;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends Serializable> EntityExtension<E> process(final DescribedEntity<E> entityType,
			final ExtensionContainer extensionContainer) {
		DescriptorProcessor.LOGGER.debug("Processing entity type: {}", entityType);
		Validate.notNull(entityType, "Entity may not be null.");
		AnnotationDescriptorImpl<E> extension = null;
		if (entityType.isAnnotationPresent(Describes.class)) {
			final Set<Describes> describes = new HashSet<Describes>(entityType.getAnnotation(Describes.class));
			final Set<Class<? extends Annotation>> annotations = this.extractAnnotations(describes);
			for (final Class<? extends Annotation> a : annotations) {
				if (!this.annotationEntityMapping.containsKey(a)) {
					this.annotationEntityMapping.put(a, this.createList());
				}
				this.annotationEntityMapping.get(a).add(entityType.getEntityType());
			}
			extension = new AnnotationDescriptorImpl<E>();
			extension.setDescribes(annotations);
			extension.setAggregates(this.extractAggregateAnnotations(entityType.getAnnotation(Aggregates.class)));
		}
		return extension;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends Serializable> OperationExtension<E> process(final DescribedOperation entityType,
			final ExtensionContainer extensionContainer) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends Serializable, P> PropertyExtension<P, E> process(final DescribedProperty<P> property,
			final ExtensionContainer extensionContainer) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Extension process(final ExtensionContainer extensionContainer) {
		return null;
	}

	/**
	 * Sets a new value for the annotationEntityMapping field.
	 * 
	 * @param annotationEntityMapping
	 *            The new value for the annotationEntityMapping field.
	 */
	public void setAnnotationEntityMapping(
			final Map<Class<? extends Annotation>, List<Class<? extends Serializable>>> annotationEntityMapping) {
		this.annotationEntityMapping = annotationEntityMapping;
	}

	/**
	 * Creates a list.
	 * 
	 * @return The list.
	 */
	private List<Class<? extends Serializable>> createList() {
		return new LinkedList<Class<? extends Serializable>>();
	}

	/**
	 * Extract the aggregate annotations.
	 * 
	 * @param aggregates
	 *            The aggregate annotations.
	 * @return The aggregate annotations.
	 */
	private Set<Class<? extends Annotation>> extractAggregateAnnotations(final List<Aggregates> aggregates) {
		final Set<Class<? extends Annotation>> result = new HashSet<Class<? extends Annotation>>();
		for (final Aggregates d : aggregates) {
			for (final Class<? extends Annotation> c : d.value()) {
				result.add(c);
			}
		}
		return result;
	}

	/**
	 * Extract the describes annotations.
	 * 
	 * @param describes
	 *            The describes annotations.
	 * @return The describe annotations.
	 */
	private Set<Class<? extends Annotation>> extractAnnotations(final Set<Describes> describes) {
		final Set<Class<? extends Annotation>> result = new HashSet<Class<? extends Annotation>>();
		for (final Describes d : describes) {
			for (final Class<? extends Annotation> c : d.value()) {
				result.add(c);
			}
		}
		return result;
	}
}
