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
package org.lunarray.model.descriptor.model;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.operation.result.ResultDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.resource.Resource;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.SampleEntity12;
import org.lunarray.model.descriptor.test.domain.SampleEntity13;
import org.lunarray.model.descriptor.test.domain.SampleEntity14;
import org.lunarray.model.descriptor.test.domain.SampleEntity15;
import org.lunarray.model.descriptor.test.domain.SampleEntity25;
import org.lunarray.model.descriptor.test.domain.SampleEntity26;

/**
 * Traverses the model.
 * 
 * @author Pal Hargitai
 */
public class PresentationModelTraverseTest {

	private Model<Object> model;

	@Before
	public void init() throws Exception {
		@SuppressWarnings("unchecked")
		final Resource<Class<? extends Object>> resource = new SimpleClazzResource<Object>(SampleEntity12.class, SampleEntity13.class,
				SampleEntity14.class, SampleEntity15.class, SampleEntity25.class, SampleEntity26.class);
		this.model = PresQualBuilder.createBuilder().resources(resource).build();
	}

	@Test
	public void traverseOperations() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			for (final OperationDescriptor<?> c : e.getOperations()) {
				c.toString();
			}
		}
	}

	@Test
	public void traverseEntities() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			e.toString();
		}
	}

	@Test
	public void traverseParameters() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			for (final OperationDescriptor<?> c : e.getOperations()) {
				for (int i = 0; i < c.getParameterCount(); i++) {
					c.getParameter(i).toString();
				}
			}
		}
	}

	@Test
	public void traverseProperties() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			for (final PropertyDescriptor<?, ?> p : e.getProperties()) {
				p.toString();
			}
		}
	}

	@Test
	public void traverseQualifierOperations() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			final QualifierEntityDescriptor<?> qe = e.adapt(QualifierEntityDescriptor.class);
			for (final Class<?> q : qe.getQualifiers()) {
				for (final OperationDescriptor<?> c : qe.getQualifierEntity(q).getOperations()) {
					c.toString();
				}
			}
		}
	}

	@Test
	public void traverseQualifierEntities() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			final QualifierEntityDescriptor<?> qe = e.adapt(QualifierEntityDescriptor.class);
			for (final Class<?> q : qe.getQualifiers()) {
				qe.getQualifierEntity(q).toString();
			}
		}
	}

	@Test
	public void traverseQualifierParameters() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			final QualifierEntityDescriptor<?> qe = e.adapt(QualifierEntityDescriptor.class);
			for (final Class<?> q : qe.getQualifiers()) {
				for (final OperationDescriptor<?> c : qe.getQualifierEntity(q).getOperations()) {
					for (int i = 0; i < c.getParameterCount(); i++) {
						c.getParameter(i).toString();
					}
				}
			}
		}
	}

	@Test
	public void traverseQualifierProperties() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			final QualifierEntityDescriptor<?> qe = e.adapt(QualifierEntityDescriptor.class);
			for (final Class<?> q : qe.getQualifiers()) {
				for (final PropertyDescriptor<?, ?> p : qe.getQualifierEntity(q).getProperties()) {
					p.toString();
				}
			}
		}
	}

	@Test
	public void traverseQualifierReturns() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			final QualifierEntityDescriptor<?> qe = e.adapt(QualifierEntityDescriptor.class);
			for (final Class<?> q : qe.getQualifiers()) {
				for (final OperationDescriptor<?> c : qe.getQualifierEntity(q).getOperations()) {
					final ResultDescriptor<?> r = c.getResultDescriptor();
					if (r != null) {
						c.getResultDescriptor().toString();
					}
				}
			}
		}
	}

	@Test
	public void traverseReturns() {
		final List<EntityDescriptor<?>> entities = this.model.getEntities();
		for (final EntityDescriptor<?> e : entities) {
			for (final OperationDescriptor<?> c : e.getOperations()) {
				final ResultDescriptor<?> r = c.getResultDescriptor();
				if (r != null) {
					c.getResultDescriptor().toString();
				}
			}
		}
	}
}
