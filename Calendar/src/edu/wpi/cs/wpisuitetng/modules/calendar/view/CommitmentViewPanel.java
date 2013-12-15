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

 /**
  * A commitment view panel can be displayed by commitment view.
  * Commitment view panel contains a commitment to retrieve data from.
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
@SuppressWarnings("serial")
public class CommitmentViewPanel extends JPanel {

	private Commitment comm;
	
	/**
	 * Constructor for CommitmentViewPanel.
	 */
	public CommitmentViewPanel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for CommitmentViewPanel.
	 * @param layout LayoutManager
	 */
	public CommitmentViewPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for CommitmentViewPanel.
	 * @param commitment Commitment
	 */
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
