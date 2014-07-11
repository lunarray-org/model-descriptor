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
package org.lunarray.model.descriptor.builder;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity22;
import org.lunarray.model.descriptor.test.domain.SampleEntity23;
import org.lunarray.model.descriptor.test.domain.SampleEntity24;

/**
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class ResolverStrategyTest {

	private Model<ModelMarker> model;
	private Resource<Class<? extends ModelMarker>> resource;

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		this.resource = new SimpleClazzResource<ModelMarker>(SampleEntity24.class, SampleEntity23.class, SampleEntity22.class);
		final PresQualBuilder<ModelMarker> builder = PresQualBuilder.createBuilder();
		this.model = builder.resources(this.resource).build();
	}

	@Test
	public void testAnnotationResolving() {
		Assert.assertEquals("test01", this.model.getEntity(SampleEntity22.class).adapt(PresentationEntityDescriptor.class).getDescription());
		Assert.assertEquals("test02", this.model.getEntity(SampleEntity23.class).adapt(PresentationEntityDescriptor.class).getDescription());
		Assert.assertEquals("test02", this.model.getEntity(SampleEntity24.class).adapt(PresentationEntityDescriptor.class).getDescription());
		Assert.assertFalse(this.model.getEntity(SampleEntity22.class).adapt(PresentationEntityDescriptor.class).isVisible());
		Assert.assertFalse(this.model.getEntity(SampleEntity23.class).adapt(PresentationEntityDescriptor.class).isVisible());
		Assert.assertFalse(this.model.getEntity(SampleEntity24.class).adapt(PresentationEntityDescriptor.class).isVisible());
	}
}
