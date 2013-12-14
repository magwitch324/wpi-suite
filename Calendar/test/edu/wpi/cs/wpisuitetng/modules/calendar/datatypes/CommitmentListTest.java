/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.datatypes;

import static org.junit.Assert.assertEquals;
import static java.util.Calendar.*;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;

/**
 */
public class CommitmentListTest {

	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */
	
	private static CommitmentList commitments;
	private final GregorianCalendar start = new GregorianCalendar();
	private final GregorianCalendar end   = new GregorianCalendar();
	
	private final static GregorianCalendar today = 
			new GregorianCalendar(2013, NOVEMBER, 23, 12, 00, 00);
	
	/**
	 * Method setup.
	 */
	@BeforeClass
	public static void setup() {
		commitments = new CommitmentList();
		commitments.add(new Commitment("Last Year",
				new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00),
				"A commitment from last year", 1, true));
		commitments.add(new Commitment("Last Month", 
				new GregorianCalendar(2013, OCTOBER, 12, 12, 00, 00),
				"A commitment from last month", 1, true));
		commitments.add(new Commitment("Last Week", 
				new GregorianCalendar(2013, NOVEMBER, 16, 12, 00, 00), 
				"A commitment for a week ago", 1, true));
		commitments.add(new Commitment("Today", today, "A commitment from today", 1, true));
		commitments.add(new Commitment("Next Week",
				new GregorianCalendar(2013, NOVEMBER, 24, 12, 00, 00),
				"A commitment for next week (tomorrow)", 1, true));
		commitments.add(new Commitment("Next Month",
				new GregorianCalendar(2013, DECEMBER, 23, 12, 00, 00),
				"A commitment for next month", 1, true));
		commitments.add(new Commitment("Next Year", 
				new GregorianCalendar(2014, JANUARY, 1, 12, 00, 00), 
				"A commitment for next year", 1, true));
		
		printlist(commitments.getCommitments());
		
	}
	
	/**
	 * Method filterAroundTwoCalendars.
	 */
	@Test
	public void filterAroundTwoCalendars() {
		start.set(2013, NOVEMBER, 18);
		end.set(2013, NOVEMBER, 25);
		
		final List<Commitment> newData = commitments.filter(start, end);
//		printlist(newData);
		assertEquals(2, newData.size());
	}
	
	/**
	 * Method filterAroundLargeSection.
	 */
	@Test
	public void filterAroundLargeSection() {
		start.set(2000, NOVEMBER, 12);
		end.set(2100, JULY, 31);
		final List<Commitment> newData = commitments.filter(start, end);
		assertEquals(7, newData.size());
	}
	
	/**
	 * Method filterAroundWeek.
	 * @throws CalendarException
	 */
	@Test
	public void filterAroundWeek() throws CalendarException {
		System.out.println("Filter around week...");
		final List<Commitment> newData = commitments.filter(today, WEEK_OF_YEAR);
		printlist(newData);
		assertEquals(1, newData.size());
	}
	
	/**
	 * Method filterAroundMonth.
	 * @throws CalendarException
	 */
	@Test
	public void filterAroundMonth() throws CalendarException {
		System.out.println("Filter around month...");
		final List<Commitment> newData = commitments.filter(today, MONTH);
		assertEquals(3, newData.size());
	}
	
	/**
	 * Method filterAroundYear.
	 * @throws CalendarException
	 */
	@Test
	public void filterAroundYear() throws CalendarException {
		System.out.println("Filter around year...");
		final List<Commitment> newData = commitments.filter(today, YEAR);
		assertEquals(5, newData.size());
	}
	
	// Helper function
	/**
	 * Method printlist.
	 * @param commits List<Commitment>
	 */
	public static void printlist(List<Commitment> commits) {
		System.out.println("Commitments: ");
		
		for (Commitment com : commits) {
			System.out.println(com.getName());
		}
		
		System.out.println("\n");
	}
	
	/**
	 * Method printcalendar.
	 * @param cal GregorianCalendar
	 */
	public void printcalendar(GregorianCalendar cal) {
		final String dayName = cal.getDisplayName(
				GregorianCalendar.DAY_OF_WEEK, LONG, Locale.ENGLISH);
		final int dayNum = cal.get(DAY_OF_MONTH);
		final String monthName = cal.getDisplayName(GregorianCalendar.MONTH, LONG, Locale.ENGLISH);
		final int year = cal.get(YEAR);
		System.out.println(dayName + ", " + monthName + " " + dayNum + ", " + year);
	}
	
}
