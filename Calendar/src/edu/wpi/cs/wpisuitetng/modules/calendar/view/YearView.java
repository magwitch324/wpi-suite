package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;

public class YearView extends CalendarView {

	private GregorianCalendar ayear;
	private YearPane yearpane;
	
	public YearView(GregorianCalendar datecalendar) {

		super(datecalendar);
		ayear = new GregorianCalendar();

		yearpane = new YearPane(datecalendar);
		setCalPane(yearpane);
		setCommitmentView(new CommitmentView());

		setRange(datecalendar);
	}
	
	@Override
	public void setRange(GregorianCalendar calendar) {
		ayear.setTime(calendar.getTime());
		
		ayear.set(Calendar.DAY_OF_YEAR, 1);
		setLabel("" + ayear.get(Calendar.YEAR));
				
		refresh();
	}

	@Override
	public void displayCalData(CommitmentList commList, boolean showCommOnCal) {

	}
}
