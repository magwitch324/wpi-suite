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

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;

public class YearView extends CalendarView {

	private GregorianCalendar ayear;
	private YearPane yearpane;
	
	public YearView(GregorianCalendar datecalendar) {

		super(datecalendar);
		ayear = new GregorianCalendar();

		yearpane = new YearPane(datecalendar);
		setCalPane(yearpane);
		setCommitmentView(new CommitmentView());

		setRange(datecalendar);
	}
	
	@Override
	public void setRange(GregorianCalendar calendar) {
		ayear.setTime(calendar.getTime());
		
		ayear.set(Calendar.DAY_OF_YEAR, 1);
		setLabel("" + ayear.get(Calendar.YEAR));
				
		refresh();
	}

	@Override
	public void displayCalData(CommitmentList commList, boolean showCommOnCal) {
		commitmentView.updateCommData(commList.getCommitments());
		// TODO filter commitments
		if (showCommOnCal){
			try{
				yearpane.displayCommitments(commList.filter(ayear, Calendar.YEAR)); //add only commitments on today to DayPane
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
}
