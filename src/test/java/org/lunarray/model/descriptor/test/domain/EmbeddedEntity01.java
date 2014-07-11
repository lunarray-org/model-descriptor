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
package org.lunarray.model.descriptor.test.domain;

import java.util.LinkedList;
import java.util.List;

public class EmbeddedEntity01<S, T> {

	public class OtherCollection {
		private T test;
		private List<S> testList;

		public T getTest() {
			return this.test;
		}

		public List<S> getTestList() {
			return this.testList;
		}

		public void setTest(final T test) {
			this.test = test;
		}

		public void setTestList(final List<S> testList) {
			this.testList = testList;
		}
	}

	private S anElement;

	private List<T> testList = new LinkedList<T>();

	public S getAnElement() {
		return this.anElement;
	}

	public List<T> getTestList() {
		return this.testList;
	}

	public void setAnElement(final S anElement) {
		this.anElement = anElement;
	}

	public void setTestList(final List<T> testList) {
		this.testList = testList;
	}
}