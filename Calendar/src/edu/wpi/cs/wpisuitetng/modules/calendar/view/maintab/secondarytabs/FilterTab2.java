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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.FilterList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;

public class FilterTab2 extends JPanel {

	private enum FilterMode {
		ADDING(0), EDITING(1), VIEWING(2);
		private final int currentMode;

		private FilterMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}

	private FilterMode mode = FilterMode.VIEWING;
	private JPanel viewPanel;
	protected FilterPanel selectedFilterPanel;
	private JPanel editPanel;
	private CategoryList teamCategories;
	private CategoryList personalCategories;
	private ArrayList<Category> inactiveCategories;
	private ArrayList<Category> activeCategories;

	private FilterList calendarFilters;
	private SpringLayout filterListLayout;
	private JPanel filterListPanel;
	private JPanel inactiveListPanel;
	private SpringLayout inactiveListLayout;
	protected CategoryPanel selectedCategoryPanel;
	private JButton addCatBtn;
	private JButton removeCatBtn;
	private JPanel activeListPanel;
	private SpringLayout activeListLayout;
	private JTextField filterName;
	private final int openedFrom;
	private JButton btnSaveFilter;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnDeleteFilter;

	public FilterTab2(int openedFrom) {
		this.openedFrom = openedFrom;

		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);

		refreshCalData();

