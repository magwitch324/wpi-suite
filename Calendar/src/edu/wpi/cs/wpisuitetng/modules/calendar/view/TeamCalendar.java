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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropsModel;

@SuppressWarnings("serial")
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
	
	public void setCommEventList() {
		//if we dont have the caldata dont do anything
		if (initialized && getCalData() != null) {
			//create a combined Commitment list
			CombinedCommitmentList combinedCommList = new CombinedCommitmentList(
					new ArrayList<Commitment>(getCalData()
							.getCommitments().getCommitments()));
			//create a combined event list
			CombinedEventList combinedEventList = getCalData()
					.getRepeatingEvents().toCombinedEventList();
			for (int i = 0; i < getCalData().getEvents()
					.getEvents().size(); i++) {
				combinedEventList.add(getCalData().getEvents()
						.getEvents().get(i));
			}
			
			//get the team data
			CalendarData teamData = CalendarDataModel.getInstance()
					.getCalendarData(ConfigManager.getConfig().getProjectName());

			//if we are supposed to show team data, we need to put the team commitments into the list in the right order
			if (getShowTeamData()) {
				// Iterate through team commitments and add each element to
				// combinedList

				for (int i = 0; i < teamData.getCommitments()
						.getCommitments().size(); i++) {
					combinedCommList.add(teamData.getCommitments()
							.getCommitments().get(i));
				}
				commitments = combinedCommList;
				
				// Iterate through team events and add each element to
				// combinedEventList
				for (int i = 0; i < teamData.getEvents()
						.getEvents().size(); i++) {
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
		
		setCommEventList();
		setView();
		//		displayCalData();

	}

	protected void displayCalData() {
		// TODO Auto-generated method stub
		if(initialized){
			calView.displayCalData(calData.getEvents(), calData.getCommitments(), getShowCommitments());
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

	@Override
	protected void updateCommPane() {
		if(initialized){
			calView.updateCommPane(calData.getCommitments(), getShowCommitments());
		}
		
	}
}
