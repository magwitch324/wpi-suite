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
	private DayPane dayPane;
	
	public DayView(Calendar datecalendar, CalendarData calData) {
		super(datecalendar);
		dayPane = new DayPane(datecalendar, calData);
		setCalPane(dayPane);
		setCommitmentView(new CommitmentView(calData));
		setRange(datecalendar);
		
	}

	@Override
	public void setRange(Calendar calendar) {
		day = (Calendar) calendar.clone();
		
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
				if (comm.getDueDate().getDay() == day.getTime().getDay())
					dayPersonalCommList.addCommitment(comm);
			}
		}
		//add team cal data if given
		if (teamCalData!=null)
		{		
			allTeamComms = teamCalData.getCommitments();
			for(Commitment comm : allTeamComms.getCommitments())
				{
					if (comm.getDueDate().getDay() == day.getTime().getDay())
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
