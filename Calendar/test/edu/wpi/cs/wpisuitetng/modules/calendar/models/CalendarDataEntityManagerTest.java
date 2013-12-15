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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.calendar.MockData;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CalendarDataEntityManagerTest {
	
	CalendarData calendarData;
	User john;
	Project testProject;
	Session defaultSession;
	Data db;
	CalendarDataEntityManager manager;
	String mockSsid;
	String id;
	
	/**
	 * Method setUp.
	 */
	@Before
	public void setUp() {
		mockSsid = "abc123";
		id = "1";
		john = new User("John", "John", "1234", 1);
		testProject = new Project("test", "1");
		defaultSession = new Session(john, testProject, mockSsid);
		calendarData = new CalendarData(id);
		
		db = new MockData(new HashSet<Object>());
		db.save(calendarData, testProject);
		db.save(john);
		manager = new CalendarDataEntityManager(db);
	}
	
	/**
	 * Method testMakeEntity.
	
	 * @throws WPISuiteException */
	@Test
	public void testMakeEntity() throws WPISuiteException {
		final CalendarData created = manager.makeEntity(defaultSession, calendarData.toJSON());
		final List<CalendarData> list = new ArrayList<CalendarData>();
		Collections.addAll(list, manager.getEntity(defaultSession, "1"));
		assertEquals("1", created.getId());
		assertSame(2, manager.getAll(defaultSession).length);
		assertEquals("1", list.get(0).getId());
		assertEquals("1", list.get(1).getId());
		assertSame(testProject, created.getProject());
	}
}
