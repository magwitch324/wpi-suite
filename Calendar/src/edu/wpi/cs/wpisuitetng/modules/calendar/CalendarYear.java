package edu.wpi.cs.wpisuitetng.modules.calendar;

import java.awt.GridLayout;

import javax.swing.JPanel;


public class CalendarYear extends JPanel {
	
	private CalendarMonth[] arrayOfMonth;
	public CalendarYear(int year) {

		this.setLayout(new GridLayout(3, 4));
		
		for (int i = 0; i < 12; i++) {
			CalendarMonth c = new CalendarMonth("jan", 3, 31);
			this.add(c.getCalPanel());
			
		}
	}
	
	
	
	
	
}
