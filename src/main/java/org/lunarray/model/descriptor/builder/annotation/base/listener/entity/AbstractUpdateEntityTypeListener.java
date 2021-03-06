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
package org.lunarray.model.descriptor.builder.annotation.base.listener.entity;

import java.io.Serializable;

import org.lunarray.common.event.Listener;
import org.lunarray.model.descriptor.builder.annotation.base.build.context.AnnotationBuilderContext;
import org.lunarray.model.descriptor.builder.annotation.base.listener.events.UpdatedEntityTypeEvent;

/**
 * The entity extension processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 * @param <B>
 *            The builder context.
 */
public abstract class AbstractUpdateEntityTypeListener<E, K extends Serializable, B extends AnnotationBuilderContext>
		implements Listener<UpdatedEntityTypeEvent<E, K, B>> {

	/** Default constructor. */
	public AbstractUpdateEntityTypeListener() {
		// Default constructor.
	}
}
