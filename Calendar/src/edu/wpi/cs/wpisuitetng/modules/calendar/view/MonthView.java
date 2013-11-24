package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class MonthView extends CalendarView {
	
	private Calendar aMonth;
	private MonthPane monthPane;
	
	public MonthView(Calendar datecalendar, CalendarData calData) {
		super(datecalendar);
		monthPane = new MonthPane(datecalendar);
		setCalPane(monthPane);
		setCommitmentView(new CommitmentView(calData));
		setRange(datecalendar);
	}
	
	
	public void setRange(Calendar calendar) {
		aMonth = (Calendar) calendar.clone();

		while (aMonth.get(Calendar.DAY_OF_MONTH) != aMonth.DAY_OF_MONTH) {
			aMonth.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		String monthName = aMonth.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int startDayNum = aMonth.get(Calendar.DAY_OF_MONTH);
		int endDayNum = aMonth.get(Calendar.DAY_OF_MONTH);
		int startYear = aMonth.get(Calendar.YEAR);
		int endYear = aMonth.get(Calendar.YEAR);
		
		setLabel(monthName + " ");
				
		refresh();
	}
	
	public void displayCalData(CalendarData personalCalData, CalendarData teamCalData) {
		commitments.update();
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayCalData(CalendarData personalCalData,
			CalendarData teamCalData, boolean showCommsOnCalPane) {
		// TODO Auto-generated method stub
		
	}

}
