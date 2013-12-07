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

/** the status of the commitment */
public enum Status {
	NEW(0), IN_PROGRESS(1), COMPLETED(2);
	public int id;

	Status(int id) {
		this.id = id;
	}

	public static Status getStatusValue(int id) {
		Status result;
		result = Status.NEW;
		switch (id) {
		case 0:
			result = Status.NEW;
			break;
		case 1:
			result = Status.IN_PROGRESS;
			break;
		case 2:
			result = Status.COMPLETED;
			break;
		}

		return result;
	}

	public static String convertToString(int id) {
		String result;
		result = "";
		switch (id) {
		case 0:
			result = "New";
			break;
		case 1:
			result = "In Progress";
			break;
		case 2:
			result = "Completed";
			break;
		}

		return result;
	}

}
