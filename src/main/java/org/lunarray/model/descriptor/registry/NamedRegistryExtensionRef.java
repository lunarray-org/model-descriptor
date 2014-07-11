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
package org.lunarray.model.descriptor.registry;

import java.io.Serializable;

import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.registry.exceptions.RegistryException;

/**
 * An extension reference.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <X>
 *            The extension type.
 * @param <N>
 *            The registry bean name type.
 */
public final class NamedRegistryExtensionRef<X extends Extension, N>
		implements Serializable {
	/** Serial id. */
	private static final long serialVersionUID = 7631903037323296190L;
	/** The concrete extension type. */
	private Class<? extends X> concreteType;
	/** The bean name. */
	private N name;

	/**
	 * Constructs the reference.
	 * 
	 * @param referredType
	 *            The referred type.
	 * @param concreteType
	 *            The concrete type.
	 * @param name
	 *            The bean name.
	 */
	public NamedRegistryExtensionRef(final Class<X> referredType, final Class<? extends X> concreteType, final N name) {
		if (CheckUtil.isNull(concreteType)) {
			this.concreteType = referredType;
		} else {
			this.concreteType = concreteType;
		}
		this.name = name;
	}

	/**
	 * Gets the value for the concreteType field.
	 * 
	 * @return The value for the concreteType field.
	 */
	public Class<? extends X> getConcreteType() {
		return this.concreteType;
	}

	/**
	 * Gets the value for the name field.
	 * 
	 * @return The value for the name field.
	 */
	public N getName() {
		return this.name;
	}

	/**
	 * Look up the extension.
	 * 
	 * @param registry
	 *            The registry.
	 * @return The extension.
	 * @throws RegistryException
	 *             Thrown if the extension was not found.
	 */
	@SuppressWarnings("unchecked")
	public X lookup(final Registry<N> registry) throws RegistryException {
		X extension;
		if (CheckUtil.isNull(this.name)) {
			extension = registry.lookup(this.concreteType);
		} else if (CheckUtil.isNull(this.concreteType)) {
			extension = (X) registry.lookup(this.name);
		} else {
			extension = registry.lookup(this.concreteType, this.name);
		}
		return extension;
	}

	/**
	 * Sets a new value for the concreteType field.
	 * 
	 * @param concreteType
	 *            The new value for the concreteType field.
	 */
	public void setConcreteType(final Class<? extends X> concreteType) {
		this.concreteType = concreteType;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 */
	public void setName(final N name) {
		this.name = name;
	}
}
