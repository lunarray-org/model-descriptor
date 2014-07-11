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
package org.lunarray.model.descriptor.presentation;

/**
 * Describes the render types.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public enum RenderType {
	/** Render as a checkbox(es). */
	CHECKBOX,
	/** Render as a date picker. */
	DATE_PICKER,
	/** Render as a date/time picker. */
	DATE_TIME_PICKER,
	/** Pick based on type. */
	DEFAULT,
	/** Render as drop down menu. */
	DROPDOWN,
	/** Render as list overview. */
	PICKLIST,
	/** Render as radio. */
	RADIO,
	/** Render as rich text area. */
	RICH_TEXT,
	/** Render as a password area. */
	SECRET,
	/** Render as a shuttle list. */
	SHUTTLE,
	/** Render as text field. */
	TEXT,
	/** Render as text area. */
	TEXT_AREA,
	/** Render as a time picker. */
	TIME_PICKER,
	/** No rendering possible, defaults to text output. */
	UNDEFINED;
}
