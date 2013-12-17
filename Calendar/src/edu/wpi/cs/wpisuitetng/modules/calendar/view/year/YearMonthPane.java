/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.year;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
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

/**
 * The inner class for a month representation on the year view.
 * Creates the header for the month name, the weekday names, and then the
 * 42 individual days.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class YearMonthPane extends JPanel{
	GregorianCalendar monthpanestart;
	GregorianCalendar monthstart;
	YearDayPane[] daypanes = new YearDayPane[42];
	List<Commitment> commlist = null;
	static final int extra_height = 20;//extra padding given to proffered size
	static final int extra_width = 20;//extra padding given to proffered size
	Color defaultbackground = Color.WHITE;
	
	/**
	 * the constructor for the month pane on the year pane
	 * @param acal the calendar holding the month to be displayed
	 */
	public YearMonthPane(GregorianCalendar acal){
		monthstart = (GregorianCalendar)acal.clone();
		monthstart.set(Calendar.DATE, 1);
		monthstart.get(Calendar.DATE);
		monthpanestart = (GregorianCalendar)monthstart.clone();
		monthpanestart.set(Calendar.DAY_OF_WEEK, monthstart.getFirstDayOfWeek());
		
		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		//this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
		
		//Creates the month lbl and a wrapper and places it in this at the top
		final JLabel monthlbl = new JLabel(acal.getDisplayName(
				Calendar.MONTH, Calendar.LONG, Locale.getDefault()), SwingConstants.CENTER);
		monthlbl.addMouseListener(new AMouseMonthEvent(acal));
		final JPanel temppane = new JPanel();
		temppane.setLayout(new GridLayout(1, 1));
		temppane.setBackground(CalendarStandard.CalendarRed);
		
		monthlbl.setBackground(CalendarStandard.CalendarRed);
		monthlbl.setFont(CalendarStandard.CalendarFont);
		monthlbl.setForeground(Color.WHITE);
		layout.putConstraint(SpringLayout.WEST, temppane, 1, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, temppane, 2, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, temppane, -1, SpringLayout.EAST, this);
		temppane.add(monthlbl);
		temppane.setPreferredSize(new Dimension(10, 20));
		this.add(temppane);
		
		//Get the weekdays pane and sets below the month lbl wrapper
		final JPanel names = getDayNames();
		layout.putConstraint(SpringLayout.WEST, names, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, names, 0, SpringLayout.SOUTH, temppane);
		layout.putConstraint(SpringLayout.EAST, names, 0, SpringLayout.EAST, this);
		this.add(names);
		
		//Create the days pane and set the grid layout
		final JPanel days = new JPanel();
		days.setLayout(new GridLayout(6, 7, 1, 1));
		
		final GregorianCalendar temp = (GregorianCalendar)monthpanestart.clone();
		
		//add the individual days to the view
		for(int i = 0; i < 42; i++){
			daypanes[i] = new YearDayPane(temp, monthstart.get(Calendar.MONTH));
			days.add(daypanes[i]);
			temp.add(Calendar.DATE, 1);
		}

		layout.putConstraint(SpringLayout.WEST, days, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, days, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.NORTH, days, 0, SpringLayout.SOUTH, names);
		layout.putConstraint(SpringLayout.EAST, days, 0, SpringLayout.EAST, this);
		days.setBackground(defaultbackground);
		this.add(days);
		
		//sets the proffered size based on all the children
		int height = 0;
		height += daypanes[0].getPreferredSize().getHeight() * 6;
		height += names.getPreferredSize().getHeight();
		height += monthlbl.getPreferredSize().getHeight();
		height += extra_height;
		int width = (int)names.getPreferredSize().getWidth();
		width += extra_width;
		this.setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Creates and returns the weekday names to be used by the constructor
	 * @return the panel with the weekday names
	 */
	protected JPanel getDayNames(){
		final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		final JPanel apane = new JPanel();
		apane.setLayout(new GridLayout(1, 7));
		double width = 0;
		JLabel lbl = null;
		for(int i = 0; i < 7; i++){
			lbl = new JLabel(days[i], SwingConstants.CENTER);
			lbl.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN, 10));
			width = width > lbl.getPreferredSize().getWidth()? 
					width : lbl.getPreferredSize().getWidth();
			lbl.setBackground(defaultbackground);
			apane.add(lbl);
		}
		apane.setPreferredSize(new Dimension(
				(int)width * 9, (int)lbl.getPreferredSize().getHeight()));
		apane.setBackground(defaultbackground);
		return apane;
	}
	
	/**
	 * Sends proper commitments down to the days
	 * @param commList the commitments to display
	 */
	public void displayCommitments(List<Commitment> commList) {
		// if we are supposed to display commitments
		if (commList != null) {

			final CombinedCommitmentList alist = new CombinedCommitmentList(commList);

			final GregorianCalendar ret = (GregorianCalendar) monthpanestart.clone();
			
			for (int i = 0; i < 42; i++) {
				try {
					daypanes[i].displayCommitments(alist.filter(ret));
				} catch (CalendarException e) {
					e.printStackTrace();
				}
				ret.add(Calendar.DATE, 1);
			}
			
		} else {
			for (int i = 0; i < 42; i++) {
				daypanes[i].displayCommitments(null);
			}
		}
	}
	
	/**
	 * Sends proper events down to the days
	 * @param eventList the events to display
	 */
	public void displayEvents(List<Event> eventList) {
		// if we are supposed to display commitments
		if (eventList != null) {
			final CombinedEventList alist = new CombinedEventList(eventList);

			final GregorianCalendar ret = (GregorianCalendar) monthpanestart.clone();
			
			for (int i = 0; i < 42; i++) {
				try {
					daypanes[i].displayEvents(alist.filter(ret));
				} catch (CalendarException e) {
					e.printStackTrace();
				}
				ret.add(Calendar.DATE, 1);
			}
		} else {
			for (int i = 0; i < 42; i++) {
				daypanes[i].displayEvents(null);
			}
		}
	}
	
	/**
	 * Mouse listener for whether the month was double clicked
	 */
	protected class AMouseMonthEvent extends MouseAdapter{
		GregorianCalendar adate = new GregorianCalendar();
		
		/**
		 * Constructor for AMouseMonthEvent.
		 * @param adate GregorianCalendar
		 */
		public AMouseMonthEvent(GregorianCalendar adate){
			this.adate.setTime(adate.getTime());

		}

	    public void mouseClicked(MouseEvent e) {
	    	if(e.getClickCount() > 1){
	    		//switch to day view
	    		GUIEventController.getInstance().switchView(adate, AbCalendar.types.MONTH);
	    	}
	    }
	}
	
}
