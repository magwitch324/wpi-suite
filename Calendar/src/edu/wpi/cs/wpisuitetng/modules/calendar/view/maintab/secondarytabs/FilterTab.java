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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

public class FilterTab {

	private int openedFrom;
	private JPanel buttonPanel;
	private JButton btnAddFilter;
	private AbstractButton btnCancel;
	private Container formPanel;
	private JButton btnDelete;
	private Component btnEdit;
	private Component btnNewFilter;

	/**
	 * Create the panel.
	 */
	public FilterTab(int openedFrom) {
		this.openedFrom = openedFrom;
		final GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		final JPanel spacePanel1 = new JPanel();
		final JPanel spacePanel2 = new JPanel();
		formPanel = new JPanel();
		formPanel.setBackground(Color.WHITE);
		formPanel.setPreferredSize(new Dimension(500,600));
		formPanel.setMinimumSize(new Dimension(500, 600));	
		add(formPanel);
		
		
		buttonPanel = new JPanel(new BorderLayout(30,0));
		buttonPanel.setBackground(Color.WHITE);
		
		//New Filter button
		try {
			final Image img = ImageIO.read(getClass().getResource("New_Icon.png"));
			btnNewFilter = new JButton("New Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("New Filter");
		}

		btnNewFilter.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnNewFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newFilter();
			}
		});
		
		final GridBagConstraints gbc_btnPanel = new GridBagConstraints();
		gbc_btnPanel.gridwidth = 3;
		gbc_btnPanel.insets = new Insets(0, 0, 0, 5);
		gbc_btnPanel.anchor = GridBagConstraints.CENTER;
		gbc_btnPanel.gridx = 1;
		gbc_btnPanel.gridy = 10;
		
		//Add Edit button
		try {
			Image img = ImageIO.read(getClass().getResource("Edit_Icon.png"));
			btnEdit = new JButton("Edit", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnCancel.setText("Edit");
		}
		
		btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeTabCancel();
			}
		});
		
		buttonPanel.add(btnAddFilter, BorderLayout.WEST);
		buttonPanel.add(btnEdit, BorderLayout.CENTER);
	                    				// Set the horizontal gap
		formPanel.add(buttonPanel, gbc_btnPanel);
		
		addEditableElementsListeners();
		
		// Add Delete Button
		try {
			final Image img = ImageIO.read(getClass().getResource("Delete_Icon.png"));
			btnDelete = new JButton("Delete Filter", new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			btnDelete.setText("Delete Filter");
		}
		
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this button
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteFilter();
			}	
		});
		buttonPanel.add(btnDelete, BorderLayout.LINE_END);
		}
			
			/**
			 * Close this filter tab
			 */
			protected void removeTab(int goTo) {
				GUIEventController.getInstance().removeFilterTab(this, goTo);
			}
			/**
			 * Close this filter tab when cancel is hit
			 */
			protected void removeTabCancel() {
				GUIEventController.getInstance().removeFilterTab(this, openedFrom);
			}
	
}
