package edu.wpi.cs.wpisuitetng.modules.calendar.GUI;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import static java.util.Calendar.*;

public class YearViewHeatMapTest {

	boolean run = true;
	
	/** 
	 * This code adds many commitments to the database
	 * -- Insert this to the CommitmentTab where you add new events.
	 * Really, nothing should ever run this class. It's just a place to save a script
	 */
	
	public void setup() {
		CalendarData calData = CalendarDataModel.getInstance().getCalendarData("test");
		
		
		// The script to add a bunch of commitments
		GregorianCalendar day = new GregorianCalendar(2013, JANUARY, 1, 12, 00, 00);
		GregorianCalendar lastDay = new GregorianCalendar();
		lastDay.setTime(day.getTime());
		lastDay.add(1, YEAR);
		
		while (day.before(lastDay)) {
			Commitment newCommitment = new Commitment("Test", day, "Test Description", 0, false);
			calData.addCommitment(newCommitment);
			day.add(1, DAY_OF_YEAR);
		}
		
	}
	
}
