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
package org.lunarray.model.descriptor.model.extension;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * A hard extension reference. Contains the extension.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <X>
 *            The extension type.
 */
public final class HardExtensionRef<X extends Extension>
		implements ExtensionRef<X>, Externalizable {

	/** The serial id. */
	private static final long serialVersionUID = -4464128717747181402L;
	/** The extension. */
	private transient X extension;

	/** Default constructor. */
	public HardExtensionRef() {
		// Default constructor.
	}

	/**
	 * Constructs the reference.
	 * 
	 * @param extension
	 *            The extension.
	 */
	public HardExtensionRef(final X extension) {
		this.extension = extension;
	}

	/** {@inheritDoc} */
	@Override
	public X get() {
		return this.extension;
	}

	/**
	 * Gets the value for the extension field.
	 * 
	 * @return The value for the extension field.
	 */
	public X getExtension() {
		return this.extension;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(final ObjectInput input) throws IOException, ClassNotFoundException {
		final boolean serialized = input.readBoolean();
		if (serialized) {
			this.extension = (X) input.readObject();
		}
	}

	/**
	 * Sets a new value for the extension field.
	 * 
	 * @param extension
	 *            The new value for the extension field.
	 */
	public void setExtension(final X extension) {
		this.extension = extension;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("HardExtensionRef[");
		builder.append("Extension: ").append(this.extension);
		builder.append("]");
		return builder.toString();
	}

	/** {@inheritDoc} */
	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		final boolean serializable = this.extension instanceof Serializable;
		out.writeBoolean(serializable);
		if (serializable) {
			out.writeObject(this.extension);
		}
	}

	/**
	 * Reads the object.
	 * 
	 * @param input
	 *            The input stream.
	 * @throws IOException
	 *             Thrown if the operation could not be completed.
	 * @throws ClassNotFoundException
	 *             Thrown if the operation could not be completed.
	 */
	private void readObject(final ObjectInputStream input) throws IOException, ClassNotFoundException {
		this.readExternal(input);
	}
}
