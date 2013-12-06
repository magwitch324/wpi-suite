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

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.LONG;
import static java.util.Calendar.YEAR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
         * Adds a single event to the events of the project
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

}