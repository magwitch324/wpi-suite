/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andrew Hurle
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.monthtab.CalendarMonth;
import edu.wpi.cs.wpisuitetng.modules.calendar.yeartab.CalendarYear;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;


/**
 * This panel will show notifications, search widgets.
 */
@SuppressWarnings("serial")
public class TeamCalendarTab extends JSplitPane {
	
	//private IterationCalendar calendarView;
	
	private JButton nextYear;
	private JButton prevYear;
	private JButton nextMonth;
	private JButton prevMonth;
	private JButton today;
	
	private JPanel  calMonth;
	
	private JLabel selectionLabel;
	
	public TeamCalendarTab() {
		JScrollPane scrollPane = new JScrollPane();
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout());
		
		JPanel buttonPanel = new JPanel();
		
		nextYear = new JButton(">>");
		nextYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		nextMonth = new JButton(">");
		nextMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		today = new JButton ("Today");
		today.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		prevMonth = new JButton("<");
		prevMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		prevYear = new JButton("<<");
		prevYear.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		//setupButtonListeners();
		
		buttonPanel.add(prevYear);
		buttonPanel.add(prevMonth);
		buttonPanel.add(today);
		buttonPanel.add(nextMonth);
		buttonPanel.add(nextYear);

		contentPanel.add(buttonPanel, "alignx center, dock north");
		CalendarYear calYear = new CalendarYear(2013);
			contentPanel.add(calYear, "alignx center, dock north");
		//}

		
		scrollPane.setViewportView(contentPanel);
		this.setRightComponent(scrollPane);
		this.setDividerLocation(180);
	}
	
	/*
	private void setupButtonListeners()
	{
		nextYear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				nextYear();
			}	
		});
		
		prevYear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				previousYear();
			}
		});
		
		today.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				today();
			}
		});
		
		prevMonth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				previousMonth();
			}
		});
		
		nextMonth.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				nextMonth();
			}
		});
		
	}
	*/
	/*
	/**
	 * Switches the calendar to the previous year.

	private void previousYear()
	{
		Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.YEAR, -1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
	
	
	/**
	 * Switches the calendar to the current date
	
	private void today()
	{
		Calendar cal = Calendar.getInstance();
		calendarView.setFirstDisplayedDay(cal.getTime());
	}

	/**
	 * Switches the calendar to the next month.

	private void nextMonth()
	{
		Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.MONTH, 1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
	
	/**
	 * Switches the calendar to the previous month.

	private void previousMonth()
	{
		Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.MONTH, -1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
	
	/**
	 * Switches the calendar to the next year.

	private void nextYear()
	{
		Calendar cal = calendarView.getCalendar();
		cal.add(Calendar.YEAR, +1);
		calendarView.setFirstDisplayedDay(cal.getTime());
	}
*/
}