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
package org.lunarray.model.descriptor.builder.annotation.base.builders.context;

import org.lunarray.model.descriptor.builder.Configuration;

/**
 * The default configuration.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DefaultConfiguration
		implements Configuration {

	/**
	 * Creates a default configuration.
	 * 
	 * @return The configuration.
	 */
	public static Configuration createDefault() {
		return new DefaultConfiguration();
	}

	/**
	 * Default constructor.
	 */
	private DefaultConfiguration() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public char embeddedIndicator() {
		return '.';
	}

	/** {@inheritDoc} */
	@Override
	public String nameRegexp() {
		return "^[\\w\\.\\$-]+$";
	}
}
