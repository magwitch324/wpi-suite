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

import java.util.Comparator;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Filter is used for filtering categories.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class Filter implements Comparator<Filter>{
	/** the ID of the filter */
	private int id;

	/** the name of the filter */
	private String name;
	
	/** the list of categories not included in this filter*/
	private List<Integer> inactiveCategories;
	
	/** the list of categories included in this filter*/
	private List<Integer> activeCategories;
	
	/**
	 * Constructs a filter with default characteristics
	 */
	public Filter() {
		name = "";
	}

	/**
	 * Construct a Filter with required properties provided and others set
	 * to default
	 * 
	
	 * @param name
	 *            The name of the filter
	 * @param catList CategoryList
	 */
	// need to phase out supplying the ID
	public Filter(String name, List<Integer> inactiveCatList, List<Integer> activeCatList) {
		this();
		this.name = name;
		inactiveCategories = inactiveCatList;
		activeCategories = activeCatList;
	}
	
	
	/**
	 * /**Getter for the id
	 * 
	
	 * @return the id */
	public int getID() {
		return id;
	}

	/**
	 * Setter for the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setID(int id) {
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
	 * the name to set
	 */
	public void setName(String n) {

		if (name.length() > 100) {
			name = n.substring(0, 100);
		} else { 
			name = n;
		}
		
	}
	
	
	/**
	 * getter for the inactiveCategories
	 * 
	
	 * @return the inactiveCategories */
	public List<Integer> getInactiveCategories() {
		return inactiveCategories;
	}

	
	
	/**
	 * Setter for the inactiveCategories
	 * 
	 * @param inactiveCatList
	 *            the inactiveCatList to set
	 */
	public void setInactiveCategories(List<Integer> inactiveCatList) {
		inactiveCategories = inactiveCatList;
	}
	
	
	
	/**
	 * getter for the activeCategories
	 * 
	
	 * @return the activeCategories */
	public List<Integer> getActiveCategories() {
		return activeCategories;
	}

	
	
	/**
	 * Setter for the activeCategories
	 * 
	 * @param activeCatList
	 *            the activeCatList to set
	 */
	public void setActiveCategories(List<Integer> activeCatList) {
		activeCategories = activeCatList;
	}
	
	
	
	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	public void delete() {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * Method toJSON.
	
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() */
	
	/**This returns a Json encoded String representation of this filter object.
	 * 
	 * @return a Json encoded String representation of this filter
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Filter.class);
	}

	/**
	 * Returns an array of Filter parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Filter
	
	 * @return an array of Filter deserialized from the given JSON string */
	public static Filter[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Filter[].class);
	}
	
	
	
	/**
	 * Method identify.
	 * @param o Object
	
	
	
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) */
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method toString.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() 
	 * * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Method compare.
	 * @param f1 Filter
	 * @param f2 Filter
	
	 * @return int */
	public int compare(Filter f1, Filter f2) {
		return f1.getName().compareToIgnoreCase(f2.getName());
	}

}

