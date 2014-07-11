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
package org.lunarray.model.descriptor.converter.registry;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Test;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.lunarray.model.descriptor.registry.Registry;

/**
 * Tests the registry converter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see RegistryConverterTool
 */
public class RegistryConverterToolTest {

	@Test
	public void testConverterTool() throws Exception {
		final RegistryConverterTool<String> tool = new RegistryConverterTool<String>();
		@SuppressWarnings("unchecked")
		final Registry<String> registry = EasyMock.createMock(Registry.class);
		EasyMock.reset(registry);
		final Set<String> converters = new LinkedHashSet<String>();
		converters.add("test01");
		converters.add("test02");
		EasyMock.expect(registry.lookupAll(Converter.class)).andReturn(converters);
		EasyMock.expect(registry.lookup(Converter.class, "test01")).andReturn(new DummyConverter());
		EasyMock.expect(registry.lookup(Converter.class, "test02")).andReturn(new DummyConverter());
		EasyMock.replay(registry);
		tool.setRegistry(registry);
		EasyMock.verify(registry);
	}

	public static class DummyConverter
			implements Converter<String> {

		/** {@inheritDoc} */
		@Override
		public String convertToInstance(final String stringValue) throws ConverterException {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String convertToInstance(final String stringValue, final Locale locale) throws ConverterException {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String convertToInstance(final String stringValue, final Locale locale, final String format) throws ConverterException {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String convertToInstance(final String stringValue, final String format) throws ConverterException {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String convertToString(final String instance) throws ConverterException {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String convertToString(final String instance, final Locale locale) throws ConverterException {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String convertToString(final String instance, final Locale locale, final String format) throws ConverterException {
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public String convertToString(final String instance, final String format) throws ConverterException {
			return null;
		}
	}
}
