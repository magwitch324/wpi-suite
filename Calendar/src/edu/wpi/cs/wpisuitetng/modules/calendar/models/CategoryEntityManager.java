package edu.wpi.cs.wpisuitetng.modules.calendar.models;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Category;

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


public class CategoryEntityManager implements EntityManager<Category> {

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
	public CategoryEntityManager(Data db) {
		this.db = db; 
	}

	/**
	 * Saves a Category when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Category makeEntity(Session s, String content) throws WPISuiteException {
		final Category newCategory = Category.fromJson(content);
		if(!db.save(newCategory, s.getProject())) {
			throw new WPISuiteException();
		}
		return newCategory;
	}
	
	/**
	 * Retrieves a single category from the database
	 * @param s the session
	 * @param id the id number of the category to retrieve
	
	
	
	
	 * @return the category matching the given id * @throws NotFoundException * @throws NotFoundException * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	@Override
	public Category[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Category[] categorys = null;
		try {
			categorys = db.retrieve(Category.class, "id", intId, s.getProject()).toArray(new Category[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(categorys.length < 1 || categorys[0] == null) {
			throw new NotFoundException();
		}
		return categorys;
	}

	/**
	 * Retrieves all categorys from the database
	 * @param s the session
	
	
	
	 * @return array of all stored categorys * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public Category[] getAll(Session s) {
		return db.retrieveAll(new Category(), s.getProject()).toArray(new Category[0]);
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save(Session s, Category model) {
		db.save(model, s.getProject());
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * Deletes a category from the database
	 * @param s the session
	 * @param id the id of the category to delete
	
	
	
	
	 * @return true if the deletion was successful * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}
	
	/**
	 * Deletes all categorys from the database
	 * @param s the session
	
	
	 * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Category(), s.getProject());
	}
	
	/**
	 * Returns the number of categorys in the database
	
	
	
	
	 * @return number of categorys stored * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new Category()).size();
	}

	/**
	 * Method update.
	 * @param session Session
	 * @param content String
	
	
	
	 * @return Category * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	@Override
	public Category update(Session session, String content) throws WPISuiteException {
		
		Category updatedCategory = Category.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Categorys.
		 * We have to get the original defect from db4o, copy properties from updatedCategory,
		 * then save the original Category again.
		 */
		List<Model> oldCategorys = db.retrieve(Category.class, "id", updatedCategory.getId(), session.getProject());
		if(oldCategorys.size() < 1 || oldCategorys.get(0) == null) {
			throw new BadRequestException("Category with ID does not exist.");
		}
				
		Category existingCategory = (Category)oldCategorys.get(0);		

		// copy values to old category and fill in our changeset appropriately
		existingCategory.setName(updatedCategory.getName());
		
		if(!db.save(existingCategory, session.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingCategory;
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
