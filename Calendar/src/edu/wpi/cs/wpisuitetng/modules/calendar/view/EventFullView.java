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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropsModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentFullView.ViewingMode;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.EventViewPanel.Sort_Type;

/**
 * This class is used for creating the event View 
 * tab that shows all events including those 
 * that have been completed.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class EventFullView extends JPanel{

	AbCalendar pcalendar;
	JPanel eventPanel;
	JScrollPane scrollPane;
	private CalendarProps calProps;
	private boolean initialized;

	//List<Event> eventList = new ArrayList<Event>();
	List<EventViewPanel> eventPanelList = new ArrayList<EventViewPanel>();
	
	private boolean reverse_sort;

	JButton jName;
	JButton jStartDate;
	JButton jEndDate;
	JButton jDescription;

	JRadioButton bothRadioButton;
	JRadioButton personalRadioButton;
	JRadioButton teamRadioButton;
	ButtonGroup viewSwitchGroup;

	/**
	 * @author CS Anonymous
	 */
	public enum ViewingMode {
		TEAM, PERSONAL, BOTH;
	};
	ViewingMode mode;
	

	Sort_Type sort_mode;
	

	/**
	 * Constructor creates main scrolling Panel and 
	 * sets calendar which will grab teams events
	 * @param personalCalendar AbCalendar
	 */
	public EventFullView(AbCalendar personalCalendar) {
		super();
		setLayout(new GridLayout(1,1));
		this.setBackground(Color.WHITE);
		
		initialized = false;
		pcalendar = personalCalendar;

		mode = ViewingMode.TEAM;

		eventPanel = new JPanel();
		
		scrollPane = new JScrollPane(eventPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		scrollPane.setViewportView(eventPanel);
		
		// Sets the UPPER RIGHT corner box
		final JPanel cornerBoxUR = new JPanel();
		cornerBoxUR.setBackground(Color.WHITE);
		scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, cornerBoxUR);
		
		scrollPane.setColumnHeader(getHeader());
		
		add(scrollPane);

		setEventList();
		setupPanels();
		initialized = true;
		applyCalProps();
	}
	
	/**
	 * Sets the calendars events to the eventList array to populate panel
	 **/
	private void setEventList() {
		eventPanelList = new ArrayList<EventViewPanel>(); 

		if (mode == ViewingMode.TEAM){
			if(pcalendar.getTeamCalData() != null){
				for(Event event : pcalendar.getTeamCalData().getEvents().getEvents()){
					eventPanelList.add(new EventViewPanel(event));
				}
			}
		} else if (mode == ViewingMode.PERSONAL){
			if(pcalendar.getMyCalData() != null){
				for(Event event : pcalendar.getMyCalData().getEvents().getEvents()){
					eventPanelList.add(new EventViewPanel(event));
				}
			}
		} else if(pcalendar.getTeamCalData() != null && pcalendar.getMyCalData() != null) { 
			for(Event event : pcalendar.getTeamCalData().getEvents().getEvents()){
				eventPanelList.add(new EventViewPanel(event));
			}
			
			for(Event event : pcalendar.getMyCalData().getEvents().getEvents()){
				eventPanelList.add(new EventViewPanel(event));
			}
		}
	}

	/** 
	 * Event panel is populated with all events 
	 * which are in separate panels that can be scrolled and clicked*/
	private void setupPanels() {
		eventPanel.removeAll();
		SpringLayout layout = new SpringLayout();
		eventPanel.setLayout(layout);
		EventViewPanel last = null;
		for(EventViewPanel evp : eventPanelList) {
			if(last == null){
				layout.putConstraint(SpringLayout.NORTH, evp, 0, SpringLayout.NORTH, eventPanel);
			}
			else{
				layout.putConstraint(SpringLayout.NORTH, evp, 0, SpringLayout.SOUTH, last);
			}
			
			layout.putConstraint(SpringLayout.WEST, evp, 0, SpringLayout.WEST, eventPanel);
			layout.putConstraint(SpringLayout.EAST, evp, 0, SpringLayout.EAST, eventPanel);
			eventPanel.add(evp);
			
			last = evp;
		}
	}

	/**
	 * Creates the view port used as the header of the scroll pane
	 * @return	The view port containing data display information and sort buttons
	 */
	private JViewport getHeader(){
		final JViewport port = new JViewport();
		final JPanel apanel = new JPanel();
		apanel.setBackground(Color.RED);
		SpringLayout layout = new SpringLayout();
		apanel.setLayout(layout);
		
		JPanel datadisplay = getDataDisplay();
		layout.putConstraint(SpringLayout.NORTH, datadisplay, 0, SpringLayout.NORTH, apanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, datadisplay, 0, SpringLayout.HORIZONTAL_CENTER, apanel);
		apanel.add(datadisplay);
		
		JPanel sortbuttons = getSortButtons();
		layout.putConstraint(SpringLayout.NORTH, sortbuttons, 0, SpringLayout.SOUTH, datadisplay);
		layout.putConstraint(SpringLayout.WEST, sortbuttons, 0, SpringLayout.WEST, apanel);
		layout.putConstraint(SpringLayout.SOUTH, sortbuttons, 0, SpringLayout.SOUTH, apanel);
		layout.putConstraint(SpringLayout.EAST, sortbuttons, 0, SpringLayout.EAST, apanel);
		apanel.add(sortbuttons);
		
		port.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				apanel.setPreferredSize(new Dimension(port.getWidth(), 70));
			}
		});
		
		port.setView(apanel);
		
		return port;
	}
	
	/**
	 * Creates the radio buttons used for which data is displayed
	 * @return The panel containing the data display radio buttons
	 */
	private JPanel getDataDisplay(){
		JPanel apanel = new JPanel();
		apanel.setBackground(Color.WHITE);
		apanel.setLayout(new GridLayout(1,3));
		
		viewSwitchGroup = new ButtonGroup();
		teamRadioButton = new JRadioButton("Team");
		teamRadioButton.setBackground(Color.WHITE);
		teamRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		teamRadioButton.setToolTipText("View Team Commitments");
		teamRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.TEAM);
			}

		});
		
		personalRadioButton = new JRadioButton("Personal");
		personalRadioButton.setBackground(Color.WHITE);
		personalRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		personalRadioButton.setToolTipText("View Personal Commitments");
		personalRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.PERSONAL);
			}

		});
		
		bothRadioButton = new JRadioButton("Both");
		bothRadioButton.setBackground(Color.WHITE);
		bothRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		bothRadioButton.setToolTipText("View All Commitments.");
		bothRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.BOTH);
			}

		});
		
		apanel.add(teamRadioButton);
		apanel.add(personalRadioButton);
		apanel.add(bothRadioButton);
		
		viewSwitchGroup.add(teamRadioButton);
		viewSwitchGroup.add(personalRadioButton);
		viewSwitchGroup.add(bothRadioButton);
		
		return apanel;
	}
	
	/**
	 * Creates the buttons used for sorting
	 * @return the panel containing all sorting buttons
	 */
	private JPanel getSortButtons(){
		JPanel apanel = new JPanel();
		apanel.setPreferredSize(new Dimension(40, 40));
		apanel.setLayout(new GridLayout(1,4));
		
		jName = new JButton("<html><font color='white'><b>" + "Name" + "</b></font></html>");
		jName.setBackground(CalendarStandard.CalendarRed);
		jName.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jName.setToolTipText("Sort by Name.");
		//sort by name
		jName.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtons();
				if(sort_mode == Sort_Type.NAME){
					reverse_sort = !reverse_sort;
				}
				else{
					sort_mode = Sort_Type.NAME;
					reverse_sort = false;
				}
				
				if(!reverse_sort){
					try {
						final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
						jName.setIcon(new ImageIcon(img));
						jName.setText("<html><font color='white'><b>" + "Name" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jName.setText("<html><font color='white'><b>" + "Name ^" + "</b></font></html>");
					}
				}
				else{
					try {
						final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
						jName.setIcon(new ImageIcon(img));
						jName.setText("<html><font color='white'><b>" + "Name" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jName.setText("<html><font color='white'><b>"+ "Name v" + "</b></font></html>");
					}
				}
				sort();
			}
		});
		
		jStartDate = new JButton("<html><font color='white'><b>" + "Start Date" + "</b></font></html>");
		jStartDate.setBackground(CalendarStandard.CalendarRed);
		jStartDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jStartDate.setToolTipText("Sort by Start Date.");
		jStartDate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtons();
				if(sort_mode == Sort_Type.START_DATE){
					reverse_sort = !reverse_sort;
				}
				else{
					sort_mode = Sort_Type.START_DATE;
					reverse_sort = false;
				}
				
				if(!reverse_sort){
					try {
						final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
						jStartDate.setIcon(new ImageIcon(img));
						jStartDate.setText("<html><font color='white'><b>" + "Start Date" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jStartDate.setText("<html><font color='white'><b>" + "Start Date ^" + "</b></font></html>");
					}
				}
				else{
					try {
						final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
						jStartDate.setIcon(new ImageIcon(img));
						jStartDate.setText("<html><font color='white'><b>" + "Start Date" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jStartDate.setText("<html><font color='white'><b>"+ "Start Date v" + "</b></font></html>");
					}
				}
				sort();
			}
		});
		
		
		jEndDate = new JButton("<html><font color='white'><b>" + "End Date" + "</b></font></html>");
		jEndDate.setBackground(CalendarStandard.CalendarRed);
		jEndDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jEndDate.setToolTipText("Sort by END Date.");
		jEndDate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtons();
				if(sort_mode == Sort_Type.END_DATE){
					reverse_sort = !reverse_sort;
				}
				else{
					sort_mode = Sort_Type.END_DATE;
					reverse_sort = false;
				}
				
				if(!reverse_sort){
					try {
						final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
						jEndDate.setIcon(new ImageIcon(img));
						jEndDate.setText("<html><font color='white'><b>" + "End Date" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jEndDate.setText("<html><font color='white'><b>" + "End Date ^" + "</b></font></html>");
					}
				}
				else{
					try {
						final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
						jEndDate.setIcon(new ImageIcon(img));
						jEndDate.setText("<html><font color='white'><b>" + "End Date" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jEndDate.setText("<html><font color='white'><b>"+ "End Date v" + "</b></font></html>");
					}
				}
				sort();
			}
		});
		
		jDescription = new JButton("<html><font color='white'><b>" + "Description" + "</b></font></html>");
		jDescription.setBackground(CalendarStandard.CalendarRed);
		jDescription.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jDescription.setToolTipText("Sort by Description");
		jDescription.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtons();
				if(sort_mode == Sort_Type.DESCRIPTION){
					reverse_sort = !reverse_sort;
				}
				else{
					sort_mode = Sort_Type.DESCRIPTION;
					reverse_sort = false;
				}
				
				if(!reverse_sort){
					try {
						final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
						jDescription.setIcon(new ImageIcon(img));
						jDescription.setText("<html><font color='white'><b>" + "Description" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jDescription.setText("<html><font color='white'><b>" + "Description ^" + "</b></font></html>");
					}
				}
				else{
					try {
						final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
						jDescription.setIcon(new ImageIcon(img));
						jDescription.setText("<html><font color='white'><b>" + "Description" + "</b></font></html>");
					} 
					catch (IOException ex) {}
					catch(IllegalArgumentException ex){
						jDescription.setText("<html><font color='white'><b>"+ "Description v" + "</b></font></html>");
					}
				}
				sort();
			}
		});
		
		apanel.add(jName);
		apanel.add(jStartDate);
		apanel.add(jEndDate);
		apanel.add(jDescription);
		
		
		
		
		return apanel;
	}
			
	/**
	 * Method updateList.
	 */
	public void updateList(){
		setEventList();
		setupPanels();
	}

	/**
	 * Method updateView.
	 */
	public void updateView(){
		eventPanel.removeAll();
		setupPanels();
	}

	private void switchView(ViewingMode newMode){
		mode = newMode;
		calProps.setEventViewMode(mode.ordinal());
		this.updateList();
	}

	/**
	 * Used after cal props has been fetched from the server.
	 */
	protected void applyCalProps(){

		calProps = CalendarPropsModel.getInstance().getCalendarProps( ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName() + "-PROPS");
		
		if(initialized && calProps != null){
			mode =  ViewingMode.values()[calProps.getEventViewMode()];

			switch (calProps.getEventViewMode()){
			
			case 0: viewSwitchGroup.setSelected(teamRadioButton.getModel(), true);
			break;
			
			case 1: viewSwitchGroup.setSelected(personalRadioButton.getModel(), true);
			break;
			
			case 2: viewSwitchGroup.setSelected(bothRadioButton.getModel(), true);
			break;
			}

			updateList();

		}
	}
	
	protected void sort(){
		Collections.sort(eventPanelList, new Comparator<EventViewPanel>() {
			@Override 
			public int compare(EventViewPanel e1, EventViewPanel e2) {
				return e1.compareTo(e2, sort_mode);
			}
		});
		
		if(reverse_sort){
			Collections.reverse(eventPanelList);
		}
		
		setupPanels();
	}
	
	private void clearButtons(){
		jName.setIcon(null);
		jStartDate.setIcon(null);
		jEndDate.setIcon(null);
		jDescription.setIcon(null);
	}

}
