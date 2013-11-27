/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;


public class WeekView extends CalendarView {

	private Calendar startDate;
	private Calendar endDate;
	private WeekPane weekPane;
	

	public WeekView(GregorianCalendar datecalendar) {
		super(datecalendar);
		weekPane = new WeekPane(datecalendar);
		setCalPane(weekPane);
		setCommitmentView(new CommitmentView());
		setRange(datecalendar);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView#setRange(java.util.Calendar)
	 */
	@Override
	public void setRange(GregorianCalendar calendar) {
		
		startDate = new GregorianCalendar();
		endDate = new GregorianCalendar();
		//set startDate to 0:00:00.000 Sunday
		startDate.setTime(calendar.getTime());
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

//		while (startDate.get(Calendar.DAY_OF_WEEK) != startDate.getFirstDayOfWeek()) {
//			startDate.add(Calendar.DAY_OF_WEEK, -1);
//		}
		
		// Get end date by skipping to next sunday and
		// then backing up to the saturday
		endDate.setTime(startDate.getTime());
		endDate.add(Calendar.WEEK_OF_MONTH, 1);
		endDate.add(Calendar.DAY_OF_MONTH, -1);
		endDate.add(Calendar.MILLISECOND, -1);

		
		String startMonthName = startDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		String endMonthName = endDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int startDayNum = startDate.get(Calendar.DAY_OF_MONTH);
		int endDayNum = endDate.get(Calendar.DAY_OF_MONTH);
		int startYear = startDate.get(Calendar.YEAR);
		int endYear = endDate.get(Calendar.YEAR);
		
		setLabel(startMonthName + " " + startDayNum + ", " + startYear + "<br>---<br>" + endMonthName + " " + endDayNum + ", " + endYear);

		refresh();
	}

	@Override
	public void displayCalData(CommitmentList commList, boolean showCommOnCal) {


		commitmentView.updateCommData(commList.getCommitments());
		// TODO filter commitments
		if (showCommOnCal)
			weekPane.displayCommitments(commList.filter(startDate, endDate)); //add only commitments on today to DayPane
		else
			weekPane.displayCommitments(null); //show no commitments on DayPane

		revalidate();
		repaint();

	}

}
