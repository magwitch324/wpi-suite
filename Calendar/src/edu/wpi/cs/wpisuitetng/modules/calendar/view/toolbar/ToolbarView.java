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
	}
	
	public ButtonsPanel_Create getButtonsPanel_Create() {
		return createButtons;
	}
}
