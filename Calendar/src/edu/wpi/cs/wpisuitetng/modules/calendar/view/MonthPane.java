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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CombinedCommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.Event;

@SuppressWarnings("serial")
public class MonthPane extends JScrollPane implements ICalPane {
	JPanel mainview;
	MonthDayPane[] days = new MonthDayPane[42];
	GregorianCalendar startdate = null;
	int curmonth = 0;

	/**
	 * Constructor for the month pane
	 * 
	 * @param acal
	 *            the date of the month that should be displayed
	 */
	public MonthPane(GregorianCalendar acal) {
		super();
		mainview = new JPanel();
		mainview.setMinimumSize(new Dimension(200, 200));
		mainview.setPreferredSize(new Dimension(200,200));
		mainview.setLayout(new GridLayout(6, 7, 1, 1));
		
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(mainview);
		this.setColumnHeader();

		curmonth = acal.get(Calendar.MONTH);
		GregorianCalendar itcal = rewindcal(acal);
		startdate = (GregorianCalendar) itcal.clone();

		for (int i = 0; i < 42; i++) {
			days[i] = new MonthDayPane(itcal, curmonth);
			mainview.add(days[i]);
			itcal.add(Calendar.DATE, 1);
		}
	}

	/**
	 * Rewinds the a copy of the given calendar to the first day of the week on
	 * or prior to the beginning of the month.
	 * 
	 * @param acal
	 *            the calendar to rewind
	 * @return the modified calendar
	 */
	protected GregorianCalendar rewindcal(GregorianCalendar acal) {
		GregorianCalendar ret = (GregorianCalendar) acal.clone();

		while (ret.get(Calendar.DATE) != 1) {
			ret.add(Calendar.DATE, -1);
		}
		while (ret.get(Calendar.DAY_OF_WEEK) != ret.getFirstDayOfWeek()) {
			ret.add(Calendar.DATE, -1);
		}

		return ret;
	}

	/**
	 * Sets the column header with the day of the week for that column
	 */
	protected void setColumnHeader() {
		JViewport port = new JViewport();
		JPanel panel = new JPanel();
		final String[][] text = {
				{ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
						"Friday", "Saturday" },
				{ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" } };
		final JLabel[] label = new JLabel[7];

		panel.setPreferredSize(new Dimension(10, 40));
		panel.setLayout(new GridLayout(1, 7, 1, 1));
		for (int i = 0; i < 7; i++) {
			JPanel wrapper = new JPanel();
			wrapper.setLayout(new GridLayout(1, 1));
			wrapper.setBackground(CalendarStandard.CalendarRed);
			label[i] = new JLabel("", SwingConstants.CENTER);
			label[i].setFont(CalendarStandard.CalendarFontBold);
			label[i].setForeground(Color.WHITE);
			wrapper.add(label[i]);
			panel.add(wrapper);
		}
		port.setView(panel);

		port.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				boolean toobig = false;
				for (int i = 0; i < 7; i++) {
					label[i].setText(text[0][i]);
					if (label[i].getPreferredSize().getWidth() > label[i]
							.getParent().getSize().getWidth())
						toobig = true;
				}
				if (toobig) {
					for (int i = 0; i < 7; i++) {
						label[i].setText(text[1][i]);
					}
				}
			}
		});

