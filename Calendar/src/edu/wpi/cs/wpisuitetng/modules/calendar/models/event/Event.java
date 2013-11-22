package edu.wpi.cs.wpisuitetng.modules.calendar.models.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.event.Event;


public class Event extends AbstractModel {

	/** the ID of the event */
	private int id; // TODO: move ID stuff to server side?

	/** the name of the event */
	private String name;

	/** the date of the event */
	private Date date;

	/** a short description of the event */
	private String description;
	
	/**
	 * team members the event is assigned to need to figure out the class
	 * of a user name, then use that instead of TeamMember
	 */
	private List<String> assignedTo;


	/**
	 * Constructs a Event with default characteristics
	 */
	public Event() {
		super();
		name = description = "";
		date = new Date(0);
	}

	/**
	 * Construct a Event with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the event
	 * @param name
	 *            The name of the event
	 * @param description
	 *            A short description of the event
	 */
	// need to phase out supplying the ID
	public Event(int id, String name, String description, Date date) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
	}


	/**
	 * Returns an instance of Event constructed using the given
	 * Event encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Event to deserialize
	
	 * @return the Event contained in the given JSON */
	public static Event fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Event.class);
	}

	/**
	 * /**Getter for the id
	 * 
	
	 * @return the id */
	public int getId() {
		return id;
	}

	/**
	 * Setter for the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * getter for the name
	 * 
	
	 * @return the name */
	public String getName() {
		return name;
	}

	/**
	 * setter for the name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String n) {
//		if (!n.equals(this.name)) {
//			String originalName = this.name;
//			String newName = n;
//			if (newName.length() > 100)
//				newName = newName.substring(0, 100);
//			String message = ("Name changed from " + originalName + " to " + newName);
//			this.history.add(message);			
//		}
		this.name = n;
		if (name.length() > 100)
			this.name = n.substring(0, 100);
	}


	/**
	 * Getter for the description
	 * 
	
	 * @return the description */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for the description
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String desc) {
//		if (!desc.equals(this.description) && !wasCreated) {
//			this.history.add("Description changed");			
//		}
		this.description = desc;
	}



	/**
	 * Getter for AssignedTo
	 * 
	
	 * @return the list of strings representing the users for whom the
	 *         event has been assigned to. */
	public List<String> getAssignedTo() {
		return assignedTo;
	}

	/**
	 * Setter for assignedTo
	 * 
	 * @param assignedTo
	 *            the list of strings representing the people who the
	 *            event is assigned to.
	 */
	public void setAssignedTo(List<String> assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method toJSON.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this event object.
	 * 
	 * @return a Json encoded String representation of this event
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Event.class);
	}

	/**
	 * Returns an array of Events parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Event
	
	 * @return an array of Event deserialized from the given JSON string */
	public static Event[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Event[].class);
	}

	/**
	 * Method identify.
	 * @param o Object
	
	
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method toString.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}


	
	/**
	 * Copies all of the values from the given event to this event.
	 * 
	 * @param toCopyFrom
	 *            the event to copy from.
	 */
	public void copyFrom(Event toCopyFrom) {
		this.description = toCopyFrom.description;
		this.name = toCopyFrom.name;
		this.date = toCopyFrom.date;
	}

	public Date getDate() {
		
		return this.date;
	}

	
	
}
