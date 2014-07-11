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
package org.lunarray.model.descriptor.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.Builder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.objectfactory.InstanceException;
import org.lunarray.model.descriptor.objectfactory.ObjectFactory;
import org.lunarray.model.descriptor.objectfactory.simple.SimpleObjectFactory;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.Entity01;
import org.lunarray.model.descriptor.test.Entity02;
import org.lunarray.model.descriptor.test.Entity03;
import org.lunarray.model.descriptor.test.Entity04;

/**
 * Tests model serialization.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public class SerializationTest {

	/** Copy buffer size. */
	private static final int BUFFER_SIZE = 10240;
	/** The model. */
	private Model<Object> model;

	/** Setup test. */
	@Before
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final Resource<Class<? extends Object>> resource = new SimpleClazzResource<Object>(Entity01.class, Entity02.class, Entity03.class,
				Entity04.class);
		final Builder<Class<? extends Object>, Object, ModelImpl<Object>, ?> builder = PresQualBuilder.createBuilder();
		builder.extensions(new TestObjectFactory());
		this.model = builder.resources(resource).build();
	}

	/** Test save serialization. */
	@Test
	public void serializeSafe() throws Exception {
		final PropertyDescriptor<String, Entity01> instanceDescriptor = this.model.getEntity(Entity01.class).getProperty(
				"embedded.identity", String.class);
		final Entity01 instanceE = new Entity01();
		instanceE.setEmbedded(null);
		instanceDescriptor.setValue(instanceE, "test");
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(SerializationTest.BUFFER_SIZE);
		final ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this.model);
		final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		final ObjectInputStream ois = new ObjectInputStream(bais);
		final Object o = ois.readObject();
		Assert.assertTrue(o instanceof Model);
		@SuppressWarnings("unchecked")
		final Model<Object> unserialized = (Model<Object>) o;
		unserialized.getExtensionContainer().updateExtensions(Collections.<ExtensionRef<?>>emptySet(),
				Collections.<Extension>singleton(new TestObjectFactory()));
		final PropertyDescriptor<String, Entity01> unserializedDescriptor = unserialized.getEntity(Entity01.class).getProperty(
				"embedded.identity", String.class);
		final Entity01 unserializedE = new Entity01();
		unserializedE.setEmbedded(null);
		unserializedDescriptor.setValue(unserializedE, "test");
	}

	/** Test unsafe serialization. */
	@Test(expected = NullPointerException.class)
	public void serializeUnsafe() throws Exception {
		final PropertyDescriptor<String, Entity01> instanceDescriptor = this.model.getEntity(Entity01.class).getProperty(
				"embedded.identity", String.class);
		final Entity01 instanceE = new Entity01();
		instanceE.setEmbedded(null);
		instanceDescriptor.setValue(instanceE, "test");
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(SerializationTest.BUFFER_SIZE);
		final ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this.model);
		final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		final ObjectInputStream ois = new ObjectInputStream(bais);
		final Object o = ois.readObject();
		Assert.assertTrue(o instanceof Model);
		@SuppressWarnings("unchecked")
		final Model<Object> unserialized = (Model<Object>) o;
		final PropertyDescriptor<String, Entity01> unserializedDescriptor = unserialized.getEntity(Entity01.class).getProperty(
				"embedded.identity", String.class);
		final Entity01 unserializedE = new Entity01();
		unserializedE.setEmbedded(null);
		unserializedDescriptor.setValue(unserializedE, "test");
	}

	/**
	 * Test object factory.
	 * 
	 * @author Pal Hargitai (pal@lunarray.org)
	 */
	public class TestObjectFactory
			implements ObjectFactory {

		/** Delegate object factory. */
		private final SimpleObjectFactory simpleObjectFactory = new SimpleObjectFactory();

		/** {@inheritDoc} */
		@Override
		public <O> O getInstance(final Class<O> type) throws InstanceException {
			return this.simpleObjectFactory.getInstance(type);
		}
	}
}
