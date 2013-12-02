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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;

public class MonthPane extends JScrollPane implements ICalPane {
	JPanel mainview;
	JComponent[] days = new JComponent[42];
	EventPane[] eventpane = new EventPane[42];
	CommitmentPane[] compane = new CommitmentPane[42];
	JSeparator[] seperator = new JSeparator[42];
	GregorianCalendar startdate = null;
	
	/**
	 * Constructor for the month pane
	 * @param acal the date of the month that should be displayed
	 */
	public MonthPane(GregorianCalendar acal) {
		super();
		mainview = new JPanel();
		mainview.setMinimumSize(new Dimension(800, 800));
		mainview.setLayout(new GridLayout(6, 7, 1, 1));
		
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(mainview);
		this.setColumnHeader();
		
		int month = acal.get(Calendar.MONTH);
		GregorianCalendar itcal = rewindcal(acal);
		startdate = (GregorianCalendar)itcal.clone();

		for (int i = 0; i < 42; i++) {
			days[i] = makeDay(itcal, i, month);
			days[i].addMouseListener(new AMouseEvent(itcal));
			mainview.add(days[i]);
			
			itcal.add(Calendar.DATE, 1);
		}
	}
	
	/**
	 * Rewinds the a copy of the given calendar to the first day of the week
	 * on or prior to the beginning of the month.
	 * @param acal the calendar to rewind
	 * @return the modified calendar
	 */
	protected GregorianCalendar rewindcal(GregorianCalendar acal){
		GregorianCalendar ret = (GregorianCalendar)acal.clone();
		
		while(ret.get(Calendar.DATE) != 1){
			ret.add(Calendar.DATE, -1);
		}
		while(ret.get(Calendar.DAY_OF_WEEK) != ret.getFirstDayOfWeek()){
			ret.add(Calendar.DATE, -1);
		}
		
		return ret;
	}
	
