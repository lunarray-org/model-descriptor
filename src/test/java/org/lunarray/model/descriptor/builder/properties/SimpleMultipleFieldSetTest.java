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
package org.lunarray.model.descriptor.builder.properties;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.builder.annotation.simple.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.property.CollectionPropertyDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity10;
import org.lunarray.model.descriptor.test.domain.SampleEntity11;

public class SimpleMultipleFieldSetTest {

	private EntityDescriptor<SampleEntity10> clazz;
	private CollectionPropertyDescriptor<String, List<String>, SampleEntity10> descriptor;
	private Model<ModelMarker> model;
	private SampleEntity10 sample;
	private final Set<String> testSet = new HashSet<String>();

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		final Resource<Class<? extends ModelMarker>> resource = new SimpleClazzResource<ModelMarker>(SampleEntity10.class,
				SampleEntity11.class);
		final Builder<Class<? extends ModelMarker>, ModelMarker, ModelImpl<ModelMarker>, ?> builder = SimpleBuilder.createBuilder();
		this.model = builder.resources(resource).build();
		this.clazz = this.model.getEntity(SampleEntity10.class);
		this.descriptor = this.clazz.getProperty("simpleStringSet").adapt(CollectionPropertyDescriptor.class);

		this.sample = new SampleEntity10();
		this.sample.getSimpleStringSet().add("test01");
		this.sample.getSimpleStringSet().add("test02");
		this.sample.getSimpleStringSet().add("test03");
		this.testSet.add("test01");
		this.testSet.add("test02");
		this.testSet.add("test03");
	}

	/** Tests FR-16. */
	@Test
	public void testAddMultiple() throws Exception {
		final List<String> addList = new LinkedList<String>();
		addList.add("test05");
		addList.add("test06");
		Assert.assertTrue(this.testSet.addAll(addList));
		Assert.assertTrue(this.descriptor.addAllValues(this.sample, addList));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-16. */
	@Test
	public void testAddSingle() throws Exception {
		Assert.assertTrue(this.testSet.add("test04"));
		Assert.assertTrue(this.descriptor.addValue(this.sample, "test04"));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-22. */
	@Test
	public void testClear() throws Exception {
		this.testSet.clear();
		this.descriptor.clearValues(this.sample);
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-21. */
	@Test
	public void testCollectionEmpty() throws Exception {
		this.testSet.clear();
		this.sample.getSimpleStringSet().clear();
		Assert.assertTrue(this.testSet.isEmpty());
		Assert.assertTrue(this.sample.getSimpleStringSet().isEmpty());
		Assert.assertTrue(this.descriptor.isValueEmpty(this.sample));
	}

	/** Tests FR-21. */
	@Test
	public void testCollectionNotEmpty() throws Exception {
		Assert.assertFalse(this.testSet.isEmpty());
		Assert.assertFalse(this.sample.getSimpleStringSet().isEmpty());
		Assert.assertFalse(this.descriptor.isValueEmpty(this.sample));
	}

	/** Tests FR-19. */
	@Test
	public void testContainsMultiple() throws Exception {
		final List<String> addList = new LinkedList<String>();
		addList.add("test01");
		addList.add("test02");
		Assert.assertTrue(this.testSet.containsAll(addList));
		Assert.assertTrue(this.descriptor.containsAllValues(this.sample, addList));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-19. */
	@Test
	public void testContainsMultipleFail() throws Exception {
		final List<String> addList = new LinkedList<String>();
		addList.add("test05");
		addList.add("test06");
		Assert.assertFalse(this.testSet.containsAll(addList));
		Assert.assertFalse(this.descriptor.containsAllValues(this.sample, addList));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-19. */
	@Test
	public void testContainsSingle() throws Exception {
		Assert.assertTrue(this.testSet.contains("test02"));
		Assert.assertTrue(this.descriptor.containsValue(this.sample, "test02"));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-19. */
	@Test
	public void testContainsSingleFail() throws Exception {
		Assert.assertFalse(this.testSet.contains("test04"));
		Assert.assertFalse(this.descriptor.containsValue(this.sample, "test04"));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-20. */
	@Test
	public void testCorrectClazz() throws Exception {
		Assert.assertEquals(String.class, this.clazz.getProperty("simpleStringList").adapt(CollectionPropertyDescriptor.class)
				.getCollectionType());
		Assert.assertEquals(String.class, this.clazz.getProperty("simpleStringSet").adapt(CollectionPropertyDescriptor.class)
				.getCollectionType());
		Assert.assertEquals(String.class, this.clazz.getProperty("testList.testList").adapt(CollectionPropertyDescriptor.class)
				.getCollectionType());
	}

	/** Base test. This is the assumption for the other tests. */
	@Test
	public void testEquality() throws Exception {
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-24. */
	@Test
	public void testIterable() throws Exception {
		final Iterator<String> it1 = this.testSet.iterator();
		for (final String it : this.descriptor.iteratable(this.sample)) {
			Assert.assertTrue(it1.hasNext());
			Assert.assertEquals(it1.next(), it);
		}
		Assert.assertFalse(it1.hasNext());
	}

	/** Tests FR-24. */
	@Test
	public void testIterator() throws Exception {
		final Iterator<String> it1 = this.testSet.iterator();
		final Iterator<String> it2 = this.descriptor.iterator(this.sample);
		while (it1.hasNext() && it2.hasNext()) {
			Assert.assertEquals(it1.next(), it2.next());
			Assert.assertFalse(it1.hasNext() ^ it2.hasNext());
		}
	}

	/** Tests FR-17. */
	@Test
	public void testRemoveMultiple() throws Exception {
		final List<String> addList = new LinkedList<String>();
		addList.add("test01");
		addList.add("test02");
		Assert.assertTrue(this.testSet.removeAll(addList));
		Assert.assertTrue(this.descriptor.removeAllValues(this.sample, addList));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-17. */
	@Test
	public void testRemoveMultipleFail() throws Exception {
		final List<String> addList = new LinkedList<String>();
		addList.add("test05");
		addList.add("test06");
		Assert.assertFalse(this.testSet.removeAll(addList));
		Assert.assertFalse(this.descriptor.removeAllValues(this.sample, addList));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-17. */
	@Test
	public void testRemoveSingle() throws Exception {
		Assert.assertTrue(this.testSet.remove("test02"));
		Assert.assertTrue(this.descriptor.removeValue(this.sample, "test02"));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-17. */
	@Test
	public void testRemoveSingleFail() throws Exception {
		Assert.assertFalse(this.testSet.remove("test04"));
		Assert.assertFalse(this.descriptor.removeValue(this.sample, "test04"));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-18. */
	@Test
	public void testRetainMultiple() throws Exception {
		final List<String> addList = new LinkedList<String>();
		addList.add("test01");
		addList.add("test02");
		Assert.assertTrue(this.testSet.retainAll(addList));
		Assert.assertTrue(this.descriptor.retainAllValues(this.sample, addList));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-18. */
	@Test
	public void testRetainMultipleFail() throws Exception {
		final List<String> addList = new LinkedList<String>();
		addList.add("test05");
		addList.add("test06");
		Assert.assertTrue(this.testSet.retainAll(addList));
		Assert.assertTrue(this.descriptor.retainAllValues(this.sample, addList));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}

	/** Tests FR-23. */
	@Test
	public void testSize() throws Exception {
		Assert.assertEquals(3, this.testSet.size());
		Assert.assertEquals(3, this.descriptor.valueSize(this.sample));
		Assert.assertTrue(this.testSet.equals(this.sample.getSimpleStringSet()));
	}
}
