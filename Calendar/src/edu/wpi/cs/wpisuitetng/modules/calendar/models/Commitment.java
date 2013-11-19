package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Commitment extends AbstractModel {
	// Commitment characteristics.
	/** the ID of the category */
	private int id;
	/** the Name of the category */
	private String name;
	/** the DueDate of the category */
	private Date dueDate;
	/** the Description of the category */
	private String description;
	/** the CategoryID of the category */
	private int categoryId;

	//Getters
	 /**Getter for the ID	
	 * @return the id */
	public int getId() 				{return id;}
	 /**Getter for the Name	
	 * @return the name */
	public String getName() 		{return name;}
	 /**Getter for the DueDate	
	 * @return the due date */
	public Date getDueDate() 		{return dueDate;}
	 /**Getter for the Description	
	 * @return the description */
	public String getDescription() 	{return description;}
	 /**Getter for the categoryId	
	 * @return the category id */
	public int getCategoryId()	 	{return categoryId;}
	
	//Setters
	/**Setter for the id
	 * @param id the id to set*/
	public void setId		   (int id)				{this.id = id;}
	/**Setter for the name
	 * @param id the name to set*/
	public void setName		   (String name) 		{this.name = name;}
	/**Setter for the dueDate
	 * @param id the due date to set*/
	public void setDueDate	   (Date dueDate) 		{this.dueDate = dueDate;}
	/**Setter for the Description
	 * @param id the Description to set*/
	public void setDescription (String description) {this.description = description;}
	/**Setter for the categoryId
	 * @param id the id to category id*/
	public void setCategoryId  (int categoryId) 	{this.categoryId = categoryId;}

	/** Construct a Category with required properties provided and others set
	 * to default
	 * @param id
	 *            The ID number of the category
	 * @param name
	 *            The name of the category*/
	public Commitment() {
		super();
		name = description = "";
		dueDate = new Date();
	}
	
	/** Construct a Category with required properties provided and others set
	 * to default
	 * @param id
	 *            The ID number of the category
	 * @param name
	 *            The name number of the category
	 * @param dueDate
	 *            The due date number of the category
	 * @param categoryId
	 *            The category ID number of the category*/

	public Commitment(String name, Date dueDate,
					String description, int categoryId) {
		this();
		this.name = name;
		this.dueDate = dueDate;
		this.description = description;
		this.categoryId = categoryId;
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
	 * Copies all of the values from the given commitment to this commitment.
	 * 
	 * @param toCopyFrom
	 *            the commitment to copy from.
	 */
	public void copyFrom(Commitment toCopyFrom) {
		this.id = 			toCopyFrom.getId();
		this.name = 		toCopyFrom.getName();
		this.dueDate = 		toCopyFrom.getDueDate();
		this.description =  toCopyFrom.getDescription();
		this.categoryId = 	toCopyFrom.getCategoryId();

	}
	
}
