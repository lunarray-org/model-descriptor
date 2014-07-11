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
package org.lunarray.model.descriptor.builder.annotation.base.builders.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.lunarray.common.check.CheckUtil;
import org.lunarray.model.descriptor.creational.CreationException;
import org.lunarray.model.descriptor.creational.CreationalStrategy;
import org.lunarray.model.descriptor.model.entity.EntityExtension;
import org.lunarray.model.descriptor.model.entity.KeyedEntityDescriptor;
import org.lunarray.model.descriptor.model.member.MemberDescriptor;
import org.lunarray.model.descriptor.model.operation.OperationDescriptor;
import org.lunarray.model.descriptor.model.property.PropertyDescriptor;
import org.lunarray.model.descriptor.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract entity descriptor.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @param <E>
 *            The entity type.
 * @param <K>
 *            The key type.
 */
public abstract class AbstractEntityDescriptor<E, K extends Serializable>
		implements KeyedEntityDescriptor<E, K> {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityDescriptor.class);
	/** Serial id. */
	private static final long serialVersionUID = 3653575243099732775L;
	/** Aliases. */
	private Map<String, String> aliases;
	/** The creational strategies. */
	private List<CreationalStrategy<E>> creationalStrategiesDesc;
	/** The extensions. */
	private Map<Class<?>, EntityExtension<E>> extensions;
	/** The key property. */
	private PropertyDescriptor<K, E> keyProperty;
	/** The members. */
	private Map<String, MemberDescriptor<E>> members;
	/** The name. */
	private String name;
	/** The operations. */
	private Map<String, OperationDescriptor<E>> operations;
	/** The properties. */
	private Map<String, PropertyDescriptor<?, E>> properties;
	/** The type. */
	private Class<E> type;

	/**
	 * Constructs the descriptor.
	 * 
	 * @param builder
	 *            The builder. May not be null.
	 */
	protected AbstractEntityDescriptor(final AbstractEntityDescriptorBuilder<E, K, ?> builder) {
		super();
		Validate.notNull(builder, "Builder may not be null.");
		this.type = builder.getEntityType().getEntityType();
		this.properties = builder.getProperties();
		this.keyProperty = builder.getKeyProperty();
		this.name = builder.getName();
		this.extensions = builder.getExtensions();
		this.aliases = builder.getAliases();
		this.members = builder.getMembers();
		this.operations = builder.getOperations();
		this.creationalStrategiesDesc = builder.getCreationalStrategies();
	}

	/** {@inheritDoc} */
	@Override
	public final E createEntity() throws CreationException {
		final List<CreationException> exceptions = new LinkedList<CreationException>();
		final Iterator<CreationalStrategy<E>> strategyIt = this.creationalStrategiesDesc.iterator();
		E result = null;
		while (strategyIt.hasNext() && CheckUtil.isNull(result)) {
			try {
				result = strategyIt.next().getInstance();
			} catch (final CreationException e) {
				AbstractEntityDescriptor.LOGGER.debug("Could not create entity.", e);
				exceptions.add(e);
			}
		}
		if (CheckUtil.isNull(result)) {
			final Throwable[] causes = new Throwable[exceptions.size()];
			exceptions.toArray(causes);
			throw new CreationException("Could not create instance.", causes);
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public final Collection<CreationalStrategy<E>> creationalStrategies() {
		return this.creationalStrategiesDesc;
	}

	/** {@inheritDoc} */
	@Override
	public final <X extends EntityExtension<E>> X extension(final Class<X> extensionClazz) {
		X extension = null;
		if (this.extensions.containsKey(extensionClazz)) {
			extension = extensionClazz.cast(this.extensions.get(extensionClazz));
		}
		return extension;
	}

	/**
	 * Gets the value for the aliases field.
	 * 
	 * @return The value for the aliases field.
	 */
	public final Map<String, String> getAliases() {
		return this.aliases;
	}

	/**
	 * Gets the value for the creationalStrategies field.
	 * 
	 * @return The value for the creationalStrategies field.
	 */
	public final List<CreationalStrategy<E>> getCreationalStrategies() {
		return this.creationalStrategiesDesc;
	}

	/**
	 * Gets the value for the creationalStrategiesDesc field.
	 * 
	 * @return The value for the creationalStrategiesDesc field.
	 */
	public final List<CreationalStrategy<E>> getCreationalStrategiesDesc() {
		return this.creationalStrategiesDesc;
	}

	/** {@inheritDoc} */
	@Override
	public final Class<E> getEntityType() {
		return this.type;
	}

	/**
	 * Gets the value for the extensions field.
	 * 
	 * @return The value for the extensions field.
	 */
	public final Map<Class<?>, EntityExtension<E>> getExtensions() {
		return this.extensions;
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyDescriptor<K, E> getKeyProperty() {
		return this.keyProperty;
	}

	/** {@inheritDoc} */
	@Override
	public final MemberDescriptor<E> getMember(final String name) {
		return this.members.get(name);
	}

	/** {@inheritDoc} */
	@Override
	public final Set<MemberDescriptor<E>> getMembers() {
		return new HashSet<MemberDescriptor<E>>(this.members.values());
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public final OperationDescriptor<E> getOperation(final String name) {
		return this.operations.get(name);
	}

	/** {@inheritDoc} */
	@Override
	public final Set<OperationDescriptor<E>> getOperations() {
		return new HashSet<OperationDescriptor<E>>(this.operations.values());
	}

	/** {@inheritDoc} */
	@Override
	public final Set<PropertyDescriptor<?, E>> getProperties() {
		return new HashSet<PropertyDescriptor<?, E>>(this.properties.values());
	}

	/** {@inheritDoc} */
	@Override
	public final PropertyDescriptor<?, E> getProperty(final String name) {
		PropertyDescriptor<?, E> result = null;
		final String propertyName = this.resolveMemberName(name);
		if (this.properties.containsKey(propertyName)) {
			result = this.properties.get(propertyName);
		}
		return result;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	/* Is checked, just not cast. */
	@Override
	public final <P> PropertyDescriptor<P, E> getProperty(final String name, final Class<P> propertyType) {
		PropertyDescriptor<P, E> result = null;
		final String propertyName = this.resolveMemberName(name);
		if (this.properties.containsKey(propertyName)) {
			final PropertyDescriptor<P, E> property = (PropertyDescriptor<P, E>) this.properties.get(propertyName);
			if (propertyType.equals(property.getPropertyType())) {
				result = property;
			}
		}
		return result;
	}

	/**
	 * Gets the value for the type field.
	 * 
	 * @return The value for the type field.
	 */
	public final Class<E> getType() {
		return this.type;
	}

	/**
	 * Sets a new value for the aliases field.
	 * 
	 * @param aliases
	 *            The new value for the aliases field.
	 */
	public final void setAliases(final Map<String, String> aliases) {
		this.aliases = aliases;
	}

	/**
	 * Sets a new value for the creationalStrategies field.
	 * 
	 * @param creationalStrategies
	 *            The new value for the creationalStrategies field.
	 */
	public final void setCreationalStrategies(final List<CreationalStrategy<E>> creationalStrategies) {
		this.creationalStrategiesDesc = creationalStrategies;
	}

	/**
	 * Sets a new value for the creationalStrategiesDesc field.
	 * 
	 * @param creationalStrategiesDesc
	 *            The new value for the creationalStrategiesDesc field.
	 */
	public final void setCreationalStrategiesDesc(final List<CreationalStrategy<E>> creationalStrategiesDesc) {
		this.creationalStrategiesDesc = creationalStrategiesDesc;
	}

	/**
	 * Sets a new value for the extensions field.
	 * 
	 * @param extensions
	 *            The new value for the extensions field.
	 */
	public final void setExtensions(final Map<Class<?>, EntityExtension<E>> extensions) {
		this.extensions = extensions;
	}

	/**
	 * Sets a new value for the keyProperty field.
	 * 
	 * @param keyProperty
	 *            The new value for the keyProperty field.
	 */
	public final void setKeyProperty(final PropertyDescriptor<K, E> keyProperty) {
		this.keyProperty = keyProperty;
	}

	/**
	 * Sets a new value for the members field.
	 * 
	 * @param members
	 *            The new value for the members field.
	 */
	public final void setMembers(final Map<String, MemberDescriptor<E>> members) {
		this.members = members;
	}

	/**
	 * Sets a new value for the name field.
	 * 
	 * @param name
	 *            The new value for the name field.
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * Sets a new value for the operations field.
	 * 
	 * @param operations
	 *            The new value for the operations field.
	 */
	public final void setOperations(final Map<String, OperationDescriptor<E>> operations) {
		this.operations = operations;
	}

	/**
	 * Sets a new value for the properties field.
	 * 
	 * @param properties
	 *            The new value for the properties field.
	 */
	public final void setProperties(final Map<String, PropertyDescriptor<?, E>> properties) {
		this.properties = properties;
	}

	/**
	 * Sets a new value for the type field.
	 * 
	 * @param type
	 *            The new value for the type field.
	 */
	public final void setType(final Class<E> type) {
		this.type = type;
	}

	/**
	 * Adapt to a new type.
	 * 
	 * @param <A>
	 *            The new type.
	 * @param adapterType
	 *            The new type.
	 * @return The current instance is the new type.
	 */
	protected final <A> A entityAdapt(final Class<A> adapterType) {
		A value = null;
		if (KeyedEntityDescriptor.class.equals(adapterType)) {
			if (!CheckUtil.isNull(this.keyProperty)) {
				value = adapterType.cast(this);
			}
		} else if (adapterType.isInstance(this)) {
			value = adapterType.cast(this);
		}
		return value;
	}

	/**
	 * Tests if adaptable to the new type.
	 * 
	 * @param adapterType
	 *            The new type.
	 * @return The current instance is adaptable to the new type.
	 */
	protected final boolean entityAdaptable(final Class<?> adapterType) {
		boolean adaptable = false;
		if (KeyedEntityDescriptor.class.equals(adapterType)) {
			adaptable ^= !CheckUtil.isNull(this.keyProperty);
		} else {
			adaptable = adapterType.isInstance(this);
		}
		return adaptable;
	}

	/**
	 * Appends a string representation.
	 * 
	 * @param builder
	 *            The builder.
	 */
	protected final void entityToString(final StringBuilder builder) {
		builder.append("\tName: ").append(this.name);
		if (!CheckUtil.isNull(this.keyProperty)) {
			builder.append("\n\tKey: ").append(this.keyProperty.toString());
		}
		builder.append("\n\tExtensions: {\n\t\t");
		StringUtil.commaSeparated(this.extensions.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n\tType: ").append(this.type);
		builder.append("\n\tProperties: {\n\t\t");
		StringUtil.commaSeparated(this.properties.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\tOperations: {\n\t\t");
		StringUtil.commaSeparated(this.operations.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n\tAliases: {\n\t\t");
		StringUtil.commaSeparated(this.aliases.keySet(), StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n\tCreational Strategies: {\n\t\t");
		StringUtil.commaSeparated(this.creationalStrategiesDesc, StringUtil.DOUBLE_TAB_NEWLINE_COMMA, builder);
		builder.append("\n\t}\n");
	}

	/**
	 * Resolves a name for a property, processes aliases.
	 * 
	 * @param name
	 *            The property name.
	 * @return The property name after processing.
	 */
	protected final String resolveMemberName(final String name) {
		String result = null;
		if (this.members.containsKey(name)) {
			result = name;
		} else if (this.aliases.containsKey(name)) {
			result = this.aliases.get(name);
		}
		return result;
	}
}
