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
package org.lunarray.model.descriptor.builder.properties.presentation;

import java.util.Collection;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.member.Cardinality;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RelationPresentationDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity12;
import org.lunarray.model.descriptor.test.domain.SampleEntity13;
import org.lunarray.model.descriptor.test.domain.SampleEntity14;
import org.lunarray.model.descriptor.test.domain.SampleEntity15;
import org.lunarray.model.descriptor.test.domain.SampleEntity16;
import org.lunarray.model.descriptor.test.domain.SampleEntity17;
import org.lunarray.model.descriptor.test.domain.SampleEntity18;
import org.lunarray.model.descriptor.test.domain.SampleEntity21;

/* Tests FRP-01 */
public class PresentationQualifierFieldTest {

	private PresentationEntityDescriptor<SampleEntity13> descriptor13;
	private PresentationEntityDescriptor<SampleEntity14> descriptor14;
	private PresentationEntityDescriptor<SampleEntity15> descriptor15;
	private PresentationEntityDescriptor<SampleEntity17> descriptor17;
	private PresentationEntityDescriptor<SampleEntity18> descriptor18;
	private PresentationEntityDescriptor<SampleEntity21> descriptor21;
	private Model<ModelMarker> model;

	@SuppressWarnings("unchecked")
	public <Type, FieldType> PresentationPropertyDescriptor<FieldType, Type> get(final EntityDescriptor<Type> cd, final String name) {
		return cd.getProperty(name).adapt(PresentationPropertyDescriptor.class);
	}

