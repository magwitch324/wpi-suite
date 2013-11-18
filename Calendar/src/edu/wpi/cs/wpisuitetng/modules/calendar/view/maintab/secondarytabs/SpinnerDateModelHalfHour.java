package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SpinnerDateModel;

/**
 * @author sfp
 * SpinnerDateModel that increments by 30 minutes
 */
public class SpinnerDateModelHalfHour extends SpinnerDateModel {

	private Calendar cal = new GregorianCalendar(); 

	public SpinnerDateModelHalfHour() {
		// TODO Auto-generated constructor stub
		super(new Date(0), null, null, Calendar.MINUTE);
	}

	public SpinnerDateModelHalfHour(Date value, Comparable start,
			Comparable end, int calendarField) {
		super(value, start, end, calendarField);
		// TODO Auto-generated constructor stub
	}
	
	public Object getNextValue()  
	   {  
	      cal.add(Calendar.MINUTE, 30);  
	      return cal.getTime();  
	   }  
	   public Object getPreviousValue()  
	   {  
		  cal.add(Calendar.MINUTE, -30);
	      return cal.getTime();// substract 15 minutes  
	   }  
	   public Object getValue()
	   {
		   return cal.getTime();
	   }
	   public void setValue(Date newTime)
	   {
		   cal.setTime(newTime);
	   }

}
