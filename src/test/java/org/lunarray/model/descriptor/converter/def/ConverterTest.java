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
package org.lunarray.model.descriptor.converter.def;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.naming.CompositeName;
import javax.naming.LinkRef;
import javax.naming.ldap.LdapName;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.lunarray.model.descriptor.converter.ConverterTool;
import org.lunarray.model.descriptor.test.SampleEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Tests the default converters.
 * 
 * @author Pal Hargitai (pal@lunarray.org)
 * @see DefaultConverterTool
 */
public class ConverterTest {

	private static final String DOCUMENT_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><document/>";
	private static final Node DOM_NODE;
	private static final String ETH_NAME;
	private static final long UUID_LSB;
	private static final long UUID_MSB;
	private static final String UUID_STR;
	private final ConverterTool converterTool = new DelegatingEnumConverterTool(new DefaultConverterTool());

	static {
		String eth = "";
		Enumeration<NetworkInterface> ifs;
		try {
			ifs = NetworkInterface.getNetworkInterfaces();
			if (ifs.hasMoreElements()) {
				eth = ifs.nextElement().getName();
			}
		} catch (final SocketException e) {
			throw new IllegalArgumentException();
		}
		ETH_NAME = eth;
		Document document = null;
		try {
			final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.newDocument();
			final Element e = document.createElementNS(null, "document");
			document.appendChild(e);
		} catch (final ParserConfigurationException e) {
			throw new IllegalArgumentException();
		}
		DOM_NODE = document;
		final UUID uuid = UUID.randomUUID();
		UUID_STR = uuid.toString();
		UUID_LSB = uuid.getLeastSignificantBits();
		UUID_MSB = uuid.getMostSignificantBits();
	}

	@Test
	public void testAtomicBooleanFromConvert() throws Exception {
		Assert.assertEquals("true", this.converterTool.convertToString(AtomicBoolean.class, new AtomicBoolean(true)));
	}

