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
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;


import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddCalendarDataController;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

public class CalendarDataModel extends AbstractListModel {

	/**
	 * The list in which all the CalendarData for a single project are contained
	 */
	private List<CalendarData> calendarDatas;
	private int nextID; // the next available ID number for the calendarData that are added.
	
	//the static object to allow the category model to be 
	private static CalendarDataModel instance; 

	/**
	 * Constructs an empty list of CalendarData for the project
	 */
	private CalendarDataModel (){
		calendarDatas = new ArrayList<CalendarData>();
	}
	
	/**
	
	 * @return the instance of the calendarData model singleton. */
	public static CalendarDataModel getInstance()
	{
		if(instance == null)
		{
			instance = new CalendarDataModel();
		}
		
		return instance;
	}
	
	/**
	 * Adds a single calendarData to the CalendarDatas of the project
	 * 
	 * @param newCalData The CalendarData to be added to the list of CalendarDatas in the project
	 */
	public void addCalendarData(CalendarData newCalData){
		// add the CalendarData
		calendarDatas.add(newCalData);
		try 
		{
			AddCalendarDataController.getInstance().addCalendarData(newCalData);
		}
		catch(Exception e)
		{
			
		}
	}
	/**
	 * Returns the CalendarData with the given ID
	 * 
	 * @param id The ID number of the CalendarData to be returned
	
	 * @return the CalendarData for the id or null if the CalendarData is not found */
	public CalendarData getCalendarData(String id)
	{
		CalendarData temp = null;
		// iterate through list of CalendarDatas until id is found
		for (int i=0; i < this.calendarDatas.size(); i++){
			temp = calendarDatas.get(i);
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
	 * Removes the CalendarData with the given ID
	 * 
	 * @param removeId The ID number of the CalendarData to be removed from the list of CalendarDatas in the project
	 */
	public void removeCalendarData(String removeId){
		// iterate through list of CalendarDatas until id of project is found
		for (int i=0; i < this.calendarDatas.size(); i++){
			if (calendarDatas.get(i).getId().equals(removeId)){
				// remove the id
				calendarDatas.remove(i);
				break;
			}
		}

	}

	/**
	 * Provides the number of elements in the list of CalendarData for the project. 
	 * 
	 * @return the number of CalendarDatas in the project * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return calendarDatas.size();
	}
	


	/**
	 * This function takes an index and finds the CalendarData in the list of CalendarDatas
	 * for the project. Used internally by the JList in NewCategoryModel.
	 * 
	 * @param index The index of the CalendarData to be returned
	
	
	
	 * @return the CalendarData associated with the provided index * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int)
	 */
	public CalendarData getElementAt(int index) {
		return calendarDatas.get(calendarDatas.size() - 1 - index);
	}

	/**
	 * Removes all CalendarData from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each category
	 * from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<CalendarData> iterator = calendarDatas.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		
	}
	
	/**
	 * Adds the given array of CalendarData to the list
	 * 
	 * @param categories the array of categories to add
	 */
	public void addCalendarData(CalendarData[] calData) {
		for (int i = 0; i < calData.length; i++) {
			this.calendarDatas.add(calData[i]);

		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
		
	}

	/**
	 * Returns the list of the CalendarDatas
	
	 * @return the categories held within the CalendarDatamodle. */
	public List<CalendarData> getCalendarData() {
		return calendarDatas;
	}

	
	
	
	

	


	
	
}
