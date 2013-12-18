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


import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProperties;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Update button by
 * updating the contents of the CalendarProps text fields to the model of an existing
 * CalendarProps.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class UpdatePropsController{
	
	private static UpdatePropsController instance;
	private final UpdatePropsRequestObserver observer;
	
	/**
	 * Construct an UpdateCalendarPropsController for the given model, view pair
	
	
	 */
	private UpdatePropsController() {
		observer = new UpdatePropsRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the UpdateCalendarPropsController or creates one if it does not
	 * exist. */
	public static UpdatePropsController getInstance()
	{
		if(instance == null)
		{
			instance = new UpdatePropsController();
		}
		
		return instance;
	}

	/**
	 * This method updates a CalendarProps to the server.
	 * @param newCalData is the CalendarProps to be updated to the server.
	 */
	public void updateCalendarProps(CalendarProperties newCalData) 
	{
		//refreshes calendar GUI
		System.out.println("Updating calprops");
		final Request request = Network.getInstance().makeRequest(
				"calendar/calendarprops", HttpMethod.POST);
		request.setBody(newCalData.toJSON()); 
		// put the updated CalendarProps in the body of the request
		request.addObserver(observer); 
		// add an observer to process the response
		request.send(); 
	}
}
