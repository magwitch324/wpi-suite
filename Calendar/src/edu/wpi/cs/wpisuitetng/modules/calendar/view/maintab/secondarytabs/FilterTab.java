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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;

 /* @author CS Anonymous
  * @version $Revision: 1.0 $
  */
/**
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
 public class FilterTab extends JPanel{

	private final int openedFrom;
	private JPanel buttonPanel;
	private JButton btnAddFilter;
	private AbstractButton btnCancel;
	private final Container viewPanel;
	private JButton btnDelete;
	private Component btnEdit;
	private Component btnNewFilter;
	private boolean initFlag;
	private JScrollPane scrollPane;
	private Component categoryList;
	private JScrollPane inactiveFilterPane;
	private JScrollPane activeFilterPane;
	private JPanel buttonPanel2;
	private JButton btnSaveFilter;
	private JButton btnCancelFilter;
	private JPanel editPanel;
	private JButton addCategoryBttn;
	private JButton moveCategoryBttn;
	private JPanel moveCatBttnPanel;
	private JButton addCatBttn;
	private JButton removeCatBttn;
	private JPanel catBttnPanel;

	/**
	 * @author Tianci
	 */
	private enum EditingMode {
		VIEWING(0),
		EDITING(1);
		private final int currentMode;
		
		private EditingMode(int currentMode) {
			this.currentMode = currentMode;
		}
	}
	/**
	 * Create the panel.
	 * @param openedFrom int
	 */
	public FilterTab(int openedFrom) {
		this.openedFrom = openedFrom;
		initFlag = false;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0};
		setLayout(gridBagLayout);

		//final JPanel aPanel = new JPanel();
		viewPanel = new JPanel();
		viewPanel.setBackground(Color.WHITE);
		viewPanel.setPreferredSize(new Dimension(500, 600));
		viewPanel.setMinimumSize(new Dimension(500, 600));	
		
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0};
		gbl.columnWeights = new double[]{0.0};
		gbl.rowHeights = new int[] {0, 0, 0};
		gbl.columnWidths = new int[] {0};
		viewPanel.setLayout(gbl);
		
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 0;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(viewPanel, constraints);		
		
		/*addLabels();
		addEditableElements();
		setDefaultValuesForEditableElements();
		addEditableElementsListeners();*/
		addFilterList();
		editingFilterMode();
		initFlag = true;
		}
	
	/**
	 * Method FilterList.
	 */
	public void addFilterList(){
		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setBackground(Color.WHITE);
		final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		viewPanel.add(scrollPane, gbc_scrollPane);
		
		final JLabel filterList = new JLabel("List of Filters", SwingConstants.CENTER);
		filterList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//		filterList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		filterList.setForeground(Color.WHITE);
		filterList.setBackground(CalendarStandard.CalendarRed);
		filterList.setOpaque(true);
		final GridBagConstraints gbc_filterList = new GridBagConstraints();
		gbc_filterList.fill = GridBagConstraints.BOTH;
		gbc_filterList.insets = new Insets(5, 0, 0, 0);
		gbc_filterList.gridx = 0;
		gbc_filterList.gridy = 0;
		viewPanel.add(filterList, gbc_filterList);
		
		addButtonPanel();
	}
	
	/**
	 * Method editingMode.
	 */
	public void editingFilterMode(){
		
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
		gbc_filterNamelbl.insets = new Insets(0, 0, 0, 5);
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
		gbc_inactiveFilterlbl.insets = new Insets(0, 0, 0, 5);
		gbc_inactiveFilterlbl.fill = GridBagConstraints.BOTH;
		gbc_inactiveFilterlbl.gridx = 0;
		gbc_inactiveFilterlbl.gridy = 1;
		editPanel.add(inactiveFilterlbl, gbc_inactiveFilterlbl);
		
		final JLabel activeFilterlbl = new JLabel();
		activeFilterlbl.setText("Catagories in Filter:");
		activeFilterlbl.setBackground(Color.WHITE);
		activeFilterlbl.setOpaque(true);
		activeFilterlbl.setHorizontalAlignment(SwingConstants.RIGHT);
		final GridBagConstraints gbc_activeFilterlbl = new GridBagConstraints();
		gbc_activeFilterlbl.insets = new Insets(0, 0, 0, 5);
		gbc_activeFilterlbl.fill = GridBagConstraints.BOTH;
		gbc_activeFilterlbl.gridx = 0;
		gbc_activeFilterlbl.gridy = 3;
		editPanel.add(activeFilterlbl, gbc_activeFilterlbl);
		
		final JTextField filterName = new JTextField();
		filterName.setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_filterName = new GridBagConstraints();
		gbc_filterName.fill = GridBagConstraints.BOTH;
		gbc_filterName.insets = new Insets(5, 0, 5, 15);
		gbc_filterName.gridwidth = 3;
		gbc_filterName.gridx = 1;
		gbc_filterName.gridy = 0;
		editPanel.add(filterName, gbc_filterName);
		
		inactiveFilterPane = new JScrollPane();
		inactiveFilterPane.getViewport().setBackground(Color.WHITE);
		final GridBagConstraints gbc_inactiveFilter = new GridBagConstraints();
		gbc_inactiveFilter.fill = GridBagConstraints.BOTH;
		gbc_inactiveFilter.insets = new Insets(5, 0, 5, 15);
		gbc_inactiveFilter.gridwidth = 3;
		gbc_inactiveFilter.gridx = 1;
		gbc_inactiveFilter.gridy = 1;
		editPanel.add(inactiveFilterPane, gbc_inactiveFilter);
		
		activeFilterPane = new JScrollPane();
		activeFilterPane.getViewport().setBackground(Color.WHITE);
		final GridBagConstraints gbc_activeFilter = new GridBagConstraints();
		gbc_activeFilter.fill = GridBagConstraints.BOTH;
		gbc_activeFilter.insets = new Insets(5, 0, 5, 15);
		gbc_activeFilter.gridwidth = 3;
		gbc_activeFilter.gridx = 1;
		gbc_activeFilter.gridy = 3;
		editPanel.add(activeFilterPane, gbc_activeFilter);
		
		catBttnPanel = new JPanel(new BorderLayout(20, 0));
		catBttnPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_catBttnPanel = new GridBagConstraints();
		gbc_catBttnPanel.anchor = GridBagConstraints.CENTER;
		gbc_catBttnPanel.insets = new Insets(5, 0, 5, 0);
		gbc_catBttnPanel.gridx = 2;
		gbc_catBttnPanel.gridy = 2;
		
		addCatBttn = new JButton();
		addCatBttn.setText("Add Category");
		
		removeCatBttn = new JButton();
		removeCatBttn.setText("Remove Category");
		
		catBttnPanel.add(addCatBttn, BorderLayout.WEST);
		catBttnPanel.add(removeCatBttn, BorderLayout.EAST);
		editPanel.add(catBttnPanel, gbc_catBttnPanel);
		
		addButtonPanel2();
	}

