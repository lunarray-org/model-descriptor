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
package org.lunarray.model.descriptor.dictionary.composite.registry;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.dictionary.composite.EntityDictionary;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.KeyedEntity01;

/**
 * Tests the composite dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see CompositeRegistryDictionary
 * @see EntityDictionary
 */
public class CompositeRegistryDictionaryTest {

	/** The dictionary. */
	private CompositeRegistryDictionary<String> dictionary;
	/** The entity dictionary mock. */
	private EntityDictionary<KeyedEntity01, String> entityDictionary;
	/** The model. */
	private Model<Object> model;
	/** The registry . */
	private Registry<String> registry;

	/** Sets up the test. */
	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		this.model = SimpleBuilder.createBuilder().resources(new SimpleClazzResource<Object>(KeyedEntity01.class)).build();
		this.entityDictionary = EasyMock.createMock(EntityDictionary.class);
		final List<EntityDictionary<?, ?>> dictionaries = new LinkedList<EntityDictionary<?, ?>>();
		dictionaries.add(this.entityDictionary);
		this.registry = EasyMock.createMock(Registry.class);
		EasyMock.reset(this.entityDictionary, this.registry);
		final Set<String> beans = new HashSet<String>();
		beans.add("test");
		EasyMock.expect(this.registry.lookupAll(EntityDictionary.class)).andReturn(beans);
		EasyMock.expect(this.registry.lookup(EntityDictionary.class, "test")).andReturn(this.entityDictionary);
		EasyMock.expect(this.entityDictionary.getEntityName()).andReturn(this.model.getEntity(KeyedEntity01.class).getName());
		EasyMock.replay(this.entityDictionary, this.registry);
		this.dictionary = new CompositeRegistryDictionary<String>();
		this.dictionary.setRegistry(this.registry);
		EasyMock.verify(this.entityDictionary, this.registry);
		EasyMock.reset(this.entityDictionary, this.registry);
	}

	/**
	 * Looks up.
	 * 
	 * @see Dictionary#lookup(EntityDescriptor)
	 */
	@Test
	public void testLookupAll() throws Exception {
		final EntityDescriptor<KeyedEntity01> d = this.model.getEntity(KeyedEntity01.class);
		final Collection<KeyedEntity01> c = new LinkedList<KeyedEntity01>();
		c.add(new KeyedEntity01());
		EasyMock.expect(this.registry.lookup(EntityDictionary.class, "test")).andReturn(this.entityDictionary);
		EasyMock.expect(this.entityDictionary.lookup()).andReturn(c);
		EasyMock.replay(this.entityDictionary, this.registry);
		Assert.assertEquals(c, this.dictionary.lookup(d));
		EasyMock.verify(this.entityDictionary, this.registry);
	}

	/**
	 * Looks up.
	 * 
	 * @see Dictionary#lookup(KeyedEntityDescriptor, java.io.Serializable)
	 */
	@Test
	public void testLookupKey() throws Exception {
		final EntityDescriptor<KeyedEntity01> d = this.model.getEntity(KeyedEntity01.class);
		@SuppressWarnings("unchecked")
		final KeyedEntityDescriptor<KeyedEntity01, String> k = d.adapt(KeyedEntityDescriptor.class);
		final KeyedEntity01 e = new KeyedEntity01();
		EasyMock.expect(this.registry.lookup(EntityDictionary.class, "test")).andReturn(this.entityDictionary);
		EasyMock.expect(this.entityDictionary.lookup("test")).andReturn(e);
		EasyMock.replay(this.entityDictionary, this.registry);
		Assert.assertEquals(e, this.dictionary.lookup(k, "test"));
		EasyMock.verify(this.entityDictionary, this.registry);
	}
}
