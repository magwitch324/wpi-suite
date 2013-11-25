package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class DayView extends CalendarView {

	Calendar day;
	Calendar endOfDay;
	private DayPane dayPane;
	
	public DayView(Calendar datecalendar) {
		super(datecalendar);
		dayPane = new DayPane(datecalendar);
		setCalPane(dayPane);
		setCommitmentView(new CommitmentView());
		setRange(datecalendar);
		
	}

	@Override
	public void setRange(Calendar calendar) {
		day = (Calendar) calendar.clone();
		//set day to 0:00:00.000
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		
		//set endOfDay to 23:59:59.999
		endOfDay = (Calendar) day.clone();
		endOfDay.add(Calendar.DATE, 1);
		endOfDay.add(Calendar.MILLISECOND, -1);
		
		String dayName = day.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
		int dayNum = day.get(day.DAY_OF_MONTH);
		String monthName = day.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int year = day.get(day.YEAR);
		setLabel(dayName + ", " + monthName + " " + dayNum + "<br>" + year);
		refresh();
	}

	@Override
	public void displayCalData(CalendarData personalCalData, CalendarData teamCalData, boolean showCommsOnCalPane) {
		
		CommitmentList dayPersonalCommList = new CommitmentList();		
		CommitmentList allPersonalComms  = new CommitmentList();
		CommitmentList dayTeamCommList = new CommitmentList();
		CommitmentList allTeamComms = new CommitmentList();
		
		//add personal cal data if given
		if(personalCalData != null)
		{
			allPersonalComms = personalCalData.getCommitments(); //currently shown commitments (personal or team)
			for(Commitment comm : allPersonalComms.getCommitments())
			{
				// add to list if within bounds of day
				if (!(comm.getDueDate().after(endOfDay.getTime()) || comm.getDueDate().before(day.getTime())))
					dayPersonalCommList.addCommitment(comm);
			}
		}
		//add team cal data if given
		if (teamCalData!=null)
		{		
			allTeamComms = teamCalData.getCommitments();
			for(Commitment comm : allTeamComms.getCommitments())
				{
					if (!(comm.getDueDate().after(endOfDay.getTime()) || comm.getDueDate().before(day.getTime())))
						dayTeamCommList.addCommitment(comm);
				}
				
		}
		if (showCommsOnCalPane)
			dayPane.displayCommitments(dayPersonalCommList, dayTeamCommList); //add only commitments on today to DayPane
		else
			dayPane.displayCommitments(new CommitmentList(), new CommitmentList()); //show no commitments on DayPane

		commitments.setCommList(allPersonalComms, allTeamComms); //add all commitments to CommitmentView
	    revalidate();
	    repaint();
	    
//	    refresh();
		
	}

}
