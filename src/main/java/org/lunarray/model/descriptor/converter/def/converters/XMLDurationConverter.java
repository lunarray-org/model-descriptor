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

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.lunarray.model.descriptor.converter.exceptions.ConverterException;

/**
 * Converter for {@link Duration}.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class XMLDurationConverter
		extends AbstractDelegatingConverter<Duration> {

	/** The data type factory. */
	private DatatypeFactory datatypeFactory;

	/**
	 * Default constructor.
	 */
	public XMLDurationConverter() {
		super();
		try {
			this.datatypeFactory = DatatypeFactory.newInstance();
		} catch (final DatatypeConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Gets the value for the datatypeFactory field.
	 * 
	 * @return The value for the datatypeFactory field.
	 */
	public DatatypeFactory getDatatypeFactory() {
		return this.datatypeFactory;
	}

	/**
	 * Sets a new value for the datatypeFactory field.
	 * 
	 * @param datatypeFactory
	 *            The new value for the datatypeFactory field.
	 */
	public void setDatatypeFactory(final DatatypeFactory datatypeFactory) {
		this.datatypeFactory = datatypeFactory;
	}

	/** {@inheritDoc} */
	@Override
	protected Duration toInstance(final String stringValue) throws ConverterException {
		return this.datatypeFactory.newDuration(stringValue);
	}

	/** {@inheritDoc} */
	@Override
	protected String toString(final Duration instance) throws ConverterException {
		return instance.toString();
	}
}
