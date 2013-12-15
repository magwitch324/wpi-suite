/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: CS Anonymous
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.year;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarException;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedEventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ICalPane;

/**
  * @author CS Anonymous
  * @version $Revision: 1.0 $
  */
 @SuppressWarnings("serial")
public class YearPane extends JScrollPane implements ICalPane{
	GregorianCalendar supcal = null;
	YearMonthPane[] monthpanes = new YearMonthPane[12];
	JPanel mainview = null;
	int width = 4;//the current width in columns of the mainview
	int height = 3;//the current height in rows of the mainview
	Color defaultbackground = Color.WHITE;
	
	/**
	 * Constructor for the Year pane that creates the 12 months and sets up the
	 * scrollpane.
	 * @param acal the calendar holding the year to be displayed
	 */
	public YearPane(GregorianCalendar acal){
		mainview = new JPanel();
		mainview.setLayout(new GridLayout(height, width, 2, 2));
		mainview.setBackground(defaultbackground);
		
		this.setViewportView(mainview);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.getVerticalScrollBar().setBackground(CalendarStandard.CalendarYellow);
		this.getVerticalScrollBar().setUnitIncrement(20);
		this.setMinimumSize(new Dimension(100, 100));
		
		supcal = (GregorianCalendar)acal.clone();
		supcal.set(supcal.get(Calendar.YEAR), Calendar.JANUARY, 1);
		final GregorianCalendar temp = (GregorianCalendar)supcal.clone();
		
		for(int i = 0; i < 12; i++){
			monthpanes[i] = new YearMonthPane(temp);
			mainview.add(monthpanes[i]);
			temp.add(Calendar.MONTH, 1);
		}
		
		/**
		 * anonymous inner class that is used to check if this has resized.
		 * when it does it will change the grid layout to better fit the months
		 */
		this.addComponentListener(new ComponentAdapter(){
		    public void componentResized(ComponentEvent e) {
		    	final double cur_width = getViewport().getSize().getWidth();
		    	final double sup_width = monthpanes[0].getPreferredSize().getWidth();
		    	//Repeatedly goes through the resize until a proper size is found
		    	while(true){
			    	if(width * sup_width > cur_width && width > 1){
			    		width -= 1;
			    	}
			    	else if(((width + 1) * sup_width) < cur_width && width < 4){
			    		width += 1;
			    	}
			    	else{
			    		break;
			    	}
		    	}
	    		height = 12 / width;
	    		final GridLayout layout = (GridLayout)mainview.getLayout();
	    		layout.setColumns(width);
	    		layout.setRows(height);
	    		mainview.revalidate();
	    		mainview.repaint();
		    }
		});
	}
	/**
	 * Sends proper commitments down to the months
	 * @param commList
	 */
	public void displayCommitments(List<Commitment> commList) {
		// if we are supposed to display commitments
		if (commList != null) {
			final CombinedCommitmentList alist = new CombinedCommitmentList(commList);

			final GregorianCalendar ret = (GregorianCalendar) supcal.clone();

			for(int i = 0; i < 12; i++){
				try{
					monthpanes[i].displayCommitments(alist.filter(ret, Calendar.MONTH));
					ret.add(Calendar.MONTH, 1);
				}
				catch(CalendarException e){
					
				}
			}
		} else {
			for (int i = 0; i < 12; i++) {
				monthpanes[i].displayCommitments(null);
			}
		}
	}
	
	/**
	 * Sends proper events down to the months
	 * @param eventList
	 */
	public void displayEvents(List<Event> eventList) {
		// if we are supposed to display commitments
		if (eventList != null) {
			final CombinedEventList alist = new CombinedEventList(eventList);
			
			final GregorianCalendar tmpCal = (GregorianCalendar) supcal.clone();

			for(int i = 0; i < 12; i++){
				try{
					monthpanes[i].displayEvents(alist.filter(tmpCal, Calendar.MONTH));
					tmpCal.add(Calendar.MONTH, 1);
				}
				catch(CalendarException e){
					
				}
			}
		} else {
			for (int i = 0; i < 12; i++) {
				monthpanes[i].displayEvents(null);
			}
		}
	}

	/**
	 * returns this in a jpanel wrapper
	 */
	@Override
	public JPanel getPane() {
		final JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 1));
		apanel.add(this);
		return apanel;
	}

	/**
	 * 
	 */
	@Override
	public void updateScrollPosition(int value) {
	}
	/**
	 * 
	 */
	@Override
	public void refresh() {
	}
	
}
