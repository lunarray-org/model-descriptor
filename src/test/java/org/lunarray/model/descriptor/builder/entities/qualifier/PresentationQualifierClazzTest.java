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

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.Qualifier01;
import org.lunarray.model.descriptor.test.domain.Qualifier02;
import org.lunarray.model.descriptor.test.domain.Qualifier03;
import org.lunarray.model.descriptor.test.domain.SampleEntity19;
import org.lunarray.model.descriptor.test.domain.SampleEntity20;

public class PresentationQualifierClazzTest {

	private PresentationEntityDescriptor<SampleEntity19> descriptor19;
	private PresentationEntityDescriptor<SampleEntity20> descriptor20;
	private Model<ModelMarker> model;

	public <Type> void assertQualifierFieldSame(final PresentationEntityDescriptor<Type> presentation, final Class<?> qualifier,
			final String fieldName, final boolean same) {
		if (same) {
			Assert.assertSame(presentation.getProperty(fieldName), this.get(presentation).getQualifierProperty(fieldName, qualifier));
			Assert.assertSame(presentation.getProperty(fieldName, String.class),
					this.get(presentation).getQualifierProperty(fieldName, qualifier, String.class));
			Assert.assertTrue(this.get(presentation).getProperties(qualifier)
					.contains(this.get(presentation).getQualifierProperty(fieldName, qualifier)));
		} else {
			Assert.assertNotSame(presentation.getProperty(fieldName), this.get(presentation).getQualifierProperty(fieldName, qualifier));
			Assert.assertNotSame(presentation.getProperty(fieldName, String.class),
					this.get(presentation).getQualifierProperty(fieldName, qualifier, String.class));
			Assert.assertFalse(this.get(presentation).getProperties(qualifier).contains(presentation.getProperty(fieldName)));
		}
	}

	@SuppressWarnings("unchecked")
	public <Type> QualifierEntityDescriptor<Type> get(final PresentationEntityDescriptor<Type> presentation) {
		return presentation.adapt(QualifierEntityDescriptor.class);
	}

	@SuppressWarnings("unchecked")
	public <Type> PresentationEntityDescriptor<Type> get(final PresentationEntityDescriptor<Type> presentation, final Class<?> qualifier) {
		final QualifierEntityDescriptor<Type> qualifierDescriptor = presentation.adapt(QualifierEntityDescriptor.class);
		final QualifierEntityDescriptor<Type> qualifierClazzDescriptor = qualifierDescriptor.getQualifierEntity(qualifier);
		final PresentationEntityDescriptor<Type> result = qualifierClazzDescriptor.adapt(PresentationEntityDescriptor.class);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity19.class,
				SampleEntity20.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(resource).build();
		this.descriptor19 = this.model.getEntity(SampleEntity19.class).adapt(PresentationEntityDescriptor.class);
		this.descriptor20 = this.model.getEntity(SampleEntity20.class).adapt(PresentationEntityDescriptor.class);
	}

