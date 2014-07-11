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
package org.lunarray.model.descriptor.test.misc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;
import org.lunarray.common.event.Listener;
import org.lunarray.common.generics.GenericsUtil;
import org.lunarray.model.descriptor.builder.annotation.base.listener.model.ProcessResourceListener;
import org.lunarray.model.descriptor.builder.annotation.simple.BuilderContext;
import org.lunarray.model.descriptor.converter.Converter;
import org.lunarray.model.descriptor.converter.def.converters.ClazzConverter;
import org.lunarray.model.descriptor.test.domain.EmbeddedEntity01;
import org.lunarray.model.descriptor.test.domain.SampleEntity07;
import org.lunarray.model.descriptor.test.domain.SampleEntity09;
import org.lunarray.model.descriptor.test.domain.SampleEntity10;

/**
 * Test reflection.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class ReflectionTest {

	/**
	 * Find a type path.
	 * 
	 * @param currentType
	 *            The current type.
	 * @param clazz
	 *            The resolvable type.
	 * @return The type path.
	 */
	private Deque<Type> findTypePath(final Type currentType, final Class<?> clazz) {
		final Deque<Type> path = new LinkedList<Type>();
		if (!clazz.equals(currentType)) {
			path.add(currentType);
			// If the clazz is a Class, look if we can find the clazz.
			if (currentType instanceof Class) {
				final Class<?> currentClazz = (Class<?>) currentType;
				for (final Type t : currentClazz.getGenericInterfaces()) {
					if (t instanceof ParameterizedType) {
						final ParameterizedType pt = (ParameterizedType) t;
						if (clazz.equals(pt.getRawType())) {
							path.add(pt);
						}
					}
				}
				// We haven't found it yet, try the superclasses.
				if ((path.size() < 2) && (currentClazz.getGenericSuperclass() != null)) {
					path.addAll(this.findTypePath(currentClazz.getGenericSuperclass(), clazz));
				}
				// Still haven't found it, try the declaring classes.
				if ((path.size() < 2) && (currentClazz.getDeclaringClass() != null)) {
					path.addAll(this.findTypePath(currentClazz.getDeclaringClass(), clazz));
				}
			} else if (currentType instanceof ParameterizedType) {
				// Search the raw type.
				final ParameterizedType pType = (ParameterizedType) currentType;
				path.addAll(this.findTypePath(pType.getRawType(), clazz));
			}
		}
		return path;
	}

	/**
	 * Gets the clazz declaring the typevariable.
	 * 
	 * @param declaringClazz
	 *            The declaring clazz.
	 * @param target
	 *            The type variable we're looking for.
	 * @return The clazz or one of it's declaring classes.
	 */
	private Class<?> getDeclaringClass(final Class<?> declaringClazz, final TypeVariable<?> target) {
		Class<?> source = declaringClazz;
		boolean equals = false;
		// Test if we can find the declaring clazz.
		for (final TypeVariable<?> tv : declaringClazz.getTypeParameters()) {
			if (tv.equals(target)) {
				equals = true;
			}
		}
		// Search it's declaring clazz.
		if (!equals && (declaringClazz.getDeclaringClass() != null)) {
			source = this.getDeclaringClass(declaringClazz.getDeclaringClass(), target);
		}
		return source;
	}

	/**
	 * Gets the most specific type we can assign to the given genericParameter
	 * to the clazz in the known fieldHierarchy.
	 * 
	 * @param clazz
	 *            THe clazz we're looking for.
	 * @param genericParameter
	 *            The parameter number of the clazz we're looking for.
	 * @param fieldHierarchy
	 *            The filed hierarachy.
	 * @return The most specific type we can assign to the given parameter.
	 */
	private Type getFieldGenericType(final Class<?> clazz, final int genericParameter, final Deque<Field> fieldHierarchy) {
		if (fieldHierarchy.isEmpty()) {
			return null;
		}
		// First field.
		final Field f = fieldHierarchy.pop();
		if (f == null) {
			return null;
		}
		// Find the true originating type.
		final Deque<Type> typePath = this.findTypePath(f.getGenericType(), clazz);
		if (f.getGenericType() instanceof ParameterizedType) {
			typePath.push(f.getGenericType());
		}
		if (typePath.isEmpty()) {
			return null;
		}
		Type result = this.traceType(f, typePath, genericParameter);
		if ((result instanceof TypeVariable) && !fieldHierarchy.isEmpty()) {
			final TypeVariable<?> tv = (TypeVariable<?>) result;
			final Class<?> declaringClass = this.getDeclaringClass(f.getDeclaringClass(), tv);
			int idx = -1;
			final TypeVariable<?>[] tvs = declaringClass.getTypeParameters();
			for (int i = 0; i < tvs.length; i++) {
				if (tvs[i].equals(tv)) {
					idx = i;
				}
			}
			if (idx >= 0) {
				result = this.getFieldGenericType(declaringClass, idx, fieldHierarchy);
			}
		}
		return result;
	}

	/**
	 * Gets a fields type.
	 * 
	 * @param clazz
	 *            The clazz.
	 * @param genericParameter
	 *            The parameter to get it.
	 * @param fieldHierarchy
	 *            The declaration path.
	 * @return The type.
	 */
	private Type getFieldGenericType(final Class<?> clazz, final int genericParameter, final Field... fieldHierarchy) {
		final Deque<Field> fields = new LinkedList<Field>();
		for (final Field f : fieldHierarchy) {
			fields.add(f);
		}
		return this.getFieldGenericType(clazz, genericParameter, fields);
	}

	/**
	 * Gets the real parameter of a type.
	 * 
	 * @param realType
	 *            The type.
	 * @return The parameter.
	 */
	private int getRealParameter(final Type realType) {
		int realVariable = -1;
		if (realType instanceof TypeVariable) {
			final Type t = (Type) ((TypeVariable<?>) realType).getGenericDeclaration();
			if (t instanceof Class) {
				final Class<?> c = (Class<?>) t;
				final TypeVariable<?>[] tvs = c.getTypeParameters();
				for (int i = 0; i < tvs.length; i++) {
					if (realType.equals(tvs[i])) {
						realVariable = i;
					}
				}
			}
		}
		return realVariable;
	}

	/**
	 * Gets the real type of a field deque.
	 * 
	 * @param fields
	 *            The field deque.
	 * @return The real type.
	 */
	private Type getRealType(final Deque<Field> fields) {
		final Field field = fields.pop();
		final Type fieldType = field.getGenericType();
		if (fieldType instanceof TypeVariable) {
			final TypeVariable<?> tv = (TypeVariable<?>) fieldType;
			final Class<?> declaringType = this.getDeclaringClass(field.getDeclaringClass(), tv);
			final TypeVariable<?> stv = this.getSuperDeclaration(field, tv);
			final int param = this.getRealParameter(stv);
			return this.getFieldGenericType(declaringType, param, fields);
		} else if (fieldType instanceof Class) {
			return fieldType;
		}
		return null;
	}

	/**
	 * Gets the real type of a field array.
	 * 
	 * @param fieldHierarchy
	 *            The field array.
	 * @return The real type.
	 */
	private Type getRealType(final Field... fieldHierarchy) {
		final Deque<Field> fields = new LinkedList<Field>();
		for (final Field f : fieldHierarchy) {
			fields.add(f);
		}
		return this.getRealType(fields);
	}

	/**
	 * gets the super declaration of a type.
	 * 
	 * @param field
	 *            The field.
	 * @param tv
	 *            The type variable.
	 * @return The super declaration.
	 */
	private TypeVariable<?> getSuperDeclaration(final Field field, TypeVariable<?> tv) {
		final TypeVariable<?> result = tv;
		Class<?> declaring = field.getDeclaringClass();
		while (declaring != null) {
			for (final TypeVariable<?> t : declaring.getTypeParameters()) {
				if (tv.equals(t)) {
					tv = t;
				}
			}
			declaring = declaring.getDeclaringClass();
		}
		return result;
	}

	/** Test declared fields. */
	@Test
	public void testDeclaredFields() throws Exception {
		int bytecodeenhancementOffset = 0;
		try {
			if (this.getClass().getDeclaredField("$jacocoData") != null) {
				bytecodeenhancementOffset++;
			}
		} catch (final NoSuchFieldException e) {
			// Ignore.
		}
		final Field[] field1 = SampleEntity10.class.getDeclaredFields();
		Assert.assertEquals(8 + bytecodeenhancementOffset, field1.length);
		final Field[] field2 = SampleEntity10.EmbeddedCollection.class.getDeclaredFields();
		Assert.assertEquals(3 + bytecodeenhancementOffset, field2.length);
		final Field[] field3 = EmbeddedEntity01.class.getDeclaredFields();
		Assert.assertEquals(2 + bytecodeenhancementOffset, field3.length);
		final Field[] field4 = EmbeddedEntity01.OtherCollection.class.getDeclaredFields();
		Assert.assertEquals(3 + bytecodeenhancementOffset, field4.length);
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType01() throws Exception {
		final Field field = SampleEntity10.class.getDeclaredField("simpleStringList");
		Assert.assertEquals(String.class, this.getFieldGenericType(Collection.class, 0, field));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType02() throws Exception {
		final Field field = SampleEntity10.class.getDeclaredField("simpleStringSet");
		Assert.assertEquals(String.class, this.getFieldGenericType(Collection.class, 0, field));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType03() throws Exception {
		final Field field = SampleEntity10.class.getDeclaredField("stringList");
		Assert.assertEquals(String.class, this.getFieldGenericType(Collection.class, 0, field));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType04() throws Exception {
		final Field field = EmbeddedEntity01.class.getDeclaredField("anElement");
		final Field fieldP = SampleEntity10.class.getDeclaredField("embeddedEntity");
		Assert.assertEquals(Integer.class, this.getRealType(field, fieldP));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType05() throws Exception {
		final Field field = SampleEntity10.class.getDeclaredField("testCollection");
		Assert.assertEquals(Integer.class, this.getFieldGenericType(Collection.class, 0, field));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType06() throws Exception {
		final Field field = SampleEntity10.class.getDeclaredField("testOtherCollection");
		Assert.assertEquals(Integer.class, this.getFieldGenericType(Collection.class, 0, field));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType07() throws Exception {
		final Field field = SampleEntity10.EmbeddedCollection.class.getDeclaredField("anElement");
		final Field fieldP = SampleEntity10.class.getDeclaredField("testList");
		Assert.assertEquals(Integer.class, this.getRealType(field, fieldP));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType08() throws Exception {
		final Field field = SampleEntity10.EmbeddedCollection.class.getDeclaredField("testList");
		final Field fieldP = SampleEntity10.class.getDeclaredField("testList");
		final Type type = this.getFieldGenericType(Collection.class, 0, field, fieldP);
		Assert.assertEquals(String.class, type);
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType09() throws Exception {
		final Field field = EmbeddedEntity01.class.getDeclaredField("testList");
		final Field fieldP = SampleEntity10.class.getDeclaredField("embeddedEntity");
		final Type type = this.getFieldGenericType(Collection.class, 0, field, fieldP);
		Assert.assertEquals(String.class, type);
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType10() throws Exception {
		final Field field = EmbeddedEntity01.OtherCollection.class.getDeclaredField("test");
		final Field fieldP = SampleEntity10.class.getDeclaredField("embeddedEntity");
		Assert.assertEquals(String.class, this.getRealType(field, fieldP));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType11() throws Exception {
		final Field field = EmbeddedEntity01.OtherCollection.class.getDeclaredField("testList");
		final Field fieldP = SampleEntity10.class.getDeclaredField("embeddedEntity");
		final Type type = this.getFieldGenericType(Collection.class, 0, field, fieldP);
		Assert.assertEquals(Integer.class, type);
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType12() throws Exception {
		final Field field = SampleEntity09.class.getDeclaredField("sampleEntity07");
		Assert.assertEquals(SampleEntity07.class, this.getRealType(field));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType13() throws Exception {
		final Field field = SampleEntity10.TestErasure.class.getSuperclass().getDeclaredField("testList");
		final Field fieldP = SampleEntity10.class.getDeclaredField("erasureTest");
		final Type type = this.getFieldGenericType(Collection.class, 0, field, fieldP);
		Assert.assertEquals(String.class, type);
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType14() throws Exception {
		final Field field = SampleEntity10.TestErasure.class.getSuperclass().getDeclaredField("anElement");
		final Field fieldP = SampleEntity10.class.getDeclaredField("erasureTest");
		Assert.assertEquals(Integer.class, this.getRealType(field, fieldP));
	}

	/** Test runtime generics. */
	@Test
	public void testGenericsFieldType15() {
		GenericsUtil.getEntityGenericType(ClazzConverter.class, 0, Converter.class);
	}

	/** Test runtime generics. */
	@Test
	public void testGenericSuper() {
		final ProcessResourceListener<Object, BuilderContext> listener = new ProcessResourceListener<Object, BuilderContext>();
		final Class<?> type = listener.getClass();
		GenericsUtil.getEntityGenericType(type, 0, Listener.class);
		final Class<?> clazz = GenericsUtil.guessClazz(type);
		Assert.assertEquals(ProcessResourceListener.class, clazz);
	}

	/**
	 * Trace the type of a field through the path to the originating index.
	 * 
	 * @param field
	 *            The field.
	 * @param typePath
	 *            The path.
	 * @param originatingIndex
	 *            The originating index.
	 * @return The type.
	 */
	private Type traceType(final Field field, final Deque<Type> typePath, final int originatingIndex) {
		final Iterator<Type> it = typePath.descendingIterator();
		int currentIndex = originatingIndex;
		Type result = null;
		while (it.hasNext() && !(result instanceof Class)) {
			final Type t = it.next();
			if (result == null) {
				if (t instanceof Class) {
					final Class<?> c = (Class<?>) t;
					if (c.getTypeParameters().length > originatingIndex) {
						result = c.getTypeParameters()[currentIndex];
					}
				} else if (t instanceof ParameterizedType) {
					final ParameterizedType pt = (ParameterizedType) t;
					if (pt.getActualTypeArguments().length > originatingIndex) {
						result = pt.getActualTypeArguments()[currentIndex];
					}
				}
			} else {
				if (t instanceof Class) {
					final Class<?> c = (Class<?>) t;
					final TypeVariable<?>[] tvs = c.getTypeParameters();
					for (int i = 0; i < tvs.length; i++) {
						if (tvs[i].equals(result)) {
							currentIndex = i;
							result = tvs[i];
						}
					}
				} else if (t instanceof ParameterizedType) {
					final ParameterizedType pt = (ParameterizedType) t;
					final Class<?> c = (Class<?>) pt.getRawType();
					Type[] tvs = c.getTypeParameters();
					for (int i = 0; i < tvs.length; i++) {
						if (tvs[i].equals(result)) {
							currentIndex = i;
							result = tvs[i];
						}
					}
					tvs = pt.getActualTypeArguments();
					for (int i = 0; i < tvs.length; i++) {
						if (tvs[i].equals(result)) {
							currentIndex = i;
							result = tvs[i];
						}
					}
					if (pt.getActualTypeArguments()[currentIndex].getClass().equals(Class.class)) {
						result = pt.getActualTypeArguments()[currentIndex];
					}
				}
			}
		}
		if (result instanceof TypeVariable<?>) {
			final Type type = field.getGenericType();
			if (type instanceof ParameterizedType) {
				final ParameterizedType pt = (ParameterizedType) type;
				result = pt.getActualTypeArguments()[currentIndex];
			}
		}
		return result;
	}
}
