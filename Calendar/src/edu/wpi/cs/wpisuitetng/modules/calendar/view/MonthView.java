package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class MonthView extends CalendarView {
	

	private GregorianCalendar aMonth;
	private MonthPane monthPane;
	


	public MonthView(GregorianCalendar datecalendar) {

		super(datecalendar);
		aMonth = new GregorianCalendar();

		monthPane = new MonthPane(datecalendar);
		setCalPane(monthPane);
		setCommitmentView(new CommitmentView());

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
	
	public void displayCalData(CalendarData personalCalData, CalendarData teamCalData) {
		commitments.update();
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayCalData(CommitmentList commList) {
		// TODO Auto-generated method stub
		
	}

}
