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

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */

public class FilterTest {
	/**
	 * Tests to ensure that a new filter is created with default values
	 */
	@Test
	public void defaultConstructorTest(){
		Filter testFilter = new Filter();
		assertEquals("", testFilter.getName());
		//assertEquals(); 
		//still waiting for filter constructor to change	
	}
	

}
