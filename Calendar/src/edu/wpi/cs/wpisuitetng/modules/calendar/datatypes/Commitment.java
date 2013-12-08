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

import java.util.Date;
import java.util.GregorianCalendar;

public class Commitment extends CalendarObject implements
		Comparable<Commitment> {

	/** the DueDate of the commitment */
	private Date dueDate;
	/** the current status of the commitment */
	private Status status = Status.NEW;

	/**
	 * Construct a commitment with default characteristics
	 */
	public Commitment() {
		super();
		dueDate = new Date();
	}

	/**
	 * Construct a commitment with required properties provided and others set
	 * to default
	 * 
	 * @param name
	 *            The name number of the commitment
	 * @param dueDate
	 *            The due date number of the commitment
	 * @param description
	 *            A brief description of the commitment
	 * @param category
	 *            The category ID number of the commitment
	 * @param isPersonal
	 *            A boolean stating that the commitment is personal
	 */
	public Commitment(String name, GregorianCalendar dueDate,
			String description, int categoryID, boolean isPersonal) {
		super(name, description, categoryID, isPersonal);
		this.dueDate = dueDate.getTime();
	}

	/**
	 * Copies all of the values from the given commitment to this commitment.
	 * 
	 * @param toCopyFrom
	 *            the commitment to copy from.
	 */
	public void copyFrom(Commitment toCopyFrom) {
		super.copyFrom(toCopyFrom);
		this.dueDate = toCopyFrom.getDueDate().getTime();
		this.status = toCopyFrom.getStatus();
	}

	public int compareTo(Commitment person) {
		if (this.name != null && person.name != null) {
			return this.name.compareToIgnoreCase(person.name);
		}
		return 0;
	}

	// GETTERS
	/**
	 * Getter for the DueDate
	 * 
	 * @return the due date
	 */
	public GregorianCalendar getDueDate() {
		GregorianCalendar tmp = new GregorianCalendar();
		tmp.setTime(dueDate);
		return tmp;
	}

	/**
	 * Getter for the status
	 * 
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	// Setters
	/**
	 * Setter for the dueDate
	 * 
	 * @param dueDate
	 *            the due date to set
	 */
	public void setDueDate(GregorianCalendar dueDate) {
		this.dueDate = dueDate.getTime();
	}

	/**
	 * Setter for the status
	 * 
	 * @param status
	 *            to status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
}