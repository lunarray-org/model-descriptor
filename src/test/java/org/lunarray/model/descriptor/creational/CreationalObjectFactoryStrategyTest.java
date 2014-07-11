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
package org.lunarray.model.descriptor.creational;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity11;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity12;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity13;
import org.lunarray.model.descriptor.creational.domain.CreationalEntityObjectFactory;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;

/**
 * Tests creational strategies.
 * 
 * @author Pal Hargitai
 */
public class CreationalObjectFactoryStrategyTest {

	private Model<Object> model;
	private SimpleClazzResource<Object> resource;

	@Before
	@SuppressWarnings("unchecked")
	public void init() throws Exception {
		this.resource = new SimpleClazzResource<Object>(CreationalEntity11.class, CreationalEntity12.class, CreationalEntity13.class);
		this.model = SimpleBuilder.createBuilder().extensions(new CreationalEntityObjectFactory()).resources(this.resource).build();
	}

	@Test
	public void testConstructorCreation() throws Exception {
		final EntityDescriptor<CreationalEntity11> entity = this.model.getEntity(CreationalEntity11.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity11> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity11 e = constructorStrategy.getInstance();
		Assert.assertEquals("constructor", e.getConstructedBy());
	}

	@Test
	public void testConstructorImplCreation() throws Exception {
		final EntityDescriptor<CreationalEntity12> entity = this.model.getEntity(CreationalEntity12.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity12> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity12 e = constructorStrategy.getInstance();
		Assert.assertEquals("constructor", e.getConstructedBy());
	}

	@Test
	public void testFactoryCreation() throws Exception {
		final EntityDescriptor<CreationalEntity13> entity = this.model.getEntity(CreationalEntity13.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity13> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity13 e = constructorStrategy.getInstance();
		Assert.assertEquals("instanceFactory", e.getConstructedBy());
	}
}
