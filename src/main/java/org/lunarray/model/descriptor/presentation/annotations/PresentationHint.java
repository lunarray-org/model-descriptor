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
package org.lunarray.model.descriptor.presentation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.lunarray.model.descriptor.presentation.RenderType;
import org.lunarray.model.descriptor.util.BooleanInherit;

/**
 * Describes the presentation hint for a property.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PresentationHint {

	/** The key for the entity, uses the resource bundle of the entity. */
	String buttonKey() default "";

	/** The format. */
	String format() default "";

	/** Indicates this property is not mutable. */
	BooleanInherit immutable() default BooleanInherit.INHERIT;

	/**
	 * Whether or not the related entity this property refers to should be
	 * rendered in-line.
	 */
	BooleanInherit inLine() default BooleanInherit.INHERIT;

	/** The key for the entity, uses the resource bundle of the entity. */
	String labelKey() default "";

	/** Indicates this property is the name property for the entity. */
	BooleanInherit name() default BooleanInherit.INHERIT;

	/** The order is which this property should appear. */
	int order() default Integer.MAX_VALUE;

	/** Provides a prefered render type. */
	RenderType render() default RenderType.DEFAULT;

	/** Indicates this property is required to have a value. */
	BooleanInherit required() default BooleanInherit.INHERIT;

	/** Indicates this property is visible. */
	BooleanInherit visible() default BooleanInherit.INHERIT;
}
