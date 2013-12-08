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
    private Category lastYear;
    private Category lastMonth;
    private Category lastWeek;
    private Category todayCategory;
    private Category nextWeek;
    private Category nextMonth;
    private Category nextYear;
    
    @Before
    public void setUp() {
    	categoryList = new CategoryList();
    	categories = new ArrayList<Category>();
        lastYear = new Category(0, "Last Year");
        lastMonth = new Category(1, "Last Month");
        lastWeek = new Category(2, "Last Week");
        todayCategory = new Category(3, "Today");
        nextWeek = new Category(4, "Next Week");
        nextMonth = new Category(5, "Next Month");
        nextYear = new Category(6, "Next Year");
    }


	/**
	 * Test to ensure addCategory works correctly
	 */
	@Test
    public void addCategoryTest() {
    	categoryList.addCategory(nextWeek);
    	categoryList.addCategory(todayCategory);
    	categoryList.addCategory(lastYear);
    	assertEquals("Last Year", categoryList.getElementAt(2).getName());
    	assertEquals("Today", categoryList.getElementAt(0).getName());
    }

	/**
	 * Test to ensure getCategory works correctly 
	 */
	@Test
	public void getCategoryTest() {
	    categoryList.addCategory(nextWeek);
	    categoryList.addCategory(todayCategory);
	    categoryList.addCategory(lastYear);
	    categoryList.addCategory(nextYear);    	
	    assertEquals("Last Year", categoryList.getCategory(0).getName());
	    assertEquals("Next Year", categoryList.getCategory(6).getName());
	    assertEquals("Today", categoryList.getCategory(3).getName());
	    }
	
	/**
	 * Test to ensure remove function works correctly
	 */
	 @Test
	    public void removeCategoryTest() {
	    	categoryList.addCategory(nextWeek);
	    	categoryList.addCategory(todayCategory);
	    	categoryList.addCategory(lastYear);
	    	categoryList.addCategory(nextYear);    	
	    	categoryList.removeCategory(0);
	    	categoryList.removeCategory(3);
	    	assertEquals(2, categoryList.getSize());
	    	assertEquals("Next Year", categoryList.getElementAt(0).getName());
	    	assertEquals("Next Week", categoryList.getElementAt(1).getName());
	    }
	 
	  /**
	   * Test to ensure remove all works correctly
	   */
	 @Test
	    public void removeAllTest() {
	    	categoryList.addCategory(nextWeek);
	    	categoryList.addCategory(todayCategory);
	    	categoryList.addCategory(lastYear);
	    	categoryList.addCategory(nextYear);
	    	categoryList.removeAll();
	    	assertEquals(0, categoryList.getSize());
	    }
	 
	 /**
	  * Test to ensure adds the given array of categories work correctly
	  */
	 @Test
	    public void addCategoriesTest() {
	    	Category[] categoryArray = new Category[]{lastYear, todayCategory, nextYear};
	    	categoryList.addCategorys(categoryArray);
	    	assertEquals(3, categoryList.getSize());
	    	assertEquals("Last Year", categoryList.getElementAt(2).getName());
	    	assertEquals("Today", categoryList.getElementAt(0).getName());
	    }
	 
	 /**
	  * Test to ensure getCategorys works correctly
	  */
	 @Test
	    public void getCategoriesTest() {
	    	categoryList.addCategory(nextWeek);
	    	categoryList.addCategory(todayCategory);
	    	categoryList.addCategory(lastYear);
	    	categoryList.addCategory(nextYear);	    	
	    	categories = categoryList.getCategorys();	    	
	    	assertEquals(4, categories.size());
	    	assertEquals("Next Year", categories.get(2).getName());
	    	assertEquals("Last Year", categories.get(0).getName());
	    }

}
