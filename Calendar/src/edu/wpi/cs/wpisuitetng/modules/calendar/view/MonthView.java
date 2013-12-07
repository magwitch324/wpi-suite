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

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;

@SuppressWarnings("serial")
public class MonthView extends CalendarView {
	
	private GregorianCalendar aMonth;
	private MonthPane monthPane;
	


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
		
		String monthName = aMonth.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		
		setLabel(monthName + " " + aMonth.get(Calendar.YEAR));
				
		refresh();
	}
	
	public void displayCalData(CalendarData personalCalData, CalendarData teamCalData) {
		commitmentView.update();
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayCalData(CommitmentList commList, boolean showCommOnCal) {
		if (super.showAllCommFlag){
			commitmentView.updateCommData(commList.getCommitments());
		} else {
			try {
				commitmentView.updateCommData(commList.filter(aMonth, Calendar.MONTH));
			} catch (CalendarException e) {
				commitmentView.updateCommData(commList.getCommitments());
			}
		}
		// TODO filter commitments
		if (showCommOnCal){
			try{
				monthPane.displayCommitments(commList.filter(aMonth, Calendar.MONTH)); //add only commitments on today to DayPane
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

}
