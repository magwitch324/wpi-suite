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

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import static java.util.Calendar.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;

public class CategoryListTest {

	private  CategoryList categoryList;
	private List<Category> categories;
	private Category lastYear = new Category(0, "Last Year");
	private Category lastMonth = new Category(1, "Last Month");
	private Category lastWeek = new Category(2, "Last Week");
	private Category todayCategory = new Category(3, "Today");
	private Category nextWeek = new Category(4, "Next Week");
	private Category nextMonth = new Category(5, "Next Month");
	private Category nextYear = new Category(6, "Next Year");
	
	@Before
	public void setUp() {
	    	categoryList = new CategoryList();
	    	categories = new ArrayList<Category>();
	    }

	/**
	 * Test ensure addCategory works correctly
	 */
	@Test
    public void addCategoryTest() {
    	categoryList.addCategory(nextWeek);
    	categoryList.addCategory(todayCategory);
    	categoryList.addCategory(lastYear);
    	assertEquals("Last Year", categoryList.getElementAt(2).getName());
    	assertEquals("Today", categoryList.getElementAt(0).getName());
    }

}
