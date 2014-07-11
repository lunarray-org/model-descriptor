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
package org.lunarray.model.descriptor.builder.annotation.presentation.builder.model;

import org.lunarray.model.descriptor.builder.annotation.base.builders.model.AbstractModel;

/**
 * Implement the model.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The entity super type.
 */
public final class ModelImpl<S>
		extends AbstractModel<S> {

	/** Serial id. */
	private static final long serialVersionUID = 2285235458926837507L;

	/**
	 * Constructs the model.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	public ModelImpl(final ModelBuilder<S> builder) {
		super(builder);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PresentationModel[\n");
		this.modelToString(builder);
		builder.append("\n]");
		return builder.toString();
	}
}
