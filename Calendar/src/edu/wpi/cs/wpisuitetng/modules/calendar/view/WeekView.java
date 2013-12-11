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

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;


@SuppressWarnings("serial")
public class WeekView extends CalendarView {

	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private final WeekPane weekPane;
	

	public WeekView(GregorianCalendar datecalendar) {
		super(datecalendar);
		weekPane = new WeekPane(datecalendar);
		setCalPane(weekPane);
		setCommitmentView(new CommitmentView());
		setRange(datecalendar);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#setRange(java.util.Calendar)
	 */
	@Override
	public void setRange(GregorianCalendar calendar) {
		
		startDate = new GregorianCalendar();
		endDate = new GregorianCalendar();
		//set startDate to 0:00:00.000 Sunday
		startDate.setTime(calendar.getTime());
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

//		while (startDate.get(Calendar.DAY_OF_WEEK) != startDate.getFirstDayOfWeek()) {
//			startDate.add(Calendar.DAY_OF_WEEK, -1);
//		}
		
		// Get end date by skipping to next sunday and
		// then backing up to the saturday
		endDate.setTime(startDate.getTime());
		endDate.add(Calendar.DAY_OF_YEAR, 7);
		endDate.add(Calendar.MILLISECOND, -1);

		
		final String startMonthName = startDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		final String endMonthName = endDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		final int startDayNum = startDate.get(Calendar.DAY_OF_MONTH);
		final int endDayNum = endDate.get(Calendar.DAY_OF_MONTH);
		final int startYear = startDate.get(Calendar.YEAR);
		final int endYear = endDate.get(Calendar.YEAR);
		
		setLabel(startMonthName + " " + startDayNum + ", " + startYear + "<br>---<br>" + endMonthName + " " + endDayNum + ", " + endYear);

		refresh();
	}

	@Override
	public void displayCalData(EventList eventList, CommitmentList commList, boolean showCommOnCal) {

		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			commitmentView.updateCommData(commList.filter(startDate,endDate));
		}
		// TODO filter commitments
		if (showCommOnCal){
			weekPane.displayCommitments(commList.filter(startDate, endDate)); //add only commitments on today to DayPane
			weekPane.displayEvents(eventList.filter(startDate, endDate));
		}
		else{
			weekPane.displayCommitments(null); //show no commitments on DayPane
			weekPane.displayEvents(eventList.filter(startDate, endDate));
		}

		revalidate();
		repaint();

	}

	@Override
	public void updateCommPane(CommitmentList commList, boolean showCommOnCal) {
		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			commitmentView.updateCommData(commList.filter(startDate,endDate));
		}
		revalidate();
		repaint();
	}

}
