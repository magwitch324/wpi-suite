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
import java.awt.GridLayout;
import java.util.Comparator;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;

/**
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class EventViewPanel extends JPanel {
	private Event event;

	enum Sort_Type{
		NAME, START_DATE, END_DATE, DESCRIPTION
	};

	/**
	 * Constructor for EventViewPanel.
	 * @param e Event
	 */
	public EventViewPanel(Event e) {
		super();
		event = e;
		this.setLayout(new GridLayout(1,4));
		
		this.add(new JLabel(event.getName()));
		this.add(new JLabel("Start time"));
		this.add(new JLabel("End Time"));
		this.add(new JLabel(event.getDescription()));
		this.setPreferredSize(new Dimension(100,100));
		this.setBackground(Color.WHITE);
	}

	public int compareTo(EventViewPanel other, Sort_Type sort_type){

		if(sort_type == Sort_Type.NAME){
			String myname = this.event.getName();
			String othername = other.event.getName();

			return myname.compareTo(othername);
		}
		else if(sort_type == Sort_Type.START_DATE){
			GregorianCalendar mycal = this.event.getStartTime();
			GregorianCalendar othercal = other.event.getStartTime();

			if(mycal.before(othercal)){
				return 1;
			}
			else if(mycal.after(othercal)){
				return -1;
			}
			else{
				return 0;
			}
		}
		else if(sort_type == Sort_Type.END_DATE){
			GregorianCalendar mycal = this.event.getEndTime();
			GregorianCalendar othercal = other.event.getEndTime();

			if(mycal.before(othercal)){
				return 1;
			}
			else if(mycal.after(othercal)){
				return -1;
			}
			else{
				return 0;
			}
		}
		else if(sort_type == Sort_Type.DESCRIPTION){
			String mydesc = this.event.getDescription();
			String otherdesc = other.event.getDescription();

			return mydesc.compareTo(otherdesc);
		}
		return 0;
	}

}
