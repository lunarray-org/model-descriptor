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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events;

import java.util.Collection;

import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.AbstractUpdatedParameterCollectionTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter.PresQualCollectionParameterDescriptorBuilder;

/**
 * An event propagated when a parameter entity type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 * @param <C>
 *            The collection type.
 */
public final class UpdatedPresentationParameterCollectionTypeEvent<C, P extends Collection<C>>
		extends AbstractUpdatedParameterCollectionTypeEvent<C, P, PresQualBuilderContext> {

	/** The builder. */
	private final transient PresQualCollectionParameterDescriptorBuilder<C, P> presentationBuilder;

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param parameter
	 *            The parameter.
	 */
	public UpdatedPresentationParameterCollectionTypeEvent(final PresQualCollectionParameterDescriptorBuilder<C, P> builder,
			final DescribedParameter<P> parameter) {
		super(builder, parameter);
		this.presentationBuilder = builder;
	}

	/**
	 * Gets the value for the presentationBuilder field.
	 * 
	 * @return The value for the presentationBuilder field.
	 */
	public PresQualCollectionParameterDescriptorBuilder<C, P> getPresentationBuilder() {
		return this.presentationBuilder;
	}
}
