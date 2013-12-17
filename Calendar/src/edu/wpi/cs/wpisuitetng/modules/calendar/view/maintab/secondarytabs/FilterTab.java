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
import java.awt.Container;
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


/**
 * Create/edit filter tab.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
 public class FilterTab extends JPanel{

	private final FilterList calendarFilters;
	private final int openedFrom;
	private JPanel buttonPanel;
	private Container viewPanel;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNewFilter;
	private boolean initFlag;
	private JScrollPane scrollPane;
	private JScrollPane inactiveCatPane;
	private JScrollPane activeCatPane;
	private JPanel buttonPanel2;
	private JButton btnSaveFilter;
	private JButton btnCancelFilter;
	private JPanel editPanel;
	private JButton addCatBtn;
	private JButton removeCatBtn;
	private JPanel catBtnPanel;
	private FilterMode mode;
	private JTextField filterName;
	private final CategoryList teamCategories;
	private final CategoryList personalCategories;
	private SpringLayout filterListLayout;
	private JPanel filterListPanel;
	private JPanel inactiveCatListPanel;
	private SpringLayout inactiveListLayout;
	private JPanel inactiveListPanel;
	private JPanel activeListPanel;
	private SpringLayout activeListLayout;
	private Filter editFilter;
	protected List<FilterPanel> selectedFilters;
	protected List<CategoryPanel> selectedCategories;
	private List<Integer> oldActiveTeamCat;
	private List<Integer> oldActivePersonalCat;
	private Filter aSelectedFilter;
	private Category aSelectedCategory;
	private CategoryList allCategories;
	private List<Integer> newTeamCatList;
	private List<Integer> newPersonalCatList;
	private final CategoryList inactiveTeamCat;
	private final CategoryList inactivePersonalCat;
	private CategoryList activeTeamCat;
	private CategoryList activePersonalCat;


	private enum FilterMode {
		ADDING(0),
		EDITING(1),
		VIEWING(2);
		private final int currentMode;
		
		private FilterMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}
	/**
	 * Constructor for Filter TAb.
	 * @param openedFrom int
	 */
	public FilterTab(int openedFrom) {
		
		this.openedFrom = openedFrom;
		initFlag = false;
		mode = FilterMode.VIEWING;
		
		//Load category lists and filter lists from CalendarDataModel
		teamCategories = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName()).getCategories(); 
		personalCategories = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + 
				"-" + ConfigManager.getConfig().getUserName()).getCategories(); 
		calendarFilters = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + 
				"-" + ConfigManager.getConfig().getUserName()).getFilters();
		
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		setLayout(gridBagLayout);
		
		aSelectedFilter = null;
		selectedFilters = new ArrayList<FilterPanel>();
		selectedCategories = new ArrayList<CategoryPanel>();
		inactiveTeamCat = new CategoryList();
		inactivePersonalCat = new CategoryList();
		for(Category c:teamCategories.getCategories()){
			inactiveTeamCat.add(c);
		}
		for(Category c:personalCategories.getCategories()){
			inactivePersonalCat.add(c);
		} 
		
		activeTeamCat = new CategoryList();
		activePersonalCat = new CategoryList();
		newTeamCatList = null;
		newPersonalCatList = null;
		
		refresh();
		initFlag = true;
		}
	
	
	/**
	 * Method FilterList.
	 */
	public void mainFilterListView(){
		
		viewPanel = new JPanel();
		viewPanel.setBackground(Color.WHITE);
		viewPanel.setPreferredSize(new Dimension(500, 600));
		viewPanel.setMaximumSize(new Dimension(500, 600));
		
		
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl.rowHeights = new int[] {0, 0, 0};
		gbl.columnWidths = new int[] {1, 0, 1};
		viewPanel.setLayout(gbl);
				
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(viewPanel, constraints);
		
		//Adds the scroll pane the filters will be on
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scrollPane.setSize(new Dimension(50, 600));
		scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
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
		
		//Adds the label on top of the scroll pane
		final JLabel filterList = new JLabel("List of Filters", SwingConstants.CENTER);
		filterList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//		filterList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
		
		selectedFilters = new ArrayList<FilterPanel>();
		
		addButtonPanel();
	}
	
	
	
	/**
	 * Method FilterList.
	 */
	public void addFilterList(){
		
		viewPanel = new JPanel();
		viewPanel.setBackground(Color.WHITE);
		viewPanel.setPreferredSize(new Dimension(500, 600));
		viewPanel.setMaximumSize(new Dimension(500, 600));
		
		
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl.rowHeights = new int[] {0, 0, 0};
		gbl.columnWidths = new int[] {1, 0, 1};
		viewPanel.setLayout(gbl);
				
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(viewPanel, constraints);
		
		//Adds the scroll pane the filters will be on
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//		scrollPane.setSize(new Dimension(50, 600));
		scrollPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
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
		
		//Adds the label on top of the scroll pane
		final JLabel filterList = new JLabel("List of Filters", SwingConstants.CENTER);
		filterList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//		filterList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
		
		addButtonPanel();
	}
	
	/**
	 * Method editingMode.
	 */
	public void editFilterMode(){
		
		editPanel = new JPanel();
		editPanel.setBackground(Color.WHITE);
		editPanel.setPreferredSize(new Dimension(500, 600));
		editPanel.setMinimumSize(new Dimension(500, 600));
		
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0};
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0};
		gbl.columnWidths = new int[] {0, 0, 0, 0};
		editPanel.setLayout(gbl);
		
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(editPanel, constraints);
		
		final JLabel filterNamelbl = new JLabel("<html><font>" + "Filter Name" + "</font>" 
												+ "<font color=red>" + "*" + "</font>" 
												+ "<font>" + ":" + "</font></html>");
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
		
		//Adds the text field for the name of the filter
		filterName = new JTextField();
		filterName.setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_filterName = new GridBagConstraints();
		gbc_filterName.fill = GridBagConstraints.BOTH;
		gbc_filterName.insets = new Insets(5, 0, 5, 15);
		gbc_filterName.gridwidth = 3;
		gbc_filterName.gridx = 1;
		gbc_filterName.gridy = 0;
		if (aSelectedFilter != null){
			filterName.setText(aSelectedFilter.getName());
		}
		editPanel.add(filterName, gbc_filterName);
		
		//adds the scroll pane containing the categories not in the filter
		inactiveCatPane = new JScrollPane();
		inactiveCatPane.setPreferredSize(new Dimension(2, 200));
		inactiveCatPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		inactiveCatPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		inactiveCatPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		inactiveCatPane.getViewport().setBackground(Color.WHITE);
		final GridBagConstraints gbc_inactiveFilter = new GridBagConstraints();
		gbc_inactiveFilter.fill = GridBagConstraints.BOTH;
		gbc_inactiveFilter.insets = new Insets(5, 0, 5, 15);
		gbc_inactiveFilter.gridwidth = 3;
		gbc_inactiveFilter.gridx = 1;
		gbc_inactiveFilter.gridy = 3;
		editPanel.add(inactiveCatPane, gbc_inactiveFilter);
		
		//adds the scroll pane containing the categories in the filter
		activeCatPane = new JScrollPane();
		activeCatPane.setPreferredSize(new Dimension(2, 200));
		activeCatPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		activeCatPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		activeCatPane.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
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
		
		//add the two buttons to move categories between active and inactive panes
		catBtnPanel = new JPanel(new BorderLayout(20, 0));
		catBtnPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_catBtnPanel = new GridBagConstraints();
		gbc_catBtnPanel.anchor = GridBagConstraints.CENTER;
		gbc_catBtnPanel.insets = new Insets(5, 0, 5, 0);
		gbc_catBtnPanel.gridx = 2;
		gbc_catBtnPanel.gridy = 2;
		
		//Add Category to Filter button
		addCatBtn = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("GreenArrowUp_Icon.png"));
			addCatBtn.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			addCatBtn.setIcon(new ImageIcon());
		}
