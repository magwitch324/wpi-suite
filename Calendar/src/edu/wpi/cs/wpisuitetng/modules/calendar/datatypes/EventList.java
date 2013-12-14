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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * List of events that will be used by the gui for display.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class EventList extends CalendarObjectList<Event> {

	/**
	 * The list in which all the events for a single project are contained
	 * @param newEvent Event
	 */
	// Use the super list calendarObjects
	
	/**
	 * Adds a single event to the events of the project in the correct order
	 * Events are listed with furthest in the future at index 0
	 * 
	 * @param newEve
	 *            The event to be added to the list of events in the project
	 */
	public void add(Event newEvent) {
		int i = 0;
		newEvent.setID(nextID);
		nextID++;
		if (calendarObjects.size() != 0) {
			while (i < calendarObjects.size()) {
				if (newEvent.getStartTime().before(
						calendarObjects.get(i).getStartTime())) {
					break;
				}
				i++;
			}
		}
		// add the Event
		calendarObjects.add(i, newEvent);

	}

	/**
	 * Returns the event with the given ID
	 * 
	 * @param id
	 *            The ID number of the event to be returned
	 * 
	
	 * @return the event for the id or null if the event is not found */
	public Event getEvent(int id) {
		return get(id);
	}

	/**
	 * Removes the event with the given ID
	 * 
	
	 * @param id int
	 */
	public void removeEvent(int id) {
		remove(id);
	}

	/**
	 * Returns the list of the Events
	 * 
	 * @return the events held within the calendarObjects list.
	 */
	public List<Event> getEvents() {
		return getCalendarObjects();
	}
	
	/**
	 * Adds the given array of events to the list
	 * 
	
	 * @param array Event[]
	 */
	public void addEvents(Event[] array) {
		addCalendarObjects(array);
	}

	/**
	 * Filter the event list to data between the start and end Calendars This is
	 * INCLUSIVE for both start and end
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Event> filter(GregorianCalendar start, GregorianCalendar end) {

		final GregorianCalendar eventStart = new GregorianCalendar();
		final GregorianCalendar eventEnd = new GregorianCalendar();
		final List<Event> newEvents = new ArrayList<Event>();

		// iterate and add all Events between start and end
		// to the event list
		for (Event event : calendarObjects) {
			eventStart.setTime(event.getStartTime().getTime());
			eventEnd.setTime(event.getEndTime().getTime());
			if (
			// Event start or Event end is within filter times
			((eventStart.after(start) && eventStart.before(end)) || (eventEnd
					.after(start) && eventEnd.before(end)))

			||

			// Event start is before, Event end is after
					(eventStart.before(start) && eventEnd.after(end))) {

				newEvents.add(event);
			}
		}

		return newEvents;
	}
	
	/**
	 * Update the calendarObject list
	 * 
	
	 * @param newObject Event
	 */
	public void update(Event newObject) {
		final Event tmp = get(newObject.getID());
		if(tmp.getStartTime().equals(newObject.getStartTime())){
			final int i = calendarObjects.indexOf(get(newObject.getID()));
			calendarObjects.remove(get(newObject.getID()));
			calendarObjects.add(i, newObject);
			return;
		}
		else{
		calendarObjects.remove(get(newObject.getID()));
		add(newObject);
		}
	}
}