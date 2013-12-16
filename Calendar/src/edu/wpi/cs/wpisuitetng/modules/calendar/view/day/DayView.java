/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.day;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentView;

 /* @author CS Anonymous
  * @version $Revision: 1.0 $
  */
/**
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
 @SuppressWarnings("serial")
public class DayView extends CalendarView {


	GregorianCalendar day;
	private final DayPane dayPane;
	private GregorianCalendar endOfDay;
	
	/**
	 * Constructor for DayView.
	 * @param datecalendar GregorianCalendar
	 */
	public DayView(GregorianCalendar datecalendar) {
		super(datecalendar);
		dayPane = new DayPane(datecalendar);
		setCalPane(dayPane);
		setCommitmentView(new CommitmentView());
		setRange(datecalendar);
	}

	@Override
	public void setRange(GregorianCalendar calendar) {
		day = new GregorianCalendar();
		day.setTime(calendar.getTime());
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		
		//set endOfDay to 23:59:59.999
		endOfDay = day;
		endOfDay.add(Calendar.DATE, 1);
		endOfDay.add(Calendar.MILLISECOND, -1);
		
		final String dayName = day.getDisplayName(
				Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
		final int dayNum = day.get(Calendar.DAY_OF_MONTH);
		final String monthName = day.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		final int year = day.get(Calendar.YEAR);
		setLabel(dayName + ", " + monthName + " " + dayNum + ", " + year);
		refresh();
	}

	@Override
	public void displayCalData(
			EventList eventList, CommitmentList commList, boolean showCommOnCal) {

		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			try {
				commitmentView.updateCommData(commList.filter(day));
			} catch (CalendarException e) {
				e.printStackTrace();
			}
		}

		if (showCommOnCal)
			{
			try {
				
				dayPane.displayEvents(eventList.filter(day));
				dayPane.displayCommitments(commList.filter(day));
			} catch (CalendarException e) {
				e.printStackTrace();
			}
			}
		else{
			dayPane.displayCommitments(null); //show no commitments on DayPane
			try {
				dayPane.displayEvents(eventList.filter(day));
			} catch (CalendarException e) {
				e.printStackTrace();
			}
		}

		revalidate();
		repaint();

		//refresh();
		
	}

	@Override
	public void updateCommPane(CommitmentList commList, boolean showCommOnCal) {
		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			try {
				commitmentView.updateCommData(commList.filter(day));
			} catch (CalendarException e) {
				e.printStackTrace();
			}
		}
		revalidate();
		repaint();
	}

}
