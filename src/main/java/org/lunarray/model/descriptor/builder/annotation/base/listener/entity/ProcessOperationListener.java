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
package org.lunarray.model.descriptor.builder.annotation.base.listener.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.common.event.EventException;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.operation.PersistentOperation;
import org.lunarray.model.descriptor.accessor.property.AbstractProperty;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.accessor.reference.ReferenceBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.build.entity.AnnotationEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationCollectionResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationOperationDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.OperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.util.ModelBuilderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener that processes operations.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder context type.
 */
public final class ProcessOperationListener<E, K extends Serializable, B extends AnnotationBuilderContext>
		extends AbstractUpdateEntityTypeListener<E, K, B> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessOperationListener.class);

	/** Default constructor. */
	public ProcessOperationListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedEntityTypeEvent<E, K, B> event) throws EventException {
		ProcessOperationListener.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		// Pass operations.
		final AnnotationEntityDescriptorBuilder<E, K, B> builder = event.getBuilder();
		builder.getBuilderContext().setAccessorContext(new ReferenceBuilder<Object, E>(builder.getBuilderContext().getConfiguration()));
		this.processOperations(builder, event.getEntity());
	}

	/**
	 * Process a collection result.
	 * 
	 * @param operationDescriptor
	 *            The operation descriptor.
	 * @return The collection result descriptor.
	 */
	private AnnotationCollectionResultDescriptorBuilder<Object, Collection<Object>, E, B> processCollectionResult(
			final AnnotationOperationDescriptorBuilder<E, B> operationDescriptor) {
		operationDescriptor.getBuilderContext().getAccessorContext();
		return operationDescriptor.createCollectionResultTypeBuilder();
	}

	/**
	 * Process operations.
	 * 
	 * @param builder
	 *            The builder.
	 * @param entityType
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	/* Deduced type. */
	private void processOperations(final AnnotationEntityDescriptorBuilder<E, K, B> builder, final DescribedEntity<E> entityType) {
		final B builderContext = builder.getBuilderContext();
		final List<DescribedProperty<?>> properties = builderContext.getPropertyResolverStrategy().resolveProperties(entityType);
		final ReferenceBuilder<Object, E> ctx = builderContext.getAccessorContext();
		final OperationAttributeResolverStrategy attributeOperationStrategy = builderContext.getOperationAttributeResolverStrategy();
		final PropertyAttributeResolverStrategy attributePropertyStrategy = builderContext.getPropertyAttributeResolverStrategy();
		for (final DescribedProperty<?> property : properties) {
			if (attributePropertyStrategy.isIgnore(property)) {
				// Ignore.
				ProcessOperationListener.LOGGER.info("Ignored property '{}'", property.getName());
			} else {
				// Process regular property.
				ctx.pushProperty(AbstractProperty.createBuilder().describedProperty(property), attributePropertyStrategy.getName(property));
				if (attributePropertyStrategy.isEmbedded(property)) {
					final DescribedEntity<E> entity = (DescribedEntity<E>) builderContext.getEntityResolverStrategy().resolveEntity(
							property.getType());
					this.processOperations(builder, entity);
				}
				property.getType();
				ctx.popProperty();
			}
		}
		// Process operations.
		final List<DescribedOperation> operations = builderContext.getOperationResolverStrategy().resolveOperations(entityType);
		for (final DescribedOperation operation : operations) {
			if (attributeOperationStrategy.isIgnore(operation)) {
				// Ignore.
				ProcessOperationListener.LOGGER.info("Ignored operation '{}'.", operation.getName());
			} else {
				final AnnotationOperationDescriptorBuilder<E, B> operationBuilder = builder.addOperation();
				// Set operation.
				operationBuilder.operation(operation);
				// Processing result type.
				// Get Type
				final Class<?> resultType = operation.getResultType();
				// Get cardinality
				final Cardinality cardinality = ModelBuilderUtil.resolveCardinality(resultType);
				final AnnotationResultDescriptorBuilder<?, E, B> resultBuilder;
				if (Cardinality.MULTIPLE == cardinality) {
					resultBuilder = this.processCollectionResult(operationBuilder);
				} else {
					resultBuilder = this.processResult(operationBuilder);
				}
				// Set known values.
				resultBuilder.cardinality(cardinality).type(resultType);
				// Set operation values.
				// Set name.
				operationBuilder.name(ctx.resolveName(operation, attributeOperationStrategy.getName(operation)));
				// Set operation reference.
				operationBuilder.operationReference(ctx.getCurrentReference(PersistentOperation.createBuilder()
						.describedOperation(operation).build()));
			}
		}
	}

	/**
	 * Process a result.
	 * 
	 * @param operationDescriptor
	 *            The operation descriptor.
	 * @return The result descriptor.
	 */
	private AnnotationResultDescriptorBuilder<?, E, B> processResult(final AnnotationOperationDescriptorBuilder<E, B> operationDescriptor) {
		operationDescriptor.getBuilderContext().getAccessorContext();
		return operationDescriptor.createResultTypeBuilder();
	}
}
