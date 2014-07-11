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

import java.util.Collection;
import java.util.Set;

/**
 * Extension container that allows initialization after unserialization.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface ExtensionContainer {

	/**
	 * Tests if the container contains an extension of the given type.
	 * 
	 * @param extension
	 *            The extension type. May not be null.
	 * @return True if and only if the container contains the extension.
	 */
	boolean contains(final Class<? extends Extension> extension);

	/**
	 * Gets an extension.
	 * 
	 * @param type
	 *            The extension type. May not be null.
	 * @return The extension.
	 * @param <X>
	 *            The extension type.
	 */
	<X extends Extension> X getExtension(final Class<X> type);

	/**
	 * Gets an extension reference.
	 * 
	 * @param type
	 *            The extension type. May not be null.
	 * @return The extension reference.
	 * @param <X>
	 *            The extension type.
	 */
	<X extends Extension> ExtensionRef<X> getExtensionRef(final Class<? super X> type);

	/**
	 * Gets the extension type.
	 * 
	 * @return The extension types.
	 */
	Set<Class<? extends Extension>> getExtensionTypes();

	/**
	 * Update the extensions.
	 * 
	 * @param extensionsReferences
	 *            Extension references. May not be null.
	 * @param extensions
	 *            The extensions. May not be null.
	 */
	void updateExtensions(final Collection<ExtensionRef<?>> extensionsReferences, final Collection<Extension> extensions);
}
