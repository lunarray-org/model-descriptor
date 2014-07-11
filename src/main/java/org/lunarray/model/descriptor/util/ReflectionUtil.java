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
package org.lunarray.model.descriptor.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;

/**
 * A general reflection utility.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum ReflectionUtil {

	/** Instance. */
	INSTANCE;

	/** Java.lang package name. */
	private static final String JAVA_LANG = "java.lang";
	/** Validation message. */
	private static final String TAIL_NULL = "Tail may not be null.";
	/** Validation message. */
	private static final String TYPE_NULL = "Type may not be null.";

	/**
	 * Gets all the fields of a type. Uses tail recursion.
	 * 
	 * @param fields
	 *            The tail. May not be null.
	 * @param type
	 *            The type. May not be null.
	 */
	public static void getFields(final List<Field> fields, final Class<?> type) {
		Validate.notNull(fields, ReflectionUtil.TAIL_NULL);
		Validate.notNull(type, ReflectionUtil.TYPE_NULL);
		for (final Field field : type.getDeclaredFields()) {
			ReflectionUtil.matchField(fields, type, field);
		}
		final Class<?> superClass = type.getSuperclass();
		if (!CheckUtil.isNull(superClass)) {
			ReflectionUtil.getFields(fields, superClass);
		}
	}

	/**
	 * Gets all methods in a type, using tail recursion.
	 * 
	 * @param methods
	 *            The tail. May not be null.
	 * @param type
	 *            The type. May not be null.
	 * @param findStatic
	 *            Set to true to find static methods, otherwise, only none
	 *            static.
	 * @param excludeJavaLang
	 *            Whether or not to exclude methods from java.lang.*.
	 */
	public static void getMethods(final List<Method> methods, final Class<?> type, final boolean findStatic, final boolean excludeJavaLang) {
		Validate.notNull(methods, ReflectionUtil.TAIL_NULL);
		Validate.notNull(type, ReflectionUtil.TYPE_NULL);
		for (final Method method : type.getMethods()) {
			if ((findStatic == Modifier.isStatic(method.getModifiers())) && !method.isBridge()
					&& ReflectionUtil.testPackage(method.getDeclaringClass(), ReflectionUtil.JAVA_LANG, excludeJavaLang)) {
				methods.add(method);
			}
		}
		final Class<?> superClass = type.getSuperclass();
		if (!CheckUtil.isNull(superClass)) {
			final String packageName = superClass.getPackage().getName();
			if (excludeJavaLang != ReflectionUtil.JAVA_LANG.equals(packageName)) {
				ReflectionUtil.getMethods(methods, superClass, findStatic, excludeJavaLang);
			}
		}
	}

	/**
	 * Tests if the field is a match.
	 * 
	 * @param type
	 *            The type.
	 * @param field
	 *            The field to match.
	 * @return True if it matches, false if it doesn't.
	 */
	private static boolean isMatch(final Class<?> type, final Field field) {
		boolean result = false;
		if (type.equals(Enum.class)) {
			// We ignore ordinal field, but include name as identifier.
			if ("name".equals(field.getName())) {
				result = true;
			}
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * Tests if the field is a proper match, then adds.
	 * 
	 * @param fields
	 *            The fields to add to.
	 * @param type
	 *            The super type.
	 * @param field
	 *            The field to add.
	 */
	private static void matchField(final List<Field> fields, final Class<?> type, final Field field) {
		if (!Modifier.isStatic(field.getModifiers()) && ReflectionUtil.isMatch(type, field)) {
			fields.add(field);
		}
	}

	/**
	 * Test if the package is to be excluded.
	 * 
	 * @param type
	 *            The type to check.
	 * @param matchPackageName
	 *            The package name to check.
	 * @param exclude
	 *            Test if the exclude should be enabled.
	 * @return True if the package matches the matchPackage and this is to be
	 *         excluded.
	 */
	private static boolean testPackage(final Class<?> type, final String matchPackageName, final boolean exclude) {
		boolean result = false;
		final String packageName = type.getPackage().getName();
		if (matchPackageName.equals(packageName)) {
			if (!exclude) {
				result = true;
			}
		} else {
			result = true;
		}
		return result;
	}
}
