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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
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

	protected void drawThis() {
		final SpringLayout layout = new SpringLayout();
		setLayout(layout);


		final JComponent viewbtnpanel = getViewButtonPanel();
		layout.putConstraint(SpringLayout.WEST, viewbtnpanel, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewbtnpanel, 5, SpringLayout.NORTH, this);
		this.add(viewbtnpanel);

		final JComponent dateswitchpanel = getDatePanel();
        layout.putConstraint(SpringLayout.NORTH, dateswitchpanel, 0, SpringLayout.SOUTH, viewbtnpanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dateswitchpanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
        this.add(dateswitchpanel);

        final JComponent datapanel = getDataDisplayPanel();
        layout.putConstraint(SpringLayout.NORTH, datapanel, 0, SpringLayout.NORTH, viewbtnpanel);
        layout.putConstraint(SpringLayout.WEST, datapanel, 30, SpringLayout.EAST, viewbtnpanel);
        layout.putConstraint(SpringLayout.SOUTH, datapanel, 0, SpringLayout.SOUTH, viewbtnpanel);
        this.add(datapanel);
        
        final JComboBox filter = new JComboBox();
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, filter, 0, SpringLayout.VERTICAL_CENTER, datapanel);
		layout.putConstraint(SpringLayout.WEST, filter, 30, SpringLayout.EAST, datapanel);
		layout.putConstraint(SpringLayout.EAST, filter, -5, SpringLayout.EAST, this);
		filter.setMaximumSize(new Dimension(200, 20));
		this.add(filter);

		layout.putConstraint(SpringLayout.WEST, viewpanel, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, viewpanel, 5, SpringLayout.SOUTH, dateswitchpanel);
		layout.putConstraint(SpringLayout.EAST, viewpanel, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, viewpanel, -5, SpringLayout.SOUTH, this);

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
			//create a combined event list
			final CombinedEventList combinedEventList = getCalData()
					.getRepeatingEvents().toCombinedEventList();
			for (int i = 0; i < getCalData().getEvents()
					.getEvents().size(); i++) {
				combinedEventList.add(getCalData().getEvents()
						.getEvents().get(i));
			}
				commitments = getCalData().getCommitments();
				events = combinedEventList;
		}
	}

	public void updateCalData() {
		if(!initialized){
			if (CalendarDataModel.getInstance().getCalendarData(
					ConfigManager.getConfig().getProjectName()) == null) {
				final CalendarData createdCal = new CalendarData(ConfigManager
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
		if(initialized){
			calView.displayCalData(events, commitments, getShowCommitments());
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

	@Override
	protected JComponent getDataDisplayPanel() {
		final JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 0));
		final SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		
		showcom = new JCheckBox("Show Commitments");
		showcom.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showcom.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN, 14f));
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

        final int width = showcom.getPreferredSize().width + 30;
        final int height = showcom.getPreferredSize().height;
        panel.setPreferredSize(new Dimension(width, height));
        
		return panel;
	}
}
