/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddPropsController;



/**
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
 public class CalendarPropsModel extends AbstractListModel {

	/**
	 * The list in which all the CalendarProps for a single project are contained
	 */
	private final List<CalendarProps> CalendarPropss;
	private int nextID; // the next available ID number for the CalendarProps that are added.
	
	//the static object to allow the category model to be 
	private static CalendarPropsModel instance; 

	/**
	 * Constructs an empty list of CalendarProps for the project
	 */
	private CalendarPropsModel (){
		CalendarPropss = new ArrayList<CalendarProps>();
	}
	
	/**
	
	 * @return the instance of the CalendarProps model singleton. */
	public static CalendarPropsModel getInstance()
	{
		if(instance == null)
		{
			instance = new CalendarPropsModel();
		}
		
		return instance;
	}
	
	/**
	 * Adds a single CalendarProps to the CalendarPropss of the project
	 * 
	 * @param newCalData The CalendarProps to be added to the list of CalendarPropss in the project
	 */
	public void addCalendarProps(CalendarProps newCalData){
		// add the CalendarProps
		CalendarPropss.add(newCalData);
		try 
		{
			AddPropsController.getInstance().addCalendarProps(newCalData);
		}
		catch(Exception e)
		{
			
		}
	}
	/**
	 * Returns the CalendarProps with the given ID
	 * 
	 * @param id The ID number of the CalendarProps to be returned
	
	 * @return the CalendarProps for the id or null if the CalendarProps is not found */
	public CalendarProps getCalendarProps(String id)
	{
		CalendarProps temp = null;
		// iterate through list of CalendarPropss until id is found
		for (int i=0; i < CalendarPropss.size(); i++){
			temp = CalendarPropss.get(i);
			if (temp.getId().equals(id)){
				break;
			}
			else{
				temp = null;
			}
		}
		return temp;
	}
	/**
	 * Removes the CalendarProps with the given ID
	 * 
	 * @param removeId The ID number of the CalendarProps 
	 * to be removed from the list of CalendarPropss
	 */
	public void removeCalendarProps(String removeId){
		// iterate through list of CalendarPropss until id of project is found
		for (int i=0; i < CalendarPropss.size(); i++){
			if (CalendarPropss.get(i).getId().equals(removeId)){
				// remove the id
				CalendarPropss.remove(i);
				break;
			}
		}

	}

	/**
	 * Provides the number of elements in the list of CalendarProps for the project. 
	 * 
	 * @return the number of CalendarPropss in the project 
	 * * @see javax.swing.ListModel#getSize() 
	 * * @see javax.swing.ListModel#getSize() 
	 * * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return CalendarPropss.size();
	}
	


	/**
	 * This function takes an index and finds the CalendarProps in the list of CalendarPropss
	 * for the project. Used internally by the JList in NewCategoryModel.
	 * 
	 * @param index The index of the CalendarProps to be returned
	
	
	
	 * @return the CalendarProps associated with the provided index 
	 * * @see javax.swing.ListModel#getElementAt(int) 
	 * * @see javax.swing.ListModel#getElementAt(int) 
	 * * @see javax.swing.ListModel#getElementAt(int)
	 */
	public CalendarProps getElementAt(int index) {
		return CalendarPropss.get(CalendarPropss.size() - 1 - index);
	}

	/**
	 * Removes all CalendarProps from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each category
	 * from the model.
	 */
	public void emptyModel() {
		final int oldSize = getSize();
		final Iterator<CalendarProps> iterator = CalendarPropss.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		
	}
	
	/**
	 * Adds the given array of CalendarProps to the list
	 * 
	
	 * @param calData CalendarProps[]
	 */
	public void addCalendarProps(CalendarProps[] calData) {
		for (int i = 0; i < calData.length; i++) {
			CalendarPropss.add(calData[i]);

		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
		
	}

	/**
	 * Returns the list of the CalendarPropss
	
	 * @return the categories held within the CalendarPropsmodle. */
	public List<CalendarProps> getCalendarProps() {
		return CalendarPropss;
	}

	
	
	
	

	


	
	
}
