package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

public class DayView extends CalendarView {

	Calendar day;
	
	public DayView(Calendar calendar) {
		super(calendar);
		setCalPane(new DayPane());
		setCommitmentView(new CommitmentView());
		refresh();
	}

	@Override
	public void setRange(Calendar calendar) {
		day = calendar;
	}

	@Override
	public void nextUnit() {
		day.add(Calendar.DAY_OF_MONTH, 1);
	}

	@Override
	public void prevUnit() {
		day.add(Calendar.DAY_OF_MONTH, -1);
	}

}
