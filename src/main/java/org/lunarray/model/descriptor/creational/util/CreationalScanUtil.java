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
package org.lunarray.model.descriptor.creational.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.util.ReflectionUtil;

/**
 * Utility for creational strategy scanning.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum CreationalScanUtil {

	/** The instance. */
	INSTANCE;

	/**
	 * Find a no-args public constructor.
	 * 
	 * @param source
	 *            The source class. May not be null.
	 * @return The constructor, or null.
	 * @param <E>
	 *            The entity type.
	 */
	@SuppressWarnings("unchecked")
	public static <E> Constructor<E> findConstructor(final Class<E> source) {
		Validate.notNull(source, "Source may not be null.");
		Constructor<E> result = null;
		if (!Modifier.isAbstract(source.getModifiers()) && !source.isSynthetic()) {
			for (final Constructor<E> constructor : (Constructor<E>[]) source.getConstructors()) {
				if (!constructor.isSynthetic() && (constructor.getParameterTypes().length == 0)) {
					result = constructor;
				}
			}
		}
		return result;
	}

	/**
	 * List factory methods.
	 * 
	 * @param source
	 *            The types to scan. May not be null.
	 * @param target
	 *            The target type to scan for. May not be null.
	 * @param findStatic
	 *            True to find just static methods.
	 * @return The list of factory methods.
	 */
	public static List<Method> scanForFactoryMethod(final Class<?> source, final Class<?> target, final boolean findStatic) {
		Validate.notNull(target, "Target may not be null.");
		final List<Method> tail = new LinkedList<Method>();
		ReflectionUtil.getMethods(tail, source, findStatic, false);
		final Iterator<Method> methodIt = tail.iterator();
		while (methodIt.hasNext()) {
			final Method method = methodIt.next();
			if ((method.getParameterTypes().length > 0) || !target.isAssignableFrom(method.getReturnType())) {
				methodIt.remove();
			}
		}
		return tail;
	}
}
