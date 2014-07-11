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
package org.lunarray.model.descriptor.scanner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.simple.SimpleBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.ResourceException;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.scanner.extension.AnnotationMetaModel;
import org.lunarray.model.descriptor.scanner.model.Entity01;
import org.lunarray.model.descriptor.scanner.model.Entity02;
import org.lunarray.model.descriptor.scanner.model.Entity03;
import org.lunarray.model.descriptor.scanner.model.Entity04;
import org.lunarray.model.descriptor.scanner.model.TestEntity01;
import org.lunarray.model.descriptor.scanner.model.TestEntity02;

/**
 * Annotation scanner test.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see AnnotationMetaModelProcessor
 * @sse AnnotationMetaModel
 */
public class AnnotationScannerModelTest {

	/** Model resources. */
	private Resource<Class<? extends Object>> resource;

	/** Setup test. */
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		this.resource = new SimpleClazzResource<Object>(TestEntity01.class, TestEntity02.class);
	}

	/** Test annotation meta model. */
	@SuppressWarnings("unchecked")
	@Test
	public void testAnnotationMetaModelEntity() throws ResourceException {
		final AnnotationMetaModelProcessor<Object> visitorBuilder = new AnnotationMetaModelProcessor<Object>();
		visitorBuilder.registerAnnotationEntity(Entity01.class);
		visitorBuilder.registerAnnotationEntity(Entity02.class);
		visitorBuilder.registerAnnotationEntity(Entity03.class);
		visitorBuilder.registerAnnotationEntity(Entity04.class);

		final Model<Object> model = SimpleBuilder.createBuilder().postProcessors(visitorBuilder).resources(this.resource).build();
		final AnnotationMetaModel<?, ?> amm01 = model.getEntity(TestEntity01.class).extension(AnnotationMetaModel.class);
		Assert.assertEquals(
				"[Entity03(annotationValues=[Entity04, Entity04], byteValues[4, 5], charValues[a, c], clazzValues[class "
						+ "org.lunarray.model.descriptor.test.Entity02, class org.lunarray.model.descriptor.test.Entity03], doubleValues[6.0, 7.0], "
						+ "enumValues[value_01, value_02], floatValues[8.0, 9.0], intValues[10, 11], longValues[12, 13], shortValues[14, 15], "
						+ "stringValues[test01, test02])]", amm01.getValues(Entity03.class).toString());
		Assert.assertEquals("[Entity01[test]]", amm01.getValues(Entity01.class).toString());
		final AnnotationMetaModel<?, ?> amm02 = model.getEntity(TestEntity02.class).extension(AnnotationMetaModel.class);
		Assert.assertEquals("[Entity04]", amm02.getValues(Entity04.class).toString());
		final List<Entity01> entities = amm02.getValues(Entity01.class);
		Assert.assertEquals(2, entities.size());
		final Set<String> stringSet = new HashSet<String>();
		for (final Entity01 e : entities) {
			stringSet.add(e.toString());
		}
		Assert.assertTrue(stringSet.contains("Entity01[testValue1, testValue2]"));
		Assert.assertTrue(stringSet.contains("Entity01[6, 8, 13]"));
		Assert.assertEquals(
				"[Entity02(value=[6, 8, 13], java.lang.Integer, value_02, Entity01[InnerTestValue01], [Entity01[TestCollection01], "
						+ "Entity01[TestCollection02], Entity01[TestCollection03]])]", amm02.getValues(Entity02.class).toString());
	}

	/** Test annotation meta model. */
	@SuppressWarnings("unchecked")
	@Test
	public void testAnnotationMetaModelProperty() throws ResourceException {
		final AnnotationMetaModelProcessor<Object> visitorBuilder = new AnnotationMetaModelProcessor<Object>();
		visitorBuilder.registerAnnotationEntity(Entity01.class);
		visitorBuilder.registerAnnotationEntity(Entity02.class);
		visitorBuilder.registerAnnotationEntity(Entity03.class);
		visitorBuilder.registerAnnotationEntity(Entity04.class);

		final Model<Object> model = SimpleBuilder.createBuilder().postProcessors(visitorBuilder).resources(this.resource).build();
		final AnnotationMetaModel<?, ?> amm01 = model.getEntity(TestEntity01.class).getProperty("someField")
				.extension(AnnotationMetaModel.class);
		Assert.assertEquals("[Entity01[test01]]", amm01.getValues().toString());
		final AnnotationMetaModel<?, ?> amm02 = model.getEntity(TestEntity02.class).getProperty("testProperty")
				.extension(AnnotationMetaModel.class);
		final List<Entity01> entities = amm02.getValues(Entity01.class);
		Assert.assertEquals(2, entities.size());
		final Set<String> stringSet = new HashSet<String>();
		for (final Entity01 e : entities) {
			stringSet.add(e.toString());
		}
		Assert.assertTrue(stringSet.contains("Entity01[testValue3, testValue4]"));
		Assert.assertTrue(stringSet.contains("Entity01[20]"));
		Assert.assertEquals("[Entity02(value=[20], java.lang.Integer, value_02, Entity01[InnerTestValue04], [Entity01[TestCollection06], "
				+ "Entity01[TestCollection07], Entity01[TestCollection08]])]", amm02.getValues(Entity02.class).toString());
	}
}
