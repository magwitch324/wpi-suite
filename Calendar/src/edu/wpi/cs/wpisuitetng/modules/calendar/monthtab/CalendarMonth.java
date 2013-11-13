/*********************************************************************
 * Copyright (c) 2013
 * 
 * All rights reserved. This program and accompanying materials are 
 * made available under the terms of the Eclipse Public License v10 
 * which accompanies this distribution, and it's available at 
 * http://www.eclipse.org/legal/elp-v10.html
 *
 * Contributors:
 *    geierqi
 *********************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.monthtab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.*;

/**
 * Description
 *
 * @author geierqi
 * @version Nov 10, 2013
 */
public class CalendarMonth extends JPanel {

	private GregorianCalendar cal = new GregorianCalendar();
	private int realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
	private int realMonth = cal.get(GregorianCalendar.MONTH); //Get month
	private int realYear = cal.get(GregorianCalendar.YEAR); //Get year
	//private JPanel MonthPane;
	
	private int year;
	private int month;
	private int day;
	private int date;
	private String monthName;

	public CalendarMonth(int year, int month) {
		this.year = year;
		this.month = month;
		int numberOfDays, startOfMonth; //Number Of Days, Start Of Month
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);
		numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		monthName = Month.getMonthName(month);
		
		this.setBounds(100, 100, 543, 396);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel MonthPanel = new JPanel();
		this.add(MonthPanel);
		
		JLabel lblNewLabel = new JLabel(monthName + ", " + year);
		MonthPanel.add(lblNewLabel);
		
		JPanel DayPanel = new JPanel();
		DayPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(DayPanel);
		DayPanel.setLayout(new GridLayout(0, 7, 0, 0));
		
		JLabel label = new JLabel("Su");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		DayPanel.add(label);
		
		JLabel label_1 = new JLabel("Mo");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Arial", Font.PLAIN, 20));
		DayPanel.add(label_1);
		
		JLabel label_2 = new JLabel("Tu");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Arial", Font.PLAIN, 20));
		DayPanel.add(label_2);
		
		JLabel label_3 = new JLabel("We");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Arial", Font.PLAIN, 20));
		DayPanel.add(label_3);
		
		JLabel label_4 = new JLabel("Th");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Arial", Font.PLAIN, 20));
		DayPanel.add(label_4);
		
		JLabel label_5 = new JLabel("Fr");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Arial", Font.PLAIN, 20));
		DayPanel.add(label_5);
		
		JLabel label_6 = new JLabel("Sa");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Arial", Font.PLAIN, 20));
		DayPanel.add(label_6);
		
		JPanel datePanel = new JPanel();
		datePanel.setBorder(null);
		this.add(datePanel);
		datePanel.setLayout(new GridLayout(6, 7, 0, 0));
		
		JButton button;
		//Draw calendar
		for (int i = 1; i <= 42; i++){
			String date = "";
			if(i >= startOfMonth && i <= numberOfDays + startOfMonth - 1) {
				date = String.valueOf(i - startOfMonth + 1);
			}
			button = new JButton(date);
			button.setFont(new Font("SimSun", Font.PLAIN, 12));
			button.setSize(new Dimension(15, 15));
			datePanel.add(button);
		}
		
		
	}

	public CalendarMonth previousYear()
	{
		return new CalendarMonth(this.year - 1, this.month);
	}
	
	public CalendarMonth previousMonth()
	{
		return new CalendarMonth(this.year, this.month - 1);
	}
	
	public CalendarMonth today()
	{
		return new CalendarMonth(this.year + 1, this.month);
	}
	
	public CalendarMonth nextYear()
	{
		return new CalendarMonth(this.year + 1, this.month);
	}
	
	public CalendarMonth nextMonth()
	{
		return new CalendarMonth(this.year, this.month + 1);
	}
	

	

	
	private enum Month {
		
	    January(0),
		February(1),
		March(2),
		April(3),
		May(4),
		June(5),
		July(6),
		August(7),
		September(8),
		October(9),
		November(10),
		December(11);
		
		private final int monthNumber;
		
		private Month(int monthNumber) {
			this.monthNumber = monthNumber;
		}
		
		private static final Map<Integer, Month> intToNameMap = new HashMap<Integer, Month>();
		static {
		    for (Month name : Month.values()) {
		        intToNameMap.put(name.monthNumber, name);
		    }
		}

		public static String getMonthName(int month) {
			String result;
		    Month name = intToNameMap.get(Integer.valueOf(month));
		    result = (name == null) ? null : name.toString();
		    return result;
		}
		
		
	}
	
	private enum Day {
		
	    Monday(1),
		Tuesday(2),
		Wendesday(3),
		Thursday(4),
		Friday(5),
		Saturday(6),
		Sunday(7);
		
		private final int dayNumber;
		
		private Day(int dayNumber) {
			this.dayNumber = dayNumber;
		}
		
		private static final Map<Integer, Day> intToNameMap = new HashMap<Integer, Day>();
		static {
		    for (Day name : Day.values()) {
		        intToNameMap.put(name.dayNumber, name);
		    }
		}

		public static String getDayName(int day) {
			String result;
		    Day name = intToNameMap.get(Integer.valueOf(day));
		    result = (name == null) ? null : name.toString();
		    return result;
		}
	}
}
