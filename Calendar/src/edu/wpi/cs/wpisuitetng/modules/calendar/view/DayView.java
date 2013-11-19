package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;

public class DayView extends CalendarView {

	Calendar day;
	private DayPane dayPane;
	
	public DayView(Calendar datecalendar, TeamCalendar tcalendar) {
		super(datecalendar);
		dayPane = new DayPane(datecalendar, tcalendar);
		setCalPane(dayPane);
		setCommitmentView(new CommitmentView());
		setRange(datecalendar);
		
	}

	@Override
	public void setRange(Calendar calendar) {
		day = (Calendar) calendar.clone();
		
		String dayName = day.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
		int dayNum = day.get(day.DAY_OF_MONTH);
		String monthName = day.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int year = day.get(day.YEAR);
		setLabel(dayName + ", " + monthName + " " + dayNum + ", " + year);
		refresh();
	}

	@Override
	public void displayCalData(CalendarData calData) {
		// TODO Auto-generated method stub
		if(calData != null)
			dayPane.displayCommitments(calData.getCommitments());
		
	}

}
