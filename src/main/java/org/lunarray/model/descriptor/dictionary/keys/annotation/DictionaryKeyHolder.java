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

import org.lunarray.model.descriptor.dictionary.DictionaryKeyPropertyExtension;

/**
 * Base implementation for the dictionary key holder.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class DictionaryKeyHolder<P, E>
		implements DictionaryKeyPropertyExtension<P, E> {

	/** Serial id. */
	private static final long serialVersionUID = 6543638667530231945L;
	/** The dictionary key. */
	private String key;

	/**
	 * Constructs the key holder.
	 * 
	 * @param key
	 *            The key.
	 */
	public DictionaryKeyHolder(final String key) {
		this.key = key;
	}

	/** {@inheritDoc} */
	@Override
	public String getDictionaryKey() {
		return this.key;
	}

	/**
	 * Gets the value for the key field.
	 * 
	 * @return The value for the key field.
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Sets a new value for the key field.
	 * 
	 * @param key
	 *            The new value for the key field.
	 */
	public void setKey(final String key) {
		this.key = key;
	}
}
