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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;

public class EventList {

        /**
         * The list in which all the events for a single project are contained
         */
        private List<Event> events;
        private int nextId;

        /**
         * Constructs an empty list of events for the project
         */
        public EventList (){
                events = new ArrayList<Event>();
                nextId = 0;
        }



        /**
         * Adds a single event to the events of the project in the correct order
         * Events are listed with furthest in the future at index 0
         * 
         * @param newEve The event to be added to the list of events in the project
         */
        public void addEvent(Event newEvent){
        	int i = 0;
    		newEvent.setId(nextId);
    		nextId++;
    		if(events.size() != 0){
    			while (i < events.size()){
    				if(newEvent.getStartTime().before(events.get(i).getEndTime())){
    					break;
    				}
    				i++;
    			}
    		}
    		// add the Event
    		events.add(i, newEvent);

        }
        /**
         * Returns the event with the given ID
         * 
         * @param id The ID number of the event to be returned

         * @return the event for the id or null if the event is not found */
        public Event getEvent(int id)
        {
                Event temp = null;
                // iterate through list of events until id is found
                for (int i=0; i < this.events.size(); i++){
                        temp = events.get(i);
                        if (temp.getId() == id){
                                break;
                        }
                }
                return temp;
        }
        /**
         * Removes the event with the given ID
         * 
         * @param removeId The ID number of the event to be removed from the list of events in the project
         */
        public void removeEvent(int removeId){
                // iterate through list of events until id of project is found
                for (int i=0; i < this.events.size(); i++){
                        if (events.get(i).getId() == removeId){
                                // remove the id
                                events.remove(i);
                                break;
                        }
                }
        }

        /**
         * Provides the number of elements in the list of events for the project. 
         * 
         * @return the number of events in the project * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
         */
        public int getSize() {
                return events.size();
        }
        
        /**
    	 * 
    	 * Provides the next ID number that should be used for a new event that is created.
    	 * 

    	 * @return the next open id number */
    	public int getNextID()
    	{

    		return this.nextId++;
    	}


        /**
         * This function takes an index and finds the event in the list of events
         * for the project. Used internally by the JList in NeweventList.
         * 
         * @param index The index of the event to be returned



         * @return the event associated with the provided index * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int)
         */
        public Event getElementAt(int index) {
                return events.get(events.size() - 1 - index);
        }

        /**
         * Removes all events from this model
         * 
         * NOTE: One cannot simply construct a new instance of
         * the model, because other classes in this module have
         * references to it. Hence, we manually remove each event
         * from the model.
         */
        public void removeAll() {
                events.removeAll(getEvents());
        }

        /**
         * Adds the given array of events to the list
         * 
         * @param events the array of events to add
         */
        public void addEvents(Event[] array) {
        	int i = 0;
    		while(i < array.length){
    			events.add(array[i]);
    			i++;
    		}
        }

        /**
         * Returns the list of the events

         * @return the events held within the eventList. */
        public List<Event> getEvents() {
                return events;
        }
        
        /**
         * Update the event list
         * 
         * @param the event to be update
         */
        public void update (Event newEvent) {
                events.remove(getEvent(newEvent.getId()));
                events.add(newEvent);
        }
        
        /**
    	 * Filter the event list to data between 
    	 * the start and end Calendars
    	 * This is INCLUSIVE for both start and end
    	 * @param start
    	 * @param end
    	 * @return
    	 */
    	public List<Event> filter(Calendar start, Calendar end) {
    		//TODO Change filter to appropriately filter things

    		GregorianCalendar eventStart = new GregorianCalendar();
    		GregorianCalendar eventEnd = new GregorianCalendar();
    		List<Event> newEvents = new ArrayList<Event>();
    		
    		// iterate and add all commitments between start and end
    		// to the commitment list
    		for (Event event : events) {
    			eventStart.setTime(event.getStartTime().getTime());
    			eventEnd.setTime(event.getEndTime().getTime());
    			if (
					//Event start or Event end is within filter times
    				((eventStart.after(start)  && eventStart.before(end)) ||
    				 (eventEnd.after(start)    && eventEnd.before(end))) 
    				 
    				|| 
    				
    				// Event start is before, Event end is after
    				 (eventStart.before(start) && eventEnd.after(end))) {
    				
    				newEvents.add(event);
    			} 
    		}
    		
    		return newEvents;
    	}
    	
    	
    	
    	/**
    	 * Filter the commitment list to data on a specific date
    	 * @param date
    	 * @return ArrayList of commitments on date
    	 */
    	public List<Event> filter(GregorianCalendar date) throws CalendarException {

    		return filter(date, Calendar.DAY_OF_MONTH);
    		
    	}
    	
    	/**
    	 * Filters the calendar data to the "amount"
    	 * around the given date
    	 * ex: 11/23/2013, Calendar.MONTH -> All of November
    	 * @param date
    	 * @param amount
    	 * @return
    	 * @throws CalendarException 
    	 */
    	public List<Event> filter(GregorianCalendar date, int amount) throws CalendarException {
    		GregorianCalendar start = new GregorianCalendar();
    		start.setTime(date.getTime());
    		start.set(Calendar.HOUR_OF_DAY, 0);
    		start.set(Calendar.MINUTE, 0);
    		start.set(Calendar.SECOND, 0);
    		
    		GregorianCalendar end   = new GregorianCalendar();
    		end.setTime(start.getTime());
    		end.set(Calendar.HOUR_OF_DAY, 23);
    		end.set(Calendar.MINUTE, 59);
    		end.set(Calendar.SECOND, 59);
    		
    		
    		/* All methods here add the given amount, then roll back
    		 * one day to specify range by the first and last day 
    		 * within that range
    		 */
    		if (amount == Calendar.DAY_OF_MONTH || amount == Calendar.DAY_OF_WEEK || amount == Calendar.DAY_OF_YEAR) {
    			// Do nothing. This allows error checking at end
    		}
    		else if (amount == Calendar.WEEK_OF_MONTH || amount == Calendar.WEEK_OF_YEAR) {
    			start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
    			end.add(Calendar.WEEK_OF_YEAR, 1);
    			end.add(Calendar.DAY_OF_YEAR, -1);
    		}
    		else if (amount == Calendar.MONTH) {
    			start.set(Calendar.DATE, 1);
    			end.add(Calendar.MONTH, 1);
    			end.add(Calendar.DAY_OF_YEAR, -1);
    		}
    		else if (amount == Calendar.YEAR) {
    			start.set(Calendar.DAY_OF_YEAR, 1);
    			end.add(Calendar.YEAR, 1);
    			end.add(Calendar.DAY_OF_YEAR, -1);
    		} else {
    			throw new CalendarException("Invalid amount! Can only filter around day, week, month, and year types");
    		}
    		
    		//System.out.println("Start: " + printcalendar(start));
    		//System.out.println("End:   " + printcalendar(end));		
    		
    		return filter(start, end);
    	}

}