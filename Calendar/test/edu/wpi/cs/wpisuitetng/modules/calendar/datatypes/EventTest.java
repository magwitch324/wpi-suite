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

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import static java.util.Calendar.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;

public class EventTest {
	
	private final static GregorianCalendar today = new GregorianCalendar(2013, DECEMBER, 6);
	private final static GregorianCalendar _20120129 = new GregorianCalendar(2012, JANUARY, 29);
	private final static GregorianCalendar _20120130 = new GregorianCalendar(2012, JANUARY, 30);
	private final static GregorianCalendar _20131111 = new GregorianCalendar(2013, NOVEMBER, 11);
	private final static GregorianCalendar _20131130 = new GregorianCalendar(2013, NOVEMBER, 30);
	private final static GregorianCalendar _20131126 = new GregorianCalendar(2013, NOVEMBER, 26);
	private final static GregorianCalendar _20131209 = new GregorianCalendar(2013, DECEMBER, 9);
	private final static GregorianCalendar _20131214 = new GregorianCalendar(2013, DECEMBER, 14);
	private final static GregorianCalendar _20140101 = new GregorianCalendar(2014, JANUARY, 01);
	private final static GregorianCalendar _20140110 = new GregorianCalendar(2014, JANUARY, 10);
	private final static GregorianCalendar _20150130 = new GregorianCalendar(2015, JANUARY, 30);
	private final static GregorianCalendar _20150320 = new GregorianCalendar(2015, MARCH, 20);
	private final static String[] people1 = new String[]{"John", "Mary", "Jack" };
	private final static String[] people2 = new String[]{"John", "Mary", "Jack", "Lucy"};
	private final static String[] people3 = new String[]{"Jack", "Lucy"};
	

	/**
	 * Tests to ensure that a new event is created with default values
	 */
	@Test
	public void defaultConstructorTest(){
		GregorianCalendar tmpCal = new GregorianCalendar();
		tmpCal.setTime(new Date());
		Date adate = new Date(0);
		Event testEvent = new Event();
		assertEquals(adate, testEvent.getStartTime().getTime());
		assertEquals(adate, testEvent.getEndTime().getTime());
		assertEquals("", testEvent.getName());
		assertEquals("", testEvent.getDescription());
		assertEquals(0, testEvent.getCategoryID());
	}
	
	/**
	 * Tests to ensure that the detailed constructor fills in fields correctly
	 */
	@Test
	public void mainConstructorTest(){
		GregorianCalendar tmpCal = new GregorianCalendar(1, DECEMBER, 2011);
		//tmpCal.setTime(new Date());
		Event testEvent = new Event ("test","test description",_20120129,_20120130,people1,1, true);
		//add Participants 
		List<String> people = new ArrayList<String>();
		//testing
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		assertEquals("test", testEvent.getName());
		assertEquals("test description", testEvent.getDescription());
		assertEquals(_20120129, testEvent.getStartTime());
		assertEquals(_20120130, testEvent.getEndTime());
		assertEquals(people, testEvent.getParticipants());
		assertEquals(1, testEvent.getCategoryID());
	}
	
	/**
	 * Ensures that setters work correctly
	 */
	@Test
	public void setterConstructorTest(){
		
<<<<<<< HEAD
		Event testEvent = new Event("test","test description",_20120129,_20120130,people1,1, true);
=======
		Event testEvent = new Event("test","test description",_20120129,_20120130,people1,1,true);
>>>>>>> 2caa1de0cfe1560f245aa6a9bc416f94fddf303b
		testEvent.setName("setter testing");
		testEvent.setDescription("setter test description");
		testEvent.setStartTime(_20131209);
		testEvent.setEndTime(_20131214);
		testEvent.setCategoryID(2);
		List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		people.add("Lucy");
		testEvent.setParticipants(people);
		assertEquals("setter testing", testEvent.getName());
		assertEquals("setter test description", testEvent.getDescription());
		assertEquals(_20131209, testEvent.getStartTime());
		assertEquals(_20131214, testEvent.getEndTime());
		assertEquals(people, testEvent.getParticipants());
		assertEquals(2, testEvent.getCategoryID());
	}
	

	/**
	 * Tests to make sure copyFrom() works as intended
	 */
	@Test
	public void copyFromTest(){
<<<<<<< HEAD
		Event testEvent2 = new Event ("test","test description",_20120129,_20120130,people1,1, true);
=======
		Event testEvent2 = new Event ("test","test description",_20120129,_20120130,people1,1,true);
>>>>>>> 2caa1de0cfe1560f245aa6a9bc416f94fddf303b
		testEvent2.setCategoryID(2);
		List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		people.add("Lucy");
		testEvent2.setParticipants(people);
		Event testEvent = new Event();
		testEvent.copyFrom(testEvent2);
		assertEquals("test", testEvent.getName());
		assertEquals("test description", testEvent.getDescription());
		assertEquals(_20120129, testEvent.getStartTime());
		assertEquals(_20120130, testEvent.getEndTime());
		assertEquals(people, testEvent.getParticipants());
		assertEquals(2, testEvent.getCategoryID());
	}
	
}
