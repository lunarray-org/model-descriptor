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
package org.lunarray.model.descriptor.adapter;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.builder.annotation.resolver.ResolverFactory;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.dictionary.enumeration.EnumDictionary;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.model.property.CollectionPropertyDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.model.relation.RelationDescriptor;
import org.lunarray.model.descriptor.objectfactory.simple.SimpleObjectFactory;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationPropertyDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.Entity01;
import org.lunarray.model.descriptor.test.Entity02;
import org.lunarray.model.descriptor.test.Entity04;

public class TestPresentationQualifierModel {

	@Test
	public void test01() throws Exception {
		@SuppressWarnings("unchecked")
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>(Entity01.class, Entity02.class);
		final Model<Object> model = PresQualBuilder.createBuilder().extensions(new SimpleObjectFactory()).resources(resource).build();
		Assert.assertNotNull(model);
		for (final EntityDescriptor<?> clazz : model.getEntities()) {
			Assert.assertNotNull(clazz);
			for (final PropertyDescriptor<?, ?> field : clazz.getProperties()) {
				Assert.assertNotNull(field);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test02() throws Exception {
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>(Entity01.class, Entity02.class);

		final Model<Object> model = PresQualBuilder.createBuilder().propertyResolver(ResolverFactory.accessorPropertyResolver())
				.extensions(new SimpleObjectFactory()).resources(resource).build();

		final Entity01 e = new Entity01();

		final EntityDescriptor<Entity01> clazz = model.getEntity(Entity01.class);

		final PropertyDescriptor<String, Entity01> field1 = clazz.getProperty("embedded.identity", String.class);
		field1.setValue(e, "test");
		Assert.assertEquals("test", e.getEmbedded().getIdentity());
		final PresentationPropertyDescriptor<String, Entity01> field1Presentation = field1.adapt(PresentationPropertyDescriptor.class);
		Assert.assertNotNull(field1Presentation);
		Assert.assertEquals("Identity", field1Presentation.getDescription());
		Assert.assertTrue(field1Presentation.isRequiredIndication());
		Assert.assertFalse(field1Presentation.isImmutable());
		final PropertyDescriptor<List<String>, Entity01> field2 = (PropertyDescriptor<List<String>, Entity01>) clazz
				.getProperty("someList");
		field2.getPropertyType();
		field2.getCardinality();
		final CollectionPropertyDescriptor<String, List<String>, Entity01> field3 = field2.adapt(CollectionPropertyDescriptor.class);
		field3.getPropertyType();
		field3.getCollectionType();
		field3.addValue(e, "test");
		Assert.assertTrue(field3.containsValue(e, "test"));
		Assert.assertEquals(1, field3.valueSize(e));
		Assert.assertFalse(field3.isRelation());

		final PropertyDescriptor<List<Entity02>, Entity01> field4 = (PropertyDescriptor<List<Entity02>, Entity01>) clazz
				.getProperty("entityList");
		field4.getPropertyType();
		field4.getCardinality();
		final CollectionPropertyDescriptor<Entity02, List<Entity02>, Entity01> field5 = field4.adapt(CollectionPropertyDescriptor.class);
		field5.getPropertyType();
		field5.getCollectionType();
		final Entity02 e02 = new Entity02();
		field5.addValue(e, e02);
		Assert.assertTrue(field5.isRelation());
		Assert.assertTrue(field5.containsValue(e, e02));
		Assert.assertEquals(1, field5.valueSize(e));

		final RelationDescriptor field6 = field4.adapt(RelationDescriptor.class);
		Assert.assertNotNull(field6.getRelatedName());
		field6.getRelatedName();

		final PropertyDescriptor<String, Entity01> field7 = clazz.getProperty("embedded.constantValue", String.class);
		final PresentationPropertyDescriptor<String, Entity01> field7Presentation = field7.adapt(PresentationPropertyDescriptor.class);
		Assert.assertTrue(field7Presentation.isImmutable());

		final PropertyDescriptor<Boolean, Entity01> field8 = clazz.getProperty("embedded.checkItem", Boolean.TYPE);
		final PresentationPropertyDescriptor<String, Entity01> field8Presentation = field8.adapt(PresentationPropertyDescriptor.class);
		Assert.assertEquals(RenderType.CHECKBOX, field8Presentation.getRenderType());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test03() throws Exception {
		@SuppressWarnings("rawtypes")
		final Resource<Class<? extends Object>> resource = new SimpleClazzResource(Entity04.class);
		final Model<Object> model = PresQualBuilder.createBuilder().extensions(new SimpleObjectFactory()).resources(resource).build();
		final KeyedEntityDescriptor<Entity04, String> clazz = model.getEntity(Entity04.class).adapt(KeyedEntityDescriptor.class);
		Assert.assertNotNull(clazz.getKeyProperty());
		for (final PropertyDescriptor<?, Entity04> fd : clazz.getProperties()) {
			Assert.assertNotNull(fd);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test04() throws Exception {
		@SuppressWarnings("rawtypes")
		final Resource<Class<? extends Object>> resource = new SimpleClazzResource(Entity04.class);
		final Model<Object> model = PresQualBuilder.createBuilder().extensions(new SimpleObjectFactory()).resources(resource).build();
		final EntityDescriptor<Entity04> clazz = model.getEntity(Entity04.class);
		final Dictionary dictionary = new EnumDictionary(null);
		final Collection<Entity04> entities = dictionary.lookup(clazz);
		Assert.assertEquals(2, entities.size());
		final KeyedEntityDescriptor<Entity04, String> cd = clazz.adapt(KeyedEntityDescriptor.class);
		final Entity04 entity = dictionary.lookup(cd, "TEST_01");
		Assert.assertNotNull(entity);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test05() throws Exception {
		@SuppressWarnings("rawtypes")
		final Resource resource = new SimpleClazzResource(Entity01.class);
		final Model<Object> model = PresQualBuilder.createBuilder().extensions(new SimpleObjectFactory()).resources(resource).build();

		final EntityDescriptor<Entity01> clazz = model.getEntity(Entity01.class);

		final PresentationEntityDescriptor<Entity01> pClazz = clazz.adapt(PresentationEntityDescriptor.class);

		pClazz.getProperty("embedded.identity").adapt(PresentationPropertyDescriptor.class);
	}
}
