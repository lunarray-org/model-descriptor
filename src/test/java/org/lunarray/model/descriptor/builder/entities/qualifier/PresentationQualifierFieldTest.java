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
package org.lunarray.model.descriptor.builder.entities.qualifier;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.Qualifier01;
import org.lunarray.model.descriptor.test.domain.Qualifier02;
import org.lunarray.model.descriptor.test.domain.Qualifier03;
import org.lunarray.model.descriptor.test.domain.SampleEntity19;
import org.lunarray.model.descriptor.test.domain.SampleEntity20;

/** Tests QCR-06. */
public class PresentationQualifierFieldTest {

	private PresentationEntityDescriptor<SampleEntity20> descriptor;
	private Model<ModelMarker> model;

	public void assertOrdered(final Class<?> qualifier, final String... expected) {
		final List<String> expectedList = new LinkedList<String>();
		for (final String e : expected) {
			expectedList.add(e);
		}
		final List<String> found = new LinkedList<String>();
		for (final PropertyDescriptor<?, Object> fd : this.get(qualifier).getOrderedProperties()) {
			found.add(fd.getName());
		}
		Assert.assertEquals(expectedList, found);
	}

	@SuppressWarnings("unchecked")
	public <Type> PresentationEntityDescriptor<Type> get(final Class<?> qualifier) {
		final QualifierEntityDescriptor<Type> qualifierDescriptor = this.descriptor.adapt(QualifierEntityDescriptor.class);
		final QualifierEntityDescriptor<Type> qualifierClazzDescriptor = qualifierDescriptor.getQualifierEntity(qualifier);
		return qualifierClazzDescriptor.adapt(PresentationEntityDescriptor.class);
	}

