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

import java.awt.Color;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CategoryTest {

	/**
	 * Tests to ensure that a new category is created with default values
	 */
	@Test
	public void defaultConstructorTest(){
		final Category testCategory = new Category ();
		assertEquals("", testCategory.getName());
		assertEquals(new Color(0),testCategory.getCategoryColor());
		assertFalse(testCategory.getIsPersonal());
	}
	
	/**
	 * Tests to ensure that the detailed constructor fills in fields correctly
	 */
	@Test
	public void mainConstructorTest(){
		final Category testCategory = new Category("C1", Color.blue, true);
		assertEquals("C1", testCategory.getName());
		assertEquals(Color.blue, testCategory.getCategoryColor());
		assertTrue(testCategory.getIsPersonal());
	}
	
	/**
	 * Ensures that setters work correctly
	 */
	@Test
	public void setterConstructorTest(){
		final Category testCategory = new Category("C1", Color.blue, true);
		testCategory.setName("setter test");
		testCategory.setCategoryColor(Color.cyan);
		testCategory.setPersonal(false);
		assertEquals("setter test", testCategory.getName());
		assertEquals(Color.cyan, testCategory.getCategoryColor());
		assertFalse(testCategory.getIsPersonal());
	}
	/**
	 * Tests to make sure copyFrom() works as intended
	 */
	@Test
	public void copyFromTest(){
		final Category testCategory1 = new Category ("C2", Color.gray, false);
		testCategory1.setCategoryColor(Color.red);
		final Category testCategory = new Category();
		testCategory.copyFrom(testCategory1);
		assertEquals("C2", testCategory.getName());
		assertEquals(Color.red, testCategory.getCategoryColor());
		assertFalse(testCategory.getIsPersonal());
	}
	
	/**
	 * Tests to ensure that compare function work correctly
	 */
	@Test
	public void compareTest(){
		final Category c1 = new Category ("C1", Color.blue, true);
		final Category c2 = new Category ("C2", Color.gray, false);
		assertEquals(-1, c1.getName().compareToIgnoreCase(c2.getName()));
	}
	
	
	
	
	
}