	/**
	 * Sets the column header with the day of the week for that column
	 */
	protected void setColumnHeader(){
		final JViewport port = new JViewport();
		final JPanel panel = new JPanel();
		final String[][] text = {{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
								{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}};
		final JLabel[] label = new JLabel[7];
		
		panel.setLayout(new GridLayout(1,7,1,1));
		for(int i = 0; i < 7; i++){
			label[i] = new JLabel("", SwingConstants.CENTER);
			label[i].setFont(CalendarStandard.CalendarFontBold);
			label[i].setForeground(Color.WHITE);
			label[i].setBackground(CalendarStandard.CalendarRed);
			panel.add(label[i]);
		}
		panel.setBackground(CalendarStandard.CalendarRed);
		port.setView(panel);
		
		port.addComponentListener(new ComponentAdapter(){
		    public void componentResized(ComponentEvent e) {
		    	double portx = port.getSize().getWidth();
		    	double viewx = panel.getSize().getWidth();
		    	
				for(int i = 0; i < 7; i++){
					label[i].setText(text[0][i]);
				}
				
				viewx = panel.getPreferredSize().getWidth();
				if(viewx > portx){
					for(int i = 0; i < 7; i++){
						label[i].setText(text[1][i]);
						viewx += label[i].getPreferredSize().getWidth();
					}
				}	
		    }
		});
		
		this.setColumnHeader(port);
		
	}
	
	/**
	 * Creates a day panel with the given information
	 * @param acal the current date that should be displayed
	 * @param offset the offset in the array that it should live
	 * @param month the current month that should be displayed
	 * @return the day containing the date label, event pane, and commitment pane
	 */
	protected JPanel makeDay(GregorianCalendar acal, int offset, int month){
		JPanel aday = new JPanel();
		SpringLayout layout = new SpringLayout();
		aday.setLayout(layout);
		
		if(month == acal.get(Calendar.MONTH))
			aday.setBackground(CalendarStandard.CalendarYellow);
		else
			aday.setBackground(new Color(220,220,220));
		
		JLabel daylab = new JLabel("" + acal.get(Calendar.DATE));
		layout.putConstraint(SpringLayout.NORTH, daylab, 0, SpringLayout.NORTH,
				aday);
		layout.putConstraint(SpringLayout.WEST, daylab, 0, SpringLayout.WEST,
				aday);
		daylab.setFont(CalendarStandard.CalendarFont);
		daylab.setBackground(new Color(0, 0, 0, 0));
		aday.add(daylab);
		seperator[offset] = new JSeparator(SwingConstants.VERTICAL);	
		layout.putConstraint(SpringLayout.NORTH, seperator[offset], 10, SpringLayout.SOUTH,
				daylab);
		layout.putConstraint(SpringLayout.SOUTH, seperator[offset], -10, SpringLayout.SOUTH,
				aday);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[offset], -2, SpringLayout.EAST,
				aday);
		seperator[offset].setBackground(CalendarStandard.CalendarRed);
		seperator[offset].setForeground(CalendarStandard.CalendarRed);
		if(acal.get(Calendar.MONTH) == month){
		aday.add(seperator[offset]);	
		}
		
		compane[offset] = new CommitmentPane();
		layout.putConstraint(SpringLayout.NORTH, compane[offset], -5, SpringLayout.NORTH,
				seperator[offset]);
		layout.putConstraint(SpringLayout.WEST, compane[offset], 1, SpringLayout.HORIZONTAL_CENTER,
				seperator[offset]);
		layout.putConstraint(SpringLayout.SOUTH, compane[offset], 5, SpringLayout.SOUTH,
				seperator[offset]);
		layout.putConstraint(SpringLayout.EAST, compane[offset], 0, SpringLayout.EAST,
				aday);
		compane[offset].setBackground(new Color(0, 0, 0, 0));
		compane[offset].addMouseListener(new PaneHover(offset, true));
		if(acal.get(Calendar.MONTH) == month){
			aday.add(compane[offset]);
		}
		
		eventpane[offset] = new EventPane();
		layout.putConstraint(SpringLayout.NORTH, eventpane[offset], -5, SpringLayout.NORTH,
				seperator[offset]);
		layout.putConstraint(SpringLayout.WEST, eventpane[offset], 0, SpringLayout.WEST,
				aday);
		layout.putConstraint(SpringLayout.SOUTH, eventpane[offset], 5, SpringLayout.SOUTH,
				seperator[offset]);
		layout.putConstraint(SpringLayout.EAST, eventpane[offset], 0, SpringLayout.HORIZONTAL_CENTER,
				seperator[offset]);
		eventpane[offset].setBackground(new Color(0, 0, 0, 0));
		eventpane[offset].addMouseListener(new PaneHover(offset, false));
		if(acal.get(Calendar.MONTH) == month){
			aday.add(eventpane[offset]);
		}
		days[offset] = aday;
		return aday;
	}

