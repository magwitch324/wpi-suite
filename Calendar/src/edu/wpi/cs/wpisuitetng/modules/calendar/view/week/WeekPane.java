/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.week;

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
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.jdesktop.swingx.border.MatteBorderExt;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.HourDisplayPort;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ICalPane;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar.types;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.day.DayDetailedPane;

/**
 * Week class used for displaying an entire week
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class WeekPane extends JPanel implements ICalPane {
	JPanel mainPanel = new JPanel();

	JScrollPane scrollPane = new JScrollPane(mainPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	GregorianCalendar mydate;
	DayDetailedPane[] day = new DayDetailedPane[7];

	/**
	 * Constructor for creating the week pane
	 * @param datecalendar GregorianCalendar
	 */
	public WeekPane(GregorianCalendar datecalendar) {
		mydate = new GregorianCalendar();
		mydate.setTime(datecalendar.getTime());
		mydate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		setLayout(new GridLayout(1, 1));
		this.add(scrollPane);

		scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setMinimumSize(new Dimension(500, 300));
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


		mainPanel.setPreferredSize(new Dimension(30, 2000));
		mainPanel.setLayout(new GridLayout(1, 7));

		final GregorianCalendar temp = new GregorianCalendar();
		temp.setTime(mydate.getTime());
		for(int i = 0; i < 7; i++){
			day[i] = new DayDetailedPane(temp, AbCalendar.types.WEEK);
			mainPanel.add(day[i]);
			day[i].addMouseListener(new AMouseEvent(temp));
			day[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			temp.add(Calendar.DATE, 1);
		}
		
		//sets the column with the days
		scrollPane.setColumnHeader(getColumnheader());
		//sets the hours
		scrollPane.setRowHeader(new HourDisplayPort(mainPanel)); 

		refresh();
		scrollPane.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseExited(MouseEvent e) {
				GUIEventController.getInstance().setScrollBarValue(
						((JScrollPane)e.getSource()).getVerticalScrollBar().getValue());
			}

		});

		
	}

	/**
	 * Set up header section for the week pane composed of day names and dates
	 * @return
	 */
	protected JViewport getColumnheader(){
		final JViewport port = new JViewport();
		final JLabel[] labels = new JLabel[7];
		final JPanel apane = new JPanel();
		port.setView(apane);
		apane.setBackground(CalendarStandard.CalendarRed);
		apane.setBorder(new EmptyBorder(5, 0, 10, 0));
		apane.setLayout(new GridLayout(1, 7));

		final String[][] weekdays = {{"Sunday, ", "Monday, ", "Tuesday, ",
			"Wednesday, ", "Thursday, ", "Friday, ", "Saturday, " },
			{"Sun, ", "Mon, ", "Tue, ", "Wed, ", "Thu, ", "Fri, ", "Sat, " },
			{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }};

		final GregorianCalendar acal = (GregorianCalendar)mydate.clone();
		for(int i=0; i < 7; i++) {
			weekdays[0][i] += acal.get(Calendar.DATE);
			weekdays[1][i] += acal.get(Calendar.DATE);

			labels[i] = new JLabel(weekdays[0][i], SwingConstants.CENTER);
			labels[i].setFont(CalendarStandard.CalendarFontBold);
			labels[i].setForeground(Color.WHITE);
			labels[i].addMouseListener(new AMouseEvent(acal));
			apane.add(labels[i]);
			acal.add(Calendar.DATE, 1);
		}


		port.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				int touse = 0;
				do{
					for(int i = 0; i < 7; i++){
						labels[i].setText(weekdays[touse][i]);
					}
					touse++;
				}while(apane.getPreferredSize().width > port.getSize().getWidth());

			}
		});


		apane.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
		port.setPreferredSize(new Dimension(10, 40));

		return port;
	}

	@Override
	public JPanel getPane() {
		return this;
	}

	/** Displays commitments on DetailedDays
	 * @param commList List of commitments to be displayed
	 */
	public void displayCommitments(List<Commitment> commList) {
		if(commList == null){
			for(int i = 0; i < 7; i++){
				day[i].displayCommitments(null);
			}
		}
		else{
			final GregorianCalendar temp = new GregorianCalendar();
			final CombinedCommitmentList alist = new CombinedCommitmentList(commList);
			temp.setTime(mydate.getTime());
			for(int i = 0; i < 7; i++){
				try{
					day[i].displayCommitments(alist.filter(temp));
				}
				catch(CalendarException e){}
				temp.add(Calendar.DATE, 1);
			}
		}
	}

	/**
	 * Method displayEvents.
	 * @param eventList the events that should be displayed
	 */
	public void displayEvents(List<Event> eventList) {
		if(eventList == null){
			for(int i = 0; i < 7; i++){
				day[i].displayEvents(null);
			}
		}
		else{
			final GregorianCalendar temp = new GregorianCalendar();
			final CombinedEventList alist = new CombinedEventList(eventList);
			temp.setTime(mydate.getTime());
			for(int i = 0; i < 7; i++){
				try{
					day[i].displayEvents(alist.filter(temp));
				}
				catch(CalendarException e){}
				temp.add(Calendar.DATE, 1);
			}
		}
	}

	/**
	 * Sets the scroll bar value from the controller
	 */
	public void refresh() {
		scrollPane.getVerticalScrollBar().setValue(
				GUIEventController.getInstance().getScrollBarValue());
	}



	/**
	 * Mouse event listening for double click so it can switch to day
	 * @author CS Anonymous
	 * @version $Revision: 1.0 $
	 */
	protected class AMouseEvent extends MouseAdapter{
		GregorianCalendar adate = new GregorianCalendar();

		/**
		 * Constructor for AMouseEvent.
		 * @param adate GregorianCalendar
		 */
		public AMouseEvent(GregorianCalendar adate){
			this.adate.setTime(adate.getTime());
		}

		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() > 1){
				//save scroll bar location
				GUIEventController.getInstance().setScrollBarValue(
						scrollPane.getVerticalScrollBar().getValue());
				//switch to day view
				GUIEventController.getInstance().switchView(adate, AbCalendar.types.DAY);
			}
		}
	}

	public void updateScrollPosition(int value){
		scrollPane.getVerticalScrollBar().setValue(value);
	}

}
