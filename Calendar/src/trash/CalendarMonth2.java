package trash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import net.miginfocom.swing.MigLayout;

public class CalendarMonth2 implements ICalenderView{

	private JPanel monthPanel;
	private JPanel2[] dayPanels;
	private JLabel[] dayLabels;
	private JLabel monthName;
	private int startDay;
	private int currentDay = 1;
	private int maxMonthDays;
	
	public CalendarMonth2(String month, int startDay, int maxMonthDays) {
		
		this.startDay = startDay;
		this.maxMonthDays = maxMonthDays;
		monthName = new JLabel(month);
		
		/**creating an adapter for clicking dayPanels*/
		MouseAdapter ma = new MouseAdapterMod();
		
		/**monthPanel will be the mainPanel returned by this object*/
		monthPanel = new JPanel();
		
		monthPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 3;
		c.gridy = 0;
		monthPanel.add(monthName, c);
		/**dayPanels consist of 42 panel objects to complete the 6-row 7-column setup*/
		dayPanels = new JPanel2[42];
		
		/**maxMonthDays sets the about of labeled days to the amount of the current month*/
		dayLabels = new JLabel[maxMonthDays];
		
		/**column and row variables which handles setting up the calendar layout*/
		int x = 0;
		int y = 1;
		
		/**looping to setup all 42 dayPanels with at least default setting and sizes*/
		for(int i = 0; i<42; i++){
			dayPanels[i] = new JPanel2();
			dayPanels[i].setBackground(Color.GRAY);
			
			/** Loop for modifying day panels into select-able days of current month */
			if(startDay == i && currentDay <= maxMonthDays){
				dayLabels[currentDay-1] = new JLabel(String.valueOf(currentDay), SwingConstants.LEFT);
				dayPanels[i].add(dayLabels[currentDay-1]);
				dayPanels[i].setBackground(Color.WHITE);
				dayPanels[i].setTag(currentDay);
				dayPanels[i].addMouseListener(ma);
				startDay++;
				currentDay++;
			}
			/**sets the label to the top left of the dayPanel*/
			dayPanels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			
		    /**gridBagConstraints which setup the month calendar layout settings*/
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.gridx = x;
			c.gridy = y;
			
			/**padding setting for top, left, bottom, and right*/
			c.insets = new Insets(5,5,5,5);
			
			/**pads set size of dayPanels and can/should be modified to make them dynamic*/
			c.ipadx = 160; 
			c.ipady = 75;
			
			/**adds each day to the main monthPanel*/
			monthPanel.add(dayPanels[i],c);
			
			/**if column position is not at the end of the week move to next day in current row*/
			if(x < 6){
				x++;
			}
			/**else move down a row and go back to first column*/
			else{
				x = 0;
				y++;
			}
		}
		
	}
	
	//interface
	@Override
	public JPanel getCalPanel() {
		// TODO Auto-generated method stub
		return monthPanel;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void goToDate(Date adate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void skipForward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skipBackward() {
		// TODO Auto-generated method stub
		
	}

	
	class MouseAdapterMod extends MouseAdapter {

		   /**Handles selecting a dayPanel and calls the dayCalendar object*/
		   public void mousePressed(MouseEvent e) {
		       JPanel2 panel = (JPanel2)e.getSource();
		       if(panel.getTag() != -1){
		    	  // GUIEventController.getInstance().showMonthView();
		    	   System.out.println(panel.getTag());
		       }
		   }
	}
	

}
