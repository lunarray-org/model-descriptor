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
package org.lunarray.model.descriptor.mapping.impl;

import java.io.Serializable;

import org.lunarray.common.check.CheckUtil;

/**
 * A class pair.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ClassPair
		implements Serializable {

	/** A prime number. */
	private static final int PRIME = 31;
	/** Serial id. */
	private static final long serialVersionUID = 7809273820531829354L;
	/** First class. */
	private Class<?> classOne;
	/** Second class. */
	private Class<?> classTwo;

	/**
	 * Default constructor.
	 */
	public ClassPair() {
		// Default constructor.
	}

	/**
	 * Constructs the pair with two classes.
	 * 
	 * @param classOne
	 *            The first class.
	 * @param classTwo
	 *            The second class.
	 */
	public ClassPair(final Class<?> classOne, final Class<?> classTwo) {
		this.classOne = classOne;
		this.classTwo = classTwo;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		boolean equal = false;
		if (obj instanceof ClassPair) {
			equal = true;
			final ClassPair pair = (ClassPair) obj;
			if (CheckUtil.isNull(this.classOne)) {
				equal &= CheckUtil.isNull(pair.classOne);
			} else {
				equal &= this.classOne.equals(pair.classOne);
			}
			if (CheckUtil.isNull(this.classTwo)) {
				equal &= CheckUtil.isNull(pair.classTwo);
			} else {
				equal &= this.classTwo.equals(pair.classTwo);
			}
		}
		return equal;
	}

	/**
	 * Gets the value for the classOne field.
	 * 
	 * @return The value for the classOne field.
	 */
	public Class<?> getClassOne() {
		return this.classOne;
	}

	/**
	 * Gets the value for the classTwo field.
	 * 
	 * @return The value for the classTwo field.
	 */
	public Class<?> getClassTwo() {
		return this.classTwo;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		int hashCode = 1;
		if (CheckUtil.isNull(this.classOne)) {
			hashCode = (ClassPair.PRIME * hashCode) + this.classOne.hashCode();
		}
		if (CheckUtil.isNull(this.classTwo)) {
			hashCode = (ClassPair.PRIME * hashCode) + this.classTwo.hashCode();
		}
		return hashCode;
	}

	/**
	 * Sets a new value for the classOne field.
	 * 
	 * @param classOne
	 *            The new value for the classOne field.
	 */
	public void setClassOne(final Class<?> classOne) {
		this.classOne = classOne;
	}

	/**
	 * Sets a new value for the classTwo field.
	 * 
	 * @param classTwo
	 *            The new value for the classTwo field.
	 */
	public void setClassTwo(final Class<?> classTwo) {
		this.classTwo = classTwo;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ClassPair[\n\tClassOne: ");
		builder.append(this.classOne);
		builder.append("\n\tClassTwo: ");
		builder.append(this.classTwo);
		builder.append("\n]\n");
		return builder.toString();
	}
}
