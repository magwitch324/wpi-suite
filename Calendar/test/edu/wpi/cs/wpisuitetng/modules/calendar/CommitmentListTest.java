package edu.wpi.cs.wpisuitetng.modules.calendar;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
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
	
	@SuppressWarnings("deprecation")
	@Before
	public void setup() {
		commitments = new CommitmentList();
		commitments.addCommitment(new Commitment("Last Year", new Date(2012, 1, 30), "A commitment from last year", 1));
		commitments.addCommitment(new Commitment("Last Month", new Date(2013, 10, 12), "A commitment from last month", 1));
		commitments.addCommitment(new Commitment("Last Week", new Date(2013, 11, 16), "A commitment for a week ago", 1));
		commitments.addCommitment(new Commitment("Today", new Date(2013, 11, 23), "A commitment from today", 1));
		commitments.addCommitment(new Commitment("Next Week", new Date(2013, 11, 24), "A commitment for next week (tomorrow)", 1));
		commitments.addCommitment(new Commitment("Next Month", new Date(2013, 12, 23), "A commitment for next month", 1));
		commitments.addCommitment(new Commitment("Next Year", new Date(2014, 1, 1), "A commitment for next year", 1));
		
	}
	
	@Test
	public void filterAroundTwoCalendars() {
		start.set(2013, Calendar.NOVEMBER, 18);
		end.set(2013, Calendar.NOVEMBER, 25);
		
		List<Commitment> newData = commitments.filter(start, end);
		assertEquals(2, newData.size());
		
	}
	
}
