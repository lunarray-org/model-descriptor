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
package org.lunarray.model.descriptor.objectfactory.registry;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.model.extension.HardExtensionRef;
import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.objectfactory.simple.SimpleObjectFactory;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;

/**
 * Tests the simple object factory.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see SimpleObjectFactory
 */
public class RegistryObjectFactoryTest {

	/** The factory. */
	private RegistryObjectFactory factory;
	/** The registry. */
	private Registry<String> registry;

	/** Setup up the test. */
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		this.factory = new RegistryObjectFactory();
		this.registry = EasyMock.createMock(Registry.class);
		this.factory.setRegistry(new HardExtensionRef<Registry<?>>(this.registry));
		EasyMock.reset(this.registry);
	}

	/**
	 * Test get.
	 * 
	 * @see ObjectFactory#getInstance(Class)
	 */
	@Test
	public void testGet() throws Exception {
		EasyMock.expect(this.registry.lookup(TestClazz.class)).andReturn(new TestClazz());
		EasyMock.replay(this.registry);
		this.factory.getInstance(TestClazz.class);
		EasyMock.verify(this.registry);
	}

	/**
	 * Test invalid.
	 * 
	 * @see ObjectFactory#getInstance(Class)
	 */
	@Test(expected = InstanceException.class)
	public void testGetInvalid() throws Exception {
		EasyMock.expect(this.registry.lookup(TestClazz.class)).andThrow(new RegistryException("Could not retrieve."));
		EasyMock.replay(this.registry);
		this.factory.getInstance(TestClazz.class);
		EasyMock.verify(this.registry);
	}

	public static class TestClazz {
		/**
		 * Default constructor.
		 */
		private TestClazz() {
			// Default constructor.
		}
	}
}