	@Test
	public void testAtomicBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals("true", this.converterTool.convertToString(AtomicBoolean.class, new AtomicBoolean(true), Locale.GERMAN));
	}

	@Test
	public void testAtomicBooleanToConvert() throws Exception {
		Assert.assertEquals(true, this.converterTool.convertToInstance(AtomicBoolean.class, "true").get());
	}

	@Test
	public void testAtomicBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(true, this.converterTool.convertToInstance(AtomicBoolean.class, "true", Locale.GERMAN).get());
	}

	@Test
	public void testAtomicIntegerFromConvert() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(AtomicInteger.class, new AtomicInteger(10)));
	}

	@Test
	public void testAtomicIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(AtomicInteger.class, new AtomicInteger(10), Locale.GERMAN));
	}

	@Test
	public void testAtomicIntegerToConvert() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicInteger.class, "10").get());
	}

	@Test
	public void testAtomicIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicInteger.class, "10", Locale.GERMAN).get());
	}

	@Test
	public void testAtomicLongFromConvert() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(AtomicLong.class, new AtomicLong(10)));
	}

	@Test
	public void testAtomicLongFromConvertLocale() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(AtomicLong.class, new AtomicLong(10), Locale.GERMAN));
	}

	@Test
	public void testAtomicLongToConvert() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicLong.class, "10").get());
	}

	@Test
	public void testAtomicLongToConvertLocale() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicLong.class, "10", Locale.GERMAN).get());
	}

	@Test
	public void testBigDecimalFromConvert() throws Exception {
		Assert.assertEquals("10.1", this.converterTool.convertToString(BigDecimal.class, BigDecimal.valueOf(10.1)));
	}

	@Test
	public void testBigDecimalFromConvertLocale() throws Exception {
		Assert.assertEquals("10,1", this.converterTool.convertToString(BigDecimal.class, BigDecimal.valueOf(10.1), Locale.GERMAN));
	}

	@Test
	public void testBigDecimalToConvert() throws Exception {
		Assert.assertEquals(BigDecimal.valueOf(10.1), this.converterTool.convertToInstance(BigDecimal.class, "10.1"));
	}

	@Test
	public void testBigDecimalToConvertLocale() throws Exception {
		Assert.assertEquals(BigDecimal.valueOf(10.1), this.converterTool.convertToInstance(BigDecimal.class, "10,1", Locale.GERMAN));
	}

	@Test
	public void testBigIntegerFromConvert() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(BigInteger.class, BigInteger.valueOf(10)));
	}

	@Test
	public void testBigIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(BigInteger.class, BigInteger.valueOf(10), Locale.GERMAN));
	}

	@Test
	public void testBigIntegerToConvert() throws Exception {
		Assert.assertEquals(BigInteger.valueOf(10), this.converterTool.convertToInstance(BigInteger.class, "10"));
	}

	@Test
	public void testBigIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(BigInteger.valueOf(10), this.converterTool.convertToInstance(BigInteger.class, "10", Locale.GERMAN));
	}

	@Test
	public void testBooleanFromConvert() throws Exception {
		Assert.assertEquals("true", this.converterTool.convertToString(Boolean.class, Boolean.valueOf(true).booleanValue()));
	}

	@Test
	public void testBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals("true", this.converterTool.convertToString(Boolean.class, Boolean.valueOf(true).booleanValue(), Locale.GERMAN));
	}

	@Test
	public void testBooleanToConvert() throws Exception {
		Assert.assertEquals(Boolean.valueOf(true), this.converterTool.convertToInstance(Boolean.class, "true"));
	}

	@Test
	public void testBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(Boolean.valueOf(true), this.converterTool.convertToInstance(Boolean.class, "true", Locale.GERMAN));
	}

	@Test
	public void testByteFromConvert() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Byte.class, Integer.valueOf(10).byteValue()));
	}

	@Test
	public void testByteFromConvertLocale() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Byte.class, Integer.valueOf(10).byteValue(), Locale.GERMAN));
	}

	@Test
	public void testByteToConvert() throws Exception {
		Assert.assertEquals(Byte.valueOf((byte) 10), this.converterTool.convertToInstance(Byte.class, "10"));
	}

	@Test
	public void testByteToConvertLocale() throws Exception {
		Assert.assertEquals(Byte.valueOf((byte) 10), this.converterTool.convertToInstance(Byte.class, "10", Locale.GERMAN));
	}

	@Test
	public void testCalendarFromConvert() throws Exception {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(1000000000000l);
		Assert.assertEquals("Sunday, September 9, 2001 3:46:40 AM CEST", this.converterTool.convertToString(Calendar.class, calendar));
	}

	@Test
	public void testCalendarToConvert() throws Exception {
		Assert.assertEquals(1000000000000l,
				this.converterTool.convertToInstance(Calendar.class, "Sunday, September 9, 2001 3:46:40 AM CEST").getTimeInMillis());
	}

	@Test
	public void testCharacterFromConvert() throws Exception {
		Assert.assertEquals("t", this.converterTool.convertToString(Character.class, Character.valueOf('t')));
	}

	@Test
	public void testCharacterFromConvertLocale() throws Exception {
		Assert.assertEquals("t", this.converterTool.convertToString(Character.class, Character.valueOf('t'), Locale.GERMAN));
	}

	@Test
	public void testCharacterToConvert() throws Exception {
		Assert.assertEquals(Character.valueOf('t'), this.converterTool.convertToInstance(Character.class, "t"));
	}

	@Test
	public void testCharacterToConvertLocale() throws Exception {
		Assert.assertEquals(Character.valueOf('t'), this.converterTool.convertToInstance(Character.class, "t", Locale.GERMAN));
	}

	@Test
	public void testClassFromConvert() throws Exception {
		Assert.assertEquals(ConverterTest.class.getCanonicalName(), this.converterTool.convertToString(Class.class, ConverterTest.class));
	}

	@Test
	public void testClassFromConvertLocale() throws Exception {
		Assert.assertEquals(ConverterTest.class.getCanonicalName(),
				this.converterTool.convertToString(Class.class, ConverterTest.class, Locale.GERMAN));
	}

	@Test
	public void testClassToConvert() throws Exception {
		Assert.assertEquals(ConverterTest.class, this.converterTool.convertToInstance(Class.class, ConverterTest.class.getCanonicalName()));
	}

	@Test
	public void testClassToConvertLocale() throws Exception {
		Assert.assertEquals(ConverterTest.class,
				this.converterTool.convertToInstance(Class.class, ConverterTest.class.getCanonicalName(), Locale.GERMAN));
	}

	@Test
	public void testCompositeNameFromConvert() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToString(CompositeName.class, new CompositeName("test")));
	}

	@Test
	public void testCompositeNameFromConvertLocale() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToString(CompositeName.class, new CompositeName("test"), Locale.GERMAN));
	}

	@Test
	public void testCompositeNameToConvert() throws Exception {
		Assert.assertEquals(new CompositeName("test"), this.converterTool.convertToInstance(CompositeName.class, "test"));
	}

	@Test
	public void testCompositeNameToConvertLocale() throws Exception {
		Assert.assertEquals(new CompositeName("test"), this.converterTool.convertToInstance(CompositeName.class, "test", Locale.GERMAN));
	}

	@Test
	public void testCurrencyFromConvert() throws Exception {
		Assert.assertEquals("EUR", this.converterTool.convertToString(Currency.class, Currency.getInstance("EUR")));
	}

	@Test
	public void testCurrencyFromConvertLocale() throws Exception {
		Assert.assertEquals("EUR", this.converterTool.convertToString(Currency.class, Currency.getInstance("EUR"), Locale.GERMAN));
	}

	@Test
	public void testCurrencyToConvert() throws Exception {
		Assert.assertEquals(Currency.getInstance("EUR"), this.converterTool.convertToInstance(Currency.class, "EUR"));
	}

	@Test
	public void testCurrencyToConvertLocale() throws Exception {
		Assert.assertEquals(Currency.getInstance("EUR"), this.converterTool.convertToInstance(Currency.class, "EUR", Locale.GERMAN));
	}

	@Test
	public void testDateFromConvert() throws Exception {
		Assert.assertEquals("Sunday, September 9, 2001 3:46:40 AM CEST",
				this.converterTool.convertToString(Date.class, new Date(1000000000000l)));
	}

	@Test
	public void testDateToConvert() throws Exception {
		Assert.assertEquals(1000000000000l, this.converterTool.convertToInstance(Date.class, "Sunday, September 9, 2001 3:46:40 AM CEST")
				.getTime());
	}

	@Test
	public void testDoubleFromConvert() throws Exception {
		Assert.assertEquals("10.1", this.converterTool.convertToString(Double.class, Double.valueOf(10.1).doubleValue()));
	}

	@Test
	public void testDoubleFromConvertLocale() throws Exception {
		Assert.assertEquals("10,1", this.converterTool.convertToString(Double.class, Double.valueOf(10.1).doubleValue(), Locale.GERMAN));
	}

	@Test
	public void testDoubleToConvert() throws Exception {
		Assert.assertEquals(Double.valueOf(10.1), this.converterTool.convertToInstance(Double.class, "10.1"));
	}

	@Test
	public void testDoubleToConvertLocale() throws Exception {
		Assert.assertEquals(Double.valueOf(10.1), this.converterTool.convertToInstance(Double.class, "10,1", Locale.GERMAN));
	}

	@Test
	public void testEnumFromConvert() throws Exception {
		Assert.assertEquals("TEST02", this.converterTool.convertToString(SampleEnum.class, SampleEnum.TEST02));
	}

	@Test
	public void testEnumFromConvertLocale() throws Exception {
		Assert.assertEquals("TEST02", this.converterTool.convertToString(SampleEnum.class, SampleEnum.TEST02, Locale.GERMAN));
	}

	@Test
	public void testEnumToConvert() throws Exception {
		Assert.assertEquals(SampleEnum.TEST02, this.converterTool.convertToInstance(SampleEnum.class, "TEST02"));
	}

	@Test
	public void testEnumToConvertLocale() throws Exception {
		Assert.assertEquals(SampleEnum.TEST02, this.converterTool.convertToInstance(SampleEnum.class, "TEST02", Locale.GERMAN));
	}

	@Test
	public void testFloatFromConvert() throws Exception {
		Assert.assertEquals("10.1", this.converterTool.convertToString(Float.class, Double.valueOf(10.1).floatValue()));
	}

	@Test
	public void testFloatFromConvertLocale() throws Exception {
		Assert.assertEquals("10,1", this.converterTool.convertToString(Float.class, Double.valueOf(10.1).floatValue(), Locale.GERMAN));
	}

	@Test
	public void testFloatToConvert() throws Exception {
		Assert.assertEquals(Float.valueOf((float) 10.1), this.converterTool.convertToInstance(Float.class, "10.1"));
	}

	@Test
	public void testFloatToConvertLocale() throws Exception {
		Assert.assertEquals(Float.valueOf((float) 10.1), this.converterTool.convertToInstance(Float.class, "10,1", Locale.GERMAN));
	}

	@Test
	public void testInetAddressFromConvert() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1",
				this.converterTool.convertToString(InetAddress.class, InetAddress.getByName("localhost")));
	}

	@Test
	public void testInetAddressFromConvertLocale() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1",
				this.converterTool.convertToString(InetAddress.class, InetAddress.getByName("localhost"), Locale.GERMAN));
	}

	@Test
	public void testInetAddressToConvert() throws Exception {
		Assert.assertEquals(InetAddress.getByName("localhost"), this.converterTool.convertToInstance(InetAddress.class, "localhost"));
	}

	@Test
	public void testInetAddressToConvertLocale() throws Exception {
		Assert.assertEquals(InetAddress.getByName("localhost"),
				this.converterTool.convertToInstance(InetAddress.class, "localhost", Locale.GERMAN));
	}

	@Test
	public void testInetSocketAddressFromConvert() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1:1000",
				this.converterTool.convertToString(InetSocketAddress.class, new InetSocketAddress("localhost", 1000)));
	}

	@Test
	public void testInetSocketAddressFromConvertLocale() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1:1000",
				this.converterTool.convertToString(InetSocketAddress.class, new InetSocketAddress("localhost", 1000), Locale.GERMAN));
	}

	@Test
	public void testInetSocketAddressToConvert() throws Exception {
		Assert.assertEquals(new InetSocketAddress("localhost", 1000),
				this.converterTool.convertToInstance(InetSocketAddress.class, "localhost:1000"));
	}

	@Test
	public void testInetSocketAddressToConvertLocale() throws Exception {
		Assert.assertEquals(new InetSocketAddress("localhost", 1000),
				this.converterTool.convertToInstance(InetSocketAddress.class, "localhost:1000", Locale.GERMAN));
	}

	@Test
	public void testIntegerFromConvert() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Integer.class, Integer.valueOf(10)));
	}

	@Test
	public void testIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Integer.class, Integer.valueOf(10), Locale.GERMAN));
	}

	@Test
	public void testIntegerToConvert() throws Exception {
		Assert.assertEquals(Integer.valueOf(10), this.converterTool.convertToInstance(Integer.class, "10"));
	}

	@Test
	public void testIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(Integer.valueOf(10), this.converterTool.convertToInstance(Integer.class, "10", Locale.GERMAN));
	}

	@Test
	public void testLdapNameFromConvert() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LdapName.class, new LdapName("cn=test")));
	}

	@Test
	public void testLdapNameFromConvertLocale() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LdapName.class, new LdapName("cn=test"), Locale.GERMAN));
	}

	@Test
	public void testLdapNameToConvert() throws Exception {
		Assert.assertEquals(new LdapName("cn=test"), this.converterTool.convertToInstance(LdapName.class, "cn=test"));
	}

	@Test
	public void testLdapNameToConvertLocale() throws Exception {
		Assert.assertEquals(new LdapName("cn=test"), this.converterTool.convertToInstance(LdapName.class, "cn=test", Locale.GERMAN));
	}

	@Test
	public void testLinkRefFromConvert() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LinkRef.class, new LinkRef("cn=test")));
	}

	@Test
	public void testLinkRefFromConvertLocale() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LinkRef.class, new LinkRef("cn=test"), Locale.GERMAN));
	}

	@Test
	public void testLinkRefToConvert() throws Exception {
		Assert.assertEquals(new LinkRef("cn=test"), this.converterTool.convertToInstance(LinkRef.class, "cn=test"));
	}

	@Test
	public void testLinkRefToConvertLocale() throws Exception {
		Assert.assertEquals(new LinkRef("cn=test"), this.converterTool.convertToInstance(LinkRef.class, "cn=test", Locale.GERMAN));
	}

	@Test
	public void testLongFromConvert() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Long.class, Long.valueOf(10)));
	}

	@Test
	public void testLongFromConvertLocale() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Long.class, Long.valueOf(10), Locale.GERMAN));
	}

	@Test
	public void testLongToConvert() throws Exception {
		Assert.assertEquals(Long.valueOf(10), this.converterTool.convertToInstance(Long.class, "10"));
	}

	@Test
	public void testLongToConvertLocale() throws Exception {
		Assert.assertEquals(Long.valueOf(10), this.converterTool.convertToInstance(Long.class, "10", Locale.GERMAN));
	}

	@Test
	public void testNetworkInterfaceFromConvert() throws Exception {
		Assert.assertEquals(ConverterTest.ETH_NAME,
				this.converterTool.convertToString(NetworkInterface.class, NetworkInterface.getByName(ConverterTest.ETH_NAME)));
	}

	@Test
	public void testNetworkInterfaceFromConvertLocale() throws Exception {
		Assert.assertEquals(ConverterTest.ETH_NAME, this.converterTool.convertToString(NetworkInterface.class,
				NetworkInterface.getByName(ConverterTest.ETH_NAME), Locale.GERMAN));
	}

	@Test
	public void testNetworkInterfaceToConvert() throws Exception {
		Assert.assertEquals(NetworkInterface.getByName(ConverterTest.ETH_NAME),
				this.converterTool.convertToInstance(NetworkInterface.class, ConverterTest.ETH_NAME));
	}

	@Test
	public void testNetworkInterfaceToConvertLocale() throws Exception {
		Assert.assertEquals(NetworkInterface.getByName(ConverterTest.ETH_NAME),
				this.converterTool.convertToInstance(NetworkInterface.class, ConverterTest.ETH_NAME, Locale.GERMAN));
	}

	@Test
	public void testNodeFromConvert() throws Exception {
		this.converterTool.convertToString(Node.class, ConverterTest.DOM_NODE);
	}

	@Test
	public void testNodeFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Node.class, ConverterTest.DOM_NODE, Locale.GERMAN);
	}

	@Test
	public void testNodeToConvert() throws Exception {
		this.converterTool.convertToInstance(Node.class, ConverterTest.DOCUMENT_CONTENT);
	}

	@Test
	public void testNodeToConvertLocale() throws Exception {
		this.converterTool.convertToInstance(Node.class, ConverterTest.DOCUMENT_CONTENT, Locale.GERMAN);
	}

	@Test
	public void testNullAtomicBooleanFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicBoolean.class, null));
	}

	@Test
	public void testNullAtomicBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicBoolean.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullAtomicBooleanToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicBoolean.class, null));
	}

	@Test
	public void testNullAtomicBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicBoolean.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullAtomicIntegerFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicInteger.class, null));
	}

	@Test
	public void testNullAtomicIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicInteger.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullAtomicIntegerToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicInteger.class, null));
	}

	@Test
	public void testNullAtomicIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicInteger.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullAtomicLongFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicLong.class, null));
	}

	@Test
	public void testNullAtomicLongFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicLong.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullAtomicLongToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicLong.class, null));
	}

	@Test
	public void testNullAtomicLongToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicLong.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullBigDecimalFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigDecimal.class, null));
	}

	@Test
	public void testNullBigDecimalFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigDecimal.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullBigDecimalToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigDecimal.class, null));
	}

	@Test
	public void testNullBigDecimalToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigDecimal.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullBigIntegerFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigInteger.class, null));
	}

	@Test
	public void testNullBigIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigInteger.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullBigIntegerToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigInteger.class, null));
	}

	@Test
	public void testNullBigIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigInteger.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullBooleanFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Boolean.class, null));
	}

	@Test
	public void testNullBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Boolean.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullBooleanToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Boolean.class, null));
	}

	@Test
	public void testNullBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Boolean.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullByteFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Byte.class, null));
	}

	@Test
	public void testNullByteFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Byte.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullByteToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Byte.class, null));
	}

	@Test
	public void testNullByteToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Byte.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullCalendarFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Calendar.class, null));
	}

	@Test
	public void testNullCalendarToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Calendar.class, null));
	}

	@Test
	public void testNullCharacterFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Character.class, null));
	}

	@Test
	public void testNullCharacterFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Character.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullCharacterToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Character.class, null));
	}

	@Test
	public void testNullCharacterToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Character.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullClassFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Class.class, null));
	}

	@Test
	public void testNullClassFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Class.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullClassToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Class.class, null));
	}

	@Test
	public void testNullClassToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Class.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullCompositeNameFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(CompositeName.class, null));
	}

	@Test
	public void testNullCompositeNameFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(CompositeName.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullCompositeNameToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(CompositeName.class, null));
	}

	@Test
	public void testNullCompositeNameToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(CompositeName.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullCurrencyFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Currency.class, null));
	}

	@Test
	public void testNullCurrencyFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Currency.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullCurrencyToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Currency.class, null));
	}

	@Test
	public void testNullCurrencyToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Currency.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullDateFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Date.class, null));
	}

	@Test
	public void testNullDateToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Date.class, null));
	}

	@Test
	public void testNullDoubleFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Double.class, null));
	}

	@Test
	public void testNullDoubleFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Double.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullDoubleToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Double.class, null));
	}

	@Test
	public void testNullDoubleToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Double.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullEnumFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(SampleEnum.class, null));
	}

	@Test
	public void testNullEnumFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(SampleEnum.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullEnumToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(SampleEnum.class, null));
	}

	@Test
	public void testNullEnumToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(SampleEnum.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullFloatFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Float.class, null));
	}

	@Test
	public void testNullFloatFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Float.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullFloatToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Float.class, null));
	}

	@Test
	public void testNullFloatToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Float.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullInetAddressFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetAddress.class, null));
	}

	@Test
	public void testNullInetAddressFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetAddress.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullInetAddressToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetAddress.class, null));
	}

	@Test
	public void testNullInetAddressToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetAddress.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullInetSocketAddressFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetSocketAddress.class, null));
	}

	@Test
	public void testNullInetSocketAddressFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetSocketAddress.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullInetSocketAddressToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetSocketAddress.class, null));
	}

	@Test
	public void testNullInetSocketAddressToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetSocketAddress.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullIntegerFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Integer.class, null));
	}

	@Test
	public void testNullIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Integer.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullIntegerToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Integer.class, null));
	}

	@Test
	public void testNullIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Integer.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullLdapNameFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LdapName.class, null));
	}

	@Test
	public void testNullLdapNameFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LdapName.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullLdapNameToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LdapName.class, null));
	}

	@Test
	public void testNullLdapNameToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LdapName.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullLinkRefFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LinkRef.class, null));
	}

	@Test
	public void testNullLinkRefFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LinkRef.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullLinkRefToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LinkRef.class, null));
	}

	@Test
	public void testNullLinkRefToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LinkRef.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullLongFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Long.class, null));
	}

	@Test
	public void testNullLongFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Long.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullLongToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Long.class, null));
	}

	@Test
	public void testNullLongToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Long.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullNetworkInterfaceFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(NetworkInterface.class, null));
	}

	@Test
	public void testNullNetworkInterfaceFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(NetworkInterface.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullNetworkInterfaceToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(NetworkInterface.class, null));
	}

	@Test
	public void testNullNetworkInterfaceToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(NetworkInterface.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullNodeFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Node.class, null));
	}

	@Test
	public void testNullNodeFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Node.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullNodeToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Node.class, null));
	}

	@Test
	public void testNullNodeToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Node.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullShortFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Short.class, null));
	}

	@Test
	public void testNullShortFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Short.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullShortToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Short.class, null));
	}

	@Test
	public void testNullShortToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Short.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullSqlDateFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(java.sql.Date.class, null));
	}

	@Test
	public void testNullSqlDateToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(java.sql.Date.class, null));
	}

	@Test
	public void testNullSqlTimeFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Time.class, null));
	}

	@Test
	public void testNullSqlTimeFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Time.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullSqlTimestampFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Timestamp.class, null));
	}

	@Test
	public void testNullSqlTimestampFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Timestamp.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullSqlTimestampToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Timestamp.class, null));
	}

	@Test
	public void testNullSqlTimestampToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Timestamp.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullSqlTimeToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Time.class, null));
	}

	@Test
	public void testNullSqlTimeToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Time.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullStringFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(String.class, null));
	}

	@Test
	public void testNullStringFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(String.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullStringToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(String.class, null));
	}

	@Test
	public void testNullStringToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(String.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullURIFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URI.class, null));
	}

	@Test
	public void testNullURIFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URI.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullURIToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URI.class, null));
	}

	@Test
	public void testNullURIToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URI.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullURLFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URL.class, null));
	}

	@Test
	public void testNullURLFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URL.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullURLToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URL.class, null));
	}

	@Test
	public void testNullURLToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URL.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullUUIDFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(UUID.class, null));
	}

	@Test
	public void testNullUUIDFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(UUID.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullUUIDToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(UUID.class, null));
	}

	@Test
	public void testNullUUIDToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(UUID.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullXMLDurationFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Duration.class, null));
	}

	@Test
	public void testNullXMLDurationFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Duration.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullXMLDurationToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Duration.class, null));
	}

	@Test
	public void testNullXMLDurationToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Duration.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullXMLGregorianCalendarFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(XMLGregorianCalendar.class, null));
	}

	@Test
	public void testNullXMLGregorianCalendarFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(XMLGregorianCalendar.class, null, Locale.GERMAN));
	}

	@Test
	public void testNullXMLGregorianCalendarToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(XMLGregorianCalendar.class, null));
	}

	@Test
	public void testNullXMLGregorianCalendarToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(XMLGregorianCalendar.class, null, Locale.GERMAN));
	}

	@Test
	public void testShortFromConvert() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Short.class, Integer.valueOf(10).shortValue()));
	}

	@Test
	public void testShortFromConvertLocale() throws Exception {
		Assert.assertEquals("10", this.converterTool.convertToString(Short.class, Integer.valueOf(10).shortValue(), Locale.GERMAN));
	}

	@Test
	public void testShortToConvert() throws Exception {
		Assert.assertEquals(Short.valueOf((short) 10), this.converterTool.convertToInstance(Short.class, "10"));
	}

	@Test
	public void testShortToConvertLocale() throws Exception {
		Assert.assertEquals(Short.valueOf((short) 10), this.converterTool.convertToInstance(Short.class, "10", Locale.GERMAN));
	}

	@Test
	public void testSqlDateFromConvert() throws Exception {
		Assert.assertEquals("Sunday, September 9, 2001 3:46:40 AM CEST",
				this.converterTool.convertToString(java.sql.Date.class, new java.sql.Date(1000000000000l)));
	}

	@Test
	public void testSqlDateToConvert() throws Exception {
		Assert.assertEquals(1000000000000l,
				this.converterTool.convertToInstance(java.sql.Date.class, "Sunday, September 9, 2001 3:46:40 AM CEST").getTime());
	}

	@Test
	public void testSqlTimeFromConvert() throws Exception {
		Assert.assertEquals("Thursday, January 1, 1970 1:00:01 AM CET", this.converterTool.convertToString(Time.class, new Time(1000)));
	}

	@Test
	public void testSqlTimeFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Time.class, new Time(1000), Locale.GERMAN);
	}

	@Test
	public void testSqlTimestampFromConvert() throws Exception {
		Assert.assertEquals("Thursday, January 1, 1970 1:00:01 AM CET",
				this.converterTool.convertToString(Timestamp.class, new Timestamp(1000l)));
	}

	@Test
	public void testSqlTimestampFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Timestamp.class, new Timestamp(1000l), Locale.GERMAN);
	}

	@Test
	public void testSqlTimestampToConvert() throws Exception {
		Assert.assertEquals(new Timestamp(1000l),
				this.converterTool.convertToInstance(Timestamp.class, "Thursday, January 1, 1970 1:00:01 AM CET"));
	}

	@Test
	public void testSqlTimeToConvert() throws Exception {
		Assert.assertEquals(new Time(1000), this.converterTool.convertToInstance(Time.class, "Thursday, January 1, 1970 1:00:01 AM CET"));
	}

	@Test
	public void testStringFromConvert() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToString(String.class, "test"));
	}

	@Test
	public void testStringFromConvertLocale() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToString(String.class, "test", Locale.GERMAN));
	}

	@Test
	public void testStringToConvert() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToInstance(String.class, "test"));
	}

	@Test
	public void testStringToConvertLocale() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToInstance(String.class, "test", Locale.GERMAN));
	}

	@Test
	public void testURIFromConvert() throws Exception {
		Assert.assertEquals("http://localhost", this.converterTool.convertToString(URI.class, new URI("http://localhost")));
	}

	@Test
	public void testURIFromConvertLocale() throws Exception {
		Assert.assertEquals("http://localhost", this.converterTool.convertToString(URI.class, new URI("http://localhost"), Locale.GERMAN));
	}

	@Test
	public void testURIToConvert() throws Exception {
		Assert.assertEquals(new URI("http://localhost"), this.converterTool.convertToInstance(URI.class, "http://localhost"));
	}

	@Test
	public void testURIToConvertLocale() throws Exception {
		Assert.assertEquals(new URI("http://localhost"), this.converterTool.convertToInstance(URI.class, "http://localhost", Locale.GERMAN));
	}

	@Test
	public void testURLFromConvert() throws Exception {
		Assert.assertEquals("http://localhost", this.converterTool.convertToString(URL.class, new URL("http://localhost")));
	}

	@Test
	public void testURLFromConvertLocale() throws Exception {
		Assert.assertEquals("http://localhost", this.converterTool.convertToString(URL.class, new URL("http://localhost"), Locale.GERMAN));
	}

	@Test
	public void testURLToConvert() throws Exception {
		Assert.assertEquals(new URL("http://localhost"), this.converterTool.convertToInstance(URL.class, "http://localhost"));
	}

	@Test
	public void testURLToConvertLocale() throws Exception {
		Assert.assertEquals(new URL("http://localhost"), this.converterTool.convertToInstance(URL.class, "http://localhost", Locale.GERMAN));
	}

	@Test
	public void testUUIDFromConvert() throws Exception {
		Assert.assertEquals(ConverterTest.UUID_STR,
				this.converterTool.convertToString(UUID.class, new UUID(ConverterTest.UUID_MSB, ConverterTest.UUID_LSB)));
	}

	@Test
	public void testUUIDFromConvertLocale() throws Exception {
		Assert.assertEquals(ConverterTest.UUID_STR,
				this.converterTool.convertToString(UUID.class, new UUID(ConverterTest.UUID_MSB, ConverterTest.UUID_LSB), Locale.GERMAN));
	}

	@Test
	public void testUUIDToConvert() throws Exception {
		Assert.assertEquals(new UUID(ConverterTest.UUID_MSB, ConverterTest.UUID_LSB),
				this.converterTool.convertToInstance(UUID.class, ConverterTest.UUID_STR));
	}

	@Test
	public void testUUIDToConvertLocale() throws Exception {
		Assert.assertEquals(new UUID(ConverterTest.UUID_MSB, ConverterTest.UUID_LSB),
				this.converterTool.convertToInstance(UUID.class, ConverterTest.UUID_STR, Locale.GERMAN));
	}

	@Test
	public void testXMLDurationFromConvert() throws Exception {
		this.converterTool.convertToString(Duration.class, DatatypeFactory.newInstance().newDuration(1000L));
	}

	@Test
	public void testXMLDurationFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Duration.class, DatatypeFactory.newInstance().newDuration(1000L), Locale.GERMAN);
	}

	@Test
	public void testXMLDurationToConvert() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newDuration(1000L),
				this.converterTool.convertToInstance(Duration.class, "P0Y0M0DT0H0M1.000S"));
	}

	@Test
	public void testXMLDurationToConvertLocale() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newDuration(1000L),
				this.converterTool.convertToInstance(Duration.class, "P0Y0M0DT0H0M1.000S", Locale.GERMAN));
	}

	@Test
	public void testXMLGregorianCalendarFromConvert() throws Exception {
		Assert.assertEquals(
				"2013-01-16T01:50:00.000+00:01",
				this.converterTool.convertToString(XMLGregorianCalendar.class,
						DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1)));
	}

	@Test
	public void testXMLGregorianCalendarFromConvertLocale() throws Exception {
		Assert.assertEquals(
				"2013-01-16T01:50:00.000+00:01",
				this.converterTool.convertToString(XMLGregorianCalendar.class,
						DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1), Locale.GERMAN));
	}

	@Test
	public void testXMLGregorianCalendarToConvert() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1),
				this.converterTool.convertToInstance(XMLGregorianCalendar.class, "2013-01-16T01:50:00.000+00:01"));
	}

	@Test
	public void testXMLGregorianCalendarToConvertLocale() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1),
				this.converterTool.convertToInstance(XMLGregorianCalendar.class, "2013-01-16T01:50:00.000+00:01", Locale.GERMAN));
	}
}
