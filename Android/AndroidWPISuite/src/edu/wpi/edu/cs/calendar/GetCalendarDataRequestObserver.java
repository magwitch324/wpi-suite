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


import edu.wpi.edu.cs.calendar.CalendarData;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer handles responses to requests for all categories
 *
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class GetCalendarDataRequestObserver implements RequestObserver {
	
	private final GetCalendarDataController controller;
	
	/**
	 * Constructs the observer given a GetCalendarDataController
	 * @param controller the controller used to retrieve categories
	 */
	public GetCalendarDataRequestObserver(GetCalendarDataController controller) {
		this.controller = controller;
	}

	/**
	 * Parse the CalendarData out of the response body and pass them to the controller
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess
	 * (edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Convert the JSON array of calendarData to a CalendarData object array
		final CalendarData[] calData = CalendarData.fromJsonArray(iReq.getResponse().getBody());
		
		// Pass these Events to the controller
		controller.receivedCalendarData(calData);
		
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError
	 * (edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/**
	 * 
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail
	 * (edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		final CalendarData[] errorCalData = { new CalendarData("Error") };
		controller.receivedCalendarData(errorCalData);
	}

}
