/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.buttons;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;


@SuppressWarnings("serial")
public class ButtonsPanel_Create extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
		private JButton createCommitButton;
		private final JButton createEventButton;
		private final JButton manageCategoryButton;
		private final JButton manageFilterButton;
//	
	
	public ButtonsPanel_Create(){
		super("");
		createCommitButton= new JButton();
		createEventButton= new JButton();
		manageCategoryButton = new JButton();
		manageFilterButton = new JButton();
//		
//		createCommitButton= new JButton("<html>Create<br />Commitment</html>");
//		createEventButton= new JButton("<html>Create<br />Event</html>");
		JPanel contentPanel = new JPanel();
//		contentPanel.setBackground(Color.WHITE);
		contentPanel.setLayout(new GridLayout(1,4,25,25));
//		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
////		this.setPreferredWidth(1200);
		
//		this.createCommitButton.setSize(800, 800);
		this.createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.createEventButton.setSize(400, 200);
		this.createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.manageCategoryButton.setSize(400, 200);
		this.manageCategoryButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.manageFilterButton.setSize(400, 200);
		this.manageFilterButton.setHorizontalAlignment(SwingConstants.CENTER);

		
		try {
			Image img = ImageIO.read(getClass().getResource("CreateCommitment_Icon.png"));
		    this.createCommitButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("CreateEvent_Icon.png"));
		    this.createEventButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("ManageCategory_Icon.png"));
		    this.manageCategoryButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("ManageFilter_Icon.png"));
		    this.manageFilterButton.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.createCommitButton.setIcon(new ImageIcon());
			this.createEventButton.setIcon(new ImageIcon());
			this.manageCategoryButton.setIcon(new ImageIcon());
			this.manageFilterButton.setIcon(new ImageIcon());
		}
		
	    this.createCommitButton.setText("Create Commitment");
	    this.createCommitButton.setFont(CalendarStandard.CalendarFontBold);
	    this.createCommitButton.setForeground(CalendarStandard.CalendarRed);
	    this.createCommitButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
	    this.createCommitButton.setBackground(CalendarStandard.CalendarYellow);
		createCommitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		// the action listener for the Create Commitment Button
		createCommitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					GUIEventController.getInstance().createCommitment();
			//	}
			}
		});	
		
		
		
	    this.createEventButton.setText("Create Event");
	    this.createEventButton.setFont(CalendarStandard.CalendarFontBold);
	    this.createEventButton.setForeground(CalendarStandard.CalendarRed);
	    this.createEventButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
	    this.createEventButton.setBackground(CalendarStandard.CalendarYellow);
		createEventButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		// the action listener for the Create Event Button
		createEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					GUIEventController.getInstance().createEvent();
			//	}
			}
		});	
		
		
	    this.manageCategoryButton.setText("Manage Categories");
	    this.manageCategoryButton.setFont(CalendarStandard.CalendarFontBold);
	    this.manageCategoryButton.setForeground(CalendarStandard.CalendarRed);
	    this.manageCategoryButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
	    this.manageCategoryButton.setBackground(CalendarStandard.CalendarYellow);
		manageCategoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		// the action listener for the Manage Category Button
//		manageCategoryButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
//				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
//					GUIEventController.getInstance().manageCategories();
//			//	}
//			}
//		});	
		
		
		
	    this.manageFilterButton.setText("Manage Filters");
	    this.manageFilterButton.setFont(CalendarStandard.CalendarFontBold);
	    this.manageFilterButton.setForeground(CalendarStandard.CalendarRed);
	    this.manageFilterButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
	    this.manageFilterButton.setBackground(CalendarStandard.CalendarYellow);
		manageFilterButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		// the action listener for the Manage Filter Button
//		manageFilterButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
//				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
//					GUIEventController.getInstance().manageFilters();
//			//	}
//			}
//		});	
		

		contentPanel.add(createCommitButton);
		contentPanel.add(createEventButton);
		contentPanel.add(manageCategoryButton);
		contentPanel.add(manageFilterButton);

		
		this.add(contentPanel);
	}
	
	/**
	 * Method getCreateCommitButton.
	
	 * @return JButton */
	public JButton getCreateCommitButton() {
		return createCommitButton;
	}

	/**
	 * Method getCreateEventButton.
	
	 * @return JButton */
//	public JButton getCreateEventButton() {
//		return createEventButton;
//	}

}
