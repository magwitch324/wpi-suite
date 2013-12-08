package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import java.util.List;

public class CombinedEventList extends EventList{
	/**
	 * This class is for dealing with combining the list of personal and team events without overriding the ids of the events
	 */

	public CombinedEventList() {
		super();
	}
	
	public CombinedEventList(List<Event> list) {
		this();
		this.calendarObjects = list;
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
						calendarObjects.get(i).getEndTime())) {
					break;
				}
				i++;
			}
		}
		// add the Event
		calendarObjects.add(i, newEvent);

	}	
}
