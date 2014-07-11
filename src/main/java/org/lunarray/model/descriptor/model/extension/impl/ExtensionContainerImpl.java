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
package org.lunarray.model.descriptor.model.extension.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.model.extension.HardExtensionRef;
import org.lunarray.model.descriptor.util.ExtensionUtil;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * A container for extensions.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class ExtensionContainerImpl
		implements Serializable, Externalizable, ExtensionContainer {

	/** Validation message. */
	private static final String EXTENSIONS_NULL = "Extensions may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = 5853153086364016888L;
	/** Validation message. */
	private static final String TYPE_NULL = "Type may not be null.";
	/** The extension references. */
	private transient Map<Class<? extends Extension>, ExtensionRef<?>> extensionReferences;
	/** Indicates the container has been initialized. */
	private transient boolean initialized;

	/**
	 * Default constructor.
	 */
	public ExtensionContainerImpl() {
		// Default constructor.
	}

	/**
	 * Default constructor.
	 * 
	 * @param extensions
	 *            The extensions. May not be null.
	 */
	public ExtensionContainerImpl(final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> extensions) {
		Validate.notNull(extensions, ExtensionContainerImpl.EXTENSIONS_NULL);
		this.extensionReferences = extensions;
		this.initialized = true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(final Class<? extends Extension> extension) {
		Validate.notNull(extension, ExtensionContainerImpl.TYPE_NULL);
		return this.extensionReferences.containsKey(extension);
	}

	/** {@inheritDoc} */
	@Override
	public <X extends Extension> X getExtension(final Class<X> type) {
		Validate.notNull(type, ExtensionContainerImpl.TYPE_NULL);
		X result = null;
		if (this.extensionReferences.containsKey(type)) {
			result = type.cast(this.extensionReferences.get(type).get());
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public <X extends Extension> ExtensionRef<X> getExtensionRef(final Class<? super X> type) {
		Validate.notNull(type, ExtensionContainerImpl.TYPE_NULL);
		ExtensionRef<X> result = null;
		if (this.extensionReferences.containsKey(type)) {
			result = (ExtensionRef<X>) this.extensionReferences.get(type);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Set<Class<? extends Extension>> getExtensionTypes() {
		return this.extensionReferences.keySet();
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(final ObjectInput input) throws IOException, ClassNotFoundException {
		Validate.notNull(input, "Input stream may not be null.");
		this.initialized = false;
		this.extensionReferences = (Map<Class<? extends Extension>, ExtensionRef<? extends Extension>>) input.readObject();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ExtensionContainer: [");
		StringUtil.commaSeparated(this.getExtensionTypes(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n]");
		return builder.toString();
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public void updateExtensions(final Collection<ExtensionRef<?>> extensionsReferences, final Collection<Extension> extensions) {
		Validate.notNull(extensionsReferences, "References may not be null.");
		Validate.notNull(extensions, ExtensionContainerImpl.EXTENSIONS_NULL);
		if (this.initialized) {
			throw new IllegalStateException("Cannot initialize an initialized container.");
		}
		this.initialized = true;
		final Map<Class<? extends Extension>, ExtensionRef<? extends Extension>> updatedExtensions = ExtensionUtil.loadExtensions(
				extensionsReferences, extensions);
		for (final Map.Entry<Class<? extends Extension>, ExtensionRef<? extends Extension>> extension : updatedExtensions.entrySet()) {
			final Class<? extends Extension> key = extension.getKey();
			if (this.extensionReferences.containsKey(key)) {
				final ExtensionRef<? extends Extension> reference = this.extensionReferences.get(key);
				if (reference instanceof HardExtensionRef<?>) {
					((HardExtensionRef<Extension>) reference).setExtension(extension.getValue().get());
				}
			} else {
				this.extensionReferences.put(key, extension.getValue());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		Validate.notNull(out, "Output stream may not be null.");
		out.writeObject(this.extensionReferences);
	}

	/**
	 * Reads the object.
	 * 
	 * @param input
	 *            The input stream. May not be null.
	 * @throws IOException
	 *             Thrown if the operation could not be completed.
	 * @throws ClassNotFoundException
	 *             Thrown if the operation could not be completed.
	 */
	private void readObject(final ObjectInputStream input) throws IOException, ClassNotFoundException {
		this.readExternal(input);
	}
}
