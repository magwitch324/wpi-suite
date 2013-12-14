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

import java.util.List;

/**
 * When the show both personal and team checkbox is selected,
 * both data will be stored in this combinedlist for display.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
public class CombinedCommitmentList extends CommitmentList {
	
	/**
	 * This class is for dealing with combining the list of personal 
	 * and team commitments without overriding the ids of the commitments
	 */

	public CombinedCommitmentList() {
	}
	
	/**
	 * Constructor for CombinedCommitmentList.
	 * @param list List<Commitment>
	 */
	public CombinedCommitmentList(List<Commitment> list) {
		this();
		calendarObjects = list;
	}
	
	@Override
	/**
	 * Adds a single commitment to the commitment of the project, 
	 * while sorting them into the right order by date
	 * 
	 * @param newReq The commitment to be added to the list of commitments in the project
	 */
	public void add(Commitment newCommitment){
		int i = 0;
		if(calendarObjects.size() != 0){
			while (i < calendarObjects.size()){
				if(newCommitment.getDueDate().before(calendarObjects.get(i).getDueDate())){
					break;
				}
				i++;
			}
		}
		// add the commitment
		calendarObjects.add(i, newCommitment);
	}
}

