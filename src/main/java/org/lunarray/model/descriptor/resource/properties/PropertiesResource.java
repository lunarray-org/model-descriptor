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
package org.lunarray.model.descriptor.resource.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.resource.NamedResource;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;
import org.lunarray.model.descriptor.util.ConfigurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The properties resource.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The class type.
 */
public final class PropertiesResource<S>
		implements Resource<Class<? extends S>>, NamedResource<Class<? extends S>> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesResource.class);
	/** The name map. */
	private transient Map<Class<? extends S>, String> nameMap;

	/**
	 * Constructs the properties resource.
	 * 
	 * @param propertiesFile
	 *            The properties location.
	 * @throws ResourceException
	 *             Thrown if the properties could not be read.
	 */
	public PropertiesResource(final File propertiesFile) throws ResourceException {
		FileInputStream inputStream = null;
		if (CheckUtil.isNull(propertiesFile)) {
			throw new IllegalArgumentException("File may not be null.");
		}
		try {
			try {
				inputStream = new FileInputStream(propertiesFile);
			} catch (final IOException e) {
				throw new ResourceException(e);
			}
			this.loadProperties(inputStream);
		} finally {
			if (!CheckUtil.isNull(inputStream)) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					PropertiesResource.LOGGER.warn("Could not close stream.", e);
				}
			}
		}
	}

	/**
	 * Default constructor.
	 * 
	 * @param inputStream
	 *            The input stream.
	 * @throws ResourceException
	 *             Thrown if the properties could not be read.
	 */
	public PropertiesResource(final InputStream inputStream) throws ResourceException {
		final Properties properties = new Properties();
		if (CheckUtil.isNull(inputStream)) {
			throw new IllegalArgumentException("Invalid stream.");
		} else {
			try {
				properties.load(inputStream);
			} catch (final IOException e) {
				throw new ResourceException(e);
			}
		}
		this.loadProperties(properties);
	}

	/**
	 * Default constructor.
	 * 
	 * @param properties
	 *            The properties.
	 * @throws ResourceException
	 *             Thrown if the properties could not be read.
	 */
	public PropertiesResource(final Properties properties) throws ResourceException {
		if (CheckUtil.isNull(properties)) {
			throw new IllegalArgumentException("Properties may not be null.");
		}
		this.loadProperties(properties);
	}

	/**
	 * Loads properties from the classpath.
	 * 
	 * @param propertiesFile
	 *            The classpath location.
	 * @throws ResourceException
	 *             Thrown if the properties could not be read.
	 */
	public PropertiesResource(final String propertiesFile) throws ResourceException {
		if (CheckUtil.isNull(propertiesFile)) {
			throw new IllegalArgumentException("Properties file may not be null.");
		}
		final Properties properties = new Properties();
		ConfigurationUtil.loadProperties(propertiesFile, properties);
		this.loadProperties(properties);
	}

	/**
	 * Gets the name map.
	 * 
	 * @return The name map.
	 */
	public Map<Class<? extends S>, String> getNameMap() {
		return new HashMap<Class<? extends S>, String>(this.nameMap);
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Class<? extends S>> getResources() {
		return this.nameMap.keySet();
	}

	/** {@inheritDoc} */
	@Override
	public Map<Class<? extends S>, String> resourceNames() {
		return this.getNameMap();
	}

	/**
	 * Load the properties.
	 * 
	 * @param inputStream
	 *            The input stream.
	 * @throws ResourceException
	 *             Thrown if the properties could not be read.
	 */
	private void loadProperties(final InputStream inputStream) throws ResourceException {
		final Properties properties = new Properties();
		if (CheckUtil.isNull(inputStream)) {
			throw new IllegalArgumentException("Invalid properties.");
		} else {
			try {
				properties.load(inputStream);
			} catch (final IOException e) {
				throw new ResourceException(e);
			}
		}
		this.loadProperties(properties);
	}

	/**
	 * Load the properties.
	 * 
	 * @param properties
	 *            The properties.
	 * @throws ResourceException
	 *             Thrown if the properties could not be read.
	 */
	@SuppressWarnings("unchecked")
	private void loadProperties(final Properties properties) throws ResourceException {
		if (CheckUtil.isNull(this.nameMap)) {
			this.nameMap = new HashMap<Class<? extends S>, String>();
		}
		final Set<String> names = properties.stringPropertyNames();
		for (final String name : names) {
			try {
				final String clazzName = properties.getProperty(name);
				final Class<S> clazz = (Class<S>) Class.forName(name);
				this.nameMap.put(clazz, clazzName);
				PropertiesResource.LOGGER.info("Resolved {} for {}({}).", clazz, name, clazzName);
			} catch (final ClassNotFoundException e) {
				throw new ResourceException(e);
			}
		}
	}
}
