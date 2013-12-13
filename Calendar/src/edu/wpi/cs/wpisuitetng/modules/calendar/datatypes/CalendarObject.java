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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public abstract class CalendarObject extends AbstractModel {

	/** the Name of the CalendarObject */
	protected String name;
	/** the Description of the CalendarObject */
	protected String description;
	/** the flag to differentiate between personal and team CalendarObject */
	protected boolean isPersonal;
	/** the categoryID of the CalendarObject */        
    protected int categoryID;
    /** the id of the CalendarObject */
    protected int id;

	public CalendarObject() {
		name = description = "";
		isPersonal = false;
		categoryID = 0;
	}
	
	public CalendarObject(String name, String description, int categoryID, boolean isPersonal) {
		this.name = name;
		this.description = description;
		this.categoryID = categoryID;
		this.isPersonal = isPersonal;
	}
	
	public void copyFrom(CalendarObject toCopyFrom) {
		name = toCopyFrom.getName();
		description = toCopyFrom.getDescription();
		isPersonal = toCopyFrom.getIsPersonal();
		categoryID = toCopyFrom.getCategoryID();
		id = toCopyFrom.getID();
	}
	
	// JSON Functions
	/**
	 * Returns an instance of CalendarObject constructed using the given CalendarObject
	 * encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded CalendarObject to deserialize
	 * 
	 * @return the CalendarObject contained in the given JSON
	 */
	public static CalendarObject fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarObject.class);
	}
	
	/**This returns a Json encoded String representation of this CalendarObject.
	 * 
	 * @return a Json encoded String representation of this CalendarObject
	 * 
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, CalendarObject.class);
	}
	
	/**
	 * Returns an array of CalendarObject parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of CalendarObject
	 * 
	 * @return an array of CalendarObject deserialized from the given JSON string
	 */
	public static CalendarObject[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, CalendarObject[].class);
	}
	
	/**
	 * Method identify.
	 * 
	 * @param o
	 *            Object
	 *            
	 * @return Boolean * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}
	
	/**
	 * Method toString.
	 * 
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * @see
	 *         edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	// GETTERS
	/**
	 * Getter for the Name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the Description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/** 
	 * Getter for personal or team commitment
	 * @return is personal
	 */
	public boolean getIsPersonal() {
		return isPersonal;
	}
	
	/**
	 * Getter for categoryID
	 * @return categoryID
	 */
	public int getCategoryID() {
		return categoryID;
	}
	
	/** 
	 * Getter for ID
	 * @return id
	 */
	public int getID() {
		return id;
	}

	// SETTERS
	/**
	 * Setter for the name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for the Description
	 * @param description the Description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Setter for personal or team commitment
	 * @param boolean isPersonal
	 */
	public void setIsPersonal (boolean isPersonal) {
		this.isPersonal = isPersonal;
	}
	
	/**
	 * Setter for categoryID
	 * @param id
	 */
	public void setCategoryID(int id) {
		categoryID = id;
	}
	
	/**
	 * Setter for ID
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	// End Getters and Setters

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
