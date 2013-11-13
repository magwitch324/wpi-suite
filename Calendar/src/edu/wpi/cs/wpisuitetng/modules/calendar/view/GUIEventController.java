/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;





import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarMonth;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarYear;
import edu.wpi.cs.wpisuitetng.modules.calendar.WeekView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.buttons.ButtonsPanel_Create;

public class GUIEventController {
	private static GUIEventController instance = null;
	private MainTabView main = null;
	private ToolbarView toolbar = null;
	//private OverviewTable overviewTable = null;
	//private OverviewTreePanel overviewTree = null;
	//private ArrayList<RequirementPanel> listOfEditingPanels = new ArrayList<RequirementPanel>();
	//private ArrayList<IterationPanel> listOfIterationPanels = new ArrayList<IterationPanel>();
	//private IterationOverviewPanel iterationOverview;

	/**
	 * Default constructor for ViewEventController.  Is protected to prevent instantiation.
	 */
	private GUIEventController() {}

	/**
	 * Returns the singleton instance of the vieweventcontroller.
	
	 * @return The instance of this controller. */
	public static GUIEventController getInstance() {
		if (instance == null) {
			instance = new GUIEventController();
		}
		return instance;
	}

	/**
	 * Sets the main view to the given view.
	
	 * @param mainview MainView
	 */
	public void setMainView(MainTabView mainview) {
		main = mainview;
	}

	/**
	 * Sets the toolbarview to the given toolbar
	 * @param tb the toolbar to be set as active.
	 */
	public void setToolBar(ToolbarView tb) {
		toolbar = tb;
		toolbar.repaint();
	}

	/**
	
	 * @return toolBar */
	public ToolbarView getToolbar() {
		return toolbar;
	}

	
	/**
	 * Returns the main view
	
	 * @return the main view */
	public MainTabView getMainView() {
		return main;
	}

	public void createCommitment() {
		ButtonsPanel_Create newCommit = new ButtonsPanel_Create();
		main.addTab("newCreate.", null, newCommit, "New Commitment");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newCommit);
	}

	public void createEvent() {
		ButtonsPanel_Create newEvent = new ButtonsPanel_Create();
		main.addTab("newEvent.", null, newEvent, "New Event");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newEvent);
	}
	
	public void showMonthView0() {
		//CalendarMonth monthView = new CalendarMonth(2013, 11);
		//JPanel monthPanel = monthView.CalendarMonthBuild();
		CalendarMonth c = new CalendarMonth("January",3,31);
		main.addTab("MonthView", null, c.getCalPanel(), "MonthView");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(c.getCalPanel());
	}

	public void showMonthView() {
		//CalendarMonth monthView = new CalendarMonth(2013, 11);
		//JPanel monthPanel = monthView.CalendarMonthBuild();
		//CalendarMonth2 monthView2 = new CalendarMonth2();
		DayView day = new DayView();
		
		//main.addTab("MonthView.", null, monthPanel, "MonthView");
		main.addTab("DayView", null, day, "MonthView");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(day);
	}
	
	public void showWeekView() {
		//CalendarMonth monthView = new CalendarMonth(2013, 11);
		//JPanel monthPanel = monthView.CalendarMonthBuild();
		//CalendarMonth2 monthView2 = new CalendarMonth2();
		WeekView week = new WeekView();
		//CalendarYear year = new CalendarYear(2013);
		//main.addTab("MonthView.", null, monthPanel, "MonthView");
		main.addTab("WeekView", null, week, "WeekView");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(week);
	}
	
	public void showYearView() {
		//CalendarMonth monthView = new CalendarMonth(2013, 11);
		//JPanel monthPanel = monthView.CalendarMonthBuild();
		//CalendarMonth2 monthView2 = new CalendarMonth2();
		CalendarYear year = new CalendarYear(2013);
		//CalendarYear year = new CalendarYear(2013);
		//main.addTab("MonthView.", null, monthPanel, "MonthView");
		main.addTab("YearView", null, year, "YearView");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(year);
	}
	
}
