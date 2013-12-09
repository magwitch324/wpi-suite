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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetPropsController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropsModel;

@SuppressWarnings("serial")
public class MyCalendar extends AbCalendar {

	private boolean preInitialized;
	private JCheckBox showteam;

	public MyCalendar() {
		super();
		preInitialized = false;
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
		
		// Displays Commitment Button
//		JComponent commitmentPanel = ButtonsPanelCreate();
//		layout.putConstraint(SpringLayout.NORTH, commitmentPanel, 5,
//				SpringLayout.NORTH, viewbtnpanel);
//		layout.putConstraint(SpringLayout.EAST, commitmentPanel, 0,
//				SpringLayout.EAST, this);
//		this.add(commitmentPanel);

		showcom = new JCheckBox("Show Commitments");
		showcom.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showcom.setFont(CalendarStandard.CalendarFont
				.deriveFont(Font.PLAIN, 14));
		showcom.setBackground(Color.WHITE);
		showcom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setMyShowComm(showcom.isSelected());
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

		
		showteam = new JCheckBox("Show Team Data");
		showteam.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showteam.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN,
				14));
		showteam.setBackground(Color.WHITE);
		showteam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setShowTeamData(showteam.isSelected());
				//update the commitments to either include or not include team data
				updateCalData();
				setView();
			}
		});
		layout.putConstraint(SpringLayout.WEST, showteam, 5, SpringLayout.EAST,
				showcom);
		layout.putConstraint(SpringLayout.NORTH, showteam, 0,
				SpringLayout.NORTH, viewbtnpanel);
		// layout.putConstraint(SpringLayout.EAST, showteam, -5,
		// SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, showteam, 0,
				SpringLayout.SOUTH, viewbtnpanel);
		this.add(showteam);
	
		
	
		// COMMENTED THIS FILTER MENU OUT FOR THE TIME BEING
		/*JComboBox filter = new JComboBox();
		layout.putConstraint(SpringLayout.WEST, filter, 30, SpringLayout.EAST,
				showteam);
		layout.putConstraint(SpringLayout.NORTH, filter, 0, SpringLayout.NORTH,
				viewbtnpanel);
		layout.putConstraint(SpringLayout.EAST, filter, -5, SpringLayout.EAST,
				this);
		layout.putConstraint(SpringLayout.SOUTH, showcom, 0,
				SpringLayout.SOUTH, viewbtnpanel);
		this.add(filter);
*/
		
		
		
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

		setView();

	}

	@Override
	public void updateCalData() {
		//if we are initializing check for the data and set initialized to true
		if (!initialized){
			//check if the personal cal data exists, if not create it
			if (CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + "-"
							+ ConfigManager.getConfig().getUserName()) == null) {
				CalendarData createdCal = new CalendarData(ConfigManager
						.getConfig().getProjectName()
						+ "-"
						+ ConfigManager.getConfig().getUserName());
				CalendarDataModel.getInstance().addCalendarData(createdCal);
			}



			initialized = true;
		}
		calData = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName());

		setCommEventList();
		setView();

	}

	protected void displayCalData() {
		if (initialized) {
			calView.displayCalData(this.events, this.commitments, this.getShowCommitments());
		}
	}

	@Override
	public boolean getShowTeamData() {
		return showteam.isSelected();
	}

	/**
	 * Called when we need to update the list of commitments to be displayed for a calendar
	 * This happens at 2 different times:
	 * 		Initialization
	 *		Checking of show team data
	 */
	public void setCommEventList() {
		//if we dont have the caldata dont do anything
		if (initialized && getCalData() != null) {
			//create a combined Commitment list
			CombinedCommitmentList combinedCommList = new CombinedCommitmentList(
					new ArrayList<Commitment>(getCalData()
							.getCommitments().getCommitments()));
			//create a combined event list
			CombinedEventList combinedEventList = new CombinedEventList(
					new ArrayList<Event>(getCalData()
							.getEvents().getEvents()));
			//get the team data
			CalendarData teamData = CalendarDataModel.getInstance()
					.getCalendarData(ConfigManager.getConfig().getProjectName());

			//if we are supposed to show team data, we need to put the team commitments into the list in the right order
			if (getShowTeamData()) {
				// Iterate through team commitments and add each element to
				// combinedList
				int j = teamData.getCommitments().getCommitments().size() - 1;
				for (int i = j; i >= 0; i--) {
					combinedCommList.add(teamData.getCommitments()
							.getCommitments().get(i));
				}
				commitments = combinedCommList;
				
				// Iterate through team events and add each element to
				// combinedEventList
				j = teamData.getEvents().getEvents().size() - 1;
				for (int i = j; i >= 0; i--) {
					combinedEventList.add(teamData.getEvents()
							.getEvents().get(i));
				}
				events = combinedEventList;
				
			}

			//if we are not supposed to show team data the CommitmentList should just be straight from the personal data
			else {
				commitments = getCalData().getCommitments();
				events = getCalData().getEvents();
			}
		}
	}
	
	/**
	 * Overrides the paintComponent method to retrieve the requirements on the first painting.
	 * 
	 * @param g	The component object to paint
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		if(!preInitialized)
		{
			try 
			{
				GetPropsController.getInstance().retrieveCalendarProps();
				GetCalendarDataController.getInstance().retrieveCalendarData();
				preInitialized = true;
				System.out.println("retrieved on initialization2");
			}
			catch (Exception e)
			{

			}
		}
		super.paintComponent(g);
	}
	
	/**
	 * Used after cal props has been fetched from the server.
	 */
	protected void applyCalProps(){
		
		//check if the personal cal props exists, if not create it
		if (CalendarPropsModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS") == null) {
			CalendarProps createdProps = new CalendarProps(ConfigManager
					.getConfig().getProjectName()
					+ "-"
					+ ConfigManager.getConfig().getUserName() + "-PROPS");
			CalendarPropsModel.getInstance().addCalendarProps(createdProps);
		}
		
		
		calProps = CalendarPropsModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS");
		//set the comm list to the new data
		showcom.setSelected(calProps.getMyShowComm());
		showteam.setSelected(calProps.getShowTeamData());
		
	}

	@Override
	protected void updateCommPane() {
		if (initialized) {
			calView.updateCommPane(this.commitments, this.getShowCommitments());
		}
		
	}

}
