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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;

public class RepeatingEventList extends CalendarObjectList<RepeatingEvent> {

	/**
	 * The list in which all the events for a single project are contained
	 */
	// Use the super list calendarObjects

	/**
	 * Constructs an empty list of events for the project
	 */
	public RepeatingEventList() {
		super();
	}

	/**
	 * Adds a single event to the events of the project in the correct order
	 * Events are listed with furthest in the future at index 0
	 * 
	 * @param newEve
	 *            The event to be added to the list of events in the project
	 */
	public void add(RepeatingEvent newEvent) {
		int i = 0;
		newEvent.setID(nextID);
		nextID++;
		if (calendarObjects.size() != 0) {
			while (i < calendarObjects.size()) {
				if (newEvent.getStartTime().before(
						calendarObjects.get(i).getEndTime())) {
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
	 * @return the event for the id or null if the event is not found
	 */
	public RepeatingEvent getEvent(int id) {
		return get(id);
	}

	/**
	 * Removes the event with the given ID
	 * 
	 * @param removeId
	 *            The ID number of the event to be removed from the list of
	 *            events in the project
	 */
	public void removeEvent(int id) {
		remove(id);
	}

	/**
	 * Returns the list of the Events
	 * 
	 * @return the events held within the calendarObjects list.
	 */
	public List<RepeatingEvent> getEvents() {
		return getCalendarObjects();
	}
	
	/**
	 * Adds the given array of events to the list
	 * 
	 * @param events
	 *            the array of events to add
	 */
	public void addEvents(RepeatingEvent[] array) {
		addCalendarObjects(array);
	}
	
	/**
	 * Update the Event list
	 * 
	 * @param the Event to be updated
	 */
	public void update(RepeatingEvent newEvent) {
		super.update(newEvent);
	}

	/**
	 * Filter the event list to data between the start and end Calendars This is
	 * INCLUSIVE for both start and end
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<RepeatingEvent> filter(GregorianCalendar start, GregorianCalendar end) {

		GregorianCalendar eventStart = new GregorianCalendar();
		GregorianCalendar eventEnd = new GregorianCalendar();
		List<RepeatingEvent> newEvents = new ArrayList<RepeatingEvent>();

		// iterate and add all Events between start and end
		// to the event list
		for (RepeatingEvent event : calendarObjects) {
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
	 * Converts the list into an event list
	 * 
	 * @return equivalent list of events
	 */
	public CombinedEventList toCombinedEventList(){
		SimpleDateFormat format = new SimpleDateFormat();
		CombinedEventList eventList = new CombinedEventList();
		GregorianCalendar eventStart = new GregorianCalendar();
		GregorianCalendar eventEnd = new GregorianCalendar();
		int field; 

		for(RepeatingEvent event : calendarObjects){
			if(event.getRepType() == RepeatType.DAY){
				field = Calendar.DATE;
			} else if(event.getRepType() == RepeatType.WEEK){
				field = Calendar.WEEK_OF_YEAR;
			} else if(event.getRepType() == RepeatType.MONTH){
				field = Calendar.MONTH;
			} else {
				field = Calendar.DATE;
			}
			for(int i = 0; i < event.getRepetitions(); i++){
				
				
				Event tmp = new Event();
				tmp.setID(event.getID());
				tmp.setName(event.getName());
				tmp.setDescription(event.getDescription());
				tmp.setParticipants(event.getParticipants());
				eventStart = event.getStartTime();
				eventStart.add(field, i);
				//System.out.println(format.format(eventStart));
				tmp.setStartTime(eventStart);
				eventEnd = event.getEndTime();
				eventEnd.add(field, i);
				//System.out.println(format.format(eventEnd));
				tmp.setEndTime(eventEnd);
				tmp.setIsPersonal(event.getIsPersonal());
				tmp.setCategoryID(event.getCategoryID());
				tmp.setIsRepeating(true);
				eventList.add(tmp);
			}
			
		
			
		}
		return eventList;
	}
}