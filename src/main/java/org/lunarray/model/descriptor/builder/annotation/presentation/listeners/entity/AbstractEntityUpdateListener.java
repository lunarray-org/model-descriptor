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

import java.util.Set;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.EntityDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.entity.PresQualEntityDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.entity.PresentationEntityAttributeResolverStrategy;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.lunarray.model.descriptor.util.BooleanInheritUtil;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Entity processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public abstract class AbstractEntityUpdateListener<E> {

	/** Default visibility. */
	private static final boolean DEFAULT_VISIBILITY = true;
	/** Descriptor. */
	private transient PresQualEntityDescriptorBuilder<E, ?> descriptor;

	/** Default constructor. */
	protected AbstractEntityUpdateListener() {
		this.descriptor = null;
	}

	/**
	 * Sets a new value for the descriptor field.
	 * 
	 * @param descriptor
	 *            The new value for the descriptor field.
	 */
	public final void setDescriptor(final PresQualEntityDescriptorBuilder<E, ?> descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * Process hints.
	 * 
	 * @param builder
	 *            The builder.
	 * @param qualifier
	 *            The qualifier
	 * @return The resource bundle.
	 */
	private String process(final EntityDetailBuilder builder, final Class<?> qualifier) {
		final DescribedEntity<E> type = this.descriptor.getEntityType();
		final PresentationEntityAttributeResolverStrategy resolver = this.descriptor.getBuilderContext()
				.getPresentationEntityAttributeResolverStrategy();
		BooleanInherit visibleEntity = BooleanInherit.INHERIT;
		if (!CheckUtil.isNull(qualifier)) {
			visibleEntity = resolver.visible(type, qualifier);
		}
		final boolean visible = BooleanInheritUtil.resolve(visibleEntity, resolver.visible(type),
				AbstractEntityUpdateListener.DEFAULT_VISIBILITY);
		if (visible) {
			builder.visible();
		} else {
			builder.invisible();
		}
		String bundle = "";
		if (!CheckUtil.isNull(qualifier)) {
			bundle = resolver.resourceBundle(type, qualifier);
		}
		if (StringUtil.isEmptyString(bundle)) {
			bundle = resolver.resourceBundle(type);
		}
		if (!StringUtil.isEmptyString(bundle)) {
			builder.resourceBundle(bundle);
		}
		return bundle;
	}

	/**
	 * Process.
	 */
	protected final void process() {
		final DescribedEntity<E> type = this.descriptor.getEntityType();
		final PresQualBuilderContext ctx = this.descriptor.getBuilderContext();
		final PresentationEntityAttributeResolverStrategy resolver = ctx.getPresentationEntityAttributeResolverStrategy();
		ctx.putBundle(type, this.process(this.descriptor.getDetailBuilder(), null));
		for (final Class<?> qualifier : resolver.qualifiers(type)) {
			ctx.putQualifier(type, qualifier);
			final EntityDetailBuilder qualifierDetail = this.descriptor.getDetailBuilder(qualifier);
			ctx.putBundle(type, qualifier, this.process(qualifierDetail, qualifier));
		}
	}

	/** Process qualifiers. */
	protected final void processNewQualifiers() {
		final DescribedEntity<E> type = this.descriptor.getEntityType();
		final Set<Class<?>> qualifiers = this.descriptor.getBuilderContext().getQualifiers(type);
		qualifiers.removeAll(this.descriptor.getQualifierDetails().keySet());
		final PresQualBuilderContext ctx = this.descriptor.getBuilderContext();
		for (final Class<?> qualifier : qualifiers) {
			ctx.putQualifier(type, qualifier);
			final EntityDetailBuilder qualifierDetail = this.descriptor.getDetailBuilder(qualifier);
			ctx.putBundle(type, qualifier, this.process(qualifierDetail, qualifier));
		}
	}
}
