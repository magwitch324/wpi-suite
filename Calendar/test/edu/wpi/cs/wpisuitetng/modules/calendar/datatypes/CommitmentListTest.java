package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import static org.junit.Assert.assertEquals;
import static java.util.Calendar.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;

public class CommitmentListTest {

	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */
	
	private static CommitmentList commitments;
	private GregorianCalendar start = new GregorianCalendar();
	private GregorianCalendar end   = new GregorianCalendar();
	
	private final static GregorianCalendar today = new GregorianCalendar(2013, NOVEMBER, 23);
	
	@BeforeClass
	public static void setup() {
		commitments = new CommitmentList();
		commitments.addCommitment(new Commitment("Last Year", new GregorianCalendar(2012, JANUARY, 30), "A commitment from last year", 1, true));
		commitments.addCommitment(new Commitment("Last Month", new GregorianCalendar(2013, OCTOBER, 12), "A commitment from last month", 1, true));
		commitments.addCommitment(new Commitment("Last Week", new GregorianCalendar(2013, NOVEMBER, 16), "A commitment for a week ago", 1, true));
		commitments.addCommitment(new Commitment("Today", today, "A commitment from today", 1, true));
		commitments.addCommitment(new Commitment("Next Week", new GregorianCalendar(2013, NOVEMBER, 24), "A commitment for next week (tomorrow)", 1, true));
		commitments.addCommitment(new Commitment("Next Month", new GregorianCalendar(2013, DECEMBER, 23), "A commitment for next month", 1, true));
		commitments.addCommitment(new Commitment("Next Year", new GregorianCalendar(2014, JANUARY, 1), "A commitment for next year", 1, true));
		
		printlist(commitments.getCommitments());
		
	}
	
	@Test
	public void filterAroundTwoCalendars() {
		start.set(2013, NOVEMBER, 18);
		end.set(2013, NOVEMBER, 25);
		
		List<Commitment> newData = commitments.filter(start, end);
//		printlist(newData);
		assertEquals(2, newData.size());
	}
	
	@Test
	public void filterAroundLargeSection() {
		start.set(2000, NOVEMBER, 12);
		end.set(2100, JULY, 31);
		List<Commitment> newData = commitments.filter(start, end);
		assertEquals(7, newData.size());
	}
	
	@Test
	public void filterAroundWeek() throws CalendarException {
		System.out.println("Filter around week...");
		List<Commitment> newData = commitments.filter(today, WEEK_OF_YEAR);
		assertEquals(1, newData.size());
	}
	
	@Test
	public void filterAroundMonth() throws CalendarException {
		System.out.println("Filter around month...");
		List<Commitment> newData = commitments.filter(today, MONTH);
		assertEquals(3, newData.size());
	}
	
	@Test
	public void filterAroundYear() throws CalendarException {
		System.out.println("Filter around year...");
		List<Commitment> newData = commitments.filter(today, YEAR);
		assertEquals(5, newData.size());
	}
	
	// Helper function
	public static void printlist(List<Commitment> commits) {
		System.out.println("Commitments: ");
		
		for (Commitment com : commits) {
			System.out.println(com.getName());
		}
		
		System.out.println("\n");
	}
	
	public void printcalendar(GregorianCalendar cal) {
		String dayName = cal.getDisplayName(GregorianCalendar.DAY_OF_WEEK, LONG, Locale.ENGLISH);
		int dayNum = cal.get(DAY_OF_MONTH);
		String monthName = cal.getDisplayName(GregorianCalendar.MONTH, LONG, Locale.ENGLISH);
		int year = cal.get(YEAR);
		System.out.println(dayName + ", " + monthName + " " + dayNum + ", " + year);
	}
	
}
