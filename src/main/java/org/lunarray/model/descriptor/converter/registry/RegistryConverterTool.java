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

import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.def.AbstractDefaultConverterTool;
import org.lunarray.model.descriptor.registry.Registry;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A registry enabled converter.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <N>
 *            The bean name type for the registry.
 */
public final class RegistryConverterTool<N>
		extends AbstractDefaultConverterTool {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistryConverterTool.class);
	/** The registry. */
	private transient Registry<N> registry;

	/**
	 * Default constructor.
	 */
	public RegistryConverterTool() {
		super();
		this.registry = null;
	}

	/**
	 * Sets a new value for the registry field.
	 * 
	 * @param registry
	 *            The new value for the registry field.
	 */
	public void setRegistry(final Registry<N> registry) {
		Validate.notNull(registry, "Registry may not be null.");
		this.registry = registry;
		this.init();
	}

	/**
	 * Initializes, populates.
	 */
	private void init() {
		try {
			final Set<N> beanNames = this.registry.lookupAll(Converter.class);
			for (final N beanName : beanNames) {
				this.addConverter(this.registry.lookup(Converter.class, beanName));
			}
		} catch (final RegistryException e) {
			RegistryConverterTool.LOGGER.warn("Unable to get bean.", e);
		}
	}
}
