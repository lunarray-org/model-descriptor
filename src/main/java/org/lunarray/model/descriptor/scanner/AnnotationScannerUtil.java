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
package org.lunarray.model.descriptor.scanner;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang.Validate;

/**
 * An annotation scanner utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum AnnotationScannerUtil {

	/** Instance. */
	INSTANCE;

	/**
	 * Gets the marked annotations.
	 * 
	 * @param marker
	 *            The marker.
	 * @param annotation
	 *            The annotation.
	 * @param transitive
	 *            Transitive traversal.
	 * @return The marked annotations.
	 */
	public static List<Annotation> getMarked(final Class<? extends Annotation> marker, final Annotation annotation, final boolean transitive) {
		final List<Annotation> markedList = new LinkedList<Annotation>();
		INSTANCE.getMarkedProcessInner(marker, annotation, transitive, markedList, AnnotationScannerUtil.createSet());
		return markedList;
	}

	/**
	 * Gets the marked annotations.
	 * 
	 * @param marker
	 *            The marker.
	 * @param entityType
	 *            The entity type. Entity type may not be null.
	 * @param transitive
	 *            Transitive traversal.
	 * @return The marked annotations.
	 */
	public static List<Annotation> getMarked(final Class<? extends Annotation> marker, final Class<?> entityType, final boolean transitive) {
		Validate.notNull(entityType, "Entity type may not be null.");
		final List<Annotation> markedList = new LinkedList<Annotation>();
		for (final Annotation a : entityType.getAnnotations()) {
			INSTANCE.getMarkedProcessInner(marker, a, transitive, markedList, AnnotationScannerUtil.createSet());
		}
		return markedList;
	}

	/**
	 * Tests if the given annotation is marked by the marker annotation.
	 * 
	 * @param marker
	 *            The marker annotation.
	 * @param annotation
	 *            The annotation to test.
	 * @param transitive
	 *            True if the annotation should be scanned recursively.
	 * @return True if and only if the annotation is marked by the marker.
	 */
	public static boolean isMarked(final Class<? extends Annotation> marker, final Class<? extends Annotation> annotation,
			final boolean transitive) {
		return INSTANCE.isMarkedInternal(marker, annotation, transitive);
	}

	/**
	 * Creates a set.
	 * 
	 * @return The set.
	 */
	private static Set<Class<? extends Annotation>> createSet() {
		return new HashSet<Class<? extends Annotation>>();
	}

	/**
	 * Get all marked annotations.
	 * 
	 * @param marker
	 *            The marker.
	 * @param annotation
	 *            The annotation.
	 * @param transitive
	 *            Whether or not to transitively process.
	 * @param tail
	 *            The tail (for tail recursion).
	 * @param processed
	 *            The processed types.
	 */
	private void getMarkedInner(final Class<? extends Annotation> marker, final Annotation annotation, final boolean transitive,
			final List<Annotation> tail, final Set<Class<? extends Annotation>> processed) {
		tail.add(annotation);
		if (transitive) {
			this.getMarkedTransitive(marker, annotation, tail, processed);
		}
	}

	/**
	 * Get all marked annotations.
	 * 
	 * @param marker
	 *            The marker.
	 * @param annotation
	 *            The annotation.
	 * @param transitive
	 *            Whether or not to transitively process.
	 * @param tail
	 *            The tail (for tail recursion).
	 * @param processed
	 *            The processed types.
	 */
	private void getMarkedProcessInner(final Class<? extends Annotation> marker, final Annotation annotation, final boolean transitive,
			final List<Annotation> tail, final Set<Class<? extends Annotation>> processed) {
		final Class<? extends Annotation> annotationType = annotation.annotationType();
		if (!processed.contains(annotationType)) {
			processed.add(annotationType);
			if (AnnotationScannerUtil.isMarked(marker, annotationType, transitive)) {
				this.getMarkedInner(marker, annotation, transitive, tail, processed);
			}
		}
	}

	/**
	 * Get all marked annotations.
	 * 
	 * @param marker
	 *            The marker.
	 * @param annotation
	 *            The annotation. May not be null.
	 * @param tail
	 *            The tail (for tail recursion).
	 * @param processed
	 *            The processed types.
	 */
	private void getMarkedTransitive(final Class<? extends Annotation> marker, final Annotation annotation, final List<Annotation> tail,
			final Set<Class<? extends Annotation>> processed) {
		Validate.notNull(annotation, "Annotation may not be null.");
		for (final Annotation a : annotation.annotationType().getAnnotations()) {
			this.getMarkedProcessInner(marker, a, true, tail, processed);
		}
	}

	/**
	 * Tests if the marker marks the annotation.
	 * 
	 * @param marker
	 *            The marker.
	 * @param annotation
	 *            The annotation. May not be null.
	 * @param transitive
	 *            Whether or not to transitively traverse.
	 * @return True if and only if the marker marks the annotation.
	 */
	private boolean isMarkedInternal(final Class<? extends Annotation> marker, final Class<? extends Annotation> annotation,
			final boolean transitive) {
		Validate.notNull(annotation, "Annotation type may not be null.");
		boolean marked = false;
		if (annotation.equals(marker)) {
			marked = true;
		} else if (transitive) {
			final Set<Class<? extends Annotation>> processed = AnnotationScannerUtil.createSet();
			final Queue<Class<? extends Annotation>> process = new LinkedList<Class<? extends Annotation>>();
			process.add(annotation);
			marked = this.isMarkedTransiviteProcess(marker, processed, process);
		}
		return marked;
	}

	/**
	 * Transitively tests for marker marks.
	 * 
	 * @param marker
	 *            The marker.
	 * @param processed
	 *            The processed annotations.
	 * @param process
	 *            The annotations to process.
	 * @return True if and only if the marker marks any of the processed
	 *         annotations.
	 */
	private boolean isMarkedTransiviteProcess(final Class<? extends Annotation> marker, final Set<Class<? extends Annotation>> processed,
			final Queue<Class<? extends Annotation>> process) {
		boolean marked = false;
		while (!process.isEmpty()) {
			final Class<? extends Annotation> poll = process.poll();
			processed.add(poll);
			if (poll.equals(marker)) {
				marked = true;
				process.clear();
			} else {
				for (final Annotation annotation : poll.getAnnotations()) {
					final Class<? extends Annotation> annotationType = annotation.annotationType();
					if (!processed.contains(annotationType)) {
						process.add(annotationType);
					}
				}
			}
		}
		return marked;
	}
}
