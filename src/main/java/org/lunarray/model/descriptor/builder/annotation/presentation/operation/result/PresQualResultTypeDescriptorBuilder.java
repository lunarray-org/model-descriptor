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
package org.lunarray.model.descriptor.builder.annotation.presentation.operation.result;

import org.lunarray.model.descriptor.builder.annotation.base.build.operation.AnnotationResultDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationResultDescriptor;

/**
 * A result type descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <R>
 *            The result type.
 * @param <E>
 *            The entity type.
 */
public interface PresQualResultTypeDescriptorBuilder<R, E>
		extends AnnotationResultDescriptorBuilder<R, E, PresQualBuilderContext> {

	/**
	 * Gets the detail builder.
	 * 
	 * @return The builder.
	 */
	ResultDetailBuilder getDetailBuilder();

	/**
	 * Gets the detail.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The detail.
	 */
	ResultDetailBuilder getDetailBuilder(final Class<?> qualifier);

	/**
	 * Add builder.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The builder.
	 */
	ResultDetailBuilder getQualifierBuilder(final Class<?> qualifier);

	/**
	 * Gets the descriptor.
	 * 
	 * @return The descriptor.
	 */
	PresentationResultDescriptor<R> getResultDescriptor();

	/**
	 * The result descriptor.
	 * 
	 * @param qualifier
	 *            The qualifier.
	 * @return The result descriptor.
	 */
	ResultDescriptor<R> getResultDescriptor(Class<?> qualifier);
}
