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
	JPanel header;
	private CalendarProps calProps;
	private boolean initialized;

	List<Event> eventList = new ArrayList<Event>();
	private int namesort = 0;
	private int startDatesort = 0;
	private int endDatesort = 0;
	private int dessort = 0;

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

	/**
	 * Constructor creates main scrolling Panel and 
	 * sets calendar which will grab teams events
	 * @param personalCalendar AbCalendar
	 */
	public EventFullView(AbCalendar personalCalendar) {
		initialized = false;
		pcalendar = personalCalendar;

		mode = ViewingMode.TEAM;

		eventPanel = new JPanel();

		// Header panel
		header = new JPanel();
		header.setBackground(Color.WHITE);
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
		header.setBorder(new EmptyBorder(5, 5, 5, 5));
		header.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

		scrollPane = new JScrollPane(eventPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER );
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);

		// Sets the UPPER RIGHT corner box
		final JPanel cornerBoxUR = new JPanel();
		cornerBoxUR.setBackground(Color.WHITE);
		scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER,
				cornerBoxUR);
		add(scrollPane);

		/*spring layout to allow adjustments to size of screen without messing up panels*/
		final SpringLayout layout = new SpringLayout();
		setLayout(layout);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
		scrollPane.setViewportView(eventPanel);

		setEventList();
		setupPanels();
		initialized = true;
		applyCalProps();
	}
	
	/**
	 * Sets the calendars events to the eventList array to populate panel
	 **/
	private void setEventList() {

		if (mode == ViewingMode.TEAM){
			if(pcalendar.getTeamCalData() != null){
				eventList = pcalendar.getTeamCalData().getEvents().getEvents();
			}
		} else if (mode == ViewingMode.PERSONAL){
			if(pcalendar.getMyCalData() != null){
				eventList = pcalendar.getMyCalData().getEvents().getEvents();
			}
		} else if(pcalendar.getTeamCalData() != null && pcalendar.getMyCalData() != null) { 
			final CombinedEventList combinedList = new CombinedEventList(
					new ArrayList<Event>(
							pcalendar.getMyCalData().getEvents().getEvents()));
			final CalendarData teamData = pcalendar.getTeamCalData();

			/*if we are supposed to show team data, 
			 * we need to put the team events into the list in the right order*/
			for (int i = 0; i < teamData.getEvents()
					.getEvents().size(); i++) {
				combinedList.add(teamData.getEvents()
						.getEvents().get(i));
			}
			eventList = combinedList.getEvents();
		}
	}

	/** 
	 * Event panel is populated with all events 
	 * which are in separate panels that can be scrolled and clicked*/
	private void setupPanels() {
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		eventPanel.setBorder(new EmptyBorder(5, 5, 10, 5));
		eventPanel.setBackground(Color.WHITE);

		header.removeAll();


		final JPanel viewSwitcher = new JPanel();

		final SpringLayout switcherLayout = new SpringLayout();

		viewSwitcher.setLayout(switcherLayout);
		viewSwitcher.setBackground(Color.WHITE);

		teamRadioButton = new JRadioButton("Team");
		teamRadioButton.setBackground(Color.WHITE);
		teamRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		teamRadioButton.setToolTipText("Click to view your Team Events.");
		teamRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.TEAM);
			}

		});
		viewSwitcher.add(teamRadioButton, SpringLayout.WEST);
		if (mode == ViewingMode.TEAM){
			teamRadioButton.setSelected(true);
		}


		personalRadioButton = new JRadioButton("Personal");
		personalRadioButton.setBackground(Color.WHITE);
		personalRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		personalRadioButton.setToolTipText("Click to view your Personal Events.");
		personalRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.PERSONAL);
			}

		});
		viewSwitcher.add(personalRadioButton, SpringLayout.HORIZONTAL_CENTER);
		if (mode == ViewingMode.PERSONAL){
			personalRadioButton.setSelected(true);
		}


		switcherLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, personalRadioButton,
				0, SpringLayout.HORIZONTAL_CENTER, viewSwitcher);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, personalRadioButton, 
				0, SpringLayout.VERTICAL_CENTER, viewSwitcher);

		bothRadioButton = new JRadioButton("Both");
		bothRadioButton.setBackground(Color.WHITE);
		bothRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this radio button
		bothRadioButton.setToolTipText("Click to view BOTH your Personal and Team Events.");
		bothRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchView(ViewingMode.BOTH);
			}

		});
		viewSwitcher.add(bothRadioButton, SpringLayout.EAST);
		if (mode == ViewingMode.BOTH){
			bothRadioButton.setSelected(true);
		}
		bothRadioButton.setMinimumSize(new Dimension(100, 50));
		bothRadioButton.setMaximumSize(new Dimension(100, 50));
		bothRadioButton.setAlignmentX(CENTER_ALIGNMENT);

		viewSwitchGroup = new ButtonGroup();
		viewSwitchGroup.add(teamRadioButton);
		viewSwitchGroup.add(personalRadioButton);
		viewSwitchGroup.add(bothRadioButton);

		switcherLayout.putConstraint(SpringLayout.EAST, teamRadioButton, 
				0, SpringLayout.WEST, personalRadioButton);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, teamRadioButton, 
				0, SpringLayout.VERTICAL_CENTER, viewSwitcher);
		switcherLayout.putConstraint(SpringLayout.WEST, bothRadioButton,
				0, SpringLayout.EAST, personalRadioButton);
		switcherLayout.putConstraint(SpringLayout.VERTICAL_CENTER, bothRadioButton,
				0, SpringLayout.VERTICAL_CENTER, viewSwitcher);



		viewSwitcher.setPreferredSize(new Dimension(300, 50));
		viewSwitcher.setMaximumSize(new Dimension(20000, 50));

		header.add(viewSwitcher);

		final JPanel topButtons = new JPanel();

		final GridLayout experimentLayout = new GridLayout(0, 4);
		topButtons.setLayout(experimentLayout);

		jName = new JButton("<html><font color='white'><b>"
				+ "Name" + "</b></font></html>");
		if(namesort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jName.setIcon(new ImageIcon(img));
				jName.setText("<html><font color='white'><b>"
						+ "Name" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jName.setText("<html><font color='white'><b>"
						+ "Name ^" + "</b></font></html>");
			}
		}
		else if(namesort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jName.setIcon(new ImageIcon(img));
				jName.setText("<html><font color='white'><b>"
						+ "Name" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jName.setText("<html><font color='white'><b>"
						+ "Name v" + "</b></font></html>");
			}
		}


		jName.setBackground(CalendarStandard.CalendarRed);
		jName.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jName.setToolTipText("Click to sort Events by Name.");
		//sort by name
		jName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startDatesort = 0;
				endDatesort = 0;
				dessort = 0;
				Collections.sort(eventList);
				if(namesort == 1){
					namesort = 2;
					Collections.reverse(eventList);
				}
				else if(namesort == 2 || namesort == 0){
					namesort = 1;
				}
				updateView();
			}

		});



		jStartDate = new JButton("<html><font color='white'><b>"
				+ "Start Date" + "</b></font></html>");
		jStartDate.setBackground(CalendarStandard.CalendarRed);

		if(startDatesort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jStartDate.setIcon(new ImageIcon(img));
				jStartDate.setText("<html><font color='white'><b>"
						+ "Start Date" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jStartDate.setText("<html><font color='white'><b>"
						+ "Start Date ^" + "</b></font></html>");
			}
		}
		else if(startDatesort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jStartDate.setIcon(new ImageIcon(img));
				jStartDate.setText("<html><font color='white'><b>"
						+ "Start Date" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jStartDate.setText("<html><font color='white'><b>"
						+ "Start Date v" + "</b></font></html>");
			}
		}

		jStartDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jStartDate.setToolTipText("Click to sort Events by Start Date.");

		// sort by date 
		jStartDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				namesort = 0;
				dessort = 0;
				Collections.sort(eventList, new Comparator<Event>() {

					@Override 
					public int compare(Event e1, Event e2) {
						int reslut = 0;
						if(e1.getStartTime().before(e2.getStartTime()))
						{
							reslut = -1;
						}
						else if(e1.getStartTime().after(e2.getStartTime())) 
						{
							reslut = 1;
						}
						return reslut;
					}
				});
				if(startDatesort == 1){
					startDatesort = 2;
					Collections.reverse(eventList);
				}
				else if(startDatesort == 2 || startDatesort == 0){
					startDatesort = 1;
				}
				updateView();
			}
		});



		jEndDate = new JButton("<html><font color='white'><b>"
				+ "End Date" + "</b></font></html>");
		jEndDate.setBackground(CalendarStandard.CalendarRed);

		if(endDatesort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jEndDate.setIcon(new ImageIcon(img));
				jEndDate.setText("<html><font color='white'><b>"
						+ "End Date" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jEndDate.setText("<html><font color='white'><b>"
						+ "End Date ^" + "</b></font></html>");
			}
		}
		else if(endDatesort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jEndDate.setIcon(new ImageIcon(img));
				jEndDate.setText("<html><font color='white'><b>"
						+ "End Date" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jEndDate.setText("<html><font color='white'><b>"
						+ "End Date v" + "</b></font></html>");
			}
		}

		jEndDate.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		jEndDate.setToolTipText("Click to sort Events by End Date.");

		// sort by date 
		jEndDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				namesort = 0;
				dessort = 0;
				Collections.sort(eventList, new Comparator<Event>() {

					@Override 
					public int compare(Event e1, Event e2) {
						int result = 0;
						if(e1.getEndTime().before(e2.getEndTime()))
						{
							result = -1;
						}
						else if(e1.getEndTime().after(e2.getEndTime())) 
						{
							result = 1;
						}
						return result;
					}
				});
				if(endDatesort == 1){
					endDatesort = 2;
					Collections.reverse(eventList);
				}
				else if(endDatesort == 2 || endDatesort == 0){
					endDatesort = 1;
				}
				updateView();
			}
		});


		jDescription = new JButton("<html><font color='white'><b>"
				+ "Description" + "</b></font></html>");
		jDescription.setBackground(CalendarStandard.CalendarRed);
		if(dessort == 1){
			try {
				final Image img = ImageIO.read(getClass().getResource("UpArrow_Icon.png"));
				jDescription.setIcon(new ImageIcon(img));
				jDescription.setText("<html><font color='white'><b>"
						+ "Description" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jDescription.setText("<html><font color='white'><b>"
						+ "Description ^" + "</b></font></html>");
			}
		}
		else if(dessort == 2){
			try {
				final Image img = ImageIO.read(getClass().getResource("DownArrow_Icon.png"));
				jDescription.setIcon(new ImageIcon(img));
				jDescription.setText("<html><font color='white'><b>"
						+ "Description" + "</b></font></html>");
			} catch (IOException ex) {}
			catch(IllegalArgumentException ex){
				jDescription.setText("<html><font color='white'><b>"
						+ "Description v" + "</b></font></html>");
			}
		}

		jDescription.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		jDescription.setToolTipText("Click to sort Events by Description.");
		jDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				namesort = 0;
				startDatesort = 0;
				endDatesort = 0;
				Collections.sort(eventList, new Comparator<Event>() {

					@Override 
					public int compare(Event e1, Event e2) {
						return e1.getDescription().compareTo(e2.getDescription());
					}
				});
				if(dessort == 1){
					dessort = 2;
					Collections.reverse(eventList);
				}
				else if(dessort == 2 || dessort == 0){
					dessort = 1;
				}
				updateView();
			}
		});



		final GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		topButtons.add(jName, c);
		topButtons.add(jStartDate, c);
		topButtons.add(jEndDate, c);
		topButtons.add(jDescription, c);
		topButtons.setPreferredSize(new Dimension(300, 50));
		topButtons.setMaximumSize(new Dimension(20000, 50));
		final Border loweredbevel1 = BorderFactory.createLoweredBevelBorder();
		topButtons.setBorder(loweredbevel1);
		topButtons.setBorder(new MatteBorder(5, 5, 5, 5, Color.WHITE));

		header.add(topButtons);

		scrollPane.setColumnHeaderView(header);


		for(int i = 0; i < eventList.size(); i++){
			EventViewPanel eventPanel = new EventViewPanel(eventList.get(i));
			Image nameImg;
			Image scaleImg;
			JLabel name = new JLabel(eventList.get(i).getName(), JLabel.LEFT);
			name.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			try {
				if (eventList.get(i).getIsPersonal())
				{
					nameImg = ImageIO.read(getClass().getResource("PersonalEvent_Icon.png"));
					scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					name.setIcon(new ImageIcon(scaleImg));
				}
				else
				{
					nameImg = ImageIO.read(getClass().getResource("TeamEvent_Icon.png"));
					scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					name.setIcon(new ImageIcon(scaleImg));
				}
			} catch (IOException | IllegalArgumentException e) {

			}

			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern("EEEE, MMMM d, y - hh:mm a");

			JLabel dateStart = new JLabel("" + 
					df.format(eventList.get(i).getStartTime().getTime()), JLabel.LEFT);
			dateStart.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			JLabel dateEnd = new JLabel("" + 
					df.format(eventList.get(i).getEndTime().getTime()), JLabel.LEFT);
			dateEnd.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			JLabel description = new JLabel("<HTML>" + 
					eventList.get(i).getDescription() + "</HTML>", JLabel.LEFT);
			description.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

			eventPanel.setLayout(experimentLayout);
			c.anchor = GridBagConstraints.BASELINE_LEADING;
			c.fill = GridBagConstraints.BASELINE_LEADING;
			c.weightx = 1;
			eventPanel.add(name, c);
			eventPanel.add(dateStart, c);
			eventPanel.add(dateEnd, c);
			eventPanel.add(description, c);
			eventPanel.setBackground(CalendarStandard.CalendarYellow);
			eventPanel.setPreferredSize(new Dimension(300, 75));
			eventPanel.setMaximumSize(new Dimension(20000, 75));
			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			eventPanel.setBorder(loweredbevel);
			eventPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
			// To change cursor as it moves over this event pannel
			eventPanel.setToolTipText("Click to Edit or Delete this Event.");
			eventPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 1)
					{
						GUIEventController.getInstance().editEvent(
								((EventViewPanel)e.getComponent()).getEvent());
					}
				}
			});

		}
	}

	/**
	 * Method updateList.
	 */
	public void updateList(){
		eventPanel.removeAll();
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

		calProps = CalendarPropsModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS");
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

}
