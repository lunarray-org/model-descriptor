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

/**
 * Converter for {@link Short}.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ShortConverter
		extends AbstractNumberConverter<Short> {

	/**
	 * Default constructor.
	 */
	public ShortConverter() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	protected Number toNumberType(final Short number) {
		return number;
	}

	/** {@inheritDoc} */
	@Override
	protected Short toResultType(final Number number) {
		Short result;
		if (number instanceof Short) {
			result = (Short) number;
		} else {
			result = Short.valueOf(number.shortValue());
		}
		return result;
	}
}
