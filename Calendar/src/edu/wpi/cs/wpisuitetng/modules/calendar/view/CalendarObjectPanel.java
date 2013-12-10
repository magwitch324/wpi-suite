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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CalendarObject;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;

@SuppressWarnings("serial")
public class CalendarObjectPanel extends JPanel {
	Event event = null;
	Commitment comm = null;
	GregorianCalendar acal = new GregorianCalendar();
	JComponent parent = null;
	SpringLayout layout= new SpringLayout();
	AbCalendar.types detailLevel;
	int columnwidth = 1;
	int columnspanned = 1;
	
	/**
	 * Protected constructor that handles common code
	 * @param parent the parent which sizes are based upon
	 * @param acal	the current date to be displayed
	 */
	private CalendarObjectPanel(JComponent parent, GregorianCalendar acal){
		super();
		this.parent = parent;
		this.acal.setTime(acal.getTime());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		this.setBackground(Color.WHITE);
		this.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
		    	if(e.getClickCount() > 1){
		    		callEdit();
		    	}
		    }
		});
		
		this.setLayout(new GridLayout(1,1));
		
	}
	
	/**
	 * The constructor
	 * @param parent the parent which sizes are based upon
	 * @param acal	the current date to be displayed
	 * @param event the event which all information is pulled to be displayed
	 */
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal, Event event){
		this(parent, acal);
		this.event = event;
		detailLevel = AbCalendar.types.DAY;
		setLabel();
	}
	
	/**
	 * The constructor
	 * @param parent the parent which sizes are based upon
	 * @param acal	the current date to be displayed
	 * @param comm the commitment which all information is pulled to be displayed
	 */
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal, Commitment comm){
		this(parent, acal);
		this.comm = comm;
		detailLevel = AbCalendar.types.DAY;
		setLabel();
	}
	
	/** 
	 * Constructor where level of detail is specified, for event
	 * @param parent
	 * @param acal
	 * @param event
	 * @param detailLevel
	 */
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal, Event event, AbCalendar.types detailLevel){
		this(parent, acal, event);
		this.detailLevel = detailLevel;
		setLabel(); 
	}
	/** 
	 * Constructor where level of detail is specified, for commitment
	 * @param parent
	 * @param acal
	 * @param event
	 * @param detailLevel
	 */
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal, Commitment comm, AbCalendar.types detailLevel){
		this(parent, acal, comm);
		this.detailLevel = detailLevel;
		setLabel();
	}
	
	
	private void setLabel() {
		JLabel alab = new JLabel();
		String type;
		CalendarObject calobj;
		if (event == null) {
			type = "Commitment";
			calobj = comm;
		} else {
			type = "Event";
			calobj = event;
		}
		String name = calobj.getName();
		String description = calobj.getDescription();
		String label;
		setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this text

		if (detailLevel == AbCalendar.types.WEEK) {
			label = name;
			setToolTipText(name);
		} else {
//			label = "<html><font size = 5><b>" + name + "</b></font><br>" + description + "</html>";
//			label = label.replaceAll("\n", "<br>");
			
			label = name;
			
			setToolTipText(name);
		}
		
		alab.setText(label);
		alab.setAlignmentX(SwingConstants.CENTER);
		alab.setAlignmentY(SwingConstants.NORTH);
		
		Image nameImg;
		Image scaleImg;
		
		try {
			if (calobj.getIsPersonal()) {	
				nameImg = ImageIO.read(getClass().getResource("Personal" + type + "_Icon.png"));
				
			} else {
				nameImg = ImageIO.read(getClass().getResource("Team" + type + "_Icon.png"));
			}
			scaleImg = nameImg.getScaledInstance(25,25, Image.SCALE_SMOOTH);
			alab.setIcon(new ImageIcon(scaleImg));
			
		} catch (IOException e) {
			//TODO Auto generated catch
		}
		
		alab.setBackground(new Color(0,0,0,0));
		alab.setMaximumSize(this.getSize());
		removeAll();
		
//		layout.putConstraint(SpringLayout.NORTH, alab, 1, SpringLayout.NORTH, this);
//		layout.putConstraint(SpringLayout.SOUTH, alab, -1, SpringLayout.SOUTH, this);
//		layout.putConstraint(SpringLayout.EAST, alab, -1, SpringLayout.EAST, this);
//		layout.putConstraint(SpringLayout.WEST, alab, 1, SpringLayout.WEST, this);
		
		add(alab, SwingConstants.CENTER);
	}
	
	/**
	 * @return the name of the event/commitment to display
	 */
	public String getName(){
		if(event != null){
			return event.getName();
		}
		else if(comm != null){
			return comm.getName();
		}
		return "";
	}
	
	/**
	 * refreshes the size of the based on the number of columns, columns spanned, and the length of it
	 */
	public void refreshSize(){
		double par_width = parent.getSize().getWidth();
		double par_height = parent.getSize().getHeight();
		Dimension new_size = new Dimension((int)((par_width-3*(columnwidth+1))/columnwidth * columnspanned), (int)(getSizeIndex()/48.0*par_height));
		this.setPreferredSize(new_size);
	}
	
	/**
	 * Sets the number
	 * @param columnwidth the number of columns
	 * @return the new column width
	 */
	public int setColumnWidth(int columnwidth){
		return (this.columnwidth = columnwidth);
	}
	
	/**
	 * Gets the column width
	 * @return the current column width
	 */
	public int getColumnWidth(){
		return this.columnwidth;
	}
	
	/**
	 * Sets the columns spanned
	 * @param columnspanned the number of columns that this should span
	 * @return
	 */
	public int setColumnSpan(int columnspanned){
		return (this.columnspanned = columnspanned);
	}
	
	/**
	 * Gets the start date based on its 2*hour and minute time
	 * If it is before this day then it will set it to 0
	 * 22:30 will return 45
	 * @return the index based on hour and minute
	 */
	public int getStartIndex(){
		GregorianCalendar tempstart = (GregorianCalendar)this.acal.clone();
		tempstart.set(Calendar.HOUR_OF_DAY, 0);
		tempstart.set(Calendar.MINUTE, 0);
		tempstart.set(Calendar.SECOND, 0);
		tempstart.set(Calendar.MILLISECOND, 0);
		
		int index = 0;
		if(!this.getStart().before(tempstart)){
			index = 2*this.getStart().get(Calendar.HOUR_OF_DAY);
			if(this.getStart().get(Calendar.MINUTE) >= 30 ){
				index++;
			}
		}

		return index;
	}
	
	/**
	 * Gets the length of this based on the start and end index
	 * @return the size in hours index
	 */
	public int getSizeIndex(){
		return getEndIndex() - getStartIndex();
	}
	
	/**
	 * Gets the end date based on its 2*hour and minute time
	 * If it is after this day then it will set it to 48
	 * 22:30 will return 45
	 * @return the index based on hour and minute
	 */
	public int getEndIndex(){
		GregorianCalendar tempend = (GregorianCalendar)this.acal.clone();
		tempend.set(Calendar.HOUR_OF_DAY, 0);
		tempend.set(Calendar.MINUTE, 0);
		tempend.set(Calendar.SECOND, 0);
		tempend.set(Calendar.MILLISECOND, 0);
		tempend.add(Calendar.DATE, 1);
		
		int index = 48;
		if(!this.getEnd().after(tempend)){
			index = 2*this.getEnd().get(Calendar.HOUR_OF_DAY);
			if(this.getEnd().get(Calendar.MINUTE) >= 30 ){
				index++;
			}
		}

		return index;
	}
	
	/**
	 * Gets the start time of this
	 * @return the start time of the event/commitment
	 */
	public GregorianCalendar getStart(){
		if(event != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(event.getStartTime().getTime());
			return cal;
		}
		else if(comm != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(comm.getDueDate().getTime());
			return cal;
		}
		return new GregorianCalendar();
	}
	
	/**
	 * Gets the end time of this
	 * @return the end time of the event/commitment
	 */
	public GregorianCalendar getEnd(){
		if(event != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(event.getEndTime().getTime());
			return cal;
		}
		else if(comm != null){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(comm.getDueDate().getTime());
			cal.add(Calendar.MINUTE, 30);
			return cal;
		}
		return new GregorianCalendar();
	}

	/**
	 * Detmines if this and the given COP conflict
	 * @param other the other COP to compare
	 * @return whether this and other conflict in time
	 */
	public boolean doesConflict(CalendarObjectPanel other){
		int thisstart = this.getStartIndex();
		int thisend = this.getEndIndex();
		int otherstart = other.getStartIndex();
		int otherend = other.getEndIndex();
		
		return (thisstart < otherend) && (thisend > otherstart);
	}
	
	/**
	 * calls the edit on the current event/commitment
	 */
	public void callEdit(){
		if(event != null){
			GUIEventController.getInstance().editEvent(event);
		}
		else if(comm != null){
			GUIEventController.getInstance().editCommitment(comm);
		}
	}
}
