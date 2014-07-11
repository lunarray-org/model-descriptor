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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.property;

import org.lunarray.model.descriptor.builder.annotation.presentation.property.PresQualPropertyDescriptor;

/**
 * Describes a property reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class PropertyReference<P, E>
		extends AbstractPropertyReference<P, E> {

	/** Serial id. */
	private static final long serialVersionUID = 7954514827527406393L;

	/**
	 * Constructs the refence.
	 * 
	 * @param delegate
	 *            The delegate. May not be null.
	 * @param propertyDetail
	 *            The detail. May not be null.
	 * @param qualifier
	 *            The qualifier. May not be null.
	 */
	public PropertyReference(final PresQualPropertyDescriptor<P, E> delegate, final PropertyDetail propertyDetail, final Class<?> qualifier) {
		super(delegate, propertyDetail, qualifier);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationPropertyReference[\n");
		this.propertyToString(builder);
		builder.append("]");
		return builder.toString();
	}
}
