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

import org.lunarray.model.descriptor.scanner.annotation.Aggregates;
import org.lunarray.model.descriptor.scanner.annotation.Describes;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation06;
import org.lunarray.model.descriptor.scanner.annotations.EntityAnnotation07;

/**
 * Test entity.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
@Describes({ EntityAnnotation06.class })
@Aggregates({ EntityAnnotation07.class })
public class Entity02
		implements Serializable {

	/** Serial id. */
	private static final long serialVersionUID = 3420282695126566342L;
	private Class<?> aggregateTest;
	private TestEnum01 enumValue;
	private Entity01 innerAnnotation;
	private List<Entity01> innerAnnotations;
	private List<Integer> value;

	public Entity02() {
		this.value = new LinkedList<Integer>();
		this.innerAnnotations = new LinkedList<Entity01>();
	}

	public Class<?> getAggregateTest() {
		return this.aggregateTest;
	}

	/**
	 * Gets the value for the enumValue field.
	 * 
	 * @return The value for the enumValue field.
	 */
	public final TestEnum01 getEnumValue() {
		return this.enumValue;
	}

	/**
	 * Gets the value for the innerAnnotation field.
	 * 
	 * @return The value for the innerAnnotation field.
	 */
	public final Entity01 getInnerAnnotation() {
		return this.innerAnnotation;
	}

	/**
	 * Gets the value for the innerAnnotations field.
	 * 
	 * @return The value for the innerAnnotations field.
	 */
	public final List<Entity01> getInnerAnnotations() {
		return this.innerAnnotations;
	}

	public List<Integer> getValue() {
		return this.value;
	}

	public void setAggregateTest(final Class<?> aggregateTest) {
		this.aggregateTest = aggregateTest;
	}

	/**
	 * Sets a new value for the enumValue field.
	 * 
	 * @param enumValue
	 *            The new value for the enumValue field.
	 */
	public final void setEnumValue(final TestEnum01 enumValue) {
		this.enumValue = enumValue;
	}

	/**
	 * Sets a new value for the innerAnnotation field.
	 * 
	 * @param innerAnnotation
	 *            The new value for the innerAnnotation field.
	 */
	public final void setInnerAnnotation(final Entity01 innerAnnotation) {
		this.innerAnnotation = innerAnnotation;
	}

	/**
	 * Sets a new value for the innerAnnotations field.
	 * 
	 * @param innerAnnotations
	 *            The new value for the innerAnnotations field.
	 */
	public final void setInnerAnnotations(final List<Entity01> innerAnnotations) {
		this.innerAnnotations = innerAnnotations;
	}

	public void setValue(final List<Integer> value) {
		this.value = value;
	}

	@Override
	public String toString() {
		String s1 = null;
		if (this.aggregateTest != null) {
			s1 = this.aggregateTest.getCanonicalName();
		}
		return "Entity02(value=" + this.value.toString() + ", " + s1 + ", " + this.enumValue.toString() + ", "
				+ this.innerAnnotation.toString() + ", " + this.innerAnnotations.toString() + ")";
	}
}
