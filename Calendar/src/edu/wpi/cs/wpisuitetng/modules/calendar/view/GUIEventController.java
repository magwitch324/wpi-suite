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

import java.awt.Image;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdatePropsController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
//import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.RepeatingEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProps;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarPropsModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.CategoryTab;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.FilterTab;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.CommitmentTab;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.CommitmentTab2;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.maintab.secondarytabs.EventTab;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.ToolbarView;


 /**
  * GUI event controller manages the event handling of the large elements
  * such as main tab and tool bar tab, team calendar tab, personal calendar tab,
  * and full commitment view tab.
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
public class GUIEventController {
	private static GUIEventController instance = null;
	private int scrollBarValue;
	private MainTabView main = null;
	private ToolbarView toolbar = null;
	private TeamCalendar teamCalendar;
	private MyCalendar myCalendar;
	private CommitmentFullView commitFullView;
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

			/**
			 * we add this shutdown hook in order to 
			 * avoid server communication every time someone clicks a checkbox
			 */
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					GUIEventController.getInstance().saveProps();
					
				}
			});

		}
		return instance;
	}
	
	/**
	 * Called on Janeway shutdown to save props
	 */
	public void saveProps(){
		//teamCalendar.saveProps();
		//myCalendar.saveProps();
		//commitFullView.saveProps();
		final CalendarProps calProps = CalendarPropsModel.getInstance().getCalendarProps(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName() + "-PROPS");
		if(calProps != null){
			UpdatePropsController.getInstance().updateCalendarProps(calProps);
		}
	}
	/**
	 * Called on Janeway shutdown to remove year old items
	 */
	public void removeYearOld(){
		//teamCalendar.saveProps();
		//myCalendar.saveProps();
		//commitFullView.saveProps();
		
		
	}

	/**
	 * Sets the main view to the given view.

	 * @param mainview MainView
	 */
	public void setMainView(MainTabView mainview) {
		main = mainview;
		teamCalendar = new TeamCalendar();
		myCalendar = new MyCalendar();
		commitFullView = new CommitmentFullView(teamCalendar, myCalendar);

		try {
			Image img = ImageIO.read(getClass().getResource("Personal_Icon.png"));
			main.addTab("My Calendar", new ImageIcon(img), myCalendar);

			img = ImageIO.read(getClass().getResource("Team_Icon.png"));
			main.addTab("Team Calendar", new ImageIcon(img), teamCalendar);

			img = ImageIO.read(getClass().getResource("All_Icon.png"));
			main.addTab("All Commitments", new ImageIcon(img), commitFullView);

		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			main.addTab("My Calendar", new ImageIcon(), myCalendar);
			main.addTab("Team Calendar", new ImageIcon(), teamCalendar);
			main.addTab("All Commitments", new ImageIcon(), commitFullView);
		}

	}

	/**
	 * Gets calendar data corresponding to currently selected tab
	 * @return index
	 */
	public AbCalendar getSelectedCalendar()
	{
		final int index = main.getSelectedIndex();
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

	/**
	 * Method removeCommTab.
	 * @param commTab CommitmentTab
	 * @param goTo int
	 */
	public void removeCommTab(CommitmentTab commTab, int goTo)
	{

			main.remove(commTab);
			switch(goTo){
				case 0 : main.setSelectedComponent(myCalendar);
						break;
				case 1 : main.setSelectedComponent(teamCalendar);
						break;
				case 3 : main.setSelectedComponent(commitFullView);
						break;
			}
	}

	/**
	 * Method createCommitment.
	 */
	public void createCommitment() {
		int openedFrom = main.getSelectedIndex();
		if (openedFrom > 2){
			openedFrom = 0;
		}
		final CommitmentTab newCommit = new CommitmentTab(openedFrom);
//		final CommitmentTab2 newCommit2 = new CommitmentTab2(openedFrom);
		try {
			final Image img = ImageIO.read(getClass().getResource("NewCommitment_Icon.png"));
			main.addTab("New Commitment", new ImageIcon(img), newCommit);
//			main.addTab("New Commitment2", new ImageIcon(img), newCommit2);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			main.addTab("New Commitment", new ImageIcon(), newCommit);
//			main.addTab("New Commitment2", new ImageIcon(), newCommit2);
		}
		//		main.addTab("New Commitment", null, newCommit, "New Commitment");
		//		newCommit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newCommit);
	}

	
	/** Edit a commitment in a new tab
	 * @param comm Commitment to edit
	
	 */
	public void editCommitment(Commitment comm) {
		int openedFrom = main.getSelectedIndex();
		if (openedFrom > 2){
			openedFrom = 0;
		}
		final CommitmentTab editCommit = new CommitmentTab(comm, openedFrom);
		try {
			final Image img = ImageIO.read(getClass().getResource("EditCommitment_Icon.png"));
			main.addTab("Edit Commitment", new ImageIcon(img), editCommit);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			main.addTab("Edit Commitment", new ImageIcon(), editCommit);
		}
		//		main.addTab("Edit Commitment", null, editCommit, "Edit Commitment");
		//		editCommit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(editCommit);
	}

	/**
	 * Method createEvent.
	 */
	public void createEvent() {
		int openedFrom = main.getSelectedIndex();
		if (openedFrom > 2){
			openedFrom = 0;
		}
		final EventTab newEvent = new EventTab(openedFrom);
		try {
			final Image img = ImageIO.read(getClass().getResource("NewEvent_Icon.png"));
			main.addTab("New Event", new ImageIcon(img), newEvent);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			main.addTab("New Event", new ImageIcon(), newEvent);
		}
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newEvent);
	}

	/** Edit a commitment in a new tab
	
	
	 * @param event Event
	 */
	public void editEvent(Event event) {
		int openedFrom = main.getSelectedIndex();
		if (openedFrom > 2){
			openedFrom = 0;
		}
		final EventTab editEvent;
		editEvent = new EventTab(event, openedFrom);
		try {
			final Image img = ImageIO.read(getClass().getResource("EditEvent_Icon.png"));
			main.addTab("Edit Event", new ImageIcon(img), editEvent);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			main.addTab("Edit Event", new ImageIcon(), editEvent);
		}
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(editEvent);
	}
	
	
	// Creates manage categories tab
	/**
	 * Method createManageCategories.
	 */
	public void createManageCategories() {
		int openedFrom = main.getSelectedIndex();
		if (openedFrom > 2){
			openedFrom = 0;
		}
		final CategoryTab newCat = new CategoryTab();
		try {
			final Image img = ImageIO.read(getClass().getResource("ManageCategory_Icon.png"));
			main.addTab("Manage Categories", new ImageIcon(img), newCat);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			main.addTab("Manage Category", new ImageIcon(), newCat);
		}
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newCat);
	}

	
	
