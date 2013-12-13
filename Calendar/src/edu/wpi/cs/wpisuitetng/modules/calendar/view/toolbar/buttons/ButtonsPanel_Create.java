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
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

<<<<<<< HEAD
/**
 * Button panel that contains the large buttons on
 * the very top of the main tab.
 */
=======

 /* @author CS Anonymous
  * @version $Revision: 1.0 $
  */
>>>>>>> 47c624fa4d0af60492268fe9ea20fe07af6dadad
@SuppressWarnings("serial")
public class ButtonsPanel_Create extends ToolbarGroupView{
	
	// initialize the main view toolbar buttons
	private final JButton createEventButton;
		private final JButton createCommitButton;
		private final JButton manageCategoryButton;
		private final JButton manageFilterButton;
		private final JButton helpButton;

//	
	
	/**
	 * Constructor for ButtonsPanel_Create.
	 */
	public ButtonsPanel_Create(){
		super("");
		createEventButton= new JButton();
		createCommitButton= new JButton();
		manageCategoryButton = new JButton();
		manageFilterButton = new JButton();
		helpButton = new JButton();

		final JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		final GridBagLayout layout = new GridBagLayout();
		contentPanel.setLayout(layout);
		
		final GridBagConstraints cons1 = new GridBagConstraints();
		cons1.anchor = GridBagConstraints.PAGE_START;
        cons1.weightx = 1;
        
		final GridBagConstraints cons2 = new GridBagConstraints();
		cons1.anchor = GridBagConstraints.PAGE_START;
        cons2.weightx = 1;
		
		final GridBagConstraints cons3 = new GridBagConstraints();
		cons3.anchor = GridBagConstraints.CENTER;
        cons3.weightx = 1;
        
		final GridBagConstraints cons4 = new GridBagConstraints();
		cons4.anchor = GridBagConstraints.LINE_END;
        cons4.weightx = 1;
        
		final GridBagConstraints cons5 = new GridBagConstraints();
		cons5.anchor = GridBagConstraints.LINE_END;
        cons5.weightx = 1;

	
		createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
        createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
		manageCategoryButton.setHorizontalAlignment(SwingConstants.CENTER);
		manageFilterButton.setHorizontalAlignment(SwingConstants.CENTER);
		helpButton.setHorizontalAlignment(SwingConstants.CENTER);

		
		try {
			Image img = ImageIO.read(getClass().getResource("CreateEvent_Icon.png"));
		    createEventButton.setIcon(new ImageIcon(img));
		    
			img = ImageIO.read(getClass().getResource("CreateCommitment_Icon.png"));
		    createCommitButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("ManageCategory_Icon.png"));
            manageCategoryButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("ManageFilter_Icon.png"));
            manageFilterButton.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("Help_Icon.png"));
            helpButton.setIcon(new ImageIcon(img));
		    
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			createEventButton.setIcon(new ImageIcon());
			createCommitButton.setIcon(new ImageIcon());
			manageCategoryButton.setIcon(new ImageIcon());
			manageFilterButton.setIcon(new ImageIcon());
			helpButton.setIcon(new ImageIcon());
			helpButton.setText("Help");
		}
		
		
		
	    createEventButton.setText("<html>Create<br />Event</html>");
	    createEventButton.setFont(CalendarStandard.CalendarFontBold);
	    createEventButton.setHorizontalAlignment(SwingConstants.CENTER);
	    createEventButton.setContentAreaFilled(false);
	    createEventButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		createEventButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		// the action listener for the Create Event Button
		createEventButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					GUIEventController.getInstance().createEvent();
			}
		});
		
		
		
	    createCommitButton.setText("<html>Create<br />Commitment</html>");
	    createCommitButton.setFont(CalendarStandard.CalendarFontBold);
	    createCommitButton.setHorizontalAlignment(SwingConstants.CENTER);
	    createCommitButton.setContentAreaFilled(false);
	    createCommitButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		createCommitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		// the action listener for the Create Commitment Button
		createCommitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					GUIEventController.getInstance().createCommitment();
			}
		});	
			
		
		// Category button Create
	    manageCategoryButton.setText("<html>Manage<br />Categories</html>");
	    manageCategoryButton.setFont(CalendarStandard.CalendarFontBold);
	    manageCategoryButton.setHorizontalAlignment(SwingConstants.CENTER);
	    manageCategoryButton.setContentAreaFilled(false);
	    manageCategoryButton.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		manageCategoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		//the action listener for the Manage Category Button
		manageCategoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					GUIEventController.getInstance().createManageCategories();
			//	}
			}
		});	
		
		
		
	    manageFilterButton.setText("<html>Manage<br />Filters</html>");
	    manageFilterButton.setFont(CalendarStandard.CalendarFontBold);
	    manageFilterButton.setHorizontalAlignment(SwingConstants.CENTER);
	    manageFilterButton.setContentAreaFilled(false);
	    manageFilterButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		manageFilterButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		// the action listener for the Manage Filter Button
		manageFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					GUIEventController.getInstance().createManageFilters();
			//	}
			}
		});	
		
		
		
	    helpButton.setText("<html>Get<br />Help</html>");
	    helpButton.setFont(CalendarStandard.CalendarFontBold);
	    helpButton.setContentAreaFilled(false);
	    helpButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		// the action listener for the Help Button
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL(
							"https://github.com/magwitch324/wpi-suite/wiki/Calendar-Module").toURI());
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});	
		

		contentPanel.add(createEventButton, cons1);
		contentPanel.add(createCommitButton, cons2);
		contentPanel.add(manageCategoryButton, cons3);
		contentPanel.add(manageFilterButton, cons4);
		contentPanel.add(helpButton, cons5);

		
		this.add(contentPanel);
	}
}
