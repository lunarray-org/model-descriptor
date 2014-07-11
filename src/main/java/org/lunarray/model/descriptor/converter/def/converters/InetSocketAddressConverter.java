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

import java.net.InetSocketAddress;

import org.lunarray.model.descriptor.converter.exceptions.ConverterException;

/**
 * Converter for {@link InetSocketAddress}.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class InetSocketAddressConverter
		extends AbstractDefaultConverter<InetSocketAddress> {

	/** The address converter. */
	private InetAddressConverter inetAddressConverter;
	/** The port converter. */
	private IntegerConverter integerConverter;

	/**
	 * Default constructor.
	 */
	public InetSocketAddressConverter() {
		super();
		this.inetAddressConverter = new InetAddressConverter();
		this.integerConverter = new IntegerConverter();
	}

	/**
	 * Gets the value for the inetAddressConverter field.
	 * 
	 * @return The value for the inetAddressConverter field.
	 */
	public InetAddressConverter getInetAddressConverter() {
		return this.inetAddressConverter;
	}

	/**
	 * Gets the value for the integerConverter field.
	 * 
	 * @return The value for the integerConverter field.
	 */
	public IntegerConverter getIntegerConverter() {
		return this.integerConverter;
	}

	/**
	 * Sets a new value for the inetAddressConverter field.
	 * 
	 * @param inetAddressConverter
	 *            The new value for the inetAddressConverter field.
	 */
	public void setInetAddressConverter(final InetAddressConverter inetAddressConverter) {
		this.inetAddressConverter = inetAddressConverter;
	}

	/**
	 * Sets a new value for the integerConverter field.
	 * 
	 * @param integerConverter
	 *            The new value for the integerConverter field.
	 */
	public void setIntegerConverter(final IntegerConverter integerConverter) {
		this.integerConverter = integerConverter;
	}

	/** {@inheritDoc} */
	@Override
	protected InetSocketAddress toInstance(final String stringValue) throws ConverterException {
		final String[] split = stringValue.split(":");
		return new InetSocketAddress(this.inetAddressConverter.convertToInstance(split[0]),
				this.integerConverter.convertToInstance(split[1]));
	}
}
