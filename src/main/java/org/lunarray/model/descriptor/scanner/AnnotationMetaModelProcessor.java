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
package org.lunarray.model.descriptor.scanner;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.exceptions.ValueAccessException;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.converter.ConverterTool;
import org.lunarray.model.descriptor.converter.def.DefaultConverterTool;
import org.lunarray.model.descriptor.converter.def.DelegatingEnumConverterTool;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.lunarray.model.descriptor.model.property.CollectionPropertyDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.lunarray.model.descriptor.objectfactory.simple.SimpleObjectFactory;
import org.lunarray.model.descriptor.resource.ResourceException;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.scanner.impl.exception.MappingException;
import org.lunarray.model.descriptor.scanner.impl.extensions.AnnotationMetaModelImpl;
import org.lunarray.model.descriptor.scanner.impl.inner.AnnotationDescriptor;
import org.lunarray.model.descriptor.scanner.impl.inner.AnnotationMetaValues;
import org.lunarray.model.descriptor.scanner.impl.inner.DescriptorProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A processor that processes annotations of a model to an annotation meta model
 * for easier processing.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The model super type.
 */
public final class AnnotationMetaModelProcessor<S>
		implements ModelProcessor<S> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationMetaModelProcessor.class);
	/**
	 * The converter, by default supports most Java platform types usable within
	 * annotations.
	 */
	private ConverterTool converterTool;
	/** The inner model. */
	private Model<Serializable> model;
	/** The inner model builder. */
	private SimpleBuilder<Serializable> modelBuilder;
	/** The inner model descriptor processor. */
	private DescriptorProcessor processor;
	/** Model resources. */
	private SimpleClazzResource<Serializable> resource;

	/**
	 * Default constructor.
	 */
	@SuppressWarnings("unchecked")
	public AnnotationMetaModelProcessor() {
		this.processor = new DescriptorProcessor();
		this.modelBuilder = SimpleBuilder.createBuilder();
		this.modelBuilder.postProcessors(this.processor);
		this.resource = new SimpleClazzResource<Serializable>();
		this.converterTool = new DelegatingEnumConverterTool(new DefaultConverterTool());
	}

	/**
	 * Gets the annotation meta values.
	 * 
	 * @return The meta values.
	 */
	public AnnotationMetaValues createMetaValues() {
		return this.createAnnotationMetaValues();
	}

	/**
	 * Gets the value for the converterTool field.
	 * 
	 * @return The value for the converterTool field.
	 */
	public ConverterTool getConverterTool() {
		return this.converterTool;
	}

	/**
	 * Gets the value for the model field.
	 * 
	 * @return The value for the model field.
	 */
	public Model<Serializable> getModel() {
		return this.model;
	}

	/**
	 * Gets the value for the modelBuilder field.
	 * 
	 * @return The value for the modelBuilder field.
	 */
	public SimpleBuilder<Serializable> getModelBuilder() {
		return this.modelBuilder;
	}

	/**
	 * Gets the value for the processor field.
	 * 
	 * @return The value for the processor field.
	 */
	public DescriptorProcessor getProcessor() {
		return this.processor;
	}

	/**
	 * Gets the value for the resource field.
	 * 
	 * @return The value for the resource field.
	 */
	public SimpleClazzResource<Serializable> getResource() {
		return this.resource;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends S> EntityExtension<E> process(final DescribedEntity<E> entityType, final ExtensionContainer extensionContainer) {
		Validate.notNull(entityType, "Entity may not be null.");
		EntityExtension<E> extension = null;
		try {
			extension = this.processNode(entityType.getAnnotations());
		} catch (final MappingException e) {
			AnnotationMetaModelProcessor.LOGGER.warn("Could not process entity.", e);
		}
		return extension;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends S> OperationExtension<E> process(final DescribedOperation entityType, final ExtensionContainer extensionContainer) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends S, P> PropertyExtension<P, E> process(final DescribedProperty<P> property, final ExtensionContainer extensionContainer) {
		Validate.notNull(property, "Property may not be null.");
		PropertyExtension<P, E> extension = null;
		try {
			extension = this.processNode(property.getAnnotations());
		} catch (final MappingException e) {
			AnnotationMetaModelProcessor.LOGGER.warn("Could not process property.", e);
		}
		return extension;
	}

	/** {@inheritDoc} */
	@Override
	public Extension process(final ExtensionContainer extensionContainer) {
		if (CheckUtil.isNull(this.model)) {
			try {
				this.model = this.modelBuilder.extensions(new SimpleObjectFactory(), this.converterTool).resources(this.resource).build();
			} catch (final ResourceException e) {
				AnnotationMetaModelProcessor.LOGGER.warn("Could not process model.", e);
			}
		}
		return null;
	}

	/**
	 * Register an annotation entity.
	 * 
	 * @param domainType
	 *            The domain type.
	 */
	public void registerAnnotationEntity(final Class<? extends Serializable> domainType) {
		this.resource.addClazz(domainType);
	}

	/**
	 * Sets a new value for the converterTool field.
	 * 
	 * @param converterTool
	 *            The new value for the converterTool field.
	 */
	public void setConverterTool(final ConverterTool converterTool) {
		this.converterTool = converterTool;
	}

	/**
	 * Sets a new value for the model field.
	 * 
	 * @param model
	 *            The new value for the model field.
	 */
	public void setModel(final Model<Serializable> model) {
		this.model = model;
	}

	/**
	 * Sets a new value for the modelBuilder field.
	 * 
	 * @param modelBuilder
	 *            The new value for the modelBuilder field.
	 */
	public void setModelBuilder(final SimpleBuilder<Serializable> modelBuilder) {
		this.modelBuilder = modelBuilder;
	}

	/**
	 * Sets a new value for the processor field.
	 * 
	 * @param processor
	 *            The new value for the processor field.
	 */
	public void setProcessor(final DescriptorProcessor processor) {
		this.processor = processor;
	}

	/**
	 * Sets a new value for the resource field.
	 * 
	 * @param resource
	 *            The new value for the resource field.
	 */
	public void setResource(final SimpleClazzResource<Serializable> resource) {
		this.resource = resource;
	}

	/**
	 * Copy array values.
	 * 
	 * @param values
	 *            The array values.
	 * @param name
	 *            The property name.
	 * @param obj
	 *            The object value.
	 * @param type
	 *            The array type.
	 * @throws MappingException
	 *             Thrown if the mapping was unsuccessful.
	 */
	private void arrayCopy(final AnnotationMetaValues values, final String name, final Object obj, final Class<?> type)
			throws MappingException {
		try {
			final Class<?> compType = type.getComponentType();
			if (Integer.TYPE.equals(compType)) {
				final int[] array = int[].class.cast(obj);
				for (final int a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Integer.TYPE, a));
				}
			} else if (Long.TYPE.equals(compType)) {
				final long[] array = long[].class.cast(obj);
				for (final long a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Long.TYPE, a));
				}
			} else {
				this.copyShortNumbers(values, name, obj, type, compType);
			}
		} catch (final ConverterException e) {
			throw new MappingException(e);
		}
	}

	/**
	 * Convert an array to string types.
	 * 
	 * @param array
	 *            The array to convert.
	 * @param type
	 *            The array type.
	 * @param tail
	 *            The result tail.
	 * @throws ConverterException
	 *             Thrown if a value could not be converted.
	 * @param <T>
	 *            The array type.
	 */
	private <T> void convert(final T[] array, final Class<T> type, final List<String> tail) throws ConverterException {
		for (final T t : array) {
			if (!CheckUtil.isNull(t)) {
				tail.add(this.converterTool.convertToString(type, t));
			}
		}
	}

	/**
	 * Copy array values.
	 * 
	 * @param values
	 *            The array values.
	 * @param name
	 *            The property name.
	 * @param obj
	 *            The object value.
	 * @param type
	 *            The array type.
	 * @param compType
	 *            The component type.
	 * @throws MappingException
	 *             Thrown if the mapping was unsuccessful.
	 */
	private void copyNonDiscreteNumbers(final AnnotationMetaValues values, final String name, final Object obj, final Class<?> type,
			final Class<?> compType) throws MappingException {
		try {
			if (Double.TYPE.equals(compType)) {
				final double[] array = double[].class.cast(obj);
				for (final double a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Double.TYPE, a));
				}
			} else if (Float.TYPE.equals(compType)) {
				final float[] array = float[].class.cast(obj);
				for (final float a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Float.TYPE, a));
				}
			} else {
				this.extractNonFloats(values, name, obj, type, compType);
			}
		} catch (final ConverterException e) {
			throw new MappingException(e);
		}
	}

	/**
	 * Copy array values.
	 * 
	 * @param values
	 *            The array values.
	 * @param name
	 *            The property name.
	 * @param obj
	 *            The object value.
	 * @param type
	 *            The array type.
	 * @param compType
	 *            The component type.
	 * @throws MappingException
	 *             Thrown if the mapping was unsuccessful.
	 */
	private void copyObjectTypes(final AnnotationMetaValues values, final String name, final Object obj, final Class<?> type,
			final Class<?> compType) throws MappingException {
		if (Annotation.class.isAssignableFrom(compType)) {
			final Annotation[] array = Annotation[].class.cast(obj);
			for (final Annotation element : array) {
				final AnnotationMetaValues innerValues = this.createMetaValues();
				values.getMetaAnnotationList(name).add(innerValues);
				this.processAnnotation(innerValues, element);
			}
		} else {
			final Object[] array = Object[].class.cast(obj);
			@SuppressWarnings("unchecked")
			final Class<Object> aType = (Class<Object>) type.getComponentType();
			try {
				this.convert(array, aType, values.getMetaValueList(name));
			} catch (final ConverterException e) {
				throw new MappingException(e);
			}
		}
	}

	/**
	 * Copy array values.
	 * 
	 * @param values
	 *            The array values.
	 * @param name
	 *            The property name.
	 * @param obj
	 *            The object value.
	 * @param type
	 *            The array type.
	 * @param compType
	 *            The component type.
	 * @throws MappingException
	 *             Thrown if the mapping was unsuccessful.
	 */
	private void copyShortNumbers(final AnnotationMetaValues values, final String name, final Object obj, final Class<?> type,
			final Class<?> compType) throws MappingException {
		try {
			if (Short.TYPE.equals(compType)) {
				final short[] array = short[].class.cast(obj);
				for (final short a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Short.TYPE, a));
				}
			} else if (Byte.TYPE.equals(compType)) {
				final byte[] array = byte[].class.cast(obj);
				for (final byte a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Byte.TYPE, a));
				}
			} else {
				this.copyNonDiscreteNumbers(values, name, obj, type, compType);
			}
		} catch (final ConverterException e) {
			throw new MappingException(e);
		}
	}

	/**
	 * Copy a value.
	 * 
	 * @param values
	 *            The values to copy to.
	 * @param annotation
	 *            The annotation to copy for.
	 * @param method
	 *            The method of the annotation to copy.
	 * @throws MappingException
	 *             Thrown if the value could not be related.
	 */
	private void copyValue(final AnnotationMetaValues values, final Annotation annotation, final Method method) throws MappingException {
		final String name = method.getName();
		final Object defaultValue = method.getDefaultValue();
		try {
			final Object obj = method.invoke(annotation);
			if (!CheckUtil.isNull(obj) && !obj.equals(defaultValue)) {
				values.getMetaAnnotationList(name);
				final Class<?> type = obj.getClass();
				this.processType(values, name, obj, type);
			}
		} catch (final IllegalArgumentException e) {
			throw new MappingException(e);
		} catch (final IllegalAccessException e) {
			throw new MappingException(e);
		} catch (final InvocationTargetException e) {
			throw new MappingException(e);
		}
	}

	/**
	 * Create annotation meta value.
	 * 
	 * @return The meta value.
	 */
	private AnnotationMetaValues createAnnotationMetaValues() {
		return new AnnotationMetaValues();
	}

	/**
	 * Extract annotation meta data.
	 * 
	 * @param annotations
	 *            The annotations.
	 * @return The meta data.
	 * @throws MappingException
	 *             Thrown if the mapping was unsuccessful.
	 */
	private AnnotationMetaValues extractCompoundMetadata(final List<Annotation> annotations) throws MappingException {
		final AnnotationMetaValues values = this.createMetaValues();
		for (final Annotation a : annotations) {
			this.processAnnotation(values, a);
		}
		return values;
	}

	/**
	 * Extract annotation methods.
	 * 
	 * @param annotationType
	 *            The annotation type.
	 * @return The methods of the annotation type.
	 */
	private List<Method> extractMethods(final Class<? extends Annotation> annotationType) {
		final List<Method> result = new LinkedList<Method>();
		for (final Method m : annotationType.getMethods()) {
			if ((m.getParameterTypes().length == 0) && annotationType.equals(m.getDeclaringClass())) {
				result.add(m);
			}
		}
		return result;
	}

	/**
	 * Copy array values.
	 * 
	 * @param values
	 *            The array values.
	 * @param name
	 *            The property name.
	 * @param obj
	 *            The object value.
	 * @param type
	 *            The array type.
	 * @param compType
	 *            The component type.
	 * @throws MappingException
	 *             Thrown if the mapping was unsuccessful.
	 */
	private void extractNonFloats(final AnnotationMetaValues values, final String name, final Object obj, final Class<?> type,
			final Class<?> compType) throws MappingException {
		try {
			if (Boolean.TYPE.equals(compType)) {
				final boolean[] array = boolean[].class.cast(obj);
				for (final boolean a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Boolean.TYPE, a));
				}
			} else if (Character.TYPE.equals(compType)) {
				final char[] array = char[].class.cast(obj);
				for (final char a : array) {
					values.getMetaValueList(name).add(this.converterTool.convertToString(Character.TYPE, a));
				}
			} else {
				this.copyObjectTypes(values, name, obj, type, compType);
			}
		} catch (final ConverterException e) {
			throw new MappingException(e);
		}
	}

	/**
	 * Process an annotation.
	 * 
	 * @param values
	 *            The values to copy to.
	 * @param anno
	 *            The annotation to copy.
	 * @throws MappingException
	 *             Thrown if the mapping was unsuccessful.
	 */
	private void processAnnotation(final AnnotationMetaValues values, final Annotation anno) throws MappingException {
		for (final Method m : this.extractMethods(anno.annotationType())) {
			this.copyValue(values, anno, m);
		}
	}

	/**
	 * Process an annotation to an entity.
	 * 
	 * @param annotationMapping
	 *            The annotation type to annotation mapping.
	 * @param annotationType
	 *            The annotation type.
	 * @param targetEntity
	 *            The target entity.
	 * @throws MappingException
	 *             Thrown if the mapping could not be done.
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @return The entity.
	 */
	private <P, E extends Serializable> E processAnnotationEntity(
			final Map.Entry<Class<? extends Annotation>, List<Annotation>> annotationMapping,
			final Class<? extends Annotation> annotationType, final Class<E> targetEntity) throws MappingException {
		@SuppressWarnings("unchecked")
		final AnnotationDescriptor<?> descriptor = this.model.getEntity(targetEntity).extension(AnnotationDescriptor.class);
		Set<Class<? extends Annotation>> aggregates = Collections.emptySet();
		if (!CheckUtil.isNull(descriptor)) {
			aggregates = descriptor.getAggregates();
		}
		final Set<Annotation> process = new LinkedHashSet<Annotation>();
		for (final Annotation ann : annotationMapping.getValue()) {
			process.addAll(AnnotationScannerUtil.getMarked(annotationType, ann, true));
			for (final Class<? extends Annotation> aggregate : aggregates) {
				process.addAll(AnnotationScannerUtil.getMarked(aggregate, ann, true));
			}
		}
		return this.processMetaValues(this.extractCompoundMetadata(new LinkedList<Annotation>(process)), targetEntity);
	}

	/**
	 * Process annotation types.
	 * 
	 * @param extension
	 *            The extension.
	 * @param annotationMapping
	 *            The annotation type to annotations mapping.
	 * @throws MappingException
	 *             Thrown if the mapping could not be done.
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 */
	private <P, E extends S> void processAnnotationTypes(final AnnotationMetaModelImpl<P, E> extension,
			final Map.Entry<Class<? extends Annotation>, List<Annotation>> annotationMapping) throws MappingException {
		for (final Map.Entry<Class<? extends Annotation>, List<Class<? extends Serializable>>> annotationEntity : this.processor
				.getAnnotationEntityMapping().entrySet()) {
			final Class<? extends Annotation> annotationType = annotationMapping.getKey();
			if (AnnotationScannerUtil.isMarked(annotationEntity.getKey(), annotationType, true) && !annotationMapping.getValue().isEmpty()) {
				for (final Class<? extends Serializable> c : annotationEntity.getValue()) {
					extension.addEntity(this.processAnnotationEntity(annotationMapping, annotationType, c));
				}
			}
		}
	}

	/**
	 * Process a visitor.
	 * 
	 * @param metadata
	 *            The meta model.
	 * @param entityType
	 *            The entity type.
	 * @return The entity.
	 * @throws MappingException
	 *             Thrown if the mapping could not be done.
	 * @param <E>
	 *            The entity type.
	 */
	private <E extends Serializable> E processMetaValues(final AnnotationMetaValues metadata, final Class<E> entityType)
			throws MappingException {
		final EntityDescriptor<E> visitorDiscriptor = this.model.getEntity(entityType);
		E visitorValue;
		try {
			visitorValue = visitorDiscriptor.createEntity();
		} catch (final CreationException e) {
			throw new MappingException(e);
		}
		for (final Map.Entry<String, List<String>> metadataValue : metadata.getMetaValues().entrySet()) {
			this.updateValue(visitorDiscriptor, visitorValue, metadataValue);
		}
		for (final Map.Entry<String, List<AnnotationMetaValues>> annotationMetadataValue : metadata.getMetaAnnotations().entrySet()) {
			this.updateAnnotationValue(visitorDiscriptor, visitorValue, annotationMetadataValue);
		}
		return visitorValue;
	}

	/**
	 * Process a node.
	 * 
	 * @param annotations
	 *            The nodes' annotations.
	 * @return The extension.
	 * @throws MappingException
	 *             Thrown if the mapping could not be done.
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 */
	private <P, E extends S> AnnotationMetaModelImpl<P, E> processNode(final Map<Class<? extends Annotation>, List<Annotation>> annotations)
			throws MappingException {
		Validate.notNull(annotations, "Annotations must be present.");
		final AnnotationMetaModelImpl<P, E> extension = new AnnotationMetaModelImpl<P, E>();
		for (final Map.Entry<Class<? extends Annotation>, List<Annotation>> a : annotations.entrySet()) {
			this.processAnnotationTypes(extension, a);
		}
		return extension;
	}

	/**
	 * Process type.
	 * 
	 * @param values
	 *            The values.
	 * @param name
	 *            The name.
	 * @param obj
	 *            The object.
	 * @param type
	 *            The type.
	 * @throws MappingException
	 *             Thrown if the mapping failed.
	 */
	@SuppressWarnings("unchecked")
	private void processType(final AnnotationMetaValues values, final String name, final Object obj, final Class<?> type)
			throws MappingException {
		if (type.isArray()) {
			this.arrayCopy(values, name, obj, type);
		} else if (Annotation.class.isAssignableFrom(type)) {
			final AnnotationMetaValues innerValues = this.createMetaValues();
			values.getMetaAnnotationList(name).add(innerValues);
			this.processAnnotation(innerValues, (Annotation) obj);
		} else {
			try {
				values.getMetaValueList(name).add(this.converterTool.convertToString((Class<Object>) obj.getClass(), obj));
			} catch (final ConverterException e) {
				throw new MappingException(e);
			}
		}
	}

	/**
	 * Update the actual value.
	 * 
	 * @param entity
	 *            The entity to update.
	 * @param prop
	 *            The property.
	 * @param values
	 *            The values.
	 * @param collectionDescriptor
	 *            The collection property.
	 * @throws MappingException
	 *             Thrown if the mapping could not be successful.
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 */
	private <E, P extends Serializable> void updateActualValue(final E entity, final PropertyDescriptor<P, E> prop,
			final List<String> values, final CollectionPropertyDescriptor<P, ?, E> collectionDescriptor) throws MappingException {
		try {
			if (prop.getCardinality() == Cardinality.MULTIPLE) {
				for (final String value : values) {
					final P convertedValue = this.converterTool.convertToInstance(collectionDescriptor.getCollectionType(), value);
					collectionDescriptor.addValue(entity, convertedValue);
				}
			} else {
				final P singleValue = this.converterTool.convertToInstance(prop.getPropertyType(), values.iterator().next());
				prop.setValue(entity, singleValue);
			}
		} catch (final ConverterException e) {
			throw new MappingException(e);
		} catch (final ValueAccessException e) {
			throw new MappingException(e);
		}
	}

	/**
	 * Update the annotation meta values.
	 * 
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @param entity
	 *            The entity.
	 * @param metadataValue
	 *            The meta value.
	 * @throws MappingException
	 *             Thrown if the mapping could not be done.
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	private <P extends Serializable, E> void updateAnnotationValue(final EntityDescriptor<E> entityDescriptor, final E entity,
			final Map.Entry<String, List<AnnotationMetaValues>> metadataValue) throws MappingException {
		final String key = metadataValue.getKey();
		final PropertyDescriptor<P, E> prop = (PropertyDescriptor<P, E>) entityDescriptor.getProperty(key);
		final List<AnnotationMetaValues> values = metadataValue.getValue();
		if (!CheckUtil.isNull(prop) && !values.isEmpty()) {
			CollectionPropertyDescriptor<P, ?, E> collectionDescriptor = null;
			Class<P> innerType = prop.getPropertyType();
			if (Cardinality.MULTIPLE == prop.getCardinality()) {
				collectionDescriptor = prop.adapt(CollectionPropertyDescriptor.class);
				innerType = collectionDescriptor.getCollectionType();
			}
			try {
				if (Cardinality.MULTIPLE == prop.getCardinality()) {
					for (final AnnotationMetaValues metaValue : values) {
						final P innerEntity = this.processMetaValues(metaValue, innerType);
						collectionDescriptor.addValue(entity, innerEntity);
					}
				} else {
					final P innerEntity = this.processMetaValues(values.iterator().next(), innerType);
					prop.setValue(entity, innerEntity);
				}
			} catch (final ValueAccessException e) {
				throw new MappingException(e);
			}
		}
	}

	/**
	 * Update the annotation values.
	 * 
	 * @param entityDescriptor
	 *            The entity descriptor.
	 * @param entity
	 *            The entity.
	 * @param metadataValue
	 *            The assignable values.
	 * @throws MappingException
	 *             Thrown if the mapping could not be done.
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	private <P extends Serializable, E> void updateValue(final EntityDescriptor<E> entityDescriptor, final E entity,
			final Map.Entry<String, List<String>> metadataValue) throws MappingException {
		final String key = metadataValue.getKey();
		final PropertyDescriptor<P, E> prop = (PropertyDescriptor<P, E>) entityDescriptor.getProperty(key);
		final List<String> values = metadataValue.getValue();
		if (!CheckUtil.isNull(prop) && !values.isEmpty()) {
			CollectionPropertyDescriptor<P, ?, E> collectionDescriptor = null;
			if (Cardinality.MULTIPLE == prop.getCardinality()) {
				collectionDescriptor = prop.adapt(CollectionPropertyDescriptor.class);
			}
			this.updateActualValue(entity, prop, values, collectionDescriptor);
		}
	}
}
