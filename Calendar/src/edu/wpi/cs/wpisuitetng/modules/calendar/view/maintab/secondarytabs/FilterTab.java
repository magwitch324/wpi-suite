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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;

 /* @author CS Anonymous
  * @version $Revision: 1.0 $
  */
public class FilterTab extends JPanel{

	private final int openedFrom;
	private JPanel buttonPanel;
	private JButton btnAddFilter;
	private AbstractButton btnCancel;
	private final Container formPanel;
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

	/**
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
		
		final GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		final JPanel aPanel = new JPanel();
		formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setPreferredSize(new Dimension(500, 600));
		formPanel.setMinimumSize(new Dimension(500, 600));	
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		add(formPanel, constraints);
		
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0};
		gbl.columnWeights = new double[]{0.5, 3.0, 0.5, 7.0, 0.5};
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWidths = new int[] {0, 0, 0, 0,0};
		formPanel.setLayout(gbl);
		
		
		/*addLabels();
		addEditableElements();
		setDefaultValuesForEditableElements();
		addEditableElementsListeners();*/
		addButtonPanel();
		//addButtonPanel2();
		addFilterList();
		editingMode();
		initFlag = true;
		}
	
	private void addButtonPanel(){
		
		buttonPanel = new JPanel(new BorderLayout(30, 0));
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.gridwidth = 2;
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 9;
		
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
		formPanel.add(buttonPanel, gbc_btnPanel);
	}	
	
	public void addButtonPanel2(){
		buttonPanel2 = new JPanel(new BorderLayout(30, 0));
		buttonPanel2.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel2 = new GridBagConstraints();
		gbc_btnPanel2.gridwidth = 2;
		gbc_btnPanel2.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel2.gridx = 3;
		gbc_btnPanel2.gridy = 9;
		
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
		buttonPanel2.add(btnCancel, BorderLayout.EAST);
		// Set the horizontal gap
		formPanel.add(buttonPanel2, gbc_btnPanel2);
	}
	
	/**
	 * Method FilterList.
	 */
	public void addFilterList(){
		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridheight = 6;
		//gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		formPanel.add(scrollPane, gbc_scrollPane);
		
		JLabel filterList = new JLabel();
		filterList.setText("List of Filters");
		filterList.setForeground(Color.WHITE);
		filterList.setBackground(CalendarStandard.CalendarRed);
		filterList.setOpaque(true);
		final GridBagConstraints gbc_filterList = new GridBagConstraints();
		gbc_filterList.fill = GridBagConstraints.BOTH;
		gbc_filterList.gridx = 1;
		gbc_filterList.gridy = 1;
		formPanel.add(filterList, gbc_filterList);
	}
	
	/**
	 * Method editingMode.
	 */
	public void editingMode(){
		final JLabel filterNamelbl = new JLabel();
		filterNamelbl.setText("Filter Name");
		filterNamelbl.setForeground(Color.WHITE);
		filterNamelbl.setBackground(CalendarStandard.CalendarRed);
		filterNamelbl.setOpaque(true);
		final GridBagConstraints gbc_filterNamelbl = new GridBagConstraints();
		gbc_filterNamelbl.fill = GridBagConstraints.BOTH;
		gbc_filterNamelbl.gridx = 3;
		gbc_filterNamelbl.gridy = 1;
		formPanel.add(filterNamelbl, gbc_filterNamelbl);
		
		final JLabel inactiveFilterlbl = new JLabel();
		inactiveFilterlbl.setText("Inactive Filters");
		inactiveFilterlbl.setForeground(Color.WHITE);
		inactiveFilterlbl.setBackground(CalendarStandard.CalendarRed);
		inactiveFilterlbl.setOpaque(true);
		final GridBagConstraints gbc_inactiveFilterlbl = new GridBagConstraints();
		gbc_inactiveFilterlbl.fill = GridBagConstraints.BOTH;
		gbc_inactiveFilterlbl.gridx = 3;
		gbc_inactiveFilterlbl.gridy = 4;
		formPanel.add(inactiveFilterlbl, gbc_inactiveFilterlbl);
		
		final JLabel activeFilterlbl = new JLabel();
		activeFilterlbl.setText("Active Filters");
		activeFilterlbl.setForeground(Color.WHITE);
		activeFilterlbl.setBackground(CalendarStandard.CalendarRed);
		activeFilterlbl.setOpaque(true);
		final GridBagConstraints gbc_activeFilterlbl = new GridBagConstraints();
		gbc_activeFilterlbl.fill = GridBagConstraints.BOTH;
		gbc_activeFilterlbl.gridx = 3;
		gbc_activeFilterlbl.gridy = 6;
		formPanel.add(activeFilterlbl, gbc_activeFilterlbl);
		
		final JTextField filterName = new JTextField();
		filterName.setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_filterName = new GridBagConstraints();
		gbc_filterName.fill = GridBagConstraints.BOTH;
		gbc_filterName.gridx = 3;
		gbc_filterName.gridy = 2;
		formPanel.add(filterName, gbc_filterName);
		
		inactiveFilterPane = new JScrollPane();
		inactiveFilterPane.getViewport().setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_inactiveFilter = new GridBagConstraints();
		gbc_inactiveFilter.fill = GridBagConstraints.BOTH;
		gbc_inactiveFilter.gridx = 3;
		gbc_inactiveFilter.gridy = 5;
		formPanel.add(inactiveFilterPane, gbc_inactiveFilter);
		
		activeFilterPane = new JScrollPane();
		activeFilterPane.getViewport().setBackground(CalendarStandard.CalendarYellow);
		final GridBagConstraints gbc_activeFilter = new GridBagConstraints();
		gbc_activeFilter.fill = GridBagConstraints.BOTH;
		gbc_activeFilter.gridx = 3;
		gbc_activeFilter.gridy = 7;
		formPanel.add(activeFilterPane, gbc_activeFilter);
	}

}
