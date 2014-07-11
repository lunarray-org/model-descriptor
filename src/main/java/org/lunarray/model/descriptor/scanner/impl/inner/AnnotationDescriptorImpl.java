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

/**
 * The annotation descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 */
public final class AnnotationDescriptorImpl<E>
		implements AnnotationDescriptor<E> {
	/** Serial id. */
	private static final long serialVersionUID = 6996321450020206141L;
	/** The aggregate types. */
	private Set<Class<? extends Annotation>> aggregates;
	/** The describe types. */
	private Set<Class<? extends Annotation>> describes;

	/**
	 * Default constructor.
	 */
	public AnnotationDescriptorImpl() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public Set<Class<? extends Annotation>> getAggregates() {
		return this.aggregates;
	}

	/** {@inheritDoc} */
	@Override
	public Set<Class<? extends Annotation>> getDescribes() {
		return this.describes;
	}

	/**
	 * Sets a new value for the aggregates field.
	 * 
	 * @param aggregates
	 *            The new value for the aggregates field.
	 */
	public void setAggregates(final Set<Class<? extends Annotation>> aggregates) {
		this.aggregates = aggregates;
	}

	/**
	 * Sets a new value for the describes field.
	 * 
	 * @param describes
	 *            The new value for the describes field.
	 */
	public void setDescribes(final Set<Class<? extends Annotation>> describes) {
		this.describes = describes;
	}
}
