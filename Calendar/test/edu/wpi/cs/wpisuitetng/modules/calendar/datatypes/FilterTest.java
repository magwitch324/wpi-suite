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
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */

public class FilterTest {
	private List<Integer> personalCategories1;
	private List<Integer> personalCategories2;
	private List<Integer> teamCategories1;
	private List<Integer> teamCategories2;
	
	private Filter filter1;
	private Filter filter2;

	@Before
	public void setUp() {
		personalCategories1 = new ArrayList<Integer>();
		personalCategories1.add(1);
		personalCategories1.add(3);
		personalCategories1.add(4);
		
		personalCategories2 = new ArrayList<Integer>();
		personalCategories2.add(1);
		personalCategories2.add(2);
		personalCategories2.add(5);
		
		teamCategories1 = new ArrayList<Integer>();
		teamCategories1.add(2);
		teamCategories1.add(5);
		teamCategories1.add(6);
		
		teamCategories2 = new ArrayList<Integer>();
		teamCategories2.add(3);
		teamCategories2.add(4);
		teamCategories2.add(7);
		
		filter1 = new Filter("Filter1", personalCategories1, teamCategories1);
		filter2 = new Filter("Filter2", personalCategories1, teamCategories1);
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
		assertEquals(0, testFilter.getID());
	}
	
	/**
	 * Tests to ensure that the detailed constructor fills in fields correctly
	 */
	@Test
	public void mainConstructorTest(){
		final Filter testFilter = new Filter("test", personalCategories1, teamCategories1);
		
		assertEquals("test", testFilter.getName());
		assertEquals(personalCategories1, testFilter.getActivePersonalCategories());
		assertEquals(teamCategories1, testFilter.getActiveTeamCategories());
	}
	/**
	 * Ensures that setters work correctly
	 */
	@Test
	public void setterConstructorTest(){
		final Filter testFilter = new Filter("test", personalCategories1, teamCategories1);
		testFilter.setName("setter tests");
		testFilter.setActivePersonalCategories(personalCategories2);
		testFilter.setActiveTeamCategories(teamCategories2);
		testFilter.setID(1);
		
		assertEquals("setter tests", testFilter.getName());
		assertEquals(personalCategories2, testFilter.getActivePersonalCategories());
		assertEquals(teamCategories2, testFilter.getActiveTeamCategories());
		assertEquals(1, testFilter.getID());
	}
	
	@Test
	public void toStringTest() {
		assertEquals("Filter1", filter1.toString());
	}
	
	/**
	 * Tests to ensure that compare function work correctly
	 */
	@Test
	public void compareTest(){
		assertEquals(-1, filter1.compare(filter1, filter2));
		filter1.setName("z");
		assertEquals(20, filter1.compare(filter1, filter2));
		filter1.setName("filter2");
		assertEquals(0, filter1.compare(filter1, filter2));
	}
	
	@Test
	public void setNameMoreThan100charsTest() {
		String sect = "aaaaaaaaaaaaaaaaaaaaaaaaa";
		String longName = sect.concat(sect).concat(sect).concat(sect).concat(sect);
		filter1.setName(longName);
		assertEquals(100, filter1.getName().length());
	}

}
