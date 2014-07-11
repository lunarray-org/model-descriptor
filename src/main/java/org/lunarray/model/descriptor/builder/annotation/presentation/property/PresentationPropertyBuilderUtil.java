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
package org.lunarray.model.descriptor.builder.annotation.presentation.property;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.resolver.property.PresentationPropertyAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.util.RenderDefaultsUtil;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.lunarray.model.descriptor.util.BooleanInheritUtil;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Utility for some generic property builder processing.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum PresentationPropertyBuilderUtil {
	/** Instance. */
	INSTANCE;

	/** Default in line value. */
	private static final boolean DEFAULT_IN_LINE = false;
	/** Default name value. */
	private static final boolean DEFAULT_NAME = false;

	/**
	 * Guess the render type.
	 * 
	 * @param <P>
	 *            Property type.
	 * @param <E>
	 *            Entity type.
	 * @param builder
	 *            The builder.
	 * @return The render type.
	 */
	public static <P, E> RenderType guessRenderType(final PresQualPropertyDescriptorBuilder<P, E> builder) {
		final RenderType render;
		if (CheckUtil.isNull(builder.getRelationType())) {
			render = RenderDefaultsUtil.INSTANCE.resolve(builder.getPropertyType(), RenderType.TEXT);
		} else {
			if (Cardinality.MULTIPLE == builder.getCardinality()) {
				render = RenderType.PICKLIST;
			} else {
				render = RenderType.DROPDOWN;
			}
		}
		return render;
	}

	/**
	 * Process hints.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P, E> void process(final PresQualPropertyDescriptorBuilder<P, E> descriptorBuilder,
			final PropertyDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationPropertyAttributeResolverStrategy resolver = ctx.getPresentationPropertyAttributeResolverStrategy();
		final DescribedProperty<P> property = descriptorBuilder.getProperty();

		int order = Integer.MIN_VALUE;
		String label = "";
		String format = "";
		if (!CheckUtil.isNull(qualifier)) {
			order = resolver.order(property, qualifier);
			label = resolver.labelKey(property, qualifier);
			format = resolver.format(property, qualifier);
		}

		if (Integer.MIN_VALUE == order) {
			order = resolver.order(property);
		}
		if (StringUtil.isEmptyString(format)) {
			format = resolver.format(property);
		}
		if (StringUtil.isEmptyString(label)) {
			label = resolver.labelKey(property);
		}
		if (StringUtil.isEmptyString(label)) {
			label = String.format("%s.%s", ctx.getEntityName(descriptorBuilder.getEntityType()), property.getName());
		}
		detailBuilder.format(format).order(order).descriptionKey(label);
		PresentationPropertyBuilderUtil.processFlags(descriptorBuilder, detailBuilder, qualifier);
		PresentationPropertyBuilderUtil.processRenderFlags(descriptorBuilder, detailBuilder, qualifier);
		PresentationPropertyBuilderUtil.processBundle(descriptorBuilder, detailBuilder, qualifier);
		PresentationPropertyBuilderUtil.processRenderType(descriptorBuilder, detailBuilder, qualifier);
	}

	/**
	 * Process bundle.
	 * 
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static void processBundle(final PresQualPropertyDescriptorBuilder<?, ?> descriptorBuilder,
			final PropertyDetailBuilder detailBuilder, final Class<?> qualifier) {
		String bundle;
		if (CheckUtil.isNull(qualifier)) {
			bundle = descriptorBuilder.getBuilderContext().getBundle(descriptorBuilder.getEntityType());
		} else {
			bundle = descriptorBuilder.getBuilderContext().getQualifierBundle(descriptorBuilder.getEntityType(), qualifier);
		}
		detailBuilder.resourceBundle(bundle);
	}

	/**
	 * Process Flags.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P, E> void processFlags(final PresQualPropertyDescriptorBuilder<P, E> descriptorBuilder,
			final PropertyDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresentationPropertyAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationPropertyAttributeResolverStrategy();
		final DescribedProperty<P> property = descriptorBuilder.getProperty();

		BooleanInherit hintRequired = BooleanInherit.INHERIT;
		BooleanInherit hintImmutable = BooleanInherit.INHERIT;
		BooleanInherit hintVisible = BooleanInherit.INHERIT;
		if (!CheckUtil.isNull(qualifier)) {
			hintImmutable = resolver.immutable(property, qualifier);
			hintVisible = resolver.visible(property, qualifier);
			hintRequired = resolver.required(property, qualifier);
		}
		final boolean isPrimitive = Cardinality.SINGLE == descriptorBuilder.getCardinality();
		final boolean immutable = BooleanInheritUtil.resolve(hintImmutable, resolver.immutable(property), descriptorBuilder.isImmutable());
		final boolean visible = BooleanInheritUtil.resolve(hintVisible, resolver.visible(property), descriptorBuilder.getValueReference()
				.isAccessible());
		final boolean required = BooleanInheritUtil.resolve(hintRequired, resolver.required(property), isPrimitive && !immutable);
		if (CheckUtil.isNull(qualifier)) {
			if (immutable) {
				descriptorBuilder.immutable();
			} else {
				descriptorBuilder.mutable();
			}
		} else if (immutable) {
			detailBuilder.immutable();
		}
		if (required) {
			detailBuilder.required();
		}
		if (!visible) {
			detailBuilder.invisible();
		}
	}

	/**
	 * Process Flags.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P, E> void processRenderFlags(final PresQualPropertyDescriptorBuilder<P, E> descriptorBuilder,
			final PropertyDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresentationPropertyAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationPropertyAttributeResolverStrategy();
		final DescribedProperty<P> property = descriptorBuilder.getProperty();

		BooleanInherit hintInLine = BooleanInherit.INHERIT;
		BooleanInherit hintName = BooleanInherit.INHERIT;
		if (!CheckUtil.isNull(qualifier)) {
			hintInLine = resolver.inLine(property, qualifier);
			hintName = resolver.name(property, qualifier);
		}
		if (BooleanInheritUtil.resolve(hintInLine, resolver.inLine(property), PresentationPropertyBuilderUtil.DEFAULT_IN_LINE)) {
			detailBuilder.inLine();
		}
		if (BooleanInheritUtil.resolve(hintName, resolver.name(property), PresentationPropertyBuilderUtil.DEFAULT_NAME)) {
			detailBuilder.name();
		}
	}

	/**
	 * Process render type.
	 * 
	 * @param <P>
	 *            The property type.
	 * @param <E>
	 *            The entity type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P, E> void processRenderType(final PresQualPropertyDescriptorBuilder<P, E> descriptorBuilder,
			final PropertyDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresentationPropertyAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationPropertyAttributeResolverStrategy();
		final DescribedProperty<P> property = descriptorBuilder.getProperty();

		RenderType renderType = RenderType.DEFAULT;
		if (!CheckUtil.isNull(qualifier)) {
			renderType = resolver.render(property, qualifier);
		}
		if (RenderType.DEFAULT == renderType) {
			renderType = resolver.render(property);
		}
		if (RenderType.DEFAULT == renderType) {
			renderType = PresentationPropertyBuilderUtil.guessRenderType(descriptorBuilder);
		}
		detailBuilder.renderType(renderType);
	}
}