//		addCatBtn.setText("Add Category");
		addCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		addCatBtn.setToolTipText("Add Category to Filter");
		addCatBtn.setEnabled(false);
		
		
		//Remove Category from Filter button
		removeCatBtn = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("RedArrowDown_Icon.png"));
			removeCatBtn.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			removeCatBtn.setIcon(new ImageIcon());
		}
//		removeCatBtn.setText("Remove Category");
		removeCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		removeCatBtn.setToolTipText("Remove Category from Filter");
		removeCatBtn.setEnabled(false);
		
		catBtnPanel.add(addCatBtn, BorderLayout.WEST);
		catBtnPanel.add(removeCatBtn, BorderLayout.EAST);
		editPanel.add(catBtnPanel, gbc_catBtnPanel);
		
		addButtonPanel2();
		selectedCategories = new ArrayList<CategoryPanel>();
	}

	/**
	 * Adds the button panel to Filter tab for viewing mode.
	 * Delete and edit button are disabled by default.
	 * 
	 */	
	private void addButtonPanel(){
		
		buttonPanel = new JPanel(new BorderLayout(25, 0));
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 2;
		gbc_btnPanel.insets = new Insets(0, 15, 0, 15);
		
		//New Filter button
		btnNewFilter = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("New_Icon.png"));
			btnNewFilter.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnNewFilter.setIcon(new ImageIcon());
		}
		btnNewFilter.setText("New Filter");
		btnNewFilter.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		
		//Add Edit button
		btnEdit = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Edit_Icon.png"));
			btnEdit.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnEdit.setIcon(new ImageIcon());
		}
		btnEdit.setText("Edit Filter");
		btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button		
		
		// Add Delete Button
		btnDelete = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			btnDelete.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setIcon(new ImageIcon());
		}
		btnDelete.setText("Delete Filter");
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		
		if(mode == FilterMode.VIEWING){
			buttonPanel.add(btnNewFilter, BorderLayout.WEST);
			buttonPanel.add(btnEdit, BorderLayout.CENTER);
			buttonPanel.add(btnDelete, BorderLayout.LINE_END);
		}
		else{
			buttonPanel.add(btnEdit, BorderLayout.LINE_START);
			buttonPanel.add(btnDelete, BorderLayout.LINE_END);
		}
		
		// Set the horizontal gap
		viewPanel.add(buttonPanel, gbc_btnPanel);
	}
	
	/**
	 * Method addButtonPanel2.
	 */
	public void addButtonPanel2(){
		buttonPanel2 = new JPanel(new BorderLayout(25, 0));
		buttonPanel2.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel2 = new GridBagConstraints();
		gbc_btnPanel2.insets = new Insets(0, 0, 0, 15);
		gbc_btnPanel2.gridwidth = 3;
		gbc_btnPanel2.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel2.gridx = 1;
		gbc_btnPanel2.gridy = 4;
		
		//New Save button
		btnSaveFilter = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Save_Icon.png"));
			btnSaveFilter.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnSaveFilter.setIcon(new ImageIcon());
		}
		btnSaveFilter.setText("Save Filter");
		btnSaveFilter.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button		
		
		//New Cancel button
		btnCancelFilter = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("Cancel_Icon.png"));
			btnCancelFilter.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancelFilter.setIcon(new ImageIcon());
		}
		btnCancelFilter.setText("Cancel");
		btnCancelFilter.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		
		
		buttonPanel2.add(btnSaveFilter, BorderLayout.WEST);
		buttonPanel2.add(btnCancelFilter, BorderLayout.EAST);
		// Set the horizontal gap
		editPanel.add(buttonPanel2, gbc_btnPanel2);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Method addListeners.
	 */
	public void addListeners(){
		btnNewFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = FilterMode.ADDING;
				aSelectedFilter = null;
				refresh();
				activeTeamCat = new CategoryList();
				activePersonalCat = new CategoryList();
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//if(mode != FilterMode.EDITING){
					mode = FilterMode.EDITING;
					refresh();
					filterName.setText(aSelectedFilter.getName());
					editFilter = aSelectedFilter;
					oldActiveTeamCat = aSelectedFilter.getActiveTeamCategories();
					oldActivePersonalCat = aSelectedFilter.getActivePersonalCategories();
			/*	}
				else{
					refresh();
				}
//				if (aSelectedFilter != null){
//					filterName.setText(aSelectedFilter.getName());
//				}*/
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//deleteFilter();
			}
		});
	}
	
	/**
	 * Method addEditViewListeners.
	 */
	public void addEditViewListeners(){
		filterName.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				saveBtnStatus();
			}
			
			//unused
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub				
			}
		});
		
		removeCatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeCategoryFromFilter();
				refresh();
			}
		});
		
		addCatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCategoryToFilter();
				refresh();
			}
		});
		
		btnCancelFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = FilterMode.VIEWING;
				refresh();
			}
		});
		
		btnSaveFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateList();
				addFilter();
				mode = FilterMode.VIEWING;
				refresh();
			}
		});
	}
	
	/**
	 * Method refresh.
	 */
	protected void refresh(){
		if(mode == FilterMode.VIEWING){
			removeAll();
			mainFilterListView();
			addListeners();
			populateFilterList();
			viewPaneBtnStatus(false);
			revalidate();
			repaint();
		}
		else if(mode == FilterMode.ADDING) {
			removeAll();
			addFilterList();
			addListeners();
			editFilterMode();
			addEditViewListeners();
			populateFilterList();
			populateActiveCatLists();
			populateInactiveCatLists();
			viewPaneBtnStatus(false);
			saveBtnStatus();
			revalidate();
			repaint();
		}
		else{
			removeAll();
			addFilterList();
			addListeners();
			editFilterMode();
			addEditViewListeners();
			populateFilterList();
			populateActiveCatLists();
			populateInactiveCatLists();
			viewPaneBtnStatus(false);
			saveBtnStatus();
			revalidate();
			repaint();
		}
	}
	
	/**
	 * Adds a new filter with the information contained in the fields
	 */
	private void addFilter(){
		final CalendarData calData;
		
		final String name = filterName.getText();

		calData = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + 
				"-" + ConfigManager.getConfig().getUserName()); 
		
		final Filter newFilter = new Filter(name, newTeamCatList, newPersonalCatList);
		if(mode == FilterMode.ADDING){
			calData.addFilter(newFilter);
		}
		else{
			editFilter.setName(name);
			editFilter.setActiveTeamCategories(newTeamCatList);
			editFilter.setActivePersonalCategories(newPersonalCatList);
			calData.getFilters().update(editFilter);
		}
		UpdateCalendarDataController.getInstance().updateCalendarData(calData);
	}
	
	private void populateFilterList(){
			
		final List<Filter> filterList = new ArrayList<Filter>();
		filterList.addAll(calendarFilters.getFilters());
		
		// FilterPanel to keep track of spring layout constraints of previously added panel
		FilterPanel oldFilterPanel = new FilterPanel(); 
		FilterPanel filterPanel = new FilterPanel();
		for(int i = 0; i < filterList.size(); i++)
		{
			filterPanel = new FilterPanel(filterList.get(i));
			//If first panel, add to top of list panel
			if (i == 0)
			{
				filterListLayout.putConstraint(SpringLayout.NORTH, filterPanel, 
						1, SpringLayout.NORTH, filterListPanel);
				filterListLayout.putConstraint(SpringLayout.WEST, filterPanel, 
						1, SpringLayout.WEST, filterListPanel);
				filterListLayout.putConstraint(SpringLayout.EAST, filterPanel,
						1, SpringLayout.EAST, filterListPanel);
			}
			else
			{
				//add panel below previous panel
				filterListLayout.putConstraint(SpringLayout.NORTH, filterPanel, 
						1, SpringLayout.SOUTH, oldFilterPanel);
				filterListLayout.putConstraint(SpringLayout.WEST, filterPanel, 
						1, SpringLayout.WEST, filterListPanel);
				filterListLayout.putConstraint(SpringLayout.EAST, filterPanel, 
						1, SpringLayout.EAST, filterListPanel);
			}
			filterListPanel.add(filterPanel);		
		
			filterPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1 /*&& mode != FilterMode.EDITING*/){
						mode = FilterMode.EDITING;
						refresh();
						aSelectedFilter = ((FilterPanel)e.getComponent()).getFilter();
						editFilter = aSelectedFilter;
						oldActiveTeamCat = aSelectedFilter.getActiveTeamCategories();
						oldActivePersonalCat = aSelectedFilter.getActivePersonalCategories();
						//setEditFields();
						filterName.setText(aSelectedFilter.getName());
					}
				/*	else if(e.getClickCount() > 1){
						refresh();
						aSelectedFilter = ((FilterPanel)e.getComponent()).getFilter();
						editFilter = aSelectedFilter;
						oldActiveTeamCat = aSelectedFilter.getActiveTeamCategories();
						oldActivePersonalCat = aSelectedFilter.getActivePersonalCategories();
						//setEditFields();
						filterName.setText(aSelectedFilter.getName());
					}*/
					/*else if (e.getClickCount() == 1 && mode == FilterMode.VIEWING){
						FilterPanel comp = (FilterPanel) e.getComponent();
						if(selectedFilters.isEmpty() || e.isControlDown());
						else
						{
							removeSelectedFilters(); //clear existing selections
						}
						selectedFilters.add(comp);
						comp.setSelected(true);
						btnEdit.setEnabled(true);
						btnDelete.setEnabled(true);
						aSelectedFilter = ((FilterPanel)e.getComponent()).getFilter();
//						filterName.setText(aSelectedFilter.getName());
					}*/
					else if(e.getClickCount() == 1){
						FilterPanel comp = (FilterPanel) e.getComponent();
						if(selectedFilters.isEmpty() || e.isControlDown());
						else
						{
							removeSelectedFilters(); //clear existing selections
						}
						selectedFilters.add(comp);
						comp.setSelected(true);
						btnEdit.setEnabled(true);
						btnDelete.setEnabled(true);
						aSelectedFilter = ((FilterPanel)e.getComponent()).getFilter();
//						filterName.setText(aSelectedFilter.getName());
					}
				}
			});
		
			oldFilterPanel = filterPanel;
		}

			filterListLayout.putConstraint(SpringLayout.SOUTH,
			filterListPanel, 0, SpringLayout.SOUTH, filterPanel);
	}
	
	
	protected void removeSelectedFilters() {
		for (FilterPanel fPanel: selectedFilters)
		{
			fPanel.setSelected(false);
		}
		selectedFilters.clear();
	}
	
	
	protected void removeSelectedCategories() {
		for (CategoryPanel cPanel: selectedCategories)
		{
			cPanel.setSelected(false);
		}
		selectedCategories.clear();
	}
	
	private void addCategoryToFilter(){
		if(aSelectedCategory.getIsPersonal()){
			inactivePersonalCat.remove(aSelectedCategory.getID());
			activePersonalCat.add(aSelectedCategory);
		}
		else{
			inactiveTeamCat.remove(aSelectedCategory.getID());
			activeTeamCat.add(aSelectedCategory);
		}
	}
	
	private void removeCategoryFromFilter(){
		if(aSelectedCategory.getIsPersonal()){
			inactivePersonalCat.add(aSelectedCategory);
			activePersonalCat.remove(aSelectedCategory.getID());
		}
		else{
			inactiveTeamCat.add(aSelectedCategory);
			activeTeamCat.remove(aSelectedCategory.getID());
		}	
	}	

	private void populateInactiveCatLists(){	
		
		/*if(mode == FilterMode.EDITING){
			for(int i = 0; i < oldActiveTeamCat.size(); i++){
				for(int j = 0; j < teamCategories.getSize(); j++)
					if(oldActiveTeamCat.get(i) == teamCategories.getCategory(j).getID()){
						inactiveTeamCat.remove(teamCategories.getCategory(j).getID());
						break;
					}
			}
		
			for(int i = 0; i < oldActivePersonalCat.size(); i++){
				for(int j = 0; j < personalCategories.getSize(); j++)
					if(oldActivePersonalCat.get(i) == personalCategories.getCategory(j).getID()){
						inactivePersonalCat.remove(personalCategories.getCategory(j).getID());
						break;
					}
			}
		}*/
		
		final List<Category> catList = new ArrayList<Category>();
		final CategoryList bothCategories = new CategoryList();
		final Category[] bothCatArray = 
				new Category[inactiveTeamCat.getSize() + inactivePersonalCat.getSize()];
		catList.addAll(inactiveTeamCat.getCategories());
		catList.addAll(inactivePersonalCat.getCategories());
		for(int i = 0; i < catList.size(); i++)
		{
			bothCatArray[i] = catList.get(i);
		}
		bothCategories.addCategories(bothCatArray);
		bothCategories.sortByAlphabet();
		catList.clear();
		catList.addAll(bothCategories.getCategories());
		
		
		// CategoryPanel to keep track of spring layout constraints of previously added panel
		CategoryPanel oldCatPanel = new CategoryPanel(); 
		CategoryPanel catPanel = new CategoryPanel();
		for(int i = 0; i < catList.size(); i++)
		{
			catPanel = new CategoryPanel(catList.get(i));
			//If first panel, add to top of list panel
			if (i == 0)
			{
				inactiveListLayout.putConstraint(SpringLayout.NORTH, catPanel, 
						1, SpringLayout.NORTH, inactiveListPanel);
				inactiveListLayout.putConstraint(SpringLayout.WEST, catPanel, 
						1, SpringLayout.WEST, inactiveListPanel);
				inactiveListLayout.putConstraint(SpringLayout.EAST, catPanel,
						1, SpringLayout.EAST, inactiveListPanel);
			}
			else
			{
				//add panel below previous panel
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
					if (e.getClickCount() > 1){
						aSelectedCategory = ((CategoryPanel)e.getComponent()).getCategory();
						addCategoryToFilter();
						refresh();
					}
					else if (e.getClickCount() == 1){
						CategoryPanel comp = (CategoryPanel) e.getComponent();
						if(selectedCategories.isEmpty() || e.isControlDown());
						else
						{
							removeSelectedCategories(); //clear existing selections
						}
						selectedCategories.add(comp);
						comp.setSelected(true);
						addCatBtn.setEnabled(true);
						removeCatBtn.setEnabled(false);
						aSelectedCategory = ((CategoryPanel)e.getComponent()).getCategory();
					}
				}
			});
			
			
			oldCatPanel = catPanel; //update oldCatPanel to be previously added panel
		}
		
		inactiveListLayout.putConstraint(SpringLayout.SOUTH,
		inactiveListPanel, 0, SpringLayout.SOUTH, catPanel);
	}
	
	private void populateActiveCatLists(){
	
		/*if(mode == FilterMode.EDITING){
			for(int i = 0; i < oldActiveTeamCat.size(); i++){
				for(int j = 0; j < teamCategories.getSize(); j++)
					if(oldActiveTeamCat.get(i) == teamCategories.getCategory(j).getID()){
						activeTeamCat.add(teamCategories.getCategory(j));
						break;
					}
			}
		
			for(int i = 0; i < oldActivePersonalCat.size(); i++){
				for(int j = 0; j < personalCategories.getSize(); j++)
					if(oldActivePersonalCat.get(i) == personalCategories.getCategory(j).getID()){
						activePersonalCat.add(personalCategories.getCategory(j));
						break;
					}
			}
		}*/
		
		final List<Category> catList = new ArrayList<Category>();
		final CategoryList bothCategories = new CategoryList();
		final Category[] bothCatArray = 
				new Category[activeTeamCat.getSize() + activePersonalCat.getSize()];
		catList.addAll(activeTeamCat.getCategories());
		catList.addAll(activePersonalCat.getCategories());
		for(int i = 0; i < catList.size(); i++)
		{
			bothCatArray[i] = catList.get(i);
		}
		bothCategories.addCategories(bothCatArray);
		bothCategories.sortByAlphabet();
		catList.clear();
		catList.addAll(bothCategories.getCategories());
		CategoryPanel oldCatPanel = new CategoryPanel(); 
		CategoryPanel catPanel = new CategoryPanel();
		for(int i = 0; i < catList.size(); i++)
		{
			catPanel = new CategoryPanel(catList.get(i));
			//If first panel, add to top of list panel
			if (i == 0)
			{
				activeListLayout.putConstraint(SpringLayout.NORTH, catPanel, 
						1, SpringLayout.NORTH, activeListPanel);
				activeListLayout.putConstraint(SpringLayout.WEST, catPanel, 
						1, SpringLayout.WEST, activeListPanel);
				activeListLayout.putConstraint(SpringLayout.EAST, catPanel,
						1, SpringLayout.EAST, activeListPanel);
			}
			else
			{
				//add panel below previous panel
				activeListLayout.putConstraint(SpringLayout.NORTH, catPanel, 
						1, SpringLayout.SOUTH, oldCatPanel);
				activeListLayout.putConstraint(SpringLayout.WEST, catPanel, 
						1, SpringLayout.WEST, activeListPanel);
				activeListLayout.putConstraint(SpringLayout.EAST, catPanel, 
						1, SpringLayout.EAST, activeListPanel);
			}

			activeListPanel.add(catPanel);
			
			catPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1){
						aSelectedCategory = ((CategoryPanel)e.getComponent()).getCategory();
						removeCategoryFromFilter();
						refresh();
					}
					else if (e.getClickCount() == 1){
						CategoryPanel comp = (CategoryPanel) e.getComponent();
						if(selectedCategories.isEmpty() || e.isControlDown());
						else
						{
							removeSelectedCategories(); //clear existing selections
						}
						selectedCategories.add(comp);
						comp.setSelected(true);
						removeCatBtn.setEnabled(true);
						addCatBtn.setEnabled(false);
						aSelectedCategory = ((CategoryPanel)e.getComponent()).getCategory();
					}
				}
			});
			
			oldCatPanel = catPanel; //update oldCatPanel to be previously added panel
		}
	}
	
	private void viewPaneBtnStatus(boolean b){
		if(b){
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
		}
		else{
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		}
	}
	
	private void saveBtnStatus(){
		if(filterName.getText().equals("") || filterName.getText().trim().length() == 0){
			btnSaveFilter.setEnabled(false);
		}
		else{
			btnSaveFilter.setEnabled(true);
		}
	}
	
	private void updateList(){
		for(int i = 0; i < activeTeamCat.getSize(); i++){
			newTeamCatList.add(activeTeamCat.getCategory(i).getID());
		}
		for(int i = 0; i < activePersonalCat.getSize(); i++){
			newPersonalCatList.add(activePersonalCat.getCategory(i).getID());
		}
	}
	
	/*private void setEditFields(){
		if(mode == FilterMode.ADDING){
			filterName.setText("**New Filter**");
			activeTeamCat = new CategoryList();
			activePersonalCat = new CategoryList();
		}
		else{
			filterName.setText(editFilter.getName());
			activeTeamCat = new CategoryList();
			activePersonalCat = new CategoryList();
			
			for(int i = 0; i < oldActiveTeamCat.size(); i++){
				for(int j = 0; j < teamCategories.getSize(); j++)
					if(oldActiveTeamCat.get(i) == teamCategories.getCategory(j).getID()){
						activeTeamCat.add(teamCategories.getCategory(j));
						break;
					}
			}
		
			for(int i = 0; i < oldActivePersonalCat.size(); i++){
				for(int j = 0; j < personalCategories.getSize(); j++)
					if(oldActivePersonalCat.get(i) == personalCategories.getCategory(j).getID()){
						activePersonalCat.add(personalCategories.getCategory(j));
						break;
					}
			}
		}
	}*/
}
