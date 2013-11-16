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
		String startDayNum = startDate.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.SHORT, Locale.ENGLISH);
		String endDayNum = endDate.getDisplayName(Calendar.DAY_OF_MONTH, Calendar.SHORT, Locale.ENGLISH);
		String startYear = startDate.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.ENGLISH);
		String endYear = endDate.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.ENGLISH);
		
		setLabel(startMonthName + " " + startDayNum + ", " + startYear + " - " + startMonthName + " " + startDayNum + ", " + startYear);
				
		refresh();
	}

}
