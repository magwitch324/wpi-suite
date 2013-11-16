/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CategoryModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the categories
 * from the server.
 *
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class GetCategoryController implements ActionListener {

	private GetCategoryRequestObserver observer;
	private static GetCategoryController instance;

	/**
	 * Constructs the controller given a CategoryModel
	 */
	private GetCategoryController() {
		
		observer = new GetCategoryRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the GetCategoryController or creates one if it does not
	 * exist. */
	public static GetCategoryController getInstance()
	{
		if(instance == null)
		{
			instance = new GetCategoryController();
		}
		
		return instance;
	}

	/**
	 * Sends an HTTP request to store a category when the
	 * update button is pressed
	 * @param e ActionEvent
	
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this category
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Sends an HTTP request to retrieve all categories
	 */
	public void retrieveCategories() {
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
		System.out.println("Retrieve Req Sent");
	}

	/**
	 * Add the given categories to the local model (they were received from the core).
	 * This method is called by the GetCategoryRequestObserver
	 * 
	 * @param categories array of categories received from the server
	 */
	public void receivedCategories(Category[] categories) {
		// Empty the local model to eliminate duplications
		CategoryModel.getInstance().emptyModel();
		System.out.println("Received category");
		// Make sure the response was not null
		if (categories != null) {
			
			// add the categories to the local model
			CategoryModel.getInstance().addCategorys(categories);
		}
	}
}
