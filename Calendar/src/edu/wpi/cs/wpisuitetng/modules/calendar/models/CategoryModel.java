package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;


import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddCategoryController;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CategoryModel;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

public class CategoryModel extends AbstractListModel {

	/**
	 * The list in which all the categories for a single project are contained
	 */
	private List<Category> categories;
	private int nextID; // the next available ID number for the categories that are added.
	
	//the static object to allow the category model to be 
	private static CategoryModel instance; 

	/**
	 * Constructs an empty list of categories for the project
	 */
	private CategoryModel (){
		categories = new ArrayList<Category>();
		nextID = 0;
	}
	
	/**
	
	 * @return the instance of the category model singleton. */
	public static CategoryModel getInstance()
	{
		if(instance == null)
		{
			instance = new CategoryModel();
		}
		
		return instance;
	}
	
	/**
	 * Adds a single category to the categories of the project
	 * 
	 * @param newReq The category to be added to the list of categories in the project
	 */
	public void addCategory(Category newReq){
		// add the category
		categories.add(newReq);
		try 
		{
			AddCategoryController.getInstance().addCategory(newReq);
		}
		catch(Exception e)
		{
			
		}
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
		for (int i=0; i < this.categories.size(); i++){
			temp = categories.get(i);
			if (temp.getId() == id){
				break;
			}
		}
		return temp;
	}
	/**
	 * Removes the category with the given ID
	 * 
	 * @param removeId The ID number of the category to be removed from the list of categories in the project
	 */
	public void removeCategory(int removeId){
		// iterate through list of categories until id of project is found
		for (int i=0; i < this.categories.size(); i++){
			if (categories.get(i).getId() == removeId){
				// remove the id
				categories.remove(i);
				break;
			}
		}
		try {
		}
		catch(Exception e) {}
	}

	/**
	 * Provides the number of elements in the list of categories for the project. 
	 * 
	 * @return the number of categories in the project * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return categories.size();
	}
	
	/**
	 * 
	 * Provides the next ID number that should be used for a new category that is created.
	 * 
	
	 * @return the next open id number */
	public int getNextID()
	{
		
		return this.nextID++;
	}

	/**
	 * This function takes an index and finds the category in the list of categories
	 * for the project. Used internally by the JList in NewCategoryModel.
	 * 
	 * @param index The index of the category to be returned
	
	
	
	 * @return the category associated with the provided index * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int)
	 */
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
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Category> iterator = categories.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		
	}
	
	/**
	 * Adds the given array of categories to the list
	 * 
	 * @param categories the array of categories to add
	 */
	public void addCategorys(Category[] categories) {
		for (int i = 0; i < categories.length; i++) {
			this.categories.add(categories[i]);
			if(categories[i].getId() >= nextID) nextID = categories[i].getId() + 1;
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
		
	}

	/**
	 * Returns the list of the categories
	
	 * @return the categories held within the categorymodel. */
	public List<Category> getCategorys() {
		return categories;
	}

	
	
	
	

	


	
	
}
