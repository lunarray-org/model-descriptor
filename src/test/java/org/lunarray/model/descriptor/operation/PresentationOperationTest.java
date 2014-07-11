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
package org.lunarray.model.descriptor.operation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lunarray.model.descriptor.builder.annotation.presentation.builder.PresQualBuilder;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.presentation.RelationPresentationDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationOperationDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationEntityDescriptor;
import org.lunarray.model.descriptor.presentation.PresentationResultDescriptor;
import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.qualifier.QualifierEntityDescriptor;
import org.lunarray.model.descriptor.resource.simpleresource.SimpleClazzResource;
import org.lunarray.model.descriptor.test.domain.Qualifier01;
import org.lunarray.model.descriptor.test.domain.SampleEntity25;
import org.lunarray.model.descriptor.test.domain.SampleEntity26;

public class PresentationOperationTest {

	private Model<Object> model;

	@Before
	public void init() throws Exception {
		final SimpleClazzResource<Object> resource = new SimpleClazzResource<Object>();
		resource.addClazz(SampleEntity25.class);
		resource.addClazz(SampleEntity26.class);
		this.model = PresQualBuilder.createBuilder().resources(resource).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOperation() {
		final PresentationEntityDescriptor<SampleEntity25> qed = this.model.getEntity(SampleEntity25.class).adapt(
				PresentationEntityDescriptor.class);
		final PresentationEntityDescriptor<SampleEntity25> qed1 = this.model.getEntity(SampleEntity25.class)
				.adapt(QualifierEntityDescriptor.class).getQualifierEntity(Qualifier01.class).adapt(PresentationEntityDescriptor.class);
		Assert.assertEquals(4, qed.getOperations().size());
		Assert.assertEquals(4, qed.getMembers().size());
		Assert.assertEquals(4, qed.getOrderedOperations().size());
		this.assertOrder(qed.getOrderedOperations(), "someMethod4", "someMethod2", "someMethod3", "someMethod");
		Assert.assertEquals(4, qed.getOrderedMembers().size());
		Assert.assertEquals(0, qed.getOrderedProperties().size());
		Assert.assertEquals(4, qed1.getOperations().size());
		Assert.assertEquals(4, qed1.getOrderedOperations().size());
		Assert.assertEquals(4, qed1.getOrderedMembers().size());
		Assert.assertEquals(0, qed1.getOrderedProperties().size());
		this.assertOrder(qed1.getOrderedOperations(), "someMethod4", "someMethod3", "someMethod2", "someMethod");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPresentation() {
		final PresentationEntityDescriptor<SampleEntity26> qed = this.model.getEntity(SampleEntity26.class).adapt(
				PresentationEntityDescriptor.class);
		final PresentationEntityDescriptor<SampleEntity26> qed1 = this.model.getEntity(SampleEntity26.class)
				.adapt(QualifierEntityDescriptor.class).getQualifierEntity(Qualifier01.class).adapt(PresentationEntityDescriptor.class);
		this.assertOperationInline(qed1, "someMethod", true);
		this.assertOperationInline(qed1, "someMethod3", true);
		this.assertOperationInline(qed1, "someMethod5", false);
		this.assertOperationInline(qed1, "someMethod7", false);
		this.assertOperation(qed1, "someMethod", "", RenderType.DROPDOWN, "SampleEntity26.someMethod", "SampleEntity26.someMethod",
				"SampleEntity26.someMethod");
		this.assertOperation(qed1, "someMethod2", "", RenderType.TEXT, "SampleEntity26.someMethod2", "SampleEntity26.someMethod2",
				"SampleEntity26.someMethod2");
		this.assertOperation(qed1, "someMethod3", "test", RenderType.DROPDOWN, "SampleEntity26.someMethod3", "SampleEntity26.someMethod3",
				"SampleEntity26.someMethod3");
		this.assertOperation(qed1, "someMethod4", "", RenderType.TEXT, "SampleEntity26.someMethod4", "SampleEntity26.someMethod4",
				"SampleEntity26.someMethod4");
		this.assertOperation(qed1, "someMethod5", "", RenderType.PICKLIST, "SampleEntity26.someMethod5", "SampleEntity26.someMethod5",
				"SampleEntity26.someMethod5");
		this.assertOperation(qed1, "someMethod6", "test2", RenderType.TEXT, "SampleEntity26.someMethod6", "SampleEntity26.someMethod6",
				"SampleEntity26.someMethod6");
		this.assertOperation(qed1, "someMethod7", "", RenderType.PICKLIST, "SampleEntity26.someMethod7", "SampleEntity26.someMethod7",
				"SampleEntity26.someMethod7");
		this.assertOperationInline(qed, "someMethod", false);
		this.assertOperationInline(qed, "someMethod3", true);
		this.assertOperationInline(qed, "someMethod5", false);
		this.assertOperationInline(qed, "someMethod7", false);
		this.assertOperation(qed, "someMethod", "", RenderType.DROPDOWN, "SampleEntity26.someMethod", "SampleEntity26.someMethod",
				"SampleEntity26.someMethod");
		this.assertOperation(qed, "someMethod2", "", RenderType.TEXT, "SampleEntity26.someMethod2", "SampleEntity26.someMethod2",
				"SampleEntity26.someMethod2");
		this.assertOperation(qed, "someMethod3", "test3", RenderType.DROPDOWN, "SampleEntity26.someMethod3", "SampleEntity26.someMethod3",
				"SampleEntity26.someMethod3");
		this.assertOperation(qed, "someMethod4", "", RenderType.TEXT, "SampleEntity26.someMethod4", "SampleEntity26.someMethod4",
				"SampleEntity26.someMethod4");
		this.assertOperation(qed, "someMethod5", "", RenderType.PICKLIST, "SampleEntity26.someMethod5", "SampleEntity26.someMethod5",
				"SampleEntity26.someMethod5");
		this.assertOperation(qed, "someMethod6", "test2", RenderType.TEXT, "SampleEntity26.someMethod6", "SampleEntity26.someMethod6",
				"SampleEntity26.someMethod6");
		this.assertOperation(qed, "someMethod7", "", RenderType.PICKLIST, "SampleEntity26.someMethod7", "SampleEntity26.someMethod7",
				"SampleEntity26.someMethod7");
	}

	private void assertOperation(final PresentationEntityDescriptor<SampleEntity26> qed, final String operation, final String format,
			final RenderType renderType, final String button, final String buttonLocale, final String buttonKey) {
		Assert.assertEquals(format, qed.getOperation(operation).getResultDescriptor().adapt(PresentationResultDescriptor.class).getFormat());
		Assert.assertEquals(renderType, qed.getOperation(operation).getResultDescriptor().adapt(PresentationResultDescriptor.class)
				.getRenderType());
		Assert.assertEquals(button, qed.getOperation(operation).adapt(PresentationOperationDescriptor.class).getButton());
		Assert.assertEquals(buttonLocale, qed.getOperation(operation).adapt(PresentationOperationDescriptor.class).getButton(Locale.GERMAN));
		Assert.assertEquals(buttonKey, qed.getOperation(operation).adapt(PresentationOperationDescriptor.class).getButtonKey());
	}

	private void assertOperationInline(final PresentationEntityDescriptor<SampleEntity26> qed, final String operation, final boolean inline) {
		Assert.assertEquals(inline, qed.getOperation(operation).getResultDescriptor().adapt(RelationPresentationDescriptor.class)
				.isInLineIndication());
	}

	private void assertOrder(final List<OperationDescriptor<SampleEntity25>> operations, final String... expected) {
		final Iterator<String> expectedIt = Arrays.asList(expected).iterator();
		final Iterator<OperationDescriptor<SampleEntity25>> operationsIt = operations.iterator();
		while (expectedIt.hasNext()) {
			final String expects = expectedIt.next();
			Assert.assertTrue(expects, operationsIt.hasNext());
			Assert.assertEquals(expects, operationsIt.next().getName());
		}
		String cmd = null;
		if (operationsIt.hasNext()) {
			cmd = operationsIt.next().getName();
		}
		Assert.assertFalse(cmd, operationsIt.hasNext());
	}
}
