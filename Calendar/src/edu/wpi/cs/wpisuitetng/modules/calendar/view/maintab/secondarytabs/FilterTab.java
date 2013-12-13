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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.UpdateCalendarDataController;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Status;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarDataModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

import javax.swing.JScrollPane;

public class FilterTab {

	private int openedFrom;
	private JPanel buttonPanel;
	private JButton btnAddFilter;
	private AbstractButton btnCancel;
	private Container formPanel;
	private JButton btnDelete;
	private Component btnEdit;
	private Component btnNewFilter;
	private boolean initFlag;
	private JScrollPane scrollPane;
	private Component categoryList;

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
	 */
	public FilterTab(int openedFrom) {
		this.openedFrom = openedFrom;
		initFlag = false;
		final GridBagLayout gbl = new GridBagLayout();
		gbl.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0};
		gbl.columnWeights = new double[]{0.0, 1.0};
		gbl.rowHeights = new int[] {0, 0, 0, 0};
		gbl.columnWidths = new int[] {0, 0};
		
		formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setPreferredSize(new Dimension(500,600));
		formPanel.setMinimumSize(new Dimension(500, 600));	
		formPanel.setLayout(gbl);
		
		/*addLabels();
		addEditableElements();
		setDefaultValuesForEditableElements();
		addEditableElementsListeners();*/
		addButtonPanel();	
		addCategoryList();
		initFlag = true;
		}
	
	private void addButtonPanel(){
		
		buttonPanel = new JPanel(new BorderLayout(30,0));
		buttonPanel.setBackground(Color.WHITE);
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.gridwidth = 2;
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 4;
		
		//New Filter button
		try {
			final Image img = ImageIO.read(getClass().getResource("New_Icon.png"));
			btnNewFilter = new JButton("New Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("New Filter");
		}

		/*btnNewFilter.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnNewFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newFilter();
			}
		});*/

		//Add Edit button
		try {
			Image img = ImageIO.read(getClass().getResource("Edit_Icon.png"));
			btnEdit = new JButton("Edit", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
		btnCancel.setText("Edit");
		}
	
		/*btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeTabCancel();
			}
		});*/
	
		// Add Delete Button
		try {
			final Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			btnDelete = new JButton("Delete Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setText("Delete Filter");
		}
	
		/*btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteFilter();
			}	
		});*/
		
		//addEditableElementsListeners();
	
		buttonPanel.add(btnNewFilter, BorderLayout.WEST);
		buttonPanel.add(btnEdit, BorderLayout.CENTER);
		buttonPanel.add(btnDelete, BorderLayout.LINE_END);
		// Set the horizontal gap
		formPanel.add(buttonPanel, gbc_btnPanel);
	}	
	
	public void addCategoryList(){
		scrollPane = new JScrollPane(categoryList, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		formPanel.add(scrollPane, gbc_scrollPane);
	}
		/**
		 * Close this filter tab
		 */
		/*protected void removeTab(int goTo) {
			GUIEventController.getInstance().removeFilterTab(this, goTo);
		}
		/**
		 * Close this filter tab when cancel is hit
		 */
		/*protected void removeTabCancel() {
			GUIEventController.getInstance().removeFilterTab(this, openedFrom);
		}*/
	
}
