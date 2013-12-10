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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

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
		private final JButton helpButton;

//	
	
	public ButtonsPanel_Create(){
		super("");
		createCommitButton= new JButton();
		createEventButton= new JButton();
		manageCategoryButton = new JButton();
		manageFilterButton = new JButton();
		helpButton = new JButton();
//		
//		createCommitButton= new JButton("<html>Create<br />Commitment</html>");
//		createEventButton= new JButton("<html>Create<br />Event</html>");
		JPanel contentPanel = new JPanel();
//		contentPanel.setBackground(Color.WHITE);
		GridBagLayout layout = new GridBagLayout();
		contentPanel.setLayout(layout);
		GridBagConstraints cons1 = new GridBagConstraints();
//		layout.ipadx = 5;
//		layout.ipady = 5;
		cons1.anchor = GridBagConstraints.PAGE_START;
        cons1.weightx = 1;
		GridBagConstraints cons2 = new GridBagConstraints();
//		layout.ipadx = 5;
//		layout.ipady = 5;
		cons2.anchor = GridBagConstraints.CENTER;
        cons2.weightx = 1;
		GridBagConstraints cons3 = new GridBagConstraints();
//		layout.ipadx = 5;
//		layout.ipady = 5;
		cons3.anchor = GridBagConstraints.LINE_END;
        cons3.weightx = 1;
		
//		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
//		this.setPreferredWidth(700);
	
//		this.createCommitButton.setSize(new Dimension(100, 250));
		this.createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.createEventButton.setPreferredSize(new Dimension(240, 250));
		this.createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.manageCategoryButton.setPreferredSize(new Dimension(240, 250));
		this.manageCategoryButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.manageFilterButton.setPreferredSize(new Dimension(240, 250));
		this.manageFilterButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.helpButton.setSize(240, 250);
		this.helpButton.setHorizontalAlignment(SwingConstants.CENTER);

		
		try {
			Image img = ImageIO.read(getClass().getResource("CreateCommitment_Icon.png"));
//			ImageIcon imageIcon = new ImageIcon(img);
//			int width = imageIcon.getIconWidth();
//			int hight = imageIcon.getIconHeight();
		    this.createCommitButton.setIcon(new ImageIcon(img));
		    //ImageObserver observer =;
			//this.createCommitButton.setMaximumSize(img.getWidth(observer ), height);
		    
		    img = ImageIO.read(getClass().getResource("CreateEvent_Icon.png"));
		    this.createEventButton.setIcon(new ImageIcon(img));
//		    ImageIcon imageIcon1 = new ImageIcon(img);
//		    this.createEventButton.setIcon((new ImageIcon(imageIcon1.getImage().getScaledInstance(width, hight,
//		            java.awt.Image.SCALE_SMOOTH))));
		    
		    img = ImageIO.read(getClass().getResource("ManageCategory_Icon.png"));
            this.manageCategoryButton.setIcon(new ImageIcon(img));
//		    ImageIcon imageIcon2 = new ImageIcon(img);
//		    this.manageCategoryButton.setIcon((new ImageIcon(imageIcon2.getImage().getScaledInstance(width, hight,
//		            java.awt.Image.SCALE_SMOOTH))));
		    
		    img = ImageIO.read(getClass().getResource("ManageFilter_Icon.png"));
            this.manageFilterButton.setIcon(new ImageIcon(img));
//		    ImageIcon imageIcon3 = new ImageIcon(img);
//		    this.manageFilterButton.setIcon((new ImageIcon(imageIcon3.getImage().getScaledInstance(width, hight,
//		            java.awt.Image.SCALE_SMOOTH))));
		    
		    img = ImageIO.read(getClass().getResource("Help_Icon.png"));
            this.helpButton.setIcon(new ImageIcon(img));
//		    ImageIcon imageIcon4 = new ImageIcon(img);
//		    this.helpButton.setIcon((new ImageIcon(imageIcon4.getImage().getScaledInstance(width, hight,
//		            java.awt.Image.SCALE_SMOOTH))));
		    
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.createCommitButton.setIcon(new ImageIcon());
			this.createEventButton.setIcon(new ImageIcon());
			this.manageCategoryButton.setIcon(new ImageIcon());
			this.manageFilterButton.setIcon(new ImageIcon());
			this.helpButton.setIcon(new ImageIcon());
			this.helpButton.setText("Help");
		}
		
	    this.createCommitButton.setText("<html>Create<br />Commitment</html>");
	    this.createCommitButton.setFont(CalendarStandard.CalendarFontBold);
	    this.createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
//	    this.createCommitButton.setForeground(CalendarStandard.CalendarRed);
//	    this.createCommitButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
//	    this.createCommitButton.setBackground(CalendarStandard.CalendarYellow);
	    this.createCommitButton.setContentAreaFilled(false);
	    this.createCommitButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//	    this.createCommitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		
		
		
	    this.createEventButton.setText("<html>Create<br />Event</html>");
	    this.createEventButton.setFont(CalendarStandard.CalendarFontBold);
	    this.createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
//	    this.createEventButton.setForeground(CalendarStandard.CalendarRed);
//	    this.createEventButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
//	    this.createEventButton.setBackground(CalendarStandard.CalendarYellow);
	    this.createEventButton.setContentAreaFilled(false);
	    this.createEventButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//	    this.createEventButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		
		
	    this.manageCategoryButton.setText("<html>Manage<br />Categories</html>");
	    this.manageCategoryButton.setFont(CalendarStandard.CalendarFontBold);
	    this.manageCategoryButton.setHorizontalAlignment(SwingConstants.CENTER);
//	    this.manageCategoryButton.setForeground(CalendarStandard.CalendarRed);
//	    this.manageCategoryButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
//	    this.manageCategoryButton.setBackground(CalendarStandard.CalendarYellow);
	    this.manageCategoryButton.setContentAreaFilled(false);
	    this.manageCategoryButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//	    this.manageCategoryButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		
		
		
	    this.manageFilterButton.setText("<html>Manage<br />Filters</html>");
	    this.manageFilterButton.setFont(CalendarStandard.CalendarFontBold);
	    this.manageFilterButton.setHorizontalAlignment(SwingConstants.CENTER);
//	    this.manageFilterButton.setForeground(CalendarStandard.CalendarRed);
//	    this.manageFilterButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
//	    this.manageFilterButton.setBackground(CalendarStandard.CalendarYellow);
	    this.manageFilterButton.setContentAreaFilled(false);
	    this.manageFilterButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//	    this.manageFilterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		
		
		
//	    this.helpButton.setText("Help Library");
	    this.helpButton.setFont(CalendarStandard.CalendarFontBold);
//	    this.helpButton.setForeground(CalendarStandard.CalendarRed);
//	    this.helpButton.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, CalendarStandard.CalendarRed));
//	    this.helpButton.setBackground(CalendarStandard.CalendarYellow);
	    this.helpButton.setContentAreaFilled(false);
	    this.helpButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//	    this.helpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		// the action listener for the Help Button
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					//GUIEventController.getInstance().helpWiki();
			//	}
				try {
					Desktop.getDesktop().browse(new URL("https://github.com/magwitch324/wpi-suite/wiki/Calendar-Module").toURI());
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});	
		
//		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
//	    createCommitButton.setBorder(raisedbevel);
		contentPanel.add(createCommitButton, cons1);
//		createEventButton.setBorder(raisedbevel);
		contentPanel.add(createEventButton, cons1);
//		manageCategoryButton.setBorder(raisedbevel);
		
		// REMOVED NON-FUNCTIONAL BUTTONS FOR THE TIME BEING
		//contentPanel.add(manageCategoryButton, cons2);
//		manageFilterButton.setBorder(raisedbevel);
		//contentPanel.add(manageFilterButton, cons3);
//		helpButton.setBorder(raisedbevel);
		contentPanel.add(helpButton, cons3);

		
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
