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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CategoryListTest {

	private  CategoryList categoryList;
    private List<Category> categories;
    private Category lastYear;
    private Category todayCategory;
    private Category nextWeek;
    private Category nextYear;
    
    /**
     * Method setUp.
     */
    @Before
    public void setUp() {
  
    	categoryList = new CategoryList();
    	categories = new ArrayList<Category>();
        lastYear = new Category("Last Year", Color.red, true);
        todayCategory = new Category("Today", Color.blue, false);
        nextWeek = new Category("Next Week", Color.yellow, true );
        nextYear = new Category("Next Year", Color.gray, true);
   
    }


	/**
	 * Test to ensure addCategory works correctly
	 */
	@Test
    public void addCategoryTest() {
    	categoryList.add(nextWeek);
    	categoryList.add(todayCategory);
    	categoryList.add(lastYear);
    	assertEquals("Last Year", categoryList.getElementAt(2).getName());
    	assertEquals("Today", categoryList.getElementAt(0).getName());
    }

	/**
	 * Test to ensure getCategory works correctly 
	 */
	@Test
	public void getCategoryTest() {
	    categoryList.add(nextWeek);
	    categoryList.add(todayCategory);
	    categoryList.add(lastYear);
	    categoryList.add(nextYear);
	    assertEquals("Next Year", categoryList.getCategory(4).getName());
	    assertEquals("Today", categoryList.getCategory(2).getName());
	    }
	
	/**
	 * Test to ensure remove function works correctly
	 */
	 @Test
	    public void removeCategoryTest() {
	    	categoryList.add(nextWeek);
	    	categoryList.add(todayCategory);
	    	categoryList.add(lastYear);
	    	categoryList.add(nextYear);
	    	categoryList.remove(0);
	    	categoryList.remove(3);
	    	assertEquals(3, categoryList.getSize());
	    	assertEquals("Today", categoryList.getElementAt(0).getName());
	    	assertEquals("Next Year", categoryList.getElementAt(1).getName());
	    }
	 
	  /**
	   * Test to ensure remove all works correctly
	   */
	 @Test
	    public void removeAllTest() {
	    	categoryList.add(nextWeek);
	    	categoryList.add(todayCategory);
	    	categoryList.add(lastYear);
	    	categoryList.add(nextYear);
	    	categoryList.removeAll();
	    	assertEquals(0, categoryList.getSize());
	    }
	 
	 /**
	  * Test to ensure adds the given array of categories work correctly
	  */
	 @Test
	    public void addCategoriesTest() {
	    	final Category[] categoryArray = new Category[]{lastYear, todayCategory, nextYear};
	    	categoryList.addCategories(categoryArray);
	    	assertEquals(3, categoryList.getSize());
	    	assertEquals("Last Year", categoryList.getElementAt(2).getName());
	    	assertEquals("Today", categoryList.getElementAt(0).getName());
	    }
	 
	 /**
	  * Test to ensure getCategorys works correctly
	  */
	 @Test
	    public void getCategoriesTest() {
	    	categoryList.add(nextWeek);
	    	categoryList.add(todayCategory);
	    	categoryList.add(lastYear);
	    	categoryList.add(nextYear);
	    	categories = categoryList.getCategories();
	    	assertEquals(4, categories.size());
	    	assertEquals("Next Year", categories.get(2).getName());
	    	assertEquals("Last Year", categories.get(0).getName());
	    }
	 
	 /**
	  * Test to ensure update works correctly
	  */
	  @Test
	    public void updateTest() {
	    	categoryList.add(nextWeek);
	    	categoryList.add(todayCategory);
	    	categoryList.add(lastYear);
	    	categoryList.add(nextYear);
	    	todayCategory.setName("abc");
	    	nextWeek.setID(9);
	    	categoryList.update(lastYear);
	    	categoryList.update(nextWeek);
	    	assertEquals("abc", categoryList.getCategory(2).getName());
	    	assertEquals(9, categoryList.getElementAt(1).getID());
	    }


}
