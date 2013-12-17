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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.CalendarStandard;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.CommitmentList;
import edu.wpi.cs.wpisuitetng.modules.calendar.datatypes.EventList;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarData;
import edu.wpi.cs.wpisuitetng.modules.calendar.models.CalendarProperties;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.day.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.month.MonthView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.week.WeekView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.year.YearView;

/*
 * Sources:
 * Icons were developed using images obtained at: 
 * [1] http://changepointnea.com/~changepo/cms-assets/images/161361.calendar-icon.png
 * [2] http://pixabay.com/p-37197/?no_redirect
 */

/**
 * Abstract calendar panel that is identified by its enumerator.
 * @author CS Anonymous
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("serial")
public abstract class AbCalendar extends JPanel {
	protected boolean initialized;
	protected CalendarData myCalData;
	protected CalendarData teamCalData;
	protected CalendarProperties calProps;

	/**
	 * @author CS Anonymous
	 */
	public enum types {
		DAY(0),
		WEEK(1),
		MONTH(2),
		YEAR(3);

		private final int currentType;

		private types(int currentType) {
			this.currentType = currentType;
		}

		public int getCurrentType() {
			return currentType;
		}
	}

	protected types currenttype = types.DAY;
	protected GregorianCalendar mycal;

	protected JPanel viewpanel = new JPanel(); 
	protected JToggleButton[] viewbtns = new JToggleButton[4];
	protected JCheckBox showcom;

	protected int[] viewsizeval = {
			Calendar.DATE, Calendar.WEEK_OF_YEAR, Calendar.MONTH, Calendar.YEAR};
	protected CalendarView calView;
	protected CommitmentList commitments;
	protected EventList events;

	/**
	 * Constructor for AbCalendar.
	 */
	protected AbCalendar(){
		initialized = false;
		mycal = new GregorianCalendar();

		super.setBackground(Color.WHITE);

		// Draws GUI
		drawThis();

	}


	/**
	 * Method drawThis.
	 */
	abstract void drawThis();
	
	abstract JComponent getDataDisplayPanel();

	protected JComponent getViewButtonPanel(){
		final JPanel apane = new JPanel();
		apane.setBackground(Color.WHITE);
		apane.setLayout(new GridLayout(1, 4, 15, 0));

		viewbtns[0] = new JToggleButton();

		try {
			final Image img = ImageIO.read(getClass().getResource("Day_Icon.png"));
			viewbtns[0].setIcon(new ImageIcon(img));
			viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[0].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			viewbtns[0].setIcon(new ImageIcon());
			viewbtns[0].setText("Day");
		} 

		viewbtns[0].setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		viewbtns[0].setToolTipText("Calendar Day View");
		viewbtns[0].setBackground(Color.WHITE);
		viewbtns[0].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.DAY);
				viewbtns[0].setBorder(BorderFactory.createDashedBorder(
						CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			}
		});

		viewbtns[0].setBorder(BorderFactory.createDashedBorder(
				CalendarStandard.CalendarRed, 2, 2, 1, true));
		apane.add(viewbtns[0]);


		viewbtns[1] = new JToggleButton();

		try {
			final Image img = ImageIO.read(getClass().getResource("Week_Icon.png"));
			viewbtns[1].setIcon(new ImageIcon(img));
			viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[1].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			viewbtns[1].setIcon(new ImageIcon());
			viewbtns[1].setText("Week");
		}

		viewbtns[1].setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		viewbtns[1].setToolTipText("Calendar Week View");
		viewbtns[1].setBackground(Color.WHITE);
		viewbtns[1].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.WEEK);
				viewbtns[1].setBorder(BorderFactory.createDashedBorder(
						CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			}
		});

		apane.add(viewbtns[1]);


		viewbtns[2] = new JToggleButton();

		try {
			final Image img = ImageIO.read(getClass().getResource("Month_Icon.png"));
			viewbtns[2].setIcon(new ImageIcon(img));
			viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[2].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			viewbtns[2].setIcon(new ImageIcon());
			viewbtns[2].setText("Month");
		}

		viewbtns[2].setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		viewbtns[2].setToolTipText("Calendar Month View");
		viewbtns[2].setBackground(Color.WHITE);
		viewbtns[2].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.MONTH);
				viewbtns[2].setBorder(BorderFactory.createDashedBorder(
						CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			}
		});

		apane.add(viewbtns[2]);


		viewbtns[3] = new JToggleButton();

		try {
			final Image img = ImageIO.read(getClass().getResource("Year_Icon.png"));
			viewbtns[3].setIcon(new ImageIcon(img));
			viewbtns[3].setBorder(BorderFactory.createEmptyBorder());
			viewbtns[3].setContentAreaFilled(false);
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			viewbtns[3].setIcon(new ImageIcon());
			viewbtns[3].setText("Year");
		}

		viewbtns[3].setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this icon
		viewbtns[3].setToolTipText("Calendar Year View");
		viewbtns[3].setBackground(Color.WHITE);
		viewbtns[3].addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				switchview(types.YEAR);
				viewbtns[3].setBorder(BorderFactory.createDashedBorder(
						CalendarStandard.CalendarRed, 2, 2, 1, true));
				viewbtns[0].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[1].setBorder(BorderFactory.createEmptyBorder());
				viewbtns[2].setBorder(BorderFactory.createEmptyBorder());
			}
		});

		apane.add(viewbtns[3]);

		return apane;
	}

	/**
	 * Method stepCalendar.
	 * @param step int
	 */
	protected void stepCalendar(int step){
		if(step == 0){
			mycal = new GregorianCalendar();
		}
		else{
			mycal.add(viewsizeval[currenttype.getCurrentType()], step);
		}
		setView();
	}

	/**
	 * Method switchview.
	 * @param changeto types
	 */
	protected void switchview(types changeto){
		if(currenttype != changeto){

			for(int i = 0; i < 4; i++){
				viewbtns[i].setSelected(false);
			}

			currenttype = changeto;
			setView();
		}

		viewbtns[changeto.getCurrentType()].setSelected(true);
	}

	protected JComponent getDatePanel(){
		final JPanel apane = new JPanel();
		apane.setBackground(new Color(0, 0, 0, 0));
		apane.setOpaque(false);
		
		final JButton backwardbutton = new JButton();
		final JLabel backLabel = new JLabel();
		try {
			final Image img = ImageIO.read(getClass().getResource("BackArrow_Icon.png"));
			backLabel.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			backLabel.setText("<<");
			backLabel.setForeground(Color.WHITE);
		}
		backLabel.setBackground(CalendarStandard.CalendarRed);
		backLabel.setOpaque(true);
		backLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		backwardbutton.add(backLabel);
		backwardbutton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		backwardbutton.setFont(CalendarStandard.CalendarFont);
		backwardbutton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		backwardbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				stepCalendar(-1);
			}
		});

		final JButton todaybutton = new JButton();
		final JLabel todayLabel = new JLabel("Today");
		todayLabel.setBackground(CalendarStandard.CalendarRed);
		todayLabel.setOpaque(true);
		todayLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		todayLabel.setForeground(Color.WHITE);
		todaybutton.add(todayLabel);
		todaybutton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		todaybutton.setFont(CalendarStandard.CalendarFont);
		todaybutton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		todaybutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				stepCalendar(0);
			}
		});

		final JButton forwardbutton = new JButton();
		final JLabel forwardLabel = new JLabel();
		try {
			final Image img = ImageIO.read(getClass().getResource("ForwardArrow_Icon.png"));
			forwardLabel.setIcon(new ImageIcon(img));
		} catch (IOException ex) {}
		catch(IllegalArgumentException ex){
			forwardLabel.setText("<<");
			forwardLabel.setForeground(Color.WHITE);
		}
		forwardLabel.setBackground(CalendarStandard.CalendarRed);
		forwardLabel.setOpaque(true);
		forwardLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		forwardbutton.add(forwardLabel);
		forwardbutton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		forwardbutton.setFont(CalendarStandard.CalendarFont);
		forwardbutton.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
		// To change cursor as it moves over this button
		forwardbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				//Execute when button is pressed
				stepCalendar(1);
			}
		});

		apane.add(backwardbutton);
		apane.add(todaybutton);
		apane.add(forwardbutton);
		return apane;
	}

	public JComponent getComponent() {
		return this; 
	}

	/**
	 * Method setView.
	 */
	protected void setView(){
		viewpanel.removeAll();
		//System.out.println("Cal COUNT start: " + viewpanel.getComponentCount());
		viewpanel.setLayout(new GridLayout(1, 1));
		//TODO do views
		switch(currenttype.getCurrentType()){
		case(0):
			calView = (new DayView(mycal));
		break;
		case(1):
			calView = (new WeekView(mycal));
		break;
		case(2):
			calView = (new MonthView(mycal));
		break;
		case(3):
			calView = (new YearView(mycal));
		break;
		default:
			//TODO error
			break;
		}
		viewpanel.removeAll();
		viewpanel.setLayout(new GridLayout(1, 1));
		displayCalData();
		viewpanel.add(calView);
		
		viewpanel.revalidate();
		viewpanel.repaint();
		//System.out.println("Cal COUNT end: " + viewpanel.getComponentCount());

	}

	public void setCal(GregorianCalendar changeto){
		mycal.setTime(changeto.getTime());
		setView();
	}

	/**
	 * Method setCalsetView.
	 * @param acal GregorianCalendar
	 * @param switchtype TeamCalendar.types
	 */
	public void setCalsetView(GregorianCalendar acal, AbCalendar.types switchtype)
	{
		mycal.setTime(acal.getTime());
		switchview(switchtype);
		setView();
	}

	public boolean getShowCommitments(){
		return showcom.isSelected();
	}

	public CalendarData getMyCalData(){
		return myCalData;
	}
	
	public CalendarData getTeamCalData(){
		return teamCalData;
	}


	/**
	 * Method updateCalData.
	 */
	abstract void updateCalData();
	/**
	 * Method displayCalData.
	 */
	protected abstract void displayCalData();
	/**
	 * Method updateCommPane.
	 */

	protected abstract void updateCommPane();

	/**
	 * Method applyCalProps.
	 */
	abstract void applyCalProps();


	/**
	 * This function is called on Janeway shutdown to save the calendar props.
	 */
/*	public void saveProps(){
		UpdatePropsController.getInstance().updateCalendarProps(calProps);
	}*/

	public void setViewButtonToActive(AbCalendar.types switchView){
		final int k = switchView.ordinal();
		int i;
		for(i = 0; i < 4; i++){
			if(i == k){
				viewbtns[i].setBorder(BorderFactory.createDashedBorder(
						CalendarStandard.CalendarRed, 2, 2, 1, true));
			}
			else{
				viewbtns[i].setBorder(BorderFactory.createEmptyBorder());
			}
		}
	}

}
