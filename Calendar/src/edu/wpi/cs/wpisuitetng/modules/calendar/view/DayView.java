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
		String dayNum = day.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.LONG, Locale.ENGLISH);
		String monthName = day.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		String year = day.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.ENGLISH);
		setLabel(dayName + ", " + monthName + " " + dayNum + ", " + year);
		refresh();
	}

}