//	 Creates manage filters tab
	/**
	 * Method createManageFilters.
	 */
	public void createManageFilters() {
		int openedFrom = main.getSelectedIndex();
		if (openedFrom > 2){
			openedFrom = 0;
		}
		final FilterTab newFilter = new FilterTab(openedFrom);
		try {
			final Image img = ImageIO.read(getClass().getResource("ManageFilter_Icon.png"));
			main.addTab("Manage Filters", new ImageIcon(img), newFilter);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			main.addTab("Manage Filters", new ImageIcon(), newFilter);
		}
		
		main.invalidate(); //force the tabbedpane to redraw.
		main.repaint();
		main.setSelectedComponent(newFilter);
	}


	/**
	 * Method switchView.
	 * @param acal GregorianCalendar
	 * @param switchtype AbCalendar.types
	 */
	public void switchView(GregorianCalendar acal, AbCalendar.types switchtype){
		getSelectedCalendar().setCalsetView(acal, switchtype);
		getSelectedCalendar().setViewButtonToActive(switchtype);

	}

	/**
	 * Method updateCalData.
	 */
	public void updateCalData() {
		teamCalendar.updateCalData();
		myCalendar.updateCalData();
		teamCalendar.calView.commitmentView.update();
		myCalendar.calView.commitmentView.update();
		commitFullView.updateList();
	}

	public void setScrollBarValue(int value) {
		scrollBarValue = value;
		teamCalendar.calView.updateScrollPosition(value);
		myCalendar.calView.updateScrollPosition(value);
	}

	public int getScrollBarValue()
	{
		return scrollBarValue;
	}

	/**
	 * Method applyCalProps.
	 */
	public void applyCalProps(){
		myCalendar.applyCalProps();
		teamCalendar.applyCalProps();
		commitFullView.applyCalProps();
	}

	/**
	 * Method removeEventTab.
	 * @param eventTab EventTab
	 * @param goTo int
	 */
	public void removeEventTab(EventTab eventTab, int goTo) {
		main.remove(eventTab);
		switch(goTo){
		case 0 : main.setSelectedComponent(myCalendar);
				break;
		case 1 : main.setSelectedComponent(teamCalendar);
				break;
	}
		
	}
	
	/**
	 * Method removeCategory.
	 * @param catToDelete Category
	 */
	public void removeCategory(Category catToDelete){
		//get relevant calendar data
		CalendarData calData;
		if (catToDelete.getIsPersonal()){
			calData = myCalendar.getCalData();
		} else {
			calData = teamCalendar.getCalData();
		}
		
		//Scrub the category from any commitment/event that it is assigned to
		List<Commitment> commitments = calData.getCommitments().getCommitments();
		for(Commitment tmpComm: commitments){
			if (tmpComm.getCategoryID() == catToDelete.getID()){
				tmpComm.setCategoryID(0);
			}
		}
		List<Event> events = calData.getEvents().getEvents();
		for(Event tmpEvent: events){
			if (tmpEvent.getCategoryID() == catToDelete.getID()){
				tmpEvent.setCategoryID(0);
			}
		}
		List<RepeatingEvent> repeatingEvents = calData.getRepeatingEvents().getEvents();
		for(RepeatingEvent tmpRepEvent: repeatingEvents){
			if (tmpRepEvent.getCategoryID() == catToDelete.getID()){
				tmpRepEvent.setCategoryID(0);
			}
		}
		
		//delete the category
		calData.getCategories().remove(catToDelete.getID());
	}
	
	
//	public void removeFilterTab(Filter filterToDelete){
//		CalendarData calData;
//		if (filterToDelete.getIsPersonal()){
//			calData = myCalendar.getCalData();
//		} else {
//			calData = teamCalendar.getCalData();
//		}
//	}


}
