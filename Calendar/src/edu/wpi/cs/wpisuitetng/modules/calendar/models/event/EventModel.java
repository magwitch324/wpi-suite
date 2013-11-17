package edu.wpi.cs.wpisuitetng.modules.calendar.models.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;




import edu.wpi.cs.wpisuitetng.modules.calendar.controller.events.AddEventController;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.event.EventModel;

public class EventModel extends AbstractListModel {

	/**
	 * The list in which all the events for a single project are contained
	 */
	private List<Event> events;
	private int nextID; // the next available ID number for the events that are added.
	
	//the static object to allow the event model to be 
	private static EventModel instance; 

	/**
	 * Constructs an empty list of events for the project
	 */
	private EventModel (){
		events = new ArrayList<Event>();
		nextID = 0;
	}
	
	/**
	
	 * @return the instance of the event model singleton. */
	public static EventModel getInstance()
	{
		if(instance == null)
		{
			instance = new EventModel();
		}
		
		return instance;
	}
	
	/**
	 * Adds a single event to the events of the project
	 * 
	 * @param newReq The event to be added to the list of events in the project
	 */
	public void addEvent(Event newReq){
		// add the event
		events.add(newReq);
		try 
		{
			AddEventController.getInstance().addEvent(newReq);
		}
		catch(Exception e)
		{
			
		}
	}
	/**
	 * Returns the Event with the given ID
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
		try {
		}
		catch(Exception e) {}
	}

	/**
	 * Provides the number of elements in the list of events for the project. This
	 * function is called internally by the JList in NewEventPanel. Returns elements
	 * in reverse order, so the newest event is returned first.
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
		
		return this.nextID++;
	}

	/**
	 * This function takes an index and finds the event in the list of events
	 * for the project. Used internally by the JList in NewEventModel.
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
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Event> iterator = events.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		
	}
	
	/**
	 * Adds the given array of events to the list
	 * 
	 * @param events the array of events to add
	 */
	public void addEvents(Event[] events) {
		for (int i = 0; i < events.length; i++) {
			this.events.add(events[i]);
			if(events[i].getId() >= nextID) nextID = events[i].getId() + 1;
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
		
	}

	/**
	 * Returns the list of the events
	
	 * @return the events held within the eventmodel. */
	public List<Event> getEvents() {
		return events;
	}

	
	
	
	

	


	
	
}
