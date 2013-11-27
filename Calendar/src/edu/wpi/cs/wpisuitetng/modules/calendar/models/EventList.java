/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventList {

        /**
         * The list in which all the events for a single project are contained
         */
        private List<Event> events;

        /**
         * Constructs an empty list of events for the project
         */
        public EventList (){
                events = new ArrayList<Event>();
        }



        /**
         * Adds a single event to the events of the project
         * 
         * @param newEve The event to be added to the list of events in the project
         */
        public void addEvent(Event newEve){
                // add the event
                events.add(newEve);
                sortByStartTime();

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
                Collections.addAll(events, array);
                sortByStartTime();
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
                sortByStartTime();
        }

        /**
         * Sort the elements in the events according to the alphabet
         */
        public void sortByStartTime() {
                Collections.sort(events, new Event());
        }
}