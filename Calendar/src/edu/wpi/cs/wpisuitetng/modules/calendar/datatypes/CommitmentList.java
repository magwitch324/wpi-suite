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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * List of commitments that will be used by the gui for display.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CommitmentList extends CalendarObjectList<Commitment> {

	/**
	 * The list in which all the commitment for a single project are contained
	 * @param newCommitment Commitment
	 */
	// Use the super list calendarObjects

	/**
	 * Adds a single commitment to the commitment of the project
	 * 
	 * @param newReq
	 *            The commitment to be added to the list of commitments in the
	 *            project
	 */
	public void add(Commitment newCommitment) {
		int i = 0;
		newCommitment.setID(nextID);
		nextID++;
		if (calendarObjects.size() != 0) {
			while (i < calendarObjects.size()) {
				if (newCommitment.getDueDate().before(
						calendarObjects.get(i).getDueDate())) {
					break;
				}
				i++;
			}
		}
		// add the commitment
		calendarObjects.add(i, newCommitment);
	}

	/**
	 * Returns the Commitment with the given ID
	 * 
	 * @param id
	 *            The ID number of the Commitment to be returned
	 * 
	
	 * @return the Commitment for the id or null if the commitment is not found */
	public Commitment getCommitment(int id) {
		return get(id);
	}

	/**
	 * Removes the Commitment with the given ID
	 * 
	
	 * @param id int
	 */
	public void removeCommmitment(int id) {
		remove(id);
	}

	/**
	 * Returns the list of the Commitments
	 * 
	 * @return the Commitments held within the calendarObjects list.
	 */
	public List<Commitment> getCommitments() {
		return getCalendarObjects();
	}
	
	/**
	 * Adds the given array of commitments to the list
	 * 
	
	 * @param array Commitment[]
	 */
	public void addCommitments(Commitment[] array) {
		addCalendarObjects(array);
	}
	
	/**
	 * Update the calendarObject list
	 * 
	
	 * @param newObject Commitment
	 */
	public void update(Commitment newObject) {
		final Commitment tmp = get(newObject.getID());
		if(tmp.getDueDate().equals(newObject.getDueDate())){
			final int i = calendarObjects.indexOf(get(newObject.getID()));
			calendarObjects.remove(get(newObject.getID()));
			calendarObjects.add(i, newObject);
			return;
		}
		else{
		calendarObjects.remove(get(newObject.getID()));
		add(newObject);
		}
	}

	/**
	 * Filter the commitment list to data between the start and end Calendars
	 * This is INCLUSIVE for both start and end
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Commitment> filter(GregorianCalendar start,
			GregorianCalendar end) {

		final GregorianCalendar commitDate = new GregorianCalendar();
		final List<Commitment> newCommitments = new ArrayList<Commitment>();

		// iterate and add all commitments between start and end
		// to the commitment list
		for (Commitment commit : calendarObjects) {
			commitDate.setTime(commit.getDueDate().getTime());
			if (commitDate.after(start) && commitDate.before(end)) {
				newCommitments.add(commit);
			}
		}

		return newCommitments;
	}
}
