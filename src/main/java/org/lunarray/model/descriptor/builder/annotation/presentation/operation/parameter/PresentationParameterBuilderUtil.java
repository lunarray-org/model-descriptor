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
package org.lunarray.model.descriptor.builder.annotation.presentation.operation.parameter;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.operation.parameter.DescribedParameter;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.resolver.parameter.PresentationParameterAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.util.RenderDefaultsUtil;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.lunarray.model.descriptor.util.BooleanInheritUtil;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Utility for some generic parameter builder processing.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum PresentationParameterBuilderUtil {
	/** Instance. */
	INSTANCE;

	/** Default in line value. */
	private static final boolean DEFAULT_IN_LINE = false;

	/**
	 * Guess the render type.
	 * 
	 * @param <P>
	 *            Parameter type.
	 * @param builder
	 *            The builder.
	 * @return The render type.
	 */
	public static <P> RenderType guessRenderType(final PresQualParameterDescriptorBuilder<P> builder) {
		final RenderType render;
		if (CheckUtil.isNull(builder.getRelationType())) {
			render = RenderDefaultsUtil.INSTANCE.resolve(builder.getParameterType(), RenderType.TEXT);
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
	 *            The parameter type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P> void process(final PresQualParameterDescriptorBuilder<P> descriptorBuilder,
			final ParameterDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationParameterAttributeResolverStrategy resolver = ctx.getPresentationParameterAttributeResolverStrategy();
		final DescribedParameter<P> parameter = descriptorBuilder.getParameter();

		int order = Integer.MIN_VALUE;
		String label = "";
		String format = "";
		if (!CheckUtil.isNull(qualifier)) {
			order = resolver.order(parameter, qualifier);
			label = resolver.labelKey(parameter, qualifier);
			format = resolver.format(parameter, qualifier);
		}

		if (Integer.MIN_VALUE == order) {
			order = resolver.order(parameter);
		}
		if (StringUtil.isEmptyString(format)) {
			format = resolver.format(parameter);
		}
		if (StringUtil.isEmptyString(label)) {
			label = resolver.labelKey(parameter);
		}
		detailBuilder.format(format).order(order).descriptionKey(label);
		PresentationParameterBuilderUtil.processFlags(descriptorBuilder, detailBuilder, qualifier);
		PresentationParameterBuilderUtil.processRenderFlags(descriptorBuilder, detailBuilder, qualifier);
		PresentationParameterBuilderUtil.processBundle(descriptorBuilder, detailBuilder, qualifier);
		PresentationParameterBuilderUtil.processRenderType(descriptorBuilder, detailBuilder, qualifier);
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
	public static void processBundle(final PresQualParameterDescriptorBuilder<?> descriptorBuilder,
			final ParameterDetailBuilder detailBuilder, final Class<?> qualifier) {
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
	 *            The parameter type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P> void processFlags(final PresQualParameterDescriptorBuilder<P> descriptorBuilder,
			final ParameterDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresentationParameterAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationParameterAttributeResolverStrategy();
		final DescribedParameter<P> parameter = descriptorBuilder.getParameter();

		BooleanInherit hintRequired = BooleanInherit.INHERIT;
		if (!CheckUtil.isNull(qualifier)) {
			hintRequired = resolver.required(parameter, qualifier);
		}
		final boolean isPrimitive = Cardinality.SINGLE == descriptorBuilder.getCardinality();
		final boolean required = BooleanInheritUtil.resolve(hintRequired, resolver.required(parameter), isPrimitive);
		if (required) {
			detailBuilder.required();
		}
	}

	/**
	 * Process Flags.
	 * 
	 * @param <P>
	 *            The parameter type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P> void processRenderFlags(final PresQualParameterDescriptorBuilder<P> descriptorBuilder,
			final ParameterDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresentationParameterAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationParameterAttributeResolverStrategy();
		final DescribedParameter<P> parameter = descriptorBuilder.getParameter();

		BooleanInherit hintInLine = BooleanInherit.INHERIT;
		if (!CheckUtil.isNull(qualifier)) {
			hintInLine = resolver.inLine(parameter, qualifier);
		}
		if (BooleanInheritUtil.resolve(hintInLine, resolver.inLine(parameter), PresentationParameterBuilderUtil.DEFAULT_IN_LINE)) {
			detailBuilder.inLine();
		}
	}

	/**
	 * Process render type.
	 * 
	 * @param <P>
	 *            The parameter type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <P> void processRenderType(final PresQualParameterDescriptorBuilder<P> descriptorBuilder,
			final ParameterDetailBuilder detailBuilder, final Class<?> qualifier) {
		final PresentationParameterAttributeResolverStrategy resolver = descriptorBuilder.getBuilderContext()
				.getPresentationParameterAttributeResolverStrategy();
		final DescribedParameter<P> parameter = descriptorBuilder.getParameter();

		RenderType renderType = RenderType.DEFAULT;
		if (!CheckUtil.isNull(qualifier)) {
			renderType = resolver.render(parameter, qualifier);
		}
		if (RenderType.DEFAULT == renderType) {
			renderType = resolver.render(parameter);
		}
		if (RenderType.DEFAULT == renderType) {
			renderType = PresentationParameterBuilderUtil.guessRenderType(descriptorBuilder);
		}
		detailBuilder.renderType(renderType);
	}
}
