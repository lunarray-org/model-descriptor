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
package org.lunarray.model.descriptor.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.model.extension.HardExtensionRef;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.objectfactory.simple.SimpleObjectFactory;
import org.lunarray.model.descriptor.registry.Registry;

/**
 * Tests the extension util.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see ExtensionUtil
 */
public class ExtensionUtilTest {

	/**
	 * Test loading of extensions.
	 * 
	 * @see ExtensionUtil#loadExtensions(Iterable, Iterable)
	 */
	@Test
	public void testLoadExtensionRefs() {
		final List<Extension> extensions = new LinkedList<Extension>();
		final List<ExtensionRef<?>> extensionRefs = new LinkedList<ExtensionRef<?>>();
		final ObjectFactory extension = new SimpleObjectFactory();
		extensionRefs.add(new HardExtensionRef<Extension>(extension));
		Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> result;
		result = ExtensionUtil.loadExtensions(extensionRefs, extensions);
		Assert.assertTrue(result.containsKey(ObjectFactory.class));
		Assert.assertEquals(extension, result.get(ObjectFactory.class).get());
	}

	/**
	 * Test loading of extensions.
	 * 
	 * @see ExtensionUtil#loadExtensions(Iterable, Iterable)
	 */
	@Test
	public void testLoadExtensionRefsIncludingRegistry() throws Exception {
		final List<Extension> extensionsList = new LinkedList<Extension>();
		final List<ExtensionRef<?>> extensionRefsList = new LinkedList<ExtensionRef<?>>();
		final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> result;
		@SuppressWarnings("unchecked")
		final Registry<String> registry = EasyMock.createMock(Registry.class);
		extensionRefsList.add(new HardExtensionRef<Extension>(registry));
		final Map<String, Extension> extensions = new HashMap<String, Extension>();
		extensions.put("objectFactory", new SimpleObjectFactory());
		EasyMock.reset(registry);
		EasyMock.expect(registry.lookupAll(Extension.class)).andReturn(extensions.keySet());
		EasyMock.expect(registry.lookup(Extension.class, "objectFactory")).andReturn(extensions.get("objectFactory")).anyTimes();
		EasyMock.expect(registry.lookup(ObjectFactory.class, "objectFactory")).andReturn((ObjectFactory) extensions.get("objectFactory"))
				.anyTimes();
		EasyMock.replay(registry);
		result = ExtensionUtil.loadExtensions(extensionRefsList, extensionsList);
		EasyMock.verify(registry);
		Assert.assertTrue(result.containsKey(ObjectFactory.class));
		Assert.assertEquals(extensions.get("objectFactory"), result.get(ObjectFactory.class).get());
	}

	/**
	 * Test loading of extensions.
	 * 
	 * @see ExtensionUtil#loadExtensions(Iterable, Iterable)
	 */
	@Test
	public void testLoadExtensions() {
		final List<Extension> extensions = new LinkedList<Extension>();
		final List<ExtensionRef<?>> extensionRefs = new LinkedList<ExtensionRef<?>>();
		final ObjectFactory extension = new SimpleObjectFactory();
		extensions.add(extension);
		Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> result;
		result = ExtensionUtil.loadExtensions(extensionRefs, extensions);
		Assert.assertTrue(result.containsKey(ObjectFactory.class));
		Assert.assertEquals(extension, result.get(ObjectFactory.class).get());
	}

	/**
	 * Test loading of extensions.
	 * 
	 * @see ExtensionUtil#loadExtensions(Iterable, Iterable)
	 */
	@Test
	public void testLoadExtensionsIncludingRegistry() throws Exception {
		final List<Extension> extensionsList = new LinkedList<Extension>();
		final List<ExtensionRef<?>> extensionRefsList = new LinkedList<ExtensionRef<?>>();
		final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> result;
		@SuppressWarnings("unchecked")
		final Registry<String> registry = EasyMock.createMock(Registry.class);
		extensionsList.add(registry);
		final Map<String, Extension> extensions = new HashMap<String, Extension>();
		extensions.put("objectFactory", new SimpleObjectFactory());
		EasyMock.reset(registry);
		EasyMock.expect(registry.lookupAll(Extension.class)).andReturn(extensions.keySet());
		EasyMock.expect(registry.lookup(Extension.class, "objectFactory")).andReturn(extensions.get("objectFactory")).anyTimes();
		EasyMock.expect(registry.lookup(ObjectFactory.class, "objectFactory")).andReturn((ObjectFactory) extensions.get("objectFactory"))
				.anyTimes();
		EasyMock.replay(registry);
		result = ExtensionUtil.loadExtensions(extensionRefsList, extensionsList);
		EasyMock.verify(registry);
		Assert.assertTrue(result.containsKey(ObjectFactory.class));
		Assert.assertEquals(extensions.get("objectFactory"), result.get(ObjectFactory.class).get());
	}

	/**
	 * Test loading of extensions.
	 * 
	 * @see ExtensionUtil#processRegistry(Map, ExtensionRef)
	 */
	@Test
	public void testProcessRegistry() throws Exception {
		final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> result = new HashMap<Class<? extends Extension>, ExtensionRef<? extends Extension>>();
		@SuppressWarnings("unchecked")
		final Registry<String> registry = EasyMock.createMock(Registry.class);
		final Map<String, Extension> extensions = new HashMap<String, Extension>();
		extensions.put("objectFactory", new SimpleObjectFactory());
		EasyMock.reset(registry);
		EasyMock.expect(registry.lookupAll(Extension.class)).andReturn(extensions.keySet());
		EasyMock.expect(registry.lookup(Extension.class, "objectFactory")).andReturn(extensions.get("objectFactory")).anyTimes();
		EasyMock.expect(registry.lookup(ObjectFactory.class, "objectFactory")).andReturn((ObjectFactory) extensions.get("objectFactory"))
				.anyTimes();
		EasyMock.replay(registry);
		ExtensionUtil.processRegistry(result, new HardExtensionRef<Registry<String>>(registry));
		EasyMock.verify(registry);
		Assert.assertTrue(result.containsKey(ObjectFactory.class));
		Assert.assertEquals(extensions.get("objectFactory"), result.get(ObjectFactory.class).get());
	}
}
