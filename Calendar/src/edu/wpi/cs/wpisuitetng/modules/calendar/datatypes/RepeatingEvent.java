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

public class RepeatingEvent extends CalendarObject {

	/** the start date of the event */
	private Date startTime;
	/** the end date of the event */
	private Date endTime;
	/** a list of participants of the event */
	private List<String> participants = new ArrayList<String>();
	/** the number of repetitions */
	private int repetitions;
	public enum RepeatType{
		DAY,WEEK,MONTH;
	};
	/** the type of the repeat */
	private RepeatType repType;

	/**
	 * Constructs a Event with default characteristics
	 */
	public RepeatingEvent() {
		super();
		this.startTime = new Date(0);
		this.endTime = new Date(0);
		this.repetitions = 0;
		this.repType = RepeatType.DAY;
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
	 * @param finalRepeat
	 * 			  A date Representing the final repeat day
	 * @param repType
	 *			  A Repeat type indicating the event's repeat type
	 */
	public RepeatingEvent(String name, String description, GregorianCalendar startTime,
			GregorianCalendar endTime, String[] people, int categoryID,
			boolean isPersonal, int repetitions, RepeatType repType) {
		super(name, description, categoryID, isPersonal);
		this.startTime = startTime.getTime();
		this.endTime = endTime.getTime();
		Collections.addAll(this.participants, people);
		this.repetitions = repetitions;
		this.repType = repType;
	}

	/**
	 * Copies all of the values from the given event to this event.
	 * 
	 * @param toCopyFrom
	 *            the event to copy from.
	 */
	public void copyFrom(RepeatingEvent toCopyFrom) {
		super.copyFrom(toCopyFrom);
		this.startTime = toCopyFrom.getStartTime().getTime();
		this.endTime = toCopyFrom.getEndTime().getTime();
		this.participants = toCopyFrom.getParticipants();
		this.repetitions = toCopyFrom.getRepetitions();
		this.repType = toCopyFrom.getRepType();
	}

	// GETTERS
	/**
	 * Getter for the start time
	 * 
	 * @return start time
	 */
	public GregorianCalendar getStartTime() {
		GregorianCalendar tmp = new GregorianCalendar();
		tmp.setTime(this.startTime);
		return tmp;
	}

	/**
	 * Getter for the end time
	 * 
	 * @return end time
	 */
	public GregorianCalendar getEndTime() {
		GregorianCalendar tmp = new GregorianCalendar();
		tmp.setTime(this.endTime);
		return tmp;
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

	/**
	 * Getter for the final Repeat date
	 * 
	 * @return final repeat date
	 */
	public int getRepetitions() {
		return repetitions;
	}
	
	/**
	 * Getter for the repeat type
	 * 
	 * @return the repType
	 */
	public RepeatType getRepType() {
		return repType;
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
	 * Setter for the final repeat date
	 * 
	 * @param finalRepeat
	 */
	public void setRepetitions(int repetitions) {
		this.repetitions = repetitions;

	}

	/**
	 * Setter for the Repeat Typw
	 * 
	 * @param repType the repType to set
	 */
	public void setRepType(RepeatType repType) {
		this.repType = repType;
	}

}