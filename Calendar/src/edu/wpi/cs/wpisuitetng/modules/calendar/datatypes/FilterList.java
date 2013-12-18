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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List of category is design for the users to
 * be able to create their own category.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class FilterList {
	
	/**
	 * The list in which all the filters are contained
	 */
	private final List<Filter> filters;

	/**
	 * Constructs an empty list of filters
	 */
	public FilterList (){
		filters = new ArrayList<Filter>();
	}



	/**
	 * Adds a single filter to the filters
	 * 
	 * @param newFilter The filter to be added to the list of filters
	 */
	public void add(Filter newFilter){
		// add the filter
		filters.add(newFilter);
		sortByAlphabet();

	}
	/**
	 * Returns the Filter with the given ID
	 * 
	 * @param id The ID number of the filter to be returned

	 * @return the filter for the id or null if the filter is not found */
	public Filter getFilter(int id)
	{
		Filter temp = null;
		// iterate through list of filters until id is found
		for (int i=0; i < filters.size(); i++){
			temp = filters.get(i);
			if (temp.getID() == id){
				break;
			}
		}
		return temp;
	}
	/**
	 * Removes the filter with the given ID
	 * 
	 * @param removeId The ID number of the filter to be removed from the list of filters
	 */
	public void remove(int removeId){
		// iterate through list of filters until id of filter is found
		for (int i=0; i < filters.size(); i++){
			if (filters.get(i).getID() == removeId){
				// remove the id
				filters.remove(i);
				break;
			}
		}
	}

	/**
	 * Provides the number of elements in the list of filters. 
	 * 
	 * @return the number of filters in the project * @see javax.swing.ListModel#getSize() 
	 * * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return filters.size();
	}


	/**
	 * This function takes an index and finds the filter in the list of filters
	 * Used internally by the JList in NewFilterList.
	 * 
	 * @param index The index of the filter to be returned



	el#getElementAt(int) 
	 *istModel#getElementAt(int)
	 * istModel#getElementAt(int) 
	 *  * @see javax.swing.ListModel#getElementAt(int)
        * @see javax.swing.ListModel#getElementAt(int) 
	 *  * @see javax.swing.ListModel#getElementAt(int) 
        * @see javax.swing.ListModel#getElementAt(int) */
	public Filter getElementAt(int index) {
		return filters.get(filters.size() - 1 - index);
	}

	/**
	 * Removes all filters from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each filter
	 * from the model.
	 */
	public void removeAll() {
		filters.removeAll(getFilters());
	}

	/**
	 * Adds the given array of filters to the list
	 * 
	
	 * @param array Category[]
	 */
	public void addFilters(Filter[] array) {
		Collections.addAll(filters, array);
		sortByAlphabet();
	}

	/**
	 * Returns the list of the filters

	 * @return the filters held within the FilterList. */
	public List<Filter> getFilters() {
		return filters;
	}
	
	/**
	 * Update the filter list
	 * 
	
	 * @param newFilter Filter
	 */
	public void update (Filter newFilter) {
		filters.remove(getFilter(newFilter.getID()));
		filters.add(newFilter);
		sortByAlphabet();
	}

	/**
	 * Sort the elements in the filters according to the alphabet
	 */
	public void sortByAlphabet() {
		Collections.sort(filters, new Filter());
	}

}
