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
public class CategoryList {

	/**
	 * The list in which all the categories for a single project are contained
	 */
	private final List<Category> categories;
	
	/** the the id to be used for the next category */
	private int nextID;

	/**
	 * Constructs an empty list of categories for the project
	 */
	public CategoryList (){
		categories = new ArrayList<Category>();
		nextID = 1; // ID of zero is for no category
	}



	/**
	 * Adds a single category to the categories of the project
	 * 
	 * @param newCat The category to be added to the list of categories in the project
	 */
	public void add(Category newCat){
		// add the category
		newCat.setID(nextID);
		nextID++;
		categories.add(newCat);
		sortByAlphabet();

	}
	/**
	 * Returns the Category with the given ID
	 * 
	 * @param id The ID number of the category to be returned

	 * @return the category for the id or null if the category is not found */
	public Category getCategory(int id)
	{
		Category temp = null;
		// iterate through list of categories until id is found
		for (int i=0; i < categories.size(); i++){
			temp = categories.get(i);
			if (temp.getID() == id){
				break;
			}
		}
		return temp;
	}
	/**
	 * Removes the category with the given ID
	 * 
	 * @param removeId The ID number of the category to be removed from the list of categories
	 */
	public void remove(int removeId){
		// iterate through list of categories until id of project is found
		for (int i=0; i < categories.size(); i++){
			if (categories.get(i).getID() == removeId){
				// remove the id
				categories.remove(i);
				break;
			}
		}
	}

	/**
	 * Provides the number of elements in the list of categories for the project. 
	 * 
	 * @return the number of categories in the project * @see javax.swing.ListModel#getSize() 
	 * * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return categories.size();
	}


	/**
	 * This function takes an index and finds the category in the list of categories
	 * for the project. Used internally by the JList in NewCategoryList.
	 * 
	 * @param index The index of the category to be returned



	el#getElementAt(int) 
	 * * @see javax.swing.ListModel#getElementAt(int) 
	 * * @see javax.swing.ListModel#getElementAt(int) 
	 * * @see javax.swing.ListModel#getElementAt(int) 
	 * * @see javax.swing.ListModel#getElementAt(int) */
	public Category getElementAt(int index) {
		return categories.get(categories.size() - 1 - index);
	}

	/**
	 * Removes all categories from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each category
	 * from the model.
	 */
	public void removeAll() {
		categories.removeAll(getCategories());
	}

	/**
	 * Adds the given array of categories to the list
	 * 
	
	 * @param array Category[]
	 */
	public void addCategories(Category[] array) {
		Collections.addAll(categories, array);
		sortByAlphabet();
	}

	/**
	 * Returns the list of the categories

	 * @return the categories held within the CategoryList. */
	public List<Category> getCategories() {
		return categories;
	}
	
	/**
	 * Update the category list
	 * 
	
	 * @param newCategory Category
	 */
	public void update (Category newCategory) {
		categories.remove(getCategory(newCategory.getID()));
		categories.add(newCategory);
		sortByAlphabet();
	}

	/**
	 * Sort the elements in the categories according to the alphabet
	 */
	public void sortByAlphabet() {
		Collections.sort(categories, new Category());
	}
}
