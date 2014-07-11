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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.reference;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.qualifier.QualifierSelected;

/**
 * Common reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public abstract class AbstractReference
		implements QualifierSelected, Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 5170450566635710359L;
	/** The current qualifier. */
	private Class<?> qualifier;

	/**
	 * Constructs the refence.
	 * 
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public AbstractReference(final Class<?> qualifier) {
		Validate.notNull(qualifier, "Qualifier may not be null.");
		this.qualifier = qualifier;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<?> getQualifier() {
		return this.qualifier;
	}

	/**
	 * Sets a new value for the qualifier field.
	 * 
	 * @param qualifier
	 *            The new value for the qualifier field.
	 */
	public final void setQualifier(final Class<?> qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void referenceToString(final StringBuilder builder) {
		builder.append("\n\tQualifier: ").append(this.qualifier.getCanonicalName());
	}
}
