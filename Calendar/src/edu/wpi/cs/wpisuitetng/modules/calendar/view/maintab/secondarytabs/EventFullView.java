/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProperties;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropertiesModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.EventViewPanel;
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
	private CalendarProperties calProps;
	private boolean initialized;

	List<EventViewPanel> eventPanelList = new ArrayList<EventViewPanel>();
	
	private boolean reverse_sort;

	//Button group for sort type
	JButton jName;
	JButton jStartDate;
	JButton jEndDate;
	JButton jDescription;

	//radio group for display type
	JRadioButton bothRadioButton;
	JRadioButton personalRadioButton;
	JRadioButton teamRadioButton;
	ButtonGroup viewSwitchGroup;
	
	// Search input
	JTextField searchInput = new JTextField();

	/**
	 * @author CS Anonymous
	 */
	public enum ViewingMode {
		PERSONAL, TEAM, BOTH;
	};
	ViewingMode mode;
	
	Sort_Type sort_mode;	//The current sorting type
	

	/**
	 * Constructor creates main scrolling Panel and 
	 * sets calendar which will grab teams events
	 * @param personalCalendar AbCalendar
	 */
	public EventFullView(AbCalendar personalCalendar) {
		setLayout(new GridLayout(1, 1));
		this.setBackground(Color.WHITE);
		
		initialized = false;
		pcalendar = personalCalendar;

		mode = ViewingMode.TEAM;

		eventPanel = new JPanel();
		eventPanel.setBackground(Color.WHITE);
		eventPanel.setBorder(new EmptyBorder(5, 5, 10, 5));
		
		scrollPane = new JScrollPane(eventPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		scrollPane.setViewportView(eventPanel);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setForeground(Color.WHITE);
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

		final String searchText = getSearchInput().trim().toLowerCase();
		
		final EventList events = new EventList();
		
		if (mode == ViewingMode.TEAM || mode == ViewingMode.BOTH) {
			if(pcalendar.getTeamCalData() != null) {
				for (Event e : pcalendar.getTeamCalData().getEvents().getEvents()) {
					events.add(e);
				}
			}
		}
			
		if (mode == ViewingMode.PERSONAL || mode == ViewingMode.BOTH) {
			if(pcalendar.getMyCalData() != null) {
				for (Event e : pcalendar.getMyCalData().getEvents().getEvents()) {
					events.add(e);
				}
			}
		}
		
		for(Event event : events.getEvents()) {
			if (getSearchInput() == "")
				{
					eventPanelList.add(new EventViewPanel(event));
				}
			else if (event.getName().toLowerCase().contains(searchText))
				{
					eventPanelList.add(new EventViewPanel(event));
				}
			else if (event.getDescription().toLowerCase().contains(searchText))
				{
					eventPanelList.add(new EventViewPanel(event));
				}
		}
		//Sorts the list of eventPanelList based on sort type and reverse_sort
		sort();
	}

	/** 
	 * Event panel is populated with all events 
	 * which are in separate panels that can be scrolled and clicked*/
	private void setupPanels() {
		eventPanel.removeAll();
		final SpringLayout layout = new SpringLayout();
		eventPanel.setLayout(layout);
		EventViewPanel last = null;
		int event_height = 0;
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
			event_height += evp.getPreferredSize().height;
			last = evp;
		}
		eventPanel.setPreferredSize(new Dimension(10, event_height));
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	/**
	 * Creates the view port used as the header of the scroll pane
	 * @return	The view port containing data display information and sort buttons
	 */
	private JViewport getHeader(){
		final JViewport port = new JViewport();
		final JPanel apanel = new JPanel();
		apanel.setBackground(Color.WHITE);
		apanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		apanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		final SpringLayout layout = new SpringLayout();
		apanel.setLayout(layout);
		
		final JPanel datadisplay = getDataDisplay();
		layout.putConstraint(SpringLayout.NORTH, datadisplay, 0, SpringLayout.NORTH, apanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, datadisplay, 0, SpringLayout.HORIZONTAL_CENTER, apanel);
		layout.putConstraint(SpringLayout.WEST, datadisplay, 0, SpringLayout.WEST, apanel);
		layout.putConstraint(SpringLayout.EAST, datadisplay, 0, SpringLayout.EAST, apanel);
		apanel.add(datadisplay);
		
		final JPanel sortbuttons = getSortButtons();
		layout.putConstraint(SpringLayout.NORTH, sortbuttons, 0, SpringLayout.SOUTH, datadisplay);
		layout.putConstraint(SpringLayout.WEST, sortbuttons, 0, SpringLayout.WEST, apanel);
		layout.putConstraint(SpringLayout.SOUTH, sortbuttons, 0, SpringLayout.SOUTH, apanel);
		layout.putConstraint(SpringLayout.EAST, sortbuttons, 0, SpringLayout.EAST, apanel);
		apanel.add(sortbuttons);
		
		port.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				apanel.setPreferredSize(new Dimension(port.getWidth(), 100));
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
		final JPanel apanel = new JPanel();
		apanel.setBackground(Color.WHITE);
		final SpringLayout layout = new SpringLayout();
		apanel.setLayout(layout);
		apanel.setPreferredSize(new Dimension(500, 50));
		apanel.setMaximumSize(new Dimension(20000, 50));
		apanel.setBorder(BorderFactory.createLoweredBevelBorder());
		apanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.WHITE));
		
		viewSwitchGroup = new ButtonGroup();
		
		teamRadioButton = new JRadioButton("Team");
		teamRadioButton.setBackground(Color.WHITE);
		teamRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
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
		bothRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.BOTH);
			}

		});
		
		apanel.add(personalRadioButton);
		apanel.add(teamRadioButton);
		apanel.add(bothRadioButton);
		
		viewSwitchGroup.add(personalRadioButton);
		viewSwitchGroup.add(teamRadioButton);
		viewSwitchGroup.add(bothRadioButton);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, personalRadioButton, 0, SpringLayout.VERTICAL_CENTER, apanel);
		layout.putConstraint(SpringLayout.EAST, personalRadioButton, 0, SpringLayout.WEST, teamRadioButton);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, teamRadioButton, 0, SpringLayout.VERTICAL_CENTER, apanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, teamRadioButton, 0, SpringLayout.HORIZONTAL_CENTER, apanel);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, bothRadioButton, 0, SpringLayout.VERTICAL_CENTER, apanel);
		layout.putConstraint(SpringLayout.WEST, bothRadioButton, 0, SpringLayout.EAST, teamRadioButton);
		
		// Create search bar
		final JPanel search = new JPanel();
		final SpringLayout searchLayout = new SpringLayout();
		search.setLayout(searchLayout);
		search.setBackground(Color.WHITE);
		
		searchInput = new JTextField();
		searchInput.setBackground(CalendarStandard.CalendarYellow);
		JLabel searchLabel = new JLabel("Search: ");
		
		// Listen for changes in the text
		searchInput.getDocument().addDocumentListener(new DocumentListener() {
		  public void changedUpdate(DocumentEvent e) {
			  update();
		  }
		  public void removeUpdate(DocumentEvent e) {
			  update();
		  }
		  public void insertUpdate(DocumentEvent e) {
			  update();
		  }

		  public void update() {
			  updateList();
		  }
		});
		
		search.add(searchLabel);
		search.add(searchInput);

		searchLayout.putConstraint(SpringLayout.WEST, searchLabel, 0, SpringLayout.WEST, search);
		searchLayout.putConstraint(SpringLayout.VERTICAL_CENTER, searchLabel, 0, SpringLayout.VERTICAL_CENTER, search);
		
		searchLayout.putConstraint(SpringLayout.WEST, searchInput, 10, SpringLayout.EAST, searchLabel);
		searchLayout.putConstraint(SpringLayout.EAST, searchInput, 0, SpringLayout.EAST, search);
		searchLayout.putConstraint(SpringLayout.VERTICAL_CENTER, searchInput, 0, SpringLayout.VERTICAL_CENTER, search);
		
		apanel.add(search);
		
		layout.putConstraint(SpringLayout.WEST, search, 50, SpringLayout.EAST, bothRadioButton);
		layout.putConstraint(SpringLayout.EAST, search, -10, SpringLayout.EAST, apanel);
		layout.putConstraint(SpringLayout.NORTH, search, 0, SpringLayout.NORTH, apanel);
		layout.putConstraint(SpringLayout.SOUTH, search, 0, SpringLayout.SOUTH, apanel);
		
		return apanel;
		
	}
	
	private String getSearchInput() {
		return searchInput.getText();
	}
	
	/**
	 * Creates the buttons used for sorting
	 * @return the panel containing all sorting buttons
	 */
	private JPanel getSortButtons(){
		final JPanel apanel = new JPanel();
		apanel.setPreferredSize(new Dimension(50, 50));
		apanel.setLayout(new GridLayout(1, 4));
		apanel.setBorder(BorderFactory.createLoweredBevelBorder());
		apanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.WHITE));
		
		
		jName = new JButton("<html><font color='white'><b>" + "Name" + "</b></font></html>");
		jName.setBackground(CalendarStandard.CalendarRed);
		jName.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jName.setToolTipText("Sort by Name");
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
						jName.setText("<html><font color='white'><b>" + "Name v" + "</b></font></html>");
					}
				}
				sort();
			}
		});
		
		jStartDate = new JButton("<html><font color='white'><b>" + "Start Date" + "</b></font></html>");
		jStartDate.setBackground(CalendarStandard.CalendarRed);
		jStartDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jStartDate.setToolTipText("Sort by Start Date");
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
						jStartDate.setText("<html><font color='white'><b>" + "Start Date v" + "</b></font></html>");
					}
				}
				sort();
			}
		});
		
		
		jEndDate = new JButton("<html><font color='white'><b>" + "End Date" + "</b></font></html>");
		jEndDate.setBackground(CalendarStandard.CalendarRed);
		jEndDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jEndDate.setToolTipText("Sort by End Date");
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
						jEndDate.setText("<html><font color='white'><b>" + "End Date v" + "</b></font></html>");
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
						jDescription.setText("<html><font color='white'><b>" + "Description v" + "</b></font></html>");
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
	 * Updates the event panel list and then displays them
	 */
	public void updateList(){
		setEventList();
		setupPanels();
	}

	/**
	 * Switches to the given view mode 
	 * @param newMode the viewing mode to change to
	 */
	private void switchView(ViewingMode newMode){
		mode = newMode;
		if( calProps != null){
			calProps.setEventViewMode(mode.ordinal());
		}
		this.updateList();
	}

	/**
	 * Used after cal props has been fetched from the server.
	 */
	public void applyCalProps(){

		calProps = CalendarPropertiesModel.getInstance().getCalendarProps( ConfigManager.getConfig().getProjectName() + "-" + ConfigManager.getConfig().getUserName() + "-PROPS");
		
		if(initialized && calProps != null){
			mode =  ViewingMode.values()[calProps.getEventViewMode()];

			switch (calProps.getEventViewMode()){
			
			case 0: viewSwitchGroup.setSelected(personalRadioButton.getModel(), true);
			break;
			
			case 1: viewSwitchGroup.setSelected(teamRadioButton.getModel(), true);
			break;
			
			
			
			case 2: viewSwitchGroup.setSelected(bothRadioButton.getModel(), true);
			break;
			}

			updateList();

		}
	}
	
	/**
	 * Sorts the list with the current sort_mode and reverse if needed
	 */
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
	
	/**
	 * Clear the icons from the sort buttons
	 */
	private void clearButtons(){
		jName.setIcon(null);
		jStartDate.setIcon(null);
		jEndDate.setIcon(null);
		jDescription.setIcon(null);
	}

}
