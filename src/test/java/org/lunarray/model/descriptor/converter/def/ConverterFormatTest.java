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
public class ConverterFormatTest {

	private static final String DOCUMENT_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><document/>";
	private static final Node DOM_NODE;
	private static final String ETH_NAME;
	private static final long UUID_LSB;
	private static final long UUID_MSB;
	private static final String UUID_STR;
	private final ConverterTool converterTool = new DelegatingEnumConverterTool(new DefaultConverterTool());

	static {
		String eth = "test";
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
		Assert.assertEquals("true", this.converterTool.convertToString(AtomicBoolean.class, new AtomicBoolean(true), "test"));
	}

	@Test
	public void testAtomicBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals("true", this.converterTool.convertToString(AtomicBoolean.class, new AtomicBoolean(true), Locale.GERMAN, "test"));
	}

	@Test
	public void testAtomicBooleanToConvert() throws Exception {
		Assert.assertEquals(true, this.converterTool.convertToInstance(AtomicBoolean.class, "true", "test").get());
	}

	@Test
	public void testAtomicBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(true, this.converterTool.convertToInstance(AtomicBoolean.class, "true", Locale.GERMAN, "test").get());
	}

	@Test
	public void testAtomicIntegerFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(AtomicInteger.class, new AtomicInteger(10), "test"));
	}

	@Test
	public void testAtomicIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(AtomicInteger.class, new AtomicInteger(10), Locale.GERMAN, "test"));
	}

	@Test
	public void testAtomicIntegerToConvert() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicInteger.class, "test10", "test").get());
	}

	@Test
	public void testAtomicIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicInteger.class, "test10", Locale.GERMAN, "test").get());
	}

	@Test
	public void testAtomicLongFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(AtomicLong.class, new AtomicLong(10), "test"));
	}

	@Test
	public void testAtomicLongFromConvertLocale() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(AtomicLong.class, new AtomicLong(10), Locale.GERMAN, "test"));
	}

	@Test
	public void testAtomicLongToConvert() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicLong.class, "test10", "test").get());
	}

	@Test
	public void testAtomicLongToConvertLocale() throws Exception {
		Assert.assertEquals(10, this.converterTool.convertToInstance(AtomicLong.class, "test10", Locale.GERMAN, "test").get());
	}

	@Test
	public void testBigDecimalFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(BigDecimal.class, BigDecimal.valueOf(10.1), "test"));
	}

	@Test
	public void testBigDecimalFromConvertLocale() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(BigDecimal.class, BigDecimal.valueOf(10.1), Locale.GERMAN, "test"));
	}

	@Test
	public void testBigDecimalToConvert() throws Exception {
		Assert.assertEquals(BigDecimal.valueOf(10), this.converterTool.convertToInstance(BigDecimal.class, "test10", "test"));
	}

	@Test
	public void testBigDecimalToConvertLocale() throws Exception {
		Assert.assertEquals(BigDecimal.valueOf(10), this.converterTool.convertToInstance(BigDecimal.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testBigIntegerFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(BigInteger.class, BigInteger.valueOf(10), "test"));
	}

	@Test
	public void testBigIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(BigInteger.class, BigInteger.valueOf(10), Locale.GERMAN, "test"));
	}

	@Test
	public void testBigIntegerToConvert() throws Exception {
		Assert.assertEquals(BigInteger.valueOf(10), this.converterTool.convertToInstance(BigInteger.class, "test10", "test"));
	}

	@Test
	public void testBigIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(BigInteger.valueOf(10), this.converterTool.convertToInstance(BigInteger.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testBooleanFromConvert() throws Exception {
		Assert.assertEquals("true", this.converterTool.convertToString(Boolean.class, Boolean.valueOf(true).booleanValue(), "test"));
	}

	@Test
	public void testBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals("true",
				this.converterTool.convertToString(Boolean.class, Boolean.valueOf(true).booleanValue(), Locale.GERMAN, "test"));
	}

	@Test
	public void testBooleanToConvert() throws Exception {
		Assert.assertEquals(Boolean.valueOf(true), this.converterTool.convertToInstance(Boolean.class, "true", "test"));
	}

	@Test
	public void testBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(Boolean.valueOf(true), this.converterTool.convertToInstance(Boolean.class, "true", Locale.GERMAN, "test"));
	}

	@Test
	public void testByteFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Byte.class, Integer.valueOf(10).byteValue(), "test"));
	}

	@Test
	public void testByteFromConvertLocale() throws Exception {
		Assert.assertEquals("test10",
				this.converterTool.convertToString(Byte.class, Integer.valueOf(10).byteValue(), Locale.GERMAN, "test"));
	}

	@Test
	public void testByteToConvert() throws Exception {
		Assert.assertEquals(Byte.valueOf((byte) 10), this.converterTool.convertToInstance(Byte.class, "test10", "test"));
	}

	@Test
	public void testByteToConvertLocale() throws Exception {
		Assert.assertEquals(Byte.valueOf((byte) 10), this.converterTool.convertToInstance(Byte.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testCalendarFromConvert() throws Exception {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(1000000000000l);
		Assert.assertEquals("20010909 03:46:40", this.converterTool.convertToString(Calendar.class, calendar, "yyyyMMdd HH:mm:ss"));
	}

	@Test
	public void testCalendarToConvert() throws Exception {
		Assert.assertEquals(999999960000l, this.converterTool.convertToInstance(Calendar.class, "20010909 03:46:40", "yyyyMMdd HH:mm")
				.getTimeInMillis());
	}

	@Test
	public void testCharacterFromConvert() throws Exception {
		Assert.assertEquals("t", this.converterTool.convertToString(Character.class, Character.valueOf('t'), "test"));
	}

	@Test
	public void testCharacterFromConvertLocale() throws Exception {
		Assert.assertEquals("t", this.converterTool.convertToString(Character.class, Character.valueOf('t'), Locale.GERMAN, "test"));
	}

	@Test
	public void testCharacterToConvert() throws Exception {
		Assert.assertEquals(Character.valueOf('t'), this.converterTool.convertToInstance(Character.class, "t", "test"));
	}

	@Test
	public void testCharacterToConvertLocale() throws Exception {
		Assert.assertEquals(Character.valueOf('t'), this.converterTool.convertToInstance(Character.class, "t", Locale.GERMAN, "test"));
	}

	@Test
	public void testClassFromConvert() throws Exception {
		Assert.assertEquals(ConverterFormatTest.class.getCanonicalName(),
				this.converterTool.convertToString(Class.class, ConverterFormatTest.class, "test"));
	}

	@Test
	public void testClassFromConvertLocale() throws Exception {
		Assert.assertEquals(ConverterFormatTest.class.getCanonicalName(),
				this.converterTool.convertToString(Class.class, ConverterFormatTest.class, Locale.GERMAN, "test"));
	}

	@Test
	public void testClassToConvert() throws Exception {
		Assert.assertEquals(ConverterFormatTest.class,
				this.converterTool.convertToInstance(Class.class, ConverterFormatTest.class.getCanonicalName(), "test"));
	}

	@Test
	public void testClassToConvertLocale() throws Exception {
		Assert.assertEquals(ConverterFormatTest.class,
				this.converterTool.convertToInstance(Class.class, ConverterFormatTest.class.getCanonicalName(), Locale.GERMAN, "test"));
	}

	@Test
	public void testCompositeNameFromConvert() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToString(CompositeName.class, new CompositeName("test"), "test"));
	}

	@Test
	public void testCompositeNameFromConvertLocale() throws Exception {
		Assert.assertEquals("test",
				this.converterTool.convertToString(CompositeName.class, new CompositeName("test"), Locale.GERMAN, "test"));
	}

	@Test
	public void testCompositeNameToConvert() throws Exception {
		Assert.assertEquals(new CompositeName("test"), this.converterTool.convertToInstance(CompositeName.class, "test", "test"));
	}

	@Test
	public void testCompositeNameToConvertLocale() throws Exception {
		Assert.assertEquals(new CompositeName("test"),
				this.converterTool.convertToInstance(CompositeName.class, "test", Locale.GERMAN, "test"));
	}

	@Test
	public void testCurrencyFromConvert() throws Exception {
		Assert.assertEquals("EUR", this.converterTool.convertToString(Currency.class, Currency.getInstance("EUR"), "test"));
	}

	@Test
	public void testCurrencyFromConvertLocale() throws Exception {
		Assert.assertEquals("EUR", this.converterTool.convertToString(Currency.class, Currency.getInstance("EUR"), Locale.GERMAN, "test"));
	}

	@Test
	public void testCurrencyToConvert() throws Exception {
		Assert.assertEquals(Currency.getInstance("EUR"), this.converterTool.convertToInstance(Currency.class, "EUR", "test"));
	}

	@Test
	public void testCurrencyToConvertLocale() throws Exception {
		Assert.assertEquals(Currency.getInstance("EUR"), this.converterTool.convertToInstance(Currency.class, "EUR", Locale.GERMAN, "test"));
	}

	@Test
	public void testDateFromConvert() throws Exception {
		Assert.assertEquals("20010909 03:46:40",
				this.converterTool.convertToString(Date.class, new Date(1000000000000l), "yyyyMMdd HH:mm:ss"));
	}

	@Test
	public void testDateToConvert() throws Exception {
		Assert.assertEquals(1000000000000l, this.converterTool.convertToInstance(Date.class, "20010909 3:46:40", "yyyyMMdd HH:mm:ss")
				.getTime());
	}

	@Test
	public void testDoubleFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Double.class, Double.valueOf(10.1).doubleValue(), "test"));
	}

	@Test
	public void testDoubleFromConvertLocale() throws Exception {
		Assert.assertEquals("test10",
				this.converterTool.convertToString(Double.class, Double.valueOf(10.1).doubleValue(), Locale.GERMAN, "test"));
	}

	@Test
	public void testDoubleToConvert() throws Exception {
		Assert.assertEquals(Double.valueOf(10), this.converterTool.convertToInstance(Double.class, "test10", "test"));
	}

	@Test
	public void testDoubleToConvertLocale() throws Exception {
		Assert.assertEquals(Double.valueOf(10), this.converterTool.convertToInstance(Double.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testEnumFromConvert() throws Exception {
		Assert.assertEquals("TEST02", this.converterTool.convertToString(SampleEnum.class, SampleEnum.TEST02, "test"));
	}

	@Test
	public void testEnumFromConvertLocale() throws Exception {
		Assert.assertEquals("TEST02", this.converterTool.convertToString(SampleEnum.class, SampleEnum.TEST02, Locale.GERMAN, "test"));
	}

	@Test
	public void testEnumToConvert() throws Exception {
		Assert.assertEquals(SampleEnum.TEST02, this.converterTool.convertToInstance(SampleEnum.class, "TEST02", "test"));
	}

	@Test
	public void testEnumToConvertLocale() throws Exception {
		Assert.assertEquals(SampleEnum.TEST02, this.converterTool.convertToInstance(SampleEnum.class, "TEST02", Locale.GERMAN, "test"));
	}

	@Test
	public void testFloatFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Float.class, Double.valueOf(10.1).floatValue(), "test"));
	}

	@Test
	public void testFloatFromConvertLocale() throws Exception {
		Assert.assertEquals("test10",
				this.converterTool.convertToString(Float.class, Double.valueOf(10.1).floatValue(), Locale.GERMAN, "test"));
	}

	@Test
	public void testFloatToConvert() throws Exception {
		Assert.assertEquals(Float.valueOf(10), this.converterTool.convertToInstance(Float.class, "test10", "test"));
	}

	@Test
	public void testFloatToConvertLocale() throws Exception {
		Assert.assertEquals(Float.valueOf(10), this.converterTool.convertToInstance(Float.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testInetAddressFromConvert() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1",
				this.converterTool.convertToString(InetAddress.class, InetAddress.getByName("localhost"), "test"));
	}

	@Test
	public void testInetAddressFromConvertLocale() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1",
				this.converterTool.convertToString(InetAddress.class, InetAddress.getByName("localhost"), Locale.GERMAN, "test"));
	}

	@Test
	public void testInetAddressToConvert() throws Exception {
		Assert.assertEquals(InetAddress.getByName("localhost"),
				this.converterTool.convertToInstance(InetAddress.class, "localhost", "test"));
	}

	@Test
	public void testInetAddressToConvertLocale() throws Exception {
		Assert.assertEquals(InetAddress.getByName("localhost"),
				this.converterTool.convertToInstance(InetAddress.class, "localhost", Locale.GERMAN, "test"));
	}

	@Test
	public void testInetSocketAddressFromConvert() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1:1000",
				this.converterTool.convertToString(InetSocketAddress.class, new InetSocketAddress("localhost", 1000), "test"));
	}

	@Test
	public void testInetSocketAddressFromConvertLocale() throws Exception {
		Assert.assertEquals("localhost/127.0.0.1:1000", this.converterTool.convertToString(InetSocketAddress.class, new InetSocketAddress(
				"localhost", 1000), Locale.GERMAN, "test"));
	}

	@Test
	public void testInetSocketAddressToConvert() throws Exception {
		Assert.assertEquals(new InetSocketAddress("localhost", 1000),
				this.converterTool.convertToInstance(InetSocketAddress.class, "localhost:1000", "test"));
	}

	@Test
	public void testInetSocketAddressToConvertLocale() throws Exception {
		Assert.assertEquals(new InetSocketAddress("localhost", 1000),
				this.converterTool.convertToInstance(InetSocketAddress.class, "localhost:1000", Locale.GERMAN, "test"));
	}

	@Test
	public void testIntegerFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Integer.class, Integer.valueOf(10), "test"));
	}

	@Test
	public void testIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Integer.class, Integer.valueOf(10), Locale.GERMAN, "test"));
	}

	@Test
	public void testIntegerToConvert() throws Exception {
		Assert.assertEquals(Integer.valueOf(10), this.converterTool.convertToInstance(Integer.class, "test10", "test"));
	}

	@Test
	public void testIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(Integer.valueOf(10), this.converterTool.convertToInstance(Integer.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testLdapNameFromConvert() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LdapName.class, new LdapName("cn=test"), "test"));
	}

	@Test
	public void testLdapNameFromConvertLocale() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LdapName.class, new LdapName("cn=test"), Locale.GERMAN, "test"));
	}

	@Test
	public void testLdapNameToConvert() throws Exception {
		Assert.assertEquals(new LdapName("cn=test"), this.converterTool.convertToInstance(LdapName.class, "cn=test", "test"));
	}

	@Test
	public void testLdapNameToConvertLocale() throws Exception {
		Assert.assertEquals(new LdapName("cn=test"), this.converterTool.convertToInstance(LdapName.class, "cn=test", Locale.GERMAN, "test"));
	}

	@Test
	public void testLinkRefFromConvert() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LinkRef.class, new LinkRef("cn=test"), "test"));
	}

	@Test
	public void testLinkRefFromConvertLocale() throws Exception {
		Assert.assertEquals("cn=test", this.converterTool.convertToString(LinkRef.class, new LinkRef("cn=test"), Locale.GERMAN, "test"));
	}

	@Test
	public void testLinkRefToConvert() throws Exception {
		Assert.assertEquals(new LinkRef("cn=test"), this.converterTool.convertToInstance(LinkRef.class, "cn=test", "test"));
	}

	@Test
	public void testLinkRefToConvertLocale() throws Exception {
		Assert.assertEquals(new LinkRef("cn=test"), this.converterTool.convertToInstance(LinkRef.class, "cn=test", Locale.GERMAN, "test"));
	}

	@Test
	public void testLongFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Long.class, Long.valueOf(10), "test"));
	}

	@Test
	public void testLongFromConvertLocale() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Long.class, Long.valueOf(10), Locale.GERMAN, "test"));
	}

	@Test
	public void testLongToConvert() throws Exception {
		Assert.assertEquals(Long.valueOf(10), this.converterTool.convertToInstance(Long.class, "test10", "test"));
	}

	@Test
	public void testLongToConvertLocale() throws Exception {
		Assert.assertEquals(Long.valueOf(10), this.converterTool.convertToInstance(Long.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testNetworkInterfaceFromConvert() throws Exception {
		Assert.assertEquals(ConverterFormatTest.ETH_NAME, this.converterTool.convertToString(NetworkInterface.class,
				NetworkInterface.getByName(ConverterFormatTest.ETH_NAME), "test"));
	}

	@Test
	public void testNetworkInterfaceFromConvertLocale() throws Exception {
		Assert.assertEquals(ConverterFormatTest.ETH_NAME, this.converterTool.convertToString(NetworkInterface.class,
				NetworkInterface.getByName(ConverterFormatTest.ETH_NAME), Locale.GERMAN, "test"));
	}

	@Test
	public void testNetworkInterfaceToConvert() throws Exception {
		Assert.assertEquals(NetworkInterface.getByName(ConverterFormatTest.ETH_NAME),
				this.converterTool.convertToInstance(NetworkInterface.class, ConverterFormatTest.ETH_NAME, "test"));
	}

	@Test
	public void testNetworkInterfaceToConvertLocale() throws Exception {
		Assert.assertEquals(NetworkInterface.getByName(ConverterFormatTest.ETH_NAME),
				this.converterTool.convertToInstance(NetworkInterface.class, ConverterFormatTest.ETH_NAME, Locale.GERMAN, "test"));
	}

	@Test
	public void testNodeFromConvert() throws Exception {
		this.converterTool.convertToString(Node.class, ConverterFormatTest.DOM_NODE, "test");
	}

	@Test
	public void testNodeFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Node.class, ConverterFormatTest.DOM_NODE, Locale.GERMAN, "test");
	}

	@Test
	public void testNodeToConvert() throws Exception {
		this.converterTool.convertToInstance(Node.class, ConverterFormatTest.DOCUMENT_CONTENT);
	}

	@Test
	public void testNodeToConvertLocale() throws Exception {
		this.converterTool.convertToInstance(Node.class, ConverterFormatTest.DOCUMENT_CONTENT, Locale.GERMAN);
	}

	@Test
	public void testNullAtomicBooleanFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicBoolean.class, null, "test"));
	}

	@Test
	public void testNullAtomicBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicBoolean.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullAtomicBooleanToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicBoolean.class, null, "test"));
	}

	@Test
	public void testNullAtomicBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicBoolean.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullAtomicIntegerFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicInteger.class, null, "test"));
	}

	@Test
	public void testNullAtomicIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicInteger.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullAtomicIntegerToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicInteger.class, null, "test"));
	}

	@Test
	public void testNullAtomicIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicInteger.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullAtomicLongFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicLong.class, null, "test"));
	}

	@Test
	public void testNullAtomicLongFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(AtomicLong.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullAtomicLongToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicLong.class, null, "test"));
	}

	@Test
	public void testNullAtomicLongToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(AtomicLong.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullBigDecimalFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigDecimal.class, null, "test"));
	}

	@Test
	public void testNullBigDecimalFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigDecimal.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullBigDecimalToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigDecimal.class, null, "test"));
	}

	@Test
	public void testNullBigDecimalToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigDecimal.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullBigIntegerFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigInteger.class, null, "test"));
	}

	@Test
	public void testNullBigIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(BigInteger.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullBigIntegerToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigInteger.class, null, "test"));
	}

	@Test
	public void testNullBigIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(BigInteger.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullBooleanFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Boolean.class, null, "test"));
	}

	@Test
	public void testNullBooleanFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Boolean.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullBooleanToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Boolean.class, null, "test"));
	}

	@Test
	public void testNullBooleanToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Boolean.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullByteFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Byte.class, null, "test"));
	}

	@Test
	public void testNullByteFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Byte.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullByteToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Byte.class, null, "test"));
	}

	@Test
	public void testNullByteToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Byte.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullCalendarFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Calendar.class, null, "test"));
	}

	@Test
	public void testNullCalendarToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Calendar.class, null, "test"));
	}

	@Test
	public void testNullCharacterFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Character.class, null, "test"));
	}

	@Test
	public void testNullCharacterFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Character.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullCharacterToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Character.class, null, "test"));
	}

	@Test
	public void testNullCharacterToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Character.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullClassFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Class.class, null, "test"));
	}

	@Test
	public void testNullClassFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Class.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullClassToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Class.class, null, "test"));
	}

	@Test
	public void testNullClassToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Class.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullCompositeNameFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(CompositeName.class, null, "test"));
	}

	@Test
	public void testNullCompositeNameFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(CompositeName.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullCompositeNameToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(CompositeName.class, null, "test"));
	}

	@Test
	public void testNullCompositeNameToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(CompositeName.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullCurrencyFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Currency.class, null, "test"));
	}

	@Test
	public void testNullCurrencyFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Currency.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullCurrencyToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Currency.class, null, "test"));
	}

	@Test
	public void testNullCurrencyToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Currency.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullDateFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Date.class, null, "test"));
	}

	@Test
	public void testNullDateToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Date.class, null, "test"));
	}

	@Test
	public void testNullDoubleFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Double.class, null, "test"));
	}

	@Test
	public void testNullDoubleFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Double.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullDoubleToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Double.class, null, "test"));
	}

	@Test
	public void testNullDoubleToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Double.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullEnumFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(SampleEnum.class, null, "test"));
	}

	@Test
	public void testNullEnumFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(SampleEnum.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullEnumToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(SampleEnum.class, null, "test"));
	}

	@Test
	public void testNullEnumToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(SampleEnum.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullFloatFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Float.class, null, "test"));
	}

	@Test
	public void testNullFloatFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Float.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullFloatToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Float.class, null, "test"));
	}

	@Test
	public void testNullFloatToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Float.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullInetAddressFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetAddress.class, null, "test"));
	}

	@Test
	public void testNullInetAddressFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetAddress.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullInetAddressToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetAddress.class, null, "test"));
	}

	@Test
	public void testNullInetAddressToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetAddress.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullInetSocketAddressFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetSocketAddress.class, null, "test"));
	}

	@Test
	public void testNullInetSocketAddressFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(InetSocketAddress.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullInetSocketAddressToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetSocketAddress.class, null, "test"));
	}

	@Test
	public void testNullInetSocketAddressToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(InetSocketAddress.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullIntegerFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Integer.class, null, "test"));
	}

	@Test
	public void testNullIntegerFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Integer.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullIntegerToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Integer.class, null, "test"));
	}

	@Test
	public void testNullIntegerToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Integer.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullLdapNameFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LdapName.class, null, "test"));
	}

	@Test
	public void testNullLdapNameFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LdapName.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullLdapNameToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LdapName.class, null, "test"));
	}

	@Test
	public void testNullLdapNameToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LdapName.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullLinkRefFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LinkRef.class, null, "test"));
	}

	@Test
	public void testNullLinkRefFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(LinkRef.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullLinkRefToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LinkRef.class, null, "test"));
	}

	@Test
	public void testNullLinkRefToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(LinkRef.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullLongFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Long.class, null, "test"));
	}

	@Test
	public void testNullLongFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Long.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullLongToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Long.class, null, "test"));
	}

	@Test
	public void testNullLongToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Long.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullNetworkInterfaceFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(NetworkInterface.class, null, "test"));
	}

	@Test
	public void testNullNetworkInterfaceFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(NetworkInterface.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullNetworkInterfaceToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(NetworkInterface.class, null, "test"));
	}

	@Test
	public void testNullNetworkInterfaceToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(NetworkInterface.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullNodeFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Node.class, null, "test"));
	}

	@Test
	public void testNullNodeFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Node.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullNodeToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Node.class, null, "test"));
	}

	@Test
	public void testNullNodeToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Node.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullShortFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Short.class, null, "test"));
	}

	@Test
	public void testNullShortFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Short.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullShortToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Short.class, null, "test"));
	}

	@Test
	public void testNullShortToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Short.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullSqlDateFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(java.sql.Date.class, null, "test"));
	}

	@Test
	public void testNullSqlDateToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(java.sql.Date.class, null, "test"));
	}

	@Test
	public void testNullSqlTimeFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Time.class, null, "test"));
	}

	@Test
	public void testNullSqlTimeFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Time.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullSqlTimestampFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Timestamp.class, null, "test"));
	}

	@Test
	public void testNullSqlTimestampFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Timestamp.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullSqlTimestampToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Timestamp.class, null, "test"));
	}

	@Test
	public void testNullSqlTimestampToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Timestamp.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullSqlTimeToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Time.class, null, "test"));
	}

	@Test
	public void testNullSqlTimeToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Time.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullStringFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(String.class, null, "test"));
	}

	@Test
	public void testNullStringFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(String.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullStringToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(String.class, null, "test"));
	}

	@Test
	public void testNullStringToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(String.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullURIFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URI.class, null, "test"));
	}

	@Test
	public void testNullURIFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URI.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullURIToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URI.class, null, "test"));
	}

	@Test
	public void testNullURIToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URI.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullURLFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URL.class, null, "test"));
	}

	@Test
	public void testNullURLFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(URL.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullURLToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URL.class, null, "test"));
	}

	@Test
	public void testNullURLToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(URL.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullUUIDFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(UUID.class, null, "test"));
	}

	@Test
	public void testNullUUIDFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(UUID.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullUUIDToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(UUID.class, null, "test"));
	}

	@Test
	public void testNullUUIDToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(UUID.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullXMLDurationFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Duration.class, null, "test"));
	}

	@Test
	public void testNullXMLDurationFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(Duration.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullXMLDurationToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Duration.class, null, "test"));
	}

	@Test
	public void testNullXMLDurationToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(Duration.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullXMLGregorianCalendarFromConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(XMLGregorianCalendar.class, null, "test"));
	}

	@Test
	public void testNullXMLGregorianCalendarFromConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToString(XMLGregorianCalendar.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testNullXMLGregorianCalendarToConvert() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(XMLGregorianCalendar.class, null, "test"));
	}

	@Test
	public void testNullXMLGregorianCalendarToConvertLocale() throws Exception {
		Assert.assertEquals(null, this.converterTool.convertToInstance(XMLGregorianCalendar.class, null, Locale.GERMAN, "test"));
	}

	@Test
	public void testShortFromConvert() throws Exception {
		Assert.assertEquals("test10", this.converterTool.convertToString(Short.class, Integer.valueOf(10).shortValue(), "test"));
	}

	@Test
	public void testShortFromConvertLocale() throws Exception {
		Assert.assertEquals("test10",
				this.converterTool.convertToString(Short.class, Integer.valueOf(10).shortValue(), Locale.GERMAN, "test"));
	}

	@Test
	public void testShortToConvert() throws Exception {
		Assert.assertEquals(Short.valueOf((short) 10), this.converterTool.convertToInstance(Short.class, "test10", "test"));
	}

	@Test
	public void testShortToConvertLocale() throws Exception {
		Assert.assertEquals(Short.valueOf((short) 10), this.converterTool.convertToInstance(Short.class, "test10", Locale.GERMAN, "test"));
	}

	@Test
	public void testSqlDateFromConvert() throws Exception {
		Assert.assertEquals("20010909 03:46:40",
				this.converterTool.convertToString(java.sql.Date.class, new java.sql.Date(1000000000000l), "yyyyMMdd HH:mm:ss"));
	}

	@Test
	public void testSqlDateToConvert() throws Exception {
		Assert.assertEquals(1000000000000l,
				this.converterTool.convertToInstance(java.sql.Date.class, "20010909 3:46:40", "yyyyMMdd HH:mm:ss").getTime());
	}

	@Test
	public void testSqlTimeFromConvert() throws Exception {
		Assert.assertEquals("19700101 01:00:01", this.converterTool.convertToString(Time.class, new Time(1000), "yyyyMMdd HH:mm:ss"));
	}

	@Test
	public void testSqlTimeFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Time.class, new Time(1000), Locale.GERMAN, "yyyyMMdd HH:mm:ss");
	}

	@Test
	public void testSqlTimestampFromConvert() throws Exception {
		Assert.assertEquals("19700101 01:00:01",
				this.converterTool.convertToString(Timestamp.class, new Timestamp(1000l), "yyyyMMdd HH:mm:ss"));
	}

	@Test
	public void testSqlTimestampFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Timestamp.class, new Timestamp(1000l), Locale.GERMAN, "yyyyMMdd HH:mm:ss");
	}

	@Test
	public void testSqlTimestampToConvert() throws Exception {
		Assert.assertEquals(new Timestamp(1000l),
				this.converterTool.convertToInstance(Timestamp.class, "19700101 01:00:01", "yyyyMMdd HH:mm:ss"));
	}

	@Test
	public void testSqlTimeToConvert() throws Exception {
		Assert.assertEquals(new Time(1000), this.converterTool.convertToInstance(Time.class, "19700101 01:00:01", "yyyyMMdd HH:mm:ss"));
	}

	@Test
	public void testStringFromConvert() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToString(String.class, "test", "test"));
	}

	@Test
	public void testStringFromConvertLocale() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToString(String.class, "test", Locale.GERMAN, "test"));
	}

	@Test
	public void testStringToConvert() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToInstance(String.class, "test", "test"));
	}

	@Test
	public void testStringToConvertLocale() throws Exception {
		Assert.assertEquals("test", this.converterTool.convertToInstance(String.class, "test", Locale.GERMAN, "test"));
	}

	@Test
	public void testURIFromConvert() throws Exception {
		Assert.assertEquals("http://localhost", this.converterTool.convertToString(URI.class, new URI("http://localhost"), "test"));
	}

	@Test
	public void testURIFromConvertLocale() throws Exception {
		Assert.assertEquals("http://localhost",
				this.converterTool.convertToString(URI.class, new URI("http://localhost"), Locale.GERMAN, "test"));
	}

	@Test
	public void testURIToConvert() throws Exception {
		Assert.assertEquals(new URI("http://localhost"), this.converterTool.convertToInstance(URI.class, "http://localhost", "test"));
	}

	@Test
	public void testURIToConvertLocale() throws Exception {
		Assert.assertEquals(new URI("http://localhost"),
				this.converterTool.convertToInstance(URI.class, "http://localhost", Locale.GERMAN, "test"));
	}

	@Test
	public void testURLFromConvert() throws Exception {
		Assert.assertEquals("http://localhost", this.converterTool.convertToString(URL.class, new URL("http://localhost"), "test"));
	}

	@Test
	public void testURLFromConvertLocale() throws Exception {
		Assert.assertEquals("http://localhost",
				this.converterTool.convertToString(URL.class, new URL("http://localhost"), Locale.GERMAN, "test"));
	}

	@Test
	public void testURLToConvert() throws Exception {
		Assert.assertEquals(new URL("http://localhost"), this.converterTool.convertToInstance(URL.class, "http://localhost", "test"));
	}

	@Test
	public void testURLToConvertLocale() throws Exception {
		Assert.assertEquals(new URL("http://localhost"),
				this.converterTool.convertToInstance(URL.class, "http://localhost", Locale.GERMAN, "test"));
	}

	@Test
	public void testUUIDFromConvert() throws Exception {
		Assert.assertEquals(ConverterFormatTest.UUID_STR, this.converterTool.convertToString(UUID.class, new UUID(
				ConverterFormatTest.UUID_MSB, ConverterFormatTest.UUID_LSB), "test"));
	}

	@Test
	public void testUUIDFromConvertLocale() throws Exception {
		Assert.assertEquals(ConverterFormatTest.UUID_STR, this.converterTool.convertToString(UUID.class, new UUID(
				ConverterFormatTest.UUID_MSB, ConverterFormatTest.UUID_LSB), Locale.GERMAN, "test"));
	}

	@Test
	public void testUUIDToConvert() throws Exception {
		Assert.assertEquals(new UUID(ConverterFormatTest.UUID_MSB, ConverterFormatTest.UUID_LSB),
				this.converterTool.convertToInstance(UUID.class, ConverterFormatTest.UUID_STR, "test"));
	}

	@Test
	public void testUUIDToConvertLocale() throws Exception {
		Assert.assertEquals(new UUID(ConverterFormatTest.UUID_MSB, ConverterFormatTest.UUID_LSB),
				this.converterTool.convertToInstance(UUID.class, ConverterFormatTest.UUID_STR, Locale.GERMAN, "test"));
	}

	@Test
	public void testXMLDurationFromConvert() throws Exception {
		this.converterTool.convertToString(Duration.class, DatatypeFactory.newInstance().newDuration(1000L), "test");
	}

	@Test
	public void testXMLDurationFromConvertLocale() throws Exception {
		this.converterTool.convertToString(Duration.class, DatatypeFactory.newInstance().newDuration(1000L), Locale.GERMAN, "test");
	}

	@Test
	public void testXMLDurationToConvert() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newDuration(1000L),
				this.converterTool.convertToInstance(Duration.class, "P0Y0M0DT0H0M1.000S", "test"));
	}

	@Test
	public void testXMLDurationToConvertLocale() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newDuration(1000L),
				this.converterTool.convertToInstance(Duration.class, "P0Y0M0DT0H0M1.000S", Locale.GERMAN, "test"));
	}

	@Test
	public void testXMLGregorianCalendarFromConvert() throws Exception {
		Assert.assertEquals(
				"2013-01-16T01:50:00.000+00:01",
				this.converterTool.convertToString(XMLGregorianCalendar.class,
						DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1), "test"));
	}

	@Test
	public void testXMLGregorianCalendarFromConvertLocale() throws Exception {
		Assert.assertEquals(
				"2013-01-16T01:50:00.000+00:01",
				this.converterTool.convertToString(XMLGregorianCalendar.class,
						DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1), Locale.GERMAN, "test"));
	}

	@Test
	public void testXMLGregorianCalendarToConvert() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1),
				this.converterTool.convertToInstance(XMLGregorianCalendar.class, "2013-01-16T01:50:00.000+00:01", "test"));
	}

	@Test
	public void testXMLGregorianCalendarToConvertLocale() throws Exception {
		Assert.assertEquals(DatatypeFactory.newInstance().newXMLGregorianCalendar(2013, 1, 16, 1, 50, 0, 0, 1),
				this.converterTool.convertToInstance(XMLGregorianCalendar.class, "2013-01-16T01:50:00.000+00:01", Locale.GERMAN, "test"));
	}
}