private void addButtonPanel(){
		
		buttonPanel = new JPanel(new BorderLayout(30, 0));
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 0;
		gbc_btnPanel.gridy = 2;
		
		//New Filter button
		try {
			final Image img = ImageIO.read(getClass().getResource("New_Icon.png"));
			btnNewFilter = new JButton("New Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("New Filter");
		}

		//Add Edit button
		try {
			final Image img = ImageIO.read(getClass().getResource("Edit_Icon.png"));
			btnEdit = new JButton("Edit Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
		btnCancel.setText("Edit Filter");
		}
		
		// Add Delete Button
		try {
			final Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			btnDelete = new JButton("Delete Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setText("Delete Filter");
		}
			
		buttonPanel.add(btnNewFilter, BorderLayout.WEST);
		buttonPanel.add(btnEdit, BorderLayout.CENTER);
		buttonPanel.add(btnDelete, BorderLayout.LINE_END);
		// Set the horizontal gap
		viewPanel.add(buttonPanel, gbc_btnPanel);
	}	
	
	public void addButtonPanel2(){
		buttonPanel2 = new JPanel(new BorderLayout(30, 0));
		buttonPanel2.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel2 = new GridBagConstraints();
		gbc_btnPanel2.gridwidth = 3;
		gbc_btnPanel2.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel2.gridx = 1;
		gbc_btnPanel2.gridy = 4;
		
		//New Save button
		try {
			final Image img = ImageIO.read(getClass().getResource("Save_Icon.png"));
			btnSaveFilter = new JButton("Save Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Save Filter");
		}
		
		//New Cancel button
		try {
			final Image img = ImageIO.read(getClass().getResource("Cancel_Icon.png"));
			btnCancelFilter = new JButton("Cancel", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Cancel");
		}
		
		buttonPanel2.add(btnSaveFilter, BorderLayout.WEST);
		buttonPanel2.add(btnCancelFilter, BorderLayout.EAST);
		// Set the horizontal gap
		editPanel.add(buttonPanel2, gbc_btnPanel2);
	}
}
