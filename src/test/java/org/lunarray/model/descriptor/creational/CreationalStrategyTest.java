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
import org.lunarray.model.descriptor.creational.domain.CreationalEntity01;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity02;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity03;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity04;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity05;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity06;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity07;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity08;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity09;
import org.lunarray.model.descriptor.creational.domain.CreationalEntity10;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;

/**
 * Tests creational strategies.
 * 
 * @author Pal Hargitai
 */
public class CreationalStrategyTest {

	private Model<Object> model;
	private SimpleClazzResource<Object> resource;

	@Before
	@SuppressWarnings("unchecked")
	public void init() throws Exception {
		this.resource = new SimpleClazzResource<Object>(CreationalEntity01.class, CreationalEntity02.class, CreationalEntity03.class,
				CreationalEntity04.class, CreationalEntity05.class, CreationalEntity06.class, CreationalEntity07.class,
				CreationalEntity08.class, CreationalEntity09.class, CreationalEntity10.class);
		this.model = SimpleBuilder.createBuilder().resources(this.resource).build();
	}

	@Test
	public void testConstructorCreation() throws Exception {
		final EntityDescriptor<CreationalEntity01> entity = this.model.getEntity(CreationalEntity01.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity01> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity01 e = constructorStrategy.getInstance();
		Assert.assertEquals("constructor", e.getConstructedBy());
	}

	@Test
	public void testConstructorCreationFactory() throws Exception {
		final EntityDescriptor<CreationalEntity05> entity = this.model.getEntity(CreationalEntity05.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity05> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity05 e = constructorStrategy.getInstance();
		Assert.assertEquals("staticFactory", e.getConstructedBy());
	}

	@Test
	public void testConstructorCreationImpl() throws Exception {
		final EntityDescriptor<CreationalEntity03> entity = this.model.getEntity(CreationalEntity03.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity03> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity03 e = constructorStrategy.getInstance();
		Assert.assertEquals("constructor", e.getConstructedBy());
	}

	@Test
	public void testFactoryMethodCreation() throws Exception {
		final EntityDescriptor<CreationalEntity02> entity = this.model.getEntity(CreationalEntity02.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity02> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity02 e = constructorStrategy.getInstance();
		Assert.assertEquals("factoryMethod", e.getConstructedBy());
	}

	@Test
	public void testFactoryMethodCreationFactory() throws Exception {
		final EntityDescriptor<CreationalEntity06> entity = this.model.getEntity(CreationalEntity06.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity06> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity06 e = constructorStrategy.getInstance();
		Assert.assertEquals("instanceFactory", e.getConstructedBy());
	}

	@Test
	public void testFactoryMethodCreationFactoryInstance() throws Exception {
		final EntityDescriptor<CreationalEntity10> entity = this.model.getEntity(CreationalEntity10.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity10> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity10 e = constructorStrategy.getInstance();
		Assert.assertEquals("instanceFactory", e.getConstructedBy());
	}

	@Test
	public void testFactoryMethodCreationImpl() throws Exception {
		final EntityDescriptor<CreationalEntity04> entity = this.model.getEntity(CreationalEntity04.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity04> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity04 e = constructorStrategy.getInstance();
		Assert.assertEquals("factoryMethod", e.getConstructedBy());
	}

	@Test
	public void testFactoryMethodCreationImplFactory() throws Exception {
		final EntityDescriptor<CreationalEntity07> entity = this.model.getEntity(CreationalEntity07.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity07> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity07 e = constructorStrategy.getInstance();
		Assert.assertEquals("staticFactory", e.getConstructedBy());
	}

	@Test
	public void testFactoryMethodCreationImplFactoryInstance() throws Exception {
		final EntityDescriptor<CreationalEntity09> entity = this.model.getEntity(CreationalEntity09.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity09> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity09 e = constructorStrategy.getInstance();
		Assert.assertEquals("instanceFactory", e.getConstructedBy());
	}

	@Test
	public void testFactoryMethodCreationImplFactoryStatic() throws Exception {
		final EntityDescriptor<CreationalEntity08> entity = this.model.getEntity(CreationalEntity08.class);
		Assert.assertEquals(1, entity.creationalStrategies().size());
		final CreationalStrategy<CreationalEntity08> constructorStrategy = entity.creationalStrategies().iterator().next();
		final CreationalEntity08 e = constructorStrategy.getInstance();
		Assert.assertEquals("instanceFactory", e.getConstructedBy());
	}
}
