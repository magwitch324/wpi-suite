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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;


@SuppressWarnings("serial")
public class ButtonsPanel_Create extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
		private JButton createCommitButton;
//		private final JButton createEventButton;
//		private final JButton createCategoryButton;
//	
	
	public ButtonsPanel_Create(){
		super("");
		createCommitButton= new JButton();
//		createEventButton= new JButton();
//		createCategoryButton = new JButton();
//		
//		createCommitButton= new JButton("<html>Create<br />Commitment</html>");
//		createEventButton= new JButton("<html>Create<br />Event</html>");
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(1200);
		
//		this.createEventButton.setSize(400, 800);
		this.createCommitButton.setPreferredSize(new Dimension(400, 800));
		this.createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
//		this.createCategoryButton.setPreferredSize(new Dimension(400, 800));
//		this.createCategoryButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		try {
			Image img = ImageIO.read(getClass().getResource("AddCommitment_Icon.png"));
		    this.createCommitButton.setIcon(new ImageIcon(img));
		    this.createCommitButton.setBorder(BorderFactory.createEmptyBorder());
		    this.createCommitButton.setContentAreaFilled(false);
		    
//		    img = ImageIO.read(getClass().getResource("AddEvent_Icon.png"));
//		    this.createEventButton.setIcon(new ImageIcon(img));
//		    this.createEventButton.setBorder(BorderFactory.createEmptyBorder());
//		    this.createEventButton.setContentAreaFilled(false);
//		    
//		    img = ImageIO.read(getClass().getResource("AddCategory_Icon.png"));
//		    this.createCategoryButton.setIcon(new ImageIcon(img));
//		    this.createCategoryButton.setBorder(BorderFactory.createEmptyBorder());
//		    this.createCategoryButton.setContentAreaFilled(false);
//		    
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.createCommitButton.setIcon(new ImageIcon());
			this.createCommitButton.setText("Create Commit");
//			this.createEventButton.setIcon(new ImageIcon());
//			this.createEventButton.setText("Create Event");
//			this.createCategoryButton.setIcon(new ImageIcon());
//			this.createCategoryButton.setText("Create Category");
		}
		
		createCommitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		// the action listener for the Create Requirement Button
		createCommitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create requirement pane if not in Multiple Requirement Editing Mode
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					GUIEventController.getInstance().createCommitment();
			//	}
			}
		});		
		
//		createEventButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
		//action listener for the Create Event Button
//		createEventButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
//					GUIEventController.getInstance().createEvent();
//				}
//		//	}
//		});
//		
//		
//		createCategoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this icon
//		//action listener for the Create Event Button
//		// TODO
//		
		
		
		// COMMENTED OUT CREATE EVENT AND CATEGORY BUTTONS BECAUSE THEY DON'T WORK AT THE MOMENT 
		this.createCommitButton.setBorder(new EmptyBorder(0, 0, 0, 15));
		contentPanel.add(createCommitButton);
//		this.createEventButton.setBorder(new EmptyBorder(0, 0, 0, 15));
//		contentPanel.add(createEventButton);
//		this.createCategoryButton.setBorder(new EmptyBorder(0, 0, 0, 15));
//		contentPanel.add(createCategoryButton);
//		
		contentPanel.setOpaque(false);
		
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
