package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

public class DayView extends CalendarView {

	public DayView(Calendar calendar) {
		super(calendar);
		setCalPane(new DayPane());
		setCommitmentView(new CommitmentView());
		refresh();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setRange(Calendar calendar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextUnit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void prevUnit() {
		// TODO Auto-generated method stub

	}

}
