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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * The annotation meta values.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class AnnotationMetaValues {
	/** Validation message. */
	private static final String KEY_NULL = "Key may not be null.";
	/** The meta annotation values. */
	private Map<String, List<AnnotationMetaValues>> metaAnnotations;
	/** The meta values. */
	private Map<String, List<String>> metaValues;

	/**
	 * Default constructor.
	 */
	public AnnotationMetaValues() {
		this.metaValues = new HashMap<String, List<String>>();
		this.metaAnnotations = new HashMap<String, List<AnnotationMetaValues>>();
	}

	/**
	 * Gets the meta annotation list.
	 * 
	 * @param key
	 *            The key. May not be null.
	 * @return The annotations.
	 */
	public List<AnnotationMetaValues> getMetaAnnotationList(final String key) {
		Validate.notNull(key, AnnotationMetaValues.KEY_NULL);
		if (!this.metaAnnotations.containsKey(key)) {
			this.metaAnnotations.put(key, new LinkedList<AnnotationMetaValues>());
		}
		return this.metaAnnotations.get(key);
	}

	/**
	 * Gets the value for the metaAnnotations field.
	 * 
	 * @return The value for the metaAnnotations field.
	 */
	public Map<String, List<AnnotationMetaValues>> getMetaAnnotations() {
		return this.metaAnnotations;
	}

	/**
	 * Gets the meta value list.
	 * 
	 * @param key
	 *            The key.
	 * @return The values.
	 */
	public List<String> getMetaValueList(final String key) {
		Validate.notNull(key, AnnotationMetaValues.KEY_NULL);
		if (!this.metaValues.containsKey(key)) {
			this.metaValues.put(key, new LinkedList<String>());
		}
		return this.metaValues.get(key);
	}

	/**
	 * Gets the value for the metaValues field.
	 * 
	 * @return The value for the metaValues field.
	 */
	public Map<String, List<String>> getMetaValues() {
		return this.metaValues;
	}

	/**
	 * Sets a new value for the metaAnnotations field.
	 * 
	 * @param metaAnnotations
	 *            The new value for the metaAnnotations field.
	 */
	public void setMetaAnnotations(final Map<String, List<AnnotationMetaValues>> metaAnnotations) {
		this.metaAnnotations = metaAnnotations;
	}

	/**
	 * Sets a new value for the metaValues field.
	 * 
	 * @param metaValues
	 *            The new value for the metaValues field.
	 */
	public void setMetaValues(final Map<String, List<String>> metaValues) {
		this.metaValues = metaValues;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "(" + this.metaValues.toString() + ", " + this.metaAnnotations.toString() + ")";
	}
}
