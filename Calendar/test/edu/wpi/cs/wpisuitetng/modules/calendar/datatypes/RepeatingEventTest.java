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
import java.util.GregorianCalendar;
import static java.util.Calendar.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class RepeatingEventTest {
	private static final GregorianCalendar _20120129 = new GregorianCalendar(2012, JANUARY, 29);
	private static final GregorianCalendar _20120130 = new GregorianCalendar(2012, JANUARY, 30);
	private static final GregorianCalendar _20131209 = new GregorianCalendar(2013, DECEMBER, 9);
	private static final GregorianCalendar _20131214 = new GregorianCalendar(2013, DECEMBER, 14);
	private static final String[] people1 = new String[]{"John", "Mary", "Jack" };
	/**
	 * Tests to ensure that repeating event is created with default values
	 */
	@Test
	public void defaultConstructorTest(){
		final GregorianCalendar tmpCal = new GregorianCalendar();
		tmpCal.setTime(new Date());
		final Date adate = new Date(0);
		final RepeatingEvent testRepeatingEvent = new RepeatingEvent();
		assertEquals("", testRepeatingEvent.getName());
		assertEquals("", testRepeatingEvent.getDescription());
		assertEquals(0, testRepeatingEvent.getCategoryID());
		assertEquals(adate, testRepeatingEvent.getStartTime().getTime());
		assertEquals(adate, testRepeatingEvent.getEndTime().getTime());
		assertEquals(0, testRepeatingEvent.getRepetitions());
		assertEquals(RepeatType.DAY, testRepeatingEvent.getRepType());
	}
	/**
	 * Tests to ensure that the detailed constructor fills in fields correctly
	 */
	@Test
	public void mainConstructorTest(){
		final RepeatingEvent testRepeatingEvent = new RepeatingEvent(
				"test", "test for repeating event", _20120129, _20120130,
				people1, 1, true, 3, RepeatType.WEEK);
		final List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		assertEquals("test", testRepeatingEvent.getName());
		assertEquals("test for repeating event", testRepeatingEvent.getDescription());
		assertEquals(_20120129, testRepeatingEvent.getStartTime());
		assertEquals(_20120130, testRepeatingEvent.getEndTime());
		assertEquals(people, testRepeatingEvent.getParticipants());
		assertEquals(1, testRepeatingEvent.getCategoryID());
		assertTrue(testRepeatingEvent.getIsPersonal());
		assertEquals(3, testRepeatingEvent.getRepetitions());
		assertEquals(RepeatType.WEEK, testRepeatingEvent.getRepType());
	}
	/**
	 * Ensures that setters work correctly
	 */
	@Test
	public void setterConstructorTest(){
		final RepeatingEvent testRepeatingEvent = new RepeatingEvent(
				"test", "test for repeating event", _20120129, _20120130,
				people1, 1, true, 3, RepeatType.WEEK);
		testRepeatingEvent.setStartTime(_20131209);
		testRepeatingEvent.setEndTime(_20131214);
		final List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		people.add("Lucy");
		testRepeatingEvent.setParticipants(people);
		testRepeatingEvent.setRepetitions(2);
		testRepeatingEvent.setRepType(RepeatType.DAY);
		assertEquals(_20131209, testRepeatingEvent.getStartTime());
		assertEquals(_20131214, testRepeatingEvent.getEndTime());
		assertEquals(people, testRepeatingEvent.getParticipants());
		assertEquals(2, testRepeatingEvent.getRepetitions());
		assertEquals(RepeatType.DAY, testRepeatingEvent.getRepType());
	}
	/**
	 * Tests to make sure copyFrom() works as intended
	 */
	@Test
	public void copyFromTest(){
		final RepeatingEvent testRepeatingEvent2 = new RepeatingEvent(
				"test", "test for repeating event", _20120129, _20120130,
				people1, 1, true, 3, RepeatType.WEEK);
		testRepeatingEvent2.setCategoryID(2);
		final List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		testRepeatingEvent2.setParticipants(people);
		final RepeatingEvent testRepeatingEvent = new RepeatingEvent();
		testRepeatingEvent.copyFrom(testRepeatingEvent2);
		assertEquals("test", testRepeatingEvent.getName());
		assertEquals("test for repeating event", testRepeatingEvent.getDescription());
		assertEquals(_20120129, testRepeatingEvent.getStartTime());
		assertEquals(_20120130, testRepeatingEvent.getEndTime());
		assertEquals(people, testRepeatingEvent.getParticipants());
		assertEquals(2, testRepeatingEvent.getCategoryID());
		assertTrue(testRepeatingEvent.getIsPersonal());
		assertEquals(3, testRepeatingEvent.getRepetitions());
		assertEquals(RepeatType.WEEK, testRepeatingEvent.getRepType());
	}
}
