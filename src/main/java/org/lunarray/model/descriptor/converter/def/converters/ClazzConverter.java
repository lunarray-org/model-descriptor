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
package org.lunarray.model.descriptor.converter.def.converters;

import org.lunarray.model.descriptor.converter.exceptions.ConverterException;

/**
 * Converter for {@link Class}.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ClazzConverter
		extends AbstractDelegatingConverter<Class<?>> {

	/**
	 * Default constructor.
	 */
	public ClazzConverter() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	protected Class<?> toInstance(final String stringValue) throws ConverterException {
		try {
			return Class.forName(stringValue);
		} catch (final ClassNotFoundException e) {
			throw new ConverterException(String.format("Could not find class '%s'.", stringValue), e);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected String toString(final Class<?> instance) throws ConverterException {
		return instance.getCanonicalName();
	}
}
