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
package org.lunarray.model.descriptor.builder.entities;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity05;
import org.lunarray.model.descriptor.test.domain.SampleEntity06;
import org.lunarray.model.descriptor.test.domain.SampleEntity07;

/**
 * Tests CR-01. Tests CR-02. Tests CR-03. Tests CR-04. Tests CR-05. Tests CR-06.
 * Tests CR-07.
 * 
 * @author Pal Hargitai
 */
public class PresentationQualifierClazzTest {

	private Model<ModelMarker> model;

	@Before
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity05.class,
				SampleEntity06.class, SampleEntity07.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(resource).build();
	}

	/** Tests CR-01. */
	@Test
	public void testClassEqualsness() {
		Assert.assertEquals(SampleEntity05.class, this.model.getEntity(SampleEntity05.class).getEntityType());
		Assert.assertEquals(SampleEntity06.class, this.model.getEntity(SampleEntity06.class).getEntityType());
		Assert.assertEquals(SampleEntity07.class, this.model.getEntity(SampleEntity07.class).getEntityType());
	}

	/** Tests CR-02. */
	@Test
	public void testGetFields() {
		Assert.assertEquals(1, this.model.getEntity(SampleEntity05.class).getProperties().size());
		Assert.assertEquals(2, this.model.getEntity(SampleEntity06.class).getProperties().size());
		Assert.assertEquals(1, this.model.getEntity(SampleEntity07.class).getProperties().size());
	}

	/** Tests CR-05. */
	@Test
	public void testIdentifiable() {
		Assert.assertNotNull(this.model.getEntity(SampleEntity07.class).adapt(KeyedEntityDescriptor.class));
	}

	/** Tests CR-07. */
	@Test
	public void testIdentifierField() {
		Assert.assertNotNull(this.model.getEntity(SampleEntity07.class).adapt(KeyedEntityDescriptor.class));
		@SuppressWarnings("unchecked")
		final KeyedEntityDescriptor<SampleEntity06, String> idDesc = this.model.getEntity(SampleEntity07.class).adapt(
				KeyedEntityDescriptor.class);
		Assert.assertNotNull(idDesc.getKeyProperty());
		Assert.assertEquals(this.model.getEntity(SampleEntity07.class).getProperty("testField"), idDesc.getKeyProperty());
	}

	/** Tests CR-03. */
	@Test
	public void testLookupFields() {
		Assert.assertNotNull(this.model.getEntity(SampleEntity05.class).getProperty("testField"));
		Assert.assertNotNull(this.model.getEntity(SampleEntity06.class).getProperty("testField"));
		Assert.assertNotNull(this.model.getEntity(SampleEntity06.class).getProperty("testField2"));
		Assert.assertNotNull(this.model.getEntity(SampleEntity07.class).getProperty("testField"));
	}

	/** Tests CR-03. */
	@Test
	public void testLookupNonexistingFields() {
		Assert.assertNull(this.model.getEntity(SampleEntity05.class).getProperty("testStatic"));
		Assert.assertNull(this.model.getEntity(SampleEntity06.class).getProperty("testField3"));
	}

	/** Tests CR-04. */
	@Test
	public void testNameEqualsness() {
		Assert.assertEquals("SampleEntity05", this.model.getEntity(SampleEntity05.class).getName());
		Assert.assertEquals("SampleEntity07", this.model.getEntity(SampleEntity07.class).getName());
	}

	/** Tests CR-06. */
	@Test
	public void testNameEqualsnessMappingName() {
		Assert.assertEquals("entity06", this.model.getEntity(SampleEntity06.class).getName());
	}

	/** Tests CR-05. */
	@Test
	public void testNotIdentifiable() {
		Assert.assertNull(this.model.getEntity(SampleEntity05.class).adapt(KeyedEntityDescriptor.class));
		Assert.assertNull(this.model.getEntity(SampleEntity06.class).adapt(KeyedEntityDescriptor.class));
	}
}
