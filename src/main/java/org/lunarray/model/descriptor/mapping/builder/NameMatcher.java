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
package org.lunarray.model.descriptor.mapping.builder;

/**
 * Tests for name matching.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public interface NameMatcher {

	/**
	 * Tests if the source and target name match for conversion.
	 * 
	 * @param sourceName
	 *            The source name.
	 * @param targetName
	 *            The target name.
	 * @return True if and only if the name matches.
	 */
	boolean isEntityMatch(String sourceName, String targetName);

	/**
	 * Tests if the source and target name match for conversion.
	 * 
	 * @param sourceName
	 *            The source name.
	 * @param targetName
	 *            The target name.
	 * @return True if and only if the name matches.
	 */
	boolean isPropertyMatch(String sourceName, String targetName);
}
