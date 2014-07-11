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
package org.lunarray.model.descriptor.dictionary.enumeration;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.SampleEnum;

/**
 * Tests the enum dictionary.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see EnumDictionary
 * @see Dictionary
 */
public class EnumDictionaryTest {

	/** The dictionary. */
	private EnumDictionary dictionary;
	/** The model. */
	private Model<Object> model;

	/** Sets up the test. */
	@Before
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>(SampleEnum.class);
		this.dictionary = new EnumDictionary(null);
		this.model = SimpleBuilder.createBuilder().resources(resource).extensions(this.dictionary).build();
	}

	/**
	 * Test lookup.
	 * 
	 * @see Dictionary#lookup(KeyedEntityDescriptor, java.io.Serializable)
	 */
	@Test
	public void testLookup() throws Exception {
		final EntityDescriptor<SampleEnum> ed = this.model.getEntity(SampleEnum.class);
		@SuppressWarnings("unchecked")
		final KeyedEntityDescriptor<SampleEnum, String> keyed = ed.adapt(KeyedEntityDescriptor.class);
		Assert.assertEquals(SampleEnum.TEST02, this.dictionary.lookup(keyed, SampleEnum.TEST02.name()));
		Assert.assertEquals(SampleEnum.TEST01, this.dictionary.lookup(keyed, SampleEnum.TEST01.name()));
	}

	/**
	 * Test lookup.
	 * 
	 * @see Dictionary#lookup(EntityDescriptor)
	 */
	@Test
	public void testLookupAll() throws Exception {
		final EntityDescriptor<SampleEnum> ed = this.model.getEntity(SampleEnum.class);
		final Collection<SampleEnum> c = this.dictionary.lookup(ed);
		Assert.assertEquals(3, c.size());
		Assert.assertTrue(c.contains(SampleEnum.TEST01));
		Assert.assertTrue(c.contains(SampleEnum.TEST02));
		Assert.assertTrue(c.contains(SampleEnum.TEST03));
	}
}
