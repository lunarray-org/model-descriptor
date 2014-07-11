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
package org.lunarray.model.descriptor.scanner.impl.inner;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.lunarray.model.descriptor.model.entity.EntityExtension;

/**
 * The annotation descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public interface AnnotationDescriptor<E>
		extends EntityExtension<E> {

	/**
	 * Gets the value for the aggregates field.
	 * 
	 * @return The value for the aggregates field.
	 */
	Set<Class<? extends Annotation>> getAggregates();

	/**
	 * Gets the value for the describes field.
	 * 
	 * @return The value for the describes field.
	 */
	Set<Class<? extends Annotation>> getDescribes();

}
