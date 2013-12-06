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

import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Commitment extends AbstractModel implements Comparable<Commitment>{
	// Commitment characteristics.
	/** the ID of the commitment */
	private int id;
	/** the Name of the commitment */
	private String name;
	/** the DueDate of the commitment */
	private Date dueDate;
	/** the Description of the commitment */
	private String description;
	/** the categoryID of the category */
	private int categoryId;
	/** the flag to differentiate between personal and team commitment */
	private boolean isPersonal;
	/** the status of the commitment */
	public enum Status {
		NEW(0), IN_PROGRESS(1), COMPLETED(2);
		public int id;

		Status(int id) {
			this.id = id;
		}
		
		public static Status getStatusValue(int id){
			Status result;
			result = Status.NEW;
			switch(id){
			case 0:
				result = Status.NEW;
				break;
			case 1:
				result = Status.IN_PROGRESS;
				break;
			case 2:
				result = Status.COMPLETED;
				break;
			}
			
			return result;
		}
		
		public static String convertToString(int id){
			String result;
			result = "";
			switch(id){
			case 0:
				result = "New";
				break;
			case 1:
				result = "In Progress";
				break;
			case 2:
				result = "Completed";
				break;
			}
			
			return result;
		}
		
	}; 
	private Status status;

	//Getters
	 /**Getter for the ID	
	 * @return the id */
	public int getId() 				{return id;}
	 /**Getter for the Name	
	 * @return the name */
	public String getName() 		{return name;}
	 /**Getter for the DueDate	
	 * @return the due date */
	public GregorianCalendar getDueDate() 		{
		GregorianCalendar tmp = new GregorianCalendar();
		tmp.setTime(dueDate);
		return tmp;
	}
	 /**Getter for the Description	
	 * @return the description */
	public String getDescription() 	{return description;}
	 /**Getter for the categoryId	
	 * @return the category id */
	public int getCategoryId()	{return categoryId;}
	 /**Getter for the status	
	 * @return the status */
	public Status getStatus()	 	{return status;}
	/** Getter for personal or team commitment
	 * @return is personal*/
	public boolean getIsPersonal()	{return isPersonal;}
	
	//Setters
	/**Setter for the id
	 * @param id the id to set*/
	public void setId		   (int id)				{this.id = id;}
	/**Setter for the name
	 * @param name the name to set*/
	public void setName		   (String name) 		{this.name = name;}
	/**Setter for the dueDate
	 * @param dueDate the due date to set*/
	public void setDueDate	   (GregorianCalendar dueDate) 		{this.dueDate = dueDate.getTime();}
	/**Setter for the Description
	 * @param description the Description to set*/
	public void setDescription (String description) {this.description = description;}
	/**Setter for the categoryId
	 * @param categoryId the id to category id*/
	public void setCategoryId  (int categoryId) 	{this.categoryId = categoryId;}
	/**Setter for the status
	 * @param status to status*/
	public void setStatus  (Status status) 	{this.status = status;}
	/**Setter for personal or team commitment
	 * @param  boolean isPersonal?*/
	public void setIsPersonal (boolean isPersonal)	{this.isPersonal = isPersonal;}
	
	/** Construct a commitment with required properties provided and others set
	 * to default
	 * @param id
	 *            The ID number of the commitment
	 * @param name
	 *            The name of the commitment*/
	public Commitment() {
		super();
		name = description = "";
		dueDate = new Date();
		status = Status.NEW;
		isPersonal = false;
	}
	
	/** Construct a commitment with required properties provided and others set
	 * to default
	 * @param id
	 *            The ID number of the commitment
	 * @param name
	 *            The name number of the commitment
	 * @param dueDate
	 *            The due date number of the commitment
	 * @param commitmentId
	 *            The commitment ID number of the commitment*/

	public Commitment(String name, GregorianCalendar dueDate,
					String description, int categoryId, boolean isPersonal) {
		this();
		this.name = name;
		this.dueDate = dueDate.getTime();
		this.description = description;
		this.categoryId = categoryId;
		this.isPersonal = isPersonal;
	}

	/**
	 * Returns an instance of Commitment constructed using the given
	 * Commitment encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Commitment to deserialize
	
	 * @return the Commitment contained in the given JSON */
	public static Commitment fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Commitment.class);
	}

	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {

	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {

	}

	/**
	 * Method toJSON.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this commitment object.
	 * 
	 * @return a Json encoded String representation of this commitment
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Commitment.class);
	}

	/**
	 * Returns an array of Commitments parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Commitment
	
	 * @return an array of Commitment deserialized from the given JSON string */
	public static Commitment[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Commitment[].class);
	}

	/**
	 * Method identify.
	 * @param o Object
	
	
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
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
	 * Copies all of the values from the given commitment to this commitment.
	 * 
	 * @param toCopyFrom
	 *            the commitment to copy from.
	 */
	public void copyFrom(Commitment toCopyFrom) {
		this.id = 			toCopyFrom.getId();
		this.name = 		toCopyFrom.getName();
		this.dueDate = 		toCopyFrom.getDueDate().getTime();
		this.description =  toCopyFrom.getDescription();
		this.categoryId = 	toCopyFrom.getCategoryId();
		this.status =       toCopyFrom.getStatus();
		this.isPersonal =   toCopyFrom.getIsPersonal();
	}
	
	public int compareTo(Commitment person) {
		  if(this.name != null && person.name != null){
		   return this.name.compareToIgnoreCase(person.name);
		  }
		  return 0;
		 }

		
	
}
