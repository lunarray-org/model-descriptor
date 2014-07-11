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
package org.lunarray.model.descriptor.operation;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.CollectionParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.presentation.RelationPresentationDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationParameterDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.Qualifier01;
import org.lunarray.model.descriptor.test.domain.Qualifier02;
import org.lunarray.model.descriptor.test.domain.SampleEntity25;

public class PresentationParameterTest {

	private Model<Object> model;

	@Before
	public void init() throws Exception {
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>();
		resource.addClazz(SampleEntity25.class);
		this.model = PresQualBuilder.createBuilder().resources(resource).build();
	}

	@Test
	public void testParameters() {
		final EntityDescriptor<SampleEntity25> entity = this.model.getEntity(SampleEntity25.class);
		final OperationDescriptor<SampleEntity25> operation1 = entity.getOperation("someMethod");
		Assert.assertEquals(2, operation1.getParameterCount());
		Assert.assertEquals(Integer.TYPE, operation1.getParameter(0).getType());
		Assert.assertEquals(Object.class, operation1.getParameter(1).getType());
		final OperationDescriptor<SampleEntity25> operation2 = entity.getOperation("someMethod2");
		Assert.assertEquals(1, operation2.getParameterCount());
		Assert.assertEquals(SampleEntity25.class, operation2.getParameter(0).getType());
		final RelationDescriptor related1 = operation2.getParameter(0).adapt(RelationDescriptor.class);
		Assert.assertEquals(RelationType.CONCRETE, related1.getRelationType());
		Assert.assertEquals("SampleEntity25", related1.getRelatedName());
		final OperationDescriptor<SampleEntity25> operation3 = entity.getOperation("someMethod3");
		Assert.assertEquals(3, operation3.getParameterCount());
		final OperationDescriptor<SampleEntity25> operation4 = entity.getOperation("someMethod4");
		Assert.assertEquals(2, operation4.getParameterCount());
		Assert.assertEquals(List.class, operation4.getParameter(0).getType());
		final CollectionParameterDescriptor<?, ?> collection0 = operation4.getParameter(0).adapt(CollectionParameterDescriptor.class);
		Assert.assertEquals(Object.class, collection0.getCollectionType());
		Assert.assertEquals(List.class, operation4.getParameter(1).getType());
		final CollectionParameterDescriptor<?, ?> collection1 = operation4.getParameter(1).adapt(CollectionParameterDescriptor.class);
		Assert.assertEquals(SampleEntity25.class, collection1.getCollectionType());
		final RelationDescriptor related2 = collection1.adapt(RelationDescriptor.class);
		Assert.assertEquals(RelationType.CONCRETE, related2.getRelationType());
		Assert.assertEquals("SampleEntity25", related2.getRelatedName());

		@SuppressWarnings("unchecked")
		final EntityDescriptor<SampleEntity25> qualifierEntity = entity.adapt(QualifierEntityDescriptor.class).getQualifierEntity(
				Qualifier02.class);
		final ParameterDescriptor<?> parameter1 = qualifierEntity.getOperation("someMethod").getParameter(0);
		final PresentationParameterDescriptor<?> parameter2 = parameter1.adapt(PresentationParameterDescriptor.class);
		Assert.assertEquals("%%", parameter2.getFormat());
		Assert.assertEquals(RenderType.CHECKBOX, parameter2.getRenderType());
	}

