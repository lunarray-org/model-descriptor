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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationOperationDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.qualifier.QualifierSelected;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Describes a qualified entity reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public final class EntityReference<E, K extends Serializable>
		implements PresentationEntityDescriptor<E>, QualifierEntityDescriptor<E>, KeyedEntityDescriptor<E, K>, QualifierSelected {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -2060229873008311786L;
	/** The entity delegate. */
	private EntityDescriptor<E, K> delegate;
	/** The entity detail. */
	private EntityDetail entityDetail;
	/** The property references. */
	private Map<String, MemberDescriptor<E>> memberReferences;
	/** The property references. */
	private Map<String, PresentationOperationDescriptor<E>> operationReferences;
	/** The ordered properties. */
	private List<MemberDescriptor<E>> orderedMembers;
	/** The ordered properties. */
	private List<PresentationOperationDescriptor<E>> orderedOperations;
	/** The ordered properties. */
	private List<PresentationPropertyDescriptor<?, E>> orderedProperties;
	/** The property references. */
	private Map<String, PresentationPropertyDescriptor<?, E>> propertyReferences;
	/** The qualifier. */
	private Class<?> qualifier;

	/**
	 * Constructs the entity reference.
	 * 
	 * @param builder
	 *            The builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public EntityReference(final EntityDescriptorBuilderImpl<E, K> builder, final Class<?> qualifier) {
		Validate.notNull(builder, "Builder may not be null.");
		Validate.notNull(qualifier, "Qualifier may not be null.");
		this.entityDetail = builder.getDetail(qualifier);
		this.delegate = builder.getDelegate();
		this.propertyReferences = builder.getProperties(qualifier);
		this.orderedProperties = builder.getOrderedProperties(qualifier);
		this.qualifier = qualifier;
		this.memberReferences = builder.getMembers(qualifier);
		this.orderedMembers = builder.getOrderedMembers(qualifier);
		this.operationReferences = builder.getOperations(qualifier);
		this.orderedOperations = builder.getOrderedOperations(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> clazz) {
		Validate.notNull(clazz, EntityReference.ADAPTER_TYPE_NULL);
		A adapted = null;
		if (QualifierSelected.class.equals(clazz)) {
			adapted = clazz.cast(this);
		} else if (this.delegate.adaptable(clazz)) {
			if (clazz.isAssignableFrom(this.getClass())) {
				adapted = clazz.cast(this);
			} else {
				adapted = this.delegate.adapt(clazz);
			}
		}
		return adapted;
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, EntityReference.ADAPTER_TYPE_NULL);
		boolean result = true;
		if (!QualifierSelected.class.equals(adapterType)) {
			result = this.delegate.adaptable(adapterType);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public E createEntity() throws CreationException {
		return this.getDelegate().createEntity();
	}

	/** {@inheritDoc} */
	@Override
	public Collection<CreationalStrategy<E>> creationalStrategies() {
		return this.getDelegate().getCreationalStrategies();
	}

	/** {@inheritDoc} */
	@Override
	public <X extends EntityExtension<E>> X extension(final Class<X> extensionClazz) {
		return this.delegate.extension(extensionClazz);
	}

	/**
	 * Gets the value for the delegate field.
	 * 
	 * @return The value for the delegate field.
	 */
	public EntityDescriptor<E, K> getDelegate() {
		return this.delegate;
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return DescribedUtil.getDescription(this.entityDetail, this.delegate.getDescription());
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription(final Locale locale) {
		return DescribedUtil.getDescription(this.entityDetail, this.delegate.getDescription(locale), locale);
	}

	/** {@inheritDoc} */
	@Override
	public String getDescriptionKey() {
		return this.entityDetail.getDescriptionKey();
	}

	/**
	 * Gets the value for the entityDetail field.
	 * 
	 * @return The value for the entityDetail field.
	 */
	public EntityDetail getEntityDetail() {
		return this.entityDetail;
	}

	/** {@inheritDoc} */
	@Override
	public Class<E> getEntityType() {
		return this.delegate.getEntityType();
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDescriptor<K, E> getKeyProperty() {
		return this.delegate.getKeyProperty();
	}

	/** {@inheritDoc} */
	@Override
	public MemberDescriptor<E> getMember(final String name) {
		return this.memberReferences.get(name);
	}

	/**
	 * Gets the value for the memberReferences field.
	 * 
	 * @return The value for the memberReferences field.
	 */
	public Map<String, MemberDescriptor<E>> getMemberReferences() {
		return this.memberReferences;
	}

	/** {@inheritDoc} */
	@Override
	public Set<MemberDescriptor<E>> getMembers() {
		return new HashSet<MemberDescriptor<E>>(this.memberReferences.values());
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return this.delegate.getName();
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDescriptor<?, E> getNameProperty() {
		return this.getProperty(this.entityDetail.getNamePropertyName());
	}

	/** {@inheritDoc} */
	@Override
	public OperationDescriptor<E> getOperation(final String name) {
		return this.operationReferences.get(name);
	}

	/**
	 * Gets the value for the operationReferences field.
	 * 
	 * @return The value for the operationReferences field.
	 */
	public Map<String, PresentationOperationDescriptor<E>> getOperationReferences() {
		return this.operationReferences;
	}

	/** {@inheritDoc} */
	@Override
	public Set<OperationDescriptor<E>> getOperations() {
		return new HashSet<OperationDescriptor<E>>(this.operationReferences.values());
	}

	/** {@inheritDoc} */
	@Override
	public Set<OperationDescriptor<E>> getOperations(final Class<?> qualifier) {
		return this.delegate.getOperations(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public List<MemberDescriptor<E>> getOrderedMembers() {
		return this.orderedMembers;
	}

	/** {@inheritDoc} */
	@Override
	public List<OperationDescriptor<E>> getOrderedOperations() {
		return new ArrayList<OperationDescriptor<E>>(this.orderedOperations);
	}

	/** {@inheritDoc} */
	@Override
	public List<PropertyDescriptor<?, E>> getOrderedProperties() {
		return new ArrayList<PropertyDescriptor<?, E>>(this.orderedProperties);
	}

	/** {@inheritDoc} */
	@Override
	public Set<PropertyDescriptor<?, E>> getProperties() {
		return new HashSet<PropertyDescriptor<?, E>>(this.propertyReferences.values());
	}

	/** {@inheritDoc} */
	@Override
	public Set<PropertyDescriptor<?, E>> getProperties(final Class<?> qualifier) {
		return this.delegate.getProperties(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDescriptor<?, E> getProperty(final String name) {
		return this.propertyReferences.get(name);
	}

	/** {@inheritDoc} */
	@Override
	public <P> PropertyDescriptor<P, E> getProperty(final String name, final Class<P> propertyType) {
		PropertyDescriptor<P, E> result = null;
		if (this.propertyReferences.containsKey(name)) {
			@SuppressWarnings("unchecked")
			final PropertyDescriptor<P, E> property = (PropertyDescriptor<P, E>) this.propertyReferences.get(name);
			if (propertyType.equals(property.getPropertyType())) {
				result = property;
			}
		}
		return result;
	}

	/**
	 * Gets the value for the propertyReferences field.
	 * 
	 * @return The value for the propertyReferences field.
	 */
	public Map<String, PresentationPropertyDescriptor<?, E>> getPropertyReferences() {
		return this.propertyReferences;
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getQualifier() {
		return this.qualifier;
	}

	/** {@inheritDoc} */
	@Override
	public QualifierEntityDescriptor<E> getQualifierEntity(final Class<?> qualifier) {
		return this.delegate.getQualifierEntity(qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public OperationDescriptor<E> getQualifierOperation(final String name, final Class<?> qualifier) {
		return this.delegate.getQualifierOperation(name, qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDescriptor<?, E> getQualifierProperty(final String name, final Class<?> qualifier) {
		return this.delegate.getQualifierProperty(name, qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public <P> PropertyDescriptor<P, E> getQualifierProperty(final String name, final Class<?> qualifier, final Class<P> propertyType) {
		return this.delegate.getQualifierProperty(name, qualifier, propertyType);
	}

	/** {@inheritDoc} */
	@Override
	public Set<Class<?>> getQualifiers() {
		return this.delegate.getQualifiers();
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBundle getResourceBundle() {
		ResourceBundle result;
		if (CheckUtil.isNull(this.entityDetail.getResourceBundle())) {
			result = this.delegate.getResourceBundle();
		} else {
			result = this.entityDetail.getResourceBundle();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBundle getResourceBundle(final Locale locale) {
		ResourceBundle result;
		if (CheckUtil.isNull(this.entityDetail.getResourceBundle())) {
			result = this.delegate.getResourceBundle(locale);
		} else {
			result = this.entityDetail.getResourceBundle(locale);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisible() {
		return this.entityDetail.isVisible();
	}

	/**
	 * Sets a new value for the delegate field.
	 * 
	 * @param delegate
	 *            The new value for the delegate field.
	 */
	public void setDelegate(final EntityDescriptor<E, K> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Sets a new value for the entityDetail field.
	 * 
	 * @param entityDetail
	 *            The new value for the entityDetail field.
	 */
	public void setEntityDetail(final EntityDetail entityDetail) {
		this.entityDetail = entityDetail;
	}

	/**
	 * Sets a new value for the memberReferences field.
	 * 
	 * @param memberReferences
	 *            The new value for the memberReferences field.
	 */
	public void setMemberReferences(final Map<String, MemberDescriptor<E>> memberReferences) {
		this.memberReferences = memberReferences;
	}

	/**
	 * Sets a new value for the operationReferences field.
	 * 
	 * @param operationReferences
	 *            The new value for the operationReferences field.
	 */
	public void setOperationReferences(final Map<String, PresentationOperationDescriptor<E>> operationReferences) {
		this.operationReferences = operationReferences;
	}

	/**
	 * Sets a new value for the orderedMembers field.
	 * 
	 * @param orderedMembers
	 *            The new value for the orderedMembers field.
	 */
	public void setOrderedMembers(final List<MemberDescriptor<E>> orderedMembers) {
		this.orderedMembers = orderedMembers;
	}

	/**
	 * Sets a new value for the orderedOperations field.
	 * 
	 * @param orderedOperations
	 *            The new value for the orderedOperations field.
	 */
	public void setOrderedOperations(final List<PresentationOperationDescriptor<E>> orderedOperations) {
		this.orderedOperations = orderedOperations;
	}

	/**
	 * Sets a new value for the orderedProperties field.
	 * 
	 * @param orderedProperties
	 *            The new value for the orderedProperties field.
	 */
	public void setOrderedProperties(final List<PresentationPropertyDescriptor<?, E>> orderedProperties) {
		this.orderedProperties = orderedProperties;
	}

	/**
	 * Sets a new value for the propertyReferences field.
	 * 
	 * @param propertyReferences
	 *            The new value for the propertyReferences field.
	 */
	public void setPropertyReferences(final Map<String, PresentationPropertyDescriptor<?, E>> propertyReferences) {
		this.propertyReferences = propertyReferences;
	}

	/**
	 * Sets a new value for the qualifier field.
	 * 
	 * @param qualifier
	 *            The new value for the qualifier field.
	 */
	public void setQualifier(final Class<?> qualifier) {
		this.qualifier = qualifier;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationEntityReference[\n");
		builder.append(this.entityDetail.toString());
		builder.append("\tOrdered Properties: {\n\t\t");
		final List<String> propertyNames = new LinkedList<String>();
		for (final PresentationPropertyDescriptor<?, E> property : this.orderedProperties) {
			propertyNames.add(property.getName());
		}
		StringUtil.commaSeparated(propertyNames, StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n\tQualifiers: {\n\t\t");
		StringUtil.commaSeparated(this.delegate.getQualifiers(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n]");
		return builder.toString();
	}
}
