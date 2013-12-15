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
import static javax.swing.SpringLayout.SOUTH;
import static javax.swing.SpringLayout.WEST;

import java.awt.Dimension;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
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
		
		add(nameField);
		mainLayout.putConstraint(WEST, nameField, 5, WEST, this);
		mainLayout.putConstraint(NORTH, nameField, 5, NORTH, this);
		
		add(dueField);
		mainLayout.putConstraint(WEST, nameField, 5, WEST, this);
		mainLayout.putConstraint(NORTH, dueField, 5, SOUTH, nameField);
		
//		mainLayout.putConstraint(EAST, dueField, -5, EAST, this);
//		mainLayout.putConstraint(SOUTH, dueField, -5, SOUTH, this);
		
	}
	
	//TODO Add a constructor for editing a commitment tab -- CommitmentTab line 200
	
	private JPanel getNameField() {
		final JPanel nameField = new JPanel();
		final SpringLayout layout = new SpringLayout();
		nameField.setLayout(layout);
		
		// Create and add Label to the nameField
		final JLabel label = new JLabel("Name*: ");
		nameField.add(label);
		layout.putConstraint(WEST, label, 0, WEST, nameField);
		layout.putConstraint(NORTH, label, 0, NORTH, nameField);
		layout.putConstraint(SOUTH, label, 0, SOUTH, nameField);
		
		// Create and add textField to the nameField
		final JTextField textField = new JTextField(50);
		textField.setPreferredSize(new Dimension(
				textField.getPreferredSize().width, textField.getPreferredSize().height + 5));
		nameField.add(textField);
		layout.putConstraint(WEST, textField, 5, EAST, label);
		layout.putConstraint(NORTH, textField, 0, NORTH, nameField);
		layout.putConstraint(EAST, textField, 0, EAST, nameField);
		layout.putConstraint(SOUTH, textField, 0, SOUTH, nameField);
		
		nameField.setPreferredSize(new Dimension(label.getPreferredSize().width
				+ 5 + textField.getPreferredSize().width, textField.getPreferredSize().height));
		
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
		final JPanel timePanel = new JPanel();
		final SpringLayout timeLayout = new SpringLayout();
		timePanel.setLayout(timeLayout);
		
		final JSpinner hourSpinner;
		final JSpinner minuteSpinner;
		final JSpinner AMPMSpinner;
		
		final JSpinner.DateEditor hourEditor;
		final JSpinner.DateEditor minuteEditor;
		final JSpinner.DateEditor AMPMEditor;
		
		final JLabel timeLabel = new JLabel("Due Time*: ");
		timePanel.add(timeLabel);
		timeLayout.putConstraint(WEST, timeLabel, 0, WEST, timePanel);
		timeLayout.putConstraint(NORTH, timeLabel, 0, NORTH, timePanel);
		
		// Create time spinners, hour, minute, and AM_PM
		hourSpinner = new JSpinner( new SpinnerDateModelHour());
		hourSpinner.setFont(CalendarStandard.CalendarFontBold.deriveFont(18));
//		hourSpinner.setUI(new SpinnerUI());
		timePanel.add(hourSpinner);
		timeLayout.putConstraint(WEST, hourSpinner, 5, EAST, timeLabel);
		hourEditor = new JSpinner.DateEditor(hourSpinner, "hh");
		hourSpinner.setEditor(hourEditor);
		hourEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		hourEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		final JLabel colon = new JLabel(":");
		timePanel.add(colon);
		timeLayout.putConstraint(WEST, colon, 2, EAST, hourSpinner);
		
		minuteSpinner = new JSpinner( new SpinnerDateModelMinute());
		minuteSpinner.setFont(CalendarStandard.CalendarFontBold.deriveFont(18));
//		minuteSpinner.setUI(new SpinnerUI());
		timePanel.add(minuteSpinner);
		timeLayout.putConstraint(WEST, minuteSpinner, 2, EAST, colon);
		minuteEditor = new JSpinner.DateEditor(minuteSpinner, "mm");
		minuteSpinner.setEditor(minuteEditor);
		minuteEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		minuteEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);

		AMPMSpinner = new JSpinner(new SpinnerDateModelAMPM());
		AMPMSpinner.setFont(CalendarStandard.CalendarFontBold.deriveFont(18));
//		AMPMSpinner.setUI(new SpinnerUI());
		timePanel.add(AMPMSpinner);
		timeLayout.putConstraint(WEST, AMPMSpinner, 5, EAST, minuteSpinner);
		AMPMEditor = new JSpinner.DateEditor(AMPMSpinner, "a");
		AMPMEditor.getTextField().setBackground(CalendarStandard.CalendarYellow);
		AMPMSpinner.setEditor(AMPMEditor);
		AMPMEditor.getTextField().setFocusLostBehavior(JFormattedTextField.PERSIST);
		
		timePanel.setPreferredSize(new Dimension(
				// width (buffers + sizes)
				14 + timeLabel.getPreferredSize().width + 
				hourSpinner.getPreferredSize().width + colon.getPreferredSize().width + 
				minuteSpinner.getPreferredSize().width + AMPMSpinner.getPreferredSize().width,
				// height (height of tallest)
				AMPMSpinner.getPreferredSize().height
				));
		
		return timePanel;
		
	}
	
	private JPanel getStatusField() {
		//TODO
		return null;
		
	}
	
}
