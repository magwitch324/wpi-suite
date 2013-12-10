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

import java.util.GregorianCalendar;

import static java.util.Calendar.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CombinedEventListTest {
	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */

	private static CombinedEventList combinedEventList;
	private static List<Event> eventList;
	
	private final static String[] people = new String[]{"John", "Mary", "Jack" };
	
	private final static GregorianCalendar today = new GregorianCalendar(2013, NOVEMBER, 23, 12, 00, 00);
	private final static GregorianCalendar today13 = new GregorianCalendar(2013, NOVEMBER, 23, 13, 00, 00);
	
	private Event lastYear   = new Event("Last Year",  "A Event from last year",  new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00),  new GregorianCalendar(2012, JANUARY, 30, 13, 00, 00), people , 1, true);
	private Event lastMonth  = new Event("Last Month", "A Event from last month", new GregorianCalendar(2013, OCTOBER, 12, 12, 00, 00),  new GregorianCalendar(2013, OCTOBER, 12, 13, 00, 00), people, 1, true);
	private Event lastWeek   = new Event("Last Week",  "A Event for a week ago",  new GregorianCalendar(2013, NOVEMBER, 16, 12, 00, 00), new GregorianCalendar(2013, NOVEMBER, 16, 13, 00, 00), people, 1, true);
	private Event todayEvent = new Event("Today",      "A Event from today", today, today13, people, 2, true);
	private Event nextWeek   = new Event("Next Week",  "A Event for next week",   new GregorianCalendar(2013, NOVEMBER, 24, 12, 00, 00), new GregorianCalendar(2013, NOVEMBER, 24, 13, 00, 00), people, 1, true);
	private Event nextMonth  = new Event("Next Month", "A Event for next month",  new GregorianCalendar(2013, DECEMBER, 23, 12, 00, 00), new GregorianCalendar(2013, DECEMBER, 23, 13, 00, 00), people, 1, true);
	private Event nextYear   = new Event("Next Year",  "A Event for next year",   new GregorianCalendar(2014, JANUARY, 1, 12, 00, 00),   new GregorianCalendar(2014, JANUARY, 1, 13, 00, 00) , people, 3, true);
	@Before
	public void setup() {
		eventList = new ArrayList<Event>();
		eventList.add(lastYear);
		eventList.add(nextWeek);
		eventList.add(nextMonth);
	}
	
	@Test
	public void addEmptyListTest() {
		combinedEventList = new CombinedEventList();
		combinedEventList.add(todayEvent);
		assertEquals(1, combinedEventList.getSize());
		assertEquals("Today", combinedEventList.getElementAt(0).getName());
	}
	
	@Test
	public void addNonEmptyListTest() {
		combinedEventList = new CombinedEventList(eventList);
		combinedEventList.add(todayEvent);
		assertEquals(4, combinedEventList.getSize());
		assertEquals("Today", combinedEventList.getElementAt(2).getName());
	}

}