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
package org.lunarray.model.descriptor.dictionary.keys.annotation;

import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.accessor.entity.DescribedEntity;
import org.lunarray.model.descriptor.accessor.operation.DescribedOperation;
import org.lunarray.model.descriptor.accessor.property.DescribedProperty;
import org.lunarray.model.descriptor.dictionary.annotations.DictionaryKey;
import org.lunarray.model.descriptor.model.ModelProcessor;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.operation.OperationExtension;
import org.lunarray.model.descriptor.model.property.PropertyExtension;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * A key post processor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The models' entity super type.
 */
public final class KeyedDictionaryPostProcessor<S>
		implements ModelProcessor<S> {

	/** Default constructor. */
	public KeyedDictionaryPostProcessor() {
		// Default constructor.
	}

	/** {@inheritDoc} */
	@Override
	public <E extends S> EntityExtension<E> process(final DescribedEntity<E> type, final ExtensionContainer extensionContainer) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends S> OperationExtension<E> process(final DescribedOperation entityType, final ExtensionContainer extensionContainer) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public <E extends S, P> PropertyExtension<P, E> process(final DescribedProperty<P> property, final ExtensionContainer extensionContainer) {
		Validate.notNull(property, "Property may not be null.");
		String keyValue = "";
		if (property.isAnnotationPresent(DictionaryKey.class)) {
			final Iterator<DictionaryKey> candidates = property.getAnnotation(DictionaryKey.class).iterator();
			while (StringUtil.isEmptyString(keyValue) && candidates.hasNext()) {
				keyValue = candidates.next().value();
			}
		}
		DictionaryKeyHolder<P, E> holder = null;
		if (!StringUtil.isEmptyString(keyValue)) {
			holder = new DictionaryKeyHolder<P, E>(keyValue);
		}
		return holder;
	}

	/** {@inheritDoc} */
	@Override
	public Extension process(final ExtensionContainer extensionContainer) {
		return null;
	}
}