	@SuppressWarnings("unchecked")
	public <Type> PresentationPropertyDescriptor<String, Type> get(final Class<?> qualifier, final String fieldName) {
		final QualifierEntityDescriptor<Type> qualifierDescriptor = this.descriptor.adapt(QualifierEntityDescriptor.class);
		final QualifierEntityDescriptor<Type> qualifierClazzDescriptor = qualifierDescriptor.getQualifierEntity(qualifier);
		return qualifierClazzDescriptor.getQualifierProperty(fieldName, qualifier, String.class)
				.adapt(PresentationPropertyDescriptor.class);
	}

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity19.class,
				SampleEntity20.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(resource).build();
		this.descriptor = this.model.getEntity(SampleEntity20.class).adapt(PresentationEntityDescriptor.class);
	}

	@Test
	public void testFields() {
		Assert.assertEquals("SampleEntity20.value01", this.get(Qualifier01.class, "value01").getDescription());
		Assert.assertEquals("Field 1a", this.get(Qualifier02.class, "value01").getDescription());
		Assert.assertEquals("SampleEntity20.value01", this.get(Qualifier03.class, "value01").getDescription());
		Assert.assertEquals("Field 2", this.get(Qualifier01.class, "value02").getDescription());
		Assert.assertEquals("Field 2a", this.get(Qualifier02.class, "value02").getDescription());
		Assert.assertEquals("Field 2a", this.get(Qualifier03.class, "value02").getDescription());
		Assert.assertEquals("field3.key", this.get(Qualifier01.class, "value03").getDescription());
		Assert.assertEquals("Field 4a", this.get(Qualifier02.class, "value03").getDescription());
		Assert.assertEquals("Field 3a", this.get(Qualifier03.class, "value03").getDescription());
		Assert.assertEquals("SampleEntity20.value04", this.get(Qualifier01.class, "value04").getDescription());
		Assert.assertEquals("Field 5a", this.get(Qualifier02.class, "value04").getDescription());
		Assert.assertEquals("SampleEntity20.value04", this.get(Qualifier03.class, "value04").getDescription());
		Assert.assertEquals("SampleEntity20.value05", this.get(Qualifier01.class, "value05").getDescription());
		Assert.assertEquals("field6.key", this.get(Qualifier02.class, "value05").getDescription());
		Assert.assertEquals("SampleEntity20.value05", this.get(Qualifier03.class, "value05").getDescription());

		Assert.assertEquals("SampleEntity20.value01", this.get(Qualifier01.class, "value01").getDescription(Locale.GERMAN));
		Assert.assertEquals("Field 1a", this.get(Qualifier02.class, "value01").getDescription(Locale.GERMAN));
		Assert.assertEquals("SampleEntity20.value01", this.get(Qualifier03.class, "value01").getDescription(Locale.GERMAN));
		Assert.assertEquals("Feld 2", this.get(Qualifier01.class, "value02").getDescription(Locale.GERMAN));
		Assert.assertEquals("Field 2a", this.get(Qualifier02.class, "value02").getDescription(Locale.GERMAN));
		Assert.assertEquals("Field 2a", this.get(Qualifier03.class, "value02").getDescription(Locale.GERMAN));
		Assert.assertEquals("field3.key", this.get(Qualifier01.class, "value03").getDescription(Locale.GERMAN));
		Assert.assertEquals("Field 4a", this.get(Qualifier02.class, "value03").getDescription(Locale.GERMAN));
		Assert.assertEquals("Field 3a", this.get(Qualifier03.class, "value03").getDescription(Locale.GERMAN));
		Assert.assertEquals("SampleEntity20.value04", this.get(Qualifier01.class, "value04").getDescription(Locale.GERMAN));
		Assert.assertEquals("Field 5a", this.get(Qualifier02.class, "value04").getDescription(Locale.GERMAN));
		Assert.assertEquals("SampleEntity20.value04", this.get(Qualifier03.class, "value04").getDescription(Locale.GERMAN));
		Assert.assertEquals("SampleEntity20.value05", this.get(Qualifier01.class, "value05").getDescription(Locale.GERMAN));
		Assert.assertEquals("field6.key", this.get(Qualifier02.class, "value05").getDescription(Locale.GERMAN));
		Assert.assertEquals("SampleEntity20.value05", this.get(Qualifier03.class, "value05").getDescription(Locale.GERMAN));

		Assert.assertEquals("SampleEntity20.value01", this.get(Qualifier01.class, "value01").getDescriptionKey());
		Assert.assertEquals("field1.key", this.get(Qualifier02.class, "value01").getDescriptionKey());
		Assert.assertEquals("SampleEntity20.value01", this.get(Qualifier03.class, "value01").getDescriptionKey());
		Assert.assertEquals("field2.key", this.get(Qualifier01.class, "value02").getDescriptionKey());
		Assert.assertEquals("field2.key", this.get(Qualifier02.class, "value02").getDescriptionKey());
		Assert.assertEquals("field2.key", this.get(Qualifier03.class, "value02").getDescriptionKey());
		Assert.assertEquals("field3.key", this.get(Qualifier01.class, "value03").getDescriptionKey());
		Assert.assertEquals("field4.key", this.get(Qualifier02.class, "value03").getDescriptionKey());
		Assert.assertEquals("field3.key", this.get(Qualifier03.class, "value03").getDescriptionKey());
		Assert.assertEquals("SampleEntity20.value04", this.get(Qualifier01.class, "value04").getDescriptionKey());
		Assert.assertEquals("field5.key", this.get(Qualifier02.class, "value04").getDescriptionKey());
		Assert.assertEquals("SampleEntity20.value04", this.get(Qualifier03.class, "value04").getDescriptionKey());
		Assert.assertEquals("SampleEntity20.value05", this.get(Qualifier01.class, "value05").getDescriptionKey());
		Assert.assertEquals("field6.key", this.get(Qualifier02.class, "value05").getDescriptionKey());
		Assert.assertEquals("SampleEntity20.value05", this.get(Qualifier03.class, "value05").getDescriptionKey());

		Assert.assertEquals("", this.get(Qualifier01.class, "value01").getFormat());
		Assert.assertEquals("", this.get(Qualifier02.class, "value01").getFormat());
		Assert.assertEquals("", this.get(Qualifier03.class, "value01").getFormat());
		Assert.assertEquals("test4", this.get(Qualifier01.class, "value02").getFormat());
		Assert.assertEquals("test4", this.get(Qualifier02.class, "value02").getFormat());
		Assert.assertEquals("test4", this.get(Qualifier03.class, "value02").getFormat());
		Assert.assertEquals("test", this.get(Qualifier01.class, "value03").getFormat());
		Assert.assertEquals("test2", this.get(Qualifier02.class, "value03").getFormat());
		Assert.assertEquals("test", this.get(Qualifier03.class, "value03").getFormat());
		Assert.assertEquals("", this.get(Qualifier01.class, "value04").getFormat());
		Assert.assertEquals("", this.get(Qualifier02.class, "value04").getFormat());
		Assert.assertEquals("", this.get(Qualifier03.class, "value04").getFormat());
		Assert.assertEquals("", this.get(Qualifier01.class, "value05").getFormat());
		Assert.assertEquals("", this.get(Qualifier02.class, "value05").getFormat());
		Assert.assertEquals("", this.get(Qualifier03.class, "value05").getFormat());

		Assert.assertEquals(Integer.MIN_VALUE, this.get(Qualifier01.class, "value01").getOrder());
		Assert.assertEquals(5, this.get(Qualifier02.class, "value01").getOrder());
		Assert.assertEquals(Integer.MIN_VALUE, this.get(Qualifier03.class, "value01").getOrder());
		Assert.assertEquals(6, this.get(Qualifier01.class, "value02").getOrder());
		Assert.assertEquals(6, this.get(Qualifier02.class, "value02").getOrder());
		Assert.assertEquals(6, this.get(Qualifier03.class, "value02").getOrder());
		Assert.assertEquals(7, this.get(Qualifier01.class, "value03").getOrder());
		Assert.assertEquals(8, this.get(Qualifier02.class, "value03").getOrder());
		Assert.assertEquals(7, this.get(Qualifier03.class, "value03").getOrder());
		Assert.assertEquals(Integer.MIN_VALUE, this.get(Qualifier01.class, "value04").getOrder());
		Assert.assertEquals(9, this.get(Qualifier02.class, "value04").getOrder());
		Assert.assertEquals(Integer.MIN_VALUE, this.get(Qualifier03.class, "value04").getOrder());
		Assert.assertEquals(Integer.MIN_VALUE, this.get(Qualifier01.class, "value05").getOrder());
		Assert.assertEquals(Integer.MAX_VALUE, this.get(Qualifier02.class, "value05").getOrder());
		Assert.assertEquals(Integer.MAX_VALUE, this.get(Qualifier03.class, "value05").getOrder());

		Assert.assertEquals(RenderType.TEXT, this.get(Qualifier01.class, "value01").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier02.class, "value01").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(Qualifier03.class, "value01").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier01.class, "value02").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier02.class, "value02").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier03.class, "value02").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier01.class, "value03").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier02.class, "value03").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier03.class, "value03").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(Qualifier01.class, "value04").getRenderType());
		Assert.assertEquals(RenderType.TEXT_AREA, this.get(Qualifier02.class, "value04").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(Qualifier03.class, "value04").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(Qualifier01.class, "value05").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(Qualifier02.class, "value05").getRenderType());
		Assert.assertEquals(RenderType.TEXT, this.get(Qualifier03.class, "value05").getRenderType());

		Assert.assertFalse(this.get(Qualifier01.class, "value01").isName());
		Assert.assertTrue(this.get(Qualifier02.class, "value01").isName());
		Assert.assertFalse(this.get(Qualifier03.class, "value01").isName());
		Assert.assertTrue(this.get(Qualifier01.class, "value02").isName());
		Assert.assertTrue(this.get(Qualifier02.class, "value02").isName());
		Assert.assertTrue(this.get(Qualifier03.class, "value02").isName());
		Assert.assertTrue(this.get(Qualifier01.class, "value03").isName());
		Assert.assertTrue(this.get(Qualifier02.class, "value03").isName());
		Assert.assertTrue(this.get(Qualifier03.class, "value03").isName());
		Assert.assertFalse(this.get(Qualifier01.class, "value04").isName());
		Assert.assertTrue(this.get(Qualifier02.class, "value04").isName());
		Assert.assertFalse(this.get(Qualifier03.class, "value04").isName());
		Assert.assertFalse(this.get(Qualifier01.class, "value05").isName());
		Assert.assertFalse(this.get(Qualifier02.class, "value05").isName());
		Assert.assertFalse(this.get(Qualifier03.class, "value05").isName());

		Assert.assertFalse(this.get(Qualifier01.class, "value01").isImmutable());
		Assert.assertTrue(this.get(Qualifier02.class, "value01").isImmutable());
		Assert.assertFalse(this.get(Qualifier03.class, "value01").isImmutable());
		Assert.assertTrue(this.get(Qualifier01.class, "value02").isImmutable());
		Assert.assertTrue(this.get(Qualifier02.class, "value02").isImmutable());
		Assert.assertTrue(this.get(Qualifier03.class, "value02").isImmutable());
		Assert.assertTrue(this.get(Qualifier01.class, "value03").isImmutable());
		Assert.assertTrue(this.get(Qualifier02.class, "value03").isImmutable());
		Assert.assertTrue(this.get(Qualifier03.class, "value03").isImmutable());
		Assert.assertTrue(this.get(Qualifier01.class, "value04").isImmutable());
		Assert.assertTrue(this.get(Qualifier02.class, "value04").isImmutable());
		Assert.assertTrue(this.get(Qualifier03.class, "value04").isImmutable());
		Assert.assertFalse(this.get(Qualifier01.class, "value05").isImmutable());
		Assert.assertFalse(this.get(Qualifier02.class, "value05").isImmutable());
		Assert.assertFalse(this.get(Qualifier03.class, "value05").isImmutable());

		Assert.assertFalse(this.get(Qualifier01.class, "value01").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier02.class, "value01").isRequiredIndication());
		Assert.assertFalse(this.get(Qualifier03.class, "value01").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier01.class, "value02").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier02.class, "value02").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier03.class, "value02").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier01.class, "value03").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier02.class, "value03").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier03.class, "value03").isRequiredIndication());
		Assert.assertFalse(this.get(Qualifier01.class, "value04").isRequiredIndication());
		Assert.assertTrue(this.get(Qualifier02.class, "value04").isRequiredIndication());
		Assert.assertFalse(this.get(Qualifier03.class, "value04").isRequiredIndication());
		Assert.assertFalse(this.get(Qualifier01.class, "value05").isRequiredIndication());
		Assert.assertFalse(this.get(Qualifier02.class, "value05").isRequiredIndication());
		Assert.assertFalse(this.get(Qualifier03.class, "value05").isRequiredIndication());

		Assert.assertTrue(this.get(Qualifier01.class, "value01").isVisible());
		Assert.assertFalse(this.get(Qualifier02.class, "value01").isVisible());
		Assert.assertTrue(this.get(Qualifier03.class, "value01").isVisible());
		Assert.assertFalse(this.get(Qualifier01.class, "value02").isVisible());
		Assert.assertFalse(this.get(Qualifier02.class, "value02").isVisible());
		Assert.assertFalse(this.get(Qualifier03.class, "value02").isVisible());
		Assert.assertFalse(this.get(Qualifier01.class, "value03").isVisible());
		Assert.assertFalse(this.get(Qualifier02.class, "value03").isVisible());
		Assert.assertFalse(this.get(Qualifier03.class, "value03").isVisible());
		Assert.assertTrue(this.get(Qualifier01.class, "value04").isVisible());
		Assert.assertFalse(this.get(Qualifier02.class, "value04").isVisible());
		Assert.assertTrue(this.get(Qualifier03.class, "value04").isVisible());
		Assert.assertTrue(this.get(Qualifier01.class, "value05").isVisible());
		Assert.assertTrue(this.get(Qualifier02.class, "value05").isVisible());
		Assert.assertTrue(this.get(Qualifier03.class, "value05").isVisible());
	}
}
