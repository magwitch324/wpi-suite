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
		
		
		ToolbarView toolbarPanel = new ToolbarView(true);
		//toolbarPanel.setLayout(new FlowLayout());
		//toolbarPanel.add(new JButton("<html>Create<br />Commitment</html>"));
		//toolbarPanel.add(new JButton("<html>Create<br />Event</html>"));
		
		//toolbarController = new ToolbarController(toolbarPanel, mainTabController);
		
		//Instantiate event controller
		GUIEventController.getInstance().setMainView(mainPanel);
		GUIEventController.getInstance().setToolBar(toolbarPanel);

		
		tabs = new ArrayList<JanewayTabModel>();
		// Create a tab model that contains the toolBar panel and the main content panel
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainPanel);

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