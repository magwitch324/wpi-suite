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

import java.util.List;

public class CombinedEventList extends EventList{
	/**
	 * This class is for dealing with combining the list of personal and team events without overriding the ids of the events
	 */

	public CombinedEventList() {
	}
	
	public CombinedEventList(List<Event> list) {
		this();
		calendarObjects = list;
	}
	
	@Override
	/**
	 * Adds a single event to the event of the project, while sorting them into the right order by date
	 * 
	 * @param newEvent The event to be added to the list of events in the project
	 */
	public void add(Event newEvent){
		int i = 0;
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
}
