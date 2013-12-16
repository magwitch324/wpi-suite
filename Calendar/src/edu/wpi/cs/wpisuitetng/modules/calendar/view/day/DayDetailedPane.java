/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.day;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

/**
 * The class for a day containing event commitments and the half hour marks
 *@author CS Anonymous
 *@version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class DayDetailedPane extends JPanel {
	GregorianCalendar acal;
	List<Event> eventlist = new ArrayList<Event>();
	List<Commitment> commlist = new ArrayList<Commitment>();
	List<CalendarObjectPanel> sortedobjects = new ArrayList<CalendarObjectPanel>();
	List<List<CalendarObjectPanel>> displayobjects = new ArrayList<List<CalendarObjectPanel>>();
	JSeparator[] halfhourmarks= new JSeparator[49];
	
	AbCalendar.types detailLevel;
	
	/**
	 * Constructor for day detailed pane
	 * @param acal the date that is used for displaying
	 * @param detailLevel AbCalendar.types
	 */
	public DayDetailedPane(GregorianCalendar calendar, AbCalendar.types detailLevel){
		this.detailLevel = detailLevel;
		
		this.acal = (GregorianCalendar)calendar.clone();
		this.setMinimumSize(new Dimension(50, 800));
		this.setPreferredSize(new Dimension(50, 800));
		this.setBackground(CalendarStandard.CalendarYellow);
		
		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		
		this.makeLines();
		
		//resize listener that should set lines based on the new size
		this.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				setLines();
				for(CalendarObjectPanel obj : sortedobjects){
					obj.refreshSize();
				}
				setPos();
				revalidate();
				repaint();
			}
		});
		
		this.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
		    	if(e.getClickCount() > 1){
		    		int clickSpot = e.getY();
		    		int interval = clickSpot/48;
		    		double blockNum = Math.floor(clickSpot/interval);
		    		GregorianCalendar time = new GregorianCalendar();
		    		time.set(Calendar.YEAR, acal.get(Calendar.YEAR));
		    		time.set(Calendar.MONTH, acal.get(Calendar.MONTH));
		    		time.set(Calendar.DAY_OF_YEAR, acal.get(Calendar.DAY_OF_YEAR));
		    		time.set(Calendar.HOUR, 0);
		    		time.set(Calendar.MINUTE, 0);
		    		time.set(Calendar.SECOND, 0);
		    		int i;
		    		for (i = 0; i < blockNum; i++)
		    			time.add(Calendar.MINUTE, 30);
		    		GUIEventController.getInstance().createEvent(time.getTime());
		    	}
		    }
		});
	}
	
	/**
	 * merges this' eventlist and commlist into a single sorted list
	 */
	protected void merge(){
		sortedobjects = new ArrayList<CalendarObjectPanel>();
		
		if(commlist.isEmpty() && !eventlist.isEmpty()){
			for(Event event : eventlist){
				sortedobjects.add(new CalendarObjectPanel(this, acal, event, detailLevel));
			}
		}
		//if we only have commitments
		else if(!commlist.isEmpty() && eventlist.isEmpty()){
			for(Commitment comm : commlist){
				sortedobjects.add(new CalendarObjectPanel(this, acal, comm, detailLevel));
			}
		}
		//if we have both
		else if(commlist != null && eventlist != null){
			int eindex = 0;
			int cindex = 0;
			final GregorianCalendar ccal = new GregorianCalendar();
			final GregorianCalendar ecal = new GregorianCalendar();
			
			while(true){
				if(cindex == commlist.size() && eindex == eventlist.size()){
					break;
				}
				
				if(cindex >= commlist.size()){
					ccal.add(Calendar.DATE, 1);
				}
				else{
					ccal.setTime(commlist.get(cindex).getDueDate().getTime());
				}
				
				if(eindex >= eventlist.size()){
					ecal.add(Calendar.DATE, 1);
				}
				else{
					ecal.setTime(eventlist.get(eindex).getStartTime().getTime());
				}
				
				if(ccal.before(ecal) && cindex < commlist.size()){
					sortedobjects.add(new CalendarObjectPanel(this, acal, commlist.get(cindex)));
					cindex++;
				}
				else if(eindex < eventlist.size()){
					sortedobjects.add(new CalendarObjectPanel(this, acal, eventlist.get(eindex)));
					eindex++;
				}
			}
		}
	}

	/**
	 * Makes the half hour lines and sets their x size
	 */
	protected void makeLines(){
		//half hour marks code
		final SpringLayout layout = (SpringLayout)this.getLayout();
		for(int i = 0; i < 49; i++){
			halfhourmarks[i] = new JSeparator();
			
			Color col;
			if(i % 2 == 0){
				col = Color.BLACK;
				layout.putConstraint(SpringLayout.WEST, halfhourmarks[i], 
						5, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.EAST, halfhourmarks[i], 
						-5, SpringLayout.EAST, this);
			}
			else{
				col = Color.GRAY;
				layout.putConstraint(SpringLayout.WEST, halfhourmarks[i], 
						15, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.EAST, halfhourmarks[i], 
						-15, SpringLayout.EAST, this);
			}
			
			halfhourmarks[i].setBackground(col);
			halfhourmarks[i].setForeground(col);
			
			if(i < 49){
				this.add(halfhourmarks[i]);
			}
		}
		layout.putConstraint(SpringLayout.NORTH, halfhourmarks[0], 0, SpringLayout.NORTH, this);
		this.setLines();
	}
	
	/**
	 * sets the y position of the half hour lines
	 */
	protected void setLines(){
		final SpringLayout layout = (SpringLayout)this.getLayout();
		for(int i = 1; i < 49; i++){
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, halfhourmarks[i], 
								(int)((this.getSize().getHeight()) * i / 48.0),
								SpringLayout.NORTH, this);
		}
	}

	/**
	 * updates this by setting the lines, 
	 * and main caller for placing commitments and events in their places
	 */
	protected void updatepane(){
		//the list used to hold the event commitment wrappers in columns and rows based on conflicts
		displayobjects = new ArrayList<List<CalendarObjectPanel>>();
		//main loop going over all the sorted objects
		for(CalendarObjectPanel cop : sortedobjects){
			int column_index = 0;
			boolean isset = false;
			//loops for going over the columns
			while(!isset){
				List<CalendarObjectPanel> column = null;
				try{
					column = displayobjects.get(column_index);
				}
				catch(IndexOutOfBoundsException e){
					//there was no column there so it must make a new one
					 column = new ArrayList<CalendarObjectPanel>();
					 displayobjects.add(column);
				}
				
				int row_index = 0;
				//loops through the row until a conflict is found or there is nothing left
				while(!isset){
					CalendarObjectPanel obj = null;
					try{
						obj = column.get(row_index);
					}
					catch(IndexOutOfBoundsException e){
						//hits the end of the column so adds it to it and quits the main loop
						column.add(cop);
						isset = true;
						break;
					}
					
					if(cop.doesConflict(obj)){
						break;
					}
					row_index++;
				}
				
				column_index++;
			}
		}

		
		//loops through the display objects setting the number of columns for a calendarobjectpanel
		for(List<CalendarObjectPanel> column : displayobjects){
			for(CalendarObjectPanel obj : column){
				obj.setColumnWidth(this.getWidth(displayobjects.indexOf(column), obj));
			}
		}
		//loops through the display objects setting the span of columns for a calendarobjectpanel
		for(List<CalendarObjectPanel> column : displayobjects){
			for(CalendarObjectPanel obj : column){
				obj.setColumnSpan(this.getSpan(displayobjects.indexOf(column), obj));
			}
		}
		
		this.removeAll();
		final SpringLayout layout = (SpringLayout)this.getLayout();
		
		//Sets the left of an object to either this or a conflict in the previous column
		for(List<CalendarObjectPanel> column : displayobjects){
			for(CalendarObjectPanel obj : column){
				layout.putConstraint(SpringLayout.WEST, obj, 3, SpringLayout.WEST, this);
				if(displayobjects.indexOf(column) != 0){
					for(CalendarObjectPanel compobj : 
						displayobjects.get(displayobjects.indexOf(column) - 1)){
						if(obj.doesConflict(compobj)){
							layout.putConstraint(SpringLayout.WEST, obj, 
									3, SpringLayout.EAST, compobj);
						}
					}
				}
			}
		}
		
		//Refreshes the size of the object based on the number of columns and its span
		for(CalendarObjectPanel obj : sortedobjects){
			obj.refreshSize();
		}
		
		//sets the y position of each COP
		this.setPos();
		
		//Adds all the objects to this
		for(CalendarObjectPanel obj : sortedobjects){
			this.add(obj);
		}
		
		//Makes the lines
		this.makeLines();
	}
	
	/**
	 * Iterates through the sorted objects and sets their position based on start times
	 */
	protected void setPos(){
		final SpringLayout layout = (SpringLayout)this.getLayout();
		for(CalendarObjectPanel obj : sortedobjects){
			layout.putConstraint(SpringLayout.NORTH, obj, 
					(int)(this.getHeight() * obj.getStartRatio()), SpringLayout.NORTH, this);
		}
	}
	
	/**
	 * Gets the number of columns that should be associated with the object
	 * @param column_index the index of the column based on the 
	 * @param cop the object to check for width
	
	 * @return the number of columns conflicting with the object */
	protected int getWidth(int column_index, CalendarObjectPanel cop){
		int width = 1;
		width += this.getWidth(column_index, cop, -1);
		width += this.getWidth(column_index, cop, +1);
		return width;
	}
	
	/**
	 * Helper function that iterates over the column to find the width
	 * @param column_index the index of the column to check
	 * @param cop the object to check for width
	 * @param value the direction that the width check should go
	
	 * @return the width going the given direction */
	protected int getWidth(int column_index, CalendarObjectPanel cop, int value){
		int width = 0;
		int max = 0;
		//Calculate width to the value
		try{
			for(CalendarObjectPanel obj :displayobjects.get(column_index + value)){
				if(obj.doesConflict(cop)){
					width = 1;
					int temp = getWidth(column_index + value, obj, value);
					max = max < temp ? temp : max;
				}
			}
		}catch(IndexOutOfBoundsException e){}
		width += max;
		
		return width;
	}

	/**
	 * Finds the number of columns an COP should span
	 * @param column_index the index of the column to check
	 * @param cop the COP to check for span
	
	 * @return the number of columns an object should span */
	protected int getSpan(int column_index, CalendarObjectPanel cop){
		int span = 0;
		for(int i = 1 + column_index; i < displayobjects.size(); i++){
			CalendarObjectPanel max_obj = null;
			int max_width = 0;
			for(CalendarObjectPanel obj : displayobjects.get(i)){
				if(obj.doesConflict(cop)){
					if(max_width < obj.getColumnWidth()){
						span = i - column_index;
						max_width = obj.getColumnWidth();
						cop.setColumnWidth(obj.getColumnWidth());
					}
				}
			}
			if(span != 0){
				break;
			}
		}
		
		if(  span == 0 ){
			final SpringLayout layout = (SpringLayout)this.getLayout();
			layout.putConstraint(SpringLayout.EAST, cop, -3, SpringLayout.EAST, this);
		}
		
		return span;
	}

	/**
	 * Displays the commitments by merging events and commitments then updating the pane
	 * @param commList the commitments to display
	 */
	public void displayCommitments(List<Commitment> commList) {
		// if we are supposed to display commitments
		commlist = new ArrayList<Commitment>();
		//sorting check
		if (commList != null) {
			
			/*for(Commitment comm : commList){
				int index = 0;
				for(Commitment comp : commlist){
					if(comp.getDueDate().after(comm.getDueDate())){
						break;
					}
					index ++;
				}
				
				commlist.add(index, comm);
				}*/
			commlist.addAll(commList);
			
			
		} else {

		}
		merge();
		updatepane();
	}
	
	/**
	 * Displays the commitments by merging events and commitments then updating the pane
	 * @param eventList the events to display
	 */
	public void displayEvents(List<Event> eventList) {
		// if we are supposed to display events
		if(eventList == null){
			eventlist = new ArrayList<Event>();
		}
		else{
			eventlist = eventList;
		}
/*
		eventlist = new ArrayList<Event>();
		//sorting check
		if (eventList != null) {
			for(Event event : eventList){
				int index = 0;
				for(Event comp : eventlist){
					if(comp.getStartTime().after(event.getStartTime())){
						break;
					}
					index ++;
				}
				
				eventlist.add(index, event);
			}
		} else {
			
		}*/	
		merge();
		updatepane();
	}
}
