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
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;

/**
 * Represents a property descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 */
public abstract class AbstractResultDescriptor<R>
		implements ResultDescriptor<R>, RelationDescriptor {

	/** The serial id. */
	private static final long serialVersionUID = -8471309824912294687L;
	/** The parameters cardinality. */
	private Cardinality cardinality;
	/** The related entity name. */
	private String relatedName;
	/** The related property. */
	private RelationType relationType;
	/** The type. */
	private Class<R> resultType;

	/**
	 * Constructs the property descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected AbstractResultDescriptor(final AbstractResultDescriptorBuilder<R, ?, ?> builder) {
		Validate.notNull(builder, "Builder may not be null.");
		this.cardinality = builder.getCardinality();
		this.relatedName = builder.getRelatedName();
		this.relationType = builder.getRelationType();
		this.resultType = builder.getResultType();
	}

	/** {@inheritDoc} */
	@Override
	public final Cardinality getCardinality() {
		return this.cardinality;
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
	public final Class<R> getResultType() {
		return this.resultType;
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
	 * Sets a new value for the resultType field.
	 * 
	 * @param resultType
	 *            The new value for the resultType field.
	 */
	public final void setResultType(final Class<R> resultType) {
		this.resultType = resultType;
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
	protected final <A> A resultAdapt(final Class<A> clazz) {
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
	protected final boolean resultAdaptable(final Class<?> clazz) {
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
	protected final void resultToString(final StringBuilder builder) {
		builder.append("\tType: ").append(this.resultType);
		builder.append("\n\tCardinality: ").append(this.cardinality);
		if (this.isRelation()) {
			builder.append("\n\tRelation type: ").append(this.relationType);
			builder.append("\n\tRelated Name: ").append(this.relatedName);
		}
		builder.append("\n");
	}
}
