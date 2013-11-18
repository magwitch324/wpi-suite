package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SpinnerDateModel;

/**
 * @author sfp
 * SpinnerDateModel Increments time value by 30 minutes
 */
public class SpinnerDateModelHalfHour extends SpinnerDateModel {


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
	      Calendar cal = new GregorianCalendar();
	      cal.setTime((Date)super.getValue());
	      cal.add(Calendar.MINUTE, 30);  
	      super.setValue(cal.getTime());
	      return super.getValue();  
	   }  
	   public Object getPreviousValue()  
	   {  
		  Calendar cal = new GregorianCalendar();
	      cal.setTime((Date)super.getValue());
	      cal.add(Calendar.MINUTE, -30);  
	      super.setValue(cal.getTime());
	      return super.getValue();// substract 15 minutes  
	   }  
	   public Object getValue()
	   {
		   return super.getValue();
	   }
	   public void setValue(Date newTime)
	   {
		   if ((newTime == null) || !(newTime instanceof Date)) {
	            throw new IllegalArgumentException("illegal value");
	        }
		   if (!newTime.equals(super.getValue())) {
			   super.setValue(newTime);
	           fireStateChanged();
		   }
	   }

}
