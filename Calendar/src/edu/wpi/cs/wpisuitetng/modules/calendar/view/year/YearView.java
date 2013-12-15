/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.year;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentView;

/**
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
 @SuppressWarnings("serial")
public class YearView extends CalendarView {

	private final GregorianCalendar ayear;
	private final YearPane yearpane;
	
	/**
	 * Constructor for YearView.
	 * @param datecalendar GregorianCalendar
	 */
	public YearView(GregorianCalendar datecalendar) {

		super(datecalendar);
		ayear = new GregorianCalendar();

		yearpane = new YearPane(datecalendar);
		setCalPane(yearpane);
		setCommitmentView(new CommitmentView());

		setRange(datecalendar);
	}
	
	/**
	 * Sets the year range for the label
	 */
	@Override
	public void setRange(GregorianCalendar calendar) {
		ayear.setTime(calendar.getTime());
		
		ayear.set(Calendar.DAY_OF_YEAR, 1);
		setLabel("" + ayear.get(Calendar.YEAR));
				
		refresh();
	}

	/**
	 * Displays the given events, commitments, and whether commitments are displayed on the year
	 */
	@Override
	public void displayCalData(
			EventList eventList, CommitmentList commList, boolean showCommOnCal) {
		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			try {
				commitmentView.updateCommData(commList.filter(ayear, Calendar.YEAR));
			} catch (CalendarException e) {
				commitmentView.updateCommData(commList.getCommitments());
			}
		}
		
		//Add the events
		try{
			yearpane.displayEvents(eventList.filter(ayear, Calendar.YEAR));
		}
		 catch (CalendarException e) {
			yearpane.displayEvents(eventList.getEvents());
		}
		
		
		
		
		if (showCommOnCal){
			try{
				yearpane.displayCommitments(commList.filter(ayear, Calendar.YEAR)); 
				//add only commitments on today to DayPane
			}
			catch(CalendarException e){
				yearpane.displayCommitments(null);
			}
		}
		else{
			yearpane.displayCommitments(null); //show no commitments on DayPane
		}
	    revalidate();
	    repaint();
	}

	/**
	 * Updates only the commitments that are displayed across the entire year
	 */
	@Override
	public void updateCommPane(CommitmentList commList, boolean showCommOnCal) {
		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			try {
				commitmentView.updateCommData(commList.filter(ayear, Calendar.YEAR));
			} catch (CalendarException e) {
				commitmentView.updateCommData(commList.getCommitments());
			}
		}
	    revalidate();
	    repaint();
	}
}
