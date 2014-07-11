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
package org.lunarray.model.descriptor.builder.annotation.presentation.operation;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.builder.annotation.presentation.PresQualBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.PresQualResultTypeDescriptorBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.operation.result.ResultDetailBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.operation.PresentationOperationAttributeResolverStrategy;
import org.lunarray.model.descriptor.builder.annotation.util.RenderDefaultsUtil;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.BooleanInherit;
import org.lunarray.model.descriptor.util.BooleanInheritUtil;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Utility for some generic operation builder processing.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum PresentationOperationBuilderUtil {
	/** Instance. */
	INSTANCE;

	/** Default in line value. */
	private static final boolean DEFAULT_IN_LINE = false;
	/** Default visibility. */
	private static final boolean DEFAULT_VISIBLE = true;

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
	public static <P, E> RenderType guessRenderType(final PresQualResultTypeDescriptorBuilder<P, E> builder) {
		final RenderType render;
		if (CheckUtil.isNull(builder.getRelationType())) {
			render = RenderDefaultsUtil.INSTANCE.resolve(builder.getResultType(), RenderType.TEXT);
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
	 * @param <E>
	 *            The entity type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param resultTypeDetailBuilder
	 *            The result type builder.
	 * @param qualifier
	 *            The qualifier.
	 */
	public static <E> void process(final PresQualOperationDescriptorBuilder<E> descriptorBuilder, final OperationDetailBuilder detailBuilder,
			final ResultDetailBuilder resultTypeDetailBuilder, final Class<?> qualifier) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationOperationAttributeResolverStrategy resolver = ctx.getPresentationOperationAttributeResolverStrategy();
		final DescribedOperation operation = descriptorBuilder.getOperation();

		int order = Integer.MIN_VALUE;
		String label = "";
		String format = "";
		String buttonKey = "";
		if (!CheckUtil.isNull(qualifier)) {
			order = resolver.order(operation, qualifier);
			label = resolver.labelKey(operation, qualifier);
			format = resolver.format(operation, qualifier);
			buttonKey = resolver.buttonKey(operation, qualifier);
		}

		if (Integer.MIN_VALUE == order) {
			order = resolver.order(operation);
		}
		if (StringUtil.isEmptyString(format)) {
			format = resolver.format(operation);
		}
		if (StringUtil.isEmptyString(buttonKey)) {
			buttonKey = resolver.buttonKey(operation);
		}
		if (StringUtil.isEmptyString(label)) {
			label = resolver.labelKey(operation);
		}
		if (StringUtil.isEmptyString(label)) {
			label = String.format("%s.%s", ctx.getEntityName(descriptorBuilder.getEntityType()), operation.getName());
		}
		if (StringUtil.isEmptyString(buttonKey)) {
			buttonKey = label;
		}
		detailBuilder.buttonKey(buttonKey).order(order).descriptionKey(label);
		resultTypeDetailBuilder.format(format);
		PresentationOperationBuilderUtil.processFlags(descriptorBuilder, detailBuilder, resultTypeDetailBuilder, qualifier);
		PresentationOperationBuilderUtil.processRenderFlags(descriptorBuilder, detailBuilder, resultTypeDetailBuilder, qualifier);
		PresentationOperationBuilderUtil.processBundle(descriptorBuilder, detailBuilder, resultTypeDetailBuilder, qualifier);
		PresentationOperationBuilderUtil.processRenderType(descriptorBuilder, detailBuilder, resultTypeDetailBuilder, qualifier);
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
	 * @param resultTypeDetailBuilder
	 *            The result type builder.
	 */
	public static void processBundle(final PresQualOperationDescriptorBuilder<?> descriptorBuilder, final OperationDetailBuilder detailBuilder,
			final ResultDetailBuilder resultTypeDetailBuilder, final Class<?> qualifier) {
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
	 * @param <E>
	 *            The entity type.
	 * @param descriptorBuilder
	 *            The descriptor builder.
	 * @param detailBuilder
	 *            The detail builder.
	 * @param qualifier
	 *            The qualifier.
	 * @param resultTypeDetailBuilder
	 *            The result type builder.
	 */
	public static <E> void processFlags(final PresQualOperationDescriptorBuilder<E> descriptorBuilder,
			final OperationDetailBuilder detailBuilder, final ResultDetailBuilder resultTypeDetailBuilder, final Class<?> qualifier) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationOperationAttributeResolverStrategy resolver = ctx.getPresentationOperationAttributeResolverStrategy();
		final DescribedOperation operation = descriptorBuilder.getOperation();

		BooleanInherit hintVisible = BooleanInherit.INHERIT;
		if (!CheckUtil.isNull(qualifier)) {
			hintVisible = resolver.visible(operation, qualifier);
		}
		final boolean visible = BooleanInheritUtil.resolve(hintVisible, resolver.visible(operation),
				PresentationOperationBuilderUtil.DEFAULT_VISIBLE);
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
	 * @param resultTypeDetailBuilder
	 *            The result type builder.
	 */
	public static <P, E> void processRenderFlags(final PresQualOperationDescriptorBuilder<E> descriptorBuilder,
			final OperationDetailBuilder detailBuilder, final ResultDetailBuilder resultTypeDetailBuilder, final Class<?> qualifier) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationOperationAttributeResolverStrategy resolver = ctx.getPresentationOperationAttributeResolverStrategy();
		final DescribedOperation operation = descriptorBuilder.getOperation();

		BooleanInherit hintInLine = BooleanInherit.INHERIT;
		if (!CheckUtil.isNull(qualifier)) {
			hintInLine = resolver.inLine(operation, qualifier);
		}
		if (BooleanInheritUtil.resolve(hintInLine, resolver.inLine(operation), PresentationOperationBuilderUtil.DEFAULT_IN_LINE)) {
			resultTypeDetailBuilder.inLine();
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
	 * @param resultTypeDetailBuilder
	 *            The result type builder.
	 */
	public static <P, E> void processRenderType(final PresQualOperationDescriptorBuilder<E> descriptorBuilder,
			final OperationDetailBuilder detailBuilder, final ResultDetailBuilder resultTypeDetailBuilder, final Class<?> qualifier) {
		final PresQualBuilderContext ctx = descriptorBuilder.getBuilderContext();
		final PresentationOperationAttributeResolverStrategy resolver = ctx.getPresentationOperationAttributeResolverStrategy();
		final DescribedOperation operation = descriptorBuilder.getOperation();

		RenderType renderType = RenderType.DEFAULT;
		if (!CheckUtil.isNull(qualifier)) {
			renderType = resolver.render(operation, qualifier);
		}
		if (RenderType.DEFAULT == renderType) {
			renderType = resolver.render(operation);
		}
		if (RenderType.DEFAULT == renderType) {
			renderType = PresentationOperationBuilderUtil.guessRenderType(descriptorBuilder.getPresentationResultBuilder());
		}
		resultTypeDetailBuilder.renderType(renderType);
	}

}
