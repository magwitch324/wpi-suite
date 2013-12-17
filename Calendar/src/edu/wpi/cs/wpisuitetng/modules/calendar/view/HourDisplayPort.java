/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;

/**
 * Viewport class for handling the creation of equal distance times
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class HourDisplayPort extends JViewport {
	JComponent reference = null;
	JPanel mainpanel = new JPanel();
	String[] times = {"12:00", " 1 AM", " 2:00", " 3:00", " 4:00", " 5:00", " 6:00",
			" 7:00", " 8:00", " 9:00", "10:00", "11:00", "12 PM",
			" 1:00", " 2:00", " 3:00", " 4:00", " 5:00", " 6:00",
			" 7:00", " 8:00", " 9:00", "10:00", "11:00"};
	
	JLabel[] labels = new JLabel[24];
	SpringLayout layout = new SpringLayout();
	
	/**
	 * Constructor for the HourDisplayPort
	 * @param reference the jcomponent which heights are referenced off of
	 */
	public HourDisplayPort(JComponent reference){
		super.setView(mainpanel);

		mainpanel.setBackground(CalendarStandard.CalendarRed);
		mainpanel.setBorder(new MatteBorder(0, 0, 0, 2, Color.GRAY));
		mainpanel.setLayout(layout);
		
		this.reference = reference;
		//adds a listener for a resize so that this can resie as well
		this.reference.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				setY();
			}
		});
		
		make();
	}
	
	/**
	 * Makes the labels for all the times needed
	 */
	protected void make(){
		for(int i = 1; i < 24; i++){
			labels[i] = new JLabel(times[i]);
			labels[i].setForeground(Color.WHITE);
			labels[i].setFont(CalendarStandard.CalendarFontBold);
			layout.putConstraint(SpringLayout.WEST, labels[i], 3, SpringLayout.WEST, mainpanel);
			mainpanel.add(labels[i]);
		}
		setY();
	}
	
	/**
	 * Goes through and set the y value for each of the time label boxes
	 */
	protected void setY(){
		int max = 0;
		final double height = reference.getHeight();
		for(int i = 1; i < 24; i++){
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, labels[i], 
					(int) (height * i / 24.0), SpringLayout.NORTH, mainpanel);
			max = labels[i].getPreferredSize().width > 
			max ? labels[i].getPreferredSize().width : max;
		}
		
		mainpanel.setPreferredSize(new Dimension(max + 7, (int)height));
		mainpanel.setSize(new Dimension(max + 7, (int)height));
	}
	
}
