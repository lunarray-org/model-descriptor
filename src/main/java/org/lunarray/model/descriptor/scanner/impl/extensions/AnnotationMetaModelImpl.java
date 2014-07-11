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
package org.lunarray.model.descriptor.scanner.impl.extensions;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.scanner.extension.AnnotationMetaModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The meta model implementation.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <P>
 *            The property type.
 * @param <E>
 *            The entity type.
 */
public final class AnnotationMetaModelImpl<P, E>
		implements AnnotationMetaModel<P, E> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationMetaModelImpl.class);
	/** Serial id. */
	private static final long serialVersionUID = -5422245578331242143L;
	/** The meta model entities. */
	private List<Object> metaModelEntities;
	/** Metamodel map. */
	private Map<Class<?>, List<Object>> metaModelMap;

	/**
	 * Default constructor.
	 */
	public AnnotationMetaModelImpl() {
		this.metaModelEntities = new LinkedList<Object>();
		this.metaModelMap = new HashMap<Class<?>, List<Object>>();
	}

	/**
	 * Adds the meta model entity.
	 * 
	 * @param metaEntity
	 *            The entity to add. May not be null.
	 */
	public void addEntity(final Object metaEntity) {
		AnnotationMetaModelImpl.LOGGER.debug("Added entity: {}", metaEntity);
		Validate.notNull("Meta entity may not be null.");
		this.metaModelEntities.add(metaEntity);
		for (final Class<?> type : this.resolveClasses(metaEntity)) {
			this.addObject(type, metaEntity);
		}
	}

	/**
	 * Gets the value for the metaModelEntities field.
	 * 
	 * @return The value for the metaModelEntities field.
	 */
	public List<Object> getMetaModelEntities() {
		return this.metaModelEntities;
	}

	/**
	 * Gets the value for the metaModelMap field.
	 * 
	 * @return The value for the metaModelMap field.
	 */
	public Map<Class<?>, List<Object>> getMetaModelMap() {
		return this.metaModelMap;
	}

	/** {@inheritDoc} */
	@Override
	public List<Object> getValues() {
		return this.metaModelEntities;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	// We're sure of this type.
	@Override
	public <T> List<T> getValues(final Class<T> type) {
		Validate.notNull(type, "Type may not be null.");
		List<T> result;
		if (this.metaModelMap.containsKey(type)) {
			result = (List<T>) this.metaModelMap.get(type);
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	/**
	 * Sets a new value for the metaModelEntities field.
	 * 
	 * @param metaModelEntities
	 *            The new value for the metaModelEntities field.
	 */
	public void setMetaModelEntities(final List<Object> metaModelEntities) {
		this.metaModelEntities = metaModelEntities;
	}

	/**
	 * Sets a new value for the metaModelMap field.
	 * 
	 * @param metaModelMap
	 *            The new value for the metaModelMap field.
	 */
	public void setMetaModelMap(final Map<Class<?>, List<Object>> metaModelMap) {
		this.metaModelMap = metaModelMap;
	}

	/**
	 * Add an object.
	 * 
	 * @param type
	 *            The type.
	 * @param entity
	 *            The entity.
	 */
	private void addObject(final Class<?> type, final Object entity) {
		if (!this.metaModelMap.containsKey(type)) {
			this.metaModelMap.put(type, new LinkedList<Object>());
		}
		this.metaModelMap.get(type).add(entity);
	}

	/**
	 * Resolve the classes.
	 * 
	 * @param metaEntity
	 *            The meta entity.
	 * @return The set.
	 */
	private Set<Class<?>> resolveClasses(final Object metaEntity) {
		final Set<Class<?>> set = new HashSet<Class<?>>();
		final Class<?> metaType = metaEntity.getClass();
		set.add(metaType);
		this.resolveClasses(set, metaType);
		return set;
	}

	/**
	 * Resolve classes.
	 * 
	 * @param tail
	 *            The result tail.
	 * @param type
	 *            The type.
	 */
	private void resolveClasses(final Set<Class<?>> tail, final Class<?> type) {
		final Class<?> superType = type.getSuperclass();
		if (!CheckUtil.isNull(superType)) {
			tail.add(superType);
			this.resolveClasses(tail, superType);
		}
		for (final Class<?> interfaceType : type.getInterfaces()) {
			tail.add(interfaceType);
			this.resolveClasses(tail, interfaceType);
		}
	}
}
