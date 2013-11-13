package edu.wpi.cs.wpisuitetng.modules.calendar;

import java.util.Date;

import javax.swing.JPanel;

public interface ICalenderView{
	//returns the jpanel on which a calendar view is to be created
	JPanel getCalPanel();
	//returns the name given to a certain calendar view
	String getName();
	//Go to a specified date in the calendar view
	void goToDate(Date adate);
	//Get the current selected date in a calendar view
	Date getDate();
	//Moves forward by one iteration of the current view
	void skipForward();
	//Moves backward by one iteration of the current view
	void skipBackward();
	
	}
