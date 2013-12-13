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



import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


public class CalPropsEntityManager implements EntityManager<CalendarProps> {

	/** The database */
	Data db;
	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public CalPropsEntityManager(Data db) {
		this.db = db; 
	}

	/**
	 * Saves a CalendarProps when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public CalendarProps makeEntity(Session s, String content) throws WPISuiteException {
		final CalendarProps newCalProps = CalendarProps.fromJson(content);
		if(!db.save(newCalProps, s.getProject())) {
			throw new WPISuiteException();
		}
		return newCalProps;
	}
	
	/**
	 * Retrieves a single CalendarProps from the database
	 * @param s the session
	 * @param id the id number of the CalendarProps to retrieve
	
	
	
	
	 * @return the CalendarProps matching the given id * @throws NotFoundException * @throws NotFoundException * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	@Override
	public CalendarProps[] getEntity(Session s, String id) throws NotFoundException {
		CalendarProps[] calData = null;
		try {
			calData = db.retrieve(CalendarProps.class, "id", id, s.getProject()).toArray(new CalendarProps[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(calData.length < 1 || calData[0] == null) {
			throw new NotFoundException();
		}
		return calData;
	}

	/**
	 * Retrieves all CalendarProps from the database
	 * @param s the session
	
	
	
	 * @return array of all stored CalendarProps * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public CalendarProps[] getAll(Session s) {
		return db.retrieveAll(new CalendarProps(), s.getProject()).toArray(new CalendarProps[0]);
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save(Session s, CalendarProps model) {
		db.save(model, s.getProject());
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		final User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * Deletes a CalendarProps from the database
	 * @param s the session
	 * @param id the id of the CalendarProps to delete
	
	
	
	
	 * @return true if the deletion was successful * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}
	
	/**
	 * Deletes all CalendarProps from the database
	 * @param s the session
	
	
	 * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new CalendarProps(), s.getProject());
	}
	
	/**
	 * Returns the number of CalendarProps in the database
	
	
	
	
	 * @return number of CalendarProps stored * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new CalendarProps()).size();
	}

	/**
	 * Method update.
	 * @param session Session
	 * @param content String
	
	
	
	 * @return CalendarProps * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	@Override
	public CalendarProps update(Session session, String content) throws WPISuiteException {
		
		final CalendarProps updatedCalData = CalendarProps.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Categorys.
		 * We have to get the original defect from db4o, copy properties from updatedCategory,
		 * then save the original Category again.
		 */
		final List<Model> oldCalData = db.retrieve(CalendarProps.class, "id", updatedCalData.getId(), session.getProject());
		if(oldCalData.size() < 1 || oldCalData.get(0) == null) {
			throw new BadRequestException("CalendarProps with ID does not exist.");
		}
				
		final CalendarProps existingCalData = (CalendarProps)oldCalData.get(0);		

		// copy values to old CalendarProps and fill in our changeset appropriately
		existingCalData.copyFrom(updatedCalData);
		
		if(!db.save(existingCalData, session.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingCalData;
	}

	/**
	 * Method advancedGet.
	 * @param arg0 Session
	 * @param arg1 String[]
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[]) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[])
	 */
	@Override
	public String advancedGet(Session arg0, String[] arg1) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPost.
	 * @param arg0 Session
	 * @param arg1 String
	 * @param arg2 String
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	@Override
	public String advancedPost(Session arg0, String arg1, String arg2) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPut.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @param arg2 String
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	@Override
	public String advancedPut(Session arg0, String[] arg1, String arg2) throws NotImplementedException {
		throw new NotImplementedException();
	}

	
}
