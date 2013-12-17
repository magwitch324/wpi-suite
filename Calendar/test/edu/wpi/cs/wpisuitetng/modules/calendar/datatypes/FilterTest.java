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
import javax.annotation.processing.Filer;
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

	@Before
	public void setUp() {
		final List<Integer> personalCategories1 = new ArrayList<Integer>();
		personalCategories1.add(1);
		personalCategories1.add(3);
		personalCategories1.add(4);
		
		final List<Integer> personalCategories2 = new ArrayList<Integer>();
		personalCategories1.add(1);
		personalCategories1.add(2);
		personalCategories1.add(5);
		
		final List<Integer> teamCategories1 = new ArrayList<Integer>();
		personalCategories1.add(2);
		personalCategories1.add(5);
		personalCategories1.add(6);
		
		final List<Integer> teamCategories2 = new ArrayList<Integer>();
		personalCategories1.add(3);
		personalCategories1.add(4);
		personalCategories1.add(7);
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
		final Filter testFilter = new Filter("test", personalCategories1,teamCategories1);
		
		assertEquals("test", testFilter.getName());
		assertEquals(personalCategories1, testFilter.getActivePersonalCategories());
		assertEquals(teamCategories1, testFilter.getActiveTeamCategories());
	}
	/**
	 * Ensures that setters work correctly
	 */
	@Test
	public void setterConstructorTest(){
		final Filter testFilter = new Filter("test", personalCategories1,teamCategories1);
		testFilter.setName("setter tests");
		testFilter.setActivePersonalCategories(personalCategories2);
		testFilter.setActiveTeamCategories(teamCategories2);
		testFilter.setID(1);
		
		assertEquals("setter tests", testFilter.getName());
		assertEquals(personalCategories2, testFilter.getActivePersonalCategories());
		assertEquals(teamCategories2, testFilter.getActiveTeamCategories());
		assertEquals(1, testFilter.getID());
	}
	
	/**
	 * Tests to ensure that compare function work correctly
	 */
	@Test
	public void compareTest(){
		final Filter F1 = new Filter("Filter1", personalCategories1,teamCategories1);
		final Filter F2 = new Filter("Filter2", personalCategories1,teamCategories1);
		assertEquals(-1, F1.getName().compareToIgnoreCase(F2.getName()));
	}

}
