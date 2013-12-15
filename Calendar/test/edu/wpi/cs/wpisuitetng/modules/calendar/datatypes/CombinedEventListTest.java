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

import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.NOVEMBER;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CombinedEventListTest {
	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */

	private static CombinedEventList combinedEventList;
	private static List<Event> eventList;
	
	private static final String[] people = new String[]{"John", "Mary", "Jack" };
	
	private static final GregorianCalendar today = 
			new GregorianCalendar(2013, NOVEMBER, 23, 12, 00, 00);
	private static final GregorianCalendar today13 = 
			new GregorianCalendar(2013, NOVEMBER, 23, 13, 00, 00);
	
	private final Event lastYear   = new Event("Last Year",  
			"A Event from last year",  new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00), 
			new GregorianCalendar(2012, JANUARY, 30, 13, 00, 00), people, 1, true);
	private final Event todayEvent = new Event("Today",     
			"A Event from today", today, today13, people, 2, true);
	private final Event nextWeek   = new Event("Next Week",  "A Event for next week",
			new GregorianCalendar(2013, NOVEMBER, 24, 12, 00, 00), 
			new GregorianCalendar(2013, NOVEMBER, 24, 13, 00, 00), people, 1, true);
	private final Event nextMonth  = new Event("Next Month", "A Event for next month",
			new GregorianCalendar(2013, DECEMBER, 23, 12, 00, 00), 
			new GregorianCalendar(2013, DECEMBER, 23, 13, 00, 00), people, 1, true);
	/**
	 * Method setup.
	 */
	@Before
	public void setup() {
		eventList = new ArrayList<Event>();
		eventList.add(lastYear);
		eventList.add(nextWeek);
		eventList.add(nextMonth);
	}
	
	/*
	 * Test to ensure addEmptyList works correctly
	 */
	/**
	 * Method addEmptyListTest.
	 */
	@Test
	public void addEmptyListTest() {
		combinedEventList = new CombinedEventList();
		combinedEventList.add(todayEvent);
		assertEquals(1, combinedEventList.getSize());
		assertEquals("Today", combinedEventList.getElementAt(0).getName());
	}
	
	/*
	 * Test to ensure addNonEmptyList works correctly
	 */
	/**
	 * Method addNonEmptyListTest.
	 */
	@Test
	public void addNonEmptyListTest() {
		combinedEventList = new CombinedEventList(eventList);
		combinedEventList.add(todayEvent);
		assertEquals(4, combinedEventList.getSize());
		assertEquals("Today", combinedEventList.getElementAt(2).getName());
	}

}