	/** Tests QCR-01, QCR-04, QCR-05, QCR-06. */
	@Test
	public void testQualifierClazz() {
		Assert.assertTrue(this.descriptor19.isVisible());
		Assert.assertTrue(this.get(this.descriptor19, Qualifier01.class).isVisible());
		Assert.assertFalse(this.get(this.descriptor19, Qualifier02.class).isVisible());
		Assert.assertFalse(this.descriptor20.isVisible());
		Assert.assertFalse(this.get(this.descriptor20, Qualifier01.class).isVisible());
		Assert.assertTrue(this.get(this.descriptor20, Qualifier02.class).isVisible());

		Assert.assertNull(this.descriptor19.getResourceBundle());
		Assert.assertNull(this.get(this.descriptor19, Qualifier01.class).getResourceBundle());
		Assert.assertNotNull(this.get(this.descriptor19, Qualifier02.class).getResourceBundle());
		Assert.assertNotNull(this.descriptor20.getResourceBundle());
		Assert.assertNotNull(this.get(this.descriptor20, Qualifier01.class).getResourceBundle());
		Assert.assertNotNull(this.get(this.descriptor20, Qualifier02.class).getResourceBundle());
		Assert.assertSame(this.descriptor20.getResourceBundle(), this.get(this.descriptor20, Qualifier01.class).getResourceBundle());
		Assert.assertNotSame(this.descriptor20.getResourceBundle(), this.get(this.descriptor20, Qualifier02.class).getResourceBundle());

		Assert.assertEquals("SampleEntity19", this.descriptor19.getDescription());
		Assert.assertEquals("SampleEntity19", this.get(this.descriptor19, Qualifier01.class).getDescription());
		Assert.assertEquals("Entity 15", this.get(this.descriptor19, Qualifier02.class).getDescription());
		Assert.assertEquals("Entity 15", this.descriptor20.getDescription());
		Assert.assertEquals("Entity 15", this.get(this.descriptor20, Qualifier01.class).getDescription());
		Assert.assertEquals("Entity 20", this.get(this.descriptor20, Qualifier02.class).getDescription());

		Assert.assertEquals("SampleEntity19", this.descriptor19.getDescriptionKey());
		Assert.assertEquals("SampleEntity19", this.get(this.descriptor19, Qualifier01.class).getDescriptionKey());
		Assert.assertEquals("entity15", this.get(this.descriptor19, Qualifier02.class).getDescriptionKey());
		Assert.assertEquals("entity15", this.descriptor20.getDescriptionKey());
		Assert.assertEquals("entity15", this.get(this.descriptor20, Qualifier01.class).getDescriptionKey());
		Assert.assertEquals("entity20", this.get(this.descriptor20, Qualifier02.class).getDescriptionKey());

		Assert.assertNull(this.descriptor19.getResourceBundle(Locale.GERMAN));
		Assert.assertNull(this.get(this.descriptor19, Qualifier01.class).getResourceBundle(Locale.GERMAN));
		Assert.assertNotNull(this.get(this.descriptor19, Qualifier02.class).getResourceBundle(Locale.GERMAN));
		Assert.assertNotNull(this.descriptor20.getResourceBundle(Locale.GERMAN));
		Assert.assertNotNull(this.get(this.descriptor20, Qualifier01.class).getResourceBundle(Locale.GERMAN));
		Assert.assertNotNull(this.get(this.descriptor20, Qualifier02.class).getResourceBundle(Locale.GERMAN));
		Assert.assertSame(this.descriptor20.getResourceBundle(Locale.GERMAN), this.get(this.descriptor20, Qualifier01.class)
				.getResourceBundle(Locale.GERMAN));
		Assert.assertNotSame(this.descriptor20.getResourceBundle(Locale.GERMAN), this.get(this.descriptor20, Qualifier02.class)
				.getResourceBundle(Locale.GERMAN));

		Assert.assertEquals("SampleEntity19", this.descriptor19.getDescription(Locale.GERMAN));
		Assert.assertEquals("SampleEntity19", this.get(this.descriptor19, Qualifier01.class).getDescription(Locale.GERMAN));
		Assert.assertEquals("Entiteit 15", this.get(this.descriptor19, Qualifier02.class).getDescription(Locale.GERMAN));
		Assert.assertEquals("Entiteit 15", this.descriptor20.getDescription(Locale.GERMAN));
		Assert.assertEquals("Entiteit 15", this.get(this.descriptor20, Qualifier01.class).getDescription(Locale.GERMAN));
		Assert.assertEquals("Entity 20", this.get(this.descriptor20, Qualifier02.class).getDescription(Locale.GERMAN));

		final Set<Class<?>> qualifiers = new HashSet<Class<?>>();
		qualifiers.add(Qualifier01.class);
		qualifiers.add(Qualifier02.class);
		qualifiers.add(Qualifier03.class);
		Assert.assertEquals(qualifiers, this.descriptor20.adapt(QualifierEntityDescriptor.class).getQualifiers());
	}
}
