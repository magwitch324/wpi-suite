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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;

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
		JComponent commitmentPanel = ButtonsPanelCreate();
		layout.putConstraint(SpringLayout.NORTH, commitmentPanel, 5,
				SpringLayout.NORTH, viewbtnpanel);
		layout.putConstraint(SpringLayout.EAST, commitmentPanel, 0,
				SpringLayout.EAST, this);
		this.add(commitmentPanel);

		showcom = new JCheckBox("Show Commitments");
		showcom.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showcom.setFont(CalendarStandard.CalendarFont
				.deriveFont(Font.PLAIN, 14));
		showcom.setBackground(Color.WHITE);
		showcom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calData.setShowComm(showcom.isSelected());
				UpdateCalendarDataController.getInstance().updateCalendarData(calData);
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
				calData.setShowTeamData(showteam.isSelected());
				UpdateCalendarDataController.getInstance().updateCalendarData(calData);
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

		// setView();

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
		//set the comm list to the new data
		showcom.setSelected(calData.getShowComm());
		showteam.setSelected(calData.getShowTeamData());
		setCommList();
		setView();

	}

	protected void displayCalData() {
		// TODO Auto-generated method stub
		if (initialized) {
			calView.displayCalData(this.commitments, this.getShowCommitments());
		}
	}

	@Override
	public boolean getShowTeamData() {
		return showteam.isSelected();
	}

	/*
	 * Called when we need to update the list of commitments to be displayed for a celandar
	 * This happens at 2 different times:
	 * 		Initialization
	 *		Checking of show team data
	 */
	public void setCommList() {
		//if we dont have the caldata dont do anything
		if (initialized && getCalData() != null) {
			//System.out.println("got COMMITMENTS FOR VIEW");
			CombinedCommitmentList combinedList = new CombinedCommitmentList(
					new ArrayList<Commitment>(getCalData()
							.getCommitments().getCommitments()));
			CalendarData teamData = CalendarDataModel.getInstance()
					.getCalendarData(ConfigManager.getConfig().getProjectName());

			//if we are supposed to show team data, we need to put the team commitments into the list in the right order
			if (getShowTeamData()) {
				// Iterate through team commitments and add each element to
				// combinedList

				for (int i = 0; i < teamData.getCommitments()
						.getCommitments().size(); i++) {
					combinedList.addCommitment(teamData.getCommitments()
							.getCommitments().get(i));
				}
				commitments = combinedList;
			}

			//if we are not supposed to show team data the CommitmentList should just be straight from the personal data
			else {
				commitments = getCalData().getCommitments();
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
				GetCalendarDataController.getInstance().retrieveCalendarData();
				preInitialized = true;
				System.out.println("retrieved on initialization2");
			}
			catch (Exception e)
			{

			}
		}
		else{
			//calView.refresh();
		}
		//System.out.println("repainting!!!!!!!!!!!!!!!");
		super.paintComponent(g);
	}

}
