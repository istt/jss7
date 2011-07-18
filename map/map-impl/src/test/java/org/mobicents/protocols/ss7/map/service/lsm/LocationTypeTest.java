/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.protocols.ss7.map.service.lsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.BitSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mobicents.protocols.ss7.map.api.service.lsm.LocationEstimateType;
import org.mobicents.protocols.ss7.map.api.service.lsm.LocationType;
import org.mobicents.protocols.ss7.map.service.lsm.LocationTypeImpl;
import org.mobicents.protocols.ss7.tcap.asn.TcapFactory;
import org.mobicents.protocols.ss7.tcap.asn.comp.Parameter;

/**
 * @author amit bhayani
 * 
 */
public class LocationTypeTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testDecode() throws Exception {
		byte[] data = new byte[] { (byte) 0x80, 0x01, 0x00 };
		Parameter p = TcapFactory.createParameter();
		p.setTag(0x30);
		p.setData(data);
		p.setPrimitive(false);

		LocationType locType = new LocationTypeImpl();
		locType.decode(p);

		assertNotNull(locType.getLocationEstimateType());
		assertEquals(LocationEstimateType.currentLocation, locType.getLocationEstimateType());
		assertNull(locType.getDeferredLocationEventType());

	}

	@Test
	public void testEncode() throws Exception {
		byte[] data = new byte[] { (byte) 0x80, 0x01, 0x00, (byte)0x81, 0x02, 0x04, (byte)-16 };
		BitSet deferredLocationEventType = new BitSet();
		deferredLocationEventType.set(0);
		deferredLocationEventType.set(1);
		deferredLocationEventType.set(2);
		deferredLocationEventType.set(3);
		
		LocationType locType = new LocationTypeImpl(LocationEstimateType.currentLocation, deferredLocationEventType);
		Parameter p = locType.encode();
		assertNotNull(p);
		assertTrue(Arrays.equals(data, p.getData())); 

		// FIXME setting of tag, primitive in Parameter is responsibility of
		// calling class

	}

}