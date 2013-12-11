package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.calendar.MockData;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


public class CalendarDataEntityManagerTest {
	
	CalendarData calendarData;
	User john;
	Project testProject;
	Session defaultSession;
	Data db;
	CalendarDataEntityManager manager;
	String mockSsid;
	String id;
	
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
	
	@Test
	public void testMakeEntity() throws WPISuiteException {
		CalendarData created = manager.makeEntity(defaultSession, calendarData.toJSON());
		assertEquals("1", created.getId());
		assertSame(created, calendarData.getEvents().get(0));
		assertSame(testProject, created.getProject());
	}
}
