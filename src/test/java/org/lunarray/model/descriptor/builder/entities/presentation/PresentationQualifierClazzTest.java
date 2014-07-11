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
package org.lunarray.model.descriptor.builder.entities.presentation;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity12;
import org.lunarray.model.descriptor.test.domain.SampleEntity13;
import org.lunarray.model.descriptor.test.domain.SampleEntity14;
import org.lunarray.model.descriptor.test.domain.SampleEntity15;

public class PresentationQualifierClazzTest {

	private PresentationEntityDescriptor<SampleEntity12> descriptor12;
	private PresentationEntityDescriptor<SampleEntity13> descriptor13;
	private PresentationEntityDescriptor<SampleEntity14> descriptor14;
	private PresentationEntityDescriptor<SampleEntity15> descriptor15;
	private Model<ModelMarker> model;

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity12.class,
				SampleEntity13.class, SampleEntity14.class, SampleEntity15.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(resource).build();
		this.descriptor12 = this.model.getEntity(SampleEntity12.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor13 = this.model.getEntity(SampleEntity13.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor14 = this.model.getEntity(SampleEntity14.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor15 = this.model.getEntity(SampleEntity15.class).adapt(PresentationEntityDescriptor.class);
	}

	/** Tests CRP-01, CRP-02, CRP-03, CRP-09. */
	@Test
	public void testDefaultDescriptor() {
		Assert.assertEquals(this.descriptor12.getName(), this.descriptor12.getDescriptionKey());
		Assert.assertEquals(this.descriptor12.getName(), this.descriptor12.getDescription());
		Assert.assertEquals(this.descriptor12.getName(), this.descriptor12.getDescription(Locale.GERMAN));
		Assert.assertEquals("entity15", this.descriptor15.getDescriptionKey());
		Assert.assertEquals("Entity 15", this.descriptor15.getDescription());
		Assert.assertEquals("Entiteit 15", this.descriptor15.getDescription(Locale.GERMAN));
	}

	/** Tests CRP-05. */
	@Test
	public void testFieldOrder() {
		@SuppressWarnings("unchecked")
		final List<PresentationPropertyDescriptor<?, SampleEntity12>> orderedList12 = this.descriptor12.adapt(
				PresentationEntityDescriptor.class).getOrderedProperties();
		Assert.assertEquals(3, orderedList12.size());
		Assert.assertEquals("field3", orderedList12.get(0).getName());
		Assert.assertEquals("field2", orderedList12.get(1).getName());
		Assert.assertEquals("field1", orderedList12.get(2).getName());
		@SuppressWarnings("unchecked")
		final List<PresentationPropertyDescriptor<?, SampleEntity14>> orderedList14 = this.descriptor14.adapt(
				PresentationEntityDescriptor.class).getOrderedProperties();
		Assert.assertEquals(3, orderedList14.size());
		Assert.assertEquals("field3", orderedList14.get(0).getName());
		Assert.assertEquals("field1", orderedList14.get(1).getName());
		Assert.assertEquals("field2", orderedList14.get(2).getName());
		@SuppressWarnings("unchecked")
		final List<PresentationPropertyDescriptor<?, SampleEntity15>> orderedList15 = this.descriptor15.adapt(
				PresentationEntityDescriptor.class).getOrderedProperties();
		Assert.assertEquals(3, orderedList15.size());
		Assert.assertEquals("field3", orderedList15.get(0).getName());
		Assert.assertEquals("field1", orderedList15.get(1).getName());
	}

	/** Tests CRP-04. */
	@Test
	public void testNameDescriptor() {
		Assert.assertEquals(this.descriptor12.getProperty("name"), this.descriptor12.getNameProperty());
		Assert.assertEquals(this.descriptor13.getProperty("name"), this.descriptor13.getNameProperty());
	}

	/** Tests CRP-01, CRP-02, CRP-03, CRP-10. */
	@Test
	public void testNonDefaultDescriptor() {
		Assert.assertEquals("entity13", this.descriptor13.getDescriptionKey());
		Assert.assertEquals("entity13", this.descriptor13.getDescription());
		Assert.assertEquals("entity13", this.descriptor13.getDescription(Locale.GERMAN));
		Assert.assertEquals("SampleEntity14", this.descriptor14.getDescriptionKey());
		Assert.assertEquals("Entity 14", this.descriptor14.getDescription());
		Assert.assertEquals("Entiteit 14", this.descriptor14.getDescription(Locale.GERMAN));
	}

	/** Tests CRP-04. */
	@Test
	public void testNonNameDescriptor() {
		Assert.assertNull(this.descriptor14.getNameProperty());
		Assert.assertNotNull(this.descriptor15.getNameProperty());
	}

	/** Tests CRP-06, CRP-07. */
	@Test
	public void testNonResourceBundleDescriptor() {
		Assert.assertNull(this.descriptor12.getResourceBundle());
		Assert.assertNull(this.descriptor12.getResourceBundle(Locale.GERMAN));
		Assert.assertNull(this.descriptor13.getResourceBundle());
		Assert.assertNull(this.descriptor13.getResourceBundle(Locale.GERMAN));
	}

	/** Tests CRP-06, CRP-07. */
	@Test
	public void testResourceBundleDescriptor() {
		Assert.assertNotNull(this.descriptor14.getResourceBundle());
		Assert.assertNotNull(this.descriptor14.getResourceBundle(Locale.GERMAN));
		Assert.assertNotNull(this.descriptor15.getResourceBundle());
		Assert.assertNotNull(this.descriptor15.getResourceBundle(Locale.GERMAN));
	}

	/** Tests CRP-11, CRP-08. */
	@Test
	public void testVisibility() {
		Assert.assertTrue(this.descriptor12.isVisible());
		Assert.assertTrue(this.descriptor13.isVisible());
		Assert.assertFalse(this.descriptor14.isVisible());
	}
}
