/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar;

/**
 * customized wrapper class to distinguish calendar exception from others.
 *  /* @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CalendarException extends Exception {

	/**
	 * Create a message with a message describing the problem
	 * @param message describes the problem
	 */
	public CalendarException(String message) {
		super(message);
	}
}
