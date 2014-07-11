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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.builder.annotation.base.builders.entity.AbstractEntityDescriptor;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.DescribedUtil;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationOperationDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Describes an entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public final class EntityDescriptor<E, K extends Serializable>
		extends AbstractEntityDescriptor<E, K>
		implements PresentationEntityDescriptor<E>, QualifierEntityDescriptor<E> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = -8535961815837935295L;
	/** The entity detail. */
	private EntityDetail entityDetail;
	/** The ordered members. */
	private List<MemberDescriptor<E>> orderedMembers;
	/** The ordered operations. */
	private List<PresentationOperationDescriptor<E>> orderedOperations;
	/** The ordered properties. */
	private List<PresentationPropertyDescriptor<?, E>> orderedProperties;
	/** The qualifier entity references. */
	private Map<Class<?>, QualifierEntityDescriptor<E>> qualifierEntityReferences;
	/** The operations by qualifier. */
	private Map<Class<?>, Map<String, PresentationOperationDescriptor<E>>> qualifierOperations;
	/** The properties by qualifier. */
	private Map<Class<?>, Map<String, PresentationPropertyDescriptor<?, E>>> qualifierProperties;
	/** The qualifiers. */
	private Set<Class<?>> qualifiers;

	/**
	 * Constructs the descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected EntityDescriptor(final EntityDescriptorBuilderImpl<E, K> builder) {
		super(builder);
		this.qualifiers = builder.getQualifiers();
		this.entityDetail = builder.getDetail();
		this.qualifierEntityReferences = builder.getEntityReferences();
		this.qualifierProperties = builder.getPropertiesByQualifier();
		this.orderedProperties = builder.getOrderedProperties();
		this.qualifierOperations = builder.getOperationsByQualifier();
		this.orderedOperations = builder.getOrderedOperations();
		this.orderedMembers = builder.getOrderedMembers();
	}

	/** {@inheritDoc} */
	@Override
	public <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, EntityDescriptor.ADAPTER_TYPE_NULL);
		return this.entityAdapt(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, EntityDescriptor.ADAPTER_TYPE_NULL);
		return this.entityAdaptable(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription() {
		return DescribedUtil.getDescription(this.entityDetail, this.getDescriptionKey());
	}

	/** {@inheritDoc} */
	@Override
	public String getDescription(final Locale locale) {
		return DescribedUtil.getDescription(this.entityDetail, this.getDescriptionKey(), locale);
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
	public PropertyDescriptor<?, E> getNameProperty() {
		PropertyDescriptor<?, E> result = null;
		final String propertyName = this.entityDetail.getNamePropertyName();
		if (!CheckUtil.isNull(propertyName)) {
			result = this.getProperty(propertyName);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Set<OperationDescriptor<E>> getOperations(final Class<?> qualifier) {
		Set<OperationDescriptor<E>> result;
		if (this.qualifierOperations.containsKey(qualifier)) {
			result = new HashSet<OperationDescriptor<E>>(this.qualifierOperations.get(qualifier).values());
		} else {
			result = this.getOperations();
		}
		return result;

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
	public Set<PropertyDescriptor<?, E>> getProperties(final Class<?> qualifier) {
		Set<PropertyDescriptor<?, E>> result;
		if (this.qualifierProperties.containsKey(qualifier)) {
			result = new HashSet<PropertyDescriptor<?, E>>(this.qualifierProperties.get(qualifier).values());
		} else {
			result = this.getProperties();
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public QualifierEntityDescriptor<E> getQualifierEntity(final Class<?> qualifier) {
		QualifierEntityDescriptor<E> result;
		if (this.qualifierEntityReferences.containsKey(qualifier)) {
			result = this.qualifierEntityReferences.get(qualifier);
		} else {
			result = this;
		}
		return result;
	}

	/**
	 * Gets the value for the qualifierEntityReferences field.
	 * 
	 * @return The value for the qualifierEntityReferences field.
	 */
	public Map<Class<?>, QualifierEntityDescriptor<E>> getQualifierEntityReferences() {
		return this.qualifierEntityReferences;
	}

	/** {@inheritDoc} */
	@Override
	public OperationDescriptor<E> getQualifierOperation(final String name, final Class<?> qualifier) {
		OperationDescriptor<E> result;
		final String memberName = this.resolveMemberName(name);
		if (this.qualifierOperations.containsKey(qualifier) && this.qualifierOperations.get(qualifier).containsKey(memberName)) {
			result = this.qualifierOperations.get(qualifier).get(memberName);
		} else {
			result = this.getOperation(memberName);
		}
		return result;
	}

	/**
	 * Gets the value for the qualifierOperations field.
	 * 
	 * @return The value for the qualifierOperations field.
	 */
	public Map<Class<?>, Map<String, PresentationOperationDescriptor<E>>> getQualifierOperations() {
		return this.qualifierOperations;
	}

	/**
	 * Gets the value for the qualifierProperties field.
	 * 
	 * @return The value for the qualifierProperties field.
	 */
	public Map<Class<?>, Map<String, PresentationPropertyDescriptor<?, E>>> getQualifierProperties() {
		return this.qualifierProperties;
	}

	/** {@inheritDoc} */
	@Override
	public PropertyDescriptor<?, E> getQualifierProperty(final String name, final Class<?> qualifier) {
		PropertyDescriptor<?, E> result;
		final String propertyName = this.resolveMemberName(name);
		if (this.qualifierProperties.containsKey(qualifier) && this.qualifierProperties.get(qualifier).containsKey(propertyName)) {
			result = this.qualifierProperties.get(qualifier).get(propertyName);
		} else {
			result = this.getProperty(propertyName);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	/* Is checked, just not cast. */
	@Override
	public <P> PropertyDescriptor<P, E> getQualifierProperty(final String name, final Class<?> qualifier, final Class<P> propertyType) {
		PropertyDescriptor<P, E> result = null;
		final String propertyName = this.resolveMemberName(name);
		if (this.qualifierProperties.containsKey(qualifier) && this.qualifierProperties.get(qualifier).containsKey(propertyName)) {
			final PropertyDescriptor<P, E> property = (PropertyDescriptor<P, E>) this.qualifierProperties.get(qualifier).get(propertyName);
			if (propertyType.equals(property.getPropertyType())) {
				result = property;
			}
		} else {
			result = this.getProperty(propertyName, propertyType);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Set<Class<?>> getQualifiers() {
		return new HashSet<Class<?>>(this.qualifiers);
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBundle getResourceBundle() {
		return this.entityDetail.getResourceBundle();
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBundle getResourceBundle(final Locale locale) {
		return this.entityDetail.getResourceBundle(locale);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isVisible() {
		return this.entityDetail.isVisible();
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
	 * Sets a new value for the qualifierEntityReferences field.
	 * 
	 * @param qualifierEntityReferences
	 *            The new value for the qualifierEntityReferences field.
	 */
	public void setQualifierEntityReferences(final Map<Class<?>, QualifierEntityDescriptor<E>> qualifierEntityReferences) {
		this.qualifierEntityReferences = qualifierEntityReferences;
	}

	/**
	 * Sets a new value for the qualifierOperations field.
	 * 
	 * @param qualifierOperations
	 *            The new value for the qualifierOperations field.
	 */
	public void setQualifierOperations(final Map<Class<?>, Map<String, PresentationOperationDescriptor<E>>> qualifierOperations) {
		this.qualifierOperations = qualifierOperations;
	}

	/**
	 * Sets a new value for the qualifierProperties field.
	 * 
	 * @param qualifierProperties
	 *            The new value for the qualifierProperties field.
	 */
	public void setQualifierProperties(final Map<Class<?>, Map<String, PresentationPropertyDescriptor<?, E>>> qualifierProperties) {
		this.qualifierProperties = qualifierProperties;
	}

	/**
	 * Sets a new value for the qualifiers field.
	 * 
	 * @param qualifiers
	 *            The new value for the qualifiers field.
	 */
	public void setQualifiers(final Set<Class<?>> qualifiers) {
		this.qualifiers = qualifiers;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationEntityDesciptor[\n");
		super.entityToString(builder);
		builder.append(this.entityDetail.toString());
		builder.append("\tOrdered Properties: {\n\t\t");
		final List<String> propertyNames = new LinkedList<String>();
		for (final PresentationPropertyDescriptor<?, E> property : this.orderedProperties) {
			propertyNames.add(property.getName());
		}
		StringUtil.commaSeparated(propertyNames, StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n\tQualifiers: {\n\t\t");
		StringUtil.commaSeparated(this.qualifiers, StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n]");
		return builder.toString();
	}
}
