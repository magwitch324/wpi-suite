package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class MonthView extends CalendarView {
	
	private AbCalendar tcalendar;
	private GregorianCalendar aMonth;
	

	public MonthView(GregorianCalendar datecalendar, AbCalendar tcalendar) {
		super(datecalendar);
		setCalPane(new MonthPane(datecalendar, tcalendar));
		aMonth = (GregorianCalendar)datecalendar.clone();
		setCommitmentView(new CommitmentView(tcalendar));
		setRange(datecalendar);
	}
	
	
	public void setRange(GregorianCalendar calendar) {
		aMonth.setTime(calendar.getTime());

		while (aMonth.get(Calendar.DAY_OF_MONTH) != aMonth.DAY_OF_MONTH) {
			aMonth.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		String monthName = aMonth.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int startDayNum = aMonth.get(Calendar.DAY_OF_MONTH);
		int endDayNum = aMonth.get(Calendar.DAY_OF_MONTH);
		int startYear = aMonth.get(Calendar.YEAR);
		int endYear = aMonth.get(Calendar.YEAR);
		
		setLabel(monthName + " " + aMonth.get(Calendar.YEAR));
				
		refresh();
	}
	
	@Override
	public void displayCalData(CalendarData calData) {
		commitments.update();
		// TODO Auto-generated method stub
		
	}

}
