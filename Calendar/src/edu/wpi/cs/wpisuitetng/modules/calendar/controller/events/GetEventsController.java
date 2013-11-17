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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.event.EventModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the events
 * from the server.
 *
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class GetEventsController implements ActionListener {

	private GetEventsRequestObserver observer;
	private static GetEventsController instance;

	/**
	 * Constructs the controller given a EventModel
	 */
	private GetEventsController() {
		
		observer = new GetEventsRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the GetEventController or creates one if it does not
	 * exist. */
	public static GetEventsController getInstance()
	{
		if(instance == null)
		{
			instance = new GetEventsController();
		}
		
		return instance;
	}

	/**
	 * Sends an HTTP request to store a event when the
	 * update button is pressed
	 * @param e ActionEvent
	
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent) */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to save this event
		final Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Sends an HTTP request to retrieve all events
	 */
	public void retrieveEvents() {
		final Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
		System.out.println("Retrieve Req Sent");
	}

	/**
	 * Add the given events to the local model (they were received from the core).
	 * This method is called by the GetEventsRequestObserver
	 * 
	 * @param events array of events received from the server
	 */
	public void receivedEvents(Event[] events) {
		// Empty the local model to eliminate duplications
		EventModel.getInstance().emptyModel();
		System.out.println("Received event");
		// Make sure the response was not null
		if (events != null) {
			
			// add the events to the local model
			EventModel.getInstance().addEvents(events);
		}
	}
}
