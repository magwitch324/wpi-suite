/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
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

public class FilterListTest {
	private List<Filter> filterList1;
	private List<Filter> filterList2;
	
	private List<Integer> personalCategories1;
	private List<Integer> personalCategories2;
	private List<Integer> teamCategories1;
	private List<Integer> teamCategories2;
	
	private Filter filter1;
	private Filter filter2;
	
	
	/**
	 * Method setup.
	 */
	@Before
	public void setup() {
		
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
		
		final Filter filter1 = new Filter("personal filter 1", personalCategories1,teamCategories1);
		final Filter filter2 = new Filter("personal filter 2", personalCategories2,teamCategories2);
		
		filterList1 = new ArrayList<Filter>();
		filterList2 = new ArrayList<Filter>();
		filterList2.add(filter1);
		filterList2.add(filter2);
	}

	/**
	 * Method addOneFilterTest.
	 */
	@Test
	public void addOneFilterTest() {
		filterList1.add(filter1);
		assertEquals("personal filter 1", filterList1.get(0).getName());
		assertEquals(personalCategories1, filterList1.get(0).getActivePersonalCategories());
		//still need to work on this one 
	}
	
	/**
	 * Method addOneFilterTest.
	 */
	@Test
	public void addTwoFilterTest() {
		//still need to work on this one 
	}
	

}
