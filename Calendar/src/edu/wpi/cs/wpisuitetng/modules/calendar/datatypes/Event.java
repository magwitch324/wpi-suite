/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Date;

/**
 * Main data storage class for event.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class Event extends CalendarObject {

	/** the start date of the event */
	private Date startTime;
	/** the end date of the event */
	private Date endTime;
	/** a list of participants of the event */
	private List<String> participants = new ArrayList<String>();
	/** indicates whether or not the event is a repeating one*/
	private boolean isRepeating;
	
	/**
	 * Constructs a Event with default characteristics
	 */
	public Event() {
		startTime = new Date(0);
		endTime = new Date(0);
		isRepeating = false;
	}

	/**
	 * Construct a Event with required properties provided and others set to
	 * default
	 * 
	 * @param name
	 *            The name of the Event
	 * @param description
	 *            The description of the Event
	 * @param startTime
	 *            The start time of the Event
	 * @param endTime
	 *            The end time of the Event
	 * @param people
	 *            The list of participants for the Event
	 * @param categoryID
	 *            The Category of the Event
	 * @param isPersonal
	 *            A boolean stating that the Event is personal
	 */
	public Event(String name, String description, GregorianCalendar startTime,
			GregorianCalendar endTime, String[] people, int categoryID,
			boolean isPersonal) {
		super(name, description, categoryID, isPersonal);
		this.startTime = startTime.getTime();
		this.endTime = endTime.getTime();
		Collections.addAll(participants, people);
	}

	/**
	 * Copies all of the values from the given event to this event.
	 * 
	 * @param toCopyFrom
	 *            the event to copy from.
	 */
	public void copyFrom(Event toCopyFrom) {
		super.copyFrom(toCopyFrom);
		startTime = toCopyFrom.getStartTime().getTime();
		endTime = toCopyFrom.getEndTime().getTime();
		participants = toCopyFrom.getParticipants();
	}

	// GETTERS
	/**
	 * Getter for the start time
	 * 
	 * @return start time
	 */
	public GregorianCalendar getStartTime() {
		final GregorianCalendar tmp = new GregorianCalendar();
		tmp.setTime(startTime);
		return tmp;
	}

	/**
	 * Getter for the end time
	 * 
	 * @return end time
	 */
	public GregorianCalendar getEndTime() {
		final GregorianCalendar tmp = new GregorianCalendar();
		tmp.setTime(endTime);
		return tmp;
	}

	/**
	 * Getter for whether or not the event is a repeating one
	 * 
	 * @return whether or not the event repeats
	 */
	public boolean getIsRepeating() {
		return isRepeating;
	}
	
	/**
	 * Getter for participants
	 * 
	 * @return the list of strings representing the users for whom the event has
	 *         been involved.
	 */
	public List<String> getParticipants() {
		return participants;
	}

	// SETTERS
	/**
	 * Setter for the start time
	 * 
	 * @param startTime
	 */
	public void setStartTime(GregorianCalendar startTime) {
		this.startTime = startTime.getTime();

	}

	/**
	 * Setter for the end time
	 * 
	 * @param endTime
	 */
	public void setEndTime(GregorianCalendar endTime) {
		this.endTime = endTime.getTime();
	}

	/**
	 * Setter for participants
	 * 
	 * @param participants
	 *            the list of strings representing the people who the event is
	 *            involved.
	 */
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	/**
	 * Setter for the whether or not the event is a repeating one
	 * 
	 * @param isRepeating
	 */
	public void setIsRepeating(boolean isRepeating) {
		this.isRepeating = isRepeating;
	}
}