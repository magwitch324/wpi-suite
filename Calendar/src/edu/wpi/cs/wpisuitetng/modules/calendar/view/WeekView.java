/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;


public class WeekView extends CalendarView {

	private Calendar startDate;
	private Calendar endDate;
	private WeekPane weekPane;
	
	public WeekView(Calendar datecalendar, CalendarData calData) {
		super(datecalendar);
		weekPane = new WeekPane(datecalendar);
		setCalPane(weekPane);
		setCommitmentView(new CommitmentView(calData));
		setRange(datecalendar);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#setRange(java.util.Calendar)
	 */
	@Override
	public void setRange(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		startDate = (Calendar) calendar.clone();

		while (startDate.get(Calendar.DAY_OF_WEEK) != startDate.getFirstDayOfWeek()) {
			startDate.add(Calendar.DAY_OF_WEEK, -1);
		}
		
		// Get end date by skipping to next sunday and
		// then backing up to the saturday
		endDate = (Calendar) startDate.clone();
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
	public void displayCalData(CalendarData personalCalData, CalendarData teamCalData ) {
		
		CommitmentList weekPersonalCommList = new CommitmentList();		
		CommitmentList allPersonalComms  = new CommitmentList();
		CommitmentList weekTeamCommList = new CommitmentList();
		CommitmentList allTeamComms = new CommitmentList();
	
		if(personalCalData != null)
		{
			allPersonalComms = personalCalData.getCommitments(); //currently shown commitments (personal or team)
			for(Commitment comm : allPersonalComms.getCommitments())
			{
				// add to list if within bounds of week
				if (!(comm.getDueDate().before(startDate.getTime()) || comm.getDueDate().after(endDate.getTime())))
					weekPersonalCommList.addCommitment(comm);
			}
		}
		if (teamCalData!=null)
		{		
			allTeamComms = teamCalData.getCommitments();
			for(Commitment comm : allTeamComms.getCommitments())
				{
					if (!(comm.getDueDate().before(startDate.getTime()) || comm.getDueDate().after(endDate.getTime())))
						weekTeamCommList.addCommitment(comm);
				}
				
		}
			
		weekPane.displayCommitments(weekPersonalCommList, weekTeamCommList);
		commitments.setCommList(allPersonalComms, allTeamComms);
//	    commitments.update();
		// TODO Auto-generated method stub
		
	}

}
