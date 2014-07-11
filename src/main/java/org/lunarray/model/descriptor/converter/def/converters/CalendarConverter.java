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

import java.util.Calendar;
import java.util.Date;

/**
 * Converter for {@link Calendar}.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class CalendarConverter
		extends AbstractDateConverter<Calendar> {

	/**
	 * Default constructor.
	 */
	public CalendarConverter() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	protected Date toDateType(final Calendar date) {
		return date.getTime();
	}

	/** {@inheritDoc} */
	@Override
	protected Calendar toResultType(final Date date) {
		final Calendar result = Calendar.getInstance();
		result.setTime(date);
		return result;
	}
}
