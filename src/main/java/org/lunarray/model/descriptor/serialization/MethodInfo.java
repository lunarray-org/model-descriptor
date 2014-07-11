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
package org.lunarray.model.descriptor.serialization;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.lunarray.common.check.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains information about a method.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class MethodInfo
		implements Serializable {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodInfo.class);
	/** Serial id. */
	private static final long serialVersionUID = -6332641563989302495L;
	/** The method arguments. */
	private Class<?>[] arguments;
	/** The containing type. */
	private Class<?> containingType;
	/** The method name. */
	private String name;

	/**
	 * Default constructor.
	 * 
	 * @param method
	 *            The method.
	 */
	public MethodInfo(final Method method) {
		if (!CheckUtil.isNull(method)) {
			this.arguments = method.getParameterTypes();
			this.containingType = method.getDeclaringClass();
			this.name = method.getName();
		}
	}

	/**
	 * Gets the value for the arguments field.
	 * 
	 * @return The value for the arguments field.
	 */
	public Class<?>[] getArguments() {
		Class<?>[] result = null;
		if (!CheckUtil.isNull(this.arguments)) {
			result = this.arguments.clone();
		}
		return result;
	}

	/**
	 * Gets the value for the containingType field.
	 * 
	 * @return The value for the containingType field.
	 */
	public Class<?> getContainingType() {
		return this.containingType;
	}

	/**
	 * Gets the value for the name field.
	 * 
	 * @return The value for the name field.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Finds the method.
	 * 
	 * @return The method.
	 */
	public Method resolveMethod() {
		Method result = null;
		if (!CheckUtil.isNull(this.containingType) && !CheckUtil.isNull(this.name) && !CheckUtil.isNull(this.getArguments())) {
			try {
				result = this.containingType.getMethod(this.getName(), this.getArguments());
			} catch (final SecurityException e) {
				MethodInfo.LOGGER.warn("Could not access method.", e);
			} catch (final NoSuchMethodException e) {
				MethodInfo.LOGGER.warn("Could not find method.", e);
			}
		}
		return result;
	}

	/**
	 * Sets a new value for the arguments field.
	 * 
	 * @param arguments
	 *            The new value for the arguments field.
	 */
	public void setArguments(final Class<?>... arguments) {
		this.arguments = null;
		if (!CheckUtil.isNull(arguments)) {
			this.arguments = arguments.clone();
		}
	}

	/**
	 * Sets a new value for the containingType field.
	 * 
	 * @param containingType
	 *            The new value for the containingType field.
	 */
	public void setContainingType(final Class<?> containingType) {
		this.containingType = containingType;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
