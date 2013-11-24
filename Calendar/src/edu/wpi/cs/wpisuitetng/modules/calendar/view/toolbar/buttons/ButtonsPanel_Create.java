/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.toolbar.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;


@SuppressWarnings("serial")
public class ButtonsPanel_Create extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
		private JButton createCommitButton;
		private final JButton createEventButton;
	
	public ButtonsPanel_Create(){
		super("");
		createCommitButton= new JButton();
		createEventButton= new JButton();
		
//		createCommitButton= new JButton("<html>Create<br />Commitment</html>");
//		createEventButton= new JButton("<html>Create<br />Event</html>");
		JPanel contentPanel = new JPanel();

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(600);
		
		this.createEventButton.setSize(300, 800);
		this.createEventButton.setBackground(Color.WHITE);
		this.createCommitButton.setPreferredSize(new Dimension(300, 800));
		this.createCommitButton.setBackground(Color.WHITE);
		this.createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		try {
			Image img = ImageIO.read(getClass().getResource("Add_Commitment.png"));
//		    Image img = ImageIO.read(getClass().getResource("new_commit.png"));
		    this.createCommitButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("Add_Event.png"));
		    this.createEventButton.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			this.createCommitButton.setIcon(new ImageIcon());
			this.createCommitButton.setText("Create Commit");
			this.createEventButton.setIcon(new ImageIcon());
			this.createEventButton.setText("Create Event");
		}
		
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
		
		//action listener for the Create Event Button
		createEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//if (!ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
					GUIEventController.getInstance().createEvent();
				}
		//	}
		});
		
			
		contentPanel.add(createCommitButton);
		contentPanel.add(createEventButton);
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
	public JButton getCreateEventButton() {
		return createEventButton;
	}

}
