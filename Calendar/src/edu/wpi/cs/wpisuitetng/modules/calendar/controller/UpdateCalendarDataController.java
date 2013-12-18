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


import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Update button by
 * updating the contents of the calendarData text fields to the model of an existing
 * calendarData.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
*/

public class UpdateCalendarDataController{
	
	private static UpdateCalendarDataController instance;
	private final UpdateCalendarDataRequestObserver observer;
	
	/**
	 * Construct an UpdateCalendarDataController for the given model, view pair
	
	
	 */
	private UpdateCalendarDataController() {
		observer = new UpdateCalendarDataRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the UpdateCalendarDataController or creates one if it does not
	 * exist. */
	public static UpdateCalendarDataController getInstance()
	{
		if(instance == null)
		{
			instance = new UpdateCalendarDataController();
		}
		
		return instance;
	}

	/**
	 * This method updates a CalendarData to the server.
	 * @param newCalData is the CalendarData to be updated to the server.
	 */
	public void updateCalendarData(CalendarData newCalData) 
	{
		//refreshes calendar GUI
		GUIEventController.getInstance().updateCalData();
		System.out.println("Updating caldata");
		final Request request = Network.getInstance().makeRequest(
				"calendar/calendardata", HttpMethod.POST);
		request.setBody(newCalData.toJSON()); 
		// put the updated CalendarData in the body of the request
		request.addObserver(observer); 
		// add an observer to process the response
		request.send(); 
	}
	
	/**
	 * This method updates a CalendarData to the server but does not update the gui
	 * @param newCalData is the CalendarData to be updated to the server.
	 */
	public void updateCalendarDataNoGUIUpdate(CalendarData newCalData) 
	{
		//refreshes calendar GUI
		//GUIEventController.getInstance().updateCalData();
		System.out.println("Updating caldata no gui update");
		final Request request = Network.getInstance().makeRequest(
				"calendar/calendardata", HttpMethod.POST);
		request.setBody(newCalData.toJSON()); 
		// put the updated CalendarData in the body of the request
		request.addObserver(observer); 
		// add an observer to process the response
		request.send(); 
	}
}
