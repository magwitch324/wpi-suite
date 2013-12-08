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


import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEventList;

public class CalendarData extends AbstractModel {

	/** the ID of the CalendarData */
	private String id;
	private CategoryList categories;
	private CommitmentList commitments;
	private EventList events;
	private RepeatingEventList repeatingEvents;

	/**
	 * Constructs a CalendarData with default characteristics
	 */
	public CalendarData() {
		super();
		id = "";
		this.categories = new CategoryList();
		this.commitments = new CommitmentList(); 
		this.events = new EventList();
		this.repeatingEvents = new RepeatingEventList();
	}

	/**
	 * Construct a CalendarData with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the CalendarData
	 * @param name
	 *            The name of the CalendarData
	 */
	// need to phase out supplying the ID
	public CalendarData(String id) {
		this();
		this.id = id;

	}
	
	
	/**
	 * Returns the list of commitments in this calendar
	 * 
	 * @return the list of commitments
	 */
	public CommitmentList getCommitments(){
		return commitments;
	}
	
	/**
	 * Returns the list of events in this calendar
	 * 
	 * @return the list of events
	 */
	public EventList getEvents(){
		return events;
	}
	
	/**
	 * Returns the list of categories in this calendar
	 * 
	 * @return the list of categories
	 */
	public CategoryList getCategories(){
		return categories;
	}
	
	/**
	 * Returns the list of repeatingEvents in this calendar
	 * 
	 * @return the list of repeating events
	 */
	public RepeatingEventList getRepeatingEvents(){
		return repeatingEvents;
	}
	
	/**
	 * Adds a event to the calendar
	 * 
	 * @param event
	 */
	public void addEvent(Event newEvent){
		events.add(newEvent);
	}
	
	/**
	 * Adds a commitment to the calendar
	 * 
	 * @param commitment
	 */
	public void addCommitment(Commitment newCommitment){
		commitments.add(newCommitment);
	}
	
	/**
	 * Adds a category to the calendar
	 * 
	 * @param category
	 */
	public void addCategory(Category newCategory){
		//TODO add correct call
	}
	
	/**
	 * Adds a repeating event to the calendar
	 * 
	 * @param repeatingEvent
	 */
	public void addRepeatingEvent(RepeatingEvent newEvent){
		repeatingEvents.add(newEvent);
	}
	
	/**
	 * Returns an instance of CalendarData constructed using the given
	 * CalendarData encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded CalendarData to deserialize
	
	 * @return the CalendarData contained in the given JSON */
	public static CalendarData fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarData.class);
	}

	/**
	 * /**Getter for the id
	 * 
	
	 * @return the id */
	public String getId() {
		return id;
	}

	/**
	 * Setter for the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	/**This returns a Json encoded String representation of this CalendarData object.
	 * 
	 * @return a Json encoded String representation of this CalendarData
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, CalendarData.class);
	}

	/**
	 * Returns an array of CalendarData parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Category
	
	 * @return an array of CalendarData deserialized from the given JSON string */
	public static CalendarData[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarData[].class);
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
		return this.getId();
	}

	public void copyFrom(CalendarData toCopyFrom){
		this.id = toCopyFrom.getId();
		this.categories = toCopyFrom.getCategories();
		this.commitments = toCopyFrom.getCommitments();
		this.events = toCopyFrom.getEvents();
		this.repeatingEvents = toCopyFrom.getRepeatingEvents();
	}

	
	
}
