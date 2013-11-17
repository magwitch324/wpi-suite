package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

public class MyCalendar extends TeamCalendar {
	
	public MyCalendar() {
		super();
		this.mycal = (Calendar) super.mycal.clone();
	}
	
}
