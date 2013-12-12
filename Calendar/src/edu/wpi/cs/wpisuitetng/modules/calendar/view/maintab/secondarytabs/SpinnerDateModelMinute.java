/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SpinnerDateModel;

/**
 * SpinnerDateModel Increments time value by 30 minutes
 */
public class SpinnerDateModelMinute extends SpinnerDateModel {


	public SpinnerDateModelMinute() {
		// TODO Auto-generated constructor stub
		super(new Date(0), null, null, Calendar.MINUTE);
	}

	public SpinnerDateModelMinute(Date value, Comparable start,
			Comparable end, int calendarField) {
		super(value, start, end, calendarField);
		// TODO Auto-generated constructor stub
	}
	
	public Object getNextValue()  
	   {  
		  final Calendar cal = new GregorianCalendar();
	      cal.setTime((Date)super.getValue());
	      cal.add(Calendar.MINUTE, 1);  
	      super.setValue(cal.getTime());
	      return super.getValue();  
	   }  
	   public Object getPreviousValue()  
	   {  
		  final Calendar cal = new GregorianCalendar();
	      cal.setTime((Date)super.getValue());
	      cal.add(Calendar.MINUTE, -1);  
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
