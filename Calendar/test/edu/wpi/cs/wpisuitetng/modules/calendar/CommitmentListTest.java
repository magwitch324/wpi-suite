package edu.wpi.cs.wpisuitetng.modules.calendar;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;

public class CommitmentListTest {

	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */
	
	CommitmentList commitments;
	Calendar start = (Calendar) Calendar.getInstance().clone();
	Calendar end   = (Calendar) Calendar.getInstance().clone();
	/*
	@SuppressWarnings("deprecation")
	@Before
	public void setup() {
		commitments = new CommitmentList();
		commitments.addCommitment(new Commitment("Last Year", new Date(112, 0, 30), "A commitment from last year", 1, true));
		commitments.addCommitment(new Commitment("Last Month", new Date(113, 10, 12), "A commitment from last month", 1, true));
		commitments.addCommitment(new Commitment("Last Week", new Date(113, 11, 16), "A commitment for a week ago", 1, true));
		commitments.addCommitment(new Commitment("Today", new Date(113, 11, 23), "A commitment from today", 1, true));
		commitments.addCommitment(new Commitment("Next Week", new Date(113, 11, 24), "A commitment for next week (tomorrow)", 1, true));
		commitments.addCommitment(new Commitment("Next Month", new Date(113, 12, 23), "A commitment for next month", 1, true));
		commitments.addCommitment(new Commitment("Next Year", new Date(114, 1, 1), "A commitment for next year", 1, true));
		
		Date date = commitments.getCommitments().get(0).getDueDate();
		
		System.out.println("Date: " + date.getMonth() + "/" + date.getDate() + "/" + (date.getYear()+1900) );
		
		printlist(commitments.getCommitments());
		
	}
	
	@Test
	public void filterAroundTwoCalendars() {
		start.set(2013, Calendar.NOVEMBER, 18);
		end.set(2013, Calendar.NOVEMBER, 25);
		
		List<Commitment> newData = commitments.filter(start, end);
		
		printlist(newData);
		
		assertEquals(2, newData.size());
		
	}
	
	// Helper function
	public void printlist(List<Commitment> commits) {
		System.out.println("Commitments: ");
		
		for (Commitment com : commits) {
			System.out.println(com.getName());
		}
		
		System.out.println("\n");
	}
	
	public void printcalendar(Calendar cal) {
		String dayName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
		int dayNum = cal.get(cal.DAY_OF_MONTH);
		String monthName = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
		int year = cal.get(cal.YEAR);
		System.out.println(dayName + ", " + monthName + " " + dayNum + ", " + year);
	}*/
	
}
