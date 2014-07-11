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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.detail;

import org.lunarray.model.descriptor.builder.annotation.presentation.DescribedDetailBuilder;

/**
 * A described builder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public abstract class AbstractDescribedDetailBuilder
		implements DescribedDetailBuilder {

	/** The description key. */
	private transient String descriptionKeyBuilder;
	/** The resource bundle. */
	private transient String resourceBundleBuilder;
	/** Visible indication. */
	private transient boolean visibleBuilder;

	/** Default constructor. */
	public AbstractDescribedDetailBuilder() {
		this.descriptionKeyBuilder = "";
		this.visibleBuilder = true;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedDetailBuilder descriptionKey(final String descriptionKey) {
		this.descriptionKeyBuilder = descriptionKey;
		return this;
	}

	/**
	 * Gets the value for the descriptionKey field.
	 * 
	 * @return The value for the descriptionKey field.
	 */
	public final String getDescriptionKey() {
		return this.descriptionKeyBuilder;
	}

	/**
	 * Gets the value for the resourceBundle field.
	 * 
	 * @return The value for the resourceBundle field.
	 */
	public final String getResourceBundle() {
		return this.resourceBundleBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedDetailBuilder invisible() {
		this.visibleBuilder = false;
		return this;
	}

	/**
	 * Gets the value for the visible field.
	 * 
	 * @return The value for the visible field.
	 */
	public final boolean isVisible() {
		return this.visibleBuilder;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedDetailBuilder resourceBundle(final String resourceBundle) {
		this.resourceBundleBuilder = resourceBundle;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public final DescribedDetailBuilder visible() {
		this.visibleBuilder = true;
		return this;
	}
}
