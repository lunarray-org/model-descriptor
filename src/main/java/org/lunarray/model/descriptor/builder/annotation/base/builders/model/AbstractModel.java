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
package org.lunarray.model.descriptor.builder.annotation.base.builders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.lunarray.model.descriptor.model.Model;
import org.lunarray.model.descriptor.model.entity.EntityDescriptor;
import org.lunarray.model.descriptor.model.extension.Extension;
import org.lunarray.model.descriptor.model.extension.ExtensionContainer;
import org.lunarray.model.descriptor.model.extension.ExtensionRef;
import org.lunarray.model.descriptor.model.extension.impl.ExtensionContainerImpl;
import org.lunarray.model.descriptor.util.StringUtil;

/**
 * Describes the model.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <S>
 *            The entity super types.
 */
public abstract class AbstractModel<S>
		implements Model<S> {

	/** Validation message. */
	private static final String ADAPTER_TYPE_NULL = "Adapter type may not be null.";
	/** Serial id. */
	private static final long serialVersionUID = 2516016330740160979L;
	/** The extension references. */
	private ExtensionContainer extensionContainer;
	/** The entity descriptors by name. */
	private Map<String, EntityDescriptor<? extends S>> nameMap;
	/** The entity descriptors by type. */
	private Map<Class<?>, EntityDescriptor<? extends S>> typeMap;

	/**
	 * Constructs the model.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected AbstractModel(final AbstractModelBuilder<S, ? extends AbstractModel<S>, ?> builder) {
		Validate.notNull(builder, "Model may not be null.");
		this.nameMap = builder.getEntitiesByName();
		this.typeMap = builder.getEntitiesByType();
		this.extensionContainer = builder.getBuilderContext().getExtensionContainer();
	}

	/** {@inheritDoc} */
	@Override
	public final <A> A adapt(final Class<A> adapterType) {
		Validate.notNull(adapterType, AbstractModel.ADAPTER_TYPE_NULL);
		return this.modelAdapt(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public final boolean adaptable(final Class<?> adapterType) {
		Validate.notNull(adapterType, AbstractModel.ADAPTER_TYPE_NULL);
		return this.modelAdaptable(adapterType);
	}

	/** {@inheritDoc} */
	@Override
	public final List<EntityDescriptor<? extends S>> getEntities() {
		return new ArrayList<EntityDescriptor<? extends S>>(this.typeMap.values());
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	/* The map key always matches the entry type. */
	@Override
	public final <E extends S> EntityDescriptor<E> getEntity(final Class<E> entityType) {
		return (EntityDescriptor<E>) this.typeMap.get(entityType);
	}

	/** {@inheritDoc} */
	@Override
	public final EntityDescriptor<?> getEntity(final String name) {
		return this.nameMap.get(name);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	/* Tested type. */
	@Override
	public final <E extends S> EntityDescriptor<E> getEntity(final String entityKey, final Class<E> entityType) {
		final EntityDescriptor<?> entity = this.getEntity(entityKey);
		EntityDescriptor<E> result = null;
		if (entityType.isAssignableFrom(entity.getEntityType())) {
			result = (EntityDescriptor<E>) entity;
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final <X extends Extension> X getExtension(final Class<X> extensionType) {
		return this.extensionContainer.getExtension(extensionType);
	}

	/**
	 * Gets the value for the extensionContainer field.
	 * 
	 * @return The value for the extensionContainer field.
	 */
	@Override
	public final ExtensionContainer getExtensionContainer() {
		return this.extensionContainer;
	}

	/** {@inheritDoc} */
	@Override
	public final <X extends Extension> ExtensionRef<X> getExtensionRef(final Class<X> extensionType) {
		return this.extensionContainer.getExtensionRef(extensionType);
	}

	/**
	 * Gets the value for the nameMap field.
	 * 
	 * @return The value for the nameMap field.
	 */
	public final Map<String, EntityDescriptor<? extends S>> getNameMap() {
		return this.nameMap;
	}

	/**
	 * Gets the value for the typeMap field.
	 * 
	 * @return The value for the typeMap field.
	 */
	public final Map<Class<?>, EntityDescriptor<? extends S>> getTypeMap() {
		return this.typeMap;
	}

	/**
	 * Sets a new value for the extensionContainer field.
	 * 
	 * @param extensionContainer
	 *            The new value for the extensionContainer field.
	 */
	public final void setExtensionContainer(final ExtensionContainerImpl extensionContainer) {
		this.extensionContainer = extensionContainer;
	}

	/**
	 * Sets a new value for the nameMap field.
	 * 
	 * @param nameMap
	 *            The new value for the nameMap field.
	 */
	public final void setNameMap(final Map<String, EntityDescriptor<? extends S>> nameMap) {
		this.nameMap = nameMap;
	}

	/**
	 * Sets a new value for the typeMap field.
	 * 
	 * @param typeMap
	 *            The new value for the typeMap field.
	 */
	public final void setTypeMap(final Map<Class<?>, EntityDescriptor<? extends S>> typeMap) {
		this.typeMap = typeMap;
	}

	/**
	 * Adapt to a new type.
	 * 
	 * @param <A>
	 *            The new type.
	 * @param clazz
	 *            The new type.
	 * @return The current instance is the new type.
	 */
	protected final <A> A modelAdapt(final Class<A> clazz) {
		A adapted = null;
		if (clazz.isInstance(this)) {
			adapted = clazz.cast(this);
		}
		return adapted;
	}

	/**
	 * Tests if the new type is adaptable.
	 * 
	 * @param clazz
	 *            The new type.
	 * @return True if and only if the new type is adaptable.
	 */
	protected final boolean modelAdaptable(final Class<?> clazz) {
		return clazz.isInstance(this);
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void modelToString(final StringBuilder builder) {
		builder.append("\tExtensions: {\n\t\t");
		StringUtil.commaSeparated(this.extensionContainer.getExtensionTypes(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n\tEntity names: {\n\t\t");
		StringUtil.commaSeparated(this.nameMap.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n\tEntity types: {\n\t\t");
		StringUtil.commaSeparated(this.typeMap.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}");
	}
}
