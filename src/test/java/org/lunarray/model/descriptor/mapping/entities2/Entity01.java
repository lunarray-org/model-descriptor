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
package org.lunarray.model.descriptor.mapping.entities2;

import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Entity01 {

	private Entity03 key0301;
	private String value01;
	private Long value02;
	private Entity02 value03;

	private Calendar value04;

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * Gets the value for the key0301 field.
	 * 
	 * @return The value for the key0301 field.
	 */
	public Entity03 getKey0301() {
		return this.key0301;
	}

	/**
	 * Gets the value for the value01 field.
	 * 
	 * @return The value for the value01 field.
	 */
	public String getValue01() {
		return this.value01;
	}

	/**
	 * Gets the value for the value02 field.
	 * 
	 * @return The value for the value02 field.
	 */
	public final Long getValue02() {
		return this.value02;
	}

	/**
	 * Gets the value for the value03 field.
	 * 
	 * @return The value for the value03 field.
	 */
	public Entity02 getValue03() {
		return this.value03;
	}

	/**
	 * Gets the value for the value04 field.
	 * 
	 * @return The value for the value04 field.
	 */
	public final Calendar getValue04() {
		return this.value04;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * Sets a new value for the key0301 field.
	 * 
	 * @param key0301
	 *            The new value for the key0301 field.
	 */
	public void setKey0301(final Entity03 key0301) {
		this.key0301 = key0301;
	}

	/**
	 * Sets a new value for the value01 field.
	 * 
	 * @param value01
	 *            The new value for the value01 field.
	 */
	public void setValue01(final String value01) {
		this.value01 = value01;
	}

	/**
	 * Sets a new value for the value02 field.
	 * 
	 * @param value02
	 *            The new value for the value02 field.
	 */
	public final void setValue02(final Long value02) {
		this.value02 = value02;
	}

	/**
	 * Sets a new value for the value03 field.
	 * 
	 * @param value03
	 *            The new value for the value03 field.
	 */
	public void setValue03(final Entity02 value03) {
		this.value03 = value03;
	}

	/**
	 * Sets a new value for the value04 field.
	 * 
	 * @param value04
	 *            The new value for the value04 field.
	 */
	public final void setValue04(final Calendar value04) {
		this.value04 = value04;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
