package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SpringLayout;

public class MyCalendar extends TeamCalendar {
	
	public MyCalendar() {
		super();
		this.removeAll();
		this.drawThis();
	}
	
	protected void drawThis(){
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		JComponent viewbtnpanel = getViewButtonPanel();
		layout.putConstraint(SpringLayout.WEST, viewbtnpanel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewbtnpanel, 5, SpringLayout.NORTH, this);
		//layout.putConstraint(SpringLayout.EAST, viewbtnpanel, -30, SpringLayout.HORIZONTAL_CENTER, this);
		this.add(viewbtnpanel);
		
		JComponent datepanel = getDatePanel();
		layout.putConstraint(SpringLayout.NORTH, datepanel, 5, SpringLayout.SOUTH, viewbtnpanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, datepanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		this.add(datepanel);
		
		JCheckBox showcom = new JCheckBox("Show Commitments");
		showcom.setFont(defualtfont);
		layout.putConstraint(SpringLayout.WEST, showcom, 30, SpringLayout.EAST, viewbtnpanel);
		layout.putConstraint(SpringLayout.NORTH, showcom, 0, SpringLayout.NORTH, viewbtnpanel);
		//layout.putConstraint(SpringLayout.EAST, showcom, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, showcom, 0, SpringLayout.SOUTH, viewbtnpanel);
		this.add(showcom);
		
		JCheckBox showteam = new JCheckBox("Show Team");
		showteam.setFont(defualtfont);
		layout.putConstraint(SpringLayout.WEST, showteam, 5, SpringLayout.EAST, showcom);
		layout.putConstraint(SpringLayout.NORTH, showteam, 0, SpringLayout.NORTH, viewbtnpanel);
		//layout.putConstraint(SpringLayout.EAST, showteam, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, showteam, 0, SpringLayout.SOUTH, viewbtnpanel);
		this.add(showteam);
		
		JComboBox filter = new JComboBox();
		layout.putConstraint(SpringLayout.WEST, filter, 30, SpringLayout.EAST, showteam);
		layout.putConstraint(SpringLayout.NORTH, filter, 0, SpringLayout.NORTH, viewbtnpanel);
		layout.putConstraint(SpringLayout.EAST, filter, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, showcom, 0, SpringLayout.SOUTH, viewbtnpanel);
		this.add(filter);
		
		layout.putConstraint(SpringLayout.WEST, viewpanel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewpanel, 5, SpringLayout.SOUTH, datepanel);
		layout.putConstraint(SpringLayout.EAST, viewpanel, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, viewpanel, -5, SpringLayout.SOUTH, this);
		
		this.add(viewpanel);
		viewbtns[currenttype.getCurrentType()].setSelected(true);
		
		setView();
		
	}
	
}
