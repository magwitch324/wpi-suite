/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.month;

import java.awt.Image;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

/**
 * The label used by a month day for events and commitments
 */
@SuppressWarnings("serial")
public class CalendarObjectWrapper extends JLabel{
	Commitment comm = null;
	Event event = null;
	
	/**
	 * Constructor for wrapper.
	 * @param comm the commitment that this should display
	 */
	public CalendarObjectWrapper(Commitment c){
		super(c.getName());
		comm = c;
		
		//adds the proper image to this
		try {
			Image nameImg;
			final Image scaleImg;
			if (comm.getIsPersonal()) {
				nameImg = ImageIO.read(AbCalendar.class.getResource("PersonalCommitment_Icon.png"));
				
			} else {
				nameImg = ImageIO.read(AbCalendar.class.getResource("TeamCommitment_Icon.png"));
			}
			
			scaleImg = nameImg.getScaledInstance(13, 13, Image.SCALE_SMOOTH);
			final ImageIcon imageIcon = new ImageIcon(scaleImg);
			this.setIcon(imageIcon);
			
		} catch (IOException exc) { }
		
		this.setPreferredSize(super.getPreferredSize());
	}
	
	/**
	 * Constructor for wrapper.
	 * @param event the event that this should display
	 */
	public CalendarObjectWrapper(Event e){
		super(e.getName());
		event = e;
		
		//adds the proper image to this
		try {
			Image nameImg;
			final Image scaleImg;
			if (event.getIsPersonal()) {
				nameImg = ImageIO.read(AbCalendar.class.getResource("PersonalEvent_Icon.png"));
				
			} else {
				nameImg = ImageIO.read(AbCalendar.class.getResource("TeamEvent_Icon.png"));
			}
			
			scaleImg = nameImg.getScaledInstance(13, 13, Image.SCALE_SMOOTH);
			final ImageIcon imageIcon = new ImageIcon(scaleImg);
			this.setIcon(imageIcon);
			
		} catch (IOException exc) { }
		
		this.setPreferredSize(super.getPreferredSize());
	}
	
	/**
	 * The commitment or event needs to be edited so call the GUI controller
	 */
	public void edit(){
		if(comm != null){
			GUIEventController.getInstance().editCommitment(comm);
		}
		else{
			GUIEventController.getInstance().editEvent(event);
		}
	}
	
	/**
	 * Creates and returns a new version of this
	 * @return a new copy of this
	 */
	public CalendarObjectWrapper copy(){
		CalendarObjectWrapper result = new CalendarObjectWrapper(event);
		if(comm != null){
			result = new CalendarObjectWrapper(comm);
		}
			return result;
	}
	
}
