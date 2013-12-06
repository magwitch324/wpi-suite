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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;

public class YearPane extends JScrollPane implements ICalPane{
	GregorianCalendar supcal = null;
	YearMonthPane[] monthpanes = new YearMonthPane[12];
	JPanel mainview = null;
	int width = 4;
	int height = 3;
	public YearPane(GregorianCalendar acal){
		super();
		mainview = new JPanel();
		mainview.setLayout(new GridLayout(height, width, 2, 2));
		
		this.setViewportView(mainview);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.supcal = (GregorianCalendar)acal.clone();
		this.supcal.set(Calendar.DAY_OF_YEAR, 1);
		
		GregorianCalendar temp = (GregorianCalendar)this.supcal.clone();
		
		for(int i = 0; i < 12; i++){
			monthpanes[i] = new YearMonthPane(temp);
			mainview.add(monthpanes[i]);
			temp.add(Calendar.MONTH, 1);
		}
		
		this.addComponentListener(new ComponentAdapter(){
		    public void componentResized(ComponentEvent e) {
		    	double cur_width = getViewport().getSize().getWidth();
		    	double sup_width = monthpanes[0].getPreferredSize().getWidth();
		    	System.out.println("Year " + getViewport().getSize() + " : " + width * sup_width);
		    	if(width * sup_width > cur_width && width > 1){
		    		width -= 1;
		    	}
		    	else if(((width + 1) * sup_width) < cur_width && width < 4){
		    		width += 1;
		    	}
	    		height = 12/width;
	    		GridLayout layout = (GridLayout)mainview.getLayout();
	    		layout.setColumns(width);
	    		layout.setRows(height);
	    		mainview.revalidate();
	    		mainview.repaint();
		    }
		});
	}
	
	protected class YearMonthPane extends JPanel{
		GregorianCalendar acal;
		YearDayPane[] daypanes = new YearDayPane[42];
		public YearMonthPane(GregorianCalendar acal){
			super();
			this.acal = (GregorianCalendar)acal.clone();
			
			SpringLayout layout = new SpringLayout();
			this.setLayout(layout);
			
			JLabel monthlbl = new JLabel(acal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
											SwingConstants.CENTER);
			JPanel temppane = new JPanel();
			temppane.setLayout(new GridLayout(1,1));
			temppane.setBackground(CalendarStandard.CalendarRed);
			
			monthlbl.setBackground(CalendarStandard.CalendarRed);
			monthlbl.setFont(CalendarStandard.CalendarFont);
			monthlbl.setForeground(Color.WHITE);
			layout.putConstraint(SpringLayout.WEST, temppane, 1, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, temppane, 2, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.EAST, temppane, -1, SpringLayout.EAST, this);
			temppane.add(monthlbl);
			temppane.setPreferredSize(new Dimension(10, 20));
			this.add(temppane);
			
			JPanel names = getDayNames();
			layout.putConstraint(SpringLayout.WEST, names, 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, names, 0, SpringLayout.SOUTH, temppane);
			layout.putConstraint(SpringLayout.EAST, names, 0, SpringLayout.EAST, this);
			this.add(names);
			
			JPanel days = new JPanel();
			days.setLayout(new GridLayout(6,7,1,1));
			
			GregorianCalendar temp = (GregorianCalendar)acal.clone();
			temp.set(Calendar.DAY_OF_WEEK, temp.getFirstDayOfWeek());
			
			for(int i = 0; i < 42; i++){
				daypanes[i] = new YearDayPane(temp, this.acal.get(Calendar.MONTH));
				days.add(daypanes[i]);
				temp.add(Calendar.DATE, 1);
			}

			layout.putConstraint(SpringLayout.WEST, days, 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.SOUTH, days, 0, SpringLayout.SOUTH, this);
			layout.putConstraint(SpringLayout.NORTH, days, 0, SpringLayout.SOUTH, names);
			layout.putConstraint(SpringLayout.EAST, days, 0, SpringLayout.EAST, this);
			this.add(days);
			
			int height = 0;
			height += daypanes[0].getPreferredSize().getHeight()*6;
			height += names.getPreferredSize().getHeight();
			System.out.println(names.getPreferredSize());
			height += monthlbl.getPreferredSize().getHeight();
			int width = (int)names.getPreferredSize().getWidth();
			this.setPreferredSize(new Dimension(width, height));


		}
		
		protected JPanel getDayNames(){
			String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
			JPanel apane = new JPanel();
			apane.setLayout(new GridLayout(1,7));
			double width = 0;
			JLabel lbl = null;
			for(int i = 0; i < 7; i++){
				lbl = new JLabel(days[i], SwingConstants.CENTER);
				lbl.setFont(CalendarStandard.CalendarFont.deriveFont(Font.PLAIN, 10));
				width = width > lbl.getPreferredSize().getWidth()? width : lbl.getPreferredSize().getWidth();
				apane.add(lbl);
			}
			apane.setPreferredSize(new Dimension((int)width*8, (int)lbl.getPreferredSize().getHeight()));
			System.out.println("Month " + apane.getPreferredSize());
			return apane;
		}
	}
	
	protected class YearDayPane extends JPanel{
		GregorianCalendar acal;
		
		public YearDayPane(GregorianCalendar acal, int month){
			super();
			this.acal = (GregorianCalendar)acal.clone();
			this.setLayout(new GridLayout(1,1));
			JLabel lbl = new JLabel("" + this.acal.get(Calendar.DATE), SwingConstants.CENTER);

			if(month == acal.get(Calendar.MONTH)){
				lbl.setForeground(new Color(0,0,0));
				this.setBackground(CalendarStandard.CalendarYellow);
			}
			else{
				lbl.setForeground(new Color(180,180,180));
				this.setBackground(new Color(220,220,220));
			}
			this.add(lbl);
			this.setPreferredSize(lbl.getPreferredSize());
		}
	}

	@Override
	public JPanel getPane() {
		JPanel apanel = new JPanel();
		apanel.setLayout(new GridLayout(1, 1));
		apanel.add(this);
		return apanel;
	}

	@Override
	public void updateScrollPosition(int value) {
	}

	@Override
	public void refresh() {
	}
}
