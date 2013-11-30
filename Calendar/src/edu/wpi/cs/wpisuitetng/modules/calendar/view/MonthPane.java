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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.models.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;

public class MonthPane extends JScrollPane implements ICalPane {
	JPanel mainview;
	JComponent[] days = new JComponent[42];
	JComponent[] eventpane = new JComponent[42];
	CommitmentPane[] compane = new CommitmentPane[42];
	GregorianCalendar startdate = null;
	
	/**
	 * 
	 * @param acal the date of the month that should be displayed
	 */
	public MonthPane(GregorianCalendar acal) {
		super();
		mainview = new JPanel();
		mainview.setMinimumSize(new Dimension(700, 700));
		mainview.setLayout(new GridLayout(6, 7, 1, 1));
		
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setViewportView(mainview);

		int month = acal.get(Calendar.MONTH);
		GregorianCalendar itcal = rewindcal(acal);
		startdate = (GregorianCalendar)itcal.clone();

		for (int i = 0; i < 42; i++) {
			JPanel aday = makeDay(itcal, i);
			if(month == itcal.get(Calendar.MONTH))
				aday.setBackground(CalendarStandard.CalendarYellow);
			else
				aday.setBackground(Color.LIGHT_GRAY);
			aday.addMouseListener(new AMouseEvent(acal, null));
			mainview.add(aday);
			
			itcal.add(Calendar.DATE, 1);
		}
	}
	
	/**
	 * 
	 * @param acal
	 * @return the calendar rewinded to the sunday on or before the first of the month
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
	 * 
	 * @param offset
	 * @return
	 */
	protected JPanel makeDay(GregorianCalendar acal, int offset){
		JPanel aday = new JPanel();
		SpringLayout layout = new SpringLayout();
		aday.setLayout(layout);
		
		JLabel daylab = new JLabel("" + acal.get(Calendar.DATE));
		layout.putConstraint(SpringLayout.NORTH, daylab, 0, SpringLayout.NORTH,
				aday);
		layout.putConstraint(SpringLayout.WEST, daylab, 0, SpringLayout.WEST,
				aday);
		daylab.setFont(CalendarStandard.CalendarFont);
		daylab.setBackground(new Color(0, 0, 0, 0));
		aday.add(daylab);
		
		compane[offset] = new CommitmentPane();
		layout.putConstraint(SpringLayout.NORTH, compane[offset], 0, SpringLayout.SOUTH,
				daylab);
		layout.putConstraint(SpringLayout.WEST, compane[offset], 0, SpringLayout.WEST,
				aday);
		layout.putConstraint(SpringLayout.SOUTH, compane[offset], 0, SpringLayout.SOUTH,
				aday);
		layout.putConstraint(SpringLayout.EAST, compane[offset], 0, SpringLayout.HORIZONTAL_CENTER,
				aday);
		compane[offset].setBackground(new Color(0, 0, 0, 0));
		aday.add(compane[offset]);
		
		eventpane[offset] = new JPanel();
		layout.putConstraint(SpringLayout.NORTH, eventpane[offset], 0, SpringLayout.SOUTH,
				daylab);
		layout.putConstraint(SpringLayout.WEST, eventpane[offset], 0, SpringLayout.HORIZONTAL_CENTER,
				aday);
		layout.putConstraint(SpringLayout.SOUTH, eventpane[offset], 0, SpringLayout.SOUTH,
				aday);
		layout.putConstraint(SpringLayout.EAST, eventpane[offset], 0, SpringLayout.EAST,
				aday);
		eventpane[offset].setBackground(new Color(0, 0, 0, 0));
		aday.add(eventpane[offset]);
		
		days[offset] = aday;
		return aday;
	}

	public JPanel getPane() {
		JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 1));
		apanel.add(this);
		return apanel;
	}
	
	/** Displays commitments on DetailedDay
	 * @param commList List of commitments to be displayed
	 * @param dayTeamCommList 
	 */
	public void displayCommitments(List<Commitment> commList) {
		System.out.println("comms: " + commList);
		for(int i = 0; i < 42; i++){
			compane[i].removeAll();
		}
		
		//if we are supposed to display commitments
		if(commList != null){
			CommitmentList alist = new CommitmentList();
			for(int i = 0; i < commList.size(); i++){
				alist.addCommitment(commList.get(i));
			}
			//alist.addCommitments((Commitment[])commList.toArray());
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
			
		}

	}
	
	protected class CommitmentPane extends JPanel{
		List<Commitment> commList = null;
		
		
		public CommitmentPane(){
			super();
			this.setPreferredSize(new Dimension(50, 20));
			this.setLayout(new SpringLayout());
			this.add(new JPanel());

			this.addComponentListener(new ComponentAdapter(){
			    public void componentResized(ComponentEvent e) {  
			    	didResize();
			    }
			});
			
			didResize();
		}
		
		public void addCommitments(List<Commitment> commList){
			this.commList = commList;
			//didResize();
		}
		
		protected void didResize(){
			if(commList != null){
				this.setBackground(this.getParent().getBackground());
				this.removeAll();
				int comsize = commList.size();
				double boxheight = this.getSize().getHeight();
				
				JLabel[] comlabs = new JLabel[comsize];
				double height = 0.0;
				int max = 0;
				int i;
				for(i = 0; i < comsize; i++){
					comlabs[i] = new JLabel("-" + commList.get(i).getName());
					comlabs[i].setFont(CalendarStandard.CalendarFont);
					if(height + comlabs[i].getPreferredSize().getHeight() > boxheight){
						break;
					}
					height += comlabs[i].getPreferredSize().getHeight();
				}
				
				max = i;
				if(i > 0 && max != comsize){
					comlabs[i-1] = new JLabel("+" + (comsize - max + 1) + " more");
				}
				SpringLayout layout = (SpringLayout)this.getLayout();
			
				//((GridLayout)this.getLayout()).setRows(max);

				for(i = 0; i < max; i ++){
					if(i == 0 ){
						layout.putConstraint(SpringLayout.NORTH, comlabs[i], 0, SpringLayout.NORTH, this);
					}
					else{
						layout.putConstraint(SpringLayout.NORTH, comlabs[i], 0, SpringLayout.SOUTH, comlabs[i-1]);
					}
					layout.putConstraint(SpringLayout.WEST, comlabs[i], 0, SpringLayout.WEST, this);
					layout.putConstraint(SpringLayout.EAST, comlabs[i], 0, SpringLayout.EAST, this);
					
					comlabs[i].setBackground(new Color(0,0,0,0));
					this.add(comlabs[i]);
				}
			}
		}
		
		public void removeAll(){
			super.removeAll();
		}
		
	}

	protected class AMouseEvent extends MouseAdapter{
		AbCalendar abCalendar;
		Calendar adate;
		
		public AMouseEvent(Calendar adate, AbCalendar abCalendar){
			this.adate = adate;
			this.abCalendar = abCalendar;
		}

	    public void mouseClicked(MouseEvent e) {
	    	if(e.getClickCount() > 1){
	    		//abCalendar.setCalsetView(adate, TeamCalendar.types.DAY);
	    	}
	    }
	}
	public void updateScrollPosition(int value){
		
	}
	public void refresh(){
		
	}
}
