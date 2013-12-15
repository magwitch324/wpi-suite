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

import org.junit.Test;

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
		assertEquals(0, testCategory.getID());
		assertEquals("", testCategory.getName());
	}
	
	/**
	 * Tests to ensure that the detailed constructor fills in fields correctly
	 */
	@Test
	public void mainConstructorTest(){
		final Category testCategory = null;//new Category (1, "test");
		assertEquals("test", testCategory.getName());
		assertEquals(1, testCategory.getID());
	}
	
	/**
	 * Ensures that setters work correctly
	 */
	@Test
	public void setterConstructorTest(){
		final Category testCategory = null;//new Category (1, "settertest");
		testCategory.setID(2);
		testCategory.setName("setter test");
		assertEquals(2, testCategory.getID());
		assertEquals("setter test", testCategory.getName());
	}
	/**
	 * Tests to ensure that compare function work correctly
	 */
	@Test
	public void compareTest(){
		final Category c1 = null;//new Category (1, "C1");
		final Category c2 = null;//new Category (2, "C2");
		assertEquals(-1, c1.getName().compareToIgnoreCase(c2.getName()));
	}
	
	
	
}
