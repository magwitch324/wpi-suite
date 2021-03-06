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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;

/**
 * List of repeating events that will be used by the gui for display.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class RepeatingEventList extends CalendarObjectList<RepeatingEvent> {

	/**
	 * The list in which all the events for a single project are contained
	 * @param newEvent RepeatingEvent
	 */
	// Use the super list calendarObjects

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
			while (i < calendarObjects.size()) {//adds the event in order
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
	public RepeatingEvent getEvent(int id) {
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
	public List<RepeatingEvent> getEvents() {
		return getCalendarObjects();
	}
	
	/**
	 * Adds the given array of events to the list
	 * 
	
	 * @param array RepeatingEvent[]
	 */
	public void addEvents(RepeatingEvent[] array) {
		addCalendarObjects(array);
	}
	
	/**
	 * Update the calendarObject list
	 * 
	
	 * @param newObject RepeatingEvent
	 */
	public void update(RepeatingEvent newObject) {
		final RepeatingEvent tmp = get(newObject.getID());
		if(tmp.getStartTime().equals(newObject.getStartTime())){
			final int i = calendarObjects.indexOf(get(newObject.getID()));
			calendarObjects.remove(get(newObject.getID()));
			calendarObjects.add(i, newObject);
		}
		else{
			calendarObjects.remove(get(newObject.getID()));
			add(newObject);
		}
	}

	/**
	 * Filter the event list to data between the start and end Calendars This is
	 * INCLUSIVE for both start and end
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @deprecated function isn't useful for this datatype 
	 * use {@link toCombinedEventList()} to convert to CombinedEventList
	 */
	@Deprecated
	public List<RepeatingEvent> filter(GregorianCalendar start, GregorianCalendar end) {

		return null;
	}
	
	/**
	 * Converts the list into an equivalent CombinedEvent list
	 * 
	
	 * @return equivalent list of events */
	public CombinedEventList toCombinedEventList(){
		final CombinedEventList eventList = new CombinedEventList();
		GregorianCalendar eventStart = new GregorianCalendar();
		GregorianCalendar eventEnd = new GregorianCalendar();
		int field; 

		//loops through repeating events
		for(RepeatingEvent event : calendarObjects){
			//sets which calendar field needs to be incremented based on repeat type
			if(event.getRepType() == RepeatType.DAY){
				field = Calendar.DATE;
			} else if(event.getRepType() == RepeatType.WEEK){
				field = Calendar.WEEK_OF_YEAR;
			} else if(event.getRepType() == RepeatType.MONTH){
				field = Calendar.MONTH;
			} else {
				field = Calendar.DATE;
			}
			
			//loops through each occurrence of the Repeating Event
			for(int i = 0; i < event.getRepetitions(); i++){
				
				//all the fields are copied to an event with the start and end time incremented
				// to make it recur
				Event tmp = new Event();
				tmp.setID(event.getID());
				tmp.setName(event.getName());
				tmp.setDescription(event.getDescription());
				tmp.setParticipants(event.getParticipants());
				//adjusts start time for this occurrence
				eventStart = event.getStartTime();
				eventStart.add(field, i);// the field always corresponds to 
										 //		how often the event has to repeat,
										 //		so we can just increment it by how many 
										 //		occurrences we are after the first one
				tmp.setStartTime(eventStart);
				//adjusts end time for this occurrence
				eventEnd = event.getEndTime();
				eventEnd.add(field, i);
				tmp.setEndTime(eventEnd);
				tmp.setIsPersonal(event.getIsPersonal());
				tmp.setCategoryID(event.getCategoryID());
				tmp.setIsRepeating(true);//Tells the GUI that the event is repeating
				eventList.add(tmp);
			}
			
		
			
		}
		return eventList;
	}
}