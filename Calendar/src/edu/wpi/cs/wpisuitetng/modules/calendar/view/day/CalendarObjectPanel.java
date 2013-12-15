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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CalendarObject;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar.types;

/**
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
 @SuppressWarnings("serial")
public class CalendarObjectPanel extends JPanel {
	Event event = null;
	Commitment comm = null;
	GregorianCalendar acal = new GregorianCalendar();
	JComponent parent = null;
	SpringLayout layout= new SpringLayout();
	AbCalendar.types detailLevel;
	int columnwidth = 2;
	int columnspanned = 1;
	
	/**
	 * Protected constructor that handles common code
	 * @param parent the parent which sizes are based upon
	 * @param acal	the current date to be displayed
	 */
	private CalendarObjectPanel(JComponent parent, GregorianCalendar acal){
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
		
		this.setLayout(layout);
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
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal, 
			Event event, AbCalendar.types detailLevel){
		this(parent, acal, event);
		this.detailLevel = detailLevel;
		setLabel(); 
	}
	/** 
	 * Constructor where level of detail is specified, for commitment
	 * @param parent
	 * @param acal
	
	 * @param detailLevel
	 * @param comm Commitment
	 */
	public CalendarObjectPanel(JComponent parent, GregorianCalendar acal,
			Commitment comm, AbCalendar.types detailLevel){
		this(parent, acal, comm);
		this.detailLevel = detailLevel;
		setLabel();
	}
	
	
	private void setLabel() {
		String type;
		String time;
		CalendarObject calobj;
		
		final SimpleDateFormat tm = new SimpleDateFormat();
		tm.applyPattern("hh:mm a");
		
		if (event == null) {
			type = "Commitment";
			calobj = comm;
			time = "Due: " + tm.format(comm.getDueDate().getTime());
		} else {
			type = "Event";
			calobj = event;
			//if it is a multi day event
			if(event.getStartTime().get(Calendar.DAY_OF_MONTH) != 
					event.getEndTime().get(Calendar.DAY_OF_MONTH)){
				final SimpleDateFormat dateFormat = new SimpleDateFormat();
						dateFormat.applyPattern("MM/dd/yy");
				//if the current day we are adding it to is the first day
				if(acal.get(Calendar.DAY_OF_MONTH) == 
						event.getStartTime().get(Calendar.DAY_OF_MONTH)){
					time = tm.format(event.getStartTime().getTime()) + 
							" - " + dateFormat.format(event.getEndTime().getTime());
				}
				else if(acal.get(Calendar.DAY_OF_MONTH) == 
						event.getEndTime().get(Calendar.DAY_OF_MONTH)){
					time = dateFormat.format(event.getStartTime().getTime())  + 
							" - " + tm.format(event.getEndTime().getTime());
				}
				else{
					time = dateFormat.format(event.getStartTime().getTime())  + 
							" - " + dateFormat.format(event.getEndTime().getTime());
				}
			}
			else{
				time = tm.format(event.getStartTime().getTime()) + 
						" - " + tm.format(event.getEndTime().getTime());
			}
		}
		
		final String name = calobj.getName();
		String description = calobj.getDescription();
		
		String tt = "<html>Name: " + name + "<br>" + time + 
				"<br>Description: " + description + "</html>";
		tt = tt.replaceAll("\n", "<br>");
		setToolTipText(tt);
		
		final JLabel nameL = new JLabel(name);
		nameL.setFont(new Font("SansSerif", Font.BOLD, 14));
		description = description.replaceAll("\n", " ");
		final JLabel descL = new JLabel(description);
		final JLabel timeL = new JLabel(time);
		final JLabel iconL = new JLabel();
		
		setCursor(new Cursor(Cursor.HAND_CURSOR)); // To change cursor as it moves over this text
		
		Image nameImg;
		final Image scaleImg;
		try {
			if (calobj.getIsPersonal()) {
				nameImg = ImageIO.read(getClass().getResource("Personal" + type + "_Icon.png"));
				
			} else {
				nameImg = ImageIO.read(getClass().getResource("Team" + type + "_Icon.png"));
			}
			scaleImg = nameImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			final ImageIcon imageIcon = new ImageIcon(scaleImg);
			iconL.setIcon(imageIcon);
			iconL.setSize(imageIcon.getIconHeight(), imageIcon.getIconWidth());
			
		} catch (IOException e) {

		}
		
		removeAll();
		
		add(iconL);
		
		layout.putConstraint(SpringLayout.WEST, iconL, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, iconL, 5, SpringLayout.NORTH, this);
		
		final SpringLayout textLayout = new SpringLayout();
		final JPanel textPanel = new JPanel();
		textPanel.setBackground(Color.WHITE);
		textPanel.setLayout(textLayout);
		
		textPanel.add(nameL);
		textPanel.add(timeL);
		textPanel.add(descL);
		
		add(textPanel);
		
		// Layout for right side of veiw
		layout.putConstraint(SpringLayout.WEST, textPanel, 5, SpringLayout.EAST, iconL);
		layout.putConstraint(SpringLayout.NORTH, textPanel, 2, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, textPanel, -5, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, textPanel, -5, SpringLayout.SOUTH, this);
		
		// Layout for name label
		textLayout.putConstraint(SpringLayout.WEST, nameL, 0, SpringLayout.WEST, textPanel);
		textLayout.putConstraint(SpringLayout.NORTH, nameL, 0, SpringLayout.NORTH, textPanel);
		textLayout.putConstraint(SpringLayout.EAST, nameL, 0, SpringLayout.EAST, textPanel);
		
		// layout for time label
		textLayout.putConstraint(SpringLayout.WEST, timeL, 0, SpringLayout.WEST, textPanel);
		textLayout.putConstraint(SpringLayout.NORTH, timeL, 0, SpringLayout.SOUTH, nameL);
		textLayout.putConstraint(SpringLayout.EAST, timeL, 0, SpringLayout.EAST, textPanel);
		
		// layout for desc label
		textLayout.putConstraint(SpringLayout.WEST, descL, 0, SpringLayout.WEST, textPanel);
		textLayout.putConstraint(SpringLayout.NORTH, descL, 0, SpringLayout.SOUTH, timeL);
		textLayout.putConstraint(SpringLayout.EAST, descL, 0, SpringLayout.EAST, textPanel);
		
	}
	
	/**
	 * @return the name of the event/commitment to display
	 */
	public String getName(){
		String result = "";
		if(event != null){
			result = event.getName();
		}
		else if(comm != null){
			result = comm.getName();
		}
		return result;
	}
	
	/**
	 * refreshes the size of the based on the number of columns, 
	 * columns spanned, and the length of it
	 */
	public void refreshSize(){
		final double par_width = parent.getSize().getWidth();
		final double par_height = parent.getSize().getHeight();
		final Dimension new_size = new Dimension((int)(
				(par_width - 3 * columnwidth - 3) / columnwidth * columnspanned), 
				(int)(par_height * this.getRatioDifference()));
		this.setPreferredSize(new_size);
	}
	
	/**
	 * Sets the number
	 * @param columnwidth the number of columns
	
	 * @return the new column width */
	public int setColumnWidth(int columnwidth){
		return (this.columnwidth = columnwidth);
	}
	
	/**
	 * Gets the column width
	 * @return the current column width
	 */
	public int getColumnWidth(){
		return columnwidth;
	}
	
	/**
	 * Sets the columns spanned
	 * @param columnspanned the number of columns that this should span
	
	
	 * @return int */
	public int setColumnSpan(int columnspanned){
		return (this.columnspanned = columnspanned);
	}
	
	/**
	 * Gets the columns spanned
	 * @return
	 */
	public int getColumnSpan(){
		return columnspanned;
	}
	
	/**
	 * Finds the start time as a ratio in a 24 hour period
	 * @return a value 0 - 1.0 of the start time, defaults to 0 if before this' today
	 */
	public double getStartRatio(){
		final GregorianCalendar tempstart = (GregorianCalendar)acal.clone();
		tempstart.set(Calendar.HOUR_OF_DAY, 0);
		tempstart.set(Calendar.MINUTE, 0);
		tempstart.set(Calendar.SECOND, 0);
		tempstart.set(Calendar.MILLISECOND, 0);
		
		double index = 0;
		if(!this.getStart().before(tempstart)){
			index = (( this.getStart().get(Calendar.HOUR_OF_DAY) * 60.0 ) + 
					(this.getStart().get(Calendar.MINUTE))) / (24.0 * 60.0);
		}

		return index;
	}
	
	/**
	 * Finds the difference between the end ratio and start ratio value
	 * @return the equivalent of the length of the event in ratio form
	 */
	public double getRatioDifference(){
		return this.getEndRatio() - this.getStartRatio();
	}
	
	/**
	 * Finds the end time as a ratio in a 24 hour period
	 * @return a value 0 - 1.0 of the end time, defaults to 1.0 if after this' today
	 */
	public double getEndRatio(){
		final GregorianCalendar tempend = (GregorianCalendar)acal.clone();
		tempend.set(Calendar.HOUR_OF_DAY, 0);
		tempend.set(Calendar.MINUTE, 0);
		tempend.set(Calendar.SECOND, 0);
		tempend.set(Calendar.MILLISECOND, 0);
		tempend.add(Calendar.DATE, 1);
		
		double index = 1.0;
		if(!this.getEnd().after(tempend)){
			index = (( this.getEnd().get(Calendar.HOUR_OF_DAY) * 60.0 ) + 
					(this.getEnd().get(Calendar.MINUTE))) / (24.0 * 60.0);
		}

		return index;
	}
	
	/**
	 * Gets the start time of this
	 * @return the start time of the event/commitment
	 */
	public GregorianCalendar getStart(){
		GregorianCalendar result = new GregorianCalendar();
		if(event != null){
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(event.getStartTime().getTime());
			result = cal;
		}
		else if(comm != null){
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(comm.getDueDate().getTime());
			result = cal;
		}
		return result;
	}
	
	/**
	 * Gets the end time of this
	 * @return the end time of the event/commitment
	 */
	public GregorianCalendar getEnd(){
		GregorianCalendar result = new GregorianCalendar();
		if(event != null){
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(event.getEndTime().getTime());
			result = cal;
		}
		else if(comm != null){
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(comm.getDueDate().getTime());
			cal.add(Calendar.MINUTE, 30);
			result = cal;
		}
		return result;
	}

	/**
	 * Detmines if this and the given COP conflict
	 * @param other the other COP to compare
	
	 * @return whether this and other conflict in time */
	public boolean doesConflict(CalendarObjectPanel other){
		final double thisstart = this.getStartRatio();
		final double thisend = this.getEndRatio();
		final double otherstart = other.getStartRatio();
		final double otherend = other.getEndRatio();
		
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