	/**
	 * Used to retrieve the month pane in JPanel form
	 * @return this in a JPanel wrapper
	 */
	public JPanel getPane() {
		JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 1));
		apanel.add(this);
		return apanel;
	}
	
	/** Displays commitments on the month pane
	 * @param commList List of commitments to be displayed
	 */
	public void displayCommitments(List<Commitment> commList) {
		System.out.println("comms: " + commList);
		for(int i = 0; i < 42; i++){
			compane[i].removeAll();
		}
		
		//if we are supposed to display commitments
		if(commList != null){
			
			for(int i = 0; i < 42; i++){
				SpringLayout layout = (SpringLayout)days[i].getLayout();
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[i], 0, SpringLayout.HORIZONTAL_CENTER, days[i]);
				MouseListener[] lists = compane[i].getMouseListeners();
				((PaneHover)lists[0]).setEnabled(true);
				lists = eventpane[i].getMouseListeners();
				((PaneHover)lists[0]).setEnabled(true);
			}
			
			CommitmentList alist = new CommitmentList();
			for(int i = 0; i < commList.size(); i++){
				alist.addCommitment(commList.get(i));
			}

			int index = 0;
			GregorianCalendar ret = (GregorianCalendar)startdate.clone();
			
			while(ret.get(Calendar.DATE) != 1){
				index++;
				ret.add(Calendar.DATE, 1);
			}
			
			compane[index].addCommitments(alist.filter(ret));
			index++;
			ret.add(Calendar.DATE, 1);
			
			while(ret.get(Calendar.DATE) != 1){
				compane[index].addCommitments(alist.filter(ret));
				index++;
				ret.add(Calendar.DATE, 1);
			}
			
		}
		else{
			for(int i = 0; i < 42; i++){
				SpringLayout layout = (SpringLayout)days[i].getLayout();
				layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[i], -2, SpringLayout.EAST, days[i]);
				MouseListener[] lists = compane[i].getMouseListeners();
				((PaneHover)lists[0]).setEnabled(false);
				lists = eventpane[i].getMouseListeners();
				((PaneHover)lists[0]).setEnabled(false);
				days[i].revalidate();
				days[i].repaint();
			}
		}
	}
	
	/**
	 * The internal class for representing commitments on a specific day in a month
	 */
	protected class CommitmentPane extends JScrollPane{
		List<Commitment> commList = null;
		JPanel small, big;
		
		/**
		 * constructor for the commitment pane
		 * goes through and initializes all the panels;
		 */
		public CommitmentPane(){
			super();
			this.setPreferredSize(new Dimension(50, 20));
			small = new JPanel();
			small.setLayout(new SpringLayout());
			small.setBackground(CalendarStandard.CalendarYellow);
			
			big = new JPanel();
			big.setLayout(new SpringLayout());
			big.setBackground(CalendarStandard.CalendarYellow);
			
			this.goSmall();
			this.setBackground(CalendarStandard.CalendarYellow);	
			this.setBorder(BorderFactory.createEmptyBorder());
			
			this.addComponentListener(new ComponentAdapter(){
			    public void componentResized(ComponentEvent e) {  
			    	didResize();
			    }
			});
			
			didResize();
		}
		
		/**
		 * Change this' commList to the given list
		 * @param commList the list to change to
		 */
		public void addCommitments(List<Commitment> commList){
			this.commList = commList;
			didResize();
		}
		
		/**
		 * used as the primary redrawing function. Will add labels to small until there
		 * are too many to fit properly where it will finish it up with a "+ ? more".
		 * Will add label to big until all commitments are added.
		 */
		protected void didResize(){
			if(commList != null){
				this.removeAll();
				int comsize = commList.size();
				this.setPreferredSize(this.getSize());
				double boxheight = this.getSize().getHeight();
				double boxwidth = this.getSize().getWidth();
				double height = 0.0;
				
				SpringLayout layout = (SpringLayout)big.getLayout();
				JLabel curlab = null, lastlab = null;
				
				for(int i = 0; i < comsize; i++){
					curlab = new JLabel("-" + commList.get(i).getName());
					curlab.setFont(CalendarStandard.CalendarFont);
					height += curlab.getPreferredSize().getHeight();
					if(lastlab == null ){
						layout.putConstraint(SpringLayout.NORTH, curlab, 0, SpringLayout.NORTH, big);
					}
					else{
						layout.putConstraint(SpringLayout.NORTH, curlab, 0, SpringLayout.SOUTH, lastlab);
					}
					layout.putConstraint(SpringLayout.WEST, curlab, 0, SpringLayout.WEST, big);
					layout.putConstraint(SpringLayout.EAST, curlab, 0, SpringLayout.EAST, big);
					
					curlab.setBackground(new Color(0,0,0,0));
					big.add(curlab);
					lastlab = curlab;
					
				}
				big.setPreferredSize(new Dimension( (int)boxwidth, (int)height));
				big.setSize(new Dimension( (int)boxwidth, (int)height));
				
				layout = (SpringLayout)small.getLayout();
				curlab = lastlab = null;
				height = 0;
				
				for(int i = 0; i < comsize; i ++){
					curlab = new JLabel("-" + commList.get(i).getName());
					if(height + curlab.getPreferredSize().getHeight() > boxheight){
						if(lastlab != null)
							lastlab.setText("+" + (comsize - i + 1) + " more");
						break;
					}
					
					height += curlab.getPreferredSize().getHeight();
					
					curlab.setFont(CalendarStandard.CalendarFont);
					if(lastlab == null ){
						layout.putConstraint(SpringLayout.NORTH, curlab, 0, SpringLayout.NORTH, small);
					}
					else{
						layout.putConstraint(SpringLayout.NORTH, curlab, 0, SpringLayout.SOUTH, lastlab);
					}
					layout.putConstraint(SpringLayout.WEST, curlab, 0, SpringLayout.WEST, small);
					layout.putConstraint(SpringLayout.EAST, curlab, 0, SpringLayout.EAST, small);
					
					curlab.setBackground(new Color(0,0,0,0));
					small.add(curlab);
					lastlab = curlab;
				}
				small.setPreferredSize(new Dimension( (int)boxwidth, (int)height));
				small.setSize(new Dimension( (int)boxwidth, (int)height));
			}
		}
		
		/**
		 * used to remove all labels from the big and small panels
		 * @override
		 */
		public void removeAll(){
			small.removeAll();
			big.removeAll();
		}
		
		/**
		 * changes the view to the small panel and sets it to have no scrolling
		 */
		public void goSmall(){
			this.setViewportView(small);
			this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		}
		
		/**
		 * changes the view to the big panel and sets it to have only vertical scrolling as needed
		 */
		public void goBig(){
			this.setViewportView(big);
			this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		}		
	}

	/**
	 * The internal class for representing events on a specific day in a month
	 */
	protected class EventPane extends JPanel{
		
	}
	
	/**
	 * Mouse listener class that is used to determine if the mouse is hovering over an 
	 * event or commitment panel. It will then make the appropriate panel go to its 
	 * expanded
	 */
	protected class PaneHover extends MouseAdapter{
		boolean iscom = false;
		boolean flag = false;
		int index;
		
		public PaneHover(int index, boolean iscompane){
			iscom = iscompane;
			this.index = index;
		}
		
		public void mouseEntered(MouseEvent e){
			if(flag){
				if(iscom){
					SpringLayout layout = (SpringLayout)days[index].getLayout();
					layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[index], 2, SpringLayout.WEST, days[index]);
					compane[index].goBig();
		    	}
		    	else{
		    		SpringLayout layout = (SpringLayout)days[index].getLayout();
					layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[index], -2, SpringLayout.EAST, days[index]);
		    	}
				
				days[index].revalidate();
	    		days[index].repaint();
			}
		}
		
		public void mouseExited(MouseEvent e){
			if(flag){
				if(iscom){
		    		SpringLayout layout = (SpringLayout)days[index].getLayout();
					layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[index], 0, SpringLayout.HORIZONTAL_CENTER, days[index]);
		    		compane[index].goSmall();
		    	}
		    	else{
		    		SpringLayout layout = (SpringLayout)days[index].getLayout();
					layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[index], 0, SpringLayout.HORIZONTAL_CENTER, days[index]);
		    	}
				days[index].revalidate();
	    		days[index].repaint();
			}
		}
		
		public void setEnabled(boolean flag){
			this.flag = flag;
		}
	}
	
	/**
	 * Mouse listener class that will listen for double clicking on a day then go to the
	 * that specific day in the day pane.
	 */
	protected class AMouseEvent extends MouseAdapter{
		GregorianCalendar adate;
		
		public AMouseEvent(GregorianCalendar adate){
			this.adate = (GregorianCalendar)adate.clone();
		}

	    public void mouseClicked(MouseEvent e) {
	    	if(e.getClickCount() > 1){
	    		//switch to day view
	    		System.out.println(adate.get(Calendar.DATE));
	    		GUIEventController.getInstance().switchView(adate, AbCalendar.types.DAY);
	    	}
	    }
	}
	
	/**
	 * A do nothing function because there are no scroll bars used 
	 */
	public void updateScrollPosition(int value){
		
	}
	
	/**
	 * A do nothing function that should eventually refresh the page.
	 */
	public void refresh(){
		
	}
}
