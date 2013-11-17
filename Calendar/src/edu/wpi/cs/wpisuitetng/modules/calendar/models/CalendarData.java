package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;

public class CalendarData extends AbstractModel {

	/** the ID of the CalendarData */
	private int id;

	/** the name of the CalendarData */
	private String name;
	private CategoryList categories;
	private CommitmentList commitments;

	/**
	 * Constructs a CalendarData with default characteristics
	 */
	public CalendarData() {
		super();
		name = "";
		this.categories = new CategoryList();
		this.commitments = new CommitmentList(); 
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
	public CalendarData(int id, String name) {
		this();
		this.id = id;
		this.name = name;
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
	 * Returns the list of categories in this calendar
	 * 
	 * @return the list of categories
	 */
	public CategoryList getCategories(){
		return categories;
	}
	
	/**
	 * Adds a commitment to the calendar
	 * 
	 * @param commitment
	 */
	public void addCommitment(Commitment newCommitment){
		//TODO add correct call
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
		return this.getName();
	}



	
	
}
