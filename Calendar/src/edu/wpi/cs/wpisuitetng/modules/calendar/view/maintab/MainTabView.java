/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.EventFullView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.tab.ClosableTabComponent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentFullView;

/**
 * This tabbed pane will appear as the main content of the Calendar tab.
 * It starts out showing the Team Calendar and My Calendar tabs. 
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class MainTabView extends JTabbedPane {

	/**
	 * Constructor for MainTabView.
	 */
	public MainTabView() {
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
//		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3, Color.WHITE));	
	}
	
	
	@Override
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		if (!((component instanceof AbCalendar) || 
				(component instanceof CommitmentFullView) || 
				(component instanceof EventFullView))) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
		super.setBackground(Color.WHITE);
		super.setOpaque(true);
	}
	
	@Override
	public void removeTabAt(int index) {
		// if a tab does not have the close button UI, it cannot be removed
		if(getTabComponentAt(index) instanceof ClosableTabComponent) {
			super.removeTabAt(index);
		}
	}
	
	@Override
	public void setComponentAt(int index, Component component) {
		super.setComponentAt(index, component);
		fireStateChanged(); // hack to make sure toolbar knows if component changes
	}
	
}
