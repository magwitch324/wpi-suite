package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;

public class MyCalendar extends AbCalendar {
	
	private JCheckBox showteam;
	
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
		
		showcom = new JCheckBox("Show Commitments");
		showcom.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN, 14));
		showcom.setBackground(Color.WHITE);
		showcom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	setView();
            }
        });
		layout.putConstraint(SpringLayout.WEST, showcom, 30, SpringLayout.EAST, viewbtnpanel);
		layout.putConstraint(SpringLayout.NORTH, showcom, 0, SpringLayout.NORTH, viewbtnpanel);
		//layout.putConstraint(SpringLayout.EAST, showcom, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, showcom, 0, SpringLayout.SOUTH, viewbtnpanel);
		this.add(showcom);
		
		showteam = new JCheckBox("Show Team Data");
		showteam.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN, 14));
		showteam.setBackground(Color.WHITE);
		showteam.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	setView();
            }
        });
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
	@Override
	public void updateCalData() {
		if (CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName()) == null){
			CalendarData createdCal = new CalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName());
			CalendarDataModel.getInstance().addCalendarData(createdCal);
		}
		calData = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName());
		calView.displayCalData(calData);
	}
	
	@Override
	public boolean getShowTeamData(){
		return showteam.isSelected();
	}
	
}
