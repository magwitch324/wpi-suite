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

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.SpringLayout;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.Box;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoryTab extends JPanel {

	private CategoryList teamCategories;
	private CategoryList personalCategories;
	private JRadioButton rdbtnPersonal;
	private JRadioButton rdbtnBoth;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;
	private JRadioButton rdbtnTeam;
	private JPanel categoryListPanel;
	private SpringLayout categoryListLayout;
	private JPanel addEditPanel;
	private JPanel viewPanel;
	private CategoryMode mode;
	private Component viewPanelStrut;

	private enum CategoryMode {
		ADDING(0),
		EDITING(1),
		VIEWING(2);
		private final int currentMode;
		
		private CategoryMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}
	
	public CategoryTab() {
		
		createBaseUI();
		
		//Load category lists from CalendarDataModel
		teamCategories = CalendarDataModel.getInstance().getCalendarData(ConfigManager.getConfig().getProjectName()).getCategories();
		personalCategories = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName()).getCategories();
		
		populateCategoryList();
		addListeners();
		
		//initialize in "viewing" mode
		setupViewingView();
		
	}




	/**
	 * Instantiate components of UI
	 */
	private void createBaseUI() {
		
		
		viewPanel = new JPanel();
		
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		viewPanel.add(horizontalBox);
		ButtonGroup teamPersonalRadioButtons = new ButtonGroup();
		
		rdbtnTeam = new JRadioButton("Team");
		teamPersonalRadioButtons.add(rdbtnTeam);
		horizontalBox.add(rdbtnTeam);
		
		rdbtnPersonal = new JRadioButton("Personal");
		teamPersonalRadioButtons.add(rdbtnPersonal);
		horizontalBox.add(rdbtnPersonal);
		
		rdbtnBoth = new JRadioButton("Both");
		rdbtnBoth.setSelected(true);
		teamPersonalRadioButtons.add(rdbtnBoth);
		horizontalBox.add(rdbtnBoth);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		viewPanel.add(scrollPane);
		
		categoryListPanel = new JPanel();
		scrollPane.setViewportView(categoryListPanel);
		categoryListLayout = new SpringLayout();
		categoryListPanel.setLayout(categoryListLayout);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		viewPanel.add(horizontalBox_1);
		
		btnDelete = new JButton("Delete");
		horizontalBox_1.add(btnDelete);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setMaximumSize(new Dimension(20, 0));
		horizontalBox_1.add(horizontalStrut_1);
		
		btnEdit = new JButton("Edit");
		horizontalBox_1.add(btnEdit);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setMaximumSize(new Dimension(20, 0));
		horizontalBox_1.add(horizontalStrut_2);
		
		btnNew = new JButton("New");
		
		horizontalBox_1.add(btnNew);
		
		viewPanelStrut = Box.createHorizontalStrut(600);
		viewPanelStrut.setMaximumSize(new Dimension(600, 0));
		viewPanel.add(viewPanelStrut);
		
		addEditPanel = new JPanel();
		addEditPanel.setLayout(new GridLayout(0, 1, 0, 0));
	}
	
	/**
	 * Populate the list of categories with data from team and personal category lists
	 */
	private void populateCategoryList() {
		
		List<Category> catList = new ArrayList<Category>();
		if(rdbtnPersonal.isSelected())
		{
			catList.addAll(personalCategories.getCategories());
		}
		else if(rdbtnTeam.isSelected())
		{
			catList.addAll(teamCategories.getCategories());
		}
		else
		{
			Category[] teamCatArray = new Category[teamCategories.getSize()];
			catList.addAll(teamCategories.getCategories());
			for(int i = 0; i < catList.size(); i++)
			{
				teamCatArray[i] = catList.get(i);
			}
			personalCategories.addCategories(teamCatArray);
			personalCategories.sortByAlphabet();
			catList.clear();
			catList.addAll(personalCategories.getCategories());
		}
		
//		for(Category cat: catList)
//		{
//			categoryListPanel.add(new CategoryPanel(cat));
//		}
		CategoryPanel catPanel = new CategoryPanel(new Category("GUI", Color.red, true));
		categoryListLayout.putConstraint(SpringLayout.NORTH, catPanel, 1, SpringLayout.NORTH, categoryListPanel);
		categoryListLayout.putConstraint(SpringLayout.WEST, catPanel, 1, SpringLayout.WEST, categoryListPanel);
		categoryListLayout.putConstraint(SpringLayout.EAST, catPanel, 1, SpringLayout.EAST, categoryListPanel);
		categoryListPanel.add(catPanel);
		
		CategoryPanel oldCatPanel = catPanel;
		catPanel = new CategoryPanel(new Category("Dev", Color.blue, true));
		categoryListLayout.putConstraint(SpringLayout.NORTH, catPanel, 1, SpringLayout.SOUTH, oldCatPanel);
		categoryListLayout.putConstraint(SpringLayout.WEST, catPanel, 1, SpringLayout.WEST, categoryListPanel);
		categoryListLayout.putConstraint(SpringLayout.EAST, catPanel, 1, SpringLayout.EAST, categoryListPanel);
		

		categoryListPanel.add(catPanel);

		
	}
	

	/**
	 * Add event handlers to GUI components
	 */
	private void addListeners() {
		// TODO Auto-generated method stub
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupAddView();
			}
		});
	}
	
	/**
	 * Setup the Adding view, where a user will create a new Category and save it.
	 * The category list will still be visible on the left
	 */
	void setupAddView()
	{
		mode = CategoryMode.ADDING;
		this.removeAll();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_viewPanel = new GridBagConstraints();
		gbc_viewPanel.insets = new Insets(0, 0, 0, 5);
		gbc_viewPanel.fill = GridBagConstraints.BOTH;
		gbc_viewPanel.gridx = 0;
		gbc_viewPanel.gridy = 0;
		add(viewPanel, gbc_viewPanel);
		
		GridBagConstraints gbc_addEditPanel = new GridBagConstraints();
		gbc_addEditPanel.fill = GridBagConstraints.BOTH;
		gbc_addEditPanel.gridx = 1;
		gbc_addEditPanel.gridy = 0;
		add(addEditPanel, gbc_addEditPanel);
		
		//set size of view panel
		viewPanel.remove(viewPanelStrut);
		viewPanelStrut = Box.createHorizontalStrut(400);
		viewPanelStrut.setMaximumSize(new Dimension(400, 0));
		viewPanel.add(viewPanelStrut);

		
	}
	
	/**
	 * Set up the view for "viewing" mode, where the user can browse and
	 * interact with the list of categories
	 */
	void setupViewingView()
	{
		mode = CategoryMode.VIEWING;
		this.removeAll();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_viewPanel = new GridBagConstraints();
		gbc_viewPanel.insets = new Insets(0, 0, 0, 5);
		gbc_viewPanel.fill = GridBagConstraints.BOTH;
		gbc_viewPanel.gridx = 1;
		gbc_viewPanel.gridy = 0;
		add(viewPanel, gbc_viewPanel);

		//set size of view panel
		viewPanel.remove(viewPanelStrut);
		viewPanelStrut = Box.createHorizontalStrut(600);
		viewPanelStrut.setMaximumSize(new Dimension(600, 0));
		viewPanel.add(viewPanelStrut);
		
	}
	
	
}
