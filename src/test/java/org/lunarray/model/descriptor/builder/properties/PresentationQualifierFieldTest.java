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

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.property.CollectionPropertyDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.DecoratedSampleEntity07;
import org.lunarray.model.descriptor.test.domain.DecoratedSampleEntity09;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity07;
import org.lunarray.model.descriptor.test.domain.SampleEntity08;
import org.lunarray.model.descriptor.test.domain.SampleEntity09;

public class PresentationQualifierFieldTest {

	private Model<ModelMarker> model;

	@Before
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity07.class,
				SampleEntity08.class, SampleEntity09.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(resource).build();
	}

	/** Tests FR-13. */
	@Test
	public void testAdapterRelated() {
		final EntityDescriptor<SampleEntity08> entity = this.model.getEntity(SampleEntity08.class);
		Assert.assertNull(entity.getProperty("field").adapt(RelationDescriptor.class));
		Assert.assertNull(entity.getProperty("field2").adapt(RelationDescriptor.class));
		Assert.assertNull(entity.getProperty("field3").adapt(RelationDescriptor.class));
		Assert.assertNull(entity.getProperty("field4").adapt(RelationDescriptor.class));
		Assert.assertNull(entity.getProperty("field5").adapt(RelationDescriptor.class));
		Assert.assertNotNull(entity.getProperty("sampleEntity07").adapt(RelationDescriptor.class));
		Assert.assertNull(this.model.getEntity(SampleEntity09.class).getProperty("testValue"));
	}

	/** Tests FR-14. */
	@Test
	public void testAdapterMultiple() {
		final EntityDescriptor<SampleEntity08> entity = this.model.getEntity(SampleEntity08.class);
		Assert.assertNull(entity.getProperty("field").adapt(CollectionPropertyDescriptor.class));
		Assert.assertNull(entity.getProperty("field2").adapt(CollectionPropertyDescriptor.class));
		Assert.assertNull(entity.getProperty("field3").adapt(CollectionPropertyDescriptor.class));
		Assert.assertNull(entity.getProperty("field4").adapt(CollectionPropertyDescriptor.class));
		Assert.assertNotNull(entity.getProperty("field5").adapt(CollectionPropertyDescriptor.class));
		Assert.assertNull(entity.getProperty("sampleEntity07").adapt(CollectionPropertyDescriptor.class));
	}

	/** Tests FR-09. Tests FR-10. Tests FR-11. Tests FR-12. */
	@Test
	public void testCardinality() {
		final EntityDescriptor<SampleEntity08> entity = this.model.getEntity(SampleEntity08.class);
		Assert.assertEquals(Cardinality.SINGLE, entity.getProperty("field").getCardinality());
		Assert.assertEquals(Cardinality.NULLABLE, entity.getProperty("field2").getCardinality());
		Assert.assertEquals(Cardinality.SINGLE, entity.getProperty("field3").getCardinality());
		Assert.assertEquals(Cardinality.NULLABLE, entity.getProperty("field4").getCardinality());
		Assert.assertEquals(Cardinality.NULLABLE, entity.getProperty("sampleEntity07").getCardinality());
		Assert.assertEquals(Cardinality.MULTIPLE, entity.getProperty("field5").getCardinality());
	}

	/** Tests FR-15. */
	@Test
	public void testClazzes() {
		final EntityDescriptor<SampleEntity08> entity = this.model.getEntity(SampleEntity08.class);
		Assert.assertEquals(Boolean.TYPE, entity.getProperty("field").getPropertyType());
		Assert.assertEquals(String.class, entity.getProperty("field2").getPropertyType());
		Assert.assertEquals(Integer.TYPE, entity.getProperty("field3").getPropertyType());
		Assert.assertEquals(String.class, entity.getProperty("field4").getPropertyType());
		Assert.assertEquals(SampleEntity07.class, entity.getProperty("sampleEntity07").getPropertyType());
	}

	/** Tests FR-05. Tests FR-06. Tests FR-07. */
	@Test
	public void testImutable() {
		final EntityDescriptor<SampleEntity08> entity = this.model.getEntity(SampleEntity08.class);
		Assert.assertEquals(false, entity.getProperty("field").isImmutable());
		Assert.assertEquals(false, entity.getProperty("field2").isImmutable());
		Assert.assertEquals(true, entity.getProperty("field3").isImmutable());
		Assert.assertEquals(true, entity.getProperty("field4").isImmutable());
		Assert.assertEquals(false, entity.getProperty("sampleEntity07").isImmutable());
	}

	/** Tests FR-08. */
	@Test
	public void testRelated() {
		final EntityDescriptor<SampleEntity08> entity = this.model.getEntity(SampleEntity08.class);
		Assert.assertEquals(false, entity.getProperty("field").isRelation());
		Assert.assertEquals(false, entity.getProperty("field2").isRelation());
		Assert.assertEquals(false, entity.getProperty("field3").isRelation());
		Assert.assertEquals(false, entity.getProperty("field4").isRelation());
		Assert.assertEquals(true, entity.getProperty("sampleEntity07").isRelation());
	}

	/** Tests FR-01. */
	@Test
	public void testNames() {
		final EntityDescriptor<SampleEntity08> entity = this.model.getEntity(SampleEntity08.class);
		Assert.assertEquals("field", entity.getProperty("field").getName());
		Assert.assertEquals("field2", entity.getProperty("field2").getName());
		Assert.assertEquals("field3", entity.getProperty("field3").getName());
		Assert.assertEquals("field4", entity.getProperty("field4").getName());
		Assert.assertEquals("sampleEntity07", entity.getProperty("sampleEntity07").getName());
	}

	/** Tests FR-02. Tests FR-03. Tests FR-04. */
	@Test
	public void valueSetEmbeddedTest() throws Exception {
		final EntityDescriptor<SampleEntity09> entity = this.model.getEntity(SampleEntity09.class);
		final DecoratedSampleEntity09 entity09 = new DecoratedSampleEntity09();
		final DecoratedSampleEntity07 entity07 = new DecoratedSampleEntity07();
		entity09.init(entity07);
		entity.getProperty("sampleEntity07.testField", String.class).setValue(entity09, "Test Value");
		Assert.assertEquals(1, entity09.getGetCounter());
		Assert.assertEquals(0, entity09.getSetCounter());
		Assert.assertEquals(0, entity07.getGetCounter());
		Assert.assertEquals(1, entity07.getSetCounter());
		Assert.assertEquals("Test Value", entity09.getSampleEntity07().getTestField());
		Assert.assertEquals(2, entity09.getGetCounter());
		Assert.assertEquals(0, entity09.getSetCounter());
		Assert.assertEquals(1, entity07.getGetCounter());
		Assert.assertEquals(1, entity07.getSetCounter());
	}

	/** Tests FR-02. Tests FR-03. Tests FR-04. */
	@Test
	public void valueSetTest() throws Exception {
		final EntityDescriptor<SampleEntity09> entity = this.model.getEntity(SampleEntity09.class);
		final DecoratedSampleEntity09 entity09 = new DecoratedSampleEntity09();
		final DecoratedSampleEntity07 entity07 = new DecoratedSampleEntity07();
		entity.getProperty("sampleEntity07", SampleEntity07.class).setValue(entity09, entity07);
		Assert.assertEquals(0, entity09.getGetCounter());
		Assert.assertEquals(1, entity09.getSetCounter());
		Assert.assertEquals(entity07, entity09.getSampleEntity07());
		Assert.assertEquals(1, entity09.getGetCounter());
		Assert.assertEquals(1, entity09.getSetCounter());
	}
}
