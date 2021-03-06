/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.month;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentView;

/**
 * Month view is manages set date range for month pane,
 * display calendar data (shows only events on default),
 * and update calendar data.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
 @SuppressWarnings("serial")
public class MonthView extends CalendarView {
	
	private final GregorianCalendar aMonth;
	private final MonthPane monthPane;
	


	/**
	 * Constructor for MonthView.
	 * @param datecalendar GregorianCalendar
	 */
	public MonthView(GregorianCalendar datecalendar) {

		super(datecalendar);
		aMonth = new GregorianCalendar();

		monthPane = new MonthPane(datecalendar);
		setCalPane(monthPane);
		setCommitmentView(new CommitmentView());

		setRange(datecalendar);
	}
	
	
	public void setRange(GregorianCalendar calendar) {
		aMonth.setTime(calendar.getTime());

		while (aMonth.get(Calendar.DAY_OF_MONTH) != 1) {
			aMonth.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		final String monthName = aMonth.getDisplayName(
				Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		
		setLabel(monthName + " " + aMonth.get(Calendar.YEAR));
				
		refresh();
	}

	@Override
	public void displayCalData(
			EventList eventList, CommitmentList commList, boolean showCommOnCal) {
		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			try {
				commitmentView.updateCommData(commList.filter(aMonth, Calendar.MONTH));
			} catch (CalendarException e) {
				commitmentView.updateCommData(commList.getCommitments());
			}
		}
		
		try {
			monthPane.displayEvents(eventList.filter(aMonth, Calendar.MONTH));
		} catch (CalendarException e1) {
			e1.printStackTrace();
		}
		
		
		if (showCommOnCal){
			try{
				monthPane.displayCommitments(commList.filter(aMonth, Calendar.MONTH)); 
				//add only commitments on today to DayPane
			}
			catch(CalendarException e){
				monthPane.displayCommitments(null);
			}
		}
		else{
			monthPane.displayCommitments(null); //show no commitments on DayPane
		}

	    revalidate();
	    repaint();
		
	}


	@Override
	public void updateCommPane(CommitmentList commList, boolean showCommOnCal) {
		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			try {
				commitmentView.updateCommData(commList.filter(aMonth, Calendar.MONTH));
			} catch (CalendarException e) {
				commitmentView.updateCommData(commList.getCommitments());
			}
		}
	    revalidate();
	    repaint();
	}
	
	public String getCommRangeText(){
		return "Show all open commitments for month";
	}

}
