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
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.CollectionParameterDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationType;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.SampleEntity25;

public class SimpleParameterTest {

	private Model<Object> model;

	@Before
	public void init() throws Exception {
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>();
		resource.addClazz(SampleEntity25.class);
		this.model = SimpleBuilder.createBuilder().resources(resource).build();
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
	}
}
