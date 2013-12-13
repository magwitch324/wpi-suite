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

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.LONG;
import static java.util.Calendar.YEAR;

import java.awt.Color;
import java.awt.Font;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * CalendarStandard sets standards in the calendar
 * module such as colors and fonts. 
 */
@SuppressWarnings("serial")
public abstract class CalendarStandard {
	
	public static final Color CalendarRed = new Color(196, 0, 14);
	public static final Color CalendarYellow = new Color(255, 255, 220);
	public static final Color HeatMapRed = new Color(255, 70, 70);
	public static final Font  CalendarFont = new Font("SansSerif", 1, 12);
	public static final Font  CalendarFontBold = new Font("SansSerif", Font.BOLD, 12);
	
	public static void printcalendar(GregorianCalendar cal) {
		final String dayName = cal.getDisplayName(GregorianCalendar.DAY_OF_WEEK,
				LONG, Locale.ENGLISH);
		final int dayNum = cal.get(DAY_OF_MONTH);
		final String monthName = cal.getDisplayName(GregorianCalendar.MONTH, LONG,
				Locale.ENGLISH);
		final int year = cal.get(YEAR);
		System.out.println(dayName + ", " + monthName + " " + dayNum + ", "
				+ year);
	}
	
}
