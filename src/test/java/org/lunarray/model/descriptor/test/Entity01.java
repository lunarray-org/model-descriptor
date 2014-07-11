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
package org.lunarray.model.descriptor.test;

import java.util.LinkedList;
import java.util.List;

import org.lunarray.model.descriptor.dictionary.annotations.DictionaryKey;
import org.lunarray.model.descriptor.model.annotations.Embedded;

public class Entity01 {

	private static String testStatic;

	@Embedded
	private Entity03 embedded;

	private List<Entity02> entityList;

	private List<String> someList;

	@DictionaryKey("testKey")
	private String value;

	public Entity01() {
		this.embedded = new Entity03();
		this.someList = new LinkedList<String>();
		this.entityList = new LinkedList<Entity02>();
	}

	/**
	 * Gets the value for the testStatic field.
	 * 
	 * @return The value for the testStatic field.
	 */
	public static String getTestStatic() {
		return Entity01.testStatic;
	}

	/**
	 * Sets a new value for the testStatic field.
	 * 
	 * @param testStatic
	 *            The new value for the testStatic field.
	 */
	public static void setTestStatic(final String testStatic) {
		Entity01.testStatic = testStatic;
	}

	public Entity03 getEmbedded() {
		return this.embedded;
	}

	public List<Entity02> getEntityList() {
		return this.entityList;
	}

	public List<String> getSomeList() {
		return this.someList;
	}

	public String getValue() {
		return this.value;
	}

	public void setEmbedded(final Entity03 embedded) {
		this.embedded = embedded;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	protected void setEntityList(final List<Entity02> entityList) {
		this.entityList = entityList;
	}

	protected void setSomeList(final List<String> someList) {
		this.someList = someList;
	}
}
