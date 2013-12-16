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
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProperties;


/**
 * Calendar view is the right side panel that displays a list of
 * commitments, current date range, and a set of radio buttons to
 * toggle on/off show all commitments.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public abstract class CalendarView extends JSplitPane {
	
	private ICalPane calPane;
	protected CommitmentView commitmentView;
	private String dateRange;
	public boolean showAllCommFlag;
	private CalendarProperties calProps;
	JRadioButton showAllButton;
	JRadioButton showVisibleButton;
	
	/**
	 * Constructor
	 * Sets up the panel with the refresh function
	 * @param calendar GregorianCalendar
	 */
	protected CalendarView(GregorianCalendar calendar) {
		showAllCommFlag = false;
	}
	/**
	 * create and display View componenets
	 */
	public void refresh() {
		setLeftComponent(calPane.getPane());
		setRightComponent(makeRightView());
		setResizeWeight(1.0);
		calPane.refresh();
		
	}
	
	/**
	 * @return
	 */
	private JPanel makeRightView() {
		
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		final JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 1, 0, 0));
		labelPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
//		labelPanel.setBorder(new EmptyBorder(0, 10, 0 , 10));
//		labelPanel.setMinimumSize(new Dimension(330, 50));
		labelPanel.setBackground(CalendarStandard.CalendarRed);
		final JLabel dateLabel = new JLabel(
				"<html><font color='white'><body style='width: 100%'><center>" + 
		dateRange + "</center></html>", SwingConstants.CENTER);
		dateLabel.setFont(CalendarStandard.CalendarFontBold.deriveFont(Font.BOLD, 16));
		dateLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
		
		labelPanel.add(dateLabel);
		
		panel.add(labelPanel);
		//radio buttons for controlling the filter in the commitment pane
		showVisibleButton = new JRadioButton(
				"Show all open commitments in visible range");
		showVisibleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showVisibleButton.setBackground(Color.WHITE);
		showVisibleButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		if(!showAllCommFlag){// need to check due to how refreshing works
			showVisibleButton.setSelected(true);
		}
		showVisibleButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showAllCommFlag = false;
				calProps.setShowCommRange(false);
				GUIEventController.getInstance().getCalendar().updateCommPane();
			}
			
		});
		panel.add(showVisibleButton);
		
		showAllButton = new JRadioButton("Show all open commitments");
		showAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		showAllButton.setBackground(Color.WHITE);
		showAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		if (showAllCommFlag){
			showAllButton.setSelected(true);
		}
		showAllButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				showAllCommFlag = true;
				calProps.setShowCommRange(true);
				GUIEventController.getInstance().getCalendar().updateCommPane();
			}
			
		});
		panel.add(showAllButton);
		
		final ButtonGroup commPanelFilters = new ButtonGroup();
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
		    viewAllCommitmentsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		    // To change cursor as it moves over this icon
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
		final JSeparator separator = new JSeparator();
		separator.setOrientation(VERTICAL_SPLIT);
		separator.setBackground(Color.gray);
		panel.add(separator);
		panel.add(commitmentView, BorderLayout.CENTER);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.gray));

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
	public abstract void setRange(GregorianCalendar calendar);
	
	public void setCalPane(ICalPane pane) {
		// TODO Auto-generated method stub
		calPane = pane;
	}
	
	public void setCommitmentView(CommitmentView comm) {
		// TODO Auto-generated method stub
		commitmentView = comm;
	}
	
	/** Display calendar data in internal panels, decides what commitments 
	 * fall within range
	
	
	
	
	 * @param eventList EventList
	 * @param commList CommitmentList
	 * @param showCommOnCal boolean
	 */
     public abstract void displayCalData(EventList eventList, 
			CommitmentList commList, boolean showCommOnCal);
	
	/**
	 * Method updateScrollPosition.
	 * @param value int
	 */
	public void updateScrollPosition(int value){
		calPane.updateScrollPosition(value);
	}

	/**
	 * Method updateCommPane.
	 * @param commList CommitmentList
	 * @param showCommOnCal boolean
	 */
	public abstract void updateCommPane(CommitmentList commList, boolean showCommOnCal);
	
	/**
	 * Method applyCalProps.
	 * @param calProps CalendarProps
	 */
	public void applyCalProps(CalendarProperties calProps){
		this.calProps = calProps;
		showAllCommFlag = calProps.getShowCommRange();
		if(!showAllCommFlag){
			showVisibleButton.setSelected(true);
		}
		else{
			showAllButton.setSelected(true);
		}
		GUIEventController.getInstance().getCalendar().updateCommPane();
		
	}
	
}
