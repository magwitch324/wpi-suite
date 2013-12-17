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

 /**
  * Calendar properties will help gui display and hide commitments/events
  * and determine the view mode. (Can be year/month/week/day).
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
public class CalendarProperties extends AbstractModel {

	/** the ID of the CalendarProps */
	private String id;
	private boolean showMyComm;
	private boolean showMyEvent;
	private int myTeamBoth;
	private boolean showTeamData;
	private int commViewMode;
	private int eventViewMode;
	private boolean showCommRange;
	private int categoryTabView;

	/**
	 * Constructs a CalendarProps with default characteristics
	 */
	public CalendarProperties() {
		id = "";
		showMyComm = false;
		showMyEvent = false;
		myTeamBoth = 0;
		showTeamData = false;
		showCommRange = false;
		commViewMode = 0;
		eventViewMode = 0;
		categoryTabView = 0;
	}

	/**
	 * Construct a CalendarProps with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the CalendarProps
	
	 */
	// need to phase out supplying the ID
	public CalendarProperties(String id) {
		this();
		this.id = id;

	}

	/**
	 * setter for persisting whether commitments should be shown on the calendar
	 * 
	 * @param showComm
	 */
	public void setMyShowComm(boolean showComm){
		showMyComm = showComm;
	}
	
	/**
	 * getter for setting whether show comm is selected at startup
	 * 
	 */
	public boolean getMyShowComm(){
		return showMyComm;
	}
	
	
	/**
	 * setter for persisting whether event should be shown on the calendar
	 * 
	 * @param showEvent
	 */
	public void setMyShowEvent(boolean showEvent){
		showMyEvent = showEvent;
	}
	
	/**
	 * getter for setting whether show event is selected at startup
	 * 
	 */
	public boolean getMyShowEvent(){
		return showMyEvent;
	}
	
	
	/**
	 * setter for persisting whether commitments should be shown on the calendar
	 * 
	 * @param showComm
	 */
	public void setMyTeamBoth(int myTeamBoth){
		this.myTeamBoth = myTeamBoth;
	}
	
	/**
	 * getter for setting whether show comm is selected at startup
	 * 
	 */
	public int getMyTeamBoth(){
		return myTeamBoth;
	}

	
	/**
	 * setter for persisting whether team data should be shown on the personal calendar
	 * 
	 * @param showComm
	 */
	public void setShowTeamData(boolean showTeam){
		showTeamData = showTeam;
	}
	
	/**
	 * getter for setting whether show team data is selected at startup
	 * 
	 */
	public boolean getShowTeamData(){
		return showTeamData;
	}
	
	/**
	 * getter for setting what is selected in all comm view
	 * 
	 * @param showComm
	 */
	public void setCommitmentViewMode(int mode){
		commViewMode = mode;
	}
	
	/**
	 * getter for setting what is selected in all comm view
	 * 
	 */
	public int getCommitmentViewMode(){
		return commViewMode;
	}
	
	
	/**
	 * setter for setting what is selected in all event view
	 * 
	 * @param mode
	 */
	public void setEventViewMode(int mode){
		eventViewMode = mode;
	}
	
	
	/**
	 * getter for setting what is selected in all event view
	 * 
	 */
	public int getEventViewMode(){
		return eventViewMode;
	}

	
	
	/**
	 * setter for setting whether commitpane is showing in range or all
	 * 
	 * @param showAll
	 */
	public void setShowCommRange(boolean showAll){
		showCommRange = showAll;
	}
	
	/**
	 * getter for setting whether commitpane is showing in range or all
	 * 
	 */
	public boolean getShowCommRange(){
		return showCommRange;
	}
	
	
	/**
	 * setter for setting whether categories tab shows team/personal/both
	 * 
	 * @param view
	 */
	public void setCategoryTabView(int view){
		categoryTabView = view;
	}
	
	/**
	 * getter for whether categories tab shows team/personal/both
	 * 
	 */
	public int getCategoryTabView(){
		return categoryTabView;
	}
	
	/**
	 * Returns an instance of calendarProps constructed using the given
	 * calendarProps encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded calendarProps to deserialize
	
	 * @return the calendarProps contained in the given JSON */
	public static CalendarProperties fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarProperties.class);
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
	
	
	 * @return String 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this calendarProps object.
	 * 
	 * @return a Json encoded String representation of this calendarProps
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, CalendarProperties.class);
	}

	/**
	 * Returns an array of calendarProps parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Category
	
	 * @return an array of calendarProps deserialized from the given JSON string */
	public static CalendarProperties[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarProperties[].class);
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
	 * @param toCopyFrom CalendarProps
	 */
	public void copyFrom(CalendarProperties toCopyFrom){
		id = toCopyFrom.getId();
		showMyComm = toCopyFrom.getMyShowComm();
		showMyEvent = toCopyFrom.getMyShowEvent();
		myTeamBoth = toCopyFrom.getMyTeamBoth();
		showTeamData = toCopyFrom.getShowTeamData();
		commViewMode = toCopyFrom.getCommitmentViewMode();
		eventViewMode = toCopyFrom.getEventViewMode();
		showCommRange = toCopyFrom.getShowCommRange();
		categoryTabView = toCopyFrom.getCategoryTabView();
	}

	
	
}
