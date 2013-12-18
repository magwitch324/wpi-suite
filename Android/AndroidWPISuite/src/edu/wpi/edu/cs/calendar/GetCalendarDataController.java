/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.edu.cs.calendar;



import android.util.Log;
import edu.wpi.edu.cs.calendar.CalendarData;
import edu.wpi.edu.cs.calendar.CalendarDataModel;
import edu.wpi.cs.loginactivity.DaySingleton;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the categories
 * from the server.
 *
 * @version $Revision: 1.0 $
 */
public class GetCalendarDataController {

	private final GetCalendarDataRequestObserver observer;
	private static GetCalendarDataController instance;

	/**
	 * Constructs the controller given a CalendarDataModel
	 */
	private GetCalendarDataController() {
		
		observer = new GetCalendarDataRequestObserver(this);
	}
	
	/**
	
	 * @return the instance of the GetCalendarDataController or creates one if it does not
	 * exist. */
	public static GetCalendarDataController getInstance()
	{
		if(instance == null)
		{
			instance = new GetCalendarDataController();
		}
		
		return instance;
	}


	/**
	 * Sends an HTTP request to retrieve all calendarDatas
	 */
	public void retrieveCalendarData() {
		final Request request = Network.getInstance().makeRequest(
				"calendar/calendardata", HttpMethod.GET); // GET == read
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
		System.out.println("Retrieve caldata Sent");
		
	}

	/**
	 * Add the given calendarDatas to the local model (they were received from the core).
	 * This method is called by the GetCalendarDataRequestObserver
	 * 
	
	 * @param calData CalendarData[]
	 */
	public void receivedCalendarData(CalendarData[] calData) {
		// Empty the local model to eliminate duplications
		CalendarDataModel.getInstance().emptyModel();
		System.out.println("Received CalData");
		// Make sure the response was not null
		if (calData != null) {
			
			// add the categories to the local model
			CalendarDataModel.getInstance().addCalendarData(calData);
			//refreshes calendar GUI
			//GUIEventController.getInstance().updateCalData();
			
			DaySingleton.getInstance().setCalStatus("Not Grabbing");
			/*CalendarData myCalData = CalendarDataModel.getInstance().getCalendarData(
					DaySingleton.getInstance().getProjectName() + "-"
							+ DaySingleton.getInstance().getUserName());
			if(myCalData == null){
				Log.d("NOOOOO", "sdfsd");
			}
			else{
			Log.d("IT WORKED OMG", myCalData.getCommitments().getCommitments().toString());
			}
			*/
		}
	}
}
