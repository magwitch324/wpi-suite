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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;


@SuppressWarnings("serial")
public abstract class CalendarView extends JSplitPane {
	
	private ICalPane calPane;
	protected CommitmentView commitmentView;
	private String dateRange;
	public boolean showAllCommFlag;
	
	/**
	 * Constructor
	 * Sets up the panel with the refresh function
	 */
	public CalendarView(GregorianCalendar calendar) {
		this.showAllCommFlag = false;
	}
	/**
	 * create and display View componenets
	 */
	public void refresh() {
		//System.out.println("NUM OF VIEW COMPS: " + this.getComponentCount());
		setLeftComponent(calPane.getPane());
		setRightComponent(makeRightView());
		setResizeWeight(1.0);
		calPane.refresh();
		
	}
	
	/**
	 * @return
	 */
	private JPanel makeRightView() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1,1,0,0));
		labelPanel.setBorder(new EmptyBorder(0, 10, 0 , 10));
		labelPanel.setMinimumSize(new Dimension(330, 50));
		labelPanel.setBackground(Color.WHITE);
		
		JLabel dateLabel = new JLabel("<html><body style='width: 100%'><center>" + dateRange + "</center></html>", SwingConstants.CENTER);
		dateLabel.setFont(CalendarStandard.CalendarFontBold.deriveFont(Font.BOLD, 16));
		dateLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
		
		labelPanel.add(dateLabel);
		
		panel.add(labelPanel);
		//radio buttons for controlling the filter in the commitment pane
		JRadioButton showVisibleButton = new JRadioButton("Show all open commitments in visible range");
		showVisibleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showVisibleButton.setBackground(Color.WHITE);
		if(!showAllCommFlag){// need to check due to how refreshing works
			showVisibleButton.setSelected(true);
		}
		showVisibleButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Show Visible pressed");
				showAllCommFlag = false;
				GUIEventController.getInstance().getSelectedCalendar().displayCalData();
			}
			
		});
		panel.add(showVisibleButton);
		
		JRadioButton showAllButton = new JRadioButton("Show all open commitments");
		showAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showAllButton.setBackground(Color.WHITE);
		if (showAllCommFlag){
			showAllButton.setSelected(true);
		}
		showAllButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Show All pressed");
				showAllCommFlag = true;
				GUIEventController.getInstance().getSelectedCalendar().displayCalData();
			}
			
		});
		panel.add(showAllButton);
		
		ButtonGroup commPanelFilters = new ButtonGroup();
		commPanelFilters.add(showVisibleButton);
		commPanelFilters.add(showAllButton);
		
		//View all Commitments Button is no longer necessary, but I'm leaving this code here
		//  in case the changes need to be reverted
		/*
		// View All Commitments Button - NOT SURE HOW TO CENTER???
		JButton viewAllCommitmentsButton = new JButton();
		
		try {
			Image img = ImageIO.read(getClass().getResource("All_Icon.png"));
		    viewAllCommitmentsButton.setIcon(new ImageIcon(img));
		    viewAllCommitmentsButton.setText("View All Commitments");
		    viewAllCommitmentsButton.setBackground(Color.WHITE);
		    viewAllCommitmentsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			viewAllCommitmentsButton.setIcon(new ImageIcon());
			viewAllCommitmentsButton.setText("View All Commitments");
		} 
		
		panel.add(viewAllCommitmentsButton);
		viewAllCommitmentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		// the action listener for the Create View All Commitments Button
		viewAllCommitmentsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIEventController.getInstance().createViewCommitmentsTab();
			}	
		});		
		*/
		JSeparator separator = new JSeparator();
		separator.setOrientation(VERTICAL_SPLIT);
		panel.add(separator);
		panel.add(commitmentView, BorderLayout.CENTER);
		panel.setBackground(Color.WHITE);
		
		return panel;
		
	}
	
	/**
	 * set the label for the current date/dates
	 * @param range
	 */
	protected void setLabel(String range) {
		dateRange = range;
	}
	
	/**
	 * set the new date range for the calendar
	 * @param calendar
	 */
	abstract public void setRange(GregorianCalendar calendar);
	
	public void setCalPane(ICalPane pane) {
		// TODO Auto-generated method stub
		this.calPane = pane;
	}
	
	public void setCommitmentView(CommitmentView comm) {
		// TODO Auto-generated method stub
		this.commitmentView = comm;
	}
	
	/** Display calendar data in internal panels, decides what commitments 
	 * fall within range
	 * @param showCommsOnCalPane 
	 * @param calData Calendar Data to be displayed
	 * @param showTeamData 
	 * @param showCommitments 
	 */
	abstract public void displayCalData(CommitmentList commList, boolean showCommOnCal);
	
	public void updateScrollPosition(int value){
		this.calPane.updateScrollPosition(value);
	}

	
	
}
