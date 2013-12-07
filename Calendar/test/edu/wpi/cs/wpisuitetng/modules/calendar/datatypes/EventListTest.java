package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import static java.util.Calendar.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EventListTest {

	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */
	
	private static EventList Events;
	private static EventList eventList1;
	
	private final static GregorianCalendar today = new GregorianCalendar(2013, DECEMBER, 6);
	private final static GregorianCalendar _20120130 = new GregorianCalendar(2012, JANUARY, 30);
	private final static GregorianCalendar _20120520 = new GregorianCalendar(2012, MAY, 20);
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
	
	Event lastYear = new Event("Last Year", "A Event from last year", _20120130, _20120520, people1 , 1);
	Event lastMonth = new Event("Last Month","A Event from last month", _20131111, _20131130, people2, 1);
	Event lastWeek = new Event("Last Week", "A Event for a week ago",_20131126, _20131130, people1, 2);
	Event todayEvent = new Event("Today","A Event from today",today, today, people3, 2);
	Event nextWeek = new Event("Next Week","A Event for next week", _20131209, _20131214, people1, 3);
	Event nextMonth = new Event("Next Month","A Event for next month", _20140101, _20140110, people3, 2);
	Event nnextYear = new Event("Next Next Year", "A Event for the year after next year", _20150130, _20150320 , people1, 3);
	@Before
	public void setup() {
		Events = new EventList();
		eventList1 = new EventList();
		Events.addEvent(new Event("Last Year", "A Event from last year", _20120130, _20120520, people1 , 1));
		Events.addEvent(new Event("Last Month","A Event from last month", _20131111, _20131130, people2, 1));
		Events.addEvent(new Event("Last Week", "A Event for a week ago",_20131126, _20131130, people1, 2));
		Events.addEvent(new Event("Today","A Event from today",today, today, people3, 2));
		Events.addEvent(new Event("Next Week","A Event for next week", _20131209, _20131214, people1, 3));
		Events.addEvent(new Event("Next Month","A Event for next month", _20140101, _20140110, people3, 2));
		Events.addEvent(new Event("Next Next Year", "A Event for the year after next year", _20150130, _20150320 , people1, 3));
		
		printlist(Events.getEvents());
		
	}
	
	@Test
	public void addOneEventTest() {
		eventList1.addEvent(nextMonth);
		eventList1.addEvent(lastYear);
		List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		List<Event> events = eventList1.getEvents();
		assertEquals(1, events.get(0).getCategoryID());
		assertEquals("A Event from last year", events.get(0).getDescription());
		assertEquals(_20120520, events.get(0).getEndTime());
		assertEquals(_20120130, events.get(0).getStartTime());
		assertEquals(people, events.get(0).getParticipants());
		assertEquals(1, events.get(0).getId());
	}
	
	@After
	public void afterTest() {
		printlist(eventList1.getEvents());
	}
	
	@Test
	public void addTwoEventTest() {
		eventList1.addEvent(lastYear);
		List<String> people = new ArrayList<String>();
		people.add("John");
		people.add("Mary");
		people.add("Jack");
		List<Event> events = eventList1.getEvents();
		assertEquals(1, events.get(0).getCategoryID());
		assertEquals("A Event from last year", events.get(0).getDescription());
		assertEquals(_20120520, events.get(0).getEndTime());
		assertEquals(_20120130, events.get(0).getStartTime());
		assertEquals(people, events.get(0).getParticipants());
		assertEquals(0, events.get(0).getId());
	}
	
	@Test
	public void getEventTest() {
		eventList1.addEvent(nextMonth);
		eventList1.addEvent(lastYear);
		assertNotNull(eventList1.getEvent(0));
		assertNotNull(eventList1.getEvent(1));
	}
	
	@Test
	public void removeEventTest1() {
		eventList1.addEvent(nextMonth);
		eventList1.addEvent(lastYear);
		eventList1.removeEvent(0);
		assertEquals(1, eventList1.getSize());
		assertEquals("Last Year", eventList1.getEvent(0).getName());
	}

	@Test
	public void removeAllEventTest() {
		eventList1.addEvent(nextMonth);
		eventList1.addEvent(lastYear);
		eventList1.removeAll();
		assertEquals(0, eventList1.getSize());
	}
	
	@Test
	public void getElementAtTest() {
		eventList1.addEvent(nextMonth);
		eventList1.addEvent(nnextYear);
		eventList1.addEvent(lastYear);
		assertEquals("Next Next Year", eventList1.getElementAt(0).getName());
	}
	
	@Test
	public void getNextIDTest() {
		eventList1.addEvent(nextMonth);
		eventList1.addEvent(nnextYear);
		eventList1.addEvent(lastYear);
		assertEquals(3, eventList1.getNextID());
	}
	
	@Test
	public void addEventsTest() {
		Event[] events = new Event[]{nextMonth, nnextYear, lastYear};
		eventList1.addEvents(events);
		assertEquals(3, eventList1.getSize());
	}
	
	@Test
	public void updateTest() {
		eventList1.addEvent(nextMonth);
		eventList1.addEvent(nnextYear);
		eventList1.addEvent(lastYear);
		lastYear.setName("Changed Last Year");
		eventList1.update(lastYear);
		assertEquals("Changed Last Year", eventList1.getElementAt(0).getName());
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