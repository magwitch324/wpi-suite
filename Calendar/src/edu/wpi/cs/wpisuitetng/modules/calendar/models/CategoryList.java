package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.events.AddCategoryController;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CategoryList;

public class CategoryList {

	/**
	 * The list in which all the categories for a single project are contained
	 */
	private List<Category> categories;

	/**
	 * Constructs an empty list of categories for the project
	 */
	public CategoryList (){
		categories = new ArrayList<Category>();
	}



	/**
	 * Adds a single category to the categories of the project
	 * 
	 * @param newCat The category to be added to the list of categories in the project
	 */
	public void addCategory(Category newCat){
		// add the category
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
	 * This function takes an index and finds the category in the list of categories
	 * for the project. Used internally by the JList in NewCategoryList.
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
	public void removeAll() {
		categories.removeAll(getCategorys());
	}

	/**
	 * Adds the given array of categories to the list
	 * 
	 * @param categories the array of categories to add
	 */
	public void addCategorys(Category[] array) {
		Collections.addAll(categories, array);
		sortByAlphabet();
	}

	/**
	 * Returns the list of the categories

	 * @return the categories held within the CategoryList. */
	public List<Category> getCategorys() {
		return categories;
	}
	
	/**
	 * Update the category list
	 * 
	 * @param the category to be update
	 */
	public void update (Category newCategory) {
		categories.remove(getCategory(newCategory.getId()));
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
