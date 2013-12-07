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

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.LONG;
import static java.util.Calendar.YEAR;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;

public class CommitmentList {

	/**
	 * The list in which all the commitment for a single project are contained
	 */
	protected List<Commitment> commitments;
	private int nextId;


	/**
	 * Constructs an empty list of categories for the project
	 */
	public CommitmentList (){
		commitments = new ArrayList<Commitment>();
		nextId = 0;
	}

	/**
	 * Adds a single commitment to the commitment of the project
	 * 
	 * @param newReq The commitment to be added to the list of commitments in the project
	 */
	public void addCommitment(Commitment newCommitment){
		int i = 0;
		newCommitment.setID(nextId);
		nextId++;
		if(commitments.size() != 0){
			while (i < commitments.size()){
				if(newCommitment.getDueDate().before(commitments.get(i).getDueDate())){
					break;
				}
				i++;
			}
		}
		// add the commitment
		commitments.add(i, newCommitment);
	}
	/**
	 * Returns the Commitment with the given ID
	 * 
	 * @param id The ID number of the Commitment to be returned

	 * @return the Commitment for the id or null if the commitment is not found */
	public Commitment getCommitment(int id)
	{
		Commitment temp = null;
		// iterate through list of categories until id is found
		for (int i=0; i < this.commitments.size(); i++){
			temp = commitments.get(i);
			if (temp.getID() == id){
				break;
			}
		}
		return temp;
	}
	/**
	 * Removes the Commitment with the given ID
	 * 
	 * @param removeId The ID number of the commitment to be removed from the list of commitments in the project
	 */
	public void removeCommmitment(int removeId){
		// iterate through list of categories until id of project is found
		for (int i=0; i < this.commitments.size(); i++){
			if (commitments.get(i).getID() == removeId){
				// remove the id
				commitments.remove(i);
				break;
			}
		}
	}

	/**
	 * Provides the number of elements in the list of Commitments for the project. 
	 * 
	 * @return the number of commitments in the project * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return commitments.size();
	}

	/**
	 * 
	 * Provides the next ID number that should be used for a new commitment that is created.
	 * 

	 * @return the next open id number */
	public int getNextID()
	{

		return this.nextId++;
	}

	/**
	 * This function takes an index and finds the commitment in the list of categories
	 * for the project. Used internally by the JList in NewCommitmentList.
	 * 
	 * @param index The index of the commitment to be returned



	 * @return the commitment associated with the provided index * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Commitment getElementAt(int index) {
		return commitments.get(commitments.size() - 1 - index);
	}

	/**
	 * Removes all commitments from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each category
	 * from the model.
	 */
	public void removeAll() {
		commitments.removeAll(getCommitments());
	}

	/**
	 * Adds the given array of commitments to the list
	 * 
	 * @param categories the array of commitments to add
	 */
	public void addCommitments(Commitment[] array) {
		int i = 0;
		while(i < array.length){
			commitments.add(array[i]);
			i++;
		}
	}

	/**
	 * Returns the list of the commitments

	 * @return the commitments held within the CommitmentList. */
	public List<Commitment> getCommitments() {
		return commitments;
	}
	
	/**
	 * Update the commitment list
	 * 
	 * @param the commitment to be update
	 */
	public void update (Commitment newCommitment) {
		commitments.remove(getCommitment(newCommitment.getID()));
		addCommitment(newCommitment);
	}
	
	/**
	 * Filter the commitment list to data between 
	 * the start and end Calendars
	 * This is INCLUSIVE for both start and end
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Commitment> filter(GregorianCalendar start, GregorianCalendar end) {

		GregorianCalendar commitDate = new GregorianCalendar();
		List<Commitment> newCommitments = new ArrayList<Commitment>();
		
		// move start and end to make the function inclusive
//		start.add(Calendar.DAY_OF_MONTH, -1);
//		end.add(Calendar.DAY_OF_MONTH, 1);
		
		// iterate and add all commitments between start and end
		// to the commitment list
		for (Commitment commit : commitments) {
			commitDate.setTime(commit.getDueDate().getTime());
			if (commitDate.after(start) && commitDate.before(end)) {
				newCommitments.add(commit);
			} 
		}
		
		return newCommitments;
	}
	
	
	
	/**
	 * Filter the commitment list to data on a specific date
	 * @param date
	 * @return ArrayList of commitments on date
	 */
	public List<Commitment> filter(GregorianCalendar date) {

		GregorianCalendar commitDate = new GregorianCalendar();
		List<Commitment> newCommitments = new ArrayList<Commitment>();
		
		for (Commitment commit : commitments) {
			commitDate.setTime(commit.getDueDate().getTime());
			if (commitDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
					&& commitDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
					&& commitDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
				newCommitments.add(commit);
			} 
		}
		
		return newCommitments;
	}
	
	/**
	 * Filters the calendar data to the "amount"
	 * around the given date
	 * ex: 11/23/2013, Calendar.MONTH -> All of November
	 * @param date
	 * @param amount
	 * @return
	 * @throws CalendarException 
	 */
	public List<Commitment> filter(GregorianCalendar date, int amount) throws CalendarException {
		GregorianCalendar start = new GregorianCalendar();
		start.setTime(date.getTime());
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		
		GregorianCalendar end   = new GregorianCalendar();		
		
		/* All methods here add the given amount, then roll back
		 * one day to specify range by the first and last day 
		 * within that range
		 */
		if (amount == Calendar.DAY_OF_MONTH || amount == Calendar.DAY_OF_WEEK || amount == Calendar.DAY_OF_YEAR) {
			// Do nothing. This allows error checking at end
		}
		else if (amount == Calendar.WEEK_OF_MONTH || amount == Calendar.WEEK_OF_YEAR) {
			start.set(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek());
			end.setTime(start.getTime());
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			end.add(Calendar.WEEK_OF_YEAR, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
			
		}
		else if (amount == Calendar.MONTH) {
			start.set(Calendar.DATE, 1);
			end.setTime(start.getTime());
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			end.add(Calendar.MONTH, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
		}
		else if (amount == Calendar.YEAR) {
			start.set(Calendar.DAY_OF_YEAR, 1);
			end.setTime(start.getTime());
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
			end.add(Calendar.YEAR, 1);
			end.add(Calendar.DAY_OF_YEAR, -1);
		} else {
			throw new CalendarException("Invalid amount! Can only filter around day, week, month, and year types");
		}
		
		//System.out.println("Start: " + printcalendar(start));
		//System.out.println("End:   " + printcalendar(end));		
		
		return filter(start, end);
	}
	
	public void printcalendar(GregorianCalendar cal) {
		String dayName = cal.getDisplayName(GregorianCalendar.DAY_OF_WEEK, LONG, Locale.ENGLISH);
		int dayNum = cal.get(DAY_OF_MONTH);
		String monthName = cal.getDisplayName(GregorianCalendar.MONTH, LONG, Locale.ENGLISH);
		int year = cal.get(YEAR);
		System.out.println(dayName + ", " + monthName + " " + dayNum + ", " + year);
	}
}
