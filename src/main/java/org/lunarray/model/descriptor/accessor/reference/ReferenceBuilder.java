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
package org.lunarray.model.descriptor.accessor.reference;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.operation.PersistentOperation;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.accessor.property.PersistentProperty;
import org.lunarray.model.descriptor.accessor.property.Property;
import org.lunarray.model.descriptor.accessor.property.PropertyBuilder;
import org.lunarray.model.descriptor.accessor.reference.operation.OperationReference;
import org.lunarray.model.descriptor.accessor.reference.operation.impl.DirectOperationReference;
import org.lunarray.model.descriptor.accessor.reference.operation.impl.PropertyOperationReference;
import org.lunarray.model.descriptor.accessor.reference.property.PropertyValueReference;
import org.lunarray.model.descriptor.accessor.reference.property.impl.DeferredPropertyValueReference;
import org.lunarray.model.descriptor.accessor.reference.property.impl.DirectPropertyValueReference;
import org.lunarray.model.descriptor.accessor.reference.property.impl.PropertyReference;
import org.lunarray.model.descriptor.accessor.reference.property.impl.PropertyReference.Builder;
import org.lunarray.model.descriptor.builder.Configuration;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;

/**
 * A context for accessors.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <T>
 *            The to type.
 * @param <F>
 *            The from type.
 */
public final class ReferenceBuilder<T, F> {

	/** The accessor deque. */
	private final transient Deque<PropertyReference<T, F>> accessors;
	/** The configuration. */
	private final transient Configuration configuration;
	/** The embedding deque. */
	private final transient Deque<String> embedded;
	/** Optional object factory. */
	private transient ExtensionRef<ObjectFactory> objectFactory;
	/** The properties deque. */
	private final transient Deque<PropertyBuilder<?>> properties;

	/**
	 * Constructs the context.
	 * 
	 * @param configuration
	 *            The configuration.
	 */
	public ReferenceBuilder(final Configuration configuration) {
		this.configuration = configuration;
		this.accessors = new LinkedList<PropertyReference<T, F>>();
		this.embedded = new LinkedList<String>();
		this.properties = new LinkedList<PropertyBuilder<?>>();
	}

	/**
	 * Gets the current reference.
	 * 
	 * @return The reference.
	 */
	@SuppressWarnings("unchecked")
	// We know it's an object factory.
	public PropertyValueReference<T, F> getCurrentReference() {
		final PropertyReference<T, F> accessor = this.accessors.pop();
		PropertyValueReference<T, F> valueReference = null;
		valueReference = new DirectPropertyValueReference<T, F>(accessor);
		for (final PropertyReference<T, F> accessorParent : this.accessors) {
			final PropertyReference<F, F> accessorFrom = (PropertyReference<F, F>) accessorParent;
			org.lunarray.model.descriptor.accessor.reference.property.impl.DeferredPropertyValueReference.Builder<T, F, F> builder;
			builder = DeferredPropertyValueReference.createBuilder();
			builder.objectFactoryReference(this.objectFactory);
			builder.valueReference(valueReference).propertyAccessor(accessorFrom);
			valueReference = builder.build();
		}
		this.accessors.push(accessor);
		return valueReference;
	}

	/**
	 * Gets the current reference.
	 * 
	 * @param operation
	 *            The operation to get the reference for.
	 * @return The reference.
	 */
	@SuppressWarnings("unchecked")
	// We know it's an object factory.
	public OperationReference<F> getCurrentReference(final PersistentOperation operation) {
		final OperationReference<F> reference;
		final DirectOperationReference<T> directReference = new DirectOperationReference<T>(operation);
		if (this.accessors.isEmpty()) {
			reference = (OperationReference<F>) directReference;
		} else {
			reference = new PropertyOperationReference<F, T>(directReference, this.getCurrentReference());
		}
		return reference;
	}

	/**
	 * Gets the properties.
	 * 
	 * @return The properties.
	 */
	public Deque<DescribedProperty<?>> getDescribedProperties() {
		final Deque<DescribedProperty<?>> result = new LinkedList<DescribedProperty<?>>();
		for (final PropertyBuilder<?> property : this.properties) {
			result.add(property.buildDescribed());
		}
		return result;
	}

	/**
	 * Gets the properties.
	 * 
	 * @return The properties.
	 */
	public Deque<Property<?>> getProperties() {
		final Deque<Property<?>> result = new LinkedList<Property<?>>();
		for (final PropertyBuilder<?> property : this.properties) {
			result.add(property.build());
		}
		return result;
	}

	/**
	 * Sets a new value for the objectFactoryRef field.
	 * 
	 * @param objectFactoryRef
	 *            The new value for the objectFactoryRef field.
	 * @return The context.
	 */
	public ReferenceBuilder<T, F> objectFactoryRef(final ExtensionRef<ObjectFactory> objectFactoryRef) {
		this.objectFactory = objectFactoryRef;
		return this;
	}

	/**
	 * Pop a property.
	 */
	public void popProperty() {
		this.accessors.poll();
		this.embedded.poll();
		this.properties.poll();
	}

	/**
	 * Add a property.
	 * 
	 * @param propertyBuilder
	 *            The property to add. May not be null.
	 * @param name
	 *            The property name.
	 */
	@SuppressWarnings("unchecked")
	// We know this is the correct type.
	public void pushProperty(final PropertyBuilder<?> propertyBuilder, final String name) {
		Validate.notNull(propertyBuilder, "Property may not be null.");
		final PersistentProperty<T> property = (PersistentProperty<T>) propertyBuilder.build();
		final Builder<T> builder = PropertyReference.createBuilder();
		builder.property(property);
		final PropertyReference<T, F> accessor = builder.build();
		this.accessors.push(accessor);
		this.embedded.push(name);
		this.properties.push(propertyBuilder);
	}

	/**
	 * Resolve the property name.
	 * 
	 * @return The name.
	 */
	public String resolveName() {
		return this.resolveNameBuilder().toString();
	}

	/**
	 * Resolve the operation name.
	 * 
	 * @param operationDescriptor
	 *            The operation.
	 * @param name
	 *            The name.
	 * @return The name.
	 */
	public String resolveName(final DescribedOperation operationDescriptor, final String name) {
		final StringBuilder nameBuilder = this.resolveNameBuilder();
		if (!this.embedded.isEmpty()) {
			nameBuilder.append(this.configuration.embeddedIndicator());
		}
		nameBuilder.append(name);
		return nameBuilder.toString();
	}

	/**
	 * Resolves the name and returns the builder.
	 * 
	 * @return The name builder.
	 */
	private StringBuilder resolveNameBuilder() {
		final StringBuilder nameBuilder = new StringBuilder();
		final Iterator<String> iterator = this.embedded.descendingIterator();
		if (iterator.hasNext()) {
			nameBuilder.append(iterator.next());
		}
		while (iterator.hasNext()) {
			nameBuilder.append(this.configuration.embeddedIndicator());
			nameBuilder.append(iterator.next());
		}
		return nameBuilder;
	}
}
