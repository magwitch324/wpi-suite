package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import java.util.ArrayList;
import java.util.List;

public class AbstractCalendarObjectList<E> {
	
	/**
     * The list in which all the CalendarObjects (Event, Commit, etc) for a single project are contained
     */
	private List<E> list;
	private int nextId;
	
	/**
     * Constructs an empty list of events for the project
     */
    public AbstractCalendarObjectList (){
            list = new ArrayList<E>();
            nextId = 0;
    }
	
    /**
     * Adds a single event to the events of the project in the correct order
     * Events are listed with furthest in the future at index 0
     * 
     * @param newEve The event to be added to the list of events in the project
     */
    /*
    public void add(E newE){
    	int i = 0;
		newE.setId(nextId);
		nextId++;
		if(list.size() != 0){
			while (i < list.size()){
				if(newE.getStartTime().before(newE.get(i).getEndTime())){
					break;
				}
				i++;
			}
		}
		// add the Event
		events.add(i, newEvent);
	
    } */
    
}
