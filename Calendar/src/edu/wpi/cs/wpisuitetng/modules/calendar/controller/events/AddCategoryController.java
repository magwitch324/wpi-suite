/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.controller.events;


import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.AddCategoryRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.category.Category;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Update button by
 * adding the contents of the category text fields to the model as a new
 * category.
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class AddCategoryController{
	
	private static AddCategoryController instance;
	private AddCategoryRequestObserver observer;
	
	/**
	 * Construct an AddEventController for the given model, view pair
	
	
	 */
	private AddCategoryController() {
		observer = new AddCategoryRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the AddEventController or creates one if it does not
	 * exist. */
	public static AddCategoryController getInstance()
	{
		if(instance == null)
		{
			instance = new AddCategoryController();
		}
		
		return instance;
	}

	/**
	 * This method adds a category to the server.
	 * @param newCategory is the category to be added to the server.
	 */
	public void addCategory(Category newCategory) 
	{
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.PUT); // PUT == create
		request.setBody(newCategory.toJSON()); // put the new category in the body of the request
		request.addObserver(observer); // add an observer to process the response
		request.send(); 
	}
}
