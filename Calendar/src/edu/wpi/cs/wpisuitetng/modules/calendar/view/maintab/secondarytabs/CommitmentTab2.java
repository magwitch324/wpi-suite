/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs;

import static javax.swing.SpringLayout.EAST;
import static javax.swing.SpringLayout.NORTH;
import static javax.swing.SpringLayout.WEST;
import static javax.swing.SpringLayout.SOUTH;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class CommitmentTab2 extends JPanel {

	/**
	 * Constructor for the Add Commitment Tab
	 * This tab has the fields for:
	 * Name
	 * Description
	 * Category
	 * Type (personal/team)
	 * Due Time
	 * Due Date
	 * Status (new/in progress/closed)
	 * @param openedFrom
	 */
	public CommitmentTab2(int openedFrom) {
		final SpringLayout mainLayout = new SpringLayout();
		setLayout(mainLayout);
		
		// Populate all the fields
		final JPanel nameField = getNameField();
		final JPanel descField = getDescField();
		final JPanel categoryField = getCategoryField();
		final JPanel typeField = getTypeField();
		final JPanel dueField = getDueField();
		final JPanel statusField = getStatusField();
		
		nameField.setBackground(Color.RED);
		
		add(nameField);
		mainLayout.putConstraint(WEST, nameField, 5, WEST, this);
		mainLayout.putConstraint(NORTH, nameField, 5, NORTH, this);
		mainLayout.putConstraint(EAST, nameField, -5, EAST, this);
		mainLayout.putConstraint(SOUTH, nameField, -5, SOUTH, this);
		
	}
	
	//TODO Add a constructor for editing a commitment tab -- CommitmentTab line 200
	
	private JPanel getNameField() {
		final JPanel nameField = new JPanel();
		final SpringLayout layout = new SpringLayout();
		nameField.setLayout(layout);
		
		// Create and add Label to the nameField
		final JLabel label = new JLabel("Name: ");
		nameField.add(label);
		layout.putConstraint(WEST, label, 5, WEST, nameField);
		layout.putConstraint(NORTH, label, 5, NORTH, nameField);
		layout.putConstraint(SOUTH, label, -5, SOUTH, nameField);
		
		// Create and add textField to the nameField
		final JTextField textField = new JTextField();
		nameField.add(textField);
		layout.putConstraint(WEST, textField, 5, EAST, label);
		layout.putConstraint(NORTH, textField, 5, NORTH, nameField);
		layout.putConstraint(EAST, textField, -5, EAST, nameField);
		layout.putConstraint(SOUTH, textField, -5, SOUTH, nameField);
		
		return nameField;
	}
	
	private JPanel getDescField() {
		//TODO
		return null;
	}
	
	private JPanel getCategoryField() {
		//TODO
		return null;
		
	}
	
	private JPanel getTypeField() {
		//TODO
		return null;
		
	}
	
	private JPanel getDueField() {
		//TODO
		return null;
		
	}
	
	private JPanel getStatusField() {
		//TODO
		return null;
		
	}
	
}
