package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.EventModel;

public class Category extends AbstractModel {

	/** the ID of the category */
	private int id;

	/** the name of the category */
	private String name;

	/**
	 * Constructs a category with default characteristics
	 */
	public Category() {
		super();
		name = "";
	}

	/**
	 * Construct a Category with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the category
	 * @param name
	 *            The name of the category
	 */
	// need to phase out supplying the ID
	public Category(int id, String name) {
		this();
		this.id = id;
		this.name = name;
	}


	/**
	 * Returns an instance of Category constructed using the given
	 * Event encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Category to deserialize
	
	 * @return the Category contained in the given JSON */
	public static Category fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Category.class);
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

		if (name.length() > 100) {
			this.name = n.substring(0, 100);
		} else { 
			this.name = n;
		}
		
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
	/**This returns a Json encoded String representation of this category object.
	 * 
	 * @return a Json encoded String representation of this category
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Category.class);
	}

	/**
	 * Returns an array of Category parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Category
	
	 * @return an array of Category deserialized from the given JSON string */
	public static Category[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Category[].class);
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



	
	
}
