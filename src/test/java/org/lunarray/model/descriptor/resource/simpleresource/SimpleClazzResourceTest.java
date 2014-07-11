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
package org.lunarray.model.descriptor.resource.simpleresource;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity01;
import org.lunarray.model.descriptor.test.domain.SampleEntity02;

/**
 * Tests the simple clazz resource.
 * 
 * @author Pal Hargitai
 * @see SimpleClazzResource
 */
public class SimpleClazzResourceTest {

	/** The resource. */
	private SimpleClazzResource<ModelMarker> resource;

	/** Sets up the test. */
	@Before
	public void init() {
		this.resource = new SimpleClazzResource<ModelMarker>();
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testAddSingleElement() {
		this.resource.addClazz(SampleEntity01.class);
		Assert.assertEquals(1, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testAddSingleElementTwice() {
		this.resource.addClazz(SampleEntity01.class);
		this.resource.addClazz(SampleEntity01.class);
		Assert.assertEquals(1, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testAddSingleValidReturn() {
		this.resource.addClazz(SampleEntity01.class);
		Assert.assertTrue(this.resource.getResources().contains(SampleEntity01.class));
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testAddTwoElements() {
		this.resource.addClazz(SampleEntity01.class);
		this.resource.addClazz(SampleEntity02.class);
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testAddTwoElementsArray() {
		this.resource.addClazzes(SampleEntity01.class, SampleEntity02.class);
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testAddTwoElementsList() {
		final List<Class<? extends ModelMarker>> list = new LinkedList<Class<? extends ModelMarker>>();
		list.add(SampleEntity01.class);
		list.add(SampleEntity02.class);
		this.resource.addClazzes(list);
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testAddTwoElementsTwice() {
		this.resource.addClazz(SampleEntity01.class);
		this.resource.addClazz(SampleEntity01.class);
		this.resource.addClazz(SampleEntity02.class);
		this.resource.addClazz(SampleEntity02.class);
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testAddTwoValidReturn() {
		this.resource.addClazz(SampleEntity01.class);
		this.resource.addClazz(SampleEntity02.class);
		Assert.assertTrue(this.resource.getResources().contains(SampleEntity01.class));
		Assert.assertTrue(this.resource.getResources().contains(SampleEntity02.class));
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConstructTwoElementsArray() {
		this.resource = new SimpleClazzResource<ModelMarker>(SampleEntity01.class, SampleEntity02.class);
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testConstructTwoElementsList() {
		final List<Class<? extends ModelMarker>> list = new LinkedList<Class<? extends ModelMarker>>();
		list.add(SampleEntity01.class);
		list.add(SampleEntity02.class);
		this.resource = new SimpleClazzResource<ModelMarker>(list);
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testNoneValidReturn() {
		Assert.assertTrue(this.resource.getResources().isEmpty());
		Assert.assertEquals(0, this.resource.getResources().size());
	}

	/**
	 * Tests adding a single element.
	 * 
	 * @see SimpleClazzResource#addClazz(Class)
	 */
	@Test
	public void testValidCollection() {
		this.resource.addClazz(SampleEntity01.class);
		Assert.assertNotNull(this.resource.getResources());
	}
}
