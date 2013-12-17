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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

/**
 * The label used by a month day for events and commitments
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class CalendarObjectWrapper extends JPanel{
	Commitment comm = null;
	Event event = null;
	JLabel label = null;
	/**
	 * Constructor for wrapper.
	
	 * @param c Commitment
	 */
	public CalendarObjectWrapper(Commitment c){
		super();
		label = new JLabel(c.getName());
		this.setLayout(new GridLayout(1,1));
		this.add(label);
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
			label.setIcon(imageIcon);
			
		} catch (IOException exc) { }
		
		Color bgcolor = null;
		try{
			if(comm.getIsPersonal()){
				bgcolor = GUIEventController.getInstance().getCalendar().getMyCalData().getCategories().getCategory(comm.getCategoryID()).getCategoryColor();
			}
			else{
				bgcolor = GUIEventController.getInstance().getCalendar().getTeamCalData().getCategories().getCategory(comm.getCategoryID()).getCategoryColor();
			}
		}
		catch(java.lang.NullPointerException excep){
			bgcolor = Color.WHITE;
		}
		
		this.setBorder(new CalendarObjectWrapperBorder(bgcolor, CalendarStandard.CalendarYellow));
	}
	
	/**
	 * Constructor for wrapper.
	
	 * @param e Event
	 */
	public CalendarObjectWrapper(Event e){
		super();
		label = new JLabel(e.getName());
		this.setLayout(new GridLayout(1,1));
		this.add(label);
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
			label.setIcon(imageIcon);
			
		} catch (IOException exc) { }

		Color bgcolor = null;
		try{
			if(event.getIsPersonal()){
				bgcolor = GUIEventController.getInstance().getCalendar().getMyCalData().getCategories().getCategory(event.getCategoryID()).getCategoryColor();
			}
			else{
				bgcolor = GUIEventController.getInstance().getCalendar().getTeamCalData().getCategories().getCategory(event.getCategoryID()).getCategoryColor();
			}
		}
		catch(java.lang.NullPointerException excep){
			bgcolor = Color.WHITE;
		}
		
		this.setBorder(new CalendarObjectWrapperBorder(bgcolor, CalendarStandard.CalendarYellow));
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
	
	 * @return a new copy of this */
	public CalendarObjectWrapper copy(){
		CalendarObjectWrapper result;
		if(comm != null){
			result = new CalendarObjectWrapper(comm);
		}
		else{
			result = new CalendarObjectWrapper(event);
		}
			return result;
	}
	
	
	
}
