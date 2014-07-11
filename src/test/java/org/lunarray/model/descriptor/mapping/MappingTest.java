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
package org.lunarray.model.descriptor.mapping;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.converter.def.DefaultConverterTool;
import org.lunarray.model.descriptor.dictionary.Dictionary;
import org.lunarray.model.descriptor.mapping.builder.ModelConversionStrategyBuilder;
import org.lunarray.model.descriptor.mapping.entities1.Entity01;
import org.lunarray.model.descriptor.mapping.entities3.Entity03;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.objectfactory.simple.SimpleObjectFactory;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;

public class MappingTest {

	private ModelConversionTool conversionTool;
	private Dictionary dictionary;
	private Model<Object> model1;
	private Model<Object> model2;
	private Model<Object> model3;

	@SuppressWarnings("unchecked")
	@Before
	public void init() throws Exception {
		this.dictionary = EasyMock.createMock(Dictionary.class);
		this.model1 = PresQualBuilder
				.createBuilder()
				.extensions(new SimpleObjectFactory(), new DefaultConverterTool(), this.dictionary)
				.resources(
						new SimpleClazzResource<Object>(org.lunarray.model.descriptor.mapping.entities1.Entity01.class,
								org.lunarray.model.descriptor.mapping.entities1.Entity02.class,
								org.lunarray.model.descriptor.mapping.entities1.Entity03.class)).build();
		this.model2 = PresQualBuilder
				.createBuilder()
				.extensions(new SimpleObjectFactory(), new DefaultConverterTool(), this.dictionary)
				.resources(
						new SimpleClazzResource<Object>(org.lunarray.model.descriptor.mapping.entities2.Entity01.class,
								org.lunarray.model.descriptor.mapping.entities2.Entity02.class,
								org.lunarray.model.descriptor.mapping.entities2.Entity03.class)).build();
		this.model3 = PresQualBuilder
				.createBuilder()
				.extensions(new SimpleObjectFactory(), new DefaultConverterTool(), this.dictionary)
				.resources(
						new SimpleClazzResource<Object>(org.lunarray.model.descriptor.mapping.entities3.Entity01.class,
								org.lunarray.model.descriptor.mapping.entities3.Entity02.class,
								org.lunarray.model.descriptor.mapping.entities3.Entity03.class)).build();
		EasyMock.reset(this.dictionary);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testNoDictionary() throws Exception {
		this.conversionTool = ModelConversionStrategyBuilder.create().addModel(this.model1).addModel(this.model2).addModel(this.model3)
				.build();
		final org.lunarray.model.descriptor.mapping.entities2.Entity03 e2 = new org.lunarray.model.descriptor.mapping.entities2.Entity03();
		final Entity03 e3 = new org.lunarray.model.descriptor.mapping.entities3.Entity03();
		e2.setValue01("key01");
		e3.setValue01("key01");
		EasyMock.expect(
				this.dictionary.lookup(
						(KeyedEntityDescriptor<org.lunarray.model.descriptor.mapping.entities2.Entity03, String>) EasyMock.notNull(),
						EasyMock.eq("key01"))).andReturn(e2);
		EasyMock.replay(this.dictionary);
		final org.lunarray.model.descriptor.mapping.entities1.Entity01 entity011 = Entity01.SAMPLE_01;
		final org.lunarray.model.descriptor.mapping.entities2.Entity01 entity012 = this.conversionTool.convert(entity011,
				org.lunarray.model.descriptor.mapping.entities2.Entity01.class);
		final org.lunarray.model.descriptor.mapping.entities3.Entity01 entity013 = this.conversionTool.convert(entity012,
				org.lunarray.model.descriptor.mapping.entities3.Entity01.class);
		final org.lunarray.model.descriptor.mapping.entities1.Entity01 entity021 = this.conversionTool.convert(entity013,
				org.lunarray.model.descriptor.mapping.entities1.Entity01.class);
		Assert.assertEquals(entity011, entity021);
		EasyMock.verify(this.dictionary);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPreferDictionary() throws Exception {
		this.conversionTool = ModelConversionStrategyBuilder.create().preferDictionary().addModel(this.model1).addModel(this.model2)
				.addModel(this.model3).build();
		final Entity03 e3 = new org.lunarray.model.descriptor.mapping.entities3.Entity03();
		e3.setValue01("key01");
		final org.lunarray.model.descriptor.mapping.entities2.Entity03 e2 = new org.lunarray.model.descriptor.mapping.entities2.Entity03();
		e2.setValue01("key01");
		EasyMock.expect(
				this.dictionary.lookup(
						(KeyedEntityDescriptor<org.lunarray.model.descriptor.mapping.entities2.Entity03, String>) EasyMock.notNull(),
						EasyMock.eq("key01"))).andReturn(e2);
		EasyMock.expect(
				this.dictionary.lookup(
						(KeyedEntityDescriptor<org.lunarray.model.descriptor.mapping.entities3.Entity03, String>) EasyMock.notNull(),
						EasyMock.eq("key01"))).andReturn(e3);
		EasyMock.replay(this.dictionary);
		final org.lunarray.model.descriptor.mapping.entities1.Entity01 entity011 = Entity01.SAMPLE_01;
		final org.lunarray.model.descriptor.mapping.entities2.Entity01 entity012 = this.conversionTool.convert(entity011,
				org.lunarray.model.descriptor.mapping.entities2.Entity01.class);
		final org.lunarray.model.descriptor.mapping.entities3.Entity01 entity013 = this.conversionTool.convert(entity012,
				org.lunarray.model.descriptor.mapping.entities3.Entity01.class);
		final org.lunarray.model.descriptor.mapping.entities1.Entity01 entity021 = this.conversionTool.convert(entity013,
				org.lunarray.model.descriptor.mapping.entities1.Entity01.class);
		Assert.assertEquals(entity011, entity021);
		EasyMock.verify(this.dictionary);
	}
}
