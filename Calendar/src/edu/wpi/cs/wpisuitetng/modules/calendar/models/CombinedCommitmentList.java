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

import java.util.ArrayList;
import java.util.List;

public class CombinedCommitmentList extends CommitmentList{

	public CombinedCommitmentList() {
		super();
	}
	
	public CombinedCommitmentList(List<Commitment> list) {
		this();
		this.commitments = list;
	}
	
	@Override
	/**
	 * Adds a single commitment to the commitment of the project
	 * 
	 * @param newReq The commitment to be added to the list of commitments in the project
	 */
	public void addCommitment(Commitment newCommitment){
		int i = 0;
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
}

