package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.Locale;

public class DayView extends CalendarView {

	Calendar day;
	
	public DayView(Calendar calendar) {
		super(calendar);
		setCalPane(new DayPane());
		setCommitmentView(new CommitmentView());
		setRange(calendar);
	}

	@Override
	public void setRange(Calendar calendar) {
		day = calendar;
		String dayName = day.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
		int dayNum = day.get(day.DAY_OF_MONTH);
		String monthName = day.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int year = day.get(day.YEAR);
		setLabel(dayName + ", " + monthName + " " + dayNum + ", " + year);
		refresh();
	}

}
