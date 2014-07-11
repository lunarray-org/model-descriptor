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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.SampleEntity25;

public class SimpleOperationTest {

	private Model<Object> model;

	@Before
	public void init() throws Exception {
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>();
		resource.addClazz(SampleEntity25.class);
		this.model = SimpleBuilder.createBuilder().resources(resource).build();
	}

	@Test
	public void testOperation() {
		Assert.assertEquals(4, this.model.getEntity(SampleEntity25.class).getOperations().size());
		Assert.assertEquals(4, this.model.getEntity(SampleEntity25.class).getMembers().size());
	}
}
