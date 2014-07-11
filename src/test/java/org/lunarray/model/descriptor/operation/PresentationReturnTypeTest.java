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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.presentation.PresentationResultDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.Qualifier01;
import org.lunarray.model.descriptor.test.domain.Qualifier02;
import org.lunarray.model.descriptor.test.domain.SampleEntity25;
import org.lunarray.model.descriptor.test.domain.SampleEntity26;

public class PresentationReturnTypeTest {

	private Model<Object> model;

	@Before
	public void init() throws Exception {
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>();
		resource.addClazz(SampleEntity25.class);
		resource.addClazz(SampleEntity26.class);
		this.model = PresQualBuilder.createBuilder().resources(resource).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOperation() {
		EntityDescriptor<SampleEntity26> descriptor = this.model.getEntity(SampleEntity26.class);
		descriptor = descriptor.adapt(QualifierEntityDescriptor.class).getQualifierEntity(Qualifier01.class);
		final OperationDescriptor<?> c1 = descriptor.getOperation("someMethod");
		this.assertDescriptor(c1.getResultDescriptor(), SampleEntity25.class, Cardinality.NULLABLE, true);
		this.assertDescriptorRelated(c1.getResultDescriptor().adapt(RelationDescriptor.class), RelationType.CONCRETE, "SampleEntity25");
		final OperationDescriptor<?> c2 = descriptor.getOperation("someMethod2");
		this.assertDescriptor(c2.getResultDescriptor(), Integer.TYPE, Cardinality.SINGLE, false);
		final OperationDescriptor<?> c3 = descriptor.getOperation("someMethod3");
		this.assertDescriptor(c3.getResultDescriptor(), SampleEntity25.class, Cardinality.NULLABLE, true);
		this.assertDescriptorRelated(c3.getResultDescriptor().adapt(RelationDescriptor.class), RelationType.CONCRETE, "SampleEntity25");
		final OperationDescriptor<?> c4 = descriptor.getOperation("someMethod4");
		this.assertDescriptor(c4.getResultDescriptor(), Void.TYPE, Cardinality.NONE, false);
		final OperationDescriptor<?> c5 = descriptor.getOperation("someMethod5");
		this.assertDescriptor(c5.getResultDescriptor(), List.class, Cardinality.MULTIPLE, true);
		this.assertDescriptorRelated(c5.getResultDescriptor().adapt(RelationDescriptor.class), RelationType.CONCRETE, "SampleEntity25");
		final OperationDescriptor<?> c6 = descriptor.getOperation("someMethod6");
		this.assertDescriptor(c6.getResultDescriptor(), Integer.class, Cardinality.NULLABLE, false);
		final OperationDescriptor<?> c7 = descriptor.getOperation("someMethod7");
		this.assertDescriptor(c7.getResultDescriptor(), List.class, Cardinality.MULTIPLE, true);
		this.assertDescriptorRelated(c7.getResultDescriptor().adapt(RelationDescriptor.class), RelationType.CONCRETE, "SampleEntity25");
		final OperationDescriptor<?> c8 = descriptor.getOperation("someMethod8");
		this.assertDescriptor(c8.getResultDescriptor(), Void.TYPE, Cardinality.NONE, false);
	}

	@Test
	public void testReturn() {
		this.assertReturn("someMethod", "", RenderType.TEXT);
		this.assertReturn("someMethod", "", RenderType.TEXT);
		this.assertReturn("someMethod2", "", RenderType.TEXT);
		this.assertReturn("someMethod3", "", RenderType.TEXT);
		this.assertReturn("someMethod3", "", RenderType.TEXT);
		this.assertReturn("someMethod3", "", RenderType.TEXT);
		this.assertReturn("someMethod4", "", RenderType.TEXT);
		this.assertReturn("someMethod4", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod2", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod3", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod3", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod3", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod4", "", RenderType.TEXT);
		this.assertReturn(Qualifier01.class, "someMethod4", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod2", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod3", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod3", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod3", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod4", "", RenderType.TEXT);
		this.assertReturn(Qualifier02.class, "someMethod4", "", RenderType.TEXT);
	}

	private void assertDescriptor(final ResultDescriptor<?> descriptor, final Class<?> type, final Cardinality cardinality,
			final boolean related) {
		Assert.assertNotNull(descriptor);
		Assert.assertEquals(type, descriptor.getResultType());
		Assert.assertEquals(cardinality, descriptor.getCardinality());
		Assert.assertEquals(related, descriptor.isRelation());
	}

	private void assertDescriptorRelated(final RelationDescriptor descriptor, final RelationType type, final String name) {
		Assert.assertNotNull(descriptor);
		Assert.assertEquals(type, descriptor.getRelationType());
		Assert.assertEquals(name, descriptor.getRelatedName());
	}

	private void assertReturn(final Class<?> qualifier, final String operation, final String format, final RenderType render) {
		final EntityDescriptor<SampleEntity25> entity = this.model.getEntity(SampleEntity25.class);
		@SuppressWarnings("unchecked")
		final QualifierEntityDescriptor<SampleEntity25> qEntity = entity.adapt(QualifierEntityDescriptor.class);
		final PresentationResultDescriptor<?> r = qEntity.getQualifierOperation(operation, qualifier).getResultDescriptor()
				.adapt(PresentationResultDescriptor.class);
		Assert.assertEquals(format, r.getFormat());
		Assert.assertEquals(render, r.getRenderType());
	}

	private void assertReturn(final String operation, final String format, final RenderType render) {
		final EntityDescriptor<SampleEntity25> entity = this.model.getEntity(SampleEntity25.class);
		final PresentationResultDescriptor<?> r = entity.getOperation(operation).getResultDescriptor()
				.adapt(PresentationResultDescriptor.class);
		Assert.assertEquals(format, r.getFormat());
		Assert.assertEquals(render, r.getRenderType());
	}
}
