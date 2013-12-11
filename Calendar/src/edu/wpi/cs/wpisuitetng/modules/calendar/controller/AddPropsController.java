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


import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Update button by
 * adding the contents of the CalendarProps text fields to the model as a new
 * category.
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class AddPropsController{
	
	private static AddPropsController instance;
	private final AddPropsRequestObserver observer;
	
	/**
	 * Construct an AddCalendarPropsController for the given model, view pair
	
	
	 */
	private AddPropsController() {
		observer = new AddPropsRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the AddCalendarPropsController or creates one if it does not
	 * exist. */
	public static AddPropsController getInstance()
	{
		if(instance == null)
		{
			instance = new AddPropsController();
		}
		
		return instance;
	}

	/**
	 * This method adds a CalendarProps to the server.
	 * @param newCalData is the CalendarProps to be added to the server.
	 */
	public void addCalendarProps(CalendarProps newCalProps) 
	{
		final Request request = Network.getInstance().makeRequest("calendar/calendarprops", HttpMethod.PUT); // PUT == create
		request.setBody(newCalProps.toJSON()); // put the new calData in the body of the request
		request.addObserver(observer); // add an observer to process the response
		request.send(); 
	}
}