	public <Type> RelationPresentationDescriptor getM(final EntityDescriptor<Type> cd, final String name) {
		return cd.getProperty(name).adapt(RelationPresentationDescriptor.class);
	}

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity12.class,
				SampleEntity13.class, SampleEntity14.class, SampleEntity15.class, SampleEntity16.class, SampleEntity17.class,
				SampleEntity18.class, SampleEntity21.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(resource).build();
		this.descriptor13 = this.model.getEntity(SampleEntity13.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor14 = this.model.getEntity(SampleEntity14.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor15 = this.model.getEntity(SampleEntity15.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor17 = this.model.getEntity(SampleEntity17.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor18 = this.model.getEntity(SampleEntity18.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor21 = this.model.getEntity(SampleEntity21.class).adapt(PresentationEntityDescriptor.class);
	}

	@Test
	public void testCollection() {
		Assert.assertEquals(Collection.class, this.descriptor21.getProperty("collection").getPropertyType());
	}

	/** Tests FRP-02, FRP-03, FRP-04. */
	@Test
	public void testDescriptionKey() {
		final PresentationPropertyDescriptor<?, SampleEntity13> description13Name = this.get(this.descriptor13, "name");
		Assert.assertEquals("SampleEntity13.name", description13Name.getDescriptionKey());
		Assert.assertEquals("SampleEntity13.name", description13Name.getDescription());
		Assert.assertEquals("SampleEntity13.name", description13Name.getDescription(Locale.GERMAN));

		final PresentationPropertyDescriptor<?, SampleEntity13> description13Field = this.get(this.descriptor13, "descriptionField");
		Assert.assertEquals("field.label", description13Field.getDescriptionKey());
		Assert.assertEquals("field.label", description13Field.getDescription());
		Assert.assertEquals("field.label", description13Field.getDescription(Locale.GERMAN));

		final PresentationPropertyDescriptor<?, SampleEntity14> description14Field1 = this.get(this.descriptor14, "field1");
		Assert.assertEquals("SampleEntity14.field1", description14Field1.getDescriptionKey());
		Assert.assertEquals("SampleEntity14.field1", description14Field1.getDescription());
		Assert.assertEquals("SampleEntity14.field1", description14Field1.getDescription(Locale.GERMAN));

		final PresentationPropertyDescriptor<?, SampleEntity14> description14Field2 = this.get(this.descriptor14, "field2");
		Assert.assertEquals("field2.key", description14Field2.getDescriptionKey());
		Assert.assertEquals("Field 2", description14Field2.getDescription());
		Assert.assertEquals("Feld 2", description14Field2.getDescription(Locale.GERMAN));

		final PresentationPropertyDescriptor<?, SampleEntity15> description15Field1 = this.get(this.descriptor15, "field1");
		Assert.assertEquals("SampleEntity15.field1", description15Field1.getDescriptionKey());
		Assert.assertEquals("SampleEntity15.field1", description15Field1.getDescription());
		Assert.assertEquals("SampleEntity15.field1", description15Field1.getDescription(Locale.GERMAN));

		final PresentationPropertyDescriptor<?, SampleEntity15> description15Field3 = this.get(this.descriptor15, "field3");
		Assert.assertEquals("test.label", description15Field3.getDescriptionKey());
		Assert.assertEquals("test.label", description15Field3.getDescription());
		Assert.assertEquals("test.label", description15Field3.getDescription(Locale.GERMAN));
	}

	@Test
	public void testFormat() {
		Assert.assertEquals("", this.get(this.descriptor18, "test01").getFormat());
		Assert.assertEquals("", this.get(this.descriptor18, "test02").getFormat());
		Assert.assertEquals("", this.get(this.descriptor18, "test03").getFormat());
		Assert.assertEquals("", this.get(this.descriptor18, "test04").getFormat());
		Assert.assertEquals("ddmmyyyy", this.get(this.descriptor18, "test05").getFormat());
		Assert.assertEquals("", this.get(this.descriptor18, "test06").getFormat());
		Assert.assertEquals("", this.get(this.descriptor18, "test07").getFormat());
		Assert.assertEquals("", this.get(this.descriptor18, "test08").getFormat());
	}

	/** Tests FRP-10. */
	@Test
	public void testImmutableProperties() {
		Assert.assertFalse(this.get(this.descriptor17, "test1").isImmutable());
		Assert.assertTrue(this.get(this.descriptor17, "test2").isImmutable());
		Assert.assertFalse(this.get(this.descriptor17, "test3").isImmutable());
		Assert.assertTrue(this.get(this.descriptor17, "test4").isImmutable());
		Assert.assertTrue(this.get(this.descriptor17, "test5").isImmutable());
		Assert.assertFalse(this.get(this.descriptor17, "test6").isImmutable());
		Assert.assertTrue(this.get(this.descriptor17, "test7").isImmutable());
		Assert.assertTrue(this.get(this.descriptor17, "test8").isImmutable());
		Assert.assertFalse(this.get(this.descriptor17, "test9").isImmutable());
		Assert.assertFalse(this.get(this.descriptor17, "test10").isImmutable());
		Assert.assertTrue(this.get(this.descriptor17, "test11").isImmutable());
		Assert.assertFalse(this.get(this.descriptor17, "test12").isImmutable());
	}

	/** Tests FRP-21, FRP-22. */
	@Test
	public void testInlineField() {
		Assert.assertFalse(this.getM(this.descriptor17, "test1").isInLineIndication());
		Assert.assertTrue(this.getM(this.descriptor17, "test2").isInLineIndication());
		Assert.assertFalse(this.getM(this.descriptor17, "test3").isInLineIndication());
		Assert.assertFalse(this.getM(this.descriptor17, "test4").isInLineIndication());
		Assert.assertTrue(this.getM(this.descriptor17, "test5").isInLineIndication());
		Assert.assertFalse(this.getM(this.descriptor17, "test6").isInLineIndication());
		Assert.assertFalse(this.getM(this.descriptor17, "test7").isInLineIndication());
		Assert.assertTrue(this.getM(this.descriptor17, "test8").isInLineIndication());
		Assert.assertFalse(this.getM(this.descriptor17, "test9").isInLineIndication());
		Assert.assertNull(this.getM(this.descriptor17, "test10"));
		Assert.assertNull(this.getM(this.descriptor17, "test11"));
		Assert.assertNull(this.getM(this.descriptor17, "test12"));
	}

	/** Tests FRP-07, FRP-14. */
	@Test
	public void testNameField() {
		Assert.assertFalse(this.get(this.descriptor15, "field1").isName());
		Assert.assertTrue(this.get(this.descriptor15, "field2").isName());
		Assert.assertFalse(this.get(this.descriptor15, "field3").isName());
	}

	/** Tests FRP-05. */
	@Test
	public void testOrder() {
		Assert.assertEquals(Integer.MAX_VALUE, this.get(this.descriptor13, "name").getOrder());
		Assert.assertEquals(Integer.MAX_VALUE, this.get(this.descriptor13, "descriptionField").getOrder());
		Assert.assertEquals(2, this.get(this.descriptor14, "field1").getOrder());
		Assert.assertEquals(3, this.get(this.descriptor14, "field2").getOrder());
		Assert.assertEquals(3, this.get(this.descriptor15, "field1").getOrder());
		Assert.assertEquals(1, this.get(this.descriptor15, "field3").getOrder());

	}

	/** Tests FRP-06, FRP-14, FRP-15, FRP-16, FRP-17, FRP-18, FRP-19. */
	@Test
	public void testRenderType() {
		Assert.assertEquals(RenderType.DROPDOWN, this.get(this.descriptor18, "test01").getRenderType());
		Assert.assertEquals(RenderType.CHECKBOX, this.get(this.descriptor18, "test02").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(this.descriptor18, "test03").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(this.descriptor18, "test04").getRenderType());
		Assert.assertEquals(RenderType.DATE_PICKER, this.get(this.descriptor18, "test05").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(this.descriptor18, "test06").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(this.descriptor18, "test07").getRenderType());
		Assert.assertEquals(RenderType.RADIO, this.get(this.descriptor18, "test08").getRenderType());
		Assert.assertEquals(RenderType.PICKLIST, this.get(this.descriptor21, "collection").getRenderType());
	}

	/** Tests FRP-08, FRP-11. */
	@Test
	public void testRequired() {
		Assert.assertFalse(this.get(this.descriptor17, "test1").isRequiredIndication());
		Assert.assertTrue(this.get(this.descriptor17, "test2").isRequiredIndication());
		Assert.assertFalse(this.get(this.descriptor17, "test3").isRequiredIndication());
		Assert.assertFalse(this.get(this.descriptor17, "test4").isRequiredIndication());
		Assert.assertTrue(this.get(this.descriptor17, "test5").isRequiredIndication());
		Assert.assertFalse(this.get(this.descriptor17, "test6").isRequiredIndication());
		Assert.assertFalse(this.get(this.descriptor17, "test7").isRequiredIndication());
		Assert.assertTrue(this.get(this.descriptor17, "test8").isRequiredIndication());
		Assert.assertFalse(this.get(this.descriptor17, "test9").isRequiredIndication());
		Assert.assertTrue(this.get(this.descriptor17, "test10").isRequiredIndication());
		Assert.assertTrue(this.get(this.descriptor17, "test11").isRequiredIndication());
		Assert.assertFalse(this.get(this.descriptor17, "test12").isRequiredIndication());
	}

	@Test
	public void testType() {
		for (final PropertyDescriptor<?, SampleEntity21> property : this.descriptor21.getProperties()) {
			Assert.assertEquals(Cardinality.MULTIPLE, property.getCardinality());
			Assert.assertTrue(Collection.class.isAssignableFrom(property.getPropertyType()));
			Assert.assertTrue(property.isRelation());
		}
	}

	/** Tests FRP-09, FRP-12, FRP-20. */
	@Test
	public void testVisibility() {
		Assert.assertTrue(this.get(this.descriptor15, "field1").isVisible());
		Assert.assertFalse(this.get(this.descriptor15, "field2").isVisible());
		Assert.assertTrue(this.get(this.descriptor15, "field3").isVisible());
		Assert.assertTrue(this.get(this.descriptor15, "field4").isVisible());
		Assert.assertFalse(this.get(this.descriptor15, "field5").isVisible());
		Assert.assertFalse(this.get(this.descriptor15, "field6").isVisible());
	}
}
