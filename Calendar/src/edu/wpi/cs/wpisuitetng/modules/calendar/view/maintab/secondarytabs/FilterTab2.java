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
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CategoryList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.FilterList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;

public class FilterTab2 extends JPanel {

	private enum FilterMode {
		ADDING(0),
		EDITING(1),
		VIEWING(2);
		private final int currentMode;
		
		private FilterMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}

	private FilterMode mode = FilterMode.VIEWING;
	private JPanel viewPanel;
	protected Filter selectedFilter;
	private JPanel editPanel;
	private CategoryList teamCategories;
	private CategoryList personalCategories;
	private FilterList calendarFilters;
	private SpringLayout filterListLayout;
	private JPanel filterListPanel;
	
	public FilterTab2(int openedFrom) {
		//TODO
		
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		setLayout(gridBagLayout);
		
		refreshCalData();
		
		refreshMainView();
	}
	
	public void refreshCalData() {
		//Load category lists and filter lists from CalendarDataModel
		teamCategories = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName()).getCategories(); 
		personalCategories = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + 
				"-" + ConfigManager.getConfig().getUserName()).getCategories(); 
		calendarFilters = CalendarDataModel.getInstance().getCalendarData(
				ConfigManager.getConfig().getProjectName() + 
				"-" + ConfigManager.getConfig().getUserName()).getFilters();
	}
	
	public void refreshEditView() { refreshEditView(null); }
	public void refreshEditView(Filter editFilter) {
		// TODO
		
		if (editFilter == null) {
			// ADDING
		} else { 
			// EDITING
		}
		revalidate();
		repaint();
	}
	
	public void refreshMainView() {
		// TODO
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
	 * Method FilterList.
	 */
	public void mainFilterListView(){
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl.rowHeights = new int[] {0, 0, 0};
		gbl.columnWidths = new int[] {1, 0, 1};
		setupFilterList(gbl);
	}
	
	public void addFilterListView() {
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0};
		gbl.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl.rowHeights = new int[] {0, 0, 0};
		gbl.columnWidths = new int[] {1, 0, 1};
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
		
		//Adds the scroll pane the filters will be on
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
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
	 * Adds the button panel to Filter tab for viewing mode.
	 * Delete and edit button are disabled by default.
	 * 
	 */	
	private void addButtonPanel(){
		
		JPanel buttonPanel = new JPanel(new BorderLayout(25, 0));
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 2;
		gbc_btnPanel.insets = new Insets(0, 15, 0, 15);
		
		//New Filter button
		JButton btnNewFilter = new JButton();
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
		btnNewFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = FilterMode.ADDING;
				selectedFilter = null;
				refreshMainView();
				refreshEditView();
			}
		});
		
		//Add Edit button
		JButton btnEdit = new JButton();
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
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});		
		
		// Add Delete Button
		JButton btnDelete = new JButton();
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
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
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
	 * Method editingMode.
	 */
	public void addEditFilterView(){
		
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
		JTextField filterName = new JTextField();
		filterName.setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_filterName = new GridBagConstraints();
		gbc_filterName.fill = GridBagConstraints.BOTH;
		gbc_filterName.insets = new Insets(5, 0, 5, 15);
		gbc_filterName.gridwidth = 3;
		gbc_filterName.gridx = 1;
		gbc_filterName.gridy = 0;
		if (selectedFilter != null){
			filterName.setText(selectedFilter.getName());
		}
		editPanel.add(filterName, gbc_filterName);
		filterName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		//adds the scroll pane containing the categories not in the filter
		JScrollPane inactiveCatPane = new JScrollPane();
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
		JScrollPane activeCatPane = new JScrollPane();
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
		
		JPanel inactiveListPanel = new JPanel();
		inactiveCatPane.setViewportView(inactiveListPanel);
		inactiveListPanel.setBackground(Color.WHITE);
		SpringLayout inactiveListLayout = new SpringLayout();
		inactiveListPanel.setLayout(inactiveListLayout);
		
		JPanel activeListPanel = new JPanel();
		activeCatPane.setViewportView(activeListPanel);
		activeListPanel.setBackground(Color.WHITE);
		SpringLayout activeListLayout = new SpringLayout();
		activeListPanel.setLayout(activeListLayout);
		
		//add the two buttons to move categories between active and inactive panes
		JPanel catBtnPanel = new JPanel(new BorderLayout(20, 0));
		catBtnPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_catBtnPanel = new GridBagConstraints();
		gbc_catBtnPanel.anchor = GridBagConstraints.CENTER;
		gbc_catBtnPanel.insets = new Insets(5, 0, 5, 0);
		gbc_catBtnPanel.gridx = 2;
		gbc_catBtnPanel.gridy = 2;
		
		//Add Category to Filter button
		JButton addCatBtn = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("GreenArrowUp_Icon.png"));
			addCatBtn.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			addCatBtn.setIcon(new ImageIcon());
		}
		addCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		addCatBtn.setToolTipText("Add Category to Filter");
		addCatBtn.setEnabled(false);
		addCatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		
		//Remove Category from Filter button
		JButton removeCatBtn = new JButton();
		try {
			final Image img = ImageIO.read(getClass().getResource("RedArrowDown_Icon.png"));
			removeCatBtn.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			removeCatBtn.setIcon(new ImageIcon());
		}
		removeCatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		removeCatBtn.setToolTipText("Remove Category from Filter");
		removeCatBtn.setEnabled(false);
		removeCatBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
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
	public void addEditFilterButtonPanel(){
		JPanel filterButtonPanel = new JPanel(new BorderLayout(25, 0));
		filterButtonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel2 = new GridBagConstraints();
		gbc_btnPanel2.insets = new Insets(0, 0, 0, 15);
		gbc_btnPanel2.gridwidth = 3;
		gbc_btnPanel2.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel2.gridx = 1;
		gbc_btnPanel2.gridy = 4;
		
		//New Save button
		JButton btnSaveFilter = new JButton();
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
		btnSaveFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		//New Cancel button
		JButton btnCancelFilter = new JButton();
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
		btnCancelFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		
		
		filterButtonPanel.add(btnSaveFilter, BorderLayout.WEST);
		filterButtonPanel.add(btnCancelFilter, BorderLayout.EAST);
		// Set the horizontal gap
		editPanel.add(filterButtonPanel, gbc_btnPanel2);
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
						//TODO
				}
			});
		
			oldFilterPanel = filterPanel;
		}

			filterListLayout.putConstraint(SpringLayout.SOUTH,
			filterListPanel, 0, SpringLayout.SOUTH, filterPanel);
	}
	
}
