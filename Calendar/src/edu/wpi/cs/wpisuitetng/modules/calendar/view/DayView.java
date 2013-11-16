package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

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
		refresh();
	}

}
