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
package org.lunarray.model.descriptor.dictionary.composite.simple;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.dictionary.PaginatedDictionary;
import org.lunarray.model.descriptor.dictionary.composite.PaginatedEntityDictionary;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.KeyedEntity01;

/**
 * Tests the composite dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see CompositePaginatedDictionary
 * @see PaginatedEntityDictionary
 */
public class CompositePaginatedDictionaryTest {

	/** The dictionary. */
	private CompositePaginatedDictionary dictionary;
	/** The entity dictionary mock. */
	private PaginatedEntityDictionary<KeyedEntity01, String> entityDictionary;
	/** The model. */
	private Model<Object> model;

	/** Sets up the test. */
	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		this.model = SimpleBuilder.createBuilder().resources(new SimpleClazzResource<Object>(KeyedEntity01.class)).build();
		this.entityDictionary = EasyMock.createMock(PaginatedEntityDictionary.class);
		final List<PaginatedEntityDictionary<?, ?>> dictionaries = new LinkedList<PaginatedEntityDictionary<?, ?>>();
		dictionaries.add(this.entityDictionary);
		EasyMock.reset(this.entityDictionary);
		EasyMock.expect(this.entityDictionary.getEntityName()).andReturn(this.model.getEntity(KeyedEntity01.class).getName());
		EasyMock.replay(this.entityDictionary);
		this.dictionary = new CompositePaginatedDictionary(dictionaries);
		EasyMock.verify(this.entityDictionary);
		EasyMock.reset(this.entityDictionary);
	}

	/**
	 * Looks up.
	 * 
	 * @see PaginatedDictionary#lookupPaginated(EntityDescriptor, int, int)
	 */
	@Test
	public void testLookupPage() throws Exception {
		final EntityDescriptor<KeyedEntity01> d = this.model.getEntity(KeyedEntity01.class);
		final KeyedEntity01 e = new KeyedEntity01();
		final List<KeyedEntity01> c = new LinkedList<KeyedEntity01>();
		c.add(e);
		EasyMock.expect(this.entityDictionary.lookup(0, 10)).andReturn(c);
		EasyMock.replay(this.entityDictionary);
		Assert.assertEquals(c, this.dictionary.lookupPaginated(d, 0, 10));
		EasyMock.verify(this.entityDictionary);
	}

	/**
	 * Looks up.
	 * 
	 * @see PaginatedDictionary#lookupTotals(EntityDescriptor)
	 */
	@Test
	public void testLookupSize() throws Exception {
		final EntityDescriptor<KeyedEntity01> d = this.model.getEntity(KeyedEntity01.class);
		EasyMock.expect(this.entityDictionary.total()).andReturn(10);
		EasyMock.replay(this.entityDictionary);
		Assert.assertEquals(10, this.dictionary.lookupTotals(d));
		EasyMock.verify(this.entityDictionary);
	}
}
