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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.GregorianCalendar;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;


/**
 * Detailed day is the basic display element for DayPane and WeekPane.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
 @SuppressWarnings("serial")
public class DetailedDay extends JPanel {
	
	JSeparator[] halfhourmarks= new JSeparator[48];
	SpringLayout layout = new SpringLayout();
	JPanel mainview = new JPanel();
	JComponent secondview = new JPanel();
	

	/**
	 * Constructor for DetailedDay.
	 * @param adate GregorianCalendar
	 */
	public DetailedDay(GregorianCalendar adate){
		this.setMinimumSize(new Dimension(50, 800));
		this.setPreferredSize(new Dimension(50, 800));
		this.addComponentListener(new resizeevent());
		this.setLayout(layout);
		
		layout.putConstraint(SpringLayout.WEST, mainview, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, mainview, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, mainview, 2, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, mainview, 0, SpringLayout.SOUTH, this);
		mainview.setBackground(new Color(0, 0, 0, 0));
		this.add(mainview, JLayeredPane.DEFAULT_LAYER);
	
		layout.putConstraint(SpringLayout.WEST, secondview, 
				2, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, secondview,
				0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, secondview, 
				-10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, secondview, 
				0, SpringLayout.SOUTH, this);
		secondview.setBackground(new Color(0, 0, 0, 0));
		this.add(secondview, JLayeredPane.PALETTE_LAYER);
		
		this.makelines();
		this.didResize();
	}
	
	/**
	 * Constructor for DetailedDay.
	 * @param adate GregorianCalendar
	 * @param secondview JComponent
	 */
	public DetailedDay(GregorianCalendar adate, JComponent secondview){
		this.setMinimumSize(new Dimension(50, 800));
		this.setPreferredSize(new Dimension(50, 800));
		this.addComponentListener(new resizeevent());
		this.secondview = secondview;
		this.setLayout(layout);
		
		layout.putConstraint(SpringLayout.WEST, mainview, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, mainview, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, mainview, 2, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, mainview, 0, SpringLayout.SOUTH, this);
		mainview.setBackground(new Color(0, 0, 0, 0));
		this.add(mainview);
		
		layout.putConstraint(SpringLayout.WEST, this.secondview, 
				2, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, this.secondview, 
				0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, this.secondview, 
				-10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, this.secondview,
				0, SpringLayout.SOUTH, this);
		this.secondview.setBackground(new Color(0, 0, 0, 0));
		this.add(this.secondview);
	
		this.makelines();
		this.didResize();
	}
	
	/**
 * Method makelines.
 */
protected void makelines(){
		//half hour marks code

		for(int i = 0; i < 48; i++){
			halfhourmarks[i] = new JSeparator();
			Color col;
			if(i % 2 == 0){
				col = Color.BLACK;
			}
			else{
				col = Color.GRAY;
			}
			halfhourmarks[i].setBackground(col);
			halfhourmarks[i].setForeground(col);
			this.add(halfhourmarks[i]);
		}
		layout.putConstraint(SpringLayout.NORTH, halfhourmarks[0], 0, SpringLayout.NORTH, this);
	}
	
	/**
	 * Method didResize.
	 */
	protected void didResize(){
		
		int x = (int)(((this.getSize().getWidth()) * 0.01) * ((this.getSize().getWidth()) * 0.01));
		x = x > 5 ? x : 5;
		x = x < 15 ? x : 15;
		layout.putConstraint(SpringLayout.WEST, halfhourmarks[0], x, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, halfhourmarks[0], -x, SpringLayout.EAST, this);
		
		for(int i = 1; i < 48; i++){
			int val = x;
			if(i % 2 == 1)
				{
				val*=2;
				}
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, halfhourmarks[i], 
								(int)((this.getSize().getHeight()) * i / 48.0),
								SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.WEST, halfhourmarks[i], val, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.EAST, 
					halfhourmarks[i], -val, SpringLayout.EAST, this);
		}
		
		this.revalidate();
		this.repaint();
		
	}
	protected class resizeevent implements ComponentListener {
	    public void componentResized(ComponentEvent e) {
	        // do stuff    
	    	didResize();
	    }

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void componentShown(ComponentEvent e) {
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
