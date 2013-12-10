/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;

@SuppressWarnings("serial")
public class CommitmentViewPanel extends JPanel {

	private Commitment comm;
	
	public CommitmentViewPanel() {
		// TODO Auto-generated constructor stub
	}

	public CommitmentViewPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public CommitmentViewPanel(Commitment commitment) {
		// TODO Auto-generated constructor stub
		comm = commitment;
	}

	public Commitment getCommitment() {
		return comm;
	}

	public void setCommitment(Commitment comm) {
		this.comm = comm;
	}

	

}
