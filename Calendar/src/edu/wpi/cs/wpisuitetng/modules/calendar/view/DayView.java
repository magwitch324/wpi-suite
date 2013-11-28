package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class DayView extends CalendarView {

	GregorianCalendar day;
	private DayPane dayPane;
	
	public DayView(GregorianCalendar datecalendar, AbCalendar abCalendar) {
		super(datecalendar);
		dayPane = new DayPane(datecalendar, abCalendar);
		setCalPane(dayPane);
		setCommitmentView(new CommitmentView(abCalendar));
		setRange(datecalendar);
		
	}

	@Override
	public void setRange(GregorianCalendar calendar) {
		day = new GregorianCalendar();
		day.setTime(calendar.getTime());
		
		String dayName = day.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
		int dayNum = day.get(day.DAY_OF_MONTH);
		String monthName = day.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int year = day.get(day.YEAR);
		setLabel(dayName + ", " + monthName + " " + dayNum + "<br>" + year);
		refresh();
	}

	@Override
	public void displayCalData(CalendarData calData) {
		// TODO Auto-generated method stub
		if(calData != null)
		{
			CommitmentList dayCommList = new CommitmentList();
			for(Commitment comm : calData.getCommitments().getCommitments())
			{
				if (comm.getDueDate().get(Calendar.DAY_OF_MONTH) == day.get(Calendar.DAY_OF_MONTH))
					dayCommList.addCommitment(comm);
			}
			dayPane.displayCommitments(dayCommList);
			commitments.setCommList();
		    commitments.update();

		}
		
	}

}