		this.setColumnHeader(port);

	}
	/**
	 * Used to retrieve the month pane in JPanel form
	 * 
	 * @return this in a JPanel wrapper
	 */
	public JPanel getPane() {
		JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 1));
		apanel.add(this);
		return apanel;
	}

	/**
	 * Displays commitments on the month pane
	 * 
	 * @param commList
	 *            List of commitments to be displayed
	 */
	public void displayCommitments(List<Commitment> commList) {
		// if we are supposed to display commitments
		if (commList != null) {

			CombinedCommitmentList alist = new CombinedCommitmentList();
			for(Commitment comm: commList){
				alist.addCommitment(comm);
			}

			int index = 0;
			GregorianCalendar ret = (GregorianCalendar) startdate.clone();

			ret.set(ret.get(Calendar.YEAR), curmonth, 1);
			
			do{
				days[index].addCommitments(alist.filter(ret));
				index++;
				ret.add(Calendar.DATE, 1);
			}while(ret.get(Calendar.DATE) != 1);

		} else {
			for (int i = 0; i < 42; i++) {
				days[i].addCommitments(null);
			}
		}
	}
	/** The internal class for representing an individual day
	 * 
	 */
	protected class MonthDayPane extends JPanel {
		List<Commitment> commlist = null;
		List<Event> eventlist = null;
		List<wrapper> wraps = null;
		JLabel datelabel = null;
		List<MouseListener> listeners = null;
		JPanel small = null, big= null;
		JScrollPane scroll = null;
		boolean enabled;
		GregorianCalendar acal = null;
		/**
		 * 
		 * @param acal
		 * @param month
		 */
		public MonthDayPane(GregorianCalendar acal, int month){
			super();
			this.acal = acal;
			SpringLayout layout = new SpringLayout();
			this.setLayout(layout);
			this.setPreferredSize(new Dimension(50, 20));
			this.setBackground(CalendarStandard.CalendarYellow);
			this.setBorder(BorderFactory.createEmptyBorder());
			this.addMouseListener(new wholecheck());
			scroll = new JScrollPane();
			small = new JPanel();
			big = new JPanel();
			
			
			datelabel = new JLabel("" + acal.get(Calendar.DATE));
			layout.putConstraint(SpringLayout.NORTH, datelabel, 1, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.WEST, datelabel, 1, SpringLayout.WEST, this);
			datelabel.setFont(CalendarStandard.CalendarFont);
			datelabel.setBackground(new Color(0, 0, 0, 0));
			this.add(datelabel);
			
			if (month != acal.get(Calendar.MONTH)){
				enabled = false;
				this.setBackground(new Color(220, 220, 220));
			}
			else{
				enabled = true;
				this.setBackground(CalendarStandard.CalendarYellow);
				
				layout.putConstraint(SpringLayout.NORTH, scroll, 0, SpringLayout.SOUTH, datelabel);
				layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this);
				layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this);
				layout.putConstraint(SpringLayout.EAST, scroll, 0, SpringLayout.EAST, this);
				scroll.setPreferredSize(new Dimension(10,10));
				scroll.setBorder(BorderFactory.createEmptyBorder());
				scroll.addMouseListener(new scrollcheck());
				this.add(scroll);
				
				small.setLayout(new SpringLayout());
				small.setBackground(CalendarStandard.CalendarYellow);
				small.setPreferredSize(new Dimension(10,10));
				
				big.setLayout(new SpringLayout());
				big.setBackground(CalendarStandard.CalendarYellow);
				
				this.goSmall();
				
				scroll.getViewport().addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent e) {
						didResize();
					}
				});
			}
		}
		/**
		 * Change this' commList to the given list
		 * @param commList
		 *            the list to change to
		 */
		public void addCommitments(List<Commitment> commlist) {
			this.commlist = commlist;
			merge();
			didResize();
		}
		
		/**
		 * Change this' eventlist to the given list
		 * @param eventlist
		 *            the list to change to
		 */
		public void addEvents(List<Event> eventlist) {
			this.eventlist = eventlist;
			merge();
			didResize();
		}
		
		protected void merge(){
			wraps = new ArrayList<wrapper>();
			if(commlist == null && eventlist != null){
				for(Event event : eventlist){
					wraps.add(new wrapper(event));
				}
			}
			else if(commlist != null && eventlist == null){
				for(Commitment comm : commlist){
					wraps.add(new wrapper(comm));
				}
			}
			else if(commlist != null && eventlist != null){
				int eindex = 0;
				int cindex = 0;
				GregorianCalendar ccal = new GregorianCalendar();
				GregorianCalendar ecal = new GregorianCalendar();
				
				while(true){
					if(cindex == commlist.size() && eindex == eventlist.size()){
						break;
					}
					
					if(cindex == commlist.size()){
						ccal.setTime(commlist.get(cindex-1).getDueDate().getTime());
						ccal.add(Calendar.DATE, 1);
					}
					else{
						ccal.setTime(commlist.get(cindex).getDueDate().getTime());
					}
					
					if(eindex == eventlist.size()){
						ecal.setTime(eventlist.get(eindex).getStartTime().getTime());
						ecal.add(Calendar.DATE, 1);
					}
					else{
						ecal.setTime(eventlist.get(eindex).getStartTime().getTime());
					}
					
					if(ccal.before(ecal)){
						wraps.add( new wrapper(commlist.get(cindex)));
						cindex++;
					}
					else{
						wraps.add( new wrapper(eventlist.get(eindex)));
						eindex++;
					}
				}
			}
		}
		
		protected void didResize(){
			if(enabled){
				small.removeAll();
				big.removeAll();
				if(wraps != null){
					this.setPreferredSize(this.getSize());
					double boxheight = scroll.getViewport().getSize().getHeight();
					double boxwidth = scroll.getViewport().getSize().getWidth();
					double height = 0.0;

					SpringLayout layout = (SpringLayout) big.getLayout();
					JLabel curlab = null, lastlab = null;

					for(wrapper wrap: wraps){
						curlab = wrap.getLabel();
						height += curlab.getPreferredSize().getHeight();
						if (lastlab == null) {
							layout.putConstraint(SpringLayout.NORTH, curlab, 1, SpringLayout.NORTH, big);
						} 
						else {
							layout.putConstraint(SpringLayout.NORTH, curlab, 0, SpringLayout.SOUTH, lastlab);
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

					for(wrapper wrap: wraps){
						curlab = wrap.getLabel();
						height += curlab.getPreferredSize().getHeight();
						if(height > boxheight ){
							break;
						}
						
						if(height + curlab.getPreferredSize().getHeight() > boxheight && (wraps.size() - wraps.indexOf(wrap) > 1)){
							curlab = new JLabel("+" + (wraps.size() - wraps.indexOf(wrap)) + " more");
						}
						
						if (lastlab == null) {
							layout.putConstraint(SpringLayout.NORTH, curlab, 1, SpringLayout.NORTH, small);
						} 
						else {
							layout.putConstraint(SpringLayout.NORTH, curlab, 0, SpringLayout.SOUTH, lastlab);
						}
						layout.putConstraint(SpringLayout.WEST, curlab, 1, SpringLayout.WEST, small);
						
						if (curlab.getPreferredSize().getWidth() > boxwidth) {
							layout.putConstraint(SpringLayout.EAST, curlab, 0, SpringLayout.EAST, small);
						}
						
						curlab.setBackground(new Color(0, 0, 0, 0));
						small.add(curlab);
						lastlab = curlab;
					}

					small.setPreferredSize(new Dimension((int) boxwidth, (int) height));
					small.setSize(new Dimension((int) boxwidth, (int) height));
					
					small.revalidate();
					small.repaint();
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
		
		protected class wrapper{
			Commitment comm = null;
			Event event = null;
			
			public wrapper(Commitment comm){
				this.comm = comm;
			}
			
			public wrapper(Event event){
				this.event = event;
			}
			
			public GregorianCalendar getTime(){
				if(comm != null){
					return comm.getDueDate();
				}
				else{
					GregorianCalendar acal = new GregorianCalendar();
					acal.setTime(event.getStartTime().getTime());
					return acal;
				}
			}
			
			public JLabel getLabel(){
				if(comm != null){
					return new LabelWrapper(comm, "-" + comm.getName());
				}
				else{
					return new LabelWrapper(event, "-" + event.getName());
				}
			}
		}
		
		protected class LabelWrapper extends JLabel{
			Commitment comm = null;
			Event event = null;
			
			public LabelWrapper(Commitment comm, String text){
				super(text);
				this.setPreferredSize(super.getPreferredSize());
				this.comm = comm;
			}
			
			public LabelWrapper(Event event, String text){
				super(text);
				this.setPreferredSize(super.getPreferredSize());
				this.event = event;
			}
			
			public void edit(){
				if(comm != null){
					GUIEventController.getInstance().editCommitment(comm);
				}
				else{
					//GUIEventController.getInstance().editEvent(event);
				}
			}
			
		}
	
		protected class wholecheck extends MouseAdapter{
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() > 1){
					GUIEventController.getInstance().switchView(acal, AbCalendar.types.DAY);
				}
			}
			
			public void mouseExited(MouseEvent e){
				goSmall();
			}
		}

		protected class scrollcheck extends MouseAdapter{
			
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 1){
					goBig();
				}
				else if(e.getClickCount() > 1){
					try{
						LabelWrapper lw = null;
						if(scroll.getViewport().getView() == small){
							lw = (LabelWrapper)small.getComponentAt(e.getPoint());
						}
						else if(scroll.getViewport().getView() == big){
							lw = (LabelWrapper)big.getComponentAt(e.getPoint());
						}
						lw.edit();
					}
					catch(Exception exp){
						GUIEventController.getInstance().switchView(acal, AbCalendar.types.DAY);
					}
				}
			}
			
			public void mouseExited(MouseEvent e){
				goSmall();
			}
		}
	}

	/**
	 * Mouse listener class that is used to determine if the mouse is hovering
	 * over an event or commitment panel. It will then make the appropriate
	 * panel go to its expanded
	 */
	/*protected class PaneHover extends MouseAdapter {
		boolean iscom = false;
		boolean flag = false;
		int index;

		public PaneHover(int index, boolean iscompane) {
			iscom = iscompane;
			this.index = index;
		}

		public void mouseEntered(MouseEvent e) {
			resetPanes();
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				if (flag) {
					if (iscom) {
						compane[index].goBig();
						SpringLayout layout = (SpringLayout) days[index]
								.getLayout();
						layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
								seperator[index], 2, SpringLayout.WEST,
								days[index]);
					} else {
						SpringLayout layout = (SpringLayout) days[index]
								.getLayout();
						layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
								seperator[index], -2, SpringLayout.EAST,
								days[index]);
					}

					days[index].revalidate();
					days[index].repaint();
				}
			}
		}

		public void mouseExited(MouseEvent e) {
			if (flag) {
				if (iscom) {
					compane[index].goSmall();
					SpringLayout layout = (SpringLayout) days[index]
							.getLayout();
					layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
							seperator[index], 0,
							SpringLayout.HORIZONTAL_CENTER, days[index]);
				} else {
					SpringLayout layout = (SpringLayout) days[index]
							.getLayout();
					layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,
							seperator[index], 0,
							SpringLayout.HORIZONTAL_CENTER, days[index]);
				}
				days[index].revalidate();
				days[index].repaint();
			}
		}

		public void setEnabled(boolean flag) {
			this.flag = flag;
		}
	}

	protected void resetPanes() {
		for (int i = 0; i < 42; i++) {
			SpringLayout layout = (SpringLayout) days[i].getLayout();
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, seperator[i],
					0, SpringLayout.HORIZONTAL_CENTER, days[i]);
			compane[i].goSmall();
			days[i].revalidate();
			days[i].repaint();
		}
	}*/

	/**
	 * Mouse listener class that will listen for double clicking on a day then
	 * go to the that specific day in the day pane.
	 */
	protected class AMouseEvent extends MouseAdapter {
		GregorianCalendar adate;

		public AMouseEvent(GregorianCalendar adate) {
			this.adate = (GregorianCalendar) adate.clone();
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() > 1) {
				// switch to day view
				GUIEventController.getInstance().switchView(adate,
						AbCalendar.types.DAY);
			}
		}
	}

	/**
	 * A do nothing function because there are no scroll bars used
	 */
	public void updateScrollPosition(int value) {

	}

	/**
	 * A do nothing function that should eventually refresh the page.
	 */
	public void refresh() {

	}
}
