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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.GetPropsController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProperties;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropertiesModel;


/**
 * My calendar tab extends abstract calendar.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
 @SuppressWarnings("serial")

public class MyCalendar extends AbCalendar {
	JRadioButton myCalendar;
	JRadioButton teamCalendar;
	JRadioButton bothCalendar;
	private boolean preInitialized;
	private JCheckBox showteam;
	JComboBox<Filter> filterComboBox;
	Filter noneFilter;
	/**
	 * Constructor for MyCalendar.
	 */
	public MyCalendar() {
		preInitialized = false;
	}

	protected void drawThis() {
		final SpringLayout layout = new SpringLayout();
		setLayout(layout);

		final JComponent viewbtnpanel = getViewButtonPanel();
		layout.putConstraint(SpringLayout.WEST, viewbtnpanel, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewbtnpanel, 5, SpringLayout.NORTH, this);
		this.add(viewbtnpanel);

		final JComponent dateswitchpanel = getDatePanel();
		layout.putConstraint(SpringLayout.NORTH, dateswitchpanel, 
				0, SpringLayout.SOUTH, viewbtnpanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dateswitchpanel, 
				0, SpringLayout.HORIZONTAL_CENTER, this);
		this.add(dateswitchpanel);

		final JComponent datapanel = getDataDisplayPanel();
		layout.putConstraint(SpringLayout.NORTH, datapanel, 0, SpringLayout.NORTH, viewbtnpanel);
		layout.putConstraint(SpringLayout.WEST, datapanel, 30, SpringLayout.EAST, viewbtnpanel);
		layout.putConstraint(SpringLayout.SOUTH, datapanel, 0, SpringLayout.SOUTH, viewbtnpanel);
		this.add(datapanel);

		filterComboBox = new JComboBox<Filter>();
		final JLabel filterLabel = new JLabel("Filter: ");
		filterLabel.setFont(CalendarStandard.CalendarFont);
		layout.putConstraint(SpringLayout.WEST, filterLabel, 30, SpringLayout.EAST, datapanel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, filterLabel, 0, SpringLayout.VERTICAL_CENTER, datapanel);
		add(filterLabel);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, filterComboBox, 
				0, SpringLayout.VERTICAL_CENTER, datapanel);
		layout.putConstraint(SpringLayout.WEST, filterComboBox, 5, SpringLayout.EAST, filterLabel);
		layout.putConstraint(SpringLayout.EAST, filterComboBox, -5, SpringLayout.EAST, this);
		filterComboBox.setMaximumSize(new Dimension(20, 20));
		filterComboBox.setBackground(CalendarStandard.CalendarYellow);
		filterComboBox.setToolTipText("Select Filters");
		filterComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCalData();
			}
		});
		this.add(filterComboBox);




		layout.putConstraint(SpringLayout.WEST, viewpanel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewpanel, 5, SpringLayout.SOUTH, dateswitchpanel);
		layout.putConstraint(SpringLayout.EAST, viewpanel, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, viewpanel, -5, SpringLayout.SOUTH, this);

		this.add(viewpanel);
		viewbtns[currenttype.getCurrentType()].setSelected(true);

		setView();
		
		//update the filter information
		noneFilter = new Filter();
		noneFilter.setID(0);
		noneFilter.setName("No Filter");
		

	}
	
	
	/**
	 * Updates the filter list in the FilterComboBox
	 */
	protected void updateFilterList(){
		
		final int selectedFilter;
		final Filter test = new Filter();
		test.setName("a test filter");
		test.setID(100);
		test.getActiveTeamCategories().add(1);

		if(filterComboBox.getSelectedItem() != null){
			selectedFilter = ((Filter) filterComboBox.getSelectedItem()).getID();
		} else {
			selectedFilter = 0;
		}


		//removes the current data from the ComboBox
		filterComboBox.removeAllItems();



		//adds the "none" filter
		filterComboBox.addItem(noneFilter);
		filterComboBox.addItem(test);

		// gets Caldata

		//extracts the filter list
		final List<Filter> filters = myCalData.getFilters().getFilters();

		//adds the categories to the comboBox
		for (Filter filter:filters){
			filterComboBox.addItem(filter);
		}

		if(selectedFilter != 0){
			filterComboBox.setSelectedItem(myCalData.getFilters().getFilter(selectedFilter));
		}
		
	}

	@Override
	public void updateCalData() {
		boolean startup = false;
		//if we are initializing check for the data and set initialized to true
		if (!initialized){
			//check if the personal cal data exists, if not create it
			if (CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName() + "-"
							+ ConfigManager.getConfig().getUserName()) == null) {
				final CalendarData createdCal = new CalendarData(ConfigManager
						.getConfig().getProjectName()
						+ "-"
						+ ConfigManager.getConfig().getUserName());
				CalendarDataModel.getInstance().addCalendarData(createdCal);
			}
			//check if the team calendar data exists, if not create it
			if (CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()) == null) {
				final CalendarData createdCal = new CalendarData(ConfigManager
						.getConfig().getProjectName());
				CalendarDataModel.getInstance().addCalendarData(createdCal);
			}

			startup = true;
			initialized = true;

		}
		myCalData = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName());

		teamCalData = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName());

		setCommEventList();
		setView();
		if(startup){
			//used to check for and remove old data. runs only on startup
			myCalData.removeYearOld();
			UpdateCalendarDataController.getInstance().updateCalendarData(myCalData);
			teamCalData.removeYearOld();
			UpdateCalendarDataController.getInstance().updateCalendarData(teamCalData);
			
			updateFilterList();
		}

	}

	protected void displayCalData() {
		if (initialized) {
			calView.displayCalData(events, commitments, this.getShowCommitments());
			calView.applyCalProps(calProps);
		}
	}

	/**
	 * Called when we need to update the list of commitments to be displayed for a calendar
	 * This happens at 2 different times:
	 * 		Initialization
	 *		Checking of show team data
	 */
	public void setCommEventList() {
		//if we dont have the caldata dont do anything
		if (initialized && getMyCalData() != null && getTeamCalData() != null) {

			
			
			CombinedCommitmentList combinedCommList;
			CombinedEventList combinedEventList;
			//If we are supposed to display just my calendar data
			if(myCalendar.isSelected() || bothCalendar.isSelected()){
				//create a combined Commitment list
				combinedCommList = new CombinedCommitmentList(
						new ArrayList<Commitment>(getMyCalData()
								.getCommitments().getCommitments()));
				//create a combined event list
				combinedEventList = getMyCalData()
						.getRepeatingEvents().toCombinedEventList();
				for (int i = 0; i < getMyCalData().getEvents()
						.getEvents().size(); i++) {
					combinedEventList.add(getMyCalData().getEvents()
							.getEvents().get(i));
				}


				//if we are supposed to show team data, 
				//we need to put the team commitments into the list in the right order
				if (bothCalendar.isSelected()) {

					// Iterate through team commitments and add each element to
					// combinedList
					// do it backwards to maintain order
					int j = getTeamCalData().getCommitments().getCommitments().size() - 1;
					for (int i = j; i >= 0; i--) {
						combinedCommList.add(getTeamCalData().getCommitments()
								.getCommitments().get(i));
					}
					

					//get the combined events for team
					final CombinedEventList teamRepeatEvents = 
							getTeamCalData().getRepeatingEvents().toCombinedEventList();
					for (int i = 0; i < teamRepeatEvents.getEvents().size(); i++){
						combinedEventList.add(teamRepeatEvents.getEvents().get(i));
					}

					// Iterate through team events and add each element to
					// combinedEventList
					// do it backwards to maintain order
					j = getTeamCalData().getEvents().getEvents().size() - 1;
					for (int i = j; i >= 0; i--) {
						combinedEventList.add(getTeamCalData().getEvents()
								.getEvents().get(i));
					}
					

				}

			}//End if myCalendar.isSelected() || bothCalendar.isSelected()
			else {//if team is selected
				//create a combined Commitment list
				combinedCommList = new CombinedCommitmentList(
						new ArrayList<Commitment>(getTeamCalData()
								.getCommitments().getCommitments()));
				//create a combined event list
				combinedEventList = getTeamCalData()
						.getRepeatingEvents().toCombinedEventList();
				for (int i = 0; i < getTeamCalData().getEvents()
						.getEvents().size(); i++) {
					combinedEventList.add(getTeamCalData().getEvents()
							.getEvents().get(i));
				}
			}//else if the team is selected
			
			//Apply the selected filter
			final Filter selectedFilter = ((Filter) filterComboBox.getSelectedItem());
			if(selectedFilter != null && selectedFilter.getID() != 0){
				final Iterator<Event> it = combinedEventList.getEvents().iterator();
				 while(it.hasNext()){
					 Event e = it.next();
					 if(!selectedFilter.getActiveTeamCategories().contains(e.getCategoryID()) && !selectedFilter.getActivePersonalCategories().contains(e.getCategoryID())){
						 it.remove();
					 }
				 }
				 
				 final Iterator<Commitment> it2 = combinedCommList.getCommitments().iterator();
				 while(it2.hasNext()){
					 Commitment c = it2.next();
					 if(!selectedFilter.getActiveTeamCategories().contains(c.getCategoryID()) && !selectedFilter.getActivePersonalCategories().contains(c.getCategoryID())){
						 it2.remove();
					 }
				 }
			}
			
			events = combinedEventList;
			commitments = combinedCommList;
			
		}//if initialized and not null
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
		if (CalendarPropertiesModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS") == null) {
			final CalendarProperties createdProps = new CalendarProperties(ConfigManager
					.getConfig().getProjectName()
					+ "-"
					+ ConfigManager.getConfig().getUserName() + "-PROPS");
			CalendarPropertiesModel.getInstance().addCalendarProps(createdProps);
		}


		calProps = CalendarPropertiesModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS");
		//set the comm list to the new data
		showcom.setSelected(calProps.getMyShowComm());
		//showteam.setSelected(calProps.getShowTeamData());
		calView.applyCalProps(calProps);
		switch(calProps.getMyTeamBoth()){
		case 0: myCalendar.setSelected(true);
		break;
		case 1: teamCalendar.setSelected(true);
		break;
		case 2: bothCalendar.setSelected(true);
		break;
		}
		

	}

	@Override
	protected void updateCommPane() {
		if (initialized) {
			calView.updateCommPane(commitments, this.getShowCommitments());
		}

	}

	@Override
	protected JComponent getDataDisplayPanel() {
		final JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 0));
		final SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);

		showcom = new JCheckBox("Show Commitments");
		showcom.setCursor(new Cursor(Cursor.HAND_CURSOR));
		showcom.setToolTipText("Display Commitments in Calendar View");
		showcom.setFont(CalendarStandard.CalendarFont);
		showcom.setBackground(Color.WHITE);
		showcom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setMyShowComm(showcom.isSelected());
				setView();
			}
		});

		layout.putConstraint(SpringLayout.NORTH, showcom, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, showcom, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, showcom, 0, SpringLayout.SOUTH, panel);
		panel.add(showcom);

		//create the my/team/both radio buttons
		myCalendar = new JRadioButton("Personal");
		myCalendar.setAlignmentX(Component.CENTER_ALIGNMENT);
		myCalendar.setBackground(Color.WHITE);
		myCalendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		myCalendar.setToolTipText("View Personal Calendar");
		myCalendar.setFont(CalendarStandard.CalendarFont);
		myCalendar.setSelected(true);
		myCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//update the commitments to either include or not include team data
				calProps.setMyTeamBoth(0);
				updateCalData();
				//setView(); redundant called in updateCalData
			}
		});

		layout.putConstraint(SpringLayout.NORTH, myCalendar, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, myCalendar, 15, SpringLayout.EAST, showcom);
		layout.putConstraint(SpringLayout.SOUTH, myCalendar, 0, SpringLayout.SOUTH, panel);
		panel.add(myCalendar);


		teamCalendar = new JRadioButton("Team");
		teamCalendar.setAlignmentX(Component.CENTER_ALIGNMENT);
		teamCalendar.setBackground(Color.WHITE);
		teamCalendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		teamCalendar.setToolTipText("View Team Calendar");
		teamCalendar.setFont(CalendarStandard.CalendarFont);
		teamCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//update the commitments to either include or not include team data
				calProps.setMyTeamBoth(1);
				updateCalData();
				//setView(); redundant called in updateCalData
			}
		});

		layout.putConstraint(SpringLayout.NORTH, teamCalendar, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, teamCalendar, 15, SpringLayout.EAST, myCalendar);
		layout.putConstraint(SpringLayout.SOUTH, teamCalendar, 0, SpringLayout.SOUTH, panel);
		panel.add(teamCalendar);

		bothCalendar = new JRadioButton("Both");
		bothCalendar.setAlignmentX(Component.CENTER_ALIGNMENT);
		bothCalendar.setBackground(Color.WHITE);
		bothCalendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		bothCalendar.setToolTipText("View Both Calendars");
		bothCalendar.setFont(CalendarStandard.CalendarFont);
		bothCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//update the commitments to either include or not include team data
				calProps.setMyTeamBoth(2);
				updateCalData();
				//setView(); redundant called in updateCalData
			}
		});

		layout.putConstraint(SpringLayout.NORTH, bothCalendar, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, bothCalendar, 15, SpringLayout.EAST, teamCalendar);
		layout.putConstraint(SpringLayout.SOUTH, bothCalendar, 0, SpringLayout.SOUTH, panel);
		panel.add(bothCalendar);

		final ButtonGroup calendarSelection = new ButtonGroup();
		calendarSelection.add(myCalendar);
		calendarSelection.add(teamCalendar);
		calendarSelection.add(bothCalendar);

		/*
		showteam = new JCheckBox("Show Team Data");
		showteam.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showteam.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN, 14f));
		showteam.setBackground(Color.WHITE);
		showteam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calProps.setShowTeamData(showteam.isSelected());
				//update the commitments to either include or not include team data
				updateCalData();
				setView();
			}
		});

        layout.putConstraint(SpringLayout.NORTH, showteam, 0, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, showteam, 15, SpringLayout.EAST, showcom);
        layout.putConstraint(SpringLayout.SOUTH, showteam, 0, SpringLayout.SOUTH, panel);
        panel.add(showteam);*/

		final int width = showcom.getPreferredSize().width + 30 
				+ myCalendar.getPreferredSize().width + 30 
				+ teamCalendar.getPreferredSize().width + 30
				+ bothCalendar.getPreferredSize().width;
		final int height = showcom.getPreferredSize().height;
		panel.setPreferredSize(new Dimension(width, height));

		return panel;
	}

}
