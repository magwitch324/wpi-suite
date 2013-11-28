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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.BorderFactory;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar.types;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.CommitmentTab;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.buttons.ButtonsPanel_Create;

/**
 * @author sfp
 *
 */
public class GUIEventController {
	private static GUIEventController instance = null;
	private int scrollBarValue;
	private MainTabView main = null;
	private ToolbarView toolbar = null;
	private TeamCalendar teamCalendar;
	private MyCalendar myCalendar;
	private List<CommitmentTab> listOfCommitmentTabs;

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
		teamCalendar = new TeamCalendar();
		myCalendar = new MyCalendar();
		try {
            Image img = ImageIO.read(getClass().getResource("Personal_Icon.png"));
            main.addTab("My Calendar", new ImageIcon(img), myCalendar);
            
            img = ImageIO.read(getClass().getResource("Team_Icon.png"));
            main.addTab("Team Calendar", new ImageIcon(img), teamCalendar);
            
            } catch (IOException ex) {}
            catch(IllegalArgumentException ex){
                    main.addTab("My Calendar", new ImageIcon(), myCalendar);
                    main.addTab("Team Calendar", new ImageIcon(), teamCalendar);
            }

	}
	
	/**
	 * Gets calendar data corresponding to currently selected tab
	 * @return index
	 */
	public AbCalendar getSelectedCalendar()
	{
		int index = main.getSelectedIndex();
		if (index == 0)
			return myCalendar;
		else if (index == 1)
			return teamCalendar;
		else
		{
			System.out.println("Error getting calendar; calendar tab not selected.");
			return myCalendar;

		}
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
	
	public void removeTab(CommitmentTab commTab, boolean isTeamComm)
	{
		
		main.remove(commTab);
		if(isTeamComm){
		main.setSelectedComponent(teamCalendar);
		}
		else{
			main.setSelectedComponent(myCalendar);

		}
		
	}

	public void createCommitment() {
		CommitmentTab newCommit = new CommitmentTab();
		main.addTab("New Commitment", null, newCommit, "New Commitment");
//		newCommit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newCommit);
	}
	
	/** Edit a commitment in a new tab
	 * @param comm Commitment to edit
	 * @param calData CalendarData where commitment is located
	 */
	public void editCommitment(Commitment comm, CalendarData calData) {
		CommitmentTab editCommit = new CommitmentTab(comm, calData);
		main.addTab("Edit Commitment", null, editCommit, "Edit Commitment");
//		editCommit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(editCommit);
	}
	
	// Creates new empty tab that will be used to put all commitments 
	public void createViewCommitmentsTab() {
		CommitmentFullView commitFullView = new CommitmentFullView(teamCalendar);
		main.addTab("All Commitments", null, commitFullView, "New Commitment");
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(commitFullView);
	}

	public void createEvent() {

	}
	
	public void switchView(GregorianCalendar acal, TeamCalendar.types switchtype){
		teamCalendar.setCalsetView(acal, switchtype);
		myCalendar.setCalsetView(acal, switchtype);

	}

	public void updateCalData() {
		// TODO Auto-generated method stub
		teamCalendar.updateCalData();
		myCalendar.updateCalData();
		teamCalendar.calView.commitmentView.update();
		myCalendar.calView.commitmentView.update();
	}

	public void setScrollBarValue(int value) {
		// TODO Auto-generated method stub
		scrollBarValue = value;
	}

	public int getScrollBarValue()
	{
		return scrollBarValue;
	}

	/** Edit a team commitment
	 * @param comm Team commitment to edit
	 */
	public void editTeamCommitment(Commitment comm) {
		editCommitment(comm, teamCalendar.getCalData());
	}
	
	/** Edit a personal commitment
	 * @param comm Personal commitment to edit
	 */
	public void editPersonalCommitment(Commitment comm) {
		editCommitment(comm, myCalendar.getCalData());
	}
	
	
}
