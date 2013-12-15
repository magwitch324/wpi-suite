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

import java.awt.LayoutManager;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;

 /* @author CS Anonymous
  * @version $Revision: 1.0 $
  */
/**
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
 @SuppressWarnings("serial")
public class EventViewPanel extends JPanel {

	private Event event;
	
	/**
	 * Constructor for EventViewPanel.
	 */
	public EventViewPanel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for EventViewPanel.
	 * @param layout LayoutManager
	 */
	public EventViewPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for EventViewPanel.
	 * @param event Event
	 */
	public EventViewPanel(Event e) {
		// TODO Auto-generated constructor stub
		event = e;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event e) {
		event = e;
	}

	

}
