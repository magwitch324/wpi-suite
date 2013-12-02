/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.buttons.ButtonsPanel_Create;



/**
 * A dummy module to demonstrate the Janeway client
 *
 */
public class Calendar implements IJanewayModule {
	
	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	//public final MainTabController mainTabController;
	//public ToolbarController toolbarController;

	public Calendar() {
		//MainTab
		MainTabView mainPanel = new MainTabView(); 
		//mainTabController = new MainTabController(mainPanel);
		
		/*
		// Setup button panel
		JToolBar toolbarPanel = new JToolBar();
		toolbarPanel.setLayout(new FlowLayout());
		toolbarPanel.add(new JButton("<html>Create<br />Commitment</html>"));
		toolbarPanel.add(new JButton("<html>Create<br />Event</html>"));
		*/
		
		
		ToolbarView toolbarPanel = new ToolbarView(false);
		/*toolbarPanel.setLayout(new FlowLayout());
		toolbarPanel.add(new JButton("<html>Create<br />Commitment</html>"));
		toolbarPanel.add(new JButton("<html>Create<br />Event</html>"));
		*/
		
		//toolbarController = new ToolbarController(toolbarPanel, mainTabController);
		
		//Instantiate event controller
		GUIEventController.getInstance().setMainView(mainPanel);
		//GUIEventController.getInstance().setToolBar(toolbarPanel);
		//GUIEventController.getInstance().getToolbar().setSize(1, 1);

		
		tabs = new ArrayList<JanewayTabModel>();
		// Create a tab model that contains the toolBar panel and the main content panel
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), new ButtonsPanel_Create(), mainPanel);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab1);

		
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "Calendar";
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}