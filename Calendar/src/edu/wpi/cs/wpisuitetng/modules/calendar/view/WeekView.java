/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.Locale;

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
		setRange(calendar);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#setRange(java.util.Calendar)
	 */
	@Override
	public void setRange(Calendar calendar) {
		startDate = calendar;
		startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Calendar endDate = startDate;
		endDate.add(Calendar.WEEK_OF_MONTH, 1);
		
		String startMonthName = startDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
		String endMonthName = endDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
		int startDayNum = startDate.get(Calendar.DAY_OF_MONTH);
		int endDayNum = endDate.get(Calendar.DAY_OF_MONTH);
		int startYear = startDate.get(Calendar.YEAR);
		int endYear = endDate.get(Calendar.YEAR);
		
		setLabel(startMonthName + " " + startDayNum + ", " + startYear + " - " + startMonthName + " " + startDayNum + ", " + startYear);
				
		refresh();
	}

}
