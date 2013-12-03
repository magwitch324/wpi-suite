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
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;

public class CalendarProps extends AbstractModel {

	/** the ID of the CalendarProps */
	private String id;
	private boolean showMyComm;
	private boolean showTeamComm;
	private boolean showTeamData;

	/**
	 * Constructs a CalendarProps with default characteristics
	 */
	public CalendarProps() {
		super();
		id = "";
		this.showMyComm = false;
		this.showTeamComm = false;
		this.showTeamData = false;
	}

	/**
	 * Construct a CalendarProps with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the CalendarProps
	 * @param name
	 *            The name of the CalendarProps
	 */
	// need to phase out supplying the ID
	public CalendarProps(String id) {
		this();
		this.id = id;

	}

	/**
	 * setter for persisting whether commitments should be shown on the calendar
	 * 
	 * @param showComm
	 */
	public void setMyShowComm(boolean showComm){
		this.showMyComm = showComm;
	}
	
	/**
	 * getter for setting whether show comm is selected at startup
	 * 
	 */
	public boolean getMyShowComm(){
		return this.showMyComm;
	}
	
	/**
	 * setter for persisting whether commitments should be shown on the calendar
	 * 
	 * @param showComm
	 */
	public void setTeamShowComm(boolean showComm){
		this.showTeamComm = showComm;
	}
	
	/**
	 * getter for setting whether show comm is selected at startup
	 * 
	 */
	public boolean getTeamShowComm(){
		return this.showTeamComm;
	}

	
	/**
	 * setter for persisting whether team data should be shown on the personal calendar
	 * 
	 * @param showComm
	 */
	public void setShowTeamData(boolean showTeam){
		this.showTeamData = showTeam;
	}
	
	/**
	 * getter for setting whether show team data is selected at startup
	 * 
	 */
	public boolean getShowTeamData(){
		return this.showTeamData;
	}
	
	/**
	 * Returns an instance of calendarProps constructed using the given
	 * calendarProps encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded calendarProps to deserialize
	
	 * @return the calendarProps contained in the given JSON */
	public static CalendarProps fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarProps.class);
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
	/**This returns a Json encoded String representation of this calendarProps object.
	 * 
	 * @return a Json encoded String representation of this calendarProps
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, CalendarProps.class);
	}

	/**
	 * Returns an array of calendarProps parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Category
	
	 * @return an array of calendarProps deserialized from the given JSON string */
	public static CalendarProps[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarProps[].class);
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

	public void copyFrom(CalendarProps toCopyFrom){
		this.id = toCopyFrom.getId();
		this.showMyComm = toCopyFrom.getMyShowComm();
		this.showTeamComm = toCopyFrom.getTeamShowComm();
		this.showTeamData = toCopyFrom.getShowTeamData();
	}

	
	
}
