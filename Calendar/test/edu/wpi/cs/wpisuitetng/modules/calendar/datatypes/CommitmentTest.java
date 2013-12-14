/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import static org.junit.Assert.assertEquals;
import static java.util.Calendar.*;

import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;

public class CommitmentTest {
	// did not test save(),delete(),toJSON(),fromJsonArray(String),identify(Object)
	// I suspect that the untested functions are unnecessary
	//    and should be considered for removal

	/**
	 * Tests to ensure that a new commitment is created with default values
	 */
	@Test
	public void defaultConstructorTest(){
		final GregorianCalendar tmpCal = new GregorianCalendar();
		tmpCal.setTime(new Date());
		final Commitment testComm = new Commitment();
		assertEquals(0, testComm.getID());
		assertEquals("", testComm.getName());
		assertEquals("", testComm.getDescription());
		//TODO: see if this line can be reworked somehow //assertEquals(tmpCal.getTime(), testComm.getDueDate().getTime());
		//It should test that the dueDate field got initialized properly
		assertEquals(Status.NEW, testComm.getStatus());
		assertEquals(false, testComm.getIsPersonal());
		assertEquals(0, testComm.getCategoryID());
	}
	
	/**
	 * Tests to ensure that the detailed constructor fills in fields correctly
	 */
	@Test
	public void mainConstructorTest(){
		final GregorianCalendar tmpCal = new GregorianCalendar(1, DECEMBER, 2013);
		//tmpCal.setTime(new Date());
		final Commitment testComm = new Commitment("test",tmpCal,"test description",2,true);
		assertEquals(0, testComm.getID());
		assertEquals("test", testComm.getName());
		assertEquals("test description", testComm.getDescription());
		assertEquals(tmpCal.getTime(), testComm.getDueDate().getTime());
		assertEquals(Status.NEW, testComm.getStatus());
		assertEquals(true, testComm.getIsPersonal());
		assertEquals(2, testComm.getCategoryID());
	}
	
	/**
	 * Ensures that setters work correctly
	 */
	@Test
	public void setterConstructorTest(){
		final GregorianCalendar tmpCal = new GregorianCalendar(1, DECEMBER, 2013);
		final GregorianCalendar tmpCal2 = new GregorianCalendar(2, DECEMBER, 2013);
		final Commitment testComm = new Commitment("test",tmpCal,"test description",2,true);
		testComm.setID(2);
		testComm.setName("such testing");
		testComm.setDueDate(tmpCal2);
		testComm.setDescription("rain and thunder");
		testComm.setIsPersonal(false);
		testComm.setCategoryID(3);
		testComm.setStatus(Status.COMPLETED);
		assertEquals(2, testComm.getID());
		assertEquals("such testing", testComm.getName());
		assertEquals("rain and thunder", testComm.getDescription());
		assertEquals(tmpCal2.getTime(), testComm.getDueDate().getTime());
		assertEquals(Status.COMPLETED, testComm.getStatus());
		assertEquals(false, testComm.getIsPersonal());
		assertEquals(3, testComm.getCategoryID());
	}
	
	/**
	 * Tests to make sure copyFrom() works as intended
	 */
	@Test
	public void copyFromTest(){
		final GregorianCalendar tmpCal = new GregorianCalendar(1, DECEMBER, 2013);
		final Commitment testComm2 = new Commitment("test",tmpCal,"test description",2,true);
		testComm2.setID(2);
		testComm2.setStatus(Status.COMPLETED);
		final Commitment testComm = new Commitment();
		testComm.copyFrom(testComm2);
		assertEquals(2, testComm.getID());
		assertEquals("test", testComm.getName());
		assertEquals("test description", testComm.getDescription());
		assertEquals(tmpCal.getTime(), testComm.getDueDate().getTime());
		assertEquals(Status.COMPLETED, testComm.getStatus());
		assertEquals(true, testComm.getIsPersonal());
		assertEquals(2, testComm.getCategoryID());
	}
	
	/**
	 * Tests toString()
	 */
	@Test
	public void toStringTest(){
		// Not sure if necessary, but here for code coverage
		final GregorianCalendar tmpCal = new GregorianCalendar(1, DECEMBER, 2013);
		final Commitment testComm = new Commitment("test",tmpCal,"test description",2,true);
		assertEquals("test", testComm.toString());
	}

	/**
	 * Makes sure that Status.getStatusValue() returns the proper values
	 */
	@Test
	public void statusIdTest(){
		assertEquals(Status.NEW, Status.getStatusValue(0));
		assertEquals(Status.IN_PROGRESS, Status.getStatusValue(1));
		assertEquals(Status.COMPLETED, Status.getStatusValue(2));
	}
	
	/**
	 * Makes sure that Status.convertToString() returns the proper values
	 */
	@Test
	public void statusTextTest(){
		assertEquals("New", Status.convertToString(0));
		assertEquals("In Progress", Status.convertToString(1));
		assertEquals("Completed", Status.convertToString(2));
	}
	
	
}
