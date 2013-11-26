/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;


public class WeekView extends CalendarView {

	private Calendar startDate;
	private Calendar endDate;
	
	public WeekView(GregorianCalendar datecalendar, AbCalendar abCalendar) {
		super(datecalendar);
		setCalPane(new WeekPane(datecalendar, abCalendar));
		setCommitmentView(new CommitmentView(abCalendar));
		setRange(datecalendar);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#setRange(java.util.Calendar)
	 */
	@Override
	public void setRange(GregorianCalendar calendar) {
		startDate = new GregorianCalendar();
		endDate = new GregorianCalendar();
		startDate.setTime(calendar.getTime());

		while (startDate.get(Calendar.DAY_OF_WEEK) != startDate.getFirstDayOfWeek()) {
			startDate.add(Calendar.DAY_OF_WEEK, -1);
		}
		
		// Get end date by skipping to next sunday and
		// then backing up to the saturday
		endDate.setTime(startDate.getTime());
		endDate.add(Calendar.WEEK_OF_MONTH, 1);
		endDate.add(Calendar.DAY_OF_MONTH, -1);
		
		
		String startMonthName = startDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		String endMonthName = endDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int startDayNum = startDate.get(Calendar.DAY_OF_MONTH);
		int endDayNum = endDate.get(Calendar.DAY_OF_MONTH);
		int startYear = startDate.get(Calendar.YEAR);
		int endYear = endDate.get(Calendar.YEAR);
		
		setLabel(startMonthName + " " + startDayNum + ", " + startYear + "<br>---<br>" + endMonthName + " " + endDayNum + ", " + endYear);
				
		refresh();
	}

	@Override
	public void displayCalData(CalendarData calData) {
		commitments.update();
		// TODO Auto-generated method stub
		
	}

}
