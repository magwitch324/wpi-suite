/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

/**
 * @author cttibbetts
 *
 */
public class WeekView extends CalendarView {

	private Calendar startDate;
	
	public WeekView(Calendar calendar) {
		super(calendar);
		setCalPane(new WeekPane());
		setCommitmentView(new CommitmentView());
		refresh();
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#setRange(java.util.Calendar)
	 */
	@Override
	public void setRange(Calendar calendar) {
		startDate = calendar;
		startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#nextUnit()
	 */
	@Override
	public void nextUnit() {
		startDate.add(Calendar.WEEK_OF_MONTH, 1);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#prevUnit()
	 */
	@Override
	public void prevUnit() {
		startDate.add(Calendar.WEEK_OF_MONTH, -1);
	}

}
