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
package org.lunarray.model.descriptor.converter.def.converters;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.lunarray.model.descriptor.converter.exceptions.ConverterException;
import org.w3c.dom.Node;

/**
 * Converter for {@link Node}.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 */
public final class DOMConverter
		extends AbstractDelegatingConverter<Node> {

	/** Transformer factory. */
	private TransformerFactory transformerFactory;

	/**
	 * Default constructor.
	 */
	public DOMConverter() {
		super();
		this.transformerFactory = TransformerFactory.newInstance();
	}

	/**
	 * Gets the value for the transformerFactory field.
	 * 
	 * @return The value for the transformerFactory field.
	 */
	public TransformerFactory getTransformerFactory() {
		return this.transformerFactory;
	}

	/**
	 * Sets a new value for the transformerFactory field.
	 * 
	 * @param transformerFactory
	 *            The new value for the transformerFactory field.
	 */
	public void setTransformerFactory(final TransformerFactory transformerFactory) {
		this.transformerFactory = transformerFactory;
	}

	/** {@inheritDoc} */
	@Override
	protected Node toInstance(final String stringValue) throws ConverterException {
		final DOMResult result = new DOMResult();
		final StreamSource source = new StreamSource(new StringReader(stringValue));
		try {
			this.transformerFactory.newTransformer().transform(source, result);
		} catch (final TransformerConfigurationException e) {
			throw new ConverterException("Could not configure to dom transformer.", e);
		} catch (final TransformerException e) {
			throw new ConverterException("Could not transform to dom.", e);
		}
		return result.getNode();
	}

	/** {@inheritDoc} */
	@Override
	protected String toString(final Node instance) throws ConverterException {
		final StringWriter writer = new StringWriter();
		final StreamResult result = new StreamResult(writer);
		final DOMSource source = new DOMSource(instance);
		try {
			this.transformerFactory.newTransformer().transform(source, result);
		} catch (final TransformerConfigurationException e) {
			throw new ConverterException("Could not configure to string transformer.", e);
		} catch (final TransformerException e) {
			throw new ConverterException("Could not transform to string.", e);
		}
		return writer.toString();
	}
}
