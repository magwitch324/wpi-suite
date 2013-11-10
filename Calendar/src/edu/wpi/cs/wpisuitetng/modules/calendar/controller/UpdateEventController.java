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


import edu.wpi.cs.wpisuitetng.modules.calendar.models.Event;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Update button by
 * adding the contents of the event text fields to the model as a new
 * event.
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class UpdateEventController{
	
	private static UpdateEventController instance;
	private UpdateEventRequestObserver observer;
	
	/**
	 * Construct an UpdateEventController for the given model, view pair
	
	
	 */
	private UpdateEventController() {
		observer = new UpdateEventRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the UpdateEventController or creates one if it does not
	 * exist. */
	public static UpdateEventController getInstance()
	{
		if(instance == null)
		{
			instance = new UpdateEventController();
		}
		
		return instance;
	}

	/**
	 * This method updates a event to the server.
	 * @param newEvent is the event to be updated to the server.
	 */
	public void updateEvent(Event newEvent) 
	{
		Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.POST); // POST == update
		request.setBody(newEvent.toJSON()); // put the new event in the body of the request
		request.addObserver(observer); // add an observer to process the response
		request.send(); 
	}
}
