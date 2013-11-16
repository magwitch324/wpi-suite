package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class TeamCalendar extends JPanel implements ICalendar {
	
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
	
	public TeamCalendar() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		
		JToggleButton DayButton = new JToggleButton("Day");
		JToggleButton WeekButton = new JToggleButton("Week");
		JToggleButton MonthButton = new JToggleButton("Month");
		JToggleButton YearButton = new JToggleButton("Year");
		JButton ForwardButton = new JButton(">>");
		JButton BackwardButton = new JButton("<<");
		JButton TodayButton = new JButton("Today");
		JComboBox FilterChoices = new JComboBox();
		
		top.add(DayButton);
		top.add(WeekButton);
		top.add(MonthButton);
		top.add(YearButton);
		top.add(BackwardButton);
		top.add(TodayButton);
		top.add(ForwardButton);
		top.add(FilterChoices);
		
		add(top);
		
		Calendar date = Calendar.getInstance();
		date.set(2013, Calendar.NOVEMBER, 16);
		
		//temp. example view
		CalendarView view = new DayView(date);
		view.setAlignmentX(CENTER_ALIGNMENT);
		add(view); 
		
		
		
		
	}
	
	public JComponent getComponent() {
		return name; 
	}
	
	
	
	
	
}
