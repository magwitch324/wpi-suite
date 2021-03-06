/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.day;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import org.jdesktop.swingx.border.MatteBorderExt;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.HourDisplayPort;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ICalPane;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar.types;

/**
 * DayPane contains a scroll pane which contains
 * a main panel for displaying the day detailed pane.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
 public class DayPane extends JPanel implements ICalPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel = new JPanel();
	GregorianCalendar day;
	private final DayDetailedPane daypane;
	private SpringLayout layout;
	private final JScrollPane scrollPane;
	private final JLabel headerlabel;
	
	/**
	 * Create the panel.
	 * @param datecalendar GregorianCalendar
	 */
	public DayPane(GregorianCalendar datecalendar) {
		
		day = new GregorianCalendar();
		day.setTime(datecalendar.getTime());
		
		setLayout(new GridLayout(1, 1));


		// HOURS
		scrollPane = new JScrollPane(mainPanel, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setMinimumSize(new Dimension(300, 300));
		scrollPane.setBackground(CalendarStandard.CalendarRed);
		
		scrollPane.getViewport().addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				final int height = mainPanel.getHeight();
				final int width = scrollPane.getViewport().getWidth();
				mainPanel.setSize(new Dimension(width, height));
				mainPanel.setPreferredSize(new Dimension(width, height));
			}
		});
		
		// Sets the UPPER LEFT corner box
		final JPanel cornerBoxUL = new JPanel();
		cornerBoxUL.setBackground(CalendarStandard.CalendarRed);
		cornerBoxUL.setBorder(new MatteBorderExt(0, 0, 2, 0, Color.GRAY));
		scrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, cornerBoxUL);
		
		// Sets the UPPER RIGHT corner box
		final JPanel cornerBoxUR = new JPanel();
		cornerBoxUR.setBackground(CalendarStandard.CalendarRed);
		cornerBoxUR.setBorder(new MatteBorderExt(0, 0, 2, 0, Color.GRAY));
		scrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, cornerBoxUR);
		add(scrollPane);


		// Create the header panel
		final JPanel header = new JPanel();
		header.setLayout(new GridLayout(1, 1));
		header.setBackground(CalendarStandard.CalendarRed);
		header.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
		header.setPreferredSize(new Dimension(10, 40));

		headerlabel = new JLabel("Event", SwingConstants.CENTER);
		headerlabel.setFont(CalendarStandard.CalendarFontBold.deriveFont(14));
		headerlabel.setForeground(Color.WHITE);
		header.add(headerlabel);
		
		scrollPane.setColumnHeaderView(header);
		
		mainPanel.setLayout(new GridLayout(1, 1));
		daypane = new DayDetailedPane(day, AbCalendar.types.DAY);
		mainPanel.add(daypane);
		mainPanel.setBackground(CalendarStandard.CalendarYellow);
		mainPanel.setPreferredSize(new Dimension(30, 2000));

		//sets the hours panel
		scrollPane.setRowHeader(new HourDisplayPort(mainPanel));

		//adds a mouse listener for scroll check
		scrollPane.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseExited(MouseEvent e) {
				GUIEventController.getInstance().setScrollBarValue(
						((JScrollPane)e.getSource()).getVerticalScrollBar().getValue());
			}

		});
		
		refresh();
	}


	public void refresh() {
   	 	scrollPane.getVerticalScrollBar().setValue(
   	 			GUIEventController.getInstance().getScrollBarValue());
	}


	/** Displays commitments on DetailedDay
	 * @param commList List of commitments to be displayed
	
	 */
	public void displayCommitments(List<Commitment> commList) {
		if(commList == null){
			headerlabel.setText("<html><font color='white'><b>"
					+ "Events" + "</b></font></html>");
		}
		else{
			headerlabel.setText("<html><font color='white'><b>"
					+ "Events and Commitments" + "</b></font></html>");
		}
		//if we are supposed to display commitments
		daypane.displayCommitments(commList);
	}
	
	/**
	 * Method displayEvents.
	 * @param eventList List<Event>
	 */
	public void displayEvents(List<Event> eventList) {
		//if we are supposed to display commitments
		daypane.displayEvents(eventList);
	}

	@Override
	public JPanel getPane() {
		return this;
	}
	
	public void updateScrollPosition(int value){
		scrollPane.getVerticalScrollBar().setValue(value);
	}
}