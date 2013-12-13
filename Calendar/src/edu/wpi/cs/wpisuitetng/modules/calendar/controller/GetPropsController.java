/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropsModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the categories
 * from the server.
 *
 */
public class GetPropsController implements ActionListener {

	private final GetPropsRequestObserver observer;
	private static GetPropsController instance;

	/**
	 * Constructs the controller given a CalendarPropsModel
	 */
	private GetPropsController() {
		
		observer = new GetPropsRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the GetCalendarPropsController or creates one if it does not
	 * exist. */
	public static GetPropsController getInstance()
	{
		if(instance == null)
		{
			instance = new GetPropsController();
		}
		
		return instance;
	}

	/**
	 * Sends an HTTP request to get a CalendarProps when the
	 * update button is pressed
	 * @param e ActionEvent
	
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to get this CalendarProps
		final Request request = Network.getInstance().makeRequest(
				"calendar/CalendarProps", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Sends an HTTP request to retrieve all CalendarPropss
	 */
	public void retrieveCalendarProps() {
		final Request request = Network.getInstance().makeRequest(
				"calendar/calendarprops", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}

	/**
	 * Add the given CalendarPropss to the local model (they were received from the core).
	 * This method is called by the GetCalendarPropsRequestObserver
	 * 
	 * @param categories array of categories received from the server
	 */
	public void receivedCalendarProps(CalendarProps[] calData) {
		// Empty the local model to eliminate duplications
		CalendarPropsModel.getInstance().emptyModel();
		System.out.println("Received CalProps");
		// Make sure the response was not null
		if (calData != null) {
			
			// add the categories to the local model
			CalendarPropsModel.getInstance().addCalendarProps(calData);
			//refreshes calendar GUI
			GUIEventController.getInstance().applyCalProps();
		}
	}
}
