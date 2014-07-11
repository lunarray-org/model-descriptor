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
package org.lunarray.model.descriptor.creational.domain;

import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;

/**
 * Stub object factory.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class CreationalEntityObjectFactory
		implements ObjectFactory {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public <O> O getInstance(final Class<O> type) throws InstanceException {
		if (CreationalEntity11.class.equals(type)) {
			return (O) new CreationalEntity11();
		} else if (CreationalEntity12Impl.class.equals(type)) {
			return (O) new CreationalEntity12Impl();
		} else if (CreationalEntity13Factory.class.equals(type)) {
			return (O) new CreationalEntity13Factory();
		}
		return null;
	}
}