	@Test
	public void testParametersPresentation() {
		this.assertParameter("someMethod", 0, "SampleEntity25.someMethod.0", "SampleEntity25.someMethod.0", "SampleEntity25.someMethod.0",
				"", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter("someMethod", 1, "SampleEntity25.someMethod.1", "SampleEntity25.someMethod.1", "SampleEntity25.someMethod.1",
				"", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter("someMethod2", 0, "SampleEntity25.someMethod2.0", "SampleEntity25.someMethod2.0",
				"SampleEntity25.someMethod2.0", "", Integer.MIN_VALUE, RenderType.DROPDOWN);
		this.assertRelatedParameter("someMethod2", 0, "SampleEntity25", RelationType.CONCRETE, false);
		this.assertParameter("someMethod3", 0, "SampleEntity25.someMethod3.0", "SampleEntity25.someMethod3.0",
				"SampleEntity25.someMethod3.0", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter("someMethod3", 1, "SampleEntity25.someMethod3.1", "SampleEntity25.someMethod3.1",
				"SampleEntity25.someMethod3.1", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter("someMethod3", 2, "SampleEntity25.someMethod3.2", "SampleEntity25.someMethod3.2",
				"SampleEntity25.someMethod3.2", "", Integer.MIN_VALUE, RenderType.DROPDOWN);
		this.assertParameter("someMethod4", 0, "SampleEntity25.someMethod4.0", "SampleEntity25.someMethod4.0",
				"SampleEntity25.someMethod4.0", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter("someMethod4", 1, "SampleEntity25.someMethod4.1", "SampleEntity25.someMethod4.1",
				"SampleEntity25.someMethod4.1", "", Integer.MIN_VALUE, RenderType.PICKLIST);
		this.assertParameter(Qualifier01.class, "someMethod", 0, "SampleEntity25.someMethod.0", "SampleEntity25.someMethod.0",
				"SampleEntity25.someMethod.0", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier01.class, "someMethod", 1, "SampleEntity25.someMethod.1", "SampleEntity25.someMethod.1",
				"SampleEntity25.someMethod.1", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier01.class, "someMethod2", 0, "SampleEntity25.someMethod2.0", "SampleEntity25.someMethod2.0",
				"SampleEntity25.someMethod2.0", "", Integer.MIN_VALUE, RenderType.DROPDOWN);
		this.assertRelatedParameter(Qualifier01.class, "someMethod2", 0, "SampleEntity25", RelationType.CONCRETE, false);
		this.assertParameter(Qualifier01.class, "someMethod3", 0, "SampleEntity25.someMethod3.0", "SampleEntity25.someMethod3.0",
				"SampleEntity25.someMethod3.0", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier01.class, "someMethod3", 1, "SampleEntity25.someMethod3.1", "SampleEntity25.someMethod3.1",
				"SampleEntity25.someMethod3.1", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier01.class, "someMethod3", 2, "SampleEntity25.someMethod3.2", "SampleEntity25.someMethod3.2",
				"SampleEntity25.someMethod3.2", "", Integer.MIN_VALUE, RenderType.DROPDOWN);
		this.assertParameter(Qualifier01.class, "someMethod4", 0, "SampleEntity25.someMethod4.0", "SampleEntity25.someMethod4.0",
				"SampleEntity25.someMethod4.0", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier01.class, "someMethod4", 1, "SampleEntity25.someMethod4.1", "SampleEntity25.someMethod4.1",
				"SampleEntity25.someMethod4.1", "", Integer.MIN_VALUE, RenderType.PICKLIST);
		this.assertParameter(Qualifier02.class, "someMethod", 0, "SampleEntity25.someMethod.0", "SampleEntity25.someMethod.0",
				"SampleEntity25.someMethod.0", "%%", Integer.MAX_VALUE, RenderType.CHECKBOX);
		this.assertParameter(Qualifier02.class, "someMethod", 1, "SampleEntity25.someMethod.1", "SampleEntity25.someMethod.1",
				"SampleEntity25.someMethod.1", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier02.class, "someMethod2", 0, "SampleEntity25.someMethod2.0", "SampleEntity25.someMethod2.0",
				"SampleEntity25.someMethod2.0", "", Integer.MIN_VALUE, RenderType.DROPDOWN);
		this.assertParameter(Qualifier02.class, "someMethod3", 0, "SampleEntity25.someMethod3.0", "SampleEntity25.someMethod3.0",
				"SampleEntity25.someMethod3.0", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier02.class, "someMethod3", 1, "SampleEntity25.someMethod3.1", "SampleEntity25.someMethod3.1",
				"SampleEntity25.someMethod3.1", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier02.class, "someMethod3", 2, "SampleEntity25.someMethod3.2", "SampleEntity25.someMethod3.2",
				"SampleEntity25.someMethod3.2", "", Integer.MIN_VALUE, RenderType.DROPDOWN);
		this.assertParameter(Qualifier02.class, "someMethod4", 0, "SampleEntity25.someMethod4.0", "SampleEntity25.someMethod4.0",
				"SampleEntity25.someMethod4.0", "", Integer.MIN_VALUE, RenderType.TEXT);
		this.assertParameter(Qualifier02.class, "someMethod4", 1, "SampleEntity25.someMethod4.1", "SampleEntity25.someMethod4.1",
				"SampleEntity25.someMethod4.1", "", Integer.MIN_VALUE, RenderType.PICKLIST);
	}

	private void assertRelatedParameter(final Class<?> qualifier, final String operation, final int parameter, final String name,
			final RelationType type, final boolean inline) {
		final EntityDescriptor<SampleEntity25> entity = this.model.getEntity(SampleEntity25.class);
		@SuppressWarnings("unchecked")
		final QualifierEntityDescriptor<SampleEntity25> qEntity = entity.adapt(QualifierEntityDescriptor.class);
		final RelationPresentationDescriptor p = qEntity.getQualifierOperation(operation, qualifier).getParameter(parameter)
				.adapt(RelationPresentationDescriptor.class);
		Assert.assertEquals(name, p.getRelatedName());
		Assert.assertEquals(type, p.getRelationType());
		Assert.assertEquals(inline, p.isInLineIndication());
	}

	private void assertRelatedParameter(final String operation, final int parameter, final String name, final RelationType type,
			final boolean inline) {
		final EntityDescriptor<SampleEntity25> entity = this.model.getEntity(SampleEntity25.class);
		final RelationPresentationDescriptor p = entity.getOperation(operation).getParameter(parameter).adapt(RelationPresentationDescriptor.class);
		Assert.assertEquals(name, p.getRelatedName());
		Assert.assertEquals(type, p.getRelationType());
		Assert.assertEquals(inline, p.isInLineIndication());
	}

	private void assertParameter(final Class<?> qualifier, final String operation, final int parameter, final String description,
			final String descriptionLocale, final String key, final String format, final int order, final RenderType render) {
		final EntityDescriptor<SampleEntity25> entity = this.model.getEntity(SampleEntity25.class);
		@SuppressWarnings("unchecked")
		final QualifierEntityDescriptor<SampleEntity25> qEntity = entity.adapt(QualifierEntityDescriptor.class);
		final PresentationParameterDescriptor<?> p = qEntity.getQualifierOperation(operation, qualifier).getParameter(parameter)
				.adapt(PresentationParameterDescriptor.class);
		Assert.assertEquals(description, p.getDescription());
		Assert.assertEquals(descriptionLocale, p.getDescription(Locale.GERMAN));
		Assert.assertEquals(key, p.getDescriptionKey());
		Assert.assertEquals(format, p.getFormat());
		Assert.assertEquals(order, p.getOrder());
		Assert.assertEquals(render, p.getRenderType());
	}

	private void assertParameter(final String operation, final int parameter, final String description, final String descriptionLocale,
			final String key, final String format, final int order, final RenderType render) {
		final EntityDescriptor<SampleEntity25> entity = this.model.getEntity(SampleEntity25.class);
		final PresentationParameterDescriptor<?> p = entity.getOperation(operation).getParameter(parameter)
				.adapt(PresentationParameterDescriptor.class);
		Assert.assertEquals(description, p.getDescription());
		Assert.assertEquals(descriptionLocale, p.getDescription(Locale.GERMAN));
		Assert.assertEquals(key, p.getDescriptionKey());
		Assert.assertEquals(format, p.getFormat());
		Assert.assertEquals(order, p.getOrder());
		Assert.assertEquals(render, p.getRenderType());
	}
}
