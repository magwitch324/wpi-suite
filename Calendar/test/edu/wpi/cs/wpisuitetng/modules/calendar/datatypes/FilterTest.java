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

import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JANUARY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent.RepeatType;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */

public class FilterTest {	
	private List<Integer> personalCategories1;
	private List<Integer> teamCategories1;

	@Before
	public void setUp() {
		List<Integer> personalCategories1 = new ArrayList<Integer>();
		personalCategories1.add(1);
		personalCategories1.add(3);
		personalCategories1.add(4);
		
		List<Integer> teamCategories1 = new ArrayList<Integer>();
		personalCategories1.add(2);
		personalCategories1.add(5);
		personalCategories1.add(6);
	}

	/**
	 * Tests to ensure that a new filter is created with default values
	 */
	@Test
	public void defaultConstructorTest(){
		final Filter testFilter = new Filter();
		assertEquals("", testFilter.getName());
		assertEquals(new ArrayList<Integer>(), testFilter.getActivePersonalCategories()); 
		assertEquals(new ArrayList<Integer>(), testFilter.getActiveTeamCategories());	
	}
	
	/**
	 * Tests to ensure that the detailed constructor fills in fields correctly
	 */
	@Test
	public void mainConstructorTest(){
		final Filter testFilter = new Filter("test", personalCategories1,teamCategories1);
		assertEquals("test", testFilter.getName());
		assertEquals(personalCategories1, testFilter.getActivePersonalCategories()); 
		assertEquals(teamCategories1, testFilter.getActiveTeamCategories());	
	}

}
