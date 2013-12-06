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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.BorderFactory;
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
	int width = 4;//the current width in columns of the mainview
	int height = 3;//the current height in rows of the mainview
	Color defaultbackground = Color.WHITE;
	
	/**
	 * Constructor for the Year pane that creates the 12 months and sets up the
	 * scrollpane.
	 * @param acal the calendar holding the year to be displayed
	 */
	public YearPane(GregorianCalendar acal){
		super();
		mainview = new JPanel();
		mainview.setLayout(new GridLayout(height, width, 2, 2));
		mainview.setBackground(defaultbackground);
		
		this.setViewportView(mainview);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBackground(defaultbackground);
		this.setMinimumSize(new Dimension(100,100));
		
		this.supcal = (GregorianCalendar)acal.clone();
		this.supcal.set(Calendar.DAY_OF_YEAR, 1);
		
		GregorianCalendar temp = (GregorianCalendar)this.supcal.clone();
		
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
		    	double cur_width = getViewport().getSize().getWidth();
		    	double sup_width = monthpanes[0].getPreferredSize().getWidth();
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
	    		height = 12/width;
	    		GridLayout layout = (GridLayout)mainview.getLayout();
	    		layout.setColumns(width);
	    		layout.setRows(height);
	    		mainview.revalidate();
	    		mainview.repaint();
		    }
		});
	}
	
	/**
	 * The inner class for a month representation on the year view.
	 * Creates the header for the month name, the weekday names, and then the
	 * 42 individual days.
	 */
	protected class YearMonthPane extends JPanel{
		GregorianCalendar acal;
		YearDayPane[] daypanes = new YearDayPane[42];
		static final int extra_height = 20;//extra padding given to proffered size
		static final int extra_width = 20;//extra padding given to proffered size
		/**
		 * the constructor for the month pane on the year pane
		 * @param acal the calendar holding the month to be displayed
		 */
		public YearMonthPane(GregorianCalendar acal){
			super();
			this.acal = (GregorianCalendar)acal.clone();
			
			SpringLayout layout = new SpringLayout();
			this.setLayout(layout);
			this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			
			//Creates the month lbl and a wrapper and places it in this at the top
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
			
			//Get the weekdays pane and sets below the month lbl wrapper
			JPanel names = getDayNames();
			layout.putConstraint(SpringLayout.WEST, names, 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, names, 0, SpringLayout.SOUTH, temppane);
			layout.putConstraint(SpringLayout.EAST, names, 0, SpringLayout.EAST, this);
			this.add(names);
			
			//Create the days pane and set the grid layout
			JPanel days = new JPanel();
			days.setLayout(new GridLayout(6,7,1,1));
			
			GregorianCalendar temp = (GregorianCalendar)acal.clone();
			temp.set(Calendar.DAY_OF_WEEK, temp.getFirstDayOfWeek());
			
			//add the individual days to the view
			for(int i = 0; i < 42; i++){
				daypanes[i] = new YearDayPane(temp, this.acal.get(Calendar.MONTH));
				days.add(daypanes[i]);
				temp.add(Calendar.DATE, 1);
			}

			layout.putConstraint(SpringLayout.WEST, days, 0, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.SOUTH, days, 0, SpringLayout.SOUTH, this);
			layout.putConstraint(SpringLayout.NORTH, days, 0, SpringLayout.SOUTH, names);
			layout.putConstraint(SpringLayout.EAST, days, 0, SpringLayout.EAST, this);
			days.setBackground(defaultbackground);
			this.add(days);
			
			//sets the proffered size based on all the children
			int height = 0;
			height += daypanes[0].getPreferredSize().getHeight()*6;
			height += names.getPreferredSize().getHeight();
			height += monthlbl.getPreferredSize().getHeight();
			height += extra_height;
			int width = (int)names.getPreferredSize().getWidth();
			width += extra_width;
			this.setPreferredSize(new Dimension(width, height));
		}
		/**
		 * Creates and returns the weekday names to be used by the constructor
		 * @return the panel with the weekday names
		 */
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
				lbl.setBackground(defaultbackground);
				apane.add(lbl);
			}
			apane.setPreferredSize(new Dimension((int)width*9, (int)lbl.getPreferredSize().getHeight()));
			apane.setBackground(defaultbackground);
			return apane;
		}
	}
	
	/**
	 *The internal class for a single day
	 */
	protected class YearDayPane extends JPanel{
		GregorianCalendar scal;
		boolean active = false;
		/**
		 * The constructor for year day pane
		 * @param acal the current day to display
		 * @param month	the current month, used to decide whether acal is part of the month
		 */
		public YearDayPane(final GregorianCalendar acal, int month){
			super();
			this.scal = (GregorianCalendar)acal.clone();
			this.setLayout(new GridLayout(1,1));
			JLabel lbl = new JLabel("" + this.scal.get(Calendar.DATE), SwingConstants.CENTER);
			active = month == acal.get(Calendar.MONTH);
			
			if(active){
				lbl.setForeground(new Color(0,0,0));
				this.setBackground(CalendarStandard.CalendarYellow);
				//adds double click feature to the days
				this.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						if(e.getClickCount() > 1){
							GUIEventController.getInstance().switchView(scal, AbCalendar.types.DAY);
						}
					}
				});
			}
			else{
				lbl.setForeground(new Color(180,180,180));
				this.setBackground(new Color(220,220,220));
			}
			this.add(lbl);
			this.setPreferredSize(lbl.getPreferredSize());
			this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		}
	}

	/**
	 * returns this in a jpanel wrapper
	 */
	@Override
	public JPanel getPane() {
		JPanel apanel = new JPanel();
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
