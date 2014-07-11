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
package org.lunarray.model.descriptor.validator;

import java.util.Collection;
import java.util.Locale;

import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.util.OperationInvocationBuilder;

/**
 * Describes a validator.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface InvocationValidator
		extends Extension {

	/**
	 * Validates the invocation.
	 * 
	 * @param invocation
	 *            The invocation.
	 * @return A collection of constraint violations.
	 */
	Collection<ParameterViolation<?>> validateInvocation(OperationInvocationBuilder<?> invocation);

	/**
	 * Validates the invocation.
	 * 
	 * @param invocation
	 *            The invocation.
	 * @param locale
	 *            The locale for the messages.
	 * @return A collection of constraint violations.
	 */
	Collection<ParameterViolation<?>> validateInvocation(OperationInvocationBuilder<?> invocation, Locale locale);
}
