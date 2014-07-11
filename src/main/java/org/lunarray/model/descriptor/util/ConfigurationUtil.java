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

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for handling property files.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum ConfigurationUtil {
	/** Instance. */
	INSTANCE;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUtil.class);

	/**
	 * Load properties into properties.
	 * 
	 * @param filename
	 *            The file name. May not be null.
	 * @param properties
	 *            The properties to load into. May not be null.
	 */
	public static void loadProperties(final String filename, final Properties properties) {
		Validate.notNull(filename, "File name must be set.");
		Validate.notNull(properties, "Properties must be set.");
		InputStream inputStream = AccessController.doPrivileged(new GetResourceAction(filename));
		try {
			if (CheckUtil.isNull(inputStream)) {
				inputStream = ConfigurationUtil.class.getResourceAsStream(filename);
			}
			if (!CheckUtil.isNull(inputStream)) {
				properties.load(inputStream);
			}
		} catch (final IOException e) {
			ConfigurationUtil.LOGGER.warn("Could not load properties.", e);
		} finally {
			if (!CheckUtil.isNull(inputStream)) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					ConfigurationUtil.LOGGER.warn("Could not close properties.", e);
				}
			}
		}
	}

	/**
	 * Gets a resource.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	public static final class GetResourceAction
			implements PrivilegedAction<InputStream> {

		/** The file name. */
		private final transient String filename;

		/**
		 * Constructs the action.
		 * 
		 * @param filename
		 *            The file name.
		 */
		public GetResourceAction(final String filename) {
			this.filename = filename;
		}

		/** {@inheritDoc} */
		@Override
		public InputStream run() {
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = null;
			if (!CheckUtil.isNull(classLoader)) {
				inputStream = classLoader.getResourceAsStream(this.filename);
			}
			return inputStream;
		}
	}
}
