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

import org.junit.Ignore;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.model.ModelImpl;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.test.Entity01;

@Ignore
public class TestCompile {

	private Model<?> model;

	protected void setModel(final Model<?> model) {
		this.model = model;
	}

	public void test01() {
		@SuppressWarnings("unused")
		final Model<?> qm = this.model.adapt(ModelImpl.class);
	}

	public void test02() {
		@SuppressWarnings("unchecked")
		final EntityDescriptor<Entity01> cd = (EntityDescriptor<Entity01>) this.model.getEntity("test");
		@SuppressWarnings({ "unchecked", "unused" })
		final PresentationEntityDescriptor<Entity01> pcd = cd.adapt(PresentationEntityDescriptor.class);
	}
}
