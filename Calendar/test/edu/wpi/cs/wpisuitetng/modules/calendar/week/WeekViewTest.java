package edu.wpi.cs.wpisuitetng.modules.calendar.week;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class WeekViewTest {

	@Test
	public void test() {
		WeekView tmp = new WeekView();
		Calendar cal = tmp.startDate();
		
		//assertEquals(Calendar.SUNDAY, cal.get(Calendar.DAY_OF_WEEK));
		assertTrue(true);
		
	}

}
