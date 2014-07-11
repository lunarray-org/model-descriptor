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
package org.lunarray.model.descriptor.builder.annotation.base.listener.events;

import org.lunarray.model.descriptor.model.operation.parameters.ParameterDescriptor;

/**
 * Propagated if an entity has been built.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The parameter type.
 */
public final class BuildParameterEvent<P> {

	/** The entity. */
	private final transient ParameterDescriptor<P> parameter;

	/**
	 * Default constructor.
	 * 
	 * @param parameter
	 *            The parameter.
	 */
	public BuildParameterEvent(final ParameterDescriptor<P> parameter) {
		this.parameter = parameter;
	}

	/**
	 * Gets the value for the parameter field.
	 * 
	 * @return The value for the parameter field.
	 */
	public ParameterDescriptor<P> getParameter() {
		return this.parameter;
	}
}
