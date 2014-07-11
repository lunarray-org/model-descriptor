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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lunarray.model.descriptor.model.annotations.Embedded;

public class SampleEntity10 implements ModelMarker {

	public class EmbeddedCollection<S, T> {

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

	public interface OtherCollection<S, T> extends Collection<T> {

	}

	public class SomeCollection<S, T> implements Collection<T> {

		private List<S> otherlist;

		@Override
		public boolean add(final T e) {
			return false;
		}

		@Override
		public boolean addAll(final Collection<? extends T> c) {
			return false;
		}

		@Override
		public void clear() {

		}

		@Override
		public boolean contains(final Object o) {
			return false;
		}

		@Override
		public boolean containsAll(final Collection<?> c) {
			return false;
		}

		public List<S> getOtherlist() {
			return this.otherlist;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public Iterator<T> iterator() {
			return null;
		}

		@Override
		public boolean remove(final Object o) {
			return false;
		}

		@Override
		public boolean removeAll(final Collection<?> c) {
			return false;
		}

		@Override
		public boolean retainAll(final Collection<?> c) {
			return false;
		}

		public void setOtherlist(final List<S> otherlist) {
			this.otherlist = otherlist;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public Object[] toArray() {
			return null;
		}

		@SuppressWarnings("hiding")
		@Override
		public <T> T[] toArray(final T[] a) {
			return null;
		}
	}

	public class StringList extends LinkedList<String> {

		/** Serial id. */
		private static final long serialVersionUID = 1288579125017295786L;

	}

	public class TestErasure<S, T> extends EmbeddedCollection<Integer, String> {

	}

	private EmbeddedEntity01<Integer, String> embeddedEntity;
	private TestErasure<Double, Boolean> erasureTest;
	private List<String> simpleStringList = new LinkedList<String>();
	private Set<String> simpleStringSet = new HashSet<String>();
	private StringList stringList = new StringList();
	private SomeCollection<String, Integer> testCollection = new SomeCollection<String, Integer>();
	@Embedded
	private EmbeddedCollection<Integer, String> testList = new EmbeddedCollection<Integer, String>();
	private OtherCollection<String, Integer> testOtherCollection;

	public EmbeddedEntity01<Integer, String> getEmbeddedEntity() {
		return this.embeddedEntity;
	}

	public TestErasure<Double, Boolean> getErasureTest() {
		return this.erasureTest;
	}

	public List<String> getSimpleStringList() {
		return this.simpleStringList;
	}

	public Set<String> getSimpleStringSet() {
		return this.simpleStringSet;
	}

	public StringList getStringList() {
		return this.stringList;
	}

	public SomeCollection<String, Integer> getTestCollection() {
		return this.testCollection;
	}

	public EmbeddedCollection<Integer, String> getTestList() {
		return this.testList;
	}

	public OtherCollection<String, Integer> getTestOtherCollection() {
		return this.testOtherCollection;
	}

	public void setEmbeddedEntity(final EmbeddedEntity01<Integer, String> embeddedEntity) {
		this.embeddedEntity = embeddedEntity;
	}

	public void setErasureTest(final TestErasure<Double, Boolean> erasureTest) {
		this.erasureTest = erasureTest;
	}

	public void setSimpleStringList(final List<String> simpleStringList) {
		this.simpleStringList = simpleStringList;
	}

	public void setSimpleStringSet(final Set<String> simpleStringSet) {
		this.simpleStringSet = simpleStringSet;
	}

	public void setStringList(final StringList stringList) {
		this.stringList = stringList;
	}

	public void setTestCollection(final SomeCollection<String, Integer> testCollection) {
		this.testCollection = testCollection;
	}

	public void setTestList(final EmbeddedCollection<Integer, String> testList) {
		this.testList = testList;
	}

	public void setTestOtherCollection(final OtherCollection<String, Integer> testOtherCollection) {
		this.testOtherCollection = testOtherCollection;
	}
}
