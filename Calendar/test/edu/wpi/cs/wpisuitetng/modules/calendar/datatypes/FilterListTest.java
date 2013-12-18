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
	private FilterList filterList1;
	private FilterList filterList2;
	
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
		
		filter1 = new Filter(
				"personal filter 1", personalCategories1, teamCategories1);
		filter2 = new Filter(
				"personal filter 2", personalCategories2, teamCategories2);
		
		filterList1 = new FilterList();
		filterList2 = new FilterList();
		filterList2.add(filter1);
		filterList2.add(filter2);
	}

	/**
	 * Method addOneFilterTest.
	 */
	@Test
	public void addOneFilterTest() {
		filterList1.add(filter1);
		assertEquals("personal filter 1", filterList1.getElementAt(0).getName());
		assertEquals(Integer.valueOf(1), 
				filterList1.getElementAt(0).getActivePersonalCategories().get(0));
	}
	
	/**
	 * Method addTwoFilterTest.
	 */
	@Test
	public void addTwoFilterTest() {
		filterList1.add(filter2);
		filterList1.add(filter1);
		assertEquals("personal filter 1", filterList1.getElementAt(0).getName());
		assertEquals(Integer.valueOf(2),
				filterList1.getElementAt(1).getActivePersonalCategories().get(1)); 
	}
	
	@Test
	public void getFilterNullTest() {
		assertNull(filterList1.getFilter(1));
	}
	
	@Test
	public void getFilterTest() {
		assertEquals("personal filter 2", filterList2.getFilter(2).getName());
		assertEquals(Integer.valueOf(3), filterList2.getFilter(1).getActiveTeamCategories().get(0));
	}
	
	@Test
	public void removeFilterTest() {
		filterList2.remove(1);
		assertEquals("personal filter 1", filterList2.getElementAt(0).getName());
		filterList2.remove(1);
		assertEquals("personal filter 1", filterList2.getElementAt(0).getName());
	}
	
	@Test
	public void getSizeTest() {
		assertEquals(2, filterList2.getSize());
		filterList2.add(new Filter("personal filter 3", personalCategories1, teamCategories2));
		assertEquals(3, filterList2.getSize());
	}
	
	@Test
	public void removeAllTest() {
		filterList2.removeAll();
		assertEquals(0, filterList2.getSize());
	}
	
	@Test
	public void addFiltersTest() {
		final Filter[] filterArray = new Filter[] {filter1, filter2};
		filterList1.addFilters(filterArray);
		assertEquals("personal filter 1", filterList1.getElementAt(0).getName());
		assertEquals(Integer.valueOf(1), 
				filterList1.getElementAt(0).getActivePersonalCategories().get(0));
	}
	
	@Test
	public void updateTest() {
		filter1.setName("Name Changed");
		filterList2.update(filter1);
		assertEquals("Name Changed", filterList2.getElementAt(0).getName());
	}
	
	@Test
	public void sortByAlphabetTest() {
		final Filter a = new Filter(
				"a", personalCategories1, teamCategories1);
		final Filter z = new Filter(
				"z", personalCategories2, teamCategories2);
		final Filter h = new Filter(
				"h", personalCategories2, teamCategories2);
		filterList1.add(h);
		filterList1.add(a);
		filterList1.add(z);
		assertEquals("z", filterList1.getElementAt(2).getName());
		assertEquals("h", filterList1.getElementAt(1).getName());
	}

}
