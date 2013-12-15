/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.month;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ICalPane;

/**
 * The panel for an entire month
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class MonthPane extends JScrollPane implements ICalPane {
	JPanel mainview;
	MonthDayPane[] days = new MonthDayPane[42];
	GregorianCalendar startdate = null;
	int curmonth = 0;

	/**
	 * Constructor for the month pane
	 * @param acal the date of the month that should be displayed
	 */
	public MonthPane(GregorianCalendar acal) {
		mainview = new JPanel();
		mainview.setMinimumSize(new Dimension(200, 200));
		mainview.setPreferredSize(new Dimension(200, 200));
		mainview.setLayout(new GridLayout(6, 7, 1, 1));
		
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(mainview);
		this.setColumnHeader();

		curmonth = acal.get(Calendar.MONTH);
		final GregorianCalendar itcal = rewindcal(acal);
		startdate = (GregorianCalendar) itcal.clone();

		//adds the individual days to the month
		for (int i = 0; i < 42; i++) {
			days[i] = new MonthDayPane(itcal, curmonth);
			mainview.add(days[i]);
			itcal.add(Calendar.DATE, 1);
		}
	}

	/**
	 * Rewinds the a copy of the given calendar to the first day of the week on
	 * or prior to the beginning of the month.
	 * @param acal the calendar to rewind
	 * @return the modified calendar */
	protected GregorianCalendar rewindcal(GregorianCalendar acal) {
		final GregorianCalendar ret = (GregorianCalendar) acal.clone();

		while (ret.get(Calendar.DATE) != 1) {
			ret.add(Calendar.DATE, -1);
		}
		while (ret.get(Calendar.DAY_OF_WEEK) != ret.getFirstDayOfWeek()) {
			ret.add(Calendar.DATE, -1);
		}

		return ret;
	}

	/**
	 * Sets the column header with the day of the week for that column
	 */
	protected void setColumnHeader() {
		final JViewport port = new JViewport();
		final JPanel panel = new JPanel();
		final String[][] text = {
				{ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
						"Friday", "Saturday" },
				{ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" } };
		final JLabel[] label = new JLabel[7];

		panel.setPreferredSize(new Dimension(10, 40));
		panel.setLayout(new GridLayout(1, 7, 1, 1));
		//sets the labels for each individual days
		for (int i = 0; i < 7; i++) {
			JPanel wrapper = new JPanel();
			wrapper.setLayout(new GridLayout(1, 1));
			wrapper.setBackground(CalendarStandard.CalendarRed);
			wrapper.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
			label[i] = new JLabel("", SwingConstants.CENTER);
			label[i].setFont(CalendarStandard.CalendarFontBold);
			label[i].setForeground(Color.WHITE);
			wrapper.add(label[i]);
			panel.add(wrapper);
		}
		port.setView(panel);

		//listens for resize so that the text can shrink or grow to fill as much as it can
		port.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				boolean toobig = false;
				for (int i = 0; i < 7; i++) {
					label[i].setText(text[0][i]);
					if (label[i].getPreferredSize().getWidth() > label[i].getParent().getSize().getWidth()){
						toobig = true;
					}
				}	
				if (toobig) {
					for (int i = 0; i < 7; i++) {
						label[i].setText(text[1][i]);
					}
				}
			}
		});

		this.setColumnHeader(port);

	}
	/**
	 * Used to retrieve the month pane in JPanel form
	 * @return this in a JPanel wrapper
	 */
	public JPanel getPane() {
		final JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 1));
		apanel.add(this);
		return apanel;
	}

	/**
	 * Displays commitments on the month pane
	 * @param commList List of commitments to be displayed
	 */
	public void displayCommitments(List<Commitment> commList) {
		// if we are supposed to display commitments
		if (commList != null) {

			final CombinedCommitmentList alist = new CombinedCommitmentList(commList);

			final GregorianCalendar ret = new GregorianCalendar();
			ret.setTime(startdate.getTime());

			for (int i = 0; i < 42; i++) {
				try {
					days[i].addCommitments(alist.filter(ret));
				} catch (CalendarException e) {
					e.printStackTrace();
				}
				ret.add(Calendar.DATE, 1);
			}

		} else {
			for (int i = 0; i < 42; i++) {
				days[i].addCommitments(null);
			}
		}
	}
	
	/**
	 * Displays the events on the month 
	 * @param eventList the events to display
	 */
	public void displayEvents(List<Event> eventList) {
		// if we are supposed to display commitments
		if (eventList != null) {

			final CombinedEventList alist = new CombinedEventList(eventList);

			final GregorianCalendar ret = new GregorianCalendar();
			ret.setTime(startdate.getTime());
					
			for (int i = 0; i < 42; i++) {
				try {
					days[i].addEvents(alist.filter(ret));
				} catch (CalendarException e) {
					e.printStackTrace();
				}
				ret.add(Calendar.DATE, 1);
			}

		} else {
			for (int i = 0; i < 42; i++) {
				days[i].addEvents(null);
			}
		}
	}

	/**
	 * A do nothing function because there are no scroll bars used
	 */
	public void updateScrollPosition(int value) {

	}

	/**
	 * A do nothing function that should eventually refresh the page.
	 */
	public void refresh() {

	}
}
