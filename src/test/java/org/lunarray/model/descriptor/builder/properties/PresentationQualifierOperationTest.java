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
package org.lunarray.model.descriptor.builder.properties;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity07;
import org.lunarray.model.descriptor.test.domain.SampleEntity29;
import org.lunarray.model.descriptor.test.domain.SampleEntity30;
import org.lunarray.model.descriptor.util.OperationInvocationBuilder;

public class PresentationQualifierOperationTest {

	private Model<ModelMarker> model;

	@Before
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity29.class,
				SampleEntity30.class, SampleEntity07.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(resource).build();
	}

	@Test
	public void testCollectionOperation() throws Exception {
		final EntityDescriptor<SampleEntity29> d = this.model.getEntity(SampleEntity29.class);
		final OperationDescriptor<SampleEntity29> c = d.getOperation("operation5");
		Assert.assertEquals(SampleEntity29.class, c.getEntityType());
		Assert.assertEquals("operation5", c.getName());
		Assert.assertEquals(1, c.getParameterCount());
		Assert.assertNotNull(c.getParameter(0));
		Assert.assertNotNull(c.getParameter(0, List.class));
		@SuppressWarnings("rawtypes")
		final ResultDescriptor<List> r = c.getResultDescriptor(List.class);
		Assert.assertEquals(Cardinality.MULTIPLE, r.getCardinality());
		Assert.assertEquals(List.class, r.getResultType());
		@SuppressWarnings("rawtypes")
		final ParameterDescriptor<List> p = c.getParameter(0, List.class);
		Assert.assertEquals(Cardinality.MULTIPLE, p.getCardinality());
		Assert.assertEquals(0, p.getIndex());
		Assert.assertEquals(List.class, p.getType());
	}

	@Test
	public void testOperation() throws Exception {
		final EntityDescriptor<SampleEntity29> d = this.model.getEntity(SampleEntity29.class);
		final OperationDescriptor<SampleEntity29> c = d.getOperation("operation");
		Assert.assertEquals(SampleEntity29.class, c.getEntityType());
		Assert.assertEquals("operation", c.getName());
		Assert.assertEquals(1, c.getParameterCount());
		Assert.assertNotNull(c.getParameter(0));
		Assert.assertNotNull(c.getParameter(0, Boolean.TYPE));
		final ResultDescriptor<Boolean> r = c.getResultDescriptor(Boolean.TYPE);
		Assert.assertEquals(Cardinality.SINGLE, r.getCardinality());
		Assert.assertEquals(Boolean.TYPE, r.getResultType());
		final ParameterDescriptor<Boolean> p = c.getParameter(0, Boolean.TYPE);
		Assert.assertEquals(Cardinality.SINGLE, p.getCardinality());
		Assert.assertEquals(0, p.getIndex());
		Assert.assertEquals(Boolean.TYPE, p.getType());
	}

	@Test
	public void testEcho() throws Exception {
		final EntityDescriptor<SampleEntity29> d = this.model.getEntity(SampleEntity29.class);
		final OperationDescriptor<SampleEntity29> c = d.getOperation("operation");
		final OperationInvocationBuilder<SampleEntity29> b = new OperationInvocationBuilder<SampleEntity29>(c);
		final SampleEntity29 e = new SampleEntity29();
		b.parameter(0, true);
		b.target(e);
		Assert.assertEquals(true, b.execute(Boolean.TYPE).booleanValue());
		Assert.assertEquals(true, b.execute());
	}

	@Test
	public void testInnerEcho() throws Exception {
		final EntityDescriptor<SampleEntity30> d = this.model.getEntity(SampleEntity30.class);
		final OperationDescriptor<SampleEntity30> c = d.getOperation("inner.operation");
		final OperationInvocationBuilder<SampleEntity30> b = new OperationInvocationBuilder<SampleEntity30>(c);
		final SampleEntity30 e = new SampleEntity30();
		b.parameter(0, true);
		b.target(e);
		Assert.assertEquals(true, b.execute());
		Assert.assertEquals(true, b.execute(Boolean.TYPE).booleanValue());
	}
}
