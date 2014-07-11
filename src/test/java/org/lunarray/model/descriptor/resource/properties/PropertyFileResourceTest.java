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
package org.lunarray.model.descriptor.resource.properties;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;
import org.lunarray.model.descriptor.resource.ResourceException;
import org.lunarray.model.descriptor.test.domain.ModelMarker;
import org.lunarray.model.descriptor.test.domain.SampleEntity01;
import org.lunarray.model.descriptor.test.domain.SampleEntity02;

/**
 * Tests the simple clazz resource.
 * 
 * @author Pal Hargitai
 * @see PropertiesResource
 */
public class PropertyFileResourceTest {

	/** The resource. */
	private PropertiesResource<ModelMarker> resource;

	/**
	 * Test loading from classpath.
	 * 
	 * @see PropertiesResource#PropertiesResource(String)
	 */
	@Test
	public void testLoadClasspath() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>("/resource.properties");
		final Map<Class<? extends ModelMarker>, String> names = this.resource.resourceNames();
		Assert.assertEquals(2, names.size());
		Assert.assertTrue(names.containsKey(SampleEntity01.class));
		Assert.assertTrue(names.containsKey(SampleEntity02.class));
		Assert.assertEquals("sample01", names.get(SampleEntity01.class));
		Assert.assertEquals("sample02", names.get(SampleEntity02.class));
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Test loading from classpath.
	 * 
	 * @see PropertiesResource#PropertiesResource(String)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLoadClasspathNull() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>((String) null);
	}

	/**
	 * Test loaded properties.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.util.Properties)
	 */
	@Test
	public void testLoaded() throws Exception {
		final Properties properties = new Properties();
		properties.put(SampleEntity01.class.getCanonicalName(), "sample01");
		properties.put(SampleEntity02.class.getCanonicalName(), "sample02");
		this.resource = new PropertiesResource<ModelMarker>(properties);
		final Map<Class<? extends ModelMarker>, String> names = this.resource.resourceNames();
		Assert.assertEquals(2, names.size());
		Assert.assertTrue(names.containsKey(SampleEntity01.class));
		Assert.assertTrue(names.containsKey(SampleEntity02.class));
		Assert.assertEquals("sample01", names.get(SampleEntity01.class));
		Assert.assertEquals("sample02", names.get(SampleEntity02.class));
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Test loaded properties.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.util.Properties)
	 */
	@Test(expected = ResourceException.class)
	public void testLoadedInvalid() throws Exception {
		final Properties properties = new Properties();
		properties.put("sample01", SampleEntity01.class.getCanonicalName());
		properties.put("sample02", SampleEntity02.class.getCanonicalName());
		this.resource = new PropertiesResource<ModelMarker>(properties);
	}

	/**
	 * Test loaded properties.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.util.Properties)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLoadedNull() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>((Properties) null);
	}

	/**
	 * Test loading from file.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.io.File)
	 */
	@Test
	public void testLoadFile() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>(new File("./src/test/resources/resource.properties"));
		final Map<Class<? extends ModelMarker>, String> names = this.resource.resourceNames();
		Assert.assertEquals(2, names.size());
		Assert.assertTrue(names.containsKey(SampleEntity01.class));
		Assert.assertTrue(names.containsKey(SampleEntity02.class));
		Assert.assertEquals("sample01", names.get(SampleEntity01.class));
		Assert.assertEquals("sample02", names.get(SampleEntity02.class));
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Test loading from file.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.io.File)
	 */
	@Test(expected = ResourceException.class)
	public void testLoadFileNonexisting() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>(new File("./src/test/resources/resource2.properties"));
	}

	/**
	 * Test loading from file.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.io.File)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLoadFileNull() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>((File) null);
	}

	/**
	 * Test loading from stream.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.io.InputStream)
	 */
	@Test
	public void testLoadStream() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>(this.getClass().getResourceAsStream("/resource.properties"));
		final Map<Class<? extends ModelMarker>, String> names = this.resource.resourceNames();
		Assert.assertEquals(2, names.size());
		Assert.assertTrue(names.containsKey(SampleEntity01.class));
		Assert.assertTrue(names.containsKey(SampleEntity02.class));
		Assert.assertEquals("sample01", names.get(SampleEntity01.class));
		Assert.assertEquals("sample02", names.get(SampleEntity02.class));
		Assert.assertEquals(2, this.resource.getResources().size());
	}

	/**
	 * Test loading from stream.
	 * 
	 * @see PropertiesResource#PropertiesResource(java.io.InputStream)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testLoadStreamNull() throws Exception {
		this.resource = new PropertiesResource<ModelMarker>((InputStream) null);
	}
}
