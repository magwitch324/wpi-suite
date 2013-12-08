/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;

public class CalendarPropsTest {

	@Test
	public void defaultConstuctorTest(){
		CalendarProps props = new CalendarProps();
		assertEquals("", props.getId());
		assertFalse(props.getMyShowComm());
		assertFalse(props.getShowTeamData());
		assertFalse(props.getTeamShowComm());
	}
	
	@Test
	public void idConstuctorTest(){
		CalendarProps props = new CalendarProps("test-id");
		assertEquals("test-id", props.getId());
		assertFalse(props.getMyShowComm());
		assertFalse(props.getShowTeamData());
		assertFalse(props.getTeamShowComm());
	}

	@Test
	public void SetterTest(){
		CalendarProps props = new CalendarProps();
		props.setId("test-id");
		props.setMyShowComm(true);
		props.setShowTeamData(true);
		props.setTeamShowComm(true);
		assertEquals("test-id", props.getId());
		assertTrue(props.getMyShowComm());
		assertTrue(props.getShowTeamData());
		assertTrue(props.getTeamShowComm());
	}
	
	@Test
	public void CopyFromTest(){
		CalendarProps props1 = new CalendarProps();
		CalendarProps props2 = new CalendarProps();
		props1.setId("test-id");
		props1.setMyShowComm(true);
		props1.setShowTeamData(true);
		props1.setTeamShowComm(true);
		props2.copyFrom(props1);
		assertEquals("test-id", props2.getId());
		assertTrue(props2.getMyShowComm());
		assertTrue(props2.getShowTeamData());
		assertTrue(props2.getTeamShowComm());
	}

}