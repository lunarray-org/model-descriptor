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
package org.lunarray.model.descriptor.builder.annotation.base.builders.operation;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.util.PrimitiveUtil;

/**
 * Represents a parameter descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 */
public abstract class AbstractParameterDescriptor<P>
		implements ParameterDescriptor<P>, RelationDescriptor {

	/** Serial id. */
	private static final long serialVersionUID = 7258047746504646235L;
	/** The parameters cardinality. */
	private Cardinality cardinality;
	/** The index. */
	private int index;
	/** The type. */
	private Class<P> parameterType;
	/** The related entity name. */
	private String relatedName;
	/** The related parameter. */
	private RelationType relationType;

	/**
	 * Constructs the parameter descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected AbstractParameterDescriptor(final AbstractParameterDescriptorBuilder<P, ?> builder) {
		Validate.notNull(builder, "Builder may not be null.");
		this.parameterType = builder.getParameterType();
		this.cardinality = builder.getCardinality();
		this.relatedName = builder.getRelatedName();
		this.relationType = builder.getRelationType();
		this.index = builder.getIndex();
	}

	/** {@inheritDoc} */
	@Override
	public final Cardinality getCardinality() {
		return this.cardinality;
	}

	/** {@inheritDoc} */
	@Override
	public final int getIndex() {
		return this.index;
	}

	/**
	 * Gets the value for the parameterType field.
	 * 
	 * @return The value for the parameterType field.
	 */
	public final Class<P> getParameterType() {
		return this.parameterType;
	}

	/** {@inheritDoc} */
	@Override
	public final String getRelatedName() {
		return this.relatedName;
	}

	/** {@inheritDoc} */
	@Override
	public final RelationType getRelationType() {
		return this.relationType;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<P> getType() {
		return this.parameterType;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isAssignable(final Object value) {
		boolean result;
		if (CheckUtil.isNull(value)) {
			result = Cardinality.NULLABLE == this.getCardinality();
		} else {
			final Class<?> valueType = value.getClass();
			if (valueType.equals(this.getType())) {
				result = true;
			} else if (this.getType().isPrimitive()) {
				result = this.isAssignablePrimitive(valueType);
			} else {
				result = this.getType().isAssignableFrom(valueType);
			}
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean isRelation() {
		return !CheckUtil.isNull(this.relationType);
	}

	/**
	 * Sets a new value for the cardinality field.
	 * 
	 * @param cardinality
	 *            The new value for the cardinality field.
	 */
	public final void setCardinality(final Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	/**
	 * Sets a new value for the index field.
	 * 
	 * @param index
	 *            The new value for the index field.
	 */
	public final void setIndex(final int index) {
		this.index = index;
	}

	/**
	 * Sets a new value for the parameterType field.
	 * 
	 * @param parameterType
	 *            The new value for the parameterType field.
	 */
	public final void setParameterType(final Class<P> parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * Sets a new value for the relatedName field.
	 * 
	 * @param relatedName
	 *            The new value for the relatedName field.
	 */
	public final void setRelatedName(final String relatedName) {
		this.relatedName = relatedName;
	}

	/**
	 * Sets a new value for the relationType field.
	 * 
	 * @param relationType
	 *            The new value for the relationType field.
	 */
	public final void setRelationType(final RelationType relationType) {
		this.relationType = relationType;
	}

	/**
	 * Tests if the value type is an assignable primitive type.
	 * 
	 * @param valueType
	 *            The value type.
	 * @return True if and only if it is assignable.
	 */
	private boolean isAssignablePrimitive(final Class<?> valueType) {
		boolean result;
		final Class<?> objectType = PrimitiveUtil.getObjectType(this.getType());
		if (CheckUtil.isNull(objectType)) {
			result = false;
		} else {
			result = objectType.isAssignableFrom(valueType);
		}
		return result;
	}

	/**
	 * Adapt to a new type.
	 * 
	 * @param <A>
	 *            The new type.
	 * @param clazz
	 *            The new type.
	 * @return The current instance is the new type.
	 */
	protected final <A> A parameterAdapt(final Class<A> clazz) {
		A value = null;
		if (RelationDescriptor.class.equals(clazz)) {
			if (this.isRelation()) {
				value = clazz.cast(this);
			}
		} else if (clazz.isInstance(this)) {
			value = clazz.cast(this);
		}
		return value;
	}

	/**
	 * Tests if adapting to the new type is possible.
	 * 
	 * @param clazz
	 *            The new type.
	 * @return True if and only if the current instance is adaptable.
	 */
	protected final boolean parameterAdaptable(final Class<?> clazz) {
		boolean result = true;
		if (RelationDescriptor.class.equals(clazz)) {
			result = this.isRelation();
		} else {
			result = clazz.isInstance(this);
		}
		return result;
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void parameterToString(final StringBuilder builder) {
		builder.append("\tType: ").append(this.parameterType);
		builder.append("\n\tIndex: ").append(this.index);
		builder.append("\n\tCardinality: ").append(this.cardinality);
		if (this.isRelation()) {
			builder.append("\n\trelation type: ").append(this.relationType);
			builder.append("\n\tRelated Name: ").append(this.relatedName);
		}
		builder.append("\n");
	}

}
