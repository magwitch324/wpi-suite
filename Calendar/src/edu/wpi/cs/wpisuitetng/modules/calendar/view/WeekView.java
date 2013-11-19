/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;

/**
 * @author cttibbetts
 *
 */
public class WeekView extends CalendarView {

	private Calendar startDate;
	private Calendar endDate;
	
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
		startDate = (Calendar) calendar.clone();

		while (startDate.get(Calendar.DAY_OF_WEEK) != startDate.getFirstDayOfWeek()) {
			startDate.add(Calendar.DAY_OF_WEEK, -1);
		}
		
		endDate = (Calendar) startDate.clone();
		endDate.add(Calendar.WEEK_OF_MONTH, 1);
		endDate.add(Calendar.DAY_OF_MONTH, -1);
		String startMonthName = startDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
		String endMonthName = endDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
		int startDayNum = startDate.get(Calendar.DAY_OF_MONTH);
		int endDayNum = endDate.get(Calendar.DAY_OF_MONTH);
		int startYear = startDate.get(Calendar.YEAR);
		int endYear = endDate.get(Calendar.YEAR);
		
		setLabel(startMonthName + " " + startDayNum + ", " + startYear + "\n-\n" + endMonthName + " " + endDayNum + ", " + endYear);
				
		refresh();
	}

	@Override
	public void displayCalData(CalendarData calData) {
		// TODO Auto-generated method stub
		
	}

}
