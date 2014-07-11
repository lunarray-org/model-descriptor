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
package org.lunarray.model.descriptor.scanner.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.lunarray.model.descriptor.scanner.annotation.Describes;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation08;

/**
 * Test entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
@Describes(EntityAnnotation08.class)
public class Entity03
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 3420282695126566342L;

	private List<Entity04> annotationValues = new LinkedList<Entity04>();

	private List<Byte> byteValues = new LinkedList<Byte>();

	private List<Character> charValues = new LinkedList<Character>();

	private List<Class<?>> clazzValues = new LinkedList<Class<?>>();

	private List<Double> doubleValues = new LinkedList<Double>();

	private List<TestEnum01> enumValues = new LinkedList<TestEnum01>();

	private List<Float> floatValues = new LinkedList<Float>();

	private List<Integer> intValues = new LinkedList<Integer>();

	private List<Long> longValues = new LinkedList<Long>();

	private List<Short> shortValues = new LinkedList<Short>();

	private List<String> stringValues = new LinkedList<String>();

	/**
	 * Gets the value for the annotationValues field.
	 * 
	 * @return The value for the annotationValues field.
	 */
	public List<Entity04> getAnnotationValues() {
		return this.annotationValues;
	}

	/**
	 * Gets the value for the byteValues field.
	 * 
	 * @return The value for the byteValues field.
	 */
	public List<Byte> getByteValues() {
		return this.byteValues;
	}

	/**
	 * Gets the value for the charValues field.
	 * 
	 * @return The value for the charValues field.
	 */
	public List<Character> getCharValues() {
		return this.charValues;
	}

	/**
	 * Gets the value for the clazzValues field.
	 * 
	 * @return The value for the clazzValues field.
	 */
	public List<Class<?>> getClazzValues() {
		return this.clazzValues;
	}

	/**
	 * Gets the value for the doubleValues field.
	 * 
	 * @return The value for the doubleValues field.
	 */
	public List<Double> getDoubleValues() {
		return this.doubleValues;
	}

	/**
	 * Gets the value for the enumValues field.
	 * 
	 * @return The value for the enumValues field.
	 */
	public List<TestEnum01> getEnumValues() {
		return this.enumValues;
	}

	/**
	 * Gets the value for the floatValues field.
	 * 
	 * @return The value for the floatValues field.
	 */
	public List<Float> getFloatValues() {
		return this.floatValues;
	}

	/**
	 * Gets the value for the intValues field.
	 * 
	 * @return The value for the intValues field.
	 */
	public List<Integer> getIntValues() {
		return this.intValues;
	}

	/**
	 * Gets the value for the longValues field.
	 * 
	 * @return The value for the longValues field.
	 */
	public List<Long> getLongValues() {
		return this.longValues;
	}

	/**
	 * Gets the value for the shortValues field.
	 * 
	 * @return The value for the shortValues field.
	 */
	public List<Short> getShortValues() {
		return this.shortValues;
	}

	/**
	 * Gets the value for the stringValues field.
	 * 
	 * @return The value for the stringValues field.
	 */
	public List<String> getStringValues() {
		return this.stringValues;
	}

	/**
	 * Sets a new value for the annotationValues field.
	 * 
	 * @param annotationValues
	 *            The new value for the annotationValues field.
	 */
	public void setAnnotationValues(final List<Entity04> annotationValues) {
		this.annotationValues = annotationValues;
	}

	/**
	 * Sets a new value for the byteValues field.
	 * 
	 * @param byteValues
	 *            The new value for the byteValues field.
	 */
	public void setByteValues(final List<Byte> byteValues) {
		this.byteValues = byteValues;
	}

	/**
	 * Sets a new value for the charValues field.
	 * 
	 * @param charValues
	 *            The new value for the charValues field.
	 */
	public void setCharValues(final List<Character> charValues) {
		this.charValues = charValues;
	}

	/**
	 * Sets a new value for the clazzValues field.
	 * 
	 * @param clazzValues
	 *            The new value for the clazzValues field.
	 */
	public void setClazzValues(final List<Class<?>> clazzValues) {
		this.clazzValues = clazzValues;
	}

	/**
	 * Sets a new value for the doubleValues field.
	 * 
	 * @param doubleValues
	 *            The new value for the doubleValues field.
	 */
	public void setDoubleValues(final List<Double> doubleValues) {
		this.doubleValues = doubleValues;
	}

	/**
	 * Sets a new value for the enumValues field.
	 * 
	 * @param enumValues
	 *            The new value for the enumValues field.
	 */
	public void setEnumValues(final List<TestEnum01> enumValues) {
		this.enumValues = enumValues;
	}

	/**
	 * Sets a new value for the floatValues field.
	 * 
	 * @param floatValues
	 *            The new value for the floatValues field.
	 */
	public void setFloatValues(final List<Float> floatValues) {
		this.floatValues = floatValues;
	}

	/**
	 * Sets a new value for the intValues field.
	 * 
	 * @param intValues
	 *            The new value for the intValues field.
	 */
	public void setIntValues(final List<Integer> intValues) {
		this.intValues = intValues;
	}

	/**
	 * Sets a new value for the longValues field.
	 * 
	 * @param longValues
	 *            The new value for the longValues field.
	 */
	public void setLongValues(final List<Long> longValues) {
		this.longValues = longValues;
	}

	/**
	 * Sets a new value for the shortValues field.
	 * 
	 * @param shortValues
	 *            The new value for the shortValues field.
	 */
	public void setShortValues(final List<Short> shortValues) {
		this.shortValues = shortValues;
	}

	/**
	 * Sets a new value for the stringValues field.
	 * 
	 * @param stringValues
	 *            The new value for the stringValues field.
	 */
	public void setStringValues(final List<String> stringValues) {
		this.stringValues = stringValues;
	}

	@Override
	public String toString() {
		return "Entity03(annotationValues=" + this.annotationValues.toString() + ", byteValues" + this.byteValues.toString()
				+ ", charValues" + this.charValues.toString() + ", clazzValues" + this.clazzValues.toString() + ", doubleValues"
				+ this.doubleValues.toString() + ", enumValues" + this.enumValues.toString() + ", floatValues"
				+ this.floatValues.toString() + ", intValues" + this.intValues.toString() + ", longValues" + this.longValues.toString()
				+ ", shortValues" + this.shortValues.toString() + ", stringValues" + this.stringValues + ")";
	}
}
