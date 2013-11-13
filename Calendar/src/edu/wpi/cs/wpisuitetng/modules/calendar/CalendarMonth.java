package edu.wpi.cs.wpisuitetng.modules.calendar;

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

import net.miginfocom.swing.MigLayout;

public class CalendarMonth implements ICalenderView{

	private JPanel monthPanel;
	private JPanel2[] dayPanels;
	private JLabel[] dayLabels;
	private int startDay = 5;
	private int currentDay = 1;
	private int maxMonthDays = 31;
	
	public CalendarMonth() {
		monthPanel = new JPanel();
		monthPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		dayPanels = new JPanel2[42];
		dayLabels = new JLabel[31];
		int x = 0;
		int y = 0;
		for(int i = 0; i<42; i++){
			dayPanels[i] = new JPanel2();
			dayPanels[i].setBackground(Color.GRAY);
			if(startDay == i && currentDay <= maxMonthDays){
				dayLabels[currentDay-1] = new JLabel(String.valueOf(currentDay), SwingConstants.LEFT);
				dayPanels[i].add(dayLabels[currentDay-1]);
				dayPanels[i].setBackground(Color.WHITE);
				dayPanels[i].setTag(currentDay);
				startDay++;
				currentDay++;
			}
			
			dayPanels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			//dayPanels[i].setLayout();
			//dayPanels[i].setSize(600, 1200);
		
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0;
			c.gridx = x;
			c.gridy = y;
			c.insets = new Insets(5,5,5,5);
			c.ipadx = 160;
			c.ipady = 75;
			monthPanel.add(dayPanels[i],c);
			
			if(x < 6){
				x++;
			}
			else{
				x = 0;
				y++;
			}
		}
		
	}
	
	
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

		   // usually better off with mousePressed rather than clicked
		   public void mousePressed(MouseEvent e) {
		       JPanel2 panel = (JPanel2)e.getSource();
		       switch(panel.getTag()){
		       case 1:
		       
		       }
		       }
		   }
	

}
