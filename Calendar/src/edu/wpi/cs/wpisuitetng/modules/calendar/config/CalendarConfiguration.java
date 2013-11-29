/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.config;

import java.io.Serializable;
import java.util.Calendar;


public class CalendarConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1184439021320564736L;
	private Calendar date;
	public CalendarConfiguration() {
		
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}

}
