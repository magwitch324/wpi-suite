/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdatePropsController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropsModel;

public class TeamCalendar extends AbCalendar {

	public TeamCalendar() {
		super();

	}

	protected void drawThis() {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		JComponent viewbtnpanel = getViewButtonPanel();
		layout.putConstraint(SpringLayout.WEST, viewbtnpanel, 5,
				SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewbtnpanel, 5,
				SpringLayout.NORTH, this);
		// layout.putConstraint(SpringLayout.EAST, viewbtnpanel, -30,
		// SpringLayout.HORIZONTAL_CENTER, this);
		this.add(viewbtnpanel);

		JComponent datepanel = getDatePanel();
		layout.putConstraint(SpringLayout.NORTH, datepanel, 5,
				SpringLayout.SOUTH, viewbtnpanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, datepanel, 0,
				SpringLayout.HORIZONTAL_CENTER, this);
		this.add(datepanel);

		showcom = new JCheckBox("Show Commitments");
		showcom.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showcom.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN, 14));
		showcom.setBackground(Color.WHITE);
		showcom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setTeamShowComm(showcom.isSelected());
				setView();
			}
		});

		layout.putConstraint(SpringLayout.WEST, showcom, 30, SpringLayout.EAST,
				viewbtnpanel);
		layout.putConstraint(SpringLayout.NORTH, showcom, 0,
				SpringLayout.NORTH, viewbtnpanel);
		// layout.putConstraint(SpringLayout.EAST, showcom, -5,
		// SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, showcom, 0,
				SpringLayout.SOUTH, viewbtnpanel);
		this.add(showcom);

		// Displays Commitment Button
//		JComponent commitmentPanel = ButtonsPanelCreate();
//		layout.putConstraint(SpringLayout.NORTH, commitmentPanel, 5,
//				SpringLayout.NORTH, viewbtnpanel);
//		layout.putConstraint(SpringLayout.EAST, commitmentPanel, 0,
//				SpringLayout.EAST, this);
//		this.add(commitmentPanel);
//		

		//COMENTED OUT FILTER DROP DOWN MENU BECAUSE IT DOESN'T DO ANYTHING AT THE MOMENT

		JComboBox filter = new JComboBox();
		layout.putConstraint(SpringLayout.WEST, filter, 30, SpringLayout.EAST,
				showcom);
		layout.putConstraint(SpringLayout.NORTH, filter, 0, SpringLayout.NORTH,
				viewbtnpanel);
		layout.putConstraint(SpringLayout.EAST, filter, -5, SpringLayout.EAST,
				this);
		layout.putConstraint(SpringLayout.SOUTH, showcom, 0,
				SpringLayout.SOUTH, viewbtnpanel);
		//		this.add(filter);

		layout.putConstraint(SpringLayout.WEST, viewpanel, 5,
				SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewpanel, 5,
				SpringLayout.SOUTH, datepanel);
		layout.putConstraint(SpringLayout.EAST, viewpanel, -5,
				SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, viewpanel, -5,
				SpringLayout.SOUTH, this);

		this.add(viewpanel);
		viewbtns[currenttype.getCurrentType()].setSelected(true);

		//		setView();


	}

	public boolean getShowTeamData(){
		return false;
	}

	public void updateCalData() {
		if(!initialized){
			if (CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()) == null) {
				CalendarData createdCal = new CalendarData(ConfigManager
						.getConfig().getProjectName());
				CalendarDataModel.getInstance().addCalendarData(createdCal);
			}
			
			initialized = true;
		}
		calData = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName());

		setView();
		//		displayCalData();

	}

	protected void displayCalData() {
		// TODO Auto-generated method stub
		if(initialized){
			calView.displayCalData(calData.getCommitments(), getShowCommitments());
		}
	}
	
	

	/**
	 * Used after cal props has been fetched from the server.
	 */
	protected void applyCalProps(){
		
		calProps = CalendarPropsModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS");
		showcom.setSelected(calProps.getTeamShowComm());
	}
}
