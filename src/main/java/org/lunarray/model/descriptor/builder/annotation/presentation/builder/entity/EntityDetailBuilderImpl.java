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

import org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail.AbstractDescribedDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.EntityDetailBuilder;

/**
 * The detail builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class EntityDetailBuilderImpl
		extends AbstractDescribedDetailBuilder
		implements EntityDetailBuilder {

	/**
	 * Creates a new builder.
	 * 
	 * @return A new builder.
	 */
	public static EntityDetailBuilderImpl create() {
		return new EntityDetailBuilderImpl();
	}

	/** The name property name. */
	private transient String namePropertyNameBuilder;

	/** Default constructor. */
	private EntityDetailBuilderImpl() {
		super();
		this.namePropertyNameBuilder = "";
	}

	/**
	 * Build the details.
	 * 
	 * @return The details.
	 */
	public EntityDetail build() {
		return new EntityDetail(this);
	}

	/**
	 * Updates the name property.
	 * 
	 * @param namePropertyName
	 *            The name property.
	 * @return The builder.
	 */
	public EntityDetailBuilderImpl namePropertyName(final String namePropertyName) {
		this.namePropertyNameBuilder = namePropertyName;
		return this;
	}

	/**
	 * Gets the value for the namePropertyName field.
	 * 
	 * @return The value for the namePropertyName field.
	 */
	protected String getNamePropertyName() {
		return this.namePropertyNameBuilder;
	}
}
