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
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class EventListTest {

	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */
	
	private static EventList Events;
	private static EventList eventList1;
	
	private final static String[] people = new String[]{"John", "Mary", "Jack" };
	
	private final static GregorianCalendar today = 
			new GregorianCalendar(2013, NOVEMBER, 23, 12, 00, 00);
	private final static GregorianCalendar today13 = 
			new GregorianCalendar(2013, NOVEMBER, 23, 13, 00, 00);
	
	Event lastYear   = new Event("Last Year",  "A Event from last year", 
			new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00), 
			new GregorianCalendar(2012, JANUARY, 30, 13, 00, 00), people , 1, true);
	Event lastMonth  = new Event("Last Month", "A Event from last month",
			new GregorianCalendar(2013, OCTOBER, 12, 12, 00, 00),  
			new GregorianCalendar(2013, OCTOBER, 12, 13, 00, 00), people, 1, true);
	Event lastWeek   = new Event("Last Week",  "A Event for a week ago", 
			new GregorianCalendar(2013, NOVEMBER, 16, 12, 00, 00), 
			new GregorianCalendar(2013, NOVEMBER, 16, 13, 00, 00), people, 1, true);
	Event todayEvent = new Event("Today",
			"A Event from today", today, today13, people, 2, true);
	Event nextWeek   = new Event("Next Week",  "A Event for next week",
			new GregorianCalendar(2013, NOVEMBER, 24, 12, 00, 00), 
			new GregorianCalendar(2013, NOVEMBER, 24, 13, 00, 00), people, 1, true);
	Event nextMonth  = new Event("Next Month", "A Event for next month",
			new GregorianCalendar(2013, DECEMBER, 23, 12, 00, 00),
			new GregorianCalendar(2013, DECEMBER, 23, 13, 00, 00), people, 1, true);
	Event nextYear   = new Event("Next Year",  "A Event for next year",
			new GregorianCalendar(2014, JANUARY, 1, 12, 00, 00),
			new GregorianCalendar(2014, JANUARY, 1, 13, 00, 00) , people, 3, true);
	/**
	 * Method setup.
	 */
	@Before
	public void setup() {
		Events = new EventList();
		eventList1 = new EventList();
		Events.add(lastYear);
		Events.add(lastMonth);
		Events.add(lastWeek);
		Events.add(todayEvent);
		Events.add(nextWeek);
		Events.add(nextMonth);
		Events.add(nextYear);
		
		printlist(Events.getEvents());
		
	}
	
	/**
	 * Method addOneEventTest.
	 */
	@Test
	public void addOneEventTest() {
		eventList1.add(nextMonth);
		eventList1.add(lastYear);
		final List<String> peopletest = new ArrayList<String>();
		peopletest.add("John");
		peopletest.add("Mary");
		peopletest.add("Jack");
		final List<Event> events = eventList1.getEvents();
		assertEquals(1, events.get(0).getCategoryID());
		assertEquals("A Event from last year", events.get(0).getDescription());
		assertEquals(peopletest, events.get(0).getParticipants());
	}
	
	/**
	 * Method afterTest.
	 */
	@After
	public void afterTest() {
		printlist(eventList1.getEvents());
	}
	
	/**
	 * Method addTwoEventTest.
	 */
	@Test
	public void addTwoEventTest() {
		eventList1.add(lastYear);
		final List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		final List<Event> events = eventList1.getEvents();
		assertEquals(1, events.get(0).getCategoryID());
		assertEquals("A Event from last year", events.get(0).getDescription());
		assertEquals(new GregorianCalendar(2012, JANUARY, 30, 13, 00, 00),
				events.get(0).getEndTime());
		assertEquals(new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00),
				events.get(0).getStartTime());
		assertEquals(people, events.get(0).getParticipants());
		assertEquals(0, events.get(0).getID());
	}
	
	/**
	 * Method getEventTest.
	 */
	@Test
	public void getEventTest() {
		eventList1.add(nextMonth);
		eventList1.add(lastYear);
		assertNotNull(eventList1.getEvent(0));
		assertNotNull(eventList1.getEvent(1));
	}
	
	/**
	 * Method removeEventTest1.
	 */
	@Test
	public void removeEventTest1() {
		eventList1.add(nextMonth);
		eventList1.add(lastYear);
		eventList1.removeEvent(0);
		assertEquals(1, eventList1.getSize());
		assertEquals("Last Year", eventList1.getEvent(0).getName());
	}

	/**
	 * Method removeAllEventTest.
	 */
	@Test
	public void removeAllEventTest() {
		eventList1.add(nextMonth);
		eventList1.add(lastYear);
		eventList1.removeAll();
		assertEquals(0, eventList1.getSize());
	}
	
	/**
	 * Method getElementAtTest.
	 */
	@Test
	public void getElementAtTest() {
		eventList1.add(nextMonth);
		eventList1.add(nextYear);
		eventList1.add(lastYear);
		assertEquals("Next Year", eventList1.getElementAt(0).getName());
	}
	
	
	/**
	 * Method getNextIDTest.
	 */
	@Test
	public void getNextIDTest() {
		eventList1.add(nextMonth);
		eventList1.add(nextYear);
		eventList1.add(lastYear);
		assertEquals(3, eventList1.getNextID());
	}
	
	/**
	 * Method addsTest.
	 */
	@Test
	public void addsTest() {
		final Event[] events = new Event[]{nextMonth, nextYear, lastYear};
		eventList1.addEvents(events);
		assertEquals(3, eventList1.getSize());
	}
	
	/**
	 * Method updateTest.
	 */
	@Test
	public void updateTest() {
		eventList1.add(nextMonth);
		eventList1.add(nextYear);
		eventList1.add(lastYear);
		lastYear.setName("Changed Last Year");
		eventList1.update(lastYear);
		assertEquals("Changed Last Year", eventList1.getElementAt(2).getName());
	}
	
	/**
	 * Method filterEventHalfInRange.
	 */
	@Test
	public void filterEventHalfInRange() {
		final EventList events = new EventList();
		events.add(new Event("Test", "Bug", 
				new GregorianCalendar(2013, NOVEMBER, 23, 10, 00, 00), 
				new GregorianCalendar(2013, NOVEMBER, 23, 23, 00, 00), people, 1, true));
		final List<Event> newData = events.filter(
				new GregorianCalendar(2013, NOVEMBER, 23, 1, 00, 00), 
				new GregorianCalendar(2013, NOVEMBER, 23, 13, 00, 00));
		assertEquals(1, newData.size());
	}
	
	/**
	 * Method filterEventAroundRange.
	 */
	@Test
	public void filterEventAroundRange() {
		final EventList events = new EventList();
		events.add(new Event("Test", "Bug", 
				new GregorianCalendar(2013, NOVEMBER, 23, 10, 00, 00),
				new GregorianCalendar(2013, NOVEMBER, 23, 23, 00, 00), people, 1, true));
		final List<Event> newData = events.filter(
				new GregorianCalendar(2013, NOVEMBER, 23, 12, 00, 00),
				new GregorianCalendar(2013, NOVEMBER, 23, 13, 00, 00));
		assertEquals(1, newData.size());
	}

	// Helper function
	private static void printlist(List<Event> events) {
		System.out.println("Events: ");
		
		for (Event event : events) {
			System.out.println(event.getName());
		}
		
		System.out.println("\n");
	}
}