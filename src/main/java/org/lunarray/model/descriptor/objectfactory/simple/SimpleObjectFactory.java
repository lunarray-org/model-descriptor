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
package org.lunarray.model.descriptor.objectfactory.simple;

import java.io.Serializable;

import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple object factory that just tries to get a new instance.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class SimpleObjectFactory
		implements ObjectFactory, Serializable {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleObjectFactory.class);
	/** Serial id. */
	private static final long serialVersionUID = -7239560674497421167L;

	/** Default constructor. */
	public SimpleObjectFactory() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public <O> O getInstance(final Class<O> type) throws InstanceException {
		try {
			SimpleObjectFactory.LOGGER.debug("Getting instance for type {}.", type);
			return type.newInstance();
		} catch (final InstantiationException e) {
			throw new InstanceException(String.format("Could not create instance for '%s'.", type.getName()), e);
		} catch (final IllegalAccessException e) {
			throw new InstanceException(String.format("Could not create access instance for '%s'.", type.getName()), e);
		}
	}
}
