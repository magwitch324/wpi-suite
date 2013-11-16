package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class TeamCalender implements ICalender {
	
	protected enum types {
		DAY(0),
		WEEK(1),
		MONTH(2),
		YEAR(3);
	
		private int currentType;
		
		private types(int currentType) {
			this.currentType = currentType;
		}
		
		public int getCurrentType() {
			return currentType;
		}
	}
	
	types currrenttype = types.DAY;
	
	JPanel name = new JPanel(); 
	
	public TeamCalender() {
		JToggleButton DayButton = new JToggleButton("Day");
		JToggleButton WeekButton = new JToggleButton("Week");
		JToggleButton MonthButton = new JToggleButton("Month");
		JToggleButton YearButton = new JToggleButton("Year");
		
		JButton ForwardButton = new JButton(">>");
		JButton BackwardButton = new JButton("<<");
		JButton TodayButton = new JButton("Today");
		
		JComboBox FilterChoices = new JComboBox();
	}
	
	public JComponent getComponent() {
		return name; 
	}
	
	
	
	
	
}
