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
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.AbCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.GUIEventController;

/**
 * Panel for the specific days in a month
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public class MonthDayPane extends JPanel {
	
	GregorianCalendar acal;
	JLabel datelabel;
	JPanel small;
	JPanel big;
	JScrollPane scroll;
	JLayer<JComponent> scrolllayer;
	SpringLayout layout;
	boolean enabled;
	static final int label_spacing = 3;
	
	List<Commitment> commlist = new ArrayList<Commitment>();
	List<Event> eventlist = new ArrayList<Event>();
	List<CalendarObjectWrapper> wraps = null;
	
	/**
	 * Constructor for MonthDayPane
	 * @param acal The current day that this should be associated with
	 * @param month The overall month the days should belong to
	 */
	public MonthDayPane(GregorianCalendar acal, int month){
		//constructor for the class variables

		layout = new SpringLayout();
		scroll = new JScrollPane();
		small = new JPanel();
		big = new JPanel();
		scrolllayer = new JLayer<JComponent>(scroll, new ScrollUI(this));
		
		this.acal = (GregorianCalendar)acal.clone();

		//sets the properties for this
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(50, 20));
		this.setBackground(CalendarStandard.CalendarYellow);
		this.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED));
		this.addMouseListener(new wholecheck());
		
		//creates and sets the date label
		datelabel = new JLabel("" + acal.get(Calendar.DATE));
		datelabel.setFont(CalendarStandard.CalendarFont);
		datelabel.setBackground(new Color(0, 0, 0, 0));
		layout.putConstraint(SpringLayout.NORTH, datelabel, 1, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, datelabel, 1, SpringLayout.WEST, this);
		this.add(datelabel);
		
		//the given day is not really in the month so set it to deactivate
		if (month != acal.get(Calendar.MONTH)){
			enabled = false;
			this.setBackground(new Color(220, 220, 220));
		}
		//the given day is in the month so set it to enabled
		else{
			enabled = true;
			
			//sets the scroll properties
			scroll.setPreferredSize(new Dimension(10, 10));
			scroll.setBorder(BorderFactory.createEmptyBorder());
			layout.putConstraint(SpringLayout.NORTH, scrolllayer, 0, SpringLayout.SOUTH, datelabel);
			layout.putConstraint(SpringLayout.WEST, scrolllayer, 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.SOUTH, scrolllayer, 0, SpringLayout.SOUTH, this);
			layout.putConstraint(SpringLayout.EAST, scrolllayer, 0, SpringLayout.EAST, this);
			this.add(scrolllayer);
			
			//sets small pane layout
			small.setLayout(new SpringLayout());
			small.setBackground(CalendarStandard.CalendarYellow);
			small.setPreferredSize(new Dimension(10, 10));
			small.addMouseListener(new scrollcheck());
			
			//sets big pane layout
			big.setLayout(new SpringLayout());
			big.setBackground(CalendarStandard.CalendarYellow);
			big.setPreferredSize(new Dimension(10, 10));
			big.addMouseListener(new scrollcheck());
			
			//set small as the default panel
			this.goSmall();
			
			//sets listener for the component resizing
			scroll.getViewport().addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					didResize();
				}
			});
		}
	}
	/**
	 * Change the commitment list to the given list
	 * @param  commlist the list to change to
	 */
	public void addCommitments(List<Commitment> cl) {
		if(enabled){
			commlist = cl;
			if(commlist == null){
				commlist = new ArrayList<Commitment>();
			}
			merge();
			didResize();
		}
	}
	
	/**
	 * Change the event list to the given list
	
	 * @param el List<Event>
	 */
	public void addEvents(List<Event> el) {
		if(enabled){
			eventlist = el;
			if(eventlist == null){
				eventlist = new ArrayList<Event>();
			}
			merge();
			didResize();
		}
	}
	
	/**
	 * Merge commitments and events so that they are ordered by time
	 */
	protected void merge(){
		wraps = new ArrayList<CalendarObjectWrapper>();
		//if we only have events
		if(commlist.isEmpty() && !eventlist.isEmpty()){
			for(Event event : eventlist){
				wraps.add(new CalendarObjectWrapper(event));
			}
		}
		//if we only have commitments
		else if(!commlist.isEmpty() && eventlist.isEmpty()){
			for(Commitment comm : commlist){
				wraps.add(new CalendarObjectWrapper(comm));
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
					ccal.add(Calendar.DATE, 2);
				}
				else{
					ccal.setTime(commlist.get(cindex).getDueDate().getTime());
				}
				
				if(eindex >= eventlist.size()){
					ecal.add(Calendar.DATE, 2);
				}
				else{
					ecal.setTime(eventlist.get(eindex).getStartTime().getTime());
				}
				
				if(ccal.before(ecal) && cindex < commlist.size()){
					wraps.add( new CalendarObjectWrapper(commlist.get(cindex)));
					cindex++;
				}
				else if(eindex < eventlist.size()){
					wraps.add( new CalendarObjectWrapper(eventlist.get(eindex)));
					eindex++;
				}
			}
		}
	}
	
	/**
	 * This did resize so all the CalendarObjectWrappers must be resized to proper layouts.
	 */
	protected void didResize(){
		if(enabled){
			small.removeAll();
			big.removeAll();
			if(wraps != null){
				this.setPreferredSize(this.getSize());
				final double boxheight = scroll.getViewport().getSize().getHeight();
				final double boxwidth = scroll.getViewport().getSize().getWidth();
				double height = 0.0;

				SpringLayout layout = (SpringLayout) big.getLayout();
				JLabel curlab = null, lastlab = null;

				for(CalendarObjectWrapper wrap: wraps){
					curlab = wrap;
					height += curlab.getPreferredSize().getHeight() + label_spacing;
					if (lastlab == null) {
						layout.putConstraint(SpringLayout.NORTH, curlab,
								1, SpringLayout.NORTH, big);
					} 
					else {
						layout.putConstraint(SpringLayout.NORTH, curlab,
								label_spacing, SpringLayout.SOUTH, lastlab);
					}
					layout.putConstraint(SpringLayout.WEST, curlab, 1, SpringLayout.WEST, big);
					
					if (curlab.getPreferredSize().getWidth() > boxwidth) {
						layout.putConstraint(SpringLayout.EAST, curlab, 0, SpringLayout.EAST, big);
					}
					
					curlab.setBackground(new Color(0, 0, 0, 0));
					big.add(curlab);
					lastlab = curlab;
				}
				
				big.setPreferredSize(new Dimension((int) boxwidth, (int) height));
				big.setSize(new Dimension((int) boxwidth, (int) height));

				layout = (SpringLayout) small.getLayout();
				curlab = lastlab = null;
				height = 0;

				for(CalendarObjectWrapper wrap: wraps){
					curlab = wrap.copy();
					height += curlab.getPreferredSize().getHeight() + label_spacing;
					
					if(height > boxheight && wraps.indexOf(wrap) > 0){
						break;
					}
					
					if(height + curlab.getPreferredSize().getHeight() +
							label_spacing > boxheight && ( wraps.size() - wraps.indexOf(wrap) > 1)){
						curlab = new JLabel("+" + (wraps.size() - wraps.indexOf(wrap)) + " more");
					}
					
					if (lastlab == null) {
						layout.putConstraint(SpringLayout.NORTH, curlab,
								1, SpringLayout.NORTH, small);
					} 
					else {
						layout.putConstraint(SpringLayout.NORTH, curlab,
								label_spacing, SpringLayout.SOUTH, lastlab);
					}
					layout.putConstraint(SpringLayout.WEST, curlab, 1, SpringLayout.WEST, small);
					
					if (curlab.getPreferredSize().getWidth() > boxwidth) {
						layout.putConstraint(SpringLayout.EAST, curlab,
								0, SpringLayout.EAST, small);
					}
					
					curlab.setBackground(new Color(0, 0, 0, 0));
					small.add(curlab);
					lastlab = curlab;
				}

				small.setPreferredSize(new Dimension((int) boxwidth, (int) height));
				small.setSize(new Dimension((int) boxwidth, (int) height));
				
				small.revalidate();
				small.repaint();
				big.revalidate();
				big.repaint();
				this.revalidate();
				this.repaint();
			}
		}
	}
	
	/**
	 * changes the view to the small panel and sets it to have no scrolling
	 */
	public void goSmall() {
		scroll.setViewportView(small);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.revalidate();
		this.repaint();
	}

	/**
	 * changes the view to the big panel and sets it to have only vertical
	 * scrolling as needed
	 */
	public void goBig() {
		scroll.setViewportView(big);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.getVerticalScrollBar().setValue(0);
		this.revalidate();
		this.repaint();
	}


	/**
	 * Internal class used to check if the user double clicked on the day
	 * @author Tianci
	 */
	protected class wholecheck extends MouseAdapter{
		/**
		 * Mouse event clicked that checks if a CalendarObjectWrapper was double clicked
		 * it will go to the day view for the clicked day
		 * @param e Does nothing
		 */
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() > 1){
				GUIEventController.getInstance().switchView(acal, AbCalendar.types.DAY);
			}
		}
	}


	/**
	 * Internal class used to check if the user double clicked on the day or a CalendarObjectWrapper
	 * @author Tianci
	 */
	protected class scrollcheck extends MouseAdapter{
		/**
		 * Mouse event clicked that checks if a CalendarObjectWrapper was clicked
		 * if so it will go to the edit for said event
		 * if not it will go to the day view for the clicked day
		 * @param e MouseEvent used to determine location and component clicked
		 */
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() > 1){
				try{
					final CalendarObjectWrapper lw = 
							(CalendarObjectWrapper)e.getComponent().getComponentAt(e.getPoint());
					lw.edit();
				}
				catch(Exception exp){
					GUIEventController.getInstance().switchView(acal, AbCalendar.types.DAY);
				}
			}
		}
	}
	

}


