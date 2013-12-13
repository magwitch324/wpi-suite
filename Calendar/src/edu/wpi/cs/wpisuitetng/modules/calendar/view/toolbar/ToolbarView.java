/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar;


import java.awt.Color;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.buttons.ButtonsPanel_Create;

/**
 * Sets up upper toolbar of RequirementManager tab
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class ToolbarView  extends DefaultToolbarView {
	
	private JButton createCommitButton;
	private JButton createEventButton;
	
	ButtonsPanel_Create createButtons;
	/**
	 * Creates and positions option buttons in upper toolbar
	 * @param visible boolean
	 */
	public ToolbarView(boolean visible) {
		super.setBackground(Color.WHITE);
		
		// REMOVE BUTTONS FOR NOW BECAUSE THEY NEED TO BE MOVED DOWN
		//createButtons = new ButtonsPanel_Create();
		//this.addGroup(createButtons);
		
		/*
		JPanel contentPanel = new JPanel();
		SpringLayout layout  = new SpringLayout();
		contentPanel.setLayout(layout);
		contentPanel.setOpaque(false);
		createCommitButton = new JButton("<html>Create<br />Commitment</html>");
		createEventButton = new JButton("<html>Create<br />Event</html>");
		contentPanel.add(createCommitButton);
		contentPanel.add(createEventButton);

		
		ToolbarGroupView toolbarGroup = new ToolbarGroupView("Home", contentPanel);
		
		// Calculate the width of the toolbar
		Double toolbarGroupWidth = createCommitButton.getPreferredSize().getWidth() +
		 createEventButton.getPreferredSize().getWidth() + 40; 
		 // 40 accounts for margins between the buttons
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		addGroup(toolbarGroup);
		*/
	}
	
	public ButtonsPanel_Create getButtonsPanel_Create() {
		return createButtons;
	}
}
