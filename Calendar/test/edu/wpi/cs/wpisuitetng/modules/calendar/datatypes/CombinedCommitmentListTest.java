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

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import static java.util.Calendar.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class CombinedCommitmentListTest {
	/*
	 * NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE NOTE
	 * For testing purposes, the "today" date will always be 
	 * NOVEMBER 23, 2013, the day of this testing class's creation
	 */

	private static CombinedCommitmentList combinedCommitmentList;
	private static List<Commitment> commitmentList;
	
	private static final GregorianCalendar today = 
			new GregorianCalendar(2013, NOVEMBER, 23, 12, 00, 00);
	private final Commitment lastYear   = 
			new Commitment("Last Year", new GregorianCalendar(2012, JANUARY, 30, 12, 00, 00), 
					"A commitment from last year", 1, true);
	private final Commitment todayCommitment = 
			new Commitment("Today", today, "A commitment from today", 1, true);
	private final Commitment nextWeek   = 
			new Commitment("Next Week", new GregorianCalendar(2013, NOVEMBER, 24, 12, 00, 00), 
					"A commitment for next week (tomorrow)", 1, true);
	private final Commitment nextMonth  = 
			new Commitment("Next Month", new GregorianCalendar(2013, DECEMBER, 23, 12, 00, 00),
					"A commitment for next month", 1, true);
	/**
	 * Method setup.
	 */
	@Before
	public void setup() {
		commitmentList = new ArrayList<Commitment>();
		commitmentList.add(lastYear);
		commitmentList.add(nextWeek);
		commitmentList.add(nextMonth);
	}
	
	
	/*
	 * Test to ensure addEmptyList works correctly
	 */
	/**
	 * Method addEmptyListTest.
	 */
	@Test
	public void addEmptyListTest() {
		combinedCommitmentList = new CombinedCommitmentList();
		combinedCommitmentList.add(todayCommitment);
		assertEquals(1, combinedCommitmentList.getSize());
		assertEquals("Today", combinedCommitmentList.getElementAt(0).getName());
	}
	
	/*
	 * Test to ensure addNonEmptyList works correctly
	 */
	/**
	 * Method addNonEmptyListTest.
	 */
	@Test
	public void addNonEmptyListTest() {
		combinedCommitmentList = new CombinedCommitmentList(commitmentList);
		combinedCommitmentList.add(todayCommitment);
		assertEquals(4, combinedCommitmentList.getSize());
		assertEquals("Today", combinedCommitmentList.getElementAt(2).getName());
	}

}
