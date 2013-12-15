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


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.FilterList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEventList;

 /**
  * Data model for the calendar. Add/delete objects inside a specific calendar.
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
public class CalendarData extends AbstractModel {

	/** the ID of the CalendarData */
	private String id;
	/** data containers*/
	private CategoryList categories;
	private CommitmentList commitments;
	private EventList events;
	private FilterList filters;
	private RepeatingEventList repeatingEvents;

	/**
	 * Constructs a CalendarData with default characteristics
	 */
	public CalendarData() {
		id = "";
		categories = new CategoryList();
		commitments = new CommitmentList(); 
		events = new EventList();
		filters = new FilterList();
		repeatingEvents = new RepeatingEventList();
	}

	/**
	 * Construct a CalendarData with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the CalendarData
	
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
	 * Returns the list of filters in this calendar
	 * 
	 * @return the list of filters
	 */
	public FilterList getFilters(){
		return filters;
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
	
	 * @param newEvent Event
	 */
	public void addEvent(Event newEvent){
		events.add(newEvent);
	}
	
	/**
	 * Adds a commitment to the calendar
	 * 
	
	 * @param newCommitment Commitment
	 */
	public void addCommitment(Commitment newCommitment){
		commitments.add(newCommitment);
	}
	
	/**
	 * Adds a category to the calendar
	 * 
	
	 * @param newCategory Category
	 */
	public void addCategory(Category newCategory){
		categories.add(newCategory);
	}
	
	/**
	 * Adds a filter to the calendar
	 * 
	
	 * @param newFilter Filter
	 */
	public void addFilter(Filter newFilter){
		filters.add(newFilter);
	}
	
	/**
	 * Adds a repeating event to the calendar
	 * 
	
	 * @param newEvent RepeatingEvent
	 */
	public void addRepeatingEvent(RepeatingEvent newEvent){
		repeatingEvents.add(newEvent);
	}
	
	/**
	 * Removes all old data
	 */
	public void removeYearOld(){
		final GregorianCalendar yearOld = new GregorianCalendar();
		yearOld.setTime(new Date());
		yearOld.add(Calendar.YEAR, -1);
		int i = 0;
		//remove all events that are a year old
		while(i < events.getEvents().size()){
			Event e = events.getEvents().get(i);
			if(e.getStartTime().before(yearOld)){
				System.out.println("REMOVING: " + e.getName());
				events.getEvents().remove(e);
			}
			else{
				break;
			}
		}
		
		//remove all commitments that are a year old
		while(i < commitments.getCommitments().size()){
			Commitment c = commitments.getCommitments().get(i);
			if(c.getDueDate().before(yearOld)){
				commitments.getCommitments().remove(c);
			}
			else{
				break;
			}
		}
		
		//remove all repevents that have the last event older than a year
		while(i < repeatingEvents.getEvents().size()){
			RepeatingEvent r = repeatingEvents.getEvents().get(i);
			GregorianCalendar tmp = new GregorianCalendar();
			tmp.setTime(r.getStartTime().getTime());
			switch(r.getRepType().ordinal()){
			case 0: tmp.add(Calendar.DATE, r.getRepetitions());
					break;
			case 1: tmp.add(Calendar.WEEK_OF_YEAR, r.getRepetitions());
					break;
			case 2: tmp.add(Calendar.MONTH, r.getRepetitions());
					break;
			}
			if(tmp.before(yearOld)){
				repeatingEvents.getEvents().remove(r);
			}
			else{
				i++;
			}
			
		}
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
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
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
	
	
	 * @return Boolean
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method toString.
	
	
	 * @return String 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		return id;
	}

	/**
	 * Method copyFrom.
	 * @param toCopyFrom CalendarData
	 */
	public void copyFrom(CalendarData toCopyFrom){
		id = toCopyFrom.getId();
		categories = toCopyFrom.getCategories();
		commitments = toCopyFrom.getCommitments();
		events = toCopyFrom.getEvents();
		repeatingEvents = toCopyFrom.getRepeatingEvents();
	}

	
	
}
