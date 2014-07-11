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
package org.lunarray.model.descriptor.objectfactory.simple;

import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;

/**
 * Tests the simple object factory.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see SimpleObjectFactory
 */
public class SimpleObjectFactoryTest {

	/** The factory. */
	private SimpleObjectFactory factory;

	/** Setup up the test. */
	@Before
	public void init() {
		this.factory = new SimpleObjectFactory();
	}

	/**
	 * Test get.
	 * 
	 * @see ObjectFactory#getInstance(Class)
	 */
	@Test
	public void testGet() throws Exception {
		this.factory.getInstance(Object.class);
	}

	/**
	 * Test invalid.
	 * 
	 * @see ObjectFactory#getInstance(Class)
	 */
	@Test(expected = InstanceException.class)
	public void testGetInvalid() throws Exception {
		this.factory.getInstance(TestClazz.class);
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
