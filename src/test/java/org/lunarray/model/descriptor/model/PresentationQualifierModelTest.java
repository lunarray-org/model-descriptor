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
package org.lunarray.model.descriptor.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity01;
import org.lunarray.model.descriptor.test.domain.SampleEntity02;
import org.lunarray.model.descriptor.test.domain.SampleEntity03;
import org.lunarray.model.descriptor.test.domain.SampleEntity04;
import org.lunarray.model.descriptor.test.domain.SampleEntity05;

/**
 * Tests MR-01. Tests MR-02. Tests MR-03. Tests MR-04. Tests MR-05.
 * 
 * @author Pal Hargitai
 */
public class PresentationQualifierModelTest {

	private Model<ModelMarker> model;

	/** Tests MR-04. */
	@Test
	public void denyExtensionImpl() {
		Assert.assertNull(this.model.getExtension(TestExtensionImpl.class));
	}

	/** Tests MR-03. */
	@Test
	public void findAll() {
		Assert.assertEquals(4, this.model.getEntities().size());
		Assert.assertTrue(this.model.getEntities().contains(this.model.getEntity(SampleEntity01.class)));
		Assert.assertTrue(this.model.getEntities().contains(this.model.getEntity(SampleEntity02.class)));
		Assert.assertTrue(this.model.getEntities().contains(this.model.getEntity(SampleEntity03.class)));
		Assert.assertTrue(this.model.getEntities().contains(this.model.getEntity(SampleEntity04.class)));
	}

	/** Tests MR-04. */
	@Test
	public void findExtension() {
		Assert.assertNotNull(this.model.getExtension(TestExtension.class));
	}

	/** Tests MR-01. */
	@Test
	public void findKnownsByClazz() {
		Assert.assertNotNull(this.model.getEntity(SampleEntity01.class));
		Assert.assertNotNull(this.model.getEntity(SampleEntity02.class));
		Assert.assertNotNull(this.model.getEntity(SampleEntity03.class));
		Assert.assertNotNull(this.model.getEntity(SampleEntity04.class));
	}

	/** Tests MR-02. */
	@Test
	public void findKnownsByKey() {
		Assert.assertNotNull(this.model.getEntity("SampleEntity01"));
		Assert.assertNotNull(this.model.getEntity("SampleEntity02"));
	}

	/** Tests MR-05. */
	@Test
	public void findKnownsByKeyMappingName() {
		Assert.assertNotNull(this.model.getEntity("entity03"));
		Assert.assertNotNull(this.model.getEntity("entity04"));
	}

	/** Tests MR-01. */
	@Test
	public void findUnknownsByClazz() {
		Assert.assertNull(this.model.getEntity(SampleEntity05.class));
	}

	/** Tests MR-02. */
	@Test
	public void findUnknownsByKey() {
		Assert.assertNull(this.model.getEntity("Testentity"));
	}

	@Before
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity01.class,
				SampleEntity02.class, SampleEntity03.class, SampleEntity04.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		builder.extensions(new TestExtensionImpl());
		this.model = builder.resources(resource).build();
	}
}
