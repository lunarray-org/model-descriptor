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

import java.io.Serializable;

import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.AbstractEntityTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.PresQualEntityDescriptorBuilder;

/**
 * An event propagated when an entity type is updated.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <K>
 *            The key type.
 * @param <E>
 *            The entity type.
 */
public final class UpdatedPresentationEntityTypeEvent<E, K extends Serializable>
		extends AbstractEntityTypeEvent<E, K, PresQualBuilderContext> {

	/** The presentation builder. */
	private final transient PresQualEntityDescriptorBuilder<E, K> presentationBuilder;

	/**
	 * Default constructor.
	 * 
	 * @param builder
	 *            The builder.
	 * @param entity
	 *            The entity.
	 */
	public UpdatedPresentationEntityTypeEvent(final PresQualEntityDescriptorBuilder<E, K> builder, final DescribedEntity<E> entity) {
		super(builder, entity);
		this.presentationBuilder = builder;
	}

	/**
	 * Gets the value for the presentationBuilder field.
	 * 
	 * @return The value for the presentationBuilder field.
	 */
	public PresQualEntityDescriptorBuilder<E, K> getPresentationBuilder() {
		return this.presentationBuilder;
	}
}