		refreshMainView();
	}

	public void refreshCalData() {
		// Load category lists and filter lists from CalendarDataModel
		teamCategories = CalendarDataModel.getInstance()
				.getCalendarData(ConfigManager.getConfig().getProjectName())
				.getCategories();
		personalCategories = CalendarDataModel
				.getInstance()
				.getCalendarData(
						ConfigManager.getConfig().getProjectName() + "-"
								+ ConfigManager.getConfig().getUserName())
				.getCategories();
		calendarFilters = CalendarDataModel
				.getInstance()
				.getCalendarData(
						ConfigManager.getConfig().getProjectName() + "-"
								+ ConfigManager.getConfig().getUserName())
				.getFilters();
	}

	public void refreshEditView() {
		refreshEditView(null);
	}

	public void refreshEditView(Filter editFilter) {
		refreshMainView();

		if (editFilter == null) {
			setupCategoryLists(null);
		} else {
			setupCategoryLists(editFilter);
		}
		checkFilterValid();
		revalidate();
		repaint();
	}

	public void refreshMainView() {
		removeAll();

		if (mode == FilterMode.VIEWING) {
			mainFilterListView();
		} else {
			addFilterListView();
			addEditFilterView();
		}

		populateFilterList();

		revalidate();
		repaint();
	}

	/**
	 * This function will take a Filter (or null) and set the category lists
	 * (active and inactive) as appropriate
	 */
	public void setupCategoryLists(Filter filter) {
		refreshCalData();
		inactiveCategories = new ArrayList<Category>();
		activeCategories = new ArrayList<Category>();

		if (filter == null) {
			for (Category c : teamCategories.getCategories()) {
				inactiveCategories.add(c);
			}
			for (Category c : personalCategories.getCategories()) {
				inactiveCategories.add(c);
			}
		} else {
			for (Category c : personalCategories.getCategories()) {
				boolean isActive = false;
				for (Integer id : selectedFilterPanel.getFilter()
						.getActivePersonalCategories()) {
					if (c.getID() == id) {
						activeCategories.add(c);
						isActive = true;
					}
				}
				if (!isActive)
					{
						inactiveCategories.add(c);
					}
			}

			for (Category c : teamCategories.getCategories()) {
				boolean isActive = false;
				for (Integer id : selectedFilterPanel.getFilter()
						.getActiveTeamCategories()) {
					if (c.getID() == id) {
						activeCategories.add(c);
						isActive = true;
					}
				}
				if (!isActive)
					{
						inactiveCategories.add(c);
					}
			}

		}
		populateInactiveCategoryList();
		populateActiveCategoryList();
	}

	public void addCategoryToFilter() {
		inactiveCategories.remove(
				selectedCategoryPanel.getCategory());
		activeCategories.add(selectedCategoryPanel.getCategory());
		checkFilterValid();
		populateInactiveCategoryList();
		populateActiveCategoryList();
	}

	public void removeCategoryFromFilter() {
		activeCategories.remove(
				selectedCategoryPanel.getCategory());
		inactiveCategories.add(selectedCategoryPanel.getCategory());
		checkFilterValid();
		populateInactiveCategoryList();
		populateActiveCategoryList();
	}

	private void addFilter() {
		final CalendarData calData;

		final String name = filterName.getText();

		calData = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + "-"
						+ ConfigManager.getConfig().getUserName());

		final List<Integer> activePersonalCategories = new ArrayList<Integer>();
		final List<Integer> activeTeamCategories = new ArrayList<Integer>();

		for (Category c : activeCategories) {
			if (c.getIsPersonal())
				{
					activePersonalCategories.add(c.getID());
				}
			else
				{
					activeTeamCategories.add(c.getID());
				}
		}

		final Filter newFilter = new Filter(name, activePersonalCategories,
				activeTeamCategories);
		if (mode == FilterMode.ADDING) {
			calData.addFilter(newFilter);
		} else {
			selectedFilterPanel.getFilter().setName(name);
			selectedFilterPanel.getFilter().setActiveTeamCategories(
					activeTeamCategories);
			selectedFilterPanel.getFilter().setActivePersonalCategories(
					activePersonalCategories);
			calData.getFilters().update(selectedFilterPanel.getFilter());
		}
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
	}

	public void checkFilterValid() {
		if (filterName.getText().trim().length() == 0 ||
				activeCategories.size() == 0) {
			btnSaveFilter.setEnabled(false);
		} else {
			btnSaveFilter.setEnabled(true);
		}
		if (mode == FilterMode.EDITING) {
			boolean change = false;
			int countMatches = selectedFilterPanel.getFilter().getActivePersonalCategories().size() +
					   selectedFilterPanel.getFilter().getActiveTeamCategories().size();
			for (Category c : activeCategories) {
				boolean contained = false;
				if (c.getIsPersonal()) {
					for (Integer id : selectedFilterPanel.getFilter()
							.getActivePersonalCategories()) {
						if (c.getID() == id) {
							contained = true;
							countMatches--;
							System.out.println("Got a match Personal");
						}
						
					}
				} else if (!c.getIsPersonal()) {
					for (Integer id : selectedFilterPanel.getFilter()
							.getActiveTeamCategories()) {
						if (c.getID() == id) {
							contained = true;
							countMatches--;
							System.out.println("Got a match Team");
						}
					}
				}
				if (!contained) change = true;
			} 
			if (countMatches != 0) change = true;
			if (!filterName.getText().equals(selectedFilterPanel.getFilter().getName())) change = true;
			if (!change) btnSaveFilter.setEnabled(false);
				
		}
		
	}
	
	
	// Functions after this point are used for adding items to the view

	/**
	 * Create the Main view that shows the list of filters This function puts
	 * the list in the center when we are in Viewing mode
	 */
	public void mainFilterListView() {
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		gbl.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl.rowHeights = new int[] { 0, 0, 0 };
		gbl.columnWidths = new int[] { 1, 0, 1 };
		setupFilterList(gbl);
	}

	/**
	 * Create the Main view that shows the list of filters This function puts
	 * the list on the left when we are in adding or editing mode.
	 */
	public void addFilterListView() {
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[] { 0.0, 1.0, 0.0 };
		gbl.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gbl.rowHeights = new int[] { 0, 0, 0 };
		gbl.columnWidths = new int[] { 1, 0, 1 };
		setupFilterList(gbl);
	}

	public void setupFilterList(GridBagLayout gbl) {
		viewPanel = new JPanel();
		viewPanel.setBackground(Color.WHITE);
		viewPanel.setPreferredSize(new Dimension(500, 600));
		viewPanel.setMaximumSize(new Dimension(500, 600));

		viewPanel.setLayout(gbl);

		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(viewPanel, constraints);

		// Adds the scroll pane the filters will be on
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setBackground(
				CalendarStandard.CalendarYellow);
		scrollPane.getViewport().setBackground(Color.WHITE);
		final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 15, 5, 15);
		gbc_scrollPane.gridwidth = 1;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		viewPanel.add(scrollPane, gbc_scrollPane);

		filterListPanel = new JPanel();
		scrollPane.setViewportView(filterListPanel);
		filterListPanel.setBackground(Color.WHITE);
		filterListLayout = new SpringLayout();
		filterListPanel.setLayout(filterListLayout);

		// Adds the label on top of the scroll pane
		final JLabel filterList = new JLabel("List of Filters",
				SwingConstants.CENTER);
		filterList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		filterList.setForeground(Color.WHITE);
		filterList.setBackground(CalendarStandard.CalendarRed);
		filterList.setOpaque(true);
		final GridBagConstraints gbc_filterList = new GridBagConstraints();
		gbc_filterList.fill = GridBagConstraints.BOTH;
		gbc_filterList.insets = new Insets(5, 15, 0, 15);
		gbc_filterList.gridwidth = 3;
		gbc_filterList.gridx = 0;
		gbc_filterList.gridy = 0;
		viewPanel.add(filterList, gbc_filterList);

		if (mode == FilterMode.VIEWING) addButtonPanel();
	}

	/**
	 * Adds the button panel to Filter tab for viewing mode. Delete and edit
	 * button are disabled by default.
	 * 
	 */
	private void addButtonPanel() {

		JPanel buttonPanel = new JPanel(new BorderLayout(25, 0));
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 2;
		gbc_btnPanel.insets = new Insets(0, 15, 0, 15);

		// New Filter button
		JButton btnNewFilter = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"New_Icon.png"));
			btnNewFilter.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnNewFilter.setIcon(new ImageIcon());
		}
		btnNewFilter.setText("New Filter");
		btnNewFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		btnNewFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = FilterMode.ADDING;
				selectedFilterPanel = null;
				refreshEditView();
			}
		});

		// Add Edit button
		btnEdit = new JButton();
		btnEdit.setEnabled(false);
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Edit_Icon.png"));
			btnEdit.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnEdit.setIcon(new ImageIcon());
		}
		btnEdit.setText("Edit Filter");
		btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = FilterMode.EDITING;
				refreshEditView(selectedFilterPanel.getFilter());
			}
		});

		// Add Delete Button
		btnDelete = new JButton();
		btnDelete.setEnabled(false);
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Delete_Icon.png"));
			btnDelete.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnDelete.setIcon(new ImageIcon());
		}
		btnDelete.setText("Delete Filter");
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalendarData calData;

				calData = CalendarDataModel.getInstance().getCalendarData(
						ConfigManager.getConfig().getProjectName() + "-"
								+ ConfigManager.getConfig().getUserName());
				calendarFilters.remove(selectedFilterPanel.getFilter().getID());
				UpdateCalendarDataController.getInstance().updateCalendarData(
						calData);
				refreshMainView();
			}
		});

		if (mode == FilterMode.VIEWING) {
			buttonPanel.add(btnNewFilter, BorderLayout.WEST);
			buttonPanel.add(btnEdit, BorderLayout.CENTER);
			buttonPanel.add(btnDelete, BorderLayout.LINE_END);
		} else {
			buttonPanel.add(btnEdit, BorderLayout.LINE_START);
			buttonPanel.add(btnDelete, BorderLayout.LINE_END);
		}

		// Set the horizontal gap
		viewPanel.add(buttonPanel, gbc_btnPanel);
	}

	/**
	 * Method editingMode.
	 */
	public void addEditFilterView() {

		editPanel = new JPanel();
		editPanel.setBackground(Color.WHITE);
		editPanel.setPreferredSize(new Dimension(500, 600));
		editPanel.setMinimumSize(new Dimension(500, 600));

		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0 };
		gbl.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0 };
		gbl.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl.columnWidths = new int[] { 0, 0, 0, 0 };
		editPanel.setLayout(gbl);

		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(editPanel, constraints);

		final JLabel filterNamelbl = new JLabel("<html><font>" + "Filter Name"
				+ "</font>" + "<font color=red>" + "*" + "</font>" + "<font>"
				+ ":" + "</font></html>");
		filterNamelbl.setBackground(Color.WHITE);
		filterNamelbl.setOpaque(true);
		filterNamelbl.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_filterNamelbl = new GridBagConstraints();
		gbc_filterNamelbl.insets = new Insets(0, 30, 0, 5);
		gbc_filterNamelbl.fill = GridBagConstraints.BOTH;
		gbc_filterNamelbl.gridx = 0;
		gbc_filterNamelbl.gridy = 0;
		editPanel.add(filterNamelbl, gbc_filterNamelbl);

		final JLabel inactiveFilterlbl = new JLabel();
		inactiveFilterlbl.setText("List of Catagories:");
		inactiveFilterlbl.setBackground(Color.WHITE);
		inactiveFilterlbl.setOpaque(true);
		inactiveFilterlbl.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_inactiveFilterlbl = new GridBagConstraints();
		gbc_inactiveFilterlbl.insets = new Insets(0, 30, 0, 5);
		gbc_inactiveFilterlbl.fill = GridBagConstraints.BOTH;
		gbc_inactiveFilterlbl.gridx = 0;
		gbc_inactiveFilterlbl.gridy = 3;
		editPanel.add(inactiveFilterlbl, gbc_inactiveFilterlbl);

		final JLabel activeFilterlbl = new JLabel();
		activeFilterlbl.setText("Catagories in Filter:");
		activeFilterlbl.setBackground(Color.WHITE);
		activeFilterlbl.setOpaque(true);
		activeFilterlbl.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_activeFilterlbl = new GridBagConstraints();
		gbc_activeFilterlbl.insets = new Insets(0, 30, 0, 5);
		gbc_activeFilterlbl.fill = GridBagConstraints.BOTH;
		gbc_activeFilterlbl.gridx = 0;
		gbc_activeFilterlbl.gridy = 1;
		editPanel.add(activeFilterlbl, gbc_activeFilterlbl);

		// Adds the text field for the name of the filter
		filterName = new JTextField();
		if (mode == FilterMode.EDITING) filterName.setText(selectedFilterPanel.getFilter().getName()); 
		filterName.setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_filterName = new GridBagConstraints();
		gbc_filterName.fill = GridBagConstraints.BOTH;
		gbc_filterName.insets = new Insets(5, 0, 5, 15);
		gbc_filterName.gridwidth = 3;
		gbc_filterName.gridx = 1;
		gbc_filterName.gridy = 0;
		editPanel.add(filterName, gbc_filterName);
		filterName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				checkFilterValid();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				checkFilterValid();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				checkFilterValid();
			}
		});

		// adds the scroll pane containing the categories not in the filter
		JScrollPane inactiveCatPane = new JScrollPane();
		inactiveCatPane.setPreferredSize(new Dimension(2, 200));
		inactiveCatPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		inactiveCatPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		inactiveCatPane.getVerticalScrollBar().setBackground(
				CalendarStandard.CalendarYellow);
		inactiveCatPane.getViewport().setBackground(Color.WHITE);
		final GridBagConstraints gbc_inactiveFilter = new GridBagConstraints();
		gbc_inactiveFilter.fill = GridBagConstraints.BOTH;
		gbc_inactiveFilter.insets = new Insets(5, 0, 5, 15);
		gbc_inactiveFilter.gridwidth = 3;
		gbc_inactiveFilter.gridx = 1;
		gbc_inactiveFilter.gridy = 3;
		editPanel.add(inactiveCatPane, gbc_inactiveFilter);

		// adds the scroll pane containing the categories in the filter
		JScrollPane activeCatPane = new JScrollPane();
		activeCatPane.setPreferredSize(new Dimension(2, 200));
		activeCatPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		activeCatPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		activeCatPane.getVerticalScrollBar().setBackground(
				CalendarStandard.CalendarYellow);
		activeCatPane.getViewport().setBackground(Color.WHITE);
		final GridBagConstraints gbc_activeFilter = new GridBagConstraints();
		gbc_activeFilter.fill = GridBagConstraints.BOTH;
		gbc_activeFilter.insets = new Insets(5, 0, 5, 15);
		gbc_activeFilter.gridwidth = 3;
		gbc_activeFilter.gridx = 1;
		gbc_activeFilter.gridy = 1;
		editPanel.add(activeCatPane, gbc_activeFilter);

		inactiveListPanel = new JPanel();
		inactiveCatPane.setViewportView(inactiveListPanel);
		inactiveListPanel.setBackground(Color.WHITE);
		inactiveListLayout = new SpringLayout();
		inactiveListPanel.setLayout(inactiveListLayout);

		activeListPanel = new JPanel();
		activeCatPane.setViewportView(activeListPanel);
		activeListPanel.setBackground(Color.WHITE);
		activeListLayout = new SpringLayout();
		activeListPanel.setLayout(activeListLayout);

		// add the two buttons to move categories between active and inactive
		// panes
		JPanel catBtnPanel = new JPanel(new BorderLayout(20, 0));
		catBtnPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_catBtnPanel = new GridBagConstraints();
		gbc_catBtnPanel.anchor = GridBagConstraints.CENTER;
		gbc_catBtnPanel.insets = new Insets(5, 0, 5, 0);
		gbc_catBtnPanel.gridx = 2;
		gbc_catBtnPanel.gridy = 2;

		// Add Category to Filter button
		addCatBtn = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"GreenArrowUp_Icon.png"));
			addCatBtn.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			addCatBtn.setIcon(new ImageIcon());
		}
		addCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		addCatBtn.setToolTipText("Add Category to Filter");
		addCatBtn.setEnabled(false);
		addCatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCategoryToFilter();
				addCatBtn.setEnabled(false);
			}
		});

		// Remove Category from Filter button
		removeCatBtn = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"RedArrowDown_Icon.png"));
			removeCatBtn.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			removeCatBtn.setIcon(new ImageIcon());
		}
		removeCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		removeCatBtn.setToolTipText("Remove Category from Filter");
		removeCatBtn.setEnabled(false);
		removeCatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeCategoryFromFilter();
				removeCatBtn.setEnabled(false);
			}
		});

		catBtnPanel.add(addCatBtn, BorderLayout.WEST);
		catBtnPanel.add(removeCatBtn, BorderLayout.EAST);
		editPanel.add(catBtnPanel, gbc_catBtnPanel);

		addEditFilterButtonPanel();
	}

	/**
	 * Method addButtonPanel2.
	 */
	public void addEditFilterButtonPanel() {
		JPanel filterButtonPanel = new JPanel(new BorderLayout(25, 0));
		filterButtonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel2 = new GridBagConstraints();
		gbc_btnPanel2.insets = new Insets(0, 0, 0, 15);
		gbc_btnPanel2.gridwidth = 3;
		gbc_btnPanel2.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel2.gridx = 1;
		gbc_btnPanel2.gridy = 4;

		// New Save button
		btnSaveFilter = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Save_Icon.png"));
			btnSaveFilter.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnSaveFilter.setIcon(new ImageIcon());
		}
		btnSaveFilter.setText("Save Filter");
		btnSaveFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		btnSaveFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFilter();
				mode = FilterMode.VIEWING;
				refreshMainView();
			}
		});

		// New Cancel button
		JButton btnCancelFilter = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Cancel_Icon.png"));
			btnCancelFilter.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnCancelFilter.setIcon(new ImageIcon());
		}
		btnCancelFilter.setText("Cancel");
		btnCancelFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		btnCancelFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = FilterMode.VIEWING;
				refreshMainView();
			}
		});
		
		// Add Delete Button
		btnDeleteFilter = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource(
					"Delete_Icon.png"));
			btnDeleteFilter.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		} catch (IllegalArgumentException ex) {
			btnDeleteFilter.setIcon(new ImageIcon());
		}
		btnDeleteFilter.setText("Delete Filter");
		btnDeleteFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// To change cursor as it moves over this button
		btnDeleteFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CalendarData calData;

				calData = CalendarDataModel.getInstance().getCalendarData(
						ConfigManager.getConfig().getProjectName() + "-"
								+ ConfigManager.getConfig().getUserName());
				calendarFilters.remove(selectedFilterPanel.getFilter().getID());
				UpdateCalendarDataController.getInstance().updateCalendarData(
						calData);
				
				mode = FilterMode.VIEWING;
				refreshMainView();
			}
		});

		if (mode == FilterMode.EDITING) {
			filterButtonPanel.add(btnSaveFilter, BorderLayout.WEST);
			filterButtonPanel.add(btnCancelFilter, BorderLayout.CENTER);
			filterButtonPanel.add(btnDeleteFilter, BorderLayout.EAST);
		} else {
			filterButtonPanel.add(btnSaveFilter, BorderLayout.WEST);
			filterButtonPanel.add(btnCancelFilter, BorderLayout.EAST);
		}
		// Set the horizontal gap
		editPanel.add(filterButtonPanel, gbc_btnPanel2);
	}

	private void populateFilterList() {

		final List<Filter> filterList = new ArrayList<Filter>();
		filterList.addAll(calendarFilters.getFilters());

		// FilterPanel to keep track of spring layout constraints of previously
		// added panel
		FilterPanel oldFilterPanel = new FilterPanel();
		FilterPanel filterPanel = new FilterPanel();
		for (int i = 0; i < filterList.size(); i++) {
			filterPanel = new FilterPanel(filterList.get(i));
			// If first panel, add to top of list panel
			if (i == 0) {
				filterListLayout.putConstraint(SpringLayout.NORTH, filterPanel,
						1, SpringLayout.NORTH, filterListPanel);
				filterListLayout.putConstraint(SpringLayout.WEST, filterPanel,
						1, SpringLayout.WEST, filterListPanel);
				filterListLayout.putConstraint(SpringLayout.EAST, filterPanel,
						1, SpringLayout.EAST, filterListPanel);
			} else {
				// add panel below previous panel
				filterListLayout.putConstraint(SpringLayout.NORTH, filterPanel,
						1, SpringLayout.SOUTH, oldFilterPanel);
				filterListLayout.putConstraint(SpringLayout.WEST, filterPanel,
						1, SpringLayout.WEST, filterListPanel);
				filterListLayout.putConstraint(SpringLayout.EAST, filterPanel,
						1, SpringLayout.EAST, filterListPanel);
			}
			filterListPanel.add(filterPanel);
			if (selectedFilterPanel != null)
				if (filterPanel.getFilter().equals(selectedFilterPanel.getFilter())) 
					{
						filterPanel.setSelected(true);
					}

			filterPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					FilterPanel fp = (FilterPanel) e.getComponent();
					
					btnEdit.setEnabled(true);
					btnDelete.setEnabled(true);
					
					if (selectedFilterPanel != null)
						{
							selectedFilterPanel.setSelected(false);
						}
					if (e.getClickCount() > 1) {
						mode = FilterMode.EDITING;
						selectedFilterPanel = fp;
						fp.setSelected(true);
						refreshEditView(fp.getFilter());
					} else if (e.getClickCount() == 1) {
						if (mode == FilterMode.VIEWING) {
							selectedFilterPanel = fp;
							fp.setSelected(true);
						}
					}
				}
			});

			oldFilterPanel = filterPanel;
		}

		filterListLayout.putConstraint(SpringLayout.SOUTH, filterListPanel, 0,
				SpringLayout.SOUTH, filterPanel);
	}

	private void populateInactiveCategoryList() {

		final List<Category> catList = inactiveCategories;

		inactiveListPanel.removeAll();
		// CategoryPanel to keep track of spring layout constraints of
		// previously added panel
		CategoryPanel oldCatPanel = new CategoryPanel();
		CategoryPanel catPanel = new CategoryPanel();
		for (int i = 0; i < catList.size(); i++) {
			catPanel = new CategoryPanel(catList.get(i));
			// If first panel, add to top of list panel
			if (i == 0) {
				inactiveListLayout.putConstraint(SpringLayout.NORTH, catPanel,
						1, SpringLayout.NORTH, inactiveListPanel);
				inactiveListLayout.putConstraint(SpringLayout.WEST, catPanel,
						1, SpringLayout.WEST, inactiveListPanel);
				inactiveListLayout.putConstraint(SpringLayout.EAST, catPanel,
						1, SpringLayout.EAST, inactiveListPanel);
			} else {
				// add panel below previous panel
				inactiveListLayout.putConstraint(SpringLayout.NORTH, catPanel,
						1, SpringLayout.SOUTH, oldCatPanel);
				inactiveListLayout.putConstraint(SpringLayout.WEST, catPanel,
						1, SpringLayout.WEST, inactiveListPanel);
				inactiveListLayout.putConstraint(SpringLayout.EAST, catPanel,
						1, SpringLayout.EAST, inactiveListPanel);
			}

			inactiveListPanel.add(catPanel);

			catPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					CategoryPanel cp = (CategoryPanel) e.getComponent();
					if (selectedCategoryPanel != null)
						{
							selectedCategoryPanel.setSelected(false);
						}
					if (e.getClickCount() > 1) {
						selectedCategoryPanel = cp;
						cp.setSelected(true);
						addCategoryToFilter();
						addCatBtn.setEnabled(false);
					} else if (e.getClickCount() == 1) {
						selectedCategoryPanel = cp;
						cp.setSelected(true);
						addCatBtn.setEnabled(true);
						removeCatBtn.setEnabled(false);
					}
				}
			});

			oldCatPanel = catPanel; // update oldCatPanel to be previously added
									// panel
		}

		inactiveListLayout.putConstraint(SpringLayout.SOUTH, inactiveListPanel,
				0, SpringLayout.SOUTH, catPanel);

		inactiveListPanel.revalidate();
		inactiveListPanel.repaint();
	}

	private void populateActiveCategoryList() {

		final List<Category> catList = activeCategories;

		activeListPanel.removeAll();
		// CategoryPanel to keep track of spring layout constraints of
		// previously added panel
		CategoryPanel oldCatPanel = new CategoryPanel();
		CategoryPanel catPanel = new CategoryPanel();
		for (int i = 0; i < catList.size(); i++) {
			catPanel = new CategoryPanel(catList.get(i));
			// If first panel, add to top of list panel
			if (i == 0) {
				activeListLayout.putConstraint(SpringLayout.NORTH, catPanel, 1,
						SpringLayout.NORTH, activeListPanel);
				activeListLayout.putConstraint(SpringLayout.WEST, catPanel, 1,
						SpringLayout.WEST, activeListPanel);
				activeListLayout.putConstraint(SpringLayout.EAST, catPanel, 1,
						SpringLayout.EAST, activeListPanel);
			} else {
				// add panel below previous panel
				activeListLayout.putConstraint(SpringLayout.NORTH, catPanel, 1,
						SpringLayout.SOUTH, oldCatPanel);
				activeListLayout.putConstraint(SpringLayout.WEST, catPanel, 1,
						SpringLayout.WEST, activeListPanel);
				activeListLayout.putConstraint(SpringLayout.EAST, catPanel, 1,
						SpringLayout.EAST, activeListPanel);
			}

			activeListPanel.add(catPanel);

			catPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					CategoryPanel cp = (CategoryPanel) e.getComponent();
					if (selectedCategoryPanel != null)
						{
							selectedCategoryPanel.setSelected(false);
						}
					if (e.getClickCount() > 1) {
						selectedCategoryPanel = cp;
						cp.setSelected(true);
						removeCategoryFromFilter();
						removeCatBtn.setEnabled(false);
					} else if (e.getClickCount() == 1) {
						selectedCategoryPanel = cp;
						cp.setSelected(true);
						removeCatBtn.setEnabled(true);
						addCatBtn.setEnabled(false);
					}
				}
			});

			oldCatPanel = catPanel; // update oldCatPanel to be previously added
									// panel
		}

		activeListLayout.putConstraint(SpringLayout.SOUTH, activeListPanel, 0,
				SpringLayout.SOUTH, catPanel);

		activeListPanel.revalidate();
		activeListPanel.repaint();
	}

}
