package edu.wpi.cs.wpisuitetng.modules.calendar.config;

import java.io.Serializable;
import java.util.Calendar;


public class CalendarConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1184439021320564736L;
	private Calendar date;
	public CalendarConfiguration() {
		
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}

}
