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

import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.AbstractDescribedDetail;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * The entity detail.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class EntityDetail
		extends AbstractDescribedDetail {

	/** Serial id. */
	private static final long serialVersionUID = -9178936818970755312L;
	/** The name property name. */
	private String namePropertyName;

	/**
	 * Constructs the detail.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	public EntityDetail(final EntityDetailBuilderImpl builder) {
		super(builder);
		this.namePropertyName = builder.getNamePropertyName();
	}

	/**
	 * Gets the value for the namePropertyName field.
	 * 
	 * @return The value for the namePropertyName field.
	 */
	public String getNamePropertyName() {
		return this.namePropertyName;
	}

	/**
	 * Sets a new value for the namePropertyName field.
	 * 
	 * @param namePropertyName
	 *            The new value for the namePropertyName field.
	 */
	public void setNamePropertyName(final String namePropertyName) {
		this.namePropertyName = namePropertyName;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		super.describedToString(builder);
		if (StringUtil.isEmptyString(this.namePropertyName)) {
			builder.append("\tName Property: ").append(this.namePropertyName);
			builder.append("\n");
		}
		return builder.toString();
	}
}
