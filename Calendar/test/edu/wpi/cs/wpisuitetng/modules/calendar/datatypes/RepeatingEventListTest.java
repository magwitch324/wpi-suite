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
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;
import static edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType.*;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class RepeatingEventListTest {

	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */
	
	private static RepeatingEventList RepeatingEvents;
	private static RepeatingEventList repeatingEventList1;
	
	private static final String[] people = new String[]{"John", "Mary", "Jack" };
	
	private static final GregorianCalendar today = 
			new GregorianCalendar(2013, NOVEMBER, 23, 12, 00, 00);
	private static final GregorianCalendar today13 = 
			new GregorianCalendar(2013, NOVEMBER, 23, 13, 00, 00);
	
	final RepeatingEvent lastYear   = 
			new RepeatingEvent("Last Year",  "A RepeatingEvent from last year",  
			new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00),  
			new GregorianCalendar(2012, JANUARY, 30, 13, 00, 00), people, 1, true, 7, WEEK);
	final RepeatingEvent lastMonth  = 
			new RepeatingEvent("Last Month", "A RepeatingEvent from last month", 
			new GregorianCalendar(2013, OCTOBER, 12, 12, 00, 00),  
			new GregorianCalendar(2013, OCTOBER, 12, 13, 00, 00), people, 1, true, 5, DAY);
	final RepeatingEvent lastWeek   = 
			new RepeatingEvent("Last Week",  "A RepeatingEvent for a week ago",  
			new GregorianCalendar(2013, NOVEMBER, 16, 12, 00, 00), 
			new GregorianCalendar(2013, NOVEMBER, 16, 13, 00, 00), 
			people, 1, true, 30, RepeatType.MONTH);
	final RepeatingEvent todayRepeatingEvent = 
			new RepeatingEvent("Today", "A RepeatingEvent from today",
					today, today13, people, 2, true, 2, DAY);
	final RepeatingEvent nextWeek   =
			new RepeatingEvent("Next Week",  "A RepeatingEvent for next week",   
			new GregorianCalendar(2013, NOVEMBER, 24, 12, 00, 00), 
			new GregorianCalendar(2013, NOVEMBER, 24, 13, 00, 00), people, 1, true, 5, WEEK);
	final RepeatingEvent nextMonth  =
			new RepeatingEvent("Next Month", "A RepeatingEvent for next month",  
			new GregorianCalendar(2013, DECEMBER, 23, 12, 00, 00), 
			new GregorianCalendar(2013, DECEMBER, 23, 13, 00, 00), 
			people, 1, true, 10, RepeatType.MONTH);
	final RepeatingEvent nextYear   = 
			new RepeatingEvent("Next Year",  "A RepeatingEvent for next year",   
			new GregorianCalendar(2014, JANUARY, 1, 12, 00, 00),  
			new GregorianCalendar(2014, JANUARY, 1, 13, 00, 00), people, 3, true, 3, null);
	/**
	 * Method setup.
	 */
	@Before
	public void setup() {
		RepeatingEvents = new RepeatingEventList();
		repeatingEventList1 = new RepeatingEventList();
		RepeatingEvents.add(lastYear);
		RepeatingEvents.add(lastMonth);
		RepeatingEvents.add(lastWeek);
		RepeatingEvents.add(todayRepeatingEvent);
		RepeatingEvents.add(nextWeek);
		RepeatingEvents.add(nextMonth);
		RepeatingEvents.add(nextYear);
		
		printlist(RepeatingEvents.getEvents());
		
	}

	/**
	 * Method addOneRepeatingEventTest.
	 */
	@Test
	public void addOneRepeatingEventTest() {
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(lastYear);
		final List<String> peopletest = new ArrayList<String>();
		peopletest.add("John");
		peopletest.add("Mary");
		peopletest.add("Jack");
		final List<RepeatingEvent> repeatingEvents = repeatingEventList1.getEvents();
		assertEquals(1, repeatingEvents.get(0).getCategoryID());
		assertEquals("A RepeatingEvent from last year", repeatingEvents.get(0).getDescription());
		assertEquals(peopletest, repeatingEvents.get(0).getParticipants());
	}
	
	/**
	 * Method afterTest.
	 */
	@After
	public void afterTest() {
		printlist(repeatingEventList1.getEvents());
	}
	
	/**
	 * Method addTwoRepeatingEventTest.
	 */
	@Test
	public void addTwoRepeatingEventTest() {
		repeatingEventList1.add(lastYear);
		final List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		final List<RepeatingEvent> repeatingEvents = repeatingEventList1.getEvents();
		assertEquals(1, repeatingEvents.get(0).getCategoryID());
		assertEquals("A RepeatingEvent from last year", repeatingEvents.get(0).getDescription());
		assertEquals(new GregorianCalendar(2012, JANUARY, 30, 13, 00, 00),
				repeatingEvents.get(0).getEndTime());
		assertEquals(new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00),
				repeatingEvents.get(0).getStartTime());
		assertEquals(people, repeatingEvents.get(0).getParticipants());
		assertEquals(0, repeatingEvents.get(0).getID());
	}
	
	/**
	 * Method getEventTest.
	 */
	@Test
	public void getEventTest() {
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(lastYear);
		assertNotNull(repeatingEventList1.getEvent(0));
		assertNotNull(repeatingEventList1.getEvent(1));
	}
	/**
	 * Method removeEventTest1.
	 */
	@Test
	public void removeEventTest1() {
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(lastYear);
		repeatingEventList1.removeEvent(0);
		assertEquals(1, repeatingEventList1.getSize());
		assertEquals("Last Year", repeatingEventList1.getEvent(0).getName());
	}

	/**
	 * Method removeAllRepeatingEventTest.
	 */
	@Test
	public void removeAllRepeatingEventTest() {
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(lastYear);
		repeatingEventList1.removeAll();
		assertEquals(0, repeatingEventList1.getSize());
	}
	
	/**
	 * Method getElementAtTest.
	 */
	@Test
	public void getElementAtTest() {
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(nextYear);
		repeatingEventList1.add(lastYear);
		assertEquals("Next Year", repeatingEventList1.getElementAt(0).getName());
	}
	
	
	/**
	 * Method getNextIDTest.
	 */
	@Test
	public void getNextIDTest() {
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(nextYear);
		repeatingEventList1.add(lastYear);
		assertEquals(3, repeatingEventList1.getNextID());
	}
	
	/**
	 * Method addsTest.
	 */
	@Test
	public void addsTest() {
		final RepeatingEvent[] repeatingEvents = 
				new RepeatingEvent[]{nextMonth, nextYear, lastYear};
		repeatingEventList1.addEvents(repeatingEvents);
		assertEquals(3, repeatingEventList1.getSize());
	}
	
	/**
	 * Method updateTest.
	 */
	@Test
	public void updateTest() {
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(nextYear);
		repeatingEventList1.add(lastYear);
		lastYear.setName("Changed Last Year");
		repeatingEventList1.update(lastYear);
		assertEquals("Changed Last Year", repeatingEventList1.getElementAt(2).getName());
	}
	
	/**
	 * Method updateWithDifferentStartTimeTest.
	 */
	@Test
	public void updateWithDifferentStartTimeTest() {
		final RepeatingEvent newLastYear = 
				new RepeatingEvent("Last Year",  "A RepeatingEvent from last year",  
				new GregorianCalendar(2012, JANUARY, 29, 12, 00, 00),  
				new GregorianCalendar(2012, JANUARY, 30, 13, 00, 00), people, 1, true, 7, WEEK);
		repeatingEventList1.add(nextMonth);
		repeatingEventList1.add(nextYear);
		repeatingEventList1.add(lastYear);
		repeatingEventList1.update(newLastYear);
		assertEquals(new GregorianCalendar(2012, JANUARY, 29, 12, 00, 00),
				repeatingEventList1.getElementAt(2).getStartTime());
	}
	
	/**
	
	
	
	*/
	
	@Test
	public void toCombinedEventListWeekRepeatTest() {
		repeatingEventList1.add(lastYear);
		final CombinedEventList combinedLastYearEvents = repeatingEventList1.toCombinedEventList();
		final GregorianCalendar startTime = new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00);
		startTime.add(Calendar.WEEK_OF_YEAR, 1);
		assertEquals(7, combinedLastYearEvents.getSize());
		assertEquals(startTime, combinedLastYearEvents.getElementAt(5).getStartTime());
	}
	
	/**
	 * Method toCombinedEventListDayRepeatTest.
	 */
	@Test
	public void toCombinedEventListDayRepeatTest() {
		repeatingEventList1.add(lastMonth);
		final CombinedEventList combinedLastYearEvents = repeatingEventList1.toCombinedEventList();
		final GregorianCalendar startTime = new GregorianCalendar(2013, OCTOBER, 12, 12, 00, 00);
		startTime.add(Calendar.DATE, 1);
		assertEquals(5, combinedLastYearEvents.getSize());
		assertEquals(startTime, combinedLastYearEvents.getElementAt(3).getStartTime());
	}
	
	/**
	 * Method toCombinedEventListMonthRepeatTest.
	 */
	@Test
	public void toCombinedEventListMonthRepeatTest() {
		repeatingEventList1.add(lastWeek);
		final CombinedEventList combinedLastYearEvents = repeatingEventList1.toCombinedEventList();
		final GregorianCalendar startTime = new GregorianCalendar(2013, NOVEMBER, 16, 12, 00, 00);
		startTime.add(Calendar.MONTH, 29);
		assertEquals(30, combinedLastYearEvents.getSize());
		assertEquals(startTime, combinedLastYearEvents.getElementAt(0).getStartTime());
	}
	
	/**
	 * Method toCombinedEventListOtherRepeatTest.
	 */
	@Test
	public void toCombinedEventListOtherRepeatTest() {
		repeatingEventList1.add(nextYear);
		final CombinedEventList combinedLastYearEvents = repeatingEventList1.toCombinedEventList();
		final GregorianCalendar startTime = new GregorianCalendar(2014, JANUARY, 1, 12, 00, 00);
		startTime.add(Calendar.DATE, 2);
		assertEquals(3, combinedLastYearEvents.getSize());
		assertEquals(startTime, combinedLastYearEvents.getElementAt(0).getStartTime());
	}

	// Helper function
	private static void printlist(List<RepeatingEvent> repeatingEvents) {
		System.out.println("RepeatingEvents: ");
		
		for (RepeatingEvent repeatingEvent : repeatingEvents) {
			System.out.println(repeatingEvent.getName());
		}
		
		System.out.println("\n");
	}
}
