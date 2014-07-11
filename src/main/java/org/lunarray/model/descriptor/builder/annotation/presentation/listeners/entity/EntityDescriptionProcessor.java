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
package org.lunarray.model.descriptor.builder.annotation.presentation.listeners.entity;

import java.io.Serializable;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.common.event.EventException;
import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.EntityDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.PresQualEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.listeners.events.UpdatedPresentationEntityTypeEvent;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.PresentationEntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public final class EntityDescriptionProcessor<E, K extends Serializable>
		implements Listener<UpdatedPresentationEntityTypeEvent<E, K>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityDescriptionProcessor.class);
	/** Descriptor. */
	private transient PresQualEntityDescriptorBuilder<E, K> descriptor;
	/** Entity type. */
	private transient DescribedEntity<?> type;

	/** Default constructor. */
	public EntityDescriptionProcessor() {
		this.descriptor = null;
		this.type = null;
	}

	/** {@inheritDoc} */
	@Override
	public void handleEvent(final UpdatedPresentationEntityTypeEvent<E, K> event) throws EventException {
		EntityDescriptionProcessor.LOGGER.debug("Handling event {}", event);
		Validate.notNull(event, "Event may not be null.");
		this.descriptor = event.getPresentationBuilder();
		this.type = event.getEntity();
		this.process();
	}

	/**
	 * Process.
	 */
	private void process() {
		final PresentationEntityAttributeResolverStrategy resolver = this.descriptor.getBuilderContext()
				.getPresentationEntityAttributeResolverStrategy();
		this.process(this.descriptor.getDetailBuilder(), null);
		for (final Class<?> qualifier : resolver.qualifiers(this.type)) {
			this.process(this.descriptor.getQualifierDetails().get(qualifier), qualifier);
		}
	}

	/**
	 * Process hints.
	 * 
	 * @param builder
	 *            The builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	private void process(final EntityDetailBuilder builder, final Class<?> qualifier) {
		final PresentationEntityAttributeResolverStrategy resolver = this.descriptor.getBuilderContext()
				.getPresentationEntityAttributeResolverStrategy();
		String key = "";
		if (!CheckUtil.isNull(qualifier)) {
			key = resolver.labelKey(this.type, qualifier);
		}
		if (StringUtil.isEmptyString(key)) {
			key = resolver.labelKey(this.type);
		}
		if (StringUtil.isEmptyString(key)) {
			key = this.descriptor.getBuilderContext().getEntityName(this.type);
		}
		builder.descriptionKey(key);
	}
}
